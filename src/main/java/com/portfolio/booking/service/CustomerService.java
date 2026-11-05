package com.portfolio.booking.service;

import com.portfolio.booking.model.Customer;
import com.portfolio.booking.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    // Constructor default with parameters (Dependency Injection)
    public CustomerService(CustomerRepository customerRepository)
    {
        this.customerRepository = customerRepository;
    }

    // Register Customer
    public Customer registerCustomer(Customer customer)
    {
        // Check whether the customer already exists in the repository
        if(customerRepository.existsByEmail(customer.getEmail()))
        {
            throw new IllegalArgumentException(
                    "A customer with this email already exists!"
            );
        }

        return customerRepository.save(customer);
    }

    // Get All Customers
    public List<Customer> getAllCustomers()
    {
        return customerRepository.findAll();
    }
}
