package com.ryvex.server.dto.auth;

import java.time.Instant;

public record LoginResponse(
        Long id,
        String username,
        String email,
        String role,
        String accessToken,
        String tokenType,
        Instant expiresAt
) {
}