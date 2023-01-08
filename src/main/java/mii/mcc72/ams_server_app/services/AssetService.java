package mii.mcc72.ams_server_app.services;

import mii.mcc72.ams_server_app.models.Asset;
import mii.mcc72.ams_server_app.models.Department;
import mii.mcc72.ams_server_app.models.History;
import mii.mcc72.ams_server_app.models.dto.AssetDTO;
import mii.mcc72.ams_server_app.models.dto.ResponseData;
import mii.mcc72.ams_server_app.models.dto.ReviewAssetDTO;
import mii.mcc72.ams_server_app.models.dto.ReviewRentDTO;
import mii.mcc72.ams_server_app.repos.AssetRepo;
import lombok.AllArgsConstructor;
import mii.mcc72.ams_server_app.repos.DepartmentRepo;
import mii.mcc72.ams_server_app.utils.AssetStatus;
import mii.mcc72.ams_server_app.utils.EmailSender;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class AssetService {

    private AssetRepo assetRepo;

    private DepartmentRepo departmentRepo;
    private EmployeeService employeeService;
    private CategoryService categoryService;

    private final TemplateEngine templateEngine;
    private final EmailSender emailSender;

    public List<Asset> getAll() {
        return assetRepo.findAll();
    }

    public List<Asset> getRecentReviewAsset() {
        return assetRepo.getRecentReviewAsset();
    }

    public Asset getById(int id) {
        return assetRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Asset ID %s Not Found !!", id))
        );
    }

    public AssetStatus getStatus() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {

            return AssetStatus.PENDING_FINANCE;
        } else {
            return AssetStatus.PENDING_ADMIN;
        }
    }

    public ResponseEntity<ResponseData<Asset>> createSubmissionAsset(@Valid AssetDTO assetDTO, int id, Errors errors) {
        if (assetRepo.existsAssetByName(assetDTO.getName())) {
            Asset targetAsset = assetRepo.findByName(assetDTO.getName()).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Asset Name %s Not Found !!", assetDTO.getName()))
            );
//             update(assetDTO, targetAsset.getId(), errors);
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

        asset.setApprovedStatus(getStatus());
        asset.setEmployee(employeeService.getById(id));
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
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        formatter.format(date);
//        try {
//            date = new SimpleDateFormat("dd/MM/yyyy").parse(assetDTO.getDate());
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
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

    public ResponseEntity<ResponseData<Asset>> reviewSubmissionRequest(@Valid int id, ReviewAssetDTO reviewAssetDTO) {
        ResponseData<Asset> responseData = new ResponseData<>();
        assetRepo.reviewSubmissionRequest(id, reviewAssetDTO.getAssetStatus());
        responseData.setStatus(true);
        responseData.setPayload(getById(id));
        if (reviewAssetDTO.getAssetStatus().equals(AssetStatus.APPROVED)) {
            responseData.setStatus(true);
            responseData.setPayload(getById(id));
            Asset asset = getById(id);
            Department department = asset.getEmployee().getDepartment();
            if (department.getBalance() > asset.getPrice()) {
                departmentRepo.calculateSubAssetWithBalance(asset.getPrice(), department.getId());
                Context ctx = new Context();
                ctx.setVariable("asset_name", asset.getName());
                ctx.setVariable("first_name", "Hi " + asset.getEmployee().getFirstName());
                ctx.setVariable("rent_status", "Submission Request " + asset.getName() + " " + reviewAssetDTO.getAssetStatus());
                ctx.setVariable("rent_list_link", "link");
                String htmlContent = templateEngine.process("mailtrap_template", ctx);
                try {
                    emailSender.send(
                            asset.getEmployee().getUser().getEmail(), "Your Submission Request Result",
                            htmlContent);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return ResponseEntity.ok(responseData);
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        return ResponseEntity.ok(responseData);
    }

    public Asset delete(int id) {
        Asset asset = getById(id);
        assetRepo.deleteById(id);
        return asset;
    }
}
