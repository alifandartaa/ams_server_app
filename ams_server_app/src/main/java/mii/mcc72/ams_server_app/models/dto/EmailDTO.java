package mii.mcc72.ams_server_app.models.dto;

import lombok.Data;

@Data
public class EmailDTO {
    private String to;
    private String subject;
    private String body;
}
