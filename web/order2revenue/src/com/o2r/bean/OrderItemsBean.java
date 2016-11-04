package com.o2r.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity  
@Table(name= "OrderItemFlipkart")
public class OrderItemsBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4050481495110877007L;
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	
	@JsonIgnore
	private long id;
	
	private long orderItemId;
	private String orderId;
	private String status;
	private boolean hold;
	private Date orderDate;
	private Date dispatchAfterDate;
	private Date dispatchByDate;
	private Date updatedAt;
	private int sla;
	private int quantity;
	private String title;
	private String listingId;
	private String fsn;
	private String sku;
	private double price;
	private double shippingFee;
	private String shippingPincode;
	private PriceComponentsBean priceComponents2;
	private List<String> stateDocuments;
	private List<Object> subItems;
	private String paymentType;
	private String sellerId;
	private FlipkartCustomerBean customerBean;
	
	public OrderItemsBean() {
	}

	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	@OneToOne(cascade = CascadeType.ALL,targetEntity =PriceComponentsBean.class)
	public PriceComponentsBean getPriceComponents() {
		return priceComponents2;
	}

	public void setPriceComponents(PriceComponentsBean priceComponents) {
		this.priceComponents2 = priceComponents;
	}

	
	public long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(long orderItemId) {
		this.orderItemId = orderItemId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isHold() {
		return hold;
	}

	public void setHold(boolean hold) {
		this.hold = hold;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getDispatchAfterDate() {
		return dispatchAfterDate;
	}

	public void setDispatchAfterDate(Date dispatchAfterDate) {
		this.dispatchAfterDate = dispatchAfterDate;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public int getSla() {
		return sla;
	}

	public void setSla(int sla) {
		this.sla = sla;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Transient
	public List<String> getStateDocuments() {
		return stateDocuments;
	}

	public void setStateDocuments(List<String> stateDocuments) {
		this.stateDocuments = stateDocuments;
	}

	

	public String getListingId() {
		return listingId;
	}

	public void setListingId(String listingId) {
		this.listingId = listingId;
	}

	public String getFsn() {
		return fsn;
	}

	public void setFsn(String fsn) {
		this.fsn = fsn;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getShippingFee() {
		return shippingFee;
	}

	public void setShippingFee(double shippingFee) {
		this.shippingFee = shippingFee;
	}

	public String getShippingPincode() {
		return shippingPincode;
	}

	public void setShippingPincode(String shippingPincode) {
		this.shippingPincode = shippingPincode;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Date getDispatchByDate() {
		return dispatchByDate;
	}

	public void setDispatchByDate(Date dispatchByDate) {
		this.dispatchByDate = dispatchByDate;
	}

	@Transient
	public List<Object> getSubItems() {
		return subItems;
	}

	public void setSubItems(List<Object> subItems) {
		this.subItems = subItems;
	}

	
	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	
	@OneToOne(cascade = CascadeType.ALL,targetEntity=FlipkartCustomerBean.class)
	public FlipkartCustomerBean getCustomerBean() {
		return customerBean;
	}

	public void setCustomerBean(FlipkartCustomerBean customerBean) {
		this.customerBean = customerBean;
	}
}
