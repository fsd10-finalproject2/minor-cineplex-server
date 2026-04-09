package com.techup.minor_cineplex.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Auth
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "email", "User already exists"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, null, "User not found"),
    INVALID_CREDENTIALS(HttpStatus.BAD_REQUEST, "email", "Invalid email or password"),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, null, "Invalid token"),
    SAME_EMAIL(HttpStatus.BAD_REQUEST, "email", "New email must be different from current email"),
    SAME_PASSWORD(HttpStatus.BAD_REQUEST, "password", "New password must be different from current password"),

    // Supabase
    AUTH_SERVICE_UNAVAILABLE(HttpStatus.INTERNAL_SERVER_ERROR, null, "Auth service unavailable"),
    INVALID_SUPABASE_RESPONSE(HttpStatus.INTERNAL_SERVER_ERROR, null, "Invalid response from Supabase"),
    SUPABASE_ID_NOT_FOUND(HttpStatus.BAD_REQUEST, null, "Supabase ID not found"),
    INVALID_SUPABASE_ID(HttpStatus.BAD_REQUEST, null, "Invalid Supabase ID"),
    UPDATE_FAILED(HttpStatus.BAD_REQUEST, null, "Failed to update user"),

    // Seat Booking
    SEAT_ALREADY_BOOKED(HttpStatus.CONFLICT, "seat", "Seat is already booked"),
    SEAT_ALREADY_SELECTED(HttpStatus.CONFLICT, "seat", "Seat is currently selected by another user"),
    SEAT_NOT_SELECTED_BY_USER(HttpStatus.BAD_REQUEST, "seat", "Seat must be selected by you before booking"),

    // General
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, null, "Validation error"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, null, "Internal server error");

    private final HttpStatus statusCode;
    private final String field;
    private final String message;
}
