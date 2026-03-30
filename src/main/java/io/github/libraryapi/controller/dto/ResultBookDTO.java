package io.github.libraryapi.controller.dto;

import io.github.libraryapi.model.BookGenre;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ResultBookDTO (
    UUID id,
    String title,
    String isbn,
    LocalDate publishDate,
    BookGenre genre,
    BigDecimal price,
    AuthorDTO author
    ){

}
