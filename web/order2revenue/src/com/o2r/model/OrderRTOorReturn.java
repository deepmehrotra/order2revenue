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
@Table(name = "OrderReturn")
public class OrderRTOorReturn {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int returnId;
	@Column
	private String returnOrRTOreason;
	@Column
	private Date returnDate;
	@Column
	private double returnOrRTOCharges;
	@Column
	private int returnorrtoQty;
	@Column
	private String returnOrRTOId;
	@Column
	private Date returnUploadDate;

	@Column(name = "estimateddeduction")
	private double returnOrRTOChargestoBeDeducted;
	@Column
	private String returnOrRTOstatus;
	@Column
	private String type;
	@Column
	private String returnCategory;
	@Column
	private String cancelType;
	
	@Column
	private double netNR;
	@Column
	private double taxPOAmt;
	@Column
	private double netPR;
	@Column
	private double grossProfit;
	@Column
	private String inventoryType;
	@Column
	private int badReturnQty;
	
	@OneToMany(mappedBy="consolidatedReturn", cascade=CascadeType.ALL)  
	private List<GatePass> gatepasses =new ArrayList<GatePass>();

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

	public int getReturnId() {
		return returnId;
	}

	public void setReturnId(int returnId) {
		this.returnId = returnId;
	}

	@Override
	public String toString() {
		return "OrderRTOorReturn [returnId=" + returnId
				+ ", returnOrRTOreason=" + returnOrRTOreason + ", returnDate="
				+ returnDate + ", returnOrRTOCharges=" + returnOrRTOCharges
				+ ", returnorrtoQty=" + returnorrtoQty + ", returnOrRTOId="
				+ returnOrRTOId + ", returnUploadDate=" + returnUploadDate
				+ "]";
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

	public List<GatePass> getGatepasses() {
		return gatepasses;
	}

	public void setGatepasses(List<GatePass> gatepasses) {
		this.gatepasses = gatepasses;
	}

	public String getInventoryType() {
		return inventoryType;
	}

	public void setInventoryType(String inventoryType) {
		this.inventoryType = inventoryType;
	}

	public int getBadReturnQty() {
		return badReturnQty;
	}

	public void setBadReturnQty(int badReturnQty) {
		this.badReturnQty = badReturnQty;
	}

}
