package com.portfolio.booking.dto.response;

import com.portfolio.booking.model.Professional;

public record ProfessionalResponse (Long id, String name, String email, String specialty, boolean active) {

    public static ProfessionalResponse from(Professional professional)
    {
        return new ProfessionalResponse
                (
                        professional.getId(),
                        professional.getName(),
                        professional.getEmail(),
                        professional.getSpecialty(),
                        professional.isActive()
                );
    }
}
