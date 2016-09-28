package com.o2r.dao;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

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
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Repository;

import com.o2r.bean.ChannelSalesDetails;
import com.o2r.bean.ChargesBean;
import com.o2r.bean.DataConfig;
import com.o2r.bean.DebitNoteBean;
import com.o2r.bean.PartnerBean;
import com.o2r.bean.PoPaymentBean;
import com.o2r.bean.PoPaymentDetailsBean;
import com.o2r.bean.ChargesBean.SortByCriteria;
import com.o2r.bean.ChargesBean.SortByCriteriaRange;
import com.o2r.helper.CustomException;
import com.o2r.helper.GlobalConstant;
import com.o2r.model.Events;
import com.o2r.model.GatePass;
import com.o2r.model.NRnReturnCharges;
import com.o2r.model.NRnReturnConfig;
import com.o2r.model.Order;
import com.o2r.model.OrderPayment;
import com.o2r.model.OrderRTOorReturn;
import com.o2r.model.OrderTax;
import com.o2r.model.OrderTimeline;
import com.o2r.model.Partner;
import com.o2r.model.Product;
import com.o2r.model.ProductConfig;
import com.o2r.model.Seller;
import com.o2r.model.SellerAccount;
import com.o2r.model.SellerAlerts;
import com.o2r.model.TaxCategory;
import com.o2r.model.TaxDetail;
import com.o2r.service.AlertsService;
import com.o2r.service.EventsService;
import com.o2r.service.PartnerService;
import com.o2r.service.ProductService;
import com.o2r.service.SellerAccountService;
import com.o2r.service.TaxDetailService;

/**
 * @author Deep Mehrotra
 *
 */
@Repository("orderDao")
public class OrderDaoImpl implements OrderDao {

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private ProductService productService;
	@Autowired
	private TaxDetailService taxDetailService;
	@Autowired
	private PartnerService partnerService;
	@Autowired
	private EventsService eventsService;
	@Autowired
	private SellerAccountService sellerAccountService;
	@Autowired
	private AlertsService alertService;
	@Autowired
	private AreaConfigDao areaConfigDao;
	@Autowired
	private SellerDao sellerDao;
	@Autowired
	private DataConfig dataConfig;

	org.springframework.core.io.Resource resource = new ClassPathResource(
			"database.properties");
	Properties props = null;

	private final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");

	private final int pageSize = 500;

	static Logger log = Logger.getLogger(OrderDaoImpl.class.getName());

	/*
	 * @SuppressWarnings("deprecation")
	 * 
	 * @Override public void addOrder(Order order, int sellerId) throws
	 * CustomException {
	 * 
	 * log.info("*** AddOrder starts ***"); Seller seller = null; Date
	 * reconciledate = null; Date tempDate = null; Session session = null;
	 * TaxDetail taxDetails = null; Events event = null; Partner partner = null;
	 * Product product;
	 * 
	 * try { product = productService.getProduct(order.getProductSkuCode(),
	 * sellerId);
	 * 
	 * calculateDeliveryDate(order, sellerId); if (product != null) { try {
	 * session = sessionFactory.openSession(); session.beginTransaction();
	 * Criteria criteria = session.createCriteria(Seller.class)
	 * .add(Restrictions.eq("id", sellerId)); criteria.createAlias("partners",
	 * "partner", CriteriaSpecification.LEFT_JOIN)
	 * .add(Restrictions.eq("partner.pcName", order.getPcName()).ignoreCase())
	 * .setResultTransformer( CriteriaSpecification.DISTINCT_ROOT_ENTITY);
	 * seller = (Seller) criteria.list().get(0); float taxpercent =
	 * taxDetailService.getTaxCategory( order.getOrderTax().getTaxCategtory(),
	 * sellerId) .getTaxPercent(); if (seller.getPartners() != null &&
	 * seller.getPartners().size() != 0) { partner =
	 * seller.getPartners().get(0); reconciledate = getreconciledate(order,
	 * seller .getPartners().get(0), order.getOrderDate()); if (reconciledate !=
	 * null) order.setPaymentDueDate(reconciledate);
	 * log.debug(" after settinf rec date delivery date :" +
	 * order.getDeliveryDate()); } order.setStatus("Shipped"); if (partner !=
	 * null && partner.getNrnReturnConfig() != null &&
	 * partner.getNrnReturnConfig().isNrCalculator()) {
	 * 
	 * // Check conditions here.... event = eventsService.isEventActiive(
	 * order.getOrderDate(), partner.getPcName(), sellerId); if (event != null)
	 * {
	 * 
	 * order.setEventName(event.getEventName()); event.setNetSalesQuantity(event
	 * .getNetSalesQuantity() + order.getQuantity());
	 * 
	 * if (event.getNrnReturnConfig() .getNrCalculatorEvent()
	 * .equalsIgnoreCase("variable")) { if
	 * (!calculateNR(event.getNrnReturnConfig(), order,
	 * product.getCategoryName(), product.getDeadWeight(),
	 * product.getVolWeight(),sellerId)) throw new Exception(); } else if
	 * (event.getNrnReturnConfig() .getNrCalculatorEvent()
	 * .equalsIgnoreCase("original")) { if
	 * (!calculateNR(seller.getPartners().get(0), order,
	 * product.getCategoryName(), product.getDeadWeight(),
	 * product.getVolWeight())) throw new Exception(); }
	 * 
	 * } else if (!calculateNR(seller.getPartners().get(0), order,
	 * product.getCategoryName(), product.getDeadWeight(),
	 * product.getVolWeight())) throw new Exception();
	 * 
	 * log.debug(" Shipping charges :" + order.getShippingCharges() +
	 * " >> Gross net rate " + order.getGrossNetRate() + " delivery date :" +
	 * order.getDeliveryDate()); } else {
	 * 
	 * props = PropertiesLoaderUtils.loadProperties(resource);
	 * order.setPartnerCommission((order.getOrderSP() - order
	 * .getGrossNetRate()) * order.getQuantity());
	 * 
	 * if(partner.isTdsApplicable()) order.getOrderTax().setTdsToDeduct(
	 * (order.getPartnerCommission() - (order .getPartnerCommission() * 100
	 * /(100 + Double.parseDouble(props.getProperty("serviceTax")))))
	 * (((props.getProperty("TDS")) != null ?
	 * Double.parseDouble(props.getProperty("TDS")) :0) /100) *
	 * order.getQuantity());
	 * 
	 * } order.setOrderMRP(order.getOrderMRP() * order.getQuantity());
	 * order.setOrderSP(order.getOrderSP() * order.getQuantity());
	 * order.setNetRate(order.getGrossNetRate() order.getQuantity()); if (event
	 * != null) { event.setNetSalesAmount(event.getNetSalesAmount() +
	 * order.getNetRate()); eventsService.addEvent(event, sellerId); }
	 * 
	 * order.setDiscount((Math.abs(order.getOrderMRP() - order.getOrderSP())));
	 * log.debug(" Tax cal SP:" + order.getOrderSP() + " >>TAxReate=" +
	 * taxpercent + "  Tax>>" + (order.getOrderSP() - (order.getOrderSP() * (100
	 * / (100 + seller .getPartners().get(0).getTaxrate())))));
	 * order.getOrderTax() .setTax(order.getOrderSP() - (order.getOrderSP() *
	 * (100 / (100 + taxpercent)))); taxDetails = new TaxDetail();
	 * taxDetails.setBalanceRemaining(order.getOrderTax() .getTax());
	 * taxDetails.setParticular(order.getOrderTax() .getTaxCategtory());
	 * taxDetails.setUploadDate(order.getOrderDate());
	 * taxDetailService.addMonthlyTaxDetail(session, taxDetails, sellerId);
	 * 
	 * 
	 * order.setTotalAmountRecieved(order.getNetRate());
	 * order.setFinalStatus("In Process"); // Set Order Timeline OrderTimeline
	 * timeline = new OrderTimeline(); // populating tax related values of order
	 * if (seller.getPartners().get(0).isTdsApplicable()) { log.debug(" PC " +
	 * order.getPartnerCommission()); taxDetails = new TaxDetail();
	 * taxDetails.setBalanceRemaining(order.getOrderTax() .getTdsToDeduct());
	 * taxDetails.setParticular("TDS");
	 * taxDetails.setUploadDate(order.getShippedDate());
	 * taxDetailService.addMonthlyTDSDetail(session, taxDetails, sellerId); } //
	 * Reducing Product Inventory For Order
	 * productService.updateInventory(order.getProductSkuCode(), 0, 0,
	 * order.getQuantity(), false, sellerId,order.getShippedDate()); checking if
	 * customer is available log.debug(" Customer Email id in add order :" +
	 * order.getCustomer().getCustomerEmail());
	 * order.getCustomer().setSellerId(sellerId);
	 * order.getCustomer().getOrders().add(order);
	 * 
	 * // Adding order to the Partner if (partner.getOrders() != null &&
	 * order.getOrderId() == 0) { partner.getOrders().add(order); }
	 * 
	 * // Setting payment difference for old orders if
	 * (order.getPaymentDueDate().compareTo(
	 * java.util.Calendar.getInstance().getTime()) < 0) {
	 * order.getOrderPayment().setPaymentDifference( 0 - order.getNetRate());
	 * order.setStatus("Payment Disputed"); order.setFinalStatus("Actionable");
	 * 
	 * }
	 * 
	 * // Setting return and rto limits tempDate = (Date)
	 * order.getDeliveryDate().clone(); tempDate.setDate(tempDate.getDate() +
	 * partner.getMaxReturnAcceptance()); order.setReturnLimitCrossed(tempDate);
	 * tempDate = (Date) order.getDeliveryDate().clone();
	 * tempDate.setDate(tempDate.getDate() + partner.getMaxRTOAcceptance());
	 * order.setrTOLimitCrossed(tempDate); // Setting Pr and Gross Profit for
	 * Order order.setPr(order.getNetRate() - order.getOrderTax().getTax());
	 * 
	 * order.setGrossProfit(order.getPr() - (product.getProductPrice() *
	 * order.getQuantity()));
	 * 
	 * // Setting order status if return limit is crossed if
	 * (order.getReturnLimitCrossed().compareTo(
	 * java.util.Calendar.getInstance().getTime()) < 0) {
	 * order.setStatus("Return Limit Crossed"); } if (order.getOrderId() != 0) {
	 * System.out.println(" Saving edited order"); // Code for order timeline
	 * timeline.setEventDate(new Date()); timeline.setEvent(" Order Edited");
	 * order.getOrderTimeline().add(timeline); order.setLastActivityOnOrder(new
	 * Date()); session.merge(order); } else {
	 * log.debug("******Saving new  order delivery date : " +
	 * order.getDeliveryDate()); // Code for order timeline
	 * timeline.setEvent("Order Created"); order.setLastActivityOnOrder(new
	 * Date()); timeline.setEventDate(new Date());
	 * order.getOrderTimeline().add(timeline); order.setSeller(seller);
	 * seller.getOrders().add(order); session.saveOrUpdate(partner);
	 * session.saveOrUpdate(seller); }
	 * taxDetailService.addMonthlyTaxDetail(session, taxDetails, sellerId); if
	 * (partner.isTdsApplicable()) { log.debug(" PC " +
	 * order.getPartnerCommission()); taxDetails = new TaxDetail();
	 * taxDetails.setBalanceRemaining(order.getOrderTax() .getTdsToDeduct());
	 * taxDetails.setParticular("TDS");
	 * taxDetails.setUploadDate(order.getShippedDate());
	 * taxDetailService.addMonthlyTDSDetail(session, taxDetails, sellerId); }
	 * productService.updateInventory(order.getProductSkuCode(), 0, 0,
	 * order.getQuantity(), false, sellerId,order.getShippedDate());
	 * session.getTransaction().commit();
	 * 
	 * } catch (Exception e) { log.debug("Inside exception in add order " +
	 * e.getLocalizedMessage() + " message: " + e.getMessage());
	 * e.printStackTrace(); log.error("Failed!", e); } finally {
	 * session.close(); } } } catch (Exception e) { e.printStackTrace();
	 * log.error("Failed!", e); log.info("Error : " +
	 * GlobalConstant.addOrderError); log.info("Error : " +
	 * GlobalConstant.addOrderErrorCode); throw new
	 * CustomException(GlobalConstant.addOrderError, new Date(), 1,
	 * GlobalConstant.addOrderErrorCode, e); }
	 * log.info("*** AddOrder ends ***"); }
	 */

	@SuppressWarnings("deprecation")
	@Override
	public void addOrder(List<Order> orderList, int sellerId)
			throws CustomException {

		log.info("*** AddOrder List starts ***");
		Seller seller = null;
		Date reconciledate = null;
		Date tempDate = null;
		Session session = null;
		TaxDetail taxDetails = null;
		Events event = null;
		Partner partner = null;
		Product product;
		boolean status = true;
		StringBuffer erroneousOrders = null;
		int orderCount = 0;
		try {

			session = sessionFactory.openSession();
			session.beginTransaction();
			if (orderList != null && orderList.size() != 0) {
				orderCount = orderList.size();
				Criteria criteria = session.createCriteria(Seller.class).add(
						Restrictions.eq("id", sellerId));
				seller = (Seller) criteria.list().get(0);
				erroneousOrders = new StringBuffer("");
				for (Order order : orderList) {

					try {
						product = productService.getProduct(
								order.getProductSkuCode(), sellerId);
						if(product != null){
							order.setProductSkuCode(product.getProductSkuCode());
						}
						partner = partnerService.getPartner(order.getPcName(),
								sellerId);

						partner = (Partner) session.get(Partner.class,
								partner.getPcId());
						Hibernate.initialize(partner.getOrders());

						calculateDeliveryDate(order, sellerId);

						float taxpercent = taxDetailService
								.getTaxCategory(
										order.getOrderTax().getTaxCategtory(),
										sellerId).getTaxPercent();

						reconciledate = getreconciledate(order, partner, order.getOrderDate());
						if (reconciledate != null)
							order.setPaymentDueDate(reconciledate);
						log.debug(" after settinf rec date delivery date :"
								+ order.getDeliveryDate());

						order.setStatus("Shipped");
						if (partner != null
								&& partner.getNrnReturnConfig() != null
								&& partner.getNrnReturnConfig()
										.isNrCalculator()) {

							// Check conditions here....
							event = eventsService.isEventActiive(
									order.getOrderDate(), partner.getPcName(),
									order.getProductSkuCode(), sellerId);
							if (event != null) {

								order.setEventName(event.getEventName());
								event.setNetSalesQuantity(event
										.getNetSalesQuantity()
										+ order.getQuantity());

								if (event.getNrnReturnConfig()
										.getNrCalculatorEvent()
										.equalsIgnoreCase("variable")) {
									if (!calculateNR(
											event.getNrnReturnConfig(), order,
											product.getCategoryName(),
											product.getDeadWeight(),
											product.getVolWeight(), sellerId))
										throw new Exception();
								} else if (event.getNrnReturnConfig()
										.getNrCalculatorEvent()
										.equalsIgnoreCase("original")) {
									if (!calculateNR(partner, order,
											product.getCategoryName(),
											product.getDeadWeight(),
											product.getVolWeight()))
										throw new Exception();
								}

							} else if (!calculateNR(partner, order,
									product.getCategoryName(),
									product.getDeadWeight(),
									product.getVolWeight()))
								throw new Exception();

							log.debug(" Shipping charges :"
									+ order.getShippingCharges()
									+ " >> Gross net rate "
									+ order.getGrossNetRate()
									+ " delivery date :"
									+ order.getDeliveryDate());
						} else {

							props = PropertiesLoaderUtils
									.loadProperties(resource);
							order.setPartnerCommission((order.getOrderSP() - order
									.getGrossNetRate()) * order.getQuantity());

							if (partner.isTdsApplicable())
								order.getOrderTax()
										.setTdsToDeduct(
												(order.getPartnerCommission() - (order
														.getPartnerCommission() * 100 / (100 + Double.parseDouble(props
														.getProperty("serviceTax")))))
														* (((props
																.getProperty("TDS")) != null ? Double
																.parseDouble(props
																		.getProperty("TDS"))
																: 0) / 100)
														* order.getQuantity());

						}
						order.setOrderMRP(order.getOrderMRP()
								* order.getQuantity());
						order.setOrderSP(order.getOrderSP()
								* order.getQuantity());
						order.setNetRate(order.getGrossNetRate()
								* order.getQuantity());
						if (event != null) {
							event.setNetSalesAmount(event.getNetSalesAmount()
									+ order.getNetRate());
							eventsService.addEvent(event, sellerId);
						}

						if(order.getOrderMRP()>order.getOrderSP())
						order.setDiscount((Math.abs(order.getOrderMRP()
								- order.getOrderSP())));
						log.debug(" Tax cal SP:"
								+ order.getOrderSP()
								+ " >>TAxReate="
								+ taxpercent
								+ "  Tax>>"
								+ (order.getOrderSP() - (order.getOrderSP() * (100 / (100 + seller
										.getPartners().get(0).getTaxrate())))));
						order.getOrderTax()
								.setTax(order.getOrderSP()
										- (order.getOrderSP() * (100 / (100 + taxpercent))));
						taxDetails = new TaxDetail();
						taxDetails.setBalanceRemaining(order.getOrderTax()
								.getTax());
						taxDetails.setParticular(order.getOrderTax()
								.getTaxCategtory());
						taxDetails.setUploadDate(order.getOrderDate());
						/*
						 * taxDetailService.addMonthlyTaxDetail(session,
						 * taxDetails, sellerId);
						 */

						order.setTotalAmountRecieved(order.getNetRate());
						order.setFinalStatus("In Process");
						// Set Order Timeline
						OrderTimeline timeline = new OrderTimeline();
						/* checking if customer is available */
						log.debug(" Customer Email id in add order :"
								+ order.getCustomer().getCustomerEmail());
						order.getCustomer().setSellerId(sellerId);
						order.getCustomer().getOrders().add(order);

						// Adding order to the Partner
						if (partner.getOrders() != null
								&& order.getOrderId() == 0) {
							partner.getOrders().add(order);
						}

						// Setting payment difference for old orders
						if (order.getPaymentDueDate().compareTo(
								java.util.Calendar.getInstance().getTime()) < 0) {
							order.getOrderPayment().setPaymentDifference(
									0 - order.getNetRate());
							order.setStatus("Payment Disputed");
							order.setFinalStatus("Actionable");

						}

						// Setting return and rto limits
						tempDate = (Date) order.getDeliveryDate().clone();
						tempDate.setDate(tempDate.getDate()
								+ partner.getMaxReturnAcceptance());
						order.setReturnLimitCrossed(tempDate);
						tempDate = (Date) order.getDeliveryDate().clone();
						tempDate.setDate(tempDate.getDate()
								+ partner.getMaxRTOAcceptance());
						order.setrTOLimitCrossed(tempDate);
						// Setting Pr and Gross Profit for Order
						order.setPr(order.getNetRate()
								- order.getOrderTax().getTax());

						order.setGrossProfit(order.getPr()
								- (product.getProductPrice() * order
										.getQuantity()));
						order.setGrossMargin(order.getGrossProfit());
						order.setProductCost(product.getProductPrice());

						if (order.getOrderId() != 0) {
							System.out.println(" Saving edited order");
							// Code for order timeline
							timeline.setEventDate(new Date());
							timeline.setEvent(" Order Edited");
							order.getOrderTimeline().add(timeline);
							order.setLastActivityOnOrder(new Date());
							session.merge(order);
						} else {
							log.debug("******Saving new  order delivery date : "
									+ order.getDeliveryDate());
							// Code for order timeline
							timeline.setEvent("Order Created");
							order.setLastActivityOnOrder(new Date());
							timeline.setEventDate(new Date());
							order.getOrderTimeline().add(timeline);
							order.setSeller(seller);
							seller.getOrders().add(order);
							session.saveOrUpdate(partner);
							session.saveOrUpdate(seller);
						}
						taxDetailService.addMonthlyTaxDetail(session,
								taxDetails, sellerId);
						if (partner.isTdsApplicable()) {
							log.debug(" PC " + order.getPartnerCommission());
							taxDetails = new TaxDetail();
							taxDetails.setBalanceRemaining(order.getOrderTax()
									.getTdsToDeduct());
							taxDetails.setParticular("TDS");
							taxDetails.setUploadDate(order.getShippedDate());
							taxDetailService.addMonthlyTDSDetail(session,
									taxDetails, sellerId);
						}
						productService.updateInventory(
								order.getProductSkuCode(), 0, 0,
								order.getQuantity(), false, sellerId,
								order.getShippedDate());
						session.getTransaction().commit();

					} catch (Exception e) {
						status = false;
						erroneousOrders.append(order.getChannelOrderID() + ",");
						e.printStackTrace();
						log.error("Failed! by sellerId : " + sellerId, e);
						orderCount--;
					}
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			log.info("Error : " + GlobalConstant.addOrderError);
			log.info("Error : " + GlobalConstant.addOrderErrorCode);
			throw new CustomException(erroneousOrders.toString(), new Date(),
					1, GlobalConstant.addOrderErrorCode, e);
		} finally {
			session.close();
		}
		try {
			SellerAccount sellerAcc = seller.getSellerAccount();
			if (sellerAcc != null) {
				sellerAcc.setOrderBucket(sellerAcc.getOrderBucket()
						- orderCount);
				sellerAcc.setSellerId(sellerId);
				sellerAccountService.saveSellerAccount(sellerAcc);
			}
		} catch (Exception e) {
			log.error("Failed to Update Bucket ! SellerController ", e);
			e.printStackTrace();
		}
		if (!status)
			throw new CustomException(erroneousOrders.toString(), new Date(),
					1, GlobalConstant.addOrderErrorCode, new Exception());
		log.info("*** AddOrder List ends ***");
	}

	@Override
	public Order addPO(Order order, int sellerId) throws CustomException {

		log.info("*** addPO Starts ***");
		order.setPoOrder(true);
		// order.setOrderDate(new Date());

		Seller seller = null;
		Date reconciledate = null;

		Session session = null;
		Partner partner = null;
		Product product = null;
		ProductConfig productConfig = null;
		List<ProductConfig> productConfigs = null;
		try {

			productConfigs = productService.getProductConfig(
					order.getProductSkuCode(), order.getPcName(), sellerId);
			if (productConfigs != null && productConfigs.size() == 1) {
				productConfig = productConfigs.get(0);
				order.setProductConfig(productConfig);
				product = productService.getProduct(
						productConfig.getProductSkuCode(), sellerId);
			}
			if (product != null) {
				try {
					session = sessionFactory.openSession();
					session.beginTransaction();
					Criteria criteria = session.createCriteria(Seller.class)
							.add(Restrictions.eq("id", sellerId));
					criteria.createAlias("partners", "partner",
							CriteriaSpecification.LEFT_JOIN)
							.add(Restrictions.eq("partner.pcName",
									order.getPcName()).ignoreCase())
							.setResultTransformer(
									CriteriaSpecification.DISTINCT_ROOT_ENTITY);
					seller = (Seller) criteria.list().get(0);
					if (seller.getPartners() != null
							&& seller.getPartners().size() != 0) {
						partner = seller.getPartners().get(0);
					}

					reconciledate = getreconciledate(order, partner,
							order.getOrderDate());
					if (reconciledate != null) {
						order.setPaymentDueDate(reconciledate);
					}

					order.setStatus("Shipped");
					if (order.getPcName().equalsIgnoreCase(
							GlobalConstant.PCMYNTRA)) {
						order.setEossValue((order.getPoPrice() * order
								.getProductConfig().getDiscount()) / 100);
					} else {
						order.setEossValue(0);
					}

					order.setGrossNetRate(order.getPoPrice()
							- order.getEossValue());
					order.setNetRate(order.getGrossNetRate()
							* order.getQuantity());
					double taxvalue = (float) (order.getPoPrice() - (order
							.getPoPrice() * 100 / (100 + order
							.getProductConfig().getTaxPo())));

					order.setOrderMRP(order.getProductConfig().getMrp()
							* order.getQuantity());
					order.setOrderSP(order.getProductConfig().getProductPrice()
							* order.getQuantity());
					order.setPoPrice(order.getPoPrice() * order.getQuantity());

					order.setPartnerCommission(order.getProductConfig()
							.getCommisionAmt() * order.getQuantity());
					order.setDiscount(((order.getPoPrice() * order
							.getProductConfig().getDiscount()) / 100)
							* order.getQuantity());

					// order.setTotalAmountRecieved(order.getNetRate());
					order.setFinalStatus("In Process");
					// Set Order Timeline
					OrderTimeline timeline = new OrderTimeline();
					// populating tax related values of order

					System.out.println(" Tax before pr:" + taxvalue);
					order.setPr((order.getGrossNetRate() - taxvalue)
							* order.getQuantity());

					// Reducing Product Inventory For Order
					productService.updateInventory(order.getProductConfig()
							.getProductSkuCode(), 0, 0, order.getQuantity(),
							false, sellerId, order.getShippedDate());
					// Adding order to the Partner
					if (partner.getOrders() != null && order.getOrderId() == 0) {
						partner.getOrders().add(order);
					}
					// Setting Gross Profit for Order
					order.setGrossProfit(order.getPr()
							- (order.getProductConfig().getProductPrice() * order
									.getQuantity()));

					order.setEossValue(order.getEossValue()
							* order.getQuantity());
					order.getOrderTax().setTax(taxvalue * order.getQuantity());
					order.getOrderTax().setTaxCategtory(
							productConfig.getTaxPoCategory());

					order.getOrderTax().setTaxToReturn(
							order.getProductConfig().getTaxSpAmt()
									* order.getQuantity());

					if (order.getOrderId() != 0) {
						System.out.println(" Saving edited order");
						// Code for order timeline
						timeline.setEventDate(new Date());
						timeline.setEvent(" Order Edited");
						order.getOrderTimeline().add(timeline);
						order.setLastActivityOnOrder(new Date());
						session.merge(order);
					} else {
						log.debug("*********Saving new  order delivery date :"
								+ order.getDeliveryDate());
						// Code for order timeline
						timeline.setEvent("Order Created");
						order.setLastActivityOnOrder(new Date());
						timeline.setEventDate(new Date());
						order.getOrderTimeline().add(timeline);
						order.setSeller(seller);
						seller.getOrders().add(order);
					}

					TaxDetail taxDetails = new TaxDetail();
					taxDetails
							.setBalanceRemaining(order.getOrderTax().getTax());
					taxDetails.setParticular(order.getOrderTax()
							.getTaxCategtory());
					taxDetails.setUploadDate(order.getOrderDate());
					taxDetailService.addMonthlyTaxDetail(session, taxDetails,
							sellerId);

					session.saveOrUpdate(partner);
					session.saveOrUpdate(seller);
					session.getTransaction().commit();

				} catch (Exception e) {
					log.error("Failed! by sellerId : " + sellerId, e);
					log.debug("Inside exception in add order "
							+ e.getLocalizedMessage() + " message: "
							+ e.getMessage());
					e.printStackTrace();
				} finally {
					session.close();
				}
				
				try {
					SellerAccount sellerAcc = seller.getSellerAccount();
					if (sellerAcc != null) {
						sellerAcc.setOrderBucket(sellerAcc.getOrderBucket() - 1);
						sellerAcc.setSellerId(sellerId);
						sellerAccountService.saveSellerAccount(sellerAcc);
						if(sellerAcc.getOrderBucket() < 1){
							SellerAlerts sellerAlert = new SellerAlerts();
							sellerAlert.setAlertDate(new Date());
							sellerAlert.setAlertType("Order");
							sellerAlert.setAlertMessage(GlobalConstant.OrderMsg);
							sellerAlert.setStatus("unread");
							alertService.saveAlerts(sellerAlert, sellerId);
						}
					}
				} catch (Exception e) {
					log.error("Failed to Update Bucket ! SellerController ", e);
					e.printStackTrace();
				}
			}
			log.info("*** addPO ends ***");
			return order;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			log.info("Error : " + GlobalConstant.addOrderError);
			log.info("Error : " + GlobalConstant.addOrderErrorCode);
			throw new CustomException(GlobalConstant.addOrderError, new Date(),
					1, GlobalConstant.addOrderErrorCode, e);
		}
	}

	@Override
	public List<Order> listOrders(int sellerId) throws CustomException {

		log.info("*** listOrders Starts ***");
		List<Order> returnlist = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Seller seller = (Seller) session.get(Seller.class, sellerId);
			if (seller.getOrders() != null && seller.getOrders().size() != 0)
				returnlist = seller.getOrders();
			returnlist = seller.getOrders();
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.listOrderError,
					new Date(), 3, GlobalConstant.listOrderErrorCode, e);
		}
		log.info("*** listOrders ends ***");
		return returnlist;
	}

	@Override
	public List<Order> listOrders(int sellerId, int pageNo)
			throws CustomException {

		log.info("*** listOrders with sellerId n pageNo Starts ***");
		List<Order> returnlist = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Order.class);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.eq("poOrder", false));

			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			criteria.addOrder(org.hibernate.criterion.Order
					.desc("lastActivityOnOrder"));
			criteria.setFirstResult(pageNo * pageSize);
			criteria.setMaxResults(pageSize);
			returnlist = criteria.list();
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.listOrdersError,
					new Date(), 3, GlobalConstant.listOrdersErrorCode, e);

		}
		log.info("*** listOrders with sellerId n pageNo ends***");
		return returnlist;
	}

	@Override
	public List<Order> listMpOrders(int sellerId) throws CustomException {

		List<Order> orderList = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Order.class);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.eq("poOrder", false));

			if (criteria != null && criteria.list().size() != 0) {
				orderList = criteria.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.listOrdersError,
					new Date(), 3, GlobalConstant.listOrdersErrorCode, e);
		}
		return orderList;
	}

	@Override
	public List<Order> listPOOrders(int sellerId, int pageNo)
			throws CustomException {

		log.info("*** listPOOrders with sellerId n pageNo Starts ***");
		List<Order> returnlist = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Order.class);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.eq("poOrder", true))
					.add(Restrictions.isNull("consolidatedOrder"))
					.add(Restrictions.disjunction()
							.add(Restrictions.isNotNull("shippedDate"))
							.add(Restrictions.isNotNull("orderReturnOrRTO")));
			// .add(Restrictions.isNull("orderReturnOrRTO"));

			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			criteria.addOrder(org.hibernate.criterion.Order
					.desc("lastActivityOnOrder"));
			criteria.setFirstResult(pageNo * pageSize);
			criteria.setMaxResults(pageSize);
			returnlist = criteria.list();
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.listOrdersError,
					new Date(), 3, GlobalConstant.listOrdersErrorCode, e);
		}
		log.info("*** listPOOrders with sellerId n pageNo ends ***");
		return returnlist;
	}

	@Override
	public Order getOrder(int orderId) throws CustomException {

		log.info("*** getOrder Starts ***");
		Order returnorder = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();

			returnorder = (Order) session.get(Order.class, orderId);
			Hibernate.initialize(returnorder.getOrderReturnOrRTO());
			Hibernate.initialize(returnorder.getOrderTax());
			Hibernate.initialize(returnorder.getOrderPayment());
			Hibernate.initialize(returnorder.getOrderTimeline());
			Hibernate.initialize(returnorder.getCustomer());
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed!", e);
			throw new CustomException(GlobalConstant.getOrderError, new Date(),
					3, GlobalConstant.getOrderErrorCode, e);

		}
		log.info("*** getOrder ends ***");
		return returnorder;
	}

	@Override
	public Order getConsolidatedOrder(String poId, String invoiceId)
			throws CustomException {

		log.info("*** getConsolidatedOrder starts ***");
		Order order = null;
		List returnList = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Order.class);
			criteria.add(Restrictions.eq("subOrderID", poId));
			criteria.add(Restrictions.eq("invoiceID", invoiceId));
			criteria.add(Restrictions.eq("poOrder", true));
			criteria.add(Restrictions.isNull("consolidatedOrder.orderId"));

			returnList = criteria.list();
			if (returnList != null && returnList.size() != 0
					&& returnList.get(0) != null) {

				order = (Order) returnList.get(0);
				Hibernate.initialize(order.getOrderReturnOrRTO());
				Hibernate.initialize(order.getOrderTax());
				Hibernate.initialize(order.getOrderPayment());
				Hibernate.initialize(order.getOrderTimeline());
				Hibernate.initialize(order.getCustomer());
			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed!", e);
			throw new CustomException(GlobalConstant.getOrderError, new Date(),
					3, GlobalConstant.getOrderErrorCode, e);

		}
		log.info("*** getConsolidatedOrder ends ***");
		return order;
	}

	@Override
	public Order getOrder(int orderId, int sellerId) throws CustomException {
		Order returnorder = null;
		List returnList = null;
		try {

			log.info("*** getOrder by Order & seller ID starts : OrderDaoImpl ***");
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Order.class);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.eq("orderId", orderId));
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			returnList = criteria.list();
			if (returnList != null && returnList.size() != 0
					&& returnList.get(0) != null) {

				returnorder = (Order) returnList.get(0);
				Hibernate.initialize(returnorder.getOrderReturnOrRTO());
				Hibernate.initialize(returnorder.getOrderTax());
				Hibernate.initialize(returnorder.getOrderPayment());
				Hibernate.initialize(returnorder.getOrderTimeline());
				Hibernate.initialize(returnorder.getCustomer());
			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.getOrderError, new Date(),
					3, GlobalConstant.getOrderErrorCode, e);
		}
		log.info("*** getOrder by Order & seller ID ends : OrderDaoImpl ***");
		return returnorder;
	}

	@Override
	public List<Order> getPOOrdersFromConsolidated(int orderId, int sellerId)
			throws CustomException {

		log.info("*** getPOOrdersFromConsolidated starts : OrderDaoImpl ***");
		Seller seller = null;
		List<Order> orderlist = null;

		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", sellerId));
			criteria.createAlias("orders", "order",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("order.consolidatedOrder.orderId",
							orderId))
					.add(Restrictions.eq("order.poOrder", true))
					.addOrder(
							org.hibernate.criterion.Order
									.desc("order.lastActivityOnOrder"))
					.setResultTransformer(
							CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			if (criteria.list().size() != 0) {
				seller = (Seller) criteria.list().get(0);
				if (seller == null)
					System.out.println("Null seller");
				orderlist = seller.getOrders();
				if (orderlist != null && orderlist.size() != 0) {
					for (Order order : orderlist) {
						Hibernate.initialize(order.getOrderTimeline());
					}
				}
			}
			session.getTransaction().commit();
			session.close();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.findOrdersError,
					new Date(), 2, GlobalConstant.findOrdersErrorcode, e);
		}
		log.info("*** getPOOrdersFromConsolidated ends : OrderDaoImpl ***");
		return orderlist;
	}

	@Override
	public List<GatePass> getGatepassesFromConsolidated(int returnId,
			int sellerId) throws CustomException {

		log.info("*** getGatepassesFromConsolidated starts : OrderDaoImpl ***");
		Seller seller = null;
		List<GatePass> gatepassList = null;

		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(GatePass.class);
			criteria.add(Restrictions.eq("consolidatedReturn.returnId",
					returnId));

			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

			if (criteria.list().size() != 0) {
				gatepassList = criteria.list();
			}
			session.getTransaction().commit();
			session.close();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.findOrdersError,
					new Date(), 2, GlobalConstant.findOrdersErrorcode, e);
		}
		log.info("*** getGatepassesFromConsolidated ends : OrderDaoImpl ***");
		return gatepassList;
	}

	@Override
	public void deleteOrder(Order order, int sellerId) throws CustomException {

		log.info("*** deleteOrder starts : OrderDaoImpl ***");
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Query deleteQuery = session
					.createSQLQuery("delete from Seller_Order_Table where seller_Id=? and orders_orderId=?");
			deleteQuery.setInteger(0, sellerId);
			deleteQuery.setInteger(1, order.getOrderId());

			int updated = deleteQuery.executeUpdate();
			int orderdelete = session.createQuery(
					"DELETE FROM Order WHERE orderId = " + order.getOrderId())
					.executeUpdate();
			log.debug(" Deletion process successful :updated" + updated
					+ "orderdelete " + orderdelete);
			session.getTransaction().commit();
			session.close();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.deleteOrderError,
					new Date(), 3, GlobalConstant.deleteOrderErrorCode, e);
		}
		log.info("*** deleteOrder ends : OrderDaoImpl ***");
	}

	@Override
	public void addReturnOrder(String channelOrderId,
			OrderRTOorReturn orderReturn, int sellerId) throws CustomException {

		log.info("*** addReturnOrder starts : OrderDaoImpl ***");
		Seller seller = null;
		Order order = null;
		Events event = null;
		TaxDetail taxDetails = null;
		TaxDetail tdsDetails = null;
		float returnChargesCalculated = 0;
		Partner partner = null;

		// To add change order status
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", sellerId));
			criteria.createAlias("orders", "order",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions
							.eq("order.channelOrderID", channelOrderId))
					.setResultTransformer(
							CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			seller = (Seller) criteria.list().get(0);
			if (seller.getOrders() != null && seller.getOrders().size() != 0) {
				order = seller.getOrders().get(0);

				// Check condition 4 Events here
				event = eventsService.isEventActiive(order.getOrderDate(),
						order.getPcName(), order.getProductSkuCode(), sellerId);
				if (event != null) {
					if (event.getNrnReturnConfig().getReturnCalculatorEvent()
							.equalsIgnoreCase("newTerms")) {
						returnChargesCalculated = calculateReturnCharges(order,
								orderReturn, sellerId,
								event.getNrnReturnConfig());
					} else {
						returnChargesCalculated = calculateReturnCharges(order,
								orderReturn, sellerId);
					}
				} else {
					returnChargesCalculated = calculateReturnCharges(order,
							orderReturn, sellerId);
				}
				if (orderReturn.getReturnorrtoQty() > 0) {
					returnChargesCalculated = returnChargesCalculated
							* orderReturn.getReturnorrtoQty();
				}
				orderReturn
						.setReturnOrRTOChargestoBeDeducted(returnChargesCalculated);
				order.setTotalAmountRecieved(order.getGrossNetRate()
						* (order.getQuantity() - orderReturn
								.getReturnorrtoQty()));

				order.getOrderReturnOrRTO().setReturnUploadDate(
						orderReturn.getReturnUploadDate());
				if (order.getReturnLimitCrossed().compareTo(
						orderReturn.getReturnDate()) < 0) {
					order.getOrderReturnOrRTO()
							.setReturnOrRTOChargestoBeDeducted(0);

					// Changing PD return quantity wise
					order.getOrderPayment().setPaymentDifference(
							order.getOrderPayment().getNetPaymentResult());
					order.setStatus("Return Limit Crossed");
					OrderTimeline timeline = new OrderTimeline();
					timeline.setEvent("Return Recieved");
					timeline.setEventDate(new Date());
					order.getOrderTimeline().add(timeline);
					OrderTimeline timeline1 = new OrderTimeline();
					timeline1.setEvent("Return Limit Crossed");
					timeline1.setEventDate(new Date());
					order.getOrderTimeline().add(timeline1);
				} else if (order.getrTOLimitCrossed().compareTo(
						orderReturn.getReturnDate()) < 0) {
					order.getOrderReturnOrRTO()
							.setReturnOrRTOChargestoBeDeducted(0);

					// Changing PD return quantity wise
					order.getOrderPayment().setPaymentDifference(
							order.getOrderPayment().getNetPaymentResult());
					order.setStatus("RTO Limit Crossed");
					OrderTimeline timeline = new OrderTimeline();
					timeline.setEvent("Return Recieved");
					timeline.setEventDate(new Date());
					order.getOrderTimeline().add(timeline);
					OrderTimeline timeline1 = new OrderTimeline();
					timeline1.setEvent("RTO Limit Crossed");
					timeline1.setEventDate(new Date());
					order.getOrderTimeline().add(timeline1);
				} else {
					order.getOrderReturnOrRTO()
							.setReturnOrRTOChargestoBeDeducted(
									orderReturn
											.getReturnOrRTOChargestoBeDeducted());
					order.getOrderPayment()
							.setPaymentDifference(
									order.getOrderPayment()
											.getNetPaymentResult()
											+ order.getOrderReturnOrRTO()
													.getReturnOrRTOChargestoBeDeducted());
					order.setStatus("Return Recieved");

					// setting gross profit

					OrderTimeline timeline = new OrderTimeline();
					timeline.setEvent("Return Recieved");
					timeline.setEventDate(new Date());
					order.getOrderTimeline().add(timeline);

				}

				if (orderReturn.getReturnorrtoQty() == order.getQuantity())
					order.setGrossProfit(-orderReturn
							.getReturnOrRTOChargestoBeDeducted());
				else {
					order.setGrossProfit(((order.getGrossProfit() / order
							.getQuantity()) * (order.getQuantity() - orderReturn
							.getReturnorrtoQty()))
							- orderReturn.getReturnOrRTOChargestoBeDeducted());
				}

				order.getOrderPayment().setPaymentDifference(
						order.getOrderPayment().getNetPaymentResult()
								- order.getTotalAmountRecieved()
								+ order.getOrderReturnOrRTO()
										.getReturnOrRTOChargestoBeDeducted());
				// Reverting Tax information for Return Order
				taxDetails = new TaxDetail();
				taxDetails
						.setBalanceRemaining(-(order.getOrderTax().getTax() / order
								.getQuantity())
								* orderReturn.getReturnorrtoQty());
				taxDetails.setParticular(order.getOrderTax().getTaxCategtory());
				taxDetails.setUploadDate(orderReturn.getReturnDate());
				taxDetailService.addMonthlyTaxDetail(session, taxDetails,
						sellerId);

				// Reverting TDS information for Return Order
				partner = partnerService
						.getPartner(order.getPcName(), sellerId);
				if (partner != null && partner.isTdsApplicable()) {
					tdsDetails = new TaxDetail();
					tdsDetails.setBalanceRemaining(-(order.getOrderTax()
							.getTdsToDeduct() / order.getQuantity())
							* orderReturn.getReturnorrtoQty());
					tdsDetails.setParticular("TDS");
					tdsDetails.setUploadDate(orderReturn.getReturnDate());
					taxDetailService.addMonthlyTDSDetail(session, tdsDetails,
							sellerId);
					// Adding TDS amount for Return Charges as per 2 % Return
					// Order
					tdsDetails = new TaxDetail();
					tdsDetails.setBalanceRemaining(order.getOrderReturnOrRTO()
							.getReturnOrRTOChargestoBeDeducted() / 50);
					tdsDetails.setParticular("TDS");
					tdsDetails.setUploadDate(orderReturn.getReturnDate());
					taxDetailService.addMonthlyTDSDetail(session, tdsDetails,
							sellerId);
					order.getOrderTax().setTdsonReturnAmt(
							order.getOrderReturnOrRTO()
									.getReturnOrRTOChargestoBeDeducted() / 50);
					order.getOrderTax().setTdsToReturn(
							(order.getOrderTax().getTdsToDeduct() / order
									.getQuantity())
									* orderReturn.getReturnorrtoQty());
				}
				order.getOrderTax().setTaxToReturn(
						(order.getOrderTax().getTax() / order.getQuantity())
								* orderReturn.getReturnorrtoQty());

				order.setFinalStatus("Actionable");
				order.getOrderReturnOrRTO().setReturnorrtoQty(
						orderReturn.getReturnorrtoQty());
				order.setNetSaleQuantity(order.getQuantity()
						- order.getOrderReturnOrRTO().getReturnorrtoQty());
				orderReturn.setReturnUploadDate(new Date());
				order.setLastActivityOnOrder(new Date());
				order.getOrderReturnOrRTO().setReturnDate(
						orderReturn.getReturnDate());
				order.getOrderReturnOrRTO().setReturnOrRTOId(
						orderReturn.getReturnOrRTOId());
				order.getOrderReturnOrRTO().setBadReturnQty(
						orderReturn.getBadReturnQty());

				order.getOrderReturnOrRTO().setReturnOrRTOreason(
						orderReturn.getReturnOrRTOreason());
				order.getOrderReturnOrRTO().setReturnOrRTOstatus("Return");

				productService
						.updateInventory(order.getProductSkuCode(), 0,
								(order.getOrderReturnOrRTO()
										.getReturnorrtoQty() - order
										.getOrderReturnOrRTO()
										.getBadReturnQty()), 0, false,
								sellerId, order.getOrderReturnOrRTO()
										.getReturnDate());

				order.setOrderReturnOrRTO(orderReturn);
				session.getTransaction().commit();
				session.close();
			}
		} catch (Exception e) {

			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.addReturnOrderError,
					new Date(), 1, GlobalConstant.addReturnOrderErrorCode, e);

		}
		log.info("*** addReturnOrder ends : OrderDaoImpl ***");
	}

	@Override
	public List<Order> findOrders(String column, String value, int sellerId,
			boolean poOrder, boolean isSearch) throws CustomException {

		log.info("*** findOrders starts : OrderDaoImpl ***");
		/* String searchString = "order." + column; */
		String searchString = column;
		System.out.println(" Inside Find order dao method searchString :"
				+ searchString + " value :" + value + "   sellerId :"
				+ sellerId);

		log.debug(" Inside Find order dao method searchString :" + searchString
				+ " value :" + value + "   sellerId :" + sellerId);

		List<Order> orderlist = null;
		Criteria criteria = null;
		List tempList = null;
		Session session = null;

		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			criteria = session.createCriteria(Order.class);
			criteria.createAlias("orderReturnOrRTO", "orderReturnOrRTO",
					CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("seller.id", sellerId));

			if (column.equals("returnOrRTOId")) {
				criteria.add(Restrictions.like(
						"orderReturnOrRTO.returnOrRTOId", value + "%"));
				tempList = criteria.list();
				if (tempList != null && tempList.size() != 0) {
					orderlist = tempList;
					if (orderlist != null && orderlist.size() != 0) {
						for (Order order : orderlist) {
							Hibernate.initialize(order.getOrderTimeline());
						}
					}
				}

				return orderlist;
			} else {

				if (isSearch == true) {
					criteria.add(Restrictions.like(searchString, value + "%")
							.ignoreCase());
				} else {
					criteria.add(Restrictions.eq(searchString, value));
				}
				if (poOrder)
					criteria.add(Restrictions.isNull("consolidatedOrder"));
				criteria.add(Restrictions.eq("poOrder", poOrder))
						.addOrder(
								org.hibernate.criterion.Order
										.desc("lastActivityOnOrder"))
						.setResultTransformer(
								CriteriaSpecification.DISTINCT_ROOT_ENTITY);

			}
			tempList = criteria.list();
			if (tempList != null && tempList.size() != 0
					&& tempList.get(0) != null) {
				orderlist = tempList;
				if (orderlist != null && orderlist.size() != 0) {
					for (Order order : orderlist) {
						Hibernate.initialize(order.getOrderTimeline());
					}
				}
			}
			session.getTransaction().commit();
			session.close();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.findOrdersError,
					new Date(), 2, GlobalConstant.findOrdersErrorcode, e);

		}
		log.info("*** findOrders ends : OrderDaoImpl ***");
		return orderlist;
	}

	@Override
	public List<Order> findOrdersbyDate(String column, Date startDate,
			Date endDate, int sellerId, boolean poOrder) throws CustomException {

		log.info("*** findOrdersbyDate starts : OrderDaoImpl ***");
		String searchString = null;
		searchString = "order." + column;
		Seller seller = null;
		List<Order> orderlist = null;
		List temp = null;

		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", sellerId));
			criteria.createAlias("orders", "order",
					CriteriaSpecification.LEFT_JOIN).add(
					Restrictions.between(searchString, startDate, endDate));
			Criterion rest1 = Restrictions.eq("order.poOrder", false);
			Criterion rest2 = Restrictions.and(
					Restrictions.eq("order.poOrder", true),
					Restrictions.isNull("order.consolidatedOrder"));
			criteria.add(Restrictions.or(rest1, rest2)).setResultTransformer(
					CriteriaSpecification.DISTINCT_ROOT_ENTITY);

			temp = criteria.list();
			if (temp != null && temp.size() != 0) {
				seller = (Seller) temp.get(0);
				orderlist = seller.getOrders();
			}

			if (orderlist != null && orderlist.size() != 0) {
				for (Order order : orderlist) {
					Hibernate.initialize(order.getOrderTimeline());
				}
			}
			if (orderlist == null || orderlist.size() == 0)
				System.out.println(" orderlist is null");
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.findOrdersbyDateError,
					new Date(), 2, GlobalConstant.findOrdersbyDateErrorCode, e);

		}
		log.info("*** findOrdersbyDate ends : OrderDaoImpl ***");
		return orderlist;
	}

	@Override
	public List<Order> findOrdersbyPaymentDate(String column, Date startDate,
			Date endDate, int sellerId) throws CustomException {

		log.info("*** findOrdersbyPaymentDate starts : OrderDaoImpl ***");
		List<Order> orderlist = null;

		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Order.class);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("orderPayment", "orderPayment",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.between("orderPayment.dateofPayment",
							startDate, endDate));
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			orderlist = criteria.list();

			if (orderlist != null && orderlist.size() != 0) {
				for (Order order : orderlist) {
					Hibernate.initialize(order.getOrderTimeline());
				}
			}

			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(
					GlobalConstant.findOrdersbyPaymentDateError, new Date(), 2,
					GlobalConstant.findOrdersbyPaymentDateErrorCode, e);

		}
		log.info("*** findOrdersbyPaymentDate ends : OrderDaoImpl ***");
		return orderlist;
	}

	@Override
	public List<Order> findOrdersbyReturnDate(String column, Date startDate,
			Date endDate, int sellerId) throws CustomException {

		log.info("*** findOrdersbyReturnDate starts : OrderDaoImpl ***");
		Seller seller = null;
		List<Order> orderlist = new ArrayList<Order>();
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", sellerId));
			seller = (Seller) criteria.list().get(0);
			if (seller.getOrders() != null) {
				for (Order order : seller.getOrders()) {
					if (order != null && order.getOrderReturnOrRTO() != null) {
						if (order.getOrderReturnOrRTO().getReturnDate()
								.compareTo(startDate) > 0
								&& order.getOrderReturnOrRTO().getReturnDate()
										.compareTo(endDate) < 0)
							orderlist.add(order);
					}
				}
				if (orderlist != null && orderlist.size() != 0) {
					for (Order order : orderlist) {
						Hibernate.initialize(order.getOrderTimeline());
					}
				}
			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			log.error("Failed! by sellerId : " + sellerId, e);
			e.printStackTrace();
			throw new CustomException(
					GlobalConstant.findOrdersbyReturnDateError, new Date(), 2,
					GlobalConstant.findOrdersbyReturnDateErrorCode, e);

		}
		log.info("*** findOrdersbyReturnDate ends : OrderDaoImpl ***");
		return orderlist;
	}

	@Override
	public void deleteReturnInfo(String orderId) {
		// To be implemented
	}

	@Override
	public List<Order> findOrdersbyCustomerDetails(String column, String value,
			int sellerId) throws CustomException {

		log.info("*** findOrdersbyCustomerDetails starts : OrderDaoImpl ***");
		Seller seller = null;
		List<Order> orderlist = new ArrayList<Order>();
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();

			Criteria criteria = session.createCriteria(Order.class);
			criteria.createAlias("customer", "customer",
					CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.eq("poOrder", false))
					.add(Restrictions.eq("customer." + column, value));
			if (criteria != null && criteria.list().size() != 0) {
				System.out.println(criteria.list().size());
				orderlist = criteria.list();
			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(
					GlobalConstant.findOrdersbyCustomerDetailsError,
					new Date(), 2,
					GlobalConstant.findOrdersbyCustomerDetailsErrorCode, e);
		}
		log.info("*** findOrdersbyCustomerDetails ends : OrderDaoImpl ***");
		return orderlist;
	}

	@Override
	public Order addOrderPayment(int orderid, OrderPayment orderPayment,
			int sellerId) throws CustomException {

		log.info("*** addOrderPayment from UI starts : OrderDaoImpl ***");
		Order order = null;
		// TaxDetail taxDetails = null;
		log.debug("Inside add ordr payment with order id " + orderid);
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Order.class).add(
					Restrictions.eq("orderId", orderid));
			if (criteria.list() != null && criteria.list().size() != 0) {
				order = (Order) criteria.list().get(0);
				Hibernate.initialize(order.getPaymentUpload());
			}
			if (order != null) {
				orderPayment.setNegativeAmount(Math.abs(orderPayment
						.getNegativeAmount()));
				if (order.getOrderReturnOrRTO() != null
						&& order.getOrderReturnOrRTO().getReturnDate() != null
						&& order.getOrderReturnOrRTO().getReturnorrtoQty() != 0) {

					if (order.getOrderReturnOrRTO().getReturnorrtoQty() != order
							.getQuantity()) {
						order.setTotalAmountRecieved(order.getGrossNetRate()
								* (order.getQuantity() - order
										.getOrderReturnOrRTO()
										.getReturnorrtoQty()));
					}
					if (orderPayment.getPositiveAmount() > 0) {

						order.getOrderPayment().setPositiveAmount(
								order.getOrderPayment().getPositiveAmount()
										+ orderPayment.getPositiveAmount());
						order.getOrderPayment().setNetPaymentResult(
								order.getOrderPayment().getNetPaymentResult()
										+ orderPayment.getPositiveAmount());

						order.getOrderPayment()
								.setPaymentDifference(
										order.getOrderPayment()
												.getNetPaymentResult()
												- order.getTotalAmountRecieved()
												+ order.getOrderReturnOrRTO()
														.getReturnOrRTOChargestoBeDeducted());
						order.setStatus("Payment Recieved");
						OrderTimeline timeline = new OrderTimeline();
						timeline.setEvent("Rs "
								+ orderPayment.getPositiveAmount()
								+ " Recieved");
						timeline.setEventDate(orderPayment.getDateofPayment());
						order.getOrderTimeline().add(timeline);

					} else {
						order.setStatus("Payment Deducted");
						OrderTimeline timeline = new OrderTimeline();
						timeline.setEvent("Rs "
								+ orderPayment.getNegativeAmount()
								+ " Deducted");
						timeline.setEventDate(orderPayment.getDateofPayment());
						order.getOrderTimeline().add(timeline);
						order.getOrderPayment().setNegativeAmount(
								order.getOrderPayment().getNegativeAmount()
										+ orderPayment.getNegativeAmount());
						order.getOrderPayment().setNetPaymentResult(
								order.getOrderPayment().getNetPaymentResult()
										- orderPayment.getNegativeAmount());
						if (order.getReturnLimitCrossed().compareTo(
								order.getOrderReturnOrRTO().getReturnDate()) < 0) {

							order.setStatus("Return Limit Crossed");
							OrderTimeline timeline1 = new OrderTimeline();
							timeline1.setEvent("Return Limit Crossed");
							timeline1.setEventDate(new Date());
							order.getOrderTimeline().add(timeline1);
						}
						order.getOrderPayment()
								.setPaymentDifference(
										order.getOrderPayment()
												.getNetPaymentResult()
												+ order.getOrderReturnOrRTO()
														.getReturnOrRTOChargestoBeDeducted()
												- order.getTotalAmountRecieved());
					}
				} else {

					if (orderPayment.getPositiveAmount() > 0) {

						order.getOrderPayment().setPositiveAmount(
								order.getOrderPayment().getPositiveAmount()
										+ orderPayment.getPositiveAmount());
						order.getOrderPayment().setNetPaymentResult(
								order.getOrderPayment().getNetPaymentResult()
										+ orderPayment.getPositiveAmount());
						order.getOrderPayment()
								.setPaymentDifference(
										order.getOrderPayment()
												.getNetPaymentResult()
												+ order.getOrderReturnOrRTO()
														.getReturnOrRTOChargestoBeDeducted()
												- order.getTotalAmountRecieved());
						log.debug("payment difference :"
								+ order.getOrderPayment()
										.getPaymentDifference());

						order.setStatus("Payment Recieved");
						OrderTimeline timeline = new OrderTimeline();
						timeline.setEvent("Rs "
								+ orderPayment.getPositiveAmount()
								+ " Recieved");
						timeline.setEventDate(orderPayment.getDateofPayment());
						order.getOrderTimeline().add(timeline);

					} else {
						order.setStatus("Return Not Recieved");
						OrderTimeline timeline = new OrderTimeline();
						timeline.setEvent("Rs "
								+ orderPayment.getNegativeAmount()
								+ " Deducted");
						timeline.setEventDate(orderPayment.getDateofPayment());
						order.getOrderTimeline().add(timeline);
						if (order.getReturnLimitCrossed().compareTo(new Date()) < 0) {
							order.setStatus("Return Limit Crossed");
							order.getOrderReturnOrRTO()
									.setReturnOrRTOChargestoBeDeducted(0);
							OrderTimeline timeline1 = new OrderTimeline();
							timeline1.setEvent("Return Limit Crossed");
							timeline1.setEventDate(new Date());
							order.getOrderTimeline().add(timeline1);
						}
						order.getOrderPayment().setNegativeAmount(
								order.getOrderPayment().getNegativeAmount()
										+ orderPayment.getNegativeAmount());
						order.getOrderPayment().setNetPaymentResult(
								order.getOrderPayment().getNetPaymentResult()
										- orderPayment.getNegativeAmount());

						order.getOrderPayment()
								.setPaymentDifference(
										order.getOrderPayment()
												.getNetPaymentResult()
												+ order.getOrderReturnOrRTO()
														.getReturnOrRTOChargestoBeDeducted()
												- order.getTotalAmountRecieved());

					}
				}
				order.getOrderPayment().setDateofPayment(
						orderPayment.getDateofPayment());
				order.getOrderPayment().setPaymentCycle(
						orderPayment.getPaymentCycle());
				order.getOrderPayment().setUploadDate(new Date());
				if (order.getOrderPayment().getPaymentDifference() < 1
						&& order.getOrderPayment().getPaymentDifference() > -1) {
					order.setFinalStatus("Settled");
				} else {

					order.setFinalStatus("Actionable");
				}

				order.setLastActivityOnOrder(new Date());
				session.saveOrUpdate(order);
				session.getTransaction().commit();
				session.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.addOrderPaymentError,
					new Date(), 1, GlobalConstant.addOrderPaymentErrorCode, e);

		}
		log.info("*** addOrderPayment ends : OrderDaoImpl ***");
		return order;
	}

	@Override
	public Order addOrderPayment(String skucode, String channelOrderId,
			OrderPayment orderPayment, int sellerId) throws CustomException {

		log.info("*** addOrderPayment with skuCode starts : OrderDaoImpl ***");
		Order order = null;
		log.debug(" SKUCODE: " + skucode + "  channedorderid " + channelOrderId
				+ "sellerId :" + sellerId);
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Order.class);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.eq("channelOrderID", channelOrderId));

			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			if (criteria.list() != null && criteria.list().size() != 0) {
				order = (Order) criteria.list().get(0);
			} else {
				log.debug("Order is Null....");
			}
			if (order != null) {
				orderPayment.setNegativeAmount(Math.abs(orderPayment
						.getNegativeAmount()));
				if (order.getOrderReturnOrRTO() != null
						&& order.getOrderReturnOrRTO().getReturnDate() != null
						&& order.getOrderReturnOrRTO().getReturnorrtoQty() != 0) {

					if (order.getOrderReturnOrRTO().getReturnorrtoQty() != order
							.getQuantity()) {
						order.setTotalAmountRecieved(order.getGrossNetRate()
								* (order.getQuantity() - order
										.getOrderReturnOrRTO()
										.getReturnorrtoQty()));
					}
					if (orderPayment.getPositiveAmount() > 0) {

						order.getOrderPayment().setPositiveAmount(
								order.getOrderPayment().getPositiveAmount()
										+ orderPayment.getPositiveAmount());
						order.getOrderPayment().setNetPaymentResult(
								order.getOrderPayment().getNetPaymentResult()
										+ orderPayment.getPositiveAmount());

						order.getOrderPayment()
								.setPaymentDifference(
										order.getOrderPayment()
												.getNetPaymentResult()
												- order.getTotalAmountRecieved()
												+ order.getOrderReturnOrRTO()
														.getReturnOrRTOChargestoBeDeducted());
						order.setStatus("Payment Recieved");
						OrderTimeline timeline = new OrderTimeline();
						timeline.setEvent("Rs "
								+ orderPayment.getPositiveAmount()
								+ " Recieved");
						timeline.setEventDate(orderPayment.getDateofPayment());
						order.getOrderTimeline().add(timeline);

					} else {
						order.setStatus("Payment Deducted");
						OrderTimeline timeline = new OrderTimeline();
						timeline.setEvent("Rs "
								+ orderPayment.getNegativeAmount()
								+ " Deducted");
						timeline.setEventDate(orderPayment.getDateofPayment());
						order.getOrderTimeline().add(timeline);
						order.getOrderPayment().setNegativeAmount(
								order.getOrderPayment().getNegativeAmount()
										+ orderPayment.getNegativeAmount());
						order.getOrderPayment().setNetPaymentResult(
								order.getOrderPayment().getNetPaymentResult()
										- orderPayment.getNegativeAmount());
						if (order.getReturnLimitCrossed().compareTo(
								order.getOrderReturnOrRTO().getReturnDate()) < 0) {

							order.setStatus("Return Limit Crossed");
							OrderTimeline timeline1 = new OrderTimeline();
							timeline1.setEvent("Return Limit Crossed");
							timeline1.setEventDate(new Date());
							order.getOrderTimeline().add(timeline1);
						}

						order.getOrderPayment()
								.setPaymentDifference(
										order.getOrderPayment()
												.getNetPaymentResult()
												+ order.getOrderReturnOrRTO()
														.getReturnOrRTOChargestoBeDeducted()
												- order.getTotalAmountRecieved());
					}
				} else {

					if (orderPayment.getPositiveAmount() > 0) {

						order.getOrderPayment().setPositiveAmount(
								order.getOrderPayment().getPositiveAmount()
										+ orderPayment.getPositiveAmount());
						order.getOrderPayment().setNetPaymentResult(
								order.getOrderPayment().getNetPaymentResult()
										+ orderPayment.getPositiveAmount());
						order.getOrderPayment()
								.setPaymentDifference(
										order.getOrderPayment()
												.getNetPaymentResult()
												+ order.getOrderReturnOrRTO()
														.getReturnOrRTOChargestoBeDeducted()
												- order.getTotalAmountRecieved());
						log.debug("payment difference :"
								+ order.getOrderPayment()
										.getPaymentDifference());

						order.setStatus("Payment Recieved");
						OrderTimeline timeline = new OrderTimeline();
						timeline.setEvent("Rs "
								+ orderPayment.getPositiveAmount()
								+ " Recieved");
						timeline.setEventDate(orderPayment.getDateofPayment());
						order.getOrderTimeline().add(timeline);

					} else {
						order.setStatus("Return Not Recieved");
						OrderTimeline timeline = new OrderTimeline();
						timeline.setEvent("Rs "
								+ orderPayment.getNegativeAmount()
								+ " Deducted");
						timeline.setEventDate(orderPayment.getDateofPayment());
						order.getOrderTimeline().add(timeline);
						if (order.getReturnLimitCrossed().compareTo(new Date()) < 0) {
							order.setStatus("Return Limit Crossed");
							order.getOrderReturnOrRTO()
									.setReturnOrRTOChargestoBeDeducted(0);
							OrderTimeline timeline1 = new OrderTimeline();
							timeline1.setEvent("Return Limit Crossed");
							timeline1.setEventDate(new Date());
							order.getOrderTimeline().add(timeline1);
						}
						order.getOrderPayment().setNegativeAmount(
								order.getOrderPayment().getNegativeAmount()
										+ orderPayment.getNegativeAmount());
						order.getOrderPayment().setNetPaymentResult(
								order.getOrderPayment().getNetPaymentResult()
										- orderPayment.getNegativeAmount());

						order.getOrderPayment()
								.setPaymentDifference(
										order.getOrderPayment()
												.getNetPaymentResult()
												+ order.getOrderReturnOrRTO()
														.getReturnOrRTOChargestoBeDeducted()
												- order.getTotalAmountRecieved());

					}
				}
				order.getOrderPayment().setDateofPayment(
						orderPayment.getDateofPayment());
				order.getOrderPayment().setPaymentCycle(
						orderPayment.getPaymentCycle());
				order.getOrderPayment().setUploadDate(new Date());
				if (order.getOrderPayment().getPaymentDifference() < 1
						&& order.getOrderPayment().getPaymentDifference() > -1) {
					order.setFinalStatus("Settled");
				} else {

					order.setFinalStatus("Actionable");
				}
				System.out.println("order in add payment :   **" + order);

				order.setLastActivityOnOrder(new Date());
				session.saveOrUpdate(order);
				session.getTransaction().commit();
				session.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.addOrderPaymentError,
					new Date(), 1, GlobalConstant.addOrderPaymentErrorCode, e);

		}
		log.info("*** addOrderPayment with skuCode ends : OrderDaoImpl ***");
		return order;
	}

	// MEthod to add debit notes for PO companies

	@Override
	public void addDebitNote(DebitNoteBean dnBean, int sellerId)
			throws CustomException {

		log.info("*** addDebitNote starts : OrderDaoImpl ***");
		log.debug("######## debit note for invoice  id :"
				+ dnBean.getInvoiceId() + " gp id : " + dnBean.getGatePassId()
				+ " po id+ " + dnBean.getPOOrderId());
		Order order = null;

		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();

			Criteria criteria = session.createCriteria(Order.class);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.eq("channelOrderID",
							dnBean.getPOOrderId()))
					.add(Restrictions.eq("invoiceID", dnBean.getInvoiceId()))
					.add(Restrictions.eq("pcName", dnBean.getPcName()))
					.add(Restrictions.eq("productSkuCode", dnBean.getSKU()));

			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			if (criteria.list() != null && criteria.list().size() != 0) {
				order = (Order) criteria.list().get(0);
			} else {
				log.debug("Order is Null....");
			}
			log.debug(" PO price : "
					+ (order.getPoPrice() / order.getQuantity())
					+ "  --- amount  : "
					+ (dnBean.getAmount() / dnBean.getQuantity()));
			if ((int) (order.getPoPrice() / order.getQuantity()) != (int) (dnBean
					.getAmount() / dnBean.getQuantity())) {
				order.getOrderPayment().setPaymentDifference(
						(order.getPoPrice() / order.getQuantity())
								- (dnBean.getAmount() / dnBean.getQuantity()));
			}

			order.getOrderReturnOrRTO().setReturnDate(dnBean.getReturnDate());
			order.getOrderReturnOrRTO().setReturnOrRTOreason(
					dnBean.getReturnReason());
			order.getOrderReturnOrRTO().setReturnorrtoQty(dnBean.getQuantity());
			order.getOrderReturnOrRTO().setReturnOrRTOChargestoBeDeducted(
					dnBean.getAmount());
			order.setSealNo(dnBean.getGatePassId());
			order.setStatus("Return Recived");

			if (order.getOrderPayment().getPaymentDifference() < 1
					&& order.getOrderPayment().getPaymentDifference() > -1) {
				order.setFinalStatus("Settled");
			} else {
				order.setFinalStatus("Actionable");
			}

			session.saveOrUpdate(order);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.addDebitNoteError,
					new Date(), 1, GlobalConstant.addDebitNoteErrorCode, e);
		}
		log.info("*** addDebitNote ends : OrderDaoImpl ***");
	}

	// Method to add PO payment
	@Override
	public Order addPOPayment(PoPaymentBean popaBean, int sellerId)
			throws CustomException {

		log.info("*** addPOPayment starts : OrderDaoImpl ***");
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();

			String orderID = null;
			boolean isGP = false;
			boolean disputedGP = false;
			boolean paymentOK = false;
			double paymentDiff = 0;

			if (popaBean.getPoOrderId() != null) {
				orderID = popaBean.getPoOrderId();
			} else {
				orderID = popaBean.getGatePassId();
				isGP = true;
			}

			Order poOrder = findConsolidatedPO("channelOrderID", orderID,
					sellerId);
			if (poOrder == null) {
				poOrder = new Order();
				poOrder.setChannelOrderID(orderID);
				poOrder.setPcName(popaBean.getPcName());
				poOrder.setSellerNote(popaBean.getSellerNote());
				poOrder.setPoOrder(true);
				poOrder.setSeller(sellerDao.getSeller(sellerId));
				disputedGP = true;
			}

			OrderPayment orderPayment = new OrderPayment();
			orderPayment.setUploadDate(new Date());
			orderPayment.setDateofPayment(popaBean.getPaymentDate());

			if (poOrder.getOrderPayment() != null) {
				orderPayment.setPositiveAmount(poOrder.getOrderPayment()
						.getPositiveAmount() + popaBean.getPositiveAmount());
				orderPayment.setNegativeAmount(poOrder.getOrderPayment()
						.getNegativeAmount() + popaBean.getNegativeAmount());
			} else {
				orderPayment.setPositiveAmount(popaBean.getPositiveAmount());
				orderPayment.setNegativeAmount(popaBean.getNegativeAmount());
			}

			orderPayment.setNetPaymentResult(orderPayment.getPositiveAmount()
					- orderPayment.getNegativeAmount());

			if (!isGP) {
				if (orderPayment.getNetPaymentResult() == poOrder.getPoPrice()) {
					paymentOK = true;
				} else {
					paymentDiff = orderPayment.getNetPaymentResult()
							- poOrder.getPoPrice();

				}
			} else {
				if (!disputedGP) {
					if (-orderPayment.getNetPaymentResult() == poOrder
							.getOrderReturnOrRTO()
							.getReturnOrRTOChargestoBeDeducted()) {
						paymentOK = true;
					} else {
						paymentDiff = poOrder.getOrderReturnOrRTO()
								.getReturnOrRTOChargestoBeDeducted()
								+ orderPayment.getNetPaymentResult();
					}
				}
			}

			paymentDiff = Math.round(paymentDiff * 100.0) / 100.0;

			orderPayment.setPaymentDifference(paymentDiff);
			// orderPayment.setNetPaymentResult(popaBean.getNpr());
			orderPayment.setPaymentdesc(popaBean.getSellerNote());

			if (disputedGP) {
				poOrder.setFinalStatus("Disputed");
				poOrder.setStatus("PO payment");
			} else {
				if (paymentOK) {
					poOrder.setFinalStatus("Settled");
					poOrder.setStatus("PO payment");
				} else {
					poOrder.setFinalStatus("Actionable");
					poOrder.setStatus("Inappropriate Payment Recieved");
				}
			}

			OrderTimeline timeline = new OrderTimeline();
			timeline.setEvent("Payment Recieved");
			timeline.setEventDate(orderPayment.getDateofPayment());
			poOrder.getOrderTimeline().add(timeline);

			poOrder.setOrderPayment(orderPayment);
			session.saveOrUpdate(poOrder);

			session.getTransaction().commit();
			session.close();

			log.info("*** addPOPayment ends : OrderDaoImpl ***");
			return poOrder;

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.addPOPaymentError,
					new Date(), 1, GlobalConstant.addPOPaymentErrorCode, e);
		}

	}

	@SuppressWarnings("deprecation")
	private Date getreconciledate(Order order, Partner partner, Date orderdate) {

		log.info("*** getreconciledate starts : OrderDaoImpl ***");
		log.debug(" Delivery date :" + order.getDeliveryDate());
		Date reconciledate = new Date();
		int startdate;
		int paydate;
		int currentdate;
		boolean payfromshippingdate;
		String paymentType;
		boolean isIsshippeddatecalc;
		int monthlypaydate;
		int noofdaysfromshippeddate;
		Date deliverydate;
		Date shippeddate;
		int orderdeliverydate;
		int ordershippeddate;
		int fsd = 0;
		int loop = 0;
		int tempsd = 0;
		int temped = 0;
		int enddate;
		Date paymentCycleStartDate = null;
		Date paymentCycleEndDate = null;

		try {
			startdate = partner.getStartcycleday();
			paydate = partner.getPaydaysfromstartday();
			currentdate = reconciledate.getDate();
			payfromshippingdate = partner.isPaycyclefromshipordel();
			paymentType = partner.getPaymentType();
			isIsshippeddatecalc = partner.isIsshippeddatecalc();
			monthlypaydate = partner.getMonthlypaydate();
			noofdaysfromshippeddate = partner.getNoofdaysfromshippeddate();
			deliverydate = new Date(dateFormat.format(order.getDeliveryDate()));
			shippeddate = new Date(dateFormat.format(order.getShippedDate()));
			orderdeliverydate = deliverydate.getDate();
			ordershippeddate = shippeddate.getDate();
			enddate = partner.getPaycycleduration();

			log.debug(" ORder delivery date in rec 2 : "
					+ order.getDeliveryDate());
					if (paymentType.equals("paymentcycle")) {
				if (payfromshippingdate)
					currentdate = ordershippeddate;
				else
					currentdate = orderdeliverydate;

				while (true) {
					loop++;
					log.debug("Loop :" + loop + " currentdate :" + currentdate
							+ ">>startdate : " + startdate + ">>enddate :"
							+ enddate);
					if (currentdate > startdate || currentdate == startdate) {
						if (currentdate < enddate || currentdate == enddate
								|| currentdate == startdate) {
							fsd = paydate;
							break;
						} else {
							tempsd = startdate;
							temped = enddate;
							startdate = enddate + 1;
							enddate = startdate + (enddate - tempsd);
							paydate = enddate + (paydate - temped);
							continue;
						}
					} else {
						temped = enddate;
						tempsd = startdate;
						enddate = startdate - 1;
						startdate = enddate - (temped - tempsd);
						paydate = enddate + (paydate - temped);
						continue;

					}
				}
				if (!payfromshippingdate) {
					reconciledate = (Date) deliverydate.clone();
					paymentCycleStartDate = (Date) deliverydate.clone();
					paymentCycleEndDate = (Date) deliverydate.clone();
				} else {
					reconciledate = (Date) shippeddate.clone();
					paymentCycleStartDate = (Date) shippeddate.clone();
					paymentCycleEndDate = (Date) shippeddate.clone();
				}
				reconciledate.setDate(paydate);
				paymentCycleStartDate.setDate(startdate);
				paymentCycleEndDate.setDate(enddate);
				log.debug("reconciledate in paycycle :" + reconciledate);
			} else if (paymentType.equals("datewisepay")) {
				log.debug(" Datewise payment isIsshippeddatecalc : "
						+ isIsshippeddatecalc + "  noofdaysfromshippeddate : "
						+ noofdaysfromshippeddate);
				log.debug(" Delivery date : " + deliverydate);
				if (isIsshippeddatecalc) {
					reconciledate = shippeddate;
					reconciledate.setDate(reconciledate.getDate()
							+ noofdaysfromshippeddate);
				} else {
					reconciledate = deliverydate;
					reconciledate.setDate(reconciledate.getDate()
							+ noofdaysfromshippeddate);
				}
				log.debug(" Reconcile date after datewisepayment : "
						+ reconciledate);
			} else {
				reconciledate = deliverydate;

				reconciledate.setMonth(reconciledate.getMonth() + 1);
				reconciledate.setDate(monthlypaydate);

			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed!", e);
		}
		log.debug(" ORder delivery date in rec : " + order.getDeliveryDate());
		log.info("*** getreconciledate ends : OrderDaoImpl ***");
		return reconciledate;
	}

	// Method to calculate NR
	private boolean calculateNR(Partner partner, Order order, String prodCat,
			float deadWeight, float volWeight) {

		log.info("*** calculateNR starts : OrderDaoImpl ***");
		log.debug("**Parameters recieved : pcNAme -> " + partner.getPcName()
				+ " channelorderid - > " + order.getChannelOrderID()
				+ " prodCat " + prodCat + "deadWeight " + deadWeight
				+ " volWeight " + volWeight);
		log.debug("**Parameters recieved : pcNAme -> " + partner.getPcName()
				+ " channelorderid - > " + order.getChannelOrderID()
				+ " prodCat " + prodCat + "deadWeight " + deadWeight
				+ " volWeight " + volWeight);
		double nrValue = 0;
		float comission = 0;
		float fixedfee = 0;
		double pccAmount = 0;
		float serviceTax = 0;
		double tds = 0;

		StringBuffer area = new StringBuffer("");
		StringBuffer volarea = new StringBuffer("");

		float vwchargetemp = 0;
		float dwchargetemp = 0;
		float shippingCharges = 0;
		String tempStr = null;
		String state = null;

		try {
			if (partner.getNrnReturnConfig() != null
					&& partner.getNrnReturnConfig().getMetroList() != null
					&& partner.getNrnReturnConfig().getMetroList().length() != 0) {
				state = areaConfigDao.getMetroFromZipCode(order.getCustomer()
						.getZipcode());
				log.debug(" City from zipcode : " + state);
			}
			if (state == null
					|| !(state.equalsIgnoreCase("Delhi")
							|| state.equalsIgnoreCase("Chennai")
							|| state.equalsIgnoreCase("Mumbai") || state
								.equalsIgnoreCase("Kolkata"))) {
				state = areaConfigDao.getStateFromZipCode(order.getCustomer()
						.getZipcode());
				log.debug(" State from zipcode : " + state);
			}
			double SP = order.getOrderSP();
			StringBuffer temp = new StringBuffer("");
			Map<String, Float> chargesMap = new HashMap<String, Float>();
			// Map<String, Float> returnMap = new HashMap<String, Float>();

			PartnerBean pbean = new PartnerBean();

			List<NRnReturnCharges> chargesList = partner.getNrnReturnConfig()
					.getCharges();

			for (NRnReturnCharges charge : chargesList) {

				if (charge.getChargeName().contains("fixedfee")
						&& charge.getCriteria() != null
						&& !"".equals(charge.getCriteria())) {

					ChargesBean chargeBean = new ChargesBean();
					chargeBean.setChargeType("fixedfee");
					chargeBean.setCriteria(charge.getCriteria());
					chargeBean.setRange(charge.getCriteriaRange());
					chargeBean.setValue(charge.getChargeAmount());
					pbean.getFixedfeeList().add(chargeBean);

				} else if (charge.getChargeName().contains("shippingfeeVolume")
						&& charge.getCriteria() != null
						&& !"".equals(charge.getCriteria())) {

					if (partner.getNrnReturnConfig().getShippingFeeType()
							.equalsIgnoreCase("variable")
							&& charge.getChargeName().contains(
									"shippingfeeVolumeVariable")) {

						ChargesBean chargeBean = pbean
								.getChargesBean("shippingfeeVolumeVariable",
										charge.getCriteria(),
										charge.getCriteriaRange());
						if (chargeBean == null) {
							chargeBean = new ChargesBean();
							chargeBean
									.setChargeType("shippingfeeVolumeVariable");
							chargeBean.setCriteria(charge.getCriteria());
							chargeBean.setRange(charge.getCriteriaRange());
							pbean.getshippingfeeVolumeVariableList().add(
									chargeBean);
						}

						if (charge.getChargeName().contains("Local")) {
							chargeBean.setLocalValue(charge.getChargeAmount());
						} else if (charge.getChargeName().contains("Zonal")) {
							chargeBean.setZonalValue(charge.getChargeAmount());
						} else if (charge.getChargeName().contains("National")) {
							chargeBean.setNationalValue(charge
									.getChargeAmount());
						} else if (charge.getChargeName().contains("Metro")) {
							chargeBean.setMetroValue(charge.getChargeAmount());
						}

					} else if (charge.getChargeName().contains(
							"shippingfeeVolumeFixed")) {
						ChargesBean chargeBean = new ChargesBean();
						chargeBean.setChargeType("shippingfeeVolumeFixed");
						chargeBean.setCriteria(charge.getCriteria());
						chargeBean.setRange(charge.getCriteriaRange());
						chargeBean.setValue(charge.getChargeAmount());
						pbean.getshippingfeeVolumeFixedList().add(chargeBean);
					}

				} else if (charge.getChargeName().contains("shippingfeeWeight")
						&& charge.getCriteria() != null
						&& !"".equals(charge.getCriteria())) {

					if (partner.getNrnReturnConfig().getShippingFeeType()
							.equalsIgnoreCase("variable")
							&& charge.getChargeName().contains(
									"shippingfeeWeightVariable")) {

						ChargesBean chargeBean = pbean
								.getChargesBean("shippingfeeWeightVariable",
										charge.getCriteria(),
										charge.getCriteriaRange());
						if (chargeBean == null) {
							chargeBean = new ChargesBean();
							chargeBean
									.setChargeType("shippingfeeWeightVariable");
							chargeBean.setCriteria(charge.getCriteria());
							chargeBean.setRange(charge.getCriteriaRange());
							pbean.getshippingfeeWeightVariableList().add(
									chargeBean);
						}

						if (charge.getChargeName().contains("Local")) {
							chargeBean.setLocalValue(charge.getChargeAmount());
						} else if (charge.getChargeName().contains("Zonal")) {
							chargeBean.setZonalValue(charge.getChargeAmount());
						} else if (charge.getChargeName().contains("National")) {
							chargeBean.setNationalValue(charge
									.getChargeAmount());
						} else if (charge.getChargeName().contains("Metro")) {
							chargeBean.setMetroValue(charge.getChargeAmount());
						}

					} else if (charge.getChargeName().contains(
							"shippingfeeWeightFixed")) {
						ChargesBean chargeBean = new ChargesBean();
						chargeBean.setChargeType("shippingfeeWeightFixed");
						chargeBean.setCriteria(charge.getCriteria());
						chargeBean.setRange(charge.getCriteriaRange());
						chargeBean.setValue(charge.getChargeAmount());
						pbean.getshippingfeeWeightFixedList().add(chargeBean);
					}

				} else {
					chargesMap.put(charge.getChargeName(),
							charge.getChargeAmount());
				}
			}
			if (pbean.getFixedfeeList() != null
					&& pbean.getFixedfeeList().size() != 0)
				Collections.sort(pbean.getFixedfeeList(),
						new SortByCriteriaRange());
			if (pbean.getshippingfeeVolumeFixedList() != null
					&& pbean.getshippingfeeVolumeFixedList().size() != 0)
				Collections.sort(pbean.getshippingfeeVolumeFixedList(),
						new SortByCriteria());
			if (pbean.getshippingfeeWeightFixedList() != null
					&& pbean.getshippingfeeWeightFixedList().size() != 0)
				Collections.sort(pbean.getshippingfeeWeightFixedList(),
						new SortByCriteria());
			if (pbean.getshippingfeeVolumeVariableList() != null
					&& pbean.getshippingfeeVolumeVariableList().size() != 0)
				Collections.sort(pbean.getshippingfeeVolumeVariableList(),
						new SortByCriteria());
			if (pbean.getshippingfeeWeightVariableList() != null
					&& pbean.getshippingfeeWeightVariableList().size() != 0)
				Collections.sort(pbean.getshippingfeeWeightVariableList(),
						new SortByCriteria());

			// Extracting comiision value
			if (partner.getNrnReturnConfig().getCommissionType() != null
					&& partner.getNrnReturnConfig().getCommissionType()
							.equals("fixed")) {
				comission = chargesMap
						.get(GlobalConstant.fixedCommissionPercent);

			} else {
				comission = chargesMap.containsKey(prodCat) ? chargesMap
						.get(prodCat) : 0;
			}

			// Add partner new changes:

			// Getting Fixed fee
			if (pbean.getFixedfeeList() != null
					&& pbean.getFixedfeeList().size() != 0) {
				boolean inRange = false;
				Iterator<ChargesBean> fixedfeeIterator = pbean
						.getFixedfeeList().iterator();
				while (fixedfeeIterator.hasNext()) {
					ChargesBean cBean = fixedfeeIterator.next();
			if (SP <= cBean.getRange()) {
						fixedfee = (float) cBean.getValue();
						inRange = true;
						break;
					}
				}
				if (!inRange) {
					fixedfee = (float) pbean.getFixedfeeList()
							.get(pbean.getFixedfeeList().size() - 1).getValue();
				}
			}

			// Payment collection charges
			if (partner.getNrnReturnConfig().isWhicheverGreaterPCC()) {
				double percentAmount = chargesMap
						.containsKey(GlobalConstant.percentSPPCCHigher) ? chargesMap
						.get(GlobalConstant.percentSPPCCHigher) * SP / 100
						: 0;
				if (chargesMap.containsKey(GlobalConstant.fixedAmtPCC)
						&& percentAmount > chargesMap
								.get(GlobalConstant.fixedAmtPCC)) {
					pccAmount = percentAmount;
				} else
					pccAmount = chargesMap
							.containsKey(GlobalConstant.fixedAmtPCC) ? chargesMap
							.get(GlobalConstant.fixedAmtPCC) : 0;

			} else {
				double pccRange = chargesMap
						.containsKey(GlobalConstant.rangePCC) ? chargesMap
						.get(GlobalConstant.rangePCC) : 0;

				if (pccRange == 0) {
					pccAmount = 0;
				} else {
					if (SP < pccRange) {
						pccAmount = chargesMap
								.containsKey(GlobalConstant.valuePCC) ? chargesMap
								.get(GlobalConstant.valuePCC) : 0;
					} else {
						pccAmount = chargesMap
								.containsKey(GlobalConstant.percentSPPCCValue) ? chargesMap
								.get(GlobalConstant.percentSPPCCValue)
								* SP
								/ 100 : 0;
					}
				}
			}

			log.debug(" States : MetroLsit : "
					+ partner.getNrnReturnConfig().getMetroList()
					+ " national list : "
					+ partner.getNrnReturnConfig().getNationalList()
					+ " LocalList : "
					+ partner.getNrnReturnConfig().getLocalList()
					+ " zonallist: "
					+ partner.getNrnReturnConfig().getZonalList());
			log.debug(" State we are geting ofrom excel : " + state);

			// ****Shipping charges
			String valueType = "";
			if (partner.getNrnReturnConfig().getShippingFeeType() != null
					&& partner.getNrnReturnConfig().getShippingFeeType()
							.equals("variable")) {
				if (partner.getNrnReturnConfig().getMetroList() != null
						&& partner.getNrnReturnConfig().getMetroList()
								.contains(state)) {

					valueType = "metro";
				} else if (partner.getNrnReturnConfig().getNationalList() != null
						&& partner.getNrnReturnConfig().getNationalList()
								.contains(state)) {
					valueType = "national";
				} else if (partner.getNrnReturnConfig().getLocalList() != null
						&& partner.getNrnReturnConfig().getLocalList()
								.contains(state)) {
					valueType = "local";
				} else if (partner.getNrnReturnConfig().getZonalList() != null
						&& partner.getNrnReturnConfig().getZonalList()
								.contains(state)) {
					valueType = "zonal";
				}
			} else {
				valueType = "fixed";
			}
			order.setVolShippingString(valueType);
			if (partner.getNrnReturnConfig().getShippingFeeType() != null
					&& partner.getNrnReturnConfig().getShippingFeeType()
							.equals("variable")) {

				if (pbean.getshippingfeeWeightVariableList() != null
						&& pbean.getshippingfeeWeightVariableList().size() != 0
						&& deadWeight >= volWeight) {
					boolean inRange = false;
					Iterator<ChargesBean> shippingfeeWeightIterator = pbean
							.getshippingfeeWeightVariableList().iterator();
					while (shippingfeeWeightIterator.hasNext()) {
						ChargesBean cBean = shippingfeeWeightIterator.next();
						if (deadWeight <= cBean.getRange()) {
							if (valueType.equalsIgnoreCase("metro"))
								dwchargetemp = (float) cBean.getMetroValue();
							else if (valueType.equalsIgnoreCase("national"))
								dwchargetemp = (float) cBean.getNationalValue();
							else if (valueType.equalsIgnoreCase("local"))
								dwchargetemp = (float) cBean.getLocalValue();
							else if (valueType.equalsIgnoreCase("zonal"))
								dwchargetemp = (float) cBean.getZonalValue();
							inRange = true;
							break;
						}
					}
					if (!inRange) {
						float tempWeight = deadWeight
								- (float) pbean
										.getshippingfeeWeightVariableList()
										.get(pbean
												.getshippingfeeWeightVariableList()
												.size() - 2).getRange();
						float addWeight = (float) pbean
								.getshippingfeeWeightVariableList()
								.get(pbean.getshippingfeeWeightVariableList()
										.size() - 1).getRange();

						if (valueType.equalsIgnoreCase("metro"))
							dwchargetemp = (float) pbean
									.getshippingfeeWeightVariableList()
									.get(pbean
											.getshippingfeeWeightVariableList()
											.size() - 2).getMetroValue();
						else if (valueType.equalsIgnoreCase("national"))
							dwchargetemp = (float) pbean
									.getshippingfeeWeightVariableList()
									.get(pbean
											.getshippingfeeWeightVariableList()
											.size() - 2).getNationalValue();
						else if (valueType.equalsIgnoreCase("local"))
							dwchargetemp = (float) pbean
									.getshippingfeeWeightVariableList()
									.get(pbean
											.getshippingfeeWeightVariableList()
											.size() - 2).getLocalValue();
						else if (valueType.equalsIgnoreCase("zonal"))
							dwchargetemp = (float) pbean
									.getshippingfeeWeightVariableList()
									.get(pbean
											.getshippingfeeWeightVariableList()
											.size() - 2).getZonalValue();

						while (tempWeight > 0) {
							if (valueType.equalsIgnoreCase("metro"))
								dwchargetemp += (float) pbean
										.getshippingfeeWeightVariableList()
										.get(pbean
												.getshippingfeeWeightVariableList()
												.size() - 1).getMetroValue();
							else if (valueType.equalsIgnoreCase("national"))
								dwchargetemp += (float) pbean
										.getshippingfeeWeightVariableList()
										.get(pbean
												.getshippingfeeWeightVariableList()
												.size() - 1).getNationalValue();
							else if (valueType.equalsIgnoreCase("local"))
								dwchargetemp += (float) pbean
										.getshippingfeeWeightVariableList()
										.get(pbean
												.getshippingfeeWeightVariableList()
												.size() - 1).getLocalValue();
							else if (valueType.equalsIgnoreCase("zonal"))
								dwchargetemp += (float) pbean
										.getshippingfeeWeightVariableList()
										.get(pbean
												.getshippingfeeWeightVariableList()
												.size() - 1).getZonalValue();

							tempWeight = tempWeight - addWeight;
						}
					}
				}

				if (pbean.getshippingfeeVolumeVariableList() != null
						&& pbean.getshippingfeeVolumeVariableList().size() != 0
						&& volWeight > deadWeight) {
					boolean inRange = false;
					Iterator<ChargesBean> shippingfeeVolumeIterator = pbean
							.getshippingfeeVolumeVariableList().iterator();
					while (shippingfeeVolumeIterator.hasNext()) {
						ChargesBean cBean = shippingfeeVolumeIterator.next();
						if (volWeight <= cBean.getRange()) {
							if (valueType.equalsIgnoreCase("metro"))
								vwchargetemp = (float) cBean.getMetroValue();
							else if (valueType.equalsIgnoreCase("national"))
								vwchargetemp = (float) cBean.getNationalValue();
							else if (valueType.equalsIgnoreCase("local"))
								vwchargetemp = (float) cBean.getLocalValue();
							else if (valueType.equalsIgnoreCase("zonal"))
								vwchargetemp = (float) cBean.getZonalValue();
							inRange = true;
							break;
						}
					}
					if (!inRange) {
						float tempWeight = volWeight
								- (float) pbean
										.getshippingfeeVolumeVariableList()
										.get(pbean
												.getshippingfeeVolumeVariableList()
												.size() - 2).getRange();
						float addWeight = (float) pbean
								.getshippingfeeVolumeVariableList()
								.get(pbean.getshippingfeeVolumeVariableList()
										.size() - 1).getRange();

						if (valueType.equalsIgnoreCase("metro"))
							vwchargetemp = (float) pbean
									.getshippingfeeVolumeVariableList()
									.get(pbean
											.getshippingfeeVolumeVariableList()
											.size() - 2).getMetroValue();
						else if (valueType.equalsIgnoreCase("national"))
							vwchargetemp = (float) pbean
									.getshippingfeeVolumeVariableList()
									.get(pbean
											.getshippingfeeVolumeVariableList()
											.size() - 2).getNationalValue();
						else if (valueType.equalsIgnoreCase("local"))
							vwchargetemp = (float) pbean
									.getshippingfeeVolumeVariableList()
									.get(pbean
											.getshippingfeeVolumeVariableList()
											.size() - 2).getLocalValue();
						else if (valueType.equalsIgnoreCase("zonal"))
							vwchargetemp = (float) pbean
									.getshippingfeeVolumeVariableList()
									.get(pbean
											.getshippingfeeVolumeVariableList()
											.size() - 2).getZonalValue();

						while (tempWeight > 0) {
							if (valueType.equalsIgnoreCase("metro"))
								vwchargetemp += (float) pbean
										.getshippingfeeVolumeVariableList()
										.get(pbean
												.getshippingfeeVolumeVariableList()
												.size() - 1).getMetroValue();
							else if (valueType.equalsIgnoreCase("national"))
								vwchargetemp += (float) pbean
										.getshippingfeeVolumeVariableList()
										.get(pbean
												.getshippingfeeVolumeVariableList()
												.size() - 1).getNationalValue();
							else if (valueType.equalsIgnoreCase("local"))
								vwchargetemp += (float) pbean
										.getshippingfeeVolumeVariableList()
										.get(pbean
												.getshippingfeeVolumeVariableList()
												.size() - 1).getLocalValue();
							else if (valueType.equalsIgnoreCase("zonal"))
								vwchargetemp += (float) pbean
										.getshippingfeeVolumeVariableList()
										.get(pbean
												.getshippingfeeVolumeVariableList()
												.size() - 1).getZonalValue();

							tempWeight = tempWeight - addWeight;
						}
					}
				}
			} else if (partner.getNrnReturnConfig().getShippingFeeType() != null
					&& partner.getNrnReturnConfig().getShippingFeeType()
							.equals("fixed")) {

				if (pbean.getshippingfeeWeightFixedList() != null
						&& pbean.getshippingfeeWeightFixedList().size() != 0
						&& deadWeight >= volWeight) {
					boolean inRange = false;
					Iterator<ChargesBean> shippingfeeWeightIterator = pbean
							.getshippingfeeWeightFixedList().iterator();
					while (shippingfeeWeightIterator.hasNext()) {
						ChargesBean cBean = shippingfeeWeightIterator.next();
						if (deadWeight <= cBean.getRange()) {
							if (valueType.equalsIgnoreCase("fixed"))
								dwchargetemp = (float) cBean.getValue();
							inRange = true;
							break;
						}
					}
					if (!inRange) {
						float tempWeight = deadWeight
								- (float) pbean
										.getshippingfeeWeightFixedList()
										.get(pbean
												.getshippingfeeWeightFixedList()
												.size() - 2).getRange();
						float addWeight = (float) pbean
								.getshippingfeeWeightFixedList()
								.get(pbean.getshippingfeeWeightFixedList()
										.size() - 1).getRange();

						if (valueType.equalsIgnoreCase("fixed"))
							dwchargetemp = (float) pbean
									.getshippingfeeWeightFixedList()
									.get(pbean.getshippingfeeWeightFixedList()
											.size() - 2).getValue();

						while (tempWeight > 0) {
							if (valueType.equalsIgnoreCase("fixed"))
								dwchargetemp += (float) pbean
										.getshippingfeeWeightFixedList()
										.get(pbean
												.getshippingfeeWeightFixedList()
												.size() - 1).getValue();

							tempWeight = tempWeight - addWeight;
						}
					}
				}

				if (pbean.getshippingfeeVolumeFixedList() != null
						&& pbean.getshippingfeeVolumeFixedList().size() != 0
						&& volWeight > deadWeight) {
					boolean inRange = false;
					Iterator<ChargesBean> shippingfeeVolumeIterator = pbean
							.getshippingfeeVolumeFixedList().iterator();
					while (shippingfeeVolumeIterator.hasNext()) {
						ChargesBean cBean = shippingfeeVolumeIterator.next();
						if (volWeight <= cBean.getRange()) {
							if (valueType.equalsIgnoreCase("fixed"))
								vwchargetemp = (float) cBean.getValue();

							inRange = true;
							break;
						}
					}
					if (!inRange) {
						float tempWeight = volWeight
								- (float) pbean
										.getshippingfeeVolumeFixedList()
										.get(pbean
												.getshippingfeeVolumeFixedList()
												.size() - 2).getRange();
						float addWeight = (float) pbean
								.getshippingfeeVolumeFixedList()
								.get(pbean.getshippingfeeVolumeFixedList()
										.size() - 1).getRange();

						if (valueType.equalsIgnoreCase("fixed"))
							vwchargetemp = (float) pbean
									.getshippingfeeVolumeFixedList()
									.get(pbean.getshippingfeeVolumeFixedList()
											.size() - 2).getValue();

						while (tempWeight > 0) {
							if (valueType.equalsIgnoreCase("fixed"))
								vwchargetemp += (float) pbean
										.getshippingfeeVolumeFixedList()
										.get(pbean
												.getshippingfeeVolumeFixedList()
												.size() - 1).getValue();

							tempWeight = tempWeight - addWeight;
						}
					}
				}
			}

			if (vwchargetemp > dwchargetemp) {
				shippingCharges = vwchargetemp;
				order.setDwShippingString(GlobalConstant.volShipping);
			} else {
				shippingCharges = dwchargetemp;
				order.setDwShippingString(GlobalConstant.dwShipping);
			}
			// End

			/*
			 * // Getting Fixed fee if
			 * (chargesMap.containsKey(GlobalConstant.fixedfeelt250) &&
			 * chargesMap.get(GlobalConstant.fixedfeelt250).intValue() != 0) {
			 * if (SP < 251) fixedfee =
			 * chargesMap.get(GlobalConstant.fixedfeelt250); else if (SP > 250
			 * && SP < 501) fixedfee = chargesMap
			 * .containsKey(GlobalConstant.fixedfeegt250lt500) ? chargesMap
			 * .get(GlobalConstant.fixedfeegt250lt500) : 0; else fixedfee =
			 * chargesMap .containsKey(GlobalConstant.fixedfeegt500) ?
			 * chargesMap .get(GlobalConstant.fixedfeegt500) : 0; } else if
			 * (chargesMap.containsKey(GlobalConstant.fixedfeelt500) &&
			 * chargesMap.get(GlobalConstant.fixedfeelt500).intValue() != 0) {
			 * if (SP < 501) fixedfee =
			 * chargesMap.get(GlobalConstant.fixedfeelt500); else fixedfee =
			 * chargesMap .containsKey(GlobalConstant.fixedfeegt500Big) ?
			 * chargesMap .get(GlobalConstant.fixedfeegt500Big) : 0; } else { if
			 * (SP < 501) fixedfee = chargesMap
			 * .containsKey(GlobalConstant.fixedfeelt500Big) ? chargesMap
			 * .get(GlobalConstant.fixedfeelt500Big) : 0; else if (SP > 500 &&
			 * SP < 1001) fixedfee = chargesMap
			 * .containsKey(GlobalConstant.fixedfeegt500lt1000) ? chargesMap
			 * .get(GlobalConstant.fixedfeegt500lt1000) : 0; else if (SP > 1000
			 * && SP < 10001) fixedfee = chargesMap
			 * .containsKey(GlobalConstant.fixedfeegt1000lt10000) ? chargesMap
			 * .get(GlobalConstant.fixedfeegt1000lt10000) : 0; else fixedfee =
			 * chargesMap .containsKey(GlobalConstant.fixedfeegt10000) ?
			 * chargesMap .get(GlobalConstant.fixedfeegt10000) : 0;
			 * 
			 * }
			 * 
			 * // Payment collection charges if
			 * (partner.getNrnReturnConfig().isWhicheverGreaterPCC()) { double
			 * percentAmount = chargesMap
			 * .containsKey(GlobalConstant.percentSPPCC) ? chargesMap
			 * .get(GlobalConstant.percentSPPCC) * SP / 100 : 0; if
			 * (chargesMap.containsKey(GlobalConstant.fixedAmtPCC) &&
			 * percentAmount > chargesMap .get(GlobalConstant.fixedAmtPCC)) {
			 * pccAmount = percentAmount; } else pccAmount = chargesMap
			 * .containsKey(GlobalConstant.fixedAmtPCC) ? chargesMap
			 * .get(GlobalConstant.fixedAmtPCC) : 0;
			 * 
			 * } else if (chargesMap.containsKey(GlobalConstant.fixedAmtPCC) &&
			 * chargesMap.get(GlobalConstant.fixedAmtPCC) != 0.0) pccAmount =
			 * chargesMap.get(GlobalConstant.fixedAmtPCC);
			 * 
			 * else pccAmount =
			 * chargesMap.containsKey(GlobalConstant.percentSPPCC) ? chargesMap
			 * .get(GlobalConstant.percentSPPCC) * SP / 100 : 0;
			 * 
			 * log.debug(" States : MetroLsit : " +
			 * partner.getNrnReturnConfig().getMetroList() + " national list : "
			 * + partner.getNrnReturnConfig().getNationalList() +
			 * " LocalList : " + partner.getNrnReturnConfig().getLocalList() +
			 * " zonallist: " + partner.getNrnReturnConfig().getZonalList());
			 * log.debug(" State we are geting ofrom excel : " + state);
			 * 
			 * // ****Shipping charges if
			 * (partner.getNrnReturnConfig().getShippingFeeType() != null &&
			 * partner.getNrnReturnConfig().getShippingFeeType()
			 * .equals("variable")) { if
			 * (partner.getNrnReturnConfig().getMetroList() != null &&
			 * partner.getNrnReturnConfig().getMetroList() .contains(state)) {
			 * 
			 * area.append("metro"); volarea.append("metro"); } else if
			 * (partner.getNrnReturnConfig().getNationalList() != null &&
			 * partner.getNrnReturnConfig().getNationalList() .contains(state))
			 * { area.append("national"); volarea.append("national"); } else if
			 * (partner.getNrnReturnConfig().getLocalList() != null &&
			 * partner.getNrnReturnConfig().getLocalList() .contains(state)) {
			 * area.append("local"); volarea.append("local"); } else if
			 * (partner.getNrnReturnConfig().getZonalList() != null &&
			 * partner.getNrnReturnConfig().getZonalList() .contains(state)) {
			 * area.append("zonal"); volarea.append("zonal"); } } else {
			 * area.append("fixed"); volarea.append("fixed"); } if (deadWeight <
			 * 501) { area.append("dwlt500");
			 * order.setDwShippingString(area.toString()); dwchargetemp =
			 * chargesMap.containsKey(area.toString()) ? chargesMap
			 * .get(area.toString()) : 0;
			 * 
			 * } else { temp = new StringBuffer(area); area.append("dwlt500");
			 * temp.append("dwgt500"); log.debug(" Area : " + area + " temp : "
			 * + temp); dwchargetemp = chargesMap.containsKey(area.toString()) ?
			 * chargesMap .get(area.toString()) : 0;
			 * log.debug(" Charges for lesstthan 500 : " + dwchargetemp); float
			 * range = (float) Math.ceil((deadWeight - 500) / 500);
			 * log.debug(" Range : " + range); dwchargetemp = dwchargetemp +
			 * (range * (chargesMap.containsKey(temp.toString()) ? chargesMap
			 * .get(temp.toString()) : 0));
			 * log.debug(" Charges for greater than 500 : " +
			 * chargesMap.get(temp.toString()));
			 * order.setDwShippingString(temp.toString());
			 * 
			 * }
			 * 
			 * log.debug("volarea  " + volarea);
			 * 
			 * if (volWeight < 501) { tempStr =
			 * volarea.append("vwlt500").toString(); log.debug(" tempStr " +
			 * tempStr);
			 * 
			 * vwchargetemp = chargesMap.containsKey(tempStr) ? chargesMap
			 * .get(tempStr) : 0; order.setVolShippingString(tempStr); } else if
			 * (volWeight > 500 && volWeight < 1001) { tempStr =
			 * volarea.append("vwgt500lt1000").toString(); vwchargetemp =
			 * chargesMap.containsKey(tempStr) ? chargesMap .get(tempStr) : 0;
			 * order.setVolShippingString(volarea.toString()); } else if
			 * (volWeight > 1000 && volWeight < 1501) { tempStr =
			 * volarea.append("vwgt1000lt1500").toString(); vwchargetemp =
			 * chargesMap.containsKey(tempStr) ? chargesMap .get(tempStr) : 0;
			 * order.setVolShippingString(volarea.toString()); } else if
			 * (volWeight > 1500 && volWeight < 5001) { tempStr =
			 * volarea.append("vwgt1500lt5000").toString();
			 * log.debug(" tempStr " + tempStr); vwchargetemp =
			 * chargesMap.containsKey(tempStr) ? chargesMap .get(tempStr) : 0;
			 * order.setVolShippingString(volarea.toString()); } else if
			 * (volWeight > 5000) { temp = new StringBuffer(volarea);
			 * volarea.append("vwgt1500lt5000");
			 * 
			 * vwchargetemp = chargesMap.containsKey(volarea.toString()) ?
			 * chargesMap .get(volarea.toString()) : 0;
			 * log.debug(" vol Charges for lesstthan 500 : " + vwchargetemp);
			 * temp.append("vwgt5000"); float range = (float)
			 * Math.ceil((volWeight - 5000) / 1000); log.debug("volarea  " +
			 * volarea + " temp : " + temp + " range invol: " + range);
			 * vwchargetemp = vwchargetemp + (range *
			 * (chargesMap.containsKey(temp.toString()) ? chargesMap
			 * .get(temp.toString()) : 0));
			 * order.setVolShippingString(temp.toString());
			 * 
			 * } log.debug(" vwchargetemp : " + vwchargetemp +
			 * " dwchargetemp : " + dwchargetemp); if (vwchargetemp >
			 * dwchargetemp) shippingCharges = vwchargetemp; else
			 * shippingCharges = dwchargetemp;
			 */

			comission = (float) (comission * SP) / 100;
			serviceTax = (chargesMap.containsKey("serviceTax") ? chargesMap
					.get("serviceTax") : 0)
					* (float) (shippingCharges + pccAmount + fixedfee + comission)
					/ 100;
			nrValue = SP - comission - fixedfee - pccAmount - shippingCharges
					- serviceTax;
			props = PropertiesLoaderUtils.loadProperties(resource);
			if (partner != null && partner.isTdsApplicable()) {
				tds = (((props.getProperty("TDS") != null ? Double
						.parseDouble(props.getProperty("TDS")) : 0)
						* comission
						/ 100 + ((fixedfee + pccAmount + shippingCharges) / 50)))
						* order.getQuantity();
				order.getOrderTax().setTdsToDeduct(tds);
			}

			order.setGrossNetRate(nrValue);
			order.setPartnerCommission(comission);
			order.setFixedfee(fixedfee);
			order.setServiceTax(serviceTax);
			order.setPccAmount(pccAmount);
			order.setShippingCharges(shippingCharges);
		} catch (Exception e) {
			log.error("Failed!", e);
			e.printStackTrace();
			return false;
		}

		log.debug(" Setting values for Nr in nrcalculator NR: "
				+ order.getNetRate() + ", commison : "
				+ order.getPartnerCommission() + " fixedfee : "
				+ order.getFixedfee() + " pcc : " + order.getPccAmount()
				+ " ->service tax : " + serviceTax + "shippingCharges : "
				+ order.getShippingCharges());
		log.info("*** calculateNR ends : OrderDaoImpl ***");
		return true;
	}

	/*
	 * Method to calculate return charges
	 */
	private float calculateReturnCharges(Order order,
			OrderRTOorReturn ordereturn, int sellerId) throws Exception {

		log.info("*** calculateReturnCharges starts : OrderDaoImpl ***");
		float totalcharge = 0;
		float revShippingFee = 0;
		String varPercentSP = null;
		String varPercentPCC = null;
		String varPercentFixAmt = null;
		String chargesType = null;
		String fixedAmount = null;
		boolean shippingfee = false;
		boolean servicetax = false;
		boolean fixedfee = false;
		boolean paycollcharges = false;
		boolean isRevShippingFee = false;

		try {
			Partner partner = partnerService.getPartner(order.getPcName(),
					sellerId);

			String returnType = ordereturn.getType();
			String faultType = ordereturn.getReturnCategory();
			String cancelType = (ordereturn.getCancelType() != null) ? ordereturn
					.getCancelType() : "";

			Map<String, Float> chargesMap = new HashMap<String, Float>();
			List<NRnReturnCharges> chargesList = partner.getNrnReturnConfig()
					.getCharges();
			for (NRnReturnCharges charge : chargesList) {
				chargesMap
						.put(charge.getChargeName(), charge.getChargeAmount());
			}

			switch (returnType) {
			case "returnCharges":
				if (faultType.equals(GlobalConstant.SellerFaultString)) {
					fixedAmount = GlobalConstant.ReturnChargesSellerFaultFixedAmount;
					varPercentSP = GlobalConstant.ReturnChargesSellerFaultVariablePercentSP;
					varPercentFixAmt = GlobalConstant.ReturnChargesSellerFaultVariableFixedAmt;
					varPercentPCC = GlobalConstant.ReturnChargesSellerFaultVariablePercentPCC;
					chargesType = partner.getNrnReturnConfig()
							.getRetCharSFType();
					shippingfee = partner.getNrnReturnConfig()
							.isRetCharSFShipFee();
					servicetax = partner.getNrnReturnConfig()
							.isRetCharSFSerTax();
					fixedfee = partner.getNrnReturnConfig().isRetCharSFFF();
					paycollcharges = partner.getNrnReturnConfig()
							.isRetCharSFPCC();
					isRevShippingFee = partner.getNrnReturnConfig()
							.isRetCharSFRevShipFee();
				} else {
					fixedAmount = GlobalConstant.ReturnChargesBuyerReturnFixedAmount;
					varPercentSP = GlobalConstant.ReturnChargesBuyerReturnVariablePercentSP;
					varPercentFixAmt = GlobalConstant.ReturnChargesBuyerReturnVariableFixedAmt;
					chargesType = partner.getNrnReturnConfig()
							.getRetCharBRType();
					shippingfee = partner.getNrnReturnConfig()
							.isRetCharBRShipFee();
					servicetax = partner.getNrnReturnConfig()
							.isRetCharBRSerTax();
					fixedfee = partner.getNrnReturnConfig().isRetCharBRFF();
					paycollcharges = partner.getNrnReturnConfig()
							.isRetCharBRPCC();
				}

				break;
			case "RTOCharges":
				if (faultType.equals(GlobalConstant.SellerFaultString)) {
					fixedAmount = GlobalConstant.RTOChargesSellerFaultFixedAmount;
					varPercentSP = GlobalConstant.RTOChargesSellerFaultVariablePercentSP;
					varPercentFixAmt = GlobalConstant.RTOChargesSellerFaultVariableFixedAmt;
					varPercentPCC = GlobalConstant.RTOChargesSellerFaultVariablePercentPCC;
					chargesType = partner.getNrnReturnConfig()
							.getRTOCharSFType();
					shippingfee = partner.getNrnReturnConfig()
							.isRTOCharSFShipFee();
					servicetax = partner.getNrnReturnConfig()
							.isRTOCharSFSerTax();
					fixedfee = partner.getNrnReturnConfig().isRTOCharSFFF();
					paycollcharges = partner.getNrnReturnConfig()
							.isRTOCharSFPCC();
					isRevShippingFee = partner.getNrnReturnConfig()
							.isRTOCharSFRevShipFee();
				} else {
					fixedAmount = GlobalConstant.RTOChargesBuyerReturnFixedAmount;
					varPercentSP = GlobalConstant.RTOChargesBuyerReturnVariablePercentSP;
					varPercentFixAmt = GlobalConstant.RTOChargesBuyerReturnVariableFixedAmt;
					chargesType = partner.getNrnReturnConfig()
							.getRTOCharBRType();
					shippingfee = partner.getNrnReturnConfig()
							.isRTOCharBRShipFee();
					servicetax = partner.getNrnReturnConfig()
							.isRTOCharBRSerTax();
					fixedfee = partner.getNrnReturnConfig().isRTOCharBRFF();
					paycollcharges = partner.getNrnReturnConfig()
							.isRTOCharBRPCC();
				}

				break;
			case "cancellationCharges":
				if (faultType.equals(GlobalConstant.SellerFaultString)
						&& cancelType
								.equals(GlobalConstant.SFCancellationAfterRTDString)) {
					fixedAmount = GlobalConstant.CancellationChargesSellerFaultFixedAmount;
					varPercentSP = GlobalConstant.CancellationChargesSellerFaultVariablePercentSP;
					varPercentFixAmt = GlobalConstant.CancellationChargesSellerFaultVariableFixedAmt;
					varPercentPCC = GlobalConstant.CancellationChargesSellerFaultVariablePercentPCC;
					chargesType = partner.getNrnReturnConfig()
							.getCanCharSFARTDType();
					shippingfee = partner.getNrnReturnConfig()
							.isCanCharSFShipFee();
					servicetax = partner.getNrnReturnConfig()
							.isCanCharSFSerTax();
					fixedfee = partner.getNrnReturnConfig().isCanCharSFFF();
					paycollcharges = partner.getNrnReturnConfig()
							.isCanCharSFPCC();
					isRevShippingFee = partner.getNrnReturnConfig()
							.isCanCharSFARTDRevShipFee();
				} else if (faultType.equals(GlobalConstant.SellerFaultString)
						&& cancelType
								.equals(GlobalConstant.SFCancellationBeforeRTDString)) {
					fixedAmount = GlobalConstant.CancellationChargesSellerFaultBRTDFixedAmount;
					varPercentSP = GlobalConstant.CancellationChargesSellerFaultBRTDVariablePercentSP;
					varPercentFixAmt = GlobalConstant.CancellationChargesSellerFaultBRTDVariableFixedAmt;
					varPercentPCC = GlobalConstant.CancellationChargesSellerFaultBRTDVariablePercentPCC;
					chargesType = partner.getNrnReturnConfig()
							.getCanCharSFBFRTDType();
					shippingfee = partner.getNrnReturnConfig()
							.isCanCharSFBRTDShipFee();
					servicetax = partner.getNrnReturnConfig()
							.isCanCharSFBRTDSerTax();
					fixedfee = partner.getNrnReturnConfig().isCanCharSFBRTDFF();
					paycollcharges = partner.getNrnReturnConfig()
							.isCanCharSFBRTDPCC();
					isRevShippingFee = partner.getNrnReturnConfig()
							.isCanCharSFBRTDRevShipFee();
				} else {
					fixedAmount = GlobalConstant.CancellationChargesBuyerReturnFixedAmount;
					varPercentSP = GlobalConstant.CancellationChargesBuyerReturnVariablePercentSP;
					varPercentFixAmt = GlobalConstant.CancellationChargesBuyerReturnVariableFixedAmt;
					chargesType = partner.getNrnReturnConfig()
							.getCanCharBRType();
					shippingfee = partner.getNrnReturnConfig()
							.isCanCharBRShipFee();
					servicetax = partner.getNrnReturnConfig()
							.isCanCharBRSerTax();
					fixedfee = partner.getNrnReturnConfig().isCanCharBRFF();
					paycollcharges = partner.getNrnReturnConfig()
							.isCanCharBRPCC();
				}

				break;
			case "replacementCharges":
				if (faultType.equals(GlobalConstant.SellerFaultString)) {
					fixedAmount = GlobalConstant.ReplacementChargesSellerFaultFixedAmount;
					varPercentSP = GlobalConstant.ReplacementChargesSellerFaultVariablePercentSP;
					varPercentFixAmt = GlobalConstant.ReplacementChargesSellerFaultVariableFixedAmt;
					varPercentPCC = GlobalConstant.ReplacementChargesSellerFaultVariablePercentPCC;
					chargesType = partner.getNrnReturnConfig()
							.getRepCharSFType();
					shippingfee = partner.getNrnReturnConfig()
							.isRepCharSFShipFee();
					servicetax = partner.getNrnReturnConfig()
							.isRepCharSFSerTax();
					fixedfee = partner.getNrnReturnConfig().isRepCharSFFF();
					paycollcharges = partner.getNrnReturnConfig()
							.isRepCharSFPCC();
					isRevShippingFee = partner.getNrnReturnConfig()
							.isRepCharSFRevShipFee();
				} else {
					fixedAmount = GlobalConstant.ReplacementChargesBuyerReturnFixedAmount;
					varPercentSP = GlobalConstant.ReplacementChargesBuyerReturnVariablePercentSP;
					varPercentFixAmt = GlobalConstant.ReplacementChargesBuyerReturnVariableFixedAmt;
					chargesType = partner.getNrnReturnConfig()
							.getRepCharBRType();
					shippingfee = partner.getNrnReturnConfig()
							.isRepCharBRShipFee();
					servicetax = partner.getNrnReturnConfig()
							.isRepCharBRSerTax();
					fixedfee = partner.getNrnReturnConfig().isRepCharBRFF();
					paycollcharges = partner.getNrnReturnConfig()
							.isRepCharBRPCC();
				}

				break;
			case "partialDeliveryCharges":

				if (faultType.equals(GlobalConstant.SellerFaultString)) {
					fixedAmount = GlobalConstant.PartialDelChargesSellerFaultFixedAmount;
					varPercentSP = GlobalConstant.PartialDelChargesSellerFaultVariablePercentSP;
					varPercentFixAmt = GlobalConstant.PartialDelChargesSellerFaultVariableFixedAmt;
					varPercentPCC = GlobalConstant.PartialDelChargesSellerFaultVariablePercentPCC;
					chargesType = partner.getNrnReturnConfig()
							.getPDCharSFType();
					shippingfee = partner.getNrnReturnConfig()
							.isPDCharSFShipFee();
					servicetax = partner.getNrnReturnConfig()
							.isPDCharSFSerTax();
					fixedfee = partner.getNrnReturnConfig().isPDCharSFFF();
					paycollcharges = partner.getNrnReturnConfig()
							.isPDCharSFPCC();
					isRevShippingFee = partner.getNrnReturnConfig()
							.isPDCharSFRevShipFee();
				} else {
					fixedAmount = GlobalConstant.PartialDelChargesBuyerReturnFixedAmount;
					varPercentSP = GlobalConstant.PartialDelChargesBuyerReturnVariablePercentSP;
					varPercentFixAmt = GlobalConstant.PartialDelChargesBuyerReturnVariableFixedAmt;
					chargesType = partner.getNrnReturnConfig()
							.getPDCharBRType();
					shippingfee = partner.getNrnReturnConfig()
							.isPDCharBRShipFee();
					servicetax = partner.getNrnReturnConfig()
							.isPDCharBRSerTax();
					fixedfee = partner.getNrnReturnConfig().isPDCharBRFF();
					paycollcharges = partner.getNrnReturnConfig()
							.isPDCharBRPCC();
				}
				break;

			default:
				break;
			}

			if (chargesType.equalsIgnoreCase(GlobalConstant.fixedString)) {
				log.debug(" Fixed amount in return : " + fixedAmount);
				totalcharge = totalcharge
						+ (chargesMap.containsKey(fixedAmount) ? chargesMap
								.get(fixedAmount) : 0);
			} else if (chargesType
					.equalsIgnoreCase(GlobalConstant.variableString)) {
				log.debug(" Variable amount in Return : " + varPercentSP
						+ " varPercentFixAmt :" + varPercentFixAmt
						+ " varPercentPCC : " + varPercentPCC
						+ "shipping fee : " + shippingfee + " fixedfee  :"
						+ fixedfee + " paycollcharges " + paycollcharges);
				System.out.println(" Variable amount in Return : "
						+ varPercentSP + " varPercentFixAmt :"
						+ varPercentFixAmt + " varPercentPCC : "
						+ varPercentPCC + "shipping fee : " + shippingfee
						+ " fixedfee  :" + fixedfee + " paycollcharges "
						+ paycollcharges);
				totalcharge = totalcharge
						+ (float) (chargesMap.containsKey(varPercentSP) ? (chargesMap
								.get(varPercentSP)
								* (order.getOrderSP() / order.getQuantity()) / 100)
								: 0);
				totalcharge = totalcharge
						+ (chargesMap.containsKey(varPercentFixAmt) ? chargesMap
								.get(varPercentFixAmt) : 0);
				totalcharge = totalcharge
						+ (float) (shippingfee ? order.getShippingCharges() : 0);
				totalcharge = totalcharge
						+ (float) (fixedfee ? order.getFixedfee() : 0);

				totalcharge = totalcharge
						+ (float) (paycollcharges ? order.getPccAmount() : 0);

				totalcharge = totalcharge
						+ (float) (chargesMap.containsKey(varPercentPCC) ? (chargesMap
								.get(varPercentPCC)
								* order.getPartnerCommission() / 100) : 0);

			}

			String revShippingType = partner.getNrnReturnConfig()
					.getRevShippingFeeType();
			if (isRevShippingFee && revShippingType != null) {

				switch (revShippingType) {
				case "revShipFeePCC":

					revShippingFee = (float) (chargesMap
							.containsKey(GlobalConstant.ReverseShippingFeePercentShipFee) ? (chargesMap
							.get(GlobalConstant.ReverseShippingFeePercentShipFee)
							* order.getShippingCharges() / 100)
							: 0);
					break;
				case "revShipFeeNA":

					revShippingFee = 0;
					break;
				case "revShipFeeGRT":

					float revShipMarketFee = (float) (chargesMap
							.containsKey(GlobalConstant.ReverseShippingFeePercentMarketFee) ? ((chargesMap
							.get(GlobalConstant.ReverseShippingFeePercentMarketFee) / 100) * order
							.getPartnerCommission())
							: 0);
					if (chargesMap
							.get(GlobalConstant.ReverseShippingFeeFlatAmt) > revShipMarketFee) {
						revShippingFee = chargesMap
								.get(GlobalConstant.ReverseShippingFeeFlatAmt);
					} else {
						revShippingFee = revShipMarketFee;
					}
					break;
				case "revShipFeeFF":

					revShippingFee = (float) (chargesMap
							.get(GlobalConstant.ReverseShippingFeeFixedAmt));
					break;
				case "revShipFeeShipFee":

					revShippingFee = (float) order.getShippingCharges();
					break;

				case "revShipFeeVar":

					Product product = productService.getProduct(
							order.getProductSkuCode(), sellerId);
					float deadWeight = (float) (chargesMap
							.containsKey(GlobalConstant.ReverseShippingFeeDeadWeightMinWeight) ? chargesMap
							.get(GlobalConstant.ReverseShippingFeeDeadWeightMinWeight)
							: 0);
					if (product != null && deadWeight < product.getDeadWeight()) {
						deadWeight = product.getDeadWeight();
					}
					float revShippingFeeDW = (float) (Math
							.ceil(deadWeight
									/ (chargesMap
											.containsKey(GlobalConstant.ReverseShippingFeeDeadWeightPerWeight) ? chargesMap
											.get(GlobalConstant.ReverseShippingFeeDeadWeightPerWeight)
											: 1)))
							* (chargesMap
									.containsKey(GlobalConstant.ReverseShippingFeeDeadWeightAmt) ? chargesMap
									.get(GlobalConstant.ReverseShippingFeeDeadWeightAmt)
									: 0);

					float volumeWeight = (float) (chargesMap
							.containsKey(GlobalConstant.ReverseShippingFeeVolumeWeightMinWeight) ? chargesMap
							.get(GlobalConstant.ReverseShippingFeeVolumeWeightMinWeight)
							: 0);
					if (volumeWeight < product.getVolWeight()) {
						volumeWeight = product.getVolWeight();
					}
					float revShippingFeeVW = (float) (Math
							.ceil(volumeWeight
									/ (chargesMap
											.containsKey(GlobalConstant.ReverseShippingFeeVolumeWeightPerWeight) ? chargesMap
											.get(GlobalConstant.ReverseShippingFeeVolumeWeightPerWeight)
											: 1)))
							* (chargesMap
									.containsKey(GlobalConstant.ReverseShippingFeeVolumeWeightAmt) ? chargesMap
									.get(GlobalConstant.ReverseShippingFeeVolumeWeightAmt)
									: 0);

					if (revShippingFeeDW > revShippingFeeVW) {
						revShippingFee = revShippingFeeDW;
					} else {
						revShippingFee = revShippingFeeVW;
					}

				default:
					break;
				}
			}

			totalcharge = totalcharge + revShippingFee;

			float serviceTax = chargesMap.containsKey("serviceTax") ? chargesMap
					.get("serviceTax") : 0;

			log.debug(" Total return charge calculated : " + totalcharge
					+ "Reverse shiping fee : " + revShippingFee
					+ " Service tax applied  : " + serviceTax);
			order.setServiceTax(serviceTax);
			if (serviceTax > 0) {
				totalcharge = totalcharge + (totalcharge * serviceTax) / 100;
			}
		} catch (Exception e) {
			log.error("Failed! by sellerId : " + sellerId, e);
		}
		log.info("*** calculateReturnCharges ends : OrderDaoImpl ***");
		return totalcharge;
	}

	private boolean calculateDeliveryDate(Order order, int sellerId) {

		log.info("*** calculateDeliveryDate starts : OrderDaoImpl ***");
		boolean result = false;
		String pincode = order.getCustomer().getZipcode();
		String statename = areaConfigDao.getStateFromZipCode(pincode);
		Date temp = null;
		try {
			int deliverydays = sellerDao.getStateDeliveryTime(sellerId,
					statename);
			temp = (Date) order.getShippedDate().clone();
			temp.setDate(temp.getDate() + deliverydays);
			order.setDeliveryDate(temp);
			log.debug(" Delivery days : " + deliverydays);
			log.debug(" Shipped date : " + order.getShippedDate());
			log.debug(" Delivery date : " + order.getDeliveryDate());
			result = true;
		} catch (CustomException e) {
			e.printStackTrace();
			return false;
		}
		log.debug(" State from order :  " + statename);
		log.info("*** calculateDeliveryDate ends : OrderDaoImpl ***");
		return result;
	}

	@Override
	public List<ChannelSalesDetails> findChannelOrdersbyDate(String string,
			Date startDate, Date endDate, int sellerIdfromSession) {

		log.info("*** findChannelOrdersbyDate starts : OrderDaoImpl ***");
		Session session = sessionFactory.getCurrentSession();

		Criteria criteriaTax = session.createCriteria(TaxCategory.class);
		ProjectionList tList = Projections.projectionList();
		HashMap<String, Float> taxMap = new HashMap<String, Float>();
		tList.add(Projections.property("taxCatId"));
		tList.add(Projections.property("taxCatName"));
		tList.add(Projections.property("taxPercent"));
		criteriaTax.setProjection(tList);
		List taxList = criteriaTax.list();
		Iterator txItr = taxList.iterator();

		while (txItr.hasNext()) {
			Object[] recordsRow = (Object[]) txItr.next();
			taxMap.put(recordsRow[1].toString(),
					Float.parseFloat(recordsRow[2].toString()));
		}

		Criteria criteria1 = session.createCriteria(Order.class);
		criteria1.createAlias("seller", "seller",
				CriteriaSpecification.LEFT_JOIN);
		criteria1.createAlias("customer", "customer",
				CriteriaSpecification.LEFT_JOIN);
		criteria1.createAlias("orderPayment", "orderPayment",
				CriteriaSpecification.LEFT_JOIN);
		criteria1.createAlias("orderTax", "orderTax",
				CriteriaSpecification.LEFT_JOIN);
		criteria1.createAlias("orderReturnOrRTO", "orderReturnOrRTO",
				CriteriaSpecification.LEFT_JOIN);
		criteria1.add(Restrictions.between("orderDate", startDate, endDate));
		criteria1.setProjection(getPL("pcName"));

		List pcNameList = criteria1.list();
		Iterator pcNameItr = pcNameList.iterator();
		List<ChannelSalesDetails> ttso = new ArrayList<ChannelSalesDetails>();
		ChannelSalesDetails temp;
		while (pcNameItr.hasNext()) {
			Object[] recordsRow = (Object[]) pcNameItr.next();
			temp = new ChannelSalesDetails();
			populateChannelSalesDetails(temp, recordsRow, startDate, endDate);
			log.debug("PC NAME " + temp.getPcName() + "  "
					+ temp.getStartDate() + " " + temp.getOrderId() + "  "
					+ temp.getInvoiceID());

			temp.setTaxPercent(taxMap.get(temp.getTaxCategtory()));
			ttso.add(temp);
		}
		log.debug("THIS IS THE TOTAL LIST OF ORDERS " + ttso.size());
		log.info("*** findChannelOrdersbyDate ends : OrderDaoImpl ***");
		return ttso;
	}

	public static void populateChannelSalesDetails(ChannelSalesDetails temp,
			Object[] recordsRow, Date startDate, Date endDate) {

		log.info("*** populateChannelSalesDetails starts : OrderDaoImpl ***");
		temp.setPcName(recordsRow[0].toString());
		temp.setTaxCategtory(recordsRow[0].toString());
		temp.setQuantity(Integer.parseInt(recordsRow[1].toString()));
		temp.setDiscount(Double.parseDouble(recordsRow[2].toString()));
		temp.setOrderMRP(Double.parseDouble(recordsRow[3].toString()));
		temp.setOrderSP(Double.parseDouble(recordsRow[4].toString()));
		temp.setShippingCharges(Double.parseDouble(recordsRow[5].toString()));
		temp.setNetSaleQuantity(Integer.parseInt(recordsRow[6].toString()));
		temp.setNetRate(Double.parseDouble(recordsRow[7].toString()));
		temp.setGrossNetRate(Double.parseDouble(recordsRow[8].toString()));
		temp.setPartnerCommission(Double.parseDouble(recordsRow[9].toString()));
		temp.setPr(Double.parseDouble(recordsRow[10].toString()));
		temp.setTotalAmountRecieved(Double.parseDouble(recordsRow[11]
				.toString()));
		temp.setPoPrice(Double.parseDouble(recordsRow[12].toString()));
		temp.setGrossProfit(Double.parseDouble(recordsRow[13].toString()));
		temp.setServiceTax(Float.parseFloat(recordsRow[14].toString()));
		temp.setFixedfee(Double.parseDouble(recordsRow[15].toString()));
		temp.setPccAmount(Double.parseDouble(recordsRow[16].toString()));
		temp.setNegativeAmount(Double.parseDouble(recordsRow[17].toString()));
		temp.setPositiveAmount(Double.parseDouble(recordsRow[18].toString()));
		temp.setActualrecived2(Double.parseDouble(recordsRow[19].toString()));
		temp.setNetPaymentResult(Double.parseDouble(recordsRow[20].toString()));
		temp.setPaymentDifference(Double.parseDouble(recordsRow[21].toString()));
		temp.setReturnOrRTOCharges(Double.parseDouble(recordsRow[22].toString()));
		temp.setReturnorrtoQty(Integer.parseInt(recordsRow[23].toString()));
		temp.setStartDate(startDate.getDate() + "|" + startDate.getMonth()
				+ "|" + startDate.getYear());
		temp.setEndDate(endDate.getDate() + "|" + endDate.getMonth() + "|"
				+ endDate.getYear());
		temp.setReturnOrRTOChargestoBeDeducted(Double
				.parseDouble(recordsRow[24].toString()));
		temp.setOrderId(Integer.parseInt(recordsRow[25].toString()));
		temp.setInvoiceID(recordsRow[26].toString());

		temp.setProductSkuCode(recordsRow[27].toString());
		temp.setTaxCategtory(recordsRow[28].toString());
		temp.setTax(Float.parseFloat(recordsRow[29].toString()));

		temp.setNrTax(Double.parseDouble(recordsRow[30].toString()));
		temp.setNrReturn(Double.parseDouble(recordsRow[31].toString()));
		temp.setReturnSP(Double.parseDouble(recordsRow[32].toString()));

		log.info("*** populateChannelSalesDetails ends : OrderDaoImpl ***");
	}

	public static ProjectionList getPL(String name) {
		ProjectionList projList = Projections.projectionList();

		log.info("*** getPL Starts : OrderDaoImpl ***");
		projList.add(Projections.property("pcName"));
		projList.add(Projections.property("quantity"));
		projList.add(Projections.property("discount"));
		projList.add(Projections.property("orderMRP"));
		projList.add(Projections.property("orderSP"));
		projList.add(Projections.property("shippingCharges"));
		projList.add(Projections.property("netSaleQuantity"));
		projList.add(Projections.property("netRate"));
		projList.add(Projections.property("grossNetRate"));
		projList.add(Projections.property("partnerCommission"));
		projList.add(Projections.property("pr"));
		projList.add(Projections.property("totalAmountRecieved"));
		projList.add(Projections.property("poPrice"));
		projList.add(Projections.property("grossProfit"));
		projList.add(Projections.property("serviceTax"));
		projList.add(Projections.property("fixedfee"));
		projList.add(Projections.property("pccAmount"));
		projList.add(Projections.property("orderPayment.negativeAmount"));
		projList.add(Projections.property("orderPayment.positiveAmount"));
		projList.add(Projections.property("orderPayment.actualrecived2"));
		projList.add(Projections.property("orderPayment.netPaymentResult"));
		projList.add(Projections.property("orderPayment.paymentDifference"));
		projList.add(Projections
				.property("orderReturnOrRTO.returnOrRTOCharges"));
		projList.add(Projections.property("orderReturnOrRTO.returnorrtoQty"));
		projList.add(Projections
				.property("orderReturnOrRTO.returnOrRTOChargestoBeDeducted"));
		projList.add(Projections.property("orderId"));
		projList.add(Projections.property("invoiceID"));
		projList.add(Projections.property("productSkuCode"));
		projList.add(Projections.property("orderTax.taxCategtory"));

		projList.add(Projections.property("orderTax.tax"));
		projList.add(Projections.sqlProjection(
				"( (tax/quantity) * returnorrtoQty ) as test",
				new String[] { "test" }, new Type[] { Hibernate.DOUBLE }));
		projList.add(Projections.sqlProjection(
				"( grossNetRate * returnorrtoQty ) as testq",
				new String[] { "testq" }, new Type[] { Hibernate.DOUBLE }));
		projList.add(Projections.sqlProjection(
				"( (orderSP/quantity) * returnorrtoQty ) as testR",
				new String[] { "testR" }, new Type[] { Hibernate.DOUBLE }));
		log.info("*** getPL ends : OrderDaoImpl ***");
		return projList;
	}

	private boolean calculateNR(NRnReturnConfig nrnReturnConfig, Order order,
			String prodCat, float deadWeight, float volWeight, int sellerId) {

		log.info("$$$ calculateNR for Events Start $$$");

		double nrValue = 0;
		float comission = 0;
		float fixedfee = 0;
		double pccAmount = 0;
		float serviceTax = 0;
		double tds = 0;
		Partner partner = null;

		StringBuffer area = new StringBuffer("");
		StringBuffer volarea = new StringBuffer("");

		float vwchargetemp = 0;
		float dwchargetemp = 0;
		float shippingCharges = 0;
		String tempStr = null;
		String state = order.getCustomer().getCustomerAddress();
		double SP = order.getOrderSP();
		StringBuffer temp = new StringBuffer("");
		Map<String, Float> chargesMap = new HashMap<String, Float>();

		List<NRnReturnCharges> chargesList = nrnReturnConfig.getCharges();

		PartnerBean pbean = new PartnerBean();

		for (NRnReturnCharges charge : chargesList) {

			if (charge.getChargeName().contains("fixedfee")
					&& charge.getCriteria() != null
					&& !"".equals(charge.getCriteria())) {

				ChargesBean chargeBean = new ChargesBean();
				chargeBean.setChargeType("fixedfee");
				chargeBean.setCriteria(charge.getCriteria());
				chargeBean.setRange(charge.getCriteriaRange());
				chargeBean.setValue(charge.getChargeAmount());
				pbean.getFixedfeeList().add(chargeBean);

			} else if (charge.getChargeName().contains("shippingfeeVolume")
					&& charge.getCriteria() != null
					&& !"".equals(charge.getCriteria())) {

				if (pbean.getNrnReturnConfig().getShippingFeeType()
						.equalsIgnoreCase("variable")
						&& charge.getChargeName().contains(
								"shippingfeeVolumeVariable")) {

					ChargesBean chargeBean = pbean.getChargesBean(
							"shippingfeeVolumeVariable", charge.getCriteria(),
							charge.getCriteriaRange());
					if (chargeBean == null) {
						chargeBean = new ChargesBean();
						chargeBean.setChargeType("shippingfeeVolumeVariable");
						chargeBean.setCriteria(charge.getCriteria());
						chargeBean.setRange(charge.getCriteriaRange());
						pbean.getshippingfeeVolumeVariableList()
								.add(chargeBean);
					}

					if (charge.getChargeName().contains("Local")) {
						chargeBean.setLocalValue(charge.getChargeAmount());
					} else if (charge.getChargeName().contains("Zonal")) {
						chargeBean.setZonalValue(charge.getChargeAmount());
					} else if (charge.getChargeName().contains("National")) {
						chargeBean.setNationalValue(charge.getChargeAmount());
					} else if (charge.getChargeName().contains("Metro")) {
						chargeBean.setMetroValue(charge.getChargeAmount());
					}

				} else if (charge.getChargeName().contains(
						"shippingfeeVolumeFixed")) {
					ChargesBean chargeBean = new ChargesBean();
					chargeBean.setChargeType("shippingfeeVolumeFixed");
					chargeBean.setCriteria(charge.getCriteria());
					chargeBean.setRange(charge.getCriteriaRange());
					chargeBean.setValue(charge.getChargeAmount());
					pbean.getshippingfeeVolumeFixedList().add(chargeBean);
				}

			} else if (charge.getChargeName().contains("shippingfeeWeight")
					&& charge.getCriteria() != null
					&& !"".equals(charge.getCriteria())) {

				if (pbean.getNrnReturnConfig().getShippingFeeType()
						.equalsIgnoreCase("variable")
						&& charge.getChargeName().contains(
								"shippingfeeWeightVariable")) {

					ChargesBean chargeBean = pbean.getChargesBean(
							"shippingfeeWeightVariable", charge.getCriteria(),
							charge.getCriteriaRange());
					if (chargeBean == null) {
						chargeBean = new ChargesBean();
						chargeBean.setChargeType("shippingfeeWeightVariable");
						chargeBean.setCriteria(charge.getCriteria());
						chargeBean.setRange(charge.getCriteriaRange());
						pbean.getshippingfeeWeightVariableList()
								.add(chargeBean);
					}

					if (charge.getChargeName().contains("Local")) {
						chargeBean.setLocalValue(charge.getChargeAmount());
					} else if (charge.getChargeName().contains("Zonal")) {
						chargeBean.setZonalValue(charge.getChargeAmount());
					} else if (charge.getChargeName().contains("National")) {
						chargeBean.setNationalValue(charge.getChargeAmount());
					} else if (charge.getChargeName().contains("Metro")) {
						chargeBean.setMetroValue(charge.getChargeAmount());
					}

				} else if (charge.getChargeName().contains(
						"shippingfeeWeightFixed")) {
					ChargesBean chargeBean = new ChargesBean();
					chargeBean.setChargeType("shippingfeeWeightFixed");
					chargeBean.setCriteria(charge.getCriteria());
					chargeBean.setRange(charge.getCriteriaRange());
					chargeBean.setValue(charge.getChargeAmount());
					pbean.getshippingfeeWeightFixedList().add(chargeBean);
				}

			} else {
				chargesMap
						.put(charge.getChargeName(), charge.getChargeAmount());
			}
		}
		if (pbean.getFixedfeeList() != null
				&& pbean.getFixedfeeList().size() != 0)
			Collections
					.sort(pbean.getFixedfeeList(), new SortByCriteriaRange());
		if (pbean.getshippingfeeVolumeFixedList() != null
				&& pbean.getshippingfeeVolumeFixedList().size() != 0)
			Collections.sort(pbean.getshippingfeeVolumeFixedList(),
					new SortByCriteria());
		if (pbean.getshippingfeeWeightFixedList() != null
				&& pbean.getshippingfeeWeightFixedList().size() != 0)
			Collections.sort(pbean.getshippingfeeWeightFixedList(),
					new SortByCriteria());
		if (pbean.getshippingfeeVolumeVariableList() != null
				&& pbean.getshippingfeeVolumeVariableList().size() != 0)
			Collections.sort(pbean.getshippingfeeVolumeVariableList(),
					new SortByCriteria());
		if (pbean.getshippingfeeWeightVariableList() != null
				&& pbean.getshippingfeeWeightVariableList().size() != 0)
			Collections.sort(pbean.getshippingfeeWeightVariableList(),
					new SortByCriteria());

		// Extracting comiision value
		try {
			if (nrnReturnConfig.getCommissionType() != null
					&& nrnReturnConfig.getCommissionType().equals("fixed")) {
				comission = chargesMap
						.get(GlobalConstant.fixedCommissionPercent);

			} else {
				comission = chargesMap.get(prodCat);
			}

			// Add partner new changes:

			// Getting Fixed fee
			if (pbean.getFixedfeeList() != null
					&& pbean.getFixedfeeList().size() != 0) {
				boolean inRange = false;
				Iterator<ChargesBean> fixedfeeIterator = pbean
						.getFixedfeeList().iterator();
				while (fixedfeeIterator.hasNext()) {
					ChargesBean cBean = fixedfeeIterator.next();
					if (SP <= cBean.getRange()) {
						fixedfee = (float) cBean.getValue();
						inRange = true;
						break;
					}
				}
				if (!inRange) {
					fixedfee = (float) pbean.getFixedfeeList()
							.get(pbean.getFixedfeeList().size() - 1).getValue();
				}
			}

			// Payment collection charges
			if (partner.getNrnReturnConfig().isWhicheverGreaterPCC()) {
				double percentAmount = chargesMap
						.containsKey(GlobalConstant.percentSPPCCHigher) ? chargesMap
						.get(GlobalConstant.percentSPPCCHigher) * SP / 100
						: 0;
				if (chargesMap.containsKey(GlobalConstant.fixedAmtPCC)
						&& percentAmount > chargesMap
								.get(GlobalConstant.fixedAmtPCC)) {
					pccAmount = percentAmount;
				} else
					pccAmount = chargesMap
							.containsKey(GlobalConstant.fixedAmtPCC) ? chargesMap
							.get(GlobalConstant.fixedAmtPCC) : 0;

			} else {
				double pccRange = chargesMap
						.containsKey(GlobalConstant.rangePCC) ? chargesMap
						.get(GlobalConstant.rangePCC) : 0;

				if (pccRange == 0) {
					pccAmount = 0;
				} else {
					if (SP < pccRange) {
						pccAmount = chargesMap
								.containsKey(GlobalConstant.valuePCC) ? chargesMap
								.get(GlobalConstant.valuePCC) : 0;
					} else {
						pccAmount = chargesMap
								.containsKey(GlobalConstant.percentSPPCCValue) ? chargesMap
								.get(GlobalConstant.percentSPPCCValue)
								* SP
								/ 100 : 0;
					}
				}
			}

			log.debug(" States : MetroLsit : "
					+ partner.getNrnReturnConfig().getMetroList()
					+ " national list : "
					+ partner.getNrnReturnConfig().getNationalList()
					+ " LocalList : "
					+ partner.getNrnReturnConfig().getLocalList()
					+ " zonallist: "
					+ partner.getNrnReturnConfig().getZonalList());
			log.debug(" State we are geting ofrom excel : " + state);

			// ****Shipping charges
			String valueType = "";
			if (partner.getNrnReturnConfig().getShippingFeeType() != null
					&& partner.getNrnReturnConfig().getShippingFeeType()
							.equals("variable")) {
				if (partner.getNrnReturnConfig().getMetroList() != null
						&& partner.getNrnReturnConfig().getMetroList()
								.contains(state)) {

					valueType = "metro";
				} else if (partner.getNrnReturnConfig().getNationalList() != null
						&& partner.getNrnReturnConfig().getNationalList()
								.contains(state)) {
					valueType = "national";
				} else if (partner.getNrnReturnConfig().getLocalList() != null
						&& partner.getNrnReturnConfig().getLocalList()
								.contains(state)) {
					valueType = "local";
				} else if (partner.getNrnReturnConfig().getZonalList() != null
						&& partner.getNrnReturnConfig().getZonalList()
								.contains(state)) {
					valueType = "zonal";
				}
			} else {
				valueType = "fixed";
			}
			order.setVolShippingString(valueType);
			if (partner.getNrnReturnConfig().getShippingFeeType() != null
					&& partner.getNrnReturnConfig().getShippingFeeType()
							.equals("variable")) {

				if (pbean.getshippingfeeWeightVariableList() != null
						&& pbean.getshippingfeeWeightVariableList().size() != 0
						&& deadWeight >= volWeight) {
					boolean inRange = false;
					Iterator<ChargesBean> shippingfeeWeightIterator = pbean
							.getshippingfeeWeightVariableList().iterator();
					while (shippingfeeWeightIterator.hasNext()) {
						ChargesBean cBean = shippingfeeWeightIterator.next();
						if (deadWeight <= cBean.getRange()) {
							if (valueType.equalsIgnoreCase("metro"))
								dwchargetemp = (float) cBean.getMetroValue();
							else if (valueType.equalsIgnoreCase("national"))
								dwchargetemp = (float) cBean.getNationalValue();
							else if (valueType.equalsIgnoreCase("local"))
								dwchargetemp = (float) cBean.getLocalValue();
							else if (valueType.equalsIgnoreCase("zonal"))
								dwchargetemp = (float) cBean.getZonalValue();
							inRange = true;
							break;
						}
					}
					if (!inRange) {
						float tempWeight = deadWeight
								- (float) pbean
										.getshippingfeeWeightVariableList()
										.get(pbean
												.getshippingfeeWeightVariableList()
												.size() - 2).getRange();
						float addWeight = (float) pbean
								.getshippingfeeWeightVariableList()
								.get(pbean.getshippingfeeWeightVariableList()
										.size() - 1).getRange();

						if (valueType.equalsIgnoreCase("metro"))
							dwchargetemp = (float) pbean
									.getshippingfeeWeightVariableList()
									.get(pbean
											.getshippingfeeWeightVariableList()
											.size() - 2).getMetroValue();
						else if (valueType.equalsIgnoreCase("national"))
							dwchargetemp = (float) pbean
									.getshippingfeeWeightVariableList()
									.get(pbean
											.getshippingfeeWeightVariableList()
											.size() - 2).getNationalValue();
						else if (valueType.equalsIgnoreCase("local"))
							dwchargetemp = (float) pbean
									.getshippingfeeWeightVariableList()
									.get(pbean
											.getshippingfeeWeightVariableList()
											.size() - 2).getLocalValue();
						else if (valueType.equalsIgnoreCase("zonal"))
							dwchargetemp = (float) pbean
									.getshippingfeeWeightVariableList()
									.get(pbean
											.getshippingfeeWeightVariableList()
											.size() - 2).getZonalValue();

						while (tempWeight > 0) {
							if (valueType.equalsIgnoreCase("metro"))
								dwchargetemp += (float) pbean
										.getshippingfeeWeightVariableList()
										.get(pbean
												.getshippingfeeWeightVariableList()
												.size() - 1).getMetroValue();
							else if (valueType.equalsIgnoreCase("national"))
								dwchargetemp += (float) pbean
										.getshippingfeeWeightVariableList()
										.get(pbean
												.getshippingfeeWeightVariableList()
												.size() - 1).getNationalValue();
							else if (valueType.equalsIgnoreCase("local"))
								dwchargetemp += (float) pbean
										.getshippingfeeWeightVariableList()
										.get(pbean
												.getshippingfeeWeightVariableList()
												.size() - 1).getLocalValue();
							else if (valueType.equalsIgnoreCase("zonal"))
								dwchargetemp += (float) pbean
										.getshippingfeeWeightVariableList()
										.get(pbean
												.getshippingfeeWeightVariableList()
												.size() - 1).getZonalValue();

							tempWeight = tempWeight - addWeight;
						}
					}
				}

				if (pbean.getshippingfeeVolumeVariableList() != null
						&& pbean.getshippingfeeVolumeVariableList().size() != 0
						&& volWeight > deadWeight) {
					boolean inRange = false;
					Iterator<ChargesBean> shippingfeeVolumeIterator = pbean
							.getshippingfeeVolumeVariableList().iterator();
					while (shippingfeeVolumeIterator.hasNext()) {
						ChargesBean cBean = shippingfeeVolumeIterator.next();
						if (volWeight <= cBean.getRange()) {
							if (valueType.equalsIgnoreCase("metro"))
								vwchargetemp = (float) cBean.getMetroValue();
							else if (valueType.equalsIgnoreCase("national"))
								vwchargetemp = (float) cBean.getNationalValue();
							else if (valueType.equalsIgnoreCase("local"))
								vwchargetemp = (float) cBean.getLocalValue();
							else if (valueType.equalsIgnoreCase("zonal"))
								vwchargetemp = (float) cBean.getZonalValue();
							inRange = true;
							break;
						}
					}
					if (!inRange) {
						float tempWeight = volWeight
								- (float) pbean
										.getshippingfeeVolumeVariableList()
										.get(pbean
												.getshippingfeeVolumeVariableList()
												.size() - 2).getRange();
						float addWeight = (float) pbean
								.getshippingfeeVolumeVariableList()
								.get(pbean.getshippingfeeVolumeVariableList()
										.size() - 1).getRange();

						if (valueType.equalsIgnoreCase("metro"))
							vwchargetemp = (float) pbean
									.getshippingfeeVolumeVariableList()
									.get(pbean
											.getshippingfeeVolumeVariableList()
											.size() - 2).getMetroValue();
						else if (valueType.equalsIgnoreCase("national"))
							vwchargetemp = (float) pbean
									.getshippingfeeVolumeVariableList()
									.get(pbean
											.getshippingfeeVolumeVariableList()
											.size() - 2).getNationalValue();
						else if (valueType.equalsIgnoreCase("local"))
							vwchargetemp = (float) pbean
									.getshippingfeeVolumeVariableList()
									.get(pbean
											.getshippingfeeVolumeVariableList()
											.size() - 2).getLocalValue();
						else if (valueType.equalsIgnoreCase("zonal"))
							vwchargetemp = (float) pbean
									.getshippingfeeVolumeVariableList()
									.get(pbean
											.getshippingfeeVolumeVariableList()
											.size() - 2).getZonalValue();

						while (tempWeight > 0) {
							if (valueType.equalsIgnoreCase("metro"))
								vwchargetemp += (float) pbean
										.getshippingfeeVolumeVariableList()
										.get(pbean
												.getshippingfeeVolumeVariableList()
												.size() - 1).getMetroValue();
							else if (valueType.equalsIgnoreCase("national"))
								vwchargetemp += (float) pbean
										.getshippingfeeVolumeVariableList()
										.get(pbean
												.getshippingfeeVolumeVariableList()
												.size() - 1).getNationalValue();
							else if (valueType.equalsIgnoreCase("local"))
								vwchargetemp += (float) pbean
										.getshippingfeeVolumeVariableList()
										.get(pbean
												.getshippingfeeVolumeVariableList()
												.size() - 1).getLocalValue();
							else if (valueType.equalsIgnoreCase("zonal"))
								vwchargetemp += (float) pbean
										.getshippingfeeVolumeVariableList()
										.get(pbean
												.getshippingfeeVolumeVariableList()
												.size() - 1).getZonalValue();

							tempWeight = tempWeight - addWeight;
						}
					}
				}
			} else if (partner.getNrnReturnConfig().getShippingFeeType() != null
					&& partner.getNrnReturnConfig().getShippingFeeType()
							.equals("fixed")) {

				if (pbean.getshippingfeeWeightFixedList() != null
						&& pbean.getshippingfeeWeightFixedList().size() != 0
						&& deadWeight >= volWeight) {
					boolean inRange = false;
					Iterator<ChargesBean> shippingfeeWeightIterator = pbean
							.getshippingfeeWeightFixedList().iterator();
					while (shippingfeeWeightIterator.hasNext()) {
						ChargesBean cBean = shippingfeeWeightIterator.next();
						if (deadWeight <= cBean.getRange()) {
							if (valueType.equalsIgnoreCase("fixed"))
								dwchargetemp = (float) cBean.getValue();
							inRange = true;
							break;
						}
					}
					if (!inRange) {
						float tempWeight = deadWeight
								- (float) pbean
										.getshippingfeeWeightFixedList()
										.get(pbean
												.getshippingfeeWeightFixedList()
												.size() - 2).getRange();
						float addWeight = (float) pbean
								.getshippingfeeWeightFixedList()
								.get(pbean.getshippingfeeWeightFixedList()
										.size() - 1).getRange();

						if (valueType.equalsIgnoreCase("fixed"))
							dwchargetemp = (float) pbean
									.getshippingfeeWeightFixedList()
									.get(pbean.getshippingfeeWeightFixedList()
											.size() - 2).getValue();

						while (tempWeight > 0) {
							if (valueType.equalsIgnoreCase("fixed"))
								dwchargetemp += (float) pbean
										.getshippingfeeWeightFixedList()
										.get(pbean
												.getshippingfeeWeightFixedList()
												.size() - 1).getValue();

							tempWeight = tempWeight - addWeight;
						}
					}
				}

				if (pbean.getshippingfeeVolumeFixedList() != null
						&& pbean.getshippingfeeVolumeFixedList().size() != 0
						&& volWeight > deadWeight) {
					boolean inRange = false;
					Iterator<ChargesBean> shippingfeeVolumeIterator = pbean
							.getshippingfeeVolumeFixedList().iterator();
					while (shippingfeeVolumeIterator.hasNext()) {
						ChargesBean cBean = shippingfeeVolumeIterator.next();
						if (volWeight <= cBean.getRange()) {
							if (valueType.equalsIgnoreCase("fixed"))
								vwchargetemp = (float) cBean.getValue();

							inRange = true;
							break;
						}
					}
					if (!inRange) {
						float tempWeight = volWeight
								- (float) pbean
										.getshippingfeeVolumeFixedList()
										.get(pbean
												.getshippingfeeVolumeFixedList()
												.size() - 2).getRange();
						float addWeight = (float) pbean
								.getshippingfeeVolumeFixedList()
								.get(pbean.getshippingfeeVolumeFixedList()
										.size() - 1).getRange();

						if (valueType.equalsIgnoreCase("fixed"))
							vwchargetemp = (float) pbean
									.getshippingfeeVolumeFixedList()
									.get(pbean.getshippingfeeVolumeFixedList()
											.size() - 2).getValue();

						while (tempWeight > 0) {
							if (valueType.equalsIgnoreCase("fixed"))
								vwchargetemp += (float) pbean
										.getshippingfeeVolumeFixedList()
										.get(pbean
												.getshippingfeeVolumeFixedList()
												.size() - 1).getValue();

							tempWeight = tempWeight - addWeight;
						}
					}
				}
			}

			if (vwchargetemp > dwchargetemp) {
				shippingCharges = vwchargetemp;
				order.setDwShippingString(GlobalConstant.volShipping);
			} else {
				shippingCharges = dwchargetemp;
				order.setDwShippingString(GlobalConstant.dwShipping);
			}

			// End

			/*
			 * // Getting Fixed fee if
			 * (chargesMap.containsKey(GlobalConstant.fixedfeelt250) &&
			 * chargesMap.get(GlobalConstant.fixedfeelt250).intValue() != 0) {
			 * if (SP < 251) fixedfee =
			 * chargesMap.get(GlobalConstant.fixedfeelt250); else if (SP > 250
			 * && SP < 501) fixedfee = chargesMap
			 * .containsKey(GlobalConstant.fixedfeegt250lt500) ? chargesMap
			 * .get(GlobalConstant.fixedfeegt250lt500) : 0; else fixedfee =
			 * chargesMap .containsKey(GlobalConstant.fixedfeegt500) ?
			 * chargesMap .get(GlobalConstant.fixedfeegt500) : 0; } else if
			 * (chargesMap.containsKey(GlobalConstant.fixedfeelt500) &&
			 * chargesMap.get(GlobalConstant.fixedfeelt500).intValue() != 0) {
			 * if (SP < 501) fixedfee =
			 * chargesMap.get(GlobalConstant.fixedfeelt500); else fixedfee =
			 * chargesMap .containsKey(GlobalConstant.fixedfeegt500Big) ?
			 * chargesMap .get(GlobalConstant.fixedfeegt500Big) : 0; } else { if
			 * (SP < 501) fixedfee = chargesMap
			 * .containsKey(GlobalConstant.fixedfeelt500Big) ? chargesMap
			 * .get(GlobalConstant.fixedfeelt500Big) : 0; else if (SP > 500 &&
			 * SP < 1001) fixedfee = chargesMap
			 * .containsKey(GlobalConstant.fixedfeegt500lt1000) ? chargesMap
			 * .get(GlobalConstant.fixedfeegt500lt1000) : 0; else if (SP > 1000
			 * && SP < 10001) fixedfee = chargesMap
			 * .containsKey(GlobalConstant.fixedfeegt1000lt10000) ? chargesMap
			 * .get(GlobalConstant.fixedfeegt1000lt10000) : 0; else fixedfee =
			 * chargesMap .containsKey(GlobalConstant.fixedfeegt10000) ?
			 * chargesMap .get(GlobalConstant.fixedfeegt10000) : 0;
			 * 
			 * }
			 * 
			 * // Payment collection charges if
			 * (nrnReturnConfig.isWhicheverGreaterPCC()) { double percentAmount
			 * = chargesMap .containsKey(GlobalConstant.percentSPPCC) ?
			 * chargesMap .get(GlobalConstant.percentSPPCC) * SP / 100 : 0; if
			 * (chargesMap.containsKey(GlobalConstant.fixedAmtPCC) &&
			 * percentAmount > chargesMap .get(GlobalConstant.fixedAmtPCC)) {
			 * pccAmount = percentAmount; } else pccAmount = chargesMap
			 * .containsKey(GlobalConstant.fixedAmtPCC) ? chargesMap
			 * .get(GlobalConstant.fixedAmtPCC) : 0;
			 * 
			 * } else if (chargesMap.containsKey(GlobalConstant.fixedAmtPCC) &&
			 * chargesMap.get(GlobalConstant.fixedAmtPCC) != 0.0) pccAmount =
			 * chargesMap.get(GlobalConstant.fixedAmtPCC);
			 * 
			 * else pccAmount =
			 * chargesMap.containsKey(GlobalConstant.percentSPPCC) ? chargesMap
			 * .get(GlobalConstant.percentSPPCC) * SP / 100 : 0;
			 * 
			 * log.debug(" States : MetroLsit : " +
			 * nrnReturnConfig.getMetroList() + " national list : " +
			 * nrnReturnConfig.getNationalList() + " LocalList : " +
			 * nrnReturnConfig.getLocalList() + " zonallist: " +
			 * nrnReturnConfig.getZonalList());
			 * log.debug(" State we are geting ofrom excel : " + state);
			 * 
			 * // ****Shipping charges if (nrnReturnConfig.getShippingFeeType()
			 * != null &&
			 * nrnReturnConfig.getShippingFeeType().equals("variable")) { if
			 * (nrnReturnConfig.getMetroList() != null &&
			 * nrnReturnConfig.getMetroList().contains(state)) {
			 * System.out.println(" Inside ,etro list setting state ");
			 * area.append("metro"); volarea.append("metro"); } else if
			 * (nrnReturnConfig.getNationalList() != null &&
			 * nrnReturnConfig.getNationalList().contains(state)) {
			 * area.append("national"); volarea.append("national"); } else if
			 * (nrnReturnConfig.getLocalList() != null &&
			 * nrnReturnConfig.getLocalList().contains(state)) {
			 * area.append("local"); volarea.append("local"); } else if
			 * (nrnReturnConfig.getZonalList() != null &&
			 * nrnReturnConfig.getZonalList().contains(state)) {
			 * area.append("zonal"); volarea.append("zonal"); } } else {
			 * area.append("fixed"); volarea.append("fixed"); } if (deadWeight <
			 * 501) { area.append("dwlt500");
			 * order.setDwShippingString(area.toString()); dwchargetemp =
			 * chargesMap.containsKey(area.toString()) ? chargesMap
			 * .get(area.toString()) : 0;
			 * 
			 * } else { temp = new StringBuffer(area); area.append("dwlt500");
			 * temp.append("dwgt500"); log.debug(" Area : " + area + " temp : "
			 * + temp); dwchargetemp = chargesMap.containsKey(area.toString()) ?
			 * chargesMap .get(area.toString()) : 0;
			 * log.debug(" Charges for lesstthan 500 : " + dwchargetemp); float
			 * range = (float) Math.ceil((deadWeight - 500) / 500);
			 * log.debug(" Range : " + range); dwchargetemp = dwchargetemp +
			 * (range * (chargesMap.containsKey(temp.toString()) ? chargesMap
			 * .get(temp.toString()) : 0));
			 * log.debug(" Charges for greater than 500 : " +
			 * chargesMap.get(temp.toString()));
			 * order.setDwShippingString(temp.toString());
			 * 
			 * }
			 * 
			 * if (volWeight < 501) { tempStr =
			 * volarea.append("vwlt500").toString(); log.debug(" tempStr " +
			 * tempStr);
			 * 
			 * vwchargetemp = chargesMap.containsKey(tempStr) ? chargesMap
			 * .get(tempStr) : 0; order.setVolShippingString(tempStr); } else if
			 * (volWeight > 500 && volWeight < 1001) { tempStr =
			 * volarea.append("vwgt500lt1000").toString(); vwchargetemp =
			 * chargesMap.containsKey(tempStr) ? chargesMap .get(tempStr) : 0;
			 * order.setVolShippingString(volarea.toString()); } else if
			 * (volWeight > 1000 && volWeight < 1501) { tempStr =
			 * volarea.append("vwgt1000lt1500").toString(); vwchargetemp =
			 * chargesMap.containsKey(tempStr) ? chargesMap .get(tempStr) : 0;
			 * order.setVolShippingString(volarea.toString()); } else if
			 * (volWeight > 1500 && volWeight < 5001) { tempStr =
			 * volarea.append("vwgt1500lt5000").toString();
			 * log.debug(" tempStr " + tempStr); vwchargetemp =
			 * chargesMap.containsKey(tempStr) ? chargesMap .get(tempStr) : 0;
			 * order.setVolShippingString(volarea.toString()); } else if
			 * (volWeight > 5000) { temp = new StringBuffer(volarea);
			 * volarea.append("vwgt1500lt5000");
			 * 
			 * vwchargetemp = chargesMap.containsKey(volarea.toString()) ?
			 * chargesMap .get(volarea.toString()) : 0;
			 * log.debug(" vol Charges for lesstthan 500 : " + vwchargetemp);
			 * temp.append("vwgt5000"); float range = (float)
			 * Math.ceil((volWeight - 5000) / 1000); log.debug("volarea  " +
			 * volarea + " temp : " + temp + " range invol: " + range);
			 * vwchargetemp = vwchargetemp + (range *
			 * (chargesMap.containsKey(temp.toString()) ? chargesMap
			 * .get(temp.toString()) : 0));
			 * order.setVolShippingString(temp.toString());
			 * 
			 * } log.debug(" vwchargetemp : " + vwchargetemp +
			 * " dwchargetemp : " + dwchargetemp); if (vwchargetemp >
			 * dwchargetemp) shippingCharges = vwchargetemp; else
			 * shippingCharges = dwchargetemp;
			 */

			comission = (float) (comission * SP) / 100;
			serviceTax = (chargesMap.containsKey("serviceTax") ? chargesMap
					.get("serviceTax") : 0)
					* (float) (shippingCharges + pccAmount + fixedfee + comission)
					/ 100;
			nrValue = SP - comission - fixedfee - pccAmount - shippingCharges
					- serviceTax;

			partner = partnerService.getPartner(order.getPcName(), sellerId);
			if (partner != null && partner.isTdsApplicable()) {
				props = PropertiesLoaderUtils.loadProperties(resource);
				tds = (((props.getProperty("TDS") != null ? Double
						.parseDouble(props.getProperty("TDS")) : 0)
						* comission
						/ 100 + ((fixedfee + pccAmount + shippingCharges) / 50)))
						* order.getQuantity();
				order.getOrderTax().setTdsToDeduct(tds);
			}
			order.setGrossNetRate(nrValue);
			order.setPartnerCommission(comission);
			order.setFixedfee(fixedfee);
			order.setPccAmount(pccAmount);
			order.setServiceTax(serviceTax);
			order.setShippingCharges(shippingCharges);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		log.debug(" Setting values for Nr in nrcalculator NR: "
				+ order.getNetRate() + ", commison : "
				+ order.getPartnerCommission() + " fixedfee : "
				+ order.getFixedfee() + " pcc : " + order.getPccAmount()
				+ " ->service tax : " + serviceTax + "shippingCharges : "
				+ order.getShippingCharges());

		log.info("$$$ calculateNR for Events exit $$$");
		return true;
	}

	private float calculateReturnCharges(Order order,
			OrderRTOorReturn ordereturn, int sellerId,
			NRnReturnConfig nrnReturnConfig) throws Exception {

		log.info("$$$ calculateReturnCharges for Events Start $$$");

		float totalcharge = 0;
		float revShippingFee = 0;
		String varPercentSP = null;
		String varPercentPCC = null;
		String varPercentFixAmt = null;
		String chargesType = null;
		String fixedAmount = null;
		boolean shippingfee = false;
		boolean servicetax = false;
		boolean fixedfee = false;
		boolean paycollcharges = false;
		boolean isRevShippingFee = false;

		String returnType = ordereturn.getType();
		String faultType = ordereturn.getReturnCategory();
		String cancelType = (ordereturn.getCancelType() != null) ? ordereturn
				.getCancelType() : "";

		Map<String, Float> chargesMap = new HashMap<String, Float>();
		List<NRnReturnCharges> chargesList = nrnReturnConfig.getCharges();
		for (NRnReturnCharges charge : chargesList) {
			chargesMap.put(charge.getChargeName(), charge.getChargeAmount());
		}

		switch (returnType) {
		case "returnCharges":
			if (faultType.equals(GlobalConstant.SellerFaultString)) {
				fixedAmount = GlobalConstant.ReturnChargesSellerFaultFixedAmount;
				varPercentSP = GlobalConstant.ReturnChargesSellerFaultVariablePercentSP;
				varPercentFixAmt = GlobalConstant.ReturnChargesSellerFaultVariableFixedAmt;
				varPercentPCC = GlobalConstant.ReturnChargesSellerFaultVariablePercentPCC;
				chargesType = nrnReturnConfig.getRetCharSFType();
				shippingfee = nrnReturnConfig.isRetCharSFShipFee();
				servicetax = nrnReturnConfig.isRetCharSFSerTax();
				fixedfee = nrnReturnConfig.isRetCharSFFF();
				paycollcharges = nrnReturnConfig.isRetCharSFPCC();
				isRevShippingFee = nrnReturnConfig.isRetCharSFRevShipFee();
			} else {
				fixedAmount = GlobalConstant.ReturnChargesBuyerReturnFixedAmount;
				varPercentSP = GlobalConstant.ReturnChargesBuyerReturnVariablePercentSP;
				varPercentFixAmt = GlobalConstant.ReturnChargesBuyerReturnVariableFixedAmt;
				chargesType = nrnReturnConfig.getRetCharBRType();
				shippingfee = nrnReturnConfig.isRetCharBRShipFee();
				servicetax = nrnReturnConfig.isRetCharBRSerTax();
				fixedfee = nrnReturnConfig.isRetCharBRFF();
				paycollcharges = nrnReturnConfig.isRetCharBRPCC();
			}

			break;
		case "RTOCharges":
			if (faultType.equals(GlobalConstant.SellerFaultString)) {
				fixedAmount = GlobalConstant.RTOChargesSellerFaultFixedAmount;
				varPercentSP = GlobalConstant.RTOChargesSellerFaultVariablePercentSP;
				varPercentFixAmt = GlobalConstant.RTOChargesSellerFaultVariableFixedAmt;
				varPercentPCC = GlobalConstant.RTOChargesSellerFaultVariablePercentPCC;
				chargesType = nrnReturnConfig.getRTOCharSFType();
				shippingfee = nrnReturnConfig.isRTOCharSFShipFee();
				servicetax = nrnReturnConfig.isRTOCharSFSerTax();
				fixedfee = nrnReturnConfig.isRTOCharSFFF();
				paycollcharges = nrnReturnConfig.isRTOCharSFPCC();
				isRevShippingFee = nrnReturnConfig.isRTOCharSFRevShipFee();
			} else {
				fixedAmount = GlobalConstant.RTOChargesBuyerReturnFixedAmount;
				varPercentSP = GlobalConstant.RTOChargesBuyerReturnVariablePercentSP;
				varPercentFixAmt = GlobalConstant.RTOChargesBuyerReturnVariableFixedAmt;
				chargesType = nrnReturnConfig.getRTOCharBRType();
				shippingfee = nrnReturnConfig.isRTOCharBRShipFee();
				servicetax = nrnReturnConfig.isRTOCharBRSerTax();
				fixedfee = nrnReturnConfig.isRTOCharBRFF();
				paycollcharges = nrnReturnConfig.isRTOCharBRPCC();
			}

			break;
		case "cancellationCharges":
			if (faultType.equals(GlobalConstant.SellerFaultString)
					&& cancelType
							.equals(GlobalConstant.SFCancellationAfterRTDString)) {
				fixedAmount = GlobalConstant.CancellationChargesSellerFaultFixedAmount;
				varPercentSP = GlobalConstant.CancellationChargesSellerFaultVariablePercentSP;
				varPercentFixAmt = GlobalConstant.CancellationChargesSellerFaultVariableFixedAmt;
				varPercentPCC = GlobalConstant.CancellationChargesSellerFaultVariablePercentPCC;
				chargesType = nrnReturnConfig.getCanCharSFARTDType();
				shippingfee = nrnReturnConfig.isCanCharSFShipFee();
				servicetax = nrnReturnConfig.isCanCharSFSerTax();
				fixedfee = nrnReturnConfig.isCanCharSFFF();
				paycollcharges = nrnReturnConfig.isCanCharSFPCC();
				isRevShippingFee = nrnReturnConfig.isCanCharSFARTDRevShipFee();
			} else if (faultType.equals(GlobalConstant.SellerFaultString)
					&& cancelType
							.equals(GlobalConstant.SFCancellationBeforeRTDString)) {
				fixedAmount = GlobalConstant.CancellationChargesSellerFaultBRTDFixedAmount;
				varPercentSP = GlobalConstant.CancellationChargesSellerFaultBRTDVariablePercentSP;
				varPercentFixAmt = GlobalConstant.CancellationChargesSellerFaultBRTDVariableFixedAmt;
				varPercentPCC = GlobalConstant.CancellationChargesSellerFaultBRTDVariablePercentPCC;
				chargesType = nrnReturnConfig.getCanCharSFBFRTDType();
				shippingfee = nrnReturnConfig.isCanCharSFBRTDShipFee();
				servicetax = nrnReturnConfig.isCanCharSFBRTDSerTax();
				fixedfee = nrnReturnConfig.isCanCharSFBRTDFF();
				paycollcharges = nrnReturnConfig.isCanCharSFBRTDPCC();
				isRevShippingFee = nrnReturnConfig.isCanCharSFBRTDRevShipFee();
			} else {
				fixedAmount = GlobalConstant.CancellationChargesBuyerReturnFixedAmount;
				varPercentSP = GlobalConstant.CancellationChargesBuyerReturnVariablePercentSP;
				varPercentFixAmt = GlobalConstant.CancellationChargesBuyerReturnVariableFixedAmt;
				chargesType = nrnReturnConfig.getCanCharBRType();
				shippingfee = nrnReturnConfig.isCanCharBRShipFee();
				servicetax = nrnReturnConfig.isCanCharBRSerTax();
				fixedfee = nrnReturnConfig.isCanCharBRFF();
				paycollcharges = nrnReturnConfig.isCanCharBRPCC();
			}

			break;
		case "replacementCharges":
			if (faultType.equals(GlobalConstant.SellerFaultString)) {
				fixedAmount = GlobalConstant.ReplacementChargesSellerFaultFixedAmount;
				varPercentSP = GlobalConstant.ReplacementChargesSellerFaultVariablePercentSP;
				varPercentFixAmt = GlobalConstant.ReplacementChargesSellerFaultVariableFixedAmt;
				varPercentPCC = GlobalConstant.ReplacementChargesSellerFaultVariablePercentPCC;
				chargesType = nrnReturnConfig.getRepCharSFType();
				shippingfee = nrnReturnConfig.isRepCharSFShipFee();
				servicetax = nrnReturnConfig.isRepCharSFSerTax();
				fixedfee = nrnReturnConfig.isRepCharSFFF();
				paycollcharges = nrnReturnConfig.isRepCharSFPCC();
				isRevShippingFee = nrnReturnConfig.isRepCharSFRevShipFee();
			} else {
				fixedAmount = GlobalConstant.ReplacementChargesBuyerReturnFixedAmount;
				varPercentSP = GlobalConstant.ReplacementChargesBuyerReturnVariablePercentSP;
				varPercentFixAmt = GlobalConstant.ReplacementChargesBuyerReturnVariableFixedAmt;
				chargesType = nrnReturnConfig.getRepCharBRType();
				shippingfee = nrnReturnConfig.isRepCharBRShipFee();
				servicetax = nrnReturnConfig.isRepCharBRSerTax();
				fixedfee = nrnReturnConfig.isRepCharBRFF();
				paycollcharges = nrnReturnConfig.isRepCharBRPCC();
			}

			break;
		case "partialDeliveryCharges":

			if (faultType.equals(GlobalConstant.SellerFaultString)) {
				fixedAmount = GlobalConstant.PartialDelChargesSellerFaultFixedAmount;
				varPercentSP = GlobalConstant.PartialDelChargesSellerFaultVariablePercentSP;
				varPercentFixAmt = GlobalConstant.PartialDelChargesSellerFaultVariableFixedAmt;
				varPercentPCC = GlobalConstant.PartialDelChargesSellerFaultVariablePercentPCC;
				chargesType = nrnReturnConfig.getPDCharSFType();
				shippingfee = nrnReturnConfig.isPDCharSFShipFee();
				servicetax = nrnReturnConfig.isPDCharSFSerTax();
				fixedfee = nrnReturnConfig.isPDCharSFFF();
				paycollcharges = nrnReturnConfig.isPDCharSFPCC();
				isRevShippingFee = nrnReturnConfig.isPDCharSFRevShipFee();
			} else {
				fixedAmount = GlobalConstant.PartialDelChargesBuyerReturnFixedAmount;
				varPercentSP = GlobalConstant.PartialDelChargesBuyerReturnVariablePercentSP;
				varPercentFixAmt = GlobalConstant.PartialDelChargesBuyerReturnVariableFixedAmt;
				chargesType = nrnReturnConfig.getPDCharBRType();
				shippingfee = nrnReturnConfig.isPDCharBRShipFee();
				servicetax = nrnReturnConfig.isPDCharBRSerTax();
				fixedfee = nrnReturnConfig.isPDCharBRFF();
				paycollcharges = nrnReturnConfig.isPDCharBRPCC();
			}
			break;

		default:
			break;
		}

		if (chargesType.equalsIgnoreCase(GlobalConstant.fixedString)) {
			log.debug(" Fixed amount in return : " + fixedAmount);
			totalcharge = totalcharge
					+ (chargesMap.containsKey(fixedAmount) ? chargesMap
							.get(fixedAmount) : 0);
		} else if (chargesType.equalsIgnoreCase(GlobalConstant.variableString)) {
			log.debug(" Variable amount in Return : " + varPercentSP
					+ " varPercentFixAmt :" + varPercentFixAmt
					+ " varPercentPCC : " + varPercentPCC + "shipping fee : "
					+ shippingfee + " fixedfee  :" + fixedfee
					+ " paycollcharges " + paycollcharges);
			totalcharge = totalcharge
					+ (float) (chargesMap.containsKey(varPercentSP) ? (chargesMap
							.get(varPercentSP)
							* (order.getOrderSP() / order.getQuantity()) / 100)
							: 0);
			totalcharge = totalcharge
					+ (chargesMap.containsKey(varPercentFixAmt) ? chargesMap
							.get(varPercentFixAmt) : 0);
			totalcharge = totalcharge
					+ (float) (shippingfee ? order.getShippingCharges() : 0);
			totalcharge = totalcharge
					+ (float) (fixedfee ? order.getFixedfee() : 0);

			totalcharge = totalcharge
					+ (float) (paycollcharges ? order.getPccAmount() : 0);

			totalcharge = totalcharge
					+ (float) (chargesMap.containsKey(varPercentPCC) ? (chargesMap
							.get(varPercentPCC) * order.getPartnerCommission() / 100)
							: 0);

		}

		if (isRevShippingFee) {
			String revShippingType = nrnReturnConfig.getRevShippingFeeType();
			switch (revShippingType) {
			case "revShipFeePCC":

				revShippingFee = (float) (chargesMap
						.containsKey(GlobalConstant.ReverseShippingFeePercentShipFee) ? (chargesMap
						.get(GlobalConstant.ReverseShippingFeePercentShipFee)
						* order.getShippingCharges() / 100) : 0);
				break;
			case "revShipFeeNA":

				revShippingFee = 0;
				break;
			case "revShipFeeGRT":

				float revShipMarketFee = (float) (chargesMap
						.containsKey(GlobalConstant.ReverseShippingFeePercentMarketFee) ? ((chargesMap
						.get(GlobalConstant.ReverseShippingFeePercentMarketFee) / 100) * order
						.getPartnerCommission())
						: 0);
				if (chargesMap.get(GlobalConstant.ReverseShippingFeeFlatAmt) > revShipMarketFee) {
					revShippingFee = chargesMap
							.get(GlobalConstant.ReverseShippingFeeFlatAmt);
				} else {
					revShippingFee = revShipMarketFee;
				}
				break;
			case "revShipFeeFF":

				revShippingFee = (float) (chargesMap
						.get(GlobalConstant.ReverseShippingFeeFixedAmt));
				break;
			case "revShipFeeShipFee":

				revShippingFee = (float) order.getShippingCharges();
				break;

			case "revShipFeeVar":

				Product product = productService.getProduct(
						order.getProductSkuCode(), sellerId);
				float deadWeight = (float) (chargesMap
						.get(GlobalConstant.ReverseShippingFeeDeadWeightMinWeight));
				if (deadWeight < product.getDeadWeight()) {
					deadWeight = product.getDeadWeight();
				}
				float revShippingFeeDW = (float) (Math
						.ceil(deadWeight
								/ (chargesMap
										.get(GlobalConstant.ReverseShippingFeeDeadWeightPerWeight))))
						* (chargesMap
								.get(GlobalConstant.ReverseShippingFeeDeadWeightAmt));

				float volumeWeight = (float) (chargesMap
						.get(GlobalConstant.ReverseShippingFeeVolumeWeightMinWeight));
				if (volumeWeight < product.getVolWeight()) {
					volumeWeight = product.getVolWeight();
				}
				float revShippingFeeVW = (float) (Math
						.ceil(volumeWeight
								/ (chargesMap
										.get(GlobalConstant.ReverseShippingFeeVolumeWeightPerWeight))))
						* (chargesMap
								.get(GlobalConstant.ReverseShippingFeeVolumeWeightAmt));

				if (revShippingFeeDW > revShippingFeeVW) {
					revShippingFee = revShippingFeeDW;
				} else {
					revShippingFee = revShippingFeeVW;
				}

			default:
				break;
			}
		}

		totalcharge = totalcharge + revShippingFee;

		/*float serviceTax = chargesMap.containsKey("serviceTax") ? chargesMap
				.get("serviceTax") : 0;*/
		
		float serviceTax = (float) dataConfig.getServiceTax();

		log.debug(" Total return charge calculated : " + totalcharge
				+ "Reverse shiping fee : " + revShippingFee
				+ " Service tax applied  : " + serviceTax);
		if (serviceTax > 0) {
			totalcharge = totalcharge + (totalcharge * serviceTax) / 100;
		}
		log.info("$$$ calculateReturnCharges for Events End $$$");
		return totalcharge;
	}

	@Override
	public Order generateConsolidatedOrder(List<Order> orderlist, int sellerId)
			throws CustomException {

		log.info("$$$ generateConsolidatedOrder Start $$$");
		int quantity = 0;
		double orderSP = 0;
		double eossValue = 0;
		double netRate = 0;
		double taxValue = 0;
		double grossPR = 0;
		double grossProfit = 0;
		double poPrice = 0;
		double discount = 0;
		double taxSP = 0;
		double partnerCommission = 0;

		Order consolidatedOrder = new Order();
		consolidatedOrder.setPoOrder(true);

		consolidatedOrder.setPcName(orderlist.get(0).getPcName());
		consolidatedOrder.setSubOrderID(orderlist.get(0).getSubOrderID());
		consolidatedOrder.setChannelOrderID(orderlist.get(0)
				.getChannelOrderID());
		consolidatedOrder.setProductSkuCode(orderlist.size() + " SKUs");
		consolidatedOrder.setInvoiceID(orderlist.get(0).getInvoiceID());
		consolidatedOrder.setShippedDate(orderlist.get(0).getShippedDate());
		consolidatedOrder.setOrderDate(orderlist.get(0).getOrderDate());
		consolidatedOrder.setPaymentDueDate(orderlist.get(0)
				.getPaymentDueDate());
		consolidatedOrder.setSealNo(orderlist.get(0).getSealNo());

		Seller seller = null;
		Session session = null;
		Partner partner = null;
		try {

			session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", sellerId));
			criteria.createAlias("partners", "partner",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("partner.pcName",
							consolidatedOrder.getPcName()).ignoreCase())
					.setResultTransformer(
							CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			seller = (Seller) criteria.list().get(0);

			for (Order order : orderlist) {
				quantity += order.getQuantity();
				orderSP += order.getOrderSP();
				poPrice += order.getPoPrice();
				eossValue += order.getEossValue();
				netRate += order.getNetRate();
				taxValue += order.getOrderTax().getTax();
				grossPR += order.getPr();
				grossProfit += order.getGrossProfit();
				discount += order.getDiscount();
				taxSP += order.getOrderTax().getTaxToReturn();
				partnerCommission += order.getPartnerCommission();
			}

			if (seller.getPartners() != null
					&& seller.getPartners().size() != 0) {
				partner = seller.getPartners().get(0);
			}

			/* populating derived values of order */
			consolidatedOrder.setStatus("Shipped");
			consolidatedOrder.setQuantity(quantity);
			consolidatedOrder.setOrderSP(orderSP);
			consolidatedOrder.setPoPrice(poPrice);

			consolidatedOrder.setEossValue(eossValue);

			consolidatedOrder.setNetRate(netRate);

			consolidatedOrder.setOrderTax(new OrderTax());
			consolidatedOrder.getOrderTax().setTax(taxValue);

			consolidatedOrder.setTotalAmountRecieved(consolidatedOrder
					.getNetRate());

			consolidatedOrder.setPartnerCommission(partnerCommission);
			consolidatedOrder.setDiscount(discount);
			consolidatedOrder.getOrderTax().setTaxSP(taxSP);

			consolidatedOrder.setFinalStatus("In Process");
			// Set Order Timeline
			OrderTimeline timeline = new OrderTimeline();
			// populating tax related values of order
			log.debug(" Tax before pr:"
					+ consolidatedOrder.getOrderTax().getTax());
			consolidatedOrder.setPr(grossPR);

			// Adding order to the Partner
			if (partner.getOrders() != null
					&& consolidatedOrder.getOrderId() == 0) {
				partner.getOrders().add(consolidatedOrder);
			}
			// Setting Gross Profit for Order
			consolidatedOrder.setGrossProfit(grossProfit);

			if (consolidatedOrder.getOrderId() != 0) {

				// Code for order timeline
				timeline.setEventDate(new Date());
				timeline.setEvent(" Order Edited");
				consolidatedOrder.getOrderTimeline().add(timeline);
				consolidatedOrder.setLastActivityOnOrder(new Date());
				session.merge(consolidatedOrder);
			} else {
				log.debug(" ****************Saving new  order delivery date :"
						+ consolidatedOrder.getDeliveryDate());
				// Code for order timeline
				timeline.setEvent("Order Created");
				consolidatedOrder.setLastActivityOnOrder(new Date());
				timeline.setEventDate(new Date());
				consolidatedOrder.getOrderTimeline().add(timeline);
				consolidatedOrder.setSeller(seller);
				seller.getOrders().add(consolidatedOrder);
				session.saveOrUpdate(partner);
				session.saveOrUpdate(seller);

			}
			session.getTransaction().commit();
			log.info("$$$ generateConsolidatedOrder ends $$$");
			return consolidatedOrder;
		} catch (Exception e) {
			log.debug("Inside exception in add order "
					+ e.getLocalizedMessage() + " message: " + e.getMessage());
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			log.info("Error : " + GlobalConstant.addOrderError);
			log.info("Error : " + GlobalConstant.addOrderErrorCode);
			throw new CustomException(GlobalConstant.addOrderError, new Date(),
					1, GlobalConstant.addOrderErrorCode, e);
		} finally {
			session.close();
		}

	}

	@Override
	public void updatePOOrders(List<Order> orderlist, Order consolidatedOrder)
			throws CustomException {

		log.info("$$$ updatePOOrders Starts : OrderDaoImpl $$$");
		Session session = null;
		try {

			session = sessionFactory.openSession();
			session.beginTransaction();
			for (Order order : orderlist) {
				order.setConsolidatedOrderID(consolidatedOrder);
				session.merge(order);
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			log.debug("Inside exception in add order "
					+ e.getLocalizedMessage() + " message: " + e.getMessage());
			e.printStackTrace();
			log.error("Failed!", e);
			log.info("Error : " + GlobalConstant.addOrderError);
			log.info("Error : " + GlobalConstant.addOrderErrorCode);
			throw new CustomException(GlobalConstant.addOrderError, new Date(),
					1, GlobalConstant.addOrderErrorCode, e);
		} finally {
			session.close();
		}
		log.info("$$$ updatePOOrders ends : OrderDaoImpl $$$");
	}

	@Override
	public Order findPOOrder(String poID, String invoiceID,
			String channelSkuRef, int sellerId) throws CustomException {

		log.info("$$$ findPOOrder Starts : OrderDaoImpl $$$");
		Seller seller = null;
		List<Order> orderlist = null;
		Order poOrder = null;

		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", sellerId));
			criteria.createAlias("orders", "order",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("order.subOrderID", poID).ignoreCase())
					.add(Restrictions.eq("order.invoiceID", invoiceID)
							.ignoreCase())
					.add(Restrictions.eq("order.productSkuCode", channelSkuRef)
							.ignoreCase())
					.add(Restrictions.eq("order.poOrder", true))
					.addOrder(
							org.hibernate.criterion.Order
									.desc("order.lastActivityOnOrder"))
					.setResultTransformer(
							CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			if (criteria.list().size() != 0) {
				seller = (Seller) criteria.list().get(0);
				if (seller == null)
					log.debug("Null seller....");
				orderlist = seller.getOrders();
				if (orderlist != null && orderlist.size() != 0) {
					for (Order order : orderlist) {
						Hibernate.initialize(order.getOrderTimeline());
					}
				}
			}
			session.getTransaction().commit();
			session.close();
			if (orderlist != null && orderlist.size() != 0) {
				poOrder = orderlist.get(0);
			}
			log.info("$$$ findPOOrder ends : OrderDaoImpl $$$");
			return poOrder;

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.findOrdersError,
					new Date(), 2, GlobalConstant.findOrdersErrorcode, e);

		}
	}

	@Override
	public GatePass addGatePass(ProductConfig productConfig, GatePass gatepass,
			int sellerId) throws CustomException {

		log.info("$$$ addGatePass Starts : OrderDaoImpl $$$");
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();

			gatepass.setPartnerCommission(productConfig.getCommisionAmt()
					* gatepass.getQuantity());
			gatepass.setTaxSPAmt(productConfig.getTaxSpAmt()
					* gatepass.getQuantity());

			double eossValue = 0;
			if (gatepass.getPcName().equalsIgnoreCase(GlobalConstant.PCMYNTRA)) {
				eossValue = (productConfig.getSuggestedPOPrice() * productConfig
						.getDiscount()) / 100;
			}
			double grossNR = productConfig.getSuggestedPOPrice() - eossValue;

			gatepass.setNetNR(grossNR * gatepass.getQuantity());
			gatepass.setTaxPOAmt(grossNR
					- (grossNR * 100 / (100 + productConfig.getTaxPo())));
			gatepass.setRetutnPR(grossNR - gatepass.getTaxPOAmt());
			gatepass.setNetPR(gatepass.getRetutnPR() * gatepass.getQuantity());

			gatepass.setGrossProfit(gatepass.getNetPR()
					- (productConfig.getProductPrice() * gatepass.getQuantity()));

			productService.updateInventory(productConfig.getProductSkuCode(),
					0, gatepass.getQuantity(), 0, false, sellerId,
					gatepass.getReturnDate());

			TaxDetail taxDetails = new TaxDetail();
			taxDetails.setBalanceRemaining(-(gatepass.getTaxPOAmt())
					* gatepass.getQuantity());
			taxDetails.setParticular(productConfig.getTaxPoCategory());
			taxDetails.setUploadDate(gatepass.getReturnDate());
			taxDetailService.addMonthlyTaxDetail(session, taxDetails, sellerId);

			session.saveOrUpdate(gatepass);
			session.getTransaction().commit();

		} catch (Exception e) {

			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.addReturnOrderError,
					new Date(), 1, GlobalConstant.addReturnOrderErrorCode, e);
		} finally {
			session.close();
		}
		log.info("$$$ addGatePass Ends : OrderDaoImpl $$$");
		return gatepass;
	}

	@Override
	public List<Order> listGatePasses(int sellerId, int pageNo)
			throws CustomException {

		log.info("$$$ listGatePasses Starts : OrderDaoImpl $$$");
		List<Order> returnlist = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Order.class);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.eq("poOrder", true))
					.add(Restrictions.isNull("consolidatedOrder"))
					.add(Restrictions.isNotNull("orderReturnOrRTO"));

			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			criteria.addOrder(org.hibernate.criterion.Order
					.desc("lastActivityOnOrder"));
			criteria.setFirstResult(pageNo * pageSize);
			criteria.setMaxResults(pageSize);
			returnlist = criteria.list();
			for (Order order : returnlist) {
				Hibernate.initialize(order.getOrderReturnOrRTO());
				Hibernate.initialize(order.getOrderTax());
				Hibernate.initialize(order.getOrderPayment());
				Hibernate.initialize(order.getOrderTimeline());
				Hibernate.initialize(order.getCustomer());
			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.listOrdersError,
					new Date(), 3, GlobalConstant.listOrdersErrorCode, e);
		}
		log.info("$$$ listGatePasses Ends : OrderDaoImpl $$$");
		return returnlist;
	}

	@Override
	public List<Order> listDisputedGatePasses(int sellerId, int pageNo)
			throws CustomException {

		log.info("$$$ listDisputedGatePasses Starts : OrderDaoImpl $$$");
		List<Order> returnlist = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Order.class);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.eq("poOrder", true))
					.add(Restrictions.isNull("consolidatedOrder"))
					.add(Restrictions.isNull("shippedDate"))
					.add(Restrictions.isNull("orderReturnOrRTO"));

			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			criteria.addOrder(org.hibernate.criterion.Order
					.desc("lastActivityOnOrder"));
			criteria.setFirstResult(pageNo * pageSize);
			criteria.setMaxResults(pageSize);
			returnlist = criteria.list();
			for (Order order : returnlist) {
				Hibernate.initialize(order.getOrderReturnOrRTO());
				Hibernate.initialize(order.getOrderTax());
				Hibernate.initialize(order.getOrderPayment());
				Hibernate.initialize(order.getOrderTimeline());
				Hibernate.initialize(order.getCustomer());
			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.listOrdersError,
					new Date(), 3, GlobalConstant.listOrdersErrorCode, e);
		}
		log.info("$$$ listGatePasses Ends : OrderDaoImpl $$$");
		return returnlist;
	}

	@Override
	public OrderRTOorReturn generateConsolidatedReturn(
			List<GatePass> gatepasslist, int sellerId) throws CustomException {

		log.info("$$$ generateConsolidatedReturn Starts : OrderDaoImpl $$$");
		int quantity = 0;
		double totalReturnCharges = 0;
		double eossValue = 0;
		double netRate = 0;
		double taxPO = 0;
		double grossPR = 0;
		double grossProfit = 0;
		double discount = 0;
		double taxSP = 0;
		double partnerCommission = 0;
		double orderSP = 0;

		OrderRTOorReturn consolidateReturn = new OrderRTOorReturn();
		Order consolidatedOrder = new Order();

		consolidatedOrder.setPoOrder(true);
		consolidatedOrder.setOrderDate(gatepasslist.get(0).getReturnDate());

		consolidatedOrder.setPcName(gatepasslist.get(0).getPcName());
		consolidatedOrder.setSubOrderID(gatepasslist.get(0).getGatepassId());
		consolidatedOrder
				.setChannelOrderID(gatepasslist.get(0).getGatepassId());
		consolidatedOrder.setProductSkuCode(gatepasslist.size() + " SKUs");
		consolidatedOrder.setInvoiceID(gatepasslist.get(0).getInvoiceID());

		consolidateReturn.setReturnDate(gatepasslist.get(0).getReturnDate());
		consolidatedOrder.setDeliveryDate(gatepasslist.get(0).getReturnDate());
		consolidatedOrder.setShippedDate(gatepasslist.get(0).getReturnDate());

		Seller seller = null;
		Session session = null;
		Partner partner = null;
		List<ProductConfig> productConfigs = null;
		ProductConfig productConfig = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", sellerId));
			criteria.createAlias("partners", "partner",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("partner.pcName",
							consolidatedOrder.getPcName()).ignoreCase())
					.setResultTransformer(
							CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			seller = (Seller) criteria.list().get(0);

			for (GatePass gatepass : gatepasslist) {

				productConfigs = productService.getProductConfig(
						gatepass.getChannelSkuRef(), gatepass.getPcName(),
						sellerId);
				if (productConfigs != null && productConfigs.size() == 1) {
					productConfig = productConfigs.get(0);
					quantity += gatepass.getQuantity();
					totalReturnCharges += gatepass.getTotalReturnCharges();
					if (gatepass.getPcName().equalsIgnoreCase(
							GlobalConstant.PCMYNTRA)) {
						eossValue += ((productConfig.getSuggestedPOPrice() * productConfig
								.getDiscount()) / 100) * gatepass.getQuantity();
					}
					netRate += gatepass.getNetNR();
					taxPO += (gatepass.getTaxPOAmt() * gatepass.getQuantity());
					grossPR += gatepass.getNetPR();
					grossProfit += gatepass.getGrossProfit();
					partnerCommission += productConfig.getCommisionAmt()
							* gatepass.getQuantity();
					discount += ((productConfig.getSuggestedPOPrice() * productConfig
							.getDiscount()) / 100) * gatepass.getQuantity();
					taxSP += productConfig.getTaxSpAmt()
							* gatepass.getQuantity();
					orderSP += productConfig.getProductPrice()
							* gatepass.getQuantity();
				}
			}

			if (seller.getPartners() != null
					&& seller.getPartners().size() != 0) {
				partner = seller.getPartners().get(0);
			}

			Date reconciledate = getreconciledate(consolidatedOrder, partner,
					consolidatedOrder.getOrderDate());
			if (reconciledate != null) {
				consolidatedOrder.setPaymentDueDate(reconciledate);
			}
			consolidatedOrder.setDeliveryDate(null);
			consolidatedOrder.setShippedDate(null);

			/* populating derived values of order */
			/* populating derived values of order */
			consolidatedOrder.setStatus("Return Recieved");

			consolidateReturn.setReturnorrtoQty(quantity);
			consolidateReturn
					.setReturnOrRTOChargestoBeDeducted(totalReturnCharges);
			consolidateReturn.setNetNR(netRate);
			consolidateReturn.setTaxPOAmt(taxPO);
			consolidateReturn.setNetPR(grossPR);
			consolidateReturn.setGrossProfit(grossProfit);

			consolidatedOrder.setEossValue(eossValue);

			// consolidatedOrder.setQuantity(quantity);
			// consolidatedOrder.setPr(grossPR);
			// consolidatedOrder.setPr(grossPR);
			consolidatedOrder.setPoPrice(totalReturnCharges);
			consolidatedOrder.setOrderSP(orderSP);

			consolidatedOrder.setOrderTax(new OrderTax());
			consolidatedOrder.getOrderTax().setTaxToReturn(taxPO);
			consolidatedOrder.getOrderTax().setTaxSP(taxSP);

			consolidatedOrder.setTotalAmountRecieved(netRate);
			consolidatedOrder.setPartnerCommission(partnerCommission);
			consolidatedOrder.setDiscount(discount);
			consolidatedOrder.setFinalStatus("In Process");
			// Set Order Timeline
			OrderTimeline timeline = new OrderTimeline();
			// populating tax related values of order
			log.debug(" Tax before pr:"
					+ consolidatedOrder.getOrderTax().getTax());
			consolidatedOrder.setPr(grossPR);

			// Adding order to the Partner
			if (partner.getOrders() != null
					&& consolidatedOrder.getOrderId() == 0) {
				partner.getOrders().add(consolidatedOrder);
			}
			// Setting Gross Profit for Order
			consolidatedOrder.setGrossProfit(grossProfit);

			if (consolidatedOrder.getOrderId() != 0) {
				// Code for order timeline
				timeline.setEventDate(new Date());
				timeline.setEvent(" Return Recieved");
				consolidatedOrder.getOrderTimeline().add(timeline);
				consolidatedOrder.setLastActivityOnOrder(new Date());
				session.merge(consolidatedOrder);
			} else {
				log.debug(" ****************Saving new  order delivery date :"
						+ consolidatedOrder.getDeliveryDate());
				// Code for order timeline
				timeline.setEvent("Return Recieved");
				consolidatedOrder.setLastActivityOnOrder(new Date());
				timeline.setEventDate(new Date());
				consolidatedOrder.getOrderTimeline().add(timeline);
				consolidatedOrder.setSeller(seller);
				consolidatedOrder.setOrderReturnOrRTO(consolidateReturn);
				seller.getOrders().add(consolidatedOrder);
				session.saveOrUpdate(partner);
				session.saveOrUpdate(seller);

			}
			session.getTransaction().commit();

		} catch (Exception e) {
			log.debug("Inside exception in generateConsolidatedReturn "
					+ e.getLocalizedMessage() + " message: " + e.getMessage());
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			log.info("Error : " + GlobalConstant.addOrderError);
			log.info("Error : " + GlobalConstant.addOrderErrorCode);
			throw new CustomException(GlobalConstant.addOrderError, new Date(),
					1, GlobalConstant.addOrderErrorCode, e);
		} finally {
			session.close();
		}
		log.info("$$$ generateConsolidatedReturn Ends : OrderDaoImpl $$$");
		return consolidateReturn;
	}

	@Override
	public void updateGatePasses(List<GatePass> gatepasslist,
			OrderRTOorReturn consolidatedReturn) throws CustomException {

		log.info("$$$ updateGatePasses Starts : OrderDaoImpl $$$");
		Session session = null;
		try {

			session = sessionFactory.openSession();
			session.beginTransaction();
			for (GatePass gatepass : gatepasslist) {
				gatepass.setConsolidatedReturn(consolidatedReturn);
				session.merge(gatepass);
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			log.debug("Inside exception in update gatepass "
					+ e.getLocalizedMessage() + " message: " + e.getMessage());
			e.printStackTrace();
			log.error("Failed!", e);
			throw new CustomException(GlobalConstant.addOrderError, new Date(),
					1, GlobalConstant.addOrderErrorCode, e);
		} finally {
			session.close();
		}
		log.info("$$$ updateGatePasses Ends : OrderDaoImpl $$$");
	}

	@Override
	public Order findConsolidatedPO(String column, String value, int sellerId)
			throws CustomException {

		log.info("$$$ findConsolidatedPO Starts : OrderDaoImpl $$$");
		Seller seller = null;
		List<Order> orderlist = null;
		Order poOrder = null;

		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", sellerId));
			criteria.createAlias("orders", "order",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("order." + column, value).ignoreCase())
					.add(Restrictions.eq("order.poOrder", true))
					.add(Restrictions.isNull("order.consolidatedOrder"))
					.addOrder(
							org.hibernate.criterion.Order
									.desc("order.lastActivityOnOrder"))
					.setResultTransformer(
							CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			if (column.equalsIgnoreCase("invoiceID"))
				criteria.add(Restrictions.isNull("order.orderReturnOrRTO"));
			if (criteria.list().size() != 0) {
				seller = (Seller) criteria.list().get(0);
				if (seller == null)
					System.out.println("Null seller");
				orderlist = seller.getOrders();
				if (orderlist != null && orderlist.size() != 0) {
					for (Order order : orderlist) {
						Hibernate.initialize(order.getOrderTimeline());
					}
				}
			}
			session.getTransaction().commit();
			session.close();
			if (orderlist != null && orderlist.size() != 0) {
				poOrder = orderlist.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.findOrdersError,
					new Date(), 2, GlobalConstant.findOrdersErrorcode, e);

		}

		log.info("$$$ findConsolidatedPO Ends : OrderDaoImpl $$$");
		return poOrder;
	}

	@Override
	public List<Order> findPOOrdersbyDate(String column, Date startDate,
			Date endDate, int sellerId) throws CustomException {

		log.info("*** findPOOrdersbyDate starts : OrderDaoImpl ***");
		String searchString = null;
		searchString = "order." + column;
		Seller seller = null;
		List<Order> orderlist = null;
		List temp = null;

		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", sellerId));
			criteria.createAlias("orders", "order",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.between(searchString, startDate, endDate))
					.add(Restrictions.eq("order.poOrder", true))
					.setResultTransformer(
							CriteriaSpecification.DISTINCT_ROOT_ENTITY);

			criteria.add(Restrictions.isNull("order.consolidatedOrder"));

			temp = criteria.list();
			if (temp != null && temp.size() != 0) {
				seller = (Seller) temp.get(0);
				orderlist = seller.getOrders();
			}

			if (orderlist != null && orderlist.size() != 0) {
				for (Order order : orderlist) {
					Hibernate.initialize(order.getOrderTimeline());
				}
			}
			if (orderlist == null || orderlist.size() == 0)
				System.out.println(" orderlist is null");
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.findOrdersbyDateError,
					new Date(), 2, GlobalConstant.findOrdersbyDateErrorCode, e);

		}
		log.info("*** findOrdersbyDate ends : OrderDaoImpl ***");
		return orderlist;
	}

	@Override
	public List<PoPaymentDetailsBean> getPOPaymentDetails(int sellerId,
			String year) {
		List<PoPaymentDetailsBean> poPaymentDetailsList = new ArrayList<PoPaymentDetailsBean>();

		log.info("***getPOPaymentDetails starts***");
		try {

			DateFormatSymbols dfs = new DateFormatSymbols();
			String[] months = dfs.getMonths();

			List<Order> poOrderlist = new ArrayList<Order>();

			DateFormat format = new SimpleDateFormat("d MMMM yyyy",
					Locale.ENGLISH);

			String dateString = "1 January " + year;
			Date startDate = format.parse(dateString);

			dateString = "31 December " + year;
			Date endDate = format.parse(dateString);

			poOrderlist = findPOOrdersbyDate("orderDate", startDate, endDate,
					sellerId);

			for (int month = 0; month < 13; month++) {
				PoPaymentDetailsBean poPaymentBean = new PoPaymentDetailsBean();
				poPaymentDetailsList.add(poPaymentBean);
			}

			for (Order poOrder : poOrderlist) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(poOrder.getOrderDate());

				PoPaymentDetailsBean poPaymentBean = poPaymentDetailsList
						.get(cal.get(Calendar.MONTH) - 1);
				PoPaymentDetailsBean poPaymentBeanOrderPay;

				poPaymentBean.setPaymentDetail(months[cal.get(Calendar.MONTH)]
						+ " " + year);

				if (poOrder.getOrderReturnOrRTO() == null) {
					poPaymentBean.setDebits(poPaymentBean.getDebits()
							+ poOrder.getPoPrice());

					poPaymentBean.setEoss(poPaymentBean.getEoss()
							+ poOrder.getEossValue());

				} else {
					poPaymentBean.setGatepass(poPaymentBean.getGatepass()
							+ poOrder.getOrderReturnOrRTO()
									.getReturnOrRTOChargestoBeDeducted());

					poPaymentBean.setEoss(poPaymentBean.getEoss()
							- poOrder.getEossValue());
				}

				if (poOrder.getOrderPayment() != null) {

					Calendar calOrderPay = Calendar.getInstance();
					calOrderPay.setTime(poOrder.getOrderPayment()
							.getDateofPayment());

					poPaymentBeanOrderPay = poPaymentDetailsList
							.get(calOrderPay.get(Calendar.MONTH) - 1);
					poPaymentBeanOrderPay.setPaymentDetail(months[calOrderPay
							.get(Calendar.MONTH)] + " " + year);

					poPaymentBeanOrderPay.setPayments(poPaymentBeanOrderPay
							.getPayments()
							+ poOrder.getOrderPayment().getNetPaymentResult());

				}
			}

			Iterator<PoPaymentDetailsBean> poIterator = poPaymentDetailsList
					.iterator();
			double prevClosingBal = 0;
			PoPaymentDetailsBean totalBean = poPaymentDetailsList.get(12);
			totalBean.setPaymentDetail("Total :");

			while (poIterator.hasNext()) {

				PoPaymentDetailsBean poPaymentBean = poIterator.next();

				if (poPaymentBean.getPaymentDetail() == null
						|| "".equals(poPaymentBean.getPaymentDetail().trim())) {
					poIterator.remove();
				} else {
					if (!"Total :".equals(poPaymentBean.getPaymentDetail()
							.trim())) {
						poPaymentBean.setClosingBal(prevClosingBal
								+ poPaymentBean.getDebits()
								- poPaymentBean.getGatepass()
								- poPaymentBean.getPayments()
								- poPaymentBean.getManualCharges()
								- poPaymentBean.getEoss());

						prevClosingBal = poPaymentBean.getClosingBal();

						totalBean.setDebits(totalBean.getDebits()
								+ poPaymentBean.getDebits());
						totalBean.setGatepass(totalBean.getGatepass()
								+ poPaymentBean.getGatepass());
						totalBean.setPayments(totalBean.getPayments()
								+ poPaymentBean.getPayments());
						totalBean.setManualCharges(totalBean.getManualCharges()
								+ poPaymentBean.getManualCharges());
						totalBean.setEoss(totalBean.getEoss()
								+ poPaymentBean.getEoss());
					}
				}
			}
			totalBean.setClosingBal(prevClosingBal);
		} catch (Exception e) {
			log.debug("Inside exception  " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		log.info("***getPOPaymentDetails ends***");
		return poPaymentDetailsList;

	}

	@Override
	public boolean isPOOrderUploaded(String poId, String invoiceId, int sellerId)
			throws CustomException {
		log.info("*** isPOOrderUploaded starts ***");
		List<Order> returnList = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Order.class);
			criteria.add(Restrictions.eq("subOrderID", poId));
			criteria.add(Restrictions.eq("invoiceID", invoiceId));
			criteria.add(Restrictions.eq("poOrder", true));
			criteria.add(Restrictions.isNotNull("consolidatedOrder.orderId"));
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN).add(
					Restrictions.eq("seller.id", sellerId));

			returnList = criteria.list();
			if (returnList != null && returnList.size() != 0
					&& returnList.get(0) != null) {

				return true;
			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.getOrderError, new Date(),
					3, GlobalConstant.getOrderErrorCode, e);

		}
		log.info("*** isPOOrderUploaded ends ***");
		return false;
	}

	@Override
	public int mpOrdersCount(int sellerId) {

		int noOfMpOrders = 0;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Order.class);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.eq("poOrder", false));
			criteria.setProjection(Projections.rowCount());

			if (criteria != null)
				noOfMpOrders = (int) criteria.uniqueResult();
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(
					"Failed! by sellerId : " + sellerId + " In MPORDER Count",
					e);
		}
		return noOfMpOrders;
	}

	@Override
	public int poOrdersCount(int sellerId) {
		int noOfPoOrders = 0;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Order.class);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.eq("poOrder", true))
					.add(Restrictions.isNull("consolidatedOrder"))
					.add(Restrictions.disjunction()
							.add(Restrictions.isNotNull("shippedDate"))
							.add(Restrictions.isNotNull("orderReturnOrRTO")));
			criteria.setProjection(Projections.rowCount());
			if (criteria != null)
				noOfPoOrders = (int) criteria.uniqueResult();
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(
					"Failed! by sellerId : " + sellerId + " In POORDER Count",
					e);
		}
		return noOfPoOrders;
	}

	@Override
	public List<String> listOrderIds(String OrderCriteria, int sellerId) {
		List<String> idsList = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Order.class);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN).add(
					Restrictions.eq("seller.id", sellerId));
			criteria.setProjection(Projections.property(OrderCriteria));
			idsList = criteria.list();

		} catch (Exception e) {
			log.error("Failed by Seller ID : " + sellerId, e);
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return idsList;
	}

	@Override
	public List<Order> searchAsIsOrder(String searchCriteria, String ID, int sellerId) {
		List<Order> orderList = null;
		Session session = null;
		String channelOrderId = "";	
		Criteria criteria=null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			criteria = session.createCriteria(Order.class);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.like(searchCriteria, ID  + "%"));
			orderList = criteria.list();
			if (orderList != null && orderList.size() != 0) {
				return orderList;
			} else if (ID.contains(GlobalConstant.orderUniqueSymbol)){
				channelOrderId = ID.substring(0,
						ID.indexOf(GlobalConstant.orderUniqueSymbol));	
				criteria = session.createCriteria(Order.class);
				criteria.createAlias("seller", "seller",
						CriteriaSpecification.LEFT_JOIN)
						.add(Restrictions.eq("seller.id", sellerId))
						.add(Restrictions
								.like(searchCriteria, channelOrderId + "%"));
				orderList = criteria.list();
				if (orderList != null && orderList.size() != 0) {
					return orderList;
				}
			}
		} catch (Exception e) {
			log.error("Failed by Seller ID : " + sellerId, e);
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return null;
	}

}
