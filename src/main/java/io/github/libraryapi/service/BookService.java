package io.github.libraryapi.service;

import io.github.libraryapi.model.Book;
import io.github.libraryapi.repository.BookRepository;
import io.github.libraryapi.validator.BookValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository repository;
    private final BookValidator validator;

    public void save(Book book) {
        validator.validate(book);
        repository.save(book);
    }

    public List<Book> findAll() {
        return repository.findAll();
    }

    public Optional<Book> findById(UUID id) {
        return repository.findById(id);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

}
