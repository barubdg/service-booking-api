package com.portfolio.booking.dto.request;

import java.time.LocalDateTime;

public record AppointmentRequest (Long customerId, Long professionalId, Long serviceId, LocalDateTime startDateTime) {
}
