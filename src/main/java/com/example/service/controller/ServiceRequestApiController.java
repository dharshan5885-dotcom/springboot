package com.example.service.controller;

import com.example.service.entity.ServiceRequest;
import com.example.service.service.ServiceRequestService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class ServiceRequestApiController {

    private final ServiceRequestService service;

    public ServiceRequestApiController(ServiceRequestService service) {
        this.service = service;
    }

    @GetMapping
    public List<ServiceRequest> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ServiceRequest getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceRequest create(@Valid @RequestBody ServiceRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public ServiceRequest update(@PathVariable Long id,
                                 @Valid @RequestBody ServiceRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
