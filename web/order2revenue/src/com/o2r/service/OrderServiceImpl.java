package com.o2r.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.o2r.bean.DebitNoteBean;
import com.o2r.bean.PoPaymentBean;
import com.o2r.dao.OrderDao;
import com.o2r.model.Order;
import com.o2r.model.OrderPayment;
import com.o2r.model.OrderRTOorReturn;

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
public void addOrder(Order order ,int sellerId) {
	orderDao.addOrder(order,sellerId);

}

@Override
public List<Order> listOrders(int sellerId) {
	return orderDao.listOrders(sellerId);
}

@Override
public Order getOrder(int orderId) {
	return orderDao.getOrder(orderId);
}

@Override
public void deleteOrder(Order order,int sellerId) {

	orderDao.deleteOrder(order,sellerId);
}

@Override
public void addReturnOrder(String channelOrderId ,OrderRTOorReturn orderReturn,int sellerId)
{
	orderDao.addReturnOrder(channelOrderId,orderReturn,sellerId);
}

@Override
public List<Order> findOrders(String column , String value ,int sellerId)
{
	return orderDao.findOrders(column,value,sellerId);
}
@Override
public List<Order> findOrdersbyDate(String column ,Date startDate , Date endDate ,int sellerId)
{
	return orderDao.findOrdersbyDate(column ,startDate,endDate,sellerId);
}
@Override
public List<Order> findOrdersbyReturnDate(String column ,Date startDate , Date endDate ,int sellerId)
{
	return orderDao.findOrdersbyReturnDate(column ,startDate,endDate,sellerId);
}
@Override
public List<Order> findOrdersbyPaymentDate(String column ,Date startDate , Date endDate ,int sellerId)
{
	return orderDao.findOrdersbyPaymentDate(column ,startDate,endDate,sellerId);
}
@Override
public List<Order> findOrdersbyCustomerDetails(String column , String value ,int sellerId)
{
	return orderDao.findOrdersbyCustomerDetails(column,value,sellerId);
}
@Override
public void deleteReturnInfo(String orderId)
{
	orderDao.deleteReturnInfo(orderId);
}

@Override
public Order addOrderPayment(String skucode ,String channelOrderId , OrderPayment orderPayment,int sellerId)
{
	return orderDao.addOrderPayment(skucode,channelOrderId ,orderPayment,sellerId);
}
@Override
public Order addOrderPayment(int orderid, OrderPayment orderPayment,int sellerId)
{
	return orderDao.addOrderPayment(orderid,orderPayment,sellerId);
}

@Override
public void addDebitNote(DebitNoteBean dnBean,int sellerId)
{
	orderDao.addDebitNote(dnBean,sellerId);
}

@Override
public void addPOPayment(PoPaymentBean popaBean,int sellerId)
{
	orderDao.addPOPayment(popaBean,sellerId);
}

@Override
public List<Order> listOrders(int sellerId, int pageNo)
{
	return orderDao.listOrders(sellerId, pageNo);
}

@Override
public Order getOrder(int orderId, int sellerId) {
	return orderDao.getOrder(orderId, sellerId);
}
}