package com.o2r.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.o2r.model.Order;
import com.o2r.model.Seller;

@Repository("mwsAmazonOrdMgmtDao")
public class MwsAmazonOrdMgmtDaoImpl implements MwsAmazonOrdMgmtDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	static Logger LOGGER = Logger.getLogger(MwsAmazonOrdMgmtDaoImpl.class.getName());
	
	@Override
	public List<Order> getOrderList() throws Exception {
		LOGGER.info("Start: getOrderList");
		List<Order> returnlist = null;
		int sellerId = 2;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Seller seller = (Seller) session.get(Seller.class, sellerId);
			if (seller.getOrders() != null && seller.getOrders().size() != 0)
				returnlist = seller.getOrders();
			returnlist = seller.getOrders();
			session.getTransaction().commit();
			session.close();
		} catch (Exception expObj) {
			expObj.printStackTrace();
		}
		LOGGER.info("End: getOrderList");
		return returnlist;
	}

}
