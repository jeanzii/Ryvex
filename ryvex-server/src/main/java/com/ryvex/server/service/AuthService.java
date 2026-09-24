package com.ryvex.server.service;

import com.ryvex.server.dto.auth.RegisterRequest;
import com.ryvex.server.dto.auth.RegisterResponse;
import com.ryvex.server.dto.auth.LoginRequest;
import com.ryvex.server.dto.auth.LoginResponse;
import com.ryvex.server.model.User;
import com.ryvex.server.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public RegisterResponse register(RegisterRequest request) {

        String username = request.username().trim();
        String email = request.email().trim().toLowerCase();

        if (userRepository.existsByUsernameIgnoreCase(username)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Username is already in use"
            );
        }

        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email is already in use"
            );
        }

        String passwordHash =
                passwordEncoder.encode(request.password());

        User user = new User(
                username,
                email,
                passwordHash
        );

        User savedUser = userRepository.save(user);

        return new RegisterResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getRole().name(),
                savedUser.getCreatedAt()
        );
    }

    public LoginResponse login(LoginRequest request) {

        String login = request.login().trim();

        User user = userRepository
                .findByUsernameIgnoreCase(login)
                .orElseGet(() ->
                        userRepository
                                .findByEmailIgnoreCase(login.toLowerCase())
                                .orElseThrow(() ->
                                        new ResponseStatusException(
                                                HttpStatus.UNAUTHORIZED,
                                                "Invalid login credentials"
                                        )
                                )
                );

        if (!passwordEncoder.matches(
                request.password(),
                user.getPasswordHash()
        )) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid login credentials"
            );
        }

        JwtService.TokenResult token =
                jwtService.createAccessToken(user);

        return new LoginResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().name(),
                token.token(),
                "Bearer",
                token.expiresAt()
        );
    }

}