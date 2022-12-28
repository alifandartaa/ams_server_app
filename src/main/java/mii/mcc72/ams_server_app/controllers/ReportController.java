package mii.mcc72.ams_server_app.controllers;

import lombok.AllArgsConstructor;
import mii.mcc72.ams_server_app.models.Report;
import mii.mcc72.ams_server_app.models.dto.ReportDTO;
import mii.mcc72.ams_server_app.models.dto.ResponseData;
import mii.mcc72.ams_server_app.services.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/report")
@PreAuthorize("hasRole('ADMIN')")
@AllArgsConstructor
public class ReportController {
    private ReportService reportService;

    @GetMapping
    public List<Report> getAll(){
        return reportService.getAll();
    }

    @GetMapping("/{id}")
    public Report getById(@PathVariable("id") int id){
        return reportService.getById(id);
    }

//    @PostMapping
//    public ResponseEntity<ResponseData<Report>> create(@RequestBody ReportDTO reportDTO, Errors errors){
//        return reportService.create(reportDTO, errors);
//    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseData<Report>> updateExistingReportById(@PathVariable int id, @RequestBody ReportDTO reportDTO, Errors errors){
        return reportService.updateExistingReportById(reportDTO, id, errors);
    }

    @DeleteMapping("/{id}")
    public Report delete(@PathVariable int id){
        return reportService.delete(id);
    }
}
