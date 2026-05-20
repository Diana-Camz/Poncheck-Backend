package com.poncheck.service.impl;

import com.poncheck.dto.request.auth.LoginRequestDTO;
import com.poncheck.dto.response.auth.AuthResponseDTO;
import com.poncheck.entity.User;
import com.poncheck.repository.UserRepository;
import com.poncheck.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @Override
    public AuthResponseDTO login(LoginRequestDTO data) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            data.username(),
                            data.password()
                    )
            );
        } catch (BadCredentialsException exception) {
            throw new BadCredentialsException("Invalid Credentials");
        }

        User user = userRepository.findByUsername(data.username())
                .orElseThrow(() -> new RuntimeException("User Not Found"));
        return new AuthResponseDTO(user);
    }
}
