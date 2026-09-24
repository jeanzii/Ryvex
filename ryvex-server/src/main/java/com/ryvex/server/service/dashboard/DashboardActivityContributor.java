package com.ryvex.server.service.dashboard;

import com.ryvex.server.dto.dashboard.DashboardActivityResponse;
import com.ryvex.server.model.User;

import java.util.List;

public interface DashboardActivityContributor {

    List<DashboardActivityResponse> contribute(
            User user
    );
}