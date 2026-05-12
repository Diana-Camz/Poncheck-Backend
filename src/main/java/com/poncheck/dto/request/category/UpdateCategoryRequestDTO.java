package com.poncheck.dto.request.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateCategoryRequestDTO(
        @NotNull
        @NotBlank
        String name
) {
}
