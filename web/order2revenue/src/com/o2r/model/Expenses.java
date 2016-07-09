package com.o2r.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table
public class Expenses {

	
	public Expenses() {
		
	}
	
	public Expenses(String expenseName, String expenseDescription,
			String expenseCatName, Date createdOn, Date expenseDate,
			double amount, int sellerId) {
		super();
		this.expenseName = expenseName;
		this.expenseDescription = expenseDescription;
		this.expenseCatName = expenseCatName;
		this.createdOn = createdOn;
		this.expenseDate = expenseDate;
		this.amount = amount;
		this.sellerId = sellerId;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private int expenseId;
	@Column
	private String expenseName;
	@Column
	private String expenseDescription;
	@Column
	private String expenseCatName;
	@Column
	private Date createdOn;
	@Column
	private Date expenseDate;
	@Column
	private double amount;
	@Column(name="expenditure")
	private String expenditureByperson;
	@Column
	private String paidTo;
	@Column
	private int sellerId;
	@ManyToOne(fetch=FetchType.EAGER)
	private ExpenseCategory expenseCategory;
	public int getExpenseId() {
		return expenseId;
	}
	public void setExpenseId(int expenseId) {
		this.expenseId = expenseId;
	}
	public String getExpenseName() {
		return expenseName;
	}
	public void setExpenseName(String expenseName) {
		this.expenseName = expenseName;
	}
	public String getExpenseDescription() {
		return expenseDescription;
	}
	public void setExpenseDescription(String expenseDescription) {
		this.expenseDescription = expenseDescription;
	}
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public ExpenseCategory getExpenseCategory() {
		return expenseCategory;
	}
	public void setExpenseCategory(ExpenseCategory expenseCategory) {
		this.expenseCategory = expenseCategory;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getExpenseCatName() {
		return expenseCatName;
	}
	public void setExpenseCatName(String expenseCatName) {
		this.expenseCatName = expenseCatName;
	}
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public String getExpenditureByperson() {
		return expenditureByperson;
	}
	public void setExpenditureByperson(String expenditureByperson) {
		this.expenditureByperson = expenditureByperson;
	}
	public String getPaidTo() {
		return paidTo;
	}
	public void setPaidTo(String paidTo) {
		this.paidTo = paidTo;
	}
	public Date getExpenseDate() {
		return expenseDate;
	}
	public void setExpenseDate(Date expenseDate) {
		this.expenseDate = expenseDate;
	}
	
	
	
}
