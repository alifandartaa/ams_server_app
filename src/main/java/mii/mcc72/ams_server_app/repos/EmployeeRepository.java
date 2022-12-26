/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mii.mcc72.ams_server_app.repos;

import java.util.Optional;
import mii.mcc72.ams_server_app.models.Employee;
import mii.mcc72.ams_server_app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author bintang mada
 */
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
    Optional<User> findById(String id);
    
    Optional<User> findByEmail(String email);
    
    
    @Transactional
    @Modifying
    @Query("UPDATE User a " + "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableUser(String email);
}
