package com.portfolio.booking.service;

import com.portfolio.booking.model.*;
import com.portfolio.booking.repository.AppointmentRepository;
import com.portfolio.booking.repository.CustomerRepository;
import com.portfolio.booking.repository.ProfessionalRepository;
import com.portfolio.booking.repository.ServiceRepository;

import java.time.LocalDateTime;
import java.util.List;

@org.springframework.stereotype.Service
public class AppointmentService
{
    private final AppointmentRepository appointmentRepository;
    private final CustomerRepository customerRepository;
    private final ProfessionalRepository professionalRepository;
    private final ServiceRepository serviceRepository;

    public AppointmentService
            (AppointmentRepository appointmentRepository,
             CustomerRepository customerRepository,
             ProfessionalRepository professionalRepository,
             ServiceRepository serviceRepository)
    {
        this.appointmentRepository = appointmentRepository;
        this.customerRepository = customerRepository;
        this.professionalRepository = professionalRepository;
        this.serviceRepository = serviceRepository;
    }

    // Create Appointment
    public Appointment createAppointment(Long customerId, Long professionalId, Long serviceId, LocalDateTime startDateTime)
    {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        Professional professional = professionalRepository.findById(professionalId)
                .orElseThrow(() -> new IllegalArgumentException("Professional not found"));

        Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new IllegalArgumentException("Service not found"));

        // Ensure the appointment is scheduled in the future
        if(!startDateTime.isAfter(LocalDateTime.now()))
        {
            // start:10AM is after now: 10:15AM (False)
            // start:11AM is after now:10:30AM (True)
            throw new IllegalArgumentException("Appointment must start in the future");
        }

        LocalDateTime endDateTime = startDateTime.plusMinutes(service.getDurationMinutes());

        List<Appointment> professionalAppointments = appointmentRepository.findByProfessional(professionalId);

        boolean hasConflict = professionalAppointments.stream()
                .filter(appointment -> appointment.getStatus() == AppointmentStatus.SCHEDULED)
                .anyMatch(appointment -> startDateTime.isBefore(appointment.getEndDateTime()) &&
                        endDateTime.isAfter(appointment.getStartDateTime()));

        if(hasConflict)
        {
            throw new IllegalArgumentException("The professional already has an appointment at this time");
        }

        Appointment appointment = new Appointment
                (null, customer, professional, service, startDateTime, endDateTime, AppointmentStatus.SCHEDULED);

        return appointmentRepository.save(appointment);

    }

    public List<Appointment> getAllAppointments()
    {
        return appointmentRepository.findAll();
    }
}
