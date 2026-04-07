package com.minorcineplex.service;

import java.util.UUID;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import com.minorcineplex.entity.User;
import com.minorcineplex.exception.AppException;
import com.minorcineplex.exception.ErrorCode;
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

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (!tokenEmail.equals(user.getEmail())) {
            user.setEmail(tokenEmail);
            userRepository.save(user);
        }

        return user;
    }
}
