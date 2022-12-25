package mii.mcc72.ams_server_app.repos;

import mii.mcc72.ams_server_app.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {
}
