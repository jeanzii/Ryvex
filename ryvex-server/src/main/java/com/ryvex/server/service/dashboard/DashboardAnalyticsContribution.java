package com.ryvex.server.service.dashboard;

import com.ryvex.server.dto.dashboard.DashboardProfitPointResponse;

import java.util.List;

public record DashboardAnalyticsContribution(
        List<DashboardProfitPointResponse> profitHistory
) {

    public DashboardAnalyticsContribution {

        if (profitHistory == null) {
            profitHistory = List.of();
        }
    }

    public static DashboardAnalyticsContribution empty() {

        return new DashboardAnalyticsContribution(
                List.of()
        );
    }
}