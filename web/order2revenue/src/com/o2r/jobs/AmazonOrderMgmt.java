package com.o2r.jobs;

import java.util.Date;
import java.util.List;

import org.apache.jasper.tagplugins.jstl.core.Out;

import com.o2r.bean.AuthInfoBean;
import com.o2r.model.Partner;
import com.o2r.model.Seller;
import com.o2r.model.PartnerSellerAuthInfo;
import com.o2r.service.MwsAmazonOrdMgmtService;
import com.o2r.service.MwsAmazonOrdMgmtServiceImpl;
import com.o2r.service.OrderService;
import com.o2r.service.OrderServiceImpl;

public class AmazonOrderMgmt {

	public MwsAmazonOrdMgmtService ordMgmtService = new MwsAmazonOrdMgmtServiceImpl();
	
	
	public OrderServiceImpl orderService = new OrderServiceImpl();
	
	
	public AmazonOrderMgmt() {
		// TODO Auto-generated constructor stub
	}
	
	public void invokeListOrdersAndOrderItems(PartnerSellerAuthInfo partnerSellerAuthInfo) throws Exception {
		/*try {
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
		}*/
		
		
		
		try {
		//List<Seller> sellers = ordMgmtService.getSellers();
		//for (Seller seller : sellers) {
		//	for (Partner partner : seller.getPartners()) {
				
		//		System.out.println("seller"+seller.getId());
		//		System.out.println("partner"+partner.getPcName());
				
				Partner partner = new Partner();
				//PartnerSellerAuthInfo  partnerSellerAuthInfo = new PartnerSellerAuthInfo();
				
				System.out.println(" HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
				//partnerSellerAuthInfo.setSellerid(partnerSellerAuthInfo.getSellerid());
				//partnerSellerAuthInfo.setPcName("Amazon");
				
				
			//	if(partner.getPcName().equalsIgnoreCase("Amazon")){
					
				//PartnerSellerAuthInfo authInfo = ordMgmtService.getAuthInfoBeanObj(partner.getAuthInfo());
				com.amazonservices.mws.orders._2013_09_01.model.ListOrdersResult listOrderResult = ordMgmtService.getListOrders(ordMgmtService.getCreatedAfter(new Date()), ordMgmtService.getCreatedBefore(new Date()), ordMgmtService.getConfiguredoOrderStatus(), partnerSellerAuthInfo);
				List<com.amazonservices.mws.orders._2013_09_01.model.Order> orders = listOrderResult.getOrders();
				for (com.amazonservices.mws.orders._2013_09_01.model.Order order : orders) {
					
					System.out.println("ORDERRSSSSSSSSSSSSS"+orders.size());
					
					
					//orderService.savePartnerSellerAutoInfo(order);
					
					/*com.amazonservices.mws.orders._2013_09_01.model.ListOrderItemsResult itemResults = ordMgmtService.getListOrderItems(partnerSellerAuthInfo, order.getAmazonOrderId());
					List<com.amazonservices.mws.orders._2013_09_01.model.OrderItem> orderItems = itemResults.getOrderItems();
					
					System.out.println(" The size of the orderItems================"+orderItems.size());
					//ordMgmtService.saveOrderInfo(order, orderItems);
					//OrderService.
					
					for(int i=0; i<orderItems.size(); i++){
					
						com.amazonservices.mws.orders._2013_09_01.model.OrderItem  orderItem= orderItems.get(i);
						System.out.println("orderItemorderItemorderItemorderItemorderItem"+orderItem);
					}*/
				}
				
				//}//if amazon
				
			//}//
		//}//
		
	} catch (Exception expObj) {
		expObj.printStackTrace();
	}
		
		
	}
	
	public static void main(String args[]) throws Exception{
		
		System.out.println(" I am here");
		AmazonOrderMgmt amazonOrderMgmt = new AmazonOrderMgmt();
		//amazonOrderMgmt.invokeListOrdersAndOrderItems();
	}
	
}
