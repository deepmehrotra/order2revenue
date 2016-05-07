package com.o2r.model;

import javax.persistence.CascadeType;
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
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private int productConfigId;
	@Column
	private int productId;
	@Column
	private String productName;
	@Column
	private String productSKuCode;
	@Column
	private String channelSKuRef;
	@Column
	private float commision;
	@Column
	private float taxSp;
	@Column
	private float taxPo;
	@Column
	private float discount;
	@Column
	private float eossDiscount;
	@Column
	private double mrp;
	@Column
	private double sp;
	@Column
	private double productPrice;
	@Column
	private double suggestedPOPrice;
	@Column
	private double eossDiscountValue;
	@Column
	private double grossNR;
	
	@ManyToOne(cascade=CascadeType.ALL)
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
	public String getProductSKuCode() {
		return productSKuCode;
	}
	public void setProductSKuCode(String productSKuCode) {
		this.productSKuCode = productSKuCode;
	}
	public String getChannelSKuRef() {
		return channelSKuRef;
	}
	public void setChannelSKuRef(String channelSKuRef) {
		this.channelSKuRef = channelSKuRef;
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
}
