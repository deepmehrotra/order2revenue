package com.o2r.bean;

import java.util.Comparator;

public class BusinessGraph {
	private double netPartnerCommissionPaid;
	private double netSaleQty;
	private double netTDSToBeDeposited;
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

	public double getNetTDSToBeDeposited() {
		return netTDSToBeDeposited;
	}

	public void setNetTDSToBeDeposited(double netTDSToBeDeposited) {
		this.netTDSToBeDeposited = netTDSToBeDeposited;
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
			Comparator<BusinessGraph> {

		@Override
		public int compare(BusinessGraph graph1, BusinessGraph graph2) {
			return graph1.netPartnerCommissionPaid < graph2.netPartnerCommissionPaid ? 1
					: (graph1.netPartnerCommissionPaid > graph2.netPartnerCommissionPaid ? -1
							: 0);
		}
	}

	public static class OrderByNetTDS implements Comparator<BusinessGraph> {

		@Override
		public int compare(BusinessGraph graph1, BusinessGraph graph2) {
			return graph1.netTDSToBeDeposited < graph2.netTDSToBeDeposited ? 1
					: (graph1.netTDSToBeDeposited > graph2.netTDSToBeDeposited ? -1
							: 0);
		}
	}

	public static class OrderByNetTax implements Comparator<BusinessGraph> {

		@Override
		public int compare(BusinessGraph graph1, BusinessGraph graph2) {
			return graph1.netTaxToBePaid < graph2.netTaxToBePaid ? 1
					: (graph1.netTaxToBePaid > graph2.netTaxToBePaid ? -1 : 0);
		}
	}

	public static class OrderByNetNPR implements Comparator<BusinessGraph> {

		@Override
		public int compare(BusinessGraph graph1, BusinessGraph graph2) {
			return graph1.netPaymentResult < graph2.netPaymentResult ? 1
					: (graph1.netPaymentResult > graph2.netPaymentResult ? -1
							: 0);
		}
	}

	public static class OrderByNetTaxable implements Comparator<BusinessGraph> {

		@Override
		public int compare(BusinessGraph graph1, BusinessGraph graph2) {
			return graph1.netTaxableSale < graph2.netTaxableSale ? 1
					: (graph1.netTaxableSale > graph2.netTaxableSale ? -1 : 0);
		}
	}
}
