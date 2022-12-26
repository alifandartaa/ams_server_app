package mii.mcc72.ams_server_app.models.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AssetDTO {
    @NotEmpty(message = "Quantity is required")
    private int qty;

    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Description is required")
    private String description;

    @NotEmpty(message = "Price is required")
    private int price;

    @NotEmpty(message = "Image is required")
    private String image;

    @NotEmpty(message = "Date is required")
    private String date;

    @NotEmpty(message = "Status is required")
    private String status;

    @NotEmpty(message = "Employee is required")
    private int employeeId;

    @NotEmpty(message = "Category is required")
    private int categoryId;
}
