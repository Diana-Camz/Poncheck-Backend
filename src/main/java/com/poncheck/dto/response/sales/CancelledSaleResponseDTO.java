package com.poncheck.dto.response.sales;

import com.poncheck.entity.CancelledSale;

import java.time.LocalDateTime;

public record CancelledSaleResponseDTO(
        Long id,
        String userId,
        Long saleId,
        LocalDateTime date,
        String reason
) {
    public CancelledSaleResponseDTO(CancelledSale cancelledSale) {
        this (
                cancelledSale.getId(),
                cancelledSale.getUser().getName(),
                cancelledSale.getSale().getId(),
                cancelledSale.getDate(),
                cancelledSale.getReason()
        );
    }
}
