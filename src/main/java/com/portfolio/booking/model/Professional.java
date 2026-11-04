package com.portfolio.booking.model;

public class Professional {

    private Long id;
    private String name;
    private String email;
    private String specialty;

    public Professional(Long id, String name, String email, String specialty) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.specialty = specialty;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getSpecialty() {
        return specialty;
    }
}
