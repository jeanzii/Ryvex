package com.ryvex.client.service;

import com.ryvex.client.dto.auth.*;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.ryvex.client.dto.dashboard.DashboardResponse;

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
                                            BASE_URL + "/api/status"
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

        return post(
                "/api/auth/register",
                new RegisterRequest(
                        username,
                        email,
                        password
                ),
                201,
                RegisterResponse.class
        );
    }

    public LoginResponse login(
            String login,
            String password
    ) {

        return post(
                "/api/auth/login",
                new LoginRequest(
                        login,
                        password
                ),
                200,
                LoginResponse.class
        );
    }

    public MeResponse getMe(
            String accessToken
    ) {

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(
                                URI.create(
                                        BASE_URL + "/api/auth/me"
                                )
                        )
                        .header(
                                "Authorization",
                                "Bearer " + accessToken
                        )
                        .header(
                                "Accept",
                                "application/json"
                        )
                        .GET()
                        .build();

        return sendForJson(
                request,
                200,
                MeResponse.class
        );
    }

    public DashboardResponse getDashboard(
            String accessToken
    ) {

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(
                                URI.create(
                                        BASE_URL + "/api/dashboard"
                                )
                        )
                        .header(
                                "Authorization",
                                "Bearer " + accessToken
                        )
                        .header(
                                "Accept",
                                "application/json"
                        )
                        .GET()
                        .build();

        return sendForJson(
                request,
                200,
                DashboardResponse.class
        );
    }

    public TokenResponse refresh(
            String refreshToken
    ) {

        return post(
                "/api/auth/refresh",
                new RefreshRequest(
                        refreshToken
                ),
                200,
                TokenResponse.class
        );
    }

    public void logout(
            String refreshToken
    ) {

        HttpRequest request =
                createPostRequest(
                        "/api/auth/logout",
                        new LogoutRequest(
                                refreshToken
                        )
                );

        sendWithoutResponse(
                request,
                204
        );
    }

    private <T> T post(
            String path,
            Object requestBody,
            int expectedStatus,
            Class<T> responseType
    ) {

        HttpRequest request =
                createPostRequest(
                        path,
                        requestBody
                );

        return sendForJson(
                request,
                expectedStatus,
                responseType
        );
    }

    private HttpRequest createPostRequest(
            String path,
            Object requestBody
    ) {

        try {

            String json =
                    objectMapper.writeValueAsString(
                            requestBody
                    );

            return HttpRequest.newBuilder()
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

        } catch (Exception e) {

            throw new ApiException(
                    0,
                    "Could not prepare the request."
            );
        }
    }

    private <T> T sendForJson(
            HttpRequest request,
            int expectedStatus,
            Class<T> responseType
    ) {

        try {

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

    private void sendWithoutResponse(
            HttpRequest request,
            int expectedStatus
    ) {

        try {

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

            for (String field : possibleFields) {

                JsonNode value =
                        json.get(field);

                if (
                        value != null
                                && value.isTextual()
                                && !value.asText().isBlank()
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
                    "Your session is invalid or has expired.";

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