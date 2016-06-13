package com.o2r.bean;

public class ChannelMCNPR {
	private String partner;
	private double baseNPR;
	private String paymentType;
	private double manualCharges;

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

	public double getManualCharges() {
		return manualCharges;
	}

	public void setManualCharges(double manualCharges) {
		this.manualCharges = manualCharges;
	}

}
