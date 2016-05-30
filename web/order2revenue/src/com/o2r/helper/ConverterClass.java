package com.o2r.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.o2r.bean.AccountTransactionBean;
import com.o2r.bean.BusinessDetails;
import com.o2r.bean.CategoryBean;
import com.o2r.bean.ChannelReportDetails;
import com.o2r.bean.CommissionDetails;
import com.o2r.bean.CustomerBean;
import com.o2r.bean.DebtorsGraph1;
import com.o2r.bean.EventsBean;
import com.o2r.bean.ExpenseBean;
import com.o2r.bean.ExpenseCategoryBean;
import com.o2r.bean.ManualChargesBean;
import com.o2r.bean.OrderBean;
import com.o2r.bean.OrderPaymentBean;
import com.o2r.bean.OrderRTOorReturnBean;
import com.o2r.bean.OrderTaxBean;
import com.o2r.bean.PartnerBean;
import com.o2r.bean.PartnerReportDetails;
import com.o2r.bean.PaymentUploadBean;
import com.o2r.bean.PlanBean;
import com.o2r.bean.ProductBean;
import com.o2r.bean.ProductConfigBean;
import com.o2r.bean.SellerAccountBean;
import com.o2r.bean.SellerBean;
import com.o2r.bean.StateBean;
import com.o2r.bean.StateDeliveryTimeBean;
import com.o2r.bean.TaxCategoryBean;
import com.o2r.bean.TaxDetailBean;
import com.o2r.model.AccountTransaction;
import com.o2r.model.Category;
import com.o2r.model.Customer;
import com.o2r.model.Events;
import com.o2r.model.ExpenseCategory;
import com.o2r.model.Expenses;
import com.o2r.model.ManualCharges;
import com.o2r.model.Order;
import com.o2r.model.OrderPayment;
import com.o2r.model.OrderRTOorReturn;
import com.o2r.model.OrderTax;
import com.o2r.model.Partner;
import com.o2r.model.PaymentUpload;
import com.o2r.model.Plan;
import com.o2r.model.Product;
import com.o2r.model.ProductConfig;
import com.o2r.model.Seller;
import com.o2r.model.SellerAccount;
import com.o2r.model.State;
import com.o2r.model.StateDeliveryTime;
import com.o2r.model.TaxCategory;
import com.o2r.model.TaxDetail;

public class ConverterClass {

	public static Order prepareModel(OrderBean orderBean) {
		Order order = new Order();
		if (orderBean != null) {
			order.setAwbNum(orderBean.getAwbNum());
			order.setGeOrderId(orderBean.getGeOrderId());
			order.setOrderDate(orderBean.getOrderDate());
			order.setPcName(orderBean.getPcName());
			order.setStatus(orderBean.getStatus());
			order.setOrderId(orderBean.getOrderId());
			order.setPaymentDueDate(orderBean.getPaymentDueDate());
			order.setProductSkuCode(orderBean.getProductSkuCode());
			order.setChannelOrderID(orderBean.getChannelOrderID());
			order.setInvoiceID(orderBean.getInvoiceID());
			order.setSubOrderID(orderBean.getSubOrderID());
			order.setPIreferenceNo(orderBean.getPIreferenceNo());
			order.setLogisticPartner(orderBean.getLogisticPartner());
			order.setOrderMRP(orderBean.getOrderMRP());
			order.setGrossProfit(orderBean.getGrossProfit());
			order.setOrderSP(orderBean.getOrderSP());
			order.setShippingCharges(orderBean.getShippingCharges());
			order.setShippedDate(orderBean.getShippedDate());
			order.setDeliveryDate(orderBean.getDeliveryDate());
			order.setLastActivityOnOrder(orderBean.getLastActivityOnOrder());
			order.setNetRate(orderBean.getNetRate());
			order.setCustomer(prepareCustomerModel((orderBean.getCustomer() != null) ? orderBean
					.getCustomer() : (new CustomerBean())));
			order.setDiscount(orderBean.getDiscount());
			order.setOrderPayment(prepareOrderPaymentModel(orderBean
					.getOrderPayment()));
			order.setOrderReturnOrRTO(prepareOrderRTOorReturnModel(orderBean
					.getOrderReturnOrRTO()));
			order.setOrderTax(prepareOrderTaxModel(orderBean.getOrderTax()));
			order.setTotalAmountRecieved(orderBean.getTotalAmountRecieved());
			order.setQuantity(orderBean.getQuantity());
			order.setPr(orderBean.getPr());
			order.setNetSaleQuantity(orderBean.getNetSaleQuantity());
			order.setUniqueItemId(orderBean.getUniqueItemId());
			order.setPartnerCommission(orderBean.getPartnerCommission());
			order.setSellerNote(orderBean.getSellerNote());
			order.setPaymentType(orderBean.getPaymentType());
			order.setFinalStatus(orderBean.getFinalStatus());
			order.setOrderTimeline(orderBean.getOrderTimeline());
			order.setReturnLimitCrossed(orderBean.getReturnLimitCrossed());
			order.setrTOLimitCrossed(orderBean.getrTOLimitCrossed());
			order.setSealNo(orderBean.getSealNo());
			order.setPoPrice(orderBean.getPoPrice());
			order.setGrossNetRate(orderBean.getGrossNetRate());
			order.setPccAmount(orderBean.getPccAmount());
			order.setServiceTax(orderBean.getServiceTax());
			order.setFixedfee(orderBean.getFixedfee());
			order.setEventName(orderBean.getEventName());
		}
		return order;
	}

	public static List<Order> prepareListofOrdersFromBean(
			List<OrderBean> orderBeans) {
		List<Order> orders = null;
		if (orderBeans != null && !orderBeans.isEmpty()) {
			orders = new ArrayList<Order>();
			Order order = null;
			for (OrderBean orderBean : orderBeans) {
				order = new Order();
				order.setAwbNum(orderBean.getAwbNum());
				order.setGeOrderId(orderBean.getGeOrderId());
				order.setOrderDate(orderBean.getOrderDate());
				order.setPcName(orderBean.getPcName());
				order.setStatus(orderBean.getStatus());
				order.setOrderId(orderBean.getOrderId());
				order.setPaymentDueDate(orderBean.getPaymentDueDate());
				order.setProductSkuCode(orderBean.getProductSkuCode());
				order.setChannelOrderID(orderBean.getChannelOrderID());
				order.setInvoiceID(orderBean.getInvoiceID());
				order.setSubOrderID(orderBean.getSubOrderID());
				order.setPIreferenceNo(orderBean.getPIreferenceNo());
				order.setLogisticPartner(orderBean.getLogisticPartner());
				order.setOrderMRP(orderBean.getOrderMRP());
				order.setOrderSP(orderBean.getOrderSP());
				order.setShippingCharges(orderBean.getShippingCharges());
				order.setShippedDate(orderBean.getShippedDate());
				order.setDeliveryDate(orderBean.getDeliveryDate());
				order.setGrossProfit(orderBean.getGrossProfit());
				order.setLastActivityOnOrder(orderBean.getLastActivityOnOrder());
				order.setNetRate(orderBean.getNetRate());
				order.setCustomer(prepareCustomerModel((orderBean.getCustomer() != null) ? orderBean
						.getCustomer() : (new CustomerBean())));
				order.setDiscount(orderBean.getDiscount());
				order.setOrderPayment(prepareOrderPaymentModel(orderBean
						.getOrderPayment()));
				order.setOrderReturnOrRTO(prepareOrderRTOorReturnModel(orderBean
						.getOrderReturnOrRTO()));
				order.setOrderTax(prepareOrderTaxModel(orderBean.getOrderTax()));
				order.setTotalAmountRecieved(orderBean.getTotalAmountRecieved());
				order.setQuantity(orderBean.getQuantity());
				order.setPr(orderBean.getPr());
				order.setGrossNetRate(orderBean.getGrossNetRate());
				order.setNetSaleQuantity(orderBean.getNetSaleQuantity());
				order.setUniqueItemId(orderBean.getUniqueItemId());
				order.setPartnerCommission(orderBean.getPartnerCommission());
				order.setSellerNote(orderBean.getSellerNote());
				order.setPaymentType(orderBean.getPaymentType());
				order.setFinalStatus(orderBean.getFinalStatus());
				order.setOrderTimeline(orderBean.getOrderTimeline());
				order.setReturnLimitCrossed(orderBean.getReturnLimitCrossed());
				order.setrTOLimitCrossed(orderBean.getrTOLimitCrossed());
				order.setSealNo(orderBean.getSealNo());
				order.setPoPrice(orderBean.getPoPrice());
				order.setPccAmount(orderBean.getPccAmount());
				order.setServiceTax(orderBean.getServiceTax());
				order.setFixedfee(orderBean.getFixedfee());
				order.setEventName(orderBean.getEventName());

				orders.add(order);
			}
		}
		return orders;
	}

	public static List<OrderBean> prepareListofBean(List<Order> orders) {
		List<OrderBean> beans = null;
		if (orders != null && !orders.isEmpty()) {
			beans = new ArrayList<OrderBean>();
			OrderBean bean = null;
			for (Order order : orders) {
				bean = new OrderBean();
				bean.setAwbNum(order.getAwbNum());
				bean.setGeOrderId(order.getGeOrderId());
				bean.setOrderDate(order.getOrderDate());
				bean.setPcName(order.getPcName());
				bean.setStatus(order.getStatus());
				bean.setOrderId(order.getOrderId());
				bean.setPaymentDueDate(order.getPaymentDueDate());
				bean.setProductSkuCode(order.getProductSkuCode());
				bean.setChannelOrderID(order.getChannelOrderID());
				bean.setInvoiceID(order.getInvoiceID());
				bean.setSubOrderID(order.getSubOrderID());
				bean.setPIreferenceNo(order.getPIreferenceNo());
				bean.setLogisticPartner(order.getLogisticPartner());
				bean.setOrderMRP(order.getOrderMRP());
				bean.setGrossProfit(order.getGrossProfit());
				bean.setOrderSP(order.getOrderSP());
				bean.setShippingCharges(order.getShippingCharges());
				bean.setShippedDate(order.getShippedDate());
				bean.setDeliveryDate(order.getDeliveryDate());
				bean.setLastActivityOnOrder(order.getLastActivityOnOrder());
				bean.setNetRate(order.getNetRate());
				bean.setCustomer(prepareCustomerBean((order.getCustomer() != null) ? order
						.getCustomer() : (new Customer())));
				bean.setDiscount(order.getDiscount());
				bean.setOrderPayment(prepareOrderPaymentBean(order
						.getOrderPayment()));
				bean.setOrderReturnOrRTO(prepareOrderRTOorReturnBean(order
						.getOrderReturnOrRTO()));
				bean.setOrderTax(prepareOrderTaxBean(order.getOrderTax()));
				bean.setTotalAmountRecieved(order.getTotalAmountRecieved());
				bean.setQuantity(order.getQuantity());
				bean.setPr(order.getPr());
				bean.setGrossNetRate(order.getGrossNetRate());
				bean.setNetSaleQuantity(order.getNetSaleQuantity());
				bean.setUniqueItemId(order.getUniqueItemId());
				bean.setPartnerCommission(order.getPartnerCommission());
				bean.setSellerNote(order.getSellerNote());
				bean.setPaymentType(order.getPaymentType());
				bean.setFinalStatus(order.getFinalStatus());
				// bean.setOrderTimeline(order.getOrderTimeline());
				bean.setReturnLimitCrossed(order.getReturnLimitCrossed());
				bean.setrTOLimitCrossed(order.getrTOLimitCrossed());
				bean.setSealNo(order.getSealNo());
				bean.setPoPrice(order.getPoPrice());
				bean.setPccAmount(order.getPccAmount());
				bean.setServiceTax(order.getServiceTax());
				bean.setFixedfee(order.getFixedfee());
				bean.setEossValue(order.getEossValue());
				bean.setEventName(order.getEventName());
				if (order.getProductConfig() != null)
					bean.setProductConfig(prepareProductConfigBean(order.getProductConfig()));
				beans.add(bean);
			}
		}
		return beans;
	}

	public static OrderBean prepareOrderBean(Order order) {
		OrderBean bean = new OrderBean();
		if (order != null) {
			bean.setAwbNum(order.getAwbNum());
			bean.setGeOrderId(order.getGeOrderId());
			bean.setOrderDate(order.getOrderDate());
			bean.setPcName(order.getPcName());
			bean.setStatus(order.getStatus());
			bean.setOrderId(order.getOrderId());
			bean.setPaymentDueDate(order.getPaymentDueDate());
			bean.setProductSkuCode(order.getProductSkuCode());
			bean.setChannelOrderID(order.getChannelOrderID());
			bean.setInvoiceID(order.getInvoiceID());
			bean.setSubOrderID(order.getSubOrderID());
			bean.setPIreferenceNo(order.getPIreferenceNo());
			bean.setLogisticPartner(order.getLogisticPartner());
			bean.setOrderMRP(order.getOrderMRP());
			bean.setOrderSP(order.getOrderSP());
			bean.setGrossProfit(order.getGrossProfit());
			bean.setShippingCharges(order.getShippingCharges());
			bean.setShippedDate(order.getShippedDate());
			bean.setDeliveryDate(order.getDeliveryDate());
			bean.setLastActivityOnOrder(order.getLastActivityOnOrder());
			bean.setNetRate(order.getNetRate());
			bean.setCustomer(prepareCustomerBean((order.getCustomer() != null) ? order
					.getCustomer() : (new Customer())));
			bean.setDiscount(order.getDiscount());
			bean.setOrderPayment(prepareOrderPaymentBean(order
					.getOrderPayment()));
			bean.setOrderReturnOrRTO(prepareOrderRTOorReturnBean(order
					.getOrderReturnOrRTO()));
			bean.setOrderTax(prepareOrderTaxBean(order.getOrderTax()));
			bean.setTotalAmountRecieved(order.getTotalAmountRecieved());
			bean.setQuantity(order.getQuantity());
			bean.setPr(order.getPr());
			bean.setGrossNetRate(order.getGrossNetRate());
			bean.setNetSaleQuantity(order.getNetSaleQuantity());
			bean.setUniqueItemId(order.getUniqueItemId());
			bean.setPartnerCommission(order.getPartnerCommission());
			bean.setSellerNote(order.getSellerNote());
			bean.setPaymentType(order.getPaymentType());
			bean.setFinalStatus(order.getFinalStatus());
			bean.setOrderTimeline(order.getOrderTimeline());
			bean.setReturnLimitCrossed(order.getReturnLimitCrossed());
			bean.setrTOLimitCrossed(order.getrTOLimitCrossed());
			bean.setSealNo(order.getSealNo());
			bean.setPoPrice(order.getPoPrice());
			bean.setEventName(order.getEventName());
		}
		return bean;
	}

	public static Seller prepareSellerModel(SellerBean sellerBean) {
		Seller seller = new Seller();
		if (sellerBean != null) {
			seller.setAddress(sellerBean.getAddress());
			seller.setCompanyName(sellerBean.getCompanyName());
			seller.setEmail(sellerBean.getEmail());
			seller.setName(sellerBean.getName());
			// seller.setOrders(prepareListofOrdersFromBean(sellerBean.getOrders()));
			seller.setId(sellerBean.getId());
			seller.setPassword(sellerBean.getPassword());
			seller.setContactNo(sellerBean.getContactNo());
			seller.setBrandName(sellerBean.getBrandName());
			seller.setLogoUrl(sellerBean.getLogoUrl());
			seller.setTinNumber(sellerBean.getTinNumber());
			seller.setTanNumber(sellerBean.getTanNumber());
			seller.setRole(sellerBean.getRole());
			seller.setStateDeliveryTime(sellerBean.getStateDeliveryTime());
		}
		return seller;
	}
	
	public static List<StateDeliveryTime> prepareStateDeliveryTimeModel(List<StateDeliveryTimeBean> stateDeliveryTimeBean) {
		List<StateDeliveryTime> models = null;
		if (stateDeliveryTimeBean != null && !stateDeliveryTimeBean.isEmpty()) {
			models = new ArrayList<StateDeliveryTime>();
			StateDeliveryTime model = null;
			for (StateDeliveryTimeBean aStateDeliveryTimeBean : stateDeliveryTimeBean) {
				model = new StateDeliveryTime();
				model.setDeliveryTime(aStateDeliveryTimeBean.getDeliveryTime());
				model.setState(prepareStateModel(aStateDeliveryTimeBean.getState()));
				model.setSeller(prepareSellerModel(aStateDeliveryTimeBean.getSeller()));
				models.add(model);
			}
		}
		return models;
	}
	
	public static State prepareStateModel(StateBean stateBean) {
		State state = new State();
		if (stateBean != null) {
			state.setId(stateBean.getId());
			state.setStateName(stateBean.getStateName());
		}
		return state;
	}
	
	public static List<SellerBean> prepareListofSellerBean(List<Seller> sellers) {
		List<SellerBean> beans = null;
		if (sellers != null && !sellers.isEmpty()) {
			beans = new ArrayList<SellerBean>();
			SellerBean bean = null;
			for (Seller seller : sellers) {
				bean = new SellerBean();
				bean.setAddress(seller.getAddress());
				bean.setCompanyName(seller.getCompanyName());
				bean.setEmail(seller.getEmail());
				bean.setId(seller.getId());
				// bean.setOrders(prepareListofBean(seller.getOrders()));
				bean.setName(seller.getName());
				bean.setPassword(seller.getPassword());
				bean.setContactNo(seller.getContactNo());
				bean.setBrandName(seller.getBrandName());
				bean.setLogoUrl(seller.getLogoUrl());
				bean.setTinNumber(seller.getTinNumber());
				bean.setTanNumber(seller.getTanNumber());
				beans.add(bean);

			}
		}
		return beans;
	}

	public static SellerBean prepareSellerBean(Seller seller) {
		SellerBean bean = new SellerBean();
		if (seller != null) {
			bean.setAddress(seller.getAddress());
			bean.setCompanyName(seller.getCompanyName());
			bean.setEmail(seller.getEmail());
			// bean.setOrders(prepareListofBean(seller.getOrders()));
			bean.setName(seller.getName());
			bean.setId(seller.getId());
			bean.setPassword(seller.getPassword());
			bean.setContactNo(seller.getContactNo());
			bean.setBrandName(seller.getBrandName());
			bean.setLogoUrl(seller.getLogoUrl());
			bean.setTinNumber(seller.getTinNumber());
			bean.setTanNumber(seller.getTanNumber());
			bean.setRole(seller.getRole());
			bean.setStateDeliveryTime(seller.getStateDeliveryTime());
		}
		return bean;
	}

	public static StateDeliveryTimeBean prepareStateDeliveryTimeBean(StateDeliveryTime stateDeliveryTime) {
		StateDeliveryTimeBean bean = null;
		if(stateDeliveryTime!=null)
		{
				bean = new StateDeliveryTimeBean();
				bean.setDeliveryTime(stateDeliveryTime.getDeliveryTime());
				bean.setState(prepareStateBean(stateDeliveryTime.getState()));
		}
				
		return bean;
	}
	
	public static List<StateDeliveryTimeBean> prepareStateDeliveryTimeBeanList(List<StateDeliveryTime> stateDeliveryTime) {
		List<StateDeliveryTimeBean> beans = null;
		if (stateDeliveryTime != null && !stateDeliveryTime.isEmpty()) {
			beans = new ArrayList<StateDeliveryTimeBean>();
			StateDeliveryTimeBean bean = null;
			for (StateDeliveryTime aStateDeliveryTime : stateDeliveryTime) {
				bean = new StateDeliveryTimeBean();
				bean.setDeliveryTime(aStateDeliveryTime.getDeliveryTime());
				bean.setState(prepareStateBean(aStateDeliveryTime.getState()));
				beans.add(bean);
			}
		}
		return beans;
	}
	
	public static StateBean prepareStateBean(State state) {
		StateBean stateBean = new StateBean();
		if (state != null) {
			stateBean.setId(state.getId());
			stateBean.setStateName(state.getStateName());
		}
		return stateBean;
	}
	
	public static Partner preparePartnerModel(PartnerBean partnerBean) {

		Partner partner = new Partner();
		if (partnerBean != null) {
			partner.setPcId(partnerBean.getPcId());
			partner.setPcDesc(partnerBean.getPcDesc());
			partner.setPcLogoUrl(partnerBean.getPcLogoUrl());
			partner.setPcName(partnerBean.getPcName());
			partner.setPaymentType(partnerBean.getPaymentType());
			partner.setIsshippeddatecalc(partnerBean.isIsshippeddatecalc());
			partner.setNoofdaysfromshippeddate(partnerBean
					.getNoofdaysfromshippeddate());
			partner.setStartcycleday(partnerBean.getStartcycleday());
			partner.setPaycycleduration(partnerBean.getPaycycleduration());
			partner.setPaydaysfromstartday(partnerBean.getPaydaysfromstartday());
			partner.setMaxReturnAcceptance(partnerBean.getMaxReturnAcceptance());
			partner.setMaxRTOAcceptance(partnerBean.getMaxRTOAcceptance());
			partner.setTaxcategory(partnerBean.getTaxcategory());
			partner.setTaxrate(partnerBean.getTaxrate());
			partner.setTdsApplicable(partnerBean.isTdsApplicable());
			partner.setPaycyclefromshipordel(partnerBean
					.isPaycyclefromshipordel());
			partner.setMonthlypaydate(partnerBean.getMonthlypaydate());
			partner.setNrnReturnConfig(partnerBean.getNrnReturnConfig());
			partner.setEvents(partnerBean.getEvents());
		}
		return partner;
	}

	public static List<PartnerBean> prepareListofPartnerBean(
			List<Partner> partners) {
		List<PartnerBean> beans = null;
		if (partners != null && !partners.isEmpty()) {
			beans = new ArrayList<PartnerBean>();
			PartnerBean bean = null;
			for (Partner partner : partners) {
				bean = new PartnerBean();
				bean.setPcId(partner.getPcId());
				bean.setPcDesc(partner.getPcDesc());
				bean.setPcLogoUrl(partner.getPcLogoUrl());
				bean.setPcName(partner.getPcName());
				bean.setPaymentType(partner.getPaymentType());
				bean.setIsshippeddatecalc(partner.isIsshippeddatecalc());
				bean.setNoofdaysfromshippeddate(partner
						.getNoofdaysfromshippeddate());
				bean.setStartcycleday(partner.getStartcycleday());
				bean.setPaycycleduration(partner.getPaycycleduration());
				bean.setPaydaysfromstartday(partner.getPaydaysfromstartday());
				bean.setMaxReturnAcceptance(partner.getMaxReturnAcceptance());
				bean.setMaxRTOAcceptance(partner.getMaxRTOAcceptance());
				bean.setTaxcategory(partner.getTaxcategory());
				bean.setTaxrate(partner.getTaxrate());
				bean.setTdsApplicable(partner.isTdsApplicable());
				bean.setPaycyclefromshipordel(partner.isPaycyclefromshipordel());
				bean.setMonthlypaydate(partner.getMonthlypaydate());
				bean.setNrnReturnConfig(partner.getNrnReturnConfig());
				bean.setEvents(partner.getEvents());
				beans.add(bean);
			}
		}
		return beans;
	}

	public static PartnerBean preparePartnerBean(Partner partner) {
		PartnerBean bean = new PartnerBean();
		if (partner != null) {
			bean.setPcId(partner.getPcId());
			bean.setPcDesc(partner.getPcDesc());
			bean.setPcLogoUrl(partner.getPcLogoUrl());
			bean.setPcName(partner.getPcName());
			bean.setPaymentType(partner.getPaymentType());
			bean.setIsshippeddatecalc(partner.isIsshippeddatecalc());
			bean.setNoofdaysfromshippeddate(partner
					.getNoofdaysfromshippeddate());
			bean.setStartcycleday(partner.getStartcycleday());
			bean.setPaycycleduration(partner.getPaycycleduration());
			bean.setPaydaysfromstartday(partner.getPaydaysfromstartday());
			bean.setMaxReturnAcceptance(partner.getMaxReturnAcceptance());
			bean.setMaxRTOAcceptance(partner.getMaxRTOAcceptance());
			bean.setTaxcategory(partner.getTaxcategory());
			bean.setTaxrate(partner.getTaxrate());
			bean.setTdsApplicable(partner.isTdsApplicable());
			bean.setPaycyclefromshipordel(partner.isPaycyclefromshipordel());
			bean.setMonthlypaydate(partner.getMonthlypaydate());
			bean.setEvents(partner.getEvents());
			bean.setNrnReturnConfig(partner.getNrnReturnConfig());
		}
		return bean;
	}

	public static CategoryBean prepareCategoryBean(Category category) {
		CategoryBean bean = new CategoryBean();
		if (category != null) {
			bean.setId(category.getCategoryId());
			bean.setCatDescription(category.getCatDescription());
			bean.setCatName(category.getCatName());
			bean.setSubCategory(category.isSubCategory());
			bean.setProductCount(category.getProductCount());
			bean.setParentCatName(category.getParentCatName());
			bean.setSubCategory(prepareListofCategoryBean(category
					.getSubCategory()));
			bean.setCreatedOn(category.getCreatedOn());
			bean.setOpeningStock(category.getOpeningStock());
			bean.setSkuCount(category.getSkuCount());
			bean.setOsUpdate(category.getOsUpdate());
		}
		return bean;
	}

	public static Category prepareCategoryModel(CategoryBean categoryBean) {
		Category category = new Category();
		if (categoryBean != null) {
			category.setCategoryId(categoryBean.getId());
			category.setCatDescription(categoryBean.getCatDescription());
			category.setCatName(categoryBean.getCatName());
			category.setSubCategory(categoryBean.isSubCategory());
			category.setProductCount(categoryBean.getProductCount());
			category.setParentCatName(categoryBean.getParentCatName());
			category.setCreatedOn(categoryBean.getCreatedOn());
			category.setOpeningStock(categoryBean.getOpeningStock());
			category.setSkuCount(categoryBean.getSkuCount());
			category.setOsUpdate(categoryBean.getOsUpdate());
		}
		return category;
	}

	public static List<CategoryBean> prepareListofCategoryBean(
			List<Category> categories) {
		List<CategoryBean> beans = null;
		if (categories != null && !categories.isEmpty()) {
			beans = new ArrayList<CategoryBean>();
			CategoryBean bean = null;
			for (Category category : categories) {
				bean = new CategoryBean();
				bean.setId(category.getCategoryId());
				bean.setCatDescription(category.getCatDescription());
				bean.setCatName(category.getCatName());
				bean.setSubCategory(category.isSubCategory());
				bean.setProductCount(category.getProductCount());
				bean.setParentCatName(category.getParentCatName());
				bean.setCreatedOn(category.getCreatedOn());
				bean.setOpeningStock(category.getOpeningStock());
				bean.setSkuCount(category.getSkuCount());
				bean.setOsUpdate(category.getOsUpdate());

				beans.add(bean);
			}
		}
		return beans;
	}

	public static ProductBean prepareProductBean(Product product) {
		ProductBean bean = new ProductBean();
		if (product != null) {
			bean.setCategoryName(product.getCategoryName());
			bean.setProductDate(product.getProductDate());
			bean.setProductId(product.getProductId());
			bean.setProductName(product.getProductName());
			bean.setProductPrice(product.getProductPrice());
			bean.setProductSkuCode(product.getProductSkuCode());
			bean.setQuantity(product.getQuantity());
			bean.setThreholdLimit(product.getThreholdLimit());
			bean.setChannelSKU(product.getChannelSKU());
			bean.setBreadth(product.getBreadth());
			bean.setLength(product.getLength());
			bean.setHeight(product.getHeight());
			bean.setDeadWeight(product.getDeadWeight());
			bean.setVolume(product.getVolume());
			bean.setVolWeight(product.getVolWeight());
		}
		return bean;
	}

	public static Product prepareProductModel(ProductBean product) {
		Product bean = new Product();
		if (product != null) {
			bean.setProductDate(product.getProductDate());
			bean.setProductId(product.getProductId());
			bean.setProductName(product.getProductName());
			bean.setProductPrice(product.getProductPrice());
			bean.setProductSkuCode(product.getProductSkuCode());
			bean.setQuantity(product.getQuantity());
			bean.setCategoryName(product.getCategoryName());
			bean.setThreholdLimit(product.getThreholdLimit());
			bean.setChannelSKU(product.getChannelSKU());
			bean.setBreadth(product.getBreadth());
			bean.setLength(product.getLength());
			bean.setHeight(product.getHeight());
			bean.setDeadWeight(product.getDeadWeight());
			bean.setVolume(product.getVolume());
			bean.setVolWeight(product.getVolWeight());
		}
		return bean;
	}

	public static List<ProductBean> prepareListofProductBean(
			List<Product> products) {
		List<ProductBean> beans = null;
		if (products != null && !products.isEmpty()) {
			beans = new ArrayList<ProductBean>();
			ProductBean bean = null;
			for (Product product : products) {
				bean = new ProductBean();
				bean.setCategoryName(product.getCategoryName());
				bean.setProductDate(product.getProductDate());
				bean.setProductId(product.getProductId());
				bean.setProductName(product.getProductName());
				bean.setProductPrice(product.getProductPrice());
				bean.setProductSkuCode(product.getProductSkuCode());
				bean.setQuantity(product.getQuantity());
				bean.setThreholdLimit(product.getThreholdLimit());
				bean.setChannelSKU(product.getChannelSKU());
				bean.setBreadth(product.getBreadth());
				bean.setLength(product.getLength());
				bean.setHeight(product.getHeight());
				bean.setDeadWeight(product.getDeadWeight());
				bean.setVolume(product.getVolume());
				bean.setVolWeight(product.getVolWeight());
				beans.add(bean);
			}
		}
		return beans;
	}

	public static Customer prepareCustomerModel(CustomerBean customerBean) {

		Customer customer = new Customer();
		if (customerBean != null) {
			customer.setCustomerId(customerBean.getCustomerId());
			customer.setCustomerAddress(customerBean.getCustomerAddress());
			customer.setCustomerCity(customerBean.getCustomerCity());
			customer.setCustomerEmail(customerBean.getCustomerEmail());
			customer.setCustomerName(customerBean.getCustomerName());
			customer.setCustomerPhnNo(customerBean.getCustomerPhnNo());
			customer.setZipcode(customerBean.getZipcode());

		}

		return customer;
	}

	public static CustomerBean prepareCustomerBean(Customer customer) {

		CustomerBean customerBean = new CustomerBean();
		if (customer != null) {
			customerBean.setCustomerId(customer.getCustomerId());
			customerBean.setCustomerAddress(customer.getCustomerAddress());
			customerBean.setCustomerCity(customer.getCustomerCity());
			customerBean.setCustomerEmail(customer.getCustomerEmail());
			customerBean.setCustomerName(customer.getCustomerName());
			customerBean.setCustomerPhnNo(customer.getCustomerPhnNo());
			customerBean.setZipcode(customer.getZipcode());

		}

		return customerBean;
	}

	public static ExpenseCategoryBean prepareExpenseCategoryBean(
			ExpenseCategory category) {
		ExpenseCategoryBean bean = new ExpenseCategoryBean();
		if (category != null) {
			bean.setExpcategoryId(category.getExpcategoryId());
			bean.setCreatedOn(category.getCreatedOn());
			bean.setExpcatDescription(category.getExpcatDescription());
			bean.setExpcatName(category.getExpcatName());
			bean.setExpenseSize(category.getExpenses().size());
		}
		// bean.setExpenses(prepareListofExpenseBean(category.getExpenses()));

		return bean;
	}

	public static ExpenseCategory prepareExpenseCategoryModel(
			ExpenseCategoryBean categoryBean) {
		ExpenseCategory category = new ExpenseCategory();
		if (categoryBean != null) {
			category.setExpcategoryId(categoryBean.getExpcategoryId());
			category.setCreatedOn(categoryBean.getCreatedOn());
			category.setExpcatDescription(categoryBean.getExpcatDescription());
			category.setExpcatName(categoryBean.getExpcatName());
		}
		// category.setExpenses(categoryBean.getExpenses());
		return category;
	}

	public static List<ExpenseCategoryBean> prepareListofExpenseCategoryBean(
			List<ExpenseCategory> categories) {
		List<ExpenseCategoryBean> beans = null;
		if (categories != null && !categories.isEmpty()) {
			beans = new ArrayList<ExpenseCategoryBean>();
			ExpenseCategoryBean bean = null;
			for (ExpenseCategory category : categories) {
				bean = new ExpenseCategoryBean();
				bean.setExpcategoryId(category.getExpcategoryId());
				bean.setCreatedOn(category.getCreatedOn());
				bean.setExpcatDescription(category.getExpcatDescription());
				bean.setExpcatName(category.getExpcatName());
				bean.setExpenseSize(category.getExpenses().size());
				// bean.setExpenses(prepareListofExpenseBean(category.getExpenses()));

				beans.add(bean);
			}
		}
		return beans;
	}

	public static ExpenseBean prepareExpenseBean(Expenses expense) {
		ExpenseBean bean = new ExpenseBean();
		if (expense != null) {
			bean.setAmount(expense.getAmount());
			bean.setCreatedOn(expense.getCreatedOn());
			// bean.setExpenseCategory(prepareExpenseCategoryBean(expense.getExpenseCategory()));
			bean.setExpenseCatName(expense.getExpenseCatName());
			bean.setExpenseDescription(expense.getExpenseDescription());
			bean.setExpenseId(expense.getExpenseId());
			bean.setExpenseName(expense.getExpenseName());
			bean.setSellerId(expense.getSellerId());
			bean.setExpenditureByperson(expense.getExpenditureByperson());
			bean.setPaidTo(expense.getPaidTo());
			bean.setExpenseDate(expense.getExpenseDate());
		}
		return bean;
	}

	public static Expenses prepareExpenseModel(ExpenseBean bean) {
		Expenses expense = new Expenses();
		if (bean != null) {
			expense.setAmount(bean.getAmount());
			expense.setCreatedOn(bean.getCreatedOn());
			// expense.setExpenseCategory(prepareExpenseCategoryModel(bean.getExpenseCategory()));
			expense.setExpenseCatName(bean.getExpenseCatName());
			expense.setExpenseDescription(bean.getExpenseDescription());
			expense.setExpenseId(bean.getExpenseId());
			expense.setExpenseName(bean.getExpenseName());
			expense.setSellerId(bean.getSellerId());
			expense.setExpenditureByperson(bean.getExpenditureByperson());
			expense.setPaidTo(bean.getPaidTo());
			expense.setExpenseDate(bean.getExpenseDate());
		}
		return expense;
	}

	public static List<ExpenseBean> prepareListofExpenseBean(
			List<Expenses> expenses) {
		List<ExpenseBean> beans = null;
		if (expenses != null && !expenses.isEmpty()) {
			beans = new ArrayList<ExpenseBean>();
			ExpenseBean bean = null;
			for (Expenses expense : expenses) {
				bean = new ExpenseBean();
				bean.setAmount(expense.getAmount());
				bean.setCreatedOn(expense.getCreatedOn());
				// bean.setExpenseCategory(prepareExpenseCategoryBean(expense.getExpenseCategory()));
				bean.setExpenseCatName(expense.getExpenseCatName());
				bean.setExpenseDescription(expense.getExpenseDescription());
				bean.setExpenseId(expense.getExpenseId());
				bean.setExpenseName(expense.getExpenseName());
				bean.setSellerId(expense.getSellerId());
				bean.setExpenditureByperson(expense.getExpenditureByperson());
				bean.setPaidTo(expense.getPaidTo());
				bean.setExpenseDate(expense.getExpenseDate());

				beans.add(bean);
			}
		}else
			beans.add(new ExpenseBean());
		
		return beans;
	}

	public static OrderTax prepareOrderTaxModel(OrderTaxBean bean) {
		OrderTax orderTax = new OrderTax();
		if (bean != null) {
			orderTax.setNetTax(bean.getNetTax());
			orderTax.setNetTds(bean.getNetTds());
			orderTax.setTax(bean.getTax());
			orderTax.setTaxCategtory(bean.getTaxCategtory());
			orderTax.setTaxdepositdate(bean.getTaxdepositdate());
			orderTax.setTaxId(bean.getTaxId());
			orderTax.setTaxperiod(bean.getTaxperiod());
			orderTax.setTaxRate(bean.getTaxRate());
			orderTax.setTdsdepositedate(bean.getTdsdepositedate());
			orderTax.setTdsperiod(bean.getTdsperiod());
			orderTax.setTdsToDeduct(bean.getTdsToDeduct());
			orderTax.setTdsToReturn(bean.getTdsToReturn());
			orderTax.setTotalpositivevalue(bean.getTotalpositivevalue());
			orderTax.setTaxToReturn(bean.getTaxToReturn());
		}
		return orderTax;
	}

	public static OrderTaxBean prepareOrderTaxBean(OrderTax bean) {
		OrderTaxBean orderTax = new OrderTaxBean();
		if (bean != null) {
			orderTax.setNetTax(bean.getNetTax());
			orderTax.setNetTds(bean.getNetTds());
			orderTax.setTax(bean.getTax());
			orderTax.setTaxCategtory(bean.getTaxCategtory());
			orderTax.setTaxdepositdate(bean.getTaxdepositdate());
			orderTax.setTaxId(bean.getTaxId());
			orderTax.setTaxperiod(bean.getTaxperiod());
			orderTax.setTaxRate(bean.getTaxRate());
			orderTax.setTdsdepositedate(bean.getTdsdepositedate());
			orderTax.setTdsperiod(bean.getTdsperiod());
			orderTax.setTdsToDeduct(bean.getTdsToDeduct());
			orderTax.setTdsToReturn(bean.getTdsToReturn());
			orderTax.setTotalpositivevalue(bean.getTotalpositivevalue());
			orderTax.setTaxToReturn(bean.getTaxToReturn());
		}
		return orderTax;
	}

	public static OrderPayment prepareOrderPaymentModel(OrderPaymentBean bean) {
		OrderPayment orderPayment = new OrderPayment();
		if (bean != null) {
			orderPayment.setDateofPayment(bean.getDateofPayment());
			orderPayment.setNegativeAmount(bean.getNegativeAmount());
			orderPayment.setPaymentId(bean.getOrderpayId());
			orderPayment.setPaymentdesc(bean.getPaymentdesc());
			orderPayment.setPaymentUploadId(bean.getPaymentUploadId());
			orderPayment.setPositiveAmount(bean.getPositiveAmount());
			orderPayment.setUploadDate(bean.getUploadDate());
			orderPayment.setActualrecived2(bean.getActualrecived2());
			orderPayment.setNetPaymentResult(bean.getNetPaymentResult());
			orderPayment.setPaymentCycle(bean.getPaymentCycle());
			orderPayment.setPaymentDifference(bean.getPaymentDifference());
			orderPayment.setPaymentCycleStart(bean.getPaymentCycleStart());
			orderPayment.setPaymentCycleEnd(bean.getPaymentCycleEnd());

		}
		return orderPayment;
	}

	public static OrderPaymentBean prepareOrderPaymentBean(OrderPayment bean) {
		OrderPaymentBean orderPayment = new OrderPaymentBean();
		if (bean != null) {
			orderPayment.setDateofPayment(bean.getDateofPayment());
			orderPayment.setNegativeAmount(bean.getNegativeAmount());
			orderPayment.setOrderpayId(bean.getPaymentId());
			orderPayment.setPaymentdesc(bean.getPaymentdesc());
			orderPayment.setPaymentUploadId(bean.getPaymentUploadId());
			orderPayment.setPositiveAmount(bean.getPositiveAmount());
			orderPayment.setUploadDate(bean.getUploadDate());
			orderPayment.setActualrecived2(bean.getActualrecived2());
			orderPayment.setNetPaymentResult(bean.getNetPaymentResult());
			orderPayment.setPaymentCycle(bean.getPaymentCycle());
			orderPayment.setPaymentDifference(bean.getPaymentDifference());
			orderPayment.setPaymentCycleStart(bean.getPaymentCycleStart());
			orderPayment.setPaymentCycleEnd(bean.getPaymentCycleEnd());
		}
		return orderPayment;
	}

	public static OrderRTOorReturn prepareOrderRTOorReturnModel(
			OrderRTOorReturnBean bean) {
		OrderRTOorReturn orderReturn = new OrderRTOorReturn();
		if (bean != null) {
			orderReturn.setReturnDate(bean.getReturnDate());
			orderReturn.setReturnId(bean.getReturnId());
			orderReturn.setReturnOrRTOCharges(bean.getReturnOrRTOCharges());
			orderReturn.setReturnOrRTOId(bean.getReturnOrRTOId());
			orderReturn.setReturnorrtoQty(bean.getReturnorrtoQty());
			orderReturn.setReturnOrRTOreason(bean.getReturnOrRTOreason());
			orderReturn.setReturnUploadDate(bean.getReturnUploadDate());
			orderReturn.setReturnOrRTOChargestoBeDeducted(bean
					.getReturnOrRTOChargestoBeDeducted());
			orderReturn.setReturnOrRTOstatus(bean.getReturnOrRTOstatus());
			orderReturn.setType(bean.getType());
			orderReturn.setCancelType(bean.getCancelType());
			orderReturn.setReturnCategory(bean.getReturnCategory());
			
			orderReturn.setNetNR(bean.getNetNR());
			orderReturn.setTaxPOAmt(bean.getTaxPOAmt());
			orderReturn.setNetPR(bean.getNetPR());
			orderReturn.setGrossProfit(bean.getGrossProfit());
		}
		return orderReturn;
	}

	public static OrderRTOorReturnBean prepareOrderRTOorReturnBean(
			OrderRTOorReturn bean) {
		OrderRTOorReturnBean orderReturn = new OrderRTOorReturnBean();
		if (bean != null) {
			orderReturn.setReturnDate(bean.getReturnDate());
			orderReturn.setReturnId(bean.getReturnId());
			orderReturn.setReturnOrRTOCharges(bean.getReturnOrRTOCharges());
			orderReturn.setReturnOrRTOId(bean.getReturnOrRTOId());
			orderReturn.setReturnorrtoQty(bean.getReturnorrtoQty());
			orderReturn.setReturnOrRTOreason(bean.getReturnOrRTOreason());
			orderReturn.setReturnUploadDate(bean.getReturnUploadDate());
			orderReturn.setReturnOrRTOChargestoBeDeducted(bean
					.getReturnOrRTOChargestoBeDeducted());
			orderReturn.setReturnOrRTOstatus(bean.getReturnOrRTOstatus());
			orderReturn.setType(bean.getType());
			orderReturn.setCancelType(bean.getCancelType());
			orderReturn.setReturnCategory(bean.getReturnCategory());
			
			orderReturn.setNetNR(bean.getNetNR());
			orderReturn.setTaxPOAmt(bean.getTaxPOAmt());
			orderReturn.setNetPR(bean.getNetPR());
			orderReturn.setGrossProfit(bean.getGrossProfit());
		}
		return orderReturn;
	}

	public static PaymentUploadBean preparePaymentUploadBean(
			PaymentUpload payUpload) {
		PaymentUploadBean bean = new PaymentUploadBean();
		if (payUpload != null) {
			bean.setUploadStatus(payUpload.getUploadStatus());
			bean.setNetRecievedAmount(payUpload.getNetRecievedAmount());
			bean.setTotalpositivevalue(payUpload.getTotalpositivevalue());
			bean.setUploadDate(payUpload.getUploadDate());
			bean.setUploadId(payUpload.getUploadId());
			bean.setUploadDesc(payUpload.getUploadDesc());
			bean.setOrders(ConverterClass.prepareListofBean(payUpload
					.getOrders()));
		}
		return bean;
	}

	public static PaymentUpload preparePaymentUploadModel(PaymentUploadBean bean) {
		PaymentUpload payment = new PaymentUpload();
		if (bean != null) {
			payment.setUploadStatus(bean.getUploadStatus());
			payment.setNetRecievedAmount(bean.getNetRecievedAmount());
			payment.setTotalpositivevalue(bean.getTotalpositivevalue());
			payment.setUploadDate(bean.getUploadDate());
			payment.setUploadId(bean.getUploadId());
			payment.setUploadDesc(bean.getUploadDesc());
			payment.setOrders(ConverterClass.prepareListofOrdersFromBean(bean
					.getOrders()));
		}

		return payment;
	}

	public static List<PaymentUploadBean> prepareListofPaymentUploadBean(
			List<PaymentUpload> payment) {
		List<PaymentUploadBean> beans = null;
		if (payment != null && !payment.isEmpty()) {
			beans = new ArrayList<PaymentUploadBean>();
			PaymentUploadBean bean = null;
			for (PaymentUpload payUpload : payment) {
				bean = new PaymentUploadBean();
				bean.setUploadStatus(payUpload.getUploadStatus());
				bean.setNetRecievedAmount(payUpload.getNetRecievedAmount());
				bean.setTotalpositivevalue(payUpload.getTotalpositivevalue());
				bean.setUploadDate(payUpload.getUploadDate());
				bean.setUploadId(payUpload.getUploadId());
				bean.setUploadDesc(payUpload.getUploadDesc());
				bean.setTotalnegativevalue(payUpload.getTotalnegativevalue());
				// bean.setOrders(ConverterClass.prepareListofBean(payUpload.getOrders()));

				beans.add(bean);
			}
		}
		return beans;
	}

	public static TaxDetailBean prepareTaxDetailBean(TaxDetail taxDetail) {
		TaxDetailBean bean = new TaxDetailBean();
		if (taxDetail != null) {
			bean.setBalanceRemaining(taxDetail.getBalanceRemaining());
			bean.setDateOfPayment(taxDetail.getDateOfPayment());
			bean.setDescription(taxDetail.getDescription());
			bean.setPaidAmount(taxDetail.getPaidAmount());
			bean.setParticular(taxDetail.getParticular());
			bean.setStatus(taxDetail.getStatus());
			bean.setTaxId(taxDetail.getTaxId());
			bean.setTaxortds(taxDetail.getTaxortds());
			bean.setTaxortdsCycle(taxDetail.getTaxortdsCycle());
			bean.setUploadDate(taxDetail.getUploadDate());
		}
		return bean;
	}

	public static TaxDetail prepareTaxDetailModel(TaxDetailBean taxDetail) {
		TaxDetail bean = new TaxDetail();
		if (taxDetail != null) {
			bean.setBalanceRemaining(taxDetail.getBalanceRemaining());
			bean.setDateOfPayment(taxDetail.getDateOfPayment());
			bean.setDescription(taxDetail.getDescription());
			bean.setPaidAmount(taxDetail.getPaidAmount());
			bean.setParticular(taxDetail.getParticular());
			bean.setStatus(taxDetail.getStatus());
			bean.setTaxId(taxDetail.getTaxId());
			bean.setTaxortds(taxDetail.getTaxortds());
			bean.setTaxortdsCycle(taxDetail.getTaxortdsCycle());
			bean.setUploadDate(taxDetail.getUploadDate());
		}
		return bean;
	}

	public static List<TaxDetailBean> prepareListofTaxDetailBean(
			List<TaxDetail> taxDetaillist) {
		List<TaxDetailBean> beans = null;
		if (taxDetaillist != null && !taxDetaillist.isEmpty()) {
			beans = new ArrayList<TaxDetailBean>();
			TaxDetailBean bean = null;
			for (TaxDetail taxDetail : taxDetaillist) {
				bean = new TaxDetailBean();
				bean.setBalanceRemaining(taxDetail.getBalanceRemaining());
				bean.setDateOfPayment(taxDetail.getDateOfPayment());
				bean.setDescription(taxDetail.getDescription());
				bean.setPaidAmount(taxDetail.getPaidAmount());
				bean.setParticular(taxDetail.getParticular());
				bean.setStatus(taxDetail.getStatus());
				bean.setTaxId(taxDetail.getTaxId());
				bean.setTaxortds(taxDetail.getTaxortds());
				bean.setTaxortdsCycle(taxDetail.getTaxortdsCycle());
				bean.setUploadDate(taxDetail.getUploadDate());

				beans.add(bean);
			}
		}
		return beans;
	}

	public static ManualChargesBean prepareManualChargesBean(
			ManualCharges manualCharges) {
		ManualChargesBean bean = new ManualChargesBean();
		if (manualCharges != null) {
			bean.setChargesCategory(manualCharges.getChargesCategory());
			bean.setChargesDesc(manualCharges.getChargesDesc());
			bean.setDateOfPayment(manualCharges.getDateOfPayment());
			bean.setMcId(manualCharges.getMcId());
			bean.setPaidAmount(manualCharges.getPaidAmount());
			bean.setParticular(manualCharges.getParticular());
			bean.setPartner(manualCharges.getPartner());
			bean.setPaymentCycle(manualCharges.getPaymentCycle());
			bean.setUploadDate(manualCharges.getUploadDate());
		}
		return bean;
	}

	public static ManualCharges prepareManualChargesModel(
			ManualChargesBean manualCharges) {
		ManualCharges bean = new ManualCharges();
		if (manualCharges != null) {
			bean.setChargesCategory(manualCharges.getChargesCategory());
			bean.setChargesDesc(manualCharges.getChargesDesc());
			bean.setDateOfPayment(manualCharges.getDateOfPayment());
			bean.setMcId(manualCharges.getMcId());
			bean.setPaidAmount(manualCharges.getPaidAmount());
			bean.setParticular(manualCharges.getParticular());
			bean.setPartner(manualCharges.getPartner());
			bean.setPaymentCycle(manualCharges.getPaymentCycle());
			bean.setUploadDate(manualCharges.getUploadDate());
		}
		return bean;
	}

	public static List<ManualChargesBean> prepareListofManualChargesBean(
			List<ManualCharges> manualChargeslist) {
		List<ManualChargesBean> beans = null;
		if (manualChargeslist != null && !manualChargeslist.isEmpty()) {
			beans = new ArrayList<ManualChargesBean>();
			ManualChargesBean bean = null;
			for (ManualCharges manualCharges : manualChargeslist) {
				bean = new ManualChargesBean();
				bean.setChargesCategory(manualCharges.getChargesCategory());
				bean.setChargesDesc(manualCharges.getChargesDesc());
				bean.setDateOfPayment(manualCharges.getDateOfPayment());
				bean.setMcId(manualCharges.getMcId());
				bean.setPaidAmount(manualCharges.getPaidAmount());
				bean.setParticular(manualCharges.getParticular());
				bean.setPartner(manualCharges.getPartner());
				bean.setPaymentCycle(manualCharges.getPaymentCycle());
				bean.setUploadDate(manualCharges.getUploadDate());

				beans.add(bean);
			}
		}
		return beans;
	}

	public static TaxCategoryBean prepareTaxCategoryBean(TaxCategory category) {
		TaxCategoryBean bean = new TaxCategoryBean();
		if (category != null) {
			bean.setPartner(category.getPartner());
			bean.setTaxCatDescription(category.getTaxCatDescription());
			bean.setTaxCatId(category.getTaxCatId());
			bean.setTaxCatName(category.getTaxCatName());
			bean.setUploadDate(category.getUploadDate());
			bean.setTaxPercent(category.getTaxPercent());
		}
		return bean;
	}

	public static TaxCategory prepareTaxCategoryModel(TaxCategoryBean bean) {
		TaxCategory category = new TaxCategory();
		if (bean != null) {
			category.setPartner(bean.getPartner());
			category.setTaxCatDescription(bean.getTaxCatDescription());
			category.setTaxCatId(bean.getTaxCatId());
			category.setTaxCatName(bean.getTaxCatName());
			category.setUploadDate(bean.getUploadDate());
			category.setTaxPercent(bean.getTaxPercent());
		}
		return category;

	}

	public static List<TaxCategoryBean> prepareListofTaxCategoryBean(
			List<TaxCategory> categories) {
		List<TaxCategoryBean> beans = null;
		if (categories != null && !categories.isEmpty()) {
			beans = new ArrayList<TaxCategoryBean>();
			TaxCategoryBean bean = null;
			for (TaxCategory category : categories) {
				bean = new TaxCategoryBean();
				bean.setPartner(category.getPartner());
				bean.setTaxCatDescription(category.getTaxCatDescription());
				bean.setTaxCatId(category.getTaxCatId());
				bean.setTaxCatName(category.getTaxCatName());
				bean.setUploadDate(category.getUploadDate());
				bean.setTaxPercent(category.getTaxPercent());

				beans.add(bean);
			}
		}
		return beans;
	}

	// Plan Module Implementation

	// Mapping PlanBean with Plan of Model

	public static Plan preparePlanModel(PlanBean planBean) {
		Plan plan = new Plan();
		if (planBean != null) {
			plan.setPlanName(planBean.getPlanName());
			plan.setActive(planBean.isActive());
			plan.setOrderCount(planBean.getOrderCount());
			plan.setDescription(planBean.getDescription());
			plan.setPid(planBean.getPid());
			plan.setPlanId(planBean.getPlanId());
			plan.setPlanPrice(planBean.getPlanPrice());
		}
		return plan;
	}

	public static List<PlanBean> prepareListofPlanBean(List<Plan> plans) {
		List<PlanBean> beans = null;
		if (plans != null && !plans.isEmpty()) {
			beans = new ArrayList<PlanBean>();
			PlanBean bean = null;
			for (Plan plan : plans) {
				bean = new PlanBean();
				bean.setPlanName(plan.getPlanName());
				bean.setActive(plan.isActive());
				bean.setDescription(plan.getDescription());
				bean.setOrderCount(plan.getOrderCount());
				bean.setPid(plan.getPid());
				bean.setPlanId(plan.getPlanId());
				bean.setPlanPrice(plan.getPlanPrice());
				beans.add(bean);
				System.out.println(beans);
			}
		}
		return beans;
	}

	// Mapping SellerAccountBean with SellerAccount

	public static SellerAccount prepareSellerAccountModel(
			SellerAccountBean sellerAccountBean) {
		SellerAccount sellerAccount = new SellerAccount();
		if (sellerAccountBean != null) {
			sellerAccount.setSelaccId(sellerAccountBean.getSelaccId());
			sellerAccount.setSellerId(sellerAccountBean.getSellerId());
			sellerAccount.setPlanId(sellerAccountBean.getPlanId());
			sellerAccount.setOrderBucket(sellerAccountBean.getOrderBucket());
			sellerAccount.setLastTransaction(sellerAccountBean
					.getLastTransaction());
			sellerAccount.setPlanTransactionId(sellerAccountBean
					.getPlanTransactionId());
			sellerAccount.setAccountStatus(sellerAccountBean.isAccountStatus());
			sellerAccount
					.setAtivationDate(sellerAccountBean.getAtivationDate());
			sellerAccount.setLastLogin(sellerAccountBean.getLastLogin());
			sellerAccount.setTotalAmountPaidToO2r(sellerAccountBean
					.getTotalOrderProcessed());
			sellerAccount.setTotalAmountPaidToO2r(sellerAccountBean
					.getTotalAmountPaidToO2r());
		}
		return sellerAccount;
	}

	public static List<SellerAccountBean> prepareListofSellerAccountBean(
			List<SellerAccount> sellerAccount) {
		List<SellerAccountBean> beanArray = null;
		if (sellerAccount != null && !sellerAccount.isEmpty()) {
			beanArray = new ArrayList<SellerAccountBean>(); // Array object
			SellerAccountBean bean = null;
			for (SellerAccount sa : sellerAccount) {
				bean = new SellerAccountBean();

				bean.setSelaccId(sa.getSelaccId());
				bean.setSellerId(sa.getSellerId());
				bean.setPlanId(sa.getPlanId());
				bean.setOrderBucket(sa.getOrderBucket());
				bean.setLastTransaction(sa.getLastTransaction());
				bean.setPlanTransactionId(sa.getPlanTransactionId());
				bean.setAccountStatus(sa.isAccountStatus());
				bean.setAtivationDate(sa.getAtivationDate());
				bean.setLastLogin(sa.getLastLogin());
				bean.setTotalOrderProcessed(sa.getTotalOrderProcessed());
				bean.setTotalAmountPaidToO2r(sa.getTotalAmountPaidToO2r());
				beanArray.add(bean);
			}
		}
		return beanArray;
	}

	// Mapping AccountTransactionBean with AccountTransaction

	public static AccountTransaction prepareAccountTransactionModel(
			AccountTransactionBean cccountTransactionBean) {
		AccountTransaction accountTransaction = new AccountTransaction();
		if (cccountTransactionBean != null) {
			accountTransaction.setAccTransId(cccountTransactionBean
					.getAccTransId());
			accountTransaction.setTransactionDate(cccountTransactionBean
					.getTransactionDate());
			accountTransaction.setTransactionAmount(cccountTransactionBean
					.getTransactionAmount());
			accountTransaction.setCurrentOrderCount(cccountTransactionBean
					.getCurrentOrderCount());
			accountTransaction.setInvoiceId(cccountTransactionBean
					.getInvoiceId());
			accountTransaction.setStatus(cccountTransactionBean.getStatus());
			accountTransaction.setTransactionId(cccountTransactionBean
					.getTransactionId());
		}
		return accountTransaction;
	}

	public static List<AccountTransactionBean> prepareListofAccountTransactionBean(
			List<AccountTransaction> accountTransaction) {
		List<AccountTransactionBean> beanArray = null;
		if (accountTransaction != null && !accountTransaction.isEmpty()) {
			beanArray = new ArrayList<AccountTransactionBean>(); // Array object
			AccountTransactionBean bean = null;
			for (AccountTransaction at : accountTransaction) {
				bean = new AccountTransactionBean();

				bean.setAccTransId(at.getAccTransId());
				bean.setTransactionDate(at.getTransactionDate());
				bean.setTransactionAmount(at.getTransactionAmount());
				bean.setCurrentOrderCount(at.getCurrentOrderCount());
				bean.setInvoiceId(at.getInvoiceId());
				bean.setTransactionId(at.getTransactionId());
				bean.setStatus(at.getStatus());
				beanArray.add(bean);
			}
		}
		return beanArray;
	}
	
	public static Events prepareEventsModel(EventsBean eventsBean){
		
		Events events=new Events();
		events.setEventId(eventsBean.getEventId());
		events.setSellerId(eventsBean.getSellerId());
		events.setEventName(eventsBean.getEventName());
		events.setChannelName(eventsBean.getChannelName());
		events.setStartDate(eventsBean.getStartDate());
		events.setEndDate(eventsBean.getEndDate());
		events.setProductCategories(eventsBean.getProductCategories());
		events.setNrType(eventsBean.getNrType());
		events.setReturnCharges(eventsBean.getReturnCharges());
		events.setNrnReturnConfig(eventsBean.getNrnReturnConfig());
		events.setPartner(eventsBean.getPartner());
		events.setCreatedDate(eventsBean.getCreatedDate());
		events.setNetSalesQuantity(eventsBean.getNetSalesQuantity());
		events.setNetSalesAmount(eventsBean.getNetSalesAmount());		
		return events;
		
	}
	public static EventsBean prepareEventsBean(Events events){
		
		EventsBean eventsBean=new EventsBean();
		eventsBean.setEventId(events.getEventId());
		eventsBean.setSellerId(events.getSellerId());
		eventsBean.setEventName(events.getEventName());
		eventsBean.setChannelName(events.getChannelName());
		eventsBean.setStartDate(events.getStartDate());
		eventsBean.setEndDate(events.getEndDate());
		eventsBean.setProductCategories(events.getProductCategories());
		eventsBean.setNrType(events.getNrType());
		eventsBean.setReturnCharges(events.getReturnCharges());
		eventsBean.setNrnReturnConfig(events.getNrnReturnConfig());
		eventsBean.setPartner(events.getPartner());
		eventsBean.setCreatedDate(events.getCreatedDate());
		eventsBean.setNetSalesQuantity(events.getNetSalesQuantity());
		eventsBean.setNetSalesAmount(events.getNetSalesAmount());
		return eventsBean;
	}
	public static List<EventsBean> prepareListOfEventsBean(List<Events> events){
		List<EventsBean> beans = null;
		if (events != null && !events.isEmpty()) {
			beans = new ArrayList<EventsBean>();
			EventsBean bean = null;
			for (Events event: events) {
				bean = new EventsBean();
				bean.setEventId(event.getEventId());
				bean.setSellerId(event.getSellerId());
				bean.setEventName(event.getEventName());
				bean.setChannelName(event.getChannelName());
				bean.setStartDate(event.getStartDate());
				bean.setEndDate(event.getEndDate());
				bean.setProductCategories(event.getProductCategories());
				bean.setNrType(event.getNrType());
				bean.setReturnCharges(event.getReturnCharges());
				bean.setNrnReturnConfig(event.getNrnReturnConfig());
				bean.setPartner(event.getPartner());
				bean.setCreatedDate(event.getCreatedDate());
				bean.setNetSalesQuantity(event.getNetSalesQuantity());
				bean.setNetSalesAmount(event.getNetSalesAmount());
				beans.add(bean);
			}
		}		
		return beans;
	}
	
	public static ProductConfigBean prepareProductConfigBean(ProductConfig productConfig){
		
		ProductConfigBean productConfigBean=new ProductConfigBean();
		productConfigBean.setProductConfigId(productConfig.getProductConfigId());
		productConfigBean.setProductId(productConfig.getProductId());
		productConfigBean.setProductName(productConfig.getProductName());
		productConfigBean.setProductSkuCode(productConfig.getProductSkuCode());
		productConfigBean.setChannelSkuRef(productConfig.getChannelSkuRef());
		productConfigBean.setCommision(productConfig.getCommision());
		productConfigBean.setCommisionAmt(productConfig.getCommisionAmt());
		productConfigBean.setTaxSp(productConfig.getTaxSp());
		productConfigBean.setTaxPo(productConfig.getTaxPo());
		productConfigBean.setDiscount(productConfig.getDiscount());
		productConfigBean.setEossDiscount(productConfig.getEossDiscount());
		productConfigBean.setMrp(productConfig.getMrp());
		productConfigBean.setSp(productConfig.getSp());
		productConfigBean.setProductPrice(productConfig.getProductPrice());
		productConfigBean.setSuggestedPOPrice(productConfig.getSuggestedPOPrice());
		productConfigBean.setEossDiscountValue(productConfig.getEossDiscountValue());
		productConfigBean.setGrossNR(productConfig.getGrossNR());
		productConfigBean.setProduct(productConfig.getProduct());
		return productConfigBean;
	}
	
	public static ProductConfig prepareProductConfigModel(ProductConfigBean productConfigBean){
		
		ProductConfig productConfig=new ProductConfig();
		
		productConfig.setProductConfigId(productConfigBean.getProductConfigId());
		productConfig.setProductId(productConfigBean.getProductId());
		productConfig.setProductName(productConfigBean.getProductName());
		productConfig.setProductSkuCode(productConfigBean.getProductSkuCode());
		productConfig.setChannelName(productConfigBean.getChannelName());
		productConfig.setChannelSkuRef(productConfigBean.getChannelSkuRef());
		productConfig.setCommision(productConfigBean.getCommision());
		productConfig.setTaxSp(productConfigBean.getTaxSp());
		productConfig.setTaxPo(productConfigBean.getTaxPo());
		productConfig.setDiscount(productConfigBean.getDiscount());
		productConfig.setEossDiscount(productConfigBean.getEossDiscount());
		productConfig.setMrp(productConfigBean.getMrp());
		productConfig.setSp(productConfigBean.getSp());
		productConfig.setProductPrice(productConfigBean.getProductPrice());
		productConfig.setSuggestedPOPrice(productConfigBean.getSuggestedPOPrice());
		productConfig.setEossDiscountValue(productConfigBean.getEossDiscountValue());
		productConfig.setGrossNR(productConfigBean.getGrossNR());
		return productConfig;
	}
	public static List<ProductConfigBean> prepareListOfProductConfigBean(List<ProductConfig> productConfigs){
		List<ProductConfigBean> beans = null;
		if (productConfigs != null && !productConfigs.isEmpty()) {
			beans = new ArrayList<ProductConfigBean>();
			ProductConfigBean bean = null;
			for (ProductConfig productConfig: productConfigs) {
				bean = new ProductConfigBean();
				bean.setProductConfigId(productConfig.getProductConfigId());
				bean.setProductId(productConfig.getProductId());
				bean.setProductName(productConfig.getProductName());
				bean.setProductSkuCode(productConfig.getProductSkuCode());
				bean.setChannelSkuRef(productConfig.getChannelSkuRef());
				bean.setCommision(productConfig.getCommision());
				bean.setTaxSp(productConfig.getTaxSp());
				bean.setTaxPo(productConfig.getTaxPo());
				bean.setDiscount(productConfig.getDiscount());
				bean.setEossDiscount(productConfig.getEossDiscount());
				bean.setMrp(productConfig.getMrp());
				bean.setSp(productConfig.getSp());
				bean.setProductPrice(productConfig.getProductPrice());
				bean.setSuggestedPOPrice(productConfig.getSuggestedPOPrice());
				bean.setEossDiscountValue(productConfig.getEossDiscountValue());
				bean.setGrossNR(productConfig.getGrossNR());
				beans.add(bean);
			}
		}		
		return beans;
	}

	/**
	 * Transform to List of PartnerBusinessGraph objects
	 * 
	 * @param partnerBusinessList
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<BusinessDetails> transformBusinessGraph(
			List<PartnerReportDetails> partnerBusinessList, String criteria) {
		Map<String, BusinessDetails> partnerBusinessGraphMap = new HashMap<String, BusinessDetails>();
		for (PartnerReportDetails partnerBusiness : partnerBusinessList) {
			String key = "";
			switch(criteria){
				case "partner":key = partnerBusiness.getPcName();break;
				case "category":key = partnerBusiness.getProductCategory();break;
				default: break;
			}
			BusinessDetails partnerBusinessGraph = partnerBusinessGraphMap.get(key);
			double netPartnerCommissionPaid = partnerBusiness.getNetPartnerCommissionPaid();
			int netSaleQty = partnerBusiness.getNetSaleQuantity();
			double netTDSToBeDeposited = partnerBusiness.getTdsToBeDeposited();
			double netPaymentResult = partnerBusiness.getNetPaymentResult();
			double paymentDifference = partnerBusiness.getPaymentDifference();
			double netTaxableSale = partnerBusiness.getOrderSP();
			double netActualSale = partnerBusiness.getNetRate() - partnerBusiness.getGrossNetRate()*partnerBusiness.getReturnQuantity();
			double netPrSale = partnerBusiness.getNetPr() - partnerBusiness.getNetPr()*(partnerBusiness.getReturnQuantity()/partnerBusiness.getGrossSaleQuantity());
			double netTaxToBePaid = partnerBusiness.getNetTaxToBePaid();
			double netEossDiscountPaid = 0;
			double netRate = partnerBusiness.getNetRate();
			double netProductCost = partnerBusiness.getProductPrice();
			double grossProfit = partnerBusiness.getGrossProfit();
			if (partnerBusinessGraph == null) {
				partnerBusinessGraph = new BusinessDetails();
			} else {
				netPartnerCommissionPaid += partnerBusinessGraph
						.getNetPartnerCommissionPaid();
				netSaleQty += partnerBusinessGraph.getNetSaleQty();
				netTDSToBeDeposited += partnerBusinessGraph
						.getNetTDSToBeDeposited();
				netPaymentResult += partnerBusinessGraph.getNetPaymentResult();
				paymentDifference += partnerBusinessGraph
						.getPaymentDifference();
				netTaxableSale += partnerBusinessGraph.getNetTaxableSale();
				netActualSale += partnerBusinessGraph.getNetActualSale();
				netPrSale += partnerBusinessGraph.getNetPrSale();
				netTaxToBePaid += partnerBusinessGraph.getNetTaxToBePaid();
				netEossDiscountPaid += partnerBusinessGraph
						.getNetEossDiscountPaid();
				netRate += partnerBusinessGraph.getNetNetRate();
				netProductCost += partnerBusinessGraph.getNetProductCost();
				grossProfit += partnerBusinessGraph.getGrossProfit();
			}
			partnerBusinessGraph.setPartner(key);
			partnerBusinessGraph.setCategoryName(key);
			partnerBusinessGraph
					.setNetPartnerCommissionPaid(netPartnerCommissionPaid);
			partnerBusinessGraph.setNetSaleQty(netSaleQty);
			partnerBusinessGraph.setNetTDSToBeDeposited(netTDSToBeDeposited);
			partnerBusinessGraph.setNetPaymentResult(netPaymentResult);
			partnerBusinessGraph.setPaymentDifference(paymentDifference);
			partnerBusinessGraph.setNetTaxableSale(netTaxableSale);
			partnerBusinessGraph.setNetActualSale(netActualSale);
			partnerBusinessGraph.setNetPrSale(netPrSale);
			partnerBusinessGraph.setNetTaxToBePaid(netTaxToBePaid);
			partnerBusinessGraph.setNetEossDiscountPaid(netEossDiscountPaid);
			partnerBusinessGraph.setNetProductCost(netProductCost);
			partnerBusinessGraph.setNetNetRate(netRate);
			partnerBusinessGraph.setGrossProfit(grossProfit);
			partnerBusinessGraphMap.put(key, partnerBusinessGraph);
		}

		List<BusinessDetails> partnerBusinessGraphList = new ArrayList<BusinessDetails>();
		Iterator entries = partnerBusinessGraphMap.entrySet().iterator();
		while (entries.hasNext()) {
			Entry<String, BusinessDetails> thisEntry = (Entry<String, BusinessDetails>) entries
					.next();
			BusinessDetails value = thisEntry.getValue();
			partnerBusinessGraphList.add(value);
		}

		return partnerBusinessGraphList;
	}

	/**
	 * Transform to List of PartnerCommissionPaidGraph objects
	 * 
	 * @param partnerCommissionList
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<CommissionDetails> transformCommissionPaid(
			List<PartnerReportDetails> partnerCommissionList, Date startDate, Date endDate, String criteria) {
		Map<String, CommissionDetails> partnerCommissionGraphMap = new HashMap<String, CommissionDetails>();
		for (PartnerReportDetails partnerBusiness : partnerCommissionList) {
			String key = "";
			switch(criteria){
				case "partner":key = partnerBusiness.getPcName();break;
				case "category":key = partnerBusiness.getProductCategory();break;
				default: break;
			}
			CommissionDetails partnerCommissionGraph = partnerCommissionGraphMap.get(key);
			Date shippedDate = partnerBusiness.getShippedDate();
			double grossPartnerCommissionPaid = 0;
			if(shippedDate!=null && shippedDate.after(startDate) && shippedDate.before(endDate)){
				grossPartnerCommissionPaid = partnerBusiness.getGrossCommissionQty();
			}
			Date returnDate = partnerBusiness.getReturnDate();
			double additionalReturnCharges = 0;
			double netReturnCommission = 0;
			if(returnDate!=null && returnDate.after(startDate) && returnDate.before(endDate)){
				additionalReturnCharges = partnerBusiness.getAdditionalReturnCharges();
				netReturnCommission = partnerBusiness.getReturnCommision();
			}
			int netSaleQty = 0;
			if(partnerBusiness.isPoOrder()){
				if(shippedDate!=null && shippedDate.after(startDate) && shippedDate.before(endDate))
					netSaleQty = partnerBusiness.getGrossSaleQuantity();
				if(returnDate!=null && returnDate.after(startDate) && returnDate.before(endDate))
					netSaleQty -= partnerBusiness.getReturnQuantity();
				 
			} else{
				netSaleQty = partnerBusiness.getNetSaleQuantity();
			}
			
			double netTDSToBeDeposited = partnerBusiness.getTdsToBeDeposited();
			double netSrCommission = netReturnCommission - additionalReturnCharges;
			double netChannelCommissionToBePaid = grossPartnerCommissionPaid - netReturnCommission + additionalReturnCharges;
			double netPartnerCommissionPaid = netChannelCommissionToBePaid;
			if (partnerCommissionGraph == null) {
				partnerCommissionGraph = new CommissionDetails();
			} else {
				netPartnerCommissionPaid += partnerCommissionGraph
						.getNetPartnerCommissionPaid();
				netSaleQty += partnerCommissionGraph.getNetSaleQty();
				netTDSToBeDeposited += partnerCommissionGraph
						.getNetTDSToBeDeposited();
				grossPartnerCommissionPaid += partnerCommissionGraph.getGrossPartnerCommissionPaid();
				additionalReturnCharges += partnerCommissionGraph.getAdditionalReturnCharges();
				netReturnCommission += partnerCommissionGraph.getNetReturnCommission();
				netSrCommission += partnerCommissionGraph.getNetSrCommisison();
				netChannelCommissionToBePaid += partnerCommissionGraph.getNetChannelCommissionToBePaid();
			}
			partnerCommissionGraph.setPartner(key);
			partnerCommissionGraph.setCategoryName(key);
			partnerCommissionGraph.setNetPartnerCommissionPaid(netPartnerCommissionPaid);
			partnerCommissionGraph.setNetSaleQty(netSaleQty);
			partnerCommissionGraph.setNetTDSToBeDeposited(netTDSToBeDeposited);
			partnerCommissionGraph.setGrossPartnerCommissionPaid(grossPartnerCommissionPaid);
			partnerCommissionGraph.setAdditionalReturnCharges(additionalReturnCharges);
			partnerCommissionGraph.setNetReturnCommission(netReturnCommission);
			partnerCommissionGraph.setNetSrCommisison(netSrCommission);
			partnerCommissionGraph.setNetChannelCommissionToBePaid(netChannelCommissionToBePaid);
			partnerCommissionGraphMap.put(key, partnerCommissionGraph);
		}

		List<CommissionDetails> partnerCommissionGraphList = new ArrayList<CommissionDetails>();
		Iterator entries = partnerCommissionGraphMap.entrySet().iterator();
		while (entries.hasNext()) {
			Entry<String, CommissionDetails> thisEntry = (Entry<String, CommissionDetails>) entries
					.next();
			CommissionDetails value = thisEntry.getValue();
			partnerCommissionGraphList.add(value);
		}

		return partnerCommissionGraphList;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<ChannelReportDetails> transformChannelReport(
			List<ChannelReportDetails> channelReportDetailsList, String criteria) {
		Map<String, ChannelReportDetails> categoryReportMap = new HashMap<String, ChannelReportDetails>();
		for(ChannelReportDetails currChannelReport: channelReportDetailsList){
			String key = "";
			switch(criteria){
				case "partner":key = currChannelReport.getPartner();break;
				case "category":key = currChannelReport.getCategory();break;
				default: break;
			}
			ChannelReportDetails channelReport = categoryReportMap.get(key);
			if(channelReport == null){
				channelReport = currChannelReport;
			} else{
				channelReport.setGpVsProductCost(channelReport.getGpVsProductCost() + currChannelReport.getGpVsProductCost());
				channelReport.setGrossNrAmount(channelReport.getGrossNrAmount() + currChannelReport.getGrossNrAmount());
				channelReport.setGrossProfit(channelReport.getGrossProfit() + currChannelReport.getGrossProfit());
				channelReport.setGrossQty(channelReport.getGrossQty() + currChannelReport.getGrossQty());
				channelReport.setGrossSpAmount(channelReport.getGrossSpAmount() + currChannelReport.getGrossSpAmount());
				channelReport.setNetAr(channelReport.getNetAr() + currChannelReport.getNetAr());
				channelReport.setNetNrAmount(channelReport.getNetNrAmount() + currChannelReport.getNetNrAmount());
				channelReport.setNetPaymentResult(channelReport.getNetPaymentResult() + currChannelReport.getNetPaymentResult());
				channelReport.setNetPureSaleNrAmount(channelReport.getNetPureSaleNrAmount() + currChannelReport.getNetPureSaleNrAmount());
				channelReport.setNetPureSaleQty(channelReport.getNetPureSaleQty() + currChannelReport.getNetPureSaleQty());
				channelReport.setNetPureSaleSpAmount(channelReport.getNetPureSaleSpAmount() + currChannelReport.getNetPureSaleSpAmount());
				channelReport.setNetQty(channelReport.getNetQty() + currChannelReport.getNetQty());
				channelReport.setNetReturnCharges(channelReport.getNetReturnCharges() + currChannelReport.getNetReturnCharges());
				channelReport.setNetSpAmount(channelReport.getNetSpAmount() + currChannelReport.getNetSpAmount());
				channelReport.setNetTaxLiability(channelReport.getNetTaxLiability() + currChannelReport.getNetTaxLiability());
				channelReport.setNetToBeReceived(channelReport.getNetToBeReceived() + currChannelReport.getNetToBeReceived());
				channelReport.setPr(channelReport.getPr() + currChannelReport.getPr());
				channelReport.setProductCost(channelReport.getProductCost() + currChannelReport.getProductCost());
				channelReport.setSaleRetNrAmount(channelReport.getSaleRetNrAmount() + currChannelReport.getSaleRetNrAmount());
				channelReport.setSaleRetQty(channelReport.getSaleRetQty() + currChannelReport.getSaleRetQty());
				channelReport.setSaleRetSpAmount(channelReport.getSaleRetSpAmount() + currChannelReport.getSaleRetSpAmount());
				channelReport.setSaleRetVsGrossSale(channelReport.getSaleRetVsGrossSale() + currChannelReport.getSaleRetVsGrossSale());
			}
			categoryReportMap.put(key, channelReport);
		}
		
		List<ChannelReportDetails> categoryReportList = new ArrayList<ChannelReportDetails>();
		Iterator entries = categoryReportMap.entrySet().iterator();
		while (entries.hasNext()) {
			Entry<String, ChannelReportDetails> thisEntry = (Entry<String, ChannelReportDetails>) entries
					.next();
			ChannelReportDetails value = thisEntry.getValue();
			categoryReportList.add(value);
		}
		
		return categoryReportList;
	}
	
/*	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<PaymentReceivedDetails> transformPayReceivedReport(
			List<PartnerReportDetails> channelReportDetailsList, String criteria) {
		List<PaymentReceivedDetails> reportList = new ArrayList<PaymentReceivedDetails>();
		for(PartnerReportDetails currChannelReport: channelReportDetailsList){
			PaymentReceivedDetails channelReport = new PaymentReceivedDetails();
			channelReport.setPaymentReceived(currChannelReport.getDateofPayment());
			channelReport.setUploadedDate(currChannelReport.getPaymentUploadedDate());
			channelReport.setPaymentType(currChannelReport.getPaymentType());
			channelReport.setPartner(currChannelReport.getPcName());
			double negativeAmount = currChannelReport.getNegativeAmount();
			double positiveAmount = currChannelReport.getPositiveAmount();
			channelReport.setNegativeAmount(negativeAmount);
			channelReport.setPositiveAmount(positiveAmount);
			double netAmountReceived = positiveAmount + negativeAmount; 
			channelReport.setNetAmountReceived(netAmountReceived);
			reportList.add(channelReport);
		}
		
		return reportList;
	}
*/	/**
	 * Merge BusinessGraph objects after 5th Iteration
	 * 
	 * @param businessGraphList
	 * @param string
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getBusinessSortedList(
			List businessGraphList, String string) {
		int maxLength = 5;
		if(businessGraphList.size()<=maxLength)
			return businessGraphList;
		int index = 1;
		List newBusinessGraphList = new ArrayList();
		BusinessDetails consolidated = new BusinessDetails();
		consolidated.setCategoryName("Others");
		consolidated.setPartner("Others");
		for(Object businessGraphObj: businessGraphList){
			if(index++ >= 5){
				BusinessDetails businessGraph = (BusinessDetails)businessGraphObj;
				switch(string){
					case "NetCommission": 
						double netCommission = businessGraph.getNetPartnerCommissionPaid();
						double netCommissionTotal = consolidated.getNetPartnerCommissionPaid();
						consolidated.setNetPartnerCommissionPaid(netCommission + netCommissionTotal);
						break;
					case "NetTax": 
						double netTaxToBePaid = businessGraph.getNetTaxToBePaid();
						double netTaxToBePaidTotal = consolidated.getNetTaxToBePaid();
						consolidated.setNetTaxToBePaid(netTaxToBePaid + netTaxToBePaidTotal);
						break;
					case "NetTDS": 
						double netTDSToBeDeposited = businessGraph.getNetTDSToBeDeposited();
						double netTDSToBeDepositedTotal = consolidated.getNetTDSToBeDeposited();
						consolidated.setNetTDSToBeDeposited(netTDSToBeDeposited + netTDSToBeDepositedTotal);
						break;
					case "NetNPR": 
						double netPrSale = businessGraph.getNetPrSale();
						double netPayDiff = businessGraph.getPaymentDifference();
						double netPrSaleTotal = consolidated.getNetPrSale();
						consolidated.setNetPrSale(netPrSale + netPrSaleTotal);
						double netPayDiffTotal = consolidated.getPaymentDifference();
						consolidated.setPaymentDifference(netPayDiff + netPayDiffTotal);
						break;
					case "NetTaxable": 
						double netTaxableSale = businessGraph.getNetTaxableSale();
						double netActualSale = businessGraph.getNetActualSale();
						double netPrSale2 = businessGraph.getNetPrSale();
						double netTaxableSaleTotal = consolidated.getNetTaxableSale();
						consolidated.setNetTaxableSale(netTaxableSale + netTaxableSaleTotal);
						double netActualSaleTotal = consolidated.getNetActualSale();
						consolidated.setNetTaxableSale(netActualSale + netActualSaleTotal);
						double netPrSale2Total = consolidated.getNetPrSale();
						consolidated.setNetTaxableSale(netPrSale2 + netPrSale2Total);
						break;
					default: break;
				}
			} else{
				newBusinessGraphList.add(businessGraphObj);
			}
		}
		newBusinessGraphList.add(consolidated);
		return newBusinessGraphList;
	}

	/**
	 * Merge CommissionPaidGraph objects after 5th Iteration
	 * 
	 * @param partnerCommissionGraphList
	 * @param string
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getCommissionSortedList(
			List commissionGraphList,
			String string) {
		int maxLength = 5;
		if(commissionGraphList.size()<=maxLength)
			return commissionGraphList;
		int index = 1;
		List newCommissionGraphList = new ArrayList();
		CommissionDetails consolidated = new CommissionDetails();
		consolidated.setCategoryName("Others");
		consolidated.setPartner("Others");
		for(Object commissionGraphObj: commissionGraphList){
			if(index++ >= 5){
				CommissionDetails commissionGraph = (CommissionDetails)commissionGraphObj;
				switch(string){
					case "GrossComm": 
						double grossCommission = commissionGraph.getGrossPartnerCommissionPaid();
						double returnCommission = commissionGraph.getNetReturnCommission();
						double addRetCharges = commissionGraph.getAdditionalReturnCharges();
						double grossCommissionTotal = consolidated.getGrossPartnerCommissionPaid();
						consolidated.setGrossPartnerCommissionPaid(grossCommission + grossCommissionTotal);
						double returnCommissionTotal = consolidated.getNetReturnCommission();
						consolidated.setNetReturnCommission(returnCommission + returnCommissionTotal);
						double addRetChargesTotal = consolidated.getAdditionalReturnCharges();
						consolidated.setAdditionalReturnCharges(addRetCharges + addRetChargesTotal);
						break;
					case "NetChann": 
						double netChannComm = commissionGraph.getNetChannelCommissionToBePaid();
						double netChannCommTotal = consolidated.getNetChannelCommissionToBePaid();
						consolidated.setNetChannelCommissionToBePaid(netChannComm + netChannCommTotal);
						break;
					default: break;
				}
			} else{
				newCommissionGraphList.add(commissionGraphObj);
			}
		}
		newCommissionGraphList.add(consolidated);
		return newCommissionGraphList;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getChannelSortedList(
			List<ChannelReportDetails> channelGraphList, String criteria) {
		int maxLength = 5;
		if(channelGraphList.size()<=maxLength)
			return channelGraphList;
		List<ChannelReportDetails> newChannelReportList = new ArrayList<>();
		int index = 1;
		boolean initialize = false;
		ChannelReportDetails consolidated = new ChannelReportDetails();
		for(ChannelReportDetails channelGraph: channelGraphList){
			if(index++ >= 5){
				if(!initialize){
					initialize = true;
					consolidated.setGrossProfit(channelGraph.getGrossProfit());
					consolidated.setGpVsProductCost(channelGraph.getGpVsProductCost());
					consolidated.setPr(channelGraph.getPr());
					consolidated.setProductCost(channelGraph.getProductCost());
					consolidated.setNetNrAmount(channelGraph.getNetNrAmount());
					consolidated.setGrossSpAmount(channelGraph.getGrossSpAmount());
					consolidated.setSaleRetSpAmount(channelGraph.getSaleRetSpAmount());
					consolidated.setNetSpAmount(channelGraph.getNetSpAmount());
					consolidated.setNetAr(channelGraph.getNetAr());
					consolidated.setNetToBeReceived(channelGraph.getNetToBeReceived());
					consolidated.setGrossQty(channelGraph.getGrossQty());
					consolidated.setSaleRetQty(channelGraph.getSaleRetQty());
					consolidated.setCategory("Others");
					consolidated.setPartner("Others");
				} else{
					switch(criteria){
						case "NetSaleSP":
							consolidated.setNetSpAmount(consolidated.getNetSpAmount() + channelGraph.getNetSpAmount());
							consolidated.setNetNrAmount(consolidated.getNetNrAmount() + channelGraph.getNetNrAmount());
							consolidated.setNetAr(consolidated.getNetAr() + channelGraph.getNetAr());
							break;
						case "NetAR":
							consolidated.setNetToBeReceived(consolidated.getNetToBeReceived() + channelGraph.getNetToBeReceived());
							consolidated.setNetAr(consolidated.getNetAr() + channelGraph.getNetAr());
							break;
						case "GSvSR":
							consolidated.setGrossQty(consolidated.getGrossQty() + channelGraph.getGrossQty());
							consolidated.setSaleRetQty(consolidated.getSaleRetQty() + channelGraph.getSaleRetQty());
							break;
						case "GSAvRA":
							consolidated.setGrossSpAmount(consolidated.getGrossSpAmount() + channelGraph.getGrossSpAmount());
							consolidated.setSaleRetSpAmount(consolidated.getSaleRetSpAmount() + channelGraph.getSaleRetSpAmount());
							break;
						case "GrossProfit":
							consolidated.setGrossProfit(consolidated.getGrossProfit() + channelGraph.getGrossProfit());
							break;
						case "GPCP": 
							consolidated.setGpVsProductCost(consolidated.getGpVsProductCost() + channelGraph.getGpVsProductCost());
							break;
						case "PR": 
							consolidated.setPr(consolidated.getPr() + channelGraph.getPr());
							consolidated.setProductCost(consolidated.getProductCost() + channelGraph.getProductCost());
							break;
						case "NR": 
							consolidated.setGrossProfit(consolidated.getGrossProfit() + channelGraph.getGrossProfit());
							consolidated.setNetNrAmount(consolidated.getNetNrAmount() + channelGraph.getNetNrAmount());
							break;
						default: break;
					}
				}
			} else{
				newChannelReportList.add(channelGraph);
			}
		}
		newChannelReportList.add(consolidated);
		return newChannelReportList;
	}

	public static List<DebtorsGraph1> transformDebtorsGraph1Graph(
			List<PartnerReportDetails> debtorsList, String criteria) {
		Map<String, DebtorsGraph1> debtorsGraph1Map = new HashMap<String, DebtorsGraph1>();
		for (PartnerReportDetails partnerBusiness : debtorsList) {
			String key = "";
			switch(criteria){
				case "partner":key = partnerBusiness.getPcName();break;
				case "category":key = partnerBusiness.getProductCategory();break;
				default: break;
			}
			DebtorsGraph1 debtorsGraph1 = debtorsGraph1Map.get(key);
			double netPaymentResult = partnerBusiness.getNetPaymentResult();
			double netPaymentDifference = partnerBusiness.getPaymentDifference();
			int orderTotal = 0;
			if(partnerBusiness.getPaymentDifference() >= -5 && partnerBusiness.getPaymentDifference() <= 5)
				orderTotal = 1;
			double netRate = 0;
			Date paymentDueDate = partnerBusiness.getPaymentDueDate();
			if(paymentDueDate!=null && paymentDueDate.after(new Date()))
				netRate = partnerBusiness.getNetRate();
			if (debtorsGraph1 == null) {
				debtorsGraph1 = new DebtorsGraph1();
			} else {
				netPaymentResult += debtorsGraph1.getNetPaymentResult();
				netPaymentDifference += debtorsGraph1.getPaymentDifference();
				orderTotal += debtorsGraph1.getNetPayDiffOrderQty();
				netRate += debtorsGraph1.getUpcomingPaymentNR();
			}
			debtorsGraph1.setNetPaymentResult(netPaymentResult);
			debtorsGraph1.setPaymentDifference(netPaymentDifference);
			debtorsGraph1.setNetPayDiffOrderQty(orderTotal);
			debtorsGraph1.setUpcomingPaymentNR(netRate);
			debtorsGraph1.setPartner(key);
			debtorsGraph1.setCategory(key);
					
			debtorsGraph1Map.put(key, debtorsGraph1);
		}

		List<DebtorsGraph1> debtorsGraph1List = new ArrayList<DebtorsGraph1>();
		Iterator entries = debtorsGraph1Map.entrySet().iterator();
		while (entries.hasNext()) {
			Entry<String, DebtorsGraph1> thisEntry = (Entry<String, DebtorsGraph1>) entries
					.next();
			DebtorsGraph1 value = thisEntry.getValue();
			debtorsGraph1List.add(value);
		}
		return debtorsGraph1List;
	}

	public static Object getDebtorsGraph1SortedList(
			List<DebtorsGraph1> debtorsGraph1List, String criteria) {
		int maxLength = 5;
		if(debtorsGraph1List.size()<=maxLength)
			return debtorsGraph1List;
		List<DebtorsGraph1> newdDebtorsGraph1List = new ArrayList<>();
		int index = 1;
		boolean initialize = false;
		DebtorsGraph1 consolidated = new DebtorsGraph1();
		for(DebtorsGraph1 debtorsGraph1: debtorsGraph1List){
			if(index++ >= 5){
				if(!initialize){
					initialize = true;
					consolidated.setNetPaymentResult(debtorsGraph1.getNetPaymentResult());
					consolidated.setPaymentDifference(debtorsGraph1.getPaymentDifference());
					consolidated.setNetPayDiffOrderQty(debtorsGraph1.getNetPayDiffOrderQty());
					consolidated.setUpcomingPaymentNR(debtorsGraph1.getUpcomingPaymentNR());
					consolidated.setCategory("Others");
					consolidated.setPartner("Others");
				} else{
					consolidated.setNetPaymentResult(consolidated.getNetPaymentResult() + debtorsGraph1.getNetPaymentResult());
					consolidated.setPaymentDifference(consolidated.getPaymentDifference() + debtorsGraph1.getPaymentDifference());
					consolidated.setNetPayDiffOrderQty(consolidated.getNetPayDiffOrderQty() + debtorsGraph1.getNetPayDiffOrderQty());
					consolidated.setUpcomingPaymentNR(consolidated.getUpcomingPaymentNR() + debtorsGraph1.getUpcomingPaymentNR());
					consolidated.setPaymentDifference(consolidated.getPaymentDifference() + debtorsGraph1.getPaymentDifference());
				}
			} else{
				newdDebtorsGraph1List.add(debtorsGraph1);
			}
		}
		newdDebtorsGraph1List.add(consolidated);
		return newdDebtorsGraph1List;
	}
}
