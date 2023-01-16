/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mii.mcc72.ams_server_app.repos;

import mii.mcc72.ams_server_app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author bintang mada
 */
@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);


    @Transactional
    @Modifying
    @Query("UPDATE User a " + "SET a.isEnabled = TRUE WHERE a.id = ?1")
    int enableUser(int id);

    @Transactional
    @Modifying
    @Query("UPDATE User a " + "SET a.isEnabled = FALSE WHERE a.id = ?1")
    int disableUser(int id);

}
