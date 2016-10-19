package com.o2r.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.o2r.helper.HelperClass;
import com.o2r.service.CategoryService;
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
	
	static Logger log = Logger.getLogger(DashboardController.class.getName());
	
	@RequestMapping(value = "/seller/showDetailed", method = RequestMethod.GET)
	public ModelAndView showDetailedDashboard() {		
		return new ModelAndView("detailedDashboard");
	}
	
}
