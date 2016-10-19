package com.o2r.dao;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("DetailedDashboardDao")
public class DetailedDashboardDaoImpl implements DetailedDashboardDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	static Logger log = Logger.getLogger(DetailedDashboardDaoImpl.class.getName());
	
}
