package mii.mcc72.ams_server_app.models.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class EmployeeDTO {
    @NotEmpty(message = "First Name employee is required")
    private String firstName;
    @NotEmpty(message = "Last Name employee is required")
    private String lastName;
    @NotEmpty(message = "Email is required")
    private String email;
    @NotEmpty(message = "Phone Number is required")
    private String phoneNumber;
    @NotEmpty(message = "Department is required")
    private int departmentId;
}
