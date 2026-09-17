package com.example.service.controller;

import com.example.service.entity.ServiceRequest;
import com.example.service.service.ServiceRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceRequestApiControllerTest {

    @Mock
    private ServiceRequestService service;

    private ServiceRequestApiController controller;

    @BeforeEach
    void setUp() {
        controller = new ServiceRequestApiController(service);
    }

    @Test
    void getAllDelegatesToService() {
        List<ServiceRequest> requests = List.of(new ServiceRequest());
        when(service.getAll()).thenReturn(requests);

        assertEquals(requests, controller.getAll());
    }

    @Test
    void getByIdDelegatesToService() {
        ServiceRequest request = new ServiceRequest();
        when(service.getById(1L)).thenReturn(request);

        assertEquals(request, controller.getById(1L));
    }

    @Test
    void createDelegatesToService() {
        ServiceRequest request = new ServiceRequest();
        when(service.create(request)).thenReturn(request);

        assertEquals(request, controller.create(request));
    }

    @Test
    void updateDelegatesToService() {
        ServiceRequest request = new ServiceRequest();
        when(service.update(2L, request)).thenReturn(request);

        assertEquals(request, controller.update(2L, request));
    }

    @Test
    void deleteDelegatesToService() {
        controller.delete(3L);

        verify(service).delete(3L);
    }
}
