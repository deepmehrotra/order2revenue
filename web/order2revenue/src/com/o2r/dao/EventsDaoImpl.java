package com.o2r.dao;

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

import com.o2r.model.Events;
import com.o2r.model.Partner;
import com.o2r.model.Seller;

@Repository("eventsDao")
public class EventsDaoImpl implements EventsDao {	
	
	@Autowired	
	private SessionFactory sessionFactory;
	static Logger log = Logger.getLogger(EventsDaoImpl.class.getName());
	
	@Override
	public void addEvent(Events events, int sellerId) {
		
		log.info("$$$ addEvent Starts.... $$$");
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();			
			Partner partner = null;
			Seller seller = null;
			List<Events> event = null;
					
			String currentPartnerName = events.getChannelName();			
			
			Criteria criterias = session.createCriteria(Events.class).add(Restrictions.eq("sellerId", sellerId));
			Criterion res1=Restrictions.and(Restrictions.ge("startDate",events.getStartDate()),Restrictions.le("endDate",events.getStartDate()));
			Criterion res2=Restrictions.and(Restrictions.ge("startDate",events.getEndDate()),Restrictions.le("endDate",events.getEndDate()));
			Criterion res3=Restrictions.and(Restrictions.le("startDate",events.getStartDate()),Restrictions.ge("endDate",events.getEndDate()));

				criterias.add(Restrictions.eq("channelName", events.getChannelName()));
				criterias.add(Restrictions.or((Restrictions.or(res1, res2)), res3));
				
					
			
			if (criterias.list() != null && criterias.list().size() != 0) {
				System.out.println("You can not create Events during these Dates.....");
			} else {
				Criteria criteria = session.createCriteria(Seller.class).add(Restrictions.eq("id", sellerId));
				criteria.createAlias("partners", "partner",	CriteriaSpecification.LEFT_JOIN)
						.add(Restrictions.eq("partner.pcName",currentPartnerName))
						.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

				if (criteria.list() != null && criteria.list().size() != 0) {
					seller = (Seller) criteria.list().get(0);
					partner = seller.getPartners().get(0);
					partner.getEvents().add(events);
					session.saveOrUpdate(partner);
				} else {
					events.setSellerId(sellerId);
					event.add(events);
					partner.setEvents(event);
					seller.getPartners().add(partner);
					session.saveOrUpdate(seller);
				}

			}

			session.getTransaction().commit();
			session.flush();
			session.close();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			//throw new CustomException(GlobalConstant.addPartnerError,new Date(), 1, GlobalConstant.addPartnerErrorCode, e);
		}
		
		log.info("$$$ addEvent Exit...");
	}	
	
	@Override
	public Events getEvent(int eventId) {		
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
			log.error(e);
			e.printStackTrace();
		}finally{
			if(session != null){
				session.close();
			}			
		}
		return event;
	}
	
	@Override
	public Events getEvent(int partnerId, int sellerId) {
		return null;
	}
	@Override
	public List<Events> getEvents(Partner partner, int sellerId) {
		List<Events> returnList=null;
		try {
			
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(Restrictions.eq("sellerId", sellerId));
			criteria.createAlias("events", "events", CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("channelName", partner.getPcName()))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			returnList=criteria.list();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return returnList;
	}
	
	@Override
	public List<Events> listEvents(int sellerId) {
		
		List<Events> returnlist = null;
				
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			
			Criteria criteria = session.createCriteria(Events.class).add(Restrictions.eq("sellerId", sellerId));			
			returnlist=criteria.list();
			
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return returnlist;
	}
	
	@Override
	public List<Events> listEvents(Date sDate, Date eDate, int sellerId) {
		List<Events> returnlist = null;
		Calendar cal=Calendar.getInstance();
		cal.setTime(sDate);
		cal.add(Calendar.DATE, -1);
		Date startDate=cal.getTime();
		cal.setTime(eDate);
		cal.add(Calendar.DATE, 1);
		Date endDate=cal.getTime();
		try {

			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(Restrictions.eq("id", sellerId));
			criteria.createAlias("events", "events", CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.between("startdate", startDate, endDate))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
				
				
				returnlist = criteria.list();

			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		
		return returnlist;
	}

}
