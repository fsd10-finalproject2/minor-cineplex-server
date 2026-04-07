package com.minorcineplex.service;

import java.util.Map;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.minorcineplex.dto.request.auth.LoginRequest;
import com.minorcineplex.dto.request.auth.RegisterRequest;
import com.minorcineplex.dto.response.auth.AuthResponse;
import com.minorcineplex.entity.User;
import com.minorcineplex.enums.Role;
import com.minorcineplex.exception.AppException;
import com.minorcineplex.exception.ErrorCode;
import com.minorcineplex.infrastructure.SupabaseClient;
import com.minorcineplex.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final SupabaseClient supabaseClient;

    public void register(RegisterRequest request) {
        Map<String, Object> response = supabaseClient.signUp(
            request.getEmail(), request.getPassword()
        );

        Object userObj = response.get("user");
        if (!(userObj instanceof Map)) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, "Invalid response from Supabase"
            );
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> userMap = (Map<String, Object>) userObj;
        String supabaseId = (String) userMap.get("id");

        if (supabaseId == null) {
            throw new AppException(ErrorCode.SUPABASE_ID_NOT_FOUND);
        }

        UUID userId;
        try {
            userId = UUID.fromString(supabaseId);
        } catch (Exception e) {
            throw new AppException(ErrorCode.INVALID_SUPABASE_ID);
        }

        if (userRepository.findById(userId).isPresent()) {
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
        }

        User user = User.builder()
            .id(userId)
            .name(request.getName())
            .email(request.getEmail())
            .role(Role.USER)
            .build();

        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest request) {
        Map<String, Object> session = supabaseClient.signIn(
            request.getEmail(), request.getPassword()
        );

        String accessToken = (String) session.get("access_token");

        @SuppressWarnings("unchecked")
        Map<String, Object> userMap = (Map<String, Object>) session.get("user");

        if (userMap == null) {
            throw new AppException(ErrorCode.INVALID_SUPABASE_RESPONSE);
        }

        String userId = (String) userMap.get("id");

        return AuthResponse.builder()
            .accessToken(accessToken)
            .userId(userId)
            .email(request.getEmail())
            .build();
    }
}
