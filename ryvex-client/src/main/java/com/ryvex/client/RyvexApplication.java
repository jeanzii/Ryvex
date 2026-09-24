package com.ryvex.client;

import com.ryvex.client.view.MainView;
import com.ryvex.client.view.auth.AuthView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Objects;

public class RyvexApplication extends Application {

    @Override
    public void start(Stage stage) {

        StackPane applicationRoot =
                new StackPane();

        Scene scene =
                new Scene(
                        applicationRoot,
                        1280,
                        800
                );

        AuthView authView =
                new AuthView(
                        loginResponse -> {

                            MainView mainView =
                                    new MainView();

                            applicationRoot
                                    .getChildren()
                                    .setAll(
                                            mainView
                                    );
                        }
                );

        applicationRoot
                .getChildren()
                .add(
                        authView
                );

        scene.getStylesheets().add(
                Objects.requireNonNull(
                        getClass().getResource(
                                "/css/ryvex.css"
                        )
                ).toExternalForm()
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

        stage.show();
    }

    public static void main(
            String[] args
    ) {

        launch(args);
    }
}