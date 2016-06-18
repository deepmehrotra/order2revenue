package com.o2r.bean;

import java.util.Comparator;
import java.util.Date;

public class PartnerReportDetails {
	private int orderId;
	private String invoiceID;
	private String awb;
	private String subOrderId;
	private String piRefNumber;
	private String logisticPartner;
	private String channelOrderID;
	private String pcName;
	private Date orderDate;
	private Date shippedDate;
	private Date deliveryDate;
	private Date paymentDueDate;
	private Date dateofPayment;
	private Date returnDate;
	
	private boolean isPoOrder;

	private String returnId;
	
	private String customerName;
	private String customerEmail;
	private String customerPhone;
	private String customerZip;
	private String customerCity;
	
	private String productCategory;
	private double productPrice;

	private int grossSaleQuantity;
	private int returnQuantity;
	private int netSaleQuantity;

	private double orderSP;
	private double returnSP;
	private double netSP;
	private double netRate;
	private double grossNetRate;

	private double netReturnCharges;
	private double grossReturnChargesReversed;
	private double totalReturnCharges;
	private double netPaymentResult;

	private double paymentDifference;
	
	private int paymentId;
	private String paymentType;
	private String paymentCycle;
	private double negativeAmount;
	private double positiveAmount;
	private Date paymentUploadedDate;

	private double grossPartnerCommission;

	private double pccAmount;
	private double fixedfee;
	private double shippingCharges;

	private double serviceTax;
	private double taxSP;
	private double taxPOPrice;
	private double grossCommission;
	private double grossCommissionQty;
	private double returnCommision;

	private double additionalReturnCharges;
	private double netPartnerCommissionPaid;

	private double tdsToBeDeducted10;
	private double tdsToBeDeducted2;
	private double grossTds;
	private double returnTds;
	private double tdsToBeDeposited;
	private double netTaxToBePaid;
	private double netEossValue;

	private double netPr;
	private String finalStatus;

	private String returnChargesDesciption;

	private double grossProfit;
	private double netActualSale;

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

	public String getAwb() {
		return awb;
	}

	public void setAwb(String awb) {
		this.awb = awb;
	}

	public String getSubOrderId() {
		return subOrderId;
	}

	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}

	public String getPiRefNumber() {
		return piRefNumber;
	}

	public void setPiRefNumber(String piRefNumber) {
		this.piRefNumber = piRefNumber;
	}

	public String getLogisticPartner() {
		return logisticPartner;
	}

	public void setLogisticPartner(String logisticPartner) {
		this.logisticPartner = logisticPartner;
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

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
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

	public boolean isPoOrder() {
		return isPoOrder;
	}

	public void setPoOrder(boolean isPoOrder) {
		this.isPoOrder = isPoOrder;
	}

	public String getReturnId() {
		return returnId;
	}

	public void setReturnId(String returnId) {
		this.returnId = returnId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getCustomerZip() {
		return customerZip;
	}

	public void setCustomerZip(String customerZip) {
		this.customerZip = customerZip;
	}

	public String getCustomerCity() {
		return customerCity;
	}

	public void setCustomerCity(String customerCity) {
		this.customerCity = customerCity;
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

	public double getReturnSP() {
		return returnSP;
	}

	public void setReturnSP(double returnSP) {
		this.returnSP = returnSP;
	}

	public double getNetSP() {
		return netSP;
	}

	public void setNetSP(double netSP) {
		this.netSP = netSP;
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

	public double getGrossReturnChargesReversed() {
		return grossReturnChargesReversed;
	}

	public void setGrossReturnChargesReversed(double grossReturnChargesReversed) {
		this.grossReturnChargesReversed = grossReturnChargesReversed;
	}

	public double getTotalReturnCharges() {
		return totalReturnCharges;
	}

	public void setTotalReturnCharges(double totalReturnCharges) {
		this.totalReturnCharges = totalReturnCharges;
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

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getPaymentCycle() {
		return paymentCycle;
	}

	public void setPaymentCycle(String paymentCycle) {
		this.paymentCycle = paymentCycle;
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

	public Date getPaymentUploadedDate() {
		return paymentUploadedDate;
	}

	public void setPaymentUploadedDate(Date paymentUploadedDate) {
		this.paymentUploadedDate = paymentUploadedDate;
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

	public double getGrossCommissionQty() {
		return grossCommissionQty;
	}

	public void setGrossCommissionQty(double grossCommissionQty) {
		this.grossCommissionQty = grossCommissionQty;
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

	public double getGrossTds() {
		return grossTds;
	}

	public void setGrossTds(double grossTds) {
		this.grossTds = grossTds;
	}

	public double getReturnTds() {
		return returnTds;
	}

	public void setReturnTds(double returnTds) {
		this.returnTds = returnTds;
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

	public String getReturnChargesDesciption() {
		return returnChargesDesciption;
	}

	public void setReturnChargesDesciption(String returnChargesDesciption) {
		this.returnChargesDesciption = returnChargesDesciption;
	}

	public double getGrossProfit() {
		return grossProfit;
	}

	public void setGrossProfit(double grossProfit) {
		this.grossProfit = grossProfit;
	}

	public double getNetActualSale() {
		return netActualSale;
	}

	public void setNetActualSale(double netActualSale) {
		this.netActualSale = netActualSale;
	}

	public static class OrderByShippedDate implements
			Comparator<PartnerReportDetails> {
		@Override
		public int compare(PartnerReportDetails graph1, PartnerReportDetails graph2) {
			if(graph1.shippedDate == null || graph2.shippedDate == null)
				return -1;
			return graph1.shippedDate.after(graph2.shippedDate) ? 1
					: (graph1.shippedDate.before(graph2.shippedDate) ? -1 : 0);
		}
	}
}
