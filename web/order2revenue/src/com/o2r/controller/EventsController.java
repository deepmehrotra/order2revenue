package com.o2r.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.o2r.bean.EventsBean;
import com.o2r.helper.ConverterClass;
import com.o2r.helper.HelperClass;
import com.o2r.model.Events;
import com.o2r.model.NRnReturnCharges;
import com.o2r.service.EventsService;

@Controller
public class EventsController {
	
	@Autowired
	private EventsService eventsService;
	
	static Logger log = Logger.getLogger(EventsController.class.getName());
	
	@SuppressWarnings("deprecation")
	public ModelAndView saveEvents(HttpServletRequest request,@ModelAttribute("command") EventsBean eventsBean, BindingResult result){
		
		log.info("**** saveEvents Starts****");
		
		List<NRnReturnCharges> chargeList=new ArrayList<NRnReturnCharges>();
		Map<String, String[]> parameters = request.getParameterMap();
		
		try {
			for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
				
				if (entry.getValue()[0] != null	&& !entry.getValue()[0].isEmpty()) {
					
					if (entry.getKey().contains("id"))
						eventsBean.setEventId(Integer.parseInt(entry.getValue()[0]));
					else if (entry.getKey().contains("eName"))
						eventsBean.setEventName(entry.getValue()[0]);
					else if (entry.getKey().contains("cName"))
						eventsBean.setChannelName(entry.getValue()[0]);
					else if (entry.getKey().contains("startDate"))
						eventsBean.setStartDate(new Date(entry.getValue()[0]));
					else if (entry.getKey().contains("endDate"))
						eventsBean.setEndDate(new Date(entry.getValue()[0]));
					else if (entry.getKey().contains("nrType"))
						eventsBean.setNrType(entry.getValue()[0]);
					else if (entry.getKey().contains("pCategories"))
						eventsBean.setProductCategories(entry.getValue()[0]);
					else if (entry.getKey().contains("nr-")) {
						
						String temp = entry.getKey().substring(3);
						NRnReturnCharges nrnReturncharge = new NRnReturnCharges();
						nrnReturncharge.setChargeAmount(Float.parseFloat(entry.getValue()[0]));
						nrnReturncharge.setChargeName(temp);
						nrnReturncharge.setConfig(eventsBean.getNrnReturnConfig());
						chargeList.add(nrnReturncharge);
						
					} else if (entry.getKey().contains("local")) {
						eventsBean.getNrnReturnConfig().setLocalList(Arrays.toString(entry.getValue()));
					} else if (entry.getKey().contains("zonal")) {
						eventsBean.getNrnReturnConfig().setZonalList(Arrays.toString(entry.getValue()));
					} else if (entry.getKey().contains("national")) {
						eventsBean.getNrnReturnConfig().setNationalList(Arrays.toString(entry.getValue()));
					} else if (entry.getKey().contains("metro")) {
						eventsBean.getNrnReturnConfig().setMetroList(Arrays.toString(entry.getValue()));
					}
				}
			}
			
			eventsBean.getNrnReturnConfig().setCharges(chargeList);
			
			try{
				
				Events events=ConverterClass.prepareEventsModel(eventsBean);
				eventsService.addEvent(events, HelperClass.getSellerIdfromSession(request));
			}catch(Exception e){
				log.info("*** Exception In EventsDaoImpl ***");
				e.printStackTrace();
			}

		} catch(Exception e){
			e.printStackTrace();
		}
		
		log.info("**** saveEvents Exit ***");
		return new ModelAndView("redirect:/miscellaneous/EventsDetail.html");
		
	}
	
	

}
