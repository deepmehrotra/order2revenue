package com.o2r.bean;

import java.util.Comparator;

public class DetailedDashboardBean {
	
	private String criteria;
	private String month;
	private int monthNo;
	private String channel;
	private String city;
	private String sku;
	private double grossValue;
	private double returnValue;
	private double netValue;
	private double addCharges;
	private double badQtyCharges;
	private double deducted;
	
	public String getCriteria() {
		return criteria;
	}
	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public int getMonthNo() {
		return monthNo;
	}
	public void setMonthNo(int monthNo) {
		this.monthNo = monthNo;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public double getGrossValue() {
		return grossValue;
	}
	public void setGrossValue(double grossValue) {
		this.grossValue = grossValue;
	}
	public double getReturnValue() {
		return returnValue;
	}
	public void setReturnValue(double returnValue) {
		this.returnValue = returnValue;
	}
	public double getNetValue() {
		return netValue;
	}
	public void setNetValue(double netValue) {
		this.netValue = netValue;
	}
	public double getAddCharges() {
		return addCharges;
	}
	public void setAddCharges(double addCharges) {
		this.addCharges = addCharges;
	}
	public double getBadQtyCharges() {
		return badQtyCharges;
	}
	public void setBadQtyCharges(double badQtyCharges) {
		this.badQtyCharges = badQtyCharges;
	}
	public double getDeducted() {
		return deducted;
	}
	public void setDeducted(double deducted) {
		this.deducted = deducted;
	}
	
	public static class sortByGrossValue implements Comparator<DetailedDashboardBean> {
		@Override
		public int compare(DetailedDashboardBean o1, DetailedDashboardBean o2) {
			
			return o1.grossValue < o2.grossValue ? 1
					: (o1.grossValue > o2.grossValue ? -1 : 0);
		}
	}
	
	public static class sortByMonth implements Comparator<DetailedDashboardBean> {
		@Override
		public int compare(DetailedDashboardBean o1, DetailedDashboardBean o2) {
			
			return o1.monthNo < o2.monthNo ? 1
					: (o1.monthNo > o2.monthNo ? -1 : 0);
		}
	}
	
	
}
