package io.github.libraryapi.repository;

import io.github.libraryapi.model.Author;
import io.github.libraryapi.model.Book;
import io.github.libraryapi.model.BookGenre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void save() {
        Author author = new Author();

        author.setName("John Doe");
        author.setNationality("American");
        author.setBirthDate(LocalDate.of(1970, 5, 15));

        Book book = new Book();
        book.setIsbn("978-3-16-148410-0");
        book.setTitle("Sample Book");
        book.setPrice(BigDecimal.valueOf(19.99));
        book.setGenre(BookGenre.FICTION);
        book.setPublishDate(LocalDate.of(2020, 1, 1));
        book.setCreatedAt(LocalDateTime.now());

        book.setAuthor(author);
        Book savedBook = bookRepository.save(book);
        assertNotNull(savedBook.getId());
        assertNotNull(savedBook.getAuthor());
        assertNotNull(savedBook.getAuthor().getId());

    }

    @Test
    void findById() {
        Author author = new Author();
        author.setName("Jane Smith");
        author.setNationality("British");
        author.setBirthDate(LocalDate.of(1980, 3, 22));

        Book book = new Book();
        book.setIsbn("978-1-23-456789-0");
        book.setTitle("Another Sample Book");
        book.setPrice(BigDecimal.valueOf(29.99));
        book.setGenre(BookGenre.FICTION);
        book.setPublishDate(LocalDate.of(2021, 6, 15));
        book.setCreatedAt(LocalDateTime.now());

        book.setAuthor(author);
        Book savedBook = bookRepository.save(book);

        var foundBook = bookRepository.findById(savedBook.getId());
        assert foundBook.isPresent();
        assert foundBook.get().getTitle().equals("Another Sample Book");

        bookRepository.deleteById(savedBook.getId());
        authorRepository.deleteById(author.getId());
    }
}
