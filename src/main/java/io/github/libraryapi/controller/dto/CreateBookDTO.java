package io.github.libraryapi.controller.dto;

import io.github.libraryapi.model.BookGenre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CreateBookDTO (
    @NotBlank(message = "Required field")
    String title,
    @NotBlank(message = "Required field")
    @ISBN(type = ISBN.Type.ANY, message = "Field does not match the expected format")
    String isbn,
    @NotNull(message = "Required field")
    @PastOrPresent(message = "Publish date cannot be in the future")
    LocalDate publishDate,
    BookGenre genre,
    BigDecimal price,
    @NotNull(message = "Required field")
    UUID authorId){

}
