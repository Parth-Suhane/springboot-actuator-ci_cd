package com.example.prodapp.controller;

import com.example.prodapp.model.User;
import com.example.prodapp.service.UserService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Tag("unit")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @Test
    void getUserShouldReturnUser() throws Exception {
        User user = new User(1L, "Parth");
        when(service.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Parth"));
    }

    @Test
    void getUserShouldReturn404IfNotFound() throws Exception {
        when(service.getUserById(10L)).thenReturn(null);

        mockMvc.perform(get("/api/v1/users/10"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUserShouldReturn400ForInvalidId() throws Exception {
        when(service.getUserById(-1L))
                .thenThrow(new IllegalArgumentException("User id must be positive"));

        mockMvc.perform(get("/api/v1/users/-1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User id must be positive"));
    }

    @Test
    void createUserShouldReturnCreatedUser() throws Exception {
        User user = new User(1L, "Parth");

        when(service.createUser("Parth")).thenReturn(user);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Parth\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Parth"));
    }

    @Test
    void createUserShouldReturn400ForBlankName() throws Exception {
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Name cannot be empty"));
    }
}
