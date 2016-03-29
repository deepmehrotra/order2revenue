/**
 * @author Kapil Halewale
 */
package com.o2r.bean;

import java.util.Date;

public class AccountTransactionBean {
	private  int accTransId;
	private Date transactionDate;
	private double transactionAmount;
	private long currentOrderCount;
	private String invoiceId;
	private String status;
	private String transactionId;
	
	
	public int getAccTransId() {
		return accTransId;
	}
	public void setAccTransId(int accTransId) {
		this.accTransId = accTransId;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public double getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public long getCurrentOrderCount() {
		return currentOrderCount;
	}
	public void setCurrentOrderCount(long currentOrderCount) {
		this.currentOrderCount = currentOrderCount;
	}
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
}
