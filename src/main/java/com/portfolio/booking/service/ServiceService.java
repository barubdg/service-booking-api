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
        if(service.getDurationMinutes()<=0)
        {
            throw new IllegalArgumentException("Service duration must be greater than zero");
        }

        if(service.getPrice().signum()<0)
        {
            throw new IllegalArgumentException("Service price cannot be negative");
        }

        return serviceRepository.save(service);
    }

    public List<Service> getAllServices()
    {
        return serviceRepository.findAll();
    }
}
