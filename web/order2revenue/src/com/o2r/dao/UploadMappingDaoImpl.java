/**
 * @Auther Deep Mehrotra
 */
package com.o2r.dao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.o2r.helper.CustomException;
import com.o2r.helper.GlobalConstant;
import com.o2r.model.ChannelUploadMapping;
import com.o2r.model.Seller;


@Repository("uploadMappingDao")
public class UploadMappingDaoImpl implements UploadMappingDao {
	 @Autowired
	 private SessionFactory sessionFactory;
	 static Logger log = Logger.getLogger(UploadMappingDaoImpl.class.getName());
	 private static final String columMapDeleteQuery =
			 "delete from ColumMap where uploadMapping_mapId=:mapId";
	
	 
		@Override
	public void addChannelUploadMapping(ChannelUploadMapping uploadMapping,int sellerId) 
	 throws CustomException{
		 
		 log.info("*** addChannelUploadMapping starts : UploadMappingDaoImpl ****");
			Seller seller = null;
			try {
				Session session = sessionFactory.openSession();
				session.beginTransaction();
				if (uploadMapping.getMapId() == 0) {
					Criteria criteria = session.createCriteria(Seller.class).add(
							Restrictions.eq("id", sellerId));
					seller = (Seller) criteria.list().get(0);
					seller.getUploadMapping().add(uploadMapping);
						session.saveOrUpdate(seller);
				} else {
					Query gettingTaxId = session
							.createSQLQuery(columMapDeleteQuery)
							.setParameter("mapId", uploadMapping.getMapId());
					gettingTaxId.executeUpdate();
					session.saveOrUpdate(uploadMapping);
						}
				session.getTransaction().commit();
				session.close();
			} catch (Exception e) {
				e.printStackTrace();
				log.equals("**Error Code : "+ (sellerId + "-" + GlobalConstant.addChannelUploadMappingError));
				log.error("Failed! by sellerId : "+sellerId,e);
				throw new CustomException(GlobalConstant.addChannelUploadMappingError,
						new Date(), 1, sellerId + "-"
								+ GlobalConstant.addChannelUploadMappingErrorCode, e);
			}
	}
	 
	@Override
	public ChannelUploadMapping getChannelUploadMapping(long mapId) throws
	CustomException{
		 log.info("*** getChannelUploadMapping by id starts : UploadMappingDaoImpl ****");
			
			ChannelUploadMapping returnObj=null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			returnObj=(ChannelUploadMapping)session.get(ChannelUploadMapping.class, mapId);
			Hibernate.initialize(returnObj.getColumMap());
			session.getTransaction().commit();
			session.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.equals("**Error Code : "+ ("-" + GlobalConstant.addChannelUploadMappingError));
			log.error("Failed! by sellerId : ",e);
			throw new CustomException(GlobalConstant.addChannelUploadMappingError,
					new Date(), 1,GlobalConstant.addChannelUploadMappingErrorCode, e);
		}
		log.info("*** getChannelUploadMapping by id Ends : UploadMappingDaoImpl ****");
		return returnObj;
	}
	@Override
	public ChannelUploadMapping getChannelUploadMapping(String channelName,String fileName,int sellerId) 
			throws CustomException{
		log.info("*** getChannelUploadMapping by channelName starts : UploadMappingDaoImpl ****");
		ChannelUploadMapping returnObj = null;
		Seller seller = null;
		List resultsetList=null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();

			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", sellerId));
			criteria.createAlias("uploadMapping", "uploadMapping",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("uploadMapping.channelName", channelName))
					.add(Restrictions.eq("uploadMapping.fileName", fileName))
					.setResultTransformer(
							CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			resultsetList=criteria.list();
			if (resultsetList != null && resultsetList.size() != 0) {
				
				seller = (Seller) resultsetList.get(0);
				returnObj = seller.getUploadMapping()!=null?seller.getUploadMapping().get(0):null;
				if(returnObj!=null)
					Hibernate.initialize(returnObj.getColumMap());
				
			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("**Error Code : "+ (sellerId + "-" + GlobalConstant.getChannelUploadMappingErrorCode));
			log.error("Failed! by sellerId : "+sellerId,e);
			throw new CustomException(GlobalConstant.getChannelUploadMappingError,
					new Date(), 1, sellerId + "-"
							+ GlobalConstant.getChannelUploadMappingErrorCode, e);

		}

		log.info("*** getChannelUploadMapping by channelName starts : UploadMappingDaoImpl ****");
		return returnObj;
	}

	/*@Override
	public void deleteAccountTransaction(AccountTransaction accountTransaction) {
	      Session session = sessionFactory.openSession();
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         AccountTransaction at = (AccountTransaction)session.get(AccountTransaction.class, accountTransaction.getAccTransId()); 
	         session.delete(at); 
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	         
	      }finally {
	         session.close(); 
	      }		
	}*/

}
