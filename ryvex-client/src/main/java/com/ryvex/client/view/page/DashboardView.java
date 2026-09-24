package com.ryvex.client.view.page;

import com.ryvex.client.auth.AuthSession;
import com.ryvex.client.dto.auth.MeResponse;
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

public class DashboardView extends VBox {

    private final ApiService apiService;
    private final AuthSession authSession;
    private final Runnable onSessionExpired;

    private final Runnable onPcBuilder;
    private final Runnable onMarketplace;
    private final Runnable onFlipping;
    private final Runnable onFinance;

    public DashboardView(
            AuthSession authSession,
            Runnable onSessionExpired,
            Runnable onPcBuilder,
            Runnable onMarketplace,
            Runnable onFlipping,
            Runnable onFinance
    ) {

        this.apiService = new ApiService();
        this.authSession = authSession;
        this.onSessionExpired = onSessionExpired;

        this.onPcBuilder = onPcBuilder;
        this.onMarketplace = onMarketplace;
        this.onFlipping = onFlipping;
        this.onFinance = onFinance;

        getStyleClass().add("content-area");

        Label title =
                new Label("Dashboard");

        title.getStyleClass().add(
                "page-title"
        );

        Label subtitle =
                new Label(
                        "Welcome back, "
                                + authSession.getUsername()
                                + "."
                );

        subtitle.getStyleClass().add(
                "page-subtitle"
        );

        GridPane statistics =
                createStatisticsGrid();

        HBox mainRow =
                new HBox(20);

        VBox quickActions =
                createQuickActionsCard();

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
                new HBox(20);

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
                subtitle,
                statistics,
                mainRow,
                bottomRow
        );
    }

    private GridPane createStatisticsGrid() {

        GridPane grid =
                new GridPane();

        grid.setHgap(20);
        grid.setVgap(20);

        VBox buildsCard =
                createStatisticCard(
                        "PC Builds",
                        "0",
                        "Saved configurations"
                );

        VBox marketplaceCard =
                createStatisticCard(
                        "Marketplace",
                        "0",
                        "Active listings"
                );

        VBox flipsCard =
                createStatisticCard(
                        "PC Flips",
                        "0",
                        "Active projects"
                );

        VBox profitCard =
                createStatisticCard(
                        "Total Profit",
                        "€0.00",
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
            String valueText,
            String descriptionText
    ) {

        Label title =
                new Label(
                        titleText
                );

        title.getStyleClass().add(
                "dashboard-stat-title"
        );

        Label value =
                new Label(
                        valueText
                );

        value.getStyleClass().add(
                "dashboard-stat-value"
        );

        Label description =
                new Label(
                        descriptionText
                );

        description.getStyleClass().add(
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
                new VBox(14);

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
                new VBox(12);

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

        Label emptyTitle =
                new Label(
                        "No recent activity"
                );

        emptyTitle.getStyleClass().add(
                "recent-activity-title"
        );

        Label emptyDescription =
                new Label(
                        "Your latest builds, listings, flips and transactions will appear here."
                );

        emptyDescription.setWrapText(
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

        card.getChildren().addAll(
                title,
                emptyState
        );

        return card;
    }

    private VBox createAccountCard() {

        VBox card =
                new VBox(10);

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

        Label accountStatus =
                new Label(
                        "Verifying authenticated session..."
                );

        accountStatus.getStyleClass().add(
                "card-text"
        );

        card.getChildren().addAll(
                title,
                accountStatus
        );

        Thread.ofVirtual().start(
                () -> {

                    try {

                        MeResponse me =
                                authSession
                                        .verifyCurrentUser();

                        Platform.runLater(
                                () ->
                                        accountStatus
                                                .setText(
                                                        me.username()
                                                                + " • "
                                                                + me.role()
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
                                        accountStatus
                                                .setText(
                                                        "Unable to verify account right now."
                                                )
                        );
                    }
                }
        );

        return card;
    }

    private VBox createServiceCard() {

        VBox card =
                new VBox(10);

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