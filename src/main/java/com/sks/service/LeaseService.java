package com.sks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sks.exception.LeaseNotFoundException;
import com.sks.model.Car;
import com.sks.model.Customer;
import com.sks.model.Lease;
import com.sks.repository.CarRepository;
import com.sks.repository.CustomerRepository;
import com.sks.repository.LeaseRepository;

import java.util.List;
import java.util.logging.Logger;

@Service
public class LeaseService {

    private static final Logger LOGGER = Logger.getLogger(LeaseService.class.getName());

    @Autowired
    private LeaseRepository leaseRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public Lease createLease(Lease lease) {
        if (lease == null) {
            throw new IllegalArgumentException("Lease cannot be null");
        }

        if (lease.getCar() == null || lease.getCustomer() == null) {
            throw new IllegalArgumentException("Car and Customer cannot be null");
        }

        Car car = carRepository.findById(lease.getCar().getCar_id())
                .orElseThrow(() -> new IllegalArgumentException("Car not found"));

        Customer customer = customerRepository.findById(lease.getCustomer().getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        lease.setCar(car);
        lease.setCustomer(customer);

        LOGGER.info("Creating lease: " + lease);
        return leaseRepository.save(lease);
    }

    @Transactional
    public void returnCar(Long leaseId) {
        Lease lease = leaseRepository.findById(leaseId)
                .orElseThrow(() -> new LeaseNotFoundException("Lease with ID: " + leaseId + " not found"));

        // Mark the lease as returned (you may need to add a 'status' field to Lease)
        lease.setReturned(true);

        // Update car status if needed (you may need to update the Car model)
        Car car = lease.getCar();
        car.setAvailable(true);

        leaseRepository.save(lease);
        carRepository.save(car);
        LOGGER.info("Returned car for lease with ID: " + leaseId);
    }


    public Lease findLeaseById(Long leaseId) {
        return leaseRepository.findById(leaseId).orElse(null);
    }

    public List<Lease> listActiveLeases() {
        LOGGER.info("Listing all active leases");
        return leaseRepository.findAll(); // Filter for active leases as needed
    }

    public List<Lease> listLeaseHistory(Long customerId) {
        LOGGER.info("Listing lease history for customer ID: " + customerId);
        return leaseRepository.findByCustomer_CustomerId(customerId);
    }

	public List<Lease> retriveLease() {
		// TODO Auto-generated method stub
		return null;
	}

	public Lease retriveLeaseById(Object lease) {
		// TODO Auto-generated method stub
		return null;
	}
}
