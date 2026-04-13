package io.github.libraryapi.controller;

import io.github.libraryapi.controller.dto.AuthorDTO;
import io.github.libraryapi.controller.mappers.AuthorMapper;
import io.github.libraryapi.service.AuthorService;
import io.github.libraryapi.model.Author;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController implements GenericController {

    private final AuthorService service;
    private final AuthorMapper mapper;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid AuthorDTO dto) {
        Author author = mapper.toEntity(dto);
        service.save(author);
        URI location = generateHeaderLocation(author.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> findById(@PathVariable String id) {
        UUID idAuthor = UUID.fromString(id);
        Optional<Author> author = service.findById(idAuthor);
        if (author.isPresent()) {
            Author authorEntity = author.get();
            AuthorDTO authorDTO = mapper.toAuthorDTO(authorEntity);
            return ResponseEntity.ok(authorDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        UUID idAuthor = UUID.fromString(id);
        Optional<Author> author = service.findById(idAuthor);
        if (author.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        service.deleteById(idAuthor);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> findAll() {
        List<Author> authors = service.findAll();
        List<AuthorDTO> authorDTOs = authors.stream()
                .map(mapper::toAuthorDTO)
                .toList();
        return ResponseEntity.ok(authorDTOs);
    }

    @GetMapping("/search")
    public ResponseEntity<List<AuthorDTO>> search(@RequestParam(required = false) String name, @RequestParam(required = false) String nationality) {
        List<Author> authors = service.searchByExample(name, nationality);
        List<AuthorDTO> authorDTOs = authors.stream()
                .map(mapper::toAuthorDTO)
                .toList();
        return ResponseEntity.ok(authorDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody @Valid AuthorDTO dto) {
        UUID idAuthor = UUID.fromString(id);
        service.update(idAuthor, dto);
        return ResponseEntity.noContent().build();
    }
}
