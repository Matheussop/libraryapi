package io.github.libraryapi.repository;

import io.github.libraryapi.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, UUID>, JpaSpecificationExecutor<Book> {
    boolean existsByAuthor_Id(UUID authorId);

    Optional<Book> findByIsbn(String isbn);
}
