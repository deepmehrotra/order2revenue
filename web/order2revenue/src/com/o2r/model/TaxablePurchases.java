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
@Table(name = "TaxablePurchases")
public class TaxablePurchases {

	public TaxablePurchases(long taxPurchaseId, String taxCategory, float taxRate,
			Date purchaseDate, String particular, double basicPrice,
			double taxAmount) {

		super();
		this.taxPurchaseId=taxPurchaseId;
		this.taxCategory = taxCategory;
		this.taxRate = taxRate;
		this.purchaseDate = purchaseDate;
		this.particular = particular;
		this.basicPrice = basicPrice;
		this.taxAmount = taxAmount;
	}

	public TaxablePurchases() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue
	private long taxPurchaseId;
	@Column
	private String taxCategory;
	@Column
	private float taxRate;
	@Column
	private Date purchaseDate;
	@Column
	private String particular;
	@Column
	private double basicPrice;
	@Column
	private double taxAmount;
	@Column
	private Date createdDate;
	@Column
	private double totalAmount;
	@ManyToOne(fetch = FetchType.LAZY)
	private Seller seller;
	
	public Seller getSeller() {
		return seller;
	}
	public void setSeller(Seller seller) {
		this.seller = seller;
	}	

	public long getTaxPurchaseId() {
		return taxPurchaseId;
	}

	public void setTaxPurchaseId(long taxPurchaseId) {
		this.taxPurchaseId = taxPurchaseId;
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

	public double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

}
