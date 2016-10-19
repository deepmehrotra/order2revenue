package com.o2r.service;

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
	public double getGrossGrossProfit(String period, int sellerId) {		
		return detailedDashboardDao.getGrossGrossProfit(period, sellerId);
	}
	
	@Override
	public double getNetGrossProfit(String period, int sellerId) {
		return detailedDashboardDao.getNetGrossProfit(period, sellerId);
	}
}
