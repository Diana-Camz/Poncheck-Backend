package com.poncheck.dto.request.product;

import com.poncheck.enums.PoncheBase;
import com.poncheck.enums.ProductSize;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record UpdateProductRequestDTO (

        @Size(max = 100)
        String name,
        String code,
        @Positive
        BigDecimal price,
        String flavor,
        String description,
        PoncheBase poncheBase,
        ProductSize productSize,
        Long categoryId
){
}
