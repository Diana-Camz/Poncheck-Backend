package com.poncheck.service;

import com.poncheck.dto.request.user.CreateUserRequestDTO;
import com.poncheck.dto.request.user.UpdateActiveUserRequestDTO;
import com.poncheck.dto.request.user.UpdateUserRequestDTO;
import com.poncheck.dto.response.user.UserResponseDTO;
import com.poncheck.entity.User;

import java.util.List;

public interface UserService {
    List<UserResponseDTO> getUsers();
    List<UserResponseDTO> getActiveUsers();
    List<UserResponseDTO> getInactiveUsers();
    UserResponseDTO getUserById(Long id);
    UserResponseDTO createUser(CreateUserRequestDTO user);
    UserResponseDTO updateUser(UpdateUserRequestDTO user);
    UserResponseDTO updateActive(UpdateActiveUserRequestDTO status);
    void deleteUser(Long id);

}
