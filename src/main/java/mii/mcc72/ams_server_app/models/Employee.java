/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mii.mcc72.ams_server_app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.sql.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author bintang mada
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id //Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 6)
    private int id;

    @Column(length = 50)
    private String first_name;
    
    @Column(length = 55)
    private String last_name;
    
    @Column(length = 50, unique = true)
    private String email;
    
    @Column(length = 50)
    private String phone_number;

//   //FK
//    @ManyToOne
//    @JoinColumn(name = "department")
//    private Department department;
     
    //FK
    @JsonIgnore
    @OneToOne(mappedBy = "employee")
    @PrimaryKeyJoinColumn
    private User user;
       
//    @JsonIgnore
//    @OneToMany(mappedBy = "employee_id")
//    private List<History> histories;
       
}
