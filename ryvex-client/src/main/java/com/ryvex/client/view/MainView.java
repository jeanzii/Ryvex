package com.ryvex.client.view;

import com.ryvex.client.auth.AuthSession;
import com.ryvex.client.view.page.DashboardView;
import com.ryvex.client.view.page.PlaceholderView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class MainView extends BorderPane {

    private final AuthSession authSession;
    private final Runnable onLogout;

    private final StackPane contentArea;

    private Button activeSidebarButton;

    /*
     * Stage 4.7:
     * Sidebar buttons are now fields so Dashboard
     * quick actions can activate them as well.
     */
    private Button dashboardButton;
    private Button pcBuilderButton;
    private Button marketplaceButton;
    private Button flippingButton;
    private Button financeButton;
    private Button settingsButton;
    private Button profileButton;

    /*
     * Stage 4.7:
     * All navigation now goes through one system.
     */
    private enum Page {
        DASHBOARD,
        PC_BUILDER,
        MARKETPLACE,
        PC_FLIPPING,
        FINANCE,
        SETTINGS,
        PROFILE
    }

    public MainView(
            AuthSession authSession,
            Runnable onLogout
    ) {

        this.authSession =
                authSession;

        this.onLogout =
                onLogout;

        contentArea =
                new StackPane();

        setLeft(
                createSidebar()
        );

        setCenter(
                contentArea
        );

        /*
         * Stage 4.7:
         * Initial page also uses centralized navigation.
         */
        navigateTo(
                Page.DASHBOARD
        );
    }

    private BorderPane createSidebar() {

        BorderPane sidebar =
                new BorderPane();

        sidebar.getStyleClass().add(
                "sidebar"
        );

        sidebar.setPrefWidth(
                240
        );

        sidebar.setMinWidth(
                240
        );

        sidebar.setMaxWidth(
                240
        );

        /*
         * =========================
         * BRANDING
         * =========================
         */

        Image logoImage =
                new Image(
                        Objects.requireNonNull(
                                getClass().getResourceAsStream(
                                        "/images/ryvex-sidebar-logo.png"
                                )
                        )
                );

        ImageView logo =
                new ImageView(
                        logoImage
                );

        logo.setPreserveRatio(
                true
        );

        logo.setFitWidth(
                195
        );

        HBox brand =
                new HBox(
                        logo
                );

        brand.setAlignment(
                Pos.CENTER
        );

        brand.setMaxWidth(
                Double.MAX_VALUE
        );

        brand.getStyleClass().add(
                "sidebar-brand"
        );

        VBox.setMargin(
                brand,
                new Insets(
                        0,
                        0,
                        28,
                        0
                )
        );

        /*
         * =========================
         * MAIN NAVIGATION
         * =========================
         */

        dashboardButton =
                createSidebarButton(
                        "Dashboard"
                );

        pcBuilderButton =
                createSidebarButton(
                        "PC Builder"
                );

        marketplaceButton =
                createSidebarButton(
                        "Marketplace"
                );

        flippingButton =
                createSidebarButton(
                        "PC Flipping"
                );

        financeButton =
                createSidebarButton(
                        "Finance"
                );

        dashboardButton.setOnAction(
                event ->
                        navigateTo(
                                Page.DASHBOARD
                        )
        );

        pcBuilderButton.setOnAction(
                event ->
                        navigateTo(
                                Page.PC_BUILDER
                        )
        );

        marketplaceButton.setOnAction(
                event ->
                        navigateTo(
                                Page.MARKETPLACE
                        )
        );

        flippingButton.setOnAction(
                event ->
                        navigateTo(
                                Page.PC_FLIPPING
                        )
        );

        financeButton.setOnAction(
                event ->
                        navigateTo(
                                Page.FINANCE
                        )
        );

        VBox topSection =
                new VBox(
                        8,
                        brand,
                        dashboardButton,
                        pcBuilderButton,
                        marketplaceButton,
                        flippingButton,
                        financeButton
                );

        topSection.setFillWidth(
                true
        );

        /*
         * =========================
         * ACCOUNT
         * =========================
         */

        Label signedInLabel =
                new Label(
                        "Signed in as"
                );

        signedInLabel.getStyleClass().add(
                "sidebar-user-label"
        );

        Label usernameLabel =
                new Label(
                        authSession.getUsername()
                );

        usernameLabel.getStyleClass().add(
                "sidebar-username"
        );

        Button logoutButton =
                new Button(
                        "Log Out"
                );

        logoutButton.getStyleClass().addAll(
                "sidebar-button",
                "logout-button"
        );

        logoutButton.setMaxWidth(
                Double.MAX_VALUE
        );

        logoutButton.setOnAction(
                event ->
                        onLogout.run()
        );

        VBox accountSection =
                new VBox(
                        6,
                        signedInLabel,
                        usernameLabel,
                        logoutButton
                );

        /*
         * =========================
         * BOTTOM NAVIGATION
         * =========================
         */

        settingsButton =
                createSidebarButton(
                        "Settings"
                );

        profileButton =
                createSidebarButton(
                        "Profile"
                );

        settingsButton.setOnAction(
                event ->
                        navigateTo(
                                Page.SETTINGS
                        )
        );

        profileButton.setOnAction(
                event ->
                        navigateTo(
                                Page.PROFILE
                        )
        );

        VBox bottomSection =
                new VBox(
                        8,
                        accountSection,
                        settingsButton,
                        profileButton
                );

        bottomSection.setFillWidth(
                true
        );

        /*
         * Top navigation stays at top.
         * Account/settings/profile stay at bottom.
         */
        sidebar.setTop(
                topSection
        );

        sidebar.setBottom(
                bottomSection
        );

        return sidebar;
    }

    private Button createSidebarButton(
            String text
    ) {

        Button button =
                new Button(
                        text
                );

        button.setMaxWidth(
                Double.MAX_VALUE
        );

        button.setAlignment(
                Pos.CENTER_LEFT
        );

        button.getStyleClass().add(
                "sidebar-button"
        );

        return button;
    }

    /*
     * Stage 4.7:
     * Central navigation system.
     *
     * Sidebar clicks and Dashboard quick actions
     * now both come through this method.
     */
    private void navigateTo(
            Page page
    ) {

        switch (page) {

            case DASHBOARD -> {

                setActiveButton(
                        dashboardButton
                );

                showDashboard();
            }

            case PC_BUILDER -> {

                setActiveButton(
                        pcBuilderButton
                );

                showPage(
                        new PlaceholderView(
                                "PC Builder",
                                "Build and manage custom PC configurations."
                        )
                );
            }

            case MARKETPLACE -> {

                setActiveButton(
                        marketplaceButton
                );

                showPage(
                        new PlaceholderView(
                                "Marketplace",
                                "Buy and sell PC hardware through Ryvex."
                        )
                );
            }

            case PC_FLIPPING -> {

                setActiveButton(
                        flippingButton
                );

                showPage(
                        new PlaceholderView(
                                "PC Flipping",
                                "Track PC flipping projects, costs and profits."
                        )
                );
            }

            case FINANCE -> {

                setActiveButton(
                        financeButton
                );

                showPage(
                        new PlaceholderView(
                                "Finance",
                                "Track your Ryvex finances and transactions."
                        )
                );
            }

            case SETTINGS -> {

                setActiveButton(
                        settingsButton
                );

                showPage(
                        new PlaceholderView(
                                "Settings",
                                "Manage your Ryvex application preferences."
                        )
                );
            }

            case PROFILE -> {

                setActiveButton(
                        profileButton
                );

                showPage(
                        new PlaceholderView(
                                "Profile",
                                "Manage your Ryvex profile and account."
                        )
                );
            }
        }
    }

    private void setActiveButton(
            Button button
    ) {

        if (
                activeSidebarButton
                        != null
        ) {

            activeSidebarButton
                    .getStyleClass()
                    .remove(
                            "sidebar-button-active"
                    );
        }

        activeSidebarButton =
                button;

        if (
                !button
                        .getStyleClass()
                        .contains(
                                "sidebar-button-active"
                        )
        ) {

            button.getStyleClass()
                    .add(
                            "sidebar-button-active"
                    );
        }
    }

    private void showDashboard() {

        DashboardView dashboardView =
                new DashboardView(
                        authSession,
                        onLogout,

                        /*
                         * Stage 4.7:
                         * Dashboard Quick Actions now use
                         * the exact same navigation system
                         * as the sidebar.
                         */
                        () ->
                                navigateTo(
                                        Page.PC_BUILDER
                                ),

                        () ->
                                navigateTo(
                                        Page.MARKETPLACE
                                ),

                        () ->
                                navigateTo(
                                        Page.PC_FLIPPING
                                ),

                        () ->
                                navigateTo(
                                        Page.FINANCE
                                )
                );

        showPage(
                dashboardView
        );
    }

    private void showPage(
            Node page
    ) {

        contentArea
                .getChildren()
                .setAll(
                        page
                );
    }
}