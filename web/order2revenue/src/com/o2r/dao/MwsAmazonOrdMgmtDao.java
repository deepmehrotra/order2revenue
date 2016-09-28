package com.o2r.dao;

import java.util.List;

import com.o2r.model.Order;

public interface MwsAmazonOrdMgmtDao {

	public List<Order> getOrderList() throws Exception;
	
}
