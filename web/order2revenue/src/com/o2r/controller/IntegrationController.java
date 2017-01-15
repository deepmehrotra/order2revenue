package com.o2r.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.o2r.helper.HelperClass;
import com.o2r.helper.IntegrationFetchClient;
import com.o2r.helper.SaveIntegrationCotents;

@Controller
public class IntegrationController {
	
	@Autowired
	private HelperClass helperClass;
	@Autowired
	private IntegrationFetchClient integrationFetchClient;
	@Autowired
	private SaveIntegrationCotents saveIntegrationCotents;
	
	@RequestMapping(value = "/seller/fetchSKU", method = RequestMethod.GET)
	public ModelAndView fetchSKU(HttpServletRequest request) {
		int sellerId = 0;
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			String result = saveIntegrationCotents.saveAPIFetchProducts(integrationFetchClient.fetchAPIProduct(sellerId), sellerId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/seller/Product.html");
	}
	
	@RequestMapping(value = "/seller/fetchSKUMapping", method = RequestMethod.GET)
	public ModelAndView fetchSKUMapping(HttpServletRequest request) {
		
		int sellerId = 0;
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			String result = saveIntegrationCotents.saveAPIFetchProductMapping(integrationFetchClient.fetchAPIProductMapping(sellerId, 3), sellerId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/seller/productMapping.html");
	}
	
	@RequestMapping(value = "/seller/fetchOrders", method = RequestMethod.GET)
	public ModelAndView fetchOrders(HttpServletRequest request) {
		
		int sellerId = 0;
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			String result = saveIntegrationCotents.saveAPIFetchProductMapping(integrationFetchClient.fetchAPIProductMapping(sellerId, 3), sellerId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/seller/productMapping.html");
	}
}
