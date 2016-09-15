package com.o2r.dao;

import java.util.List;

import com.o2r.model.SellerAlerts;

public interface AlertsDao {
	public void saveAlerts(SellerAlerts alerts, int sellerId);
	public List<SellerAlerts> getAlerts(int sellerId);
}
