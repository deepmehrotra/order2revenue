package com.o2r.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.o2r.model.Category;


public class TaxCategoryBean {
	
	private int taxCatId;
	private String taxCatName;
	private String taxCatDescription;
	private String partner;
	private Date uploadDate;
	private float taxPercent;
	private String taxCatType;
	private String productCategoryStr;
	private List<Category> productCategoryCST = new ArrayList<Category>();
	private List<Category> productCategoryLST = new ArrayList<Category>();
	public int getTaxCatId() {
		return taxCatId;
	}
	public void setTaxCatId(int taxCatId) {
		this.taxCatId = taxCatId;
	}
	public String getTaxCatName() {
		return taxCatName;
	}
	public void setTaxCatName(String taxCatName) {
		this.taxCatName = taxCatName;
	}
	public String getTaxCatDescription() {
		return taxCatDescription;
	}
	public void setTaxCatDescription(String taxCatDescription) {
		this.taxCatDescription = taxCatDescription;
	}
	public String getPartner() {
		return partner;
	}
	public void setPartner(String partner) {
		this.partner = partner;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	public float getTaxPercent() {
		return taxPercent;
	}
	public void setTaxPercent(float taxPercent) {
		this.taxPercent = taxPercent;
	}
	public String getTaxCatType() {
		return taxCatType;
	}
	public void setTaxCatType(String taxCatType) {
		this.taxCatType = taxCatType;
	}
	public List<Category> getProductCategoryCST() {
		return productCategoryCST;
	}
	public void setProductCategoryCST(List<Category> productCategoryCST) {
		this.productCategoryCST = productCategoryCST;
	}
	public List<Category> getProductCategoryLST() {
		return productCategoryLST;
	}
	public void setProductCategoryLST(List<Category> productCategoryLST) {
		this.productCategoryLST = productCategoryLST;
	}
	public String getProductCategoryStr() {
		return productCategoryStr;
	}
	public void setProductCategoryStr(String productCategoryStr) {
		this.productCategoryStr = productCategoryStr;
	}
	

}
