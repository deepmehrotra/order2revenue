package com.o2r.bean;

import java.util.Date;
import java.util.List;

import com.o2r.model.PaymentVariables;

public class OrderPaymentBean {


	private int orderpayId;
	private String channelOrderId;
	private String paymentdesc;
	private Date dateofPayment;
	private Date uploadDate;
	private double negativeAmount;
	private double positiveAmount;
	private double actualrecived2;
	private double netPaymentResult;
	private double paymentDifference;
	private int paymentUploadId;
	private int sellerId;
	private String paymentCycle;
	private Date paymentCycleStart;
	private Date paymentCycleEnd;
	private String paymentFileName;
	private List<PaymentVariables> paymentVar;
	public int getOrderpayId() {
		return orderpayId;
	}
	public void setOrderpayId(int orderpayId) {
		this.orderpayId = orderpayId;
	}
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
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public double getActualrecived2() {
		return actualrecived2;
	}
	public void setActualrecived2(double actualrecived2) {
		this.actualrecived2 = actualrecived2;
	}

	public String getPaymentCycle() {
		return paymentCycle;
	}
	public void setPaymentCycle(String paymentCycle) {
		this.paymentCycle = paymentCycle;
	}

	public double getNegativeAmount() {
		return negativeAmount;
	}
	public void setNegativeAmount(double negativeAmount) {
		this.negativeAmount = negativeAmount;
	}
	public double getNetPaymentResult() {
		return netPaymentResult;
	}
	public void setNetPaymentResult(double netPaymentResult) {
		this.netPaymentResult = netPaymentResult;
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
	public String getPaymentFileName() {
		return paymentFileName;
	}
	public void setPaymentFileName(String paymentFileName) {
		this.paymentFileName = paymentFileName;
	}
	public double getPositiveAmount() {
		return positiveAmount;
	}
	public void setPositiveAmount(double positiveAmount) {
		this.positiveAmount = positiveAmount;
	}
	public String getChannelOrderId() {
		return channelOrderId;
	}
	public void setChannelOrderId(String channelOrderId) {
		this.channelOrderId = channelOrderId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((channelOrderId == null) ? 0 : channelOrderId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderPaymentBean other = (OrderPaymentBean) obj;
		if (channelOrderId == null) {
			if (other.channelOrderId != null)
				return false;
		} else if (!channelOrderId.equals(other.channelOrderId))
			return false;
		return true;
	}
	public List<PaymentVariables> getPaymentVar() {
		return paymentVar;
	}
	public void setPaymentVar(List<PaymentVariables> paymentVar) {
		this.paymentVar = paymentVar;
	}

}
