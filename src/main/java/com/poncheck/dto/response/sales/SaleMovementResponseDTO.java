package com.poncheck.dto.response.sales;

import com.poncheck.entity.Sales;
import com.poncheck.enums.PaymentMethod;
import com.poncheck.enums.SaleStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SaleMovementResponseDTO(
          Long id,
          BigDecimal total,
          PaymentMethod paymentMethod,
          LocalDateTime date,
          SaleStatus status
) {
    public SaleMovementResponseDTO(Sales sale){
        this(
                sale.getId(),
                sale.getTotal(),
                sale.getPaymentMethod(),
                sale.getDate(),
                sale.getSaleStatus()
        );
    }
}
