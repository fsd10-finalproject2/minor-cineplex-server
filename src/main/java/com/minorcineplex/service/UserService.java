package com.minorcineplex.service;

import java.util.UUID;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import com.minorcineplex.entity.User;

import com.minorcineplex.repository.UserRepository;
import com.minorcineplex.utils.JwtUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getMe(Jwt jwt) {
        UUID userId = JwtUtils.extractUserId(jwt);
        String tokenEmail = JwtUtils.extractEmail(jwt);

        User user = userRepository.findById(userId).orElseGet(() -> {
            User newUser = User.builder()
                .id(userId)
                .name(tokenEmail.split("@")[0]) // Default name
                .email(tokenEmail)
                .role(com.minorcineplex.enums.Role.USER)
                .build();
            return userRepository.save(newUser);
        });

        if (!tokenEmail.equals(user.getEmail())) {
            user.setEmail(tokenEmail);
            userRepository.save(user);
        }

        return user;
    }
}
