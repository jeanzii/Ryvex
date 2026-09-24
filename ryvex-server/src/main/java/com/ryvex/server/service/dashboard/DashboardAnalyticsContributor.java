package com.ryvex.server.service.dashboard;

public interface DashboardAnalyticsContributor {

    DashboardAnalyticsContribution contribute(
            DashboardUserContext user
    );
}