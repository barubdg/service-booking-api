package com.portfolio.booking.dto.response;

import com.portfolio.booking.model.Appointment;
import com.portfolio.booking.model.AppointmentStatus;

import java.time.LocalDateTime;

public record AppointmentResponse (Long id, Long customerId, String customerName, Long professionalId, String professionalName,
                                   Long serviceId, String serviceName, LocalDateTime startDateTime, LocalDateTime endDateTime, AppointmentStatus status) {

    public static AppointmentResponse from(Appointment appointment)
    {
        return new AppointmentResponse(appointment.getId(),
                appointment.getCustomer().getId(),
                appointment.getCustomer().getName(),
                appointment.getProfessional().getId(),
                appointment.getProfessional().getName(),
                appointment.getService().getId(),
                appointment.getService().getName(),
                appointment.getStartDateTime(),
                appointment.getEndDateTime(),
                appointment.getStatus());
    }
}
