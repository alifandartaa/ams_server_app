package mii.mcc72.ams_server_app.services;

import lombok.AllArgsConstructor;
import mii.mcc72.ams_server_app.models.Asset;
import mii.mcc72.ams_server_app.models.History;
import mii.mcc72.ams_server_app.models.Report;
import mii.mcc72.ams_server_app.models.dto.ReportDTO;
import mii.mcc72.ams_server_app.models.dto.ResponseData;
import mii.mcc72.ams_server_app.repos.AssetRepo;
import mii.mcc72.ams_server_app.repos.HistoryRepo;
import mii.mcc72.ams_server_app.repos.ReportRepo;
import mii.mcc72.ams_server_app.utils.EmailSender;
import mii.mcc72.ams_server_app.utils.RentStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ReportService {
    private ReportRepo reportRepo;
    private HistoryRepo historyRepo;

    private AssetRepo assetRepo;
    private EmployeeService employeeService;

    private AssetService assetService;

    private final TemplateEngine templateEngine;
    private final EmailSender emailSender;

    public List<Report> getAll(){
        return reportRepo.findAll();
    }

    public Report getById(int id){
        return reportRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Report ID %s Not Found !!", id))
        );
    }

    public ResponseEntity<ResponseData<Report>> updateExistingReportById(@Valid ReportDTO reportDTO, int id, Errors errors){
        historyRepo.reviewRentRequest(id, RentStatus.BROKEN);
        assetRepo.decreaseQtyAfterBroken(historyRepo.findById(id).get().getAsset().getId());
        getById(id);
        ResponseData<Report> responseData = new ResponseData<>();
        if(errors.hasErrors()){
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.setStatus(true);
        Report report = new Report();
        report.setId(id);
        Date dateAccident;
        try {
            dateAccident = new SimpleDateFormat("dd/MM/yyyy").parse(reportDTO.getDateAccident());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        report.setDateAccident(dateAccident);
        report.setDescDamage(reportDTO.getDescDamage());
        report.setDescIncident(reportDTO.getDescIncident());
        report.setPenalty(reportDTO.getPenalty());
        report.setEmployee(employeeService.getById(reportDTO.getAdminId()));
        responseData.setPayload(reportRepo.save(report));
        Context ctx = new Context();
//        ctx.setVariable("penalty", asset.getName());
        History history = historyRepo.findById(id).get();
        ctx.setVariable("first_name", "Hi " + history.getEmployee().getFirstName());
        ctx.setVariable("penalty_cost", report.getPenalty());
//        ctx.setVariable("penalty_title", "link");
        String htmlContent = templateEngine.process("template_penalty", ctx);
        emailSender.send(
                history.getEmployee().getUser().getEmail(), "Penalty Rent Report",
                htmlContent);
        return ResponseEntity.ok(responseData);
    }

//    public void decreaseQtyAfterBroken(int id){
//        Asset asset = historyRepo.findById(id).get().getAsset();
//        asset.setQty(asset.getQty()-1);
//        assetRepo.save(asset);
//    }

    public Report delete(int id){
        Report report = getById(id);
        reportRepo.deleteById(id);
        return report;
    }
}
