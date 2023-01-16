/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mii.mcc72.ams_server_app.services;

import lombok.AllArgsConstructor;
import mii.mcc72.ams_server_app.models.ConfirmationToken;
import mii.mcc72.ams_server_app.models.Employee;
import mii.mcc72.ams_server_app.models.Role;
import mii.mcc72.ams_server_app.models.User;
import mii.mcc72.ams_server_app.models.dto.RegistrationDTO;
import mii.mcc72.ams_server_app.repos.DepartmentRepo;
import mii.mcc72.ams_server_app.repos.UserRepository;
import mii.mcc72.ams_server_app.utils.EmailSender;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private final UserRepository userRepository;

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
        try {
            emailSender.send(
                    registrationDTO.getEmail(), subject,
                    htmlContent);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return token;
    }

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
        try {
            emailSender.send(
                    registrationDTO.getEmail(), subject,
                    htmlContent);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
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
        userRepository.enableUser(
                confirmationToken.getUser().getId());
        Context ctx = new Context();
        return templateEngine.process("account_activated", ctx);
    }


}
