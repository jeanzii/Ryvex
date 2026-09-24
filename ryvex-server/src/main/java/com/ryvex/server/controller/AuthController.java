package com.ryvex.server.controller;

import com.ryvex.server.dto.auth.RegisterRequest;
import com.ryvex.server.dto.auth.RegisterResponse;
import com.ryvex.server.dto.auth.LoginRequest;
import com.ryvex.server.dto.auth.LoginResponse;
import com.ryvex.server.service.AuthService;
import com.ryvex.server.dto.auth.MeResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse register(
            @Valid @RequestBody RegisterRequest request
    ) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(
            @Valid @RequestBody LoginRequest request
    ) {
        return authService.login(request);
    }

    @GetMapping("/me")
    public MeResponse me(
            @AuthenticationPrincipal Jwt jwt
    ) {
        return new MeResponse(
                jwt.getSubject(),
                jwt.getClaimAsString("role")
        );
    }
}