package com.o2r.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.o2r.bean.ChannelSalesDetails;
import com.o2r.bean.DebitNoteBean;
import com.o2r.bean.PoPaymentBean;
import com.o2r.bean.PoPaymentDetailsBean;
import com.o2r.dao.OrderDao;
import com.o2r.helper.CustomException;
import com.o2r.model.GatePass;
import com.o2r.model.Order;
import com.o2r.model.OrderPayment;
import com.o2r.model.OrderRTOorReturn;
import com.o2r.model.ProductConfig;

/**
 * @author Deep Mehortra
 *
 */
@Service("orderService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addOrder(Order order, int sellerId) throws CustomException {
		orderDao.addOrder(order, sellerId);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Order addPO(Order order, int sellerId) throws CustomException {
		return orderDao.addPO(order, sellerId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Order generateConsolidatedOrder(List<Order> orderlist, int sellerId)
			throws CustomException {
		return orderDao.generateConsolidatedOrder(orderlist, sellerId);

	}

	@Override
	public List<Order> listOrders(int sellerId) throws CustomException {
		return orderDao.listOrders(sellerId);
	}

	@Override
	public Order getOrder(int orderId) throws CustomException {
		return orderDao.getOrder(orderId);
	}

	@Override
	public Order getConsolidatedOrder(String poId, String invoiceId)
			throws CustomException {
		return orderDao.getConsolidatedOrder(poId, invoiceId);
	}

	@Override
	public void deleteOrder(Order order, int sellerId) throws CustomException {

		orderDao.deleteOrder(order, sellerId);
	}

	@Override
	public void addReturnOrder(String channelOrderId,
			OrderRTOorReturn orderReturn, int sellerId) throws CustomException {
		orderDao.addReturnOrder(channelOrderId, orderReturn, sellerId);
	}

	@Override
	public List<Order> findOrders(String column, String value, int sellerId,
			boolean poOrder, boolean isSearch) throws CustomException {
		return orderDao.findOrders(column, value, sellerId, poOrder,isSearch);
	}

	@Override
	public List<Order> findOrdersbyDate(String column, Date startDate,
			Date endDate, int sellerId,  boolean poOrder) throws CustomException {
		return orderDao.findOrdersbyDate(column, startDate, endDate, sellerId, poOrder);
	}

	@Override
	public List<Order> findOrdersbyReturnDate(String column, Date startDate,
			Date endDate, int sellerId) throws CustomException {
		return orderDao.findOrdersbyReturnDate(column, startDate, endDate,
				sellerId);
	}

	@Override
	public List<Order> findOrdersbyPaymentDate(String column, Date startDate,
			Date endDate, int sellerId) throws CustomException {
		return orderDao.findOrdersbyPaymentDate(column, startDate, endDate,
				sellerId);
	}

	@Override
	public List<Order> findOrdersbyCustomerDetails(String column, String value,
			int sellerId) throws CustomException {
		return orderDao.findOrdersbyCustomerDetails(column, value, sellerId);
	}

	@Override
	public void deleteReturnInfo(String orderId) throws CustomException {
		orderDao.deleteReturnInfo(orderId);
	}

	@Override
	public Order addOrderPayment(String skucode, String channelOrderId,
			OrderPayment orderPayment, int sellerId) throws CustomException {
		return orderDao.addOrderPayment(skucode, channelOrderId, orderPayment,
				sellerId);
	}

	@Override
	public Order addOrderPayment(int orderid, OrderPayment orderPayment,
			int sellerId) throws CustomException {
		return orderDao.addOrderPayment(orderid, orderPayment, sellerId);
	}

	@Override
	public void addDebitNote(DebitNoteBean dnBean, int sellerId)
			throws CustomException {
		orderDao.addDebitNote(dnBean, sellerId);
	}

	@Override
	public Order addPOPayment(PoPaymentBean popaBean, int sellerId)
			throws CustomException {
		return orderDao.addPOPayment(popaBean, sellerId);
	}

	@Override
	public List<Order> listOrders(int sellerId, int pageNo)
			throws CustomException {
		return orderDao.listOrders(sellerId, pageNo);
	}

	@Override
	public List<Order> listPOOrders(int sellerId, int pageNo)
			throws CustomException {
		return orderDao.listPOOrders(sellerId, pageNo);
	}

	@Override
	public Order getOrder(int orderId, int sellerId) throws CustomException {
		return orderDao.getOrder(orderId, sellerId);
	}

	@Override
	public List<ChannelSalesDetails> findChannelOrdersbyDate(String string,
			Date startDate, Date endDate, int sellerIdfromSession) {
		return orderDao.findChannelOrdersbyDate(string, startDate, endDate,
				sellerIdfromSession);
	}

	@Override
	public void updatePOOrders(List<Order> orderlist, Order consolidatedOrder)
			throws CustomException {
		orderDao.updatePOOrders(orderlist, consolidatedOrder);

	}

	@Override
	public Order findPOOrder(String poID, String invoiceID,
			String channelSkuRef, int sellerId) throws CustomException {
		return orderDao.findPOOrder(poID, invoiceID, channelSkuRef, sellerId);
	}

	@Override
	public GatePass addGatePass(ProductConfig productConfig, GatePass gatepass, int sellerId)
			throws CustomException {
		return orderDao.addGatePass(productConfig, gatepass, sellerId);

	}

	@Override
	public List<Order> getPOOrdersFromConsolidated(int orderId, int sellerId)
			throws CustomException {
		return orderDao.getPOOrdersFromConsolidated(orderId, sellerId);
	}

	@Override
	public List<Order> listGatePasses(int sellerId, int pageNo)
			throws CustomException {
		return orderDao.listGatePasses(sellerId, pageNo);
	}

	@Override
	public OrderRTOorReturn generateConsolidatedReturn(
			List<GatePass> gatepasslist, int sellerId) throws CustomException {
		return orderDao.generateConsolidatedReturn(gatepasslist, sellerId);
	}

	@Override
	public void updateGatePasses(List<GatePass> gatepasslist,
			OrderRTOorReturn consolidatedReturn) throws CustomException {
		orderDao.updateGatePasses(gatepasslist, consolidatedReturn);
	}

	@Override
	public Order findConsolidatedPO(String column, String value, int sellerId)
			throws CustomException {
		return orderDao.findConsolidatedPO(column, value, sellerId);
	}

	@Override
	public List<PoPaymentDetailsBean> getPOPaymentDetails(int sellerId,
			boolean isMonthly) {
		return orderDao.getPOPaymentDetails(sellerId, isMonthly);
	}
}
