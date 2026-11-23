package com.portfolio.booking.repository;

import com.portfolio.booking.model.Appointment;
import com.portfolio.booking.model.AppointmentStatus;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class AppointmentRepository {

    private final List<Appointment> appointments = new ArrayList<>();
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
                appointment.getStatus(),
                appointment.getCreatedAt()
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

    public List<Appointment> findByProfessional(Long professionalId)
    {
        return appointments.stream()
                .filter(appointment -> appointment.getProfessional().getId().equals(professionalId))
                .toList();
    }

    public List<Appointment> findByCustomer(Long customerId) {
        return appointments.stream()
                .filter(appointment -> appointment.getCustomer().getId().equals(customerId))
                .toList();
    }

    public List<Appointment> findByDate(LocalDate date) {
        return appointments.stream()
                .filter(appointment -> appointment.getStartDateTime().toLocalDate().equals(date))
                .toList();
    }

    public List<Appointment> findByStatus(AppointmentStatus status) {
        return appointments.stream()
                .filter(appointment -> appointment.getStatus() == status)
                .toList();
    }

    public List<Appointment> findByFilters(LocalDate date, AppointmentStatus status) {
        return appointments.stream()
                .filter(appointment -> date == null || appointment.getStartDateTime().toLocalDate().equals(date))
                .filter(appointment -> status == null || appointment.getStatus() == status)
                .toList();
    }

    public Appointment update(Appointment appointment)
    {
        if(appointment == null || appointment.getId() == null)
        {
            throw new IllegalArgumentException("Appointment and appointment ID are required");
        }
        for (int i = 0; i < appointments.size(); i++)
        {
            Appointment current = appointments.get(i);
            if(Objects.equals(current.getId(), appointment.getId()))
            {
                appointments.set(i, appointment);
                return appointment;
            }
        }
        throw new IllegalArgumentException("Appointment not found with id"+ appointment.getId());
    }
}
