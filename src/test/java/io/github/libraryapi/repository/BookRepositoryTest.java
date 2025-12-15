package io.github.libraryapi.repository;

import io.github.libraryapi.model.Author;
import io.github.libraryapi.model.Book;
import io.github.libraryapi.model.BookGenre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
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
        book.setPublish_date(LocalDate.of(2020, 1, 1));

        book.setAuthorId(author);
        Book savedBook = bookRepository.save(book);
        assert savedBook.getId() != null;
        bookRepository.deleteById(savedBook.getId());
        authorRepository.deleteById(author.getId());

    }
}
