package com.poncheck.service.impl;

import com.poncheck.dto.request.category.CreateCategoryRequestDTO;
import com.poncheck.dto.request.category.UpdateActiveCategoryDTO;
import com.poncheck.dto.request.category.UpdateCategoryRequestDTO;
import com.poncheck.dto.response.category.CategoryResponseDTO;
import com.poncheck.entity.Business;
import com.poncheck.entity.Category;
import com.poncheck.entity.User;
import com.poncheck.exception.DuplicateFieldException;
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
    private final AuthenticatedUserService authenticatedUserService;
    private final BusinessContextService businessContextService;


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
        User currentUser = authenticatedUserService.getCurrentUser();
        Business business = currentUser.getBusiness();
        List <Category> categories = repository.findByActiveTrueAndBusinessId(business.getId());
        return categories.stream()
                .map(CategoryResponseDTO::new)
                .toList();
    }

    // Retrieves all Inactive Categories
    @Override
    public List<CategoryResponseDTO> getInactiveCategories() {
        User currentUser = authenticatedUserService.getCurrentUser();
        Business business = currentUser.getBusiness();
        List <Category> categories = repository.findByActiveFalseAndBusinessId(business.getId());
        return categories.stream()
                .map(CategoryResponseDTO::new)
                .toList();
    }

    //Retrieves a category by its ID
    @Override
    public CategoryResponseDTO getCategoryById(Long categoryId) {
        Long businessId = businessContextService.getCurrentBusiness().getId();
        Category category = repository.findByIdAndBusiness_id(categoryId, businessId)
                .orElseThrow(() -> new ResourceNotFoundException("CATEGORY_NOT_FOUND", "Category Not Found", "category", categoryId));
        return new CategoryResponseDTO(category);
    }

    //Creates a new Category
    @Override
    public CategoryResponseDTO createCategory(CreateCategoryRequestDTO data) {
        String normalizedName = data.name().trim().toLowerCase();

        Business business = businessContextService.getBusiness(data.businessId());
        if(repository.existsByNameIgnoreCaseAndBusinessId(normalizedName, business.getId())){
            throw new DuplicateFieldException("CATEGORY_ALREADY_EXISTS", "A category with this name already exists");
        }

        Category category = new Category(data.name().trim(), business);
        Category categorySaved = repository.save(category);
        return new CategoryResponseDTO(categorySaved);
    }

    //Updates name field by its ID
    @Override
    public CategoryResponseDTO updateCategory(Long categoryId, UpdateCategoryRequestDTO data) {
        Long businessId = businessContextService.getBusiness(data.businessId()).getId();
        Category category = repository.findByIdAndBusiness_id(categoryId, businessId)
                .orElseThrow(() -> new ResourceNotFoundException("CATEGORY_NOT_FOUND", "Category Not Found", "category", categoryId));
        String normalizedName = data.name().trim().toLowerCase();

        if(repository.existsByNameIgnoreCaseAndBusinessId(normalizedName, businessId)){
            throw new DuplicateFieldException("CATEGORY_ALREADY_EXISTS", "A category with this name already exists");
        }
        category.updateCategory(data.name());
        Category categoryUpdated = repository.save(category);
        return new CategoryResponseDTO(categoryUpdated);
    }

    //Updates the category active status (logical deletion)
    @Override
    public CategoryResponseDTO updateActive(Long categoryId, UpdateActiveCategoryDTO data) {
        Long businessId = businessContextService.getBusiness(data.businessId()).getId();
        Category category = repository.findByIdAndBusiness_id(categoryId, businessId)
                .orElseThrow(()-> new ResourceNotFoundException("CATEGORY_NOT_FOUND", "Category Not Found", "category", categoryId));
        category.updateActive(data.active());
        Category updatedStatus = repository.save(category);
        return new CategoryResponseDTO(updatedStatus);
    }

    //Deletes a category (physical deletion)
    @Override
    public void deleteCategory(Long id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CATEGORY_NOT_FOUND", "Category Not Found", "category", id));

        repository.delete(category);
    }
}
