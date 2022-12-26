package mii.mcc72.ams_server_app.models.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ReportDTO {
    @NotEmpty(message = "Date accident is required")
    private String dateAccident;
    @NotEmpty(message = "Description Damage is required")
    private String descDamage;
    @NotEmpty(message = "Description Incident is required")
    private String descIncident;
    @NotEmpty(message = "Penalty cost is required")
    private Long penalty;
    @NotEmpty(message = "Admin ID is required")
    private int adminId;
}
