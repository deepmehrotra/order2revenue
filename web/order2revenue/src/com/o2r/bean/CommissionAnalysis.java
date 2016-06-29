package com.o2r.bean;

import java.util.Comparator;

public class CommissionAnalysis {
	private String key;
	private double sellingFee;
	private double pcc;
	private double fixedFee;
	private double shippingCharges;
	private double serviceTax;
	private double taxSP;
	private double additionalCharges;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public double getSellingFee() {
		return sellingFee;
	}
	public void setSellingFee(double sellingFee) {
		this.sellingFee = sellingFee;
	}
	public double getPcc() {
		return pcc;
	}
	public void setPcc(double pcc) {
		this.pcc = pcc;
	}
	public double getFixedFee() {
		return fixedFee;
	}
	public void setFixedFee(double fixedFee) {
		this.fixedFee = fixedFee;
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
	public double getAdditionalCharges() {
		return additionalCharges;
	}
	public void setAdditionalCharges(double additionalCharges) {
		this.additionalCharges = additionalCharges;
	}

}
