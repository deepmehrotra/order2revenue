package com.o2r.dao;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
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
       
       
       private static final String stockValuationQuery = "Select sum(quantity*price) as valuation from(select ps.stockAvailable "
               + "as quantity,ps.price as price from ProductStockList ps, Product pr,"
               + "Product_ProductStockList prps where ps.stockId=prps.closingStocks_stockId"
               + " and prps.Product_productId=pr.productId and ps.year=:year and ps.month=:month and pr.seller_Id=:sellerId) as T";
       private static final String orderNRMonthlyQuery = "Select sum(ot.netRate) as netrate, (ot.quantity)"
                     + " as quantity,Monthname(ot.orderDate) as month ,YEAR(ot.orderDate) from "
                     + "Order_Table ot ,OrderReturn  ort where "
                     + "ot.orderDate between :startDate AND :endDate and ort.returnDate is NULL "
                     + "and ort.returnOrRTOId is NULL and ort.returnId=ot.orderReturnOrRTO_returnId and ot.seller_Id=:sellerId "
                     + "GROUP BY YEAR(ot.orderDate), MONTH(ot.orderDate) DESC";
       private static final String orderCountMonthlyQuery = "Select count(*), month(ot.orderDate) as month ,YEAR(ot.orderDate) as year from Order_Table"
                     + " ot where ot.orderDate between :startDate AND :endDate and ot.seller_Id=:sellerId "
                     + "GROUP BY YEAR(ot.orderDate), MONTH(ot.orderDate) DESC";
       private static final String paymentCountMonthlyQuery = "Select count(*), month(op.dateOfPayment) as month ,YEAR(op.dateOfPayment) "
                     + "as year from Order_Table ot ,OrderPay op where op.dateOfPayment between :startDate AND :endDate "
                     + "and op.dateOfPayment is NOT NULL and op.paymentId=ot.orderPayment_paymentId and ot.seller_Id=:sellerId "
                     + "GROUP BY YEAR(op.dateOfPayment), MONTH(op.dateOfPayment) DESC";
       private static final String paymentCountForDurationQuery = "Select count(*) from Order_Table ot ,"
                     + "OrderPay op where op.dateOfPayment between :startDate AND :endDate and op.dateOfPayment "
                     + "is NOT NULL and op.paymentId=ot.orderPayment_paymentId and ot.seller_Id=:sellerId";
       private static final String orderCountForDurationQuery = "Select count(*) from Order_Table ot "
                     + "where ot.orderDate between :startDate AND :endDate and ot.seller_Id=:sellerId";
       private static final String grossProfitForDurationQuery = "Select sum(grossProfit) from Order_Table ot where "
                     + "ot.orderDate between :startDate AND :endDate and ot.seller_Id=:sellerId";
       private static final String returnNRMonthlyQuery = "Select sum(ort.estimateddeduction) as returnCharges, (ort.returnorrtoQty)"
                     + " as quantity,Monthname(ort.returnDate) as month ,YEAR(ort.returnDate) from "
                     + "Order_Table ot ,OrderReturn  ort where "
                     + "ort.returnDate between :startDate AND :endDate and ort.returnId=ot.orderReturnOrRTO_returnId and ot.seller_Id=:sellerId "
                     + "GROUP BY YEAR(ort.returnDate), MONTH(ort.returnDate) DESC";
       private static final String grossProfitMonthlyQuery = "Select sum(ot.grossProfit) as grossProfit,Monthname(ot.orderDate) as month ,"
                     + "YEAR(ot.orderDate) from Order_Table ot where ot.orderDate "
                     + "between :startDate AND :endDate and ot.seller_Id=:sellerId "
                     + "GROUP BY YEAR(ot.orderDate), MONTH(ot.orderDate) DESC";
       private static final String expenditureMonthlyQuery = "SELECT sum(amount) as amt ,Monthname(t.expenseDate) as month, YEAR(t.expenseDate) as year FROM "
               + "Expenses t where t.expenseDate between :startDate AND :endDate and t.sellerId=:sellerId "
               + "GROUP BY YEAR(t.expenseDate), MONTH(t.expenseDate) DESC";
       @SuppressWarnings("deprecation")
       @Override
       public DashboardBean getDashboardDetails(int sellerId)
       {
               long startTime=System.currentTimeMillis();
              DashboardBean dashboardBean = null;
              Date todayDate = new Date();
              Date yeasterdayDate = new Date();
              Date lastYearEnd = new Date();
              Date thisYearSatrt = new Date();
              Date lastYearSatrt = new Date();
              Date oneMonthBack = new Date();
              Date sixMonthsBack = new Date();
              Date after10days = new Date();
              List<Object> orderNRQuantityMonthly=null;
              List<Object> returnNRQuantityMonthly=null;
              todayDate.setDate(todayDate.getDate() + 1);
              todayDate.setMonth(todayDate.getMonth());
              yeasterdayDate.setDate(yeasterdayDate.getDate() - 1);
              lastYearEnd.setDate(31);
              lastYearEnd.setMonth(11);
              lastYearEnd.setYear(lastYearEnd.getYear() - 1);
              thisYearSatrt.setDate(1);
              thisYearSatrt.setMonth(0);
              lastYearSatrt.setDate(1);
             lastYearSatrt.setMonth(0);
              lastYearSatrt.setYear(lastYearSatrt.getYear() - 1);
              after10days.setDate(after10days.getDate()+10);
              oneMonthBack.setDate(oneMonthBack.getDate() - 31);
              sixMonthsBack.setDate(1);
              sixMonthsBack.setMonth(sixMonthsBack.getMonth()-5);
              try
              {
                     dashboardBean = new DashboardBean();
                     Session session = sessionFactory.openSession();
                     session.beginTransaction();
                     System.out.println(" Calling list ofupcoming payment");
                     dashboardBean.setProfitThisYear(netProfitForTime(session,
                     lastYearEnd, todayDate, sellerId));
                      dashboardBean.setProfitLastYear(netProfitForTime(session,
                     lastYearSatrt, thisYearSatrt, sellerId));
                      if((int)dashboardBean.getProfitLastYear()!=0)
                      {
                    if(dashboardBean.getProfitThisYear()>dashboardBean.getProfitLastYear())
                        dashboardBean.setPercentChangeInProfit(((dashboardBean.getProfitThisYear()-dashboardBean.getProfitLastYear())/dashboardBean.getProfitThisYear())*100);
                    else
                        dashboardBean.setPercentChangeInProfit(((dashboardBean.getProfitLastYear()-dashboardBean.getProfitThisYear())/dashboardBean.getProfitLastYear())*100);
                      }
                      dashboardBean.setSaleQuantityThisYear(netSaleQtyforTime(session,
                     lastYearEnd, todayDate, sellerId));
                      dashboardBean.setSaleQuantityLastYear(netSaleQtyforTime(session,
                     lastYearEnd, todayDate, sellerId));
                      if((int)dashboardBean.getSaleQuantityLastYear()!=0)
                      {
                      if(dashboardBean.getSaleQuantityThisYear()>dashboardBean.getSaleQuantityLastYear())
                          dashboardBean.setPercentChangeInSQ(((dashboardBean.getSaleQuantityThisYear()-dashboardBean.getSaleQuantityLastYear())/dashboardBean.getSaleQuantityLastYear())*100);
                      else
                          dashboardBean.setPercentChangeInSQ(((dashboardBean.getSaleQuantityLastYear()-dashboardBean.getSaleQuantityThisYear())/dashboardBean.getSaleQuantityLastYear())*100);
                      }
                      dashboardBean.setTotalCustomers(getTotalCustomer(session,
                     sellerId));
                      dashboardBean.setTotalStock(getTotalSKUCount(session, sellerId));
                      dashboardBean.setLast30daysOrderCount(
                     orderCountforTimeDaily(session, oneMonthBack, todayDate,
                     sellerId));
                      dashboardBean.setLast30DaysPaymentCount(paymentCountforTimeDaily(
                     session, oneMonthBack, todayDate, sellerId));
                      dashboardBean.setLast12MonthsOrderCount(orderCountMonthly(session,
                     lastYearEnd, todayDate, sellerId));
                      dashboardBean.setLast12MonthsPaymentCount(paymentCountMonthly(session
                     , lastYearEnd, todayDate, sellerId));
                      dashboardBean.setTodaysOrderCount(countForDuration(session,
                     yeasterdayDate, todayDate, sellerId,
                     orderCountForDurationQuery));
                     dashboardBean.setThisMonthOrderCount(countForDuration(session,
                     oneMonthBack, todayDate, sellerId, orderCountForDurationQuery));
                     dashboardBean.setThisYearOrderCount(countForDuration(session,
                     lastYearEnd, todayDate, sellerId, orderCountForDurationQuery));
                      dashboardBean.setTodaysPaymentCount( countForDuration(session,
                     yeasterdayDate, todayDate, sellerId,
                     paymentCountForDurationQuery)); //todays
                     dashboardBean.setThisMonthPaymentCount( countForDuration(session,
                     oneMonthBack, todayDate, sellerId,
                     paymentCountForDurationQuery)); //this month
                     dashboardBean.setThisYearPaymentCount(countForDuration(session,
                     lastYearEnd, todayDate, sellerId, paymentCountForDurationQuery));
                     dashboardBean.setTodaysGrossProfit(amountForDuration(session,yeasterdayDate, todayDate, sellerId, grossProfitForDurationQuery));
                     dashboardBean.setThisMonthGrossProfit(amountForDuration(session,oneMonthBack, todayDate, sellerId, grossProfitForDurationQuery));
                     dashboardBean.setThisYearGrossProfit(amountForDuration(session,lastYearEnd, todayDate, sellerId, grossProfitForDurationQuery));
                     //testing purpose startdate as one monthback
                     dashboardBean.setTotalUpcomingPayments(listOfUpcomingPayment(session, oneMonthBack, after10days, sellerId));
                     dashboardBean.setTotalOutstandingPayments(listOfOutstandingPayment(session, sellerId));
                     //Six months top selling sku and region just for testing
                     dashboardBean.setTopSellingSKU(topSellingSKU(session, sixMonthsBack, todayDate, sellerId));
                     dashboardBean.setTopSellingRegion(topSellingRegion(session, sixMonthsBack, todayDate, sellerId));
                     dashboardBean.setExpenditureThisMonth(expenseGroupByCatForTime(session, oneMonthBack, todayDate, sellerId));
                     dashboardBean.setGrossProfitMonthly(grossProfitMonthly(session, sixMonthsBack, todayDate, sellerId));
                     dashboardBean.setExpenditureMonthly(expenditureMonthly(session, sixMonthsBack, todayDate, sellerId));
                     dashboardBean.setTaxAlerts(ConverterClass.prepareListofTaxDetailBean(getTaxAlert(session, sixMonthsBack, sellerId)));
                     dashboardBean.setTdsAlerts(ConverterClass.prepareListofTaxDetailBean(getTDSAlert(session, sixMonthsBack, sellerId)));
                     orderNRQuantityMonthly=orderNRQuantityMonthly(session, sixMonthsBack, todayDate, sellerId);
                     if(orderNRQuantityMonthly!=null&&orderNRQuantityMonthly.size()!=0)
                     {
                     if(orderNRQuantityMonthly.get(1)!=null)
                     dashboardBean.setSaleQuantity((Map<String,Long>)orderNRQuantityMonthly.get(1));
                     if(orderNRQuantityMonthly.get(0)!=null)
                     dashboardBean.setSaleAmount((Map<String,Double>)orderNRQuantityMonthly.get(0));
                     }
                     returnNRQuantityMonthly=returnNRQuantityMonthly(session, sixMonthsBack, todayDate, sellerId);
                     System.out.println(" returnNRQuantityMonthly value : "+returnNRQuantityMonthly);
                   System.out.println(" returnNRQuantityMonthly "+returnNRQuantityMonthly.size());
                   if(returnNRQuantityMonthly!=null&&returnNRQuantityMonthly.size()!=0)
                   {
                     if(returnNRQuantityMonthly.get(0)!=null)
                         dashboardBean.setReturnAmount((Map<String,Double>)returnNRQuantityMonthly.get(0));
                     if(returnNRQuantityMonthly.get(1)!=null)
                     dashboardBean.setReturnQuantity((Map<String,Long>)returnNRQuantityMonthly.get(1));
                   }
                     session.getTransaction().commit();
                     session.close();
              }
              catch (Exception e) {
                     System.out.println("Inside exception  " + e.getLocalizedMessage());
                     e.printStackTrace();
              }
              long endTime=System.currentTimeMillis();
              System.out.println(" Elpased time in Dashboard Data : "+(endTime-startTime));
              return dashboardBean;
       }
       public long netSaleQtyforTime(Session session, Date startDate,
                     Date endDate, int sellerId)
       {
              List<Object[]> results = null;
              long netSaleQty = 0;
              long quantity = 0;
              long returnQty = 0;
              try
              {
                     // session=sessionFactory.openSession();
                     session.beginTransaction();
                     Criteria criteria = session.createCriteria(Order.class);
                     criteria.createAlias("seller", "seller",
                                  CriteriaSpecification.LEFT_JOIN);
                     criteria.createAlias("orderPayment", "orderPayment",
                                  CriteriaSpecification.LEFT_JOIN);
                     criteria.createAlias("orderReturnOrRTO", "orderReturnOrRTO",
                                  CriteriaSpecification.LEFT_JOIN)
                     .add(Restrictions.eq("seller.id", sellerId))
                     .add(Restrictions.between("orderDate", startDate, endDate));
                     criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
                     ProjectionList projList = Projections.projectionList();
                     projList.add(Projections.sum("quantity"));
                     projList.add(Projections.sum("orderReturnOrRTO.returnorrtoQty"));
                     criteria.setProjection(projList);
                     results = criteria.list();
                     Iterator iterator1 = results.iterator();
                     if (results != null) {
                           while (iterator1.hasNext()) {
                                  System.out.println("Sale quantity : \n");
                                  Object[] recordsRow = (Object[]) iterator1.next();
                                  System.out.println(" record length:" + recordsRow.length);
                                  if(recordsRow[0]!=null&&recordsRow[1]!=null)
                                  {
                                  quantity=Long.parseLong(recordsRow[0].toString());
                                  returnQty=Long.parseLong(recordsRow[1].toString());
                                  netSaleQty=quantity-returnQty;
                                  for (int i = 0; i < recordsRow.length; i++) {
                                         System.out.print("\t" + recordsRow[i]);
                                  }
                                  }
                           }
                     }
                     // session.close();
              }
              catch (Exception e) {
                     System.out.println("Inside exception netsaleQauntity " + e.getLocalizedMessage());
                     e.printStackTrace();
              }
              return netSaleQty;
       }
       public Double netProfitForTime(Session session, Date startDate,
                     Date endDate, int sellerId)
       {
              // NP=NSR+Valuation of closing stock -Openign stock value -expense
              System.out.println(" Calculating net profit ");
              List<Double> osValuation = null;
              List<Double> csValuation = null;
              List<Double> results = null;
              List<Double> returnCharges = null;
              double openStock = 0;
              double currentStock = 0;
              double netProfit = 0;
              double totalExpenses = 0;
              double orderNr = 0;
              double totalReturn = 0;
              try
              {
                     // session=sessionFactory.openSession();
                     session.beginTransaction();
                     Criteria criteriaForNR = session.createCriteria(Order.class);
                     criteriaForNR.createAlias("seller", "seller",
                                  CriteriaSpecification.LEFT_JOIN);
                     criteriaForNR.createAlias("orderPayment", "orderPayment",
                                  CriteriaSpecification.LEFT_JOIN);
                     criteriaForNR
                                  .createAlias("orderReturnOrRTO", "orderReturnOrRTO",
                                                CriteriaSpecification.LEFT_JOIN)
                                  .add(Restrictions.eq("seller.id", sellerId))
                                  .add(Restrictions.between("orderDate", startDate, endDate));

                     criteriaForNR
                                  .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
                     ProjectionList projList = Projections.projectionList();
                     projList.add(Projections.sum("netRate"));
                     criteriaForNR.setProjection(projList);
                     results = criteriaForNR.list();
                     if (results != null && results.size() != 0&&results.get(0)!=null)
                           orderNr = results.get(0);
                     Criteria criteriaForRetrun = session.createCriteria(Order.class);
                     criteriaForRetrun.createAlias("seller", "seller",
                                  CriteriaSpecification.LEFT_JOIN);
                     criteriaForRetrun.createAlias("orderPayment", "orderPayment",
                                  CriteriaSpecification.LEFT_JOIN);
                     criteriaForRetrun
                                  .createAlias("orderReturnOrRTO", "orderReturnOrRTO",
                                                CriteriaSpecification.LEFT_JOIN)
                                  .add(Restrictions.eq("seller.id", sellerId))
                                  .add(Restrictions.between("orderDate", startDate, endDate))
                                  .add(Restrictions
                                                .isNotNull("orderReturnOrRTO.returnOrRTOId"))
                                  .add(Restrictions.isNotNull("orderReturnOrRTO.returnDate"));
                     criteriaForRetrun
                                  .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
                     ProjectionList returnProjList = Projections.projectionList();
                     returnProjList.add(Projections
                                  .sum("orderReturnOrRTO.returnOrRTOChargestoBeDeducted"));
                     criteriaForRetrun.setProjection(returnProjList);
                     returnCharges = criteriaForRetrun.list();
                     System.out.println(" Chekcing returnCharges : "+returnCharges+" returnCharges size : "+returnCharges.size());
                     if (returnCharges != null && returnCharges.size() != 0&&returnCharges.get(0)!=null)
                           totalReturn = returnCharges.get(0);
                     Query openingStockValueThisYear = session
                                  .createSQLQuery(stockValuationQuery)
                                  .setParameter("year", startDate.getYear())
                                  .setParameter("month", startDate.getMonth())
                                  .setParameter("sellerId", sellerId);
                     osValuation = openingStockValueThisYear.list();
                     Query closingStockValueThisMonth = session
                                  .createSQLQuery(stockValuationQuery)
                                  .setParameter("year", endDate.getYear())
                                  .setParameter("month", endDate.getMonth() )
                                  .setParameter("sellerId", sellerId);
                     csValuation = closingStockValueThisMonth.list();
                     if (osValuation != null && osValuation.size() != 0&&osValuation.get(0)!=null)
                           openStock = osValuation.get(0);
                     if (csValuation != null && csValuation.size() != 0&&csValuation.get(0)!=null)
                           currentStock = csValuation.get(0);
                     totalExpenses = totalExpenseForTime(session, startDate, endDate,
                                  sellerId);
                     netProfit = (orderNr - totalReturn + currentStock)
                                  - (openStock + totalExpenses);
                     System.out.println(" Order NR : " + orderNr + "-->totalReturn :"
                                  + totalReturn + "-->currentStock :" + currentStock + ""
                                  + "-->openStock :" + openStock + "=--->totalExpenses :"
                                  + totalExpenses);
                     // session.getTransaction().commit();
                     // session.close();
              }
              catch (Exception e) {
                     System.out.println("Inside exception  " + e.getLocalizedMessage());
                     e.printStackTrace();
              }
              return netProfit;
       }
       public Map<Date, Long> paymentCountforTimeDaily(Session session,
                     Date startDate, Date endDate, int sellerId)
       {
              List<Object[]> results = null;
              Map<Date, Long> paymentCount = new LinkedHashMap<>();
              try
              {
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
                     criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
                     ProjectionList projList = Projections.projectionList();
                   projList.add(Projections.rowCount());
                     projList.add(Projections
                                  .groupProperty("orderPayment.dateofPayment"));
                     criteria.setProjection(projList);
                     criteria.addOrder(org.hibernate.criterion.Order.asc("orderPayment.dateofPayment"));
                     results = criteria.list();
                     Iterator iterator1 = results.iterator();
                     if (results != null) {
                           while (iterator1.hasNext()) {
                                  System.out.println("\n");
                                  Object[] recordsRow = (Object[]) iterator1.next();
                                  if(recordsRow[0]!=null&&recordsRow[1]!=null)
                                  {
                                  System.out.println(" record Payment length:"
                                                + recordsRow.length);
                                  System.out.println(recordsRow[0].toString());
                                  System.out.println(Timestamp.valueOf(recordsRow[1]
                                                .toString()));
                                  System.out.println("conveting into date : "
                                                + new Date(Timestamp.valueOf(
                                                              recordsRow[1].toString()).getTime()));
                                  paymentCount.put(
                                                new Date(Timestamp
                                                              .valueOf(recordsRow[1].toString())
                                                              .getTime()), Long.parseLong(recordsRow[0]
                                                              .toString()));
                                  }
                           }
                     }
              }
              catch (Exception e) {
                     System.out.println("Inside payment count exception  "
                                  + e.getLocalizedMessage());
                     e.printStackTrace();
              }
              return paymentCount;
       }
       public Map<Date, Long> orderCountforTimeDaily(Session session,
                     Date startDate, Date endDate, int sellerId)
       {
              List<Object[]> results = null;
              Map<Date, Long> orderCount = new LinkedHashMap<>();
              try
              {
                     session.beginTransaction();
                     Criteria criteria = session.createCriteria(Order.class);
                     criteria.createAlias("seller", "seller",
                                  CriteriaSpecification.LEFT_JOIN)
                     .add(Restrictions.eq("seller.id", sellerId))
                     .add(Restrictions.between("orderDate", startDate, endDate));
                     criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
                     ProjectionList projList = Projections.projectionList();
                     projList.add(Projections.rowCount());
                     projList.add(Projections.groupProperty("orderDate"));
                     criteria.setProjection(projList);
                     criteria.addOrder(org.hibernate.criterion.Order.asc("orderDate"));
                     results = criteria.list();
                     Iterator iterator1 = results.iterator();
                     if (results != null) {
                           while (iterator1.hasNext()) {
                                  System.out.println("\n");
                                  Object[] recordsRow = (Object[]) iterator1.next();
                                  System.out.println(" record order length:"
                                                + recordsRow.length);
                                  if(recordsRow[0]!=null&&recordsRow[1]!=null)
                                  {
                                  System.out.println(recordsRow[0].toString());
                                  System.out.println(recordsRow[1].toString());
                                  orderCount.put(new Date(Timestamp.valueOf(recordsRow[1].toString())
                                                              .getTime()), Long.parseLong(recordsRow[0]
                                                              .toString()));
                                  }
                           }
                     }
              }
              catch (Exception e) {
                     System.out.println("Inside exception orderCountforTimeDaily "
                                  + e.getLocalizedMessage());
                     e.printStackTrace();
              }
              return orderCount;
       }
       public Map<Date,Double> listOfUpcomingPayment(Session session,
                     Date startDate, Date endDate, int sellerId)
       {
              Map<Date,Double> totalUpcomingPayments= new LinkedHashMap<>();
              List<Object[]> results = null;
              System.out.println(" Inside upcoming payments -startDate- " + startDate
                           + " endDate : " + endDate);
              try
              {
                     // session=sessionFactory.openSession();
                     session.beginTransaction();
                     Criteria criteria = session.createCriteria(Order.class);
                     criteria.createAlias("seller", "seller",
                                  CriteriaSpecification.LEFT_JOIN);
                     criteria.createAlias("orderPayment", "orderPayment",
                                  CriteriaSpecification.LEFT_JOIN)
                     .add(Restrictions.eq("seller.id", sellerId))
                     .add(Restrictions.between("paymentDueDate", startDate, endDate))
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
                                  if(recordsRow[0]!=null&&recordsRow[1]!=null)
                                  {
                                  totalUpcomingPayments.put(
                                                new Date(Timestamp
                                                              .valueOf(recordsRow[1].toString())
                                                              .getTime()), Double.parseDouble(recordsRow[0]
                                                              .toString()));
                                  System.out.println(" record length:" + recordsRow.length);
                                  for (int i = 0; i < recordsRow.length; i++) {
                                         System.out.print("\t" + recordsRow[i]);
                                  }
                                  }
                           }
                     }
                     // session.getTransaction().commit();
                     // .close();
              }
              catch (Exception e) {
                     System.out.println("Inside upcomingpayment exception  "
                                  + e.getLocalizedMessage());
                     e.printStackTrace();
              }
              return totalUpcomingPayments;
       }
       public Map<String,Double> listOfOutstandingPayment(Session session,
                     int sellerId)
       {
              List<Object[]> results = null;
              Map<String,Double> totalOutStandingPayments= new LinkedHashMap<>();
              System.out
                           .println(" Inside ListOfOutstandingPayment payments -startDate- ");
              try
              {
                     session.beginTransaction();
                     Criteria criteria = session.createCriteria(Order.class);
                     criteria.createAlias("seller", "seller",
                                  CriteriaSpecification.LEFT_JOIN);
                     criteria.createAlias("orderPayment", "orderPayment",
                                  CriteriaSpecification.LEFT_JOIN)
                     .add(Restrictions.eq("seller.id", sellerId))
                     .add(Restrictions.lt("orderPayment.paymentDifference", 0.0));
                     criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
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
                                  System.out.println(" record length:" + recordsRow.length);
                                  if(recordsRow[0]!=null&&recordsRow[1]!=null)
                                  {
                                  totalOutStandingPayments.put(recordsRow[1].toString(), Double.parseDouble(recordsRow[0]
                                                              .toString()));
                                  for (int i = 0; i < recordsRow.length; i++) {
                                         System.out.print("\t" + recordsRow[i]);
                                  }
                                  }
                           }
                     }
              }
              catch (Exception e) {
                     System.out.println("Inside upcomingpayment exception  "
                                  + e.getLocalizedMessage());
                     e.printStackTrace();
              }
              return totalOutStandingPayments;
       }
       public Map<String,Double> grossProfitMonthly(Session session, Date startDate,
                     Date endDate, int sellerId)
       {
              System.out.println(" Inside for six months ");
              // Seller seller=null;
              List<Object[]> results = null;
              Map<String,Double> gpMonthly=new LinkedHashMap<>();
              Date gpDate=null;
              try
              {
                     session.beginTransaction();
                     Query query = session.createSQLQuery(grossProfitMonthlyQuery)
                     .setParameter("startDate", startDate)
                     .setParameter("endDate", endDate)
                     .setParameter("sellerId", sellerId);
                     results = query.list();
                     Iterator iterator1 = results.iterator();
                     if (results != null) {
                           while (iterator1.hasNext()) {
                                  System.out.println("grossp ro : row\n");
                                  Object[] recordsRow = (Object[]) iterator1.next();
                                  System.out.println(" Inside Gp : recordsRow[0] : "+recordsRow[0]+" recordsRow[1] :"+recordsRow[1]+" recordsRow[2] :"+recordsRow[2]);
                                  if(recordsRow[0]!=null&&recordsRow[1]!=null&&recordsRow[2]!=null)
                                  {
                                 /* expenseDate = new Date();
                                  expenseDate
                                                .setYear(Integer.parseInt(recordsRow[2].toString()) - 1900);
                                  expenseDate.setMonth(Integer.parseInt(recordsRow[1]
                                                .toString()) - 1);
                                  expenseDate.setDate(1);*/
                                    String dateString=recordsRow[1].toString().substring(0, 3)+","+recordsRow[2].toString();
                                  gpMonthly.put(dateString,
                                               Double.parseDouble(recordsRow[0].toString()));
                                  System.out.println(" record length:" + recordsRow.length);
                                  for (int i = 0; i < recordsRow.length; i++) {
                                         System.out.print("\t" + recordsRow[i]);
                                  }
                                  }
                           }
                     }
              }
              catch (Exception e) {
                     System.out.println("Inside exception  " + e.getLocalizedMessage());
                     e.printStackTrace();
              }
              System.out.println(" Monthly gpMonthly : "+gpMonthly);
             return gpMonthly;
       }
       public long getTotalCustomer(Session session, int sellerId)
       {
              int totalCustomer = 0;
              List<Integer> results = null;
              try
              {
                     session.beginTransaction();
                     Criteria criteria = session.createCriteria(Customer.class)
                     .add(Restrictions.eq("sellerId", sellerId));
                     criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
                     ProjectionList projList = Projections.projectionList();
                    projList.add(Projections.countDistinct("customerEmail"));
                     criteria.setProjection(projList);
                     results = criteria.list();
                     if(results!=null&&results.size()!=0&&results.get(0)!=null)
                     {
                         totalCustomer = results.get(0);
                         // totalCustomer=countBigInt.longValue();
                     }
                     System.out.println(" Total Customer : "+totalCustomer);
              }
              catch (Exception e) {
                     System.out.println("Inside exception getTotalCustomer " + e.getLocalizedMessage());
                     e.printStackTrace();
              }
              return totalCustomer;
       }
       public long getTotalSKUCount(Session session, int sellerId)
       {
              long totalSkuCount = 0;
              List<Long> results = null;
              try
              {
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
                    if(results!=null&&results.size()!=0&&results.get(0)!=null)
                     {
                          totalSkuCount= results.get(0);
                     }
                     System.out.println("totalSkuCount : "+totalSkuCount);
              }
              catch (Exception e) {
                     System.out.println("Inside exception totalSkuCount " + e.getLocalizedMessage());
                     e.printStackTrace();
              }
              return totalSkuCount;
       }
       public List<TaxDetail> getTaxAlert(Session session, Date taxDate,
                     int sellerId)
       {
    	   List<TaxDetail> taxList=null;
		try {
			taxList = taxDetailService.listTaxDetails(sellerId, "Tax");
			System.out.println(" Getting tax Lsit");
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
             /* Seller seller = null;
              List<TaxDetail> taxList = null;
              try
              {
                     session.beginTransaction();
                     Criteria criteria = session.createCriteria(Seller.class).add(
                                  Restrictions.eq("id", sellerId));
                     criteria.createAlias("taxDetails", "taxDetail",
                                  CriteriaSpecification.LEFT_JOIN)
                     .add(Restrictions.ge("taxDetail.uploadDate", taxDate))
                     .add(Restrictions.eq("taxDetail.taxortds", "Tax"));
                     criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
                     criteria.addOrder(org.hibernate.criterion.Order.desc("taxDetail.uploadDate"));
                     if (criteria.list() != null&&criteria.list().size()!=0)
                     {
                           seller = (Seller) criteria.list().get(0);
                     taxList = seller.getTaxDetails();
                     }
              }
              catch (Exception e) {
                     System.out.println("Inside exception getTax Alert " + e.getLocalizedMessage());
                     e.printStackTrace();
              }*/
              return taxList;
       }
       public List<TaxDetail> getTDSAlert(Session session, Date taxDate,
                     int sellerId)
       {
              Seller seller = null;
              List<TaxDetail> taxList = null;
              try
              {
                     session.beginTransaction();
                     Criteria criteria = session.createCriteria(Seller.class).add(
                                  Restrictions.eq("id", sellerId));
                     criteria.createAlias("taxDetails", "taxDetail",
                                  CriteriaSpecification.LEFT_JOIN)
                     .add(Restrictions.ge("taxDetail.uploadDate", taxDate))
                     .add(Restrictions.eq("taxDetail.taxortds", "TDS"));
                     criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
                     criteria.addOrder(org.hibernate.criterion.Order.desc("taxDetail.uploadDate"));
                     if (criteria.list() != null&&criteria.list().size()!=0)
                     {
                           seller = (Seller) criteria.list().get(0);
                     taxList = seller.getTaxDetails();
                     }
              }
              catch (Exception e) {
                     System.out.println("Inside exception  getTDSAlert " + e.getLocalizedMessage());
                     e.printStackTrace();
              }
              return taxList;
       }
       public Map<String,Double> expenditureMonthly(Session session, Date startDate,
               Date endDate, int sellerId)
{
        System.out.println(" Inside for six months expenditureMonthly ");
        // Seller seller=null;
        List<Object[]> results = null;
        Map<String,Double> expenseMonthly=new LinkedHashMap<>();
        try
        {
               session.beginTransaction();
               Query query = session.createSQLQuery(expenditureMonthlyQuery)
               .setParameter("startDate", startDate)
               .setParameter("endDate", endDate)
               .setParameter("sellerId", sellerId);
               results = query.list();
               Iterator iterator1 = results.iterator();
               if (results != null) {
                     while (iterator1.hasNext()) {
                            System.out.println("Expense : row\n");
                            Object[] recordsRow = (Object[]) iterator1.next();
                            System.out.println("Expense  monhtly : \n "+recordsRow[0]+" recordsRow[1] "+recordsRow[1]+" recordsRow[2] "+recordsRow[2]);
                            if(recordsRow[0]!=null&&recordsRow[1]!=null&&recordsRow[2]!=null)
                            {
                          /*  expenseDate = new Date();
                            expenseDate
                                          .setYear(Integer.parseInt(recordsRow[2].toString()) - 1900);
                            expenseDate.setMonth(Integer.parseInt(recordsRow[1]
                                          .toString()) - 1);
                            expenseDate.setDate(1);
*/
                                   String dateString=recordsRow[1].toString().substring(0, 3)+","+recordsRow[2].toString();
                                  expenseMonthly.put(dateString,
                                          Double.parseDouble(recordsRow[0].toString()));
                            System.out.println("Expenses" + recordsRow.length);
                            for (int i = 0; i < recordsRow.length; i++) {
                                   System.out.print("\t" + recordsRow[i]);
                                    }
                            }
                     }
               }
        }
        catch (Exception e) {
               System.out.println("Inside expeses mnthly exception  " + e.getLocalizedMessage());
               e.printStackTrace();
        }
System.out.println(" Monthly Expense : "+expenseMonthly);
        return expenseMonthly;
}
       public Map<String,Long> topSellingRegion(Session session, Date startDate,
                     Date endDate, int sellerId)
       {
              // NP=NSR+Valuation of closing stock -Openign stock value -expense
              List<Object[]> results = null;
              Map<String,Long> topSellingRegion =new LinkedHashMap<>();
              try
              {
                     // session=sessionFactory.openSession();
                     session.beginTransaction();
                     Criteria criteria = session.createCriteria(Order.class);
                     criteria.createAlias("seller", "seller",
                                 CriteriaSpecification.LEFT_JOIN);
                     criteria.createAlias("customer", "customer",
                                  CriteriaSpecification.LEFT_JOIN)
                     .add(Restrictions.eq("seller.id", sellerId))
                     .add(Restrictions.isNotNull("customer.customerCity"))
                     .add(Restrictions.ne("customer.customerCity", ""))
                     .add(Restrictions.between("orderDate", startDate, endDate));
                     criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
                     ProjectionList projList = Projections.projectionList();
                     projList.add(Projections.rowCount(),"count");
                     projList.add(Projections.groupProperty("customer.customerCity"));
                     criteria.setProjection(projList);
                     criteria.addOrder(org.hibernate.criterion.Order.desc("count"));
                     results = criteria.list();
                     Iterator iterator1 = results.iterator();
                     if (results != null) {
                           while (iterator1.hasNext()) {
                                  System.out.println("\n");
                                  Object[] recordsRow = (Object[]) iterator1.next();
                                  if(recordsRow[0]!=null&&recordsRow[1]!=null)
                                  {
                                  topSellingRegion.put(recordsRow[1].toString(), Long.parseLong(recordsRow[0].toString()));
                                  System.out.println(" record length:" + recordsRow.length);
                                  for (int i = 0; i < recordsRow.length; i++) {
                                         System.out.print("\t" + recordsRow[i]);
                                    }
                                  }
                           }
                     }
                     // session.getTransaction().commit();
                     // session.close();
              }
              catch (Exception e) {
                     System.out.println("Inside exception  topSellingRegion " + e.getLocalizedMessage());
                     e.printStackTrace();
              }
              return topSellingRegion;
       }
       public Map<String,Long> topSellingSKU(Session session, Date startDate,
                     Date endDate, int sellerId)
       {
              // NP=NSR+Valuation of closing stock -Openign stock value -expense
              List<Object[]> results = null;
              Map<String,Long> topSKU=new LinkedHashMap<String,Long>();
              try
              {
                     // session=sessionFactory.openSession();
                     session.beginTransaction();
                     Criteria criteria = session.createCriteria(Order.class);
                     criteria.createAlias("seller", "seller",
                                  CriteriaSpecification.LEFT_JOIN)
                     .add(Restrictions.eq("seller.id", sellerId))
                     .add(Restrictions.between("orderDate", startDate, endDate));
                     criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
                     ProjectionList projList = Projections.projectionList();
                     projList.add(Projections.groupProperty("productSkuCode"));
                     projList.add(Projections.sum("quantity"),"sum");
                     criteria.setProjection(projList);
                     criteria.addOrder(org.hibernate.criterion.Order.desc("sum"));
                     results = criteria.list();
                     Iterator iterator1 = results.iterator();
                     if (results != null) {
                           while (iterator1.hasNext()) {
                                  System.out.println("\n");
                                  Object[] recordsRow = (Object[]) iterator1.next();
                                  if(recordsRow[0]!=null&&recordsRow[1]!=null)
                                  {
                                  System.out.println("Top selling sku:" + recordsRow.length);
                                  System.out.println(" record 1 : "+recordsRow[0].toString()+" record 2 : "+recordsRow[1].toString());
                                  topSKU.put(recordsRow[0].toString(), Long.parseLong(recordsRow[1].toString()));
                                  for (int i = 0; i < recordsRow.length; i++) {
                                         System.out.print("\t" + recordsRow[i]);
                                    }
                                  }
                           }
                     }
                     // session.getTransaction().commit();
                     // session.close();
              }
              catch (Exception e) {
                     System.out.println("Inside exception  " + e.getLocalizedMessage());
                     e.printStackTrace();
              }
              return topSKU;
       }
       public Map<String,Double> expenseGroupByCatForTime(Session session,
                     Date startDate, Date endDate, int sellerId)
       {
              List<Object[]> results = null;
              Map<String,Double> expenseCatWise=new LinkedHashMap<String, Double>();
              try
              {
                     Criteria criteria = session.createCriteria(Expenses.class)
                     .add(Restrictions.eq("sellerId", sellerId))
                     .add(Restrictions.between("expenseDate", startDate, endDate));
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
                                  if(recordsRow[0]!=null&&recordsRow[1]!=null)
                                  {
                                  expenseCatWise.put(recordsRow[1].toString(), Double.parseDouble(recordsRow[0].toString()));
                                  System.out.println("Expense sthis month :" + recordsRow.length);
                                  for (int i = 0; i < recordsRow.length; i++) {
                                         System.out.print("\t" + recordsRow[i]);
                                    }
                                  }
                           }
                     }
              }
              catch (Exception e) {
                     System.out.println("Inside exception expenseGroupByCatForTime  " + e.getLocalizedMessage());
                     e.printStackTrace();
              }
              System.out.println(" expenseCatWise : "+expenseCatWise);
              return expenseCatWise;
       }
       public double totalExpenseForTime(Session session, Date startDate,
                     Date endDate, int sellerId)
       {
              List<Object> results = null;
              double totalExpenses = 0;
              try
              {
                     session.beginTransaction();
                     Criteria criteria = session.createCriteria(Expenses.class)
                     .add(Restrictions.eq("sellerId", sellerId))
                     .add(Restrictions.between("expenseDate", startDate, endDate));
                     criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
                     ProjectionList projList = Projections.projectionList();
                     projList.add(Projections.sum("amount"));
                     criteria.setProjection(projList);
                     results = criteria.list();
                     if (results != null && results.size() != 0&& results.get(0)!=null)
                           totalExpenses = Double.parseDouble(results.get(0).toString());
                     System.out.println(" totalExpenses : "+totalExpenses);
              }
              catch (Exception e) {
                     System.out.println("Inside exception totalExpenseForTime " + e.getLocalizedMessage());
                     e.printStackTrace();
              }
              return totalExpenses;
       }
       public Map<Date, Long> orderCountMonthly(Session session, Date startDate,
                     Date endDate, int sellerId)
       {
              System.out.println(" Inside for six months ");
              List<Object[]> results = null;
              Map<Date, Long> orderCount = new LinkedHashMap<>();
              Date orderDate = null;
              try
              {
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
                                  System.out.println(" record length:" + recordsRow.length);
                                  if(recordsRow[0]!=null&&recordsRow[1]!=null&&recordsRow[2]!=null)
                                  {
                                  orderDate = new Date();
                                  orderDate
                                                .setYear(Integer.parseInt(recordsRow[2].toString()) - 1900);
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
              }
              catch (Exception e) {
                     System.out.println("Inside exception  " + e.getLocalizedMessage());
                     e.printStackTrace();
              }
              return orderCount;
       }
       public Map<Date, Long> paymentCountMonthly(Session session, Date startDate,
                     Date endDate, int sellerId)
       {
              System.out.println(" Inside for six months ");
              // Seller seller=null;
              List<Object[]> results = null;
              Map<Date, Long> paymentCount = new LinkedHashMap<>();
              Date paymentDate = null;
              try
              {
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
                                  System.out.println(" record length:" + recordsRow.length);
                                  if(recordsRow[0]!=null&&recordsRow[1]!=null&&recordsRow[2]!=null)
                                  {
                                  paymentDate = new Date();
                                  paymentDate.setYear(Integer.parseInt(recordsRow[2]
                                                .toString()) - 1900);
                                  paymentDate.setMonth(Integer.parseInt(recordsRow[1]
                                                .toString()) - 1);
                                  paymentDate.setDate(1);
                                  paymentCount.put(paymentDate,
                                                Long.parseLong(recordsRow[0].toString()));
                                  for (int i = 0; i < recordsRow.length; i++) {
                                         System.out.print("\t" + recordsRow[i]);
                                    }
                                  }
                           }
                     }
              }
              catch (Exception e) {
                     System.out.println("Inside paymentCount exception  "
                                  + e.getLocalizedMessage());
                     e.printStackTrace();
              }
              return paymentCount;
       }
       public long countForDuration(Session session, Date startDate, Date endDate,
                     int sellerId, String querry)
       {
              System.out.println(" Inside for countForDuration ");
              List<java.math.BigInteger> results = null;
              long count = 0;
              try
              {
                     session.beginTransaction();
                     Query query = session.createSQLQuery(querry)
                     .setParameter("startDate", startDate)
                     .setParameter("endDate", endDate)
                     .setParameter("sellerId", sellerId);
                     results = query.list();
                     if (results != null && results.size() != 0 &&results.get(0)!=null)
                     {
                           java.math.BigInteger countBigInt = results.get(0);
                           System.out.println(results.get(0));
                           System.out.println("BigInt to logn " + countBigInt.longValue());
                           count = countBigInt.longValue();
                           // count=Long.parseLong(results.get(0)[0]);
                     }
              }
              catch (Exception e) {
                     System.out.println("Inside pcount exception  "
                                  + e.getLocalizedMessage());
                     e.printStackTrace();
              }
              return count;
       }
       public double amountForDuration(Session session, Date startDate, Date endDate,
                     int sellerId, String querry)
       {
              System.out.println(" Inside for countForDuration ");
              List<Double> results = null;
              double sum = 0;
              try
              {
                     session.beginTransaction();
                     Query query = session.createSQLQuery(querry)
                     .setParameter("startDate", startDate)
                     .setParameter("endDate", endDate)
                     .setParameter("sellerId", sellerId);
                     results = query.list();
                     if (results != null && results.size() != 0&&results.get(0)!=null)
                     {
                           sum = results.get(0);
                           System.out.println(results.get(0));
                           System.out.println("Double" + sum);
                     }
              }
              catch (Exception e) {
                     System.out.println("Inside pcount exception  "
                                  + e.getLocalizedMessage());
                     e.printStackTrace();
              }
              return sum;
       }
       public List<Object> orderNRQuantityMonthly(Session session,
                     Date startDate, Date endDate, int sellerId)
       {
              System.out.println(" Inside for six months ");
              // Seller seller=null;
              List<Object[]> results = null;
             List<Object> returnList=new ArrayList<Object>();
              Map<String,Double> nrMAp=new LinkedHashMap<String, Double>();
              Map<String,Long> quantityMAp=new LinkedHashMap<String, Long>();
             // Date orderDate =new Date();
              try
              {
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
                                  System.out.println(" record length:" + recordsRow.length);
                                  if(recordsRow[0]!=null&&recordsRow[1]!=null&&recordsRow[2]!=null&&recordsRow[3]!=null)
                                  {
                                 /* orderDate = new Date();
                                  orderDate.setYear(Integer.parseInt(recordsRow[3].toString()) - 1900);
                                  orderDate.setMonth(Integer.parseInt(recordsRow[2].toString()) - 1);
                                  orderDate.setDate(1);
*/
                               String dateString=recordsRow[2].toString().substring(0, 3)+","+recordsRow[3].toString();
                                  nrMAp.put(dateString,
                                                Double.parseDouble(recordsRow[0].toString()));
                                  quantityMAp.put(dateString,
                                          Long.parseLong(recordsRow[1].toString()));
                                }
                           }
                     }
                     System.out.println(" NR monthly nrMAp "+nrMAp);
                     System.out.println(" quantityMAp  "+quantityMAp);
                    returnList.add(nrMAp);
                    returnList.add(quantityMAp);
              }
              catch (Exception e) {
                     System.out.println("Inside exception  " + e.getLocalizedMessage());
                     e.printStackTrace();
              }
              return returnList;
       }
       public List<Object> returnNRQuantityMonthly(Session session,
                     Date startDate, Date endDate, int sellerId)
       {
              System.out.println(" Inside for six months ");
              // Seller seller=null;
              List<Object[]> results = null;
              List<Object> returnList=new ArrayList<Object>();
              Map<String,Double> returnAMountMAp=new LinkedHashMap<String, Double>();
              Map<String,Long> quantityMAp=new LinkedHashMap<String, Long>();
             // Date orderDate =new Date();
              System.out.println(" returnList status :"+returnList);
              try
              {
                     session = sessionFactory.openSession();
                     session.beginTransaction();
                     Query query = session.createSQLQuery(returnNRMonthlyQuery)
                     .setParameter("startDate", startDate)
                     .setParameter("endDate", endDate)
                     .setParameter("sellerId", sellerId);
                     results = query.list();
                     Iterator iterator1 = results.iterator();
                     if (results != null) {
                           while (iterator1.hasNext()) {
                                  System.out.println("Expense : row\n");
                                  Object[] recordsRow = (Object[]) iterator1.next();
                                  if(recordsRow[0]!=null&&recordsRow[1]!=null&&recordsRow[2]!=null&&recordsRow[3]!=null)
                                  {
                                  System.out.println(" record length:" + recordsRow.length);
                                /*  orderDate.setYear(Integer.parseInt(recordsRow[3].toString()) - 1900);
                                  orderDate.setMonth(Integer.parseInt(recordsRow[2].toString()) - 1);
                                  orderDate.setDate(1);*/
                                  String dateString=recordsRow[2].toString().substring(0, 3)+","+recordsRow[3].toString();
                                  returnAMountMAp.put(dateString,
                                                Double.parseDouble(recordsRow[0].toString()));
                                  quantityMAp.put(dateString,
                                          Long.parseLong(recordsRow[1].toString()));
                                  for (int i = 0; i < recordsRow.length; i++) {
                                         System.out.print("\t" + recordsRow[i]);
                                    }
                                  }
                           }
                     }
              }
              catch (Exception e) {
                     System.out.println("Inside exception  " + e.getLocalizedMessage());
                     e.printStackTrace();
              }
              return returnList;
       }
}
