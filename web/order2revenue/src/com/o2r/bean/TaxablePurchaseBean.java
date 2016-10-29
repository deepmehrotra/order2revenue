package com.o2r.bean;



import java.util.Date;


public class TaxablePurchaseBean {

	private int TaxPurchaseId;
	
	private String taxCategory;	
	private float taxRate;
	private Date purchaseDate;
	private Date createdDate;
	private String particular;
	private double basicPrice;
	private double totalAmount;
	private double taxAmount;	
	private int sellerId;
	
	
	public int getTaxPurchaseId() {
		return TaxPurchaseId;
	}
	public void setTaxPurchaseId(int taxPurchaseId) {
		TaxPurchaseId = taxPurchaseId;
	}
	
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public String getTaxCategory() {
		return taxCategory;
	}
	public void setTaxCategory(String taxCategory) {
		this.taxCategory = taxCategory;
	}
	public float getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(float taxRate) {
		this.taxRate = taxRate;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getParticular() {
		return particular;
	}
	public void setParticular(String particular) {
		this.particular = particular;
	}
	public double getBasicPrice() {
		return basicPrice;
	}
	public void setBasicPrice(double basicPrice) {
		this.basicPrice = basicPrice;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public double getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}
	
	
	
	
}
