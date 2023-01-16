package mii.mcc72.ams_server_app.controllers;

import lombok.AllArgsConstructor;
import mii.mcc72.ams_server_app.models.Privilege;
import mii.mcc72.ams_server_app.models.dto.PrivilegeDTO;
import mii.mcc72.ams_server_app.models.dto.ResponseData;
import mii.mcc72.ams_server_app.services.PrivilegeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/privilege")
@PreAuthorize("hasRole('ADMIN')")
@AllArgsConstructor
public class PrivilegeController {
    private PrivilegeService privilegeService;

    @GetMapping
    public List<Privilege> getAll() {
        return privilegeService.getAll();
    }

    @GetMapping("/{id}")
    public Privilege getById(@PathVariable("id") int id) {
        return privilegeService.getById(id);
    }

    @PostMapping
    public ResponseEntity<ResponseData<Privilege>> create(@RequestBody PrivilegeDTO privilegeDTO, Errors errors) {
        return privilegeService.create(privilegeDTO, errors);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseData<Privilege>> update(@PathVariable int id, @RequestBody PrivilegeDTO privilegeDTO, Errors errors) {
        return privilegeService.update(privilegeDTO, id, errors);
    }

    @DeleteMapping("/{id}")
    public Privilege delete(@PathVariable int id) {
        return privilegeService.delete(id);
    }
}
