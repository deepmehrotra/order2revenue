package com.o2r.model;

import java.util.Comparator;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.o2r.bean.ChannelReportDetails;

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
	private String fileName;
	@Column
	private String description;
	@Column
	private String Status;
	@Column
	private String filePath;
	@Column
	private float timeTaken;
	@Column
	private long noOfErrors;
	@Column(nullable = false)
	private long noOfSuccess;
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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

	public long getNoOfErrors() {
		return noOfErrors;
	}

	public void setNoOfErrors(long noOfErrors) {
		this.noOfErrors = noOfErrors;
	}

	public long getNoOfSuccess() {
		return noOfSuccess;
	}

	public void setNoOfSuccess(long noOfSuccess) {
		this.noOfSuccess = noOfSuccess;
	}
	
	public static class OrderByDate implements Comparator<UploadReport> {
		@Override
		public int compare(UploadReport report1, UploadReport report2) {
			if (report1.uploadDate == null || report2.uploadDate == null)
				return -1;
			return report1.uploadDate.before(report2.uploadDate) ? 1
					: (report1.uploadDate.after(report2.uploadDate) ? -1 : 0);
		}
	}
}
