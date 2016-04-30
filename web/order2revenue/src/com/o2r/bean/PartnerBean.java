package com.o2r.bean;


import java.util.ArrayList;
import java.util.List;

import com.o2r.model.NRnReturnConfig;
import com.o2r.model.Order;


public class PartnerBean {


	private long pcId;
	private String pcName;
	private String pcDesc;
	private String pcLogoUrl;
	private String paymentType;
	private boolean isshippeddatecalc;
	private int noofdaysfromshippeddate;
	private int noofdaysfromdeliverydate;
	private int startcycleday;
	private int paycycleduration;
	private int paydaysfromstartday;
	private int maxRTOAcceptance;
	private int maxReturnAcceptance;
	private String taxcategory;
	private float taxrate;
	private boolean tdsApplicable;
	private boolean paycyclefromshipordel;
	private int monthlypaydate;
	private List<Order> orders = new ArrayList<Order>();
	private NRnReturnConfig nrnReturnConfig;
	public long getPcId() {
		return pcId;
	}
	public void setPcId(long pcId) {
		this.pcId = pcId;
	}
	public String getPcName() {
		return pcName;
	}
	public void setPcName(String pcName) {
		this.pcName = pcName;
	}
	public String getPcDesc() {
		return pcDesc;
	}
	public void setPcDesc(String pcDesc) {
		this.pcDesc = pcDesc;
	}
	public String getPcLogoUrl() {
		return pcLogoUrl;
	}
	public void setPcLogoUrl(String pcLogoUrl) {
		this.pcLogoUrl = pcLogoUrl;
	}

	public boolean isIsshippeddatecalc() {
		return isshippeddatecalc;
	}
	public void setIsshippeddatecalc(boolean isshippeddatecalc) {
		this.isshippeddatecalc = isshippeddatecalc;
	}
	public int getNoofdaysfromshippeddate() {
		return noofdaysfromshippeddate;
	}
	public void setNoofdaysfromshippeddate(int noofdaysfromshippeddate) {
		this.noofdaysfromshippeddate = noofdaysfromshippeddate;
	}

	public int getStartcycleday() {
		return startcycleday;
	}
	public void setStartcycleday(int startcycleday) {
		this.startcycleday = startcycleday;
	}
	public int getPaycycleduration() {
		return paycycleduration;
	}
	public void setPaycycleduration(int paycycleduration) {
		this.paycycleduration = paycycleduration;
	}
	public int getPaydaysfromstartday() {
		return paydaysfromstartday;
	}
	public void setPaydaysfromstartday(int paydaysfromstartday) {
		this.paydaysfromstartday = paydaysfromstartday;
	}
	public int getMaxRTOAcceptance() {
		return maxRTOAcceptance;
	}
	public void setMaxRTOAcceptance(int maxRTOAcceptance) {
		this.maxRTOAcceptance = maxRTOAcceptance;
	}
	public int getMaxReturnAcceptance() {
		return maxReturnAcceptance;
	}
	public void setMaxReturnAcceptance(int maxReturnAcceptance) {
		this.maxReturnAcceptance = maxReturnAcceptance;
	}
	public String getTaxcategory() {
		return taxcategory;
	}
	public void setTaxcategory(String taxcategory) {
		this.taxcategory = taxcategory;
	}
	public float getTaxrate() {
		return taxrate;
	}
	public void setTaxrate(float taxrate) {
		this.taxrate = taxrate;
	}
	public boolean isTdsApplicable() {
		return tdsApplicable;
	}
	public void setTdsApplicable(boolean tdsApplicable) {
		this.tdsApplicable = tdsApplicable;
	}
	public PartnerBean(String pcName, String pcDesc, String pcLogoUrl) {
		super();
		this.pcName = pcName;
		this.pcDesc = pcDesc;
		this.pcLogoUrl = pcLogoUrl;
	}
	public PartnerBean() {
		super();
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public List<Order> getOrders() {
		return orders;
	}
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	public boolean isPaycyclefromshipordel() {
		return paycyclefromshipordel;
	}
	public void setPaycyclefromshipordel(boolean paycyclefromshipordel) {
		this.paycyclefromshipordel = paycyclefromshipordel;
	}
	public int getMonthlypaydate() {
		return monthlypaydate;
	}
	public void setMonthlypaydate(int monthlypaydate) {
		this.monthlypaydate = monthlypaydate;
	}
	@Override
	public String toString() {
		return "PartnerBean [pcId=" + pcId + ", pcName=" + pcName + ", pcDesc="
				+ pcDesc + ", pcLogoUrl=" + pcLogoUrl + ", paymentType="
				+ paymentType + ", isshippeddatecalc=" + isshippeddatecalc
				+ ", noofdaysfromshippeddate=" + noofdaysfromshippeddate
				+ ", startcycleday=" + startcycleday + ", paycycleduration="
				+ paycycleduration + ", paydaysfromstartday="
				+ paydaysfromstartday + ", maxRTOAcceptance="
				+ maxRTOAcceptance + ", maxReturnAcceptance="
				+ maxReturnAcceptance + ", taxcategory=" + taxcategory
				+ ", taxrate=" + taxrate + ", tdsApplicable=" + tdsApplicable
				+ ", paycyclefromshipordel=" + paycyclefromshipordel
				+ ", monthlypaydate=" + monthlypaydate + ", orders=" + orders
				+ "]";
	}
	public int getNoofdaysfromdeliverydate() {
		return noofdaysfromdeliverydate;
	}
	public void setNoofdaysfromdeliverydate(int noofdaysfromdeliverydate) {
		this.noofdaysfromdeliverydate = noofdaysfromdeliverydate;
	}
	public NRnReturnConfig getNrnReturnConfig() {
		return nrnReturnConfig;
	}
	public void setNrnReturnConfig(NRnReturnConfig nrnReturnConfig) {
		this.nrnReturnConfig = nrnReturnConfig;
	}


}
