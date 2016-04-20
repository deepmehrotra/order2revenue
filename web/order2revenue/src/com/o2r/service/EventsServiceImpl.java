package com.o2r.service;

import java.util.Date;
import java.util.List;

import com.o2r.dao.EventsDao;
import com.o2r.model.Events;
import com.o2r.model.Partner;

public class EventsServiceImpl implements EventsService{
	
	private EventsDao eventsDao;
	
	@Override
	public void addEvent(Events event, int eventId) {
		eventsDao.addEvent(event, eventId);		
	}
	
	@Override
	public Events getEvent(int eventId) {		
		return eventsDao.getEvent(eventId);
	}
	
	@Override
	public Events getEvent(int partnerId, int sellerId) {
		return eventsDao.getEvent(partnerId, sellerId);
	}
	
	@Override
	public List<Events> getEvents(Partner partner, int sellerId) {
		return eventsDao.getEvents(partner, sellerId);
	}
	
	@Override
	public List<Events> listEvents(int sellerId) {
		return eventsDao.listEvents(sellerId);
	}
	
	@Override
	public List<Events> listEvents(Date sDate, Date eDate, int sellerId) {
		return eventsDao.listEvents(sDate, eDate, sellerId);
	}

}
