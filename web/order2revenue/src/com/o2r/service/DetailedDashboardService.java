package com.o2r.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface DetailedDashboardService {	
	
	public double getNetGrossProfit(Date startDate, Date endDate, int sellerId);
	public double getGrossGrossProfit(Date startDate, Date endDate, int sellerId);
	
	public double getGrossBadquantityValue(Date startDate, Date endDate, int sellerId);
	
	public long getNetSaleQuantity(Date startDate, Date endDate, int sellerId);
	public long getGrossSaleQuantity(Date startDate, Date endDate, int sellerId);
	
	public double getReturnNR(Date startDate, Date endDate, int sellerId);
	public double getGrossNR(Date startDate, Date endDate, int sellerId);
	
	public double getReturnPR(Date startDate, Date endDate, int sellerId);
	public double getGrossPR(Date startDate, Date endDate, int sellerId);
	
	public double getAdditionalCharges(Date startDate, Date endDate, int sellerId);	
	
	public double getReturnSP(Date startDate, Date endDate, int sellerId);
	public double getGrossSP(Date startDate, Date endDate, int sellerId);
	
	public Map<String, Object> getTopSellingSKU(Date startDate, Date endDate, int sellerId);
	public List<Map<String, Object>> getTopSellingRegion(Date startDate, Date endDate, int sellerId);
	
	public List<Map<String, Object>> getUpcomingPayment(int sellerId, String status);
	public List<Map<String, Object>> getOutstandingPayment(Date startDate, Date endDate, int sellerId, String status);
	
	
}
