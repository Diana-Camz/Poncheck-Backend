package com.poncheck.dto.response.inventory;

import com.poncheck.entity.Movement;
import com.poncheck.enums.PoncheBase;
import com.poncheck.enums.ProductSize;

import java.time.LocalDateTime;

public record MovementItemResponseDTO(
        Long id,
        Long productId,
        String name,
        PoncheBase base,
        ProductSize size,
        int quantity,
        LocalDateTime date


) {
    public MovementItemResponseDTO(Movement movement){
        this(
                movement.getId(),
                movement.getProduct().getId(),
                movement.getProduct().getName(),
                movement.getProduct().getPoncheBase(),
                movement.getProduct().getProductSize(),
                movement.getQuantity(),
                movement.getMovementAt()
        );
    }
}
