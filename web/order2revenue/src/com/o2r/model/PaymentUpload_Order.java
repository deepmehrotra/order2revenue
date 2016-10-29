package com.o2r.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PaymentUpload_Order")
public class PaymentUpload_Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int id;
	@Column
	private int orderId;
	@Column
	private double amount;
	@ManyToOne(cascade = CascadeType.ALL)
	private PaymentUpload paymentUpload;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public PaymentUpload getPaymentUpload() {
		return paymentUpload;
	}

	public void setPaymentUpload(PaymentUpload paymentUpload) {
		this.paymentUpload = paymentUpload;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}