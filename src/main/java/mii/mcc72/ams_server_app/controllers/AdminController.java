package mii.mcc72.ams_server_app.controllers;

import lombok.AllArgsConstructor;
import mii.mcc72.ams_server_app.models.Asset;
import mii.mcc72.ams_server_app.models.History;
import mii.mcc72.ams_server_app.models.User;
import mii.mcc72.ams_server_app.services.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<User> getAllEmployee() {
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

    @PutMapping("/change-status/{id}")
    public User changeStatus(@PathVariable int id) {

        return userService.changeEnabled(id);
    }
}
