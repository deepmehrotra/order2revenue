package com.o2r.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.o2r.bean.OrderBean;

@Controller
public class MwsOrderMgmtController {

	static Logger log = Logger.getLogger(MwsOrderMgmtController.class.getName());
	
	@RequestMapping(value = "/seller/test", method = RequestMethod.GET)
	public ModelAndView test(HttpServletRequest request, @ModelAttribute("command") OrderBean orderBean, BindingResult result) {
		log.info("Begin: test");
		
		try {
			
		} catch (Exception expObj) {
			expObj.printStackTrace();
		}
		
		log.info("End: test");
		return new ModelAndView("mws/test");
	}
}
