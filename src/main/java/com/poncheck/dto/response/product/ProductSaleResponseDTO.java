package com.poncheck.dto.response.product;

import com.poncheck.entity.Product;

import java.math.BigDecimal;

public record ProductSaleResponseDTO(
         Long id,
         String name,
         String code,
         String category
) {
    public ProductSaleResponseDTO(Product product) {
        this(
                product.getId(),
                product.getName(),
                product.getCode(),
                product.getCategory().getName()
        );
    }
}

