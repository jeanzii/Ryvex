package com.ryvex.server.service.dashboard;

import java.math.BigDecimal;

public record DashboardStatsContribution(
        int pcBuilds,
        int marketplaceListings,
        int activeFlips,
        BigDecimal totalProfit
) {

    public DashboardStatsContribution {

        if (totalProfit == null) {
            totalProfit = BigDecimal.ZERO;
        }
    }

    public static DashboardStatsContribution empty() {

        return new DashboardStatsContribution(
                0,
                0,
                0,
                BigDecimal.ZERO
        );
    }

    public DashboardStatsContribution add(
            DashboardStatsContribution other
    ) {

        return new DashboardStatsContribution(
                pcBuilds + other.pcBuilds(),
                marketplaceListings + other.marketplaceListings(),
                activeFlips + other.activeFlips(),
                totalProfit.add(
                        other.totalProfit()
                )
        );
    }
}