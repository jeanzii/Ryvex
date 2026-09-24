package com.ryvex.server.service.dashboard;

import com.ryvex.server.dto.dashboard.DashboardActivityResponse;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class DashboardActivityService {

    private static final int MAX_RECENT_ACTIVITY = 10;

    private final ObjectProvider<DashboardActivityContributor>
            contributors;

    public DashboardActivityService(
            ObjectProvider<DashboardActivityContributor> contributors
    ) {

        this.contributors =
                contributors;
    }

    public List<DashboardActivityResponse> getRecentActivity(
            DashboardUserContext user
    ) {

        return contributors
                .orderedStream()
                .flatMap(
                        contributor -> {

                            List<DashboardActivityResponse> activity =
                                    contributor.contribute(
                                            user
                                    );

                            if (activity == null) {
                                return List
                                        .<DashboardActivityResponse>of()
                                        .stream();
                            }

                            return activity.stream();
                        }
                )
                .filter(
                        Objects::nonNull
                )
                .filter(
                        activity ->
                                activity.occurredAt()
                                        != null
                )
                .sorted(
                        Comparator.comparing(
                                DashboardActivityResponse::occurredAt
                        ).reversed()
                )
                .limit(
                        MAX_RECENT_ACTIVITY
                )
                .toList();
    }
}