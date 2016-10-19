package com.o2r.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.o2r.dao.DashboardDaoImpl;
import com.o2r.helper.HelperClass;
import com.o2r.model.AccountTransaction;
import com.o2r.service.CategoryService;
import com.o2r.service.DashboardService;
import com.o2r.service.PartnerService;
import com.o2r.service.ProductService;

@Controller
public class DashboardController {
	
	@Autowired
	private PartnerService partnerService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private HelperClass helperClass;
	@Autowired
	private ProductService productService;
	@Autowired
	private DashboardService dashboardService;
		
	static Logger log = Logger.getLogger(DashboardController.class.getName());	
	
	@RequestMapping(value = "/seller/showDetailed", method = RequestMethod.GET)
	public ModelAndView showDetailedDashboard() {		
		return new ModelAndView("detailedDashboard");
	}
	
	@RequestMapping(value = "/seller/getGrossMargin", method = RequestMethod.GET)
	public @ResponseBody String getGrossMargin(HttpServletRequest request) {

		log.info("$$$ getGrossMargin Starts : DashboardController $$$");
		int sellerId =Integer.parseInt(request.getParameter("sellerId"));		
		Map<String, Object> grossMargin = new HashMap<String, Object>();		
		Gson gson = null;
		try {
			GsonBuilder gsonBuilder = new GsonBuilder();			
			gson = gsonBuilder.setPrettyPrinting().create();										
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by seller ID : " + sellerId, e);
		}
		log.info("$$$ getGrossMargin Ends : DashboardController $$$");
		return gson.toJson(grossMargin);
	}
	
}
