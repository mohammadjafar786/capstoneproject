package com.sks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sks.exception.CustomerNotFoundException;
import com.sks.model.Customer;
import com.sks.repository.CustomerRepository;

import java.util.List;
import java.util.logging.Logger;

@Service
public class CustomerService {

    private static final Logger LOGGER = Logger.getLogger(CustomerService.class.getName());

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public Customer addCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        LOGGER.info("Adding customer: " + customer);
        return customerRepository.save(customer);
    }

    @Transactional
    public void removeCustomer(Long customerId) {
        try {
            customerRepository.deleteById(customerId);
            LOGGER.info("Removed customer with ID: " + customerId);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.warning("Attempted to remove customer with ID: " + customerId + " but it does not exist.");
        }
    }

    public List<Customer> listCustomers() {
        LOGGER.info("Listing all customers");
        return customerRepository.findAll();
    }

    public Customer findCustomerById(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with ID: " + customerId + " not found"));
        LOGGER.info("Found customer: " + customer);
        return customer;
    }

	public Customer retriveCustomerById(Object custId) {
		// TODO Auto-generated method stub
		return null;
	}
}
