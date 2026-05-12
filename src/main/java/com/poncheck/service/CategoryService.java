package com.poncheck.service;

import com.poncheck.dto.request.category.CreateCategoryRequestDTO;
import com.poncheck.dto.request.category.UpdateActiveCategoryDTO;
import com.poncheck.dto.request.category.UpdateCategoryRequestDTO;
import com.poncheck.dto.response.category.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryResponseDTO> getCategories();
    List<CategoryResponseDTO> getActiveCategories();
    CategoryResponseDTO getCategoryById(Long id);
    CategoryResponseDTO createCategory(CreateCategoryRequestDTO data);
    CategoryResponseDTO updateCategory(Long id, UpdateCategoryRequestDTO data);
    CategoryResponseDTO updateActive(Long id, UpdateActiveCategoryDTO status);
    void deleteCategory(Long id);

}
