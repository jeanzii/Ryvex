package com.ryvex.client.view.page;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class PlaceholderView extends VBox {

    public PlaceholderView(
            String titleText,
            String descriptionText
    ) {

        getStyleClass().add("content-area");

        Label title = new Label(titleText);
        title.getStyleClass().add("page-title");

        Label description =
                new Label(descriptionText);

        description.getStyleClass().add(
                "page-subtitle"
        );

        VBox card = new VBox();
        card.getStyleClass().add("card");

        Label cardTitle =
                new Label(titleText);

        cardTitle.getStyleClass().add(
                "card-title"
        );

        Label cardText =
                new Label(
                        "This Ryvex module is currently under development."
                );

        cardText.getStyleClass().add(
                "card-text"
        );

        card.getChildren().addAll(
                cardTitle,
                cardText
        );

        getChildren().addAll(
                title,
                description,
                card
        );
    }
}