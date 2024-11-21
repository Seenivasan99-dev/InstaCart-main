package com.mycart.mycart.Controller;

import com.mycart.mycart.Entities.Category;
import com.mycart.mycart.Exceptions.AlreadyExsist;
import com.mycart.mycart.Response.CategoryApiResponse;
import com.mycart.mycart.Response.ImageApiResponse;
import com.mycart.mycart.Service.CategoryServiceIImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    CategoryServiceIImpl categoryService;
    @GetMapping("/all")
    public ResponseEntity<CategoryApiResponse> getAllCategories() {
        List<Category> category=categoryService.getAllCategories();
        if(category!=null){
            return ResponseEntity.ok(new CategoryApiResponse("All categories", category));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CategoryApiResponse("CategoryNotFound",null));
    }
    @GetMapping("/category/{catId}")
    public ResponseEntity<CategoryApiResponse> getCategoryById(int categoryId) {
        Category category=categoryService.getCategoryById(categoryId);
        if(category!=null){
            return ResponseEntity.ok(new CategoryApiResponse("Category Fetched Sucessfully", category));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CategoryApiResponse("CategoryNotFetched",null));

    }
    @PostMapping("/add")
    public ResponseEntity<CategoryApiResponse> addCategory(@RequestBody Category category) {
        Category Category=categoryService.addCategory(category);
        try {
            if (Category != null) {
                return ResponseEntity.status(HttpStatus.FOUND).body(new CategoryApiResponse("success", Category));
            }
        }
        catch(AlreadyExsist e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CategoryApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CategoryApiResponse("Not An Category", null));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryApiResponse> updateCategory(@RequestBody Category category,@PathVariable int categoryId ) {
        Category updatedCategory=categoryService.updateCategory(category, categoryId);
        if(updatedCategory!=null){
            return ResponseEntity.status(HttpStatus.FOUND).body(new CategoryApiResponse("success", updatedCategory));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CategoryApiResponse("Updation Not Sucessfull",null));

    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CategoryApiResponse> deleteCategory(@PathVariable int categoryId) {
        categoryService.deleteCategory(categoryId);
        if(categoryId>0){
            return ResponseEntity.status(HttpStatus.FOUND).body(new CategoryApiResponse("success", categoryId));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CategoryApiResponse("Not Deleted",null));
    }

    @GetMapping("/categoryname/{catname}")
    public ResponseEntity<CategoryApiResponse> getCategoryByName(String categorname) {
        Category category=categoryService.findCategoryByName(categorname);
        if(category!=null){
            return ResponseEntity.ok(new CategoryApiResponse("Category Fetched Sucessfully", category));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CategoryApiResponse("CategoryNotFetched",null));

    }










}
