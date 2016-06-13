package com.o2r.bean;

import java.util.Comparator;

public class BusinessDetails {
	private String partner;
	private String categoryName;
	private double netPartnerCommissionPaid;
	private double netSaleQty;
	private double netSP;
	private double netTDSToBeDeposited;
	private double netTDS2;
	private double netTDS10;
	private double netPaymentResult;
	private double paymentDifference;
	private double netTaxableSale;
	private double netActualSale;
	private double netPrSale;
	private double netTaxToBePaid;
	private double netEossDiscountPaid;
	private double netNetRate;
	private double netProductCost;
	private double grossProfit;

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public double getNetPartnerCommissionPaid() {
		return netPartnerCommissionPaid;
	}

	public void setNetPartnerCommissionPaid(double netPartnerCommissionPaid) {
		this.netPartnerCommissionPaid = netPartnerCommissionPaid;
	}

	public double getNetSaleQty() {
		return netSaleQty;
	}

	public void setNetSaleQty(double netSaleQty) {
		this.netSaleQty = netSaleQty;
	}

	public double getNetSP() {
		return netSP;
	}

	public void setNetSP(double netSP) {
		this.netSP = netSP;
	}

	public double getNetTDSToBeDeposited() {
		return netTDSToBeDeposited;
	}

	public void setNetTDSToBeDeposited(double netTDSToBeDeposited) {
		this.netTDSToBeDeposited = netTDSToBeDeposited;
	}

	public double getNetTDS2() {
		return netTDS2;
	}

	public void setNetTDS2(double netTDS2) {
		this.netTDS2 = netTDS2;
	}

	public double getNetTDS10() {
		return netTDS10;
	}

	public void setNetTDS10(double netTDS10) {
		this.netTDS10 = netTDS10;
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

	public double getNetTaxableSale() {
		return netTaxableSale;
	}

	public void setNetTaxableSale(double netTaxableSale) {
		this.netTaxableSale = netTaxableSale;
	}

	public double getNetActualSale() {
		return netActualSale;
	}

	public void setNetActualSale(double netActualSale) {
		this.netActualSale = netActualSale;
	}

	public double getNetPrSale() {
		return netPrSale;
	}

	public void setNetPrSale(double netPrSale) {
		this.netPrSale = netPrSale;
	}

	public double getNetTaxToBePaid() {
		return netTaxToBePaid;
	}

	public void setNetTaxToBePaid(double netTaxToBePaid) {
		this.netTaxToBePaid = netTaxToBePaid;
	}

	public double getNetEossDiscountPaid() {
		return netEossDiscountPaid;
	}

	public void setNetEossDiscountPaid(double netEossDiscountPaid) {
		this.netEossDiscountPaid = netEossDiscountPaid;
	}

	public double getNetNetRate() {
		return netNetRate;
	}

	public void setNetNetRate(double netNetRate) {
		this.netNetRate = netNetRate;
	}

	public double getNetProductCost() {
		return netProductCost;
	}

	public void setNetProductCost(double netProductCost) {
		this.netProductCost = netProductCost;
	}

	public double getGrossProfit() {
		return grossProfit;
	}

	public void setGrossProfit(double grossProfit) {
		this.grossProfit = grossProfit;
	}

	public static class OrderByNetPartnerCommission implements
			Comparator<BusinessDetails> {

		@Override
		public int compare(BusinessDetails graph1, BusinessDetails graph2) {
			return graph1.netPartnerCommissionPaid < graph2.netPartnerCommissionPaid ? 1
					: (graph1.netPartnerCommissionPaid > graph2.netPartnerCommissionPaid ? -1
							: 0);
		}
	}

	public static class OrderByNetTDS implements Comparator<BusinessDetails> {

		@Override
		public int compare(BusinessDetails graph1, BusinessDetails graph2) {
			return graph1.netTDSToBeDeposited < graph2.netTDSToBeDeposited ? 1
					: (graph1.netTDSToBeDeposited > graph2.netTDSToBeDeposited ? -1
							: 0);
		}
	}
	
	public static class OrderByEOSS implements Comparator<BusinessDetails> {

		@Override
		public int compare(BusinessDetails graph1, BusinessDetails graph2) {
			return graph1.netEossDiscountPaid < graph2.netEossDiscountPaid ? 1
					: (graph1.netEossDiscountPaid > graph2.netEossDiscountPaid ? -1
							: 0);
		}
	}

	public static class OrderByNetTax implements Comparator<BusinessDetails> {

		@Override
		public int compare(BusinessDetails graph1, BusinessDetails graph2) {
			return graph1.netTaxToBePaid < graph2.netTaxToBePaid ? 1
					: (graph1.netTaxToBePaid > graph2.netTaxToBePaid ? -1 : 0);
		}
	}

	public static class OrderByNetNPR implements Comparator<BusinessDetails> {

		@Override
		public int compare(BusinessDetails graph1, BusinessDetails graph2) {
			return graph1.netPaymentResult < graph2.netPaymentResult ? 1
					: (graph1.netPaymentResult > graph2.netPaymentResult ? -1
							: 0);
		}
	}

	public static class OrderByNetTaxable implements Comparator<BusinessDetails> {

		@Override
		public int compare(BusinessDetails graph1, BusinessDetails graph2) {
			return graph1.netTaxableSale < graph2.netTaxableSale ? 1
					: (graph1.netTaxableSale > graph2.netTaxableSale ? -1 : 0);
		}
	}
}
