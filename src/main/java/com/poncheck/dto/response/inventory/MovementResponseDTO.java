package com.poncheck.dto.response.inventory;

import com.poncheck.dto.response.product.ProductMovementResponseDTO;
import com.poncheck.dto.response.sales.SaleMovementResponseDTO;
import com.poncheck.dto.response.user.UserMovementResponseDTO;
import com.poncheck.entity.Movement;
import com.poncheck.enums.TypeMovement;

public record MovementResponseDTO(
            TypeMovement typeMovement,
            int quantity,
            ProductMovementResponseDTO product,
            UserMovementResponseDTO user,
            SaleMovementResponseDTO sale,
            String description,
            Long referenceMovementId
) {
    public MovementResponseDTO(Movement movement){
        this(
                movement.getTypeMovement(),
                movement.getQuantity(),
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
