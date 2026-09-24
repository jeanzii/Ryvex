package com.ryvex.server.dto.dashboard;

import java.util.List;

public record DashboardAnalyticsResponse(
        List<DashboardProfitPointResponse> profitHistory
) {
}