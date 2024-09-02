package com.sks.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sks.model.Payment;


public interface PaymentRepository extends CrudRepository<Payment, Long> {
    List<Payment> findByCustomerDetails_CustomerId(Integer customerId);
}