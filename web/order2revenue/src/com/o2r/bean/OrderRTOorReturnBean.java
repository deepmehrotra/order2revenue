package com.o2r.bean;

import java.util.Date;

import javax.persistence.Column;

public class OrderRTOorReturnBean {
	 int returnId;
	private String returnOrRTOreason;
	private Date returnDate;
	private double returnOrRTOCharges;
	private int returnorrtoQty;
	private String returnOrRTOId;
	private Date returnUploadDate;
	private double returnOrRTOChargestoBeDeducted;
	private String returnOrRTOstatus;
	private String type;
	private String returnCategory;
	private String cancelType;
	private int sellerId;
	
	private double netNR;
	private double taxPOAmt;
	private double netPR;
	private double grossProfit;

	public int getReturnId() {
		return returnId;
	}
	public void setReturnId(int returnId) {
		this.returnId = returnId;
	}
	public String getReturnOrRTOreason() {
		return returnOrRTOreason;
	}
	public void setReturnOrRTOreason(String returnOrRTOreason) {
		this.returnOrRTOreason = returnOrRTOreason;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public double getReturnOrRTOCharges() {
		return returnOrRTOCharges;
	}
	public void setReturnOrRTOCharges(double returnOrRTOCharges) {
		this.returnOrRTOCharges = returnOrRTOCharges;
	}
	public int getReturnorrtoQty() {
		return returnorrtoQty;
	}
	public void setReturnorrtoQty(int returnorrtoQty) {
		this.returnorrtoQty = returnorrtoQty;
	}
	public String getReturnOrRTOId() {
		return returnOrRTOId;
	}
	public void setReturnOrRTOId(String returnOrRTOId) {
		this.returnOrRTOId = returnOrRTOId;
	}
	public Date getReturnUploadDate() {
		return returnUploadDate;
	}
	public void setReturnUploadDate(Date returnUploadDate) {
		this.returnUploadDate = returnUploadDate;
	}
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public double getReturnOrRTOChargestoBeDeducted() {
		return returnOrRTOChargestoBeDeducted;
	}
	public void setReturnOrRTOChargestoBeDeducted(
			double returnOrRTOChargestoBeDeducted) {
		this.returnOrRTOChargestoBeDeducted = returnOrRTOChargestoBeDeducted;
	}
	public String getReturnOrRTOstatus() {
		return returnOrRTOstatus;
	}
	public void setReturnOrRTOstatus(String returnOrRTOstatus) {
		this.returnOrRTOstatus = returnOrRTOstatus;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getReturnCategory() {
		return returnCategory;
	}
	public void setReturnCategory(String returnCategory) {
		this.returnCategory = returnCategory;
	}
	public String getCancelType() {
		return cancelType;
	}
	public void setCancelType(String cancelType) {
		this.cancelType = cancelType;
	}
	public double getNetNR() {
		return netNR;
	}
	public void setNetNR(double netNR) {
		this.netNR = netNR;
	}
	public double getTaxPOAmt() {
		return taxPOAmt;
	}
	public void setTaxPOAmt(double taxPOAmt) {
		this.taxPOAmt = taxPOAmt;
	}
	public double getNetPR() {
		return netPR;
	}
	public void setNetPR(double netPR) {
		this.netPR = netPR;
	}
	public double getGrossProfit() {
		return grossProfit;
	}
	public void setGrossProfit(double grossProfit) {
		this.grossProfit = grossProfit;
	}



}
