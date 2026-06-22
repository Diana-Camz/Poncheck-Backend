package com.poncheck.dto.response.user;

import com.poncheck.entity.User;
import com.poncheck.enums.Role;

public record UserBusinessResponseDTO (
        Long id,
        String name,
        Role role
){
    public UserBusinessResponseDTO(User user){
        this(
                user.getId(),
                user.getName(),
                user.getRole()
        );
    }
}
