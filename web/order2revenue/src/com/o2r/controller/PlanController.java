/*
 * Author Kapil Kumar
 * J2EE Developer
 */
package com.o2r.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.o2r.bean.FormBean;
import com.o2r.bean.PlanBean;
import com.o2r.helper.ConverterClass;
import com.o2r.model.Plan;
import com.o2r.service.PlanService;

@Controller
public class PlanController {
	 @Autowired
	 private PlanService planService;
		 
// Contoller to show the home page of Plan 	
	@RequestMapping("/admin/plan.html")
	 public ModelAndView addPlan(@ModelAttribute("command")FormBean formBean, 
			   BindingResult result){
	  Map<String, Object> model = new HashMap<String, Object>();
	  model.put("plans",  ConverterClass.prepareListofPlanBean(planService.listPlans()));
	  return new ModelAndView("addPlan", model);
	}
	// Contoller to perform save action and redirect to the home page of Plan 	
	@RequestMapping(value = "/admin/savePlan.html", method = RequestMethod.POST)
	public ModelAndView savePlan(@ModelAttribute("command")PlanBean planBean, 
	   BindingResult result) {
		System.out.println("Inside Plan Save");
		System.out.println(" Plan id :"+planBean.getPid());
		System.out.println(" Plan id :"+planBean.getPlanName());
		Plan plan = ConverterClass.preparePlanModel(planBean);
		planService.addPlan(plan);
	  return new ModelAndView("redirect:plan.html");
	 }	
// Contoller to perform delete action and to revert the map object 
	@RequestMapping(value = "/admin/drop.html", method = RequestMethod.GET)
	public ModelAndView drop(@ModelAttribute("command")PlanBean planBean, 
	   BindingResult result) {
		planService.deletePlan(ConverterClass.preparePlanModel(planBean));
		  Map<String, Object> model = new HashMap<String, Object>();
		  model.put("plans",  ConverterClass.prepareListofPlanBean(planService.listPlans()));
		  System.out.println(planBean.getPid());
	return new ModelAndView("addPlan",model);
		
	}


}
