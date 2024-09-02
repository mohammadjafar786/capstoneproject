package com.sks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sks.model.Customer;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    // Find a customer by their email address
    Optional<Customer> findByEmail(String email);
    
    // Find all customers by their last name
    List<Customer> findByName(String name);
}
