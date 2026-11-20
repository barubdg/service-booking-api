package com.portfolio.booking.controller;

import com.portfolio.booking.dto.request.AppointmentRequest;
import com.portfolio.booking.dto.response.AppointmentResponse;
import com.portfolio.booking.model.Appointment;
import com.portfolio.booking.service.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService)
    {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<AppointmentResponse> createAppointment(@RequestBody AppointmentRequest request)
    {
        Appointment appointment = appointmentService.createAppointment(
                request.customerId(),
                request.professionalId(),
                request.serviceId(),
                request.startDateTime());

        AppointmentResponse response = AppointmentResponse.from(appointment);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponse>> getAllAppointments()
    {
        List<AppointmentResponse> appointments = appointmentService.getAllAppointments().stream().map(AppointmentResponse::from).toList();
        return ResponseEntity.ok(appointments);
    }
}
