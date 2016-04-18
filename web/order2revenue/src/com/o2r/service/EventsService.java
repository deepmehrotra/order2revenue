package com.o2r.service;

import java.util.List;

import com.o2r.model.Events;
import com.o2r.model.Partner;

public interface EventsService {
	
	public void addEvent(Events event,int eventId);
	public Events getEvent(int eventId);
	public List<Events> getEvents(Partner partner, int sellerId);
	public Events getEvent(int partnerId, int sellerId);
	public List<Events> listEvents(int sellerId);

}
