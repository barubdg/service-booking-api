package com.portfolio.booking.model;

import java.time.LocalDateTime;

public class Appointment {

    private Long id;
    private Customer customer;
    private Professional professional;
    private Service service;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private AppointmentStatus status;

    public Appointment(Long id, Customer customer, Professional professional, Service service, LocalDateTime startDateTime, LocalDateTime endDateTime, AppointmentStatus status) {
        this.id = id;
        this.customer = customer;
        this.professional = professional;
        this.service = service;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Professional getProfessional() {
        return professional;
    }

    public Service getService() {
        return service;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public AppointmentStatus getStatus() {
        return status;
    }
}
