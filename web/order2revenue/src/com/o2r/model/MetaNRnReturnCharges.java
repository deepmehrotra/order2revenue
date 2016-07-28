package com.o2r.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MetaNRnReturnCharges")
public class MetaNRnReturnCharges {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long nrchargeId;
	@Column
	private String chargeName;
	@Column
	private float chargeAmount;
	@Column
	private String criteria;
	@Column
	private long criteriaRange = 0;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private MetaNRnReturnConfig config;

	public long getNrchargeId() {
		return nrchargeId;
	}

	public void setNrchargeId(long nrchargeId) {
		this.nrchargeId = nrchargeId;
	}

	public void setNrchargeId(int nrchargeId) {
		this.nrchargeId = nrchargeId;
	}

	public String getChargeName() {
		return chargeName;
	}

	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
	}

	public float getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(float chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	@Override
	public String toString() {
		return "NRnReturnCharges [nrchargeId=" + nrchargeId + ", chargeName="
				+ chargeName + ", chargeAmount=" + chargeAmount + "]";
	}

	public MetaNRnReturnConfig getConfig() {
		return config;
	}

	public void setConfig(MetaNRnReturnConfig config) {
		this.config = config;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public long getCriteriaRange() {
		return criteriaRange;
	}

	public void setCriteriaRange(long criteriaRange) {
		this.criteriaRange = criteriaRange;
	}

}
