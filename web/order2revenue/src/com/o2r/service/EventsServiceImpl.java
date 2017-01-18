package com.o2r.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.o2r.dao.EventsDao;
import com.o2r.helper.CustomException;
import com.o2r.model.Events;
import com.o2r.model.Partner;

@Service("eventsService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class EventsServiceImpl implements EventsService{
	
	@Autowired
	private EventsDao eventsDao;
	
	@Override
	public void addEvent(Events event, int eventId)throws CustomException {
		eventsDao.addEvent(event, eventId);		
	}
	
	@Override
	public Events getEvent(int eventId)throws CustomException {		
		return eventsDao.getEvent(eventId);
	}
	
	@Override
	public Events getEvent(int partnerId, int sellerId)throws CustomException {
		return eventsDao.getEvent(partnerId, sellerId);
	}
	
	@Override
	public List<Events> getEvents(String pcName, int sellerId)throws CustomException {
		return eventsDao.getEvents(pcName, sellerId);
	}
	
	@Override
	public List<Events> listEvents(int sellerId)throws CustomException {
		return eventsDao.listEvents(sellerId);
	}
	
	@Override
	public List<Events> listEvents(Date sDate, Date eDate, int sellerId)throws CustomException {
		return eventsDao.listEvents(sDate, eDate, sellerId);
	}
	
	@Override
	public Events isEventActiive(Date orderDate, String channelName, String sku,int sellerId)throws CustomException {
		return eventsDao.isEventActiive(orderDate, channelName, sku, sellerId);
	}
	
	@Override
	public Events getEvent(String eventName, int sellerID)throws CustomException {
		return eventsDao.getEvent(eventName, sellerID);
	}
	
	@Override
	public boolean isDatesAllowForEvent(Date startDate, Date endDate,
			String channelName, int sellerId) {
		return eventsDao.isDatesAllowForEvent(startDate, endDate, channelName, sellerId);
	}
	
	@Override
	public void changeStatus(int eventId, int sellerId) {
		eventsDao.changeStatus(eventId, sellerId);		
	}

}
