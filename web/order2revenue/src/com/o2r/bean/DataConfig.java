package com.o2r.bean;

public class DataConfig {
	private double serviceTax;
	private String xlsPath;
	public String uploadReportPath;
	private String payuMerchantKey;
	private String payuSalt;
	private String payubaseurl;
	private String successurl;
	private String failurl;
	private int tds;

	public double getServiceTax() {
		return serviceTax;
	}

	public void setServiceTax(double serviceTax) {
		this.serviceTax = serviceTax;
	}

	public String getXlsPath() {
		return xlsPath;
	}

	public void setXlsPath(String xlspath) {
		this.xlsPath = xlspath;
	}

	public String getUploadReportPath() {
		return uploadReportPath;
	}

	public void setUploadReportPath(String uploadReportPath) {
		this.uploadReportPath = uploadReportPath;
	}

	public String getPayuMerchantKey() {
		return payuMerchantKey;
	}

	public void setPayuMerchantKey(String payuMerchantKey) {
		this.payuMerchantKey = payuMerchantKey;
	}

	public String getPayuSalt() {
		return payuSalt;
	}

	public void setPayuSalt(String payuSalt) {
		this.payuSalt = payuSalt;
	}

	public String getPayubaseurl() {
		return payubaseurl;
	}

	public void setPayubaseurl(String payubaseurl) {
		this.payubaseurl = payubaseurl;
	}

	public String getSuccessurl() {
		return successurl;
	}

	public void setSuccessurl(String successurl) {
		this.successurl = successurl;
	}

	public String getFailurl() {
		return failurl;
	}

	public void setFailurl(String failurl) {
		this.failurl = failurl;
	}

	public int getTds() {
		return tds;
	}

	public void setTds(int tds) {
		this.tds = tds;
	}
}
