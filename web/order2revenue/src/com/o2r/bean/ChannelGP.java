package com.o2r.bean;

import java.util.Comparator;

public class ChannelGP {
	private String key;
	private double settledGP;
	private double actionableGP;
	private double inProcessGP;
	private double totalGP;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public double getSettledGP() {
		return settledGP;
	}

	public void setSettledGP(double settledGP) {
		this.settledGP = settledGP;
	}

	public double getActionableGP() {
		return actionableGP;
	}

	public void setActionableGP(double actionableGP) {
		this.actionableGP = actionableGP;
	}

	public double getInProcessGP() {
		return inProcessGP;
	}

	public void setInProcessGP(double inProcessGP) {
		this.inProcessGP = inProcessGP;
	}

	public double getTotalGP() {
		return totalGP;
	}

	public void setTotalGP(double totalGP) {
		this.totalGP = totalGP;
	}

	public static class OrderByTotalGP implements Comparator<ChannelGP> {

		@Override
		public int compare(ChannelGP graph1, ChannelGP graph2) {
			return graph1.totalGP < graph2.totalGP ? 1
					: (graph1.totalGP > graph2.totalGP ? -1 : 0);
		}
	}
}
