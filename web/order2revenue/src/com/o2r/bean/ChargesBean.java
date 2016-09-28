package com.o2r.bean;

import java.util.Comparator;

public class ChargesBean {
	
	private String chargeType;
	private String criteria;
	private long range;
	private float value;
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
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
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
	
	public static class SortByCriteriaRange implements Comparator<ChargesBean> {

		@Override
		public int compare(ChargesBean charge1, ChargesBean charge2) {
			
			if (charge1.range == charge2.range) {
				return charge1.criteria.equalsIgnoreCase("upto") ? -1
						: 1;
			} else {
				return charge1.range > charge2.range ? 1
						: (charge1.range < charge2.range ? -1
								: 0);
			}
		}
	}
	
	public static class SortByCriteria implements Comparator<ChargesBean> {

		@Override
		public int compare(ChargesBean charge1, ChargesBean charge2) {
			
			if (charge1.criteria.equalsIgnoreCase(charge2.criteria)) {
				return charge1.range > charge2.range ? 1
						: (charge1.range < charge2.range ? -1
								: 0);
			} else {
				return charge1.criteria.equalsIgnoreCase("upto") ? -1
						: 1;
			}
		}
	}
}
