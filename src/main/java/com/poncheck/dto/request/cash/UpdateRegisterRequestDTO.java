package com.poncheck.dto.request.cash;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record UpdateRegisterRequestDTO(
        String description,
        @Positive
        BigDecimal realAmount,
        Long businessId
) {
}
