package com.poncheck.dto.request.cash;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CashRegisterCloseRequestDTO(
        @NotNull
        Long userId,
        @NotNull
        @Positive
        BigDecimal realAmount,
        String description
) {
}
