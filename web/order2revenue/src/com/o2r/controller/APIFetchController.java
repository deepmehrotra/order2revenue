package com.o2r.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.o2r.helper.HelperClass;
import com.o2r.helper.SaveApiContents;

@Controller
public class APIFetchController {
	
	@Autowired
	private HelperClass helperClass;
	@Resource(name = "SaveApiContents")
	private SaveApiContents SaveApiContents;
	
	static Logger log = Logger.getLogger(APIFetchController.class.getName());
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public void saveSnapdealOrdersFromAPI(HttpServletRequest request) {
				
	}
	
	/*@RequestMapping(value = "/seller/listSnapdealOrders", method = RequestMethod.GET)
	public ModelAndView listSnapdealOrderList(HttpServletRequest request) {
		int sellerId = 0;
		Map<Integer, SnapdealOrderAPI> snapdealOrderMap = new HashMap<Integer, SnapdealOrderAPI>();
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			model.put("Orders", snapdealOrderMap);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed By Seller : "+sellerId, e);
		}		
		return new ModelAndView("miscellaneous/snapdealOrderAPIList", model);		
	}*/
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ModelAndView saveSnapdealOrdersToO2R(HttpServletRequest request) {
		return null;		
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public void saveShopcluesOrdersFromAPI(HttpServletRequest request) {
				
	}
	
	@RequestMapping(value = "/seller/listShopsluesOrders_Admin", method = RequestMethod.GET)
	public ModelAndView listShopcluesOrderListForAdmin(HttpServletRequest request) {
		int sellerId = 0;
		
		/*Map<Integer, SnapdealOrderAPI> snapdealOrderMap = new HashMap<Integer, SnapdealOrderAPI>();*/
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			/*model.put("Orders", snapdealOrderMap);*/
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed By Seller : "+sellerId, e);
		}		
		return new ModelAndView("admin/snapdealOrderAPIList", model);		
	}
	
	@RequestMapping(value = "/seller/listShopsluesOrders_Seller", method = RequestMethod.GET)
	public ModelAndView listShopcluesOrderList(HttpServletRequest request) {
		int sellerId = 0;
		/*Map<Integer, SnapdealOrderAPI> snapdealOrderMap = new HashMap<Integer, SnapdealOrderAPI>();*/
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			/*model.put("Orders", snapdealOrderMap);*/
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed By Seller : "+sellerId, e);
		}		
		return new ModelAndView("miscellaneous/snapdealOrderAPIList", model);		
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ModelAndView saveShopcluesOrdersToO2R(HttpServletRequest request) {
		return null;		
	}
}
