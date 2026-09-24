package com.ryvex.server.controller;

import com.ryvex.server.dto.dashboard.DashboardResponse;
import com.ryvex.server.service.DashboardService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ryvex.server.dto.dashboard.DashboardAnalyticsResponse;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(
            DashboardService dashboardService
    ) {

        this.dashboardService =
                dashboardService;
    }

    @GetMapping
    public DashboardResponse getDashboard(
            @AuthenticationPrincipal Jwt jwt
    ) {

        return dashboardService.getDashboard(
                jwt.getSubject()
        );
    }

    @GetMapping("/analytics")
    public DashboardAnalyticsResponse getAnalytics(
            @AuthenticationPrincipal Jwt jwt
    ) {

        return dashboardService.getAnalytics(
                jwt.getSubject()
        );
    }
}