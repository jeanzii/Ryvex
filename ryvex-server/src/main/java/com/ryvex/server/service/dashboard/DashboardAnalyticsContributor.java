package com.ryvex.server.service.dashboard;

import com.ryvex.server.model.User;

public interface DashboardAnalyticsContributor {

    DashboardAnalyticsContribution contribute(
            User user
    );
}