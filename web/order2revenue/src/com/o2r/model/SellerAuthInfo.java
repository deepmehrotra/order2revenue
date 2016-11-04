package com.o2r.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name= "SellerAuthInfo")
public class SellerAuthInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2387232358586796458L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private long id;
	@Column
	private String sellerId;
	@Column
	private String mwsAuthToken;
	@Column
	private String accessKey;
	@Column
	private String secretKey;
	@Column
	private int marketPlaceId;
	@Column
	private String serviceUrl;
	@Column
	private int status;
	@Column
	private String pcName;
	
	public long getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public String getMwsAuthToken() {
		return mwsAuthToken;
	}
	public void setMwsAuthToken(String mwsAuthToken) {
		this.mwsAuthToken = mwsAuthToken;
	}
	public String getAccessKey() {
		return accessKey;
	}
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public int getMarketPlaceId() {
		return marketPlaceId;
	}
	public void setMarketPlaceId(int marketPlaceId) {
		this.marketPlaceId = marketPlaceId;
	}
	public String getServiceUrl() {
		return serviceUrl;
	}
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getPcName() {
		return pcName;
	}
	public void setPcName(String pcName) {
		this.pcName = pcName;
	}

}
