package mii.mcc72.ams_server_app.services;

import lombok.AllArgsConstructor;
import mii.mcc72.ams_server_app.models.Department;
import mii.mcc72.ams_server_app.models.dto.DepartmentDTO;
import mii.mcc72.ams_server_app.models.dto.ResponseData;
import mii.mcc72.ams_server_app.repos.DepartmentRepo;
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
public class DepartmentService {
    private DepartmentRepo departmentRepo;

    public List<Department> getAll(){
        return departmentRepo.findAll();
    }

    public Department getById(int id){
        return departmentRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Department ID %s Not Found !!", id))
        );
    }

    public ResponseEntity<ResponseData<Department>> create(@Valid DepartmentDTO departmentDTO, Errors errors){
        ResponseData<Department> responseData = new ResponseData<>();
        if(errors.hasErrors()){
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.setStatus(true);
        Department department = new Department();
        department.setName(departmentDTO.getName());
        department.setBalance(departmentDTO.getBalance());
        responseData.setPayload(departmentRepo.save(department));
        return ResponseEntity.ok(responseData);
    }

    public ResponseEntity<ResponseData<Department>> update(@Valid DepartmentDTO departmentDTO, int id, Errors errors){
        getById(id);
        ResponseData<Department> responseData = new ResponseData<>();
        if(errors.hasErrors()){
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.setStatus(true);
        Department department = new Department();
        department.setId(id);
        department.setName(departmentDTO.getName());
        responseData.setPayload(departmentRepo.save(department));
        return ResponseEntity.ok(responseData);
    }

    public Department delete(int id){
        Department department = getById(id);
        departmentRepo.deleteById(id);
        return department;
    }
}
