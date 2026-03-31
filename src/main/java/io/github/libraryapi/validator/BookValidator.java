package io.github.libraryapi.validator;

import io.github.libraryapi.exception.DuplicatedRegisterException;
import io.github.libraryapi.exception.InvalidFieldException;
import io.github.libraryapi.model.Book;
import io.github.libraryapi.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookValidator {
    private static final int PRICE_REQUIREMENT_YEAR = Calendar.getInstance().get(Calendar.YEAR);

    private final BookRepository repository;

    public void validate(Book book){
        if(existBookWithIsbn(book)){
            throw new DuplicatedRegisterException("ISBN already exists!");
        }

        if(isPriceRequiredNull(book)){
            throw new InvalidFieldException("price", String.format("For books published from %d onwards, the price is required.", PRICE_REQUIREMENT_YEAR));
        }
    }

    private boolean isPriceRequiredNull(Book book) {
        return book.getPrice() == null &&
                book.getPublishDate().getYear() >= PRICE_REQUIREMENT_YEAR;
    }

    private boolean existBookWithIsbn(Book book){
        Optional<Book> foundedBook = repository.findByIsbn(book.getIsbn());

        if(book.getId() == null){
            return foundedBook.isPresent();
        }

        return foundedBook
                .map(Book::getId)
                .stream()
                .anyMatch(id -> !id.equals(book.getId()));
    }
}
