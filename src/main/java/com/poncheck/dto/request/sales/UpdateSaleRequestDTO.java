package com.poncheck.dto.request.sales;

import com.poncheck.enums.PaymentMethod;

public record UpdateSaleRequestDTO(
        PaymentMethod paymentMethod,
        String description
) {
}
