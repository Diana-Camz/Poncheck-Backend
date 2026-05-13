package com.poncheck.controller;

import com.poncheck.dto.request.user.CreateUserRequestDTO;
import com.poncheck.dto.request.user.UpdateActiveUserRequestDTO;
import com.poncheck.dto.request.user.UpdateUserRequestDTO;
import com.poncheck.dto.response.user.UserResponseDTO;
import com.poncheck.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService service;

    //Retrieves all users
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getUsers(){
        List<UserResponseDTO> users = service.getUsers();
        return ResponseEntity.ok(users);
    }

    //Retrieves all active users
    @GetMapping("/active")
    public ResponseEntity<List<UserResponseDTO>> getActiveUsers(){
        List<UserResponseDTO> users = service.getActiveUsers();
        return ResponseEntity.ok(users);
    }

    //Retrieves all inactive users
    @GetMapping("/inactive")
    public ResponseEntity<List<UserResponseDTO>> getInactiveUsers(){
        List<UserResponseDTO> users = service.getInactiveUsers();
        return ResponseEntity.ok(users);
    }

    //Retrieves a user by its ID
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id){
        UserResponseDTO user = service.getUserById(id);
        return ResponseEntity.ok(user);
    }

    //Creates new User
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody CreateUserRequestDTO userData){
        UserResponseDTO user = service.createUser(userData);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    //Updates user fields by its ID
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequestDTO userData){
        UserResponseDTO user = service.updateUser(id, userData);
        return ResponseEntity.ok(user);
    }


    //Updates the user active status (logical deletion)
    @PatchMapping("/{id}/active")
    public ResponseEntity<UserResponseDTO> updateActive(@PathVariable Long id, @RequestBody UpdateActiveUserRequestDTO status){
        UserResponseDTO user = service.updateActive(id, status);
        return ResponseEntity.ok(user);
    }

    //Deletes a user (physical deletion)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
