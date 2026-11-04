package com.portfolio.booking.repository;

import com.portfolio.booking.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerRepository {

    private final List<Customer> customers = new ArrayList<>();
    private Long nextId = 1L;

    public Customer save(Customer customer)
    {
        Customer customerWithId = new Customer(
                nextId++,
                customer.getName(),
                customer.getEmail(),
                customer.getPhone()
        );

        customers.add(customerWithId);
        return customerWithId;
    }

    public List<Customer> findAll()
    {
        return new ArrayList<>(customers);
    }

    public Optional<Customer> findById(Long id)
    {
        return customers.stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst();
    }

    public boolean existsByEmail(String email)
    {
        return customers.stream()
                .anyMatch(customer -> customer.getEmail().equalsIgnoreCase(email));
    }
}
