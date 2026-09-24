package com.ryvex.client.dto.auth;

public record MeResponse(
        String username,
        String role
) {
}