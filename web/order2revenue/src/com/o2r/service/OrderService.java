package com.o2r.service;

import java.util.Date;
import java.util.List;

import com.o2r.bean.ChannelSalesDetails;
import com.o2r.bean.DebitNoteBean;
import com.o2r.bean.PartnerDetailsBean;
import com.o2r.bean.PoPaymentBean;
import com.o2r.bean.PoPaymentDetailsBean;
import com.o2r.helper.CustomException;
import com.o2r.model.GatePass;
import com.o2r.model.Order;
import com.o2r.model.OrderPayment;
import com.o2r.model.OrderRTOorReturn;
import com.o2r.model.ProductConfig;

/**
 * @author Deep Mehrotra
 *
 */
public interface OrderService {

	//public void addOrder(Order order, int sellerId) throws CustomException;

	public List<Order> listOrders(int sellerId) throws CustomException;
	
	public List<Order> listMpOrders(int sellerId) throws CustomException;

	public Order getOrder(int orderId) throws CustomException;

	public Order getOrder(int orderId, int sellerId) throws CustomException;

	public void deleteOrder(Order order, int sellerId) throws CustomException;

	public void addReturnOrder(String channelOrderId,
			OrderRTOorReturn orderReturn, int sellerId) throws CustomException;

	public void deleteReturnInfo(String orderId) throws CustomException;

	public List<Order> findOrders(String column, String value, int sellerId,
			boolean poOrder, boolean isSearch) throws CustomException;

	public List<Order> findOrdersbyDate(String column, Date startDate,
			Date endDate, int sellerId, boolean poOrder) throws CustomException;

	public List<Order> findOrdersbyReturnDate(String column, Date startDate,
			Date endDate, int sellerId) throws CustomException;

	public List<Order> findOrdersbyPaymentDate(String column, Date startDate,
			Date endDate, int sellerId) throws CustomException;

	public List<Order> findOrdersbyCustomerDetails(String column, String value,
			int sellerId) throws CustomException;

	public Order addOrderPayment(String skucode, String channelOrderId,
			OrderPayment orderPayment, int sellerId) throws CustomException;

	public Order addOrderPayment(int orderid, OrderPayment orderPayment,
			int sellerId) throws CustomException;

	public void addDebitNote(DebitNoteBean dnBean, int sellerId)
			throws CustomException;

	public Order addPOPayment(PoPaymentBean popaBean, int sellerId)
			throws CustomException;

	public List<Order> listOrders(int sellerId, int pageNo)
			throws CustomException;

	public List<Order> listPOOrders(int sellerId, int pageNo)
			throws CustomException;

	public List<ChannelSalesDetails> findChannelOrdersbyDate(String string,
			Date startDate, Date endDate, int sellerIdfromSession);

	public Order getConsolidatedOrder(String poId, String invoiceId)
			throws CustomException;

	public Order addPO(Order order, int sellerId) throws CustomException;

	public Order generateConsolidatedOrder(List<Order> orderlist, int sellerId)
			throws CustomException;

	public void updatePOOrders(List<Order> orderlist, Order consolidatedOrder)
			throws CustomException;

	public Order findPOOrder(String poID, String invoiceID,
			String channelSkuRef, int sellerId) throws CustomException;

	public GatePass addGatePass(ProductConfig productConfig, GatePass gatepass, int sellerId)
			throws CustomException;

	public List<Order> getPOOrdersFromConsolidated(int orderId, int sellerId)
			throws CustomException;

	public List<Order> listGatePasses(int sellerId, int pageNo)
			throws CustomException;

	public OrderRTOorReturn generateConsolidatedReturn(
			List<GatePass> gatepasslist, int sellerId) throws CustomException;

	public void updateGatePasses(List<GatePass> gatepasslist,
			OrderRTOorReturn consolidatedReturn) throws CustomException;
	
	public Order findConsolidatedPO(String column, String value, int sellerId)
			throws CustomException;

	public List<PoPaymentDetailsBean> getPOPaymentDetails(int sellerId,
			String year);

	public boolean isPOOrderUploaded(String poId, String invoiceId, int sellerId)
			throws CustomException;

	public List<GatePass> getGatepassesFromConsolidated(int returnId, int sellerId)
			throws CustomException;

	public List<Order> listDisputedGatePasses(int sellerId, int pageNo)
			throws CustomException;
	
	public void addOrder(List<Order> orderList, int sellerId) throws CustomException;

	public List<Order> findPOOrdersbyDate(String string, Date startDate,
			Date endDate, int sellerId) throws CustomException;
	
	public int mpOrdersCount(int sellerId);
	
	public int poOrdersCount(int sellerId);
	
	public List<String> listOrderIds(String criteria,int sellerId);
	
	public List<Order> searchAsIsOrder(String criteria, String ID, int sellerId);
	
	public PartnerDetailsBean detailsOfPartner(String pcName, int sellerID);
	
	public List<Order> findOrdersOnCriteria(String column, String value, int sellerId,
			boolean poOrder, boolean isSearch, int pageNo);
	
	public int countOnCriteria(String column, String value, int sellerId,
			boolean poOrder, boolean isSearch);
	
	public void markOrderStatus(String status, int orderId, int sellerId);

	public boolean reverseOrder(int orderId, int sellerId) throws CustomException;
}
