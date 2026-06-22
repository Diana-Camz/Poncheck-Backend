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
    private final BusinessContextService businessContextService;


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
        Long businessId = businessContextService.getCurrentBusiness().getId();
        List<User> users = repository.findByActiveTrueAndBusinessId(businessId);
        return users.stream()
                .map(UserResponseDTO::new)
                .toList();
    }

    //Retrieves all inactive users
    @Override
    public List<UserResponseDTO> getInactiveUsers() {
        Long businessId = businessContextService.getCurrentBusiness().getId();
        List<User> users = repository.findByActiveFalseAndBusinessId(businessId);
        return users.stream()
                .map(UserResponseDTO::new)
                .toList();
    }

    //Retrieves a user by its ID
    @Override
    public UserResponseDTO getUserById(Long userId) {
        Long businessId = businessContextService.getCurrentBusiness().getId();
        User user = repository.findByIdAndBusiness_id(userId, businessId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found", "user", userId));
        return new UserResponseDTO(user);
    }

    @Override
    public UserResponseDTO updateUser(Long userId, UpdateUserRequestDTO userData) {
        Long businessId = businessContextService.getBusiness(userData.businessId()).getId();
        User user = repository.findByIdAndBusiness_id(userId, businessId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found", "user", userId));

        if(repository.existsByUsername(userData.username())){
            throw new DuplicateFieldException("A user with this username already exists");
        }

        user.updateUser(
                userData.name(),
                userData.username()
        );

        User userSaved = repository.save(user);
        return new UserResponseDTO(userSaved);
    }

    @Override
    public UserResponseDTO updateActive(Long userId, UpdateActiveUserRequestDTO data) {
        Long businessId = businessContextService.getBusiness(data.businessId()).getId();
        User user = repository.findByIdAndBusiness_id(userId, businessId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found", "user", userId));
        user.inactiveUser(data.active());
        User userSaved = repository.save(user);
        return new UserResponseDTO(userSaved);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = repository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User Not Found", "user", userId));
        repository.delete(user);
    }
}
