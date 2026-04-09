package com.techup.minor_cineplex.dto.response.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse {
    private final String userId;
    private final String accessToken;
    private final String email;
}
