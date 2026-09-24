package com.ryvex.client.dto.auth;

public record LogoutRequest(
        String refreshToken
) {
}