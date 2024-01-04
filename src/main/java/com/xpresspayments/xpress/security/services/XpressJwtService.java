package com.xpresspayments.xpress.security.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xpresspayments.xpress.security.config.JwtConfig;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static java.time.ZoneOffset.UTC;

@Service
@AllArgsConstructor
public class XpressJwtService implements JwtService{

    private final JwtConfig jwtConfig;
    private final UserDetailsService userDetailsService;
    @Override
    public String generateTokenFor(String username) {
        return JWT.create()
                .withSubject("access_token")
                .withClaim("username", username)
                .withExpiresAt(LocalDateTime.now().plusDays(jwtConfig.getJwtDuration())
                        .toInstant(UTC))
                .withIssuer("xpress")
                .sign(HMAC512(jwtConfig.getJwtSecret().getBytes()));
    }

    @Override
    public boolean validate(String token) {
        Algorithm algorithm = HMAC512(jwtConfig.getJwtSecret().getBytes());
        DecodedJWT decodedJWT = JWT.require(algorithm)
                .build().verify(token);

        return isValidToken(decodedJWT);
    }

    @Override
    public UserDetails extractUserDetailsFrom(String token) {
        Algorithm algorithm = HMAC512(jwtConfig.getJwtSecret().getBytes());
        DecodedJWT decodedJWT = JWT.require(algorithm)
                .build().verify(token);
        String username =  decodedJWT.getClaim("username").as(String.class);
        return userDetailsService.loadUserByUsername(username);
    }

    private static boolean isValidToken(DecodedJWT decodedJWT) {
        return isTokenNotExpired(decodedJWT);
    }

    private static boolean isTokenNotExpired(DecodedJWT decodedJWT) {
        return Instant.now().isBefore(decodedJWT.getExpiresAtAsInstant());
    }
}
