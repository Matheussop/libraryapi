package io.github.libraryapi.controller;

import io.github.libraryapi.controller.dto.CreateBookDTO;
import io.github.libraryapi.controller.mappers.BookMapper;
import io.github.libraryapi.model.Book;
import io.github.libraryapi.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController implements GenericController {
    private final BookService bookService;
    private final BookMapper mapper;

    @PostMapping
    public ResponseEntity<Object> createBook(@RequestBody @Valid CreateBookDTO dto) {
        Book book = mapper.toEntityCreate(dto);
        bookService.save(book);
        URI location = generateHeaderLocation(book.getId());
        return ResponseEntity.created(location).build();

    }
}
