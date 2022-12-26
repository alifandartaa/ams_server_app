package mii.mcc72.ams_server_app.services;


import lombok.AllArgsConstructor;
import mii.mcc72.ams_server_app.models.Category;
import mii.mcc72.ams_server_app.models.dto.CategoryDTO;
import mii.mcc72.ams_server_app.models.dto.ResponseData;
import mii.mcc72.ams_server_app.repos.CategoryRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import javax.validation.Valid;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private CategoryRepo categoryRepo;

    public List<Category> getAll(){
        return categoryRepo.findAll();
    }

    public Category getById(int id){
        return categoryRepo.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("Category ID %s Not Found !!", id))
        );
    }

    public ResponseEntity<ResponseData<Category>> create(@Valid CategoryDTO categoryDTO, Errors errors){
        ResponseData<Category> responseData = new ResponseData<>();
        if(errors.hasErrors()){
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.setStatus(true);
        Category category = new Category();
        category.setName(categoryDTO.getName());
        responseData.setPayload(categoryRepo.save(category));
        return ResponseEntity.ok(responseData);
    }

    public ResponseEntity<ResponseData<Category>> update(@Valid CategoryDTO categoryDTO,int id, Errors errors){
        getById(id);
        ResponseData<Category> responseData = new ResponseData<>();
        if(errors.hasErrors()){
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.setStatus(true);
        Category category = new Category();
        category.setId(id);
        category.setName(categoryDTO.getName());
        responseData.setPayload(categoryRepo.save(category));
        return ResponseEntity.ok(responseData);
    }

    public Category delete(int id){
        Category category = getById(id);
        categoryRepo.deleteById(id);
        return category;
    }
}
