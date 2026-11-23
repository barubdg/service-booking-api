package com.portfolio.booking.service;

import com.portfolio.booking.model.Service;
import com.portfolio.booking.repository.ServiceRepository;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceService {

    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public Service registerService(Service service)
    {
        validateService(service);

        return serviceRepository.save(service);
    }

    public List<Service> getAllServices()
    {
        return serviceRepository.findAll();
    }

    public Service getServiceById(Long id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Service not found."));
    }

    public Service updateService(Long id, Service updatedService) {
        Service existingService = getServiceById(id);

        validateService(updatedService);

        Service service = new Service(
                existingService.getId(),
                updatedService.getName(),
                updatedService.getDescription(),
                updatedService.getDurationMinutes(),
                updatedService.getPrice(),
                existingService.isActive());

        return serviceRepository.update(service);
    }

    public Service activateService(Long id) {
        Service service = getServiceById(id);

        Service updatedService = new Service(
                service.getId(),
                service.getName(),
                service.getDescription(),
                service.getDurationMinutes(),
                service.getPrice(),
                true);

        return serviceRepository.update(updatedService);
    }

    public Service deactivateService(Long id) {
        Service service = getServiceById(id);

        Service updatedService = new Service(
                service.getId(),
                service.getName(),
                service.getDescription(),
                service.getDurationMinutes(),
                service.getPrice(),
                false);

        return serviceRepository.update(updatedService);
    }

    private void validateService(Service service) {
        if (service.getDurationMinutes() == null || service.getDurationMinutes() <= 0) {
            throw new IllegalArgumentException("Service duration must be greater than zero.");
        }

        if (service.getPrice() == null || service.getPrice().signum() < 0) {
            throw new IllegalArgumentException("Service price cannot be negative.");
        }
    }
}
