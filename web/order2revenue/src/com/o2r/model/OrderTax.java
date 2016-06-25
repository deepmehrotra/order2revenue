package com.o2r.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OrderTax")
public class OrderTax {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int taxId;
	@Column
	private String taxperiod;
	@Column
	private Date taxdepositdate;
	@Column
	private String tdsperiod;
	@Column
	private Date tdsdepositedate;
	@Column
	private double totalpositivevalue;
	@Column
	private double tdsToDeduct;
	@Column
	private double tdsToReturn;
	@Column
	private double tdsonReturnAmt;
	@Column
	private String taxCategtory;
	@Column
	private double taxRate;
	@Column
	private double tax;
	@Column
	private double taxToReturn;
	@Column
	private double netTax;
	@Column
	private double taxSP;

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

	public int getTaxId() {
		return taxId;
	}

	public void setTaxId(int taxId) {
		this.taxId = taxId;
	}

	@Override
	public String toString() {
		return "OrderTax [taxId=" + taxId + ", taxperiod=" + taxperiod
				+ ", taxdepositdate=" + taxdepositdate + ", tdsperiod="
				+ tdsperiod + ", tdsdepositedate=" + tdsdepositedate
				+ ", totalpositivevalue=" + totalpositivevalue
				+ ", tdsToDeduct=" + tdsToDeduct + ", tdsToReturn="
				+ tdsToReturn + ", tdsonReturnAmt=" + tdsonReturnAmt + ", taxCategtory="
				+ taxCategtory + ", taxRate=" + taxRate + ", tax=" + tax
				+ ", taxToReturn=" + taxToReturn + ", netTax=" + netTax + "]";
	}

	public double getTdsonReturnAmt() {
		return tdsonReturnAmt;
	}

	public void setTdsonReturnAmt(double tdsonReturnAmt) {
		this.tdsonReturnAmt = tdsonReturnAmt;
	}

	public double getTaxSP() {
		return taxSP;
	}

	public void setTaxSP(double taxSP) {
		this.taxSP = taxSP;
	}

}
