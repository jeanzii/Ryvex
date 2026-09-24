package com.ryvex.client.view.page;

import com.ryvex.client.service.ApiService;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DashboardView extends VBox {

    private final ApiService apiService;

    public DashboardView() {
        this.apiService = new ApiService();

        getStyleClass().add("content-area");

        Label title = new Label("Dashboard");
        title.getStyleClass().add("page-title");

        Label subtitle = new Label("Welcome to Ryvex.");
        subtitle.getStyleClass().add("page-subtitle");

        VBox serviceCard = createServiceCard();

        getChildren().addAll(
                title,
                subtitle,
                serviceCard
        );
    }

    private VBox createServiceCard() {

        VBox card = new VBox();
        card.getStyleClass().add("card");

        Label title = new Label("Ryvex Services");
        title.getStyleClass().add("card-title");

        Label status = new Label("● Checking services...");
        status.getStyleClass().add("status-checking");

        card.getChildren().addAll(
                title,
                status
        );

        checkServerStatus(status);

        return card;
    }

    private void checkServerStatus(Label statusLabel) {

        Thread.ofVirtual().start(() -> {

            boolean online = apiService.isServerOnline();

            Platform.runLater(() -> {

                statusLabel.getStyleClass().removeAll(
                        "status-checking",
                        "status-online",
                        "status-offline"
                );

                if (online) {

                    statusLabel.setText(
                            "● Ryvex services online"
                    );

                    statusLabel.getStyleClass().add(
                            "status-online"
                    );

                } else {

                    statusLabel.setText(
                            "● Ryvex services offline"
                    );

                    statusLabel.getStyleClass().add(
                            "status-offline"
                    );
                }
            });
        });
    }
}