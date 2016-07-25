package com.o2r.bean;

import java.util.ArrayList;
import java.util.List;

import com.o2r.model.Order;

public class CustomerDBaseBean {

	
	private int customerId;
	private String customerName;
	private String customerPhnNo;
	private String customerEmail;
	private String customerCity;
	private String customerAddress;
	private String zipcode;
	private long productPurchased;
	private long productReturned;
	private double netRevenue;
	private List<Order> orders = new ArrayList<Order>();
		
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerPhnNo() {
		return customerPhnNo;
	}
	public void setCustomerPhnNo(String customerPhnNo) {
		this.customerPhnNo = customerPhnNo;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public String getCustomerCity() {
		return customerCity;
	}
	public void setCustomerCity(String customerCity) {
		this.customerCity = customerCity;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public long getProductPurchased() {
		return productPurchased;
	}
	public void setProductPurchased(long productPurchased) {
		this.productPurchased = productPurchased;
	}
	public long getProductReturned() {
		return productReturned;
	}
	public void setProductReturned(long productReturned) {
		this.productReturned = productReturned;
	}
	public double getNetRevenue() {
		return netRevenue;
	}
	public void setNetRevenue(double netRevenue) {
		this.netRevenue = netRevenue;
	}
	public List<Order> getOrders() {
		return orders;
	}
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
}
