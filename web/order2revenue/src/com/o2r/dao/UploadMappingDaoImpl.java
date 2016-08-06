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


@Repository("uploadMappingDao")
public class UploadMappingDaoImpl implements UploadMappingDao {
	 @Autowired
	 private SessionFactory sessionFactory;
	 static Logger log = Logger.getLogger(UploadMappingDaoImpl.class.getName());
	 private static final String columMapDeleteQuery =
			 "delete from ColumMap where uploadMapping_mapId=:mapId";
	
	 
		@Override
	public void addChannelUploadMapping(ChannelUploadMapping uploadMapping) 
	 throws CustomException{
		 
		 log.info("*** addChannelUploadMapping starts : UploadMappingDaoImpl ****");
			try {
				Session session = sessionFactory.openSession();
				session.beginTransaction();
				if (uploadMapping.getMapId() == 0) {
					
						session.saveOrUpdate(uploadMapping);
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
				log.equals("**Error Code : Admin: -" + GlobalConstant.addChannelUploadMappingError);
				log.error("Failed! by sellerId : "+e);
				throw new CustomException(GlobalConstant.addChannelUploadMappingError,
						new Date(), 1, "Admin-"
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
	public ChannelUploadMapping getChannelUploadMapping(String channelName,String fileName) 
			throws CustomException{
		log.info("*** getChannelUploadMapping by channelName starts : UploadMappingDaoImpl ****");
		ChannelUploadMapping returnObj = null;
		List resultsetList=null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();

			Criteria criteria = session.createCriteria(ChannelUploadMapping.class);
			criteria
					.add(Restrictions.eq("channelName", channelName))
					.add(Restrictions.eq("fileName", fileName));
			resultsetList=criteria.list();
			if (resultsetList != null && resultsetList.size() != 0) {
				
				returnObj = (ChannelUploadMapping) resultsetList.get(0);
					Hibernate.initialize(returnObj.getColumMap());
				
			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("**Error Code : Admin-" + GlobalConstant.getChannelUploadMappingErrorCode);
			log.error("Failed! by Admin : ",e);
			throw new CustomException(GlobalConstant.getChannelUploadMappingError,
					new Date(), 1, 
					GlobalConstant.getChannelUploadMappingErrorCode, e);

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
