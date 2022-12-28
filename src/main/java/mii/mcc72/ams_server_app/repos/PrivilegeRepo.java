package mii.mcc72.ams_server_app.repos;

import mii.mcc72.ams_server_app.models.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface PrivilegeRepo extends JpaRepository<Privilege, Integer> {
    @Modifying
    @Query(value = "INSERT INTO role_privilege (role_id, privilege_id) values (:role_id, :privilege_id)", nativeQuery = true)
    void insertRolePrivilege(@Param("role_id") Integer roleId, @Param("privilege_id") Integer privilegeId);
}
