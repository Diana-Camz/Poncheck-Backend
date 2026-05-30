package com.poncheck.dto.request.cash;

import com.poncheck.dto.request.inventory.MovementItemRequestDTO;
import com.poncheck.enums.TypeCashMovement;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record CashMovementCreateRequestDTO(
       @NotNull
       TypeCashMovement type,
       @Positive
       @NotNull
       BigDecimal amount,
       @NotNull
       Long userId,
       Long saleId,
       Long cancelledSaleId,
       @NotNull
       Long cashRegisterId,
       String description
) {
}
