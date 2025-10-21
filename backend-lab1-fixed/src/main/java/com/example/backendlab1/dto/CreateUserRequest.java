package com.example.backendlab1.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CreateUserRequest {
    @NotBlank public String username;
    @Email @NotBlank public String email;
    @NotBlank public String password;
}
