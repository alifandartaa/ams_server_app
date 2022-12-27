package mii.mcc72.ams_server_app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mii.mcc72.ams_server_app.utils.AssetStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int qty;

    @Column(nullable = false, length = 50)
    private String name;

    @Column( nullable = false ,columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false , name = "image_asset")
    private String image;

    @Column(nullable = false ,  name = "created_date")
    private Date date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false , name = "approved_status")
    private AssetStatus approvedStatus;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Employee employee;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Category category;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "asset")
    private List<History> histories;
}
