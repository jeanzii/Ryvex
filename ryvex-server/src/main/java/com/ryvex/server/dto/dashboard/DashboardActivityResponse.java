package com.ryvex.server.dto.dashboard;

import java.time.Instant;

public record DashboardActivityResponse(
        String type,
        String message,
        Instant occurredAt
) {
}