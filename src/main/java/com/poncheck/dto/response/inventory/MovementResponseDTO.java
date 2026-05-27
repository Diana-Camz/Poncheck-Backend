package com.poncheck.dto.response.inventory;

import com.poncheck.dto.response.product.ProductMovementResponseDTO;
import com.poncheck.dto.response.sales.SaleMovementResponseDTO;
import com.poncheck.dto.response.user.UserMovementResponseDTO;
import com.poncheck.entity.Movement;
import com.poncheck.enums.TypeMovement;

public record MovementResponseDTO(
            TypeMovement typeMovement,
            int quantity,
            String description,
            ProductMovementResponseDTO product,
            SaleMovementResponseDTO sale,
            UserMovementResponseDTO user,
            Long referenceMovementId
) {
    public MovementResponseDTO(Movement movement){
        this(
                movement.getTypeMovement(),
                movement.getQuantity(),
                movement.getDescription(),
                new ProductMovementResponseDTO(movement.getProduct()),
                movement.getSale() != null
                ? new SaleMovementResponseDTO(movement.getSale())
                : null,
                new UserMovementResponseDTO(movement.getUser()),
                movement.getReferenceMovement() != null
                ? movement.getReferenceMovement().getId()
                : null

        );
    }
}
