package com.o2r.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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

import com.o2r.bean.DashboardBean;
import com.o2r.helper.ConverterClass;
import com.o2r.helper.CustomException;
import com.o2r.model.Customer;
import com.o2r.model.Expenses;
import com.o2r.model.Order;
import com.o2r.model.Seller;
import com.o2r.model.TaxDetail;
import com.o2r.service.TaxDetailService;

/**
 *
 * @author Deep Mehrotra
 *
 *
 */
@Repository("dashboardDao")
public class DashboardDaoImpl implements DashboardDao {
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private TaxDetailService taxDetailService;
	 String pattern = "yyyy-mm-dd";
	    SimpleDateFormat format = new SimpleDateFormat(pattern);
	static Logger log = Logger.getLogger(DashboardDaoImpl.class.getName());

	private static final String stockValuationQuery = "Select sum(quantity*price) as valuation from(select ps.stockAvailable "
			+ "as quantity,ps.price as price from ProductStockList ps, Product pr,"
			+ "Product_ProductStockList prps where ps.stockId=prps.closingStocks_stockId"
			+ " and prps.Product_productId=pr.productId and ps.year=:year and ps.month=:month and pr.seller_Id=:sellerId) as T";	
	private static final String orderNRMonthlyQuery = "Select sum(ot.netRate) as netrate,"
			+ "sum(ot.quantity) as quantity,Monthname(ot.shippedDate) as month ,YEAR(ot.shippedDate)"
			+ " as year from Order_Table ot where ot.shippedDate between :startDate AND "
			+ ":endDate and (ot.poOrder =0 OR (ot.poOrder =1 and ot.consolidatedOrder_orderId is NULL)) "
			+ "and ot.seller_Id=:sellerId GROUP BY YEAR(ot.shippedDate), MONTH(ot.shippedDate) "
			+ "order by YEAR(ot.shippedDate), MONTH(ot.shippedDate)";
	private static final String orderCountMonthlyQuery = "Select count(*), month(ot.shippedDate) as month ,YEAR(ot.shippedDate) as "
			+ "year from Order_Table ot where ot.shippedDate between :startDate AND :endDate and "
			+ "(ot.poOrder =0 OR (ot.poOrder =1 and  ot.consolidatedOrder_orderId is NULL ))and ot.seller_Id=:sellerId "
			+ "GROUP BY YEAR(ot.shippedDate), MONTH(ot.shippedDate) "
			+ "ORDER BY YEAR(ot.shippedDate), MONTH(ot.shippedDate)";
	private static final String paymentCountMonthlyQuery = "Select count(*), month(op.dateOfPayment) as month ,YEAR(op.dateOfPayment) "
			+ "as year from Order_Table ot,OrderPay op where op.dateOfPayment between :startDate AND :endDate "
			+ "and (ot.poOrder =0 OR (ot.poOrder =1 and  ot.consolidatedOrder_orderId is NULL ))and "
			+ "op.dateOfPayment is NOT NULL and op.paymentId=ot.orderPayment_paymentId and ot.seller_Id=:sellerId "
			+ "GROUP BY YEAR(op.dateOfPayment), MONTH(op.dateOfPayment) ORDER BY YEAR(op.dateOfPayment), MONTH(op.dateOfPayment)";
	private static final String paymentCountForDurationQuery = "Select count(*)from Order_Table ot,OrderPay op "
			+ "where op.dateOfPayment between :startDate AND :endDate "
			+ "and (ot.poOrder =0 OR (ot.poOrder =1 and  ot.consolidatedOrder_orderId is NULL ))and "
			+ "op.dateOfPayment is NOT NULL and op.paymentId=ot.orderPayment_paymentId and ot.seller_Id=:sellerId";
	private static final String orderCountForDurationQuery = "Select count(*) "
			+ "from Order_Table ot where ot.shippedDate between :startDate AND :endDate and "
			+ "(ot.poOrder =0 OR (ot.poOrder =1 and  ot.consolidatedOrder_orderId is NULL ))and ot.seller_Id=:sellerId";
	/*private static final String grossProfitForDurationQuery = "Select sum(grossProfit) from Order_Table ot where "
			+ "ot.orderDate between :startDate AND :endDate and ot.seller_Id=:sellerId";
	*/private static final String grossProfitForMPDurationQuery = "select sum((ot.pr-(prd.productPrice*ot.quantity)))"
			+ " from order_table ot , Product prd where ot.productSkuCode=prd.productSkuCode "
			+ "and ot.shippedDate between :startDate AND :endDate and "
			+ "ot.poOrder =0 and ot.seller_Id=:sellerId";
	private static final String grossProfitForMPReturnDurationQuery = "select sum(((ot.pr-(prd.productPrice*ot.quantity))/ot.quantity)"
			+ "*orr.returnorrtoQty + orr.estimateddeduction) "
			+ "from order_table ot , Product prd , orderreturn orr where ot.productSkuCode=prd.productSkuCode "
			+ "and ot.orderReturnOrRTO_returnId=orr.returnId and orr.returnDate between :startDate AND :endDate "
			+ "and ot.poOrder =0 and ot.seller_Id=:sellerId";
	private static final String grossProfitPODurationQuery = "Select sum(ot.grossProfit) as grossProfit"
			+ " from Order_Table ot where ot.shippedDate "
			+ "between  :startDate AND :endDate and ot.poOrder =1 and  ot.consolidatedOrder_orderId is NULL "
			+ "and ot.seller_Id=:sellerId";
	private static final String grossProfitGPDurationQuery = "Select sum(ot.grossProfit) as grossProfit "
			+ "from Order_Table ot ,OrderReturn  ort where ort.returnDate "
			+ "between  :startDate AND :endDate and ot.poOrder =1 and  ot.consolidatedOrder_orderId is NULL "
			+ "and ort.returnId=ot.orderReturnOrRTO_returnId and ot.seller_Id=:sellerId";
	
	private static final String returnNRPOMonthlyQuery = "Select sum(ort.estimateddeduction+ort.netNR) as returnCharges,"
			+ " sum(ort.returnorrtoQty) as quantity,Monthname(ort.returnDate) as month ,YEAR(ort.returnDate) from "
			+ "Order_Table ot ,OrderReturn  ort where ort.returnDate between :startDate AND :endDate and "
			+ "ort.returnId=ot.orderReturnOrRTO_returnId and ot.poOrder =1 and ot.consolidatedOrder_orderId is NULL and "
			+ "ot.seller_Id=:sellerId GROUP BY YEAR(ort.returnDate), MONTH(ort.returnDate) order by YEAR(ort.returnDate), MONTH(ort.returnDate)";
	private static final String returnNRMPMonthlyQuery = "Select sum(ort.estimateddeduction+(ot.grossNetRate*ort.returnorrtoQty)) as returnCharges , "
			+ "sum(ort.returnorrtoQty) as quantity,Monthname(ort.returnDate) as month ,YEAR(ort.returnDate) "
			+ "from Order_Table ot ,OrderReturn  ort where "
			+ "ort.returnDate between :startDate AND :endDate and ort.returnId=ot.orderReturnOrRTO_returnId "
			+ "and ot.poOrder =0 and ot.seller_Id=:sellerId GROUP BY YEAR(ort.returnDate), MONTH(ort.returnDate) "
			+ "order by YEAR(ort.returnDate), MONTH(ort.returnDate)";
	private static final String grossProfitMPMonthlyQuery = "Select sum((ot.pr-(prd.productPrice*ot.quantity))) as grossProfit,"
			+ "Monthname(ot.shippedDate) as month ,YEAR(ot.shippedDate) as year from Order_Table ot,Product prd where ot.shippedDate "
			+ "between  :startDate AND :endDate and ot.productSkuCode=prd.productSkuCode and ot.poOrder =0 and"
			+ " ot.seller_Id=:id GROUP BY YEAR(ot.shippedDate), MONTH(ot.shippedDate)"
			+ "ORDER BY YEAR(ot.shippedDate), MONTH(ot.shippedDate)";

	private static final String grossProfitMPReturnMonthlyQuery = "Select sum(((ot.pr-(prd.productPrice*ot.quantity))/ot.quantity)*orr.returnorrtoQty + orr.estimateddeduction)"
			+ ",Monthname(orr.returnDate) as month,YEAR(orr.returnDate) as year from "
			+ "Order_Table ot,orderreturn orr,Product prd where orr.returnDate between "
			+ ":startDate AND :endDate and ot.productSkuCode=prd.productSkuCode and ot.orderReturnOrRTO_returnId=orr.returnId"
			+ " and ot.poOrder =0 and ot.seller_Id=:rtid "
			+ "GROUP BY YEAR(orr.returnDate), MONTH(orr.returnDate)"
			+ "ORDER BY YEAR(orr.returnDate), MONTH(orr.returnDate)";
	private static final String grossProfitPOMonthlyQuery = "Select sum(ot.grossProfit) as grossProfit, "
			+ "Monthname(ot.shippedDate) as month ,"
			+ "YEAR(ot.shippedDate) as year from Order_Table ot where ot.shippedDate "
			+ "between  :startDate AND :endDate and ot.poOrder =1 and  ot.consolidatedOrder_orderId is NULL "
			+ "and ot.seller_Id=:sellerId GROUP BY YEAR(ot.shippedDate), MONTH(ot.shippedDate) "
			+ "ORDER BY YEAR(ot.shippedDate), MONTH(ot.shippedDate)";
	private static final String grossProfitGPMonthlyQuery = "Select sum(ot.grossProfit) as grossProfit,"
			+ "Monthname(ort.returnDate) as month ,"
			+ "YEAR(ort.returnDate) as year from Order_Table ot ,OrderReturn  ort where ort.returnDate "
			+ "between  :startDate AND :endDate and ot.poOrder =1 and  ot.consolidatedOrder_orderId is NULL "
			+ "and ort.returnId=ot.orderReturnOrRTO_returnId and ot.seller_Id=:sellerId "
			+ "GROUP BY YEAR(ort.returnDate), MONTH(ort.returnDate) "
			+ "ORDER BY YEAR(ort.returnDate), MONTH(ort.returnDate)";
	private static final String expenditureMonthlyQuery = "SELECT sum(amount) as amt ,Monthname(t.expenseDate) as month, YEAR(t.expenseDate) as year FROM "
			+ "Expenses t where t.expenseDate between :startDate AND :endDate and t.sellerId=:sellerId "
			+ "GROUP BY YEAR(t.expenseDate), MONTH(t.expenseDate) "
			+ "ORDER BY YEAR(t.expenseDate), MONTH(t.expenseDate)";
	private static final String orderQtyinTimeQuery = "Select sum(ot.quantity) as quantity from "
			+ "Order_Table ot where ot.shippedDate between :startDate AND :endDate "
			+ "and (ot.poOrder =0 OR (ot.poOrder =1 and  ot.consolidatedOrder_orderId is NULL ))and "
			+ "ot.seller_Id=:sellerId";
	private static final String returnQtyinTimeQuery = "Select sum(ort.returnorrtoQty) as returnquantity from Order_Table ot"
			+ " ,OrderReturn  ort where ort.returnDate between :startDate AND :endDate and "
			+ "(ot.poOrder =0 OR (ot.poOrder =1 and  ot.consolidatedOrder_orderId is NULL )) and "
			+ "ort.returnId=ot.orderReturnOrRTO_returnId and ot.seller_Id=:sellerId";

	@SuppressWarnings("deprecation")
	@Override
	public DashboardBean getDashboardDetails(int sellerId) {
		
		log.info("***getDashboardDetails starts***");
		long startTime = System.currentTimeMillis();
		DashboardBean dashboardBean = null;
		Date todayDate = new Date();
		Date tommorrowDate = new Date();
		Date yeasterdayDate = new Date();
		Date lastYearEnd = new Date();
		Date thisYearSatrt = new Date();
		Date lastYearSatrt = new Date();
		Date oneMonthBack = new Date();
		Date thismonthstart = new Date();
		Date sixMonthsBack = new Date();
		Date after10days = new Date();
		List<Object> orderNRQuantityMonthly = null;
		List<Object> returnNRQuantityMonthly = null;
		/*todayDate.setDate(todayDate.getDate() + 1);
		todayDate.setMonth(todayDate.getMonth());*/
		tommorrowDate.setDate(todayDate.getDate() + 1);
		yeasterdayDate.setDate(yeasterdayDate.getDate() - 1);
		lastYearEnd.setDate(31);
		lastYearEnd.setMonth(11);
		lastYearEnd.setYear(lastYearEnd.getYear() - 1);
		thisYearSatrt.setDate(1);
		thisYearSatrt.setMonth(0);
		thisYearSatrt.setHours(0);
		lastYearSatrt.setDate(1);
		lastYearSatrt.setMonth(0);
		lastYearSatrt.setYear(lastYearSatrt.getYear() - 1);
		after10days.setDate(after10days.getDate() + 10);
		oneMonthBack.setDate(1);
		oneMonthBack.setDate(oneMonthBack.getDate()-1);
		thismonthstart.setDate(1);
		thismonthstart.setHours(0);
		sixMonthsBack.setDate(1);
		sixMonthsBack.setMonth(sixMonthsBack.getMonth() - 5);
		try {
			dashboardBean = new DashboardBean();
			Session session = sessionFactory.openSession();
			session.beginTransaction();			
			dashboardBean.setProfitThisYear(netProfitForTime(session,lastYearEnd, tommorrowDate, sellerId));
			dashboardBean.setProfitLastYear(netProfitForTime(session,lastYearSatrt, thisYearSatrt, sellerId));
			if ((int) dashboardBean.getProfitLastYear() != 0) {
				if (dashboardBean.getProfitThisYear() > dashboardBean
						.getProfitLastYear())
					dashboardBean.setPercentChangeInProfit(((dashboardBean
							.getProfitThisYear() - dashboardBean
							.getProfitLastYear()) / dashboardBean
							.getProfitThisYear()) * 100);
				else
					dashboardBean.setPercentChangeInProfit(((dashboardBean
							.getProfitLastYear() - dashboardBean
							.getProfitThisYear()) / dashboardBean
							.getProfitLastYear()) * 100);
			}
			dashboardBean.setSaleQuantityThisYear(netSaleQtyforTime(session,
					thisYearSatrt, todayDate, sellerId));
			dashboardBean.setSaleQuantityLastYear(netSaleQtyforTime(session,
					lastYearSatrt, lastYearEnd, sellerId));
			if ((int) dashboardBean.getSaleQuantityLastYear() != 0) {
				if (dashboardBean.getSaleQuantityThisYear() > dashboardBean
						.getSaleQuantityLastYear())
					dashboardBean.setPercentChangeInSQ(((dashboardBean
							.getSaleQuantityThisYear() - dashboardBean
							.getSaleQuantityLastYear()) / dashboardBean
							.getSaleQuantityLastYear()) * 100);
				else
					dashboardBean.setPercentChangeInSQ(((dashboardBean
							.getSaleQuantityLastYear() - dashboardBean
							.getSaleQuantityThisYear()) / dashboardBean
							.getSaleQuantityLastYear()) * 100);
			}
			dashboardBean
					.setTotalCustomers(getTotalCustomer(session, sellerId));
			dashboardBean.setTotalStock(getTotalSKUCount(session, sellerId));
			dashboardBean.setLast30daysOrderCount(orderCountforTimeDaily(
					session, oneMonthBack, tommorrowDate, sellerId));
			dashboardBean.setLast30DaysPaymentCount(paymentCountforTimeDaily(
					session, oneMonthBack, tommorrowDate, sellerId));
			dashboardBean.setLast12MonthsOrderCount(orderCountMonthly(session,
					lastYearEnd, todayDate, sellerId));
			dashboardBean.setLast12MonthsPaymentCount(paymentCountMonthly(
					session, lastYearEnd, todayDate, sellerId));
			dashboardBean.setTodaysOrderCount(countForDuration(session,
					yeasterdayDate, todayDate, sellerId,
					orderCountForDurationQuery));
			dashboardBean.setThisMonthOrderCount(countForDuration(session,
					thismonthstart, todayDate, sellerId,
					orderCountForDurationQuery));
			dashboardBean.setThisYearOrderCount(countForDuration(session,
					lastYearEnd, todayDate, sellerId,
					orderCountForDurationQuery));
			dashboardBean.setTodaysPaymentCount(countForDuration(session,
					yeasterdayDate, todayDate, sellerId,
					paymentCountForDurationQuery)); // todays
			dashboardBean.setThisMonthPaymentCount(countForDuration(session,
					thismonthstart, todayDate, sellerId,
					paymentCountForDurationQuery)); // this month
			dashboardBean.setThisYearPaymentCount(countForDuration(session,
					lastYearEnd, todayDate, sellerId,
					paymentCountForDurationQuery));
			dashboardBean.setTodaysGrossProfit(grossProfitForDuration(session,
					yeasterdayDate, todayDate, sellerId));
			dashboardBean.setThisMonthGrossProfit(grossProfitForDuration(session,
					thismonthstart, todayDate, sellerId));
			dashboardBean.setThisYearGrossProfit(grossProfitForDuration(session,
					thisYearSatrt, todayDate, sellerId));
			// testing purpose startdate as one monthback
			dashboardBean.setTotalUpcomingPayments(listOfUpcomingPayment(
					session, oneMonthBack, after10days, sellerId));
			dashboardBean.setTotalOutstandingPayments(listOfOutstandingPayment(
					session, sellerId));
			// Six months top selling sku and region just for testing
			dashboardBean.setTopSellingSKU(topSellingSKU(session,
					sixMonthsBack, todayDate, sellerId));
			dashboardBean.setTopSellingRegion(topSellingRegion(session,
					sixMonthsBack, todayDate, sellerId));
			dashboardBean.setExpenditureThisMonth(expenseGroupByCatForTime(
					session, oneMonthBack, todayDate, sellerId));
			dashboardBean.setGrossProfitMonthly(grossProfitMonthly(session,
					sixMonthsBack, todayDate, sellerId));
			dashboardBean.setExpenditureMonthly(expenditureMonthly(session,
					sixMonthsBack, todayDate, sellerId));
			dashboardBean.setTaxAlerts(ConverterClass
					.prepareListofTaxDetailBean(getTaxAlert(session,
							sixMonthsBack, sellerId)));
			dashboardBean.setTdsAlerts(ConverterClass
					.prepareListofTaxDetailBean(getTDSAlert(session,
							sixMonthsBack, sellerId)));
			orderNRQuantityMonthly = orderNRQuantityMonthly(session,
					sixMonthsBack, todayDate, sellerId);
			if (orderNRQuantityMonthly != null
					&& orderNRQuantityMonthly.size() != 0) {
				if (orderNRQuantityMonthly.get(1) != null)
					dashboardBean.setSaleQuantity((Map<String, Long>) orderNRQuantityMonthly.get(1));
				if (orderNRQuantityMonthly.get(0) != null)
					dashboardBean.setSaleAmount((Map<String, Double>) orderNRQuantityMonthly.get(0));
			}
			returnNRQuantityMonthly = returnNRQuantityMonthly(session,sixMonthsBack, todayDate, sellerId);
			log.debug(" returnNRQuantityMonthly value : "+ returnNRQuantityMonthly);
			log.debug(" returnNRQuantityMonthly "+ returnNRQuantityMonthly.size());
			if (returnNRQuantityMonthly != null
					&& returnNRQuantityMonthly.size() != 0) {
				if (returnNRQuantityMonthly.get(0) != null)
					dashboardBean.setReturnAmount((Map<String, Double>) returnNRQuantityMonthly.get(0));
				if (returnNRQuantityMonthly.get(1) != null)
					dashboardBean.setReturnQuantity((Map<String, Long>) returnNRQuantityMonthly.get(1));
			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			log.error("Failed!",e);
			System.out.println("Inside exception  " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		log.debug(" Elpased time in Dashboard Data : "+ (endTime - startTime));
		log.info("*** getDashboardDetails ends ***");
		return dashboardBean;
	}

	public long netSaleQtyforTime(Session session, Date startDate,Date endDate, int sellerId) {
		
		log.info("***netSaleQtyforTime starts***");
		//List<Object[]> results = null;
		long netSaleQty = 0;
		BigDecimal quantity = BigDecimal.ZERO;
		BigDecimal returnQty=BigDecimal.ZERO;
		List<BigDecimal> tempDblist=null;
		try {			
			session.beginTransaction();
			
			Query orderQtythisyear = session
					.createSQLQuery(orderQtyinTimeQuery)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			tempDblist = orderQtythisyear.list();
			if (tempDblist != null && tempDblist.size() != 0
					&& tempDblist.get(0) != null)
				quantity = tempDblist.get(0);
			
			tempDblist=null;
			Query returnQtyThisYear = session
					.createSQLQuery(returnQtyinTimeQuery)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			tempDblist = returnQtyThisYear.list();
			
			if (tempDblist != null && tempDblist.size() != 0
					&& tempDblist.get(0) != null)
				returnQty = tempDblist.get(0);
			
				
			
			netSaleQty=quantity.longValue()-returnQty.longValue();
			log.debug("Final Net sale Qty : quantity "+quantity+" returnQty "+returnQty);
			System.out.println("Final Net sale Qty : quantity "+quantity+" returnQty "+returnQty);
		} catch (Exception e) {
			log.error("Failed!",e);
			e.printStackTrace();
		}
		log.info("***netSaleQtyforTime ends***");
		return netSaleQty;
	}

	public Double netProfitForTime(Session session, Date startDate,	Date endDate, int sellerId) {

		log.info("***netProfitForTime starts***");		
		List<Double> osValuation = null;
		List<Double> csValuation = null;
		List results = null;		
		double openStock = 0;
		double currentStock = 0;
		double netProfit = 0;
		double totalExpenses = 0;
		double orderNr = 0;
		double totalReturn = 0;
		try {			
			session.beginTransaction();
			Criteria criteriaForNR = session.createCriteria(Order.class);
			criteriaForNR.createAlias("seller", "seller",CriteriaSpecification.LEFT_JOIN);
			criteriaForNR.createAlias("orderPayment", "orderPayment",CriteriaSpecification.LEFT_JOIN);
			criteriaForNR.createAlias("orderReturnOrRTO", "orderReturnOrRTO",CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.eq("poOrder", false))
					.add(Restrictions.between("shippedDate", startDate, endDate));

			criteriaForNR.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

			Criteria criteriaForPOprice = session.createCriteria(Order.class);
			criteriaForPOprice.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN);
			criteriaForPOprice.createAlias("consolidatedOrder","consolidatedOrder", CriteriaSpecification.LEFT_JOIN);
			criteriaForPOprice
					.createAlias("orderReturnOrRTO", "orderReturnOrRTO",
							CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.eq("poOrder", true))
					.add(Restrictions.isNull("consolidatedOrder.orderId"))
					.add(Restrictions.between("shippedDate", startDate, endDate));

			criteriaForPOprice.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

			ProjectionList projList = Projections.projectionList();
			projList.add(Projections.sqlProjection("(sum( grossNetRate * (quantity-returnorrtoQty) )) as grossNR",new String[] { "grossNR" },
							new Type[] { Hibernate.DOUBLE }));
			projList.add(Projections.sum("orderReturnOrRTO.returnOrRTOChargestoBeDeducted"));

			ProjectionList POprojList = Projections.projectionList();
			POprojList.add(Projections.sum("netRate"));
			POprojList.add(Projections.sum("orderReturnOrRTO.netNR"));

			criteriaForNR.setProjection(projList);
			results = criteriaForNR.list();
			if (results != null && results.size() != 0 && results.get(0) != null) {
				Object[] objArray = (Object[]) results.get(0);
				if (objArray[0] != null && objArray[1] != null) {
					orderNr = (Double) objArray[0];
					totalReturn = (Double) objArray[1];
					log.debug(" results.size() : " + results.size());
					log.debug("**** Gross NR : " + orderNr + " Return " + objArray[1]);
				}

			}
			criteriaForPOprice.setProjection(POprojList);
			results = criteriaForPOprice.list();
			if (results != null && results.size() != 0
					&& results.get(0) != null) {
				Object[] objArray = (Object[]) results.get(0);
				if (objArray[0] != null && objArray[1] != null) {
					orderNr = orderNr + (Double) objArray[0];
					totalReturn = totalReturn + (Double) objArray[1];
					log.debug(" results.size() : " + results.size());
					log.debug("**** Gross NR : " + orderNr + " Return " + objArray[1]);
				}

			}

			Query openingStockValueThisYear = session
					.createSQLQuery(stockValuationQuery)
					.setParameter("year", startDate.getYear())
					.setParameter("month", startDate.getMonth())
					.setParameter("sellerId", sellerId);
			osValuation = openingStockValueThisYear.list();
			Query closingStockValueThisMonth = session
					.createSQLQuery(stockValuationQuery)
					.setParameter("year", endDate.getYear())
					.setParameter("month", endDate.getMonth())
					.setParameter("sellerId", sellerId);
			csValuation = closingStockValueThisMonth.list();
			if (osValuation != null && osValuation.size() != 0
					&& osValuation.get(0) != null)
				openStock = osValuation.get(0);
			if (csValuation != null && csValuation.size() != 0
					&& csValuation.get(0) != null)
				currentStock = csValuation.get(0);
			totalExpenses = totalExpenseForTime(session, startDate, endDate,
					sellerId);
			netProfit = (orderNr - totalReturn + currentStock)
					- (openStock + totalExpenses);
			log.debug(" Order NR : " + orderNr + "-->totalReturn :"
					+ totalReturn + "-->currentStock :" + currentStock + ""
					+ "-->openStock :" + openStock + "=--->totalExpenses :"
					+ totalExpenses);
			
		} catch (Exception e) {
			log.error("Failed!",e);
			log.debug("Inside exception  " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		log.info("***netProfitForTime ends***");
		return netProfit;
	}

	public Map<Date, Long> paymentCountforTimeDaily(Session session,Date startDate, Date endDate, int sellerId) {
		
		log.info("***paymentCountforTimeDaily starts***");
		List<Object[]> results = null;
		Map<Date, Long> paymentCount = new LinkedHashMap<>();
		try {
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Order.class);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("orderPayment", "orderPayment",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.isNotNull("orderPayment.dateofPayment"))
					.add(Restrictions.between("orderPayment.dateofPayment",
							startDate, endDate));
			Criterion rest1 = Restrictions.eq("poOrder", false);
			Criterion rest2 = Restrictions.and(	
					Restrictions.eq("poOrder", true),
					Restrictions.isNull("consolidatedOrder"));
			criteria.add(Restrictions.or(rest1, rest2));
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			ProjectionList projList = Projections.projectionList();
			projList.add(Projections.rowCount());
			projList.add(Projections
					.groupProperty("orderPayment.dateofPayment"));
			criteria.setProjection(projList);
			criteria.addOrder(org.hibernate.criterion.Order
					.asc("orderPayment.dateofPayment"));
			results = criteria.list();
			Iterator iterator1 = results.iterator();
			if (results != null) {
				while (iterator1.hasNext()) {
					System.out.println("\n");
					Object[] recordsRow = (Object[]) iterator1.next();
					if (recordsRow[0] != null && recordsRow[1] != null) {
						log.debug(" record Payment length:"+ recordsRow.length);
						log.debug(recordsRow[0].toString());
						log.debug(Timestamp.valueOf(recordsRow[1].toString()));
						log.debug("conveting into date : "+ new Date(Timestamp.valueOf(recordsRow[1].toString()).getTime()));
						paymentCount.put(
								new Date(Timestamp.valueOf(
										recordsRow[1].toString()).getTime()),
								Long.parseLong(recordsRow[0].toString()));
					}
				}
			}
		} catch (Exception e) {
			log.error("Failed!",e);
			log.debug("Inside payment count exception  "+ e.getLocalizedMessage());
			e.printStackTrace();
		}
		log.info("***paymentCountforTimeDaily ends***");
		return paymentCount;
	}

	public Map<Date, Long> orderCountforTimeDaily(Session session,Date startDate, Date endDate, int sellerId) {
		
		log.info("***orderCountforTimeDaily starts***");
		List<Object[]> results = null;
		Map<Date, Long> orderCount = new LinkedHashMap<>();
		try {
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Order.class);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.between("shippedDate", startDate, endDate));
			Criterion rest1 = Restrictions.eq("poOrder", false);
			Criterion rest2 = Restrictions.and(	
					Restrictions.eq("poOrder", true),
					Restrictions.isNull("consolidatedOrder"));
			criteria.add(Restrictions.or(rest1, rest2));
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			ProjectionList projList = Projections.projectionList();
			projList.add(Projections.rowCount());
			projList.add(Projections.groupProperty("shippedDate"));
			criteria.setProjection(projList);
			criteria.addOrder(org.hibernate.criterion.Order.asc("shippedDate"));
			results = criteria.list();
			Iterator iterator1 = results.iterator();
			if (results != null) {
				while (iterator1.hasNext()) {
					System.out.println("\n");
					Object[] recordsRow = (Object[]) iterator1.next();
					log.debug(" record order length:"
							+ recordsRow.length);
					if (recordsRow[0] != null && recordsRow[1] != null) {
						log.debug(recordsRow[0].toString());
						log.debug(recordsRow[1].toString());
						orderCount.put(
								new Date(Timestamp.valueOf(
										recordsRow[1].toString()).getTime()),
								Long.parseLong(recordsRow[0].toString()));
					}
				}
			}
		} catch (Exception e) {
			log.error("Failed!",e);
			log.debug("Inside exception orderCountforTimeDaily "+ e.getLocalizedMessage());
			e.printStackTrace();
		}
		log.info("***orderCountforTimeDaily ends***");
		return orderCount;
	}

	public Map<Date, Double> listOfUpcomingPayment(Session session,
			Date startDate, Date endDate, int sellerId) {
		
		log.info("***listOfUpcomingPayment starts***");
		Map<Date, Double> totalUpcomingPayments = new LinkedHashMap<>();
		List<Object[]> results = null;
		log.debug(" Inside upcoming payments -startDate- " + startDate	+ " endDate : " + endDate);
		try {			
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Order.class);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("orderPayment", "orderPayment",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.between("paymentDueDate", startDate,
							endDate))
					.add(Restrictions.isNull("orderPayment.dateofPayment"));
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			ProjectionList projList = Projections.projectionList();
			projList.add(Projections.sum("netRate"));
			projList.add(Projections.groupProperty("paymentDueDate"));
			criteria.setProjection(projList);
			criteria.addOrder(org.hibernate.criterion.Order
					.desc("paymentDueDate"));
			results = criteria.list();
			Iterator iterator1 = results.iterator();
			if (results != null) {
				while (iterator1.hasNext()) {
					System.out.println(" row\n");
					Object[] recordsRow = (Object[]) iterator1.next();
					if (recordsRow[0] != null && recordsRow[1] != null) {
						totalUpcomingPayments.put(
								new Date(Timestamp.valueOf(
										recordsRow[1].toString()).getTime()),
								Double.parseDouble(recordsRow[0].toString()));
						log.debug(" record length:"+ recordsRow.length);
						for (int i = 0; i < recordsRow.length; i++) {
							System.out.print("\t" + recordsRow[i]);
						}
					}
				}
			}			
		} catch (Exception e) {
			log.error("Failed!",e);
			log.debug("Inside upcomingpayment exception  "+ e.getLocalizedMessage());
			e.printStackTrace();
		}
		log.info("***listOfUpcomingPayment ends***");
		return totalUpcomingPayments;
	}

	public Map<String, Double> listOfOutstandingPayment(Session session,
			int sellerId) {
		
		log.info("***listOfOutstandingPayment starts***");
		List<Object[]> results = null;
		Map<String, Double> totalOutStandingPayments = new LinkedHashMap<>();
		
		try {
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Order.class);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("orderPayment", "orderPayment",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.ne("orderPayment.paymentDifference", 0.0));
		
			Criterion rest1 = Restrictions.eq("poOrder", false);
			Criterion rest2 = Restrictions.and(	
					Restrictions.eq("poOrder", true),
					Restrictions.isNull("consolidatedOrder"));
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			criteria.add(Restrictions.or(rest1, rest2));
			ProjectionList projList = Projections.projectionList();
			projList.add(Projections.sum("orderPayment.paymentDifference"));
			projList.add(Projections.groupProperty("pcName"));
			criteria.setProjection(projList);
			results = criteria.list();
			Iterator iterator1 = results.iterator();
			if (results != null) {
				while (iterator1.hasNext()) {
					System.out.println(" Out standing : \n");
					Object[] recordsRow = (Object[]) iterator1.next();					
					if (recordsRow[0] != null && recordsRow[1] != null) {
						totalOutStandingPayments.put(recordsRow[1].toString(),
								Double.parseDouble(recordsRow[0].toString()));
						for (int i = 0; i < recordsRow.length; i++) {
							System.out.print("\t" + recordsRow[i]);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Failed!",e);
			log.debug("Inside upcomingpayment exception  "	+ e.getLocalizedMessage());
			e.printStackTrace();
		}
		log.info("***listOfOutstandingPayment ends***");
		return totalOutStandingPayments;
	}

	public Map<String, Double> grossProfitMonthly(Session session,
			Date startDate, Date endDate, int sellerId) {

		log.info("***grossProfitMonthly starts***");
		List<Object[]> results = null;
		Map<String, Double> gpMonthly = new LinkedHashMap<>();
		Date gpDate = null;
		try {
			session.beginTransaction();
			Query mpquery = session.createSQLQuery(grossProfitMPMonthlyQuery)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("id", sellerId);
			Query mpReturnquery = session
					.createSQLQuery(grossProfitMPReturnMonthlyQuery)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("rtid", sellerId);
			Query poquery = session.createSQLQuery(grossProfitPOMonthlyQuery)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			Query gpquery = session.createSQLQuery(grossProfitGPMonthlyQuery)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			results = mpquery.list();
			Iterator mpiterator1 = results.iterator();
			if (results != null) {
				while (mpiterator1.hasNext()) {
					System.out.println("grossp ro : row\n");
					Object[] recordsRow = (Object[]) mpiterator1.next();
					System.out.println(" Inside Gp : recordsRow[0] : "
							+ recordsRow[0] + " recordsRow[1] :"
							+ recordsRow[1] + " recordsRow[2] :"
							+ recordsRow[2]);
					if (recordsRow[0] != null && recordsRow[1] != null
							&& recordsRow[2] != null) {

						String dateString = recordsRow[1].toString().substring(
								0, 3)
								+ "," + recordsRow[2].toString();
						gpMonthly.put(dateString,
								Double.parseDouble(recordsRow[0].toString()));

					}
				}
			}
			results = mpReturnquery.list();
			Iterator mpreturniterator1 = results.iterator();
			if (results != null) {
				while (mpreturniterator1.hasNext()) {
					System.out.println("grossp ro : row\n");
					Object[] recordsRow = (Object[]) mpreturniterator1.next();
					System.out.println(" Inside Gp : recordsRow[0] : "
							+ recordsRow[0] + " recordsRow[1] :"
							+ recordsRow[1] + " recordsRow[2] :"
							+ recordsRow[2]);
					if (recordsRow[0] != null && recordsRow[1] != null
							&& recordsRow[2] != null) {

						String dateString = recordsRow[1].toString().substring(
								0, 3)
								+ "," + recordsRow[2].toString();
						gpMonthly.put(
								dateString,
								(gpMonthly.containsKey(dateString) ? gpMonthly
										.get(dateString) : 0)
										- Double.parseDouble(recordsRow[0]
												.toString()));

					}
				}
			}
			results = poquery.list();
			Iterator poiterator1 = results.iterator();
			if (results != null) {
				while (poiterator1.hasNext()) {
					System.out.println("grossp ro : row\n");
					Object[] recordsRow = (Object[]) poiterator1.next();
					System.out.println(" Inside Gp : recordsRow[0] : "
							+ recordsRow[0] + " recordsRow[1] :"
							+ recordsRow[1] + " recordsRow[2] :"
							+ recordsRow[2]);
					if (recordsRow[0] != null && recordsRow[1] != null
							&& recordsRow[2] != null) {

						String dateString = recordsRow[1].toString().substring(
								0, 3)
								+ "," + recordsRow[2].toString();
						gpMonthly.put(
								dateString,
								(gpMonthly.containsKey(dateString) ? gpMonthly
										.get(dateString) : 0)
										+ Double.parseDouble(recordsRow[0]
												.toString()));

					}
				}
			}
			results = gpquery.list();
			Iterator gpiterator1 = results.iterator();
			if (results != null) {
				while (gpiterator1.hasNext()) {
					System.out.println("grossp ro : row\n");
					Object[] recordsRow = (Object[]) gpiterator1.next();
					System.out.println(" Inside Gp : recordsRow[0] : "
							+ recordsRow[0] + " recordsRow[1] :"
							+ recordsRow[1] + " recordsRow[2] :"
							+ recordsRow[2]);
					if (recordsRow[0] != null && recordsRow[1] != null
							&& recordsRow[2] != null) {

						String dateString = recordsRow[1].toString().substring(
								0, 3)
								+ "," + recordsRow[2].toString();
						gpMonthly.put(
								dateString,
								(gpMonthly.containsKey(dateString) ? gpMonthly
										.get(dateString) : 0)
										- Double.parseDouble(recordsRow[0]
												.toString()));

					}
				}
			}
		} catch (Exception e) {
			log.error("Failed!",e);
			log.debug("Inside exception  " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		log.debug(" Monthly gpMonthly : " + gpMonthly);
		log.info("***grossProfitMonthly ends***");
		return gpMonthly;
	}

	public long getTotalCustomer(Session session, int sellerId) {
		
		log.info("***getTotalCustomer starts***");
		int totalCustomer = 0;
		List<Integer> results = null;
		try {
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Customer.class).add(
					Restrictions.eq("sellerId", sellerId));
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			ProjectionList projList = Projections.projectionList();
			projList.add(Projections.countDistinct("customerEmail"));
			criteria.setProjection(projList);
			results = criteria.list();
			if (results != null && results.size() != 0
					&& results.get(0) != null) {
				totalCustomer = results.get(0);				
			}			
		} catch (Exception e) {
			log.error("Failed!",e);
			log.debug("Inside exception getTotalCustomer "+ e.getLocalizedMessage());
			e.printStackTrace();
		}
		log.info("***getTotalCustomer ends***");
		return totalCustomer;
	}

	public long getTotalSKUCount(Session session, int sellerId) {
		
		log.info("***getTotalSKUCount starts***");
		long totalSkuCount = 0;
		List<Long> results = null;
		try {
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", sellerId));
			criteria.createAlias("products", "product",
					CriteriaSpecification.LEFT_JOIN);
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			ProjectionList projList = Projections.projectionList();
			projList.add(Projections.sum("product.quantity"));
			criteria.setProjection(projList);
			results = criteria.list();
			if (results != null && results.size() != 0
					&& results.get(0) != null) {
				totalSkuCount = results.get(0);
			}
			
		} catch (Exception e) {
			log.error("Failed!",e);
			log.debug("Inside exception totalSkuCount "+ e.getLocalizedMessage());
			e.printStackTrace();
		}
		log.info("***getTotalSKUCount ends***");
		return totalSkuCount;
	}

	public List<TaxDetail> getTaxAlert(Session session, Date taxDate,int sellerId) {
		
		log.info("***getTaxAlert starts***");
		List<TaxDetail> taxList = null;
		try {
			taxList = taxDetailService.listTaxDetails(sellerId, "Tax");			
		} catch (CustomException e) {
			log.error("Failed!",e);
			e.printStackTrace();
		}
		log.info("***getTaxAlert ends***");
		return taxList;
	}

	public List<TaxDetail> getTDSAlert(Session session, Date taxDate,
			int sellerId) {
		
		log.info("***getTDSAlert starts***");
		Seller seller = null;
		List<TaxDetail> taxList = null;
		try {
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", sellerId));
			criteria.createAlias("taxDetails", "taxDetail",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.ge("taxDetail.uploadDate", taxDate))
					.add(Restrictions.eq("taxDetail.taxortds", "TDS"));
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			criteria.addOrder(org.hibernate.criterion.Order
					.desc("taxDetail.uploadDate"));
			if (criteria.list() != null && criteria.list().size() != 0) {
				seller = (Seller) criteria.list().get(0);
				taxList = seller.getTaxDetails();
			}
		} catch (Exception e) {
			log.error("Failed!",e);
			log.debug("Inside exception  getTDSAlert "	+ e.getLocalizedMessage());
			e.printStackTrace();
		}
		log.info("***getTDSAlert ends***");
		return taxList;
	}

	public Map<String, Double> expenditureMonthly(Session session,
			Date startDate, Date endDate, int sellerId) {
		
		log.info("***expenditureMonthly starts***");
		List<Object[]> results = null;
		Map<String, Double> expenseMonthly = new LinkedHashMap<>();
		try {
			session.beginTransaction();
			Query query = session.createSQLQuery(expenditureMonthlyQuery)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			results = query.list();
			Iterator iterator1 = results.iterator();
			if (results != null) {
				while (iterator1.hasNext()) {
					Object[] recordsRow = (Object[]) iterator1.next();					
					if (recordsRow[0] != null && recordsRow[1] != null
							&& recordsRow[2] != null) {						
						String dateString = recordsRow[1].toString().substring(
								0, 3)
								+ "," + recordsRow[2].toString();
						expenseMonthly.put(dateString,
								Double.parseDouble(recordsRow[0].toString()));
						log.debug("Expenses" + recordsRow.length);
						for (int i = 0; i < recordsRow.length; i++) {
							System.out.print("\t" + recordsRow[i]);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Failed!",e);
			log.debug("Inside expeses mnthly exception  "
					+ e.getLocalizedMessage());
			e.printStackTrace();
		}
		log.info("***expenditureMonthly ends***");
		return expenseMonthly;
	}

	public Map<String, Long> topSellingRegion(Session session, Date startDate,
			Date endDate, int sellerId) {

		log.info("***topSellingRegion starts***");
		List<Object[]> results = null;
		Map<String, Long> topSellingRegion = new LinkedHashMap<>();
		try {			
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Order.class);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("customer", "customer",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.isNotNull("customer.customerCity"))
					.add(Restrictions.ne("customer.customerCity", ""))
					.add(Restrictions.between("shippedDate", startDate, endDate));
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			ProjectionList projList = Projections.projectionList();
			projList.add(Projections.rowCount(), "count");
			projList.add(Projections.groupProperty("customer.customerCity"));
			criteria.setProjection(projList);
			criteria.addOrder(org.hibernate.criterion.Order.desc("count"));
			results = criteria.list();
			Iterator iterator1 = results.iterator();
			if (results != null) {
				while (iterator1.hasNext()) {
					System.out.println("\n");
					Object[] recordsRow = (Object[]) iterator1.next();
					if (recordsRow[0] != null && recordsRow[1] != null) {
						topSellingRegion.put(recordsRow[1].toString(),
								Long.parseLong(recordsRow[0].toString()));						
						for (int i = 0; i < recordsRow.length; i++) {
							System.out.print("\t" + recordsRow[i]);
						}
					}
				}
			}			
		} catch (Exception e) {
			log.error("Failed!",e);
			log.debug("Inside exception  topSellingRegion "+ e.getLocalizedMessage());
			e.printStackTrace();
		}
		log.info("***topSellingRegion ends***");
		return topSellingRegion;
	}

	public Map<String, Long> topSellingSKU(Session session, Date startDate,
			Date endDate, int sellerId) {

		log.info("***topSellingSKU starts***");
		List<Object[]> results = null;
		Map<String, Long> topSKU = new LinkedHashMap<String, Long>();
		try {			
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Order.class);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN)
			.createAlias("orderReturnOrRTO", "orderReturnOrRTO",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId));
					//.add(Restrictions.between("shippedDate", startDate, endDate));
			Criterion rest1 = Restrictions.eq("poOrder", false);
			Criterion rest2 = Restrictions.and(	
					Restrictions.eq("poOrder", true),
					Restrictions.isNotNull("consolidatedOrder"));
			criteria.add(Restrictions.or(rest1, rest2));
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			ProjectionList projList = Projections.projectionList();
			projList.add(Projections.groupProperty("productSkuCode"));
			projList.add(Projections.sqlProjection("(sum(quantity-returnorrtoQty)) as grossQty",new String[] { "grossQty" },
					new Type[] { Hibernate.INTEGER }),"grossQty");
			/*projList.add(Projections.sum("quantity"), "sum");
			projList.add(Projections.sum("orderReturnOrRTO.returnorrtoQty"), "return");*/
			criteria.setProjection(projList);
			criteria.addOrder(org.hibernate.criterion.Order.desc("grossQty"));
			results = criteria.list();
			Iterator iterator1 = results.iterator();
			if (results != null) {
				while (iterator1.hasNext()) {
					System.out.println("\n");
					Object[] recordsRow = (Object[]) iterator1.next();
					if (recordsRow[0] != null && recordsRow[1] != null) {
						System.out.println("Top selling sku:"
								+ recordsRow.length);
						System.out.println(" record 1 : "
								+ recordsRow[0].toString() + " record 2 : "
								+ recordsRow[1].toString());
						topSKU.put(recordsRow[0].toString(),
								Long.parseLong(recordsRow[1].toString()));
						for (int i = 0; i < recordsRow.length; i++) {
							System.out.print("\t" + recordsRow[i]);
						}
					}
				}
			}			
		} catch (Exception e) {
			log.error("Failed!",e);
			log.debug("Inside exception  " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		log.info("***topSellingSKU ends***");
		return topSKU;
	}

	public Map<String, Double> expenseGroupByCatForTime(Session session,
			Date startDate, Date endDate, int sellerId) {
		
		log.info("***expenseGroupByCatForTime starts***");
		List<Object[]> results = null;
		Map<String, Double> expenseCatWise = new LinkedHashMap<String, Double>();
		try {
			Criteria criteria = session
					.createCriteria(Expenses.class)
					.add(Restrictions.eq("sellerId", sellerId))
					.add(Restrictions
							.between("expenseDate", startDate, endDate));
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			ProjectionList projList = Projections.projectionList();
			projList.add(Projections.sum("amount"));
			projList.add(Projections.groupProperty("expenseCatName"));
			criteria.setProjection(projList);
			results = criteria.list();
			Iterator iterator1 = results.iterator();
			if (results != null) {
				while (iterator1.hasNext()) {
					System.out.println(" row\n");
					Object[] recordsRow = (Object[]) iterator1.next();
					if (recordsRow[0] != null && recordsRow[1] != null) {
						expenseCatWise.put(recordsRow[1].toString(),
								Double.parseDouble(recordsRow[0].toString()));
						System.out.println("Expense sthis month :"
								+ recordsRow.length);
						for (int i = 0; i < recordsRow.length; i++) {
							System.out.print("\t" + recordsRow[i]);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Failed!",e);
			log.debug("Inside exception expenseGroupByCatForTime  "+ e.getLocalizedMessage());
			e.printStackTrace();
		}
		log.debug(" expenseCatWise : " + expenseCatWise);
		log.info("***expenseGroupByCatForTime ends***");
		return expenseCatWise;
	}

	public double totalExpenseForTime(Session session, Date startDate,Date endDate, int sellerId) {
		
		log.info("***totalExpenseForTime starts***");
		List<Object> results = null;
		double totalExpenses = 0;
		try {
			session.beginTransaction();
			Criteria criteria = session
					.createCriteria(Expenses.class)
					.add(Restrictions.eq("sellerId", sellerId))
					.add(Restrictions
							.between("expenseDate", startDate, endDate))
					.add(Restrictions.ne("expenseCatName", "Assets"));
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			ProjectionList projList = Projections.projectionList();
			projList.add(Projections.sum("amount"));
			criteria.setProjection(projList);
			results = criteria.list();
			if (results != null && results.size() != 0	&& results.get(0) != null)
				totalExpenses = Double.parseDouble(results.get(0).toString());
			log.debug(" totalExpenses : " + totalExpenses);
		} catch (Exception e) {
			log.error("Failed!",e);
			log.debug("Inside exception totalExpenseForTime "+ e.getLocalizedMessage());
			e.printStackTrace();
		}
		log.info("***totalExpenseForTime ends***");
		return totalExpenses;
	}

	public Map<Date, Long> orderCountMonthly(Session session, Date startDate,Date endDate, int sellerId) {


		log.info("***orderCountMonthly starts***");
		List<Object[]> results = null;
		Map<Date, Long> orderCount = new LinkedHashMap<>();
		Date orderDate = null;
		try {
			session.beginTransaction();
			Query query = session.createSQLQuery(orderCountMonthlyQuery)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			results = query.list();
			Iterator iterator1 = results.iterator();
			if (results != null) {
				while (iterator1.hasNext()) {
					System.out.println("row\n");
					Object[] recordsRow = (Object[]) iterator1.next();					
					if (recordsRow[0] != null && recordsRow[1] != null
							&& recordsRow[2] != null) {
						orderDate = new Date();
						orderDate.setYear(Integer.parseInt(recordsRow[2]
								.toString()) - 1900);
						orderDate.setMonth(Integer.parseInt(recordsRow[1]
								.toString()) - 1);
						orderDate.setDate(1);
						orderCount.put(orderDate,
								Long.parseLong(recordsRow[0].toString()));
						for (int i = 0; i < recordsRow.length; i++) {
							System.out.print("\t" + recordsRow[i]);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Failed!",e);
			log.debug("Inside exception  " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		log.info("***orderCountMonthly ends***");
		return orderCount;
	}

	public Map<Date, Long> paymentCountMonthly(Session session, Date startDate,
			Date endDate, int sellerId) {
		
		log.info("***paymentCountMonthly starts***");
		List<Object[]> results = null;
		Map<Date, Long> paymentCount = new LinkedHashMap<>();
		Date paymentDate = null;
		try {
			session.beginTransaction();
			Query query = session.createSQLQuery(paymentCountMonthlyQuery)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			results = query.list();
			Iterator iterator1 = results.iterator();
			if (results != null) {
				while (iterator1.hasNext()) {
					System.out.println("row\n");
					Object[] recordsRow = (Object[]) iterator1.next();					
					if (recordsRow[0] != null && recordsRow[1] != null
							&& recordsRow[2] != null) {
						paymentDate = new Date();
						paymentDate.setYear(Integer.parseInt(recordsRow[2].toString()) - 1900);
						paymentDate.setMonth(Integer.parseInt(recordsRow[1].toString()) - 1);
						paymentDate.setDate(1);
						paymentCount.put(paymentDate,
								Long.parseLong(recordsRow[0].toString()));
						for (int i = 0; i < recordsRow.length; i++) {
							System.out.print("\t" + recordsRow[i]);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Failed!",e);
			log.debug("Inside paymentCount exception  "+ e.getLocalizedMessage());
			e.printStackTrace();
		}
		log.info("***paymentCountMonthly ends***");
		return paymentCount;
	}

	public long countForDuration(Session session, Date startDate, Date endDate,	int sellerId, String querry) {

		log.info("***countForDuration starts***");
		List<java.math.BigInteger> results = null;
		long count = 0;
		try {
			session.beginTransaction();
			Query query = session.createSQLQuery(querry)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			results = query.list();
			if (results != null && results.size() != 0
					&& results.get(0) != null) {
				java.math.BigInteger countBigInt = results.get(0);
				log.debug(results.get(0));
				log.debug("BigInt to logn " + countBigInt.longValue());
				count = countBigInt.longValue();				
			}
		} catch (Exception e) {
			log.error("Failed!",e);
			log.debug("Inside pcount exception  "+ e.getLocalizedMessage());
			e.printStackTrace();
		}
		log.info("***countForDuration ends***");
		return count;
	}

	public double amountForDuration(Session session, Date startDate,Date endDate, int sellerId, String querry) {

		log.info("***amountForDuration starts***");
		List<Double> results = null;
		double sum = 0;
		try {
			session.beginTransaction();
			Query query = session.createSQLQuery(querry)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			results = query.list();
			if (results != null && results.size() != 0
					&& results.get(0) != null) {
				sum = results.get(0);
				log.debug(results.get(0));
				log.debug("Double" + sum);
			}
		} catch (Exception e) {
			log.error("Failed!",e);
			log.debug("Inside pcount exception  "+ e.getLocalizedMessage());
			e.printStackTrace();
		}
		log.info("***amountForDuration ends***");
		return sum;
	}
	
	public double grossProfitForDuration(Session session, Date startDate,Date endDate, int sellerId) {

		log.info("***amountForDuration starts***");
		List<Double> results = null;
		double gpforMP = 0;
		double gpforMPReturn = 0;
		double gpforPO = 0;
		double gpforGP = 0;
		double sum = 0;
		try {
			session.beginTransaction();
			session.flush();
			Query gpquerryforMP = session.createSQLQuery(grossProfitForMPDurationQuery)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
					/*.setParameter("startDate", "2016-")
					.setParameter("endDate", format.format(endDate))
					.setParameter("sellerId", sellerId);*/
			results = gpquerryforMP.list();
			if (results != null && results.size() != 0
					&& results.get(0) != null) {
				gpforMP = results.get(0);
				System.out.println("gpforMP "+gpforMP);
			}
			Query gpquerryforMPReturn = session.createSQLQuery(grossProfitForMPReturnDurationQuery)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			results = gpquerryforMPReturn.list();
			if (results != null && results.size() != 0
					&& results.get(0) != null) 
				gpforMPReturn = results.get(0);
				
			Query gpquerryforPO = session.createSQLQuery(grossProfitPODurationQuery)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			results = gpquerryforPO.list();
			if (results != null && results.size() != 0
					&& results.get(0) != null) 
				gpforPO = results.get(0);
			
			Query gpquerryforGP = session.createSQLQuery(grossProfitGPDurationQuery)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			results = gpquerryforGP.list();
			if (results != null && results.size() != 0
					&& results.get(0) != null) 
				gpforGP = results.get(0);
			
			sum=gpforMP-gpforMPReturn+gpforPO-gpforGP;
			
			System.out.println(" Calculating gp for duration : startdate "+startDate+" endDate : "+endDate);
			System.out.println(" Calculating gp MP : gpforMP "+gpforMP+" gpforMPReturn : "+gpforMPReturn);
			System.out.println(" Calculating gp PO : gpforPO "+gpforPO+" gpforGP : "+gpforGP);
				log.debug(results.get(0));
				log.debug("Double" + sum);
			
		} catch (Exception e) {
			log.error("Failed!",e);
			log.debug("Inside pcount exception  "+ e.getLocalizedMessage());
			e.printStackTrace();
		}
		log.info("***amountForDuration ends***");
		return sum;
	}

	public List<Object> orderNRQuantityMonthly(Session session, Date startDate,
			Date endDate, int sellerId) {

		log.info("***orderNRQuantityMonthly starts***");
		List<Object[]> results = null;
		List<Object> returnList = new ArrayList<Object>();
		Map<String, Double> nrMAp = new LinkedHashMap<String, Double>();
		Map<String, Long> quantityMAp = new LinkedHashMap<String, Long>();		
		try {
			session.beginTransaction();
			Query query = session.createSQLQuery(orderNRMonthlyQuery)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			results = query.list();
			Iterator iterator1 = results.iterator();
			if (results != null) {
				while (iterator1.hasNext()) {
					System.out.println("Expense : row\n");
					Object[] recordsRow = (Object[]) iterator1.next();					
					if (recordsRow[0] != null && recordsRow[1] != null
							&& recordsRow[2] != null && recordsRow[3] != null) {
						String dateString = recordsRow[2].toString().substring(
								0, 3)
								+ "," + recordsRow[3].toString();
						nrMAp.put(dateString,
								Double.parseDouble(recordsRow[0].toString()));
						quantityMAp.put(dateString,
								Long.parseLong(recordsRow[1].toString()));
					}
				}
			}
			log.debug(" NR monthly nrMAp " + nrMAp);
			log.debug(" quantityMAp  " + quantityMAp);
			returnList.add(nrMAp);
			returnList.add(quantityMAp);
		} catch (Exception e) {
			log.error("Failed!",e);
			log.debug("Inside exception  " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		log.info("***orderNRQuantityMonthly ends***");
		return returnList;
	}

	public List<Object> returnNRQuantityMonthly(Session session,
			Date startDate, Date endDate, int sellerId) {

		log.info("***returnNRQuantityMonthly starts***");
		List<Object[]> resultsPO = null;
		List<Object[]> resultsMP = null;
		List<Object> returnList = new ArrayList<Object>();
		Map<String, Double> returnAMountMAp = new LinkedHashMap<String, Double>();
		Map<String, Long> quantityMAp = new LinkedHashMap<String, Long>();
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			Query POquery = session.createSQLQuery(returnNRPOMonthlyQuery)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			Query MPquery = session.createSQLQuery(returnNRMPMonthlyQuery)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			resultsMP = MPquery.list();
			resultsPO = POquery.list();
			Iterator iterator1 = resultsMP.iterator();
			if (resultsMP != null) {
				while (iterator1.hasNext()) {
					if (resultsMP != null && resultsMP.size() != 0
							&& resultsMP.get(0) != null) {
						System.out.println("Return Maount  : row\n");
						Object[] recordsRow = (Object[]) iterator1.next();
						if (recordsRow[0] != null && recordsRow[1] != null
								&& recordsRow[2] != null
								&& recordsRow[3] != null) {
							System.out.println(" record length:"
									+ recordsRow.length);
							String dateString = recordsRow[2].toString()
									.substring(0, 3)
									+ ","
									+ recordsRow[3].toString();
							returnAMountMAp.put(dateString, Double
									.parseDouble(recordsRow[0].toString()));
							quantityMAp.put(dateString,
									Long.parseLong(recordsRow[1].toString()));

						}
					}
				}
			}
			Iterator poIterator = resultsPO.iterator();
			if (resultsPO != null) {
				while (poIterator.hasNext()) {
					if (resultsPO != null && resultsPO.size() != 0
							&& resultsPO.get(0) != null) {
						System.out.println("resultsPO : row\n");
						Object[] recordsRow = (Object[]) poIterator.next();
						if (recordsRow[0] != null && recordsRow[1] != null
								&& recordsRow[2] != null
								&& recordsRow[3] != null) {
							System.out.println(" record length:"+ recordsRow.length);
							String dateString = recordsRow[2].toString().substring(0, 3)+ "," + recordsRow[3].toString();
							returnAMountMAp
									.put(dateString,
											returnAMountMAp
													.containsKey(dateString) ? returnAMountMAp
													.get(dateString)
													: 0 + Double
															.parseDouble(recordsRow[0]
																	.toString()));
							quantityMAp
									.put(dateString,
											quantityMAp.containsKey(dateString) ? quantityMAp
													.get(dateString) : 0 + Long
													.parseLong(recordsRow[1]
															.toString()));

						}
					}
				}
			}
			returnList.add(returnAMountMAp);
			returnList.add(quantityMAp);
		} catch (Exception e) {
			log.error("Failed!",e);
			log.debug("Inside exception  " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		log.debug(returnList);
		log.info("***returnNRQuantityMonthly ends***");
		return returnList;
	}
}