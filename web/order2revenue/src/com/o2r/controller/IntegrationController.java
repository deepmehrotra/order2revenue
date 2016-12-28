package com.o2r.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.o2r.helper.HelperClass;

@Controller
public class IntegrationController {
	
	@Autowired
	private HelperClass helperClass;
	
	@RequestMapping(value = "/seller/fetchSKU", method = RequestMethod.GET)
	public ModelAndView fetchSKU(HttpServletRequest request) {
		
		
		return new ModelAndView("redirect:/seller/Product.html");
	}
	
	@RequestMapping(value = "/seller/fetchSKUMapping", method = RequestMethod.GET)
	public ModelAndView fetchSKUMapping(HttpServletRequest request) {
		
		
		return new ModelAndView("redirect:/seller/productMapping.html");
	}
}
