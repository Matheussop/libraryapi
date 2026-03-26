package io.github.libraryapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "book")
@ToString(exclude = "author")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String isbn;
    private String title;
    private LocalDate publish_date;
    @Enumerated(EnumType.STRING)
    private BookGenre genre;
    @Column(precision = 18, scale = 2)
    private BigDecimal price;
    @ManyToOne(cascade = CascadeType.PERSIST) // fetch lazy don't return author unless called or in transaction
    @JoinColumn(name = "id_author")
    private Author author;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
