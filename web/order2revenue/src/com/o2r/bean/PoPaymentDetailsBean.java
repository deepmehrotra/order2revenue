package com.o2r.bean;

public class PoPaymentDetailsBean {

	private String paymentDetail;
	private double debits;
	private double payments;
	private double manualCharges;
	private double eoss;
	private double gatepass;
	private double closingBal;
	
	public double getDebits() {
		return debits;
	}
	public void setDebits(double debits) {
		this.debits = debits;
	}
	public double getPayments() {
		return payments;
	}
	public void setPayments(double payments) {
		this.payments = payments;
	}
	public double getManualCharges() {
		return manualCharges;
	}
	public void setManualCharges(double manualCharges) {
		this.manualCharges = manualCharges;
	}
	public double getEoss() {
		return eoss;
	}
	public void setEoss(double eoss) {
		this.eoss = eoss;
	}
	public double getClosingBal() {
		return closingBal;
	}
	public void setClosingBal(double closingBal) {
		this.closingBal = closingBal;
	}
	public String getPaymentDetail() {
		return paymentDetail;
	}
	public void setPaymentDetail(String paymentDetail) {
		this.paymentDetail = paymentDetail;
	}
	public double getGatepass() {
		return gatepass;
	}
	public void setGatepass(double gatepass) {
		this.gatepass = gatepass;
	}

}
