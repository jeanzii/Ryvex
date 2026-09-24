package com.ryvex.server.service.dashboard;

import com.ryvex.server.dto.dashboard.DashboardActivityResponse;

import java.util.List;

public interface DashboardActivityContributor {

    List<DashboardActivityResponse> contribute(
            DashboardUserContext user
    );
}