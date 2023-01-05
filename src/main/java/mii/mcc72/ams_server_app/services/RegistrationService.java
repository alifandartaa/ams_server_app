/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mii.mcc72.ams_server_app.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import mii.mcc72.ams_server_app.models.Employee;
import mii.mcc72.ams_server_app.models.Role;
import mii.mcc72.ams_server_app.models.User;
import mii.mcc72.ams_server_app.models.ConfirmationToken;
import mii.mcc72.ams_server_app.models.dto.RegistrationDTO;
import mii.mcc72.ams_server_app.repos.DepartmentRepo;
import mii.mcc72.ams_server_app.utils.EmailSender;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @author bintang mada, Alif Andarta
 */
@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;

    private final RoleService roleService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;

    private final DepartmentRepo departmentRepo;

    private final TemplateEngine templateEngine;

    public String registerAsEmployee(RegistrationDTO registrationDTO) {
        boolean isValidEmail = emailValidator.
                test(registrationDTO.getEmail());

        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }
        Employee employee = new Employee();
        employee.setFirstName(registrationDTO.getFirstName());
        employee.setLastName(registrationDTO.getLastName());
        employee.setPhoneNumber(registrationDTO.getPhoneNumber());
        //should be input department
        employee.setDepartment(departmentRepo.findById(registrationDTO.getDepartmentId()).get());
        User user = new User();
        user.setUsername(registrationDTO.getUsername());
        user.setPassword(registrationDTO.getPassword());
        user.setEmail(registrationDTO.getEmail());
        user.setEmployee(employee);
        List<Role> role = new ArrayList<>();
        role.add(roleService.getById(1));
        user.setRoles(role);
        String token = userService.signUpUser(user);

        String link = "http://localhost:8088/api/registration/confirm?token=" + token;
        Context ctx = new Context();
        ctx.setVariable("first_name", "Hi " + registrationDTO.getFirstName());
        ctx.setVariable("username", "Username : " + registrationDTO.getUsername());
        ctx.setVariable("password", "Password : " + registrationDTO.getPassword());
        ctx.setVariable("confirmation_link", link);
        String htmlContent = templateEngine.process("template_registration", ctx);
        String subject = "Activate Your Employee Account";
        emailSender.send(
                registrationDTO.getEmail(), subject,
                htmlContent);

        return token;
    }

//    public String registerAsAdmin(RegistrationDTO registrationDTO) {
//        boolean isValidEmail = emailValidator.
//                test(registrationDTO.getEmail());
//
//        if (!isValidEmail) {
//            throw new IllegalStateException("email not valid");
//        }
//        Employee employee = new Employee();
//        employee.setFirstName(registrationDTO.getFirstName());
//        employee.setLastName(registrationDTO.getLastName());
//        employee.setPhoneNumber(registrationDTO.getPhoneNumber());
//        //should be input department
//        employee.setDepartment(departmentRepo.findById(3).get());
//        User user = new User();
//        user.setUsername(registrationDTO.getUsername());
//        user.setPassword(registrationDTO.getPassword());
//        user.setEmail(registrationDTO.getEmail());
//        user.setEmployee(employee);
//        List<Role> role = new ArrayList<>();
//        role.add(roleService.getById(2));
//        user.setRoles(role);
//        String token = userService.signUpUser(user);
//
//        String link = "http://localhost:8088/api/registration/confirm?token=" + token;
//        Context ctx = new Context();
//        ctx.setVariable("first_name", "Hi " + registrationDTO.getFirstName());
//        ctx.setVariable("username", "Username : " + registrationDTO.getUsername());
//        ctx.setVariable("password", "Password : " + registrationDTO.getPassword());
//        ctx.setVariable("confirmation_link", link);
//        String htmlContent = templateEngine.process("template_registration", ctx);
//        String subject = "Activate Your Admin Account";
//        emailSender.send(
//                registrationDTO.getEmail(), subject ,
//                htmlContent);
//
//        return token;
//    }

    public String registerAsFinance(RegistrationDTO registrationDTO) {
        boolean isValidEmail = emailValidator.
                test(registrationDTO.getEmail());

        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }
        Employee employee = new Employee();
        employee.setFirstName(registrationDTO.getFirstName());
        employee.setLastName(registrationDTO.getLastName());
        employee.setPhoneNumber(registrationDTO.getPhoneNumber());
        User user = new User();
        user.setUsername(registrationDTO.getUsername());
        user.setPassword(registrationDTO.getPassword());
        user.setEmail(registrationDTO.getEmail());
        user.setEmployee(employee);
        List<Role> role = new ArrayList<>();
        role.add(roleService.getById(3));
        user.setRoles(role);
        String token = userService.signUpUser(user);

        String link = "http://localhost:8088/api/registration/confirm?token=" + token;
        Context ctx = new Context();
        ctx.setVariable("first_name", "Hi " + registrationDTO.getFirstName());
        ctx.setVariable("username", "Username : " + registrationDTO.getUsername());
        ctx.setVariable("password", "Password : " + registrationDTO.getPassword());
        ctx.setVariable("confirmation_link", link);
        String htmlContent = templateEngine.process("template_registration", ctx);
        String subject = "Activate Your Finance Account";
        emailSender.send(
                registrationDTO.getEmail(), subject ,
                htmlContent);
        return token;
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(()
                        -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Token Not Found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Email Already Confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Token Expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        userService.enableUser(
                confirmationToken.getUser().getUsername());
        Context ctx = new Context();
        return templateEngine.process("account_activated", ctx);
    }

}
