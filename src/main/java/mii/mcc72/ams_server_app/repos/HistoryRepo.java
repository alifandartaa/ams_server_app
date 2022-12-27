package mii.mcc72.ams_server_app.repos;

import mii.mcc72.ams_server_app.models.History;
import mii.mcc72.ams_server_app.utils.RentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface HistoryRepo extends JpaRepository<History, Integer> {
    @Transactional
    @Modifying
    @Query("UPDATE History h " + "SET h.status = ?2 " + "WHERE h.id = ?1")
    void reviewRentRequest(int id, RentStatus rentStatus);
}
