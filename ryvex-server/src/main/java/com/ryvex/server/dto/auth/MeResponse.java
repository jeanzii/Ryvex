package com.ryvex.server.dto.auth;

public record MeResponse(
        String username,
        String role
) {
}