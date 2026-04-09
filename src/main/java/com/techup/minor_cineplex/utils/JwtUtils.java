package com.techup.minor_cineplex.utils;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.server.ResponseStatusException;

public class JwtUtils {

    private JwtUtils() {}

    public static UUID extractUserId(Jwt jwt) {
        try {
            return UUID.fromString(jwt.getSubject());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid token");
        }
    }

    public static String extractEmail(Jwt jwt) {
        return jwt.getClaimAsString("email");
    }
}
