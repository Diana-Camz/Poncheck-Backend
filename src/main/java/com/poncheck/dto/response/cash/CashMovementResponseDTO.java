package com.poncheck.dto.response.cash;

import com.poncheck.dto.response.sales.CancelledSaleResponseDTO;
import com.poncheck.dto.response.sales.SaleMovementResponseDTO;
import com.poncheck.dto.response.user.UserMovementResponseDTO;
import com.poncheck.entity.CashMovement;
import com.poncheck.enums.TypeCashMovement;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CashMovementResponseDTO(
        Long id,
        TypeCashMovement typeCashMovement,
        BigDecimal amount,
        LocalDateTime movementAt,
        CashRegisterResponseDTO cashRegister,
        UserMovementResponseDTO user,
        SaleMovementResponseDTO sale,
        CancelledSaleResponseDTO cancelledSale,
        String description
) {
    public CashMovementResponseDTO(CashMovement movement){
        this(
                movement.getId(),
                movement.getTypeCashMovement(),
                movement.getAmount(),
                movement.getMovementAt(),
                new CashRegisterResponseDTO(movement.getCashRegister()),
                new UserMovementResponseDTO(movement.getUser()),
                (movement.getSale() != null)
                ? new SaleMovementResponseDTO(movement.getSale())
                : null,
                (movement.getCancelledSale() != null)
                ? new CancelledSaleResponseDTO(movement.getCancelledSale())
                : null,
                movement.getDescription()
        );
    }
}
