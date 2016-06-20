package com.o2r.bean;

import java.util.Comparator;
import java.util.List;

public class ChannelCatNPR {
	private String partner;
	private List<Double> netNPR;
	private Double totalNPR;

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
	
	public Double getTotalNPR() {
		return totalNPR;
	}

	public void setTotalNPR(Double totalNPR) {
		this.totalNPR = totalNPR;
	}

	public static class OrderByNPR implements Comparator<ChannelCatNPR> {

		@Override
		public int compare(ChannelCatNPR graph1, ChannelCatNPR graph2) {
			return graph1.totalNPR < graph2.totalNPR ? 1
					: (graph1.totalNPR > graph2.totalNPR ? -1 : 0);
		}
	}
}
