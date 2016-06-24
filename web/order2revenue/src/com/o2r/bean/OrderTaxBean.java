package com.o2r.bean;

import java.util.Date;

public class OrderTaxBean {
	
	
	private int taxId;
	private String taxperiod;
	private Date taxdepositdate;
	private String tdsperiod;
	private Date tdsdepositedate;
	private double totalpositivevalue;
	private double tdsToDeduct;
	private double tdsToReturn;
	private double tdsonReturnAmt;
	private String taxCategtory;
	private double taxRate;
	private double tax;
	private double taxToReturn;
	private double netTax;
	private int sellerId;
	
	
	
	public int getTaxId() {
		return taxId;
	}
	public void setTaxId(int taxId) {
		this.taxId = taxId;
	}
	public String getTaxperiod() {
		return taxperiod;
	}
	public void setTaxperiod(String taxperiod) {
		this.taxperiod = taxperiod;
	}
	public Date getTaxdepositdate() {
		return taxdepositdate;
	}
	public void setTaxdepositdate(Date taxdepositdate) {
		this.taxdepositdate = taxdepositdate;
	}
	public String getTdsperiod() {
		return tdsperiod;
	}
	public void setTdsperiod(String tdsperiod) {
		this.tdsperiod = tdsperiod;
	}
	public Date getTdsdepositedate() {
		return tdsdepositedate;
	}
	public void setTdsdepositedate(Date tdsdepositedate) {
		this.tdsdepositedate = tdsdepositedate;
	}
	public double getTotalpositivevalue() {
		return totalpositivevalue;
	}
	public void setTotalpositivevalue(double totalpositivevalue) {
		this.totalpositivevalue = totalpositivevalue;
	}
	public double getTdsToDeduct() {
		return tdsToDeduct;
	}
	public void setTdsToDeduct(double tdsToDeduct) {
		this.tdsToDeduct = tdsToDeduct;
	}
	public double getTdsToReturn() {
		return tdsToReturn;
	}
	public void setTdsToReturn(double tdsToReturn) {
		this.tdsToReturn = tdsToReturn;
	}
	public double getTdsonReturnAmt() {
		return tdsonReturnAmt;
	}

	public void setTdsonReturnAmt(double tdsonReturnAmt) {
		this.tdsonReturnAmt = tdsonReturnAmt;
	}
	public String getTaxCategtory() {
		return taxCategtory;
	}
	public void setTaxCategtory(String taxCategtory) {
		this.taxCategtory = taxCategtory;
	}
	public double getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}
	public double getTax() {
		return tax;
	}
	public void setTax(double tax) {
		this.tax = tax;
	}
	public double getTaxToReturn() {
		return taxToReturn;
	}
	public void setTaxToReturn(double taxToReturn) {
		this.taxToReturn = taxToReturn;
	}
	public double getNetTax() {
		return netTax;
	}
	public void setNetTax(double netTax) {
		this.netTax = netTax;
	}
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}

	
}
