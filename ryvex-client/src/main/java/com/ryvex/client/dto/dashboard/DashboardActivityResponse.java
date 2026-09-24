package com.ryvex.client.dto.dashboard;

public record DashboardActivityResponse(
        String type,
        String message,
        String occurredAt
) {
}