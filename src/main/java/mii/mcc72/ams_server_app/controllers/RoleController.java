package mii.mcc72.ams_server_app.controllers;

import lombok.AllArgsConstructor;
import mii.mcc72.ams_server_app.models.Privilege;
import mii.mcc72.ams_server_app.models.Role;
import mii.mcc72.ams_server_app.models.dto.ResponseData;
import mii.mcc72.ams_server_app.models.dto.RoleDTO;
import mii.mcc72.ams_server_app.services.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/role")
@PreAuthorize("hasRole('ADMIN')")
@AllArgsConstructor
public class RoleController {
    private RoleService roleService;

    @GetMapping
    public List<Role> getAll(){
        return roleService.getAll();
    }

    @GetMapping("/{id}")
    public Role getById(@PathVariable("id") int id){
        return roleService.getById(id);
    }

    @PostMapping
    public ResponseEntity<ResponseData<Role>> create(@RequestBody RoleDTO roleDTO, Errors errors){
        return roleService.create(roleDTO, errors);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseData<Role>> update(@PathVariable int id, @RequestBody RoleDTO roleDTO, Errors errors){
        return roleService.update(roleDTO, id, errors);
    }

    @DeleteMapping("/{id}")
    public Role delete(@PathVariable int id){
        return roleService.delete(id);
    }
}
