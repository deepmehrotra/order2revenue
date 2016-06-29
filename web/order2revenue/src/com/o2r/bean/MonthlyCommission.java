package com.o2r.bean;

public class MonthlyCommission {
	private String key;
	private String partner;
	private double grossCommission;
	private double returnCommission;
	private double additionalCharges;
	private double netCommission;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public double getGrossCommission() {
		return grossCommission;
	}

	public void setGrossCommission(double grossCommission) {
		this.grossCommission = grossCommission;
	}

	public double getReturnCommission() {
		return returnCommission;
	}

	public void setReturnCommission(double returnCommission) {
		this.returnCommission = returnCommission;
	}

	public double getAdditionalCharges() {
		return additionalCharges;
	}

	public void setAdditionalCharges(double additionalCharges) {
		this.additionalCharges = additionalCharges;
	}

	public double getNetCommission() {
		return netCommission;
	}

	public void setNetCommission(double netCommission) {
		this.netCommission = netCommission;
	}

}
