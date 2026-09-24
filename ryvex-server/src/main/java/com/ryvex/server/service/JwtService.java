package com.ryvex.server.service;

import com.ryvex.server.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;
    private final long accessTokenMinutes;

    public JwtService(
            JwtEncoder jwtEncoder,
            @Value("${ryvex.jwt.access-token-minutes:30}")
            long accessTokenMinutes
    ) {
        this.jwtEncoder = jwtEncoder;
        this.accessTokenMinutes = accessTokenMinutes;
    }

    public TokenResult createAccessToken(User user) {

        Instant now = Instant.now();

        Instant expiresAt =
                now.plus(
                        Duration.ofMinutes(
                                accessTokenMinutes
                        )
                );

        JwtClaimsSet claims =
                JwtClaimsSet.builder()
                        .issuer("ryvex-server")
                        .issuedAt(now)
                        .expiresAt(expiresAt)
                        .subject(user.getUsername())
                        .claim(
                                "userId",
                                user.getId()
                        )
                        .claim(
                                "role",
                                user.getRole().name()
                        )
                        .build();

        JwsHeader header =
                JwsHeader
                        .with(MacAlgorithm.HS256)
                        .type("JWT")
                        .build();

        String token =
                jwtEncoder.encode(
                        JwtEncoderParameters.from(
                                header,
                                claims
                        )
                ).getTokenValue();

        return new TokenResult(
                token,
                expiresAt
        );
    }

    public record TokenResult(
            String token,
            Instant expiresAt
    ) {
    }
}