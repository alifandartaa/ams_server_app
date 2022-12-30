package mii.mcc72.ams_server_app.services;

import lombok.AllArgsConstructor;
import mii.mcc72.ams_server_app.models.Asset;
import mii.mcc72.ams_server_app.models.History;
import mii.mcc72.ams_server_app.models.Report;
import mii.mcc72.ams_server_app.repos.AssetRepo;
import mii.mcc72.ams_server_app.repos.HistoryRepo;
import mii.mcc72.ams_server_app.repos.ReportRepo;
import mii.mcc72.ams_server_app.utils.AssetStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmpService {

    private AssetRepo assetRepo;

    private HistoryRepo historyRepo;

    private ReportRepo reportRepo;


    public List<Report> getPenalty(int id) {
    return reportRepo.getPenalty(id).stream().filter(report -> report.getPenalty() > 0).collect(Collectors.toList());
    }



    public List<Asset> getAllAssetsByStatus(AssetStatus status) {
        System.out.println(status);
        return assetRepo.findAll().stream().filter(asset -> asset.getApprovedStatus().equals(status)).collect(Collectors.toList());
    }

    public List<Asset> getAllAssetsByStatusAndEmpId(String status, int empId) {
        return assetRepo.findAll().stream().filter(asset -> asset.getApprovedStatus().equals(status) && asset.getEmployee().getId() == empId).collect(Collectors.toList());
    }

    public List<History> getAllHistoryByEmpId(int empId) {
        return historyRepo.findAll().stream().filter(history -> history.getEmployee().getId() == empId).collect(Collectors.toList());
    }


}
