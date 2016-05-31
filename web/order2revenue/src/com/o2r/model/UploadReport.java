package com.o2r.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "upload_log")
public class UploadReport {

	@Id
	@GeneratedValue
	@Column
	private int id;
	@Column
	private String fileType;
	@Column
	private String description;
	@Column
	private String Status;
	@Column
	private String filePath;
	@Column
	private float timeTaken;
	@Column
	private Date uploadDate;
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Seller seller;

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

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public float getTimeTaken() {
		return timeTaken;
	}

	public void setTimeTaken(float timeTaken) {
		this.timeTaken = timeTaken;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
