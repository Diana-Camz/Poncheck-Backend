package com.poncheck.dto.response.business;

import com.poncheck.dto.response.user.UserBusinessResponseDTO;
import com.poncheck.entity.Business;
import com.poncheck.entity.User;

import java.time.LocalDateTime;

public record BusinessResponseDTO(
        Long id,
        String name,
        String phone,
        String email,
        String address,
        String description,
        String logoUrl,
        LocalDateTime createdAt,
        UserBusinessResponseDTO owner
) {
    public BusinessResponseDTO(Business business){
        this(
                business.getId(),
                business.getName(),
                business.getPhone(),
                business.getEmail(),
                business.getAddress(),
                business.getDescription(),
                business.getLogoUrl(),
                business.getCreatedAt(),
                (business.getOwner() != null) ? new UserBusinessResponseDTO(business.getOwner()) : null
        );
    }
}
