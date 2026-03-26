package io.github.libraryapi.validator;

import io.github.libraryapi.exception.DuplicatedRegisterException;
import io.github.libraryapi.model.Author;
import io.github.libraryapi.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthorValidator {

    private final AuthorRepository repository;

    public void validate(Author author) {
        if (author.getName() == null || author.getName().isBlank()) {
            throw new IllegalArgumentException("Author name cannot be null or blank");
        }

        if(registerAuthorExist(author)) {
            throw new DuplicatedRegisterException("Author with the same ID already exists");
        }
    }

     private boolean registerAuthorExist(Author author) {
        Optional<Author> authorFound = repository.findByNameAndBirthDateAndNationality(author.getName(), author.getBirthDate(), author.getNationality());
        if(author.getId() == null) {
            return authorFound.isPresent();
        }
        return authorFound.isPresent() && author.getId().equals(authorFound.get().getId());

     }
}
