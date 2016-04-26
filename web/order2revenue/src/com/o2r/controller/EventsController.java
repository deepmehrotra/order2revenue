package com.o2r.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.o2r.bean.EventsBean;
import com.o2r.bean.ProductBean;
import com.o2r.helper.ConverterClass;
import com.o2r.helper.CustomException;
import com.o2r.helper.HelperClass;
import com.o2r.model.Category;
import com.o2r.model.Events;
import com.o2r.model.NRnReturnCharges;
import com.o2r.model.Partner;
import com.o2r.service.EventsService;
import com.o2r.service.PartnerService;

@Controller
public class EventsController {
	
	@Autowired
	private EventsService eventsService;
	@Autowired
	private PartnerService partnerService;
	
	static Logger log = Logger.getLogger(EventsController.class.getName());
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/seller/saveEvent", method = RequestMethod.POST)
	public ModelAndView saveEvents(HttpServletRequest request,@ModelAttribute("command") EventsBean eventsBean, BindingResult result){
		
		log.info("$$$ saveEvents Starts $$$");		
		List<NRnReturnCharges> chargeList=new ArrayList<NRnReturnCharges>();
		Map<String, String[]> parameters = request.getParameterMap();
		
		System.out.println("-------************----------++++++++++++************** Events Data :  "+ parameters);
		
		//System.out.println("Inside Events Controller : "+eventsBean.getChannelName());
		
		try {
			for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
				
				if (entry.getValue()[0] != null	&& !entry.getValue()[0].isEmpty()) {
					if (entry.getKey().contains("nrType"))
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
				eventsBean.setSellerId(HelperClass.getSellerIdfromSession(request));
				eventsBean.setCreatedDate(new Date());
				Events events=ConverterClass.prepareEventsModel(eventsBean);
				eventsService.addEvent(events, HelperClass.getSellerIdfromSession(request));
			}catch(Exception e){
				log.info("*** Exception In EventsDaoImpl ***");
				e.printStackTrace();
			}

		} catch(Exception e){
			e.printStackTrace();
		}
		
		log.info("$$$ saveEvents Exit $$$");
		return new ModelAndView("miscellaneous/eventsList");
		
	}
	
	@RequestMapping(value = "/seller/addEvent", method = RequestMethod.GET)
	public ModelAndView addEvent(HttpServletRequest request, @ModelAttribute("command") EventsBean eventsBean, BindingResult result) {

		log.info("$$$ addEvent Start $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		Events event=null;
		
		try {
			if (eventsBean.getEventId() != 0) {
				event = eventsService.getEvent(eventsBean.getEventId());
				model.put("event", event);
			}
			try {
				List<Partner> partnerList = partnerService.listPartners(HelperClass.getSellerIdfromSession(request));
				Map<String, String> partnerMap = new HashMap<String, String>();
				if (partnerList != null && !partnerList.isEmpty()) {
					for (Partner bean : partnerList) {
						partnerMap.put(bean.getPcName(), bean.getPcName());
					}
				}
				model.put("partnerMap", partnerMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("$$$ addEvent Exit $$$");
		return new ModelAndView("miscellaneous/addEvent", model);
	}
	
	
	
	@RequestMapping(value = "/seller/eventsList", method = RequestMethod.GET)
	public ModelAndView eventsList(HttpServletRequest request, @ModelAttribute("command") EventsBean eventsBean, BindingResult result) {
		
		log.info("$$$ eventsList starts $$$");
		System.out.println(" Inside eventList method");
		Map<String, Object> model = new HashMap<String, Object>();
		try { 
			model.put("eventsList", ConverterClass
					.prepareListOfEventsBean(eventsService
							.listEvents(HelperClass
									.getSellerIdfromSession(request))));
		} /*catch (CustomException ce) {
			log.error("productList exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		}*/ catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		log.info("$$$ eventsList Exit $$$");
		return new ModelAndView("miscellaneous/eventsList", model);
	}

}
