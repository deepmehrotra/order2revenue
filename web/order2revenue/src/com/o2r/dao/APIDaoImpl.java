package com.o2r.dao;

import java.util.List;
import java.util.Map;

import com.o2r.model.Order;
import com.o2r.model.ShopcluesOrderAPI;


public class APIDaoImpl implements APIDao{

	/*@Override
	public void saveSnapdealOrderFromAPI(int sellerId,
			List<SnapdealOrderAPI> orderList) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Map<Integer, SnapdealOrderAPI> listSnapdealOrderAPI(int sellerId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void saveSnapdealOrderToO2R(int sellerId, List<Order> orderList) {
		// TODO Auto-generated method stub
		
	}*/
	
	@Override
	public void saveShopcluesOrderFromAPI(int sellerId,
			List<ShopcluesOrderAPI> orderList) {
		
	}
	
	@Override
	public Map<Integer, ShopcluesOrderAPI> listShopcluesOrderAPI(int sellerId) {
		return null;
	}
	
	@Override
	public void saveShopcluesOrderToO2R(int sellerId, List<Order> orderList) {
		
	}
	
}
