package com.o2r.bean;

public class ChargesBean {
	
	private String chargeType;
	private String criteria;
	private long range;
	private double value;
	private double localValue;
	private double zonalValue;
	private double nationalValue;
	private double metroValue;
	
	public String getChargeType() {
		return chargeType;
	}
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	public String getCriteria() {
		return criteria;
	}
	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}
	public long getRange() {
		return range;
	}
	public void setRange(long range) {
		this.range = range;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public double getLocalValue() {
		return localValue;
	}
	public void setLocalValue(double localValue) {
		this.localValue = localValue;
	}
	public double getZonalValue() {
		return zonalValue;
	}
	public void setZonalValue(double zonalValue) {
		this.zonalValue = zonalValue;
	}
	public double getNationalValue() {
		return nationalValue;
	}
	public void setNationalValue(double nationalValue) {
		this.nationalValue = nationalValue;
	}
	public double getMetroValue() {
		return metroValue;
	}
	public void setMetroValue(double metroValue) {
		this.metroValue = metroValue;
	}
}
