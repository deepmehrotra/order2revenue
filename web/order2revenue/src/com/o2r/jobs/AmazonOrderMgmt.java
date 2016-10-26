package com.o2r.jobs;

import java.util.Date;
import java.util.List;

import com.o2r.bean.AuthInfoBean;
import com.o2r.model.Partner;
import com.o2r.model.Seller;
import com.o2r.service.MwsAmazonOrdMgmtService;
import com.o2r.service.MwsAmazonOrdMgmtServiceImpl;

public class AmazonOrderMgmt {

	public MwsAmazonOrdMgmtService ordMgmtService = new MwsAmazonOrdMgmtServiceImpl();
	
	public AmazonOrderMgmt() {
		// TODO Auto-generated constructor stub
	}
	
	public void invokeListOrdersAndOrderItems() throws Exception {
		try {
			List<Seller> sellers = ordMgmtService.getSellers();
			for (Seller seller : sellers) {
				for (Partner partner : seller.getPartners()) {
					AuthInfoBean authInfo = ordMgmtService.getAuthInfoBeanObj(partner.getAuthInfo());
					com.amazonservices.mws.orders._2013_09_01.model.ListOrdersResult listOrderResult = ordMgmtService.getListOrders(ordMgmtService.getCreatedAfter(new Date()), ordMgmtService.getCreatedBefore(new Date()), ordMgmtService.getConfiguredoOrderStatus(), authInfo);
					List<com.amazonservices.mws.orders._2013_09_01.model.Order> orders = listOrderResult.getOrders();
					for (com.amazonservices.mws.orders._2013_09_01.model.Order order : orders) {
						com.amazonservices.mws.orders._2013_09_01.model.ListOrderItemsResult itemResults = ordMgmtService.getListOrderItems(authInfo, order.getAmazonOrderId());
						List<com.amazonservices.mws.orders._2013_09_01.model.OrderItem> orderItems = itemResults.getOrderItems();
						ordMgmtService.saveOrderInfo(order, orderItems);
					}
				}
			}
			
		} catch (Exception expObj) {
			expObj.printStackTrace();
		}
	}
	
}
