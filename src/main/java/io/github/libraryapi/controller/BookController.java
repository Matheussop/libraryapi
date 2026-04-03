package io.github.libraryapi.controller;

import io.github.libraryapi.controller.dto.CreateBookDTO;
import io.github.libraryapi.controller.dto.ResultBookDTO;
import io.github.libraryapi.controller.mappers.BookMapper;
import io.github.libraryapi.model.Book;
import io.github.libraryapi.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController implements GenericController {
    private final BookService bookService;
    private final BookMapper mapper;

    @PostMapping
    public ResponseEntity<Void> createBook(@RequestBody @Valid CreateBookDTO dto) {
        Book book = mapper.toEntityCreate(dto);
        bookService.save(book);
        URI location = generateHeaderLocation(book.getId());
        return ResponseEntity.created(location).build();

    }

    @GetMapping
    public ResponseEntity<List<ResultBookDTO>> getAllBooks() {
        List<Book> books = bookService.findAll();
        if (!books.isEmpty()) {
            List<ResultBookDTO> booksDTO = books.stream().map(mapper::toResultDTO).toList();
            return ResponseEntity.ok(booksDTO);
        }
        return ResponseEntity.notFound().build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultBookDTO> getBookById(@PathVariable String id) {
        UUID idBook = UUID.fromString(id);

        return bookService.findById(idBook)
                .map(book -> {
                    var bookDto = mapper.toResultDTO(book);
                    return ResponseEntity.ok(bookDto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
