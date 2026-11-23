package com.portfolio.booking.dto.response;

import com.portfolio.booking.model.Service;

import java.math.BigDecimal;

public record ServiceResponse (Long id, String name, String description, Integer durationMinutes, BigDecimal price, boolean active)
{
    public static ServiceResponse from(Service service)
    {
        return new ServiceResponse(
                service.getId(),
                service.getName(),
                service.getDescription(),
                service.getDurationMinutes(),
                service.getPrice(),
                service.isActive()
        );
    }
}
