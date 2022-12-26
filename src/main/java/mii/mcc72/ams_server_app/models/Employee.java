package mii.mcc72.ams_server_app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "phone_number", nullable = false, length = 50)
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Department department;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    private List<Report> reports;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    private List<Asset> assets;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    private List<History> histories;
}
