package com.example.service.controller;

import com.example.service.entity.ServiceRequest;
import com.example.service.service.ServiceRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class WebControllerTest {

    @Mock
    private ServiceRequestService service;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    private WebController controller;

    @BeforeEach
    void setUp() {
        controller = new WebController(service);
    }

    @Test
    void dashboardPopulatesCountsAndReturnsDashboardView() {
        List<ServiceRequest> requests = List.of(new ServiceRequest(), new ServiceRequest());
        when(service.getAll()).thenReturn(requests);
        when(service.countByStatus("Pending")).thenReturn(1L);
        when(service.countByStatus("In Progress")).thenReturn(1L);
        when(service.countByStatus("Resolved")).thenReturn(0L);

        assertEquals("dashboard", controller.dashboard(model));
        verify(model).addAttribute("requests", requests);
        verify(model).addAttribute("total", 2);
        verify(model).addAttribute("pending", 1L);
        verify(model).addAttribute("inProgress", 1L);
        verify(model).addAttribute("resolved", 0L);
    }

    @Test
    void requestsPopulatesRequestsAndReturnsRequestsView() {
        List<ServiceRequest> requests = List.of(new ServiceRequest());
        when(service.getAll()).thenReturn(requests);

        assertEquals("requests", controller.requests(model));
        verify(model).addAttribute("requests", requests);
    }

    @Test
    void newRequestAddsPendingRequestAndReturnsAddView() {
        assertEquals("add-request", controller.newRequest(model));
        verify(model).addAttribute(eq("request"), any(ServiceRequest.class));
    }

    @Test
    void saveWithErrorsReturnsAddViewWithoutCreating() {
        ServiceRequest request = new ServiceRequest();
        when(bindingResult.hasErrors()).thenReturn(true);

        assertEquals("add-request", controller.save(request, bindingResult));
    }

    @Test
    void saveWithoutErrorsCreatesAndRedirects() {
        ServiceRequest request = new ServiceRequest();
        when(bindingResult.hasErrors()).thenReturn(false);

        assertEquals("redirect:/requests", controller.save(request, bindingResult));
        verify(service).create(request);
    }

    @Test
    void editLoadsRequestAndReturnsEditView() {
        ServiceRequest request = new ServiceRequest();
        when(service.getById(5L)).thenReturn(request);

        assertEquals("edit-request", controller.edit(5L, model));
        verify(model).addAttribute("request", request);
    }

    @Test
    void updateWithErrorsRestoresIdAndReturnsEditView() {
        ServiceRequest request = new ServiceRequest();
        when(bindingResult.hasErrors()).thenReturn(true);

        assertEquals("edit-request", controller.update(6L, request, bindingResult));
        assertEquals(6L, request.getId());
    }

    @Test
    void updateWithoutErrorsUpdatesAndRedirects() {
        ServiceRequest request = new ServiceRequest();
        when(bindingResult.hasErrors()).thenReturn(false);

        assertEquals("redirect:/requests", controller.update(6L, request, bindingResult));
        verify(service).update(6L, request);
    }

    @Test
    void deleteDelegatesAndRedirects() {
        assertEquals("redirect:/requests", controller.delete(8L));
        verify(service).delete(8L);
    }
}
