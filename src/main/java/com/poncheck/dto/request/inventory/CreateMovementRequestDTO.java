package com.poncheck.dto.request.inventory;

import com.poncheck.enums.TypeMovement;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record CreateMovementRequestDTO(
        @NotNull
        TypeMovement type,
        String description,
        @NotNull
        Long userId,
        @NotNull
        List<MovementItemRequestDTO> products,
        Long saleId,
        Long referenceMovement

) {
}
