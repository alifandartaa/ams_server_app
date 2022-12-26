package mii.mcc72.ams_server_app.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

@Data
public class PrivilegeDTO {
    @NotEmpty(message = "Name privilege is required")
    private String name;
}
