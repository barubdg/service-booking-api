package com.portfolio.booking.repository;

import com.portfolio.booking.model.Professional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProfessionalRepository {

    private final List<Professional> professionals = new ArrayList<>();
    private Long nextId = 1L;

    public Professional save(Professional professional)
    {
        Professional profesionalWithId = new Professional
                        (nextId++,
                        professional.getName(),
                        professional.getEmail(),
                        professional.getSpecialty(),
                        professional.isActive());

        professionals.add(profesionalWithId);
        return profesionalWithId;
    }

    public List<Professional> findAll()
    {
        return new ArrayList<>(professionals);
    }

    public Optional<Professional> findById(Long id)
    {
        return professionals.stream().
                filter(professional -> professional.getId().equals(id))
                .findFirst();
    }

    public boolean existsByEmail(String email)
    {
        return professionals.stream().
                anyMatch(professional -> professional.getEmail().equalsIgnoreCase(email));
    }

    public boolean existsByEmailAndIdNot(String email, Long id) {
        return professionals.stream()
                .anyMatch(professional -> professional.getEmail().equalsIgnoreCase(email)
                                && !professional.getId().equals(id)
                );
    }

    public Professional update(Professional professional) {
        for (int i = 0; i < professionals.size(); i++) {
            if (professionals.get(i).getId().equals(professional.getId())) {
                professionals.set(i, professional);
                return professional;
            }
        }

        throw new IllegalArgumentException(
                "Professional not found with id " + professional.getId()
        );
    }

}
