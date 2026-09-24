package com.ryvex.client;

import com.ryvex.client.auth.AuthSession;
import com.ryvex.client.service.ApiService;
import com.ryvex.client.view.MainView;
import com.ryvex.client.view.auth.AuthView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Objects;

public class RyvexApplication extends Application {

    private final ApiService apiService =
            new ApiService();

    private AuthSession authSession;

    private StackPane applicationRoot;

    @Override
    public void start(
            Stage stage
    ) {

        authSession =
                new AuthSession(
                        apiService
                );

        applicationRoot =
                new StackPane();

        Scene scene =
                new Scene(
                        applicationRoot,
                        1280,
                        800
                );

        scene.getStylesheets().add(
                Objects.requireNonNull(
                        getClass().getResource(
                                "/css/ryvex.css"
                        )
                ).toExternalForm()
        );

        Image appIcon =
                new Image(
                        Objects.requireNonNull(
                                getClass().getResourceAsStream(
                                        "/images/app-icon.png"
                                )
                        )
                );

        stage.getIcons().add(
                appIcon
        );

        stage.setTitle(
                "Ryvex"
        );

        stage.setMinWidth(
                1000
        );

        stage.setMinHeight(
                650
        );

        stage.setScene(
                scene
        );

        showAuthentication();

        stage.show();
    }

    private void showAuthentication() {

        AuthView authView =
                new AuthView(
                        apiService,
                        authSession,
                        this::showMainApplication
                );

        applicationRoot
                .getChildren()
                .setAll(
                        authView
                );
    }

    private void showMainApplication() {

        if (
                !authSession
                        .isAuthenticated()
        ) {

            showAuthentication();
            return;
        }

        MainView mainView =
                new MainView(
                        authSession,
                        this::logout
                );

        applicationRoot
                .getChildren()
                .setAll(
                        mainView
                );
    }

    private void logout() {

        Thread.ofVirtual().start(
                () -> {

                    try {

                        authSession.logout();

                    } finally {

                        Platform.runLater(
                                this::showAuthentication
                        );
                    }
                }
        );
    }

    public static void main(
            String[] args
    ) {

        launch(args);
    }
}