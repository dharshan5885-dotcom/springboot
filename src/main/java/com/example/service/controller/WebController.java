package com.example.service.controller;

import com.example.service.entity.ServiceRequest;
import com.example.service.service.ServiceRequestService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebController {

    private final ServiceRequestService service;

    public WebController(ServiceRequestService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("requests", service.getAll());
        model.addAttribute("total", service.getAll().size());
        model.addAttribute("pending", service.countByStatus("Pending"));
        model.addAttribute("inProgress", service.countByStatus("In Progress"));
        model.addAttribute("resolved", service.countByStatus("Resolved"));
        return "dashboard";
    }

    @GetMapping("/requests")
    public String requests(Model model) {
        model.addAttribute("requests", service.getAll());
        return "requests";
    }

    @GetMapping("/requests/new")
    public String newRequest(Model model) {
        ServiceRequest request = new ServiceRequest();
        request.setStatus("Pending");
        model.addAttribute("request", request);
        model.addAttribute(BindingResult.MODEL_KEY_PREFIX + "request",
            new BeanPropertyBindingResult(request, "request"));
        return "add-request";
    }

    @PostMapping("/requests/save")
    public String save(@Valid @ModelAttribute("request") ServiceRequest request,
                       BindingResult result) {
        if (result.hasErrors()) {
            return "add-request";
        }
        service.create(request);
        return "redirect:/requests";
    }

    @GetMapping("/requests/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        ServiceRequest request = service.getById(id);
        model.addAttribute("request", request);
        model.addAttribute(BindingResult.MODEL_KEY_PREFIX + "request",
            new BeanPropertyBindingResult(request, "request"));
        return "edit-request";
    }

    @PostMapping("/requests/update/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("request") ServiceRequest request,
                         BindingResult result) {
        if (result.hasErrors()) {
            request.setId(id);
            return "edit-request";
        }
        service.update(id, request);
        return "redirect:/requests";
    }

    @GetMapping("/requests/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/requests";
    }
}
