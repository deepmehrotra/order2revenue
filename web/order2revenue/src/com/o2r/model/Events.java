package com.o2r.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

public class Events {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private int eventId;
	@Column	
	private String eventName;
	@Column
	private String channelName;
	@Column
	private Date startDate;
	@Column
	private Date endDate;
	@Column
	private String productCategories;
	@Column
	private String nrType;
	@Column
	private String returnCharges;
	@Column
	private Date createdDate;
	
	@OneToOne(cascade=CascadeType.ALL)
	private NRnReturnConfig nrnReturnConfig;
	
	
	private Partner partner;
	
	
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
}
