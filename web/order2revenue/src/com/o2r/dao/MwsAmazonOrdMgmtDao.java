package com.o2r.dao;

import java.util.List;

import com.o2r.model.AmazonOrderInfo;
import com.o2r.model.Seller;

public interface MwsAmazonOrdMgmtDao {

	public List<AmazonOrderInfo> getOrderList() throws Exception;
	
	public List<Seller> getSeller() throws Exception;
	
	public void saveAmazonOrderInfo(AmazonOrderInfo orderInfo) throws Exception;
	
}
