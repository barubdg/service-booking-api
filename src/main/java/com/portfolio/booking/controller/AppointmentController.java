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

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> getAppointmentById(@PathVariable Long id)
    {
        Appointment appointment = appointmentService.getAppointmentById(id);
        AppointmentResponse response = AppointmentResponse.from(appointment);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponse> updateAppointment(@PathVariable Long id,
                                                                 @RequestBody AppointmentRequest request)
    {
        Appointment updateAppointment = appointmentService.updateAppointment(
                        id,
                        request.customerId(),
                        request.professionalId(),
                        request.serviceId(),
                        request.startDateTime());

        AppointmentResponse response = AppointmentResponse.from(updateAppointment);
        return ResponseEntity.ok(response);
    }

}
