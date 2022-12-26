package mii.mcc72.ams_server_app.services;

import lombok.AllArgsConstructor;
import mii.mcc72.ams_server_app.models.History;
import mii.mcc72.ams_server_app.models.Report;
import mii.mcc72.ams_server_app.models.dto.HistoryDTO;
import mii.mcc72.ams_server_app.models.dto.ResponseData;
import mii.mcc72.ams_server_app.repos.HistoryRepo;
import mii.mcc72.ams_server_app.util.RentStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class HistoryService {

    private HistoryRepo historyRepo;
    private AssetService assetService;
    private EmployeeService employeeService;
    private ReportService reportService;

    public List<History> getAll() {
        return historyRepo.findAll();
    }

    public History getById(int id) {
        return historyRepo.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("History ID %s Not Found !!", id))
        );
    }

    public ResponseEntity<ResponseData<History>> create(@Valid HistoryDTO historyDTO, Errors errors) {
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
        history.setStatus(RentStatus.valueOf(historyDTO.getStatus()));
        history.setAsset(assetService.getById(historyDTO.getAssetId()));
        history.setEmployee(employeeService.getById(historyDTO.getEmployeeId()));
        Report report = new Report();
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
        history.setStatus(RentStatus.valueOf(historyDTO.getStatus()));
        history.setAsset(assetService.getById(historyDTO.getAssetId()));
        history.setEmployee(employeeService.getById(historyDTO.getEmployeeId()));
        responseData.setPayload(historyRepo.save(history));
        return ResponseEntity.ok(responseData);
    }

    public History updateReport(int id , int repId){
        History history = getById(id);
        history.setReport(reportService.getById(repId));
        return historyRepo.save(history);
    }
    public History delete(int id) {
        History history = getById(id);
        historyRepo.deleteById(id);
        return history;
    }
}