package com.example.service.entity;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ServiceRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void beforeInsertSetsDateAndDefaultStatus() {
        ServiceRequest request = new ServiceRequest();
        request.setStatus(" ");

        request.beforeInsert();

        assertNotNull(request.getCreatedDate());
        assertEquals("Pending", request.getStatus());
    }

    @Test
    void beforeInsertPreservesExistingDateAndStatus() {
        LocalDateTime createdDate = LocalDateTime.of(2025, 1, 1, 10, 0);
        ServiceRequest request = new ServiceRequest();
        request.setCreatedDate(createdDate);
        request.setStatus("Resolved");

        request.beforeInsert();

        assertEquals(createdDate, request.getCreatedDate());
        assertEquals("Resolved", request.getStatus());
    }

    @Test
    void validRequestHasNoConstraintViolations() {
        ServiceRequest request = new ServiceRequest();
        request.setTitle("Printer issue");
        request.setCategory("Hardware");
        request.setDescription("The office printer is not responding");
        request.setPriority("High");
        request.setStatus("Pending");
        request.setCustomerName("Alex");
        request.setCustomerEmail("alex@example.com");

        assertTrue(validator.validate(request).isEmpty());
    }

    @Test
    void invalidRequestReportsRequiredFields() {
        ServiceRequest request = new ServiceRequest();

        var violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("title")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("customerEmail")));
    }
}
