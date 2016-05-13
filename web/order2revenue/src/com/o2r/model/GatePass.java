package com.o2r.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "gatepass")
public class GatePass {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int gpId;
	@Column
	private String gatepassId;
	@Column
	private String poID;
	@Column
	private String invoiceID;
	@Column
	private String channelSkuRef;
	@Column
	private int quantity;
	@Column
	private double returnRate;
	@Column
	private double taxAmt;
	@Column
	private double returnCharges;
	@Column
	private double totalReturnCharges;
	@Column
	private String sellerNotes;
	@Column
	private String returnReason;
	@Column
	private Date returnDate;
	@Column
	private double netNR;
	@Column
	private double taxPOAmt;
	@Column
	private double retutnPR;
	@Column
	private double netPR;
	@Column
	private double grossProfit;
	@ManyToOne(cascade = CascadeType.ALL)
	private OrderRTOorReturn consolidatedReturn;

	public int getGpId() {
		return gpId;
	}

	public void setGpId(int gpId) {
		this.gpId = gpId;
	}

	public String getGatepassId() {
		return gatepassId;
	}

	public void setGatepassId(String gatepassId) {
		this.gatepassId = gatepassId;
	}

	public String getPoID() {
		return poID;
	}

	public void setPoID(String poID) {
		this.poID = poID;
	}

	public String getInvoiceID() {
		return invoiceID;
	}

	public void setInvoiceID(String invoiceID) {
		this.invoiceID = invoiceID;
	}

	public String getChannelSkuRef() {
		return channelSkuRef;
	}

	public void setChannelSkuRef(String channelSkuRef) {
		this.channelSkuRef = channelSkuRef;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getTaxAmt() {
		return taxAmt;
	}

	public void setTaxAmt(double taxAmt) {
		this.taxAmt = taxAmt;
	}

	public double getReturnCharges() {
		return returnCharges;
	}

	public void setReturnCharges(double returnCharges) {
		this.returnCharges = returnCharges;
	}

	public double getTotalReturnCharges() {
		return totalReturnCharges;
	}

	public void setTotalReturnCharges(double totalReturnCharges) {
		this.totalReturnCharges = totalReturnCharges;
	}

	public String getSellerNotes() {
		return sellerNotes;
	}

	public void setSellerNotes(String sellerNotes) {
		this.sellerNotes = sellerNotes;
	}

	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
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

	public double getRetutnPR() {
		return retutnPR;
	}

	public void setRetutnPR(double retutnPR) {
		this.retutnPR = retutnPR;
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

	public double getReturnRate() {
		return returnRate;
	}

	public void setReturnRate(double returnRate) {
		this.returnRate = returnRate;
	}

	/*
	 * @Override public String toString() { return "OrderRTOorReturn [returnId="
	 * + returnId + ", returnOrRTOreason=" + returnOrRTOreason + ", returnDate="
	 * + returnDate + ", returnOrRTOCharges=" + returnOrRTOCharges +
	 * ", returnorrtoQty=" + returnorrtoQty + ", returnOrRTOId=" + returnOrRTOId
	 * + ", returnUploadDate=" + returnUploadDate + "]"; }
	 */
}
