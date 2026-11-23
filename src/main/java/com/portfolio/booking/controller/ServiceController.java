package com.portfolio.booking.controller;

import com.portfolio.booking.dto.request.ServiceRequest;
import com.portfolio.booking.dto.response.ServiceResponse;
import com.portfolio.booking.model.Service;
import com.portfolio.booking.service.ServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/services")
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService)
    {
        this.serviceService = serviceService;
    }

    @PostMapping
    public ResponseEntity<ServiceResponse> registerService(@RequestBody ServiceRequest request)
    {
        Service service = new Service(
                null,
                request.name(),
                request.description(),
                request.durationMinutes(),
                request.price()
        );

        Service registeredService = serviceService.registerService(service);
        ServiceResponse response = ServiceResponse.from(registeredService);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ServiceResponse>> getAllServices()
    {
        List<ServiceResponse> services = serviceService.getAllServices().stream().map(ServiceResponse::from).toList();
        return ResponseEntity.ok(services);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponse> getServiceById(@PathVariable Long id) {
        Service service = serviceService.getServiceById(id);
        ServiceResponse response = ServiceResponse.from(service);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponse> updateService(@PathVariable Long id, @RequestBody ServiceRequest request) {
        Service service = new Service(
                id,
                request.name(),
                request.description(),
                request.durationMinutes(),
                request.price()
        );

        Service updatedService = serviceService.updateService(id, service);
        ServiceResponse response = ServiceResponse.from(updatedService);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<ServiceResponse> activateService(@PathVariable Long id) {
        Service service = serviceService.activateService(id);
        ServiceResponse response = ServiceResponse.from(service);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ServiceResponse> deactivateService(@PathVariable Long id) {
        Service service = serviceService.deactivateService(id);
        ServiceResponse response = ServiceResponse.from(service);
        return ResponseEntity.ok(response);
    }
}
