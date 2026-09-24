package com.ryvex.server.dto.dashboard;

import java.math.BigDecimal;

public record DashboardStatsResponse(
        int pcBuilds,
        int marketplaceListings,
        int activeFlips,
        BigDecimal totalProfit
) {
}