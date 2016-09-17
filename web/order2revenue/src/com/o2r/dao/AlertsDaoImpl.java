package com.o2r.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.o2r.model.SellerAlerts;

@Repository("alertDao")
public class AlertsDaoImpl implements AlertsDao{
	
	@Autowired	
	private SessionFactory sessionFactory;
	static Logger log = Logger.getLogger(AlertsDaoImpl.class.getName());
	
	@Override
	public void saveAlerts(SellerAlerts alerts, int sellerId) {
		Session session = null;
		try {
			alerts.setSellerId(sellerId);
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.saveOrUpdate(alerts);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed By seller ID : "+sellerId, e);
		} finally {
			if(session != null)
				session.close();
		}
	}
	@Override
	public List<SellerAlerts> getAlerts(int sellerId) {		
		List<SellerAlerts> alertsList = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(SellerAlerts.class).add(Restrictions.eq("sellerId", sellerId));
				criteria.addOrder(Order.desc("alertDate"));
			alertsList = criteria.list();
			System.out.println(alertsList.size());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed by Seller ID : "+sellerId, e);
		} finally {
			if(session != null)
				session.close();
		}
		return alertsList;
	}
	
	@Override
	public int unreadCount(int sellerId) {
		int unreadSize = 0;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(SellerAlerts.class).add(Restrictions.eq("sellerId", sellerId));
				criteria.add(Restrictions.eq("status", "unread"));
				criteria.setProjection(Projections.rowCount());
			unreadSize = (int)criteria.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed By seller ID : "+sellerId, e);
		} finally{
			if(session != null)
				session.close();
		}
		return unreadSize;
	}
}
