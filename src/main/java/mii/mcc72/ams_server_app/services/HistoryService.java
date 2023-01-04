package mii.mcc72.ams_server_app.services;

import lombok.AllArgsConstructor;
import mii.mcc72.ams_server_app.models.History;
import mii.mcc72.ams_server_app.models.Report;
import mii.mcc72.ams_server_app.models.dto.HistoryDTO;
import mii.mcc72.ams_server_app.models.dto.ResponseData;
import mii.mcc72.ams_server_app.models.dto.ReviewRentDTO;
import mii.mcc72.ams_server_app.repos.AssetRepo;
import mii.mcc72.ams_server_app.repos.HistoryRepo;
import mii.mcc72.ams_server_app.utils.RentStatus;
import mii.mcc72.ams_server_app.utils.EmailSender;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class HistoryService {

    private HistoryRepo historyRepo;
    private AssetService assetService;
    private EmployeeService employeeService;
    private final TemplateEngine templateEngine;

    private final EmailSender emailSender;
    private final AssetRepo assetRepo;

    public List<History> getAll() {
        return historyRepo.findAll();
    }

    public List<History> getAllBrokenRentAsset(){
        return historyRepo.findAll().stream()
                .filter(history -> history.getStatus() == RentStatus.BROKEN).collect(Collectors.toList());
    }

    public History getById(int id) {
        return historyRepo.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("History ID %s Not Found !!", id))
        );
    }

    public ResponseEntity<ResponseData<History>> createRentRequest(@Valid HistoryDTO historyDTO, Errors errors) {
        ResponseData<History> responseData = new ResponseData<>();
        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.setStatus(true);
        History history = new History();
        Date startDate;
        Date endDate;
        try {
            startDate = new SimpleDateFormat("dd/MM/yyyy").parse(historyDTO.getStart());
            endDate = new SimpleDateFormat("dd/MM/yyyy").parse(historyDTO.getEnd());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        history.setNote(historyDTO.getNote());
        history.setStart(startDate);
        history.setEnd(endDate);
        history.setStatus(RentStatus.PENDING);
        history.setAsset(assetService.getById(historyDTO.getAssetId()));
        history.setEmployee(employeeService.getById(historyDTO.getEmployeeId()));
        Report report = new Report();
        report.setDateAccident(null);
        report.setDescIncident("");
        report.setDescIncident("");
        report.setPenalty(0L);
        report.setHistory(history);
        history.setReport(report);
        responseData.setPayload(historyRepo.save(history));
        return ResponseEntity.ok(responseData);
    }

    public ResponseEntity<ResponseData<History>> update( @Valid HistoryDTO historyDTO,int id, Errors errors) {
        ResponseData<History> responseData = new ResponseData<>();
        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.setStatus(true);
        History history = getById(id);
        Date startDate;
        Date endDate;
        try {
            startDate = new SimpleDateFormat("dd/MM/yyyy").parse(historyDTO.getStart());
            endDate = new SimpleDateFormat("dd/MM/yyyy").parse(historyDTO.getEnd());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        history.setNote(historyDTO.getNote());
        history.setStart(startDate);
        history.setEnd(endDate);
        history.setAsset(assetService.getById(historyDTO.getAssetId()));
        history.setEmployee(employeeService.getById(historyDTO.getEmployeeId()));
        responseData.setPayload(historyRepo.save(history));
        return ResponseEntity.ok(responseData);
    }

    public ResponseEntity<ResponseData<History>> reviewRentRequest(@Valid int id, ReviewRentDTO reviewRentDTO){
        ResponseData<History> responseData = new ResponseData<>();
        responseData.setStatus(true);
        History history = getById(id);
        if(reviewRentDTO.getRentStatus() == RentStatus.APPROVED){
        assetRepo.decreaseQtyAfterBroken(history.getAsset().getId());
        historyRepo.reviewRentRequest(id, reviewRentDTO.getRentStatus());
        }else if (reviewRentDTO.getRentStatus() == RentStatus.DONE){
            historyRepo.reviewRentRequest(id, reviewRentDTO.getRentStatus());
            assetRepo.increaseQty(history.getAsset().getId());
        }else {
            historyRepo.reviewRentRequest(id, reviewRentDTO.getRentStatus());
        }
        responseData.setPayload(getById(id));
        //send email before return
        Context ctx = new Context();
        ctx.setVariable("asset_name", history.getAsset().getName());
        ctx.setVariable("first_name", "Hi " + history.getEmployee().getFirstName());
        ctx.setVariable("rent_status", "Rent Request " + reviewRentDTO.getRentStatus());
        ctx.setVariable("rent_list_link", "link");
        String htmlContent = templateEngine.process("mailtrap_template", ctx);
        emailSender.send(
                history.getEmployee().getUser().getEmail(), "Your Rent Request Result",
                htmlContent);
        return ResponseEntity.ok(responseData);
    }

    public History delete(int id) {
        History history = getById(id);
        historyRepo.deleteById(id);
        return history;
    }
}