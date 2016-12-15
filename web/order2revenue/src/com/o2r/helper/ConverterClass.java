package com.o2r.helper;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.o2r.bean.AccountTransactionBean;
import com.o2r.bean.BusinessDetails;
import com.o2r.bean.CategoryBean;
import com.o2r.bean.ChannelCatNPR;
import com.o2r.bean.ChannelGP;
import com.o2r.bean.ChannelNPR;
import com.o2r.bean.ChannelNR;
import com.o2r.bean.ChannelNetQty;
import com.o2r.bean.ChannelReportDetails;
import com.o2r.bean.CommissionAnalysis;
import com.o2r.bean.CommissionDetails;
import com.o2r.bean.CustomerBean;
import com.o2r.bean.DebtorsGraph1;
import com.o2r.bean.EventsBean;
import com.o2r.bean.ExpenseBean;
import com.o2r.bean.ExpenseCategoryBean;
import com.o2r.bean.ExpensesDetails;
import com.o2r.bean.ManualChargesBean;
import com.o2r.bean.MetaPartnerBean;
import com.o2r.bean.NetPaymentResult;
import com.o2r.bean.NetProfitabilityST;
import com.o2r.bean.OrderBean;
import com.o2r.bean.OrderPaymentBean;
import com.o2r.bean.OrderRTOorReturnBean;
import com.o2r.bean.OrderTaxBean;
import com.o2r.bean.PartnerBean;
import com.o2r.bean.PartnerCategoryBean;
import com.o2r.bean.PartnerReportDetails;
import com.o2r.bean.PaymentUploadBean;
import com.o2r.bean.PlanBean;
import com.o2r.bean.ProductBean;
import com.o2r.bean.ProductConfigBean;
import com.o2r.bean.SellerAccountBean;
import com.o2r.bean.SellerAlertsBean;
import com.o2r.bean.SellerBean;
import com.o2r.bean.ShopcluesOrderAPIBean;
import com.o2r.bean.StateBean;
import com.o2r.bean.StateDeliveryTimeBean;
import com.o2r.bean.TaxCategoryBean;
import com.o2r.bean.TaxDetailBean;
import com.o2r.bean.UploadReportBean;
import com.o2r.bean.YearlyStockList;
import com.o2r.model.AccountTransaction;
import com.o2r.model.Category;
import com.o2r.model.Customer;
import com.o2r.model.Events;
import com.o2r.model.ExpenseCategory;
import com.o2r.model.Expenses;
import com.o2r.model.ManualCharges;
import com.o2r.model.MetaNRnReturnCharges;
import com.o2r.model.MetaNRnReturnConfig;
import com.o2r.model.MetaPartner;
import com.o2r.model.NRnReturnCharges;
import com.o2r.model.NRnReturnConfig;
import com.o2r.model.Order;
import com.o2r.model.OrderPayment;
import com.o2r.model.OrderRTOorReturn;
import com.o2r.model.OrderTax;
import com.o2r.model.Partner;
import com.o2r.model.PartnerCategoryMap;
import com.o2r.model.PaymentUpload;
import com.o2r.model.Plan;
import com.o2r.model.Product;
import com.o2r.model.ProductConfig;
import com.o2r.model.Seller;
import com.o2r.model.SellerAccount;
import com.o2r.model.SellerAlerts;
import com.o2r.model.ShopcluesOrderAPI;
import com.o2r.model.State;
import com.o2r.model.StateDeliveryTime;
import com.o2r.model.TaxCategory;
import com.o2r.model.TaxDetail;
import com.o2r.model.TaxablePurchases;
import com.o2r.model.UploadReport;
import com.o2r.bean.TaxablePurchaseBean;


public class ConverterClass {

	public static Order prepareModel(OrderBean orderBean) {
		Order order = new Order();
		if (orderBean != null) {
			order.setAwbNum(orderBean.getAwbNum());
			order.setGeOrderId(orderBean.getGeOrderId());
			order.setTypeIdentifier(orderBean.getTypeIdentifier());
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
			order.setOrderFileName(orderBean.getOrderFileName());			
			order.setVolShippingString(orderBean.getVolShippingString());
			order.setDwShippingString(orderBean.getDwShippingString());
			order.setGrossMargin(orderBean.getGrossMargin());
			order.setProductCost(orderBean.getProductCost());
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
				order.setTypeIdentifier(orderBean.getTypeIdentifier());
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
				order.setOrderFileName(orderBean.getOrderFileName());				
				order.setVolShippingString(orderBean.getVolShippingString());
				order.setDwShippingString(orderBean.getDwShippingString());
				order.setGrossMargin(orderBean.getGrossMargin());
				order.setProductCost(orderBean.getProductCost());
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
				bean.setTypeIdentifier(order.getTypeIdentifier());
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
				bean.setOrderFileName(order.getOrderFileName());
				bean.setVolShippingString(order.getVolShippingString());
				bean.setDwShippingString(order.getDwShippingString());
				bean.setGrossMargin(order.getGrossMargin());
				bean.setProductCost(order.getProductCost());
				
				bean.setSellerId(order.getSeller().getId());
				bean.setSellerName(order.getSeller().getName());
				
				if (order.getProductConfig() != null)
					bean.setProductConfig(prepareProductConfigBean(order
							.getProductConfig()));
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
			bean.setTypeIdentifier(order.getTypeIdentifier());
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
			bean.setOrderFileName(order.getOrderFileName());
			bean.setVolShippingString(order.getVolShippingString());
			bean.setDwShippingString(order.getDwShippingString());
			bean.setPccAmount(order.getPccAmount());
			bean.setFixedfee(order.getFixedfee());
			bean.setServiceTax(order.getServiceTax());
			bean.setGrossMargin(order.getGrossMargin());
			bean.setProductCost(order.getProductCost());
			
			bean.setSellerId(order.getSeller().getId());
			bean.setSellerName(order.getSeller().getName());
			
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
			seller.setVerCode(sellerBean.getVerCode());
			seller.setZipcode(sellerBean.getZipcode());
			seller.setPlan(sellerBean.getPlan());
			seller.setSellerAccount(sellerBean.getSellerAccount());
			seller.setStateDeliveryTime(sellerBean.getStateDeliveryTime());
		}
		return seller;
	}

	public static List<StateDeliveryTime> prepareStateDeliveryTimeModel(
			List<StateDeliveryTimeBean> stateDeliveryTimeBean) {
		List<StateDeliveryTime> models = null;
		if (stateDeliveryTimeBean != null && !stateDeliveryTimeBean.isEmpty()) {
			models = new ArrayList<StateDeliveryTime>();
			StateDeliveryTime model = null;
			for (StateDeliveryTimeBean aStateDeliveryTimeBean : stateDeliveryTimeBean) {
				model = new StateDeliveryTime();
				model.setDeliveryTime(aStateDeliveryTimeBean.getDeliveryTime());
				model.setState(prepareStateModel(aStateDeliveryTimeBean
						.getState()));
				model.setSeller(prepareSellerModel(aStateDeliveryTimeBean
						.getSeller()));
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
				bean.setVerCode(seller.getVerCode());
				bean.setZipcode(seller.getZipcode());
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
			bean.setVerCode(seller.getVerCode());
			bean.setZipcode(seller.getZipcode());
			bean.setPlan(seller.getPlan());
			bean.setSellerAccount(seller.getSellerAccount());
			bean.setStateDeliveryTime(seller.getStateDeliveryTime());
		}
		return bean;
	}

	public static StateDeliveryTimeBean prepareStateDeliveryTimeBean(
			StateDeliveryTime stateDeliveryTime) {
		StateDeliveryTimeBean bean = null;
		if (stateDeliveryTime != null) {
			bean = new StateDeliveryTimeBean();
			bean.setDeliveryTime(stateDeliveryTime.getDeliveryTime());
			bean.setState(prepareStateBean(stateDeliveryTime.getState()));
		}

		return bean;
	}

	public static List<StateDeliveryTimeBean> prepareStateDeliveryTimeBeanList(
			List<StateDeliveryTime> stateDeliveryTime) {
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
			partner.setPaymentCategory(partnerBean.getPaymentCategory());
			partner.setIsshippeddatecalc(partnerBean.isIsshippeddatecalc());
			partner.setNoofdaysfromshippeddate(partnerBean
					.getNoofdaysfromshippeddate());
			partner.setIsshippeddatecalcPost(partnerBean
					.isIsshippeddatecalcPost());
			partner.setNoofdaysfromshippeddatePost(partnerBean
					.getNoofdaysfromshippeddatePost());
			partner.setIsshippeddatecalcOthers(partnerBean
					.isIsshippeddatecalcOthers());
			partner.setNoofdaysfromshippeddateOthers(partnerBean
					.getNoofdaysfromshippeddateOthers());
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

	public static MetaPartner prepareMetaPartnerModel(
			MetaPartnerBean partnerBean) {

		MetaPartner partner = new MetaPartner();
		if (partnerBean != null) {
			partner.setPcId(partnerBean.getPcId());
			partner.setPcDesc(partnerBean.getPcDesc());
			partner.setPcLogoUrl(partnerBean.getPcLogoUrl());
			partner.setPcName(partnerBean.getPcName());
			partner.setPaymentType(partnerBean.getPaymentType());
			partner.setPaymentCategory(partnerBean.getPaymentCategory());
			partner.setIsshippeddatecalc(partnerBean.isIsshippeddatecalc());
			partner.setNoofdaysfromshippeddate(partnerBean
					.getNoofdaysfromshippeddate());
			partner.setIsshippeddatecalcPost(partnerBean.isIsshippeddatecalcPost());
			partner.setNoofdaysfromshippeddatePost(partnerBean.getNoofdaysfromshippeddatePost());
			partner.setIsshippeddatecalcOthers(partnerBean.isIsshippeddatecalcOthers());
			partner.setNoofdaysfromshippeddateOthers(partnerBean.getNoofdaysfromshippeddateOthers());
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
		}
		return partner;
	}

	public static Partner convertPartner(MetaPartner metaPartner) {

		Partner partner = new Partner();
		if (metaPartner != null) {
			partner.setPcDesc(metaPartner.getPcDesc());
			partner.setPcLogoUrl(metaPartner.getPcLogoUrl());
			partner.setPcName(metaPartner.getPcName());
			partner.setPaymentType(metaPartner.getPaymentType());
			partner.setPaymentCategory(metaPartner.getPaymentCategory());
			partner.setIsshippeddatecalc(metaPartner.isIsshippeddatecalc());
			partner.setNoofdaysfromshippeddate(metaPartner
					.getNoofdaysfromshippeddate());
			partner.setIsshippeddatecalcPost(metaPartner.isIsshippeddatecalcPost());
			partner.setNoofdaysfromshippeddatePost(metaPartner
					.getNoofdaysfromshippeddatePost());
			partner.setIsshippeddatecalcOthers(metaPartner.isIsshippeddatecalcOthers());
			partner.setNoofdaysfromshippeddateOthers(metaPartner
					.getNoofdaysfromshippeddateOthers());
			partner.setStartcycleday(metaPartner.getStartcycleday());
			partner.setPaycycleduration(metaPartner.getPaycycleduration());
			partner.setPaydaysfromstartday(metaPartner.getPaydaysfromstartday());
			partner.setMaxReturnAcceptance(metaPartner.getMaxReturnAcceptance());
			partner.setMaxRTOAcceptance(metaPartner.getMaxRTOAcceptance());
			partner.setTaxcategory(metaPartner.getTaxcategory());
			partner.setTaxrate(metaPartner.getTaxrate());
			partner.setTdsApplicable(metaPartner.isTdsApplicable());
			partner.setPaycyclefromshipordel(metaPartner
					.isPaycyclefromshipordel());
			partner.setMonthlypaydate(metaPartner.getMonthlypaydate());
			partner.setNrnReturnConfig(convertNRnReturnConfig(metaPartner
					.getNrnReturnConfig()));
		}
		return partner;
	}

	public static NRnReturnConfig convertNRnReturnConfig(
			MetaNRnReturnConfig metaNRnReturnConfig) {

		NRnReturnConfig nRnReturnConfig = new NRnReturnConfig();
		if (metaNRnReturnConfig != null) {
			nRnReturnConfig.setNrCalculator(metaNRnReturnConfig
					.isNrCalculator());
			nRnReturnConfig.setCommissionType(metaNRnReturnConfig
					.getCommissionType());
			nRnReturnConfig.setCategoryWiseCommsion(metaNRnReturnConfig
					.getCategoryWiseCommsion());
			nRnReturnConfig.setWhicheverGreaterPCC(metaNRnReturnConfig
					.isWhicheverGreaterPCC());
			nRnReturnConfig.setPccfixedAmt(metaNRnReturnConfig.isPccfixedAmt());
			nRnReturnConfig.setPccpercentSP(metaNRnReturnConfig
					.isPccpercentSP());
			nRnReturnConfig.setShippingFeeType(metaNRnReturnConfig
					.getShippingFeeType());
			nRnReturnConfig.setRetCharSFType(metaNRnReturnConfig
					.getRetCharSFType());
			nRnReturnConfig.setRetCharBRType(metaNRnReturnConfig
					.getRetCharBRType());
			nRnReturnConfig.setRetCharSFFF(metaNRnReturnConfig.isRetCharSFFF());
			nRnReturnConfig.setRetCharSFShipFee(metaNRnReturnConfig
					.isRetCharSFShipFee());
			nRnReturnConfig.setRetCharSFSerTax(metaNRnReturnConfig
					.isRetCharSFSerTax());
			nRnReturnConfig.setRetCharSFPCC(metaNRnReturnConfig
					.isRetCharSFPCC());
			nRnReturnConfig.setRetCharBRFF(metaNRnReturnConfig.isRetCharBRFF());
			nRnReturnConfig.setRetCharBRShipFee(metaNRnReturnConfig
					.isRetCharBRShipFee());
			nRnReturnConfig.setRetCharBRSerTax(metaNRnReturnConfig
					.isRetCharBRSerTax());
			nRnReturnConfig.setRetCharBRPCC(metaNRnReturnConfig
					.isRetCharBRPCC());
			nRnReturnConfig.setRTOCharSFType(metaNRnReturnConfig
					.getRTOCharSFType());
			nRnReturnConfig.setRTOCharBRType(metaNRnReturnConfig
					.getRTOCharBRType());
			nRnReturnConfig.setRTOCharSFFF(metaNRnReturnConfig.isRTOCharSFFF());
			nRnReturnConfig.setRTOCharSFShipFee(metaNRnReturnConfig
					.isRTOCharSFShipFee());
			nRnReturnConfig.setRTOCharSFSerTax(metaNRnReturnConfig
					.isRTOCharSFSerTax());
			nRnReturnConfig.setRTOCharSFPCC(metaNRnReturnConfig
					.isRTOCharSFPCC());
			nRnReturnConfig.setRTOCharBRFF(metaNRnReturnConfig.isRTOCharBRFF());
			nRnReturnConfig.setRTOCharBRShipFee(metaNRnReturnConfig
					.isRTOCharBRShipFee());
			nRnReturnConfig.setRTOCharBRSerTax(metaNRnReturnConfig
					.isRTOCharBRSerTax());
			nRnReturnConfig.setRTOCharBRPCC(metaNRnReturnConfig
					.isRTOCharBRPCC());
			nRnReturnConfig.setRepCharSFType(metaNRnReturnConfig
					.getRepCharSFType());
			nRnReturnConfig.setRepCharBRType(metaNRnReturnConfig
					.getRepCharBRType());
			nRnReturnConfig.setRepCharSFFF(metaNRnReturnConfig.isRepCharSFFF());
			nRnReturnConfig.setRepCharSFShipFee(metaNRnReturnConfig
					.isRepCharSFShipFee());
			nRnReturnConfig.setRepCharSFSerTax(metaNRnReturnConfig
					.isRepCharSFSerTax());
			nRnReturnConfig.setRepCharSFPCC(metaNRnReturnConfig
					.isRepCharSFPCC());
			nRnReturnConfig.setRepCharBRFF(metaNRnReturnConfig.isRepCharBRFF());
			nRnReturnConfig.setRepCharBRShipFee(metaNRnReturnConfig
					.isRepCharBRShipFee());
			nRnReturnConfig.setRepCharBRSerTax(metaNRnReturnConfig
					.isRepCharBRSerTax());
			nRnReturnConfig.setRepCharBRPCC(metaNRnReturnConfig
					.isRepCharBRPCC());
			nRnReturnConfig.setPDCharSFType(metaNRnReturnConfig
					.getPDCharSFType());
			nRnReturnConfig.setPDCharBRType(metaNRnReturnConfig
					.getPDCharBRType());
			nRnReturnConfig.setPDCharSFFF(metaNRnReturnConfig.isPDCharSFFF());
			nRnReturnConfig.setPDCharSFShipFee(metaNRnReturnConfig
					.isPDCharSFShipFee());
			nRnReturnConfig.setPDCharSFSerTax(metaNRnReturnConfig
					.isPDCharSFSerTax());
			nRnReturnConfig.setPDCharSFPCC(metaNRnReturnConfig.isPDCharSFPCC());
			nRnReturnConfig.setPDCharBRFF(metaNRnReturnConfig.isPDCharBRFF());
			nRnReturnConfig.setPDCharBRShipFee(metaNRnReturnConfig
					.isPDCharBRShipFee());
			nRnReturnConfig.setPDCharBRSerTax(metaNRnReturnConfig
					.isPDCharBRSerTax());
			nRnReturnConfig.setPDCharBRPCC(metaNRnReturnConfig.isPDCharBRPCC());
			nRnReturnConfig.setCanCharSFARTDType(metaNRnReturnConfig
					.getCanCharSFARTDType());
			nRnReturnConfig.setCanCharSFBFRTDType(metaNRnReturnConfig
					.getCanCharSFBFRTDType());
			nRnReturnConfig.setCanCharBRType(metaNRnReturnConfig
					.getCanCharBRType());
			nRnReturnConfig.setCanCharSFFF(metaNRnReturnConfig.isCanCharSFFF());
			nRnReturnConfig.setCanCharSFShipFee(metaNRnReturnConfig
					.isCanCharSFShipFee());
			nRnReturnConfig.setCanCharSFSerTax(metaNRnReturnConfig
					.isCanCharSFSerTax());
			nRnReturnConfig.setCanCharSFPCC(metaNRnReturnConfig
					.isCanCharSFPCC());
			nRnReturnConfig.setCanCharSFBRTDFF(metaNRnReturnConfig
					.isCanCharSFBRTDFF());
			nRnReturnConfig.setCanCharSFBRTDShipFee(metaNRnReturnConfig
					.isCanCharSFBRTDShipFee());
			nRnReturnConfig.setCanCharSFBRTDSerTax(metaNRnReturnConfig
					.isCanCharSFBRTDSerTax());
			nRnReturnConfig.setCanCharSFBRTDPCC(metaNRnReturnConfig
					.isCanCharSFBRTDPCC());
			nRnReturnConfig.setCanCharBRFF(metaNRnReturnConfig.isCanCharBRFF());
			nRnReturnConfig.setCanCharBRShipFee(metaNRnReturnConfig
					.isCanCharBRShipFee());
			nRnReturnConfig.setCanCharBRSerTax(metaNRnReturnConfig
					.isCanCharBRSerTax());
			nRnReturnConfig.setCanCharBRPCC(metaNRnReturnConfig
					.isCanCharBRPCC());
			nRnReturnConfig.setRevShippingFeeType(metaNRnReturnConfig
					.getRevShippingFeeType());
			nRnReturnConfig.setRetCharSFRevShipFee(metaNRnReturnConfig
					.isRetCharSFRevShipFee());
			nRnReturnConfig.setRepCharSFRevShipFee(metaNRnReturnConfig
					.isRepCharSFRevShipFee());
			nRnReturnConfig.setPDCharSFRevShipFee(metaNRnReturnConfig
					.isPDCharSFRevShipFee());
			nRnReturnConfig.setRTOCharSFRevShipFee(metaNRnReturnConfig
					.isRTOCharSFRevShipFee());
			nRnReturnConfig.setCanCharSFBRTDRevShipFee(metaNRnReturnConfig
					.isCanCharSFBRTDRevShipFee());
			nRnReturnConfig.setCanCharSFARTDRevShipFee(metaNRnReturnConfig
					.isCanCharSFARTDRevShipFee());
			nRnReturnConfig.setRetCharBRRevShipFee(metaNRnReturnConfig
					.isRetCharBRRevShipFee());
			nRnReturnConfig.setRepCharBRRevShipFee(metaNRnReturnConfig
					.isRepCharBRRevShipFee());
			nRnReturnConfig.setPDCharBRRevShipFee(metaNRnReturnConfig
					.isPDCharBRRevShipFee());
			nRnReturnConfig.setRTOCharBRRevShipFee(metaNRnReturnConfig
					.isRTOCharBRRevShipFee());
			nRnReturnConfig.setCanCharBRRevShipFee(metaNRnReturnConfig
					.isCanCharBRRevShipFee());
			nRnReturnConfig.setNrCalculatorEvent(metaNRnReturnConfig
					.getNrCalculatorEvent());
			nRnReturnConfig.setReturnCalculatorEvent(metaNRnReturnConfig
					.getReturnCalculatorEvent());
			nRnReturnConfig.setTaxSpType(metaNRnReturnConfig.getTaxSpType());
			nRnReturnConfig.setTaxPoType(metaNRnReturnConfig.getTaxPoType());
			List<NRnReturnCharges> nRnReturnChargeList = new ArrayList<NRnReturnCharges>();
			for (MetaNRnReturnCharges metaNRnReturnCharges : metaNRnReturnConfig
					.getCharges()) {
				nRnReturnChargeList
						.add(convertNRnReturnCharges(metaNRnReturnCharges));
			}
			nRnReturnConfig.setCharges(nRnReturnChargeList);
		}
		return nRnReturnConfig;
	}

	public static NRnReturnCharges convertNRnReturnCharges(
			MetaNRnReturnCharges metaNRnReturnCharges) {

		NRnReturnCharges nRnReturnCharges = new NRnReturnCharges();
		if (metaNRnReturnCharges != null) {
			nRnReturnCharges
					.setChargeName(metaNRnReturnCharges.getChargeName());
			nRnReturnCharges.setChargeAmount(metaNRnReturnCharges
					.getChargeAmount());
			nRnReturnCharges.setCriteria(metaNRnReturnCharges.getCriteria());
			nRnReturnCharges.setCriteriaRange(metaNRnReturnCharges
					.getCriteriaRange());
		}
		return nRnReturnCharges;
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
				bean.setPaymentCategory(partner.getPaymentCategory());
				bean.setIsshippeddatecalc(partner.isIsshippeddatecalc());
				bean.setNoofdaysfromshippeddate(partner
						.getNoofdaysfromshippeddate());
				bean.setIsshippeddatecalcPost(partner.isIsshippeddatecalcPost());
				bean.setNoofdaysfromshippeddatePost(partner
						.getNoofdaysfromshippeddatePost());
				bean.setIsshippeddatecalcOthers(partner.isIsshippeddatecalcOthers());
				bean.setNoofdaysfromshippeddateOthers(partner
						.getNoofdaysfromshippeddateOthers());
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
			bean.setPaymentCategory(partner.getPaymentCategory());
			bean.setIsshippeddatecalc(partner.isIsshippeddatecalc());
			bean.setNoofdaysfromshippeddate(partner
					.getNoofdaysfromshippeddate());
			bean.setIsshippeddatecalcPost(partner.isIsshippeddatecalcPost());
			bean.setNoofdaysfromshippeddatePost(partner
					.getNoofdaysfromshippeddatePost());
			bean.setIsshippeddatecalcOthers(partner.isIsshippeddatecalcOthers());
			bean.setNoofdaysfromshippeddateOthers(partner
					.getNoofdaysfromshippeddateOthers());
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
			bean.setTaxFreePriceLimit(category.getTaxFreePriceLimit());
			bean.setOpeningStock(category.getOpeningStock());
			bean.setSkuCount(category.getSkuCount());
			bean.setOsUpdate(category.getOsUpdate());
			if (category.getPartnerCatRef() != null
					&& category.getPartnerCatRef().contains(GlobalConstant.orderUniqueSymbol)) {
				bean.setPartnerCatRef(category.getPartnerCatRef().replace(GlobalConstant.orderUniqueSymbol, ""));
			} else {
				bean.setPartnerCatRef(category.getPartnerCatRef());
			}
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
			category.setTaxFreePriceLimit(categoryBean.getTaxFreePriceLimit());
			category.setOpeningStock(categoryBean.getOpeningStock());
			category.setSkuCount(categoryBean.getSkuCount());
			category.setOsUpdate(categoryBean.getOsUpdate());
			category.setPartnerCatRef(categoryBean.getPartnerCatRef());
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
				bean.setTaxFreePriceLimit(category.getTaxFreePriceLimit());
				bean.setOpeningStock(category.getOpeningStock());
				bean.setSkuCount(category.getSkuCount());
				bean.setOsUpdate(category.getOsUpdate());
				if (category.getPartnerCatRef() != null
						&& category.getPartnerCatRef().contains(GlobalConstant.orderUniqueSymbol)) {
					bean.setPartnerCatRef(category.getPartnerCatRef().replace(GlobalConstant.orderUniqueSymbol, ""));
				} else {
					bean.setPartnerCatRef(category.getPartnerCatRef());
				}
				beans.add(bean);
			}
		}
		return beans;
	}

	public static ProductBean prepareProductBean(Product product) {
		ProductBean bean = null;
		if (product != null) {
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
			customer.setStatus(customerBean.getStatus());

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
			customerBean.setStatus(customer.getStatus());

		}

		return customerBean;
	}
	
	public static SellerAlertsBean prepareSellerAlertsBean(SellerAlerts sellerAlerts) {
		SellerAlertsBean sellerAlertsBean = new SellerAlertsBean();
		if(sellerAlerts != null){
			sellerAlertsBean.setAlertId(sellerAlerts.getSellerId());
			sellerAlertsBean.setAlertType(sellerAlerts.getAlertType());
			sellerAlertsBean.setAlertMessage(sellerAlerts.getAlertMessage());
			sellerAlertsBean.setAlertDate(sellerAlerts.getAlertDate());
			sellerAlertsBean.setSellerId(sellerAlerts.getSellerId());
			sellerAlertsBean.setStatus(sellerAlerts.getStatus());
		}
		return sellerAlertsBean;
	}
	
	public static SellerAlerts prepareSellerAlertsModel(SellerAlertsBean sellerAlertsBean) {
		SellerAlerts sellerAlerts = new SellerAlerts();
		if(sellerAlertsBean != null){
			sellerAlerts.setAlertId(sellerAlertsBean.getSellerId());
			sellerAlerts.setAlertType(sellerAlertsBean.getAlertType());
			sellerAlerts.setAlertMessage(sellerAlertsBean.getAlertMessage());
			sellerAlerts.setAlertDate(sellerAlertsBean.getAlertDate());
			sellerAlerts.setSellerId(sellerAlertsBean.getSellerId());
			sellerAlerts.setStatus(sellerAlertsBean.getStatus());
		}
		return sellerAlerts;
	}

	public static List<CustomerBean> prepareListofCusomerBean(
			List<Customer> customers) {
		List<CustomerBean> beans = null;
		if (customers != null && !customers.isEmpty()) {
			beans = new ArrayList<CustomerBean>();
			CustomerBean bean = null;
			for (Customer customer : customers) {
				bean = new CustomerBean();
				bean.setCustomerId(customer.getCustomerId());
				bean.setCustomerAddress(customer.getCustomerAddress());
				bean.setCustomerCity(customer.getCustomerCity());
				bean.setCustomerEmail(customer.getCustomerEmail());
				bean.setCustomerName(customer.getCustomerName());
				bean.setCustomerPhnNo(customer.getCustomerPhnNo());
				bean.setZipcode(customer.getZipcode());
				beans.add(bean);
			}
		}
		return beans;
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
		} else {
			beans = new ArrayList<ExpenseBean>();
			beans.add(new ExpenseBean());
		}

		return beans;
	}

	
	
	
	
	
	public static TaxablePurchases prepareTaxablePurchasesModel(TaxablePurchaseBean bean) {
		TaxablePurchases taxablePurchases = new TaxablePurchases();
		if (bean != null) {
			
			System.out.println("bean.getTaxPurchaseId() "+bean.getTaxPurchaseId());
			System.out.println("bean.getTaxCategory() "+bean.getTaxCategory());
			System.out.println("bean.getTaxRate() "+bean.getTaxRate());
			System.out.println("bean.getPurchaseDate() "+bean.getPurchaseDate());
			System.out.println("bean.getBasicPrice() "+bean.getBasicPrice());
			System.out.println("bean.getParticular() "+bean.getParticular());
			System.out.println("bean.getTaxRate() "+bean.getTaxRate());
			System.out.println("bean.getTaxAmount() "+bean.getTaxAmount());
			
			
			taxablePurchases.setTaxPurchaseId(bean.getTaxPurchaseId());
			taxablePurchases.setTaxCategory(bean.getTaxCategory());
			taxablePurchases.setTaxRate(bean.getTaxRate());
			taxablePurchases.setPurchaseDate(bean.getPurchaseDate());
			taxablePurchases.setBasicPrice(bean.getBasicPrice());
			taxablePurchases.setParticular(bean.getParticular());			
			taxablePurchases.setTaxRate(bean.getTaxRate());
			taxablePurchases.setTaxAmount(bean.getTaxAmount());	
			taxablePurchases.setTotalAmount(bean.getTotalAmount());
			//taxablePurchases.setSeller(Seller  Bean.getId());
		}
		return taxablePurchases;
	}
	
	
	public static OrderTax prepareOrderTaxModel(OrderTaxBean bean) {
		OrderTax orderTax = new OrderTax();
		if (bean != null) {
			orderTax.setNetTax(bean.getNetTax());
			orderTax.setTdsonReturnAmt(bean.getTdsonReturnAmt());
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
			orderTax.setTdsonReturnAmt(bean.getTdsonReturnAmt());
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
			orderPayment.setPaymentVar(bean.getPaymentVar());
			orderPayment.setPaymentFileName(bean.getPaymentFileName());
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
			orderPayment.setPaymentVar(bean.getPaymentVar());
			orderPayment.setPaymentFileName(bean.getPaymentFileName());
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
			orderReturn.setBadReturnQty(bean.getBadReturnQty());
			orderReturn.setInventoryType(bean.getInventoryType());
			orderReturn.setReturnFileName(bean.getReturnFileName());
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
			orderReturn.setBadReturnQty(bean.getBadReturnQty());
			orderReturn.setInventoryType(bean.getInventoryType());
			orderReturn.setReturnFileName(bean.getReturnFileName());
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
			/*bean.setOrders(ConverterClass.prepareListofBean(payUpload
					.getOrders()));*/
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
			/*payment.setOrders(ConverterClass.prepareListofOrdersFromBean(bean
					.getOrders()));*/
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
			bean.setTaxCatType(category.getTaxCatType());
			bean.setProductCategoryCST(category.getProductCategoryCST());
			bean.setProductCategoryLST(category.getProductCategoryLST());
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
			category.setTaxCatType(bean.getTaxCatType());
			category.setProductCategoryCST(bean.getProductCategoryCST());
			category.setProductCategoryLST(bean.getProductCategoryLST());
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
				String productCategoryStr = "";
				bean = new TaxCategoryBean();
				bean.setPartner(category.getPartner());
				bean.setTaxCatDescription(category.getTaxCatDescription());
				bean.setTaxCatId(category.getTaxCatId());
				bean.setTaxCatName(category.getTaxCatName());
				bean.setUploadDate(category.getUploadDate());
				bean.setTaxPercent(category.getTaxPercent());
				bean.setTaxCatType(category.getTaxCatType());
				bean.setProductCategoryCST(category.getProductCategoryCST());
				bean.setProductCategoryLST(category.getProductCategoryLST());
				if (category.getTaxCatType() != null) {
					if (category.getTaxCatType().equalsIgnoreCase("LST")) {
						if (category.getProductCategoryLST() != null) {
							for (Category prodCat : category
									.getProductCategoryLST()) {
								productCategoryStr += prodCat.getCatName()
										+ ",";
							}
						}
					} else {
						if (category.getProductCategoryCST() != null) {
							for (Category prodCat : category
									.getProductCategoryCST()) {
								productCategoryStr += prodCat.getCatName()
										+ ",";
							}
						}
					}
				}
				if (productCategoryStr.length() > 1) {
					bean.setProductCategoryStr(productCategoryStr.substring(0,
							productCategoryStr.length() - 1));
				}
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
	
	public static PlanBean preparePlanBean(Plan plan) {
		PlanBean planbean = new PlanBean();
		if (plan != null) {
			planbean.setPlanName(plan.getPlanName());
			planbean.setActive(plan.isActive());
			planbean.setOrderCount(plan.getOrderCount());
			planbean.setDescription(plan.getDescription());
			planbean.setPid(plan.getPid());
			planbean.setPlanId(plan.getPlanId());
			planbean.setPlanPrice(plan.getPlanPrice());
		}
		return planbean;
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

	public static Events prepareEventsModel(EventsBean eventsBean) {

		Events events = new Events();
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
		events.setSkuList(eventsBean.getSkuList());
		events.setStatus(eventsBean.getStatus());
		events.setSelectAll(eventsBean.getSelectAll());
		
		return events;

	}

	public static EventsBean prepareEventsBean(Events events) {

		EventsBean eventsBean = new EventsBean();
		if (events != null) {
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
			eventsBean.setSkuList(events.getSkuList());
			eventsBean.setStatus(events.getStatus());
			eventsBean.setSelectAll(events.getSelectAll());
			
		}
		return eventsBean;
	}

	public static List<EventsBean> prepareListOfEventsBean(List<Events> events) {
		List<EventsBean> beans = null;
		if (events != null && !events.isEmpty()) {
			beans = new ArrayList<EventsBean>();
			EventsBean bean = null;
			for (Events event : events) {
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
				bean.setSkuList(event.getSkuList());
				bean.setStatus(event.getStatus());
				bean.setSelectAll(event.getSelectAll());
				beans.add(bean);
			}
		}
		return beans;
	}

	public static ProductConfigBean prepareProductConfigBean(
			ProductConfig productConfig) {

		ProductConfigBean productConfigBean = new ProductConfigBean();
		productConfigBean
				.setProductConfigId(productConfig.getProductConfigId());
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
		productConfigBean.setSuggestedPOPrice(productConfig
				.getSuggestedPOPrice());
		productConfigBean.setEossDiscountValue(productConfig
				.getEossDiscountValue());
		productConfigBean.setGrossNR(productConfig.getGrossNR());
		productConfigBean.setProduct(productConfig.getProduct());
		return productConfigBean;
	}

	public static ProductConfig prepareProductConfigModel(
			ProductConfigBean productConfigBean) {

		ProductConfig productConfig = new ProductConfig();

		productConfig
				.setProductConfigId(productConfigBean.getProductConfigId());
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
		productConfig.setSuggestedPOPrice(productConfigBean
				.getSuggestedPOPrice());
		productConfig.setEossDiscountValue(productConfigBean
				.getEossDiscountValue());
		productConfig.setGrossNR(productConfigBean.getGrossNR());
		return productConfig;
	}

	public static List<ProductConfigBean> prepareListOfProductConfigBean(
			List<ProductConfig> productConfigs) {
		List<ProductConfigBean> beans = null;
		if (productConfigs != null && !productConfigs.isEmpty()) {
			beans = new ArrayList<ProductConfigBean>();
			ProductConfigBean bean = null;
			for (ProductConfig productConfig : productConfigs) {
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
			switch (criteria) {
			case "partner":
				key = partnerBusiness.getPcName();
				break;
			case "category":
				key = partnerBusiness.getParentCategory();
				if (StringUtils.isEmpty(key))
					key = "B2B";
				break;
			default:
				break;
			}
			BusinessDetails partnerBusinessGraph = partnerBusinessGraphMap
					.get(key);
			double netPartnerCommissionPaid = partnerBusiness
					.getNetPartnerCommissionPaid();
			int netSaleQty = partnerBusiness.getNetSaleQuantity();
			double netTDSToBeDeposited = partnerBusiness.getTdsToBeDeposited();
			double netTDS2 = partnerBusiness.getTdsToBeDeducted2();
			double netTDS10 = partnerBusiness.getTdsToBeDeducted10();
			double netPaymentResult = partnerBusiness.getNetPaymentResult();
			double paymentDifference = partnerBusiness.getPaymentDifference();
			double netTaxableSale = partnerBusiness.getNetSP();
			double netTaxToBePaid = partnerBusiness.getNetTaxToBePaid();
			double netEossDiscountPaid = 0;
			double netSP = 0;
			double netRate = 0;
			double grossProfit = 0;
			double netProductCost = 0;
			double netPrSale = 0;
			double netActualSale = 0;
			if (partnerBusiness.isPoOrder()) {
				if (partnerBusiness.getShippedDate() != null) {
					netSP = partnerBusiness.getNetSP();
					netProductCost = partnerBusiness.getProductPrice();
					grossProfit = partnerBusiness.getGrossProfit();
					netRate = partnerBusiness.getNetRate();
					netActualSale = partnerBusiness.getNetRate();
					netPrSale = partnerBusiness.getNetPr();
					System.out.println(partnerBusiness.getChannelOrderID()
							+ ":" + netSP + ":" + netProductCost + ":"
							+ netRate);
				}
				if (partnerBusiness.getReturnDate() != null) {
					netSP -= partnerBusiness.getReturnSP();
					netProductCost -= partnerBusiness.getProductPrice();
					grossProfit -= partnerBusiness.getGrossProfit();
					netRate -= partnerBusiness.getTotalReturnCharges();
					netActualSale -= partnerBusiness.getTotalReturnCharges();
					netPrSale -= partnerBusiness.getNetReturnPr();
					System.out.println(partnerBusiness.getChannelOrderID()
							+ ":" + netSP + ":" + netProductCost + ":"
							+ netRate);
				}
			} else {
				netSP = partnerBusiness.getNetSP();
				netProductCost = partnerBusiness.getProductPrice();
				grossProfit = partnerBusiness.getGrossProfit();
				if (partnerBusiness.getShippedDate() != null) {
					netActualSale = partnerBusiness.getNetRate();
					netRate = partnerBusiness.getNetRate();
				}
				if (partnerBusiness.getReturnDate() != null) {
					netActualSale -= partnerBusiness.getTotalReturnCharges();
					netRate -= partnerBusiness.getTotalReturnCharges();
				}
				netPrSale = partnerBusiness.getNetPr()
						- partnerBusiness.getNetPr()
						* (partnerBusiness.getReturnQuantity() / ((partnerBusiness
								.getGrossSaleQuantity() == 0) ? 1
								: partnerBusiness.getGrossSaleQuantity()));
			}
			if (partnerBusiness.getShippedDate() != null) {
				netEossDiscountPaid = partnerBusiness.getNetEossValue();
			}
			if (partnerBusiness.getReturnDate() != null) {
				netEossDiscountPaid -= partnerBusiness.getNetEossValue();
			}
			if (partnerBusinessGraph == null) {
				partnerBusinessGraph = new BusinessDetails();
			} else {
				netPartnerCommissionPaid += partnerBusinessGraph
						.getNetPartnerCommissionPaid();
				netSP += partnerBusinessGraph.getNetSP();
				netSaleQty += partnerBusinessGraph.getNetSaleQty();
				netTDSToBeDeposited += partnerBusinessGraph
						.getNetTDSToBeDeposited();
				netTDS2 += partnerBusinessGraph.getNetTDS2();
				netTDS10 += partnerBusinessGraph.getNetTDS10();
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
			partnerBusinessGraph.setNetSP(netSP);
			partnerBusinessGraph.setNetTDSToBeDeposited(netTDSToBeDeposited);
			partnerBusinessGraph.setNetPaymentResult(netPaymentResult);
			partnerBusinessGraph.setPaymentDifference(paymentDifference);
			partnerBusinessGraph.setNetTDS2(netTDS2);
			partnerBusinessGraph.setNetTDS10(netTDS10);
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
	 * Transform to List of PartnerBusinessGraph objects
	 * 
	 * @param partnerBusinessList
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<CommissionAnalysis> transformCommAGraph(
			List<PartnerReportDetails> partnerBusinessList, String criteria) {
		Map<String, CommissionAnalysis> commAGraphMap = new HashMap<String, CommissionAnalysis>();
		for (PartnerReportDetails partnerBusiness : partnerBusinessList) {
			String key = "";
			switch (criteria) {
			case "partner":
				key = partnerBusiness.getPcName();
				break;
			case "category":
				key = partnerBusiness.getParentCategory();
				if (StringUtils.isEmpty(key))
					key = "B2B";
				break;
			default:
				break;
			}
			CommissionAnalysis commAGraph = commAGraphMap.get(key);
			double fixedFee = partnerBusiness.getFixedfee();
			double pcc = partnerBusiness.getPccAmount();
			double serviceTax = partnerBusiness.getServiceTax();
			double shippingCharges = partnerBusiness.getShippingCharges();
			double taxSP = 0;
			double sellingFee = 0;
			if (partnerBusiness.isPoOrder()) {
				if (partnerBusiness.getShippedDate() != null) {
					taxSP = partnerBusiness.getTaxSP();
					sellingFee = partnerBusiness.getGrossPartnerCommission();
				}
				if (partnerBusiness.getReturnDate() != null) {
					taxSP -= partnerBusiness.getTaxSP();
					sellingFee -= partnerBusiness.getGrossPartnerCommission();
				}
			} else {
				taxSP = partnerBusiness.getTaxSP();
				sellingFee = partnerBusiness.getGrossPartnerCommission();
			}
			double additionalCharges = partnerBusiness
					.getAdditionalReturnCharges();
			if (commAGraph == null) {
				commAGraph = new CommissionAnalysis();
			} else {
				fixedFee += commAGraph.getFixedFee();
				pcc += commAGraph.getPcc();
				sellingFee += commAGraph.getSellingFee();
				serviceTax += commAGraph.getServiceTax();
				shippingCharges += commAGraph.getShippingCharges();
				taxSP += commAGraph.getTaxSP();
				additionalCharges += commAGraph.getAdditionalCharges();
			}
			commAGraph.setKey(key);
			commAGraph.setFixedFee(fixedFee);
			commAGraph.setPcc(pcc);
			commAGraph.setSellingFee(sellingFee);
			commAGraph.setServiceTax(serviceTax);
			commAGraph.setShippingCharges(shippingCharges);
			commAGraph.setTaxSP(taxSP);
			commAGraph.setAdditionalCharges(additionalCharges);
			commAGraphMap.put(key, commAGraph);
		}

		List<CommissionAnalysis> commAGraphList = new ArrayList<CommissionAnalysis>();
		Iterator entries = commAGraphMap.entrySet().iterator();
		while (entries.hasNext()) {
			Entry<String, CommissionAnalysis> thisEntry = (Entry<String, CommissionAnalysis>) entries
					.next();
			CommissionAnalysis value = thisEntry.getValue();
			commAGraphList.add(value);
		}

		return commAGraphList;
	}

	/**
	 * Transform to List of PartnerCommissionPaidGraph objects
	 * 
	 * @param partnerCommissionList
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<CommissionDetails> transformCommissionPaid(
			List<PartnerReportDetails> partnerCommissionList, Date startDate,
			Date endDate, String criteria) {
		Map<String, CommissionDetails> partnerCommissionGraphMap = new HashMap<String, CommissionDetails>();
		for (PartnerReportDetails partnerBusiness : partnerCommissionList) {
			String key = "";
			switch (criteria) {
			case "partner":
				key = partnerBusiness.getPcName();
				break;
			case "category":
				key = partnerBusiness.getParentCategory();
				if (StringUtils.isEmpty(key))
					key = "B2B";
				break;
			default:
				break;
			}
			CommissionDetails partnerCommissionGraph = partnerCommissionGraphMap
					.get(key);
			Date shippedDate = partnerBusiness.getShippedDate();
			double grossPartnerCommissionPaid = 0;
			if (shippedDate != null && shippedDate.after(startDate)
					&& shippedDate.before(endDate)) {
				grossPartnerCommissionPaid = partnerBusiness
						.getGrossCommissionQty();
			}
			Date returnDate = partnerBusiness.getReturnDate();
			double additionalReturnCharges = 0;
			double netReturnCommission = 0;
			if (returnDate != null && returnDate.after(startDate)
					&& returnDate.before(endDate)) {
				additionalReturnCharges = partnerBusiness
						.getAdditionalReturnCharges();
				netReturnCommission = partnerBusiness.getReturnCommision();
			}
			int netSaleQty = 0;
			if (partnerBusiness.isPoOrder()) {
				if (shippedDate != null && shippedDate.after(startDate)
						&& shippedDate.before(endDate))
					netSaleQty = partnerBusiness.getGrossSaleQuantity();
				if (returnDate != null && returnDate.after(startDate)
						&& returnDate.before(endDate))
					netSaleQty -= partnerBusiness.getReturnQuantity();

			} else {
				netSaleQty = partnerBusiness.getNetSaleQuantity();
			}

			double netTDSToBeDeposited = partnerBusiness.getTdsToBeDeposited();
			double netSrCommission = netReturnCommission
					- additionalReturnCharges;
			double netChannelCommissionToBePaid = grossPartnerCommissionPaid
					- netReturnCommission + additionalReturnCharges;
			double netPartnerCommissionPaid = netChannelCommissionToBePaid;
			if (partnerCommissionGraph == null) {
				partnerCommissionGraph = new CommissionDetails();
			} else {
				netPartnerCommissionPaid += partnerCommissionGraph
						.getNetPartnerCommissionPaid();
				netSaleQty += partnerCommissionGraph.getNetSaleQty();
				netTDSToBeDeposited += partnerCommissionGraph
						.getNetTDSToBeDeposited();
				grossPartnerCommissionPaid += partnerCommissionGraph
						.getGrossPartnerCommissionPaid();
				additionalReturnCharges += partnerCommissionGraph
						.getAdditionalReturnCharges();
				netReturnCommission += partnerCommissionGraph
						.getNetReturnCommission();
				netSrCommission += partnerCommissionGraph.getNetSrCommisison();
				netChannelCommissionToBePaid += partnerCommissionGraph
						.getNetChannelCommissionToBePaid();
			}
			partnerCommissionGraph.setPartner(key);
			partnerCommissionGraph.setCategoryName(key);
			partnerCommissionGraph
					.setNetPartnerCommissionPaid(netPartnerCommissionPaid);
			partnerCommissionGraph.setNetSaleQty(netSaleQty);
			partnerCommissionGraph.setNetTDSToBeDeposited(netTDSToBeDeposited);
			partnerCommissionGraph
					.setGrossPartnerCommissionPaid(grossPartnerCommissionPaid);
			partnerCommissionGraph
					.setAdditionalReturnCharges(additionalReturnCharges);
			partnerCommissionGraph.setNetReturnCommission(netReturnCommission);
			partnerCommissionGraph.setNetSrCommisison(netSrCommission);
			partnerCommissionGraph
					.setNetChannelCommissionToBePaid(netChannelCommissionToBePaid);
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
		for (ChannelReportDetails currChannelReport : channelReportDetailsList) {
			String key = "";
			switch (criteria) {
			case "partner":
				key = currChannelReport.getPartner();
				break;
			case "category":
				key = currChannelReport.getParentCategory();
				if (StringUtils.isEmpty(key))
					key = "B2B";
				break;
			default:
				break;
			}
			ChannelReportDetails channelReport = categoryReportMap.get(key);
			double gpVsProductCost = currChannelReport.getGpVsProductCost();
			double grossNrAmount = currChannelReport.getGrossNrAmount();
			double grossProfit = currChannelReport.getGrossProfit();
			double grossQty = currChannelReport.getGrossQty();
			double grossSpAmount = currChannelReport.getGrossSpAmount();
			double netNrAmount = currChannelReport.getNetNrAmount();
			double netPaymentResult = currChannelReport.getNetPaymentResult();
			double paymentDifference = currChannelReport.getPaymentDifference();
			double netQty = currChannelReport.getNetQty();
			double netReturnCharges = currChannelReport.getNetReturnCharges();
			double netSpAmount = currChannelReport.getNetSpAmount();
			double netTaxLiability = currChannelReport.getNetTaxLiability();
			double netToBeReceived = currChannelReport.getNetToBeReceived();
			double netPr = currChannelReport.getNetPr();
			double saleRetNrAmount = currChannelReport.getSaleRetNrAmount();
			double saleRetQty = currChannelReport.getSaleRetQty();
			double saleRetSpAmount = currChannelReport.getSaleRetSpAmount();
			double saleRetVsGrossSale = currChannelReport
					.getSaleRetVsGrossSale();
			double netProductCost = currChannelReport.getNetProductCost();

			double netEOSSValue = currChannelReport.getNetEOSSValue();

			double grossTaxableSale = currChannelReport.getGrossTaxableSale();
			double returnTaxableSale = currChannelReport.getReturnTaxableSale();
			double netTaxableSale = currChannelReport.getNetTaxableSale();

			double grossActualSale = currChannelReport.getGrossActualSale();
			double returnActualSale = currChannelReport.getReturnActualSale();
			double netActualSale = currChannelReport.getNetActualSale();

			double grossTaxfreeSale = currChannelReport.getGrossTaxfreeSale();
			double returnTaxfreeSale = currChannelReport.getReturnTaxfreeSale();
			double netTaxfreeSale = currChannelReport.getNetTaxfreeSale();

			if (channelReport == null) {
				channelReport = new ChannelReportDetails();
			} else {
				gpVsProductCost += channelReport.getGpVsProductCost();
				grossNrAmount += channelReport.getGrossNrAmount();
				grossProfit += channelReport.getGrossProfit();
				grossQty += channelReport.getGrossQty();
				grossSpAmount += channelReport.getGrossSpAmount();
				netNrAmount += channelReport.getNetNrAmount();
				netPaymentResult += channelReport.getNetPaymentResult();
				paymentDifference += channelReport.getPaymentDifference();
				netQty += channelReport.getNetQty();
				netReturnCharges += channelReport.getNetReturnCharges();
				netSpAmount += channelReport.getNetSpAmount();
				netTaxLiability += channelReport.getNetTaxLiability();
				netToBeReceived += channelReport.getNetToBeReceived();
				netPr += channelReport.getNetPr();
				saleRetNrAmount += channelReport.getSaleRetNrAmount();
				saleRetQty += channelReport.getSaleRetQty();
				saleRetSpAmount += channelReport.getSaleRetSpAmount();
				saleRetVsGrossSale += channelReport.getSaleRetVsGrossSale();
				netProductCost += channelReport.getNetProductCost();

				netEOSSValue += channelReport.getNetEOSSValue();

				grossTaxableSale += channelReport.getGrossTaxableSale();
				returnTaxableSale += channelReport.getReturnTaxableSale();
				netTaxableSale += channelReport.getNetTaxableSale();

				grossActualSale += channelReport.getGrossActualSale();
				returnActualSale += channelReport.getReturnActualSale();
				netActualSale += channelReport.getNetActualSale();

				grossTaxfreeSale += channelReport.getGrossTaxfreeSale();
				returnTaxfreeSale += channelReport.getReturnTaxfreeSale();
				netTaxfreeSale += channelReport.getNetTaxfreeSale();
			}
			channelReport.setGpVsProductCost(gpVsProductCost);
			channelReport.setGrossNrAmount(grossNrAmount);
			channelReport.setGrossProfit(grossProfit);
			channelReport.setGrossQty(grossQty);
			channelReport.setGrossSpAmount(grossSpAmount);
			channelReport.setNetNrAmount(netNrAmount);
			channelReport.setNetPaymentResult(netPaymentResult);
			channelReport.setPaymentDifference(paymentDifference);
			channelReport.setNetQty(netQty);
			channelReport.setNetReturnCharges(netReturnCharges);
			channelReport.setNetSpAmount(netSpAmount);
			channelReport.setNetTaxLiability(netTaxLiability);
			channelReport.setNetToBeReceived(netToBeReceived);
			channelReport.setNetPr(netPr);
			channelReport.setSaleRetNrAmount(saleRetNrAmount);
			channelReport.setSaleRetQty(saleRetQty);
			channelReport.setSaleRetSpAmount(saleRetSpAmount);
			channelReport.setSaleRetVsGrossSale(saleRetVsGrossSale);
			channelReport.setNetProductCost(netProductCost);

			channelReport.setNetEOSSValue(netEOSSValue);

			channelReport.setGrossTaxableSale(grossTaxableSale);
			channelReport.setReturnTaxableSale(returnTaxableSale);
			channelReport.setNetTaxableSale(netTaxableSale);

			channelReport.setGrossActualSale(grossActualSale);
			channelReport.setReturnActualSale(returnActualSale);
			channelReport.setNetActualSale(netActualSale);

			channelReport.setGrossTaxfreeSale(grossTaxfreeSale);
			channelReport.setReturnTaxfreeSale(returnTaxfreeSale);
			channelReport.setNetTaxfreeSale(netTaxfreeSale);

			channelReport.setRetActualPercent(returnActualSale * 100
					/ grossActualSale);

			channelReport.setCategory(key);
			channelReport.setPartner(key);
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<ChannelReportDetails> transformChannelReportST(
			List<ChannelReportDetails> channelReportDetailsList, String criteria) {
		Map<String, ChannelReportDetails> categoryReportMap = new HashMap<String, ChannelReportDetails>();
		for (ChannelReportDetails currChannelReport : channelReportDetailsList) {
			String key = "";
			switch (criteria) {
			case "partner":
				key = currChannelReport.getPartner();
				break;
			case "category":
				key = currChannelReport.getParentCategory();
				if (StringUtils.isEmpty(key))
					key = "B2B";
				break;
			case "category1":
				key = currChannelReport.getCategory();
				if (StringUtils.isEmpty(key))
					key = "B2B";
				break;
			default:
				break;
			}
			String taxCategory = currChannelReport.getTaxCategory();			
			if (StringUtils.isBlank(taxCategory))
				taxCategory = "Mix";
			ChannelReportDetails channelReport = categoryReportMap.get(key
					+ taxCategory);
			double gpVsProductCost = currChannelReport.getGpVsProductCost();
			double grossNrAmount = currChannelReport.getGrossNrAmount();
			double grossProfit = currChannelReport.getGrossProfit();
			double grossQty = currChannelReport.getGrossQty();
			double grossSpAmount = currChannelReport.getGrossSpAmount();
			double netNrAmount = currChannelReport.getNetNrAmount();
			double netPaymentResult = currChannelReport.getNetPaymentResult();
			double netQty = currChannelReport.getNetQty();
			double netReturnCharges = currChannelReport.getNetReturnCharges();
			double netSpAmount = currChannelReport.getNetSpAmount();
			double netTaxLiability = currChannelReport.getNetTaxLiability();
			double netToBeReceived = currChannelReport.getNetToBeReceived();
			double netPr = currChannelReport.getNetPr();
			double saleRetNrAmount = currChannelReport.getSaleRetNrAmount();
			double saleRetQty = currChannelReport.getSaleRetQty();
			double saleRetSpAmount = currChannelReport.getSaleRetSpAmount();
			float taxCatPercent = currChannelReport.getTaxPercent();

			if (channelReport == null) {
				channelReport = new ChannelReportDetails();
			} else {
				gpVsProductCost += channelReport.getGpVsProductCost();
				grossNrAmount += channelReport.getGrossNrAmount();
				grossProfit += channelReport.getGrossProfit();
				grossQty += channelReport.getGrossQty();
				grossSpAmount += channelReport.getGrossSpAmount();
				netNrAmount += channelReport.getNetNrAmount();
				netPaymentResult += channelReport.getNetPaymentResult();
				netQty += channelReport.getNetQty();
				netReturnCharges += channelReport.getNetReturnCharges();
				netSpAmount += channelReport.getNetSpAmount();
				netTaxLiability += channelReport.getNetTaxLiability();
				netToBeReceived += channelReport.getNetToBeReceived();
				netPr += channelReport.getNetPr();
				saleRetNrAmount += channelReport.getSaleRetNrAmount();
				saleRetQty += channelReport.getSaleRetQty();
				saleRetSpAmount += channelReport.getSaleRetSpAmount();
			}
			channelReport.setGpVsProductCost(gpVsProductCost);
			channelReport.setGrossNrAmount(grossNrAmount);
			channelReport.setGrossProfit(grossProfit);
			channelReport.setGrossQty(grossQty);
			channelReport.setGrossSpAmount(grossSpAmount);
			channelReport.setNetNrAmount(netNrAmount);
			channelReport.setNetPaymentResult(netPaymentResult);
			channelReport.setNetQty(netQty);
			channelReport.setNetReturnCharges(netReturnCharges);
			channelReport.setNetSpAmount(netSpAmount);
			channelReport.setNetTaxLiability(netTaxLiability);
			channelReport.setNetToBeReceived(netToBeReceived);
			channelReport.setNetPr(netPr);
			channelReport.setSaleRetNrAmount(saleRetNrAmount);
			channelReport.setSaleRetQty(saleRetQty);
			channelReport.setSaleRetSpAmount(saleRetSpAmount);
			channelReport.setCategory(key);
			channelReport.setPartner(key);
			channelReport.setTaxCategory(taxCategory);
			channelReport.setTaxPercent(taxCatPercent);
			channelReport.setRetActualPercent(channelReport
					.getSaleRetNrAmount()
					* 100
					/ channelReport.getGrossNrAmount());
			categoryReportMap.put(key + taxCategory, channelReport);
		}

		List<ChannelReportDetails> categoryReportList = new ArrayList<ChannelReportDetails>();
		Iterator entries = categoryReportMap.entrySet().iterator();
		while (entries.hasNext()) {
			Entry<String, ChannelReportDetails> thisEntry = (Entry<String, ChannelReportDetails>) entries
					.next();
			ChannelReportDetails value = thisEntry.getValue();
			double saleRetVsGrossSale = 0;
			if (value.getGrossQty() != 0)
				saleRetVsGrossSale = value.getSaleRetQty()
						/ value.getGrossQty() * 100;
			value.setSaleRetVsGrossSale(saleRetVsGrossSale);
			categoryReportList.add(value);
		}

		return categoryReportList;
	}

	/*
	 * @SuppressWarnings({ "rawtypes", "unchecked" }) public static
	 * List<PaymentReceivedDetails> transformPayReceivedReport(
	 * List<PartnerReportDetails> channelReportDetailsList, String criteria) {
	 * List<PaymentReceivedDetails> reportList = new
	 * ArrayList<PaymentReceivedDetails>(); for(PartnerReportDetails
	 * currChannelReport: channelReportDetailsList){ PaymentReceivedDetails
	 * channelReport = new PaymentReceivedDetails();
	 * channelReport.setPaymentReceived(currChannelReport.getDateofPayment());
	 * channelReport
	 * .setUploadedDate(currChannelReport.getPaymentUploadedDate());
	 * channelReport.setPaymentType(currChannelReport.getPaymentType());
	 * channelReport.setPartner(currChannelReport.getPcName()); double
	 * negativeAmount = currChannelReport.getNegativeAmount(); double
	 * positiveAmount = currChannelReport.getPositiveAmount();
	 * channelReport.setNegativeAmount(negativeAmount);
	 * channelReport.setPositiveAmount(positiveAmount); double netAmountReceived
	 * = positiveAmount + negativeAmount;
	 * channelReport.setNetAmountReceived(netAmountReceived);
	 * reportList.add(channelReport); }
	 * 
	 * return reportList; }
	 *//**
	 * Merge BusinessGraph objects after 5th Iteration
	 * 
	 * @param businessGraphList
	 * @param string
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getBusinessSortedList(List businessGraphList,
			String string) {
		int maxLength = 5;
		if (businessGraphList.size() <= maxLength)
			return businessGraphList;
		int index = 1;
		List newBusinessGraphList = new ArrayList();
		BusinessDetails consolidated = new BusinessDetails();
		consolidated.setCategoryName("Others");
		consolidated.setPartner("Others");
		for (Object businessGraphObj : businessGraphList) {
			if (index++ >= 5) {
				BusinessDetails businessGraph = (BusinessDetails) businessGraphObj;
				switch (string) {
				case "NetSP":
					double netSP = businessGraph.getNetSP();
					double netSPTotal = consolidated.getNetSP();
					consolidated.setNetSP(netSP + netSPTotal);
					double netPartnerCommissionPaid = businessGraph
							.getNetPartnerCommissionPaid();
					double netPartnerCommissionPaidTotal = consolidated
							.getNetPartnerCommissionPaid();
					consolidated
							.setNetPartnerCommissionPaid(netPartnerCommissionPaid
									+ netPartnerCommissionPaidTotal);
					double netNetRate = businessGraph.getNetNetRate();
					double netNetRateTotal = consolidated.getNetNetRate();
					consolidated.setNetNetRate(netNetRate + netNetRateTotal);
					double netTaxToBePaid = businessGraph.getNetTaxToBePaid();
					double netTaxToBePaidTotal = consolidated
							.getNetTaxToBePaid();
					consolidated.setNetTaxToBePaid(netTaxToBePaid
							+ netTaxToBePaidTotal);
					double netPrSale = businessGraph.getNetPrSale();
					double netPrSaleTotal = consolidated.getNetPrSale();
					consolidated.setNetPrSale(netPrSale + netPrSaleTotal);
					double netTDSToBeDeposited = businessGraph
							.getNetTDSToBeDeposited();
					double netTDSToBeDepositedTotal = consolidated
							.getNetTDSToBeDeposited();
					consolidated.setNetTDSToBeDeposited(netTDSToBeDeposited
							+ netTDSToBeDepositedTotal);
					double netProductCost = businessGraph.getNetProductCost();
					double netProductCostTotal = consolidated
							.getNetProductCost();
					consolidated.setNetProductCost(netProductCost
							+ netProductCostTotal);
					double grossProfit = businessGraph.getGrossProfit();
					double grossProfitTotal = consolidated.getGrossProfit();
					consolidated.setGrossProfit(grossProfit + grossProfitTotal);
					break;
				case "NetCommission":
					double netCommission = businessGraph
							.getNetPartnerCommissionPaid();
					double netCommissionTotal = consolidated
							.getNetPartnerCommissionPaid();
					consolidated.setNetPartnerCommissionPaid(netCommission
							+ netCommissionTotal);
					break;
				case "NetTax":
					double netTaxToBePaid2 = businessGraph.getNetTaxToBePaid();
					double netTaxToBePaidTotal2 = consolidated
							.getNetTaxToBePaid();
					consolidated.setNetTaxToBePaid(netTaxToBePaid2
							+ netTaxToBePaidTotal2);
					break;
				case "NetTDS":
					double netTDSToBeDeposited2 = businessGraph
							.getNetTDSToBeDeposited();
					double netTDSToBeDepositedTotal2 = consolidated
							.getNetTDSToBeDeposited();
					consolidated.setNetTDSToBeDeposited(netTDSToBeDeposited2
							+ netTDSToBeDepositedTotal2);
					break;
				case "EOSS":
					double netEoss = businessGraph.getNetEossDiscountPaid();
					double netEossTotal = consolidated.getNetEossDiscountPaid();
					consolidated.setNetEossDiscountPaid(netEoss + netEossTotal);
					break;
				case "NetTaxable":
					double netTaxableSale = businessGraph.getNetSP();
					double netActualSale = businessGraph.getNetActualSale();
					double netPrSale2 = businessGraph.getNetPrSale();
					double netTaxableSaleTotal = consolidated.getNetSP();
					consolidated.setNetSP(netTaxableSale + netTaxableSaleTotal);
					double netActualSaleTotal = consolidated.getNetActualSale();
					consolidated.setNetActualSale(netActualSale
							+ netActualSaleTotal);
					double netPrSale2Total = consolidated.getNetPrSale();
					consolidated.setNetPrSale(netPrSale2 + netPrSale2Total);
					break;
				default:
					break;
				}
			} else {
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
	public static List getCommissionSortedList(List commissionGraphList,
			String string) {
		int maxLength = 5;
		if (commissionGraphList.size() <= maxLength)
			return commissionGraphList;
		int index = 1;
		List newCommissionGraphList = new ArrayList();
		CommissionDetails consolidated = new CommissionDetails();
		consolidated.setCategoryName("Others");
		consolidated.setPartner("Others");
		for (Object commissionGraphObj : commissionGraphList) {
			if (index++ >= 5) {
				CommissionDetails commissionGraph = (CommissionDetails) commissionGraphObj;
				switch (string) {
				case "GrossComm":
					double grossCommission = commissionGraph
							.getGrossPartnerCommissionPaid();
					double returnCommission = commissionGraph
							.getNetReturnCommission();
					double addRetCharges = commissionGraph
							.getAdditionalReturnCharges();
					double grossCommissionTotal = consolidated
							.getGrossPartnerCommissionPaid();
					consolidated.setGrossPartnerCommissionPaid(grossCommission
							+ grossCommissionTotal);
					double returnCommissionTotal = consolidated
							.getNetReturnCommission();
					consolidated.setNetReturnCommission(returnCommission
							+ returnCommissionTotal);
					double addRetChargesTotal = consolidated
							.getAdditionalReturnCharges();
					consolidated.setAdditionalReturnCharges(addRetCharges
							+ addRetChargesTotal);
					break;
				case "NetChann":
					double netChannComm = commissionGraph
							.getNetChannelCommissionToBePaid();
					double netChannCommTotal = consolidated
							.getNetChannelCommissionToBePaid();
					consolidated.setNetChannelCommissionToBePaid(netChannComm
							+ netChannCommTotal);
					break;
				default:
					break;
				}
			} else {
				newCommissionGraphList.add(commissionGraphObj);
			}
		}
		newCommissionGraphList.add(consolidated);
		return newCommissionGraphList;
	}

	@SuppressWarnings({ "rawtypes" })
	public static List getChannelNPRSortedList(List<ChannelNPR> channelGraphList) {
		int maxLength = 5;
		if (channelGraphList.size() <= maxLength)
			return channelGraphList;
		List<ChannelNPR> newChannelReportList = new ArrayList<>();
		int index = 1;
		boolean initialize = false;
		ChannelNPR consolidated = new ChannelNPR();
		for (ChannelNPR channelGraph : channelGraphList) {
			if (index++ >= 5) {
				if (!initialize) {
					initialize = true;
					consolidated.setCategory("Others");
					consolidated.setPartner("Others");
					consolidated.setCodNPR(channelGraph.getCodNPR());
					consolidated.setPrepaidNPR(channelGraph.getPrepaidNPR());
					consolidated.setB2bNPR(channelGraph.getB2bNPR());
					consolidated.setNetNPR(channelGraph.getNetNPR());
				} else {
					consolidated.setCodNPR(consolidated.getCodNPR()
							+ channelGraph.getCodNPR());
					consolidated.setPrepaidNPR(consolidated.getPrepaidNPR()
							+ channelGraph.getPrepaidNPR());
					consolidated.setB2bNPR(consolidated.getB2bNPR()
							+ channelGraph.getB2bNPR());
					consolidated.setNetNPR(consolidated.getNetNPR()
							+ channelGraph.getNetNPR());
				}
			} else {
				newChannelReportList.add(channelGraph);
			}
		}
		newChannelReportList.add(consolidated);
		return newChannelReportList;
	}

	@SuppressWarnings({ "rawtypes" })
	public static List getCommASortedList(
			List<CommissionAnalysis> channelGraphList) {
		int maxLength = 5;
		if (channelGraphList.size() <= maxLength)
			return channelGraphList;
		List<CommissionAnalysis> newChannelReportList = new ArrayList<>();
		int index = 1;
		boolean initialize = false;
		CommissionAnalysis consolidated = new CommissionAnalysis();
		for (CommissionAnalysis channelGraph : channelGraphList) {
			if (index++ >= 5) {
				if (!initialize) {
					initialize = true;
					consolidated.setKey("Others");
					consolidated.setSellingFee(channelGraph.getSellingFee());
					consolidated.setPcc(channelGraph.getPcc());
					consolidated.setFixedFee(channelGraph.getFixedFee());
					consolidated.setShippingCharges(channelGraph
							.getShippingCharges());
					consolidated.setServiceTax(channelGraph.getServiceTax());
					consolidated.setTaxSP(channelGraph.getTaxSP());
					consolidated.setAdditionalCharges(channelGraph
							.getAdditionalCharges());
				} else {
					consolidated.setSellingFee(consolidated.getSellingFee()
							+ channelGraph.getSellingFee());
					consolidated.setPcc(consolidated.getPcc()
							+ channelGraph.getPcc());
					consolidated.setFixedFee(consolidated.getFixedFee()
							+ channelGraph.getFixedFee());
					consolidated.setShippingCharges(consolidated
							.getShippingCharges()
							+ channelGraph.getShippingCharges());
					consolidated.setServiceTax(consolidated.getServiceTax()
							+ channelGraph.getServiceTax());
					consolidated.setTaxSP(consolidated.getTaxSP()
							+ channelGraph.getTaxSP());
					consolidated.setAdditionalCharges(consolidated
							.getAdditionalCharges()
							+ channelGraph.getAdditionalCharges());
				}
			} else {
				newChannelReportList.add(channelGraph);
			}
		}
		newChannelReportList.add(consolidated);
		return newChannelReportList;
	}

	@SuppressWarnings({ "rawtypes" })
	public static List getChannelCatNPRSortedList(
			List<ChannelCatNPR> channelGraphList) {
		int maxLength = 5;
		if (channelGraphList.size() <= maxLength)
			return channelGraphList;
		List<ChannelCatNPR> newChannelReportList = new ArrayList<>();
		int index = 1;
		boolean initialize = false;
		ChannelCatNPR consolidated = new ChannelCatNPR();
		for (ChannelCatNPR channelGraph : channelGraphList) {
			if (index++ >= 5) {
				if (!initialize) {
					initialize = true;
					consolidated.setPartner("Others");
					consolidated.setTotalNPR(channelGraph.getTotalNPR());
					consolidated.setNetNPR(channelGraph.getNetNPR());
				} else {
					List<Double> existingNPRList = consolidated.getNetNPR();
					List<Double> newNPRList = channelGraph.getNetNPR();
					for (int i = 0; i < existingNPRList.size(); i++) {
						Double existingNPR = existingNPRList.get(i);
						Double newNPR = newNPRList.get(i);
						existingNPRList.set(i, existingNPR + newNPR);
					}
					consolidated.setNetNPR(existingNPRList);
					consolidated.setTotalNPR(consolidated.getTotalNPR()
							+ channelGraph.getTotalNPR());
				}
			} else {
				newChannelReportList.add(channelGraph);
			}
		}
		newChannelReportList.add(consolidated);
		return newChannelReportList;
	}

	@SuppressWarnings({ "rawtypes" })
	public static List getChannelNetNrSortedList(
			List<ChannelNR> channelGraphList) {
		int maxLength = 5;
		if (channelGraphList.size() <= maxLength)
			return channelGraphList;
		List<ChannelNR> newChannelReportList = new ArrayList<>();
		int index = 1;
		boolean initialize = false;
		ChannelNR consolidated = new ChannelNR();
		for (ChannelNR channelGraph : channelGraphList) {
			if (index++ >= 5) {
				if (!initialize) {
					initialize = true;
					consolidated.setKey("Others");
					consolidated.setTotalNR(channelGraph.getTotalNR());
					consolidated
							.setActionableNR(channelGraph.getActionableNR());
					consolidated.setSettledNR(channelGraph.getSettledNR());
					consolidated.setInProcessNR(channelGraph.getInProcessNR());
				} else {
					consolidated.setTotalNR(consolidated.getTotalNR()
							+ channelGraph.getTotalNR());
					consolidated.setActionableNR(consolidated.getActionableNR()
							+ channelGraph.getActionableNR());
					consolidated.setSettledNR(consolidated.getSettledNR()
							+ channelGraph.getSettledNR());
					consolidated.setInProcessNR(consolidated.getInProcessNR()
							+ channelGraph.getInProcessNR());
				}
			} else {
				newChannelReportList.add(channelGraph);
			}
		}
		newChannelReportList.add(consolidated);
		return newChannelReportList;
	}

	@SuppressWarnings({ "rawtypes" })
	public static List getChannelNetQtySortedList(
			List<ChannelNetQty> channelGraphList) {
		int maxLength = 5;
		if (channelGraphList.size() <= maxLength)
			return channelGraphList;
		List<ChannelNetQty> newChannelReportList = new ArrayList<>();
		int index = 1;
		boolean initialize = false;
		ChannelNetQty consolidated = new ChannelNetQty();
		for (ChannelNetQty channelGraph : channelGraphList) {
			if (index++ >= 5) {
				if (!initialize) {
					initialize = true;
					consolidated.setKey("Others");
					consolidated.setTotalQty(channelGraph.getTotalQty());
					consolidated.setActionableQty(channelGraph
							.getActionableQty());
					consolidated.setSettledQty(channelGraph.getSettledQty());
					consolidated
							.setInProcessQty(channelGraph.getInProcessQty());
				} else {
					consolidated.setTotalQty(consolidated.getTotalQty()
							+ channelGraph.getTotalQty());
					consolidated.setActionableQty(consolidated
							.getActionableQty()
							+ channelGraph.getActionableQty());
					consolidated.setSettledQty(consolidated.getSettledQty()
							+ channelGraph.getSettledQty());
					consolidated.setInProcessQty(consolidated.getInProcessQty()
							+ channelGraph.getInProcessQty());
				}
			} else {
				newChannelReportList.add(channelGraph);
			}
		}
		newChannelReportList.add(consolidated);
		return newChannelReportList;
	}

	@SuppressWarnings({ "rawtypes" })
	public static List getChannelNetGPSortedList(
			List<ChannelGP> channelGraphList) {
		int maxLength = 5;
		if (channelGraphList.size() <= maxLength)
			return channelGraphList;
		List<ChannelGP> newChannelReportList = new ArrayList<>();
		int index = 1;
		boolean initialize = false;
		ChannelGP consolidated = new ChannelGP();
		for (ChannelGP channelGraph : channelGraphList) {
			if (index++ >= 5) {
				if (!initialize) {
					initialize = true;
					consolidated.setKey("Others");
					consolidated.setTotalGP(channelGraph.getTotalGP());
					consolidated
							.setActionableGP(channelGraph.getActionableGP());
					consolidated.setSettledGP(channelGraph.getSettledGP());
					consolidated.setInProcessGP(channelGraph.getInProcessGP());
				} else {
					consolidated.setTotalGP(consolidated.getTotalGP()
							+ channelGraph.getTotalGP());
					consolidated.setActionableGP(consolidated.getActionableGP()
							+ channelGraph.getActionableGP());
					consolidated.setSettledGP(consolidated.getSettledGP()
							+ channelGraph.getSettledGP());
					consolidated.setInProcessGP(consolidated.getInProcessGP()
							+ channelGraph.getInProcessGP());
				}
			} else {
				newChannelReportList.add(channelGraph);
			}
		}
		newChannelReportList.add(consolidated);
		return newChannelReportList;
	}

	@SuppressWarnings({ "rawtypes" })
	public static List getChannelSortedList(
			List<ChannelReportDetails> channelGraphList, String criteria) {
		int maxLength = 6;
		if (channelGraphList.size() <= maxLength)
			return channelGraphList;
		List<ChannelReportDetails> newChannelReportList = new ArrayList<>();
		int index = 1;
		boolean initialize = false;
		ChannelReportDetails consolidated = new ChannelReportDetails();
		for (ChannelReportDetails channelGraph : channelGraphList) {
			if (index++ >= maxLength) {
				if (!initialize) {
					initialize = true;
					consolidated.setGrossProfit(channelGraph.getGrossProfit());
					consolidated.setGpVsProductCost(channelGraph
							.getGpVsProductCost());
					consolidated.setNetPr(channelGraph.getNetPr());
					consolidated.setNetProductCost(channelGraph
							.getNetProductCost());
					consolidated.setNetNrAmount(channelGraph.getNetNrAmount());
					consolidated.setGrossSpAmount(channelGraph
							.getGrossSpAmount());
					consolidated.setSaleRetSpAmount(channelGraph
							.getSaleRetSpAmount());
					consolidated.setNetSpAmount(channelGraph.getNetSpAmount());
					consolidated.setNetAr(channelGraph.getNetAr());
					consolidated.setNetToBeReceived(channelGraph
							.getNetToBeReceived());
					consolidated.setGrossQty(channelGraph.getGrossQty());
					consolidated.setSaleRetQty(channelGraph.getSaleRetQty());
					consolidated.setNetQty(channelGraph.getNetQty());
					consolidated.setGrossNrAmount(channelGraph
							.getGrossNrAmount());

					consolidated
							.setNetEOSSValue(channelGraph.getNetEOSSValue());
					consolidated.setGrossTaxableSale(channelGraph
							.getGrossTaxableSale());
					consolidated.setReturnTaxableSale(channelGraph
							.getReturnTaxableSale());
					consolidated.setNetTaxableSale(channelGraph
							.getNetTaxableSale());

					consolidated.setGrossActualSale(channelGraph
							.getGrossActualSale());
					consolidated.setReturnActualSale(channelGraph
							.getReturnActualSale());
					consolidated.setNetActualSale(channelGraph
							.getNetActualSale());

					consolidated.setGrossTaxfreeSale(channelGraph
							.getGrossTaxfreeSale());
					consolidated.setReturnTaxfreeSale(channelGraph
							.getReturnTaxfreeSale());
					consolidated.setNetTaxfreeSale(channelGraph
							.getNetTaxfreeSale());

					consolidated.setCategory("Others");
					consolidated.setPartner("Others");
				} else {
					switch (criteria) {
					case "NetSaleSP":
						consolidated.setNetSpAmount(consolidated
								.getNetSpAmount()
								+ channelGraph.getNetSpAmount());
						consolidated.setNetNrAmount(consolidated
								.getNetNrAmount()
								+ channelGraph.getNetNrAmount());
						consolidated.setNetPr(consolidated.getNetPr()
								+ channelGraph.getNetPr());
						break;
					case "NetAR":
						consolidated.setNetToBeReceived(consolidated
								.getNetToBeReceived()
								+ channelGraph.getNetToBeReceived());
						consolidated.setNetAr(consolidated.getNetAr()
								+ channelGraph.getNetAr());
						break;
					case "GSvSR":
						consolidated.setGrossQty(consolidated.getGrossQty()
								+ channelGraph.getGrossQty());
						consolidated.setSaleRetQty(consolidated.getSaleRetQty()
								+ channelGraph.getSaleRetQty());
						consolidated.setNetQty(consolidated.getNetQty()
								+ channelGraph.getNetQty());
						break;
					case "GSAvRA":
						consolidated.setGrossSpAmount(consolidated
								.getGrossSpAmount()
								+ channelGraph.getGrossSpAmount());
						consolidated.setSaleRetSpAmount(consolidated
								.getSaleRetSpAmount()
								+ channelGraph.getSaleRetSpAmount());
						break;
					case "GrossProfit":
						consolidated.setGrossProfit(consolidated
								.getGrossProfit()
								+ channelGraph.getGrossProfit());
						break;
					case "GPCP":
						consolidated.setGpVsProductCost(consolidated
								.getGpVsProductCost()
								+ channelGraph.getGpVsProductCost());
						break;
					case "PR":
						consolidated.setNetPr(consolidated.getNetPr()
								+ channelGraph.getNetPr());
						consolidated.setNetProductCost(consolidated
								.getNetProductCost()
								+ channelGraph.getNetProductCost());
						break;
					case "NR":
						consolidated.setGrossProfit(consolidated
								.getGrossProfit()
								+ channelGraph.getGrossProfit());
						consolidated.setNetNrAmount(consolidated
								.getNetNrAmount()
								+ channelGraph.getNetNrAmount());
						break;
					case "GNR":
						consolidated.setGrossProfit(consolidated
								.getGrossProfit()
								+ channelGraph.getGrossProfit());
						consolidated.setGrossNrAmount(consolidated
								.getGrossNrAmount()
								+ channelGraph.getGrossNrAmount());
						break;
					case "EOSS":
						consolidated.setNetEOSSValue(consolidated
								.getNetEOSSValue()
								+ channelGraph.getNetEOSSValue());
						break;
					case "TaxableSale":
						consolidated.setGrossTaxableSale(consolidated
								.getGrossTaxableSale()
								+ channelGraph.getGrossTaxableSale());
						consolidated.setReturnTaxableSale(consolidated
								.getReturnTaxableSale()
								+ channelGraph.getReturnTaxableSale());
						consolidated.setNetTaxableSale(consolidated
								.getNetTaxableSale()
								+ channelGraph.getNetTaxableSale());
						break;
					case "ActualSale":
						consolidated.setGrossActualSale(consolidated
								.getGrossActualSale()
								+ channelGraph.getGrossActualSale());
						consolidated.setReturnActualSale(consolidated
								.getReturnActualSale()
								+ channelGraph.getReturnActualSale());
						consolidated.setNetActualSale(consolidated
								.getNetActualSale()
								+ channelGraph.getNetActualSale());
						break;
					case "TaxfreeSale":
						consolidated.setGrossTaxfreeSale(consolidated
								.getGrossTaxfreeSale()
								+ channelGraph.getGrossTaxfreeSale());
						consolidated.setReturnTaxfreeSale(consolidated
								.getReturnTaxfreeSale()
								+ channelGraph.getReturnTaxfreeSale());
						consolidated.setNetTaxfreeSale(consolidated
								.getNetTaxfreeSale()
								+ channelGraph.getNetTaxfreeSale());
						break;
					default:
						break;
					}
				}
			} else {
				newChannelReportList.add(channelGraph);
			}
		}
		newChannelReportList.add(consolidated);
		return newChannelReportList;
	}

	public static List<ChannelReportDetails> getChannelMonthlyList(
			List<ChannelReportDetails> channelGraphList, Date startDate,
			Date endDate) {

		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] months = dfs.getMonths();

		Map<String, ChannelReportDetails> newChannelReportMap = new HashMap<String, ChannelReportDetails>();

		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		int monthIndex = 1;
		while (cal.getTime().before(endDate)) {
			ChannelReportDetails consolidated = new ChannelReportDetails();
			consolidated.setMonth(months[cal.get(Calendar.MONTH)].substring(0,
					3) + " " + cal.get(Calendar.YEAR));
			consolidated.setMonthIndex(monthIndex);
			newChannelReportMap.put(consolidated.getMonth(), consolidated);
			cal.add(Calendar.MONTH, 1);
			monthIndex++;
		}

		for (ChannelReportDetails channelGraph : channelGraphList) {

			if (channelGraph.getShippedDate() != null) {
				Calendar orderCal = Calendar.getInstance();
				orderCal.setTime(channelGraph.getShippedDate());
				orderCal.set(Calendar.DAY_OF_MONTH, 1);

				ChannelReportDetails consolidated = newChannelReportMap
						.get(months[orderCal.get(Calendar.MONTH)].substring(0,
								3) + " " + orderCal.get(Calendar.YEAR));
				if (consolidated != null) {
					consolidated.setNetTaxableSale(consolidated
							.getNetTaxableSale()
							+ channelGraph.getGrossTaxableSale());
					consolidated.setNetActualSale(consolidated
							.getNetActualSale()
							+ channelGraph.getGrossActualSale());
					consolidated.setNetTaxfreeSale(consolidated
							.getNetTaxfreeSale()
							+ channelGraph.getGrossTaxfreeSale());
				}
			}

			if (channelGraph.getReturnDate() != null) {
				Calendar orderCal = Calendar.getInstance();
				orderCal.setTime(channelGraph.getReturnDate());
				orderCal.set(Calendar.DAY_OF_MONTH, 1);

				ChannelReportDetails consolidated = newChannelReportMap
						.get(months[orderCal.get(Calendar.MONTH)].substring(0,
								3) + " " + orderCal.get(Calendar.YEAR));
				if (consolidated != null) {
					consolidated.setNetTaxableSale(consolidated
							.getNetTaxableSale()
							- channelGraph.getReturnTaxableSale());
					consolidated.setNetActualSale(consolidated
							.getNetActualSale()
							- channelGraph.getReturnActualSale());
					consolidated.setNetTaxfreeSale(consolidated
							.getNetTaxfreeSale()
							- channelGraph.getReturnTaxfreeSale());
				}
			}

		}

		List<ChannelReportDetails> newChannelReportList = new ArrayList<>();
		for (String key : newChannelReportMap.keySet()) {
			newChannelReportList.add(newChannelReportMap.get(key));
		}
		return newChannelReportList;
	}

	/*public static List<DebtorsGraph1> transformDebtorsGraph1Graph(
			List<PartnerReportDetails> debtorsList, String criteria) {
		Date currDate = new Date();
		Map<String, DebtorsGraph1> debtorsGraph1Map = new HashMap<String, DebtorsGraph1>();
		for (PartnerReportDetails partnerBusiness : debtorsList) {
			String key = "";
			switch (criteria) {
			case "partner":
				key = partnerBusiness.getPcName();
				break;
			case "category":
				key = partnerBusiness.getParentCategory();
				if (StringUtils.isEmpty(key))
					key = "B2B";
				break;
			default:
				break;
			}
			DebtorsGraph1 debtorsGraph1 = debtorsGraph1Map.get(key);

			double actionablePD = 0;
			int actionableNetQty = 0;
			double upcomingPD = 0;
			int upcomingNetQty = 0;
			double netPaymentDifference = partnerBusiness
					.getPaymentDifference();
			int netSaleQty = partnerBusiness.getNetSaleQuantity();
			Date paymentDueDate = partnerBusiness.getPaymentDueDate();

			double netPaymentResult = partnerBusiness.getNetPaymentResult();
			actionablePD = partnerBusiness.getPaymentDifference();
			if (partnerBusiness.isPoOrder()) {
				actionableNetQty = partnerBusiness.getGrossSaleQuantity();
				if (partnerBusiness.getReturnDate() != null)
					actionableNetQty -= partnerBusiness.getReturnQuantity();
			} else {
				actionableNetQty = partnerBusiness.getNetSaleQuantity();
			}
			if (paymentDueDate != null && paymentDueDate.after(currDate)) {
				upcomingPD = partnerBusiness.getPaymentDifference();
				if (partnerBusiness.isPoOrder()) {
					upcomingNetQty = partnerBusiness.getGrossSaleQuantity();
					if (partnerBusiness.getReturnDate() != null)
						upcomingNetQty -= partnerBusiness.getReturnQuantity();
				} else {
					upcomingNetQty = partnerBusiness.getGrossSaleQuantity()
							- partnerBusiness.getReturnQuantity();
				}
			}
			double netRate = 0;
			if (paymentDueDate != null && paymentDueDate.after(new Date()))
				netRate = partnerBusiness.getNetRate();
			if (debtorsGraph1 == null) {
				debtorsGraph1 = new DebtorsGraph1();
			} else {
				netPaymentResult += debtorsGraph1.getNetPaymentResult();
				actionablePD += debtorsGraph1.getActionablePD();
				actionableNetQty += debtorsGraph1.getActionableNetQty();
				upcomingPD += debtorsGraph1.getUpcomingPD();
				upcomingNetQty += debtorsGraph1.getUpcomingNetQty();
				netPaymentDifference += debtorsGraph1.getNetPaymentDifference();
				netSaleQty += debtorsGraph1.getNetSaleQty();
			}
			debtorsGraph1.setNetPaymentResult(netPaymentResult);
			debtorsGraph1.setActionablePD(actionablePD);
			debtorsGraph1.setActionableNetQty(actionableNetQty);
			debtorsGraph1.setUpcomingPD(upcomingPD);
			debtorsGraph1.setUpcomingNetQty(upcomingNetQty);
			debtorsGraph1.setNetPaymentDifference(netPaymentDifference);
			debtorsGraph1.setNetSaleQty(netSaleQty);
			debtorsGraph1.setPartner(key);
			debtorsGraph1.setCategory(key);

			debtorsGraph1Map.put(key, debtorsGraph1);
		}
		System.out.println("debtorsGraph1Map :  "+debtorsGraph1Map.size());

		List<DebtorsGraph1> debtorsGraph1List = new ArrayList<DebtorsGraph1>();
		Iterator entries = debtorsGraph1Map.entrySet().iterator();
		while (entries.hasNext()) {
			Entry<String, DebtorsGraph1> thisEntry = (Entry<String, DebtorsGraph1>) entries
					.next();
			DebtorsGraph1 value = thisEntry.getValue();
			debtorsGraph1List.add(value);
		}
		System.out.println("debtorsGraph1List :  "+debtorsGraph1List.size());
		return debtorsGraph1List;
	}*/
	
	public static List<DebtorsGraph1> transformDebtorsGraph1Graph(
			Map<String, DebtorsGraph1> debtorsGraph1Map) {
		
		List<DebtorsGraph1> debtorsGraph1List = new ArrayList<DebtorsGraph1>();
		if(debtorsGraph1Map!=null)
		{
		Iterator entries = debtorsGraph1Map.entrySet().iterator();
		while (entries.hasNext()) {
			Entry<String, DebtorsGraph1> thisEntry = (Entry<String, DebtorsGraph1>) entries
					.next();
			DebtorsGraph1 value = thisEntry.getValue();
			debtorsGraph1List.add(value);
		}
		}
		System.out.println("debtorsGraph1List :  "+debtorsGraph1List.size());
		return debtorsGraph1List;
	}

	public static Object getDebtorsGraph1SortedList(
			List<DebtorsGraph1> debtorsGraph1List) {
		int maxLength = 5;
		if (debtorsGraph1List.size() <= maxLength)
			return debtorsGraph1List;
		List<DebtorsGraph1> newdDebtorsGraph1List = new ArrayList<>();
		int index = 1;
		boolean initialize = false;
		DebtorsGraph1 consolidated = new DebtorsGraph1();
		for (DebtorsGraph1 debtorsGraph1 : debtorsGraph1List) {
			if (index++ >= 5) {
				if (!initialize) {
					initialize = true;
					consolidated.setNetPaymentResult(debtorsGraph1
							.getNetPaymentResult());
					consolidated.setActionablePD(debtorsGraph1
							.getActionablePD());
					consolidated.setActionableNetQty(debtorsGraph1
							.getActionableNetQty());
					consolidated.setUpcomingPD(debtorsGraph1.getUpcomingPD());
					consolidated.setUpcomingNetQty(debtorsGraph1
							.getUpcomingNetQty());
					consolidated.setNetPaymentDifference(debtorsGraph1
							.getNetPaymentDifference());
					consolidated.setNetSaleQty(debtorsGraph1.getNetSaleQty());
					consolidated.setCategory("Others");
					consolidated.setPartner("Others");
				} else {
					consolidated.setNetPaymentResult(consolidated
							.getNetPaymentResult()
							+ debtorsGraph1.getNetPaymentResult());
					consolidated.setActionablePD(consolidated.getActionablePD()
							+ debtorsGraph1.getActionablePD());
					consolidated.setActionableNetQty(consolidated
							.getActionableNetQty()
							+ debtorsGraph1.getActionableNetQty());
					consolidated.setUpcomingPD(consolidated.getUpcomingPD()
							+ debtorsGraph1.getUpcomingPD());
					consolidated.setUpcomingNetQty(consolidated
							.getUpcomingNetQty()
							+ debtorsGraph1.getUpcomingNetQty());
					consolidated.setNetPaymentDifference(consolidated
							.getNetPaymentDifference()
							+ debtorsGraph1.getNetPaymentDifference());
					consolidated.setNetSaleQty(consolidated.getNetSaleQty()
							+ debtorsGraph1.getNetSaleQty());
				}
			} else {
				newdDebtorsGraph1List.add(debtorsGraph1);
			}
		}
		newdDebtorsGraph1List.add(consolidated);
		return newdDebtorsGraph1List;
	}

	public static List<UploadReportBean> prepareUploadReportListBean(
			List<UploadReport> uploadReportList) {

		List<UploadReportBean> uploadReportBeans = null;
		if (uploadReportList != null && !uploadReportList.isEmpty()) {
			uploadReportBeans = new ArrayList<UploadReportBean>();
			UploadReportBean bean = null;
			for (UploadReport uploadReport : uploadReportList) {
				bean = new UploadReportBean();
				bean.setId(uploadReport.getId());
				bean.setDescription(uploadReport.getDescription());
				bean.setFileType(uploadReport.getFileType());
				bean.setFileName(uploadReport.getFileName());
				bean.setFilePath(uploadReport.getFilePath());
				bean.setSellerId(uploadReport.getSeller().getId());
				bean.setSellerName(uploadReport.getSeller().getName());
				bean.setStatus(uploadReport.getStatus());
				bean.setTimeTaken(uploadReport.getTimeTaken());
				bean.setUploadDate(uploadReport.getUploadDate());
				bean.setNoOfErrors(uploadReport.getNoOfErrors());
				bean.setNoOfSuccess(uploadReport.getNoOfSuccess());
				bean.setUploadedAt(GlobalConstant.toDuration(new Date()
						.getTime() - uploadReport.getUploadDate().getTime()));
				uploadReportBeans.add(bean);
			}
		}

		return uploadReportBeans;
	}
	
	public static List<PartnerCategoryBean> preparePartnerCategoryListBean(
			List<PartnerCategoryMap> partnerCategoryList) {

		List<PartnerCategoryBean> partnerCategoryBeans = null;
		if (partnerCategoryList != null && !partnerCategoryList.isEmpty()) {
			partnerCategoryBeans = new ArrayList<PartnerCategoryBean>();
			PartnerCategoryBean bean = null;
			for (PartnerCategoryMap partnerCat : partnerCategoryList) {
				bean = new PartnerCategoryBean();
				bean.setId(partnerCat.getId());
				bean.setPartnerName(partnerCat.getPartnerName());
				bean.setPartnerCategoryRef(partnerCat.getPartnerCategoryRef());
				bean.setCommission(partnerCat.getCommission());
				partnerCategoryBeans.add(bean);
			}
		}
		return partnerCategoryBeans;
	}
	
	public static PartnerCategoryMap preparePartnerCategoryModel(
			PartnerCategoryBean partnerCategoryBean) {

		PartnerCategoryMap partnerCat = new PartnerCategoryMap();

		partnerCat.setId(partnerCategoryBean.getId());
		partnerCat.setPartnerName(partnerCategoryBean.getPartnerName());
		partnerCat.setPartnerCategoryRef(partnerCategoryBean.getPartnerCategoryRef());
		partnerCat.setCommission(partnerCategoryBean.getCommission());
		return partnerCat;
	}

	public static List<YearlyStockList> combineStockList(
			List<YearlyStockList> stockList) {
		Map<String, YearlyStockList> stockMap = new HashMap<String, YearlyStockList>();
		for (YearlyStockList stock : stockList) {
			String key = stock.getMonthStr();
			YearlyStockList newStock = stockMap.get(key);
			double openStock = stock.getOpenStock();
			double openStockValuation = stock.getOpenStockValuation();
			double closeStock = stock.getCloseStock();
			double closeStockValuation = stock.getCloseStockValuation();
			if (newStock == null) {
				newStock = new YearlyStockList();
				newStock.setMonth(stock.getMonth());
				newStock.setMonthStr(stock.getMonthStr());
			}
			newStock.setOpenStock(openStock + newStock.getOpenStock());
			newStock.setOpenStockValuation(openStockValuation
					+ newStock.getOpenStockValuation());
			newStock.setCloseStock(closeStock + newStock.getCloseStock());
			newStock.setCloseStockValuation(closeStockValuation
					+ newStock.getCloseStockValuation());
			stockMap.put(key, newStock);
		}

		Iterator entries = stockMap.entrySet().iterator();
		List<YearlyStockList> finalStockList = new ArrayList<YearlyStockList>();
		while (entries.hasNext()) {
			Entry<String, YearlyStockList> thisEntry = (Entry<String, YearlyStockList>) entries
					.next();
			YearlyStockList stock = thisEntry.getValue();
			finalStockList.add(stock);
		}
		Collections.sort(finalStockList, new YearlyStockList.OrderByMonth());
		return finalStockList;
	}

	public static List<Expenses> combineExpenses(List<YearlyStockList> stockList) {
		// TODO Auto-generated method stub
		return null;
	}

	public static List<Expenses> combineExpenses(int selectedYearInt,
			List<YearlyStockList> stockList) {
		List<Map<String, String>> dateRangeList = getDateRanges(selectedYearInt);
		for (Map<String, String> dateMap : dateRangeList) {

		}
		return null;
	}

	public static List<Map<String, String>> getDateRanges(int year) {
		List<Map<String, String>> dateRangeList = new ArrayList<Map<String, String>>();
		for (int i = 4; i <= 12; i++) {
			Map<String, String> dateMap = new HashMap<String, String>();
			dateMap.put("startDate", year + "-" + addZero(i) + "-01");
			if (i == 12) {
				dateMap.put("endDate", (year + 1) + "-01-01");
			} else {
				dateMap.put("endDate", year + "-" + addZero(i + 1) + "-01");
			}
			dateRangeList.add(dateMap);
		}
		for (int i = 1; i <= 3; i++) {
			Map<String, String> dateMap = new HashMap<String, String>();
			dateMap.put("startDate", (year + 1) + "-" + addZero(i) + "-01");
			dateMap.put("endDate", (year + 1) + "-" + addZero(i + 1) + "-01");
			dateRangeList.add(dateMap);
		}

		return dateRangeList;
	}

	private static String addZero(int i) {
		if (i < 10)
			return "0" + i;
		return i + "";
	}

	public static List<NetProfitabilityST> combineForShortTable(
			List<YearlyStockList> combinedStockList,
			List<NetPaymentResult> nprList, List<ChannelNR> nrList,
			List<ExpensesDetails> expensesCatList, List<String> catList) {
		Map<String, NetProfitabilityST> npMap = new HashMap<String, NetProfitabilityST>();
		List<NetProfitabilityST> npList = new ArrayList<NetProfitabilityST>();

		int counter = catList.size();
		for (YearlyStockList stock : combinedStockList) {
			NetProfitabilityST npST = new NetProfitabilityST();
			String key = stock.getMonthStr();
			npST.setKey(key);
			npST.setOpenStock(stock.getOpenStockValuation());
			npST.setCloseStock(stock.getCloseStockValuation());
			npST.setCatExpenses(getDoubleList(counter));
			npST.setNetProfit(stock.getOpenStockValuation()
					- stock.getCloseStockValuation());
			npMap.put(key, npST);
		}
		for (ChannelNR netRate : nrList) {
			String key = netRate.getKey();
			NetProfitabilityST npST = npMap.get(key);
			if (npST == null) {
				npST = new NetProfitabilityST();
				npST.setKey(key);
				npST.setCatExpenses(getDoubleList(counter));
			}
			npST.setNetRate(npST.getNpr() + netRate.getTotalNR());
			npST.setNetProfit(npST.getNetProfit() + npST.getNetRate());
			npMap.put(key, npST);
		}
		for (NetPaymentResult npr : nprList) {
			String key = npr.getKey();
			NetProfitabilityST npST = npMap.get(key);
			if (npST == null) {
				npST = new NetProfitabilityST();
				npST.setKey(key);
				npST.setCatExpenses(getDoubleList(counter));
			}
			npST.setNpr(npST.getNpr() + npr.getNetPaymentResult());
			npST.setNetProfit(npST.getNetProfit() + npST.getNpr());
			npMap.put(key, npST);
		}
		for (ExpensesDetails expenseCat : expensesCatList) {
			String key = expenseCat.getKey();
			NetProfitabilityST npST = npMap.get(key);
			if (npST == null) {
				npST = new NetProfitabilityST();
				npST.setKey(key);
			}
			List<Double> expenseList = npST.getCatExpenses();
			for (int index = 0; index < catList.size(); index++) {
				String category = catList.get(index);
				if (category.equals(expenseCat.getExpenseCatName())) {
					double expense = expenseCat.getAmount();
					expenseList.set(index, expense);
					npST.setNetProfit(npST.getNetProfit() - expense);
					npST.setTotalExpenses(npST.getTotalExpenses() + expense);
					break;
				}
			}
			npMap.put(key, npST);
		}
		Iterator entries = npMap.entrySet().iterator();
		while (entries.hasNext()) {
			Entry<String, NetProfitabilityST> thisEntry = (Entry<String, NetProfitabilityST>) entries
					.next();
			NetProfitabilityST stock = thisEntry.getValue();
			npList.add(stock);
		}
		return npList;
	}

	public static List<Double> getDoubleList(int counter) {
		List<Double> doubleList = new ArrayList<Double>();
		for (int i = 0; i < counter; i++) {
			doubleList.add(new Double(0));
		}
		return doubleList;
	}
	
	
	
	public static ShopcluesOrderAPI prepareShopcluesOrderModel(ShopcluesOrderAPIBean orderBean){
		
		ShopcluesOrderAPI orderModel = new ShopcluesOrderAPI();
		if(orderBean != null){			
			orderModel.setShopcluesOrderId(orderBean.getShopcluesOrderId());
			orderModel.setShopcluesUniqueId(orderBean.getShopcluesUniqueId());
			orderModel.setOrder_id(orderBean.getOrder_id());
			orderModel.setIs_parent_order(orderBean.getIs_parent_order());
			orderModel.setExempt_from_billing(orderBean.getExempt_from_billing());
			orderModel.setParent_order_id(orderBean.getParent_order_id());
			orderModel.setCompany_id(orderBean.getCompany_id());
			orderModel.setTimestamp(orderBean.getTimestamp());
			orderModel.setStatus(orderBean.getStatus());
			orderModel.setTotal(orderBean.getTotal());
			orderModel.setSubtotal(orderBean.getSubtotal());
			orderModel.setDetails(orderBean.getDetails());
			orderModel.setPayment_id(orderBean.getPayment_id());
			orderModel.setS_city(orderBean.getS_city());
			orderModel.setS_state(orderBean.getS_state());
			orderModel.setS_zipcode(orderBean.getS_zipcode());
			orderModel.setLabel_printed(orderBean.getLabel_printed());
			orderModel.setGift_it(orderBean.getGift_it());
			orderModel.setFirstname(orderBean.getFirstname());
			orderModel.setLastname(orderBean.getLastname());
			orderModel.setCredit_used(orderBean.getCredit_used());
			orderModel.setProduct_name(orderBean.getProduct_name());
			orderModel.setProduct_id(orderBean.getProduct_id());
			orderModel.setQuantity(orderBean.getQuantity());
			orderModel.setSelling_price(orderBean.getSelling_price());
			orderModel.setImage_path(orderBean.getImage_path());
			orderModel.setPayment_type(orderBean.getPayment_type());
			orderModel.setErrorMsg(orderBean.getErrorMsg());
			orderModel.setOrderStatus(orderBean.getOrderStatus());
			orderModel.setSellerId(orderBean.getSellerId());
		}
		return orderModel;
	}
	
	public static ShopcluesOrderAPIBean prepareShopcluesOrderBean(ShopcluesOrderAPI orderModel){
		
		ShopcluesOrderAPIBean orderBean = new ShopcluesOrderAPIBean();
		if(orderModel != null){			
			orderBean.setShopcluesOrderId(orderModel.getShopcluesOrderId());
			orderBean.setShopcluesUniqueId(orderModel.getShopcluesUniqueId());
			orderBean.setOrder_id(orderModel.getOrder_id());
			orderBean.setIs_parent_order(orderModel.getIs_parent_order());
			orderBean.setExempt_from_billing(orderModel.getExempt_from_billing());
			orderBean.setParent_order_id(orderModel.getParent_order_id());
			orderBean.setCompany_id(orderModel.getCompany_id());
			orderBean.setTimestamp(orderModel.getTimestamp());
			orderBean.setStatus(orderModel.getStatus());
			orderBean.setTotal(orderModel.getTotal());
			orderBean.setSubtotal(orderModel.getSubtotal());
			orderBean.setDetails(orderModel.getDetails());
			orderBean.setPayment_id(orderModel.getPayment_id());
			orderBean.setS_city(orderModel.getS_city());
			orderBean.setS_state(orderModel.getS_state());
			orderBean.setS_zipcode(orderModel.getS_zipcode());
			orderBean.setLabel_printed(orderModel.getLabel_printed());
			orderBean.setGift_it(orderModel.getGift_it());
			orderBean.setFirstname(orderModel.getFirstname());
			orderBean.setLastname(orderModel.getLastname());
			orderBean.setCredit_used(orderModel.getCredit_used());
			orderBean.setProduct_name(orderModel.getProduct_name());
			orderBean.setProduct_id(orderModel.getProduct_id());
			orderBean.setQuantity(orderModel.getQuantity());
			orderBean.setSelling_price(orderModel.getSelling_price());
			orderBean.setImage_path(orderModel.getImage_path());
			orderBean.setPayment_type(orderModel.getPayment_type());
			orderBean.setErrorMsg(orderModel.getErrorMsg());
			orderBean.setOrderStatus(orderModel.getOrderStatus());
			orderBean.setSellerId(orderModel.getSellerId());
		}
		return orderBean;
	}
	
	
	public static List<ShopcluesOrderAPI> listShopcluesOrderModel(List<ShopcluesOrderAPIBean> beans){
		List<ShopcluesOrderAPI> shopcluesOrderModels = new ArrayList<ShopcluesOrderAPI>();
		for(ShopcluesOrderAPIBean orderBean : beans){
			ShopcluesOrderAPI orderModel = new ShopcluesOrderAPI();
			orderModel.setShopcluesOrderId(orderBean.getShopcluesOrderId());
			orderModel.setShopcluesUniqueId(orderBean.getShopcluesUniqueId());
			orderModel.setOrder_id(orderBean.getOrder_id());
			orderModel.setIs_parent_order(orderBean.getIs_parent_order());
			orderModel.setExempt_from_billing(orderBean.getExempt_from_billing());
			orderModel.setParent_order_id(orderBean.getParent_order_id());
			orderModel.setCompany_id(orderBean.getCompany_id());
			orderModel.setTimestamp(orderBean.getTimestamp());
			orderModel.setStatus(orderBean.getStatus());
			orderModel.setTotal(orderBean.getTotal());
			orderModel.setSubtotal(orderBean.getSubtotal());
			orderModel.setDetails(orderBean.getDetails());
			orderModel.setPayment_id(orderBean.getPayment_id());
			orderModel.setS_city(orderBean.getS_city());
			orderModel.setS_state(orderBean.getS_state());
			orderModel.setS_zipcode(orderBean.getS_zipcode());
			orderModel.setLabel_printed(orderBean.getLabel_printed());
			orderModel.setGift_it(orderBean.getGift_it());
			orderModel.setFirstname(orderBean.getFirstname());
			orderModel.setLastname(orderBean.getLastname());
			orderModel.setCredit_used(orderBean.getCredit_used());
			orderModel.setProduct_name(orderBean.getProduct_name());
			orderModel.setProduct_id(orderBean.getProduct_id());
			orderModel.setQuantity(orderBean.getQuantity());
			orderModel.setSelling_price(orderBean.getSelling_price());
			orderModel.setImage_path(orderBean.getImage_path());
			orderModel.setPayment_type(orderBean.getPayment_type());
			orderModel.setErrorMsg(orderBean.getErrorMsg());
			orderModel.setOrderStatus(orderBean.getOrderStatus());
			orderModel.setSellerId(orderBean.getSellerId());
			shopcluesOrderModels.add(orderModel);
		}
		return shopcluesOrderModels;
	}
	
	public static List<ShopcluesOrderAPIBean> listShopcluesOrderBean(List<ShopcluesOrderAPI> models){
		List<ShopcluesOrderAPIBean> shopcluesOrderBeans = new ArrayList<ShopcluesOrderAPIBean>();
		for(ShopcluesOrderAPI orderModel : models){
			ShopcluesOrderAPIBean orderBean = new ShopcluesOrderAPIBean();
			orderBean.setShopcluesOrderId(orderModel.getShopcluesOrderId());
			orderBean.setShopcluesUniqueId(orderModel.getShopcluesUniqueId());
			orderBean.setOrder_id(orderModel.getOrder_id());
			orderBean.setIs_parent_order(orderModel.getIs_parent_order());
			orderBean.setExempt_from_billing(orderModel.getExempt_from_billing());
			orderBean.setParent_order_id(orderModel.getParent_order_id());
			orderBean.setCompany_id(orderModel.getCompany_id());
			orderBean.setTimestamp(orderModel.getTimestamp());
			orderBean.setStatus(orderModel.getStatus());
			orderBean.setTotal(orderModel.getTotal());
			orderBean.setSubtotal(orderModel.getSubtotal());
			orderBean.setDetails(orderModel.getDetails());
			orderBean.setPayment_id(orderModel.getPayment_id());
			orderBean.setS_city(orderModel.getS_city());
			orderBean.setS_state(orderModel.getS_state());
			orderBean.setS_zipcode(orderModel.getS_zipcode());
			orderBean.setLabel_printed(orderModel.getLabel_printed());
			orderBean.setGift_it(orderModel.getGift_it());
			orderBean.setFirstname(orderModel.getFirstname());
			orderBean.setLastname(orderModel.getLastname());
			orderBean.setCredit_used(orderModel.getCredit_used());
			orderBean.setProduct_name(orderModel.getProduct_name());
			orderBean.setProduct_id(orderModel.getProduct_id());
			orderBean.setQuantity(orderModel.getQuantity());
			orderBean.setSelling_price(orderModel.getSelling_price());
			orderBean.setImage_path(orderModel.getImage_path());
			orderBean.setPayment_type(orderModel.getPayment_type());
			orderBean.setErrorMsg(orderModel.getErrorMsg());
			orderBean.setOrderStatus(orderModel.getOrderStatus());
			orderBean.setSellerId(orderModel.getSellerId());
			shopcluesOrderBeans.add(orderBean);
		}
		return shopcluesOrderBeans;
	}

}
