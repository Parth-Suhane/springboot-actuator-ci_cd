package com.example.prodapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/greetings")
public class GreetingController {

    private static final Logger log = LoggerFactory.getLogger(GreetingController.class);

    @GetMapping
    public ResponseEntity<String> greet(@RequestParam(defaultValue = "World") String name) {
        log.info("Received greeting request for name={}", name);
        String message = "Hello, " + name + "!";
        return ResponseEntity.ok(message);
    }

    @PostMapping
    public ResponseEntity<String> greetCustom(@RequestBody GreetingRequest request) {
        log.debug("GreetingRequest payload: {}", request);
        String message = "Hello, " + request.getName() + "! " + request.getMessage();
        return ResponseEntity.ok(message);
    }

    public static class GreetingRequest {
        private String name;
        private String message;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return "GreetingRequest{" +
                    "name='" + name + '\'' +
                    ", message='" + message + '\'' +
                    '}';
        }
    }
}
