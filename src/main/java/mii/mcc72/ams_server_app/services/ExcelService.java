package mii.mcc72.ams_server_app.services;

import lombok.AllArgsConstructor;
import mii.mcc72.ams_server_app.models.Employee;
import mii.mcc72.ams_server_app.models.User;
import mii.mcc72.ams_server_app.repos.EmployeeRepository;
import mii.mcc72.ams_server_app.repos.UserRepository;
import mii.mcc72.ams_server_app.utils.ExcelHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class ExcelService {
    private EmployeeRepository employeeRepository;
    private UserRepository userRepository;
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

//    public List<Employee> getAllEmployees() {
//        return employeeRepository.findAll();
//    }
}
