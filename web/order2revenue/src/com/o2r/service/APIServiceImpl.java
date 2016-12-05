package com.o2r.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.o2r.dao.APIDao;
import com.o2r.model.Order;
import com.o2r.model.ShopcluesOrderAPI;

@Service("APIService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class APIServiceImpl implements APIService{
	
	@Autowired
	private APIDao apiDao;
	
	/*@Override
	public void saveSnapdealOrderFromAPI(int sellerId,
			List<SnapdealOrderAPI> orderList) {
		apiDao.saveSnapdealOrderFromAPI(sellerId, orderList);
	}
	
	@Override
	public Map<Integer, SnapdealOrderAPI> listSnapdealOrderAPI(int sellerId) {
		return apiDao.listSnapdealOrderAPI(sellerId);
	}
	
	@Override
	public void saveSnapdealOrderToO2R(int sellerId, List<Order> orderList) {
		apiDao.saveSnapdealOrderToO2R(sellerId, orderList);
	}*/
	
	@Override
	public void saveShopcluesOrderFromAPI(int sellerId,
			List<ShopcluesOrderAPI> orderList) {
		apiDao.saveShopcluesOrderFromAPI(sellerId, orderList);
	}
	
	@Override
	public Map<Integer, ShopcluesOrderAPI> listShopcluesOrderAPI(int sellerId) {
		return apiDao.listShopcluesOrderAPI(sellerId);
	}
	
	@Override
	public void saveShopcluesOrderToO2R(int sellerId, List<Order> orderList) {
		apiDao.saveShopcluesOrderToO2R(sellerId, orderList);
	}
}
