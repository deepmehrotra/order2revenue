/**
 * @Author  Kapil Kumar
 **/
 
package com.o2r.bean;

import java.util.Date;

public class SellerAccountBean {
	
	private int selaccId;
	private int sellerId;
	private int planId;
	private long orderBucket;
	private Date lastTransaction;
	private int planTransactionId;
	private boolean accountStatus;
	private Date ativationDate;
	private Date lastLogin;
	private long totalOrderProcessed;
	private double totalAmountPaidToO2r;
	public int getSelaccId() {
		return selaccId;
	}
	public void setSelaccId(int selaccId) {
		this.selaccId = selaccId;
	}
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public int getPlanId() {
		return planId;
	}
	public void setPlanId(int planId) {
		this.planId = planId;
	}
	public long getOrderBucket() {
		return orderBucket;
	}
	public void setOrderBucket(long orderBucket) {
		this.orderBucket = orderBucket;
	}
	public Date getLastTransaction() {
		return lastTransaction;
	}
	public void setLastTransaction(Date lastTransaction) {
		this.lastTransaction = lastTransaction;
	}
	public int getPlanTransactionId() {
		return planTransactionId;
	}
	public void setPlanTransactionId(int planTransactionId) {
		this.planTransactionId = planTransactionId;
	}
	public boolean isAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(boolean accountStatus) {
		this.accountStatus = accountStatus;
	}
	public Date getAtivationDate() {
		return ativationDate;
	}
	public void setAtivationDate(Date ativationDate) {
		this.ativationDate = ativationDate;
	}
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	public long getTotalOrderProcessed() {
		return totalOrderProcessed;
	}
	public void setTotalOrderProcessed(long totalOrderProcessed) {
		this.totalOrderProcessed = totalOrderProcessed;
	}
	public double getTotalAmountPaidToO2r() {
		return totalAmountPaidToO2r;
	}
	public void setTotalAmountPaidToO2r(double totalAmountPaidToO2r) {
		this.totalAmountPaidToO2r = totalAmountPaidToO2r;
	}
}
