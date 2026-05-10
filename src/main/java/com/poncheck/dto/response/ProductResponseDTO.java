package com.poncheck.dto.response;

import com.poncheck.entity.Product;
import com.poncheck.enums.PoncheBase;
import com.poncheck.enums.ProductSize;

import java.math.BigDecimal;

public record ProductResponseDTO(
        Long id,
        String name,
        String code,
        BigDecimal price,
        String flavor,
        String description,
        Boolean active,
        PoncheBase poncheBase,
        ProductSize productSize,
        String category
) {
    public ProductResponseDTO(Product product){
        this(
                product.getId(),
                product.getName(),
                product.getCode(),
                product.getPrice(),
                product.getFlavor(),
                product.getDescription(),
                product.getActive(),
                product.getPoncheBase(),
                product.getProductSize(),
                product.getCategory().getName()
        );
    }
}
