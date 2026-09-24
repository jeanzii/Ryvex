package com.ryvex.client.view;

import com.ryvex.client.view.page.DashboardView;
import com.ryvex.client.view.page.PlaceholderView;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import com.ryvex.client.auth.AuthSession;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.Objects;

import java.util.ArrayList;
import java.util.List;

public class MainView extends BorderPane {

    private final AuthSession authSession;
    private final Runnable onLogout;

    private final List<Button> navigationButtons =
            new ArrayList<>();

    public MainView(
            AuthSession authSession,
            Runnable onLogout
    ) {

        this.authSession =
                authSession;

        this.onLogout =
                onLogout;

        VBox sidebar = createSidebar();

        setLeft(sidebar);

        showDashboard();
    }

    private VBox createSidebar() {

        VBox sidebar = new VBox();

        sidebar.getStyleClass().add("sidebar");
        sidebar.setPrefWidth(240);

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
                javafx.geometry.Pos.CENTER
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

        Button dashboardButton =
                createSidebarButton(
                        "Dashboard"
                );

        Button pcBuilderButton =
                createSidebarButton(
                        "PC Builder"
                );

        Button marketplaceButton =
                createSidebarButton(
                        "Marketplace"
                );

        Button flippingButton =
                createSidebarButton(
                        "PC Flipping"
                );

        Button financeButton =
                createSidebarButton(
                        "Finance"
                );

        Region spacer = new Region();

        VBox.setVgrow(
                spacer,
                Priority.ALWAYS
        );

        Button settingsButton =
                createSidebarButton(
                        "Settings"
                );

        Button profileButton =
                createSidebarButton(
                        "Profile"
                );

        Label signedInLabel =
                new Label(
                        "Signed in as"
                );

        signedInLabel
                .getStyleClass()
                .add(
                        "sidebar-user-label"
                );

        Label usernameLabel =
                new Label(
                        authSession.getUsername()
                );

        usernameLabel
                .getStyleClass()
                .add(
                        "sidebar-username"
                );

        Button logoutButton =
                new Button(
                        "Log Out"
                );

        logoutButton
                .setMaxWidth(
                        Double.MAX_VALUE
                );

        logoutButton
                .getStyleClass()
                .addAll(
                        "sidebar-button",
                        "logout-button"
                );

        logoutButton.setOnAction(
                event ->
                        onLogout.run()
        );

        dashboardButton.setOnAction(
                event -> {

                    showDashboard();

                    setActiveButton(
                            dashboardButton
                    );
                }
        );

        pcBuilderButton.setOnAction(
                event -> {

                    showPage(
                            new PlaceholderView(
                                    "PC Builder",
                                    "Plan, configure and manage custom PC builds."
                            )
                    );

                    setActiveButton(
                            pcBuilderButton
                    );
                }
        );

        marketplaceButton.setOnAction(
                event -> {

                    showPage(
                            new PlaceholderView(
                                    "Marketplace",
                                    "Browse and manage PC hardware listings."
                            )
                    );

                    setActiveButton(
                            marketplaceButton
                    );
                }
        );

        flippingButton.setOnAction(
                event -> {

                    showPage(
                            new PlaceholderView(
                                    "PC Flipping",
                                    "Track PC purchases, upgrades, costs and resale profits."
                            )
                    );

                    setActiveButton(
                            flippingButton
                    );
                }
        );

        financeButton.setOnAction(
                event -> {

                    showPage(
                            new PlaceholderView(
                                    "Finance",
                                    "Track Ryvex transactions, spending and profit."
                            )
                    );

                    setActiveButton(
                            financeButton
                    );
                }
        );

        settingsButton.setOnAction(
                event -> {

                    showPage(
                            new PlaceholderView(
                                    "Settings",
                                    "Configure your Ryvex application preferences."
                            )
                    );

                    setActiveButton(
                            settingsButton
                    );
                }
        );

        profileButton.setOnAction(
                event -> {

                    showPage(
                            new PlaceholderView(
                                    "Profile",
                                    "Manage your Ryvex account and profile."
                            )
                    );

                    setActiveButton(
                            profileButton
                    );
                }
        );

        sidebar.getChildren().addAll(
                brand,

                dashboardButton,
                pcBuilderButton,
                marketplaceButton,
                flippingButton,
                financeButton,

                spacer,

                signedInLabel,
                usernameLabel,

                settingsButton,
                profileButton,
                logoutButton
        );

        setActiveButton(
                dashboardButton
        );

        return sidebar;
    }

    private Button createSidebarButton(
            String text
    ) {

        Button button =
                new Button(text);

        button.setMaxWidth(
                Double.MAX_VALUE
        );

        button.getStyleClass().add(
                "sidebar-button"
        );

        navigationButtons.add(
                button
        );

        return button;
    }

    private void showDashboard() {

        showPage(
                new DashboardView(
                        authSession,
                        onLogout,

                        () ->
                                showPage(
                                        new PlaceholderView(
                                                "PC Builder",
                                                "Plan, configure and manage custom PC builds."
                                        )
                                ),

                        () ->
                                showPage(
                                        new PlaceholderView(
                                                "Marketplace",
                                                "Browse and manage PC hardware listings."
                                        )
                                ),

                        () ->
                                showPage(
                                        new PlaceholderView(
                                                "PC Flipping",
                                                "Track PC purchases, upgrades, costs and resale profits."
                                        )
                                ),

                        () ->
                                showPage(
                                        new PlaceholderView(
                                                "Finance",
                                                "Track Ryvex transactions, spending and profit."
                                        )
                                )
                )
        );
    }

    private void showPage(
            Node page
    ) {

        setCenter(page);
    }

    private void setActiveButton(
            Button activeButton
    ) {

        for (
                Button button
                : navigationButtons
        ) {

            button.getStyleClass().remove(
                    "sidebar-button-active"
            );
        }

        if (
                !activeButton
                        .getStyleClass()
                        .contains(
                                "sidebar-button-active"
                        )
        ) {

            activeButton
                    .getStyleClass()
                    .add(
                            "sidebar-button-active"
                    );
        }
    }
}