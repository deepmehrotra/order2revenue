package com.o2r.service;

import java.util.List;
import java.util.Map;

import com.o2r.model.Order;
import com.o2r.model.ShopcluesOrderAPI;


public interface APIService {
	
	/*public void saveSnapdealOrderFromAPI (int sellerId, List<SnapdealOrderAPI> orderList);
	public Map<Integer, SnapdealOrderAPI> listSnapdealOrderAPI (int sellerId);
	public void saveSnapdealOrderToO2R (int sellerId, List<Order> orderList);*/
	
	public void saveShopcluesOrderFromAPI (int sellerId, List<ShopcluesOrderAPI> orderList);
	public Map<Integer, ShopcluesOrderAPI> listShopcluesOrderAPI (int sellerId);
	public void saveShopcluesOrderToO2R (int sellerId, List<Order> orderList);
}
