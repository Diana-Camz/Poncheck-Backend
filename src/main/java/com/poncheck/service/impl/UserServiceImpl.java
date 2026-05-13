package com.poncheck.service.impl;

import com.poncheck.dto.request.user.CreateUserRequestDTO;
import com.poncheck.dto.request.user.UpdateActiveUserRequestDTO;
import com.poncheck.dto.request.user.UpdateUserRequestDTO;
import com.poncheck.dto.response.user.UserResponseDTO;
import com.poncheck.entity.User;
import com.poncheck.exception.DuplicateFieldException;
import com.poncheck.exception.ResourceNotFoundException;
import com.poncheck.repository.UserRepository;
import com.poncheck.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    //Retrieves all users
    @Override
    public List<UserResponseDTO> getUsers() {
        List<User> users = repository.findAll();
          return  users.stream()
                    .map(UserResponseDTO::new)
                    .toList();
    }

    //Retrieves all active users
    @Override
    public List<UserResponseDTO> getActiveUsers() {
        List<User> users = repository.findByActiveTrue();
        return users.stream()
                .map(UserResponseDTO::new)
                .toList();
    }

    //Retrieves all inactive users
    @Override
    public List<UserResponseDTO> getInactiveUsers() {
        List<User> users = repository.findByActiveFalse();
        return users.stream()
                .map(UserResponseDTO::new)
                .toList();
    }

    //Retrieves a user by its ID
    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        return new UserResponseDTO(user);
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
    public UserResponseDTO updateUser(Long id, UpdateUserRequestDTO userData) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        if(repository.existsUserByUsername(userData.username())){
            throw new DuplicateFieldException("A user with this username already exists");
        }
        user.updateUser(
                userData.name(),
                userData.username(),
                userData.role()
        );

        User userSaved = repository.save(user);
        return new UserResponseDTO(userSaved);
    }

    @Override
    public UserResponseDTO updateActive(Long id, UpdateActiveUserRequestDTO status) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        user.inactiveUser(status.active());
        User userSaved = repository.save(user);
        return new UserResponseDTO(userSaved);
    }

    @Override
    public void deleteUser(Long id) {
        User user = repository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        repository.delete(user);
    }
}
