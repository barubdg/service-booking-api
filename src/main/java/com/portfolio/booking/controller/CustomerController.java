package com.portfolio.booking.controller;

import com.portfolio.booking.dto.request.CustomerRequest;
import com.portfolio.booking.dto.response.CustomerResponse;
import com.portfolio.booking.model.Customer;
import com.portfolio.booking.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService)
    {
        this.customerService=customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> registerCustomer(@Valid @RequestBody CustomerRequest request)
    {
        Customer customer = new Customer(
                null,
                request.name(),
                request.email(),
                request.phone()
        );

        Customer registeredCustomer = customerService.registerCustomer(customer);
        CustomerResponse response = CustomerResponse.from(registeredCustomer);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers()
    {
        List<CustomerResponse> customers = customerService.getAllCustomers().stream().map(CustomerResponse::from).toList();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {

        Customer customer = customerService.getCustomerById(id);
        CustomerResponse response = CustomerResponse.from(customer);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerRequest request) {
        Customer customer = new Customer(
                id,
                request.name(),
                request.email(),
                request.phone());

        Customer updatedCustomer = customerService.updateCustomer(id, customer);
        CustomerResponse response = CustomerResponse.from(updatedCustomer);
        return ResponseEntity.ok(response);
    }
}
