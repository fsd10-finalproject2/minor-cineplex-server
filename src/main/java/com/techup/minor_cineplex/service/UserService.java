package com.techup.minor_cineplex.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.techup.minor_cineplex.dto.request.user.ChangeEmailRequest;
import com.techup.minor_cineplex.dto.request.user.ResetPasswordRequest;
import com.techup.minor_cineplex.dto.response.user.UserProfileResponse;
import com.techup.minor_cineplex.entity.Users;
import com.techup.minor_cineplex.exception.AppException;
import com.techup.minor_cineplex.exception.ErrorCode;
import com.techup.minor_cineplex.infrastructure.SupabaseClient;
import com.techup.minor_cineplex.mapper.UserMapper;
import com.techup.minor_cineplex.repository.UserRepository;
import com.techup.minor_cineplex.utils.JwtUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SupabaseClient supabaseClient;
    private final UserMapper userMapper;

    // Get Current User
    public UserProfileResponse getMe(Jwt jwt) {
        UUID userId = JwtUtils.extractUserId(jwt);
        String tokenEmail = JwtUtils.extractEmail(jwt);

        log.info("Looking for userId: {}", userId);
        log.info("Token email: {}", tokenEmail);
        log.info("All users: {}", userRepository.count());

        Users user = userRepository.findById(userId)
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (!tokenEmail.equals(user.getEmail())) {
            user.setEmail(tokenEmail);
            userRepository.save(user);
        }

        return userMapper.toProfileResponse(user);
    }

    //  Change Email 
   public void changeEmail(Jwt jwt, ChangeEmailRequest request) {
        String currentEmail = JwtUtils.extractEmail(jwt);

        if (currentEmail.equals(request.getNewEmail())) {
            throw new AppException(ErrorCode.SAME_EMAIL);
        }

        supabaseClient.signIn(currentEmail, request.getPassword());
        supabaseClient.updateUserWithToken(jwt.getTokenValue(), Map.of("email", request.getNewEmail()));
    }

    // Reset Password
    public void resetPassword(Jwt jwt, ResetPasswordRequest request) {
    UUID userId = JwtUtils.extractUserId(jwt);
    String email = JwtUtils.extractEmail(jwt);

    if (request.getOldPassword().equals(request.getNewPassword())) {
        throw new AppException(ErrorCode.SAME_PASSWORD);
    }

    supabaseClient.signIn(email, request.getOldPassword());

    supabaseClient.updateUser(userId, Map.of("password", request.getNewPassword()));
    }

    public void forgotPassword(String email) {
        userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        supabaseClient.sendResetPasswordEmail(email);
    }
}