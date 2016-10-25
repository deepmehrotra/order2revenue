package com.o2r.service;

import java.util.Date;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import com.o2r.amazonservices.mws.orders.model.ListOrderItemsResult;
import com.o2r.amazonservices.mws.orders.model.ListOrdersResult;
import com.o2r.amazonservices.mws.orders.model.OrderItem;
import com.o2r.bean.AuthInfoBean;
import com.o2r.model.AmazonOrderInfo;
import com.o2r.model.Order;
import com.o2r.model.PartnerSellerAuthInfo;
import com.o2r.model.Seller;

public interface MwsAmazonOrdMgmtService {

	public List<Order> getOrderList() throws Exception;
	
	public List<Seller> getSellers() throws Exception;
	
	public AuthInfoBean getAuthInfoBeanObj(PartnerSellerAuthInfo sellerAuthInfo) throws Exception;
	
	public XMLGregorianCalendar getCreatedAfter(Date createdAfter) throws Exception;
	
	public XMLGregorianCalendar getCreatedBefore(Date createdBefore) throws Exception;
	
	public List<String> getConfiguredoOrderStatus() throws Exception;
	
	public ListOrdersResult getListOrders(XMLGregorianCalendar createdAfter, XMLGregorianCalendar createdBefore, List<String> orderStatus, AuthInfoBean authInfo) throws Exception;
	
	public ListOrderItemsResult getListOrderItems(AuthInfoBean authInfo, String amazonOrderId) throws Exception;
	
	public AmazonOrderInfo getAmazonOrderInfoObj(AuthInfoBean authInfo, String amazonOrderId) throws Exception;
	
	public void saveOrderInfo(com.o2r.amazonservices.mws.orders.model.Order order, List<OrderItem> orderItems) throws Exception;
	
}
