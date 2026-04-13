package io.github.libraryapi.service;

import io.github.libraryapi.controller.dto.AuthorDTO;
import io.github.libraryapi.controller.mappers.AuthorMapper;
import io.github.libraryapi.exception.AuthorNotFoundException;
import io.github.libraryapi.exception.OperationNotAllowedException;
import io.github.libraryapi.model.Author;
import io.github.libraryapi.repository.AuthorRepository;
import io.github.libraryapi.repository.BookRepository;
import io.github.libraryapi.validator.AuthorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository repository;
    private final AuthorValidator validator;
    private final BookRepository bookRepository;
    private final AuthorMapper mapper;

    public void save(Author author) {
        validator.validate(author);
        repository.save(author);
    }

    public Optional<Author> findById(UUID id) {
        return repository.findById(id);
    }

    public void deleteById(UUID id) {
        if (existsBooksByAuthor(id)) {
            throw new OperationNotAllowedException("Cannot delete author with associated books");
        }
        repository.deleteById(id);
    }

    public List<Author> findAll() {
        return repository.findAll();
    }

    public List<Author> findByName(String name) {
        return repository.findAll().stream()
                .filter(author -> author.getName().equalsIgnoreCase(name))
                .toList();
    }

    public List<Author> search(String name, String nationality) {
        if (name != null && nationality != null) {
            return repository.findAll().stream()
                    .filter(author -> (author.getName().equalsIgnoreCase(name)) && (author.getNationality().equalsIgnoreCase(nationality)))
                    .toList();
        } else if (name != null) {
            return findByName(name);
        } else if (nationality != null) {
            return repository.findAll().stream()
                    .filter(author -> author.getNationality().equalsIgnoreCase(nationality))
                    .toList();
        } else {
            return findAll();
        }
    }

    public List<Author> searchByExample(String name, String nationality) {
        var author = new Author();
        author.setName(name);
        author.setNationality(nationality);
        ExampleMatcher  exampleMatcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Author> exampleAuthor = Example.of(author, exampleMatcher);
        return repository.findAll(exampleAuthor);
    }
    public void update(UUID id, AuthorDTO dto) {
        findById(id).map((author) -> {
            Author authorEntity = mapper.toEntity(dto);
            authorEntity.setId(id);

            validator.validate(author);
            return repository.save(author);
        }).orElseThrow(() -> new AuthorNotFoundException(id));
    }

    public boolean existsBooksByAuthor(UUID id) {
        return bookRepository.existsByAuthor_Id(id);
    }
}
