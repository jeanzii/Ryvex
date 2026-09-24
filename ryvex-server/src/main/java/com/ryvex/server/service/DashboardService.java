package com.ryvex.server.service;

import com.ryvex.server.dto.dashboard.DashboardActivityResponse;
import com.ryvex.server.dto.dashboard.DashboardAnalyticsResponse;
import com.ryvex.server.dto.dashboard.DashboardResponse;
import com.ryvex.server.dto.dashboard.DashboardStatsResponse;
import com.ryvex.server.model.User;
import com.ryvex.server.repository.UserRepository;
import com.ryvex.server.service.dashboard.DashboardActivityService;
import com.ryvex.server.service.dashboard.DashboardAnalyticsService;
import com.ryvex.server.service.dashboard.DashboardStatsService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DashboardService {

    private final UserRepository userRepository;
    private final DashboardStatsService dashboardStatsService;
    private final DashboardAnalyticsService dashboardAnalyticsService;
    private final DashboardActivityService dashboardActivityService;

    public DashboardService(
            UserRepository userRepository,
            DashboardStatsService dashboardStatsService,
            DashboardAnalyticsService dashboardAnalyticsService,
            DashboardActivityService dashboardActivityService
    ) {

        this.userRepository =
                userRepository;

        this.dashboardStatsService =
                dashboardStatsService;

        this.dashboardAnalyticsService =
                dashboardAnalyticsService;

        this.dashboardActivityService =
                dashboardActivityService;
    }

    public DashboardResponse getDashboard(
            String username
    ) {

        User user =
                findUser(
                        username
                );

        DashboardStatsResponse stats =
                dashboardStatsService
                        .getStats(
                                user
                        );

        List<DashboardActivityResponse> recentActivity =
                dashboardActivityService
                        .getRecentActivity(
                                user
                        );

        return new DashboardResponse(
                user.getUsername(),
                user.getRole().name(),
                stats,
                recentActivity
        );
    }

    public DashboardAnalyticsResponse getAnalytics(
            String username
    ) {

        User user =
                findUser(
                        username
                );

        return dashboardAnalyticsService
                .getAnalytics(
                        user
                );
    }

    private User findUser(
            String username
    ) {

        return userRepository
                .findByUsernameIgnoreCase(
                        username
                )
                .orElseThrow(
                        () ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "User not found."
                                )
                );
    }
}