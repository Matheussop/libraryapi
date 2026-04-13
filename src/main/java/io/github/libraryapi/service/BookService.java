package io.github.libraryapi.service;

import io.github.libraryapi.controller.dto.CreateBookDTO;
import io.github.libraryapi.controller.mappers.BookMapper;
import io.github.libraryapi.exception.BookNotFoundException;
import io.github.libraryapi.model.Author;
import io.github.libraryapi.model.Book;
import io.github.libraryapi.model.BookGenre;
import io.github.libraryapi.repository.AuthorRepository;
import io.github.libraryapi.repository.BookRepository;
import io.github.libraryapi.validator.BookValidator;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.github.libraryapi.repository.spec.BookSpecs.*;
@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository repository;
    private final AuthorRepository authorRepository;
    private final BookValidator validator;
    private final BookMapper mapper;

    public UUID save(CreateBookDTO dto) {
        Author author = findExistingAuthor(dto.authorId());
        Book book = mapper.toEntity(dto);
        book.setAuthor(author);

        validator.validate(book);
        return repository.save(book).getId();
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

    public List<Book> search(String isbn,
                             String authorName,
                             String title,
                             BookGenre genre,
                             Integer publishDate) {

//        Specification<Book> spec = Specification.where(BookSpecs.isbnEqual(isbn))
//                .or(BookSpecs.titleLike(authorName))
//                .or(BookSpecs.genreEqual(genre))
//                .or(BookSpecs.publishDateEqual(publishDate));

        Specification<Book> specs = Specification.where((root, query, cb) -> cb.conjunction());
        if (StringUtils.isNotBlank(isbn)) {
            specs = specs.and(isbnEqual(isbn));
        }
        if (StringUtils.isNotBlank(authorName)) {
            specs = specs.and(authorNameLike(authorName));
        }
        if(StringUtils .isNotBlank(title)) {
            specs = specs.and(titleLike(title));
        }
        if (genre != null) {
            specs = specs.and(genreEqual(genre));
        }
        if (publishDate != null) {
            specs = specs.and(publishDateEqual(publishDate));
        }

        return repository.findAll(specs);
    }

    public void update(UUID id, CreateBookDTO dto) {
          findById(id).map(
                book -> {
                    Book bookEntity = mapper.toEntity(dto);
                    Author author = findExistingAuthor(dto.authorId());

                    book.setIsbn(bookEntity.getIsbn());
                    book.setAuthor(author);
                    book.setGenre(bookEntity.getGenre());
                    book.setPublishDate(bookEntity.getPublishDate());
                    book.setTitle(bookEntity.getTitle());
                    book.setPrice(bookEntity.getPrice());
                    validator.validate(book);
                    return repository.save(book);
                }).orElseThrow(() -> new BookNotFoundException(id));
    }

    private Author findExistingAuthor(UUID authorId){
        return authorRepository.findById(authorId).orElseThrow(() -> new BookNotFoundException(authorId));
    }
}
