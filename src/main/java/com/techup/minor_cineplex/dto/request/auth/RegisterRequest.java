package com.techup.minor_cineplex.dto.request.auth;

import com.techup.minor_cineplex.validation.FirstGroup;
import com.techup.minor_cineplex.validation.SecondGroup;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Name is required", groups = FirstGroup.class)
    @Size(min = 2, message = "Name must be at least 2 characters", groups = SecondGroup.class)
    private String name;

    @NotBlank(message = "Email is required", groups = FirstGroup.class)
    @Email(message = "Invalid email format", groups = SecondGroup.class)
    private String email;

    @NotBlank(message = "Password is required", groups = FirstGroup.class)
    @Size(min = 6, message = "Password must be at least 6 characters", groups = SecondGroup.class)
    private String password;
}