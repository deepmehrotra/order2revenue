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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

@Entity
@Table
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private int productId;
	@Column
	private String productName;
	@Column
	private float productPrice;
	@Column
	private long quantity;
	@Column
	private long threholdLimit;
	@Column
	private String channelSKU;
	@Column
	private Date productDate;
	@Column
	private String productSkuCode;
	@Column
	private String categoryName;
	@OneToOne(cascade=CascadeType.PERSIST)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private Category category;
	@OneToMany(cascade=CascadeType.ALL)
	private List<ProductStockList> closingStocks = new ArrayList<ProductStockList>();
	@ManyToOne(fetch = FetchType.LAZY)
	private Seller seller;
	@Column
	private float deadWeight;
	@Column
	private float length;
	@Column
	private float height;
	@Column
	private float breadth;
	@Column
	private float volume;
	@Column
	private float volWeight;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<ProductConfig> productConfig;

	public Seller getSeller() {
		return seller;
	}
	public void setSeller(Seller seller) {
		this.seller = seller;
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
	public Date getProductDate() {
		return productDate;
	}
	public void setProductDate(Date productDate) {
		this.productDate = productDate;
	}
	public String getProductSkuCode() {
		return productSkuCode;
	}
	public void setProductSkuCode(String productSkuCode) {
		this.productSkuCode = productSkuCode;
	}
	public float getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(float productPrice) {
		this.productPrice = productPrice;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	public long getThreholdLimit() {
		return threholdLimit;
	}
	public void setThreholdLimit(long threholdLimit) {
		this.threholdLimit = threholdLimit;
	}
	public String getChannelSKU() {
		return channelSKU;
	}
	public void setChannelSKU(String channelSKU) {
		this.channelSKU = channelSKU;
	}
	public List<ProductStockList> getClosingStocks() {
		return closingStocks;
	}
	public void setClosingStocks(List<ProductStockList> closingStocks) {
		this.closingStocks = closingStocks;
	}
	public float getDeadWeight() {
		return deadWeight;
	}
	public void setDeadWeight(float deadWeight) {
		this.deadWeight = deadWeight;
	}
	public float getLength() {
		return length;
	}
	public void setLength(float length) {
		this.length = length;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public float getBreadth() {
		return breadth;
	}
	public void setBreadth(float breadth) {
		this.breadth = breadth;
	}
	public float getVolume() {
		return volume;
	}
	public void setVolume(float volume) {
		this.volume = volume;
	}
	public float getVolWeight() {
		return volWeight;
	}
	public void setVolWeight(float volWeight) {
		this.volWeight = volWeight;
	}
	public List<ProductConfig> getProductConfig() {
		return productConfig;
	}
	public void setProductConfig(List<ProductConfig> productConfig) {
		this.productConfig = productConfig;
	}



}
