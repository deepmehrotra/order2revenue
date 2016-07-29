package com.o2r.helper;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import com.amazonservices.mws.client.MwsUtl;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrderItemsRequest;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrdersRequest;

public class AmazonUtils {
	
	private static final String MARKETPLACE_ID = "A21TJRUUN4KGV";
	private static final String SELLER_ID = "A2D1BAVBN6MZ98";

	public static ListOrdersRequest prepareListOrderRequest(){
		ListOrdersRequest request = new ListOrdersRequest();
        String sellerId = SELLER_ID;
        request.setSellerId(sellerId);
        XMLGregorianCalendar lastUpdatedAfter = MwsUtl.getDTF().newXMLGregorianCalendar();
        lastUpdatedAfter.setDay(1);
        lastUpdatedAfter.setMonth(1);
        lastUpdatedAfter.setYear(2016);
        lastUpdatedAfter.setTime(0, 0, 0);
        request.setLastUpdatedAfter(lastUpdatedAfter);
        List<String> orderStatus = new ArrayList<String>();
        orderStatus.add("Shipped");
        request.setOrderStatus(orderStatus);
        List<String> marketplaceId = new ArrayList<String>();
        marketplaceId.add(MARKETPLACE_ID);
        request.setMarketplaceId(marketplaceId);
        return request;
	}

	public static ListOrderItemsRequest prepareListOrderItemRequest(String amazonOrderId){
		 ListOrderItemsRequest request = new ListOrderItemsRequest();
        String sellerId = SELLER_ID;
        request.setSellerId(sellerId);
        request.setAmazonOrderId(amazonOrderId);
        return request;
	}
}
