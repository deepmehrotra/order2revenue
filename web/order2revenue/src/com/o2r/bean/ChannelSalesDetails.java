package com.o2r.bean;

import java.util.Date;

import javax.persistence.Column;

public class ChannelSalesDetails {
	private String pcName;
	private double orderMRP;
	private double orderSP;
	private double shippingCharges;
	private int quantity;
	private int netSaleQuantity;
	private double netRate;
	private double grossNetRate;
	private double discount;
	private double partnerCommission;
	private double pr;
	private double totalAmountRecieved;
	private double poPrice;
	private double grossProfit;
	private double fixedfee;
	private double pccAmount;
	private double negativeAmount;
	private double positiveAmount;
	private double actualrecived2;
	private double netPaymentResult;
	private double paymentDifference;
	private double returnOrRTOCharges;
	private int returnorrtoQty;
	private float serviceTax;
	private String startDate;
	private String endDate;
	private int rowCount;
	private String taxCategtory;
	private String groupByName;
	private double returnOrRTOChargestoBeDeducted;
	private int orderId;
	private String invoiceID;
	private String uniqueItemId;
	private String channelOrderID;
	private String productSkuCode;
	
	public String getProductSkuCode() {
		return productSkuCode;
	}
	public void setProductSkuCode(String productSkuCode) {
		this.productSkuCode = productSkuCode;
	}
	public String getUniqueItemId() {
		return uniqueItemId;
	}
	public void setUniqueItemId(String uniqueItemId) {
		this.uniqueItemId = uniqueItemId;
	}
	public String getChannelOrderID() {
		return channelOrderID;
	}
	public void setChannelOrderID(String channelOrderID) {
		this.channelOrderID = channelOrderID;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getInvoiceID() {
		return invoiceID;
	}
	public void setInvoiceID(String invoiceID) {
		this.invoiceID = invoiceID;
	}
	public double getReturnOrRTOChargestoBeDeducted() {
		return returnOrRTOChargestoBeDeducted;
	}
	public void setReturnOrRTOChargestoBeDeducted(
			double returnOrRTOChargestoBeDeducted) {
		this.returnOrRTOChargestoBeDeducted = returnOrRTOChargestoBeDeducted;
	}
	public String getGroupByName() {
		return groupByName;
	}
	public void setGroupByName(String groupByName) {
		this.groupByName = groupByName;
	}
	public String getTaxCategtory() {
		return taxCategtory;
	}
	public void setTaxCategtory(String taxCategtory) {
		this.taxCategtory = taxCategtory;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getPcName() {
		return pcName;
	}
	public void setPcName(String pcName) {
		this.pcName = pcName;
	}
	public double getOrderMRP() {
		return orderMRP;
	}
	public void setOrderMRP(double orderMRP) {
		this.orderMRP = orderMRP;
	}
	public double getOrderSP() {
		return orderSP;
	}
	public void setOrderSP(double orderSP) {
		this.orderSP = orderSP;
	}
	public double getShippingCharges() {
		return shippingCharges;
	}
	public void setShippingCharges(double shippingCharges) {
		this.shippingCharges = shippingCharges;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getNetSaleQuantity() {
		return netSaleQuantity;
	}
	public void setNetSaleQuantity(int netSaleQuantity) {
		this.netSaleQuantity = netSaleQuantity;
	}
	public double getNetRate() {
		return netRate;
	}
	public void setNetRate(double netRate) {
		this.netRate = netRate;
	}
	public double getGrossNetRate() {
		return grossNetRate;
	}
	public void setGrossNetRate(double grossNetRate) {
		this.grossNetRate = grossNetRate;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public double getPartnerCommission() {
		return partnerCommission;
	}
	public void setPartnerCommission(double partnerCommission) {
		this.partnerCommission = partnerCommission;
	}
	public double getPr() {
		return pr;
	}
	public void setPr(double pr) {
		this.pr = pr;
	}
	public double getTotalAmountRecieved() {
		return totalAmountRecieved;
	}
	public void setTotalAmountRecieved(double totalAmountRecieved) {
		this.totalAmountRecieved = totalAmountRecieved;
	}
	public double getPoPrice() {
		return poPrice;
	}
	public void setPoPrice(double poPrice) {
		this.poPrice = poPrice;
	}
	public double getGrossProfit() {
		return grossProfit;
	}
	public void setGrossProfit(double grossProfit) {
		this.grossProfit = grossProfit;
	}
	public double getFixedfee() {
		return fixedfee;
	}
	public void setFixedfee(double fixedfee) {
		this.fixedfee = fixedfee;
	}
	public double getPccAmount() {
		return pccAmount;
	}
	public void setPccAmount(double pccAmount) {
		this.pccAmount = pccAmount;
	}
	public double getNegativeAmount() {
		return negativeAmount;
	}
	public void setNegativeAmount(double negativeAmount) {
		this.negativeAmount = negativeAmount;
	}
	public double getPositiveAmount() {
		return positiveAmount;
	}
	public void setPositiveAmount(double positiveAmount) {
		this.positiveAmount = positiveAmount;
	}
	public double getActualrecived2() {
		return actualrecived2;
	}
	public void setActualrecived2(double actualrecived2) {
		this.actualrecived2 = actualrecived2;
	}
	public double getNetPaymentResult() {
		return netPaymentResult;
	}
	public void setNetPaymentResult(double netPaymentResult) {
		this.netPaymentResult = netPaymentResult;
	}
	public double getPaymentDifference() {
		return paymentDifference;
	}
	public void setPaymentDifference(double paymentDifference) {
		this.paymentDifference = paymentDifference;
	}
	public double getReturnOrRTOCharges() {
		return returnOrRTOCharges;
	}
	public void setReturnOrRTOCharges(double returnOrRTOCharges) {
		this.returnOrRTOCharges = returnOrRTOCharges;
	}
	public int getReturnorrtoQty() {
		return returnorrtoQty;
	}
	public void setReturnorrtoQty(int returnorrtoQty) {
		this.returnorrtoQty = returnorrtoQty;
	}
	public float getServiceTax() {
		return serviceTax;
	}
	public void setServiceTax(float serviceTax) {
		this.serviceTax = serviceTax;
	}
}
