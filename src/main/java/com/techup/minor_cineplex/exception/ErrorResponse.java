package com.techup.minor_cineplex.exception;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {
    private final int status;
    private final String message;
    private final Map<String, String[]> errors;
    private final LocalDateTime timestamp;
}