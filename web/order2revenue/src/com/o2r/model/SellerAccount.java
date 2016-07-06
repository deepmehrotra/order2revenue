package com.o2r.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class SellerAccount {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private int selaccId;
	@Column
	private int sellerId;// rem
	@Column
	private int planId;// rem
	@Column
	private long orderBucket;
	@Column
	private Date lastTransaction;
	@Column
	private int planTransactionId;//rem
	@Column
	private boolean accountStatus;
	@Column
	private Date ativationDate;
	@Column
	private Date lastLogin;
	@Column
	private long totalOrderProcessed;
	@Column
	private double totalAmountPaidToO2r;
	
	// OnetoMany mapping from SellerAccount to AccountTransaction
	@OneToMany(cascade=CascadeType.ALL)
	private List<AccountTransaction> accountTransactions=new ArrayList<AccountTransaction>();

	public List<AccountTransaction> getAccountTransactions() {
		return accountTransactions;
	}
	public void setAccountTransactions(List<AccountTransaction> accountTransactions) {
		this.accountTransactions = accountTransactions;
	}
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
