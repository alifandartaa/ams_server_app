package mii.mcc72.ams_server_app.repos;

import java.util.Optional;
import mii.mcc72.ams_server_app.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
