package com.ryvex.server.service;

import com.ryvex.server.dto.dashboard.DashboardResponse;
import com.ryvex.server.dto.dashboard.DashboardStatsResponse;
import com.ryvex.server.model.User;
import com.ryvex.server.repository.UserRepository;
import com.ryvex.server.service.dashboard.DashboardStatsService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DashboardService {

    private final UserRepository userRepository;
    private final DashboardStatsService dashboardStatsService;

    public DashboardService(
            UserRepository userRepository,
            DashboardStatsService dashboardStatsService
    ) {

        this.userRepository =
                userRepository;

        this.dashboardStatsService =
                dashboardStatsService;
    }

    public DashboardResponse getDashboard(
            String username
    ) {

        User user =
                userRepository
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

        DashboardStatsResponse stats =
                dashboardStatsService
                        .getStats(
                                user
                        );

        return new DashboardResponse(
                user.getUsername(),
                user.getRole().name(),
                stats,
                List.of()
        );
    }
}