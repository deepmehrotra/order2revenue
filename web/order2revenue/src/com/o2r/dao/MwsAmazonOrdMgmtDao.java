package com.o2r.dao;

import java.util.List;

import com.o2r.model.AmazonOrderInfo;

public interface MwsAmazonOrdMgmtDao {

	public List<AmazonOrderInfo> getOrderList() throws Exception;
	
}
