package com.poncheck.dto.response.inventory;

import com.poncheck.dto.response.product.ProductMovementResponseDTO;
import com.poncheck.dto.response.sales.SaleMovementResponseDTO;
import com.poncheck.dto.response.user.UserMovementResponseDTO;
import com.poncheck.entity.Movement;
import com.poncheck.enums.TypeInventoryMovement;

import java.time.LocalDateTime;

public record MovementResponseDTO(
            TypeInventoryMovement typeInventoryMovement,
            int quantity,
            LocalDateTime movementAt,
            ProductMovementResponseDTO product,
            UserMovementResponseDTO user,
            SaleMovementResponseDTO sale,
            String description,
            Long referenceMovementId
) {
    public MovementResponseDTO(Movement movement){
        this(
                movement.getTypeInventoryMovement(),
                movement.getQuantity(),
                movement.getMovementAt(),
                new ProductMovementResponseDTO(movement.getProduct()),
                new UserMovementResponseDTO(movement.getUser()),
                movement.getSale() != null
                ? new SaleMovementResponseDTO(movement.getSale())
                : null,
                movement.getDescription(),
                movement.getReferenceMovement() != null
                ? movement.getReferenceMovement().getId()
                : null

        );
    }
}
