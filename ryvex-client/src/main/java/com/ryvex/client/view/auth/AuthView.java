package com.ryvex.client.view.auth;

import com.ryvex.client.dto.auth.LoginResponse;
import com.ryvex.client.service.ApiException;
import com.ryvex.client.service.ApiService;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;

public class AuthView extends BorderPane {

    private final StackPane formContainer;

    private final ApiService apiService =
            new ApiService();

    private final Consumer<LoginResponse>
            onLoginSuccess;

    public AuthView(
            Consumer<LoginResponse> onLoginSuccess
    ) {

        this.onLoginSuccess =
                onLoginSuccess;

        getStyleClass().add(
                "auth-root"
        );

        VBox brandingPanel =
                createBrandingPanel();

        formContainer =
                new StackPane();

        formContainer
                .getStyleClass()
                .add(
                        "auth-form-panel"
                );

        setLeft(
                brandingPanel
        );

        setCenter(
                formContainer
        );

        showLogin(null);
    }

    private VBox createBrandingPanel() {

        Label brand =
                new Label("RYVEX");

        brand.getStyleClass().add(
                "auth-brand"
        );

        Label tagline =
                new Label(
                        "Build. Trade. Track."
                );

        tagline.getStyleClass().add(
                "brand-tagline"
        );

        Label description =
                new Label(
                        "Your centralized platform for PC building, " +
                                "hardware trading, PC flipping and finance."
                );

        description
                .getStyleClass()
                .add(
                        "brand-copy"
                );

        description.setWrapText(true);

        VBox branding =
                new VBox(
                        18,
                        brand,
                        tagline,
                        description
                );

        branding
                .getStyleClass()
                .add(
                        "auth-brand-panel"
                );

        branding.setAlignment(
                Pos.CENTER_LEFT
        );

        branding.setPrefWidth(
                460
        );

        return branding;
    }

    private void showLogin(
            String informationMessage
    ) {

        formContainer
                .getChildren()
                .setAll(
                        createLoginForm(
                                informationMessage
                        )
                );
    }

    private void showRegister() {

        formContainer
                .getChildren()
                .setAll(
                        createRegisterForm()
                );
    }

    private Node createLoginForm(
            String informationMessage
    ) {

        Label title =
                new Label(
                        "Welcome back"
                );

        title.getStyleClass().add(
                "auth-title"
        );

        Label subtitle =
                new Label(
                        "Sign in to continue to Ryvex."
                );

        subtitle.getStyleClass().add(
                "auth-subtitle"
        );

        Label loginLabel =
                new Label(
                        "Username or email"
                );

        loginLabel.getStyleClass().add(
                "field-label"
        );

        TextField loginField =
                new TextField();

        loginField.setPromptText(
                "Enter your username or email"
        );

        loginField.getStyleClass().add(
                "auth-field"
        );

        Label passwordLabel =
                new Label(
                        "Password"
                );

        passwordLabel.getStyleClass().add(
                "field-label"
        );

        PasswordField passwordField =
                new PasswordField();

        passwordField.setPromptText(
                "Enter your password"
        );

        passwordField.getStyleClass().add(
                "auth-field"
        );

        Label statusLabel =
                new Label();

        statusLabel.setWrapText(true);

        statusLabel.getStyleClass().add(
                "auth-status"
        );

        if (
                informationMessage != null
                        && !informationMessage.isBlank()
        ) {

            showSuccess(
                    statusLabel,
                    informationMessage
            );
        }

        Button loginButton =
                new Button(
                        "Sign In"
                );

        loginButton.getStyleClass().add(
                "primary-button"
        );

        loginButton.setMaxWidth(
                Double.MAX_VALUE
        );

        loginButton.setOnAction(
                event -> {

                    String login =
                            loginField
                                    .getText()
                                    .trim();

                    String password =
                            passwordField
                                    .getText();

                    if (
                            login.isBlank()
                                    || password.isBlank()
                    ) {

                        showError(
                                statusLabel,
                                "Please enter your login and password."
                        );

                        return;
                    }

                    loginButton.setDisable(
                            true
                    );

                    loginButton.setText(
                            "Signing In..."
                    );

                    statusLabel.setText(
                            ""
                    );

                    Thread.ofVirtual().start(
                            () -> {

                                try {

                                    LoginResponse response =
                                            apiService.login(
                                                    login,
                                                    password
                                            );

                                    Platform.runLater(
                                            () ->
                                                    onLoginSuccess
                                                            .accept(
                                                                    response
                                                            )
                                    );

                                } catch (
                                        ApiException e
                                ) {

                                    Platform.runLater(
                                            () -> {

                                                loginButton
                                                        .setDisable(
                                                                false
                                                        );

                                                loginButton
                                                        .setText(
                                                                "Sign In"
                                                        );

                                                showError(
                                                        statusLabel,
                                                        e.getMessage()
                                                );
                                            }
                                    );
                                }
                            }
                    );
                }
        );

        Label accountLabel =
                new Label(
                        "Don't have an account?"
                );

        accountLabel
                .getStyleClass()
                .add(
                        "auth-switch-text"
                );

        Button registerButton =
                new Button(
                        "Create account"
                );

        registerButton
                .getStyleClass()
                .add(
                        "link-button"
                );

        registerButton.setOnAction(
                event ->
                        showRegister()
        );

        HBox registerRow =
                new HBox(
                        6,
                        accountLabel,
                        registerButton
                );

        registerRow.setAlignment(
                Pos.CENTER
        );

        VBox card =
                new VBox(
                        12,
                        title,
                        subtitle,
                        createSpacer(10),
                        loginLabel,
                        loginField,
                        passwordLabel,
                        passwordField,
                        statusLabel,
                        createSpacer(4),
                        loginButton,
                        registerRow
                );

        card.getStyleClass().add(
                "auth-card"
        );

        card.setMaxWidth(
                420
        );

        return card;
    }

    private Node createRegisterForm() {

        Label title =
                new Label(
                        "Create your account"
                );

        title.getStyleClass().add(
                "auth-title"
        );

        Label subtitle =
                new Label(
                        "Join Ryvex and start building."
                );

        subtitle.getStyleClass().add(
                "auth-subtitle"
        );

        Label usernameLabel =
                new Label(
                        "Username"
                );

        usernameLabel
                .getStyleClass()
                .add(
                        "field-label"
                );

        TextField usernameField =
                new TextField();

        usernameField.setPromptText(
                "Choose a username"
        );

        usernameField
                .getStyleClass()
                .add(
                        "auth-field"
                );

        Label emailLabel =
                new Label(
                        "Email"
                );

        emailLabel
                .getStyleClass()
                .add(
                        "field-label"
                );

        TextField emailField =
                new TextField();

        emailField.setPromptText(
                "Enter your email"
        );

        emailField
                .getStyleClass()
                .add(
                        "auth-field"
                );

        Label passwordLabel =
                new Label(
                        "Password"
                );

        passwordLabel
                .getStyleClass()
                .add(
                        "field-label"
                );

        PasswordField passwordField =
                new PasswordField();

        passwordField.setPromptText(
                "Minimum 8 characters"
        );

        passwordField
                .getStyleClass()
                .add(
                        "auth-field"
                );

        Label confirmLabel =
                new Label(
                        "Confirm password"
                );

        confirmLabel
                .getStyleClass()
                .add(
                        "field-label"
                );

        PasswordField confirmField =
                new PasswordField();

        confirmField.setPromptText(
                "Repeat your password"
        );

        confirmField
                .getStyleClass()
                .add(
                        "auth-field"
                );

        Label statusLabel =
                new Label();

        statusLabel.setWrapText(true);

        statusLabel
                .getStyleClass()
                .add(
                        "auth-status"
                );

        Button createAccountButton =
                new Button(
                        "Create Account"
                );

        createAccountButton
                .getStyleClass()
                .add(
                        "primary-button"
                );

        createAccountButton
                .setMaxWidth(
                        Double.MAX_VALUE
                );

        createAccountButton.setOnAction(
                event -> {

                    String username =
                            usernameField
                                    .getText()
                                    .trim();

                    String email =
                            emailField
                                    .getText()
                                    .trim();

                    String password =
                            passwordField
                                    .getText();

                    String confirmPassword =
                            confirmField
                                    .getText();

                    if (
                            username.isBlank()
                                    || email.isBlank()
                                    || password.isBlank()
                                    || confirmPassword.isBlank()
                    ) {

                        showError(
                                statusLabel,
                                "Please complete all fields."
                        );

                        return;
                    }

                    if (
                            username.length() < 3
                    ) {

                        showError(
                                statusLabel,
                                "Username must contain at least 3 characters."
                        );

                        return;
                    }

                    if (
                            password.length() < 8
                    ) {

                        showError(
                                statusLabel,
                                "Password must contain at least 8 characters."
                        );

                        return;
                    }

                    if (
                            !password.equals(
                                    confirmPassword
                            )
                    ) {

                        showError(
                                statusLabel,
                                "Passwords do not match."
                        );

                        return;
                    }

                    createAccountButton
                            .setDisable(
                                    true
                            );

                    createAccountButton
                            .setText(
                                    "Creating Account..."
                            );

                    statusLabel.setText("");

                    Thread.ofVirtual().start(
                            () -> {

                                try {

                                    apiService.register(
                                            username,
                                            email,
                                            password
                                    );

                                    Platform.runLater(
                                            () ->
                                                    showLogin(
                                                            "Account created successfully. Sign in to continue."
                                                    )
                                    );

                                } catch (
                                        ApiException e
                                ) {

                                    Platform.runLater(
                                            () -> {

                                                createAccountButton
                                                        .setDisable(
                                                                false
                                                        );

                                                createAccountButton
                                                        .setText(
                                                                "Create Account"
                                                        );

                                                showError(
                                                        statusLabel,
                                                        e.getMessage()
                                                );
                                            }
                                    );
                                }
                            }
                    );
                }
        );

        Label existingAccount =
                new Label(
                        "Already have an account?"
                );

        existingAccount
                .getStyleClass()
                .add(
                        "auth-switch-text"
                );

        Button signInButton =
                new Button(
                        "Sign in"
                );

        signInButton
                .getStyleClass()
                .add(
                        "link-button"
                );

        signInButton.setOnAction(
                event ->
                        showLogin(null)
        );

        HBox signInRow =
                new HBox(
                        6,
                        existingAccount,
                        signInButton
                );

        signInRow.setAlignment(
                Pos.CENTER
        );

        VBox card =
                new VBox(
                        10,
                        title,
                        subtitle,
                        createSpacer(6),
                        usernameLabel,
                        usernameField,
                        emailLabel,
                        emailField,
                        passwordLabel,
                        passwordField,
                        confirmLabel,
                        confirmField,
                        statusLabel,
                        createSpacer(2),
                        createAccountButton,
                        signInRow
                );

        card.getStyleClass().add(
                "auth-card"
        );

        card.setMaxWidth(
                420
        );

        return card;
    }

    private void showError(
            Label label,
            String message
    ) {

        label.setText(message);

        label.getStyleClass().remove(
                "auth-status-success"
        );

        if (
                !label
                        .getStyleClass()
                        .contains(
                                "auth-status-error"
                        )
        ) {

            label
                    .getStyleClass()
                    .add(
                            "auth-status-error"
                    );
        }
    }

    private void showSuccess(
            Label label,
            String message
    ) {

        label.setText(message);

        label.getStyleClass().remove(
                "auth-status-error"
        );

        if (
                !label
                        .getStyleClass()
                        .contains(
                                "auth-status-success"
                        )
        ) {

            label
                    .getStyleClass()
                    .add(
                            "auth-status-success"
                    );
        }
    }

    private VBox createSpacer(
            double height
    ) {

        VBox spacer =
                new VBox();

        spacer.setMinHeight(
                height
        );

        spacer.setPrefHeight(
                height
        );

        return spacer;
    }
}