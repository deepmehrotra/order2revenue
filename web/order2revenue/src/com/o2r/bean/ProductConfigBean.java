package com.o2r.bean;

import com.o2r.model.Product;

public class ProductConfigBean {
	
	private int productConfigId;
	private int productId;
	private String channelName;
	private String productName;
	private String productSkuCode;
	private String channelSkuRef;
	private String vendorSkuRef;
	private float commision;
	private float taxSp;
	private float taxPo;
	private double commisionAmt;
	private double taxSpAmt;
	private double taxPoAmt;
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
	public String getVendorSkuRef() {
		return vendorSkuRef;
	}
	public void setVendorSkuRef(String vendorSkuRef) {
		this.vendorSkuRef = vendorSkuRef;
	}
}
