package mii.mcc72.ams_server_app.services;

import lombok.AllArgsConstructor;
import mii.mcc72.ams_server_app.models.Report;
import mii.mcc72.ams_server_app.models.dto.ReportDTO;
import mii.mcc72.ams_server_app.models.dto.ResponseData;
import mii.mcc72.ams_server_app.repos.ReportRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ReportService {
    private ReportRepo reportRepo;
    private EmployeeService employeeService;

    public List<Report> getAll(){
        return reportRepo.findAll();
    }

    public Report getById(int id){
        return reportRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Report ID %s Not Found !!", id))
        );
    }

    public ResponseEntity<ResponseData<Report>> create(@Valid ReportDTO reportDTO, Errors errors){
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
        return ResponseEntity.ok(responseData);
    }

    public ResponseEntity<ResponseData<Report>> update(@Valid ReportDTO reportDTO, int id, Errors errors){
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
        return ResponseEntity.ok(responseData);
    }

    public Report delete(int id){
        Report report = getById(id);
        reportRepo.deleteById(id);
        return report;
    }
}
