package mii.mcc72.ams_server_app.services;

import lombok.AllArgsConstructor;
import mii.mcc72.ams_server_app.models.User;
import mii.mcc72.ams_server_app.models.dto.RegistrationDTO;
import mii.mcc72.ams_server_app.models.dto.ResponseData;
import mii.mcc72.ams_server_app.repos.EmployeeRepository;
import mii.mcc72.ams_server_app.repos.UserRepository;
import mii.mcc72.ams_server_app.utils.ExcelHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class ExcelService {
    private EmployeeRepository employeeRepository;
    private UserRepository userRepository;
    private RegistrationService registrationService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void registerFromExcel(MultipartFile file) {
        try {
            List<User> users = ExcelHelper.excelToUsers(file.getInputStream());
            users.forEach(user -> {
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            });
            System.out.println("Result from Excel : " + users);
            userRepository.saveAll(users);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    public ResponseEntity<ResponseData<List<RegistrationDTO>>> registerAllFinance(List<RegistrationDTO> registrationDTOS) {
        ResponseData<List<RegistrationDTO>> responseData = new ResponseData<>();
        String message = "";
        message = "Uploaded the file successfully";
        responseData.setStatus(true);
        responseData.setMessages(Collections.singletonList(message));
        responseData.setPayload(registrationDTOS);
        System.out.println("Output RegistrationDTOs BE : " + registrationDTOS);
        for (RegistrationDTO registrationDTO : registrationDTOS) {
            registrationService.registerAsFinance(registrationDTO);
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }
}
