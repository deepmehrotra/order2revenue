package com.o2r.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PaymentUpload")
public class PaymentUpload {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int uploadId;
	@Column
	private String uploadDesc;
	@Column
	private Date uploadDate;
	@Column
	private double totalpositivevalue;
	@Column
	private double totalnegativevalue;
	@Column
	private double netRecievedAmount;
	@Column
	private String uploadStatus;
	
	@OneToMany(mappedBy = "paymentUpload", cascade = CascadeType.ALL)
	private List<PaymentUpload_Order> orderList = new ArrayList<>();
	
	public int getUploadId() {
		return uploadId;
	}

	public void setUploadId(int uploadId) {
		this.uploadId = uploadId;
	}

	public String getUploadDesc() {
		return uploadDesc;
	}

	public void setUploadDesc(String uploadDesc) {
		this.uploadDesc = uploadDesc;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public double getTotalpositivevalue() {
		return totalpositivevalue;
	}

	public void setTotalpositivevalue(double totalpositivevalue) {
		this.totalpositivevalue = totalpositivevalue;
	}

	public double getNetRecievedAmount() {
		return netRecievedAmount;
	}

	public void setNetRecievedAmount(double netRecievedAmount) {
		this.netRecievedAmount = netRecievedAmount;
	}

	public String getUploadStatus() {
		return uploadStatus;
	}

	public void setUploadStatus(String uploadStatus) {
		this.uploadStatus = uploadStatus;
	}

	public double getTotalnegativevalue() {
		return totalnegativevalue;
	}

	public void setTotalnegativevalue(double totalnegativevalue) {
		this.totalnegativevalue = totalnegativevalue;
	}
	
	public List<PaymentUpload_Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<PaymentUpload_Order> orderList) {
		this.orderList = orderList;
	}	
}


