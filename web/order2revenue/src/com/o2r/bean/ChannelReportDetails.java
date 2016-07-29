package com.o2r.bean;

import java.util.Comparator;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class ChannelReportDetails {
	private String orderId;
	private boolean isPoOrder;
	private String invoiceId;
	private String returnId;
	private String paymentId;
	private String paymentType;
	private Date shippedDate;
	private Date receivedDate;
	private Date deliveryDate;
	private Date paymentDate;
	private Date returnDate;
	private String awb;
	private String subOrderId;
	private String piRefNo;
	private String logisticPartner;
	private String partner;
	private String category;
	private String parentCategory;
	private String productSku;
	private double grossQty;
	private double grossNrAmount;
	private double grossSpAmount;
	private double saleRetQty;
	private double saleRetNrAmount;
	private double retAmountToBeReversed;
	private double saleRetSpAmount;
	private double netQty;
	private double netNrAmount;
	private double netSpAmount;
	private double saleRetVsGrossSale;
	private String taxCategory;
	private double netTaxLiability;
	private double netAr;
	private double netToBeReceived;
	private double productCost;
	private double returnProductCost;
	private double netProductCost;
	private double pr;
	private double netReturnCharges;
	private double netPr;
	private double netPaymentResult;
	private double paymentDifference;
	private double grossProfit;
	private double gpVsProductCost;
	private String finalStatus;
	
	private double retActualPercent;
	private double netEOSSValue;
	private double grossTaxableSale;
	private double returnTaxableSale;
	private double netTaxableSale;
	private double grossActualSale;
	private double returnActualSale;
	private double netActualSale;
	private double grossTaxfreeSale;
	private double returnTaxfreeSale;
	private double netTaxfreeSale;
	private String month;
	private int monthIndex;
	
	private double saleGrossProfit;
	private double returnGrossProfit;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public boolean isPoOrder() {
		return isPoOrder;
	}

	public void setPoOrder(boolean isPoOrder) {
		this.isPoOrder = isPoOrder;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getReturnId() {
		return returnId;
	}

	public void setReturnId(String returnId) {
		this.returnId = returnId;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Date getShippedDate() {
		return shippedDate;
	}

	public void setShippedDate(Date shippedDate) {
		this.shippedDate = shippedDate;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
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

	public String getPiRefNo() {
		return piRefNo;
	}

	public void setPiRefNo(String piRefNo) {
		this.piRefNo = piRefNo;
	}

	public String getLogisticPartner() {
		return logisticPartner;
	}

	public void setLogisticPartner(String logisticPartner) {
		this.logisticPartner = logisticPartner;
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

	public String getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(String parentCategory) {
		this.parentCategory = parentCategory;
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

	public double getRetAmountToBeReversed() {
		return retAmountToBeReversed;
	}

	public void setRetAmountToBeReversed(double retAmountToBeReversed) {
		this.retAmountToBeReversed = retAmountToBeReversed;
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

	public double getReturnProductCost() {
		return returnProductCost;
	}

	public void setReturnProductCost(double returnProductCost) {
		this.returnProductCost = returnProductCost;
	}

	public double getNetProductCost() {
		return netProductCost;
	}

	public void setNetProductCost(double netProductCost) {
		this.netProductCost = netProductCost;
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

	public double getNetEOSSValue() {
		return netEOSSValue;
	}

	public void setNetEOSSValue(double netEOSSValue) {
		this.netEOSSValue = netEOSSValue;
	}

	public double getGrossTaxableSale() {
		return grossTaxableSale;
	}

	public void setGrossTaxableSale(double grossTaxableSale) {
		this.grossTaxableSale = grossTaxableSale;
	}

	public double getReturnTaxableSale() {
		return returnTaxableSale;
	}

	public void setReturnTaxableSale(double returnTaxableSale) {
		this.returnTaxableSale = returnTaxableSale;
	}

	public double getNetTaxableSale() {
		return netTaxableSale;
	}

	public void setNetTaxableSale(double netTaxableSale) {
		this.netTaxableSale = netTaxableSale;
	}

	public double getGrossActualSale() {
		return grossActualSale;
	}

	public void setGrossActualSale(double grossActualSale) {
		this.grossActualSale = grossActualSale;
	}

	public double getReturnActualSale() {
		return returnActualSale;
	}

	public void setReturnActualSale(double returnActualSale) {
		this.returnActualSale = returnActualSale;
	}

	public double getNetActualSale() {
		return netActualSale;
	}

	public void setNetActualSale(double netActualSale) {
		this.netActualSale = netActualSale;
	}

	public double getGrossTaxfreeSale() {
		return grossTaxfreeSale;
	}

	public void setGrossTaxfreeSale(double grossTaxfreeSale) {
		this.grossTaxfreeSale = grossTaxfreeSale;
	}

	public double getReturnTaxfreeSale() {
		return returnTaxfreeSale;
	}

	public void setReturnTaxfreeSale(double returnTaxfreeSale) {
		this.returnTaxfreeSale = returnTaxfreeSale;
	}

	public double getNetTaxfreeSale() {
		return netTaxfreeSale;
	}

	public void setNetTaxfreeSale(double netTaxfreeSale) {
		this.netTaxfreeSale = netTaxfreeSale;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public int getMonthIndex() {
		return monthIndex;
	}

	public void setMonthIndex(int monthIndex) {
		this.monthIndex = monthIndex;
	}

	public double getRetActualPercent() {
		return retActualPercent;
	}

	public void setRetActualPercent(double retActualPercent) {
		this.retActualPercent = retActualPercent;
	}

	public double getSaleGrossProfit() {
		return saleGrossProfit;
	}

	public void setSaleGrossProfit(double saleGrossProfit) {
		this.saleGrossProfit = saleGrossProfit;
	}

	public double getReturnGrossProfit() {
		return returnGrossProfit;
	}

	public void setReturnGrossProfit(double returnGrossProfit) {
		this.returnGrossProfit = returnGrossProfit;
	}

	public static class OrderByShippedDate implements
		Comparator<ChannelReportDetails> {
		@Override
		public int compare(ChannelReportDetails graph1, ChannelReportDetails graph2) {
			if(graph1.shippedDate == null || graph2.shippedDate == null)
				return -1;
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
			String taxCategory1 = graph1.taxCategory;
			float tcPer1 = 0;
			if(StringUtils.isNotBlank(taxCategory1) && taxCategory1.indexOf("@")>-1){
				tcPer1 = Float.parseFloat(taxCategory1.split("@")[1]);
			}
			String taxCategory2 = graph2.taxCategory;
			float tcPer2 = 0;
			if(StringUtils.isNotBlank(taxCategory2) && taxCategory2.indexOf("@")>-1){
				tcPer2 = Float.parseFloat(taxCategory2.split("@")[1]);
			}
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
			return graph1.netPr < graph2.netPr ? 1
					: (graph1.netPr > graph2.netPr ? -1 : 0);
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
	
	public static class OrderByGNR implements
	Comparator<ChannelReportDetails> {
		@Override
		public int compare(ChannelReportDetails graph1, ChannelReportDetails graph2) {
			return graph1.grossNrAmount < graph2.grossNrAmount ? 1
					: (graph1.grossNrAmount > graph2.grossNrAmount ? -1 : 0);
		}
	}
	
	public static class OrderByEOSS implements
	Comparator<ChannelReportDetails> {
		@Override
		public int compare(ChannelReportDetails graph1, ChannelReportDetails graph2) {
			return graph1.netEOSSValue < graph2.netEOSSValue ? 1
					: (graph1.netEOSSValue > graph2.netEOSSValue ? -1 : 0);
		}
	}
	
	public static class OrderByTaxableSale implements
	Comparator<ChannelReportDetails> {
		@Override
		public int compare(ChannelReportDetails graph1, ChannelReportDetails graph2) {
			return graph1.netTaxableSale < graph2.netTaxableSale ? 1
					: (graph1.netTaxableSale > graph2.netTaxableSale ? -1 : 0);
		}
	}
	
	public static class OrderByActualSale implements
	Comparator<ChannelReportDetails> {
		@Override
		public int compare(ChannelReportDetails graph1, ChannelReportDetails graph2) {
			return graph1.netActualSale < graph2.netActualSale ? 1
					: (graph1.netActualSale > graph2.netActualSale ? -1 : 0);
		}
	}
	
	public static class OrderByTaxfreeSale implements
	Comparator<ChannelReportDetails> {
		@Override
		public int compare(ChannelReportDetails graph1, ChannelReportDetails graph2) {
			return graph1.netTaxfreeSale < graph2.netTaxfreeSale ? 1
					: (graph1.netTaxfreeSale > graph2.netTaxfreeSale ? -1 : 0);
		}
	}
	
	public static class OrderByMonthIndex implements
	Comparator<ChannelReportDetails> {
		@Override
		public int compare(ChannelReportDetails graph1, ChannelReportDetails graph2) {
			return graph1.monthIndex > graph2.monthIndex ? 1
					: (graph1.monthIndex < graph2.monthIndex ? -1 : 0);
		}
	}
}
