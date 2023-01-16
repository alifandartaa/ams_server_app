package mii.mcc72.ams_server_app.services;

import lombok.AllArgsConstructor;
import mii.mcc72.ams_server_app.models.Role;
import mii.mcc72.ams_server_app.models.dto.ResponseData;
import mii.mcc72.ams_server_app.models.dto.RoleDTO;
import mii.mcc72.ams_server_app.repos.RoleRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@Service
@AllArgsConstructor
public class RoleService {
    private RoleRepo roleRepo;

    public List<Role> getAll() {
        return roleRepo.findAll();
    }

    public Role getById(int id) {
        return roleRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Role ID %s Not Found !!", id))
        );
    }

    public ResponseEntity<ResponseData<Role>> create(@Valid RoleDTO roleDTO, Errors errors) {
        ResponseData<Role> responseData = new ResponseData<>();
        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.setStatus(true);
        Role role = new Role();
        role.setName(roleDTO.getName());
        responseData.setPayload(roleRepo.save(role));
        return ResponseEntity.ok(responseData);
    }

    public ResponseEntity<ResponseData<Role>> update(@Valid RoleDTO roleDTO, int id, Errors errors) {
        getById(id);
        ResponseData<Role> responseData = new ResponseData<>();
        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.setStatus(true);
        Role role = new Role();
        role.setId(id);
        role.setName(roleDTO.getName());
        responseData.setPayload(roleRepo.save(role));
        return ResponseEntity.ok(responseData);
    }

    public Role delete(int id) {
        Role role = getById(id);
        roleRepo.deleteById(id);
        return role;
    }
}
