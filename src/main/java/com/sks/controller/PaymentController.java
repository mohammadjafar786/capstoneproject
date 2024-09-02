package com.sks.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sks.model.Payment;
import com.sks.service.PaymentService;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
      
    @PostMapping("/record")
    public String recordPayment(@RequestBody Payment payment) {
        paymentService.recordPayment(payment);
        return "Payment done Sucessfully";
    }

    @GetMapping("/history/{customerId}")
    public List<Payment> getPaymentHistory(@PathVariable Integer customerId) {
        return paymentService.getPaymentHistory(customerId);
    }

    @GetMapping("/total-revenue")
    public double getTotalRevenue() {
        return paymentService.calculateTotalRevenue();
}
}