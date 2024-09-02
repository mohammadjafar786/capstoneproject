package com.sks.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sks.model.Lease;

import java.util.List;

public interface LeaseRepository extends JpaRepository<Lease, Long> {
    List<Lease> findByCustomer_CustomerId(Long customerId); // Update method name to match field name
}


