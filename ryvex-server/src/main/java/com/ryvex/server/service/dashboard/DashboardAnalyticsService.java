package com.ryvex.server.service.dashboard;

import com.ryvex.server.dto.dashboard.DashboardAnalyticsResponse;
import com.ryvex.server.dto.dashboard.DashboardProfitPointResponse;
import com.ryvex.server.model.User;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class DashboardAnalyticsService {

    private final ObjectProvider<DashboardAnalyticsContributor>
            contributors;

    public DashboardAnalyticsService(
            ObjectProvider<DashboardAnalyticsContributor> contributors
    ) {

        this.contributors =
                contributors;
    }

    public DashboardAnalyticsResponse getAnalytics(
            User user
    ) {

        List<DashboardProfitPointResponse> profitHistory =
                contributors
                        .orderedStream()
                        .map(
                                contributor ->
                                        contributor.contribute(
                                                user
                                        )
                        )
                        .filter(
                                Objects::nonNull
                        )
                        .flatMap(
                                contribution ->
                                        contribution
                                                .profitHistory()
                                                .stream()
                        )
                        .sorted(
                                Comparator.comparing(
                                        DashboardProfitPointResponse::occurredAt
                                )
                        )
                        .toList();

        return new DashboardAnalyticsResponse(
                profitHistory
        );
    }
}