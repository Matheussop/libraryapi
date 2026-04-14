package io.github.libraryapi.service;

import io.github.libraryapi.model.User;
import io.github.libraryapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public void save(User user){
        var password = user.getPassword();
        user.setPassword(encoder.encode(password));
        repository.save(user);
    }

    public User findByUsername(String username){
        return repository.findByUsername(username);
    }

    public User findByEmail(String email){
        return repository.findByEmail(email);
    }
}
