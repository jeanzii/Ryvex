package com.ryvex.client.dto.auth;

public record LoginResponse(
        Long id,
        String username,
        String email,
        String role,
        String accessToken,
        String refreshToken,
        String tokenType,
        String expiresAt
) {
}