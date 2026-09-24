package com.ryvex.client.view.component;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.util.Objects;

public class CustomTitleBar extends HBox {

    private double dragOffsetX;
    private double dragOffsetY;

    public CustomTitleBar(Stage stage) {

        getStyleClass().add(
                "custom-title-bar"
        );

        setAlignment(
                Pos.CENTER_LEFT
        );

        Image iconImage =
                new Image(
                        Objects.requireNonNull(
                                getClass().getResourceAsStream(
                                        "/images/app-icon.png"
                                )
                        )
                );

        ImageView icon =
                new ImageView(
                        iconImage
                );

        icon.setFitWidth(
                18
        );

        icon.setFitHeight(
                18
        );

        icon.setPreserveRatio(
                true
        );

        Label title =
                new Label(
                        "Ryvex"
                );

        title.getStyleClass().add(
                "custom-title-text"
        );

        HBox applicationIdentity =
                new HBox(
                        8,
                        icon,
                        title
                );

        applicationIdentity.setAlignment(
                Pos.CENTER_LEFT
        );

        Region spacer =
                new Region();

        HBox.setHgrow(
                spacer,
                Priority.ALWAYS
        );

        Button minimizeButton =
                createWindowButton(
                        "window-minimize-button"
                );

        Button maximizeButton =
                createWindowButton(
                        "window-maximize-button"
                );

        Button closeButton =
                createWindowButton(
                        "window-close-button"
                );

        minimizeButton.setOnAction(
                event ->
                        stage.setIconified(
                                true
                        )
        );

        maximizeButton.setOnAction(
                event ->
                        toggleMaximize(
                                stage
                        )
        );

        closeButton.setOnAction(
                event ->
                        stage.close()
        );

        HBox windowControls =
                new HBox(
                        9,
                        minimizeButton,
                        maximizeButton,
                        closeButton
                );

        windowControls.setAlignment(
                Pos.CENTER_RIGHT
        );

        windowControls
                .getStyleClass()
                .add(
                        "window-controls"
                );

        getChildren().addAll(
                applicationIdentity,
                spacer,
                windowControls
        );

        setOnMouseClicked(
                event -> {

                    if (
                            event.getClickCount()
                                    == 2
                    ) {

                        toggleMaximize(
                                stage
                        );
                    }
                }
        );

        setOnMousePressed(
                event -> {

                    dragOffsetX =
                            event.getSceneX();

                    dragOffsetY =
                            event.getSceneY();
                }
        );

        setOnMouseDragged(
                event -> {

                    if (
                            stage.isMaximized()
                                    || stage.isFullScreen()
                    ) {

                        return;
                    }

                    stage.setX(
                            event.getScreenX()
                                    - dragOffsetX
                    );

                    stage.setY(
                            event.getScreenY()
                                    - dragOffsetY
                    );
                }
        );
    }

    private Button createWindowButton(
            String styleClass
    ) {

        /*
         * Empty button:
         * no -, + or × characters.
         */
        Button button =
                new Button();

        button.getStyleClass().addAll(
                "window-control-button",
                styleClass
        );

        button.setFocusTraversable(
                false
        );

        return button;
    }

    private void toggleMaximize(
            Stage stage
    ) {

        stage.setMaximized(
                !stage.isMaximized()
        );
    }
}