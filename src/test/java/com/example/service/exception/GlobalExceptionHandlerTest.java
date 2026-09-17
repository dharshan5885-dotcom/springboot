package com.example.service.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleNotFoundReturnsNotFoundResponse() {
        ResponseEntity<Map<String, String>> response = handler.handleNotFound(
                new ResourceNotFoundException("Missing request"));

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(Map.of("error", "Missing request"), response.getBody());
    }
}
