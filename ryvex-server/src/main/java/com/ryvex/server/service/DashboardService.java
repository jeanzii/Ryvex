package com.ryvex.server.service;

import com.ryvex.server.dto.dashboard.DashboardResponse;
import com.ryvex.server.dto.dashboard.DashboardStatsResponse;
import com.ryvex.server.model.User;
import com.ryvex.server.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DashboardService {

    private final UserRepository userRepository;

    public DashboardService(
            UserRepository userRepository
    ) {

        this.userRepository =
                userRepository;
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

        /*
         * These values intentionally remain zero
         * until their respective Ryvex modules
         * are implemented.
         */
        DashboardStatsResponse stats =
                new DashboardStatsResponse(
                        0,
                        0,
                        0,
                        BigDecimal.ZERO
                                .setScale(2)
                );

        return new DashboardResponse(
                user.getUsername(),
                user.getRole().name(),
                stats,
                List.of()
        );
    }
}