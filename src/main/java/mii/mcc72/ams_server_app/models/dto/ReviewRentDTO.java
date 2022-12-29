package mii.mcc72.ams_server_app.models.dto;

import lombok.Data;
import mii.mcc72.ams_server_app.utils.RentStatus;

@Data
public class ReviewRentDTO {
    private RentStatus rentStatus;
}
