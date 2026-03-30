package io.github.libraryapi.controller;

import io.github.libraryapi.controller.dto.CreateBookDTO;
import io.github.libraryapi.controller.dto.ResponseError;
import io.github.libraryapi.controller.mappers.BookMapper;
import io.github.libraryapi.exception.DuplicatedRegisterException;
import io.github.libraryapi.model.Book;
import io.github.libraryapi.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;
    private final BookMapper mapper;

    @PostMapping
    public ResponseEntity<Object> createBook(@RequestBody @Valid CreateBookDTO createBookDTO) {
        try{
            Book book = mapper.toEntityCreate(createBookDTO);

            // send the entity to service for validattion and save on base
            // create an url to access the book data
            // return created code with header location
            return ResponseEntity.ok(createBookDTO);
        }catch (DuplicatedRegisterException e){
            var errorDTO = ResponseError.conflict(e.getMessage());
            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
        }
    }
}
