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
		//	System.out.println("sellersList ================"+sellersList.size());
			session = sessionFactory.openSession();
			sellersList = session.createQuery(QRY).list();
			
			if (sellersList != null && sellersList.size() != 0) {
			for (Seller seller : sellersList) {
				Hibernate.initialize(seller.getPartners());
			}
			}
			System.out.println("sellersList ================"+sellersList.size());
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
		List<String> sellersList = null;
		
		List<PartnerSellerAuthInfo> returnlist = null;
		
		
		try {	
			
			session = sessionFactory.openSession();	
			Criteria criteria = session
					.createCriteria(PartnerSellerAuthInfo.class);
			criteria.add(Restrictions.eq("PCNAME", "Amazon"));
			
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			if (criteria.list().size() != 0) {
				returnlist = criteria.list();
			}
			session.getTransaction().commit();
			session.close();
			
			System.out.println("sellersList ================"+sellersList.size());
		
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
			
			orderInfo.setSeller(seller);
			System.out.println(" THE DATA AmazonOrderInfoAmazonOrderInfoAmazonOrderInfo"+orderInfo);
			//session = sessionFactory.openSession();
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
