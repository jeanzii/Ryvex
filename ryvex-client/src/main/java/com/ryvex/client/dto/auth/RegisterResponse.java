package com.ryvex.client.dto.auth;

public record RegisterResponse(
        Long id,
        String username,
        String email,
        String role,
        String createdAt
) {
}