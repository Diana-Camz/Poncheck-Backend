package com.poncheck.dto.response.user;

import com.poncheck.entity.User;
import com.poncheck.enums.Role;

public record UserCashResponseDTO (
        Long id,
        String name,
        Role role
){
    public UserCashResponseDTO(User user){
        this(
                user.getId(),
                user.getName(),
                user.getRole()
        );
    }
}
