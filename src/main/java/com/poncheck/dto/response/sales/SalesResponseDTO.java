package com.poncheck.dto.response.sales;

import com.poncheck.entity.Sales;
import com.poncheck.enums.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SalesResponseDTO(
        BigDecimal total,
        PaymentMethod paymentMethod,
        LocalDateTime date,
        String description,
        String user
) {
    public SalesResponseDTO(Sales sale){
        this(
                sale.getTotal(),
                sale.getPaymentMethod(),
                sale.getDate(),
                sale.getDescription(),
                sale.getUser().getName()
        );
    }
}
