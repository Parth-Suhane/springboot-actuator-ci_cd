package com.example.prodapp.service;

import com.example.prodapp.model.User;
import com.example.prodapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository repo;
    private UserService service;

    @BeforeEach
    void setup() {
        repo = mock(UserRepository.class);
        service = new UserService(repo);
    }

    @Test
    void getUserByIdShouldReturnUserIfExists() {
        User user = new User(1L, "Parth");
        when(repo.findUserById(1L)).thenReturn(user);

        User result = service.getUserById(1L);

        assertThat(result).isEqualTo(user);
        verify(repo).findUserById(1L);
    }

    @Test
    void getUserByIdShouldThrowExceptionForInvalidId() {
        assertThatThrownBy(() -> service.getUserById(-1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User id must be positive");
    }

    @Test
    void getUserByIdShouldReturnNullWhenUserNotFound() {
        when(repo.findUserById(10L)).thenReturn(null);

        User result = service.getUserById(10L);

        assertThat(result).isNull();
        verify(repo).findUserById(10L);
    }

    @Test
    void createUserShouldSaveUser() {
        User saved = new User(1L, "Parth");
        when(repo.saveUser(any(User.class))).thenReturn(saved);

        User result = service.createUser("Parth");

        assertThat(result).isEqualTo(saved);
        verify(repo).saveUser(any(User.class));
    }

    @Test
    void createUserShouldForwardExceptions() {
        when(repo.saveUser(any(User.class))).thenThrow(new RuntimeException("DB error"));

        assertThatThrownBy(() -> service.createUser("Parth"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("DB error");
    }
}
