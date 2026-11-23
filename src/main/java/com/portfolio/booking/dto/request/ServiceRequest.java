package com.portfolio.booking.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record ServiceRequest (
        @NotBlank(message = "Name is required") String name,
        @NotBlank(message = "Description is required") String description,
        @Positive(message = "Duration must be greater than zero") Integer durationMinutes,
        @NotNull(message = "Price is required") @PositiveOrZero(message = "Price cannot be negative") BigDecimal price) {

}
