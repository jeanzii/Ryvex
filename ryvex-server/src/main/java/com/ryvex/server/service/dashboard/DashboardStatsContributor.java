package com.ryvex.server.service.dashboard;

import com.ryvex.server.model.User;

public interface DashboardStatsContributor {

    DashboardStatsContribution contribute(
            User user
    );
}