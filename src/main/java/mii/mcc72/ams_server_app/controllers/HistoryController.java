package mii.mcc72.ams_server_app.controllers;

import lombok.AllArgsConstructor;
import mii.mcc72.ams_server_app.models.History;
import mii.mcc72.ams_server_app.models.dto.HistoryDTO;
import mii.mcc72.ams_server_app.models.dto.ResponseData;
import mii.mcc72.ams_server_app.models.dto.ReviewRentDTO;
import mii.mcc72.ams_server_app.services.HistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/history")
@PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
@AllArgsConstructor
public class HistoryController {

    private HistoryService historyService;

    @GetMapping
    public List<History> getAll() {
        return historyService.getAll();
    }

    @PreAuthorize("hasAuthority('READ_FINANCE')")
    @GetMapping("broken")
    public List<History> getAllBrokenRentAsset() {
        return historyService.getAllBrokenRentAsset();
    }

    @GetMapping("/{id}")
    public History getById(@PathVariable("id") int id) {
        return historyService.getById(id);
    }

    @PostMapping
    public ResponseEntity<ResponseData<History>> createRentRequest(@RequestBody HistoryDTO history, Errors errors) {
        return historyService.createRentRequest(history, errors);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseData<History>> update(@PathVariable int id, @RequestBody HistoryDTO history, Errors errors) {
        return historyService.update(history, id, errors);
    }

    @PreAuthorize("hasAuthority('UPDATE_ADMIN')")
    @PutMapping("/review_rent/{id}")
    public ResponseEntity<ResponseData<History>> reviewRentRequest(@PathVariable int id, @RequestBody ReviewRentDTO reviewRentDTO) throws InterruptedException {
        return historyService.reviewRentRequest(id, reviewRentDTO);
    }

    @DeleteMapping("/{id}")
    public History delete(@PathVariable int id) {
        return historyService.delete(id);
    }
}
