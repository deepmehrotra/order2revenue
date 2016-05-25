package com.o2r.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.o2r.bean.PartnerReportDetails;
import com.o2r.bean.TotalShippedOrder;
import com.o2r.helper.CustomException;
import com.o2r.helper.GlobalConstant;
import com.o2r.model.Order;
import com.o2r.model.OrderPayment;
import com.o2r.model.OrderRTOorReturn;
import com.o2r.model.OrderTax;
import com.o2r.model.Product;
import com.o2r.model.ProductConfig;

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
	public List<PartnerReportDetails> getPartnerBusinessReport(Date startDate,
			Date endDate, int sellerId) throws CustomException {
		List<PartnerReportDetails> partnerBusinessList = new ArrayList<PartnerReportDetails>();
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();			
			List<Order> results = fetchOrders(session, sellerId, startDate, endDate);
			for (Order currOrder : results) {
				boolean isPoOrder = currOrder.isPoOrder();
				Order consolidatedOrder = currOrder.getConsolidatedOrder();
				
				OrderRTOorReturn currOrderReturnOrRTO = currOrder
						.getOrderReturnOrRTO();
				OrderPayment currOrderPayment = currOrder.getOrderPayment();
				OrderTax currOrderTax = currOrder.getOrderTax();
				PartnerReportDetails partnerBusiness = new PartnerReportDetails();
				double taxSP = 0;
				int grossSaleQty = currOrder.getQuantity();
				Criteria prodcriteria = session.createCriteria(Product.class);
				prodcriteria.add(Restrictions.eq("productSkuCode",
						currOrder.getProductSkuCode()));
				List<Product> productList = prodcriteria.list();
				if (productList.size() > 0) {
					Product currProduct = productList.get(0);
					partnerBusiness.setProductCategory(currProduct
							.getCategoryName());
					partnerBusiness.setProductPrice(currProduct.getProductPrice()*grossSaleQty);
				}
				ProductConfig productConfig = currOrder.getProductConfig();
				if(productConfig !=null){
					 // Only for PO Orders
					if(isPoOrder && consolidatedOrder!=null){
						taxSP = productConfig.getTaxSp();
					}
				}
				partnerBusiness.setTaxSP(taxSP);
				int returnQty = 0;
				double netReturnCharges = 0;
				if (currOrderReturnOrRTO != null) {
					Date returnDate = currOrderReturnOrRTO.getReturnDate();
					if(returnDate!=null && startDate.before(returnDate) && endDate.after(returnDate)){
						partnerBusiness.setReturnDate(currOrderReturnOrRTO
								.getReturnDate());
						returnQty = currOrderReturnOrRTO.getReturnorrtoQty();
						partnerBusiness.setReturnQuantity(returnQty);
						netReturnCharges = currOrderReturnOrRTO
								.getReturnOrRTOChargestoBeDeducted();
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
				if (currOrderPayment != null) {
					partnerBusiness.setDateofPayment(currOrderPayment
							.getDateofPayment());
					double netPaymentResult = currOrderPayment
							.getNetPaymentResult();
					partnerBusiness.setNetPaymentResult(netPaymentResult);
					double paymentDifference = currOrderPayment
							.getPaymentDifference();
					partnerBusiness.setPaymentDifference(paymentDifference);
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
				partnerBusiness.setGrossSaleQuantity(grossSaleQty);
				partnerBusiness.setNetSaleQuantity(grossSaleQty - returnQty);
				partnerBusiness.setOrderSP(currOrder.getOrderSP());
				double grossCommission = currOrder.getPartnerCommission()
						* grossSaleQty;
				partnerBusiness.setGrossPartnerCommission(grossCommission);
				double pccAmount = currOrder.getPccAmount() * grossSaleQty;
				partnerBusiness.setPccAmount(pccAmount);
				double fixedFee = currOrder.getFixedfee() * grossSaleQty;
				partnerBusiness.setFixedfee(fixedFee);
				double shippingCharges = currOrder.getShippingCharges()
						* grossSaleQty;
				partnerBusiness.setShippingCharges(shippingCharges);
				partnerBusiness.setServiceTax(14.5 / 100 * grossCommission
						* pccAmount * fixedFee * shippingCharges);
				double taxPOPrice = 0;
				// Only for PO Orders
				if(isPoOrder && consolidatedOrder!=null){
					taxPOPrice = currOrder.getPoPrice();
				}
				partnerBusiness.setTaxPOPrice(taxPOPrice);
				double grossCommissionToBePaid = grossCommission + taxSP
						- taxPOPrice;
				partnerBusiness.setGrossCommission(grossCommissionToBePaid);
				double returnCommision = grossCommissionToBePaid * returnQty;
				partnerBusiness.setReturnCommision(returnCommision);
				double additionalReturnCharges = 0; 
				// For Non-PO Orders
				if(!isPoOrder){
					additionalReturnCharges = netReturnCharges * returnQty;
				}
				// Only for PO Orders
				if(isPoOrder && consolidatedOrder!=null){
					additionalReturnCharges = netReturnCharges;
				}				
				partnerBusiness
						.setAdditionalReturnCharges(additionalReturnCharges);
				double netPartnerCommissionPaid = grossCommissionToBePaid
						- returnCommision + additionalReturnCharges;
				partnerBusiness
						.setNetPartnerCommissionPaid(netPartnerCommissionPaid);
				partnerBusiness.setTdsToBeDeducted10(0.1 * taxPOPrice);
				partnerBusiness
						.setTdsToBeDeducted2(0.02 * netPartnerCommissionPaid);
				double tdsToBeDeposited = 0;
				double taxToBePaid = 0;
				if(currOrderTax != null){
					tdsToBeDeposited = currOrderTax.getTdsToDeduct();
					taxToBePaid = currOrderTax.getTax();
					if(currOrderReturnOrRTO != null){
						tdsToBeDeposited -= currOrderTax.getTdsToReturn();
						taxToBePaid -= currOrderTax.getToxToReturn();
					}
				}
				partnerBusiness.setTdsToBeDeposited(tdsToBeDeposited);
				partnerBusiness.setNetTaxToBePaid(taxToBePaid);
				double netEossValue = 0; // Only for Consolidated PO
				partnerBusiness.setNetEossValue(netEossValue);
				partnerBusiness.setNetPr(currOrder.getPr());
				partnerBusiness.setGrossNetRate(currOrder.getGrossNetRate());
				partnerBusiness.setNetRate(currOrder.getNetRate());
				partnerBusiness.setFinalStatus(currOrder.getFinalStatus());
				partnerBusiness.setGrossProfit(currOrder.getGrossProfit());
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

	private List<Order> fetchOrders(Session session, int sellerId, Date startDate, Date endDate) {
		List<Order> orderList = new ArrayList<>();
		Criteria criteria = session.createCriteria(Order.class);
		criteria.add(Restrictions.eq("poOrder", false));
		criteria.createAlias("seller", "seller",
				CriteriaSpecification.LEFT_JOIN)
				.add(Restrictions.eq("seller.id", sellerId))
				.add(Restrictions
						.between("shippedDate", startDate, endDate));
		criteria.createAlias("orderPayment", "orderPayment",
				CriteriaSpecification.LEFT_JOIN);
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
				.add(Restrictions.eq("seller.id", sellerId))
				.add(Restrictions
						.between("shippedDate", startDate, endDate));
		criteria.createAlias("orderPayment", "orderPayment",
				CriteriaSpecification.LEFT_JOIN);
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