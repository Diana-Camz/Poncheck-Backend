package com.poncheck.dto.response.cash;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.poncheck.dto.response.user.UserCashResponseDTO;
import com.poncheck.entity.CashRegister;
import com.poncheck.enums.CashRegisterStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CashRegisterResponseDTO(
        Long id,
        UserCashResponseDTO openedBy,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        UserCashResponseDTO closedBy,
        BigDecimal openingAmount,
        BigDecimal expectedAmount,
        BigDecimal realAmount,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        BigDecimal difference,
        LocalDateTime openedAt,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        LocalDateTime closedAt,
        String description,
        CashRegisterStatus status
) {
    public CashRegisterResponseDTO(CashRegister register){
        this (
                register.getId(),
                new UserCashResponseDTO(register.getOpenedBy()),
                (register.getClosedBy() != null)
                ? new UserCashResponseDTO(register.getClosedBy())
                : null,
                register.getOpeningAmount(),
                register.getExpectedAmount(),
                register.getRealAmount(),
                register.getDifference(),
                register.getOpenedAt(),
                register.getClosedAt(),
                register.getDescription(),
                register.getStatus()
        );
    }
}
