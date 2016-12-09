package com.o2r.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.o2r.model.Order;
import com.o2r.model.ShopcluesOrderAPI;

@Repository("apiDao")
public class APIDaoImpl implements APIDao{

	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	
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
		
		Session session = null;
		System.out.println(orderList.size());
		List<String> uniqueIds = null;
		try {
			if(orderList.size() != 0){
				session = sessionFactory.openSession();
				session.beginTransaction();
				Criteria criteria = session.createCriteria(ShopcluesOrderAPI.class)
						.add(Restrictions.eq("sellerId", sellerId))
						.setProjection(Projections.projectionList()
								.add(Projections.property("shopcluesUniqueId")));
				uniqueIds = criteria.list();				
				for(ShopcluesOrderAPI order : orderList){
					if(order.getShopcluesOrderId() != 0){
						session.merge(order);
					} else if(uniqueIds == null || !uniqueIds.contains(order.getShopcluesUniqueId())){
						session.saveOrUpdate(order);
					}
				}
				session.getTransaction().commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(session != null){
				session.close();
			}
		}
	}
	
	@Override
	public Map<Integer, ShopcluesOrderAPI> listShopcluesOrderAPI(int sellerId) {
		
		List<ShopcluesOrderAPI> orderAPIList = new ArrayList<ShopcluesOrderAPI>();
		Map<Integer, ShopcluesOrderAPI> shopcluesOrderAPIMap = new HashMap<Integer, ShopcluesOrderAPI>();
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(ShopcluesOrderAPI.class)
					.add(Restrictions.eq("sellerId", sellerId))
					.add(Restrictions.ne("orderStatus", "ShippedToO2R"))
					.addOrder(org.hibernate.criterion.Order.desc("timestamp"));			
			orderAPIList = criteria.list();
			if(orderAPIList != null && orderAPIList.size() != 0){
				for(ShopcluesOrderAPI order : orderAPIList){
					shopcluesOrderAPIMap.put(order.getShopcluesOrderId(), order);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return shopcluesOrderAPIMap;
	}
	
	@Override
	public void saveShopcluesOrderToO2R(int sellerId, List<Order> orderList) {
		
	}
	
}
