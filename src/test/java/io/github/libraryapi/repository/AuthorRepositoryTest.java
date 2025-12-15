package io.github.libraryapi.repository;

import io.github.libraryapi.model.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void testAuthorRepositoryNotNull() {
        assert authorRepository != null;
    }

    @Test
    public void testAuthorSaveAndFind() {
        Author author = new Author();
        author.setName("J.K. Rowling");
        author.setNationality("British");
        author.setBirthDate(LocalDate.of(1990, 1, 1));

        var result = authorRepository.save(author);
        assertNotNull(result.getId());

        authorRepository.deleteById(result.getId());
    }

    @Test
    public void testAuthorUpdate() {
        Author author = new Author();
        author.setName("George R.R. Martin");
        author.setNationality("American");
        author.setBirthDate(LocalDate.of(1948, 9, 20));

        var savedAuthor = authorRepository.save(author);
        savedAuthor.setNationality("USA");
        var updatedAuthor = authorRepository.save(savedAuthor);

        assert updatedAuthor.getNationality().equals("USA");
        authorRepository.delete(author);
    }
}
