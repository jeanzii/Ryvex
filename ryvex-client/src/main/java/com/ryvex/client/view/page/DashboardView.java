package com.ryvex.client.view.page;

import com.ryvex.client.auth.AuthSession;
import com.ryvex.client.dto.dashboard.DashboardActivityResponse;
import com.ryvex.client.dto.dashboard.DashboardResponse;
import com.ryvex.client.dto.dashboard.DashboardStatsResponse;
import com.ryvex.client.service.ApiException;
import com.ryvex.client.service.ApiService;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

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

        getStyleClass().add(
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

        getChildren().addAll(
                title,
                welcomeSubtitle,
                statistics,
                mainRow,
                bottomRow
        );

        showRecentActivityLoading();

        loadDashboard();
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