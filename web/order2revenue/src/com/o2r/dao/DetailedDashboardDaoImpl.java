package com.o2r.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.o2r.bean.DetailedDashboardBean;
import com.o2r.helper.GlobalConstant;
import com.o2r.model.Order;

@Repository("DetailedDashboardDao")
public class DetailedDashboardDaoImpl implements DetailedDashboardDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	static Logger log = Logger.getLogger(DetailedDashboardDaoImpl.class.getName());
	
	private static final String grossProfitForMPDurationQuery = "select sum(ot.grossMargin) from order_table ot  where ot.shippedDate "
			+ "between :startDate AND :endDate and ot.poOrder =0  and ot.seller_Id=:sellerId";
	
	private static final String grossProfitPODurationQuery = "Select sum(ot.grossProfit) as grossProfit"
			+ " from Order_Table ot where ot.shippedDate "
			+ "between  :startDate AND :endDate and ot.poOrder =1 and  ot.consolidatedOrder_orderId is NULL "
			+ "and ot.seller_Id=:sellerId";
	
	private static final String grossBadquantityMPDurationQuery = "select sum(ot.productCost*ort.badReturnQty) from order_table ot,orderreturn ort where ort.returnDate "
			+ "between :startDate AND :endDate and ot.orderReturnOrRTO_returnId =ort.returnId and ot.poOrder =0  and ot.seller_Id=:sellerId";
	
	private static final String orderQtyinTimeQuery = "Select sum(ot.quantity) as quantity from "
			+ "Order_Table ot where ot.shippedDate between :startDate AND :endDate "
			+ "and (ot.poOrder =0 OR (ot.poOrder =1 and  ot.consolidatedOrder_orderId is NULL ))and "
			+ "ot.seller_Id=:sellerId";
	
	private static final String orderNRDurationQuery = "Select sum(ot.grossNetRate * ot.quantity) as grossnetrate from "			
			+ "Order_Table ot where ot.shippedDate between :startDate AND "
			+ ":endDate and ot.poOrder =0 and ot.seller_Id=:sellerId";
	
	private static final String orderNRPODurationQuery = "Select sum(ot.netRate) as grossnetrate from "			
			+ "Order_Table ot where ot.shippedDate between :startDate AND "
			+ ":endDate and ot.poOrder =1 and ot.consolidatedOrder_orderId is NULL "
			+ "and ot.seller_Id=:sellerId";
	
	private static final String returnNRPODuratinQuery = "Select sum(ort.netNR) as returnCharges from "
			+ "Order_Table ot ,OrderReturn  ort where ort.returnDate between :startDate AND :endDate and "
			+ "ort.returnId=ot.orderReturnOrRTO_returnId and ot.poOrder =1 and ot.consolidatedOrder_orderId is NULL and "
			+ "ot.seller_Id=:sellerId";
	
	private static final String returnNRMPDurationQuery = "Select sum(ot.grossNetRate * ort.returnorrtoQty) as returnCharges from Order_Table ot ,OrderReturn  ort where "
			+ "ort.returnDate between :startDate AND :endDate and ort.returnId=ot.orderReturnOrRTO_returnId "
			+ "and ot.poOrder =0 and ot.seller_Id=:sellerId ";
	
	private static final String orderPRDurationQuery = "Select sum(ot.pr * ot.quantity) as grossnetrate from "			
			+ "Order_Table ot where ot.shippedDate between :startDate AND "
			+ ":endDate and ot.poOrder =0 and ot.seller_Id=:sellerId";
	
	private static final String orderPRPODurationQuery = "Select sum(ot.pr) as grossnetrate from "			
			+ "Order_Table ot where ot.shippedDate between :startDate AND "
			+ ":endDate and ot.poOrder =1 and ot.consolidatedOrder_orderId is NULL "
			+ "and ot.seller_Id=:sellerId";
	
	private static final String returnPRPODuratinQuery = "Select sum(ort.netPR) as returnCharges from "
			+ "Order_Table ot ,OrderReturn  ort where ort.returnDate between :startDate AND :endDate and "
			+ "ort.returnId=ot.orderReturnOrRTO_returnId and ot.poOrder =1 and ot.consolidatedOrder_orderId is NULL and "
			+ "ot.seller_Id=:sellerId";
	
	private static final String returnPRMPDurationQuery = "Select sum(ot.pr * ort.returnorrtoQty) as returnCharges from Order_Table ot ,OrderReturn  ort where "
			+ "ort.returnDate between :startDate AND :endDate and ort.returnId=ot.orderReturnOrRTO_returnId "
			+ "and ot.poOrder =0 and ot.seller_Id=:sellerId ";
	
	private static final String additionalChargesDurationQuery = "Select sum(ort.estimateddeduction) as addCharges from Order_Table ot ,OrderReturn  ort where "
			+ "ort.returnDate between :startDate AND :endDate and ort.returnId=ot.orderReturnOrRTO_returnId "
			+ "and ot.poOrder =0 and ot.seller_Id=:sellerId ";
	
	private static final String orderSPDurationQuery = "Select sum(ot.orderSP) as orderSp from "			
			+ "Order_Table ot where ot.shippedDate between :startDate AND "
			+ ":endDate and ot.poOrder =0 and ot.seller_Id=:sellerId";
	
	private static final String orderSPPODurationQuery = "Select sum(ot.poPrice) as grossnetrate from "			
			+ "Order_Table ot,OrderReturn ort where ot.shippedDate between :startDate AND "
			+ ":endDate and ot.poOrder =1 and ot.consolidatedOrder_orderId is NULL and ort.returnId=ot.orderReturnOrRTO_returnId "
			+ "and ort.returnDate is NULL and ot.seller_Id=:sellerId";
	
	private static final String returnSPPODuratinQuery = "Select sum(ot.poPrice) as returnCharges from "
			+ "Order_Table ot ,OrderReturn  ort where ort.returnDate between :startDate AND :endDate and "
			+ "ort.returnId=ot.orderReturnOrRTO_returnId and ot.poOrder =1 and ot.consolidatedOrder_orderId is NULL and "
			+ "ot.shippedDate is NULL and ot.seller_Id=:sellerId";
	
	private static final String returnSPMPDurationQuery = "Select sum((ot.orderSP / ot.quantity)* ort.returnorrtoQty) as returnCharges from Order_Table ot ,OrderReturn  ort where "
			+ "ort.returnDate between :startDate AND :endDate and ort.returnId=ot.orderReturnOrRTO_returnId "
			+ "and ot.poOrder =0 and ot.seller_Id=:sellerId ";
	
	private static final String upcomingPaymentChannelWise = "select ot.pcName as Channel, sum(ot.netRate) as NR from order_table ot where ot.seller_id = :sellerId and "
			+ "ot.paymentDueDate >:currentDate  group by ot.pcName";
	
	private static final String upcomingPaymentAll = "select sum(ot.netRate) as NR from order_table ot where ot.seller_id = :sellerId and "
			+ "ot.paymentDueDate >:currentDate";
	
	private static final String outstandingPaymentChannelWise = "select ot.pcName, sum(ot.netRate) from order_table ot, orderpay op where ot.seller_id = :sellerId and "
			+ "ot.orderPayment_paymentId = op.paymentId and op.paymentDifference <> 0 group by ot.pcName";
	
	private static final String outstandingPaymentAll = "select sum(ot.netRate) from order_table ot, orderpay op where ot.seller_id = :sellerId "
			+ "and ot.orderPayment_paymentId = op.paymentId and op.paymentDifference <> 0";
	
	private static final String topSellingRegions = "SELECT  count(ot.orderId) as count,cus.customerCity FROM order_table ot, customer cus, orderreturn ort "
			+ "where ot.seller_id = :sellerId and ot.orderReturnOrRTO_returnId = ort.returnId and ort.returnDate is not null "
			+ "and ort.returnDate between :startDate AND :endDate and ot.customer_customerId = cus.customerId "
			+ "and cus.customerCity is not null group by cus.customerCity order by count desc";
	
	private static final String grossProfitForMPMonthWiseQuery = "select monthname(ot.shippedDate),year(ot.shippedDate),sum(ot.grossMargin) as gross from order_table ot  where ot.shippedDate "
			+ "between :startDate AND :endDate and ot.poOrder =0 and ot.seller_Id=:sellerId group by month(ot.shippedDate),year(ot.shippedDate) order by month(ot.shippedDate) desc";
	
	private static final String grossProfitPOMonthWiseQuery = "Select monthname(ot.shippedDate),year(ot.shippedDate),sum(ot.grossProfit) as grossProfit"
			+ " from Order_Table ot where ot.shippedDate "
			+ "between  :startDate AND :endDate and ot.poOrder =1 and  ot.consolidatedOrder_orderId is NULL "
			+ "and ot.seller_Id=:sellerId group by month(ot.shippedDate),year(ot.shippedDate)";
	
	private static final String returnValueForMPMonthWiseQuery = "select monthname(ort.returnDate),year(ort.returnDate),sum((ot.grossMargin / ot.quantity)* ort.returnorrtoQty) "
			+ "from order_table ot, orderreturn ort where ot.orderReturnOrRTO_returnId =ort.returnId "
			+ "and ort.returnDate between :startDate AND :endDate and ot.poOrder =0  and ot.seller_Id=:sellerId group by month(ort.returnDate),year(ort.returnDate)";
	
	private static final String returnValuePOMonthWiseQuery = "Select monthname(ort.returnDate),year(ort.returnDate),sum(ot.grossProfit)"
			+ "from Order_Table ot ,OrderReturn  ort where ort.returnDate "
			+ "between  :startDate AND :endDate and ot.poOrder =1 and  ot.consolidatedOrder_orderId is NULL "
			+ "and ort.returnId=ot.orderReturnOrRTO_returnId and ot.seller_Id=:sellerId group by month(ort.returnDate),year(ort.returnDate)";
	
	private static final String grossBadquantityMPMonthWiseQuery = "select monthname(ort.returnDate),year(ort.returnDate),sum(ot.productCost*ort.badReturnQty) "
			+ "from order_table ot,orderreturn ort where ort.returnDate between :startDate AND :endDate and "
			+ "ot.orderReturnOrRTO_returnId =ort.returnId and ot.poOrder =0  and ot.seller_Id=:sellerId group by month(ort.returnDate),year(ort.returnDate)";
	
	private static final String orderNRMPMonthWiseQuery = "Select monthname(ot.shippedDate),year(ot.shippedDate),sum(ot.grossNetRate * ot.quantity) as grossnetrate from "			
			+ "Order_Table ot where ot.shippedDate between :startDate AND "
			+ ":endDate and ot.poOrder =0 and ot.seller_Id=:sellerId group by month(ot.shippedDate),year(ot.shippedDate) order by month(ot.shippedDate) desc";
	
	private static final String orderNRPOMonthWiseQuery = "Select monthname(ot.shippedDate),year(ot.shippedDate),sum(ot.netRate) as grossnetrate from "			
			+ "Order_Table ot where ot.shippedDate between :startDate AND "
			+ ":endDate and ot.poOrder =1 and ot.consolidatedOrder_orderId is NULL "
			+ "and ot.seller_Id=:sellerId group by month(ot.shippedDate),year(ot.shippedDate)";
	
	private static final String returnNRPOMonthWiseQuery = "Select monthname(ort.returnDate),year(ort.returnDate),sum(ort.netNR) as returnCharges from "
			+ "Order_Table ot ,OrderReturn  ort where ort.returnDate between :startDate AND :endDate and "
			+ "ort.returnId=ot.orderReturnOrRTO_returnId and ot.poOrder =1 and ot.consolidatedOrder_orderId is NULL and "
			+ "ot.seller_Id=:sellerId group by month(ort.returnDate),year(ort.returnDate)";
	
	private static final String returnNRMPMonthWiseQuery = "Select monthname(ort.returnDate),year(ort.returnDate),sum(ot.grossNetRate * ort.returnorrtoQty) as returnCharges from Order_Table ot ,OrderReturn  ort where "
			+ "ort.returnDate between :startDate AND :endDate and ort.returnId=ot.orderReturnOrRTO_returnId "
			+ "and ot.poOrder =0 and ot.seller_Id=:sellerId group by month(ort.returnDate),year(ort.returnDate)";
	
	private static final String additionalChargesMonthWiseQuery = "Select monthname(ort.returnDate),year(ort.returnDate),sum(ort.estimateddeduction) as addCharges from Order_Table ot ,OrderReturn  ort where "
			+ "ort.returnDate between :startDate AND :endDate and ort.returnId=ot.orderReturnOrRTO_returnId "
			+ "and ot.poOrder =0 and ot.seller_Id=:sellerId group by month(ort.returnDate),year(ort.returnDate)";
	
	private static final String orderPRMPMonthWiseQuery = "Select monthname(ot.shippedDate),year(ot.shippedDate),sum(ot.pr * ot.quantity) as grossnetrate from "			
			+ "Order_Table ot where ot.shippedDate between :startDate AND "
			+ ":endDate and ot.poOrder =0 and ot.seller_Id=:sellerId group by month(ot.shippedDate),year(ot.shippedDate) order by month(ot.shippedDate) desc";
	
	private static final String orderPRPOMonthWiseQuery = "Select monthname(ot.shippedDate),year(ot.shippedDate),sum(ot.pr) as grossnetrate from "			
			+ "Order_Table ot where ot.shippedDate between :startDate AND "
			+ ":endDate and ot.poOrder =1 and ot.consolidatedOrder_orderId is NULL "
			+ "and ot.seller_Id=:sellerId group by month(ot.shippedDate),year(ot.shippedDate)";
	
	private static final String returnPRPOMonthWiseQuery = "Select monthname(ort.returnDate),year(ort.returnDate),sum(ort.netPR) as returnCharges from "
			+ "Order_Table ot ,OrderReturn  ort where ort.returnDate between :startDate AND :endDate and "
			+ "ort.returnId=ot.orderReturnOrRTO_returnId and ot.poOrder =1 and ot.consolidatedOrder_orderId is NULL and "
			+ "ot.seller_Id=:sellerId group by month(ort.returnDate),year(ort.returnDate)";
	
	private static final String returnPRMPMonthWiseQuery = "Select monthname(ort.returnDate),year(ort.returnDate),sum(ot.pr * ort.returnorrtoQty) as returnCharges from Order_Table ot ,OrderReturn  ort where "
			+ "ort.returnDate between :startDate AND :endDate and ort.returnId=ot.orderReturnOrRTO_returnId "
			+ "and ot.poOrder =0 and ot.seller_Id=:sellerId group by month(ort.returnDate),year(ort.returnDate)";
	
	private static final String orderSPMPMonthWiseQuery = "Select monthname(ot.shippedDate),year(ot.shippedDate),sum(ot.orderSP) as orderSp from "			
			+ "Order_Table ot where ot.shippedDate between :startDate AND "
			+ ":endDate and ot.poOrder =0 and ot.seller_Id=:sellerId group by month(ot.shippedDate),year(ot.shippedDate) order by month(ot.shippedDate) desc";
	
	private static final String orderSPPOMonthWiseQuery = "Select monthname(ot.shippedDate),year(ot.shippedDate),sum(ot.poPrice) as grossnetrate from "			
			+ "Order_Table ot,OrderReturn ort where ot.shippedDate between :startDate AND "
			+ ":endDate and ot.poOrder =1 and ot.consolidatedOrder_orderId is NULL and ort.returnId=ot.orderReturnOrRTO_returnId "
			+ "and ort.returnDate is NULL and ot.seller_Id=:sellerId group by month(ot.shippedDate),year(ot.shippedDate)";
	
	private static final String returnSPPOMonthWiseQuery = "Select monthname(ort.returnDate),year(ort.returnDate),sum(ot.poPrice) as returnCharges from "
			+ "Order_Table ot ,OrderReturn  ort where ort.returnDate between :startDate AND :endDate and "
			+ "ort.returnId=ot.orderReturnOrRTO_returnId and ot.poOrder =1 and ot.consolidatedOrder_orderId is NULL and "
			+ "ot.shippedDate is NULL and ot.seller_Id=:sellerId group by month(ort.returnDate),year(ort.returnDate)";
	
	private static final String returnSPMPMonthWiseQuery = "Select monthname(ort.returnDate),year(ort.returnDate),sum((ot.orderSP / ot.quantity)* ort.returnorrtoQty) as returnCharges from Order_Table ot ,OrderReturn  ort where "
			+ "ort.returnDate between :startDate AND :endDate and ort.returnId=ot.orderReturnOrRTO_returnId "
			+ "and ot.poOrder =0 and ot.seller_Id=:sellerId group by month(ort.returnDate),year(ort.returnDate)";
	
	private static final String grossSaleQuantityMonthWise = "Select monthname(ot.shippedDate),year(ot.shippedDate),sum(ot.quantity) from "
			+ "Order_Table ot where ot.shippedDate between :startDate AND :endDate "
			+ "and (ot.poOrder =0 OR (ot.poOrder =1 and  ot.consolidatedOrder_orderId is NULL ))and "
			+ "ot.seller_Id=:sellerId group by month(ot.shippedDate),year(ot.shippedDate)";
	
	private static final String returnSaleQuantityMonthWise = "Select monthname(ort.returnDate),year(ort.returnDate),sum(ort.returnorrtoQty) from "
			+ "Order_Table ot, OrderReturn ort where ort.returnDate between :startDate AND :endDate and ort.returnId=ot.orderReturnOrRTO_returnId "
			+ "and (ot.poOrder =0 OR (ot.poOrder =1 and  ot.consolidatedOrder_orderId is NULL ))and "
			+ "ot.seller_Id=:sellerId group by month(ort.returnDate),year(ort.returnDate)";
	
	
	@Override
	public double getGrossGrossProfit(Date startDate, Date endDate, int sellerId) {
		List<Double> results = null;			
		double grossGrossProfit = 0;
		Session session = null;		
		try {	
			double gMp = 0;
			double gPo = 0;			
			session = sessionFactory.openSession();
			session.beginTransaction();
			
			Query gpquerryforMP = session.createSQLQuery(grossProfitForMPDurationQuery)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);					
			results = gpquerryforMP.list();			
			if (results != null && results.size() != 0
					&& results.get(0) != null) {
				gMp = results.get(0);				
			}			
			
			Query gpquerryforPO = session.createSQLQuery(grossProfitPODurationQuery)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			results = gpquerryforPO.list();
			if (results != null && results.size() != 0
					&& results.get(0) != null) {
				gPo = results.get(0);
			}	
			
			grossGrossProfit = gMp + gPo;
		} catch (Exception e) {
			log.error("Failed By Seller ID : "+sellerId, e);
		} finally {
			if(session != null)
				session.close();
		}
		return grossGrossProfit;
	}
	
	@Override
	public double getNetGrossProfit(Date startDate, Date endDate, int sellerId) {					
		double netGrossProfit = 0;
		Session session = null;		
		try {			
			session = sessionFactory.openSession();
			session.beginTransaction();			
			netGrossProfit = DashboardDaoImpl.grossProfitForDuration(session, startDate, endDate, sellerId);
			
		} catch (Exception e) {
			log.error("Failed By Seller ID : "+sellerId, e);
		} finally {
			if(session != null)
				session.close();
		}
		return netGrossProfit;
	}
	
	@Override
	public double getGrossBadquantityValue(Date startDate, Date endDate,
			int sellerId) {
		List<Double> results = null;
		double netGrossBadQtyValue = 0;
		Session session = null;		
		try {			
			session = sessionFactory.openSession();
			session.beginTransaction();			
			Query gpquerryforMP = session.createSQLQuery(grossBadquantityMPDurationQuery)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);					
			results = gpquerryforMP.list();			
			if (results != null && results.size() != 0
					&& results.get(0) != null) {
				netGrossBadQtyValue = results.get(0);				
			}
			
		} catch (Exception e) {
			log.error("Failed By Seller ID : "+sellerId, e);
		} finally {
			if(session != null)
				session.close();
		}
		return netGrossBadQtyValue;
	}
	
	@Override
	public long getNetSaleQuantity(Date startDate, Date endDate, int sellerId) {
		long netSaleQty = 0;
		Session session = null;		
		try {			
			session = sessionFactory.openSession();
			session.beginTransaction();			
			netSaleQty = DashboardDaoImpl.netSaleQtyforTime(session, startDate, endDate, sellerId);
			
		} catch (Exception e) {
			log.error("Failed By Seller ID : "+sellerId, e);
		} finally {
			if(session != null)
				session.close();
		}
		return netSaleQty;
	}
	
	@Override
	public long getGrossSaleQuantity(Date startDate, Date endDate, int sellerId) {
		List<BigDecimal> tempDblist=null;
		long grossSaleQty = 0;
		Session session = null;		
		try {			
			session = sessionFactory.openSession();
			session.beginTransaction();			
			Query grossSQ = session.createSQLQuery(orderQtyinTimeQuery)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);					
			tempDblist = grossSQ.list();			
			if (tempDblist != null && tempDblist.size() != 0
					&& tempDblist.get(0) != null) {
				grossSaleQty = tempDblist.get(0).longValue();				
			}
			
		} catch (Exception e) {
			log.error("Failed By Seller ID : "+sellerId, e);
		} finally {
			if(session != null)
				session.close();
		}
		return grossSaleQty;
	}
	
	@Override
	public double getGrossNR(Date startDate, Date endDate, int sellerId) {
		List<Double> tempDblist=null;
		double grossNR = 0;
		Session session = null;		
		try {
			double mpNR = 0;
			double poNR = 0;
			session = sessionFactory.openSession();
			session.beginTransaction();			
			Query grossMpNR = session.createSQLQuery(orderNRDurationQuery)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);					
			tempDblist = grossMpNR.list();			
			if (tempDblist != null && tempDblist.size() != 0
					&& tempDblist.get(0) != null) {
				mpNR = tempDblist.get(0);			
			}
			
			Query grossPoNR = session.createSQLQuery(orderNRPODurationQuery)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);					
			tempDblist = grossPoNR.list();			
			if (tempDblist != null && tempDblist.size() != 0
					&& tempDblist.get(0) != null) {
				poNR = tempDblist.get(0);				
			}
			
			grossNR = mpNR + poNR;
			
		} catch (Exception e) {
			log.error("Failed By Seller ID : "+sellerId, e);
		} finally {
			if(session != null)
				session.close();
		}
		return grossNR;
	}
	
	@Override
	public double getReturnNR(Date startDate, Date endDate, int sellerId) {
		List<Double> tempDblist=null;
		double returnNR = 0;
		Session session = null;		
		try {
			double mpNR = 0;
			double poNR = 0;
			session = sessionFactory.openSession();
			session.beginTransaction();			
			Query returnMpNR = session.createSQLQuery(returnNRMPDurationQuery)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);					
			tempDblist = returnMpNR.list();			
			if (tempDblist != null && tempDblist.size() != 0
					&& tempDblist.get(0) != null) {
				mpNR = tempDblist.get(0);				
			}
			
			Query returnPoNR = session.createSQLQuery(returnNRPODuratinQuery)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);					
			tempDblist = returnPoNR.list();			
			if (tempDblist != null && tempDblist.size() != 0
					&& tempDblist.get(0) != null) {
				poNR = tempDblist.get(0);				
			}
			
			returnNR = mpNR + poNR;
			
		} catch (Exception e) {
			log.error("Failed By Seller ID : "+sellerId, e);
		} finally {
			if(session != null)
				session.close();
		}
		return returnNR;
	}
	
	@Override
	public double getGrossPR(Date startDate, Date endDate, int sellerId) {
		List<Double> tempDblist=null;
		double grossPR = 0;
		Session session = null;		
		try {
			double mpPR = 0;
			double poPR = 0;
			session = sessionFactory.openSession();
			session.beginTransaction();			
			Query grossMpPR = session.createSQLQuery(orderPRDurationQuery)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);					
			tempDblist = grossMpPR.list();			
			if (tempDblist != null && tempDblist.size() != 0
					&& tempDblist.get(0) != null) {
				mpPR = tempDblist.get(0);				
			}
			
			Query grossPoPR = session.createSQLQuery(orderPRPODurationQuery)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);					
			tempDblist = grossPoPR.list();			
			if (tempDblist != null && tempDblist.size() != 0
					&& tempDblist.get(0) != null) {
				poPR = tempDblist.get(0);				
			}
			
			grossPR = mpPR + poPR;
			
		} catch (Exception e) {
			log.error("Failed By Seller ID : "+sellerId, e);
		} finally {
			if(session != null)
				session.close();
		}
		return grossPR;
	}
	
	@Override
	public double getReturnPR(Date startDate, Date endDate, int sellerId) {
		List<Double> tempDblist=null;
		double returnPR = 0;
		Session session = null;		
		try {
			double mpPR = 0;
			double poPR = 0;
			session = sessionFactory.openSession();
			session.beginTransaction();			
			Query returnMpPR = session.createSQLQuery(returnPRMPDurationQuery)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);					
			tempDblist = returnMpPR.list();			
			if (tempDblist != null && tempDblist.size() != 0
					&& tempDblist.get(0) != null) {
				mpPR = tempDblist.get(0);				
			}
			
			Query returnPoPR = session.createSQLQuery(returnPRPODuratinQuery)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);					
			tempDblist = returnPoPR.list();			
			if (tempDblist != null && tempDblist.size() != 0
					&& tempDblist.get(0) != null) {
				poPR = tempDblist.get(0);				
			}
			
			returnPR = mpPR + poPR;
			
		} catch (Exception e) {
			log.error("Failed By Seller ID : "+sellerId, e);
		} finally {
			if(session != null)
				session.close();
		}
		return returnPR;
	}
	
	@Override
	public double getAdditionalCharges(Date startDate, Date endDate, int sellerId) {
		List<Double> tempDblist=null;
		double addtionalCharges = 0;
		Session session = null;		
		try {			
			session = sessionFactory.openSession();
			session.beginTransaction();			
			Query addCharges = session.createSQLQuery(additionalChargesDurationQuery)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);					
			tempDblist = addCharges.list();			
			if (tempDblist != null && tempDblist.size() != 0
					&& tempDblist.get(0) != null) {
				addtionalCharges = tempDblist.get(0);				
			}
			
		} catch (Exception e) {
			log.error("Failed By Seller ID : "+sellerId, e);
		} finally {
			if(session != null)
				session.close();
		}
		return addtionalCharges;
	}
	
	@Override
	public double getGrossSP(Date startDate, Date endDate, int sellerId) {
		List<Double> tempDblist=null;
		double grossSP = 0;
		Session session = null;		
		try {
			double mpSP = 0;
			double poSP = 0;
			session = sessionFactory.openSession();
			session.beginTransaction();			
			Query grossMpSP = session.createSQLQuery(orderSPDurationQuery)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);					
			tempDblist = grossMpSP.list();			
			if (tempDblist != null && tempDblist.size() != 0
					&& tempDblist.get(0) != null) {
				mpSP = tempDblist.get(0);				
			}
			
			Query grossPoSP = session.createSQLQuery(orderSPPODurationQuery)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);					
			tempDblist = grossPoSP.list();			
			if (tempDblist != null && tempDblist.size() != 0
					&& tempDblist.get(0) != null) {
				poSP = tempDblist.get(0);				
			}
			
			grossSP = mpSP + poSP;
			
		} catch (Exception e) {
			log.error("Failed By Seller ID : "+sellerId, e);
		} finally {
			if(session != null)
				session.close();
		}
		return grossSP;
	}
	
	@Override
	public double getReturnSP(Date startDate, Date endDate, int sellerId) {
		List<Double> tempDblist=null;
		double returnSP = 0;
		Session session = null;		
		try {
			double mpSP = 0;
			double poSP = 0;
			session = sessionFactory.openSession();
			session.beginTransaction();			
			Query returnMpSP = session.createSQLQuery(returnSPMPDurationQuery)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);					
			tempDblist = returnMpSP.list();			
			if (tempDblist != null && tempDblist.size() != 0
					&& tempDblist.get(0) != null) {
				mpSP = tempDblist.get(0);				
			}
			
			Query returnPoSP = session.createSQLQuery(returnSPPODuratinQuery)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);					
			tempDblist = returnPoSP.list();			
			if (tempDblist != null && tempDblist.size() != 0
					&& tempDblist.get(0) != null) {
				poSP = tempDblist.get(0);				
			}
			
			returnSP = mpSP + poSP;
			
		} catch (Exception e) {
			log.error("Failed By Seller ID : "+sellerId, e);
		} finally {
			if(session != null)
				session.close();
		}
		return returnSP;
	}
	
	@Override
	public List<Map<String, Object>> getTopSellingSKU(Date startDate, Date endDate, String status, int sellerId) {
		Object[] result = null;
		List<Object> results = null;
		List<Map<String, Object>> topSKUs = new ArrayList<Map<String,Object>>();
		Map<String, Object> topSKU = new HashMap<String, Object>();
		Session session = null;
		try {	
			session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Order.class);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN)
			.createAlias("orderReturnOrRTO", "orderReturnOrRTO",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.between("shippedDate", startDate, endDate));
			Criterion rest1 = Restrictions.eq("poOrder", false);
			Criterion rest2 = Restrictions.and(	
					Restrictions.eq("poOrder", true),
					Restrictions.isNotNull("consolidatedOrder"));
			criteria.add(Restrictions.or(rest1, rest2));
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			ProjectionList projList = Projections.projectionList();
			projList.add(Projections.groupProperty("productSkuCode"));
			projList.add(Projections.sqlProjection("(sum(quantity)) as grossQty",new String[] { "grossQty" },
					new Type[] { Hibernate.INTEGER }),"grossQty");
			projList.add(Projections.sqlProjection("(sum(quantity-returnorrtoQty)) as netQty",new String[] { "netQty" },
					new Type[] { Hibernate.INTEGER }),"netQty");
			criteria.setProjection(projList);
			criteria.addOrder(org.hibernate.criterion.Order.desc("netQty"));
			if(status != null){
				results = criteria.list();
				if(results != null && results.size() != 0){
					for(Object each : results){
						Object[] sku = (Object[]) each;
						topSKU.put("sku", sku[0]);
						topSKU.put("grossQ", Long.parseLong(sku[1].toString()));
						topSKU.put("netQ", Long.parseLong(sku[2].toString()));
						topSKUs.add(topSKU);
						topSKU = new HashMap<String, Object>();
					}
				}
			} else {
				if(criteria.list().size() != 0){
					result = (Object[]) criteria.list().get(0);			
					if (result != null) {
						topSKU.put("sku", result[0]);
						topSKU.put("grossQ", Long.parseLong(result[1].toString()));
						topSKU.put("netQ", Long.parseLong(result[2].toString()));
						topSKUs.add(topSKU);				
					}
				}				
			}						
		} catch (Exception e) {
			log.error("Failed! by sellerId : "+sellerId,e);
			e.printStackTrace();
		} finally {
			if(session != null)
				session.close();
		}		
		return topSKUs;
	}
	
	@Override
	public List<Map<String, Object>> getTopSellingRegion(Date startDate,
			Date endDate, String status, int sellerId) {
		List<Object> results = null;
		List<Map<String, Object>> topRegions = new ArrayList<Map<String,Object>>();
		Map<String, Long> topRegionGross = new LinkedHashMap<String, Long>();
		Map<String, Long> topRegionReturn = new LinkedHashMap<String, Long>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Session session = null;
		String key= "";
		long value = 0;
		try {	
			session = sessionFactory.openSession();
			session.beginTransaction();
			topRegionGross = DashboardDaoImpl.topSellingRegion(session, startDate, endDate, sellerId);			
			System.out.println(key +" : "+value);
			Query topSelleingRegions = session.createSQLQuery(topSellingRegions)						
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			results = topSelleingRegions.list();
			if(results != null && results.size() != 0){
				for(Object obj : results){
					Object[] topR = (Object[]) obj;
					topRegionReturn.put((String)topR[1], Long.parseLong(topR[0].toString()));
				}
			}
			if(status != null){
				if(topRegionGross != null && topRegionGross.size() != 0){
					for (Map.Entry<String, Long> entry : topRegionGross.entrySet())
					{
						resultMap.put("Region", entry.getKey());						
						resultMap.put("grossSale", entry.getValue());				
						resultMap.put("returnSale", topRegionReturn.get(entry.getKey()) != null ? topRegionReturn.get(entry.getKey()) : 0);
						resultMap.put("netSale", (entry.getValue() - (topRegionReturn.get(entry.getKey()) != null ? topRegionReturn.get(entry.getKey()) : 0)));
						topRegions.add(resultMap);
						resultMap = new LinkedHashMap<String, Object>();
					}
				}
			} else {
				if(topRegionGross.size() != 0){				
					Map.Entry<String, Long> entry=topRegionGross.entrySet().iterator().next();
					key= entry.getKey();
					value=entry.getValue();
					resultMap.put("Region", key);
					resultMap.put("grossSale", value);				
					resultMap.put("returnSale", topRegionReturn.get(key) != null ? topRegionReturn.get(key) : 0);
					resultMap.put("netSale", (value - (topRegionReturn.get(key) != null ? topRegionReturn.get(key) : 0)));
					topRegions.add(resultMap);
				}				
			}			
		} catch (Exception e) {
			log.error("Failed! by sellerId : "+sellerId,e);
			e.printStackTrace();
		} finally {
			if(session != null)
				session.close();
		}		
		return topRegions;
	}
	
	@Override
	public List<Map<String, Object>> getUpcomingPayment(int sellerId,
			String status) {
		List<Map<String, Object>> upcomingPaymentList = new ArrayList<Map<String,Object>>();
		Map<String, Object> upcomingPaymentMap = new HashMap<String, Object>();
		List<Object> upPayList = null;
		Session session = null;
		Date currentDate = new Date();
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			if(status.equalsIgnoreCase("list")){
				Query upcomPayList = session.createSQLQuery(upcomingPaymentChannelWise)						
						.setParameter("sellerId", sellerId)
						.setParameter("currentDate", currentDate);
				upPayList = upcomPayList.list();			
				if (upPayList != null && upPayList.size() != 0
						&& upPayList.get(0) != null) {
					for(Object obj : upPayList)	{
						Object[] result = (Object[]) obj;
						upcomingPaymentMap.put((String) result[0], Double.parseDouble(result[1].toString()));
						upcomingPaymentList.add(upcomingPaymentMap);
						upcomingPaymentMap = new HashMap<String, Object>();
					}
				}
			} else {
				Query upcomPay = session.createSQLQuery(upcomingPaymentAll)						
						.setParameter("sellerId", sellerId)
						.setParameter("currentDate", currentDate);
				if(upcomPay != null && upcomPay.list().size() != 0){
					double upPay = (double) upcomPay.list().get(0);
					upcomingPaymentMap.put("Total", upPay);
					upcomingPaymentList.add(upcomingPaymentMap);
				}	
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(session != null){
				session.close();
			}
		}	
		System.out.println(upcomingPaymentList.size());
		return upcomingPaymentList;
	}
	
	@Override
	public List<Map<String, Object>> getOutstandingPayment(int sellerId, String status) {
		List<Map<String, Object>> outstandingPaymentList = new ArrayList<Map<String,Object>>();
		Map<String, Object> outstandingPaymentMap = new HashMap<String, Object>();
		Session session = null;
		List<Object> ouPayList = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			if(status.equalsIgnoreCase("list")){
				Query outPayList = session.createSQLQuery(outstandingPaymentChannelWise)						
						.setParameter("sellerId", sellerId);
				ouPayList = outPayList.list();			
				if (ouPayList != null && ouPayList.size() != 0
						&& ouPayList.get(0) != null) {
					for(Object obj : ouPayList)	{
						Object[] result = (Object[]) obj;
						outstandingPaymentMap.put((String) result[0], Double.parseDouble(result[1].toString()));
						outstandingPaymentList.add(outstandingPaymentMap);
						outstandingPaymentMap = new HashMap<String, Object>();
					}
				}
			} else {
				Query outPay = session.createSQLQuery(outstandingPaymentAll)						
						.setParameter("sellerId", sellerId);
				if(outPay != null && outPay.list().size() != 0){
					double ouPay = (double) outPay.list().get(0);
					outstandingPaymentMap.put("Total", ouPay);
					outstandingPaymentList.add(outstandingPaymentMap);
				}	
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(session != null){
				session.close();
			}
		}	
		System.out.println(outstandingPaymentList.size());
		return outstandingPaymentList;
	}
	
	@Override
	public List<DetailedDashboardBean> getGrossMarginList(Date startDate,
			Date endDate, int sellerId) {
		
		List<Object> results = null;
		DetailedDashboardBean DDbean = null;
		List<DetailedDashboardBean> grossMarginMonthWiseMapList = new ArrayList<DetailedDashboardBean>();
		Map<String, Object> grossMarginMPMap = new HashMap<String, Object>();
		Map<String, Object> returnMarginMPMap = new HashMap<String, Object>();
		Map<String, Object> grossMarginPOMap = new HashMap<String, Object>();
		Map<String, Object> returnMarginPOMap = new HashMap<String, Object>();
		Map<String, Object> badQuantityMap = new HashMap<String, Object>();
		Map<String, Object> grossMarginMap = new HashMap<String, Object>();
		Map<String, Object> returnMarginMap = new HashMap<String, Object>();
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			Query grossMarginMonthWise = session.createSQLQuery(grossProfitForMPMonthWiseQuery)						
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			results = grossMarginMonthWise.list();
			if(results != null && results.size() != 0){
				for(Object obj : results){
					Object[] grossNR = (Object[]) obj;
					grossMarginMPMap.put(grossNR[0].toString()+","+grossNR[1].toString(), grossNR[2]);
				}
			}
			
			Query grossMarginPOMonthWise = session.createSQLQuery(grossProfitPOMonthWiseQuery)						
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			results = grossMarginPOMonthWise.list();
			if(results != null && results.size() != 0){
				for(Object obj : results){
					Object[] grossNR = (Object[]) obj;
					grossMarginPOMap.put(grossNR[0].toString()+","+grossNR[1].toString(), grossNR[2]);
				}
			}
			
			Query returnMarginMPMonthWise = session.createSQLQuery(returnValueForMPMonthWiseQuery)						
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			results = returnMarginMPMonthWise.list();
			if(results != null && results.size() != 0){
				for(Object obj : results){
					Object[] returnNR = (Object[]) obj;
					returnMarginMPMap.put(returnNR[0].toString()+","+returnNR[1].toString(), returnNR[2]);
				}
			}
			
			Query returnMarginPOMonthWise = session.createSQLQuery(returnValuePOMonthWiseQuery)						
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			results = returnMarginPOMonthWise.list();
			if(results != null && results.size() != 0){
				for(Object obj : results){
					Object[] returnNR = (Object[]) obj;
					returnMarginPOMap.put(returnNR[0].toString()+","+returnNR[1].toString(), returnNR[2]);
				}
			}
			
			Query badQuantityMonthWise = session.createSQLQuery(grossBadquantityMPMonthWiseQuery)						
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			results = badQuantityMonthWise.list();
			if(results != null && results.size() != 0){
				for(Object obj : results){
					Object[] returnNR = (Object[]) obj;
					badQuantityMap.put(returnNR[0].toString()+","+returnNR[1].toString(), returnNR[2]);
				}
			}
			
			if(grossMarginMPMap.size() != 0){
				for (Map.Entry<String, Object> entry : grossMarginMPMap.entrySet())
				{
					grossMarginMap.put(entry.getKey(), (double)entry.getValue() + (double)(grossMarginPOMap.get(entry.getKey()) != null ? grossMarginPOMap.get(entry.getKey()) : 0d));
				}
			}
			if(returnMarginMPMap.size() != 0){
				for (Map.Entry<String, Object> entry : returnMarginMPMap.entrySet())
				{
					returnMarginMap.put(entry.getKey(), (double)entry.getValue() + (double)(returnMarginPOMap.get(entry.getKey()) != null ? returnMarginPOMap.get(entry.getKey()) : 0d));
				}
			}
			
			if(grossMarginMap.size() != 0){				
				for (Map.Entry<String, Object> entry : grossMarginMap.entrySet())
				{
					DDbean = new DetailedDashboardBean();
				    DDbean.setMonth(entry.getKey());
				    DDbean.setMonthNo(GlobalConstant.MonthNo(entry.getKey().substring(0, entry.getKey().indexOf(","))));
				    DDbean.setGrossValue((double)entry.getValue());
				    DDbean.setReturnValue((double)(returnMarginMap.get(entry.getKey()) != null ? returnMarginMap.get(entry.getKey()) : 0d));
				    DDbean.setNetValue((double)entry.getValue() - (double)(returnMarginMap.get(entry.getKey()) != null ? returnMarginMap.get(entry.getKey()) : 0d)
				    						- (double)(badQuantityMap.get(entry.getKey()) != null ? badQuantityMap.get(entry.getKey()) : 0d));
				    DDbean.setBadQtyCharges((double)(badQuantityMap.get(entry.getKey()) != null ? badQuantityMap.get(entry.getKey()) : 0d));
				    grossMarginMonthWiseMapList.add(DDbean);
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(session != null)
				session.close();
		}
		
		return grossMarginMonthWiseMapList;

		
	}
	
	@Override
	public List<DetailedDashboardBean> getActualSaleList(Date startDate,
			Date endDate, int sellerId) {
		List<Object> results = null;
		DetailedDashboardBean DDbean = null;
		List<DetailedDashboardBean> NRMonthWiseMapList = new ArrayList<DetailedDashboardBean>();
		Map<String, Object> grossNRMPMap = new HashMap<String, Object>();
		Map<String, Object> returnNRMPMap = new HashMap<String, Object>();
		Map<String, Object> grossNRPOMap = new HashMap<String, Object>();
		Map<String, Object> returnNRPOMap = new HashMap<String, Object>();
		Map<String, Object> addChargesMap = new HashMap<String, Object>();
		Map<String, Object> grossNRMap = new HashMap<String, Object>();
		Map<String, Object> returnNRMap = new HashMap<String, Object>();
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			Query grossNRMonthWise = session.createSQLQuery(orderNRMPMonthWiseQuery)						
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			results = grossNRMonthWise.list();
			if(results != null && results.size() != 0){
				for(Object obj : results){
					Object[] grossNR = (Object[]) obj;
					grossNRMPMap.put(grossNR[0].toString()+","+grossNR[1].toString(), grossNR[2]);
				}
			}
			
			Query grossNRPOMonthWise = session.createSQLQuery(orderNRPOMonthWiseQuery)						
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			results = grossNRPOMonthWise.list();
			if(results != null && results.size() != 0){
				for(Object obj : results){
					Object[] grossNR = (Object[]) obj;
					grossNRPOMap.put(grossNR[0].toString()+","+grossNR[1].toString(), grossNR[2]);
				}
			}
			
			Query returnNRMonthWise = session.createSQLQuery(returnNRMPMonthWiseQuery)						
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			results = returnNRMonthWise.list();
			if(results != null && results.size() != 0){
				for(Object obj : results){
					Object[] returnNR = (Object[]) obj;
					returnNRMPMap.put(returnNR[0].toString()+","+returnNR[1].toString(), returnNR[2]);
				}
			}
			
			Query returnNRPOMonthWise = session.createSQLQuery(returnNRPOMonthWiseQuery)						
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			results = returnNRPOMonthWise.list();
			if(results != null && results.size() != 0){
				for(Object obj : results){
					Object[] returnNR = (Object[]) obj;
					returnNRPOMap.put(returnNR[0].toString()+","+returnNR[1].toString(), returnNR[2]);
				}
			}
			
			Query addChargesMonthWise = session.createSQLQuery(additionalChargesMonthWiseQuery)						
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			results = addChargesMonthWise.list();
			if(results != null && results.size() != 0){
				for(Object obj : results){
					Object[] returnNR = (Object[]) obj;
					addChargesMap.put(returnNR[0].toString()+","+returnNR[1].toString(), returnNR[2]);
				}
			}
			
			if(grossNRMPMap.size() != 0){
				for (Map.Entry<String, Object> entry : grossNRMPMap.entrySet())
				{
				    grossNRMap.put(entry.getKey(), (double)entry.getValue() + (double)(grossNRPOMap.get(entry.getKey()) != null ? grossNRPOMap.get(entry.getKey()) : 0d));
				}
			}
			if(returnNRMPMap.size() != 0){
				for (Map.Entry<String, Object> entry : returnNRMPMap.entrySet())
				{
				    returnNRMap.put(entry.getKey(), (double)entry.getValue() + (double)(returnNRPOMap.get(entry.getKey()) != null ? returnNRPOMap.get(entry.getKey()) : 0d));
				}
			}			
			if(grossNRMap.size() != 0){				
				for (Map.Entry<String, Object> entry : grossNRMap.entrySet())
				{
					DDbean = new DetailedDashboardBean();
				    DDbean.setMonth(entry.getKey());
				    DDbean.setMonthNo(GlobalConstant.MonthNo(entry.getKey().substring(0, entry.getKey().indexOf(","))));
				    DDbean.setGrossValue((double)entry.getValue());
				    DDbean.setReturnValue((double)(returnNRMap.get(entry.getKey()) != null ? returnNRMap.get(entry.getKey()) : 0d));
				    DDbean.setNetValue((double)entry.getValue() - (double)(returnNRMap.get(entry.getKey()) != null ? returnNRMap.get(entry.getKey()) : 0d)
				    						- (double)(addChargesMap.get(entry.getKey()) != null ? addChargesMap.get(entry.getKey()) : 0d));
				    DDbean.setAddCharges((double)(addChargesMap.get(entry.getKey()) != null ? addChargesMap.get(entry.getKey()) : 0d));
				    NRMonthWiseMapList.add(DDbean);
				}
			}				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(session != null)
				session.close();
		}
		
		return NRMonthWiseMapList;
	}
	
	@Override
	public List<DetailedDashboardBean> getTaxFreeSaleList(Date startDate,
			Date endDate, int sellerId) {
		List<Object> results = null;
		DetailedDashboardBean DDbean = null;
		List<DetailedDashboardBean> PRMonthWiseMapList = new ArrayList<DetailedDashboardBean>();
		Map<String, Object> grossPRMPMap = new HashMap<String, Object>();
		Map<String, Object> returnPRMPMap = new HashMap<String, Object>();
		Map<String, Object> grossPRPOMap = new HashMap<String, Object>();
		Map<String, Object> returnPRPOMap = new HashMap<String, Object>();
		Map<String, Object> addChargesMap = new HashMap<String, Object>();
		Map<String, Object> grossPRMap = new HashMap<String, Object>();
		Map<String, Object> returnPRMap = new HashMap<String, Object>();
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			Query grossPRMonthWise = session.createSQLQuery(orderPRMPMonthWiseQuery)						
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			results = grossPRMonthWise.list();
			if(results != null && results.size() != 0){
				for(Object obj : results){
					Object[] grossNR = (Object[]) obj;
					grossPRMPMap.put(grossNR[0].toString()+","+grossNR[1].toString(), grossNR[2]);
				}
			}
			
			Query grossPRPOMonthWise = session.createSQLQuery(orderPRPOMonthWiseQuery)						
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			results = grossPRPOMonthWise.list();
			if(results != null && results.size() != 0){
				for(Object obj : results){
					Object[] grossNR = (Object[]) obj;
					grossPRPOMap.put(grossNR[0].toString()+","+grossNR[1].toString(), grossNR[2]);
				}
			}
			
			Query returnPRMonthWise = session.createSQLQuery(returnPRMPMonthWiseQuery)						
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			results = returnPRMonthWise.list();
			if(results != null && results.size() != 0){
				for(Object obj : results){
					Object[] returnNR = (Object[]) obj;
					returnPRMPMap.put(returnNR[0].toString()+","+returnNR[1].toString(), returnNR[2]);
				}
			}
			
			Query returnPRPOMonthWise = session.createSQLQuery(returnPRPOMonthWiseQuery)						
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			results = returnPRPOMonthWise.list();
			if(results != null && results.size() != 0){
				for(Object obj : results){
					Object[] returnNR = (Object[]) obj;
					returnPRPOMap.put(returnNR[0].toString()+","+returnNR[1].toString(), returnNR[2]);
				}
			}
			
			Query addChargesMonthWise = session.createSQLQuery(additionalChargesMonthWiseQuery)						
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			results = addChargesMonthWise.list();
			if(results != null && results.size() != 0){
				for(Object obj : results){
					Object[] returnNR = (Object[]) obj;
					addChargesMap.put(returnNR[0].toString()+","+returnNR[1].toString(), returnNR[2]);
				}
			}
			
			if(grossPRMPMap.size() != 0){
				for (Map.Entry<String, Object> entry : grossPRMPMap.entrySet())
				{
					grossPRMap.put(entry.getKey(), (double)entry.getValue() + (double)(grossPRPOMap.get(entry.getKey()) != null ? grossPRPOMap.get(entry.getKey()) : 0d));
				}
			}
			if(returnPRMPMap.size() != 0){
				for (Map.Entry<String, Object> entry : returnPRMPMap.entrySet())
				{
					returnPRMap.put(entry.getKey(), (double)entry.getValue() + (double)(returnPRPOMap.get(entry.getKey()) != null ? returnPRPOMap.get(entry.getKey()) : 0d));
				}
			}
			
			if(grossPRMap.size() != 0){				
				for (Map.Entry<String, Object> entry : grossPRMap.entrySet())
				{
					DDbean = new DetailedDashboardBean();
				    DDbean.setMonth(entry.getKey());
				    DDbean.setMonthNo(GlobalConstant.MonthNo(entry.getKey().substring(0, entry.getKey().indexOf(","))));
				    DDbean.setGrossValue((double)entry.getValue());
				    DDbean.setReturnValue((double)(returnPRMap.get(entry.getKey()) != null ? returnPRMap.get(entry.getKey()) : 0d));
				    DDbean.setNetValue((double)entry.getValue() - (double)(returnPRMap.get(entry.getKey()) != null ? returnPRMap.get(entry.getKey()) : 0d)
				    						- (double)(addChargesMap.get(entry.getKey()) != null ? addChargesMap.get(entry.getKey()) : 0d));
				    DDbean.setAddCharges((double)(addChargesMap.get(entry.getKey()) != null ? addChargesMap.get(entry.getKey()) : 0d));
				    PRMonthWiseMapList.add(DDbean);
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(session != null)
				session.close();
		}
		
		return PRMonthWiseMapList;
	}
	
	@Override
	public List<DetailedDashboardBean> getTaxableSaleList(Date startDate,
			Date endDate, int sellerId) {
		List<Object> results = null;
		DetailedDashboardBean DDbean = null;
		List<DetailedDashboardBean> SPMonthWiseMapList = new ArrayList<DetailedDashboardBean>();
		Map<String, Object> grossSPMPMap = new HashMap<String, Object>();
		Map<String, Object> returnSPMPMap = new HashMap<String, Object>();
		Map<String, Object> grossSPPOMap = new HashMap<String, Object>();
		Map<String, Object> returnSPPOMap = new HashMap<String, Object>();		
		Map<String, Object> grossSPMap = new HashMap<String, Object>();
		Map<String, Object> returnSPMap = new HashMap<String, Object>();
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			Query grossSPMonthWise = session.createSQLQuery(orderSPMPMonthWiseQuery)						
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			results = grossSPMonthWise.list();
			if(results != null && results.size() != 0){
				for(Object obj : results){
					Object[] grossNR = (Object[]) obj;
					grossSPMPMap.put(grossNR[0].toString()+","+grossNR[1].toString(), grossNR[2]);
				}
			}
			
			Query grossSPPOMonthWise = session.createSQLQuery(orderSPPOMonthWiseQuery)						
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			results = grossSPPOMonthWise.list();
			if(results != null && results.size() != 0){
				for(Object obj : results){
					Object[] grossNR = (Object[]) obj;
					grossSPPOMap.put(grossNR[0].toString()+","+grossNR[1].toString(), grossNR[2]);
				}
			}
			
			Query returnSPMPMonthWise = session.createSQLQuery(returnSPMPMonthWiseQuery)						
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			results = returnSPMPMonthWise.list();
			if(results != null && results.size() != 0){
				for(Object obj : results){
					Object[] returnNR = (Object[]) obj;
					returnSPMPMap.put(returnNR[0].toString()+","+returnNR[1].toString(), returnNR[2]);
				}
			}		
			
			Query returnSPPOMonthWise = session.createSQLQuery(returnSPPOMonthWiseQuery)						
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			results = returnSPPOMonthWise.list();
			if(results != null && results.size() != 0){
				for(Object obj : results){
					Object[] returnNR = (Object[]) obj;
					returnSPPOMap.put(returnNR[0].toString()+","+returnNR[1].toString(), returnNR[2]);
				}
			}	
			
			if(grossSPMPMap.size() != 0){
				for (Map.Entry<String, Object> entry : grossSPMPMap.entrySet())
				{
					grossSPMap.put(entry.getKey(), (double)entry.getValue() + (double)(grossSPPOMap.get(entry.getKey()) != null ? grossSPPOMap.get(entry.getKey()) : 0d));
				}
			}
			if(returnSPMPMap.size() != 0){
				for (Map.Entry<String, Object> entry : returnSPMPMap.entrySet())
				{
					returnSPMap.put(entry.getKey(), (double)entry.getValue() + (double)(returnSPPOMap.get(entry.getKey()) != null ? returnSPPOMap.get(entry.getKey()) : 0d));
				}
			}
			
			if(grossSPMap.size() != 0){				
				for (Map.Entry<String, Object> entry : grossSPMap.entrySet())
				{
					DDbean = new DetailedDashboardBean();
				    DDbean.setMonth(entry.getKey());
				    DDbean.setMonthNo(GlobalConstant.MonthNo(entry.getKey().substring(0, entry.getKey().indexOf(","))));
				    DDbean.setGrossValue((double)entry.getValue());
				    DDbean.setReturnValue((double)(returnSPMap.get(entry.getKey()) != null ? returnSPMap.get(entry.getKey()) : 0d));
				    DDbean.setNetValue((double)entry.getValue() - (double)(returnSPMap.get(entry.getKey()) != null ? returnSPMap.get(entry.getKey()) : 0d));				    
				    SPMonthWiseMapList.add(DDbean);
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(session != null)
				session.close();
		}
		
		return SPMonthWiseMapList;
	}
	
	@Override
	public List<DetailedDashboardBean> getSaleQuantityList(Date startDate,
			Date endDate, int sellerId) {
		List<Object> results = null;
		DetailedDashboardBean DDbean = null;
		List<DetailedDashboardBean> SaleQtyMonthWiseMapList = new ArrayList<DetailedDashboardBean>();				
		Map<String, Object> grossSaleQtyMap = new HashMap<String, Object>();
		Map<String, Object> returnSaleQtyMap = new HashMap<String, Object>();
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			Query grossSaleQtyMonthWise = session.createSQLQuery(grossSaleQuantityMonthWise)						
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			results = grossSaleQtyMonthWise.list();
			if(results != null && results.size() != 0){
				for(Object obj : results){
					Object[] grossNR = (Object[]) obj;
					BigDecimal tempDblist= (BigDecimal) grossNR[2];
					grossSaleQtyMap.put(grossNR[0].toString()+","+grossNR[1].toString(), tempDblist.longValue());
				}
			}			
			
			Query returnSaleQtyMonthWise = session.createSQLQuery(returnSaleQuantityMonthWise)						
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			results = returnSaleQtyMonthWise.list();
			if(results != null && results.size() != 0){
				for(Object obj : results){
					Object[] returnNR = (Object[]) obj;
					BigDecimal tempDblist= (BigDecimal) returnNR[2];
					returnSaleQtyMap.put(returnNR[0].toString()+","+returnNR[1].toString(), tempDblist.longValue());
				}
			}			
			if(grossSaleQtyMap.size() != 0){				
				for (Map.Entry<String, Object> entry : grossSaleQtyMap.entrySet())
				{
					DDbean = new DetailedDashboardBean();
				    DDbean.setMonth(entry.getKey());
				    DDbean.setMonthNo(GlobalConstant.MonthNo(entry.getKey().substring(0, entry.getKey().indexOf(","))));
				    DDbean.setGrossValue((long)entry.getValue());
				    DDbean.setReturnValue((long)(returnSaleQtyMap.get(entry.getKey()) != null ? returnSaleQtyMap.get(entry.getKey()) : 0l));
				    DDbean.setNetValue((long)entry.getValue() - (long)(returnSaleQtyMap.get(entry.getKey()) != null ? returnSaleQtyMap.get(entry.getKey()) : 0l));				    
				    SaleQtyMonthWiseMapList.add(DDbean);
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(session != null)
				session.close();
		}
		
		return SaleQtyMonthWiseMapList;
	}
	
}
