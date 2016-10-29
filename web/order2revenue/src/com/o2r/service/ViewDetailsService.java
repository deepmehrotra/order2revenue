package com.o2r.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.o2r.bean.DashboardBean;
import com.o2r.bean.ViewDetailsBean;

/**
 * @author Deep Mehrotra
 *
 */
public interface ViewDetailsService { 
	
	public List<ViewDetailsBean> getTopSKUDetailsToday(Date startDate, Date endDate, int sellerId);
	
	public List<ViewDetailsBean> getTopSKUDetailsQuarterly(Date startDate, Date endDate, int sellerId, String selectedQuarter);
	
	public List<ViewDetailsBean> getTopSKUDetailsMonthly(Date startDate, Date endDate, int sellerId, String selectedmonth);	
	
	public List<ViewDetailsBean> getTopSKUDetailsAnnualy(Date startDate, Date endDate, int sellerId, String selectedYear);	
	
	public List<ViewDetailsBean> getTopSKUCityDetails(Date startDate, Date endDate, int sellerId);
	
}