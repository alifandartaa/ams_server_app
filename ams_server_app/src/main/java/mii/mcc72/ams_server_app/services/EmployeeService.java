package mii.mcc72.ams_server_app.services;

import lombok.AllArgsConstructor;
import mii.mcc72.ams_server_app.models.Employee;
import mii.mcc72.ams_server_app.models.dto.EmployeeDTO;
import mii.mcc72.ams_server_app.models.dto.ResponseData;
import mii.mcc72.ams_server_app.repos.DepartmentRepo;
import mii.mcc72.ams_server_app.repos.EmployeeRepo;
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
public class EmployeeService {
    private EmployeeRepo employeeRepo;
    private DepartmentService departmentService;

    public List<Employee> getAll(){
        return employeeRepo.findAll();
    }

    public Employee getById(int id){
        return employeeRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Employee ID %s Not Found !!", id))
        );
    }

    public ResponseEntity<ResponseData<Employee>> create(@Valid EmployeeDTO employeeDTO, Errors errors){
        ResponseData<Employee> responseData = new ResponseData<>();
        if(errors.hasErrors()){
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.setStatus(true);
        Employee employee = new Employee();
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setPhoneNumber(employeeDTO.getPhoneNumber());
        employee.setDepartment(departmentService.getById(employeeDTO.getDepartmentId()));
        responseData.setPayload(employeeRepo.save(employee));
        return ResponseEntity.ok(responseData);
    }

    public ResponseEntity<ResponseData<Employee>> update(@Valid EmployeeDTO employeeDTO, int id, Errors errors){
        getById(id);
        ResponseData<Employee> responseData = new ResponseData<>();
        if(errors.hasErrors()){
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.setStatus(true);
        Employee employee = new Employee();
        employee.setId(id);
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setPhoneNumber(employeeDTO.getPhoneNumber());
        employee.setDepartment(departmentService.getById(employeeDTO.getDepartmentId()));
        responseData.setPayload(employeeRepo.save(employee));
        return ResponseEntity.ok(responseData);
    }

    public Employee delete(int id){
        Employee employee = getById(id);
        employeeRepo.deleteById(id);
        return employee;
    }
}
