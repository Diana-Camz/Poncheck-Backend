package com.poncheck.dto.request.inventory;

public record UpdateMovementRequestDTO(
        String description,
        Long businessId
) {
}
