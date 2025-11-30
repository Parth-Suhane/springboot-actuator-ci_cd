package com.example.prodapp.repository;

import com.example.prodapp.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository {

    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);

    private final Map<Long, User> store = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);

    public User findUserById(Long id) {
        log.debug("Looking for user in store. id={}", id);
        return store.get(id);
    }

    public User saveUser(User user) {
        long id = idGen.getAndIncrement();
        User newUser = new User(id, user.getName());

        store.put(id, newUser);
        log.info("User saved. id={}, name={}", newUser.getId(), newUser.getName());

        return newUser;
    }
}
