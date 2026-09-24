package com.ryvex.client;

import com.ryvex.client.view.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class RyvexApplication extends Application {

    @Override
    public void start(Stage stage) {

        MainView mainView =
                new MainView();

        Scene scene =
                new Scene(
                        mainView,
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

        stage.setTitle("Ryvex");

        stage.setMinWidth(1000);
        stage.setMinHeight(650);

        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}