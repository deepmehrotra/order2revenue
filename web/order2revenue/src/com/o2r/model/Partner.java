package com.o2r.model;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table
public class Partner {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private long pcId;
	@Column
	private String pcName;
	@Column
	private String pcDesc;
	@Column
	private String pcLogoUrl;
	@Column
	private String paymentType;
	@Column
	private String paymentCategory;
	@Column
	private boolean isshippeddatecalc;
	@Column
	private int noofdaysfromshippeddate;
	@Column
	private boolean isshippeddatecalcPost;
	@Column
	private int noofdaysfromshippeddatePost;
	@Column
	private boolean isshippeddatecalcOthers;
	@Column
	private int noofdaysfromshippeddateOthers;
	@Column
	private int startcycleday;
	@Column
	private int paycycleduration;
	@Column
	private int paydaysfromstartday;
	@Column
	private int maxRTOAcceptance;
	@Column
	private int maxReturnAcceptance;
	@Column
	private String taxcategory;
	@Column
	private float taxrate;
	@Column
	private boolean tdsApplicable;
	@Column
	private boolean paycyclefromshipordel;
	@Column
	private int monthlypaydate;
	
	@OneToMany(mappedBy="partner", cascade=CascadeType.ALL)
	private List<Events> events;
	
	@OneToOne(cascade=CascadeType.ALL)
	private NRnReturnConfig nrnReturnConfig;
	
	

	@OneToMany(cascade=CascadeType.ALL)
	private List<Order> orders = new ArrayList<Order>();
	
	
	@OneToOne(cascade=CascadeType.ALL)
	private SellerAuthInfo sellerAuthInfo;
	
	
	public SellerAuthInfo getSellerAuthInfo() {
		return sellerAuthInfo;
	}
	public void setSellerAuthInfo(SellerAuthInfo sellerAuthInfo) {
		this.sellerAuthInfo = sellerAuthInfo;
	}	

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
	public List<Order> getOrders() {
		return orders;
	}
	public void setOrders(List<Order> orders) {
		this.orders = orders;
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
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
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
	public NRnReturnConfig getNrnReturnConfig() {
		return nrnReturnConfig;
	}
	public void setNrnReturnConfig(NRnReturnConfig nrnReturnConfig) {
		this.nrnReturnConfig = nrnReturnConfig;
	}
	public List<Events> getEvents() {
		return events;
	}
	public void setEvents(List<Events> events) {
		this.events = events;
	}
	public String getPaymentCategory() {
		return paymentCategory;
	}
	public void setPaymentCategory(String paymentCategory) {
		this.paymentCategory = paymentCategory;
	}
	public boolean isIsshippeddatecalcPost() {
		return isshippeddatecalcPost;
	}
	public void setIsshippeddatecalcPost(boolean isshippeddatecalcPost) {
		this.isshippeddatecalcPost = isshippeddatecalcPost;
	}
	public int getNoofdaysfromshippeddatePost() {
		return noofdaysfromshippeddatePost;
	}
	public void setNoofdaysfromshippeddatePost(int noofdaysfromshippeddatePost) {
		this.noofdaysfromshippeddatePost = noofdaysfromshippeddatePost;
	}
	public boolean isIsshippeddatecalcOthers() {
		return isshippeddatecalcOthers;
	}
	public void setIsshippeddatecalcOthers(boolean isshippeddatecalcOthers) {
		this.isshippeddatecalcOthers = isshippeddatecalcOthers;
	}
	public int getNoofdaysfromshippeddateOthers() {
		return noofdaysfromshippeddateOthers;
	}
	public void setNoofdaysfromshippeddateOthers(
			int noofdaysfromshippeddateOthers) {
		this.noofdaysfromshippeddateOthers = noofdaysfromshippeddateOthers;
	}


}
