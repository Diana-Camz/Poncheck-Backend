package com.poncheck.dto.response.inventory;

import com.poncheck.dto.response.product.ProductMovementItemResponseDTO;
import com.poncheck.dto.response.user.UserMovementResponseDTO;
import com.poncheck.entity.Movement;
import com.poncheck.enums.PoncheBase;
import com.poncheck.enums.ProductSize;
import com.poncheck.enums.TypeMovement;

import java.time.LocalDateTime;

public record MovementItemResponseDTO(
        Long id,
        TypeMovement typeMovement,
        ProductMovementItemResponseDTO product,
        UserMovementResponseDTO user,
        int quantity,
        LocalDateTime date,
        String description


) {
    public MovementItemResponseDTO(Movement movement){
        this(
                movement.getId(),
                movement.getTypeMovement(),
                new ProductMovementItemResponseDTO(movement.getProduct()),
                new UserMovementResponseDTO(movement.getUser()),
                movement.getQuantity(),
                movement.getMovementAt(),
                movement.getDescription()
        );
    }
}
