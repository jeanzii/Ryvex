package com.ryvex.client.service;

import com.ryvex.client.dto.auth.*;
import com.ryvex.client.dto.dashboard.DashboardAnalyticsResponse;
import com.ryvex.client.dto.dashboard.DashboardResponse;
import com.ryvex.client.dto.pcbuild.*;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class ApiService {

    private static final String BASE_URL =
            "http://localhost:8080";

    private final HttpClient httpClient =
            HttpClient.newHttpClient();

    private final ObjectMapper objectMapper =
            new ObjectMapper();

    /*
     * =========================================================
     * SERVER STATUS
     * =========================================================
     */

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

    /*
     * =========================================================
     * AUTHENTICATION
     * =========================================================
     */

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

    /*
     * =========================================================
     * DASHBOARD
     * =========================================================
     */

    public DashboardResponse getDashboard(
            String accessToken
    ) {

        HttpRequest request =
                createAuthenticatedGetRequest(
                        "/api/dashboard",
                        accessToken
                );

        return sendForJson(
                request,
                200,
                DashboardResponse.class
        );
    }

    public DashboardAnalyticsResponse getDashboardAnalytics(
            String accessToken
    ) {

        HttpRequest request =
                createAuthenticatedGetRequest(
                        "/api/dashboard/analytics",
                        accessToken
                );

        return sendForJson(
                request,
                200,
                DashboardAnalyticsResponse.class
        );
    }

    /*
     * =========================================================
     * PC BUILDER
     * =========================================================
     */

    public List<PcBuildResponse> getBuilds(
            String accessToken
    ) {

        HttpRequest request =
                createAuthenticatedGetRequest(
                        "/api/builds",
                        accessToken
                );

        PcBuildResponse[] builds =
                sendForJson(
                        request,
                        200,
                        PcBuildResponse[].class
                );

        return Arrays.asList(
                builds
        );
    }

    public PcBuildResponse getBuild(
            String accessToken,
            Long buildId
    ) {

        HttpRequest request =
                createAuthenticatedGetRequest(
                        "/api/builds/" + buildId,
                        accessToken
                );

        return sendForJson(
                request,
                200,
                PcBuildResponse.class
        );
    }

    public PcBuildResponse createBuild(
            String accessToken,
            String name
    ) {

        HttpRequest request =
                createAuthenticatedJsonRequest(
                        "/api/builds",
                        accessToken,
                        "POST",
                        new CreatePcBuildRequest(
                                name
                        )
                );

        return sendForJson(
                request,
                201,
                PcBuildResponse.class
        );
    }

    public PcBuildResponse updateBuild(
            String accessToken,
            Long buildId,
            String name
    ) {

        HttpRequest request =
                createAuthenticatedJsonRequest(
                        "/api/builds/" + buildId,
                        accessToken,
                        "PUT",
                        new UpdatePcBuildRequest(
                                name
                        )
                );

        return sendForJson(
                request,
                200,
                PcBuildResponse.class
        );
    }

    public void deleteBuild(
            String accessToken,
            Long buildId
    ) {

        HttpRequest request =
                createAuthenticatedDeleteRequest(
                        "/api/builds/" + buildId,
                        accessToken
                );

        sendWithoutResponse(
                request,
                204
        );
    }

    public PcBuildResponse assignComponent(
            String accessToken,
            Long buildId,
            ComponentCategory category,
            Long componentId
    ) {

        HttpRequest request =
                createAuthenticatedJsonRequest(
                        "/api/builds/"
                                + buildId
                                + "/components/"
                                + category.name(),
                        accessToken,
                        "PUT",
                        new AssignComponentRequest(
                                componentId
                        )
                );

        return sendForJson(
                request,
                200,
                PcBuildResponse.class
        );
    }

    public PcBuildResponse removeComponent(
            String accessToken,
            Long buildId,
            ComponentCategory category
    ) {

        HttpRequest request =
                createAuthenticatedDeleteRequest(
                        "/api/builds/"
                                + buildId
                                + "/components/"
                                + category.name(),
                        accessToken
                );

        return sendForJson(
                request,
                200,
                PcBuildResponse.class
        );
    }

    /*
     * =========================================================
     * HARDWARE CATALOG
     * =========================================================
     */

    public List<HardwareComponentResponse> getHardware(
            String accessToken
    ) {

        HttpRequest request =
                createAuthenticatedGetRequest(
                        "/api/hardware",
                        accessToken
                );

        HardwareComponentResponse[] components =
                sendForJson(
                        request,
                        200,
                        HardwareComponentResponse[].class
                );

        return Arrays.asList(
                components
        );
    }

    public List<HardwareComponentResponse> getHardware(
            String accessToken,
            ComponentCategory category
    ) {

        if (
                category == null
        ) {

            return getHardware(
                    accessToken
            );
        }

        HttpRequest request =
                createAuthenticatedGetRequest(
                        "/api/hardware?category="
                                + category.name(),
                        accessToken
                );

        HardwareComponentResponse[] components =
                sendForJson(
                        request,
                        200,
                        HardwareComponentResponse[].class
                );

        return Arrays.asList(
                components
        );
    }

    /*
     * =========================================================
     * GENERIC POST
     * =========================================================
     */

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

    /*
     * =========================================================
     * REQUEST BUILDERS
     * =========================================================
     */

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
                                    .ofString(
                                            json
                                    )
                    )
                    .build();

        } catch (Exception e) {

            throw new ApiException(
                    0,
                    "Could not prepare the request."
            );
        }
    }

    private HttpRequest createAuthenticatedGetRequest(
            String path,
            String accessToken
    ) {

        return HttpRequest.newBuilder()
                .uri(
                        URI.create(
                                BASE_URL + path
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
    }

    private HttpRequest createAuthenticatedDeleteRequest(
            String path,
            String accessToken
    ) {

        return HttpRequest.newBuilder()
                .uri(
                        URI.create(
                                BASE_URL + path
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
                .DELETE()
                .build();
    }

    private HttpRequest createAuthenticatedJsonRequest(
            String path,
            String accessToken,
            String method,
            Object requestBody
    ) {

        try {

            String json =
                    objectMapper.writeValueAsString(
                            requestBody
                    );

            HttpRequest.Builder builder =
                    HttpRequest.newBuilder()
                            .uri(
                                    URI.create(
                                            BASE_URL + path
                                    )
                            )
                            .header(
                                    "Authorization",
                                    "Bearer " + accessToken
                            )
                            .header(
                                    "Content-Type",
                                    "application/json"
                            )
                            .header(
                                    "Accept",
                                    "application/json"
                            );

            return switch (method) {

                case "POST" ->
                        builder
                                .POST(
                                        HttpRequest.BodyPublishers
                                                .ofString(
                                                        json
                                                )
                                )
                                .build();

                case "PUT" ->
                        builder
                                .PUT(
                                        HttpRequest.BodyPublishers
                                                .ofString(
                                                        json
                                                )
                                )
                                .build();

                default ->
                        throw new ApiException(
                                0,
                                "Unsupported HTTP method: "
                                        + method
                        );
            };

        } catch (ApiException e) {

            throw e;

        } catch (Exception e) {

            throw new ApiException(
                    0,
                    "Could not prepare the request."
            );
        }
    }

    /*
     * =========================================================
     * RESPONSE HANDLING
     * =========================================================
     */

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

    /*
     * =========================================================
     * ERROR HANDLING
     * =========================================================
     */

    private String extractErrorMessage(
            int statusCode,
            String body
    ) {

        try {

            JsonNode json =
                    objectMapper.readTree(
                            body
                    );

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
                        json.get(
                                field
                        );

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

            case 404 ->
                    "The requested resource could not be found.";

            case 409 ->
                    "Ryvex could not complete the request because the data has changed.";

            case 500 ->
                    "Ryvex encountered a server error.";

            default ->
                    "Ryvex could not complete the request.";
        };
    }
}