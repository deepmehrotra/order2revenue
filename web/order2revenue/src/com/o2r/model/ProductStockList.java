package com.o2r.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class ProductStockList implements Comparable<ProductStockList> {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private int stockId;
	@Column
	private Date createdDate;
	@Column
	private long stockAvailable;
	@Column
	private int updatedate;
	@Column
	private int year;
	@Column
	private int month;
	@Column
	private double price;
	public int getStockId() {
		return stockId;
	}
	public void setStockId(int stockId) {
		this.stockId = stockId;
	}
	
	public long getStockAvailable() {
		return stockAvailable;
	}
	public void setStockAvailable(long stockAvailable) {
		this.stockAvailable = stockAvailable;
	}

	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(int updatedate) {
		this.updatedate = updatedate;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	@Override
	public int compareTo(ProductStockList o) {
		// TODO Auto-generated method stub
		return o.getCreatedDate().compareTo(this.createdDate);
		
	}
	

}
