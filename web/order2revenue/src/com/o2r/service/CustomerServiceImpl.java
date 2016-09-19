package com.o2r.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.o2r.bean.CustomerDBaseBean;
import com.o2r.dao.CustomerDao;
import com.o2r.model.Customer;

@Service("CustomerService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private CustomerDao customerDao;
	
	@Override
	public List<Customer> listCustomers(int sellerId) {		
		return customerDao.listCustomers(sellerId);
	}
	
	@Override
	public List<CustomerDBaseBean> listCustomerDB(int sellerId, int pageNo) {
		return customerDao.listCustomerDB(sellerId, pageNo);
	}
	
	@Override
	public void changeStatus(int id, int sellerId) {
		customerDao.changeStatus(id, sellerId);
	}
	
	@Override
	public boolean isBlackList(String phone, int sellerId) {		
		return customerDao.isBlackList(phone, sellerId);
	}
	
}
