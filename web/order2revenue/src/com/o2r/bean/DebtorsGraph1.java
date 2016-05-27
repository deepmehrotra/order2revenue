package com.o2r.bean;

import java.util.Comparator;

public class DebtorsGraph1 {
	private String partner;
	private String category;
	private double netPaymentResult;
	private double paymentDifference;
	private int netPayDiffOrderQty;
	private double upcomingPaymentNR;

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
	
	public int getNetPayDiffOrderQty() {
		return netPayDiffOrderQty;
	}

	public void setNetPayDiffOrderQty(int netPayDiffOrderQty) {
		this.netPayDiffOrderQty = netPayDiffOrderQty;
	}

	public double getUpcomingPaymentNR() {
		return upcomingPaymentNR;
	}

	public void setUpcomingPaymentNR(double upcomingPaymentNR) {
		this.upcomingPaymentNR = upcomingPaymentNR;
	}

	public static class OrderByNPR implements Comparator<DebtorsGraph1> {

		@Override
		public int compare(DebtorsGraph1 graph1, DebtorsGraph1 graph2) {
			return graph1.netPaymentResult < graph2.netPaymentResult ? 1
					: (graph1.netPaymentResult > graph2.netPaymentResult ? -1 : 0);
		}
	}

	public static class OrderByNPDQY implements Comparator<DebtorsGraph1> {

		@Override
		public int compare(DebtorsGraph1 graph1, DebtorsGraph1 graph2) {
			return graph1.netPayDiffOrderQty < graph2.netPayDiffOrderQty ? 1
					: (graph1.netPayDiffOrderQty > graph2.netPayDiffOrderQty ? -1 : 0);
		}
	}
	
	public static class OrderByUPNR implements Comparator<DebtorsGraph1> {

		@Override
		public int compare(DebtorsGraph1 graph1, DebtorsGraph1 graph2) {
			return graph1.upcomingPaymentNR < graph2.upcomingPaymentNR ? 1
					: (graph1.upcomingPaymentNR > graph2.upcomingPaymentNR ? -1 : 0);
		}
	}
}
