package com.o2r.bean;

public class DataConfig {
	private Double serviceTax;
	private String xlsPath;
	public String uploadReportPath;

	public Double getServiceTax() {
		return serviceTax;
	}

	public void setServiceTax(Double serviceTax) {
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
}
