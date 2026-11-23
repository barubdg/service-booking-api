package com.portfolio.booking.repository;

import com.portfolio.booking.model.Service;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ServiceRepository {

    private final List<Service> services = new ArrayList<>();
    private Long nextId = 1L;

    public Service save(Service service)
    {
        Service serviceWithId = new Service(
                nextId++,
                service.getName(),
                service.getDescription(),
                service.getDurationMinutes(),
                service.getPrice(),
                service.isActive()
        );

        services.add(serviceWithId);
        return serviceWithId;
    }

    public List<Service> findAll()
    {
        return new ArrayList<>(services);
    }

    public Optional<Service> findById(Long id)
    {
        return services.stream()
                .filter(service -> service.getId().equals(id))
                .findFirst();
    }

    public Service update(Service service) {
        for (int i = 0; i < services.size(); i++) {
            if (services.get(i).getId().equals(service.getId())) {
                services.set(i, service);
                return service;
            }
        }

        throw new IllegalArgumentException(
                "Service not found with id " + service.getId()
        );
    }
}
