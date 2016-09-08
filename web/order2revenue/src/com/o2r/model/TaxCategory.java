package com.o2r.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

@Entity
@Table
public class TaxCategory {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private int taxCatId;
	@Column
	private String taxCatName;
	@Column
	private String taxCatDescription;
	@Column
	private String partner;
	@Column
	private Date uploadDate;
	@Column
	private float taxPercent;	
	@Column
	private String taxCatType;
	
	@OneToMany(mappedBy="CST", cascade=CascadeType.ALL)
	private List<Category> productCategoryCST = new ArrayList<Category>();
	
	@OneToMany(mappedBy="LST", cascade=CascadeType.ALL)
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
	
}
