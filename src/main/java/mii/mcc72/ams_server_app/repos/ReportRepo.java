package mii.mcc72.ams_server_app.repos;

import mii.mcc72.ams_server_app.models.History;
import mii.mcc72.ams_server_app.models.Report;
import mii.mcc72.ams_server_app.models.dto.PenaltyDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public interface ReportRepo extends JpaRepository<Report, Integer> {

    @Transactional
    @Modifying
    @Query("SELECT r FROM Report r WHERE r.id IN (SELECT h.id FROM History h WHERE h.employee.id = ?1)")
    List<Report> getPenalty(int id);

    @Transactional
    @Modifying
    @Query(value = "SELECT asset.name, asset.image_asset, report.date_accident , report.desc_damage , report.desc_incident , report.penalty , report.id FROM report JOIN history ON report.id = history.id JOIN asset ON history.asset_id = asset.id WHERE history.employee_id = ?1 AND report.penalty > 0 ORDER BY report.id", nativeQuery = true)
    List<Object> getListPenalty(int id);
    @Transactional
    @Modifying
    @Query(value = "SELECT asset.name, asset.image_asset, asset.description , history.date_start , history.date_end, report.date_accident , report.desc_damage , report.desc_incident , report.penalty , report.id FROM report JOIN history ON report.id = history.id JOIN asset ON history.asset_id = asset.id WHERE report.id = ?1 AND report.penalty > 0 ORDER BY report.id", nativeQuery = true)
    List<Object> getById(int id);

    @Transactional
    @Modifying
    @Query(value = "SELECT employee.first_name , employee.last_name , asset.name , report.desc_damage , report.desc_incident , report.penalty , history.date_start , history.date_end , report.date_accident , report.id FROM report JOIN history ON report.id = history.id JOIN asset ON history.asset_id = asset.id JOIN employee ON history.employee_id = employee.id WHERE report.admin_id = ?1", nativeQuery = true)
    List<Object> getReport(int id);
//    SELECT employee.first_name , employee.last_name , asset.name , report.desc_damage , report.desc_incident , report.penalty , history.date_start , history.date_end , report.date_accident , report.id FROM report JOIN history ON report.id = history.id JOIN asset ON history.asset_id = asset.id JOIN employee ON history.employee_id = employee.id WHERE report.admin_id = 1;

}
