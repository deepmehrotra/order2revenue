package com.o2r.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "OrderPay")
public class OrderPayment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int paymentId;
	@Column
	private String paymentdesc;	
	@Column
	private Date dateofPayment;
	@Column
	private Date uploadDate;
	@Column
	private double negativeAmount;
	@Column
	private double positiveAmount;
	@Column
	private double actualrecived2;
	@Column
	private double netPaymentResult;
	@Column
	private double paymentDifference;
	@Column
	private int paymentUploadId;
	@Column
	private String paymentCycle;
	@Column
	private Date paymentCycleStart;
	@Column
	private Date paymentCycleEnd;
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private List<PaymentVariables> paymentVar ;

	public String getPaymentdesc() {
		return paymentdesc;
	}

	public void setPaymentdesc(String paymentdesc) {
		this.paymentdesc = paymentdesc;
	}

	public Date getDateofPayment() {
		return dateofPayment;
	}

	public void setDateofPayment(Date dateofPayment) {
		this.dateofPayment = dateofPayment;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public int getPaymentUploadId() {
		return paymentUploadId;
	}

	public void setPaymentUploadId(int paymentUploadId) {
		this.paymentUploadId = paymentUploadId;
	}

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public String getPaymentCycle() {
		return paymentCycle;
	}

	public void setPaymentCycle(String paymentCycle) {
		this.paymentCycle = paymentCycle;
	}

	public double getActualrecived2() {
		return actualrecived2;
	}

	public void setActualrecived2(double actualrecived2) {
		this.actualrecived2 = actualrecived2;
	}

	public double getNegativeAmount() {
		return negativeAmount;
	}

	public void setNegativeAmount(double negativeAmount) {
		this.negativeAmount = negativeAmount;
	}

	public double getPositiveAmount() {
		return positiveAmount;
	}

	public void setPositiveAmount(double positiveAmount) {
		this.positiveAmount = positiveAmount;
	}

	public double getNetPaymentResult() {
		return netPaymentResult;
	}

	public void setNetPaymentResult(double netPaymentResult) {
		this.netPaymentResult = netPaymentResult;
	}

	@Override
	public String toString() {
		return "OrderPayment [paymentId=" + paymentId + ", paymentdesc="
				+ paymentdesc + ", dateofPayment=" + dateofPayment
				+ ", uploadDate=" + uploadDate + ", negativeAmount="
				+ negativeAmount + ", positiveAmount=" + positiveAmount
				+ ", actualrecived2=" + actualrecived2 + ", netPaymentResult="
				+ netPaymentResult + ", paymentUploadId=" + paymentUploadId
				+ ", paymentCycle=" + paymentCycle + "]";
	}

	public double getPaymentDifference() {
		return paymentDifference;
	}

	public void setPaymentDifference(double paymentDifference) {
		this.paymentDifference = paymentDifference;
	}

	public Date getPaymentCycleStart() {
		return paymentCycleStart;
	}

	public void setPaymentCycleStart(Date paymentCycleStart) {
		this.paymentCycleStart = paymentCycleStart;
	}

	public Date getPaymentCycleEnd() {
		return paymentCycleEnd;
	}

	public void setPaymentCycleEnd(Date paymentCycleEnd) {
		this.paymentCycleEnd = paymentCycleEnd;
	}

	public List<PaymentVariables> getPaymentVar() {
		return paymentVar;
	}

	public void setPaymentVar(List<PaymentVariables> paymentVar) {
		this.paymentVar = paymentVar;
	}
}
