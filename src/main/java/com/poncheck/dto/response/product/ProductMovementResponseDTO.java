package com.poncheck.dto.response.product;

import com.poncheck.entity.Product;

import java.math.BigDecimal;

public record ProductMovementResponseDTO(
         Long id,
         String name,
         String code,
         BigDecimal price,
         String category
) {
    public ProductMovementResponseDTO(Product product) {
        this(
                product.getId(),
                product.getName(),
                product.getCode(),
                product.getPrice(),
                product.getCategory().getName()
        );
    }
}
