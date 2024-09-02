package com.sks.Request;

public class PaymentDTO {
	private Long lease;
	private Double amount;
	public Long getLease() {
		return lease;
	}
	public void setLease(Long lease) {
		this.lease = lease;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "PaymentDTO [lease=" + lease + ", amount=" + amount + "]";
	}
	public PaymentDTO(Long lease, Double amount) {
		super();
		this.lease = lease;
		this.amount = amount;
	}
	public PaymentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
