package com.poncheck.dto.response;

import com.poncheck.entity.Category;

public record CategoryResponseDTO(
        Long id,
        String name
) {
    public CategoryResponseDTO(Category category){
        this(
                category.getId(),
                category.getName()
        );
    }
}
