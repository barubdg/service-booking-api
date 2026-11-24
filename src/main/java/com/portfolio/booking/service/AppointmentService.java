package com.portfolio.booking.service;

import com.portfolio.booking.model.*;
import com.portfolio.booking.repository.AppointmentRepository;
import com.portfolio.booking.repository.CustomerRepository;
import com.portfolio.booking.repository.ProfessionalRepository;
import com.portfolio.booking.repository.ServiceRepository;

import com.portfolio.booking.exception.BusinessException;
import com.portfolio.booking.exception.ResourceNotFoundException;

import java.time.LocalDate;
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
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        Professional professional = professionalRepository.findById(professionalId)
                .orElseThrow(() -> new ResourceNotFoundException("Professional not found"));

        Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));

        if (!professional.isActive()) {
            throw new BusinessException("Inactive professionals cannot receive appointments.");
        }

        if (!service.isActive()) {
            throw new BusinessException("Inactive services cannot be booked.");
        }

        validateFutureDate(startDateTime);

        LocalDateTime endDateTime = startDateTime.plusMinutes(service.getDurationMinutes());

        validateNoConflict(professionalId, startDateTime, endDateTime, null);

        Appointment appointment = new Appointment
                (null, customer, professional, service, startDateTime, endDateTime, AppointmentStatus.SCHEDULED, LocalDateTime.now());

        return appointmentRepository.save(appointment);

    }

    public List<Appointment> getAllAppointments()
    {
        return appointmentRepository.findAll();
    }

    public List<Appointment> getAppointmentsByFilters(LocalDate date, AppointmentStatus status) {
        return appointmentRepository.findByFilters(date, status);
    }

    public Appointment getAppointmentById(Long id)
    {
        return appointmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));
    }

    public List<Appointment> getAppointmentsByCustomer(Long customerId) {
        customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found."));

        return appointmentRepository.findByCustomer(customerId);
    }

    public List<Appointment> getAppointmentsByProfessional(Long professionalId)
    {
        professionalRepository.findById(professionalId)
                .orElseThrow(() -> new ResourceNotFoundException("Professional not found"));

        return appointmentRepository.findByProfessional(professionalId);
    }

    public Appointment updateAppointment(Long appointmentId, Long customerId, Long professionalId, Long serviceId, LocalDateTime startDateTime)
    {

        // Verify that Appointment exists.
        Appointment existingAppointment = getAppointmentById(appointmentId);

        if (existingAppointment.getStatus() != AppointmentStatus.SCHEDULED) {
            throw new BusinessException("Only scheduled appointments can be updated.");
        }

        // Retrieve the related entities (Customer, Professional and Service)
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        Professional professional = professionalRepository.findById(professionalId)
                .orElseThrow(() -> new ResourceNotFoundException("Professional not found"));

        Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));

        if (!professional.isActive()) {
            throw new BusinessException("Inactive professionals cannot receive appointments.");
        }

        if (!service.isActive()) {
            throw new BusinessException("Inactive services cannot be booked.");
        }

        validateFutureDate(startDateTime);

        // Calculate the new end date
        LocalDateTime endDateTime = startDateTime.plusMinutes(service.getDurationMinutes());

        validateNoConflict(professionalId, startDateTime, endDateTime, appointmentId);

        // Create the updated appointment preserving its ID
        Appointment updateAppointment = new Appointment(
                existingAppointment.getId(),
                customer,
                professional,
                service,
                startDateTime,
                endDateTime,
                existingAppointment.getStatus(),
                existingAppointment.getCreatedAt()
        );

        return appointmentRepository.update(updateAppointment);
    }

    public Appointment cancelAppointment(Long appointmentId)
    {
        Appointment appointment = getAppointmentById(appointmentId);

        if(appointment.getStatus() != AppointmentStatus.SCHEDULED)
        {
            throw new BusinessException("Only scheduled appointments can be cancelled");
        }

        Appointment cancelledAppointment = new Appointment(appointment.getId(),
                appointment.getCustomer(),
                appointment.getProfessional(),
                appointment.getService(),
                appointment.getStartDateTime(),
                appointment.getEndDateTime(),
                AppointmentStatus.CANCELLED,
                appointment.getCreatedAt());

        return appointmentRepository.update(cancelledAppointment);
    }

    private void validateFutureDate(LocalDateTime startDateTime) {
        if (startDateTime == null || !startDateTime.isAfter(LocalDateTime.now())) {
            throw new BusinessException("Appointment must start in the future.");
        }
    }

    private void validateNoConflict(Long professionalId, LocalDateTime startDateTime,LocalDateTime endDateTime, Long excludedAppointmentId) {
        List<Appointment> professionalAppointments = appointmentRepository.findByProfessional(professionalId);

        boolean hasConflict = professionalAppointments.stream()
                .filter(appointment -> appointment.getStatus() == AppointmentStatus.SCHEDULED)
                .filter(appointment -> excludedAppointmentId == null || !appointment.getId().equals(excludedAppointmentId))
                .anyMatch(appointment -> startDateTime.isBefore(appointment.getEndDateTime())
                                && endDateTime.isAfter(appointment.getStartDateTime()));

        if (hasConflict) {
            throw new BusinessException("The professional already has an appointment at this time.");
        }
    }

}
