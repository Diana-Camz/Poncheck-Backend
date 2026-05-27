package com.poncheck.dto.response.sales;


import com.poncheck.dto.response.product.ProductSaleResponseDTO;
import com.poncheck.entity.SaleItem;

import java.math.BigDecimal;

public record SaleItemsResponseDTO(
        Long itemId,
        ProductSaleResponseDTO product,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal subtotal

) {
    public SaleItemsResponseDTO(SaleItem saleItem){
        this(
                saleItem.getId(),
                new ProductSaleResponseDTO(saleItem.getProduct()),
                saleItem.getQuantity(),
                saleItem.getUnitPrice(),
                saleItem.getSubtotal()
        );
    }
}
