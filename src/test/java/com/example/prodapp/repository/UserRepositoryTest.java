package com.example.prodapp.repository;

import com.example.prodapp.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
class UserRepositoryTest {

    private UserRepository repo;

    @BeforeEach
    void setup() {
        repo = new UserRepository();
    }

    @Test
    void saveUserShouldAssignIdAndStoreUser() {
        User saved = repo.saveUser(new User(null, "Parth"));

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Parth");
        assertThat(repo.findUserById(saved.getId())).isEqualTo(saved);
    }

    @Test
    void findUserByIdShouldReturnNullIfUserNotFound() {
        User user = repo.findUserById(999L);
        assertThat(user).isNull();
    }
}
