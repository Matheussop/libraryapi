package io.github.libraryapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "book")
@ToString(exclude = "authorId")
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
    @ManyToOne(cascade =  CascadeType.PERSIST) // fetch lazy don't return author unless called or in transaction
    @JoinColumn(name = "author_id")
    private Author authorId;

}
