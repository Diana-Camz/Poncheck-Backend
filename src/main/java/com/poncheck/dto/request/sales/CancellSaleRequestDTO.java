package com.poncheck.dto.request.sales;

import java.time.LocalDateTime;

public record CancellSaleRequestDTO(
        Long userId,
        Long saleId,
        LocalDateTime date,
        String reason

) {
}
