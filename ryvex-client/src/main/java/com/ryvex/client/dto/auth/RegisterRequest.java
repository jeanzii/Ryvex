package com.ryvex.client.dto.auth;

public record RegisterRequest(
        String username,
        String email,
        String password
) {
}