package io.github.libraryapi.service;

import io.github.libraryapi.model.Author;
import io.github.libraryapi.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService {
    private final AuthorRepository repository;

    public AuthorService(AuthorRepository repository) {
        this.repository = repository;
    }

    public Author save(Author author) {
        return repository.save(author);
    }

    public Optional<Author> findById(UUID id) {
        return repository.findById(id);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public List<Author> findAll() {
        return repository.findAll();
    }

    public List<Author> findByName(String name) {
        return repository.findAll().stream()
                .filter(author -> author.getName().equalsIgnoreCase(name))
                .toList();
    }

    public List<Author> search(String name, String nationality) {
        if (name != null && nationality != null) {
            return repository.findAll().stream()
                    .filter(author -> (author.getName().equalsIgnoreCase(name)) && (author.getNationality().equalsIgnoreCase(nationality)))
                    .toList();
        } else if (name != null) {
            return findByName(name);
        } else if (nationality != null) {
            return repository.findAll().stream()
                    .filter(author -> author.getNationality().equalsIgnoreCase(nationality))
                    .toList();
        } else {
            return findAll();
        }
    }
}
