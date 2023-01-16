package mii.mcc72.ams_server_app.controllers;

import lombok.AllArgsConstructor;
import mii.mcc72.ams_server_app.models.dto.LoginDTO;
import mii.mcc72.ams_server_app.services.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private LoginService loginService;

    @PostMapping("/login")
    public Object login(@RequestBody LoginDTO loginDTO) {
        return loginService.login(loginDTO);
    }

}
