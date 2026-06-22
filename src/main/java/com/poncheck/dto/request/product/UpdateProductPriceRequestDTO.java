package com.poncheck.dto.request.product;

import com.poncheck.enums.PoncheBase;
import com.poncheck.enums.ProductSize;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record UpdateProductPriceRequestDTO(
        Long categoryId,
        ProductSize productSize,
        PoncheBase poncheBase,
        String name,
        @Positive
        @NotNull
        BigDecimal price
) {
}
