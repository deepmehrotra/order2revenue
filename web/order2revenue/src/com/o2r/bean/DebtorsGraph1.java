package com.o2r.bean;

import java.util.Comparator;

public class DebtorsGraph1 {
	private String partner;
	private String category;
	private double netPaymentResult;
	private double actionablePD;
	private int actionableNetQty;
	private double upcomingPD;
	private int upcomingNetQty;
	
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

	public double getActionablePD() {
		return actionablePD;
	}

	public void setActionablePD(double actionablePD) {
		this.actionablePD = actionablePD;
	}

	public int getActionableNetQty() {
		return actionableNetQty;
	}

	public void setActionableNetQty(int actionableNetQty) {
		this.actionableNetQty = actionableNetQty;
	}

	public double getUpcomingPD() {
		return upcomingPD;
	}

	public void setUpcomingPD(double upcomingPD) {
		this.upcomingPD = upcomingPD;
	}

	public int getUpcomingNetQty() {
		return upcomingNetQty;
	}

	public void setUpcomingNetQty(int upcomingNetQty) {
		this.upcomingNetQty = upcomingNetQty;
	}



	public static class OrderByNPR implements Comparator<DebtorsGraph1> {

		@Override
		public int compare(DebtorsGraph1 graph1, DebtorsGraph1 graph2) {
			return graph1.netPaymentResult < graph2.netPaymentResult ? 1
					: (graph1.netPaymentResult > graph2.netPaymentResult ? -1 : 0);
		}
	}

	public static class OrderByAC implements Comparator<DebtorsGraph1> {

		@Override
		public int compare(DebtorsGraph1 graph1, DebtorsGraph1 graph2) {
			return graph1.actionablePD < graph2.actionablePD ? 1
					: (graph1.actionablePD > graph2.actionablePD ? -1 : 0);
		}
	}
	
	public static class OrderByUP implements Comparator<DebtorsGraph1> {

		@Override
		public int compare(DebtorsGraph1 graph1, DebtorsGraph1 graph2) {
			return graph1.upcomingPD < graph2.upcomingPD ? 1
					: (graph1.upcomingPD > graph2.upcomingPD ? -1 : 0);
		}
	}
}
