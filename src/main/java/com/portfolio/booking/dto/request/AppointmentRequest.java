package com.portfolio.booking.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AppointmentRequest (
        @NotNull(message = "Customer ID is required") @Positive(message = "Customer ID must be positive") Long customerId,
        @NotNull(message = "Professional ID is required") @Positive(message = "Professional ID must be positive") Long professionalId,
        @NotNull(message = "Service ID is required") @Positive(message = "Service ID must be positive") Long serviceId,
        @NotNull(message = "Start date and time are required") LocalDateTime startDateTime) {
}
