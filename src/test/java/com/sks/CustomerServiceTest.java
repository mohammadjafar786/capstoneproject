package com.sks;

import com.sks.exception.CustomerNotFoundException;
import com.sks.model.Customer;
import com.sks.repository.CustomerRepository;
import com.sks.service.CustomerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest(classes=CarRentalSystemApplicationTests.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

   @Mock
    private CustomerRepository customerRepository;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddCustomer() {
       Customer customer = new Customer();
        customer.setCustomerId(1L);

        when(customerRepository.save(customer)).thenReturn(customer);

        Customer result = customerService.addCustomer(customer);
       assertNotNull(result);
       assertEquals(customer.getCustomerId(), result.getCustomerId());
        verify(customerRepository, times(1)).save(customer);
   }

    @Test  
    public void testRemoveCustomer_Success() {
        Long customerId = 1L;

       doNothing().when(customerRepository).deleteById(customerId);
        customerService.removeCustomer(customerId);
        verify(customerRepository, times(1)).deleteById(customerId);
    }

   @Test
    public void testRemoveCustomer_NotFound() {
        Long customerId = 1L;

        doThrow(new EmptyResultDataAccessException("Customer not found", 1)).when(customerRepository).deleteById(customerId);

        customerService.removeCustomer(customerId);
        verify(customerRepository, times(1)).deleteById(customerId);
    }

    @Test
   public void testListCustomers() {
       Customer customer1 = new Customer();
        customer1.setCustomerId(1L);
        Customer customer2 = new Customer();
        customer2.setCustomerId(2L);
        List<Customer> customers = Arrays.asList(customer1, customer2);
       when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> result = customerService.listCustomers();
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    public void testFindCustomerById_Found() {
        Long customerId = 1L;
        Customer customer = new Customer();
       customer.setCustomerId(customerId);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        Customer result = customerService.findCustomerById(customerId);
        assertNotNull(result);
        assertEquals(customerId, result.getCustomerId());
        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    public void testFindCustomerById_NotFound() {
       Long customerId = 1L;

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

       Exception exception = assertThrows(CustomerNotFoundException.class, () -> {
           customerService.findCustomerById(customerId);
       });

        assertEquals("Customer with ID: " + customerId + " not found", exception.getMessage());
        verify(customerRepository, times(1)).findById(customerId);   }}






