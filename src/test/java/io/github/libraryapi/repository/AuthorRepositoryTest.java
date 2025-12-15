package io.github.libraryapi.repository;

import io.github.libraryapi.model.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.UUID;

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

    @Test
    public void testAuthorFindAll() {
        Author author1 = new Author();
        author1.setName("Isaac Asimov");
        author1.setNationality("American");
        author1.setBirthDate(LocalDate.of(1920, 1, 2));

        Author author2 = new Author();
        author2.setName("Arthur C. Clarke");
        author2.setNationality("British");
        author2.setBirthDate(LocalDate.of(1917, 12, 16));

        authorRepository.save(author1);
        authorRepository.save(author2);

        var authorsCount = authorRepository.count();
        assert authorsCount >= 2;

        authorRepository.delete(author1);
        authorRepository.delete(author2);
    }

    @Test
    public void testAuthorFindById() {
        Author author = new Author();
        author.setName("J.R.R. Tolkien");
        author.setNationality("British");
        author.setBirthDate(LocalDate.of(1892, 1, 3));

        var savedAuthor = authorRepository.save(author);
        UUID authorId = savedAuthor.getId();

        var foundAuthor = authorRepository.findById(authorId);
        assert foundAuthor.isPresent();
    }

    @Test
    public void testAuthorDelete() {
        Author author = new Author();
        author.setName("Agatha Christie");
        author.setNationality("British");
        author.setBirthDate(LocalDate.of(1890, 9, 15));

        var savedAuthor = authorRepository.save(author);
        UUID authorId = savedAuthor.getId();

        authorRepository.deleteById(authorId);
        var deletedAuthor = authorRepository.findById(authorId);

        assert deletedAuthor.isEmpty();
    }
}
