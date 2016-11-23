package com.o2r.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.amazonservices.mws.orders._2013_09_01.MarketplaceWebServiceOrdersClient;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrderItemsRequest;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrderItemsResponse;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrderItemsResult;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrdersRequest;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrdersResponse;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrdersResult;
import com.amazonservices.mws.orders._2013_09_01.model.OrderItem;
import com.amazonservices.mws.orders._2013_09_01.model.ResponseHeaderMetadata;
import com.amazonservices.mws.orders._2013_09_01.samples.MarketplaceWebServiceOrdersSampleConfig;
import com.o2r.bean.AuthInfoBean;
import com.o2r.dao.MwsAmazonOrdMgmtDao;
import com.o2r.model.AmazonOrderInfo;
import com.o2r.model.AmazonOrderItemInfo;
import com.o2r.model.Order;
import com.o2r.model.PartnerSellerAuthInfo;
import com.o2r.model.Seller;

@Service("mwsAmazonOrdMgmtService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MwsAmazonOrdMgmtServiceImpl implements MwsAmazonOrdMgmtService {

	@Autowired
	private MwsAmazonOrdMgmtDao amazonOrdMgmtDao;

	@Override
	public List<Order> getOrderList() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Seller> getSellers() throws Exception {
		return amazonOrdMgmtDao.getSeller();
	}

	
	@Override
	public List<PartnerSellerAuthInfo> getSellersFromPartnerSellerAuthoInfo() throws Exception {
		return amazonOrdMgmtDao.getSellersFromPartnerSellerAuthoInfo();
	}
	
	
	
	
	@Override
	public PartnerSellerAuthInfo getAuthInfoBeanObj(
			PartnerSellerAuthInfo sellerAuthInfo) throws Exception {
		PartnerSellerAuthInfo authInfoBean = null;
		try {
			authInfoBean = new PartnerSellerAuthInfo();
			authInfoBean.setSellerid(sellerAuthInfo.getSellerid());
			authInfoBean.setAccesskey(sellerAuthInfo.getAccesskey());
			authInfoBean.setMwsauthtoken(sellerAuthInfo.getMwsauthtoken());
			authInfoBean.setSecretkey(sellerAuthInfo.getSecretkey());
			authInfoBean.setMarketplaceid(sellerAuthInfo.getMarketplaceid());
			authInfoBean.setServiceurl(sellerAuthInfo.getServiceurl());
			authInfoBean.setStatus(sellerAuthInfo.getStatus());
		} catch (Exception expOj) {
			expOj.printStackTrace();
		}
		return authInfoBean;
	}

	@Override
	public XMLGregorianCalendar getCreatedAfter(Date createdAfter)
			throws Exception {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DATE, 2);
			calendar.set(Calendar.MONTH, 9);
			calendar.set(Calendar.YEAR, 2016);
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(calendar.getTime());
			XMLGregorianCalendar dateCreatedAfter = DatatypeFactory
					.newInstance().newXMLGregorianCalendar(c);
			dateCreatedAfter = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(c);
			return dateCreatedAfter;
		} catch (Exception expObj) {
			expObj.printStackTrace();
		}
		return null;
	}

	@Override
	public XMLGregorianCalendar getCreatedBefore(Date createdBefore)
			throws Exception {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DATE, 3);
			calendar.set(Calendar.MONTH, 9);
			calendar.set(Calendar.YEAR, 2016);
			calendar.set(Calendar.MINUTE, Calendar.MINUTE - 2);
			System.out.println("date:" + calendar.getTime());
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(calendar.getTime());
			XMLGregorianCalendar dateCreatedBefore = DatatypeFactory
					.newInstance().newXMLGregorianCalendar(c);
			dateCreatedBefore = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(c);
			return dateCreatedBefore;
		} catch (Exception expObj) {
			expObj.printStackTrace();
		}
		return null;
	}

	@Override
	public List<String> getConfiguredoOrderStatus() throws Exception {
		List<String> orderStatus = null;
		try {
			orderStatus = new ArrayList<String>();
			orderStatus.add("");
		} catch (Exception expObj) {
			expObj.printStackTrace();
		}
		return orderStatus;
	}

	@Override
	public com.amazonservices.mws.orders._2013_09_01.model.ListOrdersResult getListOrders(
			XMLGregorianCalendar createdAfter,
			XMLGregorianCalendar createdBefore, List<String> orderStatus,
			PartnerSellerAuthInfo authInfo) throws Exception {
		com.amazonservices.mws.orders._2013_09_01.model.ListOrdersResult listOrdersResult = null;
		try {
			listOrdersResult = new com.amazonservices.mws.orders._2013_09_01.model.ListOrdersResult();
			MarketplaceWebServiceOrdersClient client = com.o2r.amazonservices.mws.orders.config.MarketplaceWebServiceOrdersSampleConfig
					.getClient();
			ListOrdersRequest request = new ListOrdersRequest();
			List<String> marketplaceIds = new ArrayList<String>();
			marketplaceIds.add(authInfo.getMarketplaceid());
			request.setSellerId(authInfo.getSellerid());
			request.setMWSAuthToken(authInfo.getMwsauthtoken());
			request.setMarketplaceId(marketplaceIds);
			request.setCreatedAfter(createdAfter);
			request.setCreatedBefore(createdBefore);
			request.setOrderStatus(orderStatus);
			ListOrdersResponse response = client.listOrders(request);
			ResponseHeaderMetadata rhmd = response.getResponseHeaderMetadata();
			listOrdersResult = response.getListOrdersResult();
		} catch (Exception expObj) {
			expObj.printStackTrace();
		}
		return listOrdersResult;
	}

	@Override
	public com.amazonservices.mws.orders._2013_09_01.model.ListOrderItemsResult getListOrderItems(
			PartnerSellerAuthInfo authInfo, String amazonOrderId)
			throws Exception {
		com.amazonservices.mws.orders._2013_09_01.model.ListOrderItemsResult listOrderItemsResult = null;
		try {
			listOrderItemsResult = new com.amazonservices.mws.orders._2013_09_01.model.ListOrderItemsResult();
			MarketplaceWebServiceOrdersClient client = com.o2r.amazonservices.mws.orders.config.MarketplaceWebServiceOrdersSampleConfig
					.getClient();
			ListOrderItemsRequest request = new ListOrderItemsRequest();
			request.setSellerId(authInfo.getSellerid());
			request.setMWSAuthToken(authInfo.getMwsauthtoken());
			request.setAmazonOrderId(amazonOrderId);
			ListOrderItemsResponse response = client.listOrderItems(request);
			ResponseHeaderMetadata rhmd = response.getResponseHeaderMetadata();
			listOrderItemsResult = response.getListOrderItemsResult();
		} catch (Exception expObj) {
			expObj.printStackTrace();
		}
		return listOrderItemsResult;
	}

	@Override
	public AmazonOrderInfo getAmazonOrderInfoObj(AuthInfoBean authInfo,
			String amazonOrderId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	// getAmazonOrderInfoList

	@Override
	public List<AmazonOrderInfo> getAmazonOrderInfoList(
			PartnerSellerAuthInfo authInfo) throws Exception {
		// TODO Auto-generated method stub

		return amazonOrdMgmtDao.getOrderList();
		// return null;
	}

	@Override
	public void saveOrderInfo(
			com.amazonservices.mws.orders._2013_09_01.model.Order order,
			List<com.amazonservices.mws.orders._2013_09_01.model.OrderItem> orderItems)
			throws Exception {
		try {

			AmazonOrderInfo amazonOrderInfo = new AmazonOrderInfo();

			amazonOrderInfo.setAmazonorderid(order.getAmazonOrderId());
			amazonOrderInfo.setSellerorderid(order.getSellerOrderId());
			amazonOrderInfo.setPurchasedate(toDate(order.getPurchaseDate()));
			amazonOrderInfo
					.setLastupdatedate(toDate(order.getLastUpdateDate()));
			amazonOrderInfo.setOrderstatus(order.getOrderStatus());
			amazonOrderInfo
					.setFulfillmentchannel(order.getFulfillmentChannel());
			amazonOrderInfo.setSaleschannel(order.getSalesChannel());
			amazonOrderInfo.setOrderchannel(order.getOrderChannel());
			amazonOrderInfo.setShipservicelevel(order.getShipServiceLevel());
			amazonOrderInfo.setOrdertotalcurrencycode(order.getOrderTotal()
					.getCurrencyCode());
			amazonOrderInfo.setOrdertotalamount(order.getOrderTotal()
					.getAmount());
			amazonOrderInfo.setNumberofitemsshipped(order
					.getNumberOfItemsShipped());
			amazonOrderInfo.setNumberofitemsunshipped(order
					.getNumberOfItemsUnshipped());

			// amazonOrderInfo.setPaymentexecutiondetailcurrencycode(order.getPaymentExecutionDetail().get(0).getPayment().getCurrencyCode());
			// amazonOrderInfo.setPaymentexecutiondetailamount(order.getPaymentExecutionDetail().get(0).getPayment().getAmount());
			// amazonOrderInfo.setPaymentexecutiondetailpaymentmethod(order.getPaymentExecutionDetail().get(0).getPaymentMethod());
			amazonOrderInfo.setPaymentmethod(order.getPaymentMethod());
			amazonOrderInfo.setMarketplaceid(order.getMarketplaceId());
			amazonOrderInfo.setBuyeremail(order.getBuyerEmail());
			amazonOrderInfo.setBuyername(order.getBuyerName());
			amazonOrderInfo.setShipmentservicelevelcategory(order
					.getShipmentServiceLevelCategory());
			amazonOrderInfo.setShippedbyamazontfm(order.getShippedByAmazonTFM()
					.toString());
			amazonOrderInfo.setTfmshipmentstatus(order.getTFMShipmentStatus());
			amazonOrderInfo.setCbadisplayableshippinglabel(order
					.getCbaDisplayableShippingLabel());
			amazonOrderInfo.setOrdertype(order.getOrderType());
			amazonOrderInfo.setEarliestshipdate(toDate(order
					.getEarliestShipDate()));
			amazonOrderInfo
					.setLatestshipdate(toDate(order.getLatestShipDate()));
			amazonOrderInfo.setEarliestdeliverydate(toDate(order
					.getEarliestDeliveryDate()));
			amazonOrderInfo.setLatestdeliverydate(toDate(order
					.getLatestDeliveryDate()));
			if (order.getIsBusinessOrder() != null)
				amazonOrderInfo.setIsbusinessorder(order.getIsBusinessOrder()
						.toString());
			amazonOrderInfo.setPurchaseordernumber(order
					.getPurchaseOrderNumber());
			amazonOrderInfo.setIsprime(order.getIsPrime().toString());
			amazonOrderInfo.setIspremiumorder(order.getIsPremiumOrder()
					.toString());

			// amazonOrderInfo.setSeller(seller);
			// amazonOrderInfo.setRequestid(order);

			List<AmazonOrderItemInfo> amazonOrderItemInfos = new ArrayList<AmazonOrderItemInfo>();
			for (OrderItem orderItem : orderItems) {
				AmazonOrderItemInfo amazonOrderItemInfo = new AmazonOrderItemInfo();
				amazonOrderItemInfo.setAmazonorderid(orderItem.getASIN());
				amazonOrderItemInfo.setSellersku(orderItem.getSellerSKU());
				amazonOrderItemInfo.setOrderitemid(orderItem.getOrderItemId());
				amazonOrderItemInfo.setTitle(orderItem.getTitle());
				amazonOrderItemInfo.setQuantityordered(String.valueOf(orderItem
						.getQuantityOrdered()));
				amazonOrderItemInfo.setQuantityshipped(String.valueOf(orderItem
						.getQuantityShipped()));
				// amazonOrderItemInfo.setPointsgrantedpointsnumber(String.valueOf(orderItem.getPointsGranted().getPointsNumber()));
				// amazonOrderItemInfo.setPointsgrantedcurrencycode(orderItem.getPointsGranted().getPointsMonetaryValue().getCurrencyCode());
				// amazonOrderItemInfo.setPointsgrantedamount(orderItem.getPointsGranted().getPointsMonetaryValue().getAmount());
				amazonOrderItemInfo.setItempricecurrencycode(orderItem
						.getItemPrice().getCurrencyCode());
				amazonOrderItemInfo.setItempriceamount(orderItem.getItemPrice()
						.getAmount());
				amazonOrderItemInfo.setShippingpricecurrencycode(orderItem
						.getShippingPrice().getCurrencyCode());
				amazonOrderItemInfo.setShippingpriceamount(orderItem
						.getShippingPrice().getAmount());
				amazonOrderItemInfo.setGiftwrappricecurrencycode(orderItem
						.getGiftWrapPrice().getCurrencyCode());
				amazonOrderItemInfo.setGiftwrappriceamount(orderItem
						.getGiftWrapPrice().getAmount());
				amazonOrderItemInfo.setItemtaxcurrencycode(orderItem
						.getItemTax().getCurrencyCode());
				amazonOrderItemInfo.setItemtaxamount(orderItem.getItemTax()
						.getAmount());
				amazonOrderItemInfo.setShippingtaxcurrencycode(orderItem
						.getShippingTax().getCurrencyCode());
				amazonOrderItemInfo.setShippingtaxamount(orderItem
						.getShippingTax().getAmount());
				amazonOrderItemInfo.setGiftwraptaxcurrencycode(orderItem
						.getGiftWrapTax().getCurrencyCode());
				amazonOrderItemInfo.setGiftwraptaxamount(orderItem
						.getGiftWrapTax().getAmount());
				amazonOrderItemInfo.setShippingdiscountcurrencycode(orderItem
						.getShippingDiscount().getCurrencyCode());
				amazonOrderItemInfo.setShippingdiscountamount(orderItem
						.getShippingDiscount().getAmount());
				amazonOrderItemInfo.setPromotiondiscountcurrencycode(orderItem
						.getPromotionDiscount().getCurrencyCode());
				amazonOrderItemInfo.setPromotiondiscountamount(orderItem
						.getPromotionDiscount().getAmount());
				amazonOrderItemInfo.setPromotionids(orderItem.getPromotionIds()
						.toString());
				// amazonOrderItemInfo.setCodfeecurrencycode(orderItem.getCODFee().getCurrencyCode());
				// amazonOrderItemInfo.setCodfeeamount(orderItem.getCODFee().getAmount());
				// amazonOrderItemInfo.setCodfeediscountcurrencycode(orderItem.getCODFeeDiscount().getCurrencyCode());
				// amazonOrderItemInfo.setCodfeediscountamount(orderItem.getCODFeeDiscount().getAmount());
				amazonOrderItemInfo.setGiftmessagetext(orderItem
						.getGiftMessageText());
				amazonOrderItemInfo.setGiftwraplevel(orderItem
						.getGiftWrapLevel());
				// amazonOrderItemInfo.setInvoicedatainvoicerequirement(orderItem.getInvoiceData().getInvoiceRequirement());
				// amazonOrderItemInfo.setInvoicedatabuyerselectedinvoicecategory(orderItem.getInvoiceData().getBuyerSelectedInvoiceCategory());
				// amazonOrderItemInfo.setInvoicedatainvoicetitle(orderItem.getInvoiceData().getInvoiceTitle());
				// amazonOrderItemInfo.setInvoicedatainvoiceinformation(orderItem.getInvoiceData().getInvoiceInformation());
				amazonOrderItemInfo.setConditionnote(orderItem
						.getConditionNote());
				amazonOrderItemInfo.setConditionid(orderItem.getConditionId());
				amazonOrderItemInfo.setConditionsubtypeid(orderItem
						.getConditionSubtypeId());
				amazonOrderItemInfo.setScheduleddeliverystartdate(orderItem
						.getScheduledDeliveryStartDate());
				amazonOrderItemInfo.setScheduleddeliveryenddate(orderItem
						.getScheduledDeliveryEndDate());
				amazonOrderItemInfo.setPricedesignation(orderItem
						.getPriceDesignation());
				// amazonOrderItemInfo.setBuyercustomizedurl(orderItem.getBuyerCustomizedInfo().getCustomizedURL());
				amazonOrderItemInfos.add(amazonOrderItemInfo);
			}

			//amazonOrderInfo.setAmazonOrderItemInfos(new HashSet<AmazonOrderItemInfo>(amazonOrderItemInfos));
			System.out.println(" amazonOrderInfo" + amazonOrderInfo);
			if (amazonOrderInfo != null)
				amazonOrdMgmtDao.saveAmazonOrderInfo(amazonOrderInfo);
		} catch (Exception expObj) {
			expObj.printStackTrace();
		}
	}

	@Override
	public void saveOrderInfo(
			com.amazonservices.mws.orders._2013_09_01.model.Order order)
			throws Exception {
		try {
			AmazonOrderInfo amazonOrderInfo = new AmazonOrderInfo();
			amazonOrderInfo.setAmazonorderid(order.getAmazonOrderId());
			amazonOrderInfo.setSellerorderid(order.getSellerOrderId());
			amazonOrderInfo.setPurchasedate(toDate(order.getPurchaseDate()));
			amazonOrderInfo
					.setLastupdatedate(toDate(order.getLastUpdateDate()));
			amazonOrderInfo.setOrderstatus(order.getOrderStatus());
			amazonOrderInfo
					.setFulfillmentchannel(order.getFulfillmentChannel());
			amazonOrderInfo.setSaleschannel(order.getSalesChannel());
			amazonOrderInfo.setOrderchannel(order.getOrderChannel());
			amazonOrderInfo.setShipservicelevel(order.getShipServiceLevel());
			amazonOrderInfo.setOrdertotalcurrencycode(order.getOrderTotal()
					.getCurrencyCode());
			amazonOrderInfo.setOrdertotalamount(order.getOrderTotal()
					.getAmount());
			amazonOrderInfo.setNumberofitemsshipped(order
					.getNumberOfItemsShipped());
			amazonOrderInfo.setNumberofitemsunshipped(order
					.getNumberOfItemsUnshipped());
			// amazonOrderInfo.setPaymentexecutiondetailcurrencycode(order.getPaymentExecutionDetail().get(0).getPayment().getCurrencyCode());
			// amazonOrderInfo.setPaymentexecutiondetailamount(order.getPaymentExecutionDetail().get(0).getPayment().getAmount());
			// amazonOrderInfo.setPaymentexecutiondetailpaymentmethod(order.getPaymentExecutionDetail().get(0).getPaymentMethod());
			amazonOrderInfo.setPaymentmethod(order.getPaymentMethod());
			amazonOrderInfo.setMarketplaceid(order.getMarketplaceId());
			amazonOrderInfo.setBuyeremail(order.getBuyerEmail());
			amazonOrderInfo.setBuyername(order.getBuyerName());
			amazonOrderInfo.setShipmentservicelevelcategory(order
					.getShipmentServiceLevelCategory());
			amazonOrderInfo.setShippedbyamazontfm(order.getShippedByAmazonTFM()
					.toString());
			amazonOrderInfo.setTfmshipmentstatus(order.getTFMShipmentStatus());
			amazonOrderInfo.setCbadisplayableshippinglabel(order
					.getCbaDisplayableShippingLabel());
			amazonOrderInfo.setOrdertype(order.getOrderType());
			amazonOrderInfo.setEarliestshipdate(toDate(order
					.getEarliestShipDate()));
			amazonOrderInfo
					.setLatestshipdate(toDate(order.getLatestShipDate()));
			amazonOrderInfo.setEarliestdeliverydate(toDate(order
					.getEarliestDeliveryDate()));
			amazonOrderInfo.setLatestdeliverydate(toDate(order
					.getLatestDeliveryDate()));
			// amazonOrderInfo.setIsbusinessorder(order.getIsBusinessOrder().toString());
			amazonOrderInfo.setIsbusinessorder(order.getIsBusinessOrder() + "");
			amazonOrderInfo.setPurchaseordernumber(order
					.getPurchaseOrderNumber());
			amazonOrderInfo.setIsprime(order.getIsPrime().toString());
			amazonOrderInfo.setIspremiumorder(order.getIsPremiumOrder()
					.toString());
			// amazonOrderInfo.setRequestid(order);

			// amazonOrderInfo.setSeller(seller);

			// amazonOrderInfo.setAmazonOrderItemInfos(new
			// HashSet<AmazonOrderItemInfo>(amazonOrderItemInfos));
			amazonOrdMgmtDao.saveAmazonOrderInfo(amazonOrderInfo);
		} catch (Exception expObj) {
			expObj.printStackTrace();
		}
	}

	@Override
	public Date toDate(XMLGregorianCalendar calendar) throws Exception {
		try {
			if (calendar == null) {
				return null;
			} else {
				return calendar.toGregorianCalendar().getTime();
			}
		} catch (Exception expObj) {
			expObj.printStackTrace();
		}
		return calendar.toGregorianCalendar().getTime();
	}

	/*
	 * @Override public PartnerSellerAuthInfo getAuthInfoBeanObj(
	 * PartnerSellerAuthInfo sellerAuthInfo) throws Exception { // TODO
	 * Auto-generated method stub return null; }
	 * 
	 * @Override public ListOrdersResult getListOrders(XMLGregorianCalendar
	 * createdAfter, XMLGregorianCalendar createdBefore, List<String>
	 * orderStatus, PartnerSellerAuthInfo authInfo) throws Exception { // TODO
	 * Auto-generated method stub return null; }
	 * 
	 * @Override public ListOrderItemsResult getListOrderItems(
	 * PartnerSellerAuthInfo authInfo, String amazonOrderId) throws Exception {
	 * // TODO Auto-generated method stub return null; }
	 */

}
