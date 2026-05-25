package com.poncheck.dto.request.sales;

import com.poncheck.entity.User;
import com.poncheck.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateSaleRequestDTO(
        @NotNull
        @Positive
        BigDecimal total,
        @NotNull
        PaymentMethod paymentMethod,
        LocalDateTime date,
        String description,
        @NotNull
        Long userId
) {
}
