package com.ryvex.server.service.dashboard;

public interface DashboardStatsContributor {

    DashboardStatsContribution contribute(
            DashboardUserContext user
    );
}