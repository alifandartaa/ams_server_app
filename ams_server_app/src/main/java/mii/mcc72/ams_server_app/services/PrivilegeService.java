package mii.mcc72.ams_server_app.services;

import lombok.AllArgsConstructor;
import mii.mcc72.ams_server_app.models.Privilege;
import mii.mcc72.ams_server_app.models.dto.ResponseData;
import mii.mcc72.ams_server_app.models.dto.PrivilegeDTO;
import mii.mcc72.ams_server_app.repos.PrivilegeRepo;
import mii.mcc72.ams_server_app.repos.PrivilegeRepo;
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
public class PrivilegeService {
    private PrivilegeRepo privilegeRepo;

    public List<Privilege> getAll(){
        return privilegeRepo.findAll();
    }

    public Privilege getById(int id){
        return privilegeRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Privilege ID %s Not Found !!", id))
        );
    }

    public ResponseEntity<ResponseData<Privilege>> create(@Valid PrivilegeDTO privilegeDTO, Errors errors){
        ResponseData<Privilege> responseData = new ResponseData<>();
        if(errors.hasErrors()){
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.setStatus(true);
        Privilege privilege = new Privilege();
        privilege.setName(privilegeDTO.getName());
        responseData.setPayload(privilegeRepo.save(privilege));
        return ResponseEntity.ok(responseData);
    }

    public ResponseEntity<ResponseData<Privilege>> update(@Valid PrivilegeDTO privilegeDTO, int id, Errors errors){
        getById(id);
        ResponseData<Privilege> responseData = new ResponseData<>();
        if(errors.hasErrors()){
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.setStatus(true);
        Privilege privilege = new Privilege();
        privilege.setId(id);
        privilege.setName(privilegeDTO.getName());
        responseData.setPayload(privilegeRepo.save(privilege));
        return ResponseEntity.ok(responseData);
    }

    public Privilege delete(int id){
        Privilege privilege = getById(id);
        privilegeRepo.deleteById(id);
        return privilege;
    }
}
