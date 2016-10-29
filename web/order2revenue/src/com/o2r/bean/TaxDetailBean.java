package com.o2r.bean;

import java.util.Date;


public class TaxDetailBean {
	private int taxId;
	private String taxortds;
	private String description;
	private String status;
	private String taxortdsCycle;
	private String particular;
	private Double paidAmount;
	private Date dateOfPayment;
	private Double balanceRemaining;
	private Date uploadDate;
	private String taxCatType;
	private String taxCategory;
	
	private double vat;	
	private double cst;
	private double excise;
	private double customDuty;
	private double antiDumpingDuty;
	private double totalTax;
	
	public double getTotalTax() {
		return totalTax;
	}
	public void setTotalTax(double totalTax) {
		this.totalTax = totalTax;
	}
	public double getVat() {
		return vat;
	}
	public void setVat(double vat) {
		this.vat = vat;
	}
	public double getCst() {
		return cst;
	}
	public void setCst(double cst) {
		this.cst = cst;
	}
	public double getExcise() {
		return excise;
	}
	public void setExcise(double excise) {
		this.excise = excise;
	}
	public double getCustomDuty() {
		return customDuty;
	}
	public void setCustomDuty(double customDuty) {
		this.customDuty = customDuty;
	}
	public double getAntiDumpingDuty() {
		return antiDumpingDuty;
	}
	public void setAntiDumpingDuty(double antiDumpingDuty) {
		this.antiDumpingDuty = antiDumpingDuty;
	}
	
	
	
	
	public String getTaxCategory() {
		return taxCategory;
	}
	public void setTaxCategory(String taxCategory) {
		this.taxCategory = taxCategory;
	}
	public String getTaxCatType() {
		return taxCatType;
	}
	public void setTaxCatType(String taxCatType) {
		this.taxCatType = taxCatType;
	}
	public int getTaxId() {
		return taxId;
	}
	public void setTaxId(int taxId) {
		this.taxId = taxId;
	}
	public String getTaxortds() {
		return taxortds;
	}
	public void setTaxortds(String taxortds) {
		this.taxortds = taxortds;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTaxortdsCycle() {
		return taxortdsCycle;
	}
	public void setTaxortdsCycle(String taxortdsCycle) {
		this.taxortdsCycle = taxortdsCycle;
	}
	public String getParticular() {
		return particular;
	}
	public void setParticular(String particular) {
		this.particular = particular;
	}
	public Double getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}
	public Date getDateOfPayment() {
		return dateOfPayment;
	}
	public void setDateOfPayment(Date dateOfPayment) {
		this.dateOfPayment = dateOfPayment;
	}
	public Double getBalanceRemaining() {
		return balanceRemaining;
	}
	public void setBalanceRemaining(Double balanceRemaining) {
		this.balanceRemaining = balanceRemaining;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

}
