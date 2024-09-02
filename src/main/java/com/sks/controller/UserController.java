package com.sks.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sks.Request.PaymentDTO;
import com.sks.model.Car;
import com.sks.model.Customer;
import com.sks.model.Lease;
import com.sks.model.Payment;
import com.sks.service.CarService;
import com.sks.service.CustomerService;
import com.sks.service.LeaseService;
import com.sks.service.PaymentService;


@RestController
@RequestMapping("api/user")
public class UserController {
	
	@Autowired
	CustomerService customerServiceImpl;
	
	@Autowired
	LeaseService leaseServiceImpl;
	
	@Autowired
	PaymentService paymentServiceImpl;
	
	@Autowired
	CarService serviceImpl;
	
	@GetMapping(value = "getCars" , produces = "application/json")
	public List<Car> getCars(){
		return serviceImpl.listAvailableCars();
	}
	
	@PostMapping(value = "rentcar" , produces = "application/json")
	public String rentACar(@RequestBody Lease management) {
		leaseServiceImpl.createLease(management);
		List<Lease> list= leaseServiceImpl.retriveLease();
		Lease management2 =  list.getLast();
		long diffInMillis = management.getStartDate().getTime() - management.getEndDate().getTime();
        long diffInDays = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS) + 1;
		int amount  = (int) (diffInDays  * 1500);
		return "the amount you have to pay  = " + amount + "Your Lease Id : " + management2.getLeaseId();
	}
	
	@PostMapping(value = "makepayment" , produces = "application/json")
	public String makePayment(@RequestBody PaymentDTO dto) {
		
		Payment handling = new Payment();
		
		Car carManagement = new Car();
		
		Lease leaseManagement = new Lease();
		
		Customer customerManagement = new Customer();
		
		leaseManagement = leaseServiceImpl.retriveLeaseById(dto.getLease());
		
		carManagement = serviceImpl.retriveCarById(leaseManagement.getCar().getCar_id());
		
		customerManagement = customerServiceImpl.retriveCustomerById(leaseManagement.getCustomer().getCustId());
		
		leaseManagement.setLeaseId(dto.getLease());
		
		handling.setAmount(dto.getAmount());
		handling.setCustomerDetails(customerManagement);
		handling.setPaymentDate(LocalDate.now());
		paymentServiceImpl.recordPayment(handling);
		
		return "Payment Sucessful , \n" + dto.getAmount() + " Received";
	}
}