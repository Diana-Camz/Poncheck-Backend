package com.poncheck.dto.request.inventory;

import com.poncheck.enums.TypeInventoryMovement;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateMovementRequestDTO(
        @NotNull
        TypeInventoryMovement type,
        String description,
        @NotNull
        Long userId,
        @NotNull
        List<MovementItemRequestDTO> products,
        Long saleId,
        Long referenceMovement

) {
}
