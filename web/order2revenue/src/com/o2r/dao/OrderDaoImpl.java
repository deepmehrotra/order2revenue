package com.o2r.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.o2r.bean.ChannelSalesDetails;
import com.o2r.bean.DebitNoteBean;
import com.o2r.bean.PoPaymentBean;
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
import com.o2r.model.TaxDetail;
import com.o2r.service.EventsService;
import com.o2r.service.PartnerService;
import com.o2r.service.ProductService;
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
	private AreaConfigDao areaConfigDao;
	@Autowired
	private SellerDao sellerDao;

	private final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");

	private final int pageSize = 500;

	static Logger log = Logger.getLogger(SellerDaoImpl.class.getName());

	@SuppressWarnings("deprecation")
	@Override
	public void addOrder(Order order, int sellerId) throws CustomException {
		System.out.println(" Order id " + order.getOrderId());
		System.out.println(" Order Partner " + order.getPcName());
		System.out.println("Order delivery date " + order.getDeliveryDate());
		System.out.println("Order customer email in order: "
				+ order.getCustomer().getCustomerEmail());
		Seller seller = null;
		Date reconciledate = null;
		// Customer customer = null;
		Date tempDate = null;
		Session session = null;
		TaxDetail taxDetails = null;
		Events event = null;
		Partner partner = null;
		Product product;
		try {
			product = productService.getProduct(order.getProductSkuCode(),
					sellerId);

			calculateDeliveryDate(order, sellerId);
			if (product != null) {
				try {
					System.out
							.println(" Before starting the session in orderdaolimpl");
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
					float taxpercent = taxDetailService.getTaxCategory(
							order.getOrderTax().getTaxCategtory(), sellerId)
							.getTaxPercent();
					if (seller.getPartners() != null
							&& seller.getPartners().size() != 0) {
						partner = seller.getPartners().get(0);
						reconciledate = getreconciledate(order, seller
								.getPartners().get(0), order.getOrderDate());
						if (reconciledate != null)
							order.setPaymentDueDate(reconciledate);
						System.out
								.println(" after settinf rec date delivery date :"
										+ order.getDeliveryDate());
					}
					/* populating derived values of order */
					order.setStatus("Shipped");
					if (partner != null && partner.getNrnReturnConfig() != null
							&& partner.getNrnReturnConfig().isNrCalculator()) {

						// Check conditions here....
						event = eventsService.isEventActiive(
								order.getOrderDate(), partner.getPcName(),
								sellerId);
						if (event != null) {

							event.setNetSalesQuantity(event
									.getNetSalesQuantity()
									+ order.getQuantity());

							if (event.getNrnReturnConfig()
									.getNrCalculatorEvent()
									.equalsIgnoreCase("variable")) {
								if (!calculateNR(event.getNrnReturnConfig(),
										order, product.getCategoryName(),
										product.getDeadWeight(),
										product.getVolWeight()))
									throw new Exception();
								event.setNetSalesAmount(event
										.getNetSalesAmount()
										+ order.getNetRate());
							} else if (event.getNrnReturnConfig()
									.getNrCalculatorEvent()
									.equalsIgnoreCase("original")) {
								if (!calculateNR(seller.getPartners().get(0),
										order, product.getCategoryName(),
										product.getDeadWeight(),
										product.getVolWeight()))
									throw new Exception();
								event.setNetSalesAmount(event
										.getNetSalesAmount()
										+ order.getNetRate());
							}
						} else if (!calculateNR(seller.getPartners().get(0),
								order, product.getCategoryName(),
								product.getDeadWeight(), product.getVolWeight()))
							throw new Exception();

						System.out.println(" Shipping charges :"
								+ order.getShippingCharges()
								+ " >> Gross net rate "
								+ order.getGrossNetRate() + " delivery date :"
								+ order.getDeliveryDate());
					} else {

						order.setPartnerCommission((order.getOrderSP() - order
								.getGrossNetRate()) * order.getQuantity());
						order.getOrderTax().setTdsToDeduct(
								(order.getPartnerCommission() - (order
										.getPartnerCommission() * 100 / 114.5))
										* (.1) * order.getQuantity());

					}
					order.setOrderMRP(order.getOrderMRP() * order.getQuantity());
					order.setOrderSP(order.getOrderSP() * order.getQuantity());
					order.setNetRate(order.getGrossNetRate()
							* order.getQuantity());

					if ((int) order.getPoPrice() != 0
							&& order.getPcName().equals("Myntra")) {
						double taxvalue = order.getPoPrice()
								- (order.getPoPrice() * (100 / (100 + taxpercent)));
						order.setDiscount((Math.abs(order.getPoPrice()
								- order.getNetRate())));
						order.getOrderTax().setTax(taxvalue);
					} else {
						order.setDiscount((Math.abs(order.getOrderMRP()
								- order.getOrderSP())));
						System.out
								.println(" Tax cal SP:"
										+ order.getOrderSP()
										+ " >>TAxReate="
										+ taxpercent
										+ "  Tax>>"
										+ (order.getOrderSP() - (order
												.getOrderSP() * (100 / (100 + seller
												.getPartners().get(0)
												.getTaxrate())))));
						order.getOrderTax()
								.setTax(order.getOrderSP()
										- (order.getOrderSP() * (100 / (100 + taxpercent))));
						taxDetails = new TaxDetail();
						taxDetails.setBalanceRemaining(order.getOrderTax()
								.getTax());
						taxDetails.setParticular(order.getOrderTax()
								.getTaxCategtory());
						taxDetails.setUploadDate(order.getOrderDate());
						taxDetailService.addMonthlyTaxDetail(session,
								taxDetails, sellerId);
					}

					order.setTotalAmountRecieved(order.getNetRate());
					order.setFinalStatus("In Process");
					// Set Order Timeline
					OrderTimeline timeline = new OrderTimeline();
					// populating tax related values of order
					System.out.println(" Tax before pr:"
							+ order.getOrderTax().getTax());
					order.setPr(order.getNetRate()
							- order.getOrderTax().getTax());
					if (seller.getPartners().get(0).isTdsApplicable()) {
						System.out.println(" PC "
								+ order.getPartnerCommission());
						/*
						 * order.getOrderTax().setTdsToDeduct(
						 * order.getPartnerCommission() * (.1));
						 */
						taxDetails = new TaxDetail();
						taxDetails.setBalanceRemaining(order.getOrderTax()
								.getTdsToDeduct());
						taxDetails.setParticular("TDS");
						taxDetails.setUploadDate(order.getOrderDate());
						taxDetailService.addMonthlyTDSDetail(session,
								taxDetails, sellerId);
					}
					// Reducing Product Inventory For Order
					productService.updateInventory(order.getProductSkuCode(),
							0, 0, order.getQuantity(), false, sellerId);
					/*
					 * product=productService.getProduct(order.getProductSkuCode(
					 * ), sellerId); product.setQuantity(product.getQuantity()-
					 * order.getQuantity()); session.saveOrUpdate(product);
					 */
					/* checking if customer is available */
					System.out.println(" Customer Email id in add order :"
							+ order.getCustomer().getCustomerEmail());
					/*
					 * if (order.getCustomer() != null &&
					 * order.getCustomer().getCustomerEmail() != null &&
					 * seller.getPartners().get(0).isTdsApplicable()) {
					 */

					order.getCustomer().setSellerId(sellerId);
					order.getCustomer().getOrders().add(order);
					System.out.println(" After setting seller id in customer");
					/*
					 * CustomerDao customerdao = new CustomerDaoImpl(); customer
					 * = customerdao.getCustomer(order.getCustomer()
					 * .getCustomerEmail(), sellerId, session); if (customer !=
					 * null) { order.setCustomer(customer);
					 * customer.getOrders().add(order); }
					 */
					// }
					// Adding order to the Partner
					if (partner.getOrders() != null && order.getOrderId() == 0) {
						partner.getOrders().add(order);
					}
					// Setting payment difference for old orders

					if (order.getPaymentDueDate().compareTo(
							java.util.Calendar.getInstance().getTime()) < 0) {
						System.out
								.println(" Setting payment difference for old orders");
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
					// Setting Gross Profit for Order
					order.setGrossProfit(order.getNetRate()
							- (product.getProductPrice() * order.getQuantity()));

					// Setting order status if return limit is crossed
					if (order.getReturnLimitCrossed().compareTo(
							java.util.Calendar.getInstance().getTime()) < 0) {
						order.setStatus("Return Limit Crossed");
					}
					if (order.getOrderId() != 0) {
						System.out.println(" Saving edited order");
						// Code for order timeline
						timeline.setEventDate(new Date());
						timeline.setEvent(" Order Edited");
						order.getOrderTimeline().add(timeline);
						order.setLastActivityOnOrder(new Date());
						session.merge(order);
					} else {
						System.out
								.println(" ****************Saving new  order delivery date :"
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
					session.getTransaction().commit();
					/*
					 * session.getTransaction().commit(); session.close();
					 */
				} catch (Exception e) {
					// if(session.getTransaction()!=null&&session.getTransaction().isActive())
					// session.getTransaction().rollback();
					System.out.println("Inside exception in add order "
							+ e.getLocalizedMessage() + " message: "
							+ e.getMessage());
					e.printStackTrace();
				} finally {
					session.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			log.info("Error : " + GlobalConstant.addOrderError);
			log.info("Error : " + GlobalConstant.addOrderErrorCode);
			throw new CustomException(GlobalConstant.addOrderError, new Date(),
					1, GlobalConstant.addOrderErrorCode, e);
		}
	}

	@Override
	public Order addPO(Order order, int sellerId) throws CustomException {

		order.setPO(true);
		order.setOrderDate(new Date());
		System.out.println(" PO id " + order.getSubOrderID());
		System.out.println(" Order Partner " + order.getPcName());
		System.out.println(" Invoice id " + order.getInvoiceID());

		Seller seller = null;
		Date reconciledate = null;
		// Customer customer = null;
		Session session = null;
		Partner partner = null;
		Product product = null;
		try {

			ProductConfig productConfig = productService.getProductConfig(
					order.getProductSkuCode(), order.getPcName(), sellerId);
			if (productConfig != null) {
				order.setProductConfig(productConfig);
				product = productService.getProduct(
						productConfig.getProductSkuCode(), sellerId);
			}
			if (product != null) {
				try {
					System.out
							.println(" Before starting the session in orderdaolimpl");
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
					/*
					 * float taxpercent = taxDetailService.getTaxCategory(
					 * order.getOrderTax().getTaxCategtory(), sellerId)
					 * .getTaxPercent();
					 */
					if (seller.getPartners() != null
							&& seller.getPartners().size() != 0) {
						partner = seller.getPartners().get(0);
						/*
						 * reconciledate = getreconciledate(order, seller
						 * .getPartners().get(0), order.getOrderDate()); if
						 * (reconciledate != null)
						 * order.setPaymentDueDate(reconciledate); System.out
						 * .println(" after settinf rec date delivery date :" +
						 * order.getDeliveryDate());
						 */
					}
					/* populating derived values of order */
					order.setStatus("Shipped");
					order.setEossValue((order.getPoPrice() * order
							.getProductConfig().getDiscount()) / 100);
					order.setGrossNetRate(order.getPoPrice()
							- order.getEossValue());
					order.setNetRate(order.getGrossNetRate()
							* order.getQuantity());
					double taxvalue = (float) (order.getPoPrice() - (order
							.getPoPrice() * 100 / (100 + order
							.getProductConfig().getTaxPo())));

					order.setOrderMRP(order.getOrderMRP() * order.getQuantity());
					order.setOrderSP(order.getOrderSP() * order.getQuantity());

					order.setDiscount(order.getProductConfig().getDiscount());
					order.getOrderTax().setTax(taxvalue);

					order.setTotalAmountRecieved(order.getNetRate());
					order.setFinalStatus("In Process");
					// Set Order Timeline
					OrderTimeline timeline = new OrderTimeline();
					// populating tax related values of order
					System.out.println(" Tax before pr:"
							+ order.getOrderTax().getTax());
					order.setPr(order.getNetRate()
							- order.getOrderTax().getTax());

					// Reducing Product Inventory For Order
					productService.updateInventory(order.getProductConfig()
							.getProductSkuCode(), 0, 0, order.getQuantity(),
							false, sellerId);
					/*
					 * product=productService.getProduct(order.getProductSkuCode(
					 * ), sellerId); product.setQuantity(product.getQuantity()-
					 * order.getQuantity()); session.saveOrUpdate(product);
					 */

					// Adding order to the Partner
					if (partner.getOrders() != null && order.getOrderId() == 0) {
						partner.getOrders().add(order);
					}
					// Setting Gross Profit for Order
					order.setGrossProfit((order.getPr() * order.getQuantity())
							- (order.getProductConfig().getProductPrice() * order
									.getQuantity()));

					if (order.getOrderId() != 0) {
						System.out.println(" Saving edited order");
						// Code for order timeline
						timeline.setEventDate(new Date());
						timeline.setEvent(" Order Edited");
						order.getOrderTimeline().add(timeline);
						order.setLastActivityOnOrder(new Date());
						session.merge(order);
					} else {
						System.out
								.println(" ****************Saving new  order delivery date :"
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
					session.getTransaction().commit();
					/*
					 * session.getTransaction().commit(); session.close();
					 */
				} catch (Exception e) {
					// if(session.getTransaction()!=null&&session.getTransaction().isActive())
					// session.getTransaction().rollback();
					System.out.println("Inside exception in add order "
							+ e.getLocalizedMessage() + " message: "
							+ e.getMessage());
					e.printStackTrace();
				} finally {
					session.close();
				}
			}
			return order;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			log.info("Error : " + GlobalConstant.addOrderError);
			log.info("Error : " + GlobalConstant.addOrderErrorCode);
			throw new CustomException(GlobalConstant.addOrderError, new Date(),
					1, GlobalConstant.addOrderErrorCode, e);
		}

	}

	@Override
	public List<Order> listOrders(int sellerId) throws CustomException {
		// sellerId=4;
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

			log.error(e);
			throw new CustomException(GlobalConstant.listOrderError,
					new Date(), 3, GlobalConstant.listOrderErrorCode, e);

			/*
			 * System.out.println(" Exception in getting order list :" +
			 * e.getLocalizedMessage());
			 */
		}
		return returnlist;
	}

	@Override
	public List<Order> listOrders(int sellerId, int pageNo)
			throws CustomException {
		// sellerId=4;
		System.out.println(" inside list order pageNo " + pageNo);
		List<Order> returnlist = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Order.class);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.eq("isPO", false));

			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			criteria.addOrder(org.hibernate.criterion.Order
					.desc("lastActivityOnOrder"));
			criteria.setFirstResult(pageNo * pageSize);
			criteria.setMaxResults(pageSize);
			returnlist = criteria.list();
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {

			log.error(e);
			throw new CustomException(GlobalConstant.listOrdersError,
					new Date(), 3, GlobalConstant.listOrdersErrorCode, e);

			/*
			 * System.out.println(" Exception in getting order list :" +
			 * e.getLocalizedMessage());
			 */
		}

		return returnlist;
	}

	@Override
	public List<Order> listPOOrders(int sellerId, int pageNo)
			throws CustomException {
		// sellerId=4;
		System.out.println(" inside list POorder pageNo " + pageNo);
		List<Order> returnlist = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Order.class);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.eq("isPO", true));

			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			criteria.addOrder(org.hibernate.criterion.Order
					.desc("lastActivityOnOrder"));
			criteria.setFirstResult(pageNo * pageSize);
			criteria.setMaxResults(pageSize);
			returnlist = criteria.list();
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {

			log.error(e);
			throw new CustomException(GlobalConstant.listOrdersError,
					new Date(), 3, GlobalConstant.listOrdersErrorCode, e);

			/*
			 * System.out.println(" Exception in getting order list :" +
			 * e.getLocalizedMessage());
			 */
		}

		return returnlist;
	}

	@Override
	public Order getOrder(int orderId) throws CustomException {
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

			log.error(e);
			throw new CustomException(GlobalConstant.getOrderError, new Date(),
					3, GlobalConstant.getOrderErrorCode, e);

			/*
			 * System.out.println(" Exception in getting order list :" +
			 * e.getLocalizedMessage());
			 */
		}
		return returnorder;
		// return (Order) sessionFactory.getCurrentSession().get(Order.class,
		// orderId);
	}

	@Override
	public Order getConsolidatedOrder(String poId, String invoiceId)
			throws CustomException {
		Order order = null;
		List returnList = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Order.class);
			criteria.add(Restrictions.eq("subOrderID", poId));
			criteria.add(Restrictions.eq("invoiceID", invoiceId));
			criteria.add(Restrictions.eq("isPO", true));
			criteria.add(Restrictions.eq("consolidatedOrder.orderId", 0));

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

			log.error(e);
			throw new CustomException(GlobalConstant.getOrderError, new Date(),
					3, GlobalConstant.getOrderErrorCode, e);

			/*
			 * System.out.println(" Exception in getting order list :" +
			 * e.getLocalizedMessage());
			 */
		}
		return order;
		// return (Order) sessionFactory.getCurrentSession().get(Order.class,
		// orderId);
	}

	@Override
	public Order getOrder(int orderId, int sellerId) throws CustomException {
		Order returnorder = null;
		List returnList = null;
		try {

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

			log.error(e);
			throw new CustomException(GlobalConstant.getOrderError, new Date(),
					3, GlobalConstant.getOrderErrorCode, e);

			/*
			 * System.out.println(" Exception in getting order list :" +
			 * e.getLocalizedMessage());
			 */
		}
		return returnorder;
	}

	@Override
	public void deleteOrder(Order order, int sellerId) throws CustomException {
		System.out.println(" In Order delete oid " + order.getOrderId());
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
			System.out.println(" Deletion process successful :updated"
					+ updated + "orderdelete " + orderdelete);
			session.getTransaction().commit();
			session.close();

		} catch (Exception e) {

			log.error(e);
			throw new CustomException(GlobalConstant.deleteOrderError,
					new Date(), 3, GlobalConstant.deleteOrderErrorCode, e);

			/*
			 * System.out.println(" Inside delleting order" +
			 * e.getLocalizedMessage()); e.printStackTrace();
			 */
		}

	}

	@Override
	public void addReturnOrder(String channelOrderId,
			OrderRTOorReturn orderReturn, int sellerId) throws CustomException {
		System.out.println(" saving rder id :" + channelOrderId);
		Seller seller = null;
		Order order = null;
		Events event = null;
		Product product = null;
		TaxDetail taxDetails = null;
		TaxDetail tdsDetails = null;
		float returnChargesCalculated = 0;

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

				// Check condition here
				event = eventsService.isEventActiive(order.getOrderDate(),
						order.getPcName(), sellerId);
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

				if ((int) order.getOrderReturnOrRTO()
						.getReturnOrRTOChargestoBeDeducted() == 0) {
					order.getOrderReturnOrRTO().setReturnUploadDate(
							orderReturn.getReturnUploadDate());
				}
				if (order.getReturnLimitCrossed().compareTo(
						orderReturn.getReturnDate()) < 0) {
					order.getOrderReturnOrRTO()
							.setReturnOrRTOChargestoBeDeducted(0);
					/*
					 * order.getOrderPayment().setPaymentDifference(
					 * order.getOrderPayment().getNetPaymentResult() -
					 * order.getNetRate());
					 */
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
					/*
					 * order.getOrderPayment().setPaymentDifference(
					 * order.getOrderPayment().getNetPaymentResult() -
					 * order.getNetRate());
					 */
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
					if (orderReturn.getReturnorrtoQty() == order.getQuantity())
						order.setGrossProfit(-orderReturn
								.getReturnOrRTOChargestoBeDeducted());

					else {
						product = productService.getProduct(
								order.getProductSkuCode(), sellerId);
						order.setGrossProfit(order.getNetRate()
								- (order.getNetRate() / order.getQuantity())
								* orderReturn.getReturnorrtoQty()
								- orderReturn
										.getReturnOrRTOChargestoBeDeducted()
								- (product.getProductPrice() * (order
										.getQuantity() - orderReturn
										.getReturnorrtoQty())));
					}

					OrderTimeline timeline = new OrderTimeline();
					timeline.setEvent("Return Recieved");
					timeline.setEventDate(new Date());
					order.getOrderTimeline().add(timeline);

				}
				/*
				 * if ((int) order.getOrderPayment().getNegativeAmount() != 0) {
				 * order.getOrderPayment() .setPaymentDifference(
				 * order.getOrderPayment() .getNetPaymentResult() +
				 * order.getOrderReturnOrRTO()
				 * .getReturnOrRTOChargestoBeDeducted() );
				 * 
				 * } if (order.getOrderPayment().getNetPaymentResult() > 0) {
				 * order.getOrderPayment() .setPaymentDifference(
				 * -(order.getOrderPayment() .getNetPaymentResult() + order
				 * .getOrderReturnOrRTO()
				 * .getReturnOrRTOChargestoBeDeducted())); }
				 */
				// order.setStatus("Return Recieved");

				if (order.getQuantity() != orderReturn.getReturnorrtoQty()) {
					order.getOrderPayment().setPaymentDifference(
							order.getOrderPayment().getPaymentDifference()
									+ order.getGrossNetRate()
									* orderReturn.getReturnorrtoQty());
					order.setGrossProfit(order.getGrossProfit()
							* orderReturn.getReturnorrtoQty()
							/ order.getQuantity());
				}

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
				tdsDetails = new TaxDetail();
				tdsDetails.setBalanceRemaining(-(order.getOrderTax()
						.getTdsToDeduct() / order.getQuantity())
						* orderReturn.getReturnorrtoQty());
				tdsDetails.setParticular("TDS");
				tdsDetails.setUploadDate(orderReturn.getReturnDate());
				taxDetailService.addMonthlyTDSDetail(session, tdsDetails,
						sellerId);

				order.getOrderTax().setToxToReturn(
						(order.getOrderTax().getTax() / order.getQuantity())
								* orderReturn.getReturnorrtoQty());
				order.setFinalStatus("Actionable");
				order.setNetSaleQuantity(order.getQuantity()
						- order.getOrderReturnOrRTO().getReturnorrtoQty());
				orderReturn.setReturnUploadDate(new Date());
				order.setLastActivityOnOrder(new Date());
				order.getOrderReturnOrRTO().setReturnDate(
						orderReturn.getReturnDate());
				order.getOrderReturnOrRTO().setReturnOrRTOId(
						orderReturn.getReturnOrRTOId());
				order.getOrderReturnOrRTO().setReturnorrtoQty(
						orderReturn.getReturnorrtoQty());
				order.getOrderReturnOrRTO().setReturnOrRTOreason(
						orderReturn.getReturnOrRTOreason());
				order.getOrderReturnOrRTO().setReturnOrRTOstatus("Return");
				productService.updateInventory(order.getProductSkuCode(), 0,
						order.getOrderReturnOrRTO().getReturnorrtoQty(), 0,
						false, sellerId);

				order.setOrderReturnOrRTO(orderReturn);
				session.getTransaction().commit();
				session.close();
			}
		} catch (Exception e) {

			e.printStackTrace();
			log.error(e);
			throw new CustomException(GlobalConstant.addReturnOrderError,
					new Date(), 1, GlobalConstant.addReturnOrderErrorCode, e);

		}

	}

	@Override
	public List<Order> findOrders(String column, String value, int sellerId,
			boolean isPO) throws CustomException {
		String searchString = "order." + column;
		System.out.println(" Inside Find order dao method searchString :"
				+ searchString + " value :" + value + "   sellerId :"
				+ sellerId);

		Seller seller = null;
		List<Order> orderlist = null;

		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", sellerId));
			criteria.createAlias("orders", "order",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq(searchString, value).ignoreCase())
					.add(Restrictions.eq("order.isPO", isPO))
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
			log.error(e);
			throw new CustomException(GlobalConstant.findOrdersError,
					new Date(), 2, GlobalConstant.findOrdersErrorcode, e);

			/*
			 * System.out.println(" Exception in find order "+
			 * e.getLocalizedMessage()); e.printStackTrace();
			 */
		}
		return orderlist;
	}

	@Override
	public List<Order> findOrdersbyDate(String column, Date startDate,
			Date endDate, int sellerId) throws CustomException {
		String searchString = null;
		searchString = "order." + column;
		Seller seller = null;
		List<Order> orderlist = null;

		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", sellerId));
			criteria.createAlias("orders", "order",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.between(searchString, startDate, endDate))
					.setResultTransformer(
							CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			seller = (Seller) criteria.list().get(0);
			orderlist = seller.getOrders();
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

			log.error(e);
			throw new CustomException(GlobalConstant.findOrdersbyDateError,
					new Date(), 2, GlobalConstant.findOrdersbyDateErrorCode, e);
			/*
			 * System.out.println(" Inside findorderbydate  exception :"+
			 * e.getMessage()); e.printStackTrace();
			 */
		}
		return orderlist;
	}

	@Override
	public List<Order> findOrdersbyPaymentDate(String column, Date startDate,
			Date endDate, int sellerId) throws CustomException {

		// String searchString=null;
		// searchString="order."+column;
		// Seller seller=null;
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

			log.error(e);
			throw new CustomException(
					GlobalConstant.findOrdersbyPaymentDateError, new Date(), 2,
					GlobalConstant.findOrdersbyPaymentDateErrorCode, e);

			/*
			 * System.out.println(" Inside findorderby payment date  exception :"
			 * + e.getMessage()); e.printStackTrace();
			 */
		}
		return orderlist;
	}

	@Override
	public List<Order> findOrdersbyReturnDate(String column, Date startDate,
			Date endDate, int sellerId) throws CustomException {

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
			log.error(e);
			throw new CustomException(
					GlobalConstant.findOrdersbyReturnDateError, new Date(), 2,
					GlobalConstant.findOrdersbyReturnDateErrorCode, e);
			// e.printStackTrace();
		}

		return orderlist;
	}

	@Override
	public void deleteReturnInfo(String orderId) {
		// To be implemented
	}

	@Override
	public List<Order> findOrdersbyCustomerDetails(String column, String value,
			int sellerId) throws CustomException {
		Seller seller = null;
		List<Order> orderlist = new ArrayList<Order>();
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", sellerId));
			seller = (Seller) criteria.list().get(0);

			for (Order order : seller.getOrders()) {
				if (order.getCustomer().getCustomerCity()
						.equalsIgnoreCase(value)
						|| order.getCustomer().getCustomerName()
								.equalsIgnoreCase(value)
						|| order.getCustomer().getCustomerEmail()
								.equalsIgnoreCase(value)
						|| order.getCustomer().getCustomerPhnNo()
								.equalsIgnoreCase(value))
					orderlist.add(order);
			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {

			log.error(e);
			throw new CustomException(
					GlobalConstant.findOrdersbyCustomerDetailsError,
					new Date(), 2,
					GlobalConstant.findOrdersbyCustomerDetailsErrorCode, e);

			// e.printStackTrace();
		}

		return orderlist;
	}

	@Override
	public Order addOrderPayment(int orderid, OrderPayment orderPayment,
			int sellerId) throws CustomException {
		Order order = null;
		TaxDetail taxDetails = null;
		System.out.println("Inside add ordr payment iwtih order id " + orderid);
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Order.class).add(
					Restrictions.eq("orderId", orderid));
			if (criteria.list() != null && criteria.list().size() != 0)
				order = (Order) criteria.list().get(0);
			orderPayment.setNegativeAmount(Math.abs(orderPayment
					.getNegativeAmount()));
			if (order != null && order.getOrderReturnOrRTO() != null
					&& order.getOrderReturnOrRTO().getReturnDate() != null
					&& order.getOrderReturnOrRTO().getReturnorrtoQty() != 0) {
				if (order.getOrderReturnOrRTO().getReturnorrtoQty() != order
						.getQuantity()) {
					order.setTotalAmountRecieved(order.getGrossNetRate()
							* (order.getQuantity() - order
									.getOrderReturnOrRTO().getReturnorrtoQty()));
				}
				if (((int) orderPayment.getPositiveAmount()) != 0) {
					if (order.getReturnLimitCrossed().compareTo(
							order.getOrderReturnOrRTO().getReturnDate()) < 0) {
						// Reverting Tax information for Return Order in case
						// when return has recieved and positive payment is
						// recieved after RTO limit crossed
						taxDetails = new TaxDetail();
						taxDetails.setBalanceRemaining(order.getOrderTax()
								.getTax());
						taxDetails.setParticular(order.getOrderTax()
								.getTaxCategtory());
						taxDetails.setUploadDate(orderPayment
								.getDateofPayment());
						taxDetailService.addMonthlyTaxDetail(session,
								taxDetails, sellerId);
					}
					order.getOrderPayment().setPositiveAmount(
							orderPayment.getPositiveAmount());
					order.getOrderPayment().setNetPaymentResult(
							order.getOrderPayment().getNetPaymentResult()
									+ orderPayment.getPositiveAmount());
					if (order.getOrderPayment().getNetPaymentResult() > 0) {
						if (order.getReturnLimitCrossed().compareTo(
								order.getOrderReturnOrRTO().getReturnDate()) < 0) {
							order.getOrderPayment().setPaymentDifference(
									order.getOrderPayment()
											.getNetPaymentResult()
											- order.getTotalAmountRecieved());
						} else {
							order.getOrderPayment()
									.setPaymentDifference(
											order.getOrderPayment()
													.getNetPaymentResult()
													- (order.getTotalAmountRecieved() - order
															.getOrderReturnOrRTO()
															.getReturnOrRTOChargestoBeDeducted()));
						}
					} else {
						order.getOrderPayment()
								.setPaymentDifference(
										order.getOrderPayment()
												.getNetPaymentResult()
												+ order.getOrderReturnOrRTO()
														.getReturnOrRTOChargestoBeDeducted());

					}

					order.setStatus("Payment Recieved");
					OrderTimeline timeline = new OrderTimeline();
					timeline.setEvent("Rs " + orderPayment.getPositiveAmount()
							+ " Recieved");
					timeline.setEventDate(new Date());
					order.getOrderTimeline().add(timeline);

				} else {
					order.setStatus("Payment Deducted");
					OrderTimeline timeline = new OrderTimeline();
					timeline.setEvent("Rs " + orderPayment.getNegativeAmount()
							+ " Deducted");
					timeline.setEventDate(new Date());
					order.getOrderTimeline().add(timeline);
					order.getOrderPayment().setNegativeAmount(
							orderPayment.getNegativeAmount());
					order.getOrderPayment().setNetPaymentResult(
							order.getOrderPayment().getNetPaymentResult()
									- orderPayment.getNegativeAmount());
					if (order.getReturnLimitCrossed().compareTo(
							order.getOrderReturnOrRTO().getReturnDate()) < 0) {
						order.getOrderPayment().setPaymentDifference(
								order.getOrderPayment().getNetPaymentResult()
										- order.getTotalAmountRecieved());
						order.setStatus("Return Limit Crossed");
						OrderTimeline timeline1 = new OrderTimeline();
						timeline1.setEvent("Return Limit Crossed");
						timeline1.setEventDate(new Date());
						order.getOrderTimeline().add(timeline1);
					} else {
						order.getOrderPayment()
								.setPaymentDifference(
										order.getOrderPayment()
												.getNetPaymentResult()
												+ order.getOrderReturnOrRTO()
														.getReturnOrRTOChargestoBeDeducted());
					}

				}
			} else {

				if (((int) orderPayment.getPositiveAmount()) != 0) {

					order.getOrderPayment().setPositiveAmount(
							orderPayment.getPositiveAmount());
					order.getOrderPayment().setNetPaymentResult(
							order.getOrderPayment().getNetPaymentResult()
									+ orderPayment.getPositiveAmount());
					order.getOrderPayment()
							.setPaymentDifference(
									order.getOrderPayment()
											.getNetPaymentResult()
											- (order.getTotalAmountRecieved() - order
													.getOrderReturnOrRTO()
													.getReturnOrRTOChargestoBeDeducted()));
					System.out.println("payment difference :"
							+ order.getOrderPayment().getPaymentDifference());

					order.setStatus("Payment Recieved");
					OrderTimeline timeline = new OrderTimeline();
					timeline.setEvent("Rs " + orderPayment.getPositiveAmount()
							+ " Recieved");
					timeline.setEventDate(new Date());
					order.getOrderTimeline().add(timeline);

				} else {
					order.setStatus("Return Not Recieved");
					OrderTimeline timeline = new OrderTimeline();
					timeline.setEvent("Rs " + orderPayment.getNegativeAmount()
							+ " Deducted");
					timeline.setEventDate(new Date());
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
							orderPayment.getNegativeAmount());
					order.getOrderPayment().setNetPaymentResult(
							order.getOrderPayment().getNetPaymentResult()
									- orderPayment.getNegativeAmount());
					order.getOrderPayment()
							.setPaymentDifference(
									order.getOrderPayment()
											.getNetPaymentResult()
											- order.getNetRate()
											+ order.getOrderReturnOrRTO()
													.getReturnOrRTOChargestoBeDeducted());

				}
			}
			order.getOrderPayment().setDateofPayment(
					orderPayment.getDateofPayment());
			order.getOrderPayment().setPaymentCycle(
					orderPayment.getPaymentCycle());
			order.getOrderPayment().setUploadDate(new Date());
			if (order.getOrderPayment().getPaymentDifference() > 0
					|| (int) order.getOrderPayment().getPaymentDifference() == 0) {
				// order.setStatus("OK Payment");
				order.setFinalStatus("Settled");
			} else {
				// order.setStatus("Payment Difference");
				order.setFinalStatus("Actionable");
			}
			System.out.println("order in add payment :   **" + order);
			order.setLastActivityOnOrder(new Date());
			// To Do - implement netactualrate 2
			session.saveOrUpdate(order);
			session.getTransaction().commit();
			session.close();

		} catch (Exception e) {

			log.error(e);
			throw new CustomException(GlobalConstant.addOrderPaymentError,
					new Date(), 1, GlobalConstant.addOrderPaymentErrorCode, e);

			// e.printStackTrace();
		}

		return order;
	}

	@Override
	public Order addOrderPayment(String skucode, String channelOrderId,
			OrderPayment orderPayment, int sellerId) throws CustomException {
		Order order = null;
		System.out.println(" SKUCODE: " + skucode + "  channedorderid "
				+ channelOrderId + "sellerId :" + sellerId);
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			// Criteria
			// criteria=session.createCriteria(Order.class).add(Restrictions.eq("channelOrderID",
			// channelOrderId));
			Criteria criteria = session.createCriteria(Order.class);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.eq("channelOrderID", channelOrderId))
					.add(Restrictions.eq("productSkuCode", skucode));

			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			if (criteria.list() != null && criteria.list().size() != 0) {
				System.out.println("get order ");
				order = (Order) criteria.list().get(0);
			} else {
				System.out.println(" Null order");
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
					if (((int) orderPayment.getPositiveAmount()) != 0) {

						order.getOrderPayment().setPositiveAmount(
								orderPayment.getPositiveAmount());
						order.getOrderPayment().setNetPaymentResult(
								order.getOrderPayment().getNetPaymentResult()
										+ orderPayment.getPositiveAmount());
						if (order.getOrderPayment().getNetPaymentResult() > 0) {
							if (order.getReturnLimitCrossed()
									.compareTo(
											order.getOrderReturnOrRTO()
													.getReturnDate()) < 0) {
								order.getOrderPayment()
										.setPaymentDifference(
												order.getOrderPayment()
														.getNetPaymentResult()
														- order.getTotalAmountRecieved());
							} else {
								order.getOrderPayment()
										.setPaymentDifference(
												order.getOrderPayment()
														.getNetPaymentResult()
														- (order.getTotalAmountRecieved() - order
																.getOrderReturnOrRTO()
																.getReturnOrRTOChargestoBeDeducted()));
							}
						} else {
							order.getOrderPayment()
									.setPaymentDifference(
											order.getOrderPayment()
													.getNetPaymentResult()
													+ order.getOrderReturnOrRTO()
															.getReturnOrRTOChargestoBeDeducted());

						}

						order.setStatus("Payment Recieved");
						OrderTimeline timeline = new OrderTimeline();
						timeline.setEvent("Rs "
								+ orderPayment.getPositiveAmount()
								+ " Recieved");
						timeline.setEventDate(new Date());
						order.getOrderTimeline().add(timeline);

					} else {
						order.setStatus("Payment Deducted");
						OrderTimeline timeline = new OrderTimeline();
						timeline.setEvent("Rs "
								+ orderPayment.getNegativeAmount()
								+ " Deducted");
						timeline.setEventDate(new Date());
						order.getOrderTimeline().add(timeline);
						order.getOrderPayment().setNegativeAmount(
								orderPayment.getNegativeAmount());
						order.getOrderPayment().setNetPaymentResult(
								order.getOrderPayment().getNetPaymentResult()
										- orderPayment.getNegativeAmount());
						if (order.getReturnLimitCrossed().compareTo(
								order.getOrderReturnOrRTO().getReturnDate()) < 0) {
							order.getOrderPayment().setPaymentDifference(
									order.getOrderPayment()
											.getNetPaymentResult()
											- order.getTotalAmountRecieved());
							order.setStatus("Return Limit Crossed");
							OrderTimeline timeline1 = new OrderTimeline();
							timeline1.setEvent("Return Limit Crossed");
							timeline1.setEventDate(new Date());
							order.getOrderTimeline().add(timeline1);
						} else {
							order.getOrderPayment()
									.setPaymentDifference(
											order.getOrderPayment()
													.getNetPaymentResult()
													+ order.getOrderReturnOrRTO()
															.getReturnOrRTOChargestoBeDeducted());
						}

					}
				} else {

					if (((int) orderPayment.getPositiveAmount()) != 0) {

						order.getOrderPayment().setPositiveAmount(
								orderPayment.getPositiveAmount());
						order.getOrderPayment().setNetPaymentResult(
								order.getOrderPayment().getNetPaymentResult()
										+ orderPayment.getPositiveAmount());
						order.getOrderPayment()
								.setPaymentDifference(
										order.getOrderPayment()
												.getNetPaymentResult()
												- (order.getTotalAmountRecieved() - order
														.getOrderReturnOrRTO()
														.getReturnOrRTOChargestoBeDeducted()));
						System.out.println("payment difference :"
								+ order.getOrderPayment()
										.getPaymentDifference());

						order.setStatus("Payment Recieved");
						OrderTimeline timeline = new OrderTimeline();
						timeline.setEvent("Rs "
								+ orderPayment.getPositiveAmount()
								+ " Recieved");
						timeline.setEventDate(new Date());
						order.getOrderTimeline().add(timeline);

					} else {
						order.setStatus("Return Not Recieved");
						OrderTimeline timeline = new OrderTimeline();
						timeline.setEvent("Rs "
								+ orderPayment.getNegativeAmount()
								+ " Deducted");
						timeline.setEventDate(new Date());
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
								orderPayment.getNegativeAmount());
						order.getOrderPayment().setNetPaymentResult(
								order.getOrderPayment().getNetPaymentResult()
										- orderPayment.getNegativeAmount());
						order.getOrderPayment()
								.setPaymentDifference(
										order.getOrderPayment()
												.getNetPaymentResult()
												- order.getNetRate()
												+ order.getOrderReturnOrRTO()
														.getReturnOrRTOChargestoBeDeducted());

					}
				}
				order.getOrderPayment().setDateofPayment(
						orderPayment.getDateofPayment());
				order.getOrderPayment().setPaymentCycle(
						orderPayment.getPaymentCycle());
				order.getOrderPayment().setUploadDate(new Date());
				if (order.getOrderPayment().getPaymentDifference() > 0
						|| (int) order.getOrderPayment().getPaymentDifference() == 0) {
					// order.setStatus("OK Payment");
					order.setFinalStatus("Settled");
				} else {
					// order.setStatus("Payment Difference");
					order.setFinalStatus("Actionable");
				}
				System.out.println("order in add payment :   **" + order);
				// To Do - implement netactualrate 2
				order.setLastActivityOnOrder(new Date());
				session.saveOrUpdate(order);
				session.getTransaction().commit();
				session.close();
			}
		} catch (Exception e) {

			log.error(e);
			throw new CustomException(GlobalConstant.addOrderPaymentError,
					new Date(), 1, GlobalConstant.addOrderPaymentErrorCode, e);

			// e.printStackTrace();
		}

		return order;
	}

	// MEthod to add debit notes for PO companies

	@Override
	public void addDebitNote(DebitNoteBean dnBean, int sellerId)
			throws CustomException {

		System.out.println("######## debit note for invoice  id :"
				+ dnBean.getInvoiceId() + " gp id : " + dnBean.getGatePassId()
				+ " po id+ " + dnBean.getPOOrderId());
		Order order = null;

		// To add change order status
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			// Criteria
			// criteria=session.createCriteria(Order.class).add(Restrictions.eq("channelOrderID",
			// channelOrderId));
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
				System.out.println("get order ");
				order = (Order) criteria.list().get(0);
			} else {
				System.out.println(" Null order");
			}
			System.out.println(" PO price : "
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

			if ((int) order.getOrderPayment().getPaymentDifference() != 0) {
				order.setFinalStatus("Actionable");
			} else {
				order.setFinalStatus("Settled");
			}

			session.saveOrUpdate(order);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {

			log.error(e);
			throw new CustomException(GlobalConstant.addDebitNoteError,
					new Date(), 1, GlobalConstant.addDebitNoteErrorCode, e);

			/*
			 * System.out.println(" Getting exception inadd debit note : "+
			 * e.getLocalizedMessage()); e.printStackTrace();
			 */
		}

	}

	// Method to add PO payment

	@Override
	@SuppressWarnings("unchecked")
	public void addPOPayment(PoPaymentBean popaBean, int sellerId)
			throws CustomException {
		System.out.println(" PoPaymentBean note for invoice  id :"
				+ popaBean.getInvoiceID() + " gp id : "
				+ popaBean.getGatePassId() + " po id+ "
				+ popaBean.getPoOrderId());
		// Seller seller = null;
		List<Order> orderlist = null;
		double positiveamount = 0;
		double returnamount = 0;
		boolean positiveOK = false;
		boolean returnOK = false;

		// To add change order status
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			// Criteria
			// criteria=session.createCriteria(Order.class).add(Restrictions.eq("channelOrderID",
			// channelOrderId));
			Criteria criteria = session.createCriteria(Order.class);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.eq("channelOrderID",
							popaBean.getPoOrderId()))
					.add(Restrictions.eq("invoiceID", popaBean.getInvoiceID()))
					.add(Restrictions.eq("pcName", popaBean.getPcName()));

			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			orderlist = criteria.list();

			Criteria amountCriteria = session.createCriteria(Order.class);
			amountCriteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN);
			amountCriteria.createAlias("orderPayment", "orderPayment",
					CriteriaSpecification.LEFT_JOIN);
			amountCriteria
					.createAlias("orderReturnOrRTO", "orderReturnOrRTO",
							CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.eq("channelOrderID",
							popaBean.getPoOrderId()))
					.add(Restrictions.eq("invoiceID", popaBean.getInvoiceID()))
					.add(Restrictions.eq("pcName", popaBean.getPcName()));
			amountCriteria
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			ProjectionList projList = Projections.projectionList();
			projList.add(Projections.sum("orderPayment.negativeAmount"));
			projList.add(Projections.sum("poPrice"));
			projList.add(Projections
					.sum("orderReturnOrRTO.returnOrRTOChargestoBeDeducted"));
			amountCriteria.setProjection(projList);
			List<Object[]> results = amountCriteria.list();
			Iterator iterator1 = results.iterator();
			if (results != null) {
				while (iterator1.hasNext()) {

					Object[] recordsRow = (Object[]) iterator1.next();
					// negativeamount=(Double)recordsRow[0];
					positiveamount = (Double) recordsRow[1];
					returnamount = (Double) recordsRow[2];

				}
			}

			if ((int) positiveamount == (int) popaBean.getPositiveAmount()) {
				positiveOK = true;
			}
			if ((int) returnamount == (int) popaBean.getNegativeAmount()) {
				returnOK = true;
			}
			if (orderlist != null && orderlist.size() != 0) {
				System.out.println("get order ");

				for (Order order : orderlist) {
					if (positiveOK && returnOK) {
						order.setFinalStatus("Settled");
						order.setStatus("PO payment");
						order.getOrderPayment().setDateofPayment(
								popaBean.getPaymentDate());

					} else {
						order.setFinalStatus("Actionable");
						order.setStatus("Inappropriate Payment Recieved");
					}

					order.getOrderPayment().setDateofPayment(
							popaBean.getPaymentDate());
					session.saveOrUpdate(order);
				}
			} else {
				System.out.println(" Null order");
			}

			// session.saveOrUpdate(order);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {

			log.error(e);
			throw new CustomException(GlobalConstant.addPOPaymentError,
					new Date(), 1, GlobalConstant.addPOPaymentErrorCode, e);

			/*
			 * System.out.println(" Getting exception inadd debit note : "+
			 * e.getLocalizedMessage()); e.printStackTrace();
			 */
		}
	}

	@SuppressWarnings("deprecation")
	private Date getreconciledate(Order order, Partner partner, Date orderdate) {
		System.out.println(" Inside reconciled date method");
		System.out.println(" Delivery date :" + order.getDeliveryDate());
		Date reconciledate = new Date();
		int startdate = partner.getStartcycleday();
		// int cycleduration=partner.getPaycycleduration();
		int paydate = partner.getPaydaysfromstartday();
		int currentdate = reconciledate.getDate();
		boolean payfromshippingdate = partner.isPaycyclefromshipordel();
		String paymentType = partner.getPaymentType();
		boolean isIsshippeddatecalc = partner.isIsshippeddatecalc();
		int monthlypaydate = partner.getMonthlypaydate();
		int noofdaysfromshippeddate = partner.getNoofdaysfromshippeddate();
		Date deliverydate = new Date(dateFormat.format(order.getDeliveryDate()));
		Date shippeddate = new Date(dateFormat.format(order.getShippedDate()));
		int orderdeliverydate = deliverydate.getDate();
		int ordershippeddate = shippeddate.getDate();
		int fsd = 0;
		int loop = 0;
		int tempsd = 0;
		int temped = 0;
		int enddate = partner.getPaycycleduration();
		Date paymentCycleStartDate = null;
		Date paymentCycleEndDate = null;
		// Map<String, Date> returndates = new HashMap<>();

		System.out.println(" ORder delivery date in rec 2 : "
				+ order.getDeliveryDate());
		if (paymentType.equals("paymentcycle")) {
			if (payfromshippingdate)
				currentdate = ordershippeddate;
			else
				currentdate = orderdeliverydate;

			while (true) {
				loop++;
				System.out.println("Loop :" + loop + " currentdate :"
						+ currentdate + ">>startdate : " + startdate
						+ ">>enddate :" + enddate);
				if (currentdate > startdate || currentdate == startdate) {
					if (currentdate < enddate || currentdate == enddate
							|| currentdate == startdate) {
						fsd = paydate;
						System.out.println(" Cycle Start Day :" + startdate);
						System.out.println(" Cycle End Day :" + enddate);
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
					System.out.println(" Payent date in previous cycle : ");

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
			System.out.println("reconciledate in paycycle :" + reconciledate);
		} else if (paymentType.equals("datewisepay")) {
			System.out.println(" Datewise payment isIsshippeddatecalc : "
					+ isIsshippeddatecalc + "  noofdaysfromshippeddate : "
					+ noofdaysfromshippeddate);
			System.out.println(" Delivery date : " + deliverydate);
			if (isIsshippeddatecalc) {
				reconciledate = shippeddate;
				reconciledate.setDate(reconciledate.getDate()
						+ noofdaysfromshippeddate);
			} else {
				reconciledate = deliverydate;
				reconciledate.setDate(reconciledate.getDate()
						+ noofdaysfromshippeddate);
			}
			System.out.println(" Reconcile date after datewisepayment : "
					+ reconciledate);
		} else {
			reconciledate = deliverydate;
			reconciledate.setMonth(reconciledate.getMonth() + 1);
			reconciledate.setDate(monthlypaydate);

		}
		System.out.println(" ORder delivery date in rec : "
				+ order.getDeliveryDate());
		return reconciledate;
	}

	// Method to calculate NR
	private boolean calculateNR(Partner partner, Order order, String prodCat,
			float deadWeight, float volWeight) {
		log.info("****Start calculateNR***");
		System.out.println("**Parameters recieved : pcNAme -> "
				+ partner.getPcName() + " channelorderid - > "
				+ order.getChannelOrderID() + " prodCat " + prodCat
				+ "deadWeight " + deadWeight + " volWeight " + volWeight);
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
		/*
		 * String pattern1 = prodCat + "="; String pattern2 = ",";
		 */
		StringBuffer area = new StringBuffer("");
		StringBuffer volarea = new StringBuffer("");
		/*
		 * float deadWieghtCharges = 0; float volWeightCharges = 0;
		 */
		float vwchargetemp = 0;
		float dwchargetemp = 0;
		float shippingCharges = 0;
		String tempStr = null;
		String state = null;
		// String state = order.getCustomer().getCustomerAddress();
		if (partner.getNrnReturnConfig() != null
				&& partner.getNrnReturnConfig().getMetroList() != null
				&& partner.getNrnReturnConfig().getMetroList().length() != 0) {
			state = areaConfigDao.getCityFromZipCode(order.getCustomer()
					.getZipcode());
			System.out.println(" City from zipcode : " + state);
		}
		if (state == null
				|| !(state.equalsIgnoreCase("Chennai")
						|| state.equalsIgnoreCase("Mumbai") || state
							.equalsIgnoreCase("Kolkata"))) {
			state = areaConfigDao.getStateFromZipCode(order.getCustomer()
					.getZipcode());
			System.out.println(" State from zipcode : " + state);
		}
		double SP = order.getOrderSP();
		StringBuffer temp = new StringBuffer("");
		Map<String, Float> chargesMap = new HashMap<String, Float>();
		/* Map<String, Float> returnMap = new HashMap<String, Float>(); */

		List<NRnReturnCharges> chargesList = partner.getNrnReturnConfig()
				.getCharges();
		System.out.println(" Putting values in chargeMap from db : ");
		for (NRnReturnCharges charge : chargesList) {
			chargesMap.put(charge.getChargeName(), charge.getChargeAmount());
		}
		// Extracting comiision value
		try {
			if (partner.getNrnReturnConfig().getCommissionType() != null
					&& partner.getNrnReturnConfig().getCommissionType()
							.equals("fixed")) {
				comission = chargesMap
						.get(GlobalConstant.fixedCommissionPercent);

			} else {
				comission = chargesMap.get(prodCat);
			}

			// Getting Fixed fee
			if (chargesMap.containsKey(GlobalConstant.fixedfeelt250)
					&& chargesMap.get(GlobalConstant.fixedfeelt250).intValue() != 0) {
				if (SP < 251)
					fixedfee = chargesMap.get(GlobalConstant.fixedfeelt250);
				else if (SP > 250 && SP < 501)
					fixedfee = chargesMap
							.containsKey(GlobalConstant.fixedfeegt250lt500) ? chargesMap
							.get(GlobalConstant.fixedfeegt250lt500) : 0;
				else
					fixedfee = chargesMap
							.containsKey(GlobalConstant.fixedfeegt500) ? chargesMap
							.get(GlobalConstant.fixedfeegt500) : 0;
			} else if (chargesMap.containsKey(GlobalConstant.fixedfeelt500)
					&& chargesMap.get(GlobalConstant.fixedfeelt500).intValue() != 0) {
				if (SP < 501)
					fixedfee = chargesMap.get(GlobalConstant.fixedfeelt500);
				else
					fixedfee = chargesMap
							.containsKey(GlobalConstant.fixedfeegt500Big) ? chargesMap
							.get(GlobalConstant.fixedfeegt500Big) : 0;
			} else {
				if (SP < 501)
					fixedfee = chargesMap
							.containsKey(GlobalConstant.fixedfeelt500Big) ? chargesMap
							.get(GlobalConstant.fixedfeelt500Big) : 0;
				else if (SP > 500 && SP < 1001)
					fixedfee = chargesMap
							.containsKey(GlobalConstant.fixedfeegt500lt1000) ? chargesMap
							.get(GlobalConstant.fixedfeegt500lt1000) : 0;
				else if (SP > 1000 && SP < 10001)
					fixedfee = chargesMap
							.containsKey(GlobalConstant.fixedfeegt1000lt10000) ? chargesMap
							.get(GlobalConstant.fixedfeegt1000lt10000) : 0;
				else
					fixedfee = chargesMap
							.containsKey(GlobalConstant.fixedfeegt10000) ? chargesMap
							.get(GlobalConstant.fixedfeegt10000) : 0;

			}

			// Payment collection charges
			if (partner.getNrnReturnConfig().isWhicheverGreaterPCC()) {
				double percentAmount = chargesMap
						.containsKey(GlobalConstant.percentSPPCC) ? chargesMap
						.get(GlobalConstant.percentSPPCC) * SP / 100 : 0;
				if (chargesMap.containsKey(GlobalConstant.fixedAmtPCC)
						&& percentAmount > chargesMap
								.get(GlobalConstant.fixedAmtPCC)) {
					pccAmount = percentAmount;
				} else
					pccAmount = chargesMap
							.containsKey(GlobalConstant.fixedAmtPCC) ? chargesMap
							.get(GlobalConstant.fixedAmtPCC) : 0;

			} else if (chargesMap.containsKey(GlobalConstant.fixedAmtPCC)
					&& chargesMap.get(GlobalConstant.fixedAmtPCC) != 0.0)
				pccAmount = chargesMap.get(GlobalConstant.fixedAmtPCC);

			else
				pccAmount = chargesMap.containsKey(GlobalConstant.percentSPPCC) ? chargesMap
						.get(GlobalConstant.percentSPPCC) * SP / 100
						: 0;

			System.out.println(" States : MetroLsit : "
					+ partner.getNrnReturnConfig().getMetroList()
					+ " national list : "
					+ partner.getNrnReturnConfig().getNationalList()
					+ " LocalList : "
					+ partner.getNrnReturnConfig().getLocalList()
					+ " zonallist: "
					+ partner.getNrnReturnConfig().getZonalList());
			System.out.println(" State we are geting ofrom excel : " + state);

			// ****Shipping charges
			if (partner.getNrnReturnConfig().getShippingFeeType() != null
					&& partner.getNrnReturnConfig().getShippingFeeType()
							.equals("variable")) {
				if (partner.getNrnReturnConfig().getMetroList() != null
						&& partner.getNrnReturnConfig().getMetroList()
								.contains(state)) {
					System.out.println(" Inside ,etro list setting state ");
					area.append("metro");
					volarea.append("metro");
				} else if (partner.getNrnReturnConfig().getNationalList() != null
						&& partner.getNrnReturnConfig().getNationalList()
								.contains(state)) {
					area.append("national");
					volarea.append("national");
				} else if (partner.getNrnReturnConfig().getLocalList() != null
						&& partner.getNrnReturnConfig().getLocalList()
								.contains(state)) {
					area.append("local");
					volarea.append("local");
				} else if (partner.getNrnReturnConfig().getZonalList() != null
						&& partner.getNrnReturnConfig().getZonalList()
								.contains(state)) {
					area.append("zonal");
					volarea.append("zonal");
				}
			} else {
				area.append("fixed");
				volarea.append("fixed");
			}
			if (deadWeight < 501) {
				area.append("dwlt500");
				order.setDwShippingString(area.toString());
				dwchargetemp = chargesMap.containsKey(area.toString()) ? chargesMap
						.get(area.toString()) : 0;

			} else {
				temp = new StringBuffer(area);
				area.append("dwlt500");
				temp.append("dwgt500");
				System.out.println(" Area : " + area + " temp : " + temp);
				dwchargetemp = chargesMap.containsKey(area.toString()) ? chargesMap
						.get(area.toString()) : 0;
				System.out.println(" Charges for lesstthan 500 : "
						+ dwchargetemp);
				float range = (float) Math.ceil((deadWeight - 500) / 500);
				System.out.println(" Range : " + range);
				dwchargetemp = dwchargetemp
						+ (range * (chargesMap.containsKey(temp.toString()) ? chargesMap
								.get(temp.toString()) : 0));
				System.out.println(" Charges for greater than 500 : "
						+ chargesMap.get(temp.toString()));
				order.setDwShippingString(temp.toString());

			}

			System.out.println("volarea  " + volarea);

			if (volWeight < 501) {
				tempStr = volarea.append("vwlt500").toString();
				System.out.println(" tempStr " + tempStr);
				/*
				 * vwchargetemp =
				 * chargesMap.get(volarea.append("vwlt500").toString());
				 */
				vwchargetemp = chargesMap.containsKey(tempStr) ? chargesMap
						.get(tempStr) : 0;
				order.setVolShippingString(tempStr);
			} else if (volWeight > 500 && volWeight < 1001) {
				tempStr = volarea.append("vwgt500lt1000").toString();
				vwchargetemp = chargesMap.containsKey(tempStr) ? chargesMap
						.get(tempStr) : 0;
				order.setVolShippingString(volarea.toString());
			} else if (volWeight > 1000 && volWeight < 1501) {
				tempStr = volarea.append("vwgt1000lt1500").toString();
				vwchargetemp = chargesMap.containsKey(tempStr) ? chargesMap
						.get(tempStr) : 0;
				order.setVolShippingString(volarea.toString());
			} else if (volWeight > 1500 && volWeight < 5001) {
				tempStr = volarea.append("vwgt1500lt5000").toString();
				System.out.println(" tempStr " + tempStr);
				vwchargetemp = chargesMap.containsKey(tempStr) ? chargesMap
						.get(tempStr) : 0;
				order.setVolShippingString(volarea.toString());
			} else if (volWeight > 5000) {
				temp = new StringBuffer(volarea);
				volarea.append("vwgt1500lt5000");
				/*
				 * System.out.println(" Charges for lesstthan 500 : "+dwchargetemp
				 * ); float range = (float) Math.ceil((deadWeight - 500) / 500);
				 * System.out.println(" Range : "+range); dwchargetemp =
				 * dwchargetemp + (range *
				 * (chargesMap.containsKey(temp.toString()) ? chargesMap
				 * .get(temp.toString()) : 0));
				 * System.out.println(" Charges for greater than 500 : "
				 * +chargesMap .get(temp.toString()));
				 */
				vwchargetemp = chargesMap.containsKey(volarea.toString()) ? chargesMap
						.get(volarea.toString()) : 0;
				System.out.println(" vol Charges for lesstthan 500 : "
						+ vwchargetemp);
				temp.append("vwgt5000");
				float range = (float) Math.ceil((volWeight - 5000) / 1000);
				System.out.println("volarea  " + volarea + " temp : " + temp
						+ " range invol: " + range);
				vwchargetemp = vwchargetemp
						+ (range * (chargesMap.containsKey(temp.toString()) ? chargesMap
								.get(temp.toString()) : 0));
				order.setVolShippingString(temp.toString());

			}
			System.out.println(" vwchargetemp : " + vwchargetemp
					+ " dwchargetemp : " + dwchargetemp);
			if (vwchargetemp > dwchargetemp)
				shippingCharges = vwchargetemp;
			else
				shippingCharges = dwchargetemp;
			comission = (float) (comission * SP) / 100;
			serviceTax = (chargesMap.containsKey("serviceTax") ? chargesMap
					.get("serviceTax") : 0)
					* (float) (shippingCharges + pccAmount + fixedfee + comission)
					/ 100;
			nrValue = SP - comission - fixedfee - pccAmount - shippingCharges
					- serviceTax;
			tds = ((comission / 10) + ((fixedfee + pccAmount) / 50))
					* order.getQuantity();
			order.getOrderTax().setTdsToDeduct(tds);
			order.setGrossNetRate(nrValue);
			order.setPartnerCommission(comission);
			order.setFixedfee(fixedfee);
			order.setPccAmount(pccAmount);
			order.setShippingCharges(shippingCharges);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		// Or we can pass order reference in parameterand set these values
		/*
		 * returnMap.put("nrValue", (float) nrValue); returnMap.put("comission",
		 * comission); returnMap.put("fixedfee", fixedfee);
		 * returnMap.put("pccAmount", (float) pccAmount);
		 * returnMap.put("shippingCharges", shippingCharges);
		 */
		System.out.println(" Setting values for Nr in nrcalculator NR: "
				+ order.getNetRate() + ", commison : "
				+ order.getPartnerCommission() + " fixedfee : "
				+ order.getFixedfee() + " pcc : " + order.getPccAmount()
				+ " ->service tax : " + serviceTax + "shippingCharges : "
				+ order.getShippingCharges());
		return true;
	}

	/*
	 * Method to calculate return charges
	 */
	private float calculateReturnCharges(Order order,
			OrderRTOorReturn ordereturn, int sellerId) throws Exception {
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

		Partner partner = partnerService
				.getPartner(order.getPcName(), sellerId);

		String returnType = ordereturn.getType();
		String faultType = ordereturn.getReturnCategory();
		String cancelType = (ordereturn.getCancelType() != null) ? ordereturn
				.getCancelType() : "";

		Map<String, Float> chargesMap = new HashMap<String, Float>();
		List<NRnReturnCharges> chargesList = partner.getNrnReturnConfig()
				.getCharges();
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
				chargesType = partner.getNrnReturnConfig().getRetCharSFType();
				shippingfee = partner.getNrnReturnConfig().isRetCharSFShipFee();
				servicetax = partner.getNrnReturnConfig().isRetCharSFSerTax();
				fixedfee = partner.getNrnReturnConfig().isRetCharSFFF();
				paycollcharges = partner.getNrnReturnConfig().isRetCharSFPCC();
				isRevShippingFee = partner.getNrnReturnConfig()
						.isRetCharSFRevShipFee();
			} else {
				fixedAmount = GlobalConstant.ReturnChargesBuyerReturnFixedAmount;
				varPercentSP = GlobalConstant.ReturnChargesBuyerReturnVariablePercentSP;
				varPercentFixAmt = GlobalConstant.ReturnChargesBuyerReturnVariableFixedAmt;
				chargesType = partner.getNrnReturnConfig().getRetCharBRType();
				shippingfee = partner.getNrnReturnConfig().isRetCharBRShipFee();
				servicetax = partner.getNrnReturnConfig().isRetCharBRSerTax();
				fixedfee = partner.getNrnReturnConfig().isRetCharBRFF();
				paycollcharges = partner.getNrnReturnConfig().isRetCharBRPCC();
			}

			break;
		case "RTOCharges":
			if (faultType.equals(GlobalConstant.SellerFaultString)) {
				fixedAmount = GlobalConstant.RTOChargesSellerFaultFixedAmount;
				varPercentSP = GlobalConstant.RTOChargesSellerFaultVariablePercentSP;
				varPercentFixAmt = GlobalConstant.RTOChargesSellerFaultVariableFixedAmt;
				varPercentPCC = GlobalConstant.RTOChargesSellerFaultVariablePercentPCC;
				chargesType = partner.getNrnReturnConfig().getRTOCharSFType();
				shippingfee = partner.getNrnReturnConfig().isRTOCharSFShipFee();
				servicetax = partner.getNrnReturnConfig().isRTOCharSFSerTax();
				fixedfee = partner.getNrnReturnConfig().isRTOCharSFFF();
				paycollcharges = partner.getNrnReturnConfig().isRTOCharSFPCC();
				isRevShippingFee = partner.getNrnReturnConfig()
						.isRTOCharSFRevShipFee();
			} else {
				fixedAmount = GlobalConstant.RTOChargesBuyerReturnFixedAmount;
				varPercentSP = GlobalConstant.RTOChargesBuyerReturnVariablePercentSP;
				varPercentFixAmt = GlobalConstant.RTOChargesBuyerReturnVariableFixedAmt;
				chargesType = partner.getNrnReturnConfig().getRTOCharBRType();
				shippingfee = partner.getNrnReturnConfig().isRTOCharBRShipFee();
				servicetax = partner.getNrnReturnConfig().isRTOCharBRSerTax();
				fixedfee = partner.getNrnReturnConfig().isRTOCharBRFF();
				paycollcharges = partner.getNrnReturnConfig().isRTOCharBRPCC();
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
				shippingfee = partner.getNrnReturnConfig().isCanCharSFShipFee();
				servicetax = partner.getNrnReturnConfig().isCanCharSFSerTax();
				fixedfee = partner.getNrnReturnConfig().isCanCharSFFF();
				paycollcharges = partner.getNrnReturnConfig().isCanCharSFPCC();
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
				chargesType = partner.getNrnReturnConfig().getCanCharBRType();
				shippingfee = partner.getNrnReturnConfig().isCanCharBRShipFee();
				servicetax = partner.getNrnReturnConfig().isCanCharBRSerTax();
				fixedfee = partner.getNrnReturnConfig().isCanCharBRFF();
				paycollcharges = partner.getNrnReturnConfig().isCanCharBRPCC();
			}

			break;
		case "replacementCharges":
			if (faultType.equals(GlobalConstant.SellerFaultString)) {
				fixedAmount = GlobalConstant.ReplacementChargesSellerFaultFixedAmount;
				varPercentSP = GlobalConstant.ReplacementChargesSellerFaultVariablePercentSP;
				varPercentFixAmt = GlobalConstant.ReplacementChargesSellerFaultVariableFixedAmt;
				varPercentPCC = GlobalConstant.ReplacementChargesSellerFaultVariablePercentPCC;
				chargesType = partner.getNrnReturnConfig().getRepCharSFType();
				shippingfee = partner.getNrnReturnConfig().isRepCharSFShipFee();
				servicetax = partner.getNrnReturnConfig().isRepCharSFSerTax();
				fixedfee = partner.getNrnReturnConfig().isRepCharSFFF();
				paycollcharges = partner.getNrnReturnConfig().isRepCharSFPCC();
				isRevShippingFee = partner.getNrnReturnConfig()
						.isRepCharSFRevShipFee();
			} else {
				fixedAmount = GlobalConstant.ReplacementChargesBuyerReturnFixedAmount;
				varPercentSP = GlobalConstant.ReplacementChargesBuyerReturnVariablePercentSP;
				varPercentFixAmt = GlobalConstant.ReplacementChargesBuyerReturnVariableFixedAmt;
				chargesType = partner.getNrnReturnConfig().getRepCharBRType();
				shippingfee = partner.getNrnReturnConfig().isRepCharBRShipFee();
				servicetax = partner.getNrnReturnConfig().isRepCharBRSerTax();
				fixedfee = partner.getNrnReturnConfig().isRepCharBRFF();
				paycollcharges = partner.getNrnReturnConfig().isRepCharBRPCC();
			}

			break;
		case "partialDeliveryCharges":

			if (faultType.equals(GlobalConstant.SellerFaultString)) {
				fixedAmount = GlobalConstant.PartialDelChargesSellerFaultFixedAmount;
				varPercentSP = GlobalConstant.PartialDelChargesSellerFaultVariablePercentSP;
				varPercentFixAmt = GlobalConstant.PartialDelChargesSellerFaultVariableFixedAmt;
				varPercentPCC = GlobalConstant.PartialDelChargesSellerFaultVariablePercentPCC;
				chargesType = partner.getNrnReturnConfig().getPDCharSFType();
				shippingfee = partner.getNrnReturnConfig().isPDCharSFShipFee();
				servicetax = partner.getNrnReturnConfig().isPDCharSFSerTax();
				fixedfee = partner.getNrnReturnConfig().isPDCharSFFF();
				paycollcharges = partner.getNrnReturnConfig().isPDCharSFPCC();
				isRevShippingFee = partner.getNrnReturnConfig()
						.isPDCharSFRevShipFee();
			} else {
				fixedAmount = GlobalConstant.PartialDelChargesBuyerReturnFixedAmount;
				varPercentSP = GlobalConstant.PartialDelChargesBuyerReturnVariablePercentSP;
				varPercentFixAmt = GlobalConstant.PartialDelChargesBuyerReturnVariableFixedAmt;
				chargesType = partner.getNrnReturnConfig().getPDCharBRType();
				shippingfee = partner.getNrnReturnConfig().isPDCharBRShipFee();
				servicetax = partner.getNrnReturnConfig().isPDCharBRSerTax();
				fixedfee = partner.getNrnReturnConfig().isPDCharBRFF();
				paycollcharges = partner.getNrnReturnConfig().isPDCharBRPCC();
			}
			break;

		default:
			break;
		}

		if (chargesType.equalsIgnoreCase(GlobalConstant.fixedString)) {
			System.out.println(" Fixed amount in return : " + fixedAmount);
			totalcharge = totalcharge
					+ (chargesMap.containsKey(fixedAmount) ? chargesMap
							.get(fixedAmount) : 0);
		} else if (chargesType.equalsIgnoreCase(GlobalConstant.variableString)) {
			System.out.println(" Variable amount in Return : " + varPercentSP
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
			String revShippingType = partner.getNrnReturnConfig()
					.getRevShippingFeeType();
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

		float serviceTax = chargesMap.containsKey("serviceTax") ? chargesMap
				.get("serviceTax") : 0;

		System.out.println(" Total return charge calculated : " + totalcharge
				+ "Reverse shiping fee : " + revShippingFee
				+ " Service tax applied  : " + serviceTax);
		if (serviceTax > 0) {
			totalcharge = totalcharge + (totalcharge * serviceTax) / 100;
		}
		return totalcharge;
	}

	/*
	 * private boolean calculateDeliveryDate(Order order) { boolean
	 * result=false; String pincode=order.getCustomer().getZipcode(); String
	 * statename=areaConfigDao.getStateFromZipCode(pincode); int
	 * deliverydays=sellerD
	 * System.out.println(" State from order :  "+statename); return result; }
	 */

	private boolean calculateDeliveryDate(Order order, int sellerId) {
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
			System.out.println(" Delivery days : " + deliverydays);
			System.out.println(" Shipped date : " + order.getShippedDate());
			System.out.println(" Delivery date : " + order.getDeliveryDate());
			result = true;
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		System.out.println(" State from order :  " + statename);
		return result;
	}

	@Override
	public List<ChannelSalesDetails> findChannelOrdersbyDate(String string,
			Date startDate, Date endDate, int sellerIdfromSession) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
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
		criteria1.createAlias("orderTimeline", "orderTimeline",
				CriteriaSpecification.LEFT_JOIN).add(
				Restrictions.eq("seller.id", sellerIdfromSession));
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
			System.out.println("PC NAME " + temp.getPcName() + "  "
					+ temp.getStartDate() + " " + temp.getOrderId() + "  "
					+ temp.getInvoiceID());
			ttso.add(temp);
		}
		System.out.println("THIS IS THE TOTAL LIST OF ORDERS " + ttso.size());
		return ttso;
	}

	public static void populateChannelSalesDetails(ChannelSalesDetails temp,
			Object[] recordsRow, Date startDate, Date endDate) {

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
		;
		temp.setProductSkuCode(recordsRow[27].toString());
		temp.setTaxCategtory(recordsRow[28].toString());
	}

	public static ProjectionList getPL(String name) {
		ProjectionList projList = Projections.projectionList();

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

		return projList;
	}

	private boolean calculateNR(NRnReturnConfig nrnReturnConfig, Order order,
			String prodCat, float deadWeight, float volWeight) {

		log.info("$$$ calculateNR for Events Start $$$");

		double nrValue = 0;
		float comission = 0;
		float fixedfee = 0;
		double pccAmount = 0;
		float serviceTax = 0;
		double tds = 0;
		/*
		 * String pattern1 = prodCat + "="; String pattern2 = ",";
		 */
		StringBuffer area = new StringBuffer("");
		StringBuffer volarea = new StringBuffer("");
		/*
		 * float deadWieghtCharges = 0; float volWeightCharges = 0;
		 */
		float vwchargetemp = 0;
		float dwchargetemp = 0;
		float shippingCharges = 0;
		String tempStr = null;
		String state = order.getCustomer().getCustomerAddress();
		double SP = order.getOrderSP();
		StringBuffer temp = new StringBuffer("");
		Map<String, Float> chargesMap = new HashMap<String, Float>();
		/* Map<String, Float> returnMap = new HashMap<String, Float>(); */

		List<NRnReturnCharges> chargesList = nrnReturnConfig.getCharges();
		System.out.println(" Putting values in chargeMap from db : ");
		for (NRnReturnCharges charge : chargesList) {
			System.out.println(" Charge : " + charge.getChargeName());
			chargesMap.put(charge.getChargeName(), charge.getChargeAmount());
		}
		// Extracting comiision value
		try {
			if (nrnReturnConfig.getCommissionType() != null
					&& nrnReturnConfig.getCommissionType().equals("fixed")) {
				comission = chargesMap
						.get(GlobalConstant.fixedCommissionPercent);

			} else {
				comission = chargesMap.get(prodCat);
			}

			// Getting Fixed fee
			if (chargesMap.containsKey(GlobalConstant.fixedfeelt250)
					&& chargesMap.get(GlobalConstant.fixedfeelt250).intValue() != 0) {
				if (SP < 251)
					fixedfee = chargesMap.get(GlobalConstant.fixedfeelt250);
				else if (SP > 250 && SP < 501)
					fixedfee = chargesMap
							.containsKey(GlobalConstant.fixedfeegt250lt500) ? chargesMap
							.get(GlobalConstant.fixedfeegt250lt500) : 0;
				else
					fixedfee = chargesMap
							.containsKey(GlobalConstant.fixedfeegt500) ? chargesMap
							.get(GlobalConstant.fixedfeegt500) : 0;
			} else if (chargesMap.containsKey(GlobalConstant.fixedfeelt500)
					&& chargesMap.get(GlobalConstant.fixedfeelt500).intValue() != 0) {
				if (SP < 501)
					fixedfee = chargesMap.get(GlobalConstant.fixedfeelt500);
				else
					fixedfee = chargesMap
							.containsKey(GlobalConstant.fixedfeegt500Big) ? chargesMap
							.get(GlobalConstant.fixedfeegt500Big) : 0;
			} else {
				if (SP < 501)
					fixedfee = chargesMap
							.containsKey(GlobalConstant.fixedfeelt500Big) ? chargesMap
							.get(GlobalConstant.fixedfeelt500Big) : 0;
				else if (SP > 500 && SP < 1001)
					fixedfee = chargesMap
							.containsKey(GlobalConstant.fixedfeegt500lt1000) ? chargesMap
							.get(GlobalConstant.fixedfeegt500lt1000) : 0;
				else if (SP > 1000 && SP < 10001)
					fixedfee = chargesMap
							.containsKey(GlobalConstant.fixedfeegt1000lt10000) ? chargesMap
							.get(GlobalConstant.fixedfeegt1000lt10000) : 0;
				else
					fixedfee = chargesMap
							.containsKey(GlobalConstant.fixedfeegt10000) ? chargesMap
							.get(GlobalConstant.fixedfeegt10000) : 0;

			}

			// Payment collection charges
			if (nrnReturnConfig.isWhicheverGreaterPCC()) {
				double percentAmount = chargesMap
						.containsKey(GlobalConstant.percentSPPCC) ? chargesMap
						.get(GlobalConstant.percentSPPCC) * SP / 100 : 0;
				if (chargesMap.containsKey(GlobalConstant.fixedAmtPCC)
						&& percentAmount > chargesMap
								.get(GlobalConstant.fixedAmtPCC)) {
					pccAmount = percentAmount;
				} else
					pccAmount = chargesMap
							.containsKey(GlobalConstant.fixedAmtPCC) ? chargesMap
							.get(GlobalConstant.fixedAmtPCC) : 0;

			} else if (chargesMap.containsKey(GlobalConstant.fixedAmtPCC)
					&& chargesMap.get(GlobalConstant.fixedAmtPCC) != 0.0)
				pccAmount = chargesMap.get(GlobalConstant.fixedAmtPCC);

			else
				pccAmount = chargesMap.containsKey(GlobalConstant.percentSPPCC) ? chargesMap
						.get(GlobalConstant.percentSPPCC) * SP / 100
						: 0;

			System.out.println(" States : MetroLsit : "
					+ nrnReturnConfig.getMetroList() + " national list : "
					+ nrnReturnConfig.getNationalList() + " LocalList : "
					+ nrnReturnConfig.getLocalList() + " zonallist: "
					+ nrnReturnConfig.getZonalList());
			System.out.println(" State we are geting ofrom excel : " + state);

			// ****Shipping charges
			if (nrnReturnConfig.getShippingFeeType() != null
					&& nrnReturnConfig.getShippingFeeType().equals("variable")) {
				if (nrnReturnConfig.getMetroList() != null
						&& nrnReturnConfig.getMetroList().contains(state)) {
					System.out.println(" Inside ,etro list setting state ");
					area.append("metro");
					volarea.append("metro");
				} else if (nrnReturnConfig.getNationalList() != null
						&& nrnReturnConfig.getNationalList().contains(state)) {
					area.append("national");
					volarea.append("national");
				} else if (nrnReturnConfig.getLocalList() != null
						&& nrnReturnConfig.getLocalList().contains(state)) {
					area.append("local");
					volarea.append("local");
				} else if (nrnReturnConfig.getZonalList() != null
						&& nrnReturnConfig.getZonalList().contains(state)) {
					area.append("zonal");
					volarea.append("zonal");
				}
			} else {
				area.append("fixed");
				volarea.append("fixed");
			}
			if (deadWeight < 501) {
				area.append("dwlt500");
				order.setDwShippingString(area.toString());
				dwchargetemp = chargesMap.containsKey(area.toString()) ? chargesMap
						.get(area.toString()) : 0;

			} else {
				temp = new StringBuffer(area);
				area.append("dwlt500");
				temp.append("dwgt500");
				System.out.println(" Area : " + area + " temp : " + temp);
				dwchargetemp = chargesMap.containsKey(area.toString()) ? chargesMap
						.get(area.toString()) : 0;
				System.out.println(" Charges for lesstthan 500 : "
						+ dwchargetemp);
				float range = (float) Math.ceil((deadWeight - 500) / 500);
				System.out.println(" Range : " + range);
				dwchargetemp = dwchargetemp
						+ (range * (chargesMap.containsKey(temp.toString()) ? chargesMap
								.get(temp.toString()) : 0));
				System.out.println(" Charges for greater than 500 : "
						+ chargesMap.get(temp.toString()));
				order.setDwShippingString(temp.toString());

			}

			System.out.println("volarea  " + volarea);

			if (volWeight < 501) {
				tempStr = volarea.append("vwlt500").toString();
				System.out.println(" tempStr " + tempStr);
				/*
				 * vwchargetemp =
				 * chargesMap.get(volarea.append("vwlt500").toString());
				 */
				vwchargetemp = chargesMap.containsKey(tempStr) ? chargesMap
						.get(tempStr) : 0;
				order.setVolShippingString(tempStr);
			} else if (volWeight > 500 && volWeight < 1001) {
				tempStr = volarea.append("vwgt500lt1000").toString();
				vwchargetemp = chargesMap.containsKey(tempStr) ? chargesMap
						.get(tempStr) : 0;
				order.setVolShippingString(volarea.toString());
			} else if (volWeight > 1000 && volWeight < 1501) {
				tempStr = volarea.append("vwgt1000lt1500").toString();
				vwchargetemp = chargesMap.containsKey(tempStr) ? chargesMap
						.get(tempStr) : 0;
				order.setVolShippingString(volarea.toString());
			} else if (volWeight > 1500 && volWeight < 5001) {
				tempStr = volarea.append("vwgt1500lt5000").toString();
				System.out.println(" tempStr " + tempStr);
				vwchargetemp = chargesMap.containsKey(tempStr) ? chargesMap
						.get(tempStr) : 0;
				order.setVolShippingString(volarea.toString());
			} else if (volWeight > 5000) {
				temp = new StringBuffer(volarea);
				volarea.append("vwgt1500lt5000");

				vwchargetemp = chargesMap.containsKey(volarea.toString()) ? chargesMap
						.get(volarea.toString()) : 0;
				System.out.println(" vol Charges for lesstthan 500 : "
						+ vwchargetemp);
				temp.append("vwgt5000");
				float range = (float) Math.ceil((volWeight - 5000) / 1000);
				System.out.println("volarea  " + volarea + " temp : " + temp
						+ " range invol: " + range);
				vwchargetemp = vwchargetemp
						+ (range * (chargesMap.containsKey(temp.toString()) ? chargesMap
								.get(temp.toString()) : 0));
				order.setVolShippingString(temp.toString());

			}
			System.out.println(" vwchargetemp : " + vwchargetemp
					+ " dwchargetemp : " + dwchargetemp);
			if (vwchargetemp > dwchargetemp)
				shippingCharges = vwchargetemp;
			else
				shippingCharges = dwchargetemp;
			comission = (float) (comission * SP) / 100;
			serviceTax = (chargesMap.containsKey("serviceTax") ? chargesMap
					.get("serviceTax") : 0)
					* (float) (shippingCharges + pccAmount + fixedfee + comission)
					/ 100;
			nrValue = SP - comission - fixedfee - pccAmount - shippingCharges
					- serviceTax;
			tds = ((comission / 10) + ((fixedfee + pccAmount) / 50))
					* order.getQuantity();
			order.getOrderTax().setTdsToDeduct(tds);
			order.setGrossNetRate(nrValue);
			order.setPartnerCommission(comission);
			order.setFixedfee(fixedfee);
			order.setPccAmount(pccAmount);
			order.setShippingCharges(shippingCharges);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		// Or we can pass order reference in parameterand set these values
		/*
		 * returnMap.put("nrValue", (float) nrValue); returnMap.put("comission",
		 * comission); returnMap.put("fixedfee", fixedfee);
		 * returnMap.put("pccAmount", (float) pccAmount);
		 * returnMap.put("shippingCharges", shippingCharges);
		 */
		System.out.println(" Setting values for Nr in nrcalculator NR: "
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
			System.out.println(" Fixed amount in return : " + fixedAmount);
			totalcharge = totalcharge
					+ (chargesMap.containsKey(fixedAmount) ? chargesMap
							.get(fixedAmount) : 0);
		} else if (chargesType.equalsIgnoreCase(GlobalConstant.variableString)) {
			System.out.println(" Variable amount in Return : " + varPercentSP
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

		float serviceTax = chargesMap.containsKey("serviceTax") ? chargesMap
				.get("serviceTax") : 0;

		System.out.println(" Total return charge calculated : " + totalcharge
				+ "Reverse shiping fee : " + revShippingFee
				+ " Service tax applied  : " + serviceTax);
		if (serviceTax > 0) {
			totalcharge = totalcharge + (totalcharge * serviceTax) / 100;
		}
		return totalcharge;
	}

	@Override
	public Order generateConsolidatedOrder(List<Order> orderlist, int sellerId)
			throws CustomException {

		int quantity = 0;
		double orderSP = 0;
		double eossValue = 0;
		double netRate = 0;
		double taxValue = 0;
		double grossPR = 0;
		double grossProfit = 0;

		Order consolidatedOrder = new Order();
		consolidatedOrder.setPO(true);
		consolidatedOrder.setOrderDate(new Date());

		consolidatedOrder.setPcName(orderlist.get(0).getPcName());
		consolidatedOrder.setSubOrderID(orderlist.get(0).getSubOrderID());
		consolidatedOrder.setChannelOrderID(orderlist.get(0)
				.getChannelOrderID());
		consolidatedOrder.setProductSkuCode(orderlist.size() + " SKUs");
		consolidatedOrder.setInvoiceID(orderlist.get(0).getInvoiceID());
		consolidatedOrder.setShippedDate(orderlist.get(0).getShippedDate());

		Seller seller = null;
		Session session = null;
		Partner partner = null;
		try {
			System.out.println(" Before starting the session in orderdaolimpl");
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
				eossValue += order.getEossValue();
				netRate += order.getNetRate();
				taxValue += order.getOrderTax().getTax();
				grossPR += order.getPr();
				grossProfit += order.getGrossProfit();
			}

			if (seller.getPartners() != null
					&& seller.getPartners().size() != 0) {
				partner = seller.getPartners().get(0);
			}

			/* populating derived values of order */
			consolidatedOrder.setStatus("Shipped");
			consolidatedOrder.setQuantity(quantity);
			consolidatedOrder.setOrderSP(orderSP);

			consolidatedOrder.setEossValue(eossValue);

			consolidatedOrder.setNetRate(netRate);

			consolidatedOrder.setOrderTax(new OrderTax());
			consolidatedOrder.getOrderTax().setTax(taxValue);

			consolidatedOrder.setTotalAmountRecieved(consolidatedOrder
					.getNetRate());
			consolidatedOrder.setFinalStatus("In Process");
			// Set Order Timeline
			OrderTimeline timeline = new OrderTimeline();
			// populating tax related values of order
			System.out.println(" Tax before pr:"
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
				System.out.println(" Saving edited order");
				// Code for order timeline
				timeline.setEventDate(new Date());
				timeline.setEvent(" Order Edited");
				consolidatedOrder.getOrderTimeline().add(timeline);
				consolidatedOrder.setLastActivityOnOrder(new Date());
				session.merge(consolidatedOrder);
			} else {
				System.out
						.println(" ****************Saving new  order delivery date :"
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
				/*
				 * for (Order order : orderlist) {
				 * order.setConsolidatedOrderID(consolidatedOrder);
				 * session.merge(order); }
				 */
			}
			session.getTransaction().commit();
			return consolidatedOrder;
		} catch (Exception e) {
			System.out.println("Inside exception in add order "
					+ e.getLocalizedMessage() + " message: " + e.getMessage());
			e.printStackTrace();
			log.error(e);
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
			System.out.println("Inside exception in add order "
					+ e.getLocalizedMessage() + " message: " + e.getMessage());
			e.printStackTrace();
			log.error(e);
			log.info("Error : " + GlobalConstant.addOrderError);
			log.info("Error : " + GlobalConstant.addOrderErrorCode);
			throw new CustomException(GlobalConstant.addOrderError, new Date(),
					1, GlobalConstant.addOrderErrorCode, e);
		} finally {
			session.close();
		}

	}

	@Override
	public Order findPOOrder(String poID, String invoiceID,
			String channelSkuRef, int sellerId) throws CustomException {

		Seller seller = null;
		List<Order> orderlist = null;

		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", sellerId));
			criteria.createAlias("orders", "order",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("subOrderID", poID).ignoreCase())
					.add(Restrictions.eq("invoiceID", invoiceID).ignoreCase())
					.add(Restrictions.eq("productSkuCode", channelSkuRef)
							.ignoreCase())
					.add(Restrictions.eq("order.isPO", true))
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
			log.error(e);
			throw new CustomException(GlobalConstant.findOrdersError,
					new Date(), 2, GlobalConstant.findOrdersErrorcode, e);

			/*
			 * System.out.println(" Exception in find order "+
			 * e.getLocalizedMessage()); e.printStackTrace();
			 */
		}
		return orderlist.get(0);

	}

	@Override
	public void addGatePass(Order order, GatePass gatepass, int sellerId)
			throws CustomException {
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();

			gatepass.setNetNR(order.getGrossNetRate() * gatepass.getQuantity());
			gatepass.setTaxPOAmt(order.getGrossNetRate()
					- (order.getGrossNetRate() * 100 / (100 + order
							.getProductConfig().getTaxPo())));
			gatepass.setRetutnPR(order.getGrossNetRate()
					- gatepass.getTaxPOAmt());
			gatepass.setNetPR(gatepass.getRetutnPR() * gatepass.getQuantity());

			gatepass.setGrossProfit(gatepass.getNetPR()
					- (order.getProductConfig().getProductPrice() * gatepass
							.getQuantity()));

			OrderTimeline timeline = new OrderTimeline();
			timeline.setEvent("Return Recieved");
			timeline.setEventDate(new Date());
			order.getOrderTimeline().add(timeline);
			order.setStatus("Return Recieved");

			productService.updateInventory(order.getProductConfig()
					.getProductSkuCode(), 0, gatepass.getQuantity(), 0, false,
					sellerId);

			order.setGatepass(gatepass);
			session.merge(order);
			session.getTransaction().commit();
			session.close();

		} catch (Exception e) {

			e.printStackTrace();
			log.error(e);
			throw new CustomException(GlobalConstant.addReturnOrderError,
					new Date(), 1, GlobalConstant.addReturnOrderErrorCode, e);

		}

	}
}
