package com.o2r.bean;

public class ChannelNetQty {
	private String key;
	private Double settledQty;
	private Double actionableQty;
	private Double inProcessQty;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Double getSettledQty() {
		return settledQty;
	}

	public void setSettledQty(Double settledQty) {
		this.settledQty = settledQty;
	}

	public Double getActionableQty() {
		return actionableQty;
	}

	public void setActionableQty(Double actionableQty) {
		this.actionableQty = actionableQty;
	}

	public Double getInProcessQty() {
		return inProcessQty;
	}

	public void setInProcessQty(Double inProcessQty) {
		this.inProcessQty = inProcessQty;
	}

}
