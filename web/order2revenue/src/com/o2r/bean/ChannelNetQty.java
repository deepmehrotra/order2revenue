package com.o2r.bean;

import java.util.Comparator;

public class ChannelNetQty {
	private String key;
	private int settledQty;
	private int actionableQty;
	private int inProcessQty;
	private int totalQty;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getSettledQty() {
		return settledQty;
	}

	public void setSettledQty(int settledQty) {
		this.settledQty = settledQty;
	}

	public int getActionableQty() {
		return actionableQty;
	}

	public void setActionableQty(int actionableQty) {
		this.actionableQty = actionableQty;
	}

	public int getInProcessQty() {
		return inProcessQty;
	}

	public void setInProcessQty(int inProcessQty) {
		this.inProcessQty = inProcessQty;
	}

	public int getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(int totalQty) {
		this.totalQty = totalQty;
	}

	public static class OrderByTotalQty implements Comparator<ChannelNetQty> {

		@Override
		public int compare(ChannelNetQty graph1, ChannelNetQty graph2) {
			return graph1.totalQty < graph2.totalQty ? 1
					: (graph1.totalQty > graph2.totalQty ? -1 : 0);
		}
	}
}
