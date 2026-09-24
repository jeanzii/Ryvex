package com.ryvex.client;

import com.ryvex.client.service.ApiService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RyvexApplication extends Application {

    private final ApiService apiService = new ApiService();

    @Override
    public void start(Stage stage) {

        Label titleLabel = new Label("RYVEX");
        Label serverStatusLabel = new Label("Checking Ryvex services...");

        VBox root = new VBox(
                20,
                titleLabel,
                serverStatusLabel
        );

        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 800, 500);

        stage.setTitle("Ryvex");
        stage.setScene(scene);
        stage.show();

        checkServerStatus(serverStatusLabel);
    }

    private void checkServerStatus(Label statusLabel) {

        Thread.ofVirtual().start(() -> {

            boolean online = apiService.isServerOnline();

            Platform.runLater(() -> {

                if (online) {
                    statusLabel.setText("● Ryvex services online");
                } else {
                    statusLabel.setText("● Ryvex services offline");
                }
            });
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}