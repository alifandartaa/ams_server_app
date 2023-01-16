package mii.mcc72.ams_server_app.services;

import lombok.AllArgsConstructor;
import mii.mcc72.ams_server_app.models.Asset;
import mii.mcc72.ams_server_app.models.History;
import mii.mcc72.ams_server_app.models.User;
import mii.mcc72.ams_server_app.repos.*;
import mii.mcc72.ams_server_app.utils.AssetStatus;
import mii.mcc72.ams_server_app.utils.RentStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdminService {
    private final EmployeeRepo employeeRepo;
    private final ReportRepo reportRepo;
    private final UserService userService;
    private final UserRepository userRepository;
    private AssetRepo assetRepo;
    private HistoryRepo historyRepo;

    public List<Asset> getSubmission(int empId) {
        return assetRepo.findAll().stream().filter(asset -> !asset.getApprovedStatus().equals(AssetStatus.PENDING_ADMIN) && asset.getEmployee().getId() == empId).collect(Collectors.toList());
    }

    public List<Asset> getReqSubmission(int empId, int department) {
        return assetRepo.findAll().stream().filter(asset -> asset.getEmployee().getId() != empId && asset.getEmployee().getDepartment().getId() == department).filter(asset -> asset.getApprovedStatus().equals(AssetStatus.PENDING_ADMIN)).collect(Collectors.toList());
    }

    public List<Asset> getListSubmission(int empId, int department) {
        return assetRepo.findAll().stream().filter(asset -> asset.getEmployee().getId() != empId && asset.getEmployee().getDepartment().getId() == department).filter(asset -> asset.getApprovedStatus().equals(AssetStatus.DENIED) || asset.getApprovedStatus().equals(AssetStatus.PENDING_FINANCE) || asset.getApprovedStatus().equals(AssetStatus.APPROVED)).collect(Collectors.toList());
    }

    public Asset getQty(int id) {
        return assetRepo.findById(historyRepo.findById(id).get().getAsset().getId()).get();
    }

    public List<History> getReqRent(int department) {
        return historyRepo.findAll().stream().filter(history -> history.getStatus().equals(RentStatus.PENDING) && history.getEmployee().getDepartment().getId() == department).collect(Collectors.toList());
    }

    public List<History> getRent(int department) {
        return historyRepo.findAll().stream().filter(history -> history.getEmployee().getDepartment().getId() == department).filter(history -> history.getStatus().equals(RentStatus.APPROVED) || history.getStatus().equals(RentStatus.DENIED)).collect(Collectors.toList());
    }

    public List<Object> getReport(int id) {
        return reportRepo.getReport(id);
    }

    public List<History> getReqReturn(int department) {
        return historyRepo.findAll().stream().filter(history -> history.getStatus().equals(RentStatus.DONE) && history.getEmployee().getDepartment().getId() == department).collect(Collectors.toList());
    }

    public List<User> getAllEmployee(int id) {
        return userRepository.findAll().stream().filter(employee -> employee.getId() != id).collect(Collectors.toList());
    }

}
