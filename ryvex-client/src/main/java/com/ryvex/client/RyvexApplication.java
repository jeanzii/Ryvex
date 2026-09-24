package com.ryvex.client;

import com.ryvex.client.auth.AuthSession;
import com.ryvex.client.service.ApiService;
import com.ryvex.client.view.MainView;
import com.ryvex.client.view.auth.AuthView;
import com.ryvex.client.view.component.CustomTitleBar;
import com.ryvex.client.view.component.WindowResizeHelper;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class RyvexApplication extends Application {

    private static final double WINDOW_CORNER_RADIUS =
            16;

    private final ApiService apiService =
            new ApiService();

    private AuthSession authSession;

    private StackPane applicationRoot;

    @Override
    public void start(
            Stage stage
    ) {

        /*
         * Transparent window surface allows
         * genuinely rounded outer corners.
         */
        stage.initStyle(
                StageStyle.TRANSPARENT
        );

        authSession =
                new AuthSession(
                        apiService
                );

        applicationRoot =
                new StackPane();

        CustomTitleBar titleBar =
                new CustomTitleBar(
                        stage
                );

        BorderPane windowRoot =
                new BorderPane();

        windowRoot
                .getStyleClass()
                .add(
                        "window-root"
                );

        windowRoot.setTop(
                titleBar
        );

        windowRoot.setCenter(
                applicationRoot
        );

        /*
         * Clip the complete application window,
         * not just the background.
         *
         * This gives the actual window rounded
         * corners instead of merely painting
         * rounded CSS borders.
         */
        Rectangle windowClip =
                new Rectangle();

        windowClip.widthProperty().bind(
                windowRoot.widthProperty()
        );

        windowClip.heightProperty().bind(
                windowRoot.heightProperty()
        );

        windowRoot.setClip(
                windowClip
        );

        Scene scene =
                new Scene(
                        windowRoot,
                        1280,
                        800
                );

        /*
         * Anything outside the rounded clip
         * becomes truly transparent.
         */
        scene.setFill(
                Color.TRANSPARENT
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

        WindowResizeHelper.enable(
                stage,
                windowRoot
        );

        /*
         * Update rounded corners depending
         * on the current window state.
         */
        Runnable updateWindowShape =
                () ->
                        updateWindowShape(
                                stage,
                                windowRoot,
                                windowClip
                        );

        stage.maximizedProperty()
                .addListener(
                        (
                                observable,
                                oldValue,
                                newValue
                        ) ->
                                updateWindowShape.run()
                );

        stage.fullScreenProperty()
                .addListener(
                        (
                                observable,
                                oldValue,
                                newValue
                        ) ->
                                updateWindowShape.run()
                );

        showAuthentication();

        stage.show();

        updateWindowShape.run();
    }

    private void updateWindowShape(
            Stage stage,
            BorderPane windowRoot,
            Rectangle windowClip
    ) {

        boolean squareWindow =
                stage.isMaximized()
                        || stage.isFullScreen();

        if (squareWindow) {

            windowClip.setArcWidth(
                    0
            );

            windowClip.setArcHeight(
                    0
            );

            windowRoot
                    .getStyleClass()
                    .remove(
                            "window-rounded"
                    );

            if (
                    !windowRoot
                            .getStyleClass()
                            .contains(
                                    "window-maximized"
                            )
            ) {

                windowRoot
                        .getStyleClass()
                        .add(
                                "window-maximized"
                        );
            }

        } else {

            /*
             * Rectangle arc dimensions represent
             * the diameter rather than radius.
             */
            windowClip.setArcWidth(
                    WINDOW_CORNER_RADIUS * 2
            );

            windowClip.setArcHeight(
                    WINDOW_CORNER_RADIUS * 2
            );

            windowRoot
                    .getStyleClass()
                    .remove(
                            "window-maximized"
                    );

            if (
                    !windowRoot
                            .getStyleClass()
                            .contains(
                                    "window-rounded"
                            )
            ) {

                windowRoot
                        .getStyleClass()
                        .add(
                                "window-rounded"
                        );
            }
        }
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