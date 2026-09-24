package com.ryvex.client.dto.dashboard;

import java.math.BigDecimal;

public record DashboardStatsResponse(
        int pcBuilds,
        int marketplaceListings,
        int activeFlips,
        BigDecimal totalProfit
) {
}