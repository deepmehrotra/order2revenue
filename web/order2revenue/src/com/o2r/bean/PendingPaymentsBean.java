package com.o2r.bean;

import java.util.Date;

public class PendingPaymentsBean {

	private String channelOrderId;
	private String pcName;
	private Date paymentDate;
	private double recievable;
	private double balance;
	private String status;
	public String getChannelOrderId() {
		return channelOrderId;
	}
	public void setChannelOrderId(String channelOrderId) {
		this.channelOrderId = channelOrderId;
	}
	public String getPcName() {
		return pcName;
	}
	public void setPcName(String pcName) {
		this.pcName = pcName;
	}
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getRecievable() {
		return recievable;
	}
	public void setRecievable(double recievable) {
		this.recievable = recievable;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}

}
