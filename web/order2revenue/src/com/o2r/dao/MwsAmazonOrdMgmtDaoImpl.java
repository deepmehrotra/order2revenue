package com.o2r.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.o2r.model.AmazonOrderInfo;
import com.o2r.model.PartnerCategoryMap;
import com.o2r.model.PartnerSellerAuthInfo;
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
		final String QRY = "from AmazonOrderInfo";
		try {

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
	public List<AmazonOrderInfo> getOrderList(String value) throws Exception {
		LOGGER.info("Start: getOrderList");
		Session session = null;
		List<AmazonOrderInfo> amazonOrderInfos = new ArrayList<AmazonOrderInfo>();
		final String QRY = "from AmazonOrderInfo where amazonorderid in("+value+")";
		try {

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
	public List<AmazonOrderInfo> getOrderListbyStatus(String value) throws Exception {
		LOGGER.info("Start: getOrderList");
		Session session = null;
		List<AmazonOrderInfo> amazonOrderInfos = new ArrayList<AmazonOrderInfo>();
		//final String QRY = "from AmazonOrderInfo where AMAZONORDERID in ('404-0238009-5378707')";		
		final String QRY = "from AmazonOrderInfo where AMAZONORDERID in ("+value+")";
		try {
			
			System.out.println("QRYQRYQRYQRYQRY"+QRY);
			
			session = sessionFactory.openSession();
			amazonOrderInfos = session.createQuery(QRY).list();	
			
			System.out.println("QRYQRYQRYQRYQRY size"+amazonOrderInfos.size());
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
			
			if (sellersList != null && sellersList.size() != 0) {
			for (Seller seller : sellersList) {
				Hibernate.initialize(seller.getPartners());
			}
			}			
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
	public List<PartnerSellerAuthInfo> getSellersFromPartnerSellerAuthoInfo() throws Exception {
		Session session = null;
		//List<String> sellersList = null;
		
		List<PartnerSellerAuthInfo> returnlist = null;
		
		//final String QRY = "from PartnerSellerAuthInfo where PCNAME like '%Amazon%'";
		final String QRY = "from PartnerSellerAuthInfo where PCNAME ='Amazon.in'";
		try {			
			
			session = sessionFactory.openSession();				
			returnlist = session.createQuery(QRY).list();
			
		} catch (Exception expObj) {
			
			expObj.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return returnlist;
	}
	
	
	@Override
	public void saveAmazonOrderInfo(AmazonOrderInfo orderInfo) throws Exception {




		Session session = null;
		Seller seller = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();			
			
			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", 65));
			seller = (Seller) criteria.list().get(0);
			seller.getOrderInfos().add(orderInfo);
			orderInfo.setSeller(seller);		
			session.saveOrUpdate(seller);
			//session.save(orderInfo);	
			session.getTransaction().commit();
		} catch (Exception expObj) {
			expObj.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	

	@Override
	public void saveAmazonOrderInfo(AmazonOrderInfo orderInfo, int sellerId) throws Exception {




		Session session = null;
		Seller seller = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();			
			
			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", sellerId));
			seller = (Seller) criteria.list().get(0);
			seller.getOrderInfos().add(orderInfo);
			orderInfo.setSeller(seller);		
			session.saveOrUpdate(seller);
			//session.save(orderInfo);	
			session.getTransaction().commit();
		} catch (Exception expObj) {
			expObj.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
}
