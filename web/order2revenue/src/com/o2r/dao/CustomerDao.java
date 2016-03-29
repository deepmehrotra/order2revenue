package com.o2r.dao;

import org.hibernate.Session;

import com.o2r.model.Customer;

/**
 * @author Deep Mehrotra
 *
 */
public interface CustomerDao {
 
	 //public void addCustomer(Customer customer,int sellerId);

	// public List<Customer> listCustomers(int sellerId);
	 
	 
	 public Customer getCustomer(int customerId);
	public Customer getCustomer(String customerEmail, int sellerId,Session session);
	//public Customer getCustomer(String customerEmail, int sellerId,Session session);
	 
	// public void deleteCustomer(Customer customer,int sellerId);
}