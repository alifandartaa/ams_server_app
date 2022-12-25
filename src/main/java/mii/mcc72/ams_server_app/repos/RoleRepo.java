package mii.mcc72.ams_server_app.repos;

import mii.mcc72.ams_server_app.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Integer> {
}
