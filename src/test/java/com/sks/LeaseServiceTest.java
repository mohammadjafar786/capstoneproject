package com.sks;

import com.sks.exception.LeaseNotFoundException;
import com.sks.model.Car;
import com.sks.model.Customer;
import com.sks.model.Lease;
import com.sks.repository.CarRepository;
import com.sks.repository.CustomerRepository;
import com.sks.repository.LeaseRepository;
import com.sks.service.LeaseService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest(classes=CarRentalSystemApplicationTests.class)
public class LeaseServiceTest {

    @InjectMocks
    private LeaseService leaseService;

    @Mock
    private LeaseRepository leaseRepository;

    @Mock
    private CarRepository carRepository;

   @Mock   private CustomerRepository customerRepository;    
@BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

   @Test
   public void testCreateLease_Success() {
        Car car = new Car();
        car.setCar_id(1L);

        Customer customer = new Customer();
        customer.setCustomerId(1L);

        Lease lease = new Lease();
        lease.setCar(car);
        lease.setCustomer(customer);

        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(leaseRepository.save(lease)).thenReturn(lease);

        Lease result = leaseService.createLease(lease);
        assertNotNull(result);
        assertEquals(car, result.getCar());
        assertEquals(customer, result.getCustomer());
        verify(leaseRepository, times(1)).save(lease);
    }

    @Test
    public void testCreateLease_CarNotFound() {
        Customer customer = new Customer();
        customer.setCustomerId(1L);

        Lease lease = new Lease();
        lease.setCar(new Car());
       lease.setCustomer(customer);

        when(carRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            leaseService.createLease(lease);
        });

        assertEquals("Car not found", exception.getMessage());
        verify(leaseRepository, never()).save(any(Lease.class));
    }

  @Test
    public void testCreateLease_CustomerNotFound() {
        Car car = new Car();
       car.setCar_id(1L);

        Lease lease = new Lease();
        lease.setCar(car);
        lease.setCustomer(new Customer());

        when(carRepository.findById(anyLong())).thenReturn(Optional.of(car));
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            leaseService.createLease(lease);
        });

        assertEquals("Customer not found", exception.getMessage());
        verify(leaseRepository, never()).save(any(Lease.class));
    }

    @Test
    public void testReturnCar_Success() {
        // Prepare the Lease and Car objects
        Lease lease = new Lease();
        lease.setLeaseId(1L);
        Car car = new Car();
        lease.setCar(car);

        // Mock the behavior of leaseRepository.findById and carRepository.save
        when(leaseRepository.findById(1L)).thenReturn(Optional.of(lease));
        when(leaseRepository.save(lease)).thenReturn(lease);
        when(carRepository.save(car)).thenReturn(car);

        // Call the method under test
        leaseService.returnCar(1L);

        // Verify the lease was marked as returned
        assertTrue(lease.isReturned());  // This requires that you have the `isReturned()` method in the Lease class

        // Verify the car was marked as available
        assertTrue(car.isAvailable());
        // Verify that the save methods were called
        verify(leaseRepository, times(1)).save(lease);
        verify(carRepository, times(1)).save(car);
    }


    @Test
    public void testReturnCar_LeaseNotFound() {
        when(leaseRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(LeaseNotFoundException.class, () -> {
            leaseService.returnCar(1L);
        });

        assertEquals("Lease with ID: 1 not found", exception.getMessage());
        verify(leaseRepository, never()).save(any(Lease.class));
    }

    @Test
   public void testFindLeaseById_Found() {
        Lease lease = new Lease();
        lease.setLeaseId(1L);

        when(leaseRepository.findById(1L)).thenReturn(Optional.of(lease));

        Lease result = leaseService.findLeaseById(1L);
        assertNotNull(result);
        assertEquals(lease, result);
        verify(leaseRepository, times(1)).findById(1L);
    }

   @Test
    public void testFindLeaseById_NotFound() {
        when(leaseRepository.findById(1L)).thenReturn(Optional.empty());

        Lease result = leaseService.findLeaseById(1L);
       assertNull(result);
        verify(leaseRepository, times(1)).findById(1L);
    }

    @Test
    public void testListActiveLeases() {
        Lease lease1 = new Lease();
        Lease lease2 = new Lease();        List<Lease> leases = Arrays.asList(lease1, lease2);

       when(leaseRepository.findAll()).thenReturn(leases);

        List<Lease> result = leaseService.listActiveLeases();
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(leaseRepository, times(1)).findAll();
    }

    @Test
    public void testListLeaseHistory() {
        Lease lease1 = new Lease();
        Lease lease2 = new Lease();
        List<Lease> leases = Arrays.asList(lease1, lease2);

        when(leaseRepository.findByCustomer_CustomerId(1L)).thenReturn(leases);

        List<Lease> result = leaseService.listLeaseHistory(1L);
       assertNotNull(result);
       assertEquals(2, result.size());
        verify(leaseRepository, times(1)).findByCustomer_CustomerId(1L);
    }
}





