package com.o2r.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.o2r.bean.DashboardBean;
import com.o2r.bean.OrderBean;
import com.o2r.helper.ConverterClass;
import com.o2r.helper.HelperClass;
import com.o2r.model.GenericQuery;
import com.o2r.model.Order;
import com.o2r.model.Seller;
import com.o2r.service.AdminService;
import com.o2r.service.DashboardService;
import com.o2r.service.OrderService;
import com.o2r.service.SellerService;


/**
 * @author Deep Mehrotra
 *
 */
@Controller
public class GenericController {


 @Autowired
 private SellerService sellerService;

 @Autowired
 private DashboardService dashboardService;
 @Autowired
 private OrderService orderService;
 @Autowired
 private AdminService adminService;

 private Logger logger = Logger.getLogger(GenericController.class);

@RequestMapping(value = "/seller/orderindex", method = RequestMethod.GET)
public ModelAndView welcome(HttpServletRequest request) {
	System.out.println(" Inside order index ");
	logger.info("nside order index");
	logger.info("catalina.base : "+System.getProperty("catalina.base"));
	Seller seller=sellerService.getSeller(request.getUserPrincipal().getName());
	if(seller!=null)
	{
	request.getSession().setAttribute( "sellerId", seller.getId() );

	System.out.println(" Afterlogin"+request.getUserPrincipal().getName());
  //return "landing";

	return new ModelAndView("redirect:/seller/dashboard.html");
	}
	else
	{
		return new ModelAndView("redirect:/login-form.html");
	}
 }

@RequestMapping(value = "/seller/dashboard", method = RequestMethod.GET)
public ModelAndView displayDashboard(HttpServletRequest request,@ModelAttribute("command")DashboardBean dashboardBean,
          BindingResult result) {
    System.out.println("Inside dashboard");
    Map<String, Object> model = new HashMap<String, Object>();
    DashboardBean dbean=dashboardService.getDashboardDetails(HelperClass.getSellerIdfromSession(request));
    model.put("dashboardValue",dbean);

  return new ModelAndView("landing",model);
 }

@RequestMapping(value = "/seller/initialsetup", method = RequestMethod.GET)
public String initialsetup(HttpServletRequest request) {
	System.out.println(" Inside initialsetup ");
/*	System.out.println(" Afterlogin"+request.getUserPrincipal().getName());*/
  return "initialsetup/initialsetup";
 }

@RequestMapping(value = "/seller/productInfo", method = RequestMethod.GET)
public String productInfo(HttpServletRequest request) {
	System.out.println(" Inside productInfo ");
/*	System.out.println(" Afterlogin"+request.getUserPrincipal().getName());*/
  return "initialsetup/productInfo";
 }


@RequestMapping(value = "/seller/partnerInfo", method = RequestMethod.GET)
public String partnerInfo(HttpServletRequest request) {
	System.out.println(" Inside opartner info ");
/*	System.out.println(" Afterlogin"+request.getUserPrincipal().getName());*/
  return "initialsetup/partnerInfo";
 }

@RequestMapping(value = "/seller/dailyactivities", method = RequestMethod.GET)
public String dailyactivities(HttpServletRequest request) {
	System.out.println(" Inside initialsetup ");
/*	System.out.println(" Afterlogin"+request.getUserPrincipal().getName());*/
  return "dailyactivities/dailyactivities";
 }

@RequestMapping(value = "/login-form", method = RequestMethod.GET)
public String redirectlogin() {
	System.out.println(" Inside login-form ");
/*	System.out.println(" Afterlogin"+request.getUserPrincipal().getName());*/
  return "login-form";
 }

@RequestMapping(value = "/seller/findGlobalOrders", method = RequestMethod.POST)
public ModelAndView findGlobalOrders(HttpServletRequest request,@ModelAttribute("command")OrderBean orderBean,
   BindingResult result) {
	System.out.println(" Insode global search order");
	   List<OrderBean> orderlist=null;
	  List<Order> temporaryorderlist=null;
	  Map<String, Object> model = new HashMap<String, Object>();
	  Date startDate=null;
	  Date endDate=null;
	  int sellerId=HelperClass.getSellerIdfromSession(request);
	  String searchCriteria=request.getParameter("searchCriteria");
	  String searchString=request.getParameter("searchstring");
	  if(request.getParameter("startDate")!=null&&request.getParameter("endDate")!=null&&(request.getParameter("startDate").length()!=0)&&
			  (request.getParameter("endDate").length()!=0))
	  {
		  startDate=new Date(request.getParameter("startDate"));
		  endDate=new Date(request.getParameter("endDate"));
	  }

System.out.println(" Values in global search header :searchCriteria : "+searchCriteria+"  -->searchString : "+searchString+" "
		+ "startDate : "+startDate+" endDate  "+endDate);
	  if(searchCriteria.equals("channelOrderID")&&searchString!=null)
	  {
			  System.out.println(" Inside search find channel order id orders");
			   orderlist=ConverterClass.prepareListofBean(orderService.findOrders(searchCriteria, searchString, sellerId));

	  }
	  else   if(searchCriteria.equals("orderDate")&&startDate!=null&&endDate!=null)
	  {
		 orderlist=new ArrayList<OrderBean>();
			  temporaryorderlist=orderService.findOrdersbyDate(searchCriteria, startDate, endDate,sellerId);
			if(temporaryorderlist!=null&&temporaryorderlist.size()!=0)
		  orderlist= ConverterClass.prepareListofBean(temporaryorderlist);

	  }
System.out.println(" Search order list :  "+orderlist);
	  model.put("searchOrderList", orderlist);
	  return new ModelAndView("globalOrderSearch", model);
 }

@RequestMapping(value = "/userquery", method = RequestMethod.POST)
public @ResponseBody String saveQuery(HttpServletRequest request) {

	logger.info("nside saveQuery");
	String email=request.getParameter("email");
		String name=request.getParameter("name");
		String query=request.getParameter("query");
		GenericQuery gq=new GenericQuery();
		try
		{
	if(email!=null)
	{
		gq.setEmail(email);
		if(name!=null)
			gq.setName(name);
		if(query!=null)
			gq.setQueryText(query);
		gq.setQueryTime(new Date());

		adminService.addQuery(gq);
	}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error(e);
			return "false";
		}

return "true";
 }
}