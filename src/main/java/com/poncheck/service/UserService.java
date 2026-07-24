package com.poncheck.service;

import com.poncheck.dto.request.user.UpdateActiveUserRequestDTO;
import com.poncheck.dto.request.user.UpdateUserRequestDTO;
import com.poncheck.dto.response.user.UserResponseDTO;

import java.util.List;

public interface UserService {
    List<UserResponseDTO> getUsers();
    List<UserResponseDTO> getActiveUsers();
    List<UserResponseDTO> getInactiveUsers();
    UserResponseDTO getUserById(Long id);
    UserResponseDTO getCurrentUser();
    UserResponseDTO updateUser(Long id, UpdateUserRequestDTO user);
    UserResponseDTO updateActive(Long id, UpdateActiveUserRequestDTO status);
    void deleteUser(Long id);

}
