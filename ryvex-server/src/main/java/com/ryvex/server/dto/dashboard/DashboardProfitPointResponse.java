package com.ryvex.server.dto.dashboard;

import java.math.BigDecimal;
import java.time.Instant;

public record DashboardProfitPointResponse(
        Instant occurredAt,
        BigDecimal totalProfit
) {
}