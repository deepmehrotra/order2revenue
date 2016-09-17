package com.o2r.service;

import java.util.List;

import com.o2r.bean.CustomerDBaseBean;
import com.o2r.model.Customer;

public interface CustomerService {
	public List<Customer> listCustomers(int sellerId);
	public List<CustomerDBaseBean> listCustomerDB(int sellerId, int pageNo);
	public void changeStatus(int id, int sellerId);
	public boolean isBlackList(String phone, int sellerId);
}
