package com.o2r.dao;

import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("DetailedDashboardDao")
public class DetailedDashboardDaoImpl implements DetailedDashboardDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	static Logger log = Logger.getLogger(DetailedDashboardDaoImpl.class.getName());
	
	private Date currentDate = new Date();
	
	@Override
	public double getGrossGrossProfit(String period, int sellerId) {
		
		return 0;
	}
	
	@Override
	public double getNetGrossProfit(String period, int sellerId) {		
		
		
		Session session = null;
		double netGrossProfit = 0;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			
		} catch (Exception e) {
			
		}		
		return netGrossProfit;
	}
	
}
