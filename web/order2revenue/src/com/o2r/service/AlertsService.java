package com.o2r.service;

import java.util.List;

import com.o2r.model.SellerAlerts;

public interface AlertsService {
	public void saveAlerts(SellerAlerts alerts, int sellerId);
	public List<SellerAlerts> getAlerts(int sellerId);
	public int unreadCount(int sellerId);
	public void markRead(int sellerId);
}
