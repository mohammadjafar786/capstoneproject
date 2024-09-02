package com.sks.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sks.model.Payment;
import com.sks.repository.PaymentRepository;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public void recordPayment(Payment payment) {
        paymentRepository.save(payment);
    }
    public List<Payment> getPaymentHistory(Integer customerId) {
        return paymentRepository.findByCustomerDetails_CustomerId(customerId);
    }
    public double calculateTotalRevenue() {
        
        Iterable<Payment> payments = paymentRepository.findAll();
        final double[] totalRevenue = {0};
        payments.forEach(payment -> totalRevenue[0] += payment.getAmount());
        return totalRevenue[0];
    }
}