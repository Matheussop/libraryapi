package io.github.libraryapi.controller.dto;

import io.github.libraryapi.model.Author;

import java.time.LocalDate;
import java.util.UUID;

public record AuthorDTO(
        UUID id,
        String name,
        LocalDate birthDate,
        String nationality) {
    public AuthorDTO {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        if (birthDate == null) {
            throw new IllegalArgumentException("Birth date cannot be null");
        }
    }
    public Author toEntity() {
        Author author = new Author();
        author.setName(name);
        author.setBirthDate(birthDate);
        author.setNationality(nationality);
        return author;
    }
}
