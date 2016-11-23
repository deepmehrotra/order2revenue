package com.o2r.bean;


public class AuthInfoBean {

    private String sellerid;
    private String mwsauthtoken;
    private String accesskey;
    private String secretkey;
    private String marketplaceid;
    private String serviceurl;
    private Integer status;
	public String getSellerid() {
		return sellerid;
	}
	public void setSellerid(String sellerid) {
		this.sellerid = sellerid;
	}
	public String getMwsauthtoken() {
		return mwsauthtoken;
	}
	public void setMwsauthtoken(String mwsauthtoken) {
		this.mwsauthtoken = mwsauthtoken;
	}
	public String getAccesskey() {
		return accesskey;
	}
	public void setAccesskey(String accesskey) {
		this.accesskey = accesskey;
	}
	public String getSecretkey() {
		return secretkey;
	}
	public void setSecretkey(String secretkey) {
		this.secretkey = secretkey;
	}
	public String getMarketplaceid() {
		return marketplaceid;
	}
	public void setMarketplaceid(String marketplaceid) {
		this.marketplaceid = marketplaceid;
	}
	public String getServiceurl() {
		return serviceurl;
	}
	public void setServiceurl(String serviceurl) {
		this.serviceurl = serviceurl;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
