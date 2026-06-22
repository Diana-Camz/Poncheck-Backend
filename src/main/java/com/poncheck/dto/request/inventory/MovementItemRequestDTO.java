package com.poncheck.dto.request.inventory;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MovementItemRequestDTO(
        @NotNull
        Long productId,
        @NotNull
        @Positive
        int quantity
) {
}
