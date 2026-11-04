package com.portfolio.booking.model;

import java.math.BigDecimal;

public class Service {

    private Long id;
    private String name;
    private String description;
    private Integer durationMinutes;
    private BigDecimal price;

    public Service(Long id, String name, String description, Integer durationMinutes, BigDecimal price)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.durationMinutes = durationMinutes;
        this.price = price;

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
