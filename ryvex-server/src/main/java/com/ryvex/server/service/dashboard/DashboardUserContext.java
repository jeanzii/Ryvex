package com.ryvex.server.service.dashboard;

public record DashboardUserContext(
        Long id,
        String username,
        String role
) {
}