package com.ryvex.server.dto.auth;

import java.time.LocalDateTime;

public record RegisterResponse(
        Long id,
        String username,
        String email,
        String role,
        LocalDateTime createdAt
) {
}