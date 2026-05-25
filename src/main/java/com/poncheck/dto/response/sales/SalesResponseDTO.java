package com.poncheck.dto.response.sales;

import com.poncheck.entity.Sales;
import com.poncheck.enums.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record SalesResponseDTO(
        BigDecimal total,
        PaymentMethod paymentMethod,
        LocalDateTime date,
        String description,
        String user,
        List<SaleItemsResponseDTO> items
) {
    public SalesResponseDTO(Sales sale){
        this(
                sale.getTotal(),
                sale.getPaymentMethod(),
                sale.getDate(),
                sale.getDescription(),
                sale.getUser().getName(),
                sale.getItems().stream().map(SaleItemsResponseDTO::new).toList()
        );
    }
}
