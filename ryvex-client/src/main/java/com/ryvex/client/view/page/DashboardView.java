package com.ryvex.client.view.page;

import com.ryvex.client.auth.AuthSession;
import com.ryvex.client.dto.auth.MeResponse;
import com.ryvex.client.service.ApiException;
import com.ryvex.client.service.ApiService;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DashboardView extends VBox {

    private final ApiService apiService;
    private final AuthSession authSession;
    private final Runnable onSessionExpired;

    public DashboardView(
            AuthSession authSession,
            Runnable onSessionExpired
    ) {

        this.apiService =
                new ApiService();

        this.authSession =
                authSession;

        this.onSessionExpired =
                onSessionExpired;

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

        Label subtitle =
                new Label(
                        "Welcome to Ryvex."
                );

        subtitle.getStyleClass().add(
                "page-subtitle"
        );

        VBox accountCard =
                createAccountCard();

        VBox serviceCard =
                createServiceCard();

        getChildren().addAll(
                title,
                subtitle,
                accountCard,
                serviceCard
        );
    }

    private VBox createAccountCard() {

        VBox card =
                new VBox();

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
                new VBox();

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