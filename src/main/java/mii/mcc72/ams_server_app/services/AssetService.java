package mii.mcc72.ams_server_app.services;

import mii.mcc72.ams_server_app.models.Asset;
import mii.mcc72.ams_server_app.models.dto.AssetDTO;
import mii.mcc72.ams_server_app.models.dto.ResponseData;
import mii.mcc72.ams_server_app.repos.AssetRepo;
import lombok.AllArgsConstructor;
import mii.mcc72.ams_server_app.utils.AssetStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class AssetService {

    private AssetRepo assetRepo;
    private EmployeeService employeeService;
    private CategoryService categoryService;

    public List<Asset> getAll() {
        return assetRepo.findAll();
    }

    public Asset getById(int id) {
        return assetRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Asset ID %s Not Found !!", id))
        );
    }

    public ResponseEntity<ResponseData<Asset>> createSubmissionAsset(@Valid AssetDTO assetDTO, Errors errors) {
        if (assetRepo.existsAssetByName(assetDTO.getName())) {
            Asset targetAsset = assetRepo.findByName(assetDTO.getName()).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Asset Name %s Not Found !!", assetDTO.getName()))
            );
            update(assetDTO, targetAsset.getId(), errors);
        }
        ResponseData<Asset> responseData = new ResponseData<>();
        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.setStatus(true);
        Asset asset = new Asset();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        formatter.format(date);
//        try {
////            date = new SimpleDateFormat("dd/MM/yyyy").parse(assetDTO.getDate());
////            Date date = new Date();
//        } catch (ParseException e) {
//            responseData.setStatus(false);
//            responseData.setPayload(null);
//            responseData.setMessages(Collections.singletonList(e.getMessage()));
//            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(responseData);
//        }
        asset.setQty(assetDTO.getQty());
        asset.setName(assetDTO.getName());
        asset.setDescription(assetDTO.getDescription());
        asset.setPrice(assetDTO.getPrice());
        asset.setImage(assetDTO.getImage());
        asset.setDate(date);
        asset.setApprovedStatus(AssetStatus.PENDING);
        asset.setEmployee(employeeService.getById(assetDTO.getEmployeeId()));
        asset.setCategory(categoryService.getById(assetDTO.getCategoryId()));
        responseData.setPayload(assetRepo.save(asset));
        return ResponseEntity.ok(responseData);
    }

    public ResponseEntity<ResponseData<Asset>> update(@Valid AssetDTO assetDTO, int id, Errors errors) {
        getById(id);
        ResponseData<Asset> responseData = new ResponseData<>();
        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.setStatus(true);
        Asset asset = new Asset();
        asset.setId(id);
        Date date;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(assetDTO.getDate());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        asset.setQty(assetDTO.getQty());
        asset.setName(assetDTO.getName());
        asset.setDescription(assetDTO.getDescription());
        asset.setPrice(assetDTO.getPrice());
        asset.setImage(assetDTO.getImage());
        asset.setDate(date);
        asset.setApprovedStatus(AssetStatus.valueOf(assetDTO.getStatus()));
        asset.setEmployee(employeeService.getById(assetDTO.getEmployeeId()));
        asset.setCategory(categoryService.getById(assetDTO.getCategoryId()));
        responseData.setPayload(assetRepo.save(asset));
        return ResponseEntity.ok(responseData);
    }

    public Asset delete(int id) {
        Asset asset = getById(id);
        assetRepo.deleteById(id);
        return asset;
    }
}
