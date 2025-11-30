package com.example.prodapp.service;

import com.example.prodapp.repository.UserRepository;
import com.example.prodapp.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public User getUserById(Long id) {
        log.info("Fetching user with id={}", id);

        if (id <= 0) {
            log.warn("Invalid user id provided: {}", id);
            throw new IllegalArgumentException("User id must be positive");
        }

        User user = repo.findUserById(id);

        if (user == null) {
            log.warn("User not found for id={}", id);
        } else {
            log.debug("User retrieved from repository: {}", user);
        }

        return user;
    }

    public User createUser(String name) {
        log.info("Creating user with name={}", name);

        try {
            User saved = repo.saveUser(new User(null, name));
            log.debug("Saved user: {}", saved);
            return saved;
        } catch (Exception ex) {
            log.error("Error while saving user. name={}, error={}", name, ex.getMessage(), ex);
            throw ex;
        }
    }
}
