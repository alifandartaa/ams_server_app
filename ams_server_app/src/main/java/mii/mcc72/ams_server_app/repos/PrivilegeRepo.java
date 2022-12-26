package mii.mcc72.ams_server_app.repos;

import mii.mcc72.ams_server_app.models.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepo extends JpaRepository<Privilege, Integer> {
}
