package com.ryvex.server.dto.dashboard;

import java.util.List;

public record DashboardResponse(
        String username,
        String role,
        DashboardStatsResponse stats,
        List<DashboardActivityResponse> recentActivity
) {
}