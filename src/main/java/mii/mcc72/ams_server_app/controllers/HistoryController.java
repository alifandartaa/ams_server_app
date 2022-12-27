package mii.mcc72.ams_server_app.controllers;

import lombok.AllArgsConstructor;
import mii.mcc72.ams_server_app.models.History;
import mii.mcc72.ams_server_app.models.dto.HistoryDTO;
import mii.mcc72.ams_server_app.models.dto.ResponseData;
import mii.mcc72.ams_server_app.services.HistoryService;
import mii.mcc72.ams_server_app.utils.RentStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/history")
@AllArgsConstructor
public class HistoryController {

    private HistoryService historyService;

    @GetMapping
    public List<History> getAll(){
        return historyService.getAll();
    }

    @GetMapping("broken")
    public List<History> getAllBrokenRentAsset(){
        return historyService.getAllBrokenRentAsset();
    }

    @GetMapping("/{id}")
    public History getById(@PathVariable("id") int id){
        return historyService.getById(id);
    }

    @PostMapping
    public ResponseEntity<ResponseData<History>> create(@RequestBody HistoryDTO history , Errors errors){
        return historyService.createRentRequest(history , errors);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseData<History>> update(@PathVariable int id, @RequestBody HistoryDTO history , Errors errors){
        return historyService.update(history , id, errors);
    }

    @PostMapping("/review_rent/{id}")
    public ResponseEntity<ResponseData<History>> reviewRentRequest(@PathVariable int id, @RequestParam("rent_status") RentStatus rentStatus){
        return historyService.reviewRentRequest(id, rentStatus);
    }

    @DeleteMapping("/{id}")
    public History delete(@PathVariable int id){
        return historyService.delete(id);
    }
}
