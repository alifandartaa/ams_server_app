package mii.mcc72.ams_server_app.models.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class DepartmentDTO {
    @NotEmpty(message = "Name department is required")
    private String name;
    @NotEmpty(message = "Balance is required")
    private int balance;
}
