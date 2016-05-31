package com.o2r.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

import com.o2r.bean.ChannelReportDetails;
import com.o2r.bean.CommissionDetails;
import com.o2r.bean.PartnerReportDetails;
import com.o2r.bean.TotalShippedOrder;
import com.o2r.helper.CustomException;
import com.o2r.helper.GlobalConstant;
import com.o2r.model.Customer;
import com.o2r.model.Order;
import com.o2r.model.OrderPayment;
import com.o2r.model.OrderRTOorReturn;
import com.o2r.model.OrderTax;
import com.o2r.model.Product;

/**
 * @author Deep Mehrotra
 *
 */

@Repository("reportGeneratorDao")
public class ReportsGeneratorDaoImpl implements ReportsGeneratorDao {

	@Autowired
	private SessionFactory sessionFactory;

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
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			log.info("Error :", e);
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
			List<Object[]> results = criteria.list();
			log.debug("results.size()  " + results.size());
			ttso = new TotalShippedOrder[results.size()];
			log.debug(" Array size :" + ttso.length);
			log.debug("ttso  Object " + ttso[0]);
			Iterator iterator1 = results.iterator();
			int iteratorCount = 0;
			if (results != null) {
				while (iterator1.hasNext()) {
					System.out.println("\n");
					ttsoTemp = new TotalShippedOrder();
					Object[] recordsRow = (Object[]) iterator1.next();
					log.debug("recordsRow.length  "+ recordsRow.length);
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
			List<Object[]> deliveredOrderCount = criteriaforDeliveredOrder.list();
			Iterator DOiterator = deliveredOrderCount.iterator();
			if (deliveredOrderCount != null) {
				iteratorCount = 0;
				while (DOiterator.hasNext()) {
					System.out.println("\n");
					Object[] recordsRow = (Object[]) DOiterator.next();
					log.debug(" Delivered order partner :"
							+ recordsRow[0].toString() + " coumt :"
							+ recordsRow[1].toString());
					totalNoofDO = totalNoofDO
							+ Integer.parseInt(recordsRow[1].toString());
					ttso[iteratorCount++].setNoOfDeliveredOrder(Integer
							.parseInt(recordsRow[1].toString()));
				}
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

			List<Object[]> returnOrderCount = criteriaforReturnOrder.list();
			Iterator ROiterator = returnOrderCount.iterator();
			if (returnOrderCount != null) {
				iteratorCount = 0;
				while (ROiterator.hasNext()) {
					Object[] recordsRow = (Object[]) ROiterator.next();
					log.debug(" Return order partner :"
							+ recordsRow[0].toString() + " coumt :"
							+ recordsRow[1].toString());

					totalNoofRO = totalNoofRO
							+ Integer.parseInt(recordsRow[1].toString());
					ttso[iteratorCount++].setNoOfReturnOrder(Integer
							.parseInt(recordsRow[1].toString()));

				}
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

			List<Object[]> actionableOrderCount = criteriaforActionalbleOrder.list();
			Iterator AOiterator = actionableOrderCount.iterator();
			if (actionableOrderCount != null) {
				iteratorCount = 0;
				while (AOiterator.hasNext()) {
					Object[] recordsRow = (Object[]) AOiterator.next();
					log.debug(" Actionable order partner :"
							+ recordsRow[0].toString() + " coumt :"
							+ recordsRow[1].toString());

					totalNoofAO = totalNoofAO
							+ Integer.parseInt(recordsRow[1].toString());
					ttso[iteratorCount++].setNoOfActionableOrders(Integer
							.parseInt(recordsRow[1].toString()));

				}
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

			List<Object[]> settledOrderCount = criteriaforSettledOrder.list();
			Iterator SOiterator = settledOrderCount.iterator();
			if (settledOrderCount != null) {
				iteratorCount = 0;
				while (SOiterator.hasNext()) {
					Object[] recordsRow = (Object[]) SOiterator.next();
					log.debug(" Settled order partner :"
							+ recordsRow[0].toString() + " coumt :"
							+ recordsRow[1].toString());
					totalNoofSO = totalNoofSO
							+ Integer.parseInt(recordsRow[1].toString());
					ttso[iteratorCount++].setNoOfSettledOrders(Integer
							.parseInt(recordsRow[1].toString()));

				}
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

			List<Object[]> RTOlimitCrossOrderCount = criteriaRTOlimitCross.list();
			Iterator rtoOiterator = RTOlimitCrossOrderCount.iterator();
			if (RTOlimitCrossOrderCount != null) {
				iteratorCount = 0;
				while (rtoOiterator.hasNext()) {
					Object[] recordsRow = (Object[]) rtoOiterator.next();
					log.debug(" Rto limit partner :"
							+ recordsRow[0].toString() + " coumt :"
							+ recordsRow[1].toString());

					totalNoofRTOCross = totalNoofRTOCross
							+ Integer.parseInt(recordsRow[1].toString());
					ttso[iteratorCount++].setNoOfRTOLimitCrossed(Integer
							.parseInt(recordsRow[1].toString()));

				}
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
			List<Object[]> returnlimitCrossOrderCount = criteriaReturnlimitCross.list();
			Iterator returniterator = returnlimitCrossOrderCount.iterator();
			if (returnlimitCrossOrderCount != null) {
				while (returniterator.hasNext()) {
					iteratorCount = 0;
					Object[] recordsRow = (Object[]) returniterator.next();
					log.debug(" Return limit partner :"
							+ recordsRow[0].toString() + " coumt :"
							+ recordsRow[1].toString());

					totalNoofreturnCross = totalNoofreturnCross
							+ Integer.parseInt(recordsRow[1].toString());
					ttso[iteratorCount++].setNoOfReturnLimitCrossed(Integer
							.parseInt(recordsRow[1].toString()));

				}
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
			List<Object[]> cityresults = criteriaforCitiesOrder.list();
			Iterator cityIterator = cityresults.iterator();
			if (cityresults != null) {
				while (cityIterator.hasNext()) {
					
					Object[] recordsRow = (Object[]) cityIterator.next();
					log.debug(" Cities row length "+ recordsRow.length);
					if (recordsRow.length > 0) {
						if (!cityMap.containsKey(recordsRow[0].toString())) {
							cityMap.put(recordsRow[0].toString(), Double
									.parseDouble(recordsRow[1].toString()));
						}
						log.debug("city " + recordsRow[0]+ " count : " + recordsRow[1]);
						cityTotalQuantity = cityTotalQuantity
								+ Integer.parseInt(recordsRow[1].toString());
					}

				}
			}
			log.debug(" ***cityTotalQuantity " + cityTotalQuantity);
			ttso[0].setCityQuantity(cityMap);
			for (Map.Entry<String, Double> entry : cityMap.entrySet()) {
				log.debug("for city : " + entry.getKey()
						+ " count is :" + entry.getValue() + " percent is :"
						+ (entry.getValue()) / cityTotalQuantity * 100);
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

			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			log.info("Error :", e);
			throw new CustomException(
					GlobalConstant.getAllPartnerTSOdetailsError, new Date(), 3,
					GlobalConstant.getAllPartnerTSOdetailsErrorCode, e);
			
		}
		
		log.info("*** getAllPartnerTSOdetails Starts : ReportsGeneratorDaoImpl ****");
		return Arrays.asList(ttso);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PartnerReportDetails> getDebtorsReportDetails(Date startDate,
			Date endDate, int sellerId) throws CustomException {
		List<PartnerReportDetails> partnerBusinessList = new ArrayList<PartnerReportDetails>();
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();			
			List<Order> results = fetchDebtorsOrders(session, sellerId, startDate, endDate);
			for (Order currOrder : results) {
				PartnerReportDetails partnerBusiness = transformPartnerDetail(currOrder, session, startDate, endDate);
				partnerBusinessList.add(partnerBusiness);
			}
			log.info("Total Orders" + partnerBusinessList.size());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			log.info("Error :", e);
			throw new CustomException(
					GlobalConstant.getAllPartnerTSOdetailsError, new Date(), 3,
					GlobalConstant.getAllPartnerTSOdetailsErrorCode, e);

		}
		return partnerBusinessList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PartnerReportDetails> getPartnerReportDetails(Date startDate,
			Date endDate, int sellerId) throws CustomException {
		List<PartnerReportDetails> partnerBusinessList = new ArrayList<PartnerReportDetails>();
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();			
			List<Order> results = fetchOrders(session, sellerId, startDate, endDate);
			for (Order currOrder : results) {
				PartnerReportDetails partnerBusiness = transformPartnerDetail(currOrder, session, startDate, endDate);
				partnerBusinessList.add(partnerBusiness);
			}			
			log.info("Total Orders" + partnerBusinessList.size());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			log.info("Error :", e);
			throw new CustomException(
					GlobalConstant.getAllPartnerTSOdetailsError, new Date(), 3,
					GlobalConstant.getAllPartnerTSOdetailsErrorCode, e);

		}
		return partnerBusinessList;
	}
	
	private PartnerReportDetails transformPartnerDetail(Order currOrder, Session session, Date startDate,
			Date endDate) {
		PartnerReportDetails partnerBusiness = new PartnerReportDetails();
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
		if(isPoOrder && consolidatedOrder!=null)
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
		if (productList.size() > 0) {
			Product currProduct = productList.get(0);
			partnerBusiness.setProductCategory(currProduct
					.getCategoryName());
			partnerBusiness.setProductPrice(currProduct.getProductPrice()*quantity);
		}
		
		double tdsToBeDeposited = 0;
		double taxToBePaid = 0;
		double taxSP = 0;
		double taxPOPrice = 0;		
		double grossTds = 0;
		double returnTds = 0;
		if(currOrderTax != null){
			grossTds = currOrderTax.getTdsToDeduct();
			taxToBePaid = currOrderTax.getTax();
			if(currOrderReturnOrRTO != null){
				returnTds = currOrderTax.getTdsToReturn();
				tdsToBeDeposited = grossTds - returnTds;
				taxToBePaid -= currOrderTax.getTaxToReturn();
			}
			// Only for PO
			if(isPoOrder && consolidatedOrder!=null){
				taxSP = currOrderTax.getTaxToReturn();
				taxPOPrice = currOrderTax.getTax();
			}
		}
		partnerBusiness.setTaxSP(taxSP);
		partnerBusiness.setTaxPOPrice(taxPOPrice);
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
		if (currOrderReturnOrRTO != null) {
			Date returnDate = currOrderReturnOrRTO.getReturnDate();
			boolean dateCriteria = returnDate!=null;
			if(dateCriteria){
				partnerBusiness.setReturnDate(currOrderReturnOrRTO.getReturnDate());
				returnQty = currOrderReturnOrRTO.getReturnorrtoQty();
				returnSP = grossSP*returnQty;
				partnerBusiness.setReturnQuantity(returnQty);
				netReturnCharges = grossNetRate * returnQty;
				additionalReturnCharges = currOrderReturnOrRTO.getReturnOrRTOChargestoBeDeducted();
				partnerBusiness.setNetReturnCharges(netReturnCharges);
				partnerBusiness.setReturnId(currOrderReturnOrRTO.getReturnOrRTOId());
				
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
		if(quantity > 0)
			grossReturnChargesReversed = netRate/quantity*returnQty;
		partnerBusiness.setGrossReturnChargesReversed(grossReturnChargesReversed);
		partnerBusiness.setTotalReturnCharges(grossReturnChargesReversed + netReturnCharges);
		partnerBusiness.setOrderSP(orderSP);
		partnerBusiness.setReturnSP(returnSP);
		partnerBusiness.setNetSP(grossSP/quantity*(quantity-returnQty));
		if (currOrderPayment != null) {
			partnerBusiness.setDateofPayment(currOrderPayment
					.getDateofPayment());
			double netPaymentResult = currOrderPayment
					.getNetPaymentResult();
			partnerBusiness.setNetPaymentResult(netPaymentResult);
			double paymentDifference = currOrderPayment
					.getPaymentDifference();
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
		if(isPoOrder && consolidatedOrder!=null){
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
		if(!isPoOrder){
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
		double serviceTax = (totalAmount)*14.5/100;
		double serviceTaxNoQty = (grossCommissionNoQty + pccAmountNoQty + fixedFeeNoQty + shippingChargesNoQty)*14.5/100;
		double serviceTaxQty = (grossCommissionQty + pccAmountQty + fixedFeeQty + shippingChargesQty)*14.5/100;
		partnerBusiness.setServiceTax(serviceTax);
		double grossCommissionToBePaid = 0;
		if(isPoOrder && consolidatedOrder!=null)
			grossCommissionToBePaid = grossCommission + taxSP - taxPOPrice;
		else
			grossCommissionToBePaid = totalAmount + serviceTax;
		double grossCommissionToBePaidNoQty = grossCommissionNoQty + pccAmountNoQty + fixedFeeNoQty + shippingChargesNoQty + serviceTaxNoQty;
		double grossCommissionToBePaidQty = grossCommissionQty + pccAmountQty + fixedFeeQty + shippingChargesQty + serviceTaxQty;
		partnerBusiness.setGrossCommissionQty(grossCommissionToBePaidQty);
		partnerBusiness.setGrossCommission(grossCommissionToBePaid);
		double returnCommision = 0;
		// MP & PO Conditions
		if(isPoOrder && consolidatedOrder!=null){
			returnCommision = grossCommissionToBePaid;
		} else{
			returnCommision = grossCommissionToBePaidNoQty;
		}
		partnerBusiness.setReturnCommision(returnCommision);
		partnerBusiness.setAdditionalReturnCharges(additionalReturnCharges);
		double netPartnerCommissionPaid = grossCommissionToBePaidQty - returnCommision + additionalReturnCharges;
		partnerBusiness.setNetPartnerCommissionPaid(netPartnerCommissionPaid);
		partnerBusiness.setTdsToBeDeducted10(0.1 * grossCommission);
		partnerBusiness.setTdsToBeDeducted2(0.02 * (pccAmount + fixedFee + shippingCharges));
		partnerBusiness.setNetTaxToBePaid(taxToBePaid);
		double netEossValue = 0; // Only for Consolidated PO
		partnerBusiness.setNetEossValue(netEossValue);
		partnerBusiness.setNetPr(currOrder.getPr()/quantity*(quantity-returnQty));
		partnerBusiness.setGrossNetRate(grossNetRate*quantity);
		partnerBusiness.setNetRate(currOrder.getNetRate());
		partnerBusiness.setFinalStatus(currOrder.getFinalStatus());
		partnerBusiness.setGrossProfit(currOrder.getGrossProfit());
		return partnerBusiness;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChannelReportDetails> getChannelReportDetails(Date startDate,
			Date endDate, int sellerId) throws CustomException {
		
		log.info("*** getChannelReportDetails Starts : ReportsGeneratorDaoImpl ****");
		List<ChannelReportDetails> channelReportDetailsList = new ArrayList<ChannelReportDetails>();
		 
		 try
		 {
			 Session session=sessionFactory.openSession();
			 session.getTransaction().begin();
			 
			 List<Order> orders = fetchOrders(session, sellerId, startDate, endDate);
			 for (Order currOrder : orders) {
					boolean isPoOrder = currOrder.isPoOrder();
					Order consolidateOrder = currOrder.getConsolidatedOrder();
					ChannelReportDetails channelReport = new ChannelReportDetails();
					channelReport.setPartner(currOrder.getPcName());
					channelReport.setOrderId(currOrder.getChannelOrderID());
					channelReport.setInvoiceId(currOrder.getInvoiceID());
					channelReport.setShippedDate(currOrder.getShippedDate());
					channelReport.setProductSku(currOrder.getProductSkuCode());

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
					channelReport.setGrossNrAmount(grossNrAmount);
					channelReport.setGrossSpAmount(grossSpAmount);

					OrderRTOorReturn currOrderReturn = currOrder.getOrderReturnOrRTO();
					if(currOrderReturn != null){
						channelReport.setNetReturnCharges(currOrderReturn.getReturnOrRTOChargestoBeDeducted());
						saleRetQty = currOrderReturn.getReturnorrtoQty();
						// Only for PO Order
						if(isPoOrder && consolidateOrder!=null){
							saleRetNrAmount = currOrderReturn.getNetNR();
						}
					}
					if(!isPoOrder)
						
					// MP/PO Order conditions
					if(!isPoOrder){
						saleRetNrAmount = grossNrAmount*saleRetQty;
						if(grossSaleQty != 0)
							saleRetSpAmount = grossSpAmount*(saleRetQty/grossSaleQty);
						channelReport.setPr(orderPr);
					} else if(isPoOrder && consolidateOrder!=null){
						saleRetSpAmount = grossSpAmount;
						if(grossSaleQty != 0)
							channelReport.setPr(orderPr * saleRetQty/grossSaleQty);
					}
					channelReport.setSaleRetQty(saleRetQty);
					channelReport.setSaleRetNrAmount(saleRetNrAmount);
					channelReport.setSaleRetSpAmount(saleRetSpAmount);
					
					double saleRetVsGrossSale = 0;
					if(grossSaleQty != 0)
						saleRetVsGrossSale = saleRetQty/grossSaleQty*100;
					double netQty = grossSaleQty - saleRetQty;
					double netNrAmount = grossNrAmount - saleRetNrAmount;
					double netSpAmount = grossSpAmount - saleRetSpAmount;
					channelReport.setSaleRetVsGrossSale(saleRetVsGrossSale);
					channelReport.setNetQty(netQty);
					channelReport.setNetNrAmount(netNrAmount);
					channelReport.setNetSpAmount(netSpAmount);
					
					double taxCatPercent = 0;
					OrderTax currOrderTax = currOrder.getOrderTax();
					if(currOrderTax != null){
						String taxCategory = currOrderTax.getTaxCategtory();
						channelReport.setTaxCategory(taxCategory);
						if(StringUtils.isNotBlank(taxCategory)){
							String[] taxCategoryArr = taxCategory.split("@");
							if(taxCategoryArr.length==2){
								taxCatPercent = Double.parseDouble(taxCategoryArr[1]);
							}
						}
					}
					double netTaxLiability = 0;
					if(taxCatPercent != 0)
						netTaxLiability = netSpAmount - netSpAmount*(100/(100 + taxCatPercent));
					channelReport.setNetTaxLiability(netTaxLiability);
					channelReport.setNetPureSaleQty(netQty);
					channelReport.setNetPureSaleNrAmount(netNrAmount);
					channelReport.setNetPureSaleSpAmount(netSpAmount - netTaxLiability);
					
					OrderPayment currOrderPayment = currOrder.getOrderPayment();
					if(currOrderPayment != null){
						channelReport.setNetToBeReceived(currOrderPayment.getPaymentDifference());
						double netPaymentResult = currOrderPayment.getNetPaymentResult();
						channelReport.setNetPaymentResult(netPaymentResult);
						channelReport.setNetAr(netPaymentResult);
					}
					
					double productCost = 0;
					double gpVsProductCost = 0;
					Criteria prodcriteria = session.createCriteria(Product.class);
					prodcriteria.add(Restrictions.eq("productSkuCode",
							currOrder.getProductSkuCode()));
					List<Product> productList = prodcriteria.list();
					if (productList.size() > 0) {
						Product currProduct = productList.get(0);
						if(!isPoOrder){
							productCost = currProduct.getProductPrice();
						}
						channelReport.setCategory(currProduct.getCategoryName());
					}
					if(productCost != 0)
						gpVsProductCost = grossProfit/productCost*100;
					channelReport.setProductCost(productCost);
					channelReport.setGpVsProductCost(gpVsProductCost);
					
					channelReportDetailsList.add(channelReport);
			 }
		 } catch (Exception e) {
			log.error(e);
			log.info("Error :", e);
			throw new CustomException(
					GlobalConstant.getAllPartnerTSOdetailsError, new Date(), 3,
					GlobalConstant.getAllPartnerTSOdetailsErrorCode, e);

		}
			 
		return channelReportDetailsList;	 
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
		List<Order> orderList = new ArrayList<>();
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
		criteria.add(Restrictions.isNotNull("consolidatedOrder"));
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

		return orderList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CommissionDetails> fetchPC(int sellerId, Date startDate, Date endDate, String criteria) {
		Map<String, CommissionDetails> commDetailsMap = new HashMap<String, CommissionDetails>(); 
		Session session=sessionFactory.openSession();
		session.getTransaction().begin();
		String mpOrderQueryStr = "select ot.pcName, sum((ot.partnerCommission+ot.pccAmount+ot.fixedFee+ot.shippingCharges) * ot.quantity * 1.145) " +
				"as grossComm, sum(ot.quantity) as netSaleQty, sum(otx.tdsToDeduct) as tdsToDeduct from order_table ot, ordertax otx " +
				"where ot.orderTax_taxId = otx.taxId and ot.poOrder = 0  and ot.seller_Id=:sellerId and ot.shippedDate " +
				"between :startDate AND :endDate group by ot.pcName";
		if("category".equalsIgnoreCase(criteria))
			mpOrderQueryStr = "select pr.categoryName, sum((ot.partnerCommission+ot.pccAmount+ot.fixedFee+ot.shippingCharges) * ot.quantity * 1.145) " +
				"as grossComm, sum(ot.quantity) as netSaleQty, sum(otx.tdsToDeduct) as tdsToDeduct from order_table ot, ordertax otx " +
				", product pr where ot.productSkuCode = pr.productSkuCode and ot.orderTax_taxId = otx.taxId and ot.poOrder = 0  and " +
				"ot.seller_Id=:sellerId and ot.shippedDate between :startDate AND :endDate group by pr.categoryName";
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
		
		String poOrderQueryStr = "select ot.pcName, sum( ot.partnerCommission+otx.taxToReturn-otx.tax ) as grossComm, " +
				"sum(ot.quantity) as saleQty, sum(otx.tdsToDeduct) as tdsToDeduct from order_table ot, ordertax otx " +
				"where ot.orderTax_taxId = otx.taxId and ot.poOrder = 1 and ot.consolidatedOrder_orderId is not null " +
				"and ot.seller_Id=:sellerId and ot.shippedDate between :startDate AND :endDate group by ot.pcName";
		if("category".equalsIgnoreCase(criteria))
			poOrderQueryStr = "select pr.categoryName, sum( ot.partnerCommission+otx.taxToReturn-otx.tax ) as grossComm, " +
					"sum(ot.quantity) as saleQty, sum(otx.tdsToDeduct) as tdsToDeduct from order_table ot, ordertax otx, product pr " +
					"where ot.productSkuCode = pr.productSkuCode and ot.orderTax_taxId = otx.taxId and ot.poOrder = 1 and " +
					"ot.consolidatedOrder_orderId is not null and ot.seller_Id=:sellerId and " +
					"ot.shippedDate between :startDate AND :endDate group by pr.categoryName";
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
			int netSaleQty = Integer.parseInt(order[2].toString());
			double grossCommissionPaid = Double.parseDouble(order[1].toString());
			double tdsToDeduct = Double.parseDouble(order[3].toString());
			commDetails.setNetSaleQty(existSQ + netSaleQty);
			commDetails.setGrossPartnerCommissionPaid(existGC + grossCommissionPaid);
			commDetails.setNetTDSToBeDeposited(existTDS + tdsToDeduct);
			commDetailsMap.put(key, commDetails);
		}
		String mpReturnQueryStr = "select ot.pcName, sum((ot.partnerCommission+ot.pccAmount+ot.fixedFee+ot.shippingCharges) * orr.returnorrtoQty * 1.145) as returnComm, " +
				"sum(estimateddeduction) as addRetCharges, sum(orr.returnorrtoQty) as returnQty, sum(otx.tdsToReturn) as tdsToReturn from order_table ot, " +
				"orderreturn orr, ordertax otx where ot.orderTax_taxId = otx.taxId and ot.orderReturnOrRTO_returnId = orr.returnId and poOrder = 0 " +
				"and ot.seller_Id=:sellerId and orr.returnDate between :startDate AND :endDate group by ot.pcName";
		if("category".equalsIgnoreCase(criteria))
			mpReturnQueryStr = "select pr.categoryName, sum((ot.partnerCommission+ot.pccAmount+ot.fixedFee+ot.shippingCharges) * orr.returnorrtoQty * 1.145) as returnComm, " +
					"sum(estimateddeduction) as addRetCharges, sum(orr.returnorrtoQty) as returnQty, sum(otx.tdsToReturn) as tdsToReturn from order_table ot, " +
					"orderreturn orr, ordertax otx, product pr where ot.productSkuCode = pr.productSkuCode and ot.orderTax_taxId = otx.taxId and " +
					"ot.orderReturnOrRTO_returnId = orr.returnId and poOrder = 0 and ot.seller_Id=:sellerId and " +
					"orr.returnDate between :startDate AND :endDate group by pr.categoryName";
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

		String poReturnQueryStr = "select ot.pcName, sum( (ot.partnerCommission+otx.taxToReturn-otx.tax) * orr.returnorrtoQty ) as returnComm, " +
				"sum(estimateddeduction) as addRetCharges, sum(orr.returnorrtoQty) as returnQty, sum(otx.tdsToReturn) as tdsToReturn  " +
				"from order_table ot, ordertax otx, orderreturn orr where ot.orderReturnOrRTO_returnId = orr.returnId and " +
				"ot.orderTax_taxId = otx.taxId and ot.poOrder = 1 and ot.consolidatedOrder_orderId is not null and ot.seller_Id=:sellerId " +
				"and orr.returnDate between :startDate AND :endDate group by ot.pcName";
		if("category".equalsIgnoreCase(criteria))
			poReturnQueryStr = "select pr.categoryName, sum( (ot.partnerCommission+otx.taxToReturn-otx.tax) * orr.returnorrtoQty ), " +
					"sum(estimateddeduction) as returnComm, sum(orr.returnorrtoQty) as returnQty, sum(otx.tdsToReturn) as tdsToReturn  " +
					"from order_table ot, ordertax otx, orderreturn orr, product pr where ot.productSkuCode = pr.productSkuCode and " +
					"ot.orderReturnOrRTO_returnId = orr.returnId and ot.orderTax_taxId = otx.taxId and ot.poOrder = 1 and " +
					"ot.consolidatedOrder_orderId is not null and ot.seller_Id=:sellerId " +
					"and orr.returnDate between :startDate AND :endDate group by pr.categoryName";
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
			commDetails.setAdditionalReturnCharges(existARC + additionalRetCharges);
			commDetails.setNetTDSToBeDeposited(existTDS - tdsToReturn);
			commDetailsMap.put(key, commDetails);
		}
		List<CommissionDetails> commDetailsList = new ArrayList<CommissionDetails>();
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
		return commDetailsList;
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
		List<Order> orderList = new ArrayList<>();
		Criterion lhs = Restrictions.and(Restrictions.le("orderPayment.paymentDifference", 5.0), 
				Restrictions.ge("orderPayment.paymentDifference", -5.0));
		Criterion rhs = Restrictions.ge("paymentDueDate", new Date());

		Criteria criteria = session.createCriteria(Order.class);
		criteria.add(Restrictions.eq("poOrder", false));
		criteria.add(Restrictions.le("shippedDate", new Date()));
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
		criteria.add(Restrictions.isNotNull("consolidatedOrder"));
		criteria.add(Restrictions.le("shippedDate", new Date()));
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
		return orderList;
	}

	/*@Override
	public void addUploadReport(UploadReport uploadReport)
			throws CustomException {
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			session.saveOrUpdate(uploadReport);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			log.error(e.getStackTrace());
			throw new CustomException(GlobalConstant.addReportError,
					new Date(), 1, GlobalConstant.addReportErrorCode, e);
		}
	}*/

	/*@Override
	public UploadReport addUploadReport(UploadReport uploadReport, int sellerId)
			throws CustomException {
		log.info("***addUploadReport Start****");
		Seller seller = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", sellerId));
			seller = (Seller) criteria.list().get(0);
			if (seller.getUploadReportList() != null)
				seller.getUploadReportList().add(uploadReport);
			session.saveOrUpdate(seller);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			log.equals("**Error Code : "
					+ (sellerId + "-" + GlobalConstant.addReportErrorCode));
			log.error(e);
			throw new CustomException(GlobalConstant.addReportError,
					new Date(), 1, sellerId + "-"
							+ GlobalConstant.addReportErrorCode, e);
		}
		log.info("***addUploadReport Exit****");
		return uploadReport;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<UploadReport> listUploadReport() throws CustomException {
		try {
			return (List<UploadReport>) sessionFactory.getCurrentSession()
					.createCriteria(UploadReport.class).list();
		} catch (Exception e) {
			log.error(e);
			throw new CustomException(GlobalConstant.listReportError,
					new Date(), 3, GlobalConstant.listReportsErrorCode, e);
		}
	}*/
}