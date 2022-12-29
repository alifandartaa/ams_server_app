package mii.mcc72.ams_server_app.controllers;

import lombok.AllArgsConstructor;
import mii.mcc72.ams_server_app.models.Asset;
import mii.mcc72.ams_server_app.models.History;
import mii.mcc72.ams_server_app.models.Report;
import mii.mcc72.ams_server_app.models.User;
import mii.mcc72.ams_server_app.services.AssetService;
import mii.mcc72.ams_server_app.services.EmpService;
import mii.mcc72.ams_server_app.services.UserService;
import mii.mcc72.ams_server_app.utils.AssetStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/user")
@PreAuthorize("hasRole('EMPLOYEE')")
@AllArgsConstructor
public class UserController {

    private EmpService empService;

    private UserService userService;

    @GetMapping("/penalty")
    public List<Report> getPenalty() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByUsername(auth.getName());
        return empService.getPenalty(user.getId());
    }
    @GetMapping("/available")
    public List<Asset> getAvailable(){
        System.out.println("get available");
        return empService.getAllAssetsByStatus(AssetStatus.APPROVED);
    }
    @GetMapping("/submissions")
    public List<Asset> getSubmission(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByUsername(auth.getName());
        return empService.getAllAssetsByStatusAndEmpId("PENDING_ADMIN", user.getId());
    }
    @GetMapping("/rent")
    public List<History> getRent(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByUsername(auth.getName());
        return empService.getAllHistoryByEmpId(user.getId());
    }


}
