package com.o2r.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "NRnReturnCharges")
public class NRnReturnCharges {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int nrchargeId;
	@Column
	private String chargeName;
	@Column
	private float chargeAmount;

	public int getNrchargeId() {
		return nrchargeId;
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

}
