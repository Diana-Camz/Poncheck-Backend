package com.poncheck.dto.response.sales;

import com.poncheck.entity.CancelledSale;

import java.time.LocalDateTime;

public record CancelledSaleResponseDTO(
        String userId,
        LocalDateTime date,
        String reason
) {
    public CancelledSaleResponseDTO(CancelledSale cancelledSale) {
        this (
                cancelledSale.getUser().getName(),
                cancelledSale.getDate(),
                cancelledSale.getReason()
        );
    }
}
