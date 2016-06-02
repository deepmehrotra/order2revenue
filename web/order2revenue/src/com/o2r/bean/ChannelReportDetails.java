package com.o2r.bean;

import java.util.Comparator;
import java.util.Date;

public class ChannelReportDetails {
	private String orderId;
	private String invoiceId;
	private Date shippedDate;
	private String partner;
	private String category;
	private String productSku;
	private double grossQty;
	private double grossNrAmount;
	private double grossSpAmount;
	private double saleRetQty;
	private double saleRetNrAmount;
	private double saleRetSpAmount;
	private double netQty;
	private double netNrAmount;
	private double netSpAmount;
	private double netPureSaleQty;
	private double netPureSaleNrAmount;
	private double netPureSaleSpAmount;
	private double saleRetVsGrossSale;
	private String taxCategory;
	private double netTaxLiability;
	private double netAr;
	private double netToBeReceived;
	private double productCost;
	private double pr;
	private double netReturnCharges;
	private double netPr;
	private double netPaymentResult;
	private double paymentDifference;
	private double grossProfit;
	private double gpVsProductCost;
	private String finalStatus;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Date getShippedDate() {
		return shippedDate;
	}

	public void setShippedDate(Date shippedDate) {
		this.shippedDate = shippedDate;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getProductSku() {
		return productSku;
	}

	public void setProductSku(String productSku) {
		this.productSku = productSku;
	}

	public double getGrossQty() {
		return grossQty;
	}

	public void setGrossQty(double grossQty) {
		this.grossQty = grossQty;
	}

	public double getGrossNrAmount() {
		return grossNrAmount;
	}

	public void setGrossNrAmount(double grossNrAmount) {
		this.grossNrAmount = grossNrAmount;
	}

	public double getGrossSpAmount() {
		return grossSpAmount;
	}

	public void setGrossSpAmount(double grossSpAmount) {
		this.grossSpAmount = grossSpAmount;
	}

	public double getSaleRetQty() {
		return saleRetQty;
	}

	public void setSaleRetQty(double saleRetQty) {
		this.saleRetQty = saleRetQty;
	}

	public double getSaleRetNrAmount() {
		return saleRetNrAmount;
	}

	public void setSaleRetNrAmount(double saleRetNrAmount) {
		this.saleRetNrAmount = saleRetNrAmount;
	}

	public double getSaleRetSpAmount() {
		return saleRetSpAmount;
	}

	public void setSaleRetSpAmount(double saleRetSpAmount) {
		this.saleRetSpAmount = saleRetSpAmount;
	}

	public double getNetQty() {
		return netQty;
	}

	public void setNetQty(double netQty) {
		this.netQty = netQty;
	}

	public double getNetNrAmount() {
		return netNrAmount;
	}

	public void setNetNrAmount(double netNrAmount) {
		this.netNrAmount = netNrAmount;
	}

	public double getNetSpAmount() {
		return netSpAmount;
	}

	public void setNetSpAmount(double netSpAmount) {
		this.netSpAmount = netSpAmount;
	}

	public double getNetPureSaleQty() {
		return netPureSaleQty;
	}

	public void setNetPureSaleQty(double netPureSaleQty) {
		this.netPureSaleQty = netPureSaleQty;
	}

	public double getNetPureSaleNrAmount() {
		return netPureSaleNrAmount;
	}

	public void setNetPureSaleNrAmount(double netPureSaleNrAmount) {
		this.netPureSaleNrAmount = netPureSaleNrAmount;
	}

	public double getNetPureSaleSpAmount() {
		return netPureSaleSpAmount;
	}

	public void setNetPureSaleSpAmount(double netPureSaleSpAmount) {
		this.netPureSaleSpAmount = netPureSaleSpAmount;
	}

	public double getSaleRetVsGrossSale() {
		return saleRetVsGrossSale;
	}

	public void setSaleRetVsGrossSale(double saleRetVsGrossSale) {
		this.saleRetVsGrossSale = saleRetVsGrossSale;
	}

	public String getTaxCategory() {
		return taxCategory;
	}

	public void setTaxCategory(String taxCategory) {
		this.taxCategory = taxCategory;
	}

	public double getNetTaxLiability() {
		return netTaxLiability;
	}

	public void setNetTaxLiability(double netTaxLiability) {
		this.netTaxLiability = netTaxLiability;
	}

	public double getNetAr() {
		return netAr;
	}

	public void setNetAr(double netAr) {
		this.netAr = netAr;
	}

	public double getNetToBeReceived() {
		return netToBeReceived;
	}

	public void setNetToBeReceived(double netToBeReceived) {
		this.netToBeReceived = netToBeReceived;
	}

	public double getProductCost() {
		return productCost;
	}

	public void setProductCost(double productCost) {
		this.productCost = productCost;
	}

	public double getPr() {
		return pr;
	}

	public void setPr(double pr) {
		this.pr = pr;
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

	public double getNetPr() {
		return netPr;
	}

	public void setNetPr(double netPr) {
		this.netPr = netPr;
	}

	public double getPaymentDifference() {
		return paymentDifference;
	}

	public void setPaymentDifference(double paymentDifference) {
		this.paymentDifference = paymentDifference;
	}

	public double getGrossProfit() {
		return grossProfit;
	}

	public void setGrossProfit(double grossProfit) {
		this.grossProfit = grossProfit;
	}

	public double getGpVsProductCost() {
		return gpVsProductCost;
	}

	public void setGpVsProductCost(double gpVsProductCost) {
		this.gpVsProductCost = gpVsProductCost;
	}
	
	public String getFinalStatus() {
		return finalStatus;
	}

	public void setFinalStatus(String finalStatus) {
		this.finalStatus = finalStatus;
	}

	public static class OrderByShippedDate implements
		Comparator<ChannelReportDetails> {
		@Override
		public int compare(ChannelReportDetails graph1, ChannelReportDetails graph2) {
			return graph1.shippedDate.after(graph2.shippedDate) ? 1
					: (graph1.shippedDate.before(graph2.shippedDate) ? -1 : 0);
		}
	}
	
	public static class OrderByNetSaleSP implements
	Comparator<ChannelReportDetails> {
		@Override
		public int compare(ChannelReportDetails graph1, ChannelReportDetails graph2) {
			return graph1.netSpAmount < graph2.netSpAmount ? 1
					: (graph1.netSpAmount > graph2.netSpAmount ? -1 : 0);
		}
	}
	
	public static class OrderByTC implements
	Comparator<ChannelReportDetails> {
		@Override
		public int compare(ChannelReportDetails graph1, ChannelReportDetails graph2) {
			int tcPer1 = Integer.parseInt(graph1.taxCategory.split("@")[1]);
			int tcPer2 = Integer.parseInt(graph2.taxCategory.split("@")[1]);
			return tcPer1 < tcPer2 ? -1
					: (tcPer1 > tcPer2 ? 1 : 0);
		}
	}
	
	public static class OrderByNPR implements
	Comparator<ChannelReportDetails> {
		@Override
		public int compare(ChannelReportDetails graph1, ChannelReportDetails graph2) {
			return graph1.netPaymentResult < graph2.netPaymentResult ? 1
					: (graph1.netPaymentResult > graph2.netPaymentResult ? -1 : 0);
		}
	}
	
	public static class OrderByGSvSR implements
	Comparator<ChannelReportDetails> {
		@Override
		public int compare(ChannelReportDetails graph1, ChannelReportDetails graph2) {
			return graph1.grossQty < graph2.grossQty ? 1
					: (graph1.grossQty > graph2.grossQty ? -1 : 0);
		}
	}
	
	public static class OrderByGSAvRA implements
	Comparator<ChannelReportDetails> {
		@Override
		public int compare(ChannelReportDetails graph1, ChannelReportDetails graph2) {
			return graph1.grossSpAmount < graph2.grossSpAmount ? 1
					: (graph1.grossSpAmount > graph2.grossSpAmount ? -1 : 0);
		}
	}
	
	public static class OrderByGrossProfit implements
	Comparator<ChannelReportDetails> {
		@Override
		public int compare(ChannelReportDetails graph1, ChannelReportDetails graph2) {
			return graph1.grossProfit < graph2.grossProfit ? 1
					: (graph1.grossProfit > graph2.grossProfit ? -1 : 0);
		}
	}
	
	public static class OrderByGPCP implements
	Comparator<ChannelReportDetails> {
		@Override
		public int compare(ChannelReportDetails graph1, ChannelReportDetails graph2) {
			return graph1.gpVsProductCost < graph2.gpVsProductCost ? 1
					: (graph1.gpVsProductCost > graph2.gpVsProductCost ? -1 : 0);
		}
	}
	
	public static class OrderByPR implements
	Comparator<ChannelReportDetails> {
		@Override
		public int compare(ChannelReportDetails graph1, ChannelReportDetails graph2) {
			return graph1.pr < graph2.pr ? 1
					: (graph1.pr > graph2.pr ? -1 : 0);
		}
	}
	
	public static class OrderByNR implements
	Comparator<ChannelReportDetails> {
		@Override
		public int compare(ChannelReportDetails graph1, ChannelReportDetails graph2) {
			return graph1.netNrAmount < graph2.netNrAmount ? 1
					: (graph1.netNrAmount > graph2.netNrAmount ? -1 : 0);
		}
	}
}
