package com.poncheck.dto.response.user;

import com.poncheck.entity.User;
import com.poncheck.enums.Role;

public record UserResponseDTO(
        Long id,
        String name,
        String username,
        Role role,
        Boolean active
) {
    public UserResponseDTO(User user){
        this(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getRole(),
                user.getActive()
        );
    }

}
