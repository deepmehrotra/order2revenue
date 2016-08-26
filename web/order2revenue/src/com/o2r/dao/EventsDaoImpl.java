package com.o2r.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.o2r.helper.CustomException;
import com.o2r.helper.GlobalConstant;
import com.o2r.model.Events;
import com.o2r.model.Partner;
import com.o2r.model.Seller;

@Repository("eventsDao")
public class EventsDaoImpl implements EventsDao {	
	
	//private static final String isEventActive = "select * from events ev where ev.sellerId=:sellerId and ev.channelName=:partner and (:orderDate between ev.startDate and ev.endDate)";
	
	@Autowired	
	private SessionFactory sessionFactory;
	static Logger log = Logger.getLogger(EventsDaoImpl.class.getName());
	
	@Override
	public void addEvent(Events events, int sellerId)throws CustomException  {
		
		log.info("$$$ addEvent Starts.... $$$");
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();			
			Partner partner = null;
			Seller seller = null;					
			String currentPartnerName = events.getChannelName();
			if(events.getEventId() != 0){
				session.merge(events);
			} else {					
				
				if (isDatesAllowForEvent(events.getStartDate(), events.getEndDate(), events.getChannelName(), sellerId) == false) {
					log.debug("*** You can not create Events during these Dates..... !!!");
					GlobalConstant.addEventError="You can not create Events during these Dates !!!";
					throw new Exception();
				} else {
					Criteria criteria = session.createCriteria(Seller.class)
							.add(Restrictions.eq("id", sellerId));
					criteria.createAlias("partners", "partner",
							CriteriaSpecification.LEFT_JOIN)
							.add(Restrictions.eq("partner.pcName",currentPartnerName))
							.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

					if (criteria.list() != null && criteria.list().size() != 0) {
						seller = (Seller) criteria.list().get(0);
						partner = seller.getPartners().get(0);
						partner.getEvents().add(events);
						session.saveOrUpdate(partner);
					}
				}
			}
			session.getTransaction().commit();
			session.flush();			
		} catch (Exception e) {
			log.error("Failed! by sellerId : "+sellerId,e);
			e.printStackTrace();
			throw new CustomException(GlobalConstant.addEventError,new Date(), 1, GlobalConstant.addEventErrorCode, e);
		} finally {
			if(session != null)
				session.close();
		}
		log.info("$$$ addEvent Exit...");
	}	
	
	@Override
	public Events getEvent(int eventId)throws CustomException {	
		log.info("$$$ getEvent with eventId Starts...***");
		Events event=null;
		Session session=null;
		try {			
			session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Events.class).add(Restrictions.eq("eventId", eventId));
			event=(Events)criteria.list().get(0);
			if(event.getNrnReturnConfig()!=null)
				Hibernate.initialize(event.getNrnReturnConfig().getCharges());
		} catch (Exception e) {
			log.error("Failed!",e);
			e.printStackTrace();
			throw new CustomException(GlobalConstant.getEventError,new Date(), 3, GlobalConstant.getEventErrorCode, e);
		}finally{
			if(session != null){
				session.close();
			}			
		}
		log.info("$$$ getEvent with eventId Exit...***");
		return event;
	}
	
	@Override
	public Events getEvent(int partnerId, int sellerId)throws CustomException {
		return null;
	}
	@Override
	public List<Events> getEvents(Partner partner, int sellerId)throws CustomException {
		
		log.info("*** getEvents Starts...***");
		List<Events> returnList=null;
		Session session=null;
		try {
			
			session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(Restrictions.eq("sellerId", sellerId));
			criteria.createAlias("events", "events", CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("channelName", partner.getPcName()))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			returnList=criteria.list();
		} catch (Exception e) {
			log.error("Failed! by sellerId : "+sellerId,e);
			e.printStackTrace();
			throw new CustomException(GlobalConstant.getEventError,new Date(), 3, GlobalConstant.getEventErrorCode, e);
		} finally{
			if(session != null){
				session.close();
			}			
		}
		log.info("*** getEvents ends...***");
		return returnList;
	}
	
	@Override
	public List<Events> listEvents(int sellerId)throws CustomException {
		
		log.info("*** listEvents Starts...***");		
		List<Events> returnlist = null;
		Session session=null; 		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();			
			Criteria criteria = session.createCriteria(Events.class).add(Restrictions.eq("sellerId", sellerId));			
			returnlist=criteria.list();			
			session.getTransaction().commit();			
		} catch (Exception e) {
			log.error("Failed! by sellerId : "+sellerId,e);
			e.printStackTrace();
			throw new CustomException(GlobalConstant.listEventError,new Date(), 3, GlobalConstant.listEventErrorCode, e);
		}finally{
			if(session != null){
				session.close();
			}			
		}
		log.info("*** listEvents Ends...***");		
		return returnlist;
	}
	
	@Override
	public List<Events> listEvents(Date sDate, Date eDate, int sellerId)throws CustomException {
		
		log.info("*** listEvents between dates Starts...***");		
		List<Events> returnlist = null;
		Calendar cal=Calendar.getInstance();
		cal.setTime(sDate);
		cal.add(Calendar.DATE, -1);
		Date startDate=cal.getTime();
		cal.setTime(eDate);
		cal.add(Calendar.DATE, 1);
		Date endDate=cal.getTime();
		Session session=null;
		try {

			session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(Restrictions.eq("id", sellerId));
			criteria.createAlias("events", "events", CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.between("startdate", startDate, endDate))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);				
				
				returnlist = criteria.list();			
		} catch (Exception e) {
			log.error("Failed! by sellerId : "+sellerId,e);
			e.printStackTrace();
			throw new CustomException(GlobalConstant.listEventError,new Date(), 3, GlobalConstant.listEventErrorCode, e);
		} finally{
			if(session != null){
				session.close();
			}			
		}
		log.info("*** listEvents between dates ends...***");
		return returnlist;
	}
	
	@Override
	public Events isEventActiive(Date orderDate, String channelName, String sku, int sellerId)throws CustomException {
		log.info("$$$ isEventActive Start $$$");
		
		List eventList=null;
		Session session=null;
		try{
			session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Events.class).add(Restrictions.eq("sellerId", sellerId)).add(Restrictions.eq("channelName", channelName))
								.add(Restrictions.le("startDate",orderDate))
								.add(Restrictions.ge("endDate", orderDate));			
			
			if(criteria.list() != null && criteria.list().size() !=0){				
				if(criteria.list().get(0)!=null)
				{
					eventList=criteria.list();
					Events event=(Events)eventList.get(0);					
					if(event != null && ((event.getSelectAll() != null && event.getSelectAll().equals("true")) 
							|| (sku != null && event.getSkuList() != null && event.getSkuList().contains(sku)))
							&& event.getStatus().equals("Active")){	
						log.info("$$$ isEventActive Exit $$$");
						return event;
					}
				}
			}
		}catch(Exception e){
			log.error("Failed! by sellerId : "+sellerId,e);
			e.printStackTrace();
		}finally{
			if(session != null){
				session.close();
			}			
		}
		
		log.info("$$$ isEventActive Exit $$$");
		return null;
	}
	
	@Override
	public Events getEvent(String eventName, int sellerID)throws CustomException {
		
		log.info("$$$ getEvent Starts $$$");
		Events event=null;
		Session session=null;
		List eventList=null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Events.class).add(Restrictions.eq("sellerId", sellerID)).add(Restrictions.eq("eventName", eventName));
			eventList=criteria.list();
			if(eventList!=null&&eventList.size()!=0&&eventList.get(0)!=null)
				event=(Events)eventList.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : "+sellerID,e);
			throw new CustomException(GlobalConstant.getEventError,new Date(), 3, GlobalConstant.getEventErrorCode, e);
		}finally{
			if(session != null){
				session.close();
			}			
		}
		log.info("$$$ getEvent ends $$$");
		return event;
	}
	
	@Override
	public boolean isDatesAllowForEvent(Date startDate, Date endDate, String channelName, int sellerId){
		
		log.info("*** isDatesAllowForEvent starts ***");
		Session session=null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();	
			
			Criteria criterias = session.createCriteria(Events.class).add(Restrictions.eq("sellerId", sellerId));
			Criterion res1 = Restrictions.and(Restrictions.le("startDate", startDate),Restrictions.ge("endDate", startDate));
			Criterion res2 = Restrictions.and(Restrictions.le("startDate", endDate),Restrictions.ge("endDate", endDate));
			Criterion res3 = Restrictions.and(Restrictions.ge("startDate", startDate),Restrictions.le("endDate", endDate));

			criterias.add(Restrictions.eq("channelName",channelName));
			criterias.add(Restrictions.or((Restrictions.or(res1, res2)),res3));
			
			if (criterias.list() != null && criterias.list().size() != 0) {
				log.info("*** isDatesAllowForEvent ends ***");
				return false;
			}
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("Failed! by sellerId : "+sellerId,e);
		}finally{
			if(session != null)
				session.close();
		}
		log.info("*** isDatesAllowForEvent ends ***");
		return true;
	}
	
	@Override
	public void changeStatus(int eventId, int sellerId) {
		log.info("*** changeStatus starts ***");
		Session session=null;
		Events event=null;
		List eventList=null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();	
			
			Criteria criteria = session.createCriteria(Events.class).add(Restrictions.eq("sellerId", sellerId)).add(Restrictions.eq("eventId", eventId));
			eventList=criteria.list();
			if (eventList != null && eventList.size() != 0 && eventList.get(0) != null)
				event = (Events) eventList.get(0);
			if(event != null){
				if(event.getStatus().equals("Active")){
					event.setStatus("Pause");
				}else{
					event.setStatus("Active");
				}
			}
			session.merge(event);
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			log.error("Failed! by sellerId : "+sellerId,e);
		}finally{
			if(session != null)
				session.close();
		}
		log.info("*** changeStatus ends ***");
	}

}
