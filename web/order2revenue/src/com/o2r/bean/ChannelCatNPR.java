package com.o2r.bean;

import java.util.List;

public class ChannelCatNPR {
	private String partner;
	private List<Double> netNPR;

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public List<Double> getNetNPR() {
		return netNPR;
	}

	public void setNetNPR(List<Double> netNPR) {
		this.netNPR = netNPR;
	}
}
