package com.portfolio.booking.model;

import java.math.BigDecimal;

public class Service {

    private Long id;
    private String name;
    private String description;
    private Integer durationMinutes;
    private BigDecimal price;
    private boolean active;

    public Service(Long id, String name, String description,
                   Integer durationMinutes, BigDecimal price) {
        this(id, name, description, durationMinutes, price, true);
    }

    public Service(Long id, String name, String description, Integer durationMinutes, BigDecimal price, boolean active)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.durationMinutes = durationMinutes;
        this.price = price;
        this.active = active;

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

    public boolean isActive() {
        return active;
    }
}
