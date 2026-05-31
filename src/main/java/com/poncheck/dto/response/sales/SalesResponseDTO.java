package com.poncheck.dto.response.sales;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.poncheck.dto.response.user.UserSaleResponseDTO;
import com.poncheck.entity.Sales;
import com.poncheck.enums.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record SalesResponseDTO(
        Long id,
        BigDecimal total,
        PaymentMethod paymentMethod,
        LocalDateTime date,
        String description,
        UserSaleResponseDTO user,
        List<SaleItemsResponseDTO> items,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        CancelledSaleResponseDTO cancelled
) {
    public SalesResponseDTO(Sales sale){
        this(
                sale.getId(),
                sale.getTotal(),
                sale.getPaymentMethod(),
                sale.getDate(),
                sale.getDescription(),
                new UserSaleResponseDTO(sale.getUser()),
                sale.getItems().stream().map(SaleItemsResponseDTO::new).toList(),
                sale.getCancelled() != null
                    ? new CancelledSaleResponseDTO(sale.getCancelled())
                    : null
        );
    }
}
