package com.techup.minor_cineplex.mapper;

import org.springframework.stereotype.Component;

import com.techup.minor_cineplex.dto.response.user.UserProfileResponse;
import com.techup.minor_cineplex.entity.Users;

@Component
public class UserMapper {

    public UserProfileResponse toProfileResponse(Users user) {
        return UserProfileResponse.builder()
            .id(user.getId().toString())
            .email(user.getEmail())
            .name(user.getName())
            .avatarUrl(null)
            .build();
    }

    public Users toEntity(String name, String email, java.util.UUID id) {
        return Users.builder()
            .id(id)
            .name(name)
            .email(email)
            .build();
    }
}