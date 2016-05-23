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
import com.o2r.model.PaymentUpload;
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

	static Logger log = Logger.getLogger(OrderDaoImpl.class.getName());

	@SuppressWarnings("deprecation")
	@Override
	public void addOrder(Order order, int sellerId) throws CustomException {
		
		log.info("*** AddOrder starts ***");
		Seller seller = null;
		Date reconciledate = null;		
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
						log.debug(" after settinf rec date delivery date :"+ order.getDeliveryDate());
					}
					order.setStatus("Shipped");
					if (partner != null && partner.getNrnReturnConfig() != null
							&& partner.getNrnReturnConfig().isNrCalculator()) {

						// Check conditions here....
						event = eventsService.isEventActiive(
								order.getOrderDate(), partner.getPcName(),
								sellerId);
						if (event != null) {

							order.setEventName(event.getEventName());
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
							} else if (event.getNrnReturnConfig()
									.getNrCalculatorEvent()
									.equalsIgnoreCase("original")) {
								if (!calculateNR(seller.getPartners().get(0),
										order, product.getCategoryName(),
										product.getDeadWeight(),
										product.getVolWeight()))
									throw new Exception();
							}

						} else if (!calculateNR(seller.getPartners().get(0),
								order, product.getCategoryName(),
								product.getDeadWeight(), product.getVolWeight()))
							throw new Exception();

						log.debug(" Shipping charges :"
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
					if (event != null) {
						event.setNetSalesAmount(event.getNetSalesAmount()
								+ order.getNetRate());
						eventsService.addEvent(event, sellerId);
					}

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
						log.debug(" Tax cal SP:"
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
					log.debug(" Tax before pr:"	+ order.getOrderTax().getTax());
					order.setPr(order.getNetRate()
							- order.getOrderTax().getTax());
					if (seller.getPartners().get(0).isTdsApplicable()) {
						log.debug(" PC "+ order.getPartnerCommission());
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
					/* checking if customer is available */
					log.debug(" Customer Email id in add order :"
							+ order.getCustomer().getCustomerEmail());
					order.getCustomer().setSellerId(sellerId);
					order.getCustomer().getOrders().add(order);
					
					// Adding order to the Partner
					if (partner.getOrders() != null && order.getOrderId() == 0) {
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
						log.debug("******Saving new  order delivery date : "+ order.getDeliveryDate());
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
					
				} catch (Exception e) {
					log.debug("Inside exception in add order "
							+ e.getLocalizedMessage() + " message: "
							+ e.getMessage());
					e.printStackTrace();
					log.error(e);
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
		log.info("*** AddOrder ends ***");
	}

	@Override
	public Order addPO(Order order, int sellerId) throws CustomException {
		
		log.info("*** addPO Starts ***");
		order.setPoOrder(true);
		order.setOrderDate(new Date());
		Seller seller = null;
		Date reconciledate = null;		
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
					session = sessionFactory.openSession();
					session.beginTransaction();
					Criteria criteria = session.createCriteria(Seller.class)
							.add(Restrictions.eq("id", sellerId));
					criteria.createAlias("partners", "partner",CriteriaSpecification.LEFT_JOIN)
							.add(Restrictions.eq("partner.pcName",order.getPcName()).ignoreCase())
							.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
					seller = (Seller) criteria.list().get(0);
					if (seller.getPartners() != null
							&& seller.getPartners().size() != 0) {
						partner = seller.getPartners().get(0);
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

					order.setOrderMRP(order.getOrderMRP() * order.getQuantity());
					order.setOrderSP(order.getProductConfig().getSp()
							* order.getQuantity());
					order.setPoPrice(order.getPoPrice() * order.getQuantity());

					order.setDiscount(order.getProductConfig().getDiscount());
					order.getOrderTax().setTax(taxvalue);

					TaxDetail taxDetails = new TaxDetail();
					taxDetails.setBalanceRemaining(order.getOrderTax().getTax());
					taxDetails.setUploadDate(order.getOrderDate());
					taxDetailService.addMonthlyTaxDetail(session, taxDetails,sellerId);

					// order.setTotalAmountRecieved(order.getNetRate());
					order.setFinalStatus("In Process");
					// Set Order Timeline
					OrderTimeline timeline = new OrderTimeline();
					// populating tax related values of order
					log.debug(" Tax before pr:"+ order.getOrderTax().getTax());
					order.setPr((order.getGrossNetRate() - order.getOrderTax().getTax()) * order.getQuantity());

					// Reducing Product Inventory For Order
					productService.updateInventory(order.getProductConfig()
							.getProductSkuCode(), 0, 0, order.getQuantity(),
							false, sellerId);
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
						log.debug("*********Saving new  order delivery date :"+ order.getDeliveryDate());
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
					
				} catch (Exception e) {
					log.error(e);
					log.debug("Inside exception in add order "
							+ e.getLocalizedMessage() + " message: "
							+ e.getMessage());
					e.printStackTrace();
				} finally {
					session.close();
				}
			}
			log.info("*** addPO ends ***");
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
			log.error(e);
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
			log.error(e);
			throw new CustomException(GlobalConstant.listOrdersError,
					new Date(), 3, GlobalConstant.listOrdersErrorCode, e);

		}
		log.info("*** listOrders with sellerId n pageNo ends***");
		return returnlist;
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
					.add(Restrictions.isNull("orderReturnOrRTO"));

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
			log.error(e);
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
			log.error(e);
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
			e.printStackTrace();
			log.error(e);
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
			log.error(e);
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
			log.error(e);
			throw new CustomException(GlobalConstant.findOrdersError,
					new Date(), 2, GlobalConstant.findOrdersErrorcode, e);
		}
		log.info("*** getPOOrdersFromConsolidated ends : OrderDaoImpl ***");
		return orderlist;
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
			log.debug(" Deletion process successful :updated"
					+ updated + "orderdelete " + orderdelete);
			session.getTransaction().commit();
			session.close();

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
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

		// To add change order status
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(Restrictions.eq("id", sellerId));
			criteria.createAlias("orders", "order",	CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("order.channelOrderID", channelOrderId))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
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
					if (orderReturn.getReturnorrtoQty() == order.getQuantity())
						order.setGrossProfit(-orderReturn
								.getReturnOrRTOChargestoBeDeducted());
					

					OrderTimeline timeline = new OrderTimeline();
					timeline.setEvent("Return Recieved");
					timeline.setEventDate(new Date());
					order.getOrderTimeline().add(timeline);

				}
				
				if (order.getQuantity() != orderReturn.getReturnorrtoQty()) {
					
					order.setGrossProfit(order.getGrossProfit()
							* orderReturn.getReturnorrtoQty()
							/ order.getQuantity());
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
				order.getOrderTax().setTdsToReturn(
						(order.getOrderTax().getTdsToDeduct() / order
								.getQuantity())
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
		log.info("*** addReturnOrder ends : OrderDaoImpl ***");
	}

	@Override
	public List<Order> findOrders(String column, String value, int sellerId,
			boolean poOrder,boolean isSearch) throws CustomException {
		
		log.info("*** findOrders starts : OrderDaoImpl ***");
		String searchString = "order." + column;

		log.debug(" Inside Find order dao method searchString :"
				+ searchString + " value :" + value + "   sellerId :"
				+ sellerId);

		Seller seller = null;
		List<Order> orderlist = null;
		Criteria criteria = null;

		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			
			if(column.equals("returnOrRTOId")){
				criteria = session.createCriteria(Order.class);
				criteria.createAlias("orderReturnOrRTO", "orderReturnOrRTO",CriteriaSpecification.LEFT_JOIN)					
				.add(Restrictions.like("orderReturnOrRTO.returnOrRTOId",value+"%"));
				if (criteria.list().size() != 0) {
					orderlist=criteria.list();
					if (orderlist != null && orderlist.size() != 0) {
						for (Order order : orderlist) {
							Hibernate.initialize(order.getOrderTimeline());
						}
					}
				}
				return orderlist;				
			}else{
				
				criteria = session.createCriteria(Seller.class).add(Restrictions.eq("id", sellerId));
				criteria.createAlias("orders", "order",CriteriaSpecification.LEFT_JOIN);
				if(isSearch == true){
					criteria.add(Restrictions.like(searchString, value+"%").ignoreCase());
				}else{
					criteria.add(Restrictions.eq(searchString, value));
				}
				criteria.add(Restrictions.eq("order.poOrder", poOrder))
						.addOrder(org.hibernate.criterion.Order.desc("order.lastActivityOnOrder"))
						.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
				
				if (poOrder)
					criteria.add(Restrictions.eq("order.consolidatedOrder",
							null));
				
				
			}
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
			throw new CustomException(GlobalConstant.findOrdersError,new Date(), 2, GlobalConstant.findOrdersErrorcode, e);

		}
		log.info("*** findOrders ends : OrderDaoImpl ***");
		return orderlist;
	}

	@Override
	public List<Order> findOrdersbyDate(String column, Date startDate,
			Date endDate, int sellerId) throws CustomException {
		
		
		log.info("*** findOrdersbyDate starts : OrderDaoImpl ***");
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
			e.printStackTrace();
			log.error(e);
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
			log.error(e);
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
			log.error(e);
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
			e.printStackTrace();
			log.error(e);
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
		
		log.info("*** addOrderPayment starts : OrderDaoImpl ***");
		Order order = null;
		TaxDetail taxDetails = null;
		log.debug("Inside add ordr payment iwtih order id " + orderid);
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Order.class).add(
					Restrictions.eq("orderId", orderid));
			if (criteria.list() != null && criteria.list().size() != 0)
				order = (Order) criteria.list().get(0);
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
								orderPayment.getNegativeAmount());
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
												+ order.getOrderReturnOrRTO()
														.getReturnOrRTOChargestoBeDeducted()
												- order.getTotalAmountRecieved());
						log.debug("payment difference :"+ order.getOrderPayment().getPaymentDifference());

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
								orderPayment.getNegativeAmount());
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
				if (order.getOrderPayment().getPaymentDifference() > 0
						|| (int) order.getOrderPayment().getPaymentDifference() == 0) {
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
			log.error(e);
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
		log.debug(" SKUCODE: " + skucode + "  channedorderid "+ channelOrderId + "sellerId :" + sellerId);
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Order.class);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.eq("channelOrderID", channelOrderId))
					.add(Restrictions.eq("productSkuCode", skucode));

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
								orderPayment.getNegativeAmount());
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
												+ order.getOrderReturnOrRTO()
														.getReturnOrRTOChargestoBeDeducted()
												- order.getTotalAmountRecieved());
						log.debug("payment difference :"+ order.getOrderPayment().getPaymentDifference());

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
								orderPayment.getNegativeAmount());
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
				if (order.getOrderPayment().getPaymentDifference() > 0
						|| (int) order.getOrderPayment().getPaymentDifference() == 0) {
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
			log.error(e);
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

			if ((int) order.getOrderPayment().getPaymentDifference() != 0) {
				order.setFinalStatus("Actionable");
			} else {
				order.setFinalStatus("Settled");
			}

			session.saveOrUpdate(order);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			throw new CustomException(GlobalConstant.addDebitNoteError,
					new Date(), 1, GlobalConstant.addDebitNoteErrorCode, e);			
		}
		log.info("*** addDebitNote ends : OrderDaoImpl ***");
	}

	// Method to add PO payment
	@Override
	public void addPOPayment(PoPaymentBean popaBean, int sellerId)
			throws CustomException {
		
		log.info("*** addPOPayment starts : OrderDaoImpl ***");
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();

			String orderID = null;
			boolean isGP = false;
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

			OrderPayment orderPayment = new OrderPayment();
			orderPayment.setUploadDate(new Date());
			orderPayment.setDateofPayment(popaBean.getPaymentDate());

			if (!isGP) {
				orderPayment.setPositiveAmount(popaBean.getPositiveAmount());
				if (popaBean.getPositiveAmount() == poOrder.getNetRate()) {
					paymentOK = true;
				} else {
					paymentDiff = popaBean.getPositiveAmount()
							- poOrder.getNetRate();
				}
			} else {
				orderPayment.setNegativeAmount(popaBean.getNegativeAmount());
				if (popaBean.getNegativeAmount() == poOrder
						.getOrderReturnOrRTO()
						.getReturnOrRTOChargestoBeDeducted()) {
					paymentOK = true;
				} else {
					paymentDiff = poOrder.getOrderReturnOrRTO()
							.getReturnOrRTOChargestoBeDeducted()
							- popaBean.getNegativeAmount();
				}
			}

			orderPayment.setPaymentDifference(paymentDiff);
			orderPayment.setNetPaymentResult(popaBean.getNpr());
			orderPayment.setPaymentdesc(popaBean.getSellerNote());

			if (paymentOK) {
				poOrder.setFinalStatus("Settled");
				poOrder.setStatus("PO payment");
			} else {
				poOrder.setFinalStatus("Actionable");
				poOrder.setStatus("Inappropriate Payment Recieved");
			}

			poOrder.setOrderPayment(orderPayment);
			session.saveOrUpdate(poOrder);

			session.getTransaction().commit();
			session.close();

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			throw new CustomException(GlobalConstant.addPOPaymentError,
					new Date(), 1, GlobalConstant.addPOPaymentErrorCode, e);
		}
		log.info("*** addPOPayment ends : OrderDaoImpl ***");
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
			
			
			log.debug(" ORder delivery date in rec 2 : "+ order.getDeliveryDate());
			if (paymentType.equals("paymentcycle")) {
				if (payfromshippingdate)
					currentdate = ordershippeddate;
				else
					currentdate = orderdeliverydate;

				while (true) {
					loop++;
					log.debug("Loop :" + loop + " currentdate :"
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
			log.error(e);
		}		
		log.debug(" ORder delivery date in rec : "+ order.getDeliveryDate());
		log.info("*** getreconciledate ends : OrderDaoImpl ***");
		return reconciledate;
	}

	// Method to calculate NR
	private boolean calculateNR(Partner partner, Order order, String prodCat,float deadWeight, float volWeight) {
		
		log.info("*** calculateNR starts : OrderDaoImpl ***");
		log.debug("**Parameters recieved : pcNAme -> "
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
		
		StringBuffer area = new StringBuffer("");
		StringBuffer volarea = new StringBuffer("");
		
		float vwchargetemp = 0;
		float dwchargetemp = 0;
		float shippingCharges = 0;
		String tempStr = null;
		String state = null;
		
		if (partner.getNrnReturnConfig() != null
				&& partner.getNrnReturnConfig().getMetroList() != null
				&& partner.getNrnReturnConfig().getMetroList().length() != 0) {
			state = areaConfigDao.getCityFromZipCode(order.getCustomer()
					.getZipcode());
			log.debug(" City from zipcode : " + state);
		}
		if (state == null
				|| !(state.equalsIgnoreCase("Chennai")
						|| state.equalsIgnoreCase("Mumbai") || state
							.equalsIgnoreCase("Kolkata"))) {
			state = areaConfigDao.getStateFromZipCode(order.getCustomer()
					.getZipcode());
			log.debug(" State from zipcode : " + state);
		}
		double SP = order.getOrderSP();
		StringBuffer temp = new StringBuffer("");
		Map<String, Float> chargesMap = new HashMap<String, Float>();
		/* Map<String, Float> returnMap = new HashMap<String, Float>(); */

		List<NRnReturnCharges> chargesList = partner.getNrnReturnConfig().getCharges();
		
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
			if (partner.getNrnReturnConfig().getShippingFeeType() != null
					&& partner.getNrnReturnConfig().getShippingFeeType()
							.equals("variable")) {
				if (partner.getNrnReturnConfig().getMetroList() != null
						&& partner.getNrnReturnConfig().getMetroList()
								.contains(state)) {
					
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
				log.debug(" Area : " + area + " temp : " + temp);
				dwchargetemp = chargesMap.containsKey(area.toString()) ? chargesMap
						.get(area.toString()) : 0;
				log.debug(" Charges for lesstthan 500 : "+ dwchargetemp);
				float range = (float) Math.ceil((deadWeight - 500) / 500);
				log.debug(" Range : " + range);
				dwchargetemp = dwchargetemp
						+ (range * (chargesMap.containsKey(temp.toString()) ? chargesMap
								.get(temp.toString()) : 0));
				log.debug(" Charges for greater than 500 : "+ chargesMap.get(temp.toString()));
				order.setDwShippingString(temp.toString());

			}

			log.debug("volarea  " + volarea);

			if (volWeight < 501) {
				tempStr = volarea.append("vwlt500").toString();
				log.debug(" tempStr " + tempStr);
				
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
				log.debug(" tempStr " + tempStr);
				vwchargetemp = chargesMap.containsKey(tempStr) ? chargesMap
						.get(tempStr) : 0;
				order.setVolShippingString(volarea.toString());
			} else if (volWeight > 5000) {
				temp = new StringBuffer(volarea);
				volarea.append("vwgt1500lt5000");
				
				vwchargetemp = chargesMap.containsKey(volarea.toString()) ? chargesMap
						.get(volarea.toString()) : 0;
				log.debug(" vol Charges for lesstthan 500 : "+ vwchargetemp);
				temp.append("vwgt5000");
				float range = (float) Math.ceil((volWeight - 5000) / 1000);
				log.debug("volarea  " + volarea + " temp : " + temp + " range invol: " + range);
				vwchargetemp = vwchargetemp
						+ (range * (chargesMap.containsKey(temp.toString()) ? chargesMap
								.get(temp.toString()) : 0));
				order.setVolShippingString(temp.toString());

			}
			log.debug(" vwchargetemp : " + vwchargetemp + " dwchargetemp : " + dwchargetemp);
			if (vwchargetemp > dwchargetemp)
				shippingCharges = vwchargetemp;
			else
				shippingCharges = dwchargetemp;
			comission = (float) (comission * SP) / 100;
			serviceTax = (chargesMap.containsKey("serviceTax") ? chargesMap
					.get("serviceTax") : 0)
					* (float) (shippingCharges + pccAmount + fixedfee + comission)/ 100;
			nrValue = SP - comission - fixedfee - pccAmount - shippingCharges
					- serviceTax;
			tds = ((comission / 10) + ((fixedfee + pccAmount + shippingCharges) / 50))
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

		log.debug(" Total return charge calculated : " + totalcharge
				+ "Reverse shiping fee : " + revShippingFee
				+ " Service tax applied  : " + serviceTax);
		order.setServiceTax(serviceTax);
		if (serviceTax > 0) {
			totalcharge = totalcharge + (totalcharge * serviceTax) / 100;
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
			log.debug("PC NAME " + temp.getPcName() + "  "
					+ temp.getStartDate() + " " + temp.getOrderId() + "  "
					+ temp.getInvoiceID());
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
		;
		temp.setProductSkuCode(recordsRow[27].toString());
		temp.setTaxCategtory(recordsRow[28].toString());
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
		
		log.info("*** getPL ends : OrderDaoImpl ***");
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

			log.debug(" States : MetroLsit : "
					+ nrnReturnConfig.getMetroList() + " national list : "
					+ nrnReturnConfig.getNationalList() + " LocalList : "
					+ nrnReturnConfig.getLocalList() + " zonallist: "
					+ nrnReturnConfig.getZonalList());
			log.debug(" State we are geting ofrom excel : " + state);

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
				log.debug(" Area : " + area + " temp : " + temp);
				dwchargetemp = chargesMap.containsKey(area.toString()) ? chargesMap
						.get(area.toString()) : 0;
				log.debug(" Charges for lesstthan 500 : "
						+ dwchargetemp);
				float range = (float) Math.ceil((deadWeight - 500) / 500);
				log.debug(" Range : " + range);
				dwchargetemp = dwchargetemp
						+ (range * (chargesMap.containsKey(temp.toString()) ? chargesMap
								.get(temp.toString()) : 0));
				log.debug(" Charges for greater than 500 : "+ chargesMap.get(temp.toString()));
				order.setDwShippingString(temp.toString());

			}


			if (volWeight < 501) {
				tempStr = volarea.append("vwlt500").toString();
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
				log.debug(" tempStr " + tempStr);
				vwchargetemp = chargesMap.containsKey(tempStr) ? chargesMap
						.get(tempStr) : 0;
				order.setVolShippingString(volarea.toString());
			} else if (volWeight > 5000) {
				temp = new StringBuffer(volarea);
				volarea.append("vwgt1500lt5000");

				vwchargetemp = chargesMap.containsKey(volarea.toString()) ? chargesMap
						.get(volarea.toString()) : 0;
				log.debug(" vol Charges for lesstthan 500 : "
						+ vwchargetemp);
				temp.append("vwgt5000");
				float range = (float) Math.ceil((volWeight - 5000) / 1000);
				log.debug("volarea  " + volarea + " temp : " + temp
						+ " range invol: " + range);
				vwchargetemp = vwchargetemp
						+ (range * (chargesMap.containsKey(temp.toString()) ? chargesMap
								.get(temp.toString()) : 0));
				order.setVolShippingString(temp.toString());

			}
			log.debug(" vwchargetemp : " + vwchargetemp
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

		float serviceTax = chargesMap.containsKey("serviceTax") ? chargesMap
				.get("serviceTax") : 0;

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

		Order consolidatedOrder = new Order();
		consolidatedOrder.setPoOrder(true);
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
			log.error(e);
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
			log.error(e);
			throw new CustomException(GlobalConstant.findOrdersError,
					new Date(), 2, GlobalConstant.findOrdersErrorcode, e);

		}
	}

	@Override
	public GatePass addGatePass(ProductConfig productConfig, GatePass gatepass,
			int sellerId) throws CustomException {
		
		log.info("$$$ addGatePass Starts : OrderDaoImpl $$$");
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();

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

			TaxDetail taxDetails = new TaxDetail();
			taxDetails.setBalanceRemaining(-(gatepass.getTaxPOAmt())
					* gatepass.getQuantity());
			taxDetails.setUploadDate(gatepass.getReturnDate());
			taxDetailService.addMonthlyTaxDetail(session, taxDetails, sellerId);


			productService.updateInventory(productConfig.getProductSkuCode(),
					0, gatepass.getQuantity(), 0, false, sellerId);

			session.saveOrUpdate(gatepass);
			session.getTransaction().commit();
			session.close();
			
		} catch (Exception e) {

			e.printStackTrace();
			log.error(e);
			throw new CustomException(GlobalConstant.addReturnOrderError,
					new Date(), 1, GlobalConstant.addReturnOrderErrorCode, e);
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
			log.error(e);
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
		double taxValue = 0;
		double grossPR = 0;
		double grossProfit = 0;

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

			for (GatePass gatepass : gatepasslist) {

				ProductConfig productConfig = productService.getProductConfig(
						gatepass.getChannelSkuRef(), gatepass.getPcName(),
						sellerId);
				if (productConfig != null) {
					quantity += gatepass.getQuantity();
					totalReturnCharges += gatepass.getTotalReturnCharges();
					if (gatepass.getPcName().equalsIgnoreCase(
							GlobalConstant.PCMYNTRA)) {
						eossValue += (productConfig.getSuggestedPOPrice() * productConfig
								.getDiscount()) / 100;
					}
					netRate += gatepass.getNetNR();
					taxValue += gatepass.getTaxAmt();
					grossPR += gatepass.getNetPR();
					grossProfit += gatepass.getGrossProfit();
				}
			}

			if (seller.getPartners() != null
					&& seller.getPartners().size() != 0) {
				partner = seller.getPartners().get(0);
			}

			/* populating derived values of order */
			consolidatedOrder.setStatus("Return Recieved");

			consolidateReturn.setReturnorrtoQty(quantity);
			consolidateReturn
					.setReturnOrRTOChargestoBeDeducted(totalReturnCharges);
			consolidateReturn.setNetNR(netRate);
			consolidateReturn.setTaxPOAmt(taxValue);
			consolidateReturn.setNetPR(grossPR);
			consolidateReturn.setGrossProfit(grossProfit);

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
				log.debug(" ****************Saving new  order delivery date :"+ consolidatedOrder.getDeliveryDate());
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
							+ e.getLocalizedMessage() + " message: "
							+ e.getMessage());
			e.printStackTrace();
			log.error(e);
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
			log.error(e);
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
			log.error(e);
			throw new CustomException(GlobalConstant.findOrdersError,
					new Date(), 2, GlobalConstant.findOrdersErrorcode, e);

		}
		
		log.info("$$$ findConsolidatedPO Ends : OrderDaoImpl $$$");
		return poOrder;
	}

}
