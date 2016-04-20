package com.o2r.bean;

import java.util.Date;

import com.o2r.model.NRnReturnConfig;
import com.o2r.model.Partner;

public class EventsBean {
	
	private int sellerId;	
	private int eventId;
	private String eventName;
	private String channelName;
	private Date startDate;
	private Date endDate;
	private String productCategories;
	private String nrType;
	private String returnCharges;
	private NRnReturnConfig nrnReturnConfig;
	private Partner partner;
	private Date createdDate;
	private int netSalesQuantity;
	private double netSalesAmount;
	
	
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public int getEventId() {
		return eventId;
	}
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getProductCategories() {
		return productCategories;
	}
	public void setProductCategories(String productCategories) {
		this.productCategories = productCategories;
	}
	public String getNrType() {
		return nrType;
	}
	public void setNrType(String nrType) {
		this.nrType = nrType;
	}
	public String getReturnCharges() {
		return returnCharges;
	}
	public void setReturnCharges(String returnCharges) {
		this.returnCharges = returnCharges;
	}
	public NRnReturnConfig getNrnReturnConfig() {
		return nrnReturnConfig;
	}
	public void setNrnReturnConfig(NRnReturnConfig nrnReturnConfig) {
		this.nrnReturnConfig = nrnReturnConfig;
	}
	public Partner getPartner() {
		return partner;
	}
	public void setPartner(Partner partner) {
		this.partner = partner;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public int getNetSalesQuantity() {
		return netSalesQuantity;
	}
	public void setNetSalesQuantity(int netSalesQuantity) {
		this.netSalesQuantity = netSalesQuantity;
	}
	public double getNetSalesAmount() {
		return netSalesAmount;
	}
	public void setNetSalesAmount(double netSalesAmount) {
		this.netSalesAmount = netSalesAmount;
	}
	

}
