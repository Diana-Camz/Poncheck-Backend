package com.poncheck.dto.request.sales;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SaleItemRequestDTO(
        @NotNull
        Long productId,
        @NotNull
        @Positive
        Integer quantity
) {
}
