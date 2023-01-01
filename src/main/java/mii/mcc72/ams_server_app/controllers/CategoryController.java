package mii.mcc72.ams_server_app.controllers;

import lombok.AllArgsConstructor;
import mii.mcc72.ams_server_app.models.Category;
import mii.mcc72.ams_server_app.models.dto.CategoryDTO;
import mii.mcc72.ams_server_app.models.dto.ResponseData;
import mii.mcc72.ams_server_app.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/category")
@PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN')")
@AllArgsConstructor
public class CategoryController {

    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAll(){
        return categoryService.getAll();
    }

    @GetMapping("/{id}")
    public Category getById(@PathVariable("id") int id){
        return categoryService.getById(id);
    }

    @PostMapping
    public ResponseEntity<ResponseData<Category>> create(@RequestBody CategoryDTO category , Errors errors){
        return categoryService.create(category , errors);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseData<Category>> update(@PathVariable int id, @RequestBody CategoryDTO category , Errors errors){
        return categoryService.update(category , id, errors);
    }

    @DeleteMapping("/{id}")
    public Category delete(@PathVariable int id){
        return categoryService.delete(id);
    }
}
