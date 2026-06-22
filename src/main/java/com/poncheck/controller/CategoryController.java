package com.poncheck.controller;

import com.poncheck.dto.request.category.CreateCategoryRequestDTO;
import com.poncheck.dto.request.category.UpdateActiveCategoryDTO;
import com.poncheck.dto.request.category.UpdateCategoryRequestDTO;
import com.poncheck.dto.response.category.CategoryResponseDTO;
import com.poncheck.service.CategoryService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Categories", description = "Endpoints for managing product categories, including creation, updates, activation, deactivation, and deletion.")
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @Operation(summary = "Get all categories", description = "Retrieves all categories available for the current business.")
    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getCategories(){
        List <CategoryResponseDTO> categories = service.getCategories();
        return ResponseEntity.ok(categories);
    }

    @Operation(summary = "Get active categories", description = "Retrieves all active categories available for the current business.")
    @GetMapping("/active")
    public ResponseEntity<List<CategoryResponseDTO>> getActiveCategories(){
        List <CategoryResponseDTO> categories = service.getActiveCategories();
        return ResponseEntity.ok(categories);
    }

    @Operation(summary = "Get inactive categories", description = "Retrieves all inactive categories available for the current business.")
    @GetMapping("/inactive")
    public ResponseEntity<List<CategoryResponseDTO>> getInactiveCategories(){
        List <CategoryResponseDTO> categories = service.getInactiveCategories();
        return ResponseEntity.ok(categories);
    }

    @Operation(summary = "Get category by ID", description = "Retrieves detailed information about a specific category.")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long id){
        CategoryResponseDTO category = service.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    @Operation(summary = "Create category", description = "Creates a new category for the current business.")
    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody CreateCategoryRequestDTO data){
        CategoryResponseDTO category = service.createCategory(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @Operation(summary = "Update category", description = "Updates the information of an existing category.")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Long id, @RequestBody UpdateCategoryRequestDTO data){
        CategoryResponseDTO category = service.updateCategory(id, data);
        return ResponseEntity.ok(category);
    }
    @Operation(summary = "Update category active status", description = "Activates or deactivates a category without permanently removing it from the system.")
    @PatchMapping("/{id}/active")
    public ResponseEntity <CategoryResponseDTO> updateActive(@PathVariable Long id, @RequestBody UpdateActiveCategoryDTO status){
        CategoryResponseDTO category = service.updateActive(id, status);
        return ResponseEntity.ok(category);
    }

    //@Operation(summary = "Delete category", description = "Permanently removes a category from the system.")
    @Hidden
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id){
        service.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

}
