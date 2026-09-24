package com.ryvex.client.dto.auth;

public record TokenResponse(
        String accessToken,
        String refreshToken,
        String tokenType,
        String expiresAt
) {
}