package com.o2r.bean;

import java.util.List;

public class MwsSellerInfoBean {

	private String accessKey;
	
	private String secretKey;
	
	private String sellerId;
	
	private List<String> marketplaceIds;
	
	private String mwsAuthToken;

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

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public List<String> getMarketplaceIds() {
		return marketplaceIds;
	}

	public void setMarketplaceIds(List<String> marketplaceIds) {
		this.marketplaceIds = marketplaceIds;
	}

	public String getMwsAuthToken() {
		return mwsAuthToken;
	}

	public void setMwsAuthToken(String mwsAuthToken) {
		this.mwsAuthToken = mwsAuthToken;
	}
	
}
