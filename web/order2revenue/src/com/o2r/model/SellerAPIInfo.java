package com.o2r.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table
public class SellerAPIInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int infoId;	
	@Column
	private String sellerName;
	@Column
	private int sellerId;
	@Column(columnDefinition = "TEXT")
	private String tokenUserKey;
	@Column
	private int pageSize;
	@Column
	private String statusId;
	@Column
	private String statusMessage;
	
	
	public int getInfoId() {
		return infoId;
	}
	public void setInfoId(int infoId) {
		this.infoId = infoId;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public String getTokenUserKey() {
		return tokenUserKey;
	}
	public void setTokenUserKey(String tokenUserKey) {
		this.tokenUserKey = tokenUserKey;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getStatusId() {
		return statusId;
	}
	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	
	
}
