package mii.mcc72.ams_server_app.controllers;


import lombok.AllArgsConstructor;
import mii.mcc72.ams_server_app.models.Asset;
import mii.mcc72.ams_server_app.models.Employee;
import mii.mcc72.ams_server_app.models.dto.AssetDTO;
import mii.mcc72.ams_server_app.models.dto.ResponseData;
import mii.mcc72.ams_server_app.services.AssetService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/asset")
@PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN', 'FINANCE')")
@AllArgsConstructor
public class AssetController {

    private AssetService assetService;

    @GetMapping
    public List<Asset> getAll(){
        return assetService.getAll();
    }

    @GetMapping("/{id}")
    public Asset getById(@PathVariable("id") int id){
        return assetService.getById(id);
    }

    @PostMapping
    public ResponseEntity<ResponseData<Asset>> createSubmissionAsset(@RequestBody AssetDTO asset , Errors errors){
        return assetService.createSubmissionAsset(asset , errors);
    }

    @PreAuthorize("hasAuthority('UPDATE_FINANCE')")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseData<Asset>> update(@PathVariable int id, @RequestBody AssetDTO asset , Errors errors){
        return assetService.update(asset , id, errors);
    }

    @PreAuthorize("hasAuthority('DELETE_FINANCE')")
    @DeleteMapping("/{id}")
    public Asset delete(@PathVariable int id){
        return assetService.delete(id);
    }
}
