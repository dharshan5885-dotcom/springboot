package com.example.service.service;

import com.example.service.entity.ServiceRequest;
import com.example.service.exception.ResourceNotFoundException;
import com.example.service.repository.ServiceRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceRequestService {

    private final ServiceRequestRepository repository;

    public ServiceRequestService(ServiceRequestRepository repository) {
        this.repository = repository;
    }

    public List<ServiceRequest> getAll() {
        return repository.findAll();
    }

    public ServiceRequest getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service request not found: " + id));
    }

    public ServiceRequest create(ServiceRequest request) {
        request.setStatus("Pending");
        return repository.save(request);
    }

    public ServiceRequest update(Long id, ServiceRequest request) {
        ServiceRequest existing = getById(id);
        existing.setTitle(request.getTitle());
        existing.setCategory(request.getCategory());
        existing.setDescription(request.getDescription());
        existing.setPriority(request.getPriority());
        existing.setStatus(request.getStatus());
        existing.setCustomerName(request.getCustomerName());
        existing.setCustomerEmail(request.getCustomerEmail());
        return repository.save(existing);
    }

    public void delete(Long id) {
        ServiceRequest existing = getById(id);
        repository.delete(existing);
    }

    public long countByStatus(String status) {
        return repository.findAll().stream()
                .filter(r -> status.equalsIgnoreCase(r.getStatus()))
                .count();
    }
}
