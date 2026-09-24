package com.ryvex.client.view.page;

import com.ryvex.client.auth.AuthSession;
import com.ryvex.client.dto.dashboard.DashboardActivityResponse;
import com.ryvex.client.dto.dashboard.DashboardAnalyticsResponse;
import com.ryvex.client.dto.dashboard.DashboardProfitPointResponse;
import com.ryvex.client.dto.dashboard.DashboardResponse;
import com.ryvex.client.dto.dashboard.DashboardStatsResponse;
import com.ryvex.client.service.ApiException;
import com.ryvex.client.service.ApiService;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;

import java.math.RoundingMode;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DashboardView extends VBox {

    private final ApiService apiService;
    private final AuthSession authSession;
    private final Runnable onSessionExpired;

    private final Runnable onPcBuilder;
    private final Runnable onMarketplace;
    private final Runnable onFlipping;
    private final Runnable onFinance;

    private final Label welcomeSubtitle;

    private final Label pcBuildsValue;
    private final Label marketplaceValue;
    private final Label flipsValue;
    private final Label profitValue;

    private final Label accountStatus;

    private final VBox recentActivityContent;
    private final StackPane analyticsContent;

    private final DateTimeFormatter chartDateFormatter =
            DateTimeFormatter.ofPattern(
                    "dd MMM"
            );

    public DashboardView(
            AuthSession authSession,
            Runnable onSessionExpired,
            Runnable onPcBuilder,
            Runnable onMarketplace,
            Runnable onFlipping,
            Runnable onFinance
    ) {

        this.apiService =
                new ApiService();

        this.authSession =
                authSession;

        this.onSessionExpired =
                onSessionExpired;

        this.onPcBuilder =
                onPcBuilder;

        this.onMarketplace =
                onMarketplace;

        this.onFlipping =
                onFlipping;

        this.onFinance =
                onFinance;

        VBox dashboardContent =
                new VBox();

        dashboardContent.getStyleClass().add(
                "content-area"
        );

        Label title =
                new Label(
                        "Dashboard"
                );

        title.getStyleClass().add(
                "page-title"
        );

        welcomeSubtitle =
                new Label(
                        "Loading your dashboard..."
                );

        welcomeSubtitle
                .getStyleClass()
                .add(
                        "page-subtitle"
                );

        pcBuildsValue =
                createStatisticValue();

        marketplaceValue =
                createStatisticValue();

        flipsValue =
                createStatisticValue();

        profitValue =
                createStatisticValue();

        GridPane statistics =
                createStatisticsGrid();

        analyticsContent =
                new StackPane();

        VBox analyticsCard =
                createAnalyticsCard();

        HBox mainRow =
                new HBox(
                        20
                );

        VBox quickActions =
                createQuickActionsCard();

        recentActivityContent =
                new VBox(
                        8
                );

        VBox recentActivity =
                createRecentActivityCard();

        HBox.setHgrow(
                quickActions,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                recentActivity,
                Priority.ALWAYS
        );

        quickActions.setMaxWidth(
                Double.MAX_VALUE
        );

        recentActivity.setMaxWidth(
                Double.MAX_VALUE
        );

        mainRow.getChildren().addAll(
                quickActions,
                recentActivity
        );

        HBox bottomRow =
                new HBox(
                        20
                );

        accountStatus =
                new Label(
                        "Loading account..."
                );

        accountStatus
                .getStyleClass()
                .add(
                        "card-text"
                );

        VBox accountCard =
                createAccountCard();

        VBox serviceCard =
                createServiceCard();

        HBox.setHgrow(
                accountCard,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                serviceCard,
                Priority.ALWAYS
        );

        accountCard.setMaxWidth(
                Double.MAX_VALUE
        );

        serviceCard.setMaxWidth(
                Double.MAX_VALUE
        );

        bottomRow.getChildren().addAll(
                accountCard,
                serviceCard
        );

        dashboardContent.getChildren().addAll(
                title,
                welcomeSubtitle,
                statistics,
                analyticsCard,
                mainRow,
                bottomRow
        );

        ScrollPane scrollPane =
                new ScrollPane(
                        dashboardContent
                );

        scrollPane.setFitToWidth(
                true
        );

        scrollPane.setHbarPolicy(
                ScrollPane.ScrollBarPolicy.NEVER
        );

        scrollPane.setVbarPolicy(
                ScrollPane.ScrollBarPolicy.AS_NEEDED
        );

        scrollPane.setPannable(
                false
        );

        scrollPane.getStyleClass().add(
                "dashboard-scroll-pane"
        );

        scrollPane.setMaxSize(
                Double.MAX_VALUE,
                Double.MAX_VALUE
        );

        getChildren().add(
                scrollPane
        );

        VBox.setVgrow(
                scrollPane,
                Priority.ALWAYS
        );

        showRecentActivityLoading();

        loadDashboard();
        loadAnalytics();
    }

    private Label createStatisticValue() {

        Label value =
                new Label(
                        "—"
                );

        value.getStyleClass().add(
                "dashboard-stat-value"
        );

        return value;
    }

    private GridPane createStatisticsGrid() {

        GridPane grid =
                new GridPane();

        grid.setHgap(
                20
        );

        grid.setVgap(
                20
        );

        VBox buildsCard =
                createStatisticCard(
                        "PC Builds",
                        pcBuildsValue,
                        "Saved configurations"
                );

        VBox marketplaceCard =
                createStatisticCard(
                        "Marketplace",
                        marketplaceValue,
                        "Active listings"
                );

        VBox flipsCard =
                createStatisticCard(
                        "PC Flips",
                        flipsValue,
                        "Active projects"
                );

        VBox profitCard =
                createStatisticCard(
                        "Total Profit",
                        profitValue,
                        "Tracked flipping profit"
                );

        grid.add(
                buildsCard,
                0,
                0
        );

        grid.add(
                marketplaceCard,
                1,
                0
        );

        grid.add(
                flipsCard,
                2,
                0
        );

        grid.add(
                profitCard,
                3,
                0
        );

        GridPane.setHgrow(
                buildsCard,
                Priority.ALWAYS
        );

        GridPane.setHgrow(
                marketplaceCard,
                Priority.ALWAYS
        );

        GridPane.setHgrow(
                flipsCard,
                Priority.ALWAYS
        );

        GridPane.setHgrow(
                profitCard,
                Priority.ALWAYS
        );

        buildsCard.setMaxWidth(
                Double.MAX_VALUE
        );

        marketplaceCard.setMaxWidth(
                Double.MAX_VALUE
        );

        flipsCard.setMaxWidth(
                Double.MAX_VALUE
        );

        profitCard.setMaxWidth(
                Double.MAX_VALUE
        );

        return grid;
    }

    private VBox createStatisticCard(
            String titleText,
            Label value,
            String descriptionText
    ) {

        Label title =
                new Label(
                        titleText
                );

        title.getStyleClass().add(
                "dashboard-stat-title"
        );

        Label description =
                new Label(
                        descriptionText
                );

        description
                .getStyleClass()
                .add(
                        "dashboard-stat-description"
                );

        VBox card =
                new VBox(
                        8,
                        title,
                        value,
                        description
                );

        card.getStyleClass().addAll(
                "card",
                "dashboard-stat-card"
        );

        return card;
    }

    private VBox createQuickActionsCard() {

        VBox card =
                new VBox(
                        14
                );

        card.getStyleClass().add(
                "card"
        );

        Label title =
                new Label(
                        "Quick Actions"
                );

        title.getStyleClass().add(
                "card-title"
        );

        Button createBuild =
                createQuickActionButton(
                        "Create PC Build",
                        onPcBuilder
                );

        Button browseMarketplace =
                createQuickActionButton(
                        "Browse Marketplace",
                        onMarketplace
                );

        Button addFlip =
                createQuickActionButton(
                        "Track PC Flip",
                        onFlipping
                );

        Button openFinance =
                createQuickActionButton(
                        "Open Finance",
                        onFinance
                );

        card.getChildren().addAll(
                title,
                createBuild,
                browseMarketplace,
                addFlip,
                openFinance
        );

        return card;
    }

    private Button createQuickActionButton(
            String text,
            Runnable action
    ) {

        Button button =
                new Button(
                        text
                );

        button.setMaxWidth(
                Double.MAX_VALUE
        );

        button.getStyleClass().add(
                "dashboard-action-button"
        );

        button.setOnAction(
                event ->
                        action.run()
        );

        return button;
    }

    private VBox createRecentActivityCard() {

        VBox card =
                new VBox(
                        12
                );

        card.getStyleClass().add(
                "card"
        );

        Label title =
                new Label(
                        "Recent Activity"
                );

        title.getStyleClass().add(
                "card-title"
        );

        card.getChildren().addAll(
                title,
                recentActivityContent
        );

        return card;
    }

    private VBox createAccountCard() {

        VBox card =
                new VBox(
                        10
                );

        card.getStyleClass().add(
                "card"
        );

        Label title =
                new Label(
                        "Your Account"
                );

        title.getStyleClass().add(
                "card-title"
        );

        card.getChildren().addAll(
                title,
                accountStatus
        );

        return card;
    }

    private VBox createServiceCard() {

        VBox card =
                new VBox(
                        10
                );

        card.getStyleClass().add(
                "card"
        );

        Label title =
                new Label(
                        "Ryvex Services"
                );

        title.getStyleClass().add(
                "card-title"
        );

        Label status =
                new Label(
                        "● Checking services..."
                );

        status.getStyleClass().add(
                "status-checking"
        );

        card.getChildren().addAll(
                title,
                status
        );

        checkServerStatus(
                status
        );

        return card;
    }

    private VBox createAnalyticsCard() {

        VBox card =
                new VBox(
                        12
                );

        card.getStyleClass().add(
                "card"
        );

        Label title =
                new Label(
                        "Profit Analytics"
                );

        title.getStyleClass().add(
                "card-title"
        );

        Label subtitle =
                new Label(
                        "Your tracked PC flipping profit over time."
                );

        subtitle.getStyleClass().add(
                "card-text"
        );

        showAnalyticsLoading();

        card.getChildren().addAll(
                title,
                subtitle,
                analyticsContent
        );

        return card;
    }

    private void loadDashboard() {

        Thread.ofVirtual().start(
                () -> {

                    try {

                        DashboardResponse dashboard =
                                authSession
                                        .executeAuthenticated(
                                                apiService::getDashboard
                                        );

                        Platform.runLater(
                                () ->
                                        applyDashboard(
                                                dashboard
                                        )
                        );

                    } catch (ApiException e) {

                        if (
                                e.getStatusCode()
                                        == 401
                        ) {

                            authSession.clear();

                            Platform.runLater(
                                    onSessionExpired
                            );

                            return;
                        }

                        Platform.runLater(
                                () ->
                                        showDashboardError(
                                                e.getMessage()
                                        )
                        );
                    }
                }
        );
    }

    private void loadAnalytics() {

        Thread.ofVirtual().start(
                () -> {

                    try {

                        DashboardAnalyticsResponse analytics =
                                authSession
                                        .executeAuthenticated(
                                                apiService::getDashboardAnalytics
                                        );

                        Platform.runLater(
                                () ->
                                        renderAnalytics(
                                                analytics
                                        )
                        );

                    } catch (ApiException e) {

                        if (
                                e.getStatusCode()
                                        == 401
                        ) {

                            authSession.clear();

                            Platform.runLater(
                                    onSessionExpired
                            );

                            return;
                        }

                        Platform.runLater(
                                this::showAnalyticsError
                        );
                    }
                }
        );
    }

    private void applyDashboard(
            DashboardResponse dashboard
    ) {

        welcomeSubtitle.setText(
                "Welcome back, "
                        + dashboard.username()
                        + "."
        );

        accountStatus.setText(
                dashboard.username()
                        + " • "
                        + dashboard.role()
        );

        DashboardStatsResponse stats =
                dashboard.stats();

        pcBuildsValue.setText(
                Integer.toString(
                        stats.pcBuilds()
                )
        );

        marketplaceValue.setText(
                Integer.toString(
                        stats.marketplaceListings()
                )
        );

        flipsValue.setText(
                Integer.toString(
                        stats.activeFlips()
                )
        );

        String profit =
                stats.totalProfit()
                        .setScale(
                                2,
                                RoundingMode.HALF_UP
                        )
                        .toPlainString();

        profitValue.setText(
                "€" + profit
        );

        renderRecentActivity(
                dashboard.recentActivity()
        );
    }

    private void showDashboardError(
            String message
    ) {

        welcomeSubtitle.setText(
                "Unable to load your dashboard."
        );

        accountStatus.setText(
                message
        );

        pcBuildsValue.setText(
                "—"
        );

        marketplaceValue.setText(
                "—"
        );

        flipsValue.setText(
                "—"
        );

        profitValue.setText(
                "—"
        );

        recentActivityContent
                .getChildren()
                .clear();

        Label error =
                new Label(
                        "Unable to load recent activity."
                );

        error.getStyleClass().add(
                "card-text"
        );

        recentActivityContent
                .getChildren()
                .add(
                        error
                );
    }

    private void showRecentActivityLoading() {

        recentActivityContent
                .getChildren()
                .clear();

        Label loading =
                new Label(
                        "Loading recent activity..."
                );

        loading.getStyleClass().add(
                "card-text"
        );

        recentActivityContent
                .getChildren()
                .add(
                        loading
                );
    }

    private void renderRecentActivity(
            List<DashboardActivityResponse> activities
    ) {

        recentActivityContent
                .getChildren()
                .clear();

        if (
                activities == null
                        || activities.isEmpty()
        ) {

            Label emptyTitle =
                    new Label(
                            "No recent activity"
                    );

            emptyTitle
                    .getStyleClass()
                    .add(
                            "recent-activity-title"
                    );

            Label emptyDescription =
                    new Label(
                            "Your latest builds, listings, flips and transactions will appear here."
                    );

            emptyDescription
                    .setWrapText(
                            true
                    );

            emptyDescription
                    .getStyleClass()
                    .add(
                            "card-text"
                    );

            VBox emptyState =
                    new VBox(
                            6,
                            emptyTitle,
                            emptyDescription
                    );

            emptyState.setAlignment(
                    Pos.CENTER_LEFT
            );

            recentActivityContent
                    .getChildren()
                    .add(
                            emptyState
                    );

            return;
        }

        for (
                DashboardActivityResponse activity
                : activities
        ) {

            Label message =
                    new Label(
                            activity.message()
                    );

            message.getStyleClass().add(
                    "recent-activity-title"
            );

            Label timestamp =
                    new Label(
                            formatTimestamp(
                                    activity.occurredAt()
                            )
                    );

            timestamp.getStyleClass().add(
                    "card-text"
            );

            VBox row =
                    new VBox(
                            3,
                            message,
                            timestamp
                    );

            recentActivityContent
                    .getChildren()
                    .add(
                            row
                    );
        }
    }

    private void showAnalyticsLoading() {

        Label loading =
                new Label(
                        "Loading analytics..."
                );

        loading.getStyleClass().add(
                "card-text"
        );

        analyticsContent
                .getChildren()
                .setAll(
                        loading
                );
    }

    private void showAnalyticsError() {

        Label error =
                new Label(
                        "Unable to load analytics."
                );

        error.getStyleClass().add(
                "card-text"
        );

        analyticsContent
                .getChildren()
                .setAll(
                        error
                );
    }

    private void renderAnalytics(
            DashboardAnalyticsResponse analytics
    ) {

        if (
                analytics == null
                        || analytics.profitHistory() == null
                        || analytics.profitHistory().isEmpty()
        ) {

            VBox emptyState =
                    createAnalyticsEmptyState();

            analyticsContent
                    .getChildren()
                    .setAll(
                            emptyState
                    );

            return;
        }

        LineChart<String, Number> chart =
                createProfitChart(
                        analytics.profitHistory()
                );

        analyticsContent
                .getChildren()
                .setAll(
                        chart
                );
    }

    private VBox createAnalyticsEmptyState() {

        Label title =
                new Label(
                        "No profit data yet"
                );

        title.getStyleClass().add(
                "recent-activity-title"
        );

        Label description =
                new Label(
                        "Your profit chart will appear once PC flipping transactions are available."
                );

        description.setWrapText(
                true
        );

        description.getStyleClass().add(
                "card-text"
        );

        VBox emptyState =
                new VBox(
                        6,
                        title,
                        description
                );

        emptyState.getStyleClass().add(
                "dashboard-chart-empty"
        );

        return emptyState;
    }

    private LineChart<String, Number> createProfitChart(
            List<DashboardProfitPointResponse> points
    ) {

        CategoryAxis xAxis =
                new CategoryAxis();

        NumberAxis yAxis =
                new NumberAxis();

        xAxis.setLabel(
                "Date"
        );

        yAxis.setLabel(
                "Profit (€)"
        );

        LineChart<String, Number> chart =
                new LineChart<>(
                        xAxis,
                        yAxis
                );

        chart.setLegendVisible(
                false
        );

        chart.setAnimated(
                false
        );

        chart.setCreateSymbols(
                true
        );

        chart.setMinHeight(
                260
        );

        chart.setPrefHeight(
                300
        );

        chart.getStyleClass().add(
                "dashboard-profit-chart"
        );

        XYChart.Series<String, Number> series =
                new XYChart.Series<>();

        for (
                DashboardProfitPointResponse point
                : points
        ) {

            series.getData().add(
                    new XYChart.Data<>(
                            formatChartDate(
                                    point.occurredAt()
                            ),
                            point.totalProfit()
                    )
            );
        }

        chart.getData().add(
                series
        );

        return chart;
    }

    private String formatTimestamp(
            String timestamp
    ) {

        try {

            return Instant
                    .parse(
                            timestamp
                    )
                    .atZone(
                            ZoneId.systemDefault()
                    )
                    .format(
                            DateTimeFormatter.ofPattern(
                                    "dd MMM yyyy • HH:mm"
                            )
                    );

        } catch (Exception e) {

            return timestamp;
        }
    }

    private String formatChartDate(
            String occurredAt
    ) {

        try {

            return Instant
                    .parse(
                            occurredAt
                    )
                    .atZone(
                            ZoneId.systemDefault()
                    )
                    .format(
                            chartDateFormatter
                    );

        } catch (Exception e) {

            return occurredAt;
        }
    }

    private void checkServerStatus(
            Label statusLabel
    ) {

        Thread.ofVirtual().start(
                () -> {

                    boolean online =
                            apiService
                                    .isServerOnline();

                    Platform.runLater(
                            () -> {

                                statusLabel
                                        .getStyleClass()
                                        .removeAll(
                                                "status-checking",
                                                "status-online",
                                                "status-offline"
                                        );

                                if (online) {

                                    statusLabel.setText(
                                            "● Ryvex services online"
                                    );

                                    statusLabel
                                            .getStyleClass()
                                            .add(
                                                    "status-online"
                                            );

                                } else {

                                    statusLabel.setText(
                                            "● Ryvex services offline"
                                    );

                                    statusLabel
                                            .getStyleClass()
                                            .add(
                                                    "status-offline"
                                            );
                                }
                            }
                    );
                }
        );
    }
}