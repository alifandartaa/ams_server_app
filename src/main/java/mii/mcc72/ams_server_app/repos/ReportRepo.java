package mii.mcc72.ams_server_app.repos;

import mii.mcc72.ams_server_app.models.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
@Transactional
public interface ReportRepo extends JpaRepository<Report, Integer> {

}
