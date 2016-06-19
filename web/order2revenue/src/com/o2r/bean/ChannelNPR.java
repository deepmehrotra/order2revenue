package com.o2r.bean;

import java.util.Comparator;

public class ChannelNPR {
	private String partner;
	private String category;
	private double prepaidNPR;
	private double codNPR;
	private double b2bNPR;
	private double netNPR;

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getPrepaidNPR() {
		return prepaidNPR;
	}

	public void setPrepaidNPR(double prepaidNPR) {
		this.prepaidNPR = prepaidNPR;
	}

	public double getCodNPR() {
		return codNPR;
	}

	public void setCodNPR(double codNPR) {
		this.codNPR = codNPR;
	}

	public double getB2bNPR() {
		return b2bNPR;
	}

	public void setB2bNPR(double b2bNPR) {
		this.b2bNPR = b2bNPR;
	}

	public double getNetNPR() {
		return netNPR;
	}

	public void setNetNPR(double netNPR) {
		this.netNPR = netNPR;
	}
	
	public static class OrderByNPR implements Comparator<ChannelNPR> {

		@Override
		public int compare(ChannelNPR graph1, ChannelNPR graph2) {
			return graph1.netNPR < graph2.netNPR ? 1
					: (graph1.netNPR > graph2.netNPR ? -1 : 0);
		}
	}

}
