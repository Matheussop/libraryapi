package io.github.libraryapi.controller;

import io.github.libraryapi.controller.dto.CreateBookDTO;
import io.github.libraryapi.controller.dto.ResultBookDTO;
import io.github.libraryapi.controller.mappers.BookMapper;
import io.github.libraryapi.model.Book;
import io.github.libraryapi.model.BookGenre;
import io.github.libraryapi.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController implements GenericController {
    private final BookService service;
    private final BookMapper mapper;

    @PostMapping
    public ResponseEntity<Void> createBook(@RequestBody @Valid CreateBookDTO dto) {
        UUID bookId = service.save(dto);
        URI location = generateHeaderLocation(bookId);
        return ResponseEntity.created(location).build();

    }

    @GetMapping
    public ResponseEntity<List<ResultBookDTO>> getAllBooks() {
        List<Book> books = service.findAll();
        if (!books.isEmpty()) {
            List<ResultBookDTO> booksDTO = books.stream().map(mapper::toResultDTO).toList();
            return ResponseEntity.ok(booksDTO);
        }
        return ResponseEntity.notFound().build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultBookDTO> getBookById(@PathVariable String id) {
        UUID idBook = UUID.fromString(id);

        return service.findById(idBook)
                .map(book -> {
                    var bookDto = mapper.toResultDTO(book);
                    return ResponseEntity.ok(bookDto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        UUID idBook = UUID.fromString(id);
        return service.findById(idBook)
                .<ResponseEntity<Void>>map(book -> {
                    service.delete(idBook);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() ->  ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ResultBookDTO>> search(@RequestParam(required = false) String isbn,
                                                     @RequestParam(required = false) String authorName,
                                                     @RequestParam(required = false) String title,
                                                     @RequestParam(required = false) BookGenre genre,
                                                     @RequestParam(required = false) Integer publishDate){
        List<Book> response = service.search(isbn, authorName, title, genre, publishDate);
        List<ResultBookDTO> bookList = response
                .stream()
                .map(mapper::toResultDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(bookList);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> update(
            @PathVariable String id,
            @RequestBody @Valid CreateBookDTO dto
    ){
        UUID idBook = UUID.fromString(id);
        service.update(idBook, dto);
        return ResponseEntity.noContent().build();
    }
}
