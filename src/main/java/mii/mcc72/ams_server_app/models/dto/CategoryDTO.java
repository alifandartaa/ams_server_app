package mii.mcc72.ams_server_app.models.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CategoryDTO {
    @NotEmpty(message = "Name is required")
    private String name;


}
