package io.github.libraryapi.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UserDTO (
        @NotBlank(message = "Field required")
        String username,
        @Email(message = "Invalid email format")
        @NotBlank(message = "Field required")
        String email,
        @NotBlank(message = "Field required")
        String password,
        List<String> roles) {
}
