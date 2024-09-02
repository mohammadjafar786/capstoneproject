package com.sks.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Lease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leaseId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    private String leaseType;

    private double totalCost;
    
    private boolean returned;

	public Long getLeaseId() {
		return leaseId;
	}

	public void setLeaseId(Long leaseId) {
		this.leaseId = leaseId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getLeaseType() {
		return leaseType;
	}

	public void setLeaseType(String leaseType) {
		this.leaseType = leaseType;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public boolean isReturned() {
		return returned;
	}

	public void setReturned(boolean returned) {
		this.returned = returned;
	}

	@Override
	public String toString() {
		return "Lease [leaseId=" + leaseId + ", customer=" + customer + ", car=" + car + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", leaseType=" + leaseType + ", totalCost=" + totalCost + ", returned="
				+ returned + "]";
	}

	public Lease(Long leaseId, Customer customer, Car car, Date startDate, Date endDate, String leaseType,
			double totalCost, boolean returned) {
		super();
		this.leaseId = leaseId;
		this.customer = customer;
		this.car = car;
		this.startDate = startDate;
		this.endDate = endDate;
		this.leaseType = leaseType;
		this.totalCost = totalCost;
		this.returned = returned;
	}

	public Lease() {
		super();
		// TODO Auto-generated constructor stub
	}
}
    