package com.poncheck.dto.request.cash;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record UpdateCashMovementRequestDTO(
        @NotNull
        String description,
        @Positive
        BigDecimal amount,
        Long businessId
) {
}
