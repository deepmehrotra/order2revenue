package com.o2r.bean;

import java.util.Date;

public class PartnerDetailsBean {
	private String pcName;
	private long totalNoOfOrders;
	private Date firstOrderDate;
	private Date lastOrderDate;
	private long totalNoOfPayments;
	private Date firstPaymentDate;
	private Date lastPaymentDate;
	private long totalNoOfReturns;
	private Date firstReturnDate;
	private Date lastReturnDate;
	
	public String getPcName() {
		return pcName;
	}
	public void setPcName(String pcName) {
		this.pcName = pcName;
	}
	public long getTotalNoOfOrders() {
		return totalNoOfOrders;
	}
	public void setTotalNoOfOrders(long totalNoOfOrders) {
		this.totalNoOfOrders = totalNoOfOrders;
	}
	public Date getFirstOrderDate() {
		return firstOrderDate;
	}
	public void setFirstOrderDate(Date firstOrderDate) {
		this.firstOrderDate = firstOrderDate;
	}
	public Date getLastOrderDate() {
		return lastOrderDate;
	}
	public void setLastOrderDate(Date lastOrderDate) {
		this.lastOrderDate = lastOrderDate;
	}
	public long getTotalNoOfPayments() {
		return totalNoOfPayments;
	}
	public void setTotalNoOfPayments(long totalNoOfPayments) {
		this.totalNoOfPayments = totalNoOfPayments;
	}
	public Date getFirstPaymentDate() {
		return firstPaymentDate;
	}
	public void setFirstPaymentDate(Date firstPaymentDate) {
		this.firstPaymentDate = firstPaymentDate;
	}
	public Date getLastPaymentDate() {
		return lastPaymentDate;
	}
	public void setLastPaymentDate(Date lastPaymentDate) {
		this.lastPaymentDate = lastPaymentDate;
	}
	public long getTotalNoOfReturns() {
		return totalNoOfReturns;
	}
	public void setTotalNoOfReturns(long totalNoOfReturns) {
		this.totalNoOfReturns = totalNoOfReturns;
	}
	public Date getFirstReturnDate() {
		return firstReturnDate;
	}
	public void setFirstReturnDate(Date firstReturnDate) {
		this.firstReturnDate = firstReturnDate;
	}
	public Date getLastReturnDate() {
		return lastReturnDate;
	}
	public void setLastReturnDate(Date lastReturnDate) {
		this.lastReturnDate = lastReturnDate;
	}
}
