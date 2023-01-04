package mii.mcc72.ams_server_app.controllers;

import lombok.AllArgsConstructor;
import mii.mcc72.ams_server_app.models.*;
import mii.mcc72.ams_server_app.services.EmpService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import mii.mcc72.ams_server_app.models.dto.HistoryDTO;
import mii.mcc72.ams_server_app.models.dto.ResponseData;
import mii.mcc72.ams_server_app.services.AdminService;
import mii.mcc72.ams_server_app.services.AssetService;
import mii.mcc72.ams_server_app.services.HistoryService;
import mii.mcc72.ams_server_app.services.UserService;
import mii.mcc72.ams_server_app.utils.AssetStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
@AllArgsConstructor
public class AdminController {

    private EmpService empService;
    private AdminService adminService;
    private HistoryService historyService;
    private AssetService assetService;
    private UserService userService;
//
//    @PreAuthorize("hasAuthority('READ_ADMIN')")
//    @GetMapping("/assets_pending_admin")
//    public List<Asset> getAssetsPendingAdmin() {
//        return adminService.getPendingAdminAssets();
//    }
//
////    @GetMapping("/rentReview")
////    public List<History> getAllRentAsset(){
////        return historyService.getAll();
////    }
////
////    @GetMapping("/submissionReview")
////    public List<Asset> getAllSubmissionAsset(){
////        return assetService.getAll();
////    }
//    //    dashboard
//    @GetMapping("/penalty")
//    public List<Report> getPenalty() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User user = userService.getByUsername(auth.getName());
//        return adminService.getPenalty(user.getId());
//    }
//
//    @GetMapping("/available")
//    public List<Asset> getAvailable() {
//        System.out.println("get available");
//        return adminService.getAllAssetsByStatus(AssetStatus.APPROVED);
//    }
//
//    @GetMapping("/submission")
//    public List<Asset> getSubmission() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User user = userService.getByUsername(auth.getName());
//        return adminService.getAllAssetsByStatusAndEmpId(AssetStatus.PENDING_ADMIN, user.getId());
//    }
//
//
////    ListPenalty
//    @GetMapping("/listPenalty")
//    public List<Object> getListPenalty() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User user = userService.getByUsername(auth.getName());
//        return adminService.getListPenalty(user.getId());
//    }
//
//    @GetMapping("/penalty/{id}")
//    public Object getById(@PathVariable int id) {
//        return adminService.getById(id);
//    }
//
//    @PostMapping("/rentAsset")
//    public ResponseEntity<ResponseData<History>> rentAsset(@RequestBody HistoryDTO historyDTO, Errors errors) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = userService.getByUsername(authentication.getName());
//        return adminService.createRentRequest(historyDTO, user.getId(), errors);
//    }
    @GetMapping("/submission")
    public List<Asset> getSubmission() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByUsername(auth.getName());
        return adminService.getSubmission(user.getId());
    }
    @GetMapping("/reqSubmission")
    public List<Asset> getReqSubmission() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByUsername(auth.getName());
        return adminService.getReqSubmission(user.getId(), user.getEmployee().getDepartment().getId());
    }
    @GetMapping("/listSubmission")
    public List<Asset> getListSubmission() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByUsername(auth.getName());
        return adminService.getListSubmission(user.getId() , user.getEmployee().getDepartment().getId());
    }

    @GetMapping("/getQty/{id}")
    public Asset getQty(@PathVariable int id) {
        return adminService.getQty(id);
    }
    @GetMapping("/reqRent")
    public List<History> getReqRent() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByUsername(auth.getName());
        return adminService.getReqRent(user.getEmployee().getDepartment().getId());
    }
    @GetMapping("/rent")
    public List<History> getRent() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByUsername(auth.getName());
        return adminService.getRent(user.getEmployee().getDepartment().getId());
    }
    @GetMapping("/report")
    public List<Object> getReport() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByUsername(auth.getName());
        return adminService.getReport(user.getId());
    }
    @GetMapping("/return")
    public List<History> getReqReturn() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByUsername(auth.getName());
        return adminService.getReqReturn(user.getEmployee().getDepartment().getId());
    }

    @GetMapping("/user")
    public List<Employee> getAllEmployee() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByUsername(auth.getName());
        return adminService.getAllEmployee( user.getId());
    }
    
    @GetMapping("/list-user")
    public List<User> getALlUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByUsername(auth.getName());
        return userService.getAllUser();
    }

    @GetMapping("/list-user")
    public List<User> getALlUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByUsername(auth.getName());
        return userService.getAllUser();
    }

}
