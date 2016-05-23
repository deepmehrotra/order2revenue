/*
 * Author Kapil Kumar
 * J2EE Developer
 */
package com.o2r.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.o2r.bean.FormBean;
import com.o2r.bean.PlanBean;
import com.o2r.dao.PlanDaoImpl;
import com.o2r.helper.ConverterClass;
import com.o2r.helper.CustomException;
import com.o2r.model.Plan;
import com.o2r.service.PlanService;

@Controller
public class PlanController {
	@Autowired
	private PlanService planService;
	
	static Logger log = Logger.getLogger(PlanController.class.getName());
	
	// Contoller to show the home page of Plan
	@RequestMapping("/admin/plan.html")
	public ModelAndView addPlan(@ModelAttribute("command") FormBean formBean,
			BindingResult result) {
		
		log.info("$$$ addPlan Starts : PlanController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		try{
		model.put("plans",ConverterClass.prepareListofPlanBean(planService.listPlans()));
		}catch(CustomException ce){
			log.error("addPlan exception : "+ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		}
		log.info("$$$ addPlan Ends : PlanController $$$");
		return new ModelAndView("addPlan", model);
	}

	// Contoller to perform save action and redirect to the home page of Plan
	@RequestMapping(value = "/admin/savePlan.html", method = RequestMethod.POST)
	public ModelAndView savePlan(@ModelAttribute("command") PlanBean planBean,
			BindingResult result) {
		
		log.info("$$$ savePlan Starts : PlanController $$$");
		Plan plan;
		Map<String, Object> model = new HashMap<String, Object>();
		try{
		plan = ConverterClass.preparePlanModel(planBean);
		planService.addPlan(plan);
		}catch(CustomException ce){
			log.error("savePlan exception : "+ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		}
		log.info("$$$ savePlan Ends : PlanController $$$");
		return new ModelAndView("redirect:plan.html");
	}

	// Contoller to perform delete action and to revert the map object
	@RequestMapping(value = "/admin/drop.html", method = RequestMethod.GET)
	public ModelAndView drop(@ModelAttribute("command") PlanBean planBean,
			BindingResult result) {
		
		log.info("$$$ drop() Starts : PlanController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		
		try{
			planService.deletePlan(ConverterClass.preparePlanModel(planBean));
			model.put("plans",ConverterClass.prepareListofPlanBean(planService.listPlans()));
		}catch(CustomException ce){
			log.error("drop exception : "+ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		}
		log.info("$$$ drop() Ends : PlanController $$$");
		return new ModelAndView("addPlan", model);

	}

}
