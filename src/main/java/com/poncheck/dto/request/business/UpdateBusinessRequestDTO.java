package com.poncheck.dto.request.business;

import com.poncheck.entity.User;

public record UpdateBusinessRequestDTO(
        String name,
        String phone,
        String email,
        String address,
        String description,
        String logoUrl,
        Long ownerId
) {
}
