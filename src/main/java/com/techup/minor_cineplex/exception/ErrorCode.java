package com.techup.minor_cineplex.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Auth
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "User already exists"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    INVALID_CREDENTIALS(HttpStatus.BAD_REQUEST, "Invalid email or password"),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "Invalid token"),
    SAME_EMAIL(HttpStatus.BAD_REQUEST, "New email must be different from current email"),
    SAME_PASSWORD(HttpStatus.BAD_REQUEST, "New password must be different from current password"),
    
    // Supabase
    AUTH_SERVICE_UNAVAILABLE(HttpStatus.INTERNAL_SERVER_ERROR, "Auth service unavailable"),
    INVALID_SUPABASE_RESPONSE(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid response from Supabase"),
    SUPABASE_ID_NOT_FOUND(HttpStatus.BAD_REQUEST, "Supabase ID not found"),
    INVALID_SUPABASE_ID(HttpStatus.BAD_REQUEST, "Invalid Supabase ID"),
    UPDATE_FAILED(HttpStatus.BAD_REQUEST, "Failed to update user"),

    // General
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "Validation error"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");

    private final HttpStatus status;
    private final String message;
}