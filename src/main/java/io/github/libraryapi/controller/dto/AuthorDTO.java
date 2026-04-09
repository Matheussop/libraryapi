package io.github.libraryapi.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AuthorDTO(
        UUID id,
        @NotBlank(message = "Required field")
        @Size(max = 100, message = "Name must be at most 100 characters")
        String name,
        @NotNull(message = "Required field")
        @Past(message = "Birth date must be in the past")
        LocalDate birthDate,
        @NotBlank(message = "Required field")
        @Size(max = 50, min = 2, message = "Field does not match the expected format")
        String nationality) {
}
