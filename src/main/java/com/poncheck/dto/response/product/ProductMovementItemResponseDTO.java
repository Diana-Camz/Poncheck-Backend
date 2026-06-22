package com.poncheck.dto.response.product;

import com.poncheck.entity.Product;
import com.poncheck.enums.PoncheBase;
import com.poncheck.enums.ProductSize;

public record ProductMovementItemResponseDTO(
        Long id,
        String name,
        PoncheBase base,
        ProductSize size
) {
    public ProductMovementItemResponseDTO(Product product){
          this(
                product.getId(),
                product.getName(),
                product.getPoncheBase(),
                product.getProductSize()
          );
    }
}
