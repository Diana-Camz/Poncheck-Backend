package com.poncheck.service.impl;

import com.poncheck.dto.request.user.UpdateActiveUserRequestDTO;
import com.poncheck.dto.request.user.UpdateUserRequestDTO;
import com.poncheck.dto.response.user.UserResponseDTO;
import com.poncheck.entity.Business;
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
    private final AuthenticatedUserService authenticatedUserService;


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
        User currentUser = authenticatedUserService.getCurrentUser();
        Business business = currentUser.getBusiness();
        List<User> users = repository.findByActiveTrueAndBusinessId(business.getId());
        return users.stream()
                .map(UserResponseDTO::new)
                .toList();
    }

    //Retrieves all inactive users
    @Override
    public List<UserResponseDTO> getInactiveUsers() {
        User currentUser = authenticatedUserService.getCurrentUser();
        Business business = currentUser.getBusiness();
        List<User> users = repository.findByActiveFalseAndBusinessId(business.getId());
        return users.stream()
                .map(UserResponseDTO::new)
                .toList();
    }

    //Retrieves a user by its ID
    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found", "user", id));
        return new UserResponseDTO(user);
    }

    @Override
    public UserResponseDTO updateUser(Long id, UpdateUserRequestDTO userData) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found", "user", id));

        Business business = user.getBusiness();
        if(repository.existsByUsername(userData.username())){
            throw new DuplicateFieldException("A user with this username already exists");
        }
        user.updateUser(
                userData.name(),
                userData.username(),
                null,
                business
        );

        User userSaved = repository.save(user);
        return new UserResponseDTO(userSaved);
    }

    @Override
    public UserResponseDTO updateActive(Long id, UpdateActiveUserRequestDTO status) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found", "user", id));
        user.inactiveUser(status.active());
        User userSaved = repository.save(user);
        return new UserResponseDTO(userSaved);
    }

    @Override
    public void deleteUser(Long id) {
        User user = repository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("User Not Found", "user", id));
        repository.delete(user);
    }
}
