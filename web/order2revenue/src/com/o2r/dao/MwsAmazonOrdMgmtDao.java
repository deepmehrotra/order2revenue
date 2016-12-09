package com.o2r.dao;

import java.util.List;

import com.o2r.model.AmazonOrderInfo;
import com.o2r.model.PartnerSellerAuthInfo;
import com.o2r.model.Seller;
 
public interface MwsAmazonOrdMgmtDao {

	public List<AmazonOrderInfo> getOrderList() throws Exception;	
	
	public List<AmazonOrderInfo> getOrderList(String value) throws Exception;
	
	public List<Seller> getSeller() throws Exception;
	
	public List<PartnerSellerAuthInfo> getSellersFromPartnerSellerAuthoInfo() throws Exception;
	
	public void saveAmazonOrderInfo(AmazonOrderInfo orderInfo,int sellerId) throws Exception;

	public void saveAmazonOrderInfo(AmazonOrderInfo orderInfo) throws Exception;
	
	List<AmazonOrderInfo> getOrderListbyStatus(String value) throws Exception;
	
}
