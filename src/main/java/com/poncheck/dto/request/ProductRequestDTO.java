package com.poncheck.dto.request;

import com.poncheck.enums.PoncheBase;
import com.poncheck.enums.ProductSize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;


import java.math.BigDecimal;

public record ProductRequestDTO(
        @NotBlank
        @Size(max = 100)
        String name,
        @NotNull
        String code,
        @NotNull
        @Positive
        BigDecimal price,
        String flavor,
        String description,
        PoncheBase poncheBase,
        ProductSize productSize,
        @NotNull
        Long categoryId
) {
}
