package com.ryvex.server.service;

import com.ryvex.server.model.RefreshToken;
import com.ryvex.server.model.User;
import com.ryvex.server.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.HexFormat;

@Service
public class RefreshTokenService {

    private static final SecureRandom SECURE_RANDOM =
            new SecureRandom();

    private final RefreshTokenRepository refreshTokenRepository;

    private final long refreshTokenDays;

    public RefreshTokenService(
            RefreshTokenRepository refreshTokenRepository,
            @Value("${ryvex.jwt.refresh-token-days:30}")
            long refreshTokenDays
    ) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.refreshTokenDays = refreshTokenDays;
    }

    @Transactional
    public String createRefreshToken(User user) {

        String rawToken = generateToken();
        String tokenHash = hashToken(rawToken);

        Instant now = Instant.now();

        RefreshToken refreshToken =
                new RefreshToken(
                        tokenHash,
                        user,
                        now,
                        now.plus(
                                Duration.ofDays(
                                        refreshTokenDays
                                )
                        )
                );

        refreshTokenRepository.save(refreshToken);

        return rawToken;
    }

    @Transactional
    public RotatedRefreshToken rotate(
            String rawToken
    ) {

        String tokenHash = hashToken(rawToken);

        RefreshToken storedToken =
                refreshTokenRepository
                        .findByTokenHash(tokenHash)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.UNAUTHORIZED,
                                        "Invalid refresh token"
                                )
                        );

        if (storedToken
                .getExpiresAt()
                .isBefore(Instant.now())) {

            refreshTokenRepository.delete(storedToken);

            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Refresh token has expired"
            );
        }

        User user = storedToken.getUser();

        /*
         * Rotation:
         * the old token can never be used again.
         */
        refreshTokenRepository.delete(storedToken);

        String newRawToken =
                createRefreshToken(user);

        return new RotatedRefreshToken(
                user,
                newRawToken
        );
    }

    @Transactional
    public void revoke(String rawToken) {

        String tokenHash = hashToken(rawToken);

        refreshTokenRepository
                .findByTokenHash(tokenHash)
                .ifPresent(
                        refreshTokenRepository::delete
                );
    }

    private String generateToken() {

        byte[] bytes = new byte[32];

        SECURE_RANDOM.nextBytes(bytes);

        return Base64
                .getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);
    }

    private String hashToken(String token) {

        try {

            MessageDigest digest =
                    MessageDigest.getInstance(
                            "SHA-256"
                    );

            byte[] hash =
                    digest.digest(
                            token.getBytes(
                                    StandardCharsets.UTF_8
                            )
                    );

            return HexFormat
                    .of()
                    .formatHex(hash);

        } catch (NoSuchAlgorithmException e) {

            throw new IllegalStateException(
                    "SHA-256 is unavailable",
                    e
            );
        }
    }

    public record RotatedRefreshToken(
            User user,
            String refreshToken
    ) {
    }
}