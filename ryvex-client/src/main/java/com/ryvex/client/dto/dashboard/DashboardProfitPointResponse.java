package com.ryvex.client.dto.dashboard;

import java.math.BigDecimal;

public record DashboardProfitPointResponse(
        String occurredAt,
        BigDecimal totalProfit
) {
}