package com.ryvex.client.service;

import com.ryvex.client.dto.auth.LoginRequest;
import com.ryvex.client.dto.auth.LoginResponse;
import com.ryvex.client.dto.auth.RegisterRequest;
import com.ryvex.client.dto.auth.RegisterResponse;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiService {

    private static final String BASE_URL =
            "http://localhost:8080";

    private final HttpClient httpClient =
            HttpClient.newHttpClient();

    private final ObjectMapper objectMapper =
            new ObjectMapper();

    public boolean isServerOnline() {

        try {

            HttpRequest request =
                    HttpRequest.newBuilder()
                            .uri(
                                    URI.create(
                                            BASE_URL
                                                    + "/api/status"
                                    )
                            )
                            .GET()
                            .build();

            HttpResponse<String> response =
                    httpClient.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            return response.statusCode() == 200;

        } catch (Exception e) {

            return false;
        }
    }

    public RegisterResponse register(
            String username,
            String email,
            String password
    ) {

        RegisterRequest request =
                new RegisterRequest(
                        username,
                        email,
                        password
                );

        return post(
                "/api/auth/register",
                request,
                201,
                RegisterResponse.class
        );
    }

    public LoginResponse login(
            String login,
            String password
    ) {

        LoginRequest request =
                new LoginRequest(
                        login,
                        password
                );

        return post(
                "/api/auth/login",
                request,
                200,
                LoginResponse.class
        );
    }

    private <T> T post(
            String path,
            Object requestBody,
            int expectedStatus,
            Class<T> responseType
    ) {

        try {

            String json =
                    objectMapper.writeValueAsString(
                            requestBody
                    );

            HttpRequest request =
                    HttpRequest.newBuilder()
                            .uri(
                                    URI.create(
                                            BASE_URL + path
                                    )
                            )
                            .header(
                                    "Content-Type",
                                    "application/json"
                            )
                            .header(
                                    "Accept",
                                    "application/json"
                            )
                            .POST(
                                    HttpRequest.BodyPublishers
                                            .ofString(json)
                            )
                            .build();

            HttpResponse<String> response =
                    httpClient.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            if (
                    response.statusCode()
                            != expectedStatus
            ) {

                throw new ApiException(
                        response.statusCode(),
                        extractErrorMessage(
                                response.statusCode(),
                                response.body()
                        )
                );
            }

            return objectMapper.readValue(
                    response.body(),
                    responseType
            );

        } catch (ApiException e) {

            throw e;

        } catch (ConnectException e) {

            throw new ApiException(
                    0,
                    "Unable to connect to Ryvex services."
            );

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            throw new ApiException(
                    0,
                    "The request was interrupted."
            );

        } catch (Exception e) {

            throw new ApiException(
                    0,
                    "An unexpected connection error occurred."
            );
        }
    }

    private String extractErrorMessage(
            int statusCode,
            String body
    ) {

        try {

            JsonNode json =
                    objectMapper.readTree(body);

            String[] possibleFields = {
                    "detail",
                    "message",
                    "error",
                    "title"
            };

            for (
                    String field
                    : possibleFields
            ) {

                JsonNode value =
                        json.get(field);

                if (
                        value != null
                                && value.isTextual()
                                && !value
                                .asText()
                                .isBlank()
                ) {

                    return value.asText();
                }
            }

        } catch (Exception ignored) {
        }

        return switch (statusCode) {

            case 400 ->
                    "The information you entered is invalid.";

            case 401 ->
                    "Invalid username/email or password.";

            case 403 ->
                    "You are not allowed to perform this action.";

            case 409 ->
                    "That username or email is already in use.";

            case 500 ->
                    "Ryvex encountered a server error.";

            default ->
                    "Ryvex could not complete the request.";
        };
    }
}