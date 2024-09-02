package com.sks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sks.model.Customer;
import com.sks.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping(value = "customerAdd", produces = "application/json")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
        Customer addedCustomer = customerService.addCustomer(customer);
        return new ResponseEntity<>(addedCustomer, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Void> removeCustomer(@PathVariable Long id) {
        customerService.removeCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "customers", produces = "application/json")
    public ResponseEntity<List<Customer>> listCustomers() {
        List<Customer> customers = customerService.listCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping(value = "find/{id}", produces = "application/json")
    public ResponseEntity<Customer> findCustomerById(@PathVariable Long id) {
        Customer customer = customerService.findCustomerById(id);
        return customer != null ? 
               new ResponseEntity<>(customer, HttpStatus.OK) : 
               new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "update/{id}", produces = "application/json")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        // Check if the customer exists
        Customer existingCustomer = customerService.findCustomerById(id);
        if (existingCustomer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Update customer details
        customer.setId(id); // Ensure the ID is correctly set
        Customer updatedCustomer = customerService.addCustomer(customer);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }
}
