package com.o2r.service;

import java.util.List;

import com.o2r.model.Order;

public interface MwsAmazonOrdMgmtService {

	public List<Order> getOrderList() throws Exception;
	
}
