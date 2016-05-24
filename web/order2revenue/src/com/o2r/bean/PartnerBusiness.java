package com.o2r.bean;

import java.util.Date;

public class PartnerBusiness {
	private String invoiceID;
	private String channelOrderID;
	private String pcName;
	private Date orderDate;
	private Date shippedDate;
	private Date paymentDueDate;
	private Date dateofPayment;
	private Date returnDate;

	private String productCategory;
	private double productPrice;

	private int grossSaleQuantity;
	private int returnQuantity;
	private int netSaleQuantity;

	private double orderSP;
	private double netRate;
	private double grossNetRate;

	private double netReturnCharges;
	private double netPaymentResult;

	private double paymentDifference;

	private double grossPartnerCommission;

	private double pccAmount;
	private double fixedfee;
	private double shippingCharges;

	private double serviceTax;
	private double taxSP;
	private double taxPOPrice;
	private double grossCommission;
	private double returnCommision;

	private double additionalReturnCharges;
	private double netPartnerCommissionPaid;

	private double tdsToBeDeducted10;
	private double tdsToBeDeducted2;
	private double tdsToBeDeposited;
	private double netTaxToBePaid;
	private double netEossValue;

	private double netPr;
	private String finalStatus;
	
	private double grossProfit;

	public String getInvoiceID() {
		return invoiceID;
	}

	public void setInvoiceID(String invoiceID) {
		this.invoiceID = invoiceID;
	}

	public String getChannelOrderID() {
		return channelOrderID;
	}

	public void setChannelOrderID(String channelOrderID) {
		this.channelOrderID = channelOrderID;
	}

	public String getPcName() {
		return pcName;
	}

	public void setPcName(String pcName) {
		this.pcName = pcName;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getShippedDate() {
		return shippedDate;
	}

	public void setShippedDate(Date shippedDate) {
		this.shippedDate = shippedDate;
	}

	public Date getPaymentDueDate() {
		return paymentDueDate;
	}

	public void setPaymentDueDate(Date paymentDueDate) {
		this.paymentDueDate = paymentDueDate;
	}

	public Date getDateofPayment() {
		return dateofPayment;
	}

	public void setDateofPayment(Date dateofPayment) {
		this.dateofPayment = dateofPayment;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public int getGrossSaleQuantity() {
		return grossSaleQuantity;
	}

	public void setGrossSaleQuantity(int grossSaleQuantity) {
		this.grossSaleQuantity = grossSaleQuantity;
	}

	public int getReturnQuantity() {
		return returnQuantity;
	}

	public void setReturnQuantity(int returnQuantity) {
		this.returnQuantity = returnQuantity;
	}

	public int getNetSaleQuantity() {
		return netSaleQuantity;
	}

	public void setNetSaleQuantity(int netSaleQuantity) {
		this.netSaleQuantity = netSaleQuantity;
	}

	public double getOrderSP() {
		return orderSP;
	}

	public void setOrderSP(double orderSP) {
		this.orderSP = orderSP;
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

	public double getNetReturnCharges() {
		return netReturnCharges;
	}

	public void setNetReturnCharges(double netReturnCharges) {
		this.netReturnCharges = netReturnCharges;
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

	public double getGrossPartnerCommission() {
		return grossPartnerCommission;
	}

	public void setGrossPartnerCommission(double grossPartnerCommission) {
		this.grossPartnerCommission = grossPartnerCommission;
	}

	public double getPccAmount() {
		return pccAmount;
	}

	public void setPccAmount(double pccAmount) {
		this.pccAmount = pccAmount;
	}

	public double getFixedfee() {
		return fixedfee;
	}

	public void setFixedfee(double fixedfee) {
		this.fixedfee = fixedfee;
	}

	public double getShippingCharges() {
		return shippingCharges;
	}

	public void setShippingCharges(double shippingCharges) {
		this.shippingCharges = shippingCharges;
	}

	public double getServiceTax() {
		return serviceTax;
	}

	public void setServiceTax(double serviceTax) {
		this.serviceTax = serviceTax;
	}

	public double getTaxSP() {
		return taxSP;
	}

	public void setTaxSP(double taxSP) {
		this.taxSP = taxSP;
	}

	public double getTaxPOPrice() {
		return taxPOPrice;
	}

	public void setTaxPOPrice(double taxPOPrice) {
		this.taxPOPrice = taxPOPrice;
	}

	public double getGrossCommission() {
		return grossCommission;
	}

	public void setGrossCommission(double grossCommission) {
		this.grossCommission = grossCommission;
	}

	public double getReturnCommision() {
		return returnCommision;
	}

	public void setReturnCommision(double returnCommision) {
		this.returnCommision = returnCommision;
	}

	public double getAdditionalReturnCharges() {
		return additionalReturnCharges;
	}

	public void setAdditionalReturnCharges(double additionalReturnCharges) {
		this.additionalReturnCharges = additionalReturnCharges;
	}

	public double getNetPartnerCommissionPaid() {
		return netPartnerCommissionPaid;
	}

	public void setNetPartnerCommissionPaid(double netPartnerCommissionPaid) {
		this.netPartnerCommissionPaid = netPartnerCommissionPaid;
	}

	public double getTdsToBeDeducted10() {
		return tdsToBeDeducted10;
	}

	public void setTdsToBeDeducted10(double tdsToBeDeducted10) {
		this.tdsToBeDeducted10 = tdsToBeDeducted10;
	}

	public double getTdsToBeDeducted2() {
		return tdsToBeDeducted2;
	}

	public void setTdsToBeDeducted2(double tdsToBeDeducted2) {
		this.tdsToBeDeducted2 = tdsToBeDeducted2;
	}

	public double getTdsToBeDeposited() {
		return tdsToBeDeposited;
	}

	public void setTdsToBeDeposited(double tdsToBeDeposited) {
		this.tdsToBeDeposited = tdsToBeDeposited;
	}

	public double getNetTaxToBePaid() {
		return netTaxToBePaid;
	}

	public void setNetTaxToBePaid(double netTaxToBePaid) {
		this.netTaxToBePaid = netTaxToBePaid;
	}

	public double getNetEossValue() {
		return netEossValue;
	}

	public void setNetEossValue(double netEossValue) {
		this.netEossValue = netEossValue;
	}

	public double getNetPr() {
		return netPr;
	}

	public void setNetPr(double netPr) {
		this.netPr = netPr;
	}

	public String getFinalStatus() {
		return finalStatus;
	}

	public void setFinalStatus(String finalStatus) {
		this.finalStatus = finalStatus;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public double getGrossProfit() {
		return grossProfit;
	}

	public void setGrossProfit(double grossProfit) {
		this.grossProfit = grossProfit;
	}
}
