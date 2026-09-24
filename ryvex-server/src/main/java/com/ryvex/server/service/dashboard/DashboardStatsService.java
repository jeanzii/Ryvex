package com.ryvex.server.service.dashboard;

import com.ryvex.server.dto.dashboard.DashboardStatsResponse;
import com.ryvex.server.model.User;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.util.Objects;

@Service
public class DashboardStatsService {

    private final ObjectProvider<DashboardStatsContributor>
            contributors;

    public DashboardStatsService(
            ObjectProvider<DashboardStatsContributor> contributors
    ) {

        this.contributors =
                contributors;
    }

    public DashboardStatsResponse getStats(
            User user
    ) {

        DashboardStatsContribution combined =
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
                        .reduce(
                                DashboardStatsContribution.empty(),
                                DashboardStatsContribution::add
                        );

        return new DashboardStatsResponse(
                combined.pcBuilds(),
                combined.marketplaceListings(),
                combined.activeFlips(),
                combined.totalProfit()
                        .setScale(
                                2,
                                RoundingMode.HALF_UP
                        )
        );
    }
}