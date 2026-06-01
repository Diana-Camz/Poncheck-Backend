package com.poncheck.dto.request.sales;

import com.poncheck.enums.PaymentMethod;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateSaleRequestDTO(
        @NotNull
        PaymentMethod paymentMethod,
        String description,
        @NotEmpty
        List<SaleItemRequestDTO> items
) {
}
