package com.portfolio.booking.controller;

import com.portfolio.booking.dto.request.CustomerRequest;
import com.portfolio.booking.dto.response.CustomerResponse;
import com.portfolio.booking.model.Customer;
import com.portfolio.booking.service.CustomerService;
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
    public ResponseEntity<CustomerResponse> registerCustomer(@RequestBody CustomerRequest request)
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
}
