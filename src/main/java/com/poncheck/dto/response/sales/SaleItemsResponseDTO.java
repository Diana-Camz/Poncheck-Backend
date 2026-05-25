package com.poncheck.dto.response.sales;


import com.poncheck.entity.SaleItem;

import java.math.BigDecimal;

public record SaleItemsResponseDTO(
        Long itemId,
        String itemName,
        String category,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal subtotal

) {
    public SaleItemsResponseDTO(SaleItem saleItem){
        this(
                saleItem.getId(),
                saleItem.getProduct().getName(),
                saleItem.getProduct().getCategory().getName(),
                saleItem.getQuantity(),
                saleItem.getUnitPrice(),
                saleItem.getSubtotal()
        );
    }
}
