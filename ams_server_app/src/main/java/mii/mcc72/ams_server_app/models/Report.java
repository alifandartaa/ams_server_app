package mii.mcc72.ams_server_app.models;

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

    @Column(name = "date_accident", nullable = false)
    private Date dateAccident;

    @Column(name = "desc_damage", columnDefinition = "TEXT", nullable = false)
    private String descDamage;

    @Column(name = "desc_incident", columnDefinition = "TEXT", nullable = false)
    private String descIncident;

    @Column(nullable = false)
    private Long penalty;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "admin_id", referencedColumnName = "id")
    private Employee employee;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private History history;
}
