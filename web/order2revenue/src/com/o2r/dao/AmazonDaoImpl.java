package com.o2r.dao;

import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.o2r.helper.CustomException;
import com.o2r.helper.GlobalConstant;
import com.o2r.model.AmazonOrder;

@Repository("amazonDao")
public class AmazonDaoImpl implements AmazonDao {

	// private static final String isEventActive =
	// "select * from events ev where ev.sellerId=:sellerId and ev.channelName=:partner and (:orderDate between ev.startDate and ev.endDate)";

	@Autowired
	private SessionFactory sessionFactory;
	static Logger log = Logger.getLogger(AmazonDaoImpl.class.getName());

	@Override
	public void addAmazonOrder(AmazonOrder amazonOrder) throws CustomException {

		log.info("BEGIN: addAmazonOrder");
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			
			session.saveOrUpdate(amazonOrder);
			
			session.getTransaction().commit();
			session.flush();
			session.close();
		} catch (Exception e) {
			log.error("Failed!", e);
			e.printStackTrace();
			throw new CustomException(GlobalConstant.addEventError, new Date(),
					1, GlobalConstant.addEventErrorCode, e);
		}
		log.info("END: addAmazonOrder");
	}
}
