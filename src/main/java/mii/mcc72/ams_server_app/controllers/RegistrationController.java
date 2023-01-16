/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mii.mcc72.ams_server_app.controllers;

import lombok.AllArgsConstructor;
import mii.mcc72.ams_server_app.models.dto.RegistrationDTO;
import mii.mcc72.ams_server_app.services.RegistrationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author bintang mada, Alif Andarta
 */
@RestController
@RequestMapping(path = "registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PreAuthorize("hasAuthority('CREATE_ADMIN')")
    @PostMapping
    public String registerAsEmployee(@RequestBody RegistrationDTO request) {
        return registrationService.registerAsEmployee(request);
    }

    @PreAuthorize("hasAuthority('CREATE_ADMIN')")
    @PostMapping("finance")
    public String registerAsFinance(@RequestBody RegistrationDTO request) {
        return registrationService.registerAsFinance(request);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

}
