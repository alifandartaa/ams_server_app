package mii.mcc72.ams_server_app.controllers;

import lombok.AllArgsConstructor;
import mii.mcc72.ams_server_app.models.Asset;
import mii.mcc72.ams_server_app.models.History;
import mii.mcc72.ams_server_app.models.Report;
import mii.mcc72.ams_server_app.models.User;
import mii.mcc72.ams_server_app.models.dto.HistoryDTO;
import mii.mcc72.ams_server_app.models.dto.PenaltyDTO;
import mii.mcc72.ams_server_app.models.dto.ResponseData;
import mii.mcc72.ams_server_app.services.EmpService;
import mii.mcc72.ams_server_app.services.UserService;
import mii.mcc72.ams_server_app.utils.AssetStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/emp")
@PreAuthorize("hasRole('EMPLOYEE')")
@AllArgsConstructor
public class EmpController {

    private EmpService empService;

    private UserService userService;

    //    dashboard
    @GetMapping("/penalty")
    public List<Report> getPenalty() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByUsername(auth.getName());
        return empService.getPenalty(user.getId());
    }

    @GetMapping("/available")
    public List<Asset> getAvailable() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByUsername(auth.getName());
        return empService.getAllAssetsByStatus(AssetStatus.APPROVED , user.getEmployee().getDepartment().getName());
    }

    @GetMapping("/submission")
    public List<Asset> getSubmission() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByUsername(auth.getName());
        return empService.getAllAssetsByStatusAndEmpId(AssetStatus.PENDING_ADMIN, user.getId());
    }

    @GetMapping("/rent")
    public List<History> getRent() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByUsername(auth.getName());
        return empService.getAllHistoryByEmpId(user.getId());
    }

//    ListPenalty
    @GetMapping("/listPenalty")
    public List<Object> getListPenalty() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByUsername(auth.getName());
        return empService.getListPenalty(user.getId());
    }
    @GetMapping("/penalty/{id}")
    public Object getById(@PathVariable int id) {
        return empService.getById(id);
    }

    @PostMapping("/rentAsset")
    public ResponseEntity<ResponseData<History>> rentAsset(@RequestBody HistoryDTO historyDTO , Errors errors) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByUsername(authentication.getName());
        return empService.createRentRequest(historyDTO , user.getId(), errors);
    }

    @PreAuthorize("hasAuthority('READ_FINANCE')")
    @GetMapping("/assets_pending_finance")
    public List<Asset> getAssetsPendingFinance(){
        return empService.getPendingFinanceAssets();
    }

}
