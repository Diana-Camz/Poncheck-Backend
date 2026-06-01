package com.poncheck.service.impl;

import com.poncheck.dto.request.auth.AuthLoginRequestDTO;
import com.poncheck.dto.request.auth.AuthRegisterRequestDTO;
import com.poncheck.dto.response.auth.AuthResponseDTO;
import com.poncheck.dto.response.token.TokenResponseDTO;
import com.poncheck.entity.Business;
import com.poncheck.entity.User;
import com.poncheck.enums.Role;
import com.poncheck.exception.DuplicateFieldException;
import com.poncheck.exception.ResourceNotFoundException;
import com.poncheck.repository.BusinessRepository;
import com.poncheck.repository.UserRepository;
import com.poncheck.service.AuthService;
import com.poncheck.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final BusinessRepository businessRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResponseDTO login(AuthLoginRequestDTO data) {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            data.username(),
                            data.password()
                    )
            );

        User user = userRepository.findByUsername(data.username())
                .orElseThrow(() -> new RuntimeException("User Not Found"));
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        user.setRefreshToken(refreshToken);
        userRepository.save(user);
        return new AuthResponseDTO(user, jwtToken, refreshToken);
    }

    //Creates new User
    @Override
    public AuthResponseDTO register(AuthRegisterRequestDTO userData) {
        if(userRepository.existsUserByUsername(userData.username())){
            throw new DuplicateFieldException("A user with this username already exists");
        }

        String hashedPassword = passwordEncoder.encode(userData.password());
        Business business = null;
        if(userData.businessId() != null){
            business = businessRepository.findById(userData.businessId())
                    .orElseThrow(() -> new ResourceNotFoundException("Business Not Found", "business", userData.businessId()));
        }

        User user = new User(
                userData.name(),
                userData.username(),
                hashedPassword,
                userData.role(),
                business
        );

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        user.setRefreshToken(refreshToken);
        //User userSaved = userRepository.save(user);
        userRepository.save(user);
        return new AuthResponseDTO(user, jwtToken, refreshToken);
    }

    public TokenResponseDTO refreshToken(String authHeader){
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            throw new IllegalArgumentException("Invalid Bearer token");
        }

        String refreshToken = authHeader.substring(7);
        String username = jwtService.extractUsername(refreshToken);

        if(username == null){
            throw new IllegalArgumentException("Invalid Refresh Token");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " Not Found"));

        if(!jwtService.isTokenValid(refreshToken, user)){
            throw new IllegalArgumentException("Invalid Refresh Token");
        }

        String accessToken = jwtService.generateToken(user);
        return new TokenResponseDTO(accessToken, refreshToken);

    }

    public void logout(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Token");
        }

        String jwtToken = token.substring(7);

        final String username = jwtService.extractUsername(jwtToken);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        if(!jwtService.isTokenValid(jwtToken, user)){
            throw new IllegalArgumentException("Invalid Token");
        }

        user.setRefreshToken(null);
        userRepository.save(user);

    }
}
