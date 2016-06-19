package com.o2r.bean;

import java.util.Comparator;

public class ChannelMCNPR {
	private String partner;
	private double baseNPR;
	private String paymentType;
	private double positiveAmount;
	private double negativeAmount;

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public double getBaseNPR() {
		return baseNPR;
	}

	public void setBaseNPR(double baseNPR) {
		this.baseNPR = baseNPR;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public double getPositiveAmount() {
		return positiveAmount;
	}

	public void setPositiveAmount(double positiveAmount) {
		this.positiveAmount = positiveAmount;
	}

	public double getNegativeAmount() {
		return negativeAmount;
	}

	public void setNegativeAmount(double negativeAmount) {
		this.negativeAmount = negativeAmount;
	}

	public static class OrderByPartner implements Comparator<ChannelMCNPR> {

		@Override
		public int compare(ChannelMCNPR graph1, ChannelMCNPR graph2) {
			return graph1.partner.compareTo(graph2.partner);
		}
	}
}
