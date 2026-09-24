package com.ryvex.client.dto.auth;

public record LoginRequest(
        String login,
        String password
) {
}