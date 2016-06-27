package com.o2r.bean;

public class ChannelNR {
	private String key;
	private String partner;
	private double settledNR;
	private double actionableNR;
	private double inProcessNR;
	private double totalNR;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public double getSettledNR() {
		return settledNR;
	}

	public void setSettledNR(double settledNR) {
		this.settledNR = settledNR;
	}

	public double getActionableNR() {
		return actionableNR;
	}

	public void setActionableNR(double actionableNR) {
		this.actionableNR = actionableNR;
	}

	public double getInProcessNR() {
		return inProcessNR;
	}

	public void setInProcessNR(double inProcessNR) {
		this.inProcessNR = inProcessNR;
	}

	public double getTotalNR() {
		return totalNR;
	}

	public void setTotalNR(double totalNR) {
		this.totalNR = totalNR;
	}

}
