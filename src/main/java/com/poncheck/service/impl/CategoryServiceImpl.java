package com.poncheck.service.impl;

import com.poncheck.dto.request.category.CreateCategoryRequestDTO;
import com.poncheck.dto.request.category.UpdateActiveCategoryDTO;
import com.poncheck.dto.request.category.UpdateCategoryRequestDTO;
import com.poncheck.dto.response.category.CategoryResponseDTO;
import com.poncheck.entity.Category;
import com.poncheck.exception.ResourceNotFoundException;
import com.poncheck.repository.CategoryRepository;
import com.poncheck.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;


    //Retrieves all categories
    @Override
    public List<CategoryResponseDTO> getCategories() {
        List <Category> categories = repository.findAll();
        return categories.stream()
                .map(CategoryResponseDTO::new)
                .toList();
    }

    // Retrieves all active categories
    @Override
    public List<CategoryResponseDTO> getActiveCategories() {
        List <Category> categories = repository.findByActiveTrue();
        return categories.stream()
                .map(CategoryResponseDTO::new)
                .toList();
    }

    // Retrieves all Inactive Categories
    @Override
    public List<CategoryResponseDTO> getInactiveCategories() {
        List <Category> categories = repository.findByActiveFalse();
        return categories.stream()
                .map(CategoryResponseDTO::new)
                .toList();
    }

    //Retrieves a category by its ID
    @Override
    public CategoryResponseDTO getCategoryById(Long id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category Not Found"));
        return new CategoryResponseDTO(category);
    }

    //Creates a new Category
    @Override
    public CategoryResponseDTO createCategory(CreateCategoryRequestDTO data) {
        Category category = new Category(data);
        Category categorySaved = repository.save(category);
        return new CategoryResponseDTO(categorySaved);
    }

    @Override
    public CategoryResponseDTO updateCategory(Long id, UpdateCategoryRequestDTO data) {
        return null;
    }

    @Override
    public CategoryResponseDTO updateActive(Long id, UpdateActiveCategoryDTO status) {
        return null;
    }

    //Deletes a category (physical deletion)
    @Override
    public void deleteCategory(Long id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category Not Found"));

        repository.delete(category);
    }
}
