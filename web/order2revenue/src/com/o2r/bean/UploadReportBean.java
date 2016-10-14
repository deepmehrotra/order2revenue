package com.o2r.bean;

import java.util.Date;

public class UploadReportBean {
	
	private Integer id;
	private String fileType;
	private String fileName;
	private String description;
	private String status;
	private String filePath;
	private int sellerId;
	private String sellerName;
	private double timeTaken;
	private long noOfSuccess;
	private long noOfErrors;
	private Date uploadDate;
	private String uploadedAt;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public double getTimeTaken() {
		return timeTaken;
	}
	public void setTimeTaken(double timeTaken) {
		this.timeTaken = timeTaken;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	public String getUploadedAt() {
		return uploadedAt;
	}
	public void setUploadedAt(String uploadedAt) {
		this.uploadedAt = uploadedAt;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getNoOfSuccess() {
		return noOfSuccess;
	}
	public void setNoOfSuccess(long noOfSuccess) {
		this.noOfSuccess = noOfSuccess;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public long getNoOfErrors() {
		return noOfErrors;
	}
	public void setNoOfErrors(long noOfErrors) {
		this.noOfErrors = noOfErrors;
	}
}
