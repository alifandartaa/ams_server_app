package mii.mcc72.ams_server_app.repos;

import mii.mcc72.ams_server_app.models.Report;
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
//    @Query("UPDATE ConfirmationToken c " + "SET c.confirmedAt = ?2 " + "WHERE c.token = ?1")
//    @Query(value = "SELECT SUM(penalty) From report WHERE id IN (SELECT id FROM history WHERE employee_id = ?1)", nativeQuery = true)
    List<Report> getPenalty(int id);

}
