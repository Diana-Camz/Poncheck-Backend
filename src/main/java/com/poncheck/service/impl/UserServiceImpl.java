package com.poncheck.service.impl;

import com.poncheck.dto.request.user.CreateUserRequestDTO;
import com.poncheck.dto.request.user.UpdateActiveUserRequestDTO;
import com.poncheck.dto.request.user.UpdateUserRequestDTO;
import com.poncheck.dto.response.user.UserResponseDTO;
import com.poncheck.entity.User;
import com.poncheck.exception.DuplicateFieldException;
import com.poncheck.repository.UserRepository;
import com.poncheck.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public List<UserResponseDTO> getUsers() {
        return List.of();
    }

    @Override
    public List<UserResponseDTO> getActiveUsers() {
        return List.of();
    }

    @Override
    public List<UserResponseDTO> getInactiveUsers() {
        return List.of();
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        return null;
    }

    //Creates new User
    @Override
    public UserResponseDTO createUser(CreateUserRequestDTO userData) {
        if(repository.existsUserByUsername(userData.username())){
            throw new DuplicateFieldException("A user with this username already exists");
        }
        User user = new User(
                userData.name(),
                userData.username(),
                userData.password(),
                userData.role()
        );

        User userSaved = repository.save(user);
        return new UserResponseDTO(userSaved);
    }

    @Override
    public UserResponseDTO updateUser(UpdateUserRequestDTO user) {
        return null;
    }

    @Override
    public UserResponseDTO updateActive(UpdateActiveUserRequestDTO status) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }
}
