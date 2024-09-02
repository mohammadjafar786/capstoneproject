package com.sks.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sks.Request.LeaseDTO;
import com.sks.model.Car;
import com.sks.model.Customer;
import com.sks.model.Lease;
import com.sks.repository.CarRepository;
import com.sks.repository.CustomerRepository;
import com.sks.service.LeaseService;

@RestController
@RequestMapping("/leases")
public class LeaseController {

    @Autowired
    private LeaseService leaseService;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CustomerRepository customerRepository;

   
    
    @PostMapping(value ="leaseCar", produces = "application/json")
    public ResponseEntity<Lease> createLease(@RequestBody LeaseDTO leaseDTO) {
        Car car = carRepository.findById(leaseDTO.getCarId())
                .orElseThrow(() -> new RuntimeException("Car not found"));
        Customer customer = customerRepository.findById(leaseDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Lease lease = new Lease();
        lease.setCar(car);
        lease.setCustomer(customer);
        lease.setStartDate(leaseDTO.getStartDate());
        lease.setEndDate(leaseDTO.getEndDate());
        lease.setLeaseType(leaseDTO.getLeaseType());
        lease.setTotalCost(leaseDTO.getTotalCost());

        Lease createdLease = leaseService.createLease(lease);
        return new ResponseEntity<>(createdLease, HttpStatus.CREATED);
    }

    @PostMapping(value ="lease/return/{id}",produces = "application/json")
    public ResponseEntity<Void> returnCar(@PathVariable Long id) {
        leaseService.returnCar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value ="leases" , produces = "application/json")
    public ResponseEntity<List<Lease>> listActiveLeases() {
        List<Lease> activeLeases = leaseService.listActiveLeases();
        return new ResponseEntity<>(activeLeases, HttpStatus.OK);
    }

    @GetMapping(value ="lease/history/{customerId}", produces = "application/json")
    public ResponseEntity<List<Lease>> listLeaseHistory(@PathVariable Long customerId) {
        List<Lease> leaseHistory = leaseService.listLeaseHistory(customerId);
        return new ResponseEntity<>(leaseHistory, HttpStatus.OK);
    }
}
