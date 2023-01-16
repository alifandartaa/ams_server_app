package mii.mcc72.ams_server_app.controllers;

import lombok.AllArgsConstructor;
import mii.mcc72.ams_server_app.models.Report;
import mii.mcc72.ams_server_app.models.User;
import mii.mcc72.ams_server_app.models.dto.ReportDTO;
import mii.mcc72.ams_server_app.models.dto.ResponseData;
import mii.mcc72.ams_server_app.services.ReportService;
import mii.mcc72.ams_server_app.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/report")
@PreAuthorize("hasRole('ADMIN')")
@AllArgsConstructor
public class ReportController {
    private ReportService reportService;
    private UserService userService;

    @GetMapping
    public List<Report> getAll() {
        return reportService.getAll();
    }

    @GetMapping("/{id}")
    public Report getById(@PathVariable("id") int id) {
        return reportService.getById(id);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ResponseData<Report>> updateExistingReportById(@PathVariable int id, @RequestBody ReportDTO reportDTO, Errors errors) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByUsername(auth.getName());
        return reportService.updateExistingReportById(reportDTO, id, user.getId(), errors);
    }

    @DeleteMapping("/{id}")
    public Report delete(@PathVariable int id) {
        return reportService.delete(id);
    }
}
