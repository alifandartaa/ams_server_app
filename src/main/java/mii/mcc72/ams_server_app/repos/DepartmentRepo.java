package mii.mcc72.ams_server_app.repos;

import mii.mcc72.ams_server_app.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, Integer> {

}
