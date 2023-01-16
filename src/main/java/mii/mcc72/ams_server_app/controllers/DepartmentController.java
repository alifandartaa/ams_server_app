package mii.mcc72.ams_server_app.controllers;

import lombok.AllArgsConstructor;
import mii.mcc72.ams_server_app.models.Department;
import mii.mcc72.ams_server_app.models.dto.DepartmentDTO;
import mii.mcc72.ams_server_app.models.dto.ResponseData;
import mii.mcc72.ams_server_app.services.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/department")
@PreAuthorize("hasAnyRole('ADMIN','FINANCE')")
@AllArgsConstructor
public class DepartmentController {
    private DepartmentService departmentService;

    @GetMapping
    public List<Department> getAll() {
        return departmentService.getAll();
    }

    @GetMapping("/{id}")
    public Department getById(@PathVariable("id") int id) {
        return departmentService.getById(id);
    }

    @PostMapping
    public ResponseEntity<ResponseData<Department>> create(@RequestBody DepartmentDTO departmentDTO, Errors errors) {
        return departmentService.create(departmentDTO, errors);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseData<Department>> update(@PathVariable int id, @RequestBody DepartmentDTO departmentDTO, Errors errors) {
        return departmentService.update(departmentDTO, id, errors);
    }

    @DeleteMapping("/{id}")
    public Department delete(@PathVariable int id) {
        return departmentService.delete(id);
    }
}
