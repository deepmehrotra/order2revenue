package com.o2r.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.o2r.dao.AlertsDao;
import com.o2r.model.SellerAlerts;

@Service("AlertsService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AlertsServiceImpl implements AlertsService{
	
	@Autowired
	private AlertsDao alertsDao;
	
	@Override
	public void saveAlerts(SellerAlerts alerts, int sellerId) {
		alertsDao.saveAlerts(alerts, sellerId);
	}
	@Override
	public List<SellerAlerts> getAlerts(int sellerId) {		
		return alertsDao.getAlerts(sellerId);
	}
}
