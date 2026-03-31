package io.github.libraryapi.repository;

import io.github.libraryapi.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, UUID> {
    boolean existsByAuthor_Id(UUID authorId);

    Optional<Book> findByIsbn(String isbn);
}
