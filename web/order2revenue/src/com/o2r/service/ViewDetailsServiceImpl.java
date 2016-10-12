package com.o2r.service;

import java.util.Date;
import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.o2r.bean.ViewDetailsBean;
import com.o2r.dao.ViewDetailsDao;

/**
 * @author Deep Mehrotra
 *
 */
@Service("viewDetailsService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ViewDetailsServiceImpl implements ViewDetailsService {

	@Autowired
	private ViewDetailsDao viewDetailsDao;

	public List<ViewDetailsBean> getTopSKUDetailsToday(Date startDate,
			Date endDate, int sellerId) {

		return viewDetailsDao.getTopSKUDetailsToday(startDate, endDate,	sellerId);

	}

	public List<ViewDetailsBean> getTopSKUDetailsQuarterly(Date startDate,
			Date endDate, int sellerId, String selectedQuarter) {

		return viewDetailsDao.getTopSKUDetailsQuarterly(startDate, endDate,
				sellerId, selectedQuarter);

	}
	
	public List<ViewDetailsBean> getTopSKUDetailsAnnualy(Date startDate,
			Date endDate, int sellerId, String selectedYear) {

		return viewDetailsDao.getTopSKUDetailsAnnualy(startDate, endDate,
				sellerId, selectedYear);

	}
	
	
	
	

	public List<ViewDetailsBean> getTopSKUDetailsMonthly(Date startDate,
			Date endDate, int sellerId, String selectedmonth) {

		return viewDetailsDao.getTopSKUDetailsMonthly(startDate, endDate,
				sellerId, selectedmonth);

	}

	public List<ViewDetailsBean> getTopSKUCityDetails(Date startDate,
			Date endDate, int sellerId) {

		return viewDetailsDao
				.getTopSKUCityDetails(startDate, endDate, sellerId);

	}

}