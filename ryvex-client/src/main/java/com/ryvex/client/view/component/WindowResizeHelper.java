package com.ryvex.client.view.component;

import javafx.scene.Cursor;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public final class WindowResizeHelper {

    private static final double RESIZE_MARGIN = 6;

    private WindowResizeHelper() {
    }

    private enum ResizeMode {
        NONE,
        NORTH,
        SOUTH,
        EAST,
        WEST,
        NORTH_EAST,
        NORTH_WEST,
        SOUTH_EAST,
        SOUTH_WEST
    }

    public static void enable(
            Stage stage,
            Region root
    ) {

        final ResizeState state =
                new ResizeState();

        root.setOnMouseMoved(
                event -> {

                    if (stage.isMaximized()) {

                        root.setCursor(
                                Cursor.DEFAULT
                        );

                        state.mode =
                                ResizeMode.NONE;

                        return;
                    }

                    state.mode =
                            determineResizeMode(
                                    event.getX(),
                                    event.getY(),
                                    root.getWidth(),
                                    root.getHeight()
                            );

                    root.setCursor(
                            cursorFor(
                                    state.mode
                            )
                    );
                }
        );

        root.setOnMousePressed(
                event -> {

                    state.mode =
                            determineResizeMode(
                                    event.getX(),
                                    event.getY(),
                                    root.getWidth(),
                                    root.getHeight()
                            );

                    state.startMouseX =
                            event.getScreenX();

                    state.startMouseY =
                            event.getScreenY();

                    state.startStageX =
                            stage.getX();

                    state.startStageY =
                            stage.getY();

                    state.startWidth =
                            stage.getWidth();

                    state.startHeight =
                            stage.getHeight();
                }
        );

        root.setOnMouseDragged(
                event -> {

                    if (
                            state.mode
                                    == ResizeMode.NONE
                                    || stage.isMaximized()
                    ) {
                        return;
                    }

                    double deltaX =
                            event.getScreenX()
                                    - state.startMouseX;

                    double deltaY =
                            event.getScreenY()
                                    - state.startMouseY;

                    if (
                            state.mode
                                    == ResizeMode.EAST
                                    || state.mode
                                    == ResizeMode.NORTH_EAST
                                    || state.mode
                                    == ResizeMode.SOUTH_EAST
                    ) {

                        stage.setWidth(
                                Math.max(
                                        stage.getMinWidth(),
                                        state.startWidth
                                                + deltaX
                                )
                        );
                    }

                    if (
                            state.mode
                                    == ResizeMode.SOUTH
                                    || state.mode
                                    == ResizeMode.SOUTH_EAST
                                    || state.mode
                                    == ResizeMode.SOUTH_WEST
                    ) {

                        stage.setHeight(
                                Math.max(
                                        stage.getMinHeight(),
                                        state.startHeight
                                                + deltaY
                                )
                        );
                    }

                    if (
                            state.mode
                                    == ResizeMode.WEST
                                    || state.mode
                                    == ResizeMode.NORTH_WEST
                                    || state.mode
                                    == ResizeMode.SOUTH_WEST
                    ) {

                        double newWidth =
                                state.startWidth
                                        - deltaX;

                        if (
                                newWidth
                                        >= stage.getMinWidth()
                        ) {

                            stage.setX(
                                    state.startStageX
                                            + deltaX
                            );

                            stage.setWidth(
                                    newWidth
                            );
                        }
                    }

                    if (
                            state.mode
                                    == ResizeMode.NORTH
                                    || state.mode
                                    == ResizeMode.NORTH_EAST
                                    || state.mode
                                    == ResizeMode.NORTH_WEST
                    ) {

                        double newHeight =
                                state.startHeight
                                        - deltaY;

                        if (
                                newHeight
                                        >= stage.getMinHeight()
                        ) {

                            stage.setY(
                                    state.startStageY
                                            + deltaY
                            );

                            stage.setHeight(
                                    newHeight
                            );
                        }
                    }
                }
        );
    }

    private static ResizeMode determineResizeMode(
            double x,
            double y,
            double width,
            double height
    ) {

        boolean left =
                x <= RESIZE_MARGIN;

        boolean right =
                x >= width
                        - RESIZE_MARGIN;

        boolean top =
                y <= RESIZE_MARGIN;

        boolean bottom =
                y >= height
                        - RESIZE_MARGIN;

        if (top && left) {
            return ResizeMode.NORTH_WEST;
        }

        if (top && right) {
            return ResizeMode.NORTH_EAST;
        }

        if (bottom && left) {
            return ResizeMode.SOUTH_WEST;
        }

        if (bottom && right) {
            return ResizeMode.SOUTH_EAST;
        }

        if (top) {
            return ResizeMode.NORTH;
        }

        if (bottom) {
            return ResizeMode.SOUTH;
        }

        if (left) {
            return ResizeMode.WEST;
        }

        if (right) {
            return ResizeMode.EAST;
        }

        return ResizeMode.NONE;
    }

    private static Cursor cursorFor(
            ResizeMode mode
    ) {

        return switch (mode) {

            case NORTH ->
                    Cursor.N_RESIZE;

            case SOUTH ->
                    Cursor.S_RESIZE;

            case EAST ->
                    Cursor.E_RESIZE;

            case WEST ->
                    Cursor.W_RESIZE;

            case NORTH_EAST ->
                    Cursor.NE_RESIZE;

            case NORTH_WEST ->
                    Cursor.NW_RESIZE;

            case SOUTH_EAST ->
                    Cursor.SE_RESIZE;

            case SOUTH_WEST ->
                    Cursor.SW_RESIZE;

            default ->
                    Cursor.DEFAULT;
        };
    }

    private static class ResizeState {

        private ResizeMode mode =
                ResizeMode.NONE;

        private double startMouseX;
        private double startMouseY;

        private double startStageX;
        private double startStageY;

        private double startWidth;
        private double startHeight;
    }
}