package mii.mcc72.ams_server_app.controllers;

import lombok.AllArgsConstructor;
import mii.mcc72.ams_server_app.models.Employee;
import mii.mcc72.ams_server_app.models.dto.EmployeeDTO;
import mii.mcc72.ams_server_app.models.dto.ResponseData;
import mii.mcc72.ams_server_app.services.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/employee")
@AllArgsConstructor
public class EmployeeController {
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAll() {
        return employeeService.getAll();
    }

    @GetMapping("/{id}")
    public Employee getById(@PathVariable("id") int id) {
        return employeeService.getById(id);
    }

    @PostMapping
    public ResponseEntity<ResponseData<Employee>> create(@RequestBody EmployeeDTO employeeDTO, Errors errors) {
        return employeeService.create(employeeDTO, errors);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseData<Employee>> update(@PathVariable int id, @RequestBody EmployeeDTO employeeDTO, Errors errors) {
        return employeeService.update(employeeDTO, id, errors);
    }

    @DeleteMapping("/{id}")
    public Employee delete(@PathVariable int id) {
        return employeeService.delete(id);
    }
}
