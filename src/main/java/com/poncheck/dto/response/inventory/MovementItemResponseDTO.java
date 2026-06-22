package com.poncheck.dto.response.inventory;

import com.poncheck.dto.response.product.ProductMovementItemResponseDTO;
import com.poncheck.dto.response.user.UserMovementResponseDTO;
import com.poncheck.entity.Movement;
import com.poncheck.enums.TypeInventoryMovement;

import java.time.LocalDateTime;

public record MovementItemResponseDTO(
        Long id,
        TypeInventoryMovement typeInventoryMovement,
        ProductMovementItemResponseDTO product,
        UserMovementResponseDTO user,
        int quantity,
        LocalDateTime date,
        String description


) {
    public MovementItemResponseDTO(Movement movement){
        this(
                movement.getId(),
                movement.getTypeInventoryMovement(),
                new ProductMovementItemResponseDTO(movement.getProduct()),
                new UserMovementResponseDTO(movement.getUser()),
                movement.getQuantity(),
                movement.getMovementAt(),
                movement.getDescription()
        );
    }
}
