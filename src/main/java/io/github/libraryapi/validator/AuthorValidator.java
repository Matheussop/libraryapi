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
        if(registerAuthorExist(author)) {
            throw new DuplicatedRegisterException("Author already exists");
        }
    }

     private boolean registerAuthorExist(Author author) {
        Optional<Author> authorFound = repository.findByNameAndBirthDateAndNationality(
                author.getName(),
                author.getBirthDate(),
                author.getNationality());
        if (author.getId() == null) {
            return false;
        }
         return authorFound.filter(value -> !author.getId().equals(value.getId())).isPresent();

     }
}
