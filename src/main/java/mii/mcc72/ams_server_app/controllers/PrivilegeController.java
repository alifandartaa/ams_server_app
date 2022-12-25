package mii.mcc72.ams_server_app.controllers;

import lombok.AllArgsConstructor;
import mii.mcc72.ams_server_app.models.Privilege;
import mii.mcc72.ams_server_app.models.Role;
import mii.mcc72.ams_server_app.models.dto.ResponseData;
import mii.mcc72.ams_server_app.models.dto.PrivilegeDTO;
import mii.mcc72.ams_server_app.services.PrivilegeService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/privilege")
@AllArgsConstructor
public class PrivilegeController {
    private PrivilegeService privilegeService;

    @GetMapping
    public List<Privilege> getAll(){
        return privilegeService.getAll();
    }

    @GetMapping("/{id}")
    public Privilege getById(@PathVariable("id") int id){
        return privilegeService.getById(id);
    }

    @PostMapping
    public ResponseEntity<ResponseData<Privilege>> create(@RequestBody PrivilegeDTO roleDTO, Errors errors){
        return privilegeService.create(roleDTO, errors);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseData<Privilege>> update(@PathVariable int id, @RequestBody PrivilegeDTO roleDTO, Errors errors){
        return privilegeService.update(roleDTO, id, errors);
    }

    @DeleteMapping("/{id}")
    public Privilege delete(@PathVariable int id){
        return privilegeService.delete(id);
    }
}
