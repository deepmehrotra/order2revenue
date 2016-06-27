package com.o2r.bean;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class YearlyStockList {

	private String category;
	private String monthStr;
	private int month;
	private double openStock;
	private double openStockValuation;
	private double closeStock;
	private double closeStockValuation;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getMonthStr() {
		return monthStr;
	}

	public void setMonthStr(String monthStr) {
		this.monthStr = monthStr;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public double getOpenStock() {
		return openStock;
	}

	public void setOpenStock(double openStock) {
		this.openStock = openStock;
	}

	public double getOpenStockValuation() {
		return openStockValuation;
	}

	public void setOpenStockValuation(double openStockValuation) {
		this.openStockValuation = openStockValuation;
	}

	public double getCloseStock() {
		return closeStock;
	}

	public void setCloseStock(double closeStock) {
		this.closeStock = closeStock;
	}

	public double getCloseStockValuation() {
		return closeStockValuation;
	}

	public void setCloseStockValuation(double closeStockValuation) {
		this.closeStockValuation = closeStockValuation;
	}
	
	public static class OrderByMonthCat implements Comparator<YearlyStockList> {
		@Override
		public int compare(YearlyStockList graph1, YearlyStockList graph2) {
			int i = graph1.month < graph2.month ? -1
					: (graph1.month > graph2.month ? 1 : 0);
			if(i != 0){
				return i;
			}			
			return graph1.category.compareTo(graph2.category);
		}
	}
	
	public static class OrderByMonth implements Comparator<YearlyStockList> {
		@Override
		public int compare(YearlyStockList graph1, YearlyStockList graph2) {
			return graph1.month < graph2.month ? -1
					: (graph1.month > graph2.month ? 1 : 0);
		}
	}
	
	public static class OrderByCategory implements Comparator<YearlyStockList> {
		@Override
		public int compare(YearlyStockList graph1, YearlyStockList graph2) {
			return graph1.category.compareTo(graph2.category);
		}
	}
}
