package com.example.prodapp.controller;

import com.example.prodapp.model.User;
import com.example.prodapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        log.info("API call: GET /users/{}", id);

        try {
            User user = service.getUserById(id);
            if (user == null) {
                log.warn("User not found for id={}", id);
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException ex) {
            log.warn("Invalid request for id={}. error={}", id, ex.getMessage());
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request) {
        log.info("API call: POST /users with payload={}", request);

        if (request.getName() == null || request.getName().isBlank()) {
            log.warn("Validation failure: name is blank");
            return ResponseEntity.badRequest().body("Name cannot be empty");
        }

        User created = service.createUser(request.getName());
        return ResponseEntity.ok(created);
    }

    public static class CreateUserRequest {
        private String name;

        public String getName() { return name; }

        public void setName(String name) { this.name = name; }

        @Override
        public String toString() {
            return "CreateUserRequest{name='" + name + "'}";
        }
    }
}
