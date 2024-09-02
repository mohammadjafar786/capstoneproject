package com.sks;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.sks.model.Customer;
import com.sks.model.Payment;
import com.sks.repository.PaymentRepository;
import com.sks.service.PaymentService;

@SpringBootTest
public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRecordPayment() {
        Payment payment = new Payment();
        payment.setPaymentId(1L);
        payment.setAmount(100.0);
        payment.setPaymentDate(LocalDate.now());

        paymentService.recordPayment(payment);

        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    void testGetPaymentHistory() {
        Customer customer = new Customer();
        customer.setCustomerId(1L);

        Payment payment1 = new Payment();
        payment1.setPaymentId(1L);
        payment1.setAmount(100.0);
        payment1.setPaymentDate(LocalDate.now());
        payment1.setCustomerDetails(customer);

        Payment payment2 = new Payment();
        payment2.setPaymentId(2L);
        payment2.setAmount(150.0);
        payment2.setPaymentDate(LocalDate.now());
        payment2.setCustomerDetails(customer);

        List<Payment> payments = Arrays.asList(payment1, payment2);

        when(paymentRepository.findByCustomerDetails_CustomerId(1)).thenReturn(payments);

        List<Payment> result = paymentService.getPaymentHistory(1);
        
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(100.0, result.get(0).getAmount());
        assertEquals(150.0, result.get(1).getAmount());
    }

    @Test
    void testCalculateTotalRevenue() {
        Payment payment1 = new Payment();
        payment1.setAmount(100.0);

        Payment payment2 = new Payment();
        payment2.setAmount(150.0);

        when(paymentRepository.findAll()).thenReturn(Arrays.asList(payment1, payment2));

        double totalRevenue = paymentService.calculateTotalRevenue();

        assertEquals(250.0, totalRevenue, 0.001);
    }
}
