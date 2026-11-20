package com.portfolio.booking.dto.request;

import java.math.BigDecimal;

public record ServiceRequest (String name, String description, Integer durationMinutes, BigDecimal price) {

}
