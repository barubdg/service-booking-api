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

    public Professional getProfessionalById(Long id) {
        return professionalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Professional not found."));
    }

    public Professional updateProfessional(Long id, Professional updatedProfessional) {
        Professional existingProfessional = getProfessionalById(id);

        if (professionalRepository.existsByEmailAndIdNot(updatedProfessional.getEmail(), id)) {
            throw new IllegalArgumentException("A professional with this email already exists.");
        }

        Professional professional = new Professional(
                existingProfessional.getId(),
                updatedProfessional.getName(),
                updatedProfessional.getEmail(),
                updatedProfessional.getSpecialty(),
                existingProfessional.isActive());

        return professionalRepository.update(professional);
    }

    public Professional activateProfessional(Long id) {
        Professional professional = getProfessionalById(id);

        Professional updatedProfessional = new Professional(
                professional.getId(),
                professional.getName(),
                professional.getEmail(),
                professional.getSpecialty(),
                true);

        return professionalRepository.update(updatedProfessional);
    }

    public Professional deactivateProfessional(Long id) {
        Professional professional = getProfessionalById(id);

        Professional updatedProfessional = new Professional(
                professional.getId(),
                professional.getName(),
                professional.getEmail(),
                professional.getSpecialty(),
                false);

        return professionalRepository.update(updatedProfessional);
    }
}
