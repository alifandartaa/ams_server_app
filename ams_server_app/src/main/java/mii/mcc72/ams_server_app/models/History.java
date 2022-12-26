package mii.mcc72.ams_server_app.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mii.mcc72.ams_server_app.util.RentStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false , columnDefinition = "TEXT")
    private String note;

    @Column(nullable = false , name = "date_start")
    private Date start;

    @Column(nullable = false , name = "date_end")
    private Date end;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false , name = "rent_status")
    private RentStatus status;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Employee employee;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Asset asset;

    @OneToOne(mappedBy = "history", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Report report;

}
