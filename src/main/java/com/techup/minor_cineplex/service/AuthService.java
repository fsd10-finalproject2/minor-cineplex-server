package com.techup.minor_cineplex.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.techup.minor_cineplex.dto.request.auth.LoginRequest;
import com.techup.minor_cineplex.dto.request.auth.RegisterRequest;
import com.techup.minor_cineplex.dto.response.auth.AuthResponse;
import com.techup.minor_cineplex.exception.AppException;
import com.techup.minor_cineplex.exception.ErrorCode;
import com.techup.minor_cineplex.infrastructure.SupabaseClient;
import com.techup.minor_cineplex.mapper.UserMapper;
import com.techup.minor_cineplex.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final UserRepository userRepository;
    private final SupabaseClient supabaseClient;
    private final UserMapper userMapper;

    //  Register 
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

        userRepository.save(userMapper.toEntity(request.getName(), request.getEmail(), userId));
    }

    // Login
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