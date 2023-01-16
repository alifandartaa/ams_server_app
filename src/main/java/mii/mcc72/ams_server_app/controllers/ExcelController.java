package mii.mcc72.ams_server_app.controllers;

import lombok.AllArgsConstructor;
import mii.mcc72.ams_server_app.models.dto.RegistrationDTO;
import mii.mcc72.ams_server_app.models.dto.ResponseData;
import mii.mcc72.ams_server_app.services.ExcelService;
import mii.mcc72.ams_server_app.utils.ExcelHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("excel")
@AllArgsConstructor
public class ExcelController {
    private ExcelService excelService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseData> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        ResponseData<Object> responseData = new ResponseData<>();

        if (ExcelHelper.hasExcelFormat(file)) {
            excelService.registerFromExcel(file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            responseData.setStatus(true);
            responseData.setMessages(Collections.singletonList(message));
            responseData.setPayload(file.getOriginalFilename());
            return ResponseEntity.status(HttpStatus.OK).body(responseData);
        }

        message = "Please upload an excel file!";
        responseData.setStatus(false);
        responseData.setMessages(Collections.singletonList(message));
        responseData.setPayload(file.getOriginalFilename());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
    }

    @PostMapping("/regis-finance")
    public ResponseEntity<ResponseData<List<RegistrationDTO>>> registerFinanceFromExcel(@RequestBody List<RegistrationDTO> registrationDTOS) {
        return excelService.registerAllFinance(registrationDTOS);
    }
}
