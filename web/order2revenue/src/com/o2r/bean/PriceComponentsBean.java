package com.o2r.bean;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;  
import javax.persistence.Table; 

@Entity  
@Table(name= "PriceComponentFlipkart")
public class PriceComponentsBean implements Serializable {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8860481583342105394L;
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	
	
	private long id;
	private double sellingPrice;
	private double customerPrice;
	private double shippingCharge;
	private double totalPrice;
	
	
	@JsonCreator
	public PriceComponentsBean() {
		
	}
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getSellingPrice() {
		return sellingPrice;
	}
	public void setSellingPrice(double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}
	public double getCustomerPrice() {
		return customerPrice;
	}
	public void setCustomerPrice(double customerPrice) {
		this.customerPrice = customerPrice;
	}
	public double getShippingCharge() {
		return shippingCharge;
	}
	public void setShippingCharge(double shippingCharge) {
		this.shippingCharge = shippingCharge;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

}
