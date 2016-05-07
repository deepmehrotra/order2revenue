package com.o2r.bean;

import com.o2r.model.Product;

public class ProductConfigBean {
	
	private int productConfigId;
	private int productId;
	private String productName;
	private String productSKuCode;
	private String channelSKuRef;
	private float commision;
	private float taxSp;
	private float taxPo;
	private float discount;
	private float eossDiscount;
	private double mrp;
	private double sp;
	private double productPrice;
	private double suggestedPOPrice;
	private double eossDiscountValue;
	private double grossNR;
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
