package com.example.service.service;

import com.example.service.entity.ServiceRequest;
import com.example.service.exception.ResourceNotFoundException;
import com.example.service.repository.ServiceRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceRequestServiceTest {

    @Mock
    private ServiceRequestRepository repository;

    private ServiceRequestService service;

    @BeforeEach
    void setUp() {
        service = new ServiceRequestService(repository);
    }

    @Test
    void getAllReturnsRepositoryResults() {
        List<ServiceRequest> requests = List.of(new ServiceRequest());
        when(repository.findAll()).thenReturn(requests);

        assertEquals(requests, service.getAll());
        verify(repository).findAll();
    }

    @Test
    void getByIdReturnsRequestWhenPresent() {
        ServiceRequest request = new ServiceRequest();
        when(repository.findById(7L)).thenReturn(Optional.of(request));

        assertEquals(request, service.getById(7L));
    }

    @Test
    void getByIdThrowsWhenRequestIsMissing() {
        when(repository.findById(7L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> service.getById(7L));

        assertEquals("Service request not found: 7", exception.getMessage());
    }

    @Test
    void createSetsPendingStatusAndSavesRequest() {
        ServiceRequest request = new ServiceRequest();
        request.setStatus("Resolved");
        when(repository.save(request)).thenReturn(request);

        assertEquals(request, service.create(request));
        assertEquals("Pending", request.getStatus());
        verify(repository).save(request);
    }

    @Test
    void updateCopiesEditableFieldsAndSavesExistingRequest() {
        ServiceRequest existing = new ServiceRequest();
        ServiceRequest update = new ServiceRequest();
        update.setTitle("Updated title");
        update.setCategory("Hardware");
        update.setDescription("Updated description");
        update.setPriority("High");
        update.setStatus("In Progress");
        update.setCustomerName("Alex");
        update.setCustomerEmail("alex@example.com");
        when(repository.findById(3L)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);

        assertEquals(existing, service.update(3L, update));
        assertEquals("Updated title", existing.getTitle());
        assertEquals("Hardware", existing.getCategory());
        assertEquals("Updated description", existing.getDescription());
        assertEquals("High", existing.getPriority());
        assertEquals("In Progress", existing.getStatus());
        assertEquals("Alex", existing.getCustomerName());
        assertEquals("alex@example.com", existing.getCustomerEmail());
        verify(repository).save(existing);
    }

    @Test
    void deleteRemovesExistingRequest() {
        ServiceRequest existing = new ServiceRequest();
        when(repository.findById(4L)).thenReturn(Optional.of(existing));

        service.delete(4L);

        verify(repository).delete(existing);
    }

    @Test
    void countByStatusIgnoresCase() {
        ServiceRequest pending = new ServiceRequest();
        pending.setStatus("Pending");
        ServiceRequest resolved = new ServiceRequest();
        resolved.setStatus("Resolved");
        when(repository.findAll()).thenReturn(List.of(pending, resolved));

        assertEquals(1, service.countByStatus("pending"));
    }
}
