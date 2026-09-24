package com.ryvex.client.auth;

import com.ryvex.client.dto.auth.LoginResponse;
import com.ryvex.client.dto.auth.MeResponse;
import com.ryvex.client.dto.auth.TokenResponse;
import com.ryvex.client.service.ApiException;
import com.ryvex.client.service.ApiService;
import java.util.function.Function;

import java.time.Instant;

public class AuthSession {

    private static final long REFRESH_BUFFER_SECONDS = 30;

    private final ApiService apiService;

    private String accessToken;
    private String refreshToken;
    private String tokenType;

    private Instant accessTokenExpiresAt;

    private String username;
    private String role;

    public AuthSession(
            ApiService apiService
    ) {

        this.apiService =
                apiService;
    }

    public synchronized void start(
            LoginResponse response
    ) {

        accessToken =
                response.accessToken();

        refreshToken =
                response.refreshToken();

        tokenType =
                response.tokenType();

        accessTokenExpiresAt =
                parseInstant(
                        response.expiresAt()
                );

        username =
                response.username();

        role =
                response.role();
    }

    public MeResponse verifyCurrentUser() {

        MeResponse me =
                executeAuthenticated(
                        apiService::getMe
                );

        updateUser(
                me
        );

        return me;
    }

    public synchronized String getValidAccessToken() {

        ensureAuthenticated();

        boolean tokenNeedsRefresh =
                accessTokenExpiresAt == null
                        || Instant.now()
                        .plusSeconds(
                                REFRESH_BUFFER_SECONDS
                        )
                        .isAfter(
                                accessTokenExpiresAt
                        );

        if (tokenNeedsRefresh) {
            refreshTokens();
        }

        return accessToken;
    }

    private synchronized void refreshTokens() {

        ensureAuthenticated();

        TokenResponse response =
                apiService.refresh(
                        refreshToken
                );

        accessToken =
                response.accessToken();

        refreshToken =
                response.refreshToken();

        tokenType =
                response.tokenType();

        accessTokenExpiresAt =
                parseInstant(
                        response.expiresAt()
                );
    }

    public void logout() {

        String currentRefreshToken;

        synchronized (this) {

            currentRefreshToken =
                    refreshToken;
        }

        try {

            if (
                    currentRefreshToken != null
                            && !currentRefreshToken.isBlank()
            ) {

                apiService.logout(
                        currentRefreshToken
                );
            }

        } finally {

            clear();
        }
    }

    public synchronized void clear() {

        accessToken = null;
        refreshToken = null;
        tokenType = null;
        accessTokenExpiresAt = null;

        username = null;
        role = null;
    }

    public synchronized boolean isAuthenticated() {

        return accessToken != null
                && refreshToken != null;
    }

    public synchronized String getUsername() {
        return username;
    }

    public synchronized String getRole() {
        return role;
    }

    public synchronized String getTokenType() {
        return tokenType;
    }

    private synchronized String getAccessTokenSnapshot() {

        ensureAuthenticated();

        return accessToken;
    }

    private synchronized void updateUser(
            MeResponse me
    ) {

        username =
                me.username();

        role =
                me.role();
    }

    private synchronized void ensureAuthenticated() {

        if (!isAuthenticated()) {

            throw new ApiException(
                    401,
                    "Your session has expired. Please sign in again."
            );
        }
    }

    private Instant parseInstant(
            String value
    ) {

        try {

            return Instant.parse(
                    value
            );

        } catch (Exception e) {

            /*
             * If the timestamp cannot be parsed,
             * force a token refresh on the next request.
             */
            return Instant.EPOCH;
        }
    }

    public <T> T executeAuthenticated(
            Function<String, T> request
    ) {

        /*
         * getValidAccessToken() automatically refreshes
         * the token when its known expiry is approaching.
         */
        String token =
                getValidAccessToken();

        try {

            return request.apply(
                    token
            );

        } catch (ApiException e) {

            /*
             * The backend may reject a token even if our
             * local expiry calculation believed it was valid.
             *
             * In that case, refresh once and retry.
             */
            if (
                    e.getStatusCode()
                            != 401
            ) {

                throw e;
            }

            refreshTokens();

            return request.apply(
                    getAccessTokenSnapshot()
            );
        }
    }
}