package com.portfolio.booking.repository;

import com.portfolio.booking.model.Appointment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AppointmentRepository {

    private final ArrayList<Appointment> appointments = new ArrayList<>();
    private Long nextId = 1L;

    public Appointment save(Appointment appointment)
    {
        Appointment appointmentWithId = new Appointment(
                nextId++,
                appointment.getCustomer(),
                appointment.getProfessional(),
                appointment.getService(),
                appointment.getStartDateTime(),
                appointment.getEndDateTime(),
                appointment.getStatus()
        );

        appointments.add(appointmentWithId);
        return appointmentWithId;
    }

    public List<Appointment> findAll()
    {
        return new ArrayList<>(appointments); // create a copy of original Arraylist
    }

    public Optional<Appointment> findById(Long id)
    {
        return appointments.stream()
                .filter(appointment -> appointment.getId().equals(id))
                .findFirst();
    }

    /* What method would we need in AppointmentRepository to retrieve appointments belonging to a specific professional? */
    public List<Appointment> findByProfessional(Long professionalId)
    {
        return appointments.stream()
                .filter(appointment -> appointment.getProfessional().getId().equals(professionalId))
                .toList();
    }
}
