package com.o2r.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.o2r.dao.DetailedDashboardDao;

@Service("DetailedDashboardService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class DetailedDashboardServiceImpl implements DetailedDashboardService{
	
	@Autowired
	private DetailedDashboardDao detailedDashboardDao;
	
	@Override
	public double getGrossGrossProfit(Date startDate, Date endDate, int sellerId) {		
		return detailedDashboardDao.getGrossGrossProfit(startDate, endDate, sellerId);
	}
	
	@Override
	public double getNetGrossProfit(Date startDate, Date endDate, int sellerId) {
		return detailedDashboardDao.getNetGrossProfit(startDate, endDate, sellerId);
	}
	
	@Override
	public double getGrossBadquantityValue(Date startDate, Date endDate, int sellerId) {
		return detailedDashboardDao.getGrossBadquantityValue(startDate, endDate, sellerId);
	}
	
	@Override
	public long getGrossSaleQuantity(Date startDate, Date endDate, int sellerId) {
		return detailedDashboardDao.getGrossSaleQuantity(startDate, endDate, sellerId);
	}
	
	@Override
	public long getNetSaleQuantity(Date startDate, Date endDate, int sellerId) {
		return detailedDashboardDao.getNetSaleQuantity(startDate, endDate, sellerId);
	}
	
	@Override
	public double getGrossNR(Date startDate, Date endDate, int sellerId) {
		return detailedDashboardDao.getGrossNR(startDate, endDate, sellerId);
	}
	
	@Override
	public double getReturnNR(Date startDate, Date endDate, int sellerId) {
		return detailedDashboardDao.getReturnNR(startDate, endDate, sellerId);
	}
	
	@Override
	public double getGrossPR(Date startDate, Date endDate, int sellerId) {
		return detailedDashboardDao.getGrossPR(startDate, endDate, sellerId);
	}
	
	@Override
	public double getReturnPR(Date startDate, Date endDate, int sellerId) {
		return detailedDashboardDao.getReturnPR(startDate, endDate, sellerId);
	}
	
	@Override
	public double getAdditionalCharges(Date startDate, Date endDate, int sellerId) {
		return detailedDashboardDao.getAdditionalCharges(startDate, endDate, sellerId);
	}
	
	@Override
	public double getGrossSP(Date startDate, Date endDate, int sellerId) {
		return detailedDashboardDao.getGrossSP(startDate, endDate, sellerId);
	}
	
	@Override
	public double getReturnSP(Date startDate, Date endDate, int sellerId) {
		return detailedDashboardDao.getReturnSP(startDate, endDate, sellerId);
	}
	
	@Override
	public Map<String, Object> getTopSellingSKU(Date startDate, Date endDate,
			int sellerId) {
		return detailedDashboardDao.getTopSellingSKU(startDate, endDate, sellerId);
	}
	
	@Override
	public List<Map<String, Object>> getTopSellingRegion(Date startDate,
			Date endDate, int sellerId) {
		return detailedDashboardDao.getTopSellingRegion(startDate, endDate, sellerId);
	}
	
	@Override
	public List<Map<String, Object>> getUpcomingPayment(int sellerId, String status) {
		return detailedDashboardDao.getUpcomingPayment(sellerId, status);
	}
	
	@Override
	public List<Map<String, Object>> getOutstandingPayment(int sellerId, String status) {
		return detailedDashboardDao.getOutstandingPayment(sellerId, status);
	}
}
