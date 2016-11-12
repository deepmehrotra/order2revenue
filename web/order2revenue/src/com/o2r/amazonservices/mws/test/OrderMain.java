package com.o2r.amazonservices.mws.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.amazonservices.mws.orders._2013_09_01.MarketplaceWebServiceOrders;
import com.amazonservices.mws.orders._2013_09_01.MarketplaceWebServiceOrdersClient;
import com.amazonservices.mws.orders._2013_09_01.model.GetOrderRequest;
import com.amazonservices.mws.orders._2013_09_01.model.GetOrderResponse;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrderItemsRequest;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrderItemsResponse;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrdersRequest;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrdersResponse;
import com.amazonservices.mws.orders._2013_09_01.model.ResponseHeaderMetadata;
import com.o2r.amazonservices.mws.constants.MWSConstants;

public class OrderMain {

	public OrderMain() {
		try {
			testHai();
			invokeListOrders();
			//invokeGetOrder();
			//invokeListOrderItems();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testHai() throws Exception {
		System.out.println("Hai");
	}
	
	public void listOrders(MarketplaceWebServiceOrdersClient client, ListOrdersRequest request) throws Exception {
		System.out.println("Begin: Get All Orders Based On TimeFrame using ListOrders api from Amazon");
		
		try {
			ListOrdersResponse response = client.listOrders(request);
			ResponseHeaderMetadata rhmd = response.getResponseHeaderMetadata();
            System.out.println("RequestId: "+rhmd.getRequestId());
            System.out.println("Timestamp: "+rhmd.getTimestamp());
            String responseXml = response.toXML();
            System.out.println("Orders Response:"+responseXml);
		} catch (Exception expObj) {
			expObj.printStackTrace();
		}
		System.out.println("End: Get All Orders Based On TimeFrame using ListOrders api from Amazon");
	}
	
	public void invokeListOrders() throws Exception {
		try {
			MarketplaceWebServiceOrdersClient client = com.o2r.amazonservices.mws.orders.config.MarketplaceWebServiceOrdersSampleConfig.getClient();
			ListOrdersRequest request = new ListOrdersRequest();
			List<String> marketplaceIds = new ArrayList<String>();
			marketplaceIds.add(MWSConstants.MARKETPLACEID);
			request.setSellerId(MWSConstants.SELLERID);
			request.setMWSAuthToken(MWSConstants.MWSAUTHTOKEN);
			request.setMarketplaceId(marketplaceIds);
			request.setCreatedAfter(getCreatedAfter());
			request.setCreatedBefore(getCreatedBefore());
			List<String> orderStatus = new ArrayList<String>();
			/*orderStatus.add("Pending");
			orderStatus.add("PendingAvailability");*/
			orderStatus.add("Shipped");
			request.setOrderStatus(orderStatus);
			listOrders(client, request);
		} catch (Exception expObj) {
			expObj.printStackTrace();
		}
	}
	
	public XMLGregorianCalendar getCreatedAfter() throws Exception {
		try {
			Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DATE, 2);
            calendar.set(Calendar.MONTH, 9);
            calendar.set(Calendar.YEAR, 2016);
            GregorianCalendar c = new GregorianCalendar();
            c.setTime(calendar.getTime());
            XMLGregorianCalendar dateCreatedAfter = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
            dateCreatedAfter = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
            return dateCreatedAfter;
            
		} catch (Exception expObj) {
			expObj.printStackTrace();
		}
		return null;
	}
	
	public XMLGregorianCalendar getCreatedBefore() throws Exception {
		try {
			Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DATE, 3);
            calendar.set(Calendar.MONTH, 9);
            calendar.set(Calendar.YEAR, 2016);
            calendar.set(Calendar.MINUTE, Calendar.MINUTE - 2);
            System.out.println("date:"+calendar.getTime());
            GregorianCalendar c = new GregorianCalendar();
            c.setTime(calendar.getTime());
            XMLGregorianCalendar dateCreatedBefore = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
            dateCreatedBefore = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
            return dateCreatedBefore;
		} catch (Exception expObj) {
			expObj.printStackTrace();
		}
		return null;
	}
	
	public void getOrder(MarketplaceWebServiceOrders client, GetOrderRequest request) throws Exception {
		try {
			GetOrderResponse response = client.getOrder(request);
            ResponseHeaderMetadata rhmd = response.getResponseHeaderMetadata();
            System.out.println("RequestId: "+rhmd.getRequestId());
            System.out.println("Timestamp: "+rhmd.getTimestamp());
            String responseXml = response.toXML();
            System.out.println("Order Response: "+responseXml);
		} catch (Exception expObj) {
			expObj.printStackTrace();
		}
	}
	
	public void invokeGetOrder() throws Exception {
		try {
			MarketplaceWebServiceOrdersClient client = com.o2r.amazonservices.mws.orders.config.MarketplaceWebServiceOrdersSampleConfig.getClient();
			GetOrderRequest request = new GetOrderRequest();
			request.setSellerId(MWSConstants.SELLERID);
			request.setMWSAuthToken(MWSConstants.MWSAUTHTOKEN);
			List<String> amazonOrderIds = new ArrayList<String>();
			//amazonOrderIds.add("404-3214063-5845955");
			amazonOrderIds.add("403-2044050-9547564");
	        request.setAmazonOrderId(amazonOrderIds);
	        getOrder(client, request);
		} catch (Exception expObj) {
			expObj.printStackTrace();
		}
	}
	
	public void listOrderItems(MarketplaceWebServiceOrders client, ListOrderItemsRequest request) throws Exception {
		try {
			ListOrderItemsResponse response = client.listOrderItems(request);
            ResponseHeaderMetadata rhmd = response.getResponseHeaderMetadata();
            // We recommend logging every the request id and timestamp of every call.
            System.out.println("Response:");
            System.out.println("RequestId: "+rhmd.getRequestId());
            System.out.println("Timestamp: "+rhmd.getTimestamp());
            String responseXml = response.toXML();
            System.out.println("listOrderItems"+responseXml);
		} catch (Exception expObj) {
			expObj.printStackTrace();
		}
	}
	
	public void invokeListOrderItems() throws Exception {
		try {
			MarketplaceWebServiceOrdersClient client = com.o2r.amazonservices.mws.orders.config.MarketplaceWebServiceOrdersSampleConfig.getClient();
	        ListOrderItemsRequest request = new ListOrderItemsRequest();
	        String sellerId = MWSConstants.SELLERID;
	        request.setSellerId(sellerId);
	        String mwsAuthToken = MWSConstants.MWSAUTHTOKEN;
	        request.setMWSAuthToken(mwsAuthToken);
	        String amazonOrderId = "403-2044050-9547564";
	        request.setAmazonOrderId(amazonOrderId);
			listOrderItems(client, request);
		} catch (Exception expObj) {
			expObj.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new OrderMain();
	}
	
	
}
