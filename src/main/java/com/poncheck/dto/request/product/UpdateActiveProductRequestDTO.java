package com.poncheck.dto.request.product;

import jakarta.validation.constraints.NotNull;

public record UpdateActiveProductRequestDTO(
        @NotNull
        Boolean active
) {
}
