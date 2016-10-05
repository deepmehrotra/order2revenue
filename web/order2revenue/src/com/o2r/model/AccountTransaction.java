/**
 * @author Kapil Halewale
 */
package com.o2r.model;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class AccountTransaction {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private  int accTransId;
	@Column
	private Date transactionDate;
	@Column
	private double transactionAmount;
	@Column
	private long currentOrderCount;
	@Column
	private String invoiceId;
	@Column
	private String status;
	@Column
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
	@Override
	public String toString() {
		return "AccountTransaction [accTransId=" + accTransId
				+ ", transactionDate=" + transactionDate
				+ ", transactionAmount=" + transactionAmount
				+ ", currentOrderCount=" + currentOrderCount + ", invoiceId="
				+ invoiceId + ", status=" + status + ", transactionId="
				+ transactionId + "]";
	}
	
}
