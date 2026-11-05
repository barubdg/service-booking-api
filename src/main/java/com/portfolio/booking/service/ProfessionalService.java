package com.portfolio.booking.service;

import com.portfolio.booking.model.Professional;
import com.portfolio.booking.repository.ProfessionalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessionalService
{
    private final ProfessionalRepository professionalRepository;

    public ProfessionalService(ProfessionalRepository professionalRepository)
    {
        this.professionalRepository = professionalRepository;
    }

    // Register a Professional
    public Professional registerProfessional(Professional professional)
    {
        if(professionalRepository.existsByEmail(professional.getEmail()))
        {
            throw new IllegalArgumentException("A professional with this email already exists");
        }



        return professionalRepository.save(professional);
    }

    public List<Professional> getAllProfessionals()
    {
        return professionalRepository.findAll();
    }
}
