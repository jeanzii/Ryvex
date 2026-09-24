package com.ryvex.client.dto.dashboard;

import java.util.List;

public record DashboardAnalyticsResponse(
        List<DashboardProfitPointResponse> profitHistory
) {
}