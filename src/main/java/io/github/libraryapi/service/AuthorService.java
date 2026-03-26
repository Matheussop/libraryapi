package io.github.libraryapi.service;

import io.github.libraryapi.exception.OperationNotAllowedException;
import io.github.libraryapi.model.Author;
import io.github.libraryapi.repository.AuthorRepository;
import io.github.libraryapi.repository.BookRepository;
import io.github.libraryapi.validator.AuthorValidator;
import lombok.RequiredArgsConstructor;
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

    public Author save(Author author) {
        validator.validate(author);
        return repository.save(author);
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

    public void update(Author author) {
        if (author.getId() == null) {
            throw new IllegalArgumentException("ID cannot be null for update");
        }
        validator.validate(author);
        repository.save(author);
    }

    public boolean existsBooksByAuthor(UUID id) {
        return bookRepository.existsByAuthor_Id(id);
    }
}
