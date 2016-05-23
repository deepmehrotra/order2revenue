package com.o2r.service;

import java.util.Date;
import java.util.List;

import com.o2r.helper.CustomException;
import com.o2r.model.Events;
import com.o2r.model.Partner;

public interface EventsService {
	
	public void addEvent(Events event,int sellerId)throws CustomException;
	public Events getEvent(int eventId);
	public List<Events> getEvents(Partner partner, int sellerId);
	public Events getEvent(int partnerId, int sellerId);
	public List<Events> listEvents(int sellerId);
	public List<Events> listEvents(Date sDate, Date eDate, int sellerID);
	public Events isEventActiive(Date orderDate,String channelName, int sellerId);
	public Events getEvent(String eventName, int sellerID);
}
