package com.o2r.bean;

import java.util.List;

public class NetProfitabilityST {
	private String key;
	private double openStock;
	private double closeStock;
	private double npr;
	private double netRate;
	private List<Double> catExpenses;
	private double totalExpenses;
	private double netProfit;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public double getOpenStock() {
		return openStock;
	}

	public void setOpenStock(double openStock) {
		this.openStock = openStock;
	}

	public double getCloseStock() {
		return closeStock;
	}

	public void setCloseStock(double closeStock) {
		this.closeStock = closeStock;
	}

	public double getNpr() {
		return npr;
	}

	public void setNpr(double npr) {
		this.npr = npr;
	}

	public double getNetRate() {
		return netRate;
	}

	public void setNetRate(double netRate) {
		this.netRate = netRate;
	}

	public List<Double> getCatExpenses() {
		return catExpenses;
	}

	public void setCatExpenses(List<Double> catExpenses) {
		this.catExpenses = catExpenses;
	}

	public double getTotalExpenses() {
		return totalExpenses;
	}

	public void setTotalExpenses(double totalExpenses) {
		this.totalExpenses = totalExpenses;
	}

	public double getNetProfit() {
		return netProfit;
	}

	public void setNetProfit(double netProfit) {
		this.netProfit = netProfit;
	}

}
