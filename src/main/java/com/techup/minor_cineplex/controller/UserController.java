package com.techup.minor_cineplex.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techup.minor_cineplex.dto.request.user.ChangeEmailRequest;
import com.techup.minor_cineplex.dto.request.user.ForgotPasswordRequest;
import com.techup.minor_cineplex.dto.request.user.ResetPasswordRequest;
import com.techup.minor_cineplex.dto.response.user.UserProfileResponse;
import com.techup.minor_cineplex.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getMe(
        @AuthenticationPrincipal Jwt jwt
    ) {
        return ResponseEntity.ok(userService.getMe(jwt));
    }

    @PutMapping("/change-email")
    public ResponseEntity<Map<String, String>> changeEmail(
        @AuthenticationPrincipal Jwt jwt,
        @RequestBody @Valid ChangeEmailRequest request
    ) {
        userService.changeEmail(jwt, request);
        return ResponseEntity.ok(Map.of("message", "Confirmation email sent. Please check your inbox to confirm the new email."));
    }

    @PutMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(
        @AuthenticationPrincipal Jwt jwt,
        @RequestBody @Valid ResetPasswordRequest request
    ) {
        userService.resetPassword(jwt, request);
        return ResponseEntity.ok(Map.of("message", "Password reset successfully"));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(
        @RequestBody @Valid ForgotPasswordRequest request
    ) {
        userService.forgotPassword(request.getEmail());
        return ResponseEntity.ok(Map.of("message", "Reset password email sent"));
    }
}