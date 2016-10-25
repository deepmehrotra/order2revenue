package com.o2r.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.o2r.model.AmazonOrderInfo;
import com.o2r.model.Seller;

@Repository("mwsAmazonOrdMgmtDao")
public class MwsAmazonOrdMgmtDaoImpl implements MwsAmazonOrdMgmtDao {

	@Autowired
	private SessionFactory sessionFactory;

	static Logger LOGGER = Logger.getLogger(MwsAmazonOrdMgmtDaoImpl.class
			.getName());

	@Override
	public List<AmazonOrderInfo> getOrderList() throws Exception {
		LOGGER.info("Start: getOrderList");
		Session session = null;
		List<AmazonOrderInfo> amazonOrderInfos = new ArrayList<AmazonOrderInfo>();
		final String QRY = "from ExceptionLogging";
		try {

			// session = HibernateUtil.getSessionFactory().openSession();
			session = sessionFactory.openSession();
			amazonOrderInfos = session.createQuery(QRY).list();
			LOGGER.info("Response: " + amazonOrderInfos.size());
			LOGGER.info("Response:" + session.createQuery(QRY).list().size());

		} catch (Exception expObj) {
			expObj.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		LOGGER.info("End: getOrderList");
		return amazonOrderInfos;
	}

	@Override
	public List<Seller> getSeller() throws Exception {
		Session session = null;
		List<Seller> sellersList = null;
		final String QRY = "from Seller";
		try {
			session = sessionFactory.openSession();
			sellersList = session.createQuery(QRY).list();
		} catch (Exception expObj) {
			expObj.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return sellersList;
	}

	@Override
	public void saveAmazonOrderInfo(AmazonOrderInfo orderInfo) throws Exception {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			session.save(orderInfo);
			transaction.commit();
		} catch (Exception expObj) {
			expObj.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
}
