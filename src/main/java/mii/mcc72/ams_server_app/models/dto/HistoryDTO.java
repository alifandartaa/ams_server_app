package mii.mcc72.ams_server_app.models.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class HistoryDTO {

    @NotEmpty(message = "Note is required")
    private String note;

    @NotEmpty(message = "Start Date is required")
    private String start;

    @NotEmpty(message = "End Date is required")
    private String end;

    @NotEmpty(message = "Status is required")
    private String status;

    @NotEmpty(message = "Employee is required")
    private int employeeId;

    @NotEmpty(message = "Asset is required")
    private int assetId;
}
