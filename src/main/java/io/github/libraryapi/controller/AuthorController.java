package io.github.libraryapi.controller;

import io.github.libraryapi.controller.dto.AuthorDTO;
import io.github.libraryapi.controller.dto.ResponseError;
import io.github.libraryapi.exception.DuplicatedRegisterException;
import io.github.libraryapi.exception.OperationNotAllowedException;
import io.github.libraryapi.service.AuthorService;
import io.github.libraryapi.model.Author;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService service;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid AuthorDTO author) {
        try {
            Author authorEntity = author.toEntity();
            Author saved = service.save(authorEntity);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(saved.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        } catch (DuplicatedRegisterException e) {
            var dtoError = ResponseError.conflict(e.getMessage());
            return ResponseEntity.status(dtoError.status()).body(dtoError);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> findById(@PathVariable String id) {
        UUID idAuthor = UUID.fromString(id);
        Optional<Author> author = service.findById(idAuthor);
        if (author.isPresent()) {
            Author authorEntity = author.get();
            AuthorDTO authorDTO = new AuthorDTO(
                    authorEntity.getId(),
                    authorEntity.getName(),
                    authorEntity.getBirthDate(),
                    authorEntity.getNationality()
            );
            return ResponseEntity.ok(authorDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable String id) {
        try {
            UUID idAuthor = UUID.fromString(id);
            Optional<Author> author = service.findById(idAuthor);
            if (author.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            service.deleteById(idAuthor);
            return ResponseEntity.noContent().build();
        } catch (OperationNotAllowedException e) {
            var dtoError = ResponseError.conflict(e.getMessage());
            return ResponseEntity.status(dtoError.status()).body(dtoError);
        }
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> findAll() {
        List<Author> authors = service.findAll();
        List<AuthorDTO> authorDTOs = authors.stream()
                .map(author -> new AuthorDTO(
                        author.getId(),
                        author.getName(),
                        author.getBirthDate(),
                        author.getNationality()
                ))
                .toList();
        return ResponseEntity.ok(authorDTOs);
    }

    @GetMapping("/search")
    public ResponseEntity<List<AuthorDTO>> search(@RequestParam(required = false) String name,@RequestParam(required = false) String nationality) {
        List<Author> authors = service.search(name, nationality);
        List<AuthorDTO> authorDTOs = authors.stream()
                .map(author -> new AuthorDTO(
                        author.getId(),
                        author.getName(),
                        author.getBirthDate(),
                        author.getNationality()
                ))
                .toList();
        return ResponseEntity.ok(authorDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable String id,@RequestBody @Valid AuthorDTO dto) {
        try {
            UUID idAuthor = UUID.fromString(id);
            Optional<Author> existingAuthor = service.findById(idAuthor);
            if (existingAuthor.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            Author authorEntity = dto.toEntity();
            authorEntity.setId(idAuthor);
            service.update(authorEntity);
            return ResponseEntity.noContent().build();
        } catch (DuplicatedRegisterException e) {
            var dtoError = ResponseError.conflict(e.getMessage());
            return ResponseEntity.status(dtoError.status()).body(dtoError);
        }
    }
}
