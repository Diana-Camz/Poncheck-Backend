package com.poncheck.controller;

import com.poncheck.dto.request.category.CreateCategoryRequestDTO;
import com.poncheck.dto.request.category.UpdateActiveCategoryDTO;
import com.poncheck.dto.request.category.UpdateCategoryRequestDTO;
import com.poncheck.dto.response.category.CategoryResponseDTO;
import com.poncheck.entity.Category;
import com.poncheck.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    //Retrieves a list of all Categories
    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getCategories(){
        List <CategoryResponseDTO> categories = service.getCategories();
        return ResponseEntity.ok(categories);
    }

    //Retrieves a list of active categories
    @GetMapping("/active")
    public ResponseEntity<List<CategoryResponseDTO>> getActiveCategories(){
        List <CategoryResponseDTO> categories = service.getActiveCategories();
        return ResponseEntity.ok(categories);
    }

    //Retrieves a list of inactive categories
    @GetMapping("/inactive")
    public ResponseEntity<List<CategoryResponseDTO>> getInactiveCategories(){
        List <CategoryResponseDTO> categories = service.getInactiveCategories();
        return ResponseEntity.ok(categories);
    }

    //Retrieves a category by its ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long id){
        CategoryResponseDTO category = service.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    //Creates a new Category
    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody CreateCategoryRequestDTO data){
        CategoryResponseDTO category = service.createCategory(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    //Updates name field
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Long id, @RequestBody UpdateCategoryRequestDTO data){
        CategoryResponseDTO category = service.updateCategory(id, data);
        return ResponseEntity.ok(category);
    }
    //Updates active status (logical deletion)
    @PatchMapping("/{id}/active")
    public ResponseEntity <CategoryResponseDTO> updateActive(@PathVariable Long id, @RequestBody UpdateActiveCategoryDTO status){
        CategoryResponseDTO category = service.updateActive(id, status);
        return ResponseEntity.ok(category);
    }

    //Deletes a category by its ID (physical deletion)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id){
        service.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

}
