package com.poncheck.dto.request.category;

import jakarta.validation.constraints.NotNull;

public record UpdateActiveCategoryDTO(
        @NotNull
        Boolean active
) {
}
