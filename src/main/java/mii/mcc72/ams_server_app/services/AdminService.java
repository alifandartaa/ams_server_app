package mii.mcc72.ams_server_app.services;

import lombok.AllArgsConstructor;
import mii.mcc72.ams_server_app.models.Asset;
import mii.mcc72.ams_server_app.models.Employee;
import mii.mcc72.ams_server_app.models.History;
import mii.mcc72.ams_server_app.models.Report;
import mii.mcc72.ams_server_app.models.dto.HistoryDTO;
import mii.mcc72.ams_server_app.models.dto.PenaltyDTO;
import mii.mcc72.ams_server_app.models.dto.ResponseData;
import mii.mcc72.ams_server_app.repos.AssetRepo;
import mii.mcc72.ams_server_app.repos.EmployeeRepo;
import mii.mcc72.ams_server_app.repos.HistoryRepo;
import mii.mcc72.ams_server_app.repos.ReportRepo;
import mii.mcc72.ams_server_app.utils.AssetStatus;
import mii.mcc72.ams_server_app.utils.RentStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdminService {
//
    private AssetRepo assetRepo;
//
    private HistoryRepo historyRepo;
    private final EmployeeRepo employeeRepo;
    private final ReportRepo reportRepo;

    //
//    private ReportRepo reportRepo;
//
//    private AssetService assetService;
//
//    private EmployeeService employeeService;
//
//    public List<Asset> getPendingAdminAssets(){
//        return assetRepo.getPendingAdminAssets();
//    }
//
//
//    //------------------
//    public List<Report> getPenalty(int id) {
//    return reportRepo.getPenalty(id).stream().filter(report -> report.getPenalty() > 0).collect(Collectors.toList());
//    }
//
//    public List<Asset> getAllAssetsByStatus(AssetStatus status) {
//        System.out.println(status);
//        return assetRepo.findAll().stream().filter(asset -> asset.getApprovedStatus().equals(status)).collect(Collectors.toList());
//    }
//
//
    public List<Asset> getSubmission(int empId) {
        return assetRepo.findAll().stream().filter(asset -> !asset.getApprovedStatus().equals(AssetStatus.PENDING_ADMIN) && asset.getEmployee().getId() == empId).collect(Collectors.toList());
    }
    public List<Asset> getReqSubmission(int empId , int department) {
        return assetRepo.findAll().stream().filter(asset ->  asset.getEmployee().getId() != empId && asset.getEmployee().getDepartment().getId() == department).filter(asset -> asset.getApprovedStatus().equals(AssetStatus.PENDING_ADMIN)).collect(Collectors.toList());
    }
    public List<Asset> getListSubmission(int empId , int department) {
        return assetRepo.findAll().stream().filter(asset ->  asset.getEmployee().getId() != empId && asset.getEmployee().getDepartment().getId() == department).filter( asset -> asset.getApprovedStatus().equals(AssetStatus.DENIED) || asset.getApprovedStatus().equals(AssetStatus.PENDING_FINANCE) || asset.getApprovedStatus().equals(AssetStatus.APPROVED) ).collect(Collectors.toList());
    }

    public List<History> getReqRent() {
        return historyRepo.findAll().stream().filter(history -> history.getStatus().equals(RentStatus.PENDING)).collect(Collectors.toList());
    }
    public List<History> getRent() {
        return historyRepo.findAll().stream().filter(history -> history.getStatus().equals(RentStatus.APPROVED) || history.getStatus().equals(RentStatus.DENIED)).collect(Collectors.toList());
    }
    public List<Report> getReport() {
        return reportRepo.findAll();
    }
    public List<History> getReqReturn() {
        return historyRepo.findAll().stream().filter(history -> history.getStatus().equals(RentStatus.DONE)).collect(Collectors.toList());
    }
    public List<Employee> getAllEmployee(int id) {
        return employeeRepo.findAll().stream().filter(employee -> employee.getId() != id).collect(Collectors.toList());
    }
//
//    public List<History> getAllHistoryByEmpId(int empId) {
//        return historyRepo.findAll().stream().filter(history -> history.getEmployee().getId() == empId).collect(Collectors.toList());
//    }
//
//    public List<Object> getListPenalty(int id) {
//        return reportRepo.getListPenalty(id);
//    }
//    public Object getById(int id) {
//        return reportRepo.getById(id);
//    }
//
//    public ResponseEntity<ResponseData<History>> createRentRequest(@Valid HistoryDTO historyDTO,int id, Errors errors) {
//        ResponseData<History> responseData = new ResponseData<>();
//        if (errors.hasErrors()) {
//            for (ObjectError error : errors.getAllErrors()) {
//                responseData.getMessages().add(error.getDefaultMessage());
//            }
//            responseData.setStatus(false);
//            responseData.setPayload(null);
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
//        }
//        responseData.setStatus(true);
//        History history = new History();
//        Date startDate;
//        Date endDate;
//        try {
//            startDate = new SimpleDateFormat("yyyy-MM-dd").parse(historyDTO.getStart());
//            System.out.println(startDate);
//            endDate = new SimpleDateFormat("yyyy-MM-dd").parse(historyDTO.getEnd());
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//        history.setNote(historyDTO.getNote());
//        history.setStart(startDate);
//        history.setEnd(endDate);
//        history.setStatus(RentStatus.PENDING);
//        history.setAsset(assetService.getById(historyDTO.getAssetId()));
//        history.setEmployee(employeeService.getById(id));
//        Report report = new Report();
//        report.setDateAccident(null);
//        report.setDescIncident("");
//        report.setDescIncident("");
//        report.setPenalty(0L);
//        report.setHistory(history);
//        history.setReport(report);
//        responseData.setPayload(historyRepo.save(history));
//        return ResponseEntity.ok(responseData);
//    }


}
