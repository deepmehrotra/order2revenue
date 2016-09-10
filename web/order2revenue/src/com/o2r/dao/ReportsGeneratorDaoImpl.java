package com.o2r.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.o2r.bean.ChannelCatNPR;
import com.o2r.bean.ChannelGP;
import com.o2r.bean.ChannelMC;
import com.o2r.bean.ChannelMCNPR;
import com.o2r.bean.ChannelNPR;
import com.o2r.bean.ChannelNR;
import com.o2r.bean.ChannelNetQty;
import com.o2r.bean.ChannelReportDetails;
import com.o2r.bean.CommissionDetails;
import com.o2r.bean.ConsolidatedOrderBean;
import com.o2r.bean.DataConfig;
import com.o2r.bean.MonthlyCommission;
import com.o2r.bean.NetPaymentResult;
import com.o2r.bean.PartnerReportDetails;
import com.o2r.bean.TotalShippedOrder;
import com.o2r.bean.YearlyStockList;
import com.o2r.helper.ConverterClass;
import com.o2r.helper.CustomException;
import com.o2r.helper.GlobalConstant;
import com.o2r.model.Customer;
import com.o2r.model.Order;
import com.o2r.model.OrderPayment;
import com.o2r.model.OrderRTOorReturn;
import com.o2r.model.OrderTax;
import com.o2r.model.Product;
import com.o2r.model.Seller;
import com.o2r.model.TaxCategory;
import com.o2r.model.UploadReport;
import com.o2r.service.OrderService;
import com.o2r.service.TaxDetailService;

/**
 * @author Deep Mehrotra
 *
 */

@Repository("reportGeneratorDao")
public class ReportsGeneratorDaoImpl implements ReportsGeneratorDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private DataConfig dataConfig;
	
	@Autowired
	private OrderService orderService;
	
	@Resource(name="taxDetailService")
	private TaxDetailService taxDetailService;

	static Logger log = Logger.getLogger(ReportsGeneratorDaoImpl.class.getName());

	@SuppressWarnings("unchecked")
	@Override
	public TotalShippedOrder getPartnerTSOdetails(String pcName,
			Date startDate, Date endDate, int sellerId) throws CustomException {
		
		log.info("*** getPartnerTSOdetails Starts : ReportsGeneratorDaoImpl ****");
		log.debug(" getPartnerTSOdetails   : pcName :" + pcName
				+ " >>startDate" + startDate + ">>endDate :" + endDate);
		TotalShippedOrder ttso = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Order.class);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.eq("pcName", pcName))
					.add(Restrictions.between("orderDate", startDate, endDate));
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			ProjectionList projList = Projections.projectionList();
			projList.add(Projections.sum("quantity"));
			projList.add(Projections.sum("netRate"));
			criteria.setProjection(projList);
			List<Object[]> results = criteria.list();
			Iterator iterator1 = results.iterator();
			if (results != null) {
				while (iterator1.hasNext()) {
					System.out.println("\n");
					Object[] recordsRow = (Object[]) iterator1.next();
					log.debug(" record length:" + recordsRow.length);
					for (int i = 0; i < recordsRow.length; i++) {
						System.out.print("\t" + recordsRow[i]);

					}
				}
			}

			session.getTransaction().commit();
			session.close();
		} catch (NullPointerException e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : "+sellerId,e);					
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : "+sellerId,e);
			throw new CustomException(GlobalConstant.getPartnerTSOdetailsError,
					new Date(), 3,
					GlobalConstant.getPartnerTSOdetailsErrorCode, e);			
		}
		log.info("*** getPartnerTSOdetails ends : ReportsGeneratorDaoImpl ****");
		return ttso;
	}
	
	
	
	
	@Override
	public List<TotalShippedOrder> getAllPartnerTSOdetails(Date startDate,
			Date endDate, int sellerId) throws CustomException {

		log.info("*** getAllPartnerTSOdetails Starts : ReportsGeneratorDaoImpl ****");
		TotalShippedOrder[] ttso = null;

		int totalQuantity = 0;
		int returnQuantity = 0;
		double totalNR = 0;
		double returnAmount = 0;
		double netSaleAmoount = 0;
		int cityTotalQuantity = 0;
		int totalNoofDO = 0;
		int totalNoofRO = 0;
		int totalNoofSO = 0;
		int totalNoofAO = 0;
		int totalNoofRTOCross = 0;
		int totalNoofreturnCross = 0;
		int iteratorCount = 0;
		TotalShippedOrder ttsoTemp = null;
		Map<String, Double> cityMap = new HashMap<>();
		Map<String, Double> cityPercentMap = new HashMap<>();
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			
			Criteria criteria = session.createCriteria(Order.class);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("customer", "customer",
					CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("orderPayment", "orderPayment",
					CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("orderReturnOrRTO", "orderReturnOrRTO",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.between("orderDate", startDate, endDate));
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			ProjectionList projList = Projections.projectionList();
			projList.add(Projections.groupProperty("pcName"));
			projList.add(Projections.sum("quantity"));
			projList.add(Projections.sum("netRate"));
			projList.add(Projections.sum("orderReturnOrRTO.returnorrtoQty"));
			projList.add(Projections.sum("orderPayment.negativeAmount"));
			projList.add(Projections.sum("orderSP"));
			projList.add(Projections.sum("orderPayment.positiveAmount"));
			criteria.setProjection(projList);
			criteria.addOrder(org.hibernate.criterion.Order.asc("pcName"));
			
			
			try
			{
				List<Object[]> results = criteria.list();
				/*log.debug("results.size()  " + results.size());*/
				ttso = new TotalShippedOrder[results.size()];

				Iterator iterator1 = results.iterator();
			if (results != null) {
				while (iterator1.hasNext()) {
					System.out.println("\n");
					ttsoTemp = new TotalShippedOrder();
					Object[] recordsRow = (Object[]) iterator1.next();
					/*log.debug("recordsRow.length  "+ recordsRow.length);*/
					log.debug(" Quantity  NR ReturnQty  Return Amount SaleAmount  PositiveAmount");
					for (int i = 0; i < recordsRow.length; i++) {
						if (i == 0) {
							log.debug(" Setting pc name :"
									+ recordsRow[i].toString());
							
							ttsoTemp.setPcName(recordsRow[i].toString());
							log.debug(" After setting pc name = "
									+ ttsoTemp.getPcName());
						}
						if (i == 1) {
							ttsoTemp.setSaleQuantity(Integer
									.parseInt(recordsRow[i].toString()));
							totalQuantity = totalQuantity
									+ Integer
											.parseInt(recordsRow[i].toString());
						} else if (i == 2) {
							ttsoTemp.setNr(Double.parseDouble(recordsRow[i]
									.toString()));
							totalNR = totalNR
									+ Double.parseDouble(recordsRow[i]
											.toString());
						} else if (i == 3) {
							ttsoTemp.setReturnQuantity(Integer
									.parseInt(recordsRow[i].toString()));
							returnQuantity = returnQuantity
									+ Integer
											.parseInt(recordsRow[i].toString());
						} else if (i == 4) {
							ttsoTemp.setReturnAmount(Double
									.parseDouble(recordsRow[i].toString()));
							returnAmount = returnAmount
									+ Double.parseDouble(recordsRow[i]
											.toString());
						} else if (i == 5) {
							ttsoTemp.setNetSaleAmount(Double
									.parseDouble(recordsRow[i].toString()));
							netSaleAmoount = netSaleAmoount
									+ Double.parseDouble(recordsRow[i]
											.toString());
						}

						log.debug("\t" + recordsRow[i]);

					}
					ttso[iteratorCount] = ttsoTemp;
					iteratorCount++;
				}
			}
			}catch(Exception e)
			{
				log.info("Failed! Error in getting "+e.getMessage());
			}
			Criteria criteriaforDeliveredOrder = session.createCriteria(Order.class);
			criteriaforDeliveredOrder
					.createAlias("seller", "seller",
							CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.between("orderDate", startDate, endDate))
					.add(Restrictions.le("deliveryDate", new Date()));
			criteriaforDeliveredOrder
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			ProjectionList DOprojList = Projections.projectionList();
			DOprojList.add(Projections.groupProperty("pcName"));
			DOprojList.add(Projections.rowCount());
			criteriaforDeliveredOrder.setProjection(DOprojList);
			// Object deliveredOrderCount =
			// criteriaforDeliveredOrder.list().get(0);
			try
			{
			List<Object[]> deliveredOrderCount = criteriaforDeliveredOrder.list();
			Iterator DOiterator = deliveredOrderCount.iterator();
			if (deliveredOrderCount != null) {
				iteratorCount = 0;
				while (DOiterator.hasNext()) {
					System.out.println("\n");
					Object[] recordsRow = (Object[]) DOiterator.next();
					if(recordsRow[1]!=null)
					{
					totalNoofDO = totalNoofDO
							+ Integer.parseInt(recordsRow[1].toString());
					ttso[iteratorCount++].setNoOfDeliveredOrder(Integer
							.parseInt(recordsRow[1].toString()));
					}
				}
			}
			}catch(Exception e)
			{
				log.info("Failed! Error in getting Delivered Order Count"+e.getMessage());
			}
			Criteria criteriaforReturnOrder = session
					.createCriteria(Order.class);
			criteriaforReturnOrder.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN);
			criteriaforReturnOrder
					.createAlias("orderReturnOrRTO", "orderReturnOrRTO",
							CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.between("orderDate", startDate, endDate))
					.add(Restrictions
							.isNotNull("orderReturnOrRTO.returnOrRTOId"));
			criteriaforReturnOrder
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			ProjectionList ROprojList = Projections.projectionList();
			ROprojList.add(Projections.groupProperty("pcName"));
			ROprojList.add(Projections.rowCount());
			criteriaforReturnOrder.setProjection(ROprojList);
			try
			{
			List<Object[]> returnOrderCount = criteriaforReturnOrder.list();
			Iterator ROiterator = returnOrderCount.iterator();
			if (returnOrderCount != null) {
				iteratorCount = 0;
				while (ROiterator.hasNext()) {
					Object[] recordsRow = (Object[]) ROiterator.next();
					if(recordsRow[1]!=null)
					{
					totalNoofRO = totalNoofRO
							+ Integer.parseInt(recordsRow[1].toString());
					ttso[iteratorCount++].setNoOfReturnOrder(Integer
							.parseInt(recordsRow[1].toString()));
					}

				}
			}
			}catch(Exception e)
			{
				log.info("Failed! Error in getting Return Order Count"+e.getMessage());
			}
			/*
			 * Code for caluclating no of actionalble orders
			 */
			Criteria criteriaforActionalbleOrder = session
					.createCriteria(Order.class);
			criteriaforActionalbleOrder
					.createAlias("seller", "seller",
							CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.between("orderDate", startDate, endDate))
					.add(Restrictions.eq("finalStatus", "Actionable"));
			criteriaforActionalbleOrder
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			ProjectionList AOprojList = Projections.projectionList();
			AOprojList.add(Projections.groupProperty("pcName"));
			AOprojList.add(Projections.rowCount());
			criteriaforActionalbleOrder.setProjection(AOprojList);
			try
			{
			List<Object[]> actionableOrderCount = criteriaforActionalbleOrder.list();
			Iterator AOiterator = actionableOrderCount.iterator();
			if (actionableOrderCount != null) {
				iteratorCount = 0;
				while (AOiterator.hasNext()) {
					Object[] recordsRow = (Object[]) AOiterator.next();
					if(recordsRow[1]!=null)
					{
					totalNoofAO = totalNoofAO
							+ Integer.parseInt(recordsRow[1].toString());
					ttso[iteratorCount++].setNoOfActionableOrders(Integer
							.parseInt(recordsRow[1].toString()));
					}

				}
			}
			}catch(Exception e)
			{
				log.info("Failed! Error in getting Actionable Order Count"+e.getMessage());
			}

			/*
			 * Code for caluclating no of settled orders
			 */
			Criteria criteriaforSettledOrder = session
					.createCriteria(Order.class);
			criteriaforSettledOrder
					.createAlias("seller", "seller",
							CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.between("orderDate", startDate, endDate))
					.add(Restrictions.eq("finalStatus", "Settled"));
			criteriaforSettledOrder
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			ProjectionList SOprojList = Projections.projectionList();
			SOprojList.add(Projections.groupProperty("pcName"));
			SOprojList.add(Projections.rowCount());
			criteriaforSettledOrder.setProjection(SOprojList);

			try
			{
			List<Object[]> settledOrderCount = criteriaforSettledOrder.list();
			Iterator SOiterator = settledOrderCount.iterator();
			if (settledOrderCount != null) {
				iteratorCount = 0;
				while (SOiterator.hasNext()) {
					Object[] recordsRow = (Object[]) SOiterator.next();
					if(recordsRow[1]!=null)
					{
					totalNoofSO = totalNoofSO
							+ Integer.parseInt(recordsRow[1].toString());
					ttso[iteratorCount++].setNoOfSettledOrders(Integer
							.parseInt(recordsRow[1].toString()));
					}

				}
			}
			}catch(Exception e)
			{
				log.info("Failed! Error in getting Settled Order Count"+e.getMessage());
			}

			/*
			 * Code for caluclating no of RTOlimit crossed orders
			 */
			Criteria criteriaRTOlimitCross = session
					.createCriteria(Order.class);
			criteriaRTOlimitCross.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN);
			criteriaRTOlimitCross
					.createAlias("orderReturnOrRTO", "orderReturnOrRTO",
							CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.geProperty("orderReturnOrRTO.returnDate",
							"rTOLimitCrossed"));
			criteriaRTOlimitCross
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			ProjectionList RTOprojList = Projections.projectionList();
			RTOprojList.add(Projections.groupProperty("pcName"));
			RTOprojList.add(Projections.rowCount());
			criteriaRTOlimitCross.setProjection(RTOprojList);
			try
			{
			List<Object[]> RTOlimitCrossOrderCount = criteriaRTOlimitCross.list();
			Iterator rtoOiterator = RTOlimitCrossOrderCount.iterator();
			if (RTOlimitCrossOrderCount != null) {
				iteratorCount = 0;
				while (rtoOiterator.hasNext()) {
					Object[] recordsRow = (Object[]) rtoOiterator.next();
					if(recordsRow[1]!=null)
					{
					totalNoofRTOCross = totalNoofRTOCross
							+ Integer.parseInt(recordsRow[1].toString());
					ttso[iteratorCount++].setNoOfRTOLimitCrossed(Integer
							.parseInt(recordsRow[1].toString()));
					}

				}
			}
			}catch(Exception e)
			{
				log.info("Failed! Error in getting RTO Limit Crossed Order Count"+e.getMessage());
			}

			/*
			 * Code for caluclating no of retunrLimit crossed orders
			 */
			Criteria criteriaReturnlimitCross = session
					.createCriteria(Order.class);
			criteriaReturnlimitCross.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN);
			criteriaReturnlimitCross
					.createAlias("orderReturnOrRTO", "orderReturnOrRTO",
							CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.geProperty("orderReturnOrRTO.returnDate",
							"returnLimitCrossed"));
			criteriaReturnlimitCross
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			ProjectionList returnprojList = Projections.projectionList();
			returnprojList.add(Projections.groupProperty("pcName"));
			returnprojList.add(Projections.rowCount());
			criteriaReturnlimitCross.setProjection(returnprojList);
			try
			{
			List<Object[]> returnlimitCrossOrderCount = criteriaReturnlimitCross.list();
			Iterator returniterator = returnlimitCrossOrderCount.iterator();
			if (returnlimitCrossOrderCount != null) {
				while (returniterator.hasNext()) {
					iteratorCount = 0;
					Object[] recordsRow = (Object[]) returniterator.next();
					if(recordsRow[1]!=null)
					{
					totalNoofreturnCross = totalNoofreturnCross
							+ Integer.parseInt(recordsRow[1].toString());
					ttso[iteratorCount++].setNoOfReturnLimitCrossed(Integer
							.parseInt(recordsRow[1].toString()));
					}

				}
			}
			}catch(Exception e)
			{
				log.info("Failed! Error in getting Return Limit Order Count"+e.getMessage());
			}
			/*
			 * Code for caluclating cities wise distribution of orders
			 */
			Criteria criteriaforCitiesOrder = session
					.createCriteria(Order.class);
			criteriaforCitiesOrder.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN);
			criteriaforCitiesOrder
					.createAlias("customer", "customer",
							CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.between("orderDate", startDate, endDate))
					.add(Restrictions.isNotNull("customer.customerCity"))
					.add(Restrictions.ne("customer.customerCity", ""));
			criteriaforCitiesOrder
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			ProjectionList ccprojList = Projections.projectionList();
			ccprojList.add(Projections.groupProperty("customer.customerCity"));
			ccprojList.add(Projections.rowCount());
			criteriaforCitiesOrder.setProjection(ccprojList);
			try
			{
			List<Object[]> cityresults = criteriaforCitiesOrder.list();
			Iterator cityIterator = cityresults.iterator();
			if (cityresults != null) {
				while (cityIterator.hasNext()) {
					
					Object[] recordsRow = (Object[]) cityIterator.next();
					log.debug(" Cities row length "+ recordsRow.length);
					if (recordsRow.length > 0&&recordsRow[0]!=null&&recordsRow[1]!=null) {
						if (!cityMap.containsKey(recordsRow[0].toString())) {
							cityMap.put(recordsRow[0].toString(), Double
									.parseDouble(recordsRow[1].toString()));
						}
						//log.debug("city " + recordsRow[0]+ " count : " + recordsRow[1]);
						cityTotalQuantity = cityTotalQuantity
								+ Integer.parseInt(recordsRow[1].toString());
					}

				}
			}
			}catch(Exception e)
			{
				log.info("Failed! Error in getting City wise Order Count"+e.getMessage());
			}
			log.debug(" ***cityTotalQuantity " + cityTotalQuantity);
			if(ttso!=null&&ttso.length!=0)
			{
			ttso[0].setCityQuantity(cityMap);
			for (Map.Entry<String, Double> entry : cityMap.entrySet()) {
				/*log.debug("for city : " + entry.getKey()
						+ " count is :" + entry.getValue() + " percent is :"
						+ (entry.getValue()) / cityTotalQuantity * 100);*/
				cityPercentMap.put(entry.getKey(), (entry.getValue())
						/ cityTotalQuantity * 100);
			}

			ttso[0].setCityQuantity(cityMap);
			ttso[0].setCityPercentage(cityPercentMap);

			for (int i = 0; i < ttso.length; i++) {
				ttso[i].setSaleQuantityPercent(ttso[i].getSaleQuantity() * 100
						/ totalQuantity);
				if ((int) returnQuantity != 0)
					ttso[i].setReturnQuantityPercent(ttso[i]
							.getReturnQuantity() * 100 / returnQuantity);
				ttso[i].setNrPercent(ttso[i].getNr() * 100 / totalNR);
				if ((int) returnAmount != 0)
					ttso[i].setReturnAmountPercent(ttso[i].getReturnAmount()
							* 100 / returnAmount);
				if ((int) netSaleAmoount != 0)
					ttso[i].setNetSaleAmountPercent(ttso[i].getNetSaleAmount()
							* 100 / netSaleAmoount);
				if ((int) totalNoofDO != 0)
					ttso[i].setDeliveredOrderPercent(ttso[i]
							.getNoOfDeliveredOrder() * 100 / totalNoofDO);
				if ((int) totalNoofAO != 0)
					ttso[i].setActionableOrdersPercent(ttso[i]
							.getNoOfActionableOrders() * 100 / totalNoofAO);
				if ((int) totalNoofRO != 0)
					ttso[i].setReturnOrderPercent(ttso[i].getNoOfReturnOrder()
							* 100 / totalNoofRO);
				ttso[i].setRTOOrderPercent(ttso[i].getNoOfRTOOrder());

				if ((int) totalNoofRTOCross != 0)
					ttso[i].setRTOLimitCrossedPercent(ttso[i]
							.getNoOfRTOLimitCrossed() * 100 / totalNoofRTOCross);
				if ((int) totalNoofreturnCross != 0)
					ttso[i].setReturnLimitCrossedPercent(ttso[i]
							.getNoOfReturnLimitCrossed()
							* 100
							/ totalNoofreturnCross);
				if ((int) totalNoofSO != 0)
					ttso[i].setSettledOrdersPercent(ttso[i]
							.getNoOfSettledOrders() * 100 / totalNoofSO);

			}
		}
			session.getTransaction().commit();
			session.close();
		} catch (NullPointerException e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : "+sellerId,e);					
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : "+sellerId,e);
			throw new CustomException(
					GlobalConstant.getAllPartnerTSOdetailsError, new Date(), 3,
					GlobalConstant.getAllPartnerTSOdetailsErrorCode, e);			
		}		
		log.info("*** getAllPartnerTSOdetails Starts : ReportsGeneratorDaoImpl ****");
		return Arrays.asList(ttso);
	}
	
	@Override
	public List<ConsolidatedOrderBean> getConsolidatedOrdersReport(
			Date startDate, Date endDate, String status, int sellerId) {
		log.info("*** getConsolidatedOrdersReport Starts : ReportsGeneratorDaoImpl ****");

		List<Order> orderList=null;
		List<Order> orderlistGpReturn=null;
		List temp=null;
		List<ConsolidatedOrderBean> consolidatedList=new ArrayList<ConsolidatedOrderBean>();
		Date currentDate=new Date();
		Map<String, Object> consolidatedMap=new HashMap<String, Object>();
		ConsolidatedOrderBean bean=null;
		try {
			orderList=orderService.findOrdersbyDate("shippedDate", startDate, endDate, sellerId, false);
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			
			Criteria criteria = session.createCriteria(Order.class);
			criteria.createAlias("orderReturnOrRTO", "orderReturnOrRTO",CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("seller", "seller",CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("seller.id", sellerId));			
			criteria.add(Restrictions.between("orderReturnOrRTO.returnDate", startDate, endDate))
					.add(Restrictions.and(    
                    Restrictions.eq("poOrder", true),
                    Restrictions.isNull("consolidatedOrder")))
					.setResultTransformer(
							CriteriaSpecification.DISTINCT_ROOT_ENTITY);						
			
			temp = criteria.list();
			if (temp != null && temp.size() != 0) {				
				orderlistGpReturn = temp;
			}
			if(orderList!=null&&orderlistGpReturn!=null)			
			orderList.addAll(orderlistGpReturn);
			else if(orderList==null&&orderlistGpReturn!=null)
				orderList=orderlistGpReturn;
			
			if(orderList != null && orderList.size() != 0){
				for (Order order:orderList) {
					if(consolidatedMap.containsKey(order.getPcName())){
						bean=(ConsolidatedOrderBean)consolidatedMap.get(order.getPcName());						
					}else{
						bean=new ConsolidatedOrderBean();
						bean.setPcName(order.getPcName());
					}
					bean.setNetPaymentResult(bean.getNetPaymentResult()+order.getOrderPayment().getNetPaymentResult());
					bean.setSaleQuantity(bean.getSaleQuantity()+order.getQuantity());
					
					if(order.getOrderReturnOrRTO() != null){
						bean.setReturnQuantiy(bean.getReturnQuantiy()+order.getOrderReturnOrRTO().getReturnorrtoQty());
						if(order.getOrderReturnOrRTO().getReturnDate() != null){
							bean.setReturnOrder(bean.getReturnOrder()+1);
						}
						if(order.getOrderReturnOrRTO().getReturnDate() != null && order.getReturnLimitCrossed() != null 
								&& order.getOrderReturnOrRTO().getReturnDate().after(order.getReturnLimitCrossed())){
							bean.setReturnLimitCrossed(bean.getReturnLimitCrossed()+1);						
						}
						if(order.getOrderReturnOrRTO().getReturnDate() != null && order.getrTOLimitCrossed() != null 
								&& order.getOrderReturnOrRTO().getReturnDate().after(order.getrTOLimitCrossed())){
							bean.setRtoLimitCrossed(bean.getRtoLimitCrossed()+1);						
						}
					}
					
					if(order.getDeliveryDate() != null && order.getDeliveryDate().before(currentDate)){
						bean.setDeliveredOrder(bean.getDeliveredOrder()+1);						
					}				
					bean.setPaymentDifferenceAmount(bean.getPaymentDifferenceAmount()+order.getOrderPayment().getPaymentDifference());
					if(order.getOrderPayment().getPaymentDifference() < 1 && order.getOrderPayment().getPaymentDifference() > -1){
						bean.setSettledOrder(bean.getSettledOrder()+1);
					}
					if(order.getOrderPayment().getPaymentDifference() < -1 || order.getOrderPayment().getPaymentDifference() > 1){
						bean.setActionableOrder(bean.getActionableOrder()+1);
					}										
					consolidatedMap.put(order.getPcName(), bean);
				}				
			}
			if(consolidatedMap != null){
				for (Map.Entry<String, Object> entry : consolidatedMap.entrySet())
				{
				    consolidatedList.add((ConsolidatedOrderBean)entry.getValue());
				}
			}
			
		} catch (NullPointerException e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : "+sellerId,e);					
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("*** getConsolidatedOrdersReport ends : ReportsGeneratorDaoImpl ****");

		return consolidatedList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PartnerReportDetails> getDebtorsReportDetails(Date startDate,
			Date endDate, int sellerId) throws CustomException {
		log.info("*** getDebtorsReportDetails Starts : ReportsGeneratorDaoImpl ****");

		List<PartnerReportDetails> partnerBusinessList = new ArrayList<PartnerReportDetails>();
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();			
			List<Order> results = fetchDebtorsOrders(session, sellerId, startDate, endDate);
			Map<String, PartnerReportDetails> poOrderMap = new HashMap<String, PartnerReportDetails>();
			for (Order currOrder : results) {
				PartnerReportDetails partnerBusiness = transformPartnerDetail(currOrder, session, startDate, endDate);
					partnerBusinessList.add(partnerBusiness);
			}
			log.info("Total Orders" + partnerBusinessList.size());
		} catch (NullPointerException e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : "+sellerId,e);					
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : "+sellerId,e);
			throw new CustomException(
					GlobalConstant.getAllPartnerTSOdetailsError, new Date(), 3,
					GlobalConstant.getAllPartnerTSOdetailsErrorCode, e);

		}
		log.info("*** getDebtorsReportDetails Starts : ReportsGeneratorDaoImpl ****");

		return partnerBusinessList;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PartnerReportDetails> getPartnerReportDetails(Date startDate,
			Date endDate, int sellerId) throws CustomException {
		log.info("*** getPartnerReportDetails Starts : ReportsGeneratorDaoImpl ****");

		List<PartnerReportDetails> partnerBusinessList = new ArrayList<PartnerReportDetails>();
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			log.info("Service Tax: " + dataConfig.getServiceTax());
			List<Order> results = fetchOrders(session, sellerId, startDate, endDate);
			Map<String, PartnerReportDetails> poOrderMap = new HashMap<String, PartnerReportDetails>();
			for (Order currOrder : results) {
				PartnerReportDetails partnerBusiness = transformPartnerDetail(currOrder, session, startDate, endDate);
				partnerBusinessList.add(partnerBusiness);
			}
			log.info("Total Orders" + partnerBusinessList.size());
		} catch (NullPointerException e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : "+sellerId,e);					
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : "+sellerId,e);
			throw new CustomException(
					GlobalConstant.getAllPartnerTSOdetailsError, new Date(), 3,
					GlobalConstant.getAllPartnerTSOdetailsErrorCode, e);

		}
		log.info("*** getPartnerReportDetails ends : ReportsGeneratorDaoImpl ****");

		return partnerBusinessList;
	}

	private PartnerReportDetails transformPartnerDetail(Order currOrder, Session session, Date startDate,
			Date endDate) {
		log.info("*** transformPartnerDetail Starts : ReportsGeneratorDaoImpl ****");

		PartnerReportDetails partnerBusiness = new PartnerReportDetails();
		try {			
			boolean isPoOrder = currOrder.isPoOrder();
			Order consolidatedOrder = currOrder.getConsolidatedOrder();
			Customer currCustomer = currOrder.getCustomer();
			OrderRTOorReturn currOrderReturnOrRTO = currOrder
					.getOrderReturnOrRTO();
			OrderPayment currOrderPayment = currOrder.getOrderPayment();
			OrderTax currOrderTax = currOrder.getOrderTax();
			int quantity = currOrder.getQuantity();
			partnerBusiness.setDeliveryDate(currOrder.getDeliveryDate());
			partnerBusiness.setAwb(currOrder.getAwbNum());
			partnerBusiness.setPiRefNumber(currOrder.getPIreferenceNo());
			partnerBusiness.setSubOrderId(currOrder.getSubOrderID());
			partnerBusiness.setLogisticPartner(currOrder.getLogisticPartner());
			partnerBusiness.setPaymentType(currOrder.getPaymentType());
			if(isPoOrder && consolidatedOrder==null)
				partnerBusiness.setPoOrder(true);
			else
				partnerBusiness.setPoOrder(false);
			if(currCustomer != null){
				partnerBusiness.setCustomerName(currCustomer.getCustomerName());
				partnerBusiness.setCustomerEmail(currCustomer.getCustomerEmail());
				partnerBusiness.setCustomerZip(currCustomer.getZipcode());
				partnerBusiness.setCustomerPhone(currCustomer.getCustomerPhnNo());
				partnerBusiness.setCustomerCity(currCustomer.getCustomerCity());
			}
			Criteria prodcriteria = session.createCriteria(Product.class);
			prodcriteria.add(Restrictions.eq("productSkuCode",
					currOrder.getProductSkuCode()));
			List<Product> productList = prodcriteria.list();
			double productCost = 0;
			if (productList.size() > 0) {
				Product currProduct = productList.get(0);
				partnerBusiness.setProductCategory(currProduct.getCategory().getParentCatName());
				partnerBusiness.setParentCategory(currProduct.getCategory().getParentCatName());
				productCost = currProduct.getProductPrice();
			}
			
			double tdsToBeDeposited = 0;
			double taxToBePaid = 0;
			double taxSP = 0;
			double taxPOPrice = 0;		
			double grossTds = 0;
			double returnTds = 0;
			double taxToDeduct = 0;
			double tdsOnReturnAmt = 0;
			if(currOrderTax != null){
				if(currOrder.getShippedDate() != null)
					grossTds = currOrderTax.getTdsToDeduct();
				taxToBePaid = currOrderTax.getTax();
				tdsOnReturnAmt = currOrderTax.getTdsonReturnAmt();
				tdsToBeDeposited = grossTds;
				if(currOrderReturnOrRTO != null && currOrderReturnOrRTO.getReturnDate() != null){
					returnTds = currOrderTax.getTdsToReturn();
					tdsToBeDeposited = tdsToBeDeposited - returnTds + tdsOnReturnAmt;
					taxToBePaid -= currOrderTax.getTaxToReturn();
					taxToDeduct = currOrderTax.getTdsToDeduct();
				}
				// Only for PO
				if(partnerBusiness.isPoOrder()){
					taxSP = currOrderTax.getTaxSP();
					if(currOrder.getShippedDate() != null)
						taxPOPrice = currOrderTax.getTax();
				}
			}
			partnerBusiness.setTaxSP(taxSP);
			partnerBusiness.setGrossTds(grossTds);
			partnerBusiness.setReturnTds(returnTds);
			partnerBusiness.setTdsToBeDeposited(tdsToBeDeposited);
			
			int returnQty = 0;
			double netReturnCharges = 0;
			double grossNetRate = currOrder.getGrossNetRate();
			double returnSP = 0;
			double grossSP = currOrder.getOrderSP();
			double orderSP =  grossSP * quantity;
			double netRate = currOrder.getNetRate();
			double grossReturnChargesReversed = 0; 
			double additionalReturnCharges = 0; 
			double netPr = 0;
			double returnNetPr = 0;
			double returnNR = 0;
			double returnChargesToBeDeducted = 0;
			Date returnDate = null;
			if (currOrderReturnOrRTO != null) {
				returnDate = currOrderReturnOrRTO.getReturnDate();
				boolean dateCriteria = returnDate!=null;
				if(dateCriteria){
					partnerBusiness.setReturnDate(currOrderReturnOrRTO.getReturnDate());
					returnQty = currOrderReturnOrRTO.getReturnorrtoQty();
					returnSP = grossSP*returnQty;
					returnNetPr = currOrderReturnOrRTO.getNetPR();
					partnerBusiness.setNetReturnPr(returnNetPr);
					partnerBusiness.setReturnQuantity(returnQty);
					netReturnCharges = grossNetRate * returnQty;
					returnChargesToBeDeducted = currOrderReturnOrRTO.getReturnOrRTOChargestoBeDeducted();
					returnNR = currOrderReturnOrRTO.getNetNR();
					if(!partnerBusiness.isPoOrder()){
						additionalReturnCharges = returnChargesToBeDeducted;
					} 
					partnerBusiness.setNetReturnCharges(returnChargesToBeDeducted);
					partnerBusiness.setReturnId(currOrderReturnOrRTO.getReturnOrRTOId());
					if(partnerBusiness.isPoOrder() && returnDate != null)
						taxPOPrice = currOrderReturnOrRTO.getTaxPOAmt();
					
					String type = currOrderReturnOrRTO.getType();
					String returnCategory = currOrderReturnOrRTO.getReturnCategory();
					String cancelType = currOrderReturnOrRTO.getCancelType();
					StringBuilder builder = new StringBuilder();
					if(StringUtils.isNotBlank(type)){
						builder.append(type);
						builder.append(" >> ");
					}
					if(StringUtils.isNotBlank(returnCategory)){
						builder.append(returnCategory);
						builder.append(" >> ");
					}
					if(StringUtils.isNotBlank(cancelType)){
						builder.append(type);
					}
					partnerBusiness.setReturnChargesDesciption(builder.toString());
				}
			}
			if(partnerBusiness.isPoOrder()){
				if(returnDate == null){
					partnerBusiness.setGrossNetRate(currOrder.getNetRate());
					partnerBusiness.setNetActualSale(currOrder.getNetRate());
				} else{
					partnerBusiness.setGrossNetRate(0);
					partnerBusiness.setNetActualSale(-(returnNR));
				}
			}

			if(!partnerBusiness.isPoOrder()){
				if(quantity != 0){
					netPr = currOrder.getPr()/quantity*(quantity-returnQty);
				}
				partnerBusiness.setProductPrice(productCost*(quantity - returnQty));
			} else{
				if(currOrder.getShippedDate() != null)
					netPr = currOrder.getPr() - returnNetPr;
				partnerBusiness.setProductPrice(currOrder.getOrderSP());
			}
			partnerBusiness.setTaxPOPrice(taxPOPrice);
			if(quantity != 0)
				grossReturnChargesReversed = netRate/quantity*returnQty;
			partnerBusiness.setGrossReturnChargesReversed(grossReturnChargesReversed);
			if(!partnerBusiness.isPoOrder()){
				double netActualSale = currOrder.getGrossNetRate()*(quantity - returnQty) - additionalReturnCharges;
				partnerBusiness.setNetActualSale(netActualSale);
				partnerBusiness.setTotalReturnCharges(additionalReturnCharges + netReturnCharges);
			} else{
				partnerBusiness.setTotalReturnCharges(returnNR);
			}
			if(partnerBusiness.isPoOrder()){
				partnerBusiness.setNetSP(currOrder.getPoPrice() - returnChargesToBeDeducted);	
				partnerBusiness.setReturnSP(currOrder.getPoPrice());
				partnerBusiness.setOrderSP(currOrder.getOrderSP());
			}
			else{
				partnerBusiness.setReturnSP(returnSP);
				partnerBusiness.setOrderSP(orderSP);
				if(quantity != 0)
					partnerBusiness.setNetSP(grossSP/quantity*(quantity-returnQty));
			}
			if (currOrderPayment != null) {
				partnerBusiness.setDateofPayment(currOrderPayment
						.getDateofPayment());
				double netPaymentResult = currOrderPayment
						.getNetPaymentResult();
				partnerBusiness.setNetPaymentResult(netPaymentResult);
				double paymentDifference = currOrderPayment.getPaymentDifference();
				partnerBusiness.setPaymentDifference(paymentDifference);
				partnerBusiness.setPaymentCycle(currOrderPayment.getPaymentCycle());
				partnerBusiness.setNegativeAmount(currOrderPayment.getNegativeAmount());
				partnerBusiness.setPositiveAmount(currOrderPayment.getPositiveAmount());
				partnerBusiness.setPaymentId(currOrderPayment.getPaymentId());			
				partnerBusiness.setPaymentUploadedDate(currOrderPayment.getUploadDate());
			}
			partnerBusiness.setOrderId(currOrder.getOrderId());
			partnerBusiness.setInvoiceID(currOrder.getInvoiceID());
			partnerBusiness
					.setChannelOrderID(currOrder.getChannelOrderID());
			partnerBusiness.setPcName(currOrder.getPcName());
			partnerBusiness.setOrderDate(currOrder.getOrderDate());
			partnerBusiness.setShippedDate(currOrder.getShippedDate());
			partnerBusiness
					.setPaymentDueDate(currOrder.getPaymentDueDate());
			partnerBusiness.setGrossSaleQuantity(quantity);
			int netSaleQty = quantity - returnQty;
			partnerBusiness.setNetSaleQuantity(netSaleQty);
			double grossCommission = 0;
			double grossCommissionNoQty = 0;
			double grossCommissionQty = 0;
			// MP & PO Conditions
			if(partnerBusiness.isPoOrder()){
				grossCommission = currOrder.getPartnerCommission();
				grossCommissionNoQty = grossCommission;
				grossCommissionQty = grossCommission;
			} else{
				grossCommission = currOrder.getPartnerCommission() * netSaleQty;
				grossCommissionNoQty = currOrder.getPartnerCommission() * returnQty;
				grossCommissionQty = currOrder.getPartnerCommission() * quantity;
			}
			partnerBusiness.setGrossPartnerCommission(grossCommission);
			double pccAmount = 0;
			double fixedFee = 0;
			double shippingCharges = 0;
			double pccAmountNoQty = 0;
			double fixedFeeNoQty = 0;
			double shippingChargesNoQty = 0;
			double pccAmountQty = 0;
			double fixedFeeQty = 0;
			double shippingChargesQty = 0;
			// Only for MP
			if(!partnerBusiness.isPoOrder()){
				pccAmount = currOrder.getPccAmount() * netSaleQty;
				fixedFee = currOrder.getFixedfee() * netSaleQty; 
				shippingCharges = currOrder.getShippingCharges() * netSaleQty;
				pccAmountNoQty = currOrder.getPccAmount() * returnQty;
				fixedFeeNoQty = currOrder.getFixedfee() * returnQty; 
				shippingChargesNoQty = currOrder.getShippingCharges() * returnQty;
				pccAmountQty = currOrder.getPccAmount() * quantity;
				fixedFeeQty = currOrder.getFixedfee() * quantity; 
				shippingChargesQty = currOrder.getShippingCharges() * quantity;
			}
			partnerBusiness.setPccAmount(pccAmount);				
			partnerBusiness.setFixedfee(fixedFee);
			partnerBusiness.setShippingCharges(shippingCharges);
			double totalAmount = grossCommission + pccAmount + fixedFee + shippingCharges;
			double serviceTax = (totalAmount)*dataConfig.getServiceTax()/100;
			double serviceTaxNoQty = 0;
			double serviceTaxQty = 0;
			double grossCommissionToBePaid = 0;
			// PO Condition
			if(partnerBusiness.isPoOrder()){
				grossCommissionToBePaid = grossCommission + taxSP - taxPOPrice;
			}
			else{
				serviceTaxNoQty = (grossCommissionNoQty + pccAmountNoQty + fixedFeeNoQty + shippingChargesNoQty)*dataConfig.getServiceTax()/100;
				serviceTaxQty = (grossCommissionQty + pccAmountQty + fixedFeeQty + shippingChargesQty)*dataConfig.getServiceTax()/100;
				grossCommissionToBePaid = totalAmount + serviceTax;
				partnerBusiness.setServiceTax(serviceTax);
			}
			double grossCommissionToBePaidNoQty = grossCommissionNoQty + pccAmountNoQty + fixedFeeNoQty + shippingChargesNoQty + serviceTaxNoQty;
			double grossCommissionToBePaidQty = 0;
			// MP Condition
			if(!partnerBusiness.isPoOrder())
				grossCommissionToBePaidQty = grossCommissionQty + pccAmountQty + fixedFeeQty + shippingChargesQty + serviceTaxQty;
			// PO Condition
			if(partnerBusiness.isPoOrder() && currOrder.getShippedDate() != null){
				grossCommissionToBePaidQty = grossCommissionToBePaid;
			}
			partnerBusiness.setGrossCommissionQty(grossCommissionToBePaidQty);
			partnerBusiness.setGrossCommission(grossCommissionToBePaid);
			double returnCommision = 0;
			// MP & PO Conditions
			if(returnDate != null){
				if(partnerBusiness.isPoOrder()){
					returnCommision = grossCommissionToBePaid;
				} else{
					returnCommision = grossCommissionToBePaidNoQty;
				}
			}
			partnerBusiness.setReturnCommision(returnCommision);
			partnerBusiness.setAdditionalReturnCharges(additionalReturnCharges);
			double netPartnerCommissionPaid = grossCommissionToBePaidQty - returnCommision + additionalReturnCharges;
			partnerBusiness.setNetPartnerCommissionPaid(netPartnerCommissionPaid);
			if(taxToDeduct != 0){
				partnerBusiness.setTdsToBeDeducted10(0.1 * grossCommission);
				partnerBusiness.setTdsToBeDeducted2(0.02 * (pccAmount + fixedFee + shippingCharges) + tdsOnReturnAmt);
			}
			partnerBusiness.setNetTaxToBePaid(taxToBePaid);
			double netEossValue = 0; 
			// Only for Consolidated PO
			if(partnerBusiness.isPoOrder())
				netEossValue = currOrder.getEossValue();
			partnerBusiness.setNetEossValue(netEossValue);
			partnerBusiness.setNetPr(netPr);
			if(!partnerBusiness.isPoOrder())
				partnerBusiness.setGrossNetRate(grossNetRate * quantity);
			partnerBusiness.setNetRate(currOrder.getNetRate());
			partnerBusiness.setFinalStatus(currOrder.getFinalStatus());
			partnerBusiness.setGrossProfit(currOrder.getGrossProfit());
		} catch (NullPointerException e) {
			e.printStackTrace();
			log.error("Failed!",e);					
		} catch (Exception e) {
			log.error("Failed ", e);
			e.printStackTrace();
		}		
		log.info("*** transformPartnerDetail Starts : ReportsGeneratorDaoImpl ****");

		return partnerBusiness;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChannelReportDetails> getChannelReportDetails(Date startDate,
			Date endDate, int sellerId, String reportName) throws CustomException {
		
		log.info("*** getChannelReportDetails Starts : ReportsGeneratorDaoImpl ****");
		List<ChannelReportDetails> channelReportDetailsList = new ArrayList<ChannelReportDetails>();
		 Map<String, Float> taxCatPercentMap =null;
		 
		 try
		 {
			 Session session=sessionFactory.openSession();
			 session.getTransaction().begin();
			 List<Order> orders;
			 if("paymentsReceievedReport".equalsIgnoreCase(reportName)){
				 orders = fetchOrdersByPayment(session, sellerId, startDate, endDate);
			 } else{
				 orders = fetchOrders(session, sellerId, startDate, endDate);
			 }
			 taxCatPercentMap=taxDetailService.getTaxCategoryMap(sellerId);
			 Map<String, ChannelReportDetails> poOrderMap = new HashMap<String, ChannelReportDetails>();
			 if(taxCatPercentMap!=null)
			 {
			 for (Order currOrder : orders) {
				 float percent=taxCatPercentMap.containsKey(currOrder.getOrderTax().getTaxCategtory())?
					 taxCatPercentMap.get(currOrder.getOrderTax().getTaxCategtory()):0;
				 ChannelReportDetails channelReport = transformChannelReport(currOrder,percent, session, sellerId);
				 channelReportDetailsList.add(channelReport);
			 }
			 }
		 } catch (NullPointerException e) {
				e.printStackTrace();
				log.error("Failed! by sellerId : "+sellerId,e);					
		 } catch (Exception e) {
			 log.error("Failed! by sellerId : "+sellerId,e);
			throw new CustomException(
					GlobalConstant.getAllPartnerTSOdetailsError, new Date(), 3,
					GlobalConstant.getAllPartnerTSOdetailsErrorCode, e);

		}
			log.info("*** getChannelReportDetails ends : ReportsGeneratorDaoImpl ****");
 
		return channelReportDetailsList;	 
	}

	private ChannelReportDetails transformChannelReport(Order currOrder,float taxpercent, Session session, int sellerId) {
		log.info("*** transformChannelReport Starts : ReportsGeneratorDaoImpl ****");

		ChannelReportDetails channelReport = new ChannelReportDetails();
		try {
			boolean isPoOrder = currOrder.isPoOrder();
			Order consolidateOrder = currOrder.getConsolidatedOrder();
			
			channelReport.setPartner(currOrder.getPcName());
			channelReport.setOrderId(currOrder.getChannelOrderID());
			channelReport.setInvoiceId(currOrder.getInvoiceID());
			channelReport.setShippedDate(currOrder.getShippedDate());
			channelReport.setReceivedDate(currOrder.getOrderDate());
			channelReport.setDeliveryDate(currOrder.getDeliveryDate());
			channelReport.setProductSku(currOrder.getProductSkuCode());
			channelReport.setPaymentType(currOrder.getPaymentType());
			channelReport.setAwb(currOrder.getAwbNum());
			channelReport.setPiRefNo(currOrder.getPIreferenceNo());
			channelReport.setSubOrderId(currOrder.getSubOrderID());
			channelReport.setLogisticPartner(currOrder.getLogisticPartner());
			if(isPoOrder && consolidateOrder==null)
				channelReport.setPoOrder(true);
			else
				channelReport.setPoOrder(false);
			double orderPr = currOrder.getPr();
			double grossProfit = currOrder.getGrossProfit();
			double grossSaleQty = currOrder.getQuantity();
			double grossNrAmount = currOrder.getGrossNetRate();
			double grossSpAmount = currOrder.getOrderSP();
			double saleRetQty = 0;
			double saleRetNrAmount = 0;
			double saleRetSpAmount = 0;
			channelReport.setGrossProfit(grossProfit);
			
			channelReport.setGrossQty(grossSaleQty);
			
			OrderRTOorReturn currOrderReturn = currOrder.getOrderReturnOrRTO();
			
			if(channelReport.isPoOrder()) {
				channelReport.setGrossNrAmount(currOrder.getNetRate());
				if(currOrderReturn == null){
					channelReport.setGrossSpAmount(currOrder.getPoPrice());
					channelReport.setNetEOSSValue(currOrder.getEossValue());
				}
			} else {
				channelReport.setGrossNrAmount(grossNrAmount*grossSaleQty);
				channelReport.setGrossSpAmount(grossSpAmount);
			}
			
			double additionalCharges = 0; 
			if(currOrderReturn != null){
				additionalCharges = currOrderReturn.getReturnOrRTOChargestoBeDeducted();
				channelReport.setNetReturnCharges(additionalCharges);
				saleRetQty = currOrderReturn.getReturnorrtoQty();
				channelReport.setReturnDate(currOrderReturn.getReturnDate());
				channelReport.setReturnId(currOrderReturn.getReturnOrRTOId());
				// Only for PO Order
				if(channelReport.isPoOrder()){
					saleRetNrAmount = currOrderReturn.getNetNR();
					saleRetSpAmount = additionalCharges;
					additionalCharges = 0;
					channelReport.setReturnId(currOrderReturn.getReturnOrRTOId());
					channelReport.setNetEOSSValue(-currOrder.getEossValue());
					channelReport.setNetReturnCharges(additionalCharges);
				}
				channelReport.setReturnGrossProfit(currOrder.getGrossProfit());
			} else {
				channelReport.setSaleGrossProfit(currOrder.getGrossProfit());
			}
			
			double netPr = currOrder.getPr()/grossSaleQty*(grossSaleQty-saleRetQty);
			channelReport.setNetPr(netPr);	
			// MP/PO Order conditions
			if(channelReport.isPoOrder()){
				//saleRetSpAmount = grossSpAmount;
				if(currOrderReturn != null) {
					channelReport.setNetPr(-currOrder.getPr());
					channelReport.setPr(-currOrder.getPr());
				} else {
					channelReport.setNetPr(currOrder.getPr());
					channelReport.setPr(currOrder.getPr());
				}
				/*if(grossSaleQty != 0)
					channelReport.setPr(orderPr * saleRetQty/grossSaleQty);*/
			} else {
				saleRetNrAmount = grossNrAmount*saleRetQty;
				if(grossSaleQty != 0)
					saleRetSpAmount = grossSpAmount*(saleRetQty/grossSaleQty);
				channelReport.setPr(orderPr);
			}
			channelReport.setSaleRetQty(saleRetQty);
			channelReport.setSaleRetNrAmount(saleRetNrAmount + additionalCharges);
			channelReport.setRetAmountToBeReversed(saleRetNrAmount);
			channelReport.setSaleRetSpAmount(saleRetSpAmount);
			
			double saleRetVsGrossSale = 0;
			if(grossSaleQty != 0)
				saleRetVsGrossSale = grossSaleQty/saleRetQty*100;
			double netQty = grossSaleQty - saleRetQty;
			double netNrAmount = grossNrAmount*grossSaleQty - saleRetNrAmount - additionalCharges;
			double netSpAmount = grossSpAmount - saleRetSpAmount;
			channelReport.setSaleRetVsGrossSale(saleRetVsGrossSale);
			channelReport.setNetQty(netQty);
			if(channelReport.isPoOrder()){
				channelReport.setNetNrAmount(channelReport.getGrossNrAmount() - channelReport.getSaleRetNrAmount());
				channelReport.setNetSpAmount(channelReport.getGrossSpAmount() - channelReport.getSaleRetSpAmount());
			} else {
				channelReport.setNetNrAmount(netNrAmount);
				channelReport.setNetSpAmount(netSpAmount);
			}
			
			double taxCatPercent = 0;
			double netTaxLiability = 0;
			OrderTax currOrderTax = currOrder.getOrderTax();
			if(currOrderTax != null){
				if(channelReport.isPoOrder()){
					if(currOrderReturn != null) {
						netTaxLiability = - currOrderTax.getTaxToReturn();
					} else {
						netTaxLiability = currOrderTax.getTax();
					}
				} 
				String taxCategory = currOrderTax.getTaxCategtory();
				channelReport.setTaxCategory(taxCategory);
				channelReport.setTaxPercent(taxpercent);
				taxCatPercent=taxpercent;
			}
			if(taxCatPercent != 0)
				netTaxLiability = netSpAmount - netSpAmount*(100/(100 + taxCatPercent));
			channelReport.setNetTaxLiability(netTaxLiability);
			
			OrderPayment currOrderPayment = currOrder.getOrderPayment();
			if(currOrderPayment != null){
				channelReport.setNetToBeReceived(currOrderPayment.getPaymentDifference());
				double netPaymentResult = currOrderPayment.getNetPaymentResult();
				double paymentDifference = currOrderPayment.getPaymentDifference();
				channelReport.setNetPaymentResult(netPaymentResult);
				channelReport.setPaymentDifference(paymentDifference);
				channelReport.setNetAr(netPaymentResult);
				channelReport.setPaymentId(currOrderPayment.getPaymentId() + "");						
				channelReport.setPaymentDate(currOrderPayment.getDateofPayment());
			}
			
			double productCost = 0;
			double gpVsProductCost = 0;
			Criteria prodcriteria = session.createCriteria(Product.class);
			prodcriteria.add(Restrictions.eq("productSkuCode",
					currOrder.getProductSkuCode()));
			List<Product> productList = prodcriteria.list();
			if (productList.size() > 0) {
				Product currProduct = productList.get(0);
				if(!channelReport.isPoOrder()){
					productCost = currProduct.getProductPrice();
				} else{
					System.out.println(currProduct.getCategoryName() + "--" + currProduct.getCategory().getParentCatName());
				}
				channelReport.setCategory(currProduct.getCategoryName());
				channelReport.setParentCategory(currProduct.getCategory().getParentCatName());
			}
			if(productCost != 0){
				double grossProductCost = productCost*grossSaleQty;
				double returnProductCost = productCost*saleRetQty;
				double netProductCost = grossProductCost - returnProductCost;
				channelReport.setProductCost(grossProductCost);
				channelReport.setReturnProductCost(returnProductCost);
				channelReport.setNetProductCost(netProductCost);
				if(netProductCost != 0)
					gpVsProductCost = grossProfit/netProductCost*100;
			}
			channelReport.setGpVsProductCost(gpVsProductCost);
			channelReport.setFinalStatus(currOrder.getFinalStatus());
			
			channelReport.setReturnTaxableSale(channelReport.getSaleRetSpAmount());
			channelReport.setReturnActualSale(channelReport.getSaleRetNrAmount());
			if(channelReport.isPoOrder()) {
				if (currOrderReturn != null) {
					channelReport.setReturnTaxfreeSale(-channelReport.getPr());
					channelReport.setReturnProductCost(currOrder.getOrderSP());
					channelReport.setNetProductCost(-currOrder.getOrderSP());
					channelReport.setGrossProfit(-currOrder.getGrossProfit());
				} else {
					channelReport.setGrossTaxfreeSale(channelReport.getPr());
					channelReport.setProductCost(currOrder.getOrderSP());
					channelReport.setNetProductCost(currOrder.getOrderSP());
				}
				gpVsProductCost = channelReport.getGrossProfit() /
									channelReport.getNetProductCost() * 100;
				channelReport.setGpVsProductCost(gpVsProductCost);
			} else {
				channelReport.setReturnTaxfreeSale(currOrder.getPr()/grossSaleQty*saleRetQty);
				channelReport.setGrossTaxfreeSale(channelReport.getPr());
			}
			
			channelReport.setGrossTaxableSale(channelReport.getGrossSpAmount());
			channelReport.setGrossActualSale(channelReport.getGrossNrAmount());
			
			channelReport.setNetTaxableSale(channelReport.getGrossTaxableSale() - 
					channelReport.getReturnTaxableSale());
			channelReport.setNetActualSale(channelReport.getGrossActualSale() - 
					channelReport.getReturnActualSale());
			channelReport.setNetTaxfreeSale(channelReport.getGrossTaxfreeSale() - 
					channelReport.getReturnTaxfreeSale());
		} catch (NullPointerException e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : "+sellerId,e);					
		} catch (Exception e) {
			log.error("Failed by SellerID : "+sellerId, e);
			e.printStackTrace();
		}
		log.info("*** transformChannelReport ends : ReportsGeneratorDaoImpl ****");

		return channelReport;
	}

	/**
	 * Generic method to fetch list of Order objects (MP + PO)
	 * 
	 * @param session
	 * @param sellerId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Order> fetchOrders(Session session, int sellerId, Date startDate, Date endDate) {
		log.info("*** fetchOrders Starts : ReportsGeneratorDaoImpl ****");

		List<Order> orderList = new ArrayList<>();
		
		try {
			Criteria criteria = session.createCriteria(Order.class);
			criteria.add(Restrictions.eq("poOrder", false));
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId));
			criteria.createAlias("orderPayment", "orderPayment",
					CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("orderReturnOrRTO", "orderReturnOrRTO",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.or(
							Restrictions.between("shippedDate", startDate, endDate),
							Restrictions.between("orderReturnOrRTO.returnDate", startDate, endDate)));
			criteria.createAlias("orderTax", "orderTax",
					CriteriaSpecification.LEFT_JOIN);
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			criteria.addOrder(org.hibernate.criterion.Order.asc("shippedDate"));
			orderList.addAll(criteria.list());

			criteria = session.createCriteria(Order.class);
			criteria.add(Restrictions.eq("poOrder", true));
			criteria.add(Restrictions.isNull("consolidatedOrder"));
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId));
			criteria.createAlias("orderPayment", "orderPayment",
					CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("orderReturnOrRTO", "orderReturnOrRTO",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.or(
							Restrictions.between("shippedDate", startDate, endDate),
							Restrictions.between("orderReturnOrRTO.returnDate", startDate, endDate)));
			criteria.createAlias("orderTax", "orderTax",
					CriteriaSpecification.LEFT_JOIN);
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			criteria.addOrder(org.hibernate.criterion.Order.asc("shippedDate"));
			orderList.addAll(criteria.list());
		} catch (NullPointerException e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : "+sellerId,e);					
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed by SellerID : "+sellerId, e);
		}
		log.info("*** fetchOrders ends : ReportsGeneratorDaoImpl ****");

		return orderList;
	}
	
	/**
	 * Generic method to fetch list of Order objects (MP + PO)
	 * 
	 * @param session
	 * @param sellerId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Order> fetchOrdersByPayment(Session session, int sellerId, Date startDate, Date endDate) {
		log.info("*** fetchOrdersByPayment starts : ReportsGeneratorDaoImpl ****");

		List<Order> orderList = new ArrayList<>();
		try {
			Criteria criteria = session.createCriteria(Order.class);
			criteria.add(Restrictions.eq("poOrder", false));
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId));
			criteria.createAlias("orderPayment", "orderPayment",
					CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("orderReturnOrRTO", "orderReturnOrRTO",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.between("orderPayment.dateofPayment", startDate, endDate));
			criteria.createAlias("orderTax", "orderTax",
					CriteriaSpecification.LEFT_JOIN);
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			criteria.addOrder(org.hibernate.criterion.Order.asc("shippedDate"));
			orderList.addAll(criteria.list());
			
			criteria = session.createCriteria(Order.class);
			criteria.add(Restrictions.eq("poOrder", true));
			criteria.add(Restrictions.isNull("consolidatedOrder"));
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId));
			criteria.createAlias("orderPayment", "orderPayment",
					CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("orderReturnOrRTO", "orderReturnOrRTO",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.between("orderPayment.dateofPayment", startDate, endDate));
			criteria.createAlias("orderTax", "orderTax",
					CriteriaSpecification.LEFT_JOIN);
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			criteria.addOrder(org.hibernate.criterion.Order.asc("shippedDate"));
			orderList.addAll(criteria.list());
			
		} catch (NullPointerException e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : "+sellerId,e);					
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed by SellerID : "+sellerId, e);
		}
		log.info("*** fetchOrdersByPayment ends : ReportsGeneratorDaoImpl ****");

		return orderList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CommissionDetails> fetchPC(int sellerId, Date startDate, Date endDate, String criteria) {
		log.info("*** fetchPC starts : ReportsGeneratorDaoImpl ****");

		Map<String, CommissionDetails> commDetailsMap = new HashMap<String, CommissionDetails>(); 
		List<CommissionDetails> commDetailsList = new ArrayList<CommissionDetails>();
		try {
			Session session=sessionFactory.openSession();
			session.getTransaction().begin();
			String mpOrderQueryStr = "select ot.pcName, sum((ot.partnerCommission+ot.pccAmount+ot.fixedFee+ot.shippingCharges) * ot.quantity * 1.15) " +
					"as grossComm, sum(ot.quantity) as netSaleQty, sum(otx.tdsToDeduct) as tdsToDeduct from order_table ot, ordertax otx " +
					"where ot.orderTax_taxId = otx.taxId and ot.poOrder = 0  and ot.seller_Id=:sellerId and ot.shippedDate " +
					"between :startDate AND :endDate group by ot.pcName";
			if("category".equalsIgnoreCase(criteria))
				mpOrderQueryStr = "select cat.parentCatName, sum((ot.partnerCommission+ot.pccAmount+ot.fixedFee+ot.shippingCharges) * ot.quantity * 1.15) " +
					"as grossComm, sum(ot.quantity) as netSaleQty, sum(otx.tdsToDeduct) as tdsToDeduct from order_table ot, ordertax otx " +
					", product pr, category cat where pr.category_categoryId = cat.categoryId and ot.productSkuCode = pr.productSkuCode and ot.orderTax_taxId = otx.taxId and ot.poOrder = 0  and " +
					"ot.seller_Id=:sellerId and ot.seller_id=pr.seller_Id and ot.shippedDate between :startDate AND :endDate group by cat.parentCatName";
			Query mpOrderQuery = session.createSQLQuery(mpOrderQueryStr)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			List<Object[]> orderList2 = mpOrderQuery.list();
			for(Object[] order: orderList2){
				CommissionDetails commDetails = new CommissionDetails();
				String key = order[0].toString();
				if("category".equalsIgnoreCase(criteria))
					commDetails.setCategoryName(key);
				else
					commDetails.setPartner(key);
				double grossCommissionPaid = Double.parseDouble(order[1].toString());
				int netSaleQty = Integer.parseInt(order[2].toString());
				double tdsToDeduct = Double.parseDouble(order[3].toString());
				commDetails.setGrossPartnerCommissionPaid(grossCommissionPaid);
				commDetails.setNetSaleQty(netSaleQty);
				commDetails.setNetTDSToBeDeposited(tdsToDeduct);
				commDetailsMap.put(key, commDetails);
			}
			
			String poOrderQueryStr = "select ot.pcName, sum( ot.partnerCommission+otx.taxSP-otx.tax ) as grossComm, " +
					"sum(ot.quantity) as saleQty, sum(otx.tdsToDeduct) as tdsToDeduct from order_table ot, ordertax otx " +
					"where ot.orderTax_taxId = otx.taxId and ot.poOrder = 1 and ot.consolidatedOrder_orderId is null " +
					"and ot.seller_Id=:sellerId and ot.shippedDate between :startDate AND :endDate group by ot.pcName";
			if("category".equalsIgnoreCase(criteria))
				poOrderQueryStr = "select 'B2B', sum( ot.partnerCommission+otx.taxSP-otx.tax ) as grossComm, " +
						"sum(ot.quantity) as saleQty, sum(otx.tdsToDeduct) as tdsToDeduct from order_table ot, ordertax otx " +
						"where ot.orderTax_taxId = otx.taxId and ot.poOrder = 1 and ot.consolidatedOrder_orderId is null and ot.seller_Id=:sellerId and " +
						"ot.shippedDate between :startDate AND :endDate";
			Query poOrderQuery = session.createSQLQuery(poOrderQueryStr)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			orderList2 = poOrderQuery.list();
			for(Object[] order: orderList2){
				String key = order[0].toString();
				CommissionDetails commDetails = commDetailsMap.get(key);
				double existGC = 0;
				int existSQ = 0;
				double existTDS = 0;
				int netSaleQty=0;
				double grossCommissionPaid=0;
				double tdsToDeduct=0;
				if(commDetails!=null){
					existGC = commDetails.getGrossPartnerCommissionPaid();
					existSQ = commDetails.getNetSaleQty();
					existTDS = commDetails.getNetTDSToBeDeposited();
				}
				else
					commDetails = new CommissionDetails();
				if("category".equalsIgnoreCase(criteria))
					commDetails.setCategoryName(key);
				else
					commDetails.setPartner(key);
				if(order[2] != null)
					netSaleQty = Integer.parseInt(order[2].toString());
				if(order[1] != null)
					grossCommissionPaid = Double.parseDouble(order[1].toString());
				if(order[3] != null)
				tdsToDeduct = Double.parseDouble(order[3].toString());
				commDetails.setNetSaleQty(existSQ + netSaleQty);
				commDetails.setGrossPartnerCommissionPaid(existGC + grossCommissionPaid);
				commDetails.setNetTDSToBeDeposited(existTDS + tdsToDeduct);
				commDetailsMap.put(key, commDetails);
			}
			String mpReturnQueryStr = "select ot.pcName, sum((ot.partnerCommission+ot.pccAmount+ot.fixedFee+ot.shippingCharges) * orr.returnorrtoQty * 1.15) as returnComm, " +
					"sum(estimateddeduction) as addRetCharges, sum(orr.returnorrtoQty) as returnQty, sum(otx.tdsToReturn - otx.tdsonReturnAmt) as tdsToReturn from order_table ot, " +
					"orderreturn orr, ordertax otx where ot.orderTax_taxId = otx.taxId and ot.orderReturnOrRTO_returnId = orr.returnId and poOrder = 0 " +
					"and ot.seller_Id=:sellerId and orr.returnDate between :startDate AND :endDate group by ot.pcName";
			if("category".equalsIgnoreCase(criteria))
				mpReturnQueryStr = "select cat.parentCatName, sum((ot.partnerCommission+ot.pccAmount+ot.fixedFee+ot.shippingCharges) * orr.returnorrtoQty * 1.15) as returnComm, " +
						"sum(estimateddeduction) as addRetCharges, sum(orr.returnorrtoQty) as returnQty, sum(otx.tdsToReturn - otx.tdsonReturnAmt) as tdsToReturn from order_table ot, " +
						"orderreturn orr, ordertax otx, product pr, category cat where pr.category_categoryId = cat.categoryId and ot.productSkuCode = pr.productSkuCode and " +
						"ot.orderTax_taxId = otx.taxId and ot.orderReturnOrRTO_returnId = orr.returnId and poOrder = 0 and ot.seller_Id=:sellerId and " +
						"ot.seller_id=pr.seller_Id and orr.returnDate between :startDate AND :endDate group by cat.parentCatName";
			Query mpReturnQuery = session.createSQLQuery(mpReturnQueryStr)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			orderList2 = mpReturnQuery.list();
			for(Object[] order: orderList2){
				String key = order[0].toString();
				CommissionDetails commDetails = commDetailsMap.get(key);
				double existTDS = 0;
				int existRQ = 0;
				if(commDetails != null){
					existTDS = commDetails.getNetTDSToBeDeposited(); 
					existRQ = commDetails.getNetSaleQty();
				}
				else
					commDetails = new CommissionDetails();
				if("category".equalsIgnoreCase(criteria))
					commDetails.setCategoryName(key);
				else
					commDetails.setPartner(key);
				double returnCommission = Double.parseDouble(order[1].toString());
				double additionalRetCharges = Double.parseDouble(order[2].toString());
				int returnQty = Integer.parseInt(order[3].toString());
				double tdsToReturn = Double.parseDouble(order[4].toString());
				commDetails.setNetReturnCommission(returnCommission);
				commDetails.setAdditionalReturnCharges(additionalRetCharges);
				commDetails.setNetSaleQty(existRQ - returnQty);
				commDetails.setNetTDSToBeDeposited(existTDS - tdsToReturn);
				commDetailsMap.put(key, commDetails);
			}

			String poReturnQueryStr = "select ot.pcName, sum(ot.partnerCommission+otx.taxSP-orr.taxPOAmt) as returnComm, " +
					"sum(estimateddeduction) as addRetCharges, sum(orr.returnorrtoQty) as returnQty, sum(otx.tdsToReturn - otx.tdsonReturnAmt) as tdsToReturn " +
					"from order_table ot, ordertax otx, orderreturn orr where ot.orderReturnOrRTO_returnId = orr.returnId and " +
					"ot.orderTax_taxId = otx.taxId and ot.poOrder = 1 and ot.consolidatedOrder_orderId is null and ot.seller_Id=:sellerId " +
					"and orr.returnDate between :startDate AND :endDate group by ot.pcName";
			if("category".equalsIgnoreCase(criteria))
				poReturnQueryStr = "select 'B2B', sum(ot.partnerCommission+otx.taxSP-orr.taxPOAmt), " +
						"sum(estimateddeduction) as returnComm, sum(orr.returnorrtoQty) as returnQty, sum(otx.tdsToReturn - otx.tdsonReturnAmt) as tdsToReturn  " +
						"from order_table ot, ordertax otx, orderreturn orr where " +
						"ot.orderReturnOrRTO_returnId = orr.returnId and ot.orderTax_taxId = otx.taxId and ot.poOrder = 1 and " +
						"ot.consolidatedOrder_orderId is null and ot.seller_Id=:sellerId " +
						"and orr.returnDate between :startDate AND :endDate";
			Query poReturnQuery = session.createSQLQuery(poReturnQueryStr)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			orderList2 = poReturnQuery.list();
			for(Object[] order: orderList2){
				String key = order[0].toString();
				CommissionDetails commDetails = commDetailsMap.get(key);
				double existRC = 0;
				double existARC = 0;
				int existRQ = 0;
				double existTDS = 0;
				if(commDetails!=null){
					existRC = commDetails.getNetReturnCommission();
					existARC = commDetails.getAdditionalReturnCharges();
					existRQ = commDetails.getNetSaleQty();
					existTDS = commDetails.getNetTDSToBeDeposited(); 
				}				
				else
					commDetails = new CommissionDetails();
				if("category".equalsIgnoreCase(criteria))
					commDetails.setCategoryName(key);
				else
					commDetails.setPartner(key);
				double returnCommission = Double.parseDouble(order[1].toString());
				double additionalRetCharges = Double.parseDouble(order[2].toString());
				int returnQty = Integer.parseInt(order[3].toString());
				double tdsToReturn = Double.parseDouble(order[4].toString());
				commDetails.setNetSaleQty(existRQ - returnQty);
				commDetails.setNetReturnCommission(existRC + returnCommission);
//				commDetails.setAdditionalReturnCharges(existARC + additionalRetCharges);
				commDetails.setNetTDSToBeDeposited(tdsToReturn - existTDS);
				commDetailsMap.put(key, commDetails);
			}			
			Iterator entries = commDetailsMap.entrySet().iterator();
			while (entries.hasNext()) {
				Entry<String, CommissionDetails> thisEntry = (Entry<String, CommissionDetails>) entries
						.next();
				CommissionDetails commDetails = thisEntry.getValue();
				double gc = commDetails.getGrossPartnerCommissionPaid();
				double rc = commDetails.getNetReturnCommission();
				double arc = commDetails.getAdditionalReturnCharges();
				commDetails.setNetSrCommisison(rc - arc);
				commDetails.setNetChannelCommissionToBePaid(gc - rc + arc);
				commDetails.setNetPartnerCommissionPaid(gc - rc + arc);
				commDetailsList.add(commDetails);
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : "+sellerId,e);					
		} catch (Exception e) {
			log.error("Failed by SellerID : "+sellerId, e);
			e.printStackTrace();
		}
		log.info("*** fetchPC ends : ReportsGeneratorDaoImpl ****");

		return commDetailsList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ChannelNR> fetchChannelNR(int sellerId, Date startDate, Date endDate, String criteria) {
		log.info("*** fetchChannelNR starts : ReportsGeneratorDaoImpl ****");

		List<ChannelNR> channelNRList = new ArrayList<ChannelNR>();
		Map<String, ChannelNR> channelNRMap = new HashMap<String, ChannelNR>();
		try {
			Session session=sessionFactory.openSession();
			session.getTransaction().begin();
			String orderQueryStr = "select pcName, sum(netRate) as NetRate from order_table where finalStatus = 'Actionable' and " +
					"seller_Id=:sellerId and shippedDate between :startDate AND :endDate group by pcName order by NetRate";
			if("category".equals(criteria))
				orderQueryStr = "select cat.parentCatName, sum(ot.netRate) as NetRate from order_table ot, product pr, category cat where ot.finalStatus = 'Actionable' " +
						"and pr.category_categoryId = cat.categoryId and ot.productSkuCode = pr.productSkuCode and ot.seller_Id=:sellerId and " +
						"ot.seller_id=pr.seller_Id and ot.shippedDate between :startDate AND :endDate group by cat.parentCatName order by NetRate";
			Query orderQuery = session.createSQLQuery(orderQueryStr)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			List<Object[]> orderList = orderQuery.list();
			for(Object[] order: orderList){
				ChannelNR channelNR = new ChannelNR();
				String key = order[0].toString();
				channelNR.setKey(key);
				double actionableNR = Double.parseDouble(order[1].toString());
				channelNR.setActionableNR(actionableNR);
				channelNR.setTotalNR(actionableNR);
				channelNRMap.put(key,channelNR);
			}
			
			orderQueryStr = "select pcName, sum(netRate) as NetRate from order_table where finalStatus = 'Settled' and " +
					"seller_Id=:sellerId and shippedDate between :startDate AND :endDate group by pcName order by NetRate";
			if("category".equals(criteria))
				orderQueryStr = "select cat.parentCatName, sum(ot.netRate) as NetRate from order_table ot, product pr, category cat where ot.finalStatus = 'Settled' " +
						"and pr.category_categoryId = cat.categoryId and ot.productSkuCode = pr.productSkuCode and ot.seller_Id=:sellerId and " +
						"ot.seller_id=pr.seller_Id and ot.shippedDate between :startDate AND :endDate group by cat.parentCatName order by NetRate";
			orderQuery = session.createSQLQuery(orderQueryStr)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			orderList = orderQuery.list();
			for(Object[] order: orderList){
				String key = order[0].toString();
				ChannelNR channelNR = channelNRMap.get(key);
				if(channelNR == null){
					channelNR = new ChannelNR();
					channelNR.setKey(key);
				}
				double settledNR = Double.parseDouble(order[1].toString());
				channelNR.setSettledNR(settledNR);
				channelNR.setTotalNR(channelNR.getTotalNR() + settledNR);
				channelNRMap.put(key,channelNR);
			}
			
			orderQueryStr = "select pcName, sum(netRate) as NetRate from order_table where finalStatus = 'In Process' and " +
					"seller_Id=:sellerId and shippedDate between :startDate AND :endDate group by pcName order by NetRate";
			if("category".equals(criteria))
				orderQueryStr = "select cat.parentCatName, sum(ot.netRate) as NetRate from order_table ot, product pr, category cat where ot.finalStatus = 'In Process' " +
						"and pr.category_categoryId = cat.categoryId and ot.productSkuCode = pr.productSkuCode and ot.seller_Id=:sellerId and " +
						"ot.seller_id=pr.seller_Id and ot.shippedDate between :startDate AND :endDate group by cat.parentCatName order by NetRate";
			orderQuery = session.createSQLQuery(orderQueryStr)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			orderList = orderQuery.list();
			for(Object[] order: orderList){
				String key = order[0].toString();
				ChannelNR channelNR = channelNRMap.get(key);
				if(channelNR == null){
					channelNR = new ChannelNR();
					channelNR.setKey(key);
				}
				double inProcessNR = Double.parseDouble(order[1].toString());
				channelNR.setInProcessNR(inProcessNR);
				channelNR.setTotalNR(channelNR.getTotalNR() + inProcessNR);
				channelNRMap.put(key,channelNR);
			}
			Iterator entries = channelNRMap.entrySet().iterator();
			while (entries.hasNext()) {
				Entry<String, ChannelNR> thisEntry = (Entry<String, ChannelNR>) entries.next();
				channelNRList.add(thisEntry.getValue());
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : "+sellerId,e);					
		} catch (Exception e) {
			log.error("Failed by SellerID : "+sellerId, e);
		}
		log.info("*** fetchChannelNR ends : ReportsGeneratorDaoImpl ****");

		return channelNRList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MonthlyCommission> fetchMonthlyComm(int sellerId, Date startDate, Date endDate) {
		log.info("*** fetchMonthlyComm starts : ReportsGeneratorDaoImpl ****");

		List<MonthlyCommission> monthlyList = new ArrayList<MonthlyCommission>();
		Map<String, MonthlyCommission> monthlyMap = new HashMap<String, MonthlyCommission>();
		try {
			Session session=sessionFactory.openSession();
			session.getTransaction().begin();
			String queryStr = "select concat(monthname(ot.shippedDate), ' ', year(ot.shippedDate)) as Month, " +
					"sum((ot.partnerCommission+ot.pccAmount+ot.fixedFee+ot.shippingCharges) * ot.quantity * 1.15) " +
					"as grossComm from order_table ot " +
					"where ot.poOrder = 0  and ot.seller_Id=:sellerId and ot.shippedDate " +
					"between :startDate AND :endDate group by Month";
			Query orderQuery = session.createSQLQuery(queryStr)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			List<Object[]> orderList = orderQuery.list();
			for(Object[] order: orderList){
				MonthlyCommission monthlyComm = new MonthlyCommission();
				String key = order[0].toString();
				monthlyComm.setKey(key);
				double grossCommission = Double.parseDouble(order[1].toString());
				monthlyComm.setGrossCommission(grossCommission);
				monthlyComm.setNetCommission(grossCommission);
				monthlyMap.put(key, monthlyComm);
			}
			
			queryStr = "select concat(monthname(orr.returnDate), ' ', year(orr.returnDate)) as Month, " +
					"sum((ot.partnerCommission+ot.pccAmount+ot.fixedFee+ot.shippingCharges) * orr.returnorrtoQty) " +
					"as returnComm, sum(orr.estimateddeduction * orr.returnorrtoQty) as additionalCharges from order_table ot, orderreturn orr " +
					"where ot.orderReturnOrRTO_returnId = orr.returnId and ot.poOrder = 0  and ot.seller_Id=:sellerId and orr.returnDate " +
					"between :startDate AND :endDate group by Month";
			orderQuery = session.createSQLQuery(queryStr)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			orderList = orderQuery.list();
			for(Object[] order: orderList){
				String key = order[0].toString();
				MonthlyCommission monthlyComm = monthlyMap.get(key);
				if(monthlyComm == null)
					monthlyComm = new MonthlyCommission();
				monthlyComm.setKey(key);
				double returnCommission = Double.parseDouble(order[1].toString());
				double additionalCharges = Double.parseDouble(order[2].toString());
				monthlyComm.setReturnCommission(returnCommission);
				monthlyComm.setAdditionalCharges(additionalCharges);
				double grossCommission = monthlyComm.getGrossCommission();
				monthlyComm.setNetCommission(grossCommission - returnCommission + additionalCharges);
				monthlyMap.put(key, monthlyComm);
			}
			
			queryStr = "select concat(monthname(ot.shippedDate), ' ', year(ot.shippedDate)) as Month, " +
					"sum(ot.partnerCommission+otx.taxSP-otx.tax) as grossComm from order_table ot, ordertax otx " +
					"where ot.orderTax_taxId = otx.taxId and ot.poOrder = 1 " +
					"and ot.consolidatedOrder_orderId is null and ot.seller_Id=:sellerId and ot.shippedDate " +
					"between :startDate AND :endDate group by Month";
			orderQuery = session.createSQLQuery(queryStr)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			orderList = orderQuery.list();
			for(Object[] order: orderList){
				String key = order[0].toString();
				MonthlyCommission monthlyComm = monthlyMap.get(key);
				if(monthlyComm == null)
					monthlyComm = new MonthlyCommission();
				monthlyComm.setKey(key);
				double additionalCharges = monthlyComm.getAdditionalCharges();
				double grossCommission = monthlyComm.getGrossCommission();
				double returnCommission = monthlyComm.getReturnCommission();
				monthlyComm.setGrossCommission(grossCommission + Double.parseDouble(order[1].toString()));
				grossCommission = monthlyComm.getGrossCommission();
				monthlyComm.setNetCommission(grossCommission - returnCommission + additionalCharges);
				monthlyMap.put(key, monthlyComm);
			}
			
			queryStr = "select concat(monthname(orr.returnDate), ' ', year(orr.returnDate)) as Month, " +
					"sum(ot.partnerCommission+otx.taxSP-orr.taxPOAmt) " +
					"as returnComm from order_table ot, ordertax otx, orderreturn orr " +
					"where ot.orderReturnOrRTO_returnId = orr.returnId and ot.orderTax_taxId = otx.taxId and ot.poOrder = 1 and ot.consolidatedOrder_orderId is null " +
					"and ot.seller_Id=:sellerId and orr.returnDate between :startDate AND :endDate group by Month";
			orderQuery = session.createSQLQuery(queryStr)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			orderList = orderQuery.list();
			for(Object[] order: orderList){
				String key = order[0].toString();
				MonthlyCommission monthlyComm = monthlyMap.get(key);
				if(monthlyComm == null)
					monthlyComm = new MonthlyCommission();
				monthlyComm.setKey(key);
				double additionalCharges = monthlyComm.getAdditionalCharges();
				double grossCommission = monthlyComm.getGrossCommission();
				double returnCommission = monthlyComm.getReturnCommission();
				monthlyComm.setReturnCommission(returnCommission + Double.parseDouble(order[1].toString()));
				returnCommission = monthlyComm.getReturnCommission();
				monthlyComm.setNetCommission(grossCommission - returnCommission + additionalCharges);
				monthlyMap.put(key, monthlyComm);
			}

			Iterator entries = monthlyMap.entrySet().iterator();
			while (entries.hasNext()) {
				Entry<String, MonthlyCommission> thisEntry = (Entry<String, MonthlyCommission>) entries.next();
				monthlyList.add(thisEntry.getValue());
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : "+sellerId,e);					
		} catch (Exception e) {
			log.error("Failed by SellerID : "+sellerId, e);
			e.printStackTrace();
		}
		log.info("*** fetchMonthlyComm end : ReportsGeneratorDaoImpl ****");

		return monthlyList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ChannelNetQty> fetchChannelNetQty(int sellerId, Date startDate, Date endDate, String criteria) {
		log.info("*** fetchChannelNetQty start : ReportsGeneratorDaoImpl ****");

		List<ChannelNetQty> channelNRList = new ArrayList<ChannelNetQty>();
		Map<String, ChannelNetQty> channelNRMap = new HashMap<String, ChannelNetQty>();
		try {
			Session session=sessionFactory.openSession();
			session.getTransaction().begin();
			String orderQueryStr = "select ot.pcName, sum(ot.quantity - orr.returnorrtoQty) as NetSaleQuantity from order_table ot, orderreturn orr " +
					"where ot.finalStatus = 'Actionable' and ot.orderReturnOrRTO_returnId = orr.returnId and ot.seller_Id=:sellerId and " +
					"((ot.shippedDate between :startDate AND :endDate) or (orr.returnDate between :startDate AND :endDate)) group by ot.pcName order by NetSaleQuantity";
			if("category".equals(criteria))
				orderQueryStr = "select cat.parentCatName, sum(ot.quantity - orr.returnorrtoQty) as NetSaleQuantity from order_table ot, product pr, category cat, " +
						"orderreturn orr where ot.orderReturnOrRTO_returnId = orr.returnId and ot.finalStatus = 'Actionable' " +
						"and pr.category_categoryId = cat.categoryId and ot.productSkuCode = pr.productSkuCode and ot.seller_Id=:sellerId and " +
						"ot.seller_id=pr.seller_Id and ((ot.shippedDate between :startDate AND :endDate) or (orr.returnDate between :startDate AND :endDate)) group by cat.parentCatName order by NetSaleQuantity";
			Query orderQuery = session.createSQLQuery(orderQueryStr)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			List<Object[]> orderList = orderQuery.list();
			for(Object[] order: orderList){
				ChannelNetQty channelNR = new ChannelNetQty();
				String key = order[0].toString();
				channelNR.setKey(key);
				int actionableQty = Integer.parseInt(order[1].toString());
				channelNR.setActionableQty(actionableQty);
				channelNR.setTotalQty(actionableQty);
				channelNRMap.put(key,channelNR);
			}
			
			orderQueryStr = "select ot.pcName, sum(ot.quantity - orr.returnorrtoQty) as NetSaleQuantity from order_table ot, orderreturn orr " +
					"where ot.finalStatus = 'Settled' and ot.orderReturnOrRTO_returnId = orr.returnId and ot.seller_Id=:sellerId and " +
					"((ot.shippedDate between :startDate AND :endDate) or (orr.returnDate between :startDate AND :endDate)) group by ot.pcName order by NetSaleQuantity";
			if("category".equals(criteria))
				orderQueryStr = "select cat.parentCatName, sum(ot.quantity - orr.returnorrtoQty) as NetSaleQuantity from order_table ot, product pr, category cat, " +
						"orderreturn orr where ot.orderReturnOrRTO_returnId = orr.returnId and ot.finalStatus = 'Settled' " +
						"and pr.category_categoryId = cat.categoryId and ot.productSkuCode = pr.productSkuCode and ot.seller_Id=:sellerId and " +
						"ot.seller_id=pr.seller_Id and ((ot.shippedDate between :startDate AND :endDate) or (orr.returnDate between :startDate AND :endDate)) group by cat.parentCatName order by NetSaleQuantity";
			orderQuery = session.createSQLQuery(orderQueryStr)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			orderList = orderQuery.list();
			for(Object[] order: orderList){
				String key = order[0].toString();
				ChannelNetQty channelNR = channelNRMap.get(key);
				if(channelNR == null){
					channelNR = new ChannelNetQty();
					channelNR.setKey(key);
				}
				int settledQty = Integer.parseInt(order[1].toString());
				channelNR.setSettledQty(settledQty);
				channelNR.setTotalQty(channelNR.getTotalQty() + settledQty); 
				channelNRMap.put(key,channelNR);
			}
			
			orderQueryStr = "select ot.pcName, sum(ot.quantity - orr.returnorrtoQty) as NetSaleQuantity from order_table ot, orderreturn orr " +
					"where ot.finalStatus = 'In Process' and ot.orderReturnOrRTO_returnId = orr.returnId and ot.seller_Id=:sellerId and " +
					"((ot.shippedDate between :startDate AND :endDate) or (orr.returnDate between :startDate AND :endDate)) group by ot.pcName order by NetSaleQuantity";
			if("category".equals(criteria))
				orderQueryStr = "select cat.parentCatName, sum(ot.quantity - orr.returnorrtoQty) as NetSaleQuantity from order_table ot, product pr, category cat, " +
						"orderreturn orr where ot.orderReturnOrRTO_returnId = orr.returnId and ot.finalStatus = 'In Process' " +
						"and pr.category_categoryId = cat.categoryId and ot.productSkuCode = pr.productSkuCode and ot.seller_Id=:sellerId and " +
						"ot.seller_id=pr.seller_Id and ((ot.shippedDate between :startDate AND :endDate) or (orr.returnDate between :startDate AND :endDate)) group by cat.parentCatName order by NetSaleQuantity";
			orderQuery = session.createSQLQuery(orderQueryStr)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			orderList = orderQuery.list();
			for(Object[] order: orderList){
				String key = order[0].toString();
				ChannelNetQty channelNR = channelNRMap.get(key);
				if(channelNR == null){
					channelNR = new ChannelNetQty();
					channelNR.setKey(key);
				}
				int inProcessQty = Integer.parseInt(order[1].toString());
				channelNR.setInProcessQty(inProcessQty);
				channelNR.setTotalQty(channelNR.getTotalQty() + inProcessQty); 
				channelNRMap.put(key,channelNR);
			}
			Iterator entries = channelNRMap.entrySet().iterator();
			while (entries.hasNext()) {
				Entry<String, ChannelNetQty> thisEntry = (Entry<String, ChannelNetQty>) entries.next();
				channelNRList.add(thisEntry.getValue());
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : "+sellerId,e);					
		} catch (Exception e) {
			log.error("Failed by SellerID : "+sellerId, e);
			e.printStackTrace();
		}
		log.info("*** fetchChannelNetQty end : ReportsGeneratorDaoImpl ****");

		return channelNRList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ChannelGP> fetchChannelGP(int sellerId, Date startDate, Date endDate, String criteria) {
		log.info("*** fetchChannelGP start : ReportsGeneratorDaoImpl ****");

		List<ChannelGP> channelNRList = new ArrayList<ChannelGP>();
		Map<String, ChannelGP> channelNRMap = new HashMap<String, ChannelGP>();
		try {
			Session session=sessionFactory.openSession();
			session.getTransaction().begin();
			String orderQueryStr = "select pcName, sum(grossProfit) as grossProfit from order_table where finalStatus = 'Actionable' and " +
					"seller_Id=:sellerId and shippedDate between :startDate AND :endDate group by pcName order by NetSaleQuantity";
			if("category".equals(criteria))
				orderQueryStr = "select cat.parentCatName, sum(ot.grossProfit) as NetSaleQuantity from order_table ot, product pr, category cat where ot.finalStatus = 'Actionable' " +
						"and pr.category_categoryId = cat.categoryId and ot.productSkuCode = pr.productSkuCode and ot.seller_Id=:sellerId and " +
						"ot.seller_id=pr.seller_Id and ot.shippedDate between :startDate AND :endDate group by cat.parentCatName order by grossProfit";
			Query orderQuery = session.createSQLQuery(orderQueryStr)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			List<Object[]> orderList = orderQuery.list();
			for(Object[] order: orderList){
				ChannelGP channelNR = new ChannelGP();
				String key = order[0].toString();
				channelNR.setKey(key);
				channelNR.setActionableGP(new Double(order[1].toString()));
				channelNRMap.put(key,channelNR);
			}
			
			orderQueryStr = "select pcName, sum(grossProfit) as grossProfit from order_table where finalStatus = 'Settled' and " +
					"seller_Id=:sellerId and shippedDate between :startDate AND :endDate group by pcName order by grossProfit";
			if("category".equals(criteria))
				orderQueryStr = "select cat.parentCatName, sum(ot.grossProfit) as NetSaleQuantity from order_table ot, product pr, category cat where ot.finalStatus = 'Settled' " +
						"and pr.category_categoryId = cat.categoryId and ot.productSkuCode = pr.productSkuCode and ot.seller_Id=:sellerId and " +
						"ot.seller_id=pr.seller_Id and ot.shippedDate between :startDate AND :endDate group by cat.parentCatName order by grossProfit";
			orderQuery = session.createSQLQuery(orderQueryStr)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			orderList = orderQuery.list();
			for(Object[] order: orderList){
				String key = order[0].toString();
				ChannelGP channelNR = channelNRMap.get(key);
				if(channelNR == null){
					channelNR = new ChannelGP();
					channelNR.setKey(key);
				}
				channelNR.setSettledGP(new Double(order[1].toString()));
				channelNRMap.put(key,channelNR);
			}
			
			orderQueryStr = "select pcName, sum(grossProfit) as grossProfit from order_table where finalStatus = 'In Process' and " +
					"seller_Id=:sellerId and shippedDate between :startDate AND :endDate group by pcName order by grossProfit";
			if("category".equals(criteria))
				orderQueryStr = "select cat.parentCatName, sum(ot.grossProfit) as NetSaleQuantity from order_table ot, product pr, category cat where ot.finalStatus = 'In Process' " +
						"and pr.category_categoryId = cat.categoryId and ot.productSkuCode = pr.productSkuCode and ot.seller_Id=:sellerId and " +
						"ot.seller_id=pr.seller_Id and ot.shippedDate between :startDate AND :endDate group by cat.parentCatName order by grossProfit";
			orderQuery = session.createSQLQuery(orderQueryStr)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			orderList = orderQuery.list();
			for(Object[] order: orderList){
				String key = order[0].toString();
				ChannelGP channelNR = channelNRMap.get(key);
				if(channelNR == null){
					channelNR = new ChannelGP();
					channelNR.setKey(key);
				}
				channelNR.setInProcessGP(new Double(order[1].toString()));
				channelNRMap.put(key,channelNR);
			}
			Iterator entries = channelNRMap.entrySet().iterator();
			while (entries.hasNext()) {
				Entry<String, ChannelGP> thisEntry = (Entry<String, ChannelGP>) entries.next();
				channelNRList.add(thisEntry.getValue());
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : "+sellerId,e);					
		} catch (Exception e) {
			log.error("Failed by Seller ID : "+sellerId, e);
			e.printStackTrace();
		}
		log.info("*** fetchChannelGP end : ReportsGeneratorDaoImpl ****");

		return channelNRList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ChannelMC> fetchChannelMC(int sellerId, Date startDate, Date endDate, String criteria) {
		log.info("*** fetchChannelMC start : ReportsGeneratorDaoImpl ****");

		List<ChannelMC> channelMCList = new ArrayList<ChannelMC>();
		try {
			Session session=sessionFactory.openSession();
			session.getTransaction().begin();
			String orderQueryStr = "SELECT mc.partner, sum(mc.paidAmount) as ManualCharges FROM paymentupload pu, manualcharges mc " +
					"where pu.uploadId = mc.chargesDesc and pu.uploadDate between :startDate AND :endDate group by mc.partner order by ManualCharges desc";
			Query orderQuery = session.createSQLQuery(orderQueryStr)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate);
			List<Object[]> orderList = orderQuery.list();
			for(Object[] order: orderList){
				ChannelMC channelMC = new ChannelMC();
				channelMC.setPartner(order[0].toString());
				channelMC.setManualCharges(new Double(order[1].toString()));
				channelMCList.add(channelMC);
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : "+sellerId,e);					
		} catch (Exception e) {
			log.error("Failed By Seller Id : "+sellerId, e);
		}
		log.info("*** fetchChannelMC end : ReportsGeneratorDaoImpl ****");

		return channelMCList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ChannelCatNPR> fetchChannelCatNPR(int sellerId, Date startDate, Date endDate, String criteria) {
		log.info("*** fetchChannelCatNPR start : ReportsGeneratorDaoImpl ****");

		List<ChannelCatNPR> channelNPRList = new ArrayList<ChannelCatNPR>();
		try {
			Session session=sessionFactory.openSession();
			session.getTransaction().begin();
			String mpOrderQueryStr = "select ot.pcName, cat.parentCatName, sum(op.netPaymentResult) as 'NPR' from order_table ot, orderpay op " +
					", product pr, category cat where pr.category_categoryId = cat.categoryId and ot.productSkuCode = pr.productSkuCode and " +
					"ot.orderPayment_paymentId = op.paymentId and ot.poOrder = 0 " +
					"and ot.seller_Id=:sellerId and ot.seller_id=pr.seller_Id and op.dateOfPayment between :startDate AND :endDate group by ot.pcName, " +
					"cat.parentCatName order by ot.pcName";
			Query mpOrderQuery = session.createSQLQuery(mpOrderQueryStr)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			List<Object[]> orderList = mpOrderQuery.list();
			Set<String> categorySet = new HashSet<String>();
			categorySet.add("B2B");
			for(Object[] order: orderList){
				categorySet.add(order[1].toString());
			}
			List<String> catList = new ArrayList<>();
			catList.addAll(categorySet);
			Map<String, ChannelCatNPR> channelCatMap = new HashMap<String, ChannelCatNPR>();
			for(Object[] order: orderList){
				String currPartner = order[0].toString();
				ChannelCatNPR channelCatNPR = channelCatMap.get(currPartner);
				List<Double> netNPRList;
				if(channelCatNPR == null){
					channelCatNPR = new ChannelCatNPR();
					netNPRList = new ArrayList<Double>();
					int counter = 0;
					while(counter++ < catList.size()){
						netNPRList.add(new Double(0));
					}
					channelCatNPR.setPartner(currPartner);
					channelCatNPR.setNetNPR(netNPRList);
				}
				netNPRList = channelCatNPR.getNetNPR();
				double totalNPR = 0;
				for(int index =0; index<catList.size(); index++){
					String category = catList.get(index);
					if(category.equals(order[1].toString())){
						double netNPR = new Double(order[2].toString());
						netNPRList.set(index, netNPR);
						totalNPR += netNPR;
						break;
					}
				}
				channelCatNPR.setNetNPR(netNPRList);
				channelCatNPR.setTotalNPR(totalNPR);
				channelCatMap.put(currPartner, channelCatNPR);
			}
			
			String poOrderQueryStr = "select ot.pcName, 'B2B', sum(op.netPaymentResult) as 'NPR' from order_table ot, orderpay op " +
					"where ot.orderPayment_paymentId = op.paymentId and ot.poOrder = 1 and ot.consolidatedOrder_orderId is null " +
					"and ot.seller_Id=:sellerId and op.dateOfPayment between :startDate AND :endDate group by ot.pcName order by ot.pcName";
			Query poOrderQuery = session.createSQLQuery(poOrderQueryStr)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			orderList = poOrderQuery.list();
			for(Object[] order: orderList){
				String currPartner = order[0].toString();
				ChannelCatNPR channelCatNPR = channelCatMap.get(currPartner);
				List<Double> netNPRList;
				if(channelCatNPR == null){
					channelCatNPR = new ChannelCatNPR();
					netNPRList = new ArrayList<Double>();
					int counter = 0;
					while(counter++ < catList.size()){
						netNPRList.add(new Double(0));
					}
					channelCatNPR.setPartner(currPartner);
					channelCatNPR.setNetNPR(netNPRList);
				}
				netNPRList = channelCatNPR.getNetNPR();
				double totalNPR = 0;
				for(int index =0; index<catList.size(); index++){
					String category = catList.get(index);
					if(category.equals(order[1].toString())){
						double netNPR = new Double(order[2].toString());
						netNPRList.set(index, netNPR);
						totalNPR += netNPR;
						break;
					}
				}
				channelCatNPR.setNetNPR(netNPRList);
				channelCatNPR.setTotalNPR(totalNPR);
				channelCatMap.put(currPartner, channelCatNPR);
			}
			
			Iterator entries = channelCatMap.entrySet().iterator();
			while (entries.hasNext()) {
				Entry<String, ChannelCatNPR> thisEntry = (Entry<String, ChannelCatNPR>) entries
						.next();
				ChannelCatNPR commDetails = thisEntry.getValue();
				channelNPRList.add(commDetails);
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : "+sellerId,e);					
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed by Seller ID : "+sellerId, e);
		}
		log.info("*** fetchChannelMC end : ReportsGeneratorDaoImpl ****");

		return channelNPRList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ChannelNPR> fetchChannelNPR(int sellerId, Date startDate, Date endDate, String criteria) {
		log.info("*** fetchChannelNPR start : ReportsGeneratorDaoImpl ****");

		List<ChannelNPR> channelNPRList = new ArrayList<ChannelNPR>();
		Map<String, ChannelNPR> channelNprMap = new HashMap<String, ChannelNPR>(); 
		try {
			Session session=sessionFactory.openSession();
			session.getTransaction().begin();
			String mpOrderQueryStr = "SELECT ot.pcName, ot.paymentType, sum(op.netPaymentResult) as 'NPR' FROM order_table ot, orderpay op where " +
					"ot.orderPayment_paymentId = op.paymentId and (ot.poOrder = 0 or (ot.poOrder = 1 and ot.consolidatedOrder_orderId is null)) " +
					"and ot.seller_Id=:sellerId and op.dateOfPayment between :startDate AND :endDate group by ot.pcName, ot.paymentType";
			if("category".equalsIgnoreCase(criteria))
				mpOrderQueryStr = "select cat.parentCatName, ot.paymentType, sum(op.netPaymentResult) as 'NPR' from order_table ot, orderpay op " +
					", product pr, category cat where pr.category_categoryId = cat.categoryId and ot.productSkuCode = pr.productSkuCode and " +
					"ot.orderPayment_paymentId = op.paymentId and ot.poOrder = 0 " +
					"and ot.seller_id=pr.seller_Id and ot.seller_Id=:sellerId and op.dateOfPayment between :startDate AND :endDate group by cat.parentCatName, ot.paymentType";
			Query mpOrderQuery = session.createSQLQuery(mpOrderQueryStr)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			List<Object[]> orderList = mpOrderQuery.list();
			for(Object[] order: orderList){
				String key = order[0].toString();
				ChannelNPR channelNPR = channelNprMap.get(key);
				if(channelNPR == null){
					channelNPR = new ChannelNPR();
				}
				channelNPR.setCategory(key);
				channelNPR.setPartner(key);
				String paymentType;
				if(order[1] != null)
					paymentType = order[1].toString();
				else
					paymentType = "B2B";
				double npr = Double.parseDouble(order[2].toString());
				switch(paymentType){
					case "Prepaid": channelNPR.setPrepaidNPR(npr); break;
					case "COD": channelNPR.setCodNPR(npr); break;
					default: channelNPR.setB2bNPR(npr); break;
				}
				channelNPR.setNetNPR(npr + channelNPR.getNetNPR());
				channelNprMap.put(key, channelNPR);
			}
			
			if("category".equalsIgnoreCase(criteria)){
				String poOrderQueryStr = "select 'B2B', 'B2B', sum(op.netPaymentResult) as 'NPR' from order_table ot, orderpay op " +
						"where ot.orderPayment_paymentId = op.paymentId and ot.poOrder = 1 and ot.consolidatedOrder_orderId is null " +
						"and ot.seller_Id=:sellerId and op.dateOfPayment between :startDate AND :endDate";
				Query poOrderQuery = session.createSQLQuery(poOrderQueryStr)
						.setParameter("startDate", startDate)
						.setParameter("endDate", endDate)
						.setParameter("sellerId", sellerId);
				orderList = poOrderQuery.list();
				for(Object[] order: orderList){
					String key = order[0].toString();
					ChannelNPR channelNPR = channelNprMap.get(key);
					if(channelNPR == null){
						channelNPR = new ChannelNPR();
					}
					channelNPR.setCategory(key);
					channelNPR.setPartner(key);
					String paymentType = order[1].toString();
					double npr = Double.parseDouble(order[2]!=null?order[2].toString():"0");
					switch(paymentType){
						case "Prepaid": channelNPR.setPrepaidNPR(npr); break;
						case "COD": channelNPR.setCodNPR(npr); break;
						default: channelNPR.setB2bNPR(npr); break;
					}
					channelNPR.setNetNPR(npr + channelNPR.getNetNPR());
					channelNprMap.put(key, channelNPR);
				}
			}			
			
			Iterator entries = channelNprMap.entrySet().iterator();
			while (entries.hasNext()) {
				Entry<String, ChannelNPR> thisEntry = (Entry<String, ChannelNPR>) entries
						.next();
				ChannelNPR channelNPR = thisEntry.getValue();
				channelNPRList.add(channelNPR);
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : "+sellerId,e);					
		} catch (Exception e) {
			log.error("Failed By Seller Id : "+sellerId, e);
			e.printStackTrace();
		}
		log.info("*** fetchChannelNPR start : ReportsGeneratorDaoImpl ****");

		return channelNPRList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ChannelMCNPR> fetchChannelMCNPR(int sellerId, Date startDate, Date endDate, String criteria) {
		log.info("*** fetchChannelMCNPR start : ReportsGeneratorDaoImpl ****");

		List<ChannelMCNPR> channelNPRList = new ArrayList<ChannelMCNPR>();
		Map<String, ChannelMCNPR> channelNprMap = new HashMap<String, ChannelMCNPR>(); 
		try {
			Session session=sessionFactory.openSession();
			session.getTransaction().begin();
			String mpOrderQueryStr = "SELECT ot.pcName, ot.paymentType, sum(op.netPaymentResult) as 'NPR', sum(op.positiveAmount) as 'Positive Amount', " +
					"sum(op.negativeAmount) as 'Negative Amount' FROM order_table ot, orderpay op where ot.orderPayment_paymentId = op.paymentId and " +
					"(ot.poOrder = 0 or (ot.poOrder = 1 and ot.consolidatedOrder_orderId is null)) and ot.seller_Id=:sellerId and " +
					"op.dateOfPayment between :startDate AND :endDate group by ot.pcName, ot.paymentType";
			Query mpOrderQuery = session.createSQLQuery(mpOrderQueryStr)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			List<Object[]> orderList = mpOrderQuery.list();
			for(Object[] order: orderList){
				String key1 = order[0].toString();
				String key2;
				if(order[1] != null)
					key2 = order[1].toString();
				else
					key2 = "B2B";
				ChannelMCNPR channelNPR = new ChannelMCNPR();
				channelNPR.setPartner(key1);
				channelNPR.setPaymentType(key2);
				channelNPR.setBaseNPR(Double.parseDouble(order[2].toString()));
				channelNPR.setPositiveAmount(Double.parseDouble(order[3].toString()));
				channelNPR.setNegativeAmount(Double.parseDouble(order[4].toString()));
				channelNprMap.put(key1 + key2, channelNPR);
			}
			
			Iterator entries = channelNprMap.entrySet().iterator();
			while (entries.hasNext()) {
				Entry<String, ChannelMCNPR> thisEntry = (Entry<String, ChannelMCNPR>) entries
						.next();
				ChannelMCNPR channelNPR = thisEntry.getValue();
				channelNPRList.add(channelNPR);
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : "+sellerId,e);					
		} catch (Exception e) {
			log.error("Failed by Seller ID : "+sellerId, e);
			e.printStackTrace();
		}
		log.info("*** fetchChannelMCNPR ends : ReportsGeneratorDaoImpl ****");

		return channelNPRList;
	}
	
	/**
	 * Generic method to fetch list of Order objects (MP + PO)
	 * 
	 * @param session
	 * @param sellerId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Order> fetchDebtorsOrders(Session session, int sellerId, Date startDate, Date endDate) {
		log.info("*** fetchDebtorsOrders start : ReportsGeneratorDaoImpl ****");

		List<Order> orderList = new ArrayList<>();
		try {
			Criterion lhs = Restrictions.or(Restrictions.ge("orderPayment.paymentDifference", 1.0), 
					Restrictions.le("orderPayment.paymentDifference", -1.0));
			Criterion rhs = Restrictions.ge("paymentDueDate", new Date());

			Criteria criteria = session.createCriteria(Order.class);
			criteria.add(Restrictions.eq("poOrder", false));
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId));
			criteria.createAlias("orderPayment", "orderPayment",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.or(lhs, rhs));
			criteria.createAlias("orderTax", "orderTax",
					CriteriaSpecification.LEFT_JOIN);
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			criteria.addOrder(org.hibernate.criterion.Order.asc("shippedDate"));
			orderList.addAll(criteria.list());
			

			criteria = session.createCriteria(Order.class);
			criteria.add(Restrictions.eq("poOrder", true));
			criteria.add(Restrictions.isNull("consolidatedOrder"));
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId));
			criteria.createAlias("orderPayment", "orderPayment",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.or(lhs, rhs));
			criteria.createAlias("orderTax", "orderTax",
					CriteriaSpecification.LEFT_JOIN);
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			criteria.addOrder(org.hibernate.criterion.Order.asc("shippedDate"));
			orderList.addAll(criteria.list());
		} catch (NullPointerException e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : "+sellerId,e);					
		} catch (Exception e) {
			log.error("Failed By Seller ID : "+sellerId, e);
			e.printStackTrace();
		}
		log.info("*** fetchDebtorsOrders ends : ReportsGeneratorDaoImpl ****");

		return orderList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<YearlyStockList> fetchStockList(int year) {
		log.info("*** fetchStockList start : ReportsGeneratorDaoImpl ****");

		List<YearlyStockList> prStockList = new ArrayList<YearlyStockList>();
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Session session=sessionFactory.openSession();
			session.getTransaction().begin();
			String queryStr = "SELECT cat.parentCatName, pst.createdDate, pst.price, pst.stockAvailable FROM o2rschema.product pr, category cat, " +
					"product_productstocklist ppst, productstocklist pst where " +
					"pr.Category_categoryId = cat.categoryId and ppst.product_productId = pr.productId and " +
					"pst.stockId = ppst.closingStocks_stockId order by cat.parentCatName";
			Query query = session.createSQLQuery(queryStr);
			List<Object[]> stockList = query.list();
			Set<String> categorySet = new HashSet<String>();		
			List<Map<String, String>> dateRangeList = ConverterClass.getDateRanges(year);
			for(Object[] stock: stockList){
				categorySet.add(stock[1].toString());
			}
			Map<String, YearlyStockList> yearlyStockMap = new HashMap<String, YearlyStockList>();
			
			for(Map<String, String> dateMap: dateRangeList){
				String startDateStr = dateMap.get("startDate");
				String[] startDateArr = startDateStr.split("-");
				int startMonth = Integer.parseInt(startDateArr[1]) - 1;
				String currMonth = GlobalConstant.monthNameMap.get(startDateArr[1]);
				currMonth += " " + startDateArr[0];
				String endDateStr = dateMap.get("endDate");
				String[] endDateArr = endDateStr.split("-");
				int endMonth = Integer.parseInt(endDateArr[1]) - 1;
				for(Object[] stock: stockList){
					String key = stock[0].toString();
					double stockPrice = Double.parseDouble(stock[2].toString());
					double stockAvailable = Double.parseDouble(stock[3].toString());
					double stockValue = stockPrice * stockAvailable;
					String createdDate = stock[1].toString().split(" ")[0];
					int createdMonth = Integer.parseInt(createdDate.split("-")[1]) - 1;
					
					YearlyStockList stockObj = yearlyStockMap.get(key + currMonth);
					if(stockObj == null){
						stockObj = new YearlyStockList();
						stockObj.setCategory(key);
						stockObj.setMonthStr(currMonth);
						stockObj.setMonth(startMonth);
					}
					
					if(startMonth == createdMonth){
						double existStock = stockObj.getOpenStock();
						double existStockVal = stockObj.getOpenStockValuation();
						stockObj.setOpenStock(existStock + stockAvailable);
						stockObj.setOpenStockValuation(existStockVal + stockValue);
					}
					if(endMonth == createdMonth){
						double existStock = stockObj.getCloseStock();
						double existStockVal = stockObj.getCloseStockValuation();
						stockObj.setCloseStock(existStock + stockAvailable);
						stockObj.setCloseStockValuation(existStockVal + stockValue);
					}
					yearlyStockMap.put(key + currMonth, stockObj);				
				}
			}
			
			Iterator entries = yearlyStockMap.entrySet().iterator();
			while (entries.hasNext()) {
				Entry<String, YearlyStockList> thisEntry = (Entry<String, YearlyStockList>) entries
						.next();
				YearlyStockList channelNPR = thisEntry.getValue();
				prStockList.add(channelNPR);
			}
			Collections.sort(prStockList, new YearlyStockList.OrderByMonthCat());		
		} catch (NullPointerException e) {
			e.printStackTrace();
			log.error("Failed!",e);					
		} catch (Exception e) {
			log.error("Failed",e);
			e.printStackTrace();
		}
		log.info("*** fetchStockList ends : ReportsGeneratorDaoImpl ****");

		return prStockList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<NetPaymentResult> fetchNPR(int sellerId, Date startDate, Date endDate) {
		log.info("*** fetchNPR start : ReportsGeneratorDaoImpl ****");

		List<NetPaymentResult> nprList = new ArrayList<NetPaymentResult>();
		try {
			Session session=sessionFactory.openSession();
			session.getTransaction().begin();
			String orderQueryStr = "select concat(monthname(op.dateOfPayment), ' ', year(op.dateOfPayment)) as Month, " +
					"sum(op.netPaymentResult) as NetPaymentResult from orderpay op, order_table ot where " +
					"op.paymentId = ot.orderPayment_paymentId and op.dateOfPayment between :startDate and :endDate and " +
					"op.dateOfPayment is not null and ot.seller_id = :sellerId group by Month";
			Query orderQuery = session.createSQLQuery(orderQueryStr)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			List<Object[]> orderList = orderQuery.list();
			for(Object[] order: orderList){
				NetPaymentResult npr = new NetPaymentResult();
				String key = order[0].toString();
				npr.setKey(key);
				npr.setNetPaymentResult(Double.parseDouble(order[1].toString()));
				nprList.add(npr);
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : "+sellerId,e);					
		} catch (Exception e) {
			log.error("Failed by seller ID : "+sellerId, e);
			e.printStackTrace();
		}
		log.info("*** fetchNPR ends : ReportsGeneratorDaoImpl ****");

		return nprList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ChannelNR> fetchNetRate(int sellerId, Date startDate, Date endDate) {
		log.info("*** fetchNetRate start : ReportsGeneratorDaoImpl ****");

		List<ChannelNR> netRateList = new ArrayList<ChannelNR>();
		Map<String, ChannelNR> netRateMap = new HashMap<String, ChannelNR>();
		try {
			Session session=sessionFactory.openSession();
			session.getTransaction().begin();
			
			String orderQueryStr = "select concat(monthname(shippedDate), ' ', year(shippedDate)) as Month, " +
					"sum(netRate) from order_table where shippedDate between :startDate and :endDate " +
					"and seller_id = :sellerId and (poOrder = 1 OR (poOrder = 1 and consolidatedOrder_orderId is null)) group by Month";
			Query orderQuery = session.createSQLQuery(orderQueryStr)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			List<Object[]> orderList = orderQuery.list();
			for(Object[] order: orderList){
				String key = order[0].toString();
				ChannelNR netRate = new ChannelNR();
				netRate.setKey(key);
				netRate.setTotalNR(Double.parseDouble(order[1].toString()));
				netRateMap.put(key, netRate);
			}
			
			orderQueryStr = "select concat(monthname(orr.returnDate), ' ', year(orr.returnDate)) as Month, " +
					"sum(ot.grossNetRate*orr.returnorrtoQty) as NetRate from orderreturn orr, order_table ot where " +
					"orr.returnId = ot.orderReturnOrRTO_returnId and (poOrder = 0 OR ot.poOrder = 1 and ot.consolidatedOrder_orderId is null) " +
					"and orr.returnDate between :startDate and :endDate and ot.seller_id = :sellerId group by Month";
			orderQuery = session.createSQLQuery(orderQueryStr)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			orderList = orderQuery.list();
			for(Object[] order: orderList){
				String key = order[0].toString();
				ChannelNR netRate = netRateMap.get(key);
				if(netRate == null){
					netRate = new ChannelNR();
					netRate.setKey(key);
				}
				double retNetRate = Double.parseDouble(order[1].toString());
				netRate.setTotalNR(netRate.getTotalNR() - retNetRate);
				netRateMap.put(key, netRate);
			}
			
			orderQueryStr = "select concat(monthname(orr.returnDate), ' ', year(orr.returnDate)) as Month, " +
					"sum(orr.netNR) as NetRate from orderreturn orr, order_table ot where " +
					"orr.returnId = ot.orderReturnOrRTO_returnId and ot.poOrder = 1 and ot.consolidatedOrder_orderId is null " +
					"and orr.returnDate between :startDate and :endDate and ot.seller_id = :sellerId group by Month";
			orderQuery = session.createSQLQuery(orderQueryStr)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			orderList = orderQuery.list();
			for(Object[] order: orderList){
				String key = order[0].toString();
				ChannelNR netRate = netRateMap.get(key);
				if(netRate == null){
					netRate = new ChannelNR();
					netRate.setKey(key);
				}
				double retNetRate = Double.parseDouble(order[1].toString());
				netRate.setTotalNR(netRate.getTotalNR() - retNetRate);
				netRateMap.put(key, netRate);
			}
			
			Iterator entries = netRateMap.entrySet().iterator();
			while (entries.hasNext()) {
				Entry<String, ChannelNR> thisEntry = (Entry<String, ChannelNR>) entries
						.next();
				ChannelNR netRate = thisEntry.getValue();
				netRateList.add(netRate);
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : "+sellerId,e);					
		} catch (Exception e) {
			log.error("Failed By Seller ID : "+sellerId, e);
			e.printStackTrace();
		}
		log.info("*** fetchNetRate ends : ReportsGeneratorDaoImpl ****");

		return netRateList;
	}
	
	public List<Double> getStockList(){
		List<Double> stockList = new ArrayList<Double>();
		for(int i=0; i<12; i++){
			stockList.add(new Double(0));
		}
		return stockList;
	}
		
	@Override
	public UploadReport addUploadReport(UploadReport uploadReport, int sellerId)
			throws CustomException {
		log.info("***addUploadReport Start for : " + sellerId + "****");
		Seller seller = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", sellerId));
			seller = (Seller) criteria.list().get(0);
			uploadReport.setSeller(seller);
			if (seller.getUploadReportList() != null) {
				seller.getUploadReportList().add(uploadReport);
			}
			session.saveOrUpdate(seller);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			log.equals("**Error Code : "
					+ (sellerId + "-" + GlobalConstant.addReportErrorCode));
			log.error("Failed! by sellerId : "+sellerId,e);
			throw new CustomException(GlobalConstant.addReportError,
					new Date(), 1, sellerId + "-"
							+ GlobalConstant.addReportErrorCode, e);
		}
		log.info("***addUploadReport Exit****");
		return uploadReport;
	}
	
	@Override
	public List<UploadReport> listUploadReport(int sellerId, boolean doSort) throws CustomException {
		log.info("*** listUploadReport start : ReportsGeneratorDaoImpl ****");

		List<UploadReport> returnlist = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Seller seller = (Seller) session.get(Seller.class, sellerId);
			if (seller.getUploadReportList() != null && seller.getUploadReportList().size() != 0) {
				
				returnlist = seller.getUploadReportList();
				
				if (doSort) {
					Collections.sort(returnlist, new UploadReport.OrderByDate());
				}
			}
			session.getTransaction().commit();
			session.close();
			log.info("*** listUploadReport ends : ReportsGeneratorDaoImpl ****");

			return returnlist;
		} catch (Exception e) {
			log.error("Failed! by sellerId : "+sellerId,e);
			throw new CustomException(GlobalConstant.listReportError,
					new Date(), 3, GlobalConstant.listReportsErrorCode, e);
		}

	}

	@Override
	public UploadReport getUploadLog(int id, int sellerId) throws CustomException {
		log.info("*** getUploadLog start : ReportsGeneratorDaoImpl ****");

		List<UploadReport> returnlist = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", sellerId));
			criteria.createAlias("uploadReportList", "uploadReport",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("uploadReport.id",
							id))
					.setResultTransformer(
							CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			if (criteria.list().size() != 0) {
				Seller seller = (Seller) criteria.list().get(0);
				if (seller == null)
					System.out.println("Null seller");
				returnlist = seller.getUploadReportList();		
			}
			session.getTransaction().commit();
			session.close();
			log.info("*** getUploadLog ends : ReportsGeneratorDaoImpl ****");

			return returnlist.get(0);
		} catch (Exception e) {
			log.error("Failed! by sellerId : "+sellerId,e);
			throw new CustomException(GlobalConstant.listReportError,
					new Date(), 3, GlobalConstant.listReportsErrorCode, e);
		}
	}
}
