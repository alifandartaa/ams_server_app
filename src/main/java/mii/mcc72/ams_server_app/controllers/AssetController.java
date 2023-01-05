package mii.mcc72.ams_server_app.controllers;


import lombok.AllArgsConstructor;
import mii.mcc72.ams_server_app.models.Asset;
import mii.mcc72.ams_server_app.models.Employee;
import mii.mcc72.ams_server_app.models.History;
import mii.mcc72.ams_server_app.models.User;
import mii.mcc72.ams_server_app.models.dto.AssetDTO;
import mii.mcc72.ams_server_app.models.dto.ResponseData;
import mii.mcc72.ams_server_app.models.dto.ReviewAssetDTO;
import mii.mcc72.ams_server_app.models.dto.ReviewRentDTO;
import mii.mcc72.ams_server_app.services.AssetService;
import mii.mcc72.ams_server_app.services.UserService;
import mii.mcc72.ams_server_app.utils.RentStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/asset")
@PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN', 'FINANCE')")
@AllArgsConstructor
public class AssetController {

    private AssetService assetService;
    private UserService userService;


    @GetMapping
    public List<Asset> getAll() {
        return assetService.getAll();
    }

    @GetMapping("/{id}")
    public Asset getById(@PathVariable("id") int id) {
        return assetService.getById(id);
    }

    @PostMapping
    public ResponseEntity<ResponseData<Asset>> createSubmissionAsset(@RequestBody AssetDTO asset, Errors errors) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByUsername(auth.getName());
        return assetService.createSubmissionAsset(asset, user.getId(), errors);
    }

    @PreAuthorize("hasAuthority('UPDATE_FINANCE')")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseData<Asset>> update(@PathVariable int id, @RequestBody AssetDTO asset, Errors errors) {
        return assetService.update(asset, id, errors);
    }

    @PreAuthorize("hasAnyAuthority('UPDATE_ADMIN','UPDATE_FINANCE')")
    @PostMapping("/review_asset/{id}")
    public ResponseEntity<ResponseData<Asset>> reviewSubmissionRequest(@PathVariable int id, @RequestBody ReviewAssetDTO reviewAssetDTO) {
        return assetService.reviewSubmissionRequest(id, reviewAssetDTO);
    }

    @PreAuthorize("hasAuthority('READ_FINANCE')")
    @GetMapping("recent_review")
    public List<Asset> recentReviewAssetByFinance() {
        return assetService.getRecentReviewAsset();
    }

    @PreAuthorize("hasAuthority('DELETE_FINANCE')")
    @DeleteMapping("/{id}")
    public Asset delete(@PathVariable int id) {
        return assetService.delete(id);
    }
}
