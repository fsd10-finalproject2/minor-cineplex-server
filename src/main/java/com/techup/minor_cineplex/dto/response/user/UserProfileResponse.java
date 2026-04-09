package com.techup.minor_cineplex.dto.response.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileResponse {
    private final String id;
    private final String email;
    private final String name;
    private final String avatarUrl;
}
