package mii.mcc72.ams_server_app.repos;

import mii.mcc72.ams_server_app.models.Department;
import mii.mcc72.ams_server_app.utils.AssetStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface DepartmentRepo extends JpaRepository<Department, Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE Department d " + " SET d.balance = balance - ?1 " + " WHERE d.id = ?2")
    void calculateSubAssetWithBalance(int costSubAsset, int id);
}
