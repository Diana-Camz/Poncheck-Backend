package com.poncheck.dto.request.sales;

import java.time.LocalDateTime;

public record CancelSaleRequestDTO(
        Long userId,
        String reason

) {
}
