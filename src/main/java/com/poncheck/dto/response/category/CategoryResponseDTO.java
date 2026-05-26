package com.poncheck.dto.response.category;

import com.poncheck.entity.Category;

public record CategoryResponseDTO(
        Long id,
        String name,
        Boolean active
) {
    public CategoryResponseDTO(Category category){
        this(
                category.getId(),
                category.getName(),
                category.getActive()
        );
    }
}
