package com.o2r.jobs;

import java.util.Date;
import java.util.List;

import com.o2r.amazonservices.mws.orders.model.ListOrderItemsResult;
import com.o2r.amazonservices.mws.orders.model.ListOrdersResult;
import com.o2r.amazonservices.mws.orders.model.Order;
import com.o2r.amazonservices.mws.orders.model.OrderItem;
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
					ListOrdersResult listOrderResult = ordMgmtService.getListOrders(ordMgmtService.getCreatedAfter(new Date()), ordMgmtService.getCreatedBefore(new Date()), ordMgmtService.getConfiguredoOrderStatus(), authInfo);
					List<Order> orders = listOrderResult.getOrders();
					for (Order order : orders) {
						ListOrderItemsResult itemResults = ordMgmtService.getListOrderItems(authInfo, order.getAmazonOrderId());
						List<OrderItem> orderItems = itemResults.getOrderItems();
						ordMgmtService.saveOrderInfo(order, orderItems);
					}
				}
			}
			
		} catch (Exception expObj) {
			expObj.printStackTrace();
		}
	}
	
}
