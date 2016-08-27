package com.o2r.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class ProductConfig {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int productConfigId;
	@Column
	private int productId;
	@Column
	private String channelName;
	@Column
	private String productName;
	@Column
	private String productSkuCode;
	@Column
	private String channelSkuRef;
	@Column
	private String vendorSkuRef;
	@Column
	private String taxSpCategory;
	@Column
	private String taxPoCategory;
	@Column
	private float commision;
	@Column
	private float taxSp;
	@Column
	private float taxPo;
	@Column
	private double commisionAmt;
	@Column
	private double taxSpAmt;
	@Column
	private double taxPoAmt;
	@Column
	private float discount;
	@Column
	private float eossDiscount;
	@Column
	private double mrp;
	@Column
	private double sp;
	@Column
	private double pr;
	@Column
	private double productPrice;
	@Column
	private double suggestedPOPrice;
	@Column
	private double eossDiscountValue;
	@Column
	private double grossNR;

	@ManyToOne
	private Product product;

	public int getProductConfigId() {
		return productConfigId;
	}

	public void setProductConfigId(int productConfigId) {
		this.productConfigId = productConfigId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductSkuCode() {
		return productSkuCode;
	}

	public void setProductSkuCode(String productSkuCode) {
		this.productSkuCode = productSkuCode;
	}

	public String getChannelSkuRef() {
		return channelSkuRef;
	}

	public void setChannelSkuRef(String channelSkuRef) {
		this.channelSkuRef = channelSkuRef;
	}

	public float getCommision() {
		return commision;
	}

	public void setCommision(float commision) {
		this.commision = commision;
	}

	public float getTaxSp() {
		return taxSp;
	}

	public void setTaxSp(float taxSp) {
		this.taxSp = taxSp;
	}

	public float getTaxPo() {
		return taxPo;
	}

	public void setTaxPo(float taxPo) {
		this.taxPo = taxPo;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public float getEossDiscount() {
		return eossDiscount;
	}

	public void setEossDiscount(float eossDiscount) {
		this.eossDiscount = eossDiscount;
	}

	public double getMrp() {
		return mrp;
	}

	public void setMrp(double mrp) {
		this.mrp = mrp;
	}

	public double getSp() {
		return sp;
	}

	public void setSp(double sp) {
		this.sp = sp;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public double getSuggestedPOPrice() {
		return suggestedPOPrice;
	}

	public void setSuggestedPOPrice(double suggestedPOPrice) {
		this.suggestedPOPrice = suggestedPOPrice;
	}

	public double getEossDiscountValue() {
		return eossDiscountValue;
	}

	public void setEossDiscountValue(double eossDiscountValue) {
		this.eossDiscountValue = eossDiscountValue;
	}

	public double getGrossNR() {
		return grossNR;
	}

	public void setGrossNR(double grossNR) {
		this.grossNR = grossNR;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public double getCommisionAmt() {
		return commisionAmt;
	}

	public void setCommisionAmt(double commisionAmt) {
		this.commisionAmt = commisionAmt;
	}

	public double getTaxSpAmt() {
		return taxSpAmt;
	}

	public void setTaxSpAmt(double taxSpAmt) {
		this.taxSpAmt = taxSpAmt;
	}

	public double getTaxPoAmt() {
		return taxPoAmt;
	}

	public void setTaxPoAmt(double taxPoAmt) {
		this.taxPoAmt = taxPoAmt;
	}

	public double getPr() {
		return pr;
	}

	public void setPr(double pr) {
		this.pr = pr;
	}

	public String getTaxSpCategory() {
		return taxSpCategory;
	}

	public void setTaxSpCategory(String taxSpCategory) {
		this.taxSpCategory = taxSpCategory;
	}

	public String getTaxPoCategory() {
		return taxPoCategory;
	}

	public void setTaxPoCategory(String taxPoCategory) {
		this.taxPoCategory = taxPoCategory;
	}

	public String getVendorSkuRef() {
		return vendorSkuRef;
	}

	public void setVendorSkuRef(String vendorSkuRef) {
		this.vendorSkuRef = vendorSkuRef;
	}
}
