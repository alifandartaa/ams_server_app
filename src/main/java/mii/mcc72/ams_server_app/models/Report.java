package mii.mcc72.ams_server_app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    @Id
    private int id;

    @Column(name = "date_accident")
    private Date dateAccident;

    @Column(name = "desc_damage", columnDefinition = "TEXT")
    private String descDamage;

    @Column(name = "desc_incident", columnDefinition = "TEXT")
    private String descIncident;

    private Long penalty;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "admin_id", referencedColumnName = "id")
    private Employee employee;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    @JsonIgnore
    private History history;
}
