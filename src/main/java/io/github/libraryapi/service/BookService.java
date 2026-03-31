package io.github.libraryapi.service;

import io.github.libraryapi.model.Book;
import io.github.libraryapi.repository.BookRepository;
import io.github.libraryapi.validator.BookValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository repository;
    private final BookValidator validator;

    public Book save(Book book) {
        validator.validate(book);
        return repository.save(book);
    }

}
