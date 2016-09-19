package com.o2r.controller;

import java.util.ArrayList;
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
import org.springframework.web.servlet.ModelAndView;

import com.o2r.bean.CustomerBean;
import com.o2r.bean.CustomerDBaseBean;
import com.o2r.helper.CustomException;
import com.o2r.helper.HelperClass;
import com.o2r.model.Order;
import com.o2r.service.CustomerService;
import com.o2r.service.OrderService;

@Controller
public class CustomerController {
	
	@Autowired
	private HelperClass helperClass;	
	@Autowired
	private CustomerService customerService;	
	@Autowired
	private OrderService orderService;

	static Logger log = Logger.getLogger(EventsController.class.getName());
	
	@RequestMapping(value = "/seller/customerList", method = RequestMethod.GET)
	public ModelAndView listCustomer(HttpServletRequest request,@ModelAttribute("command") CustomerBean customerBean, BindingResult result){
		
		log.info("$$$ listCustomer Ends : CustomerController $$$");
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		int pageNo = request.getParameter("page") != null ? Integer
				.parseInt(request.getParameter("page")) : 0;
		List<CustomerDBaseBean> customerBeanList=new ArrayList<CustomerDBaseBean>();
		try {
			sellerId=helperClass.getSellerIdfromSession(request);
			customerBeanList=customerService.listCustomerDB(helperClass.getSellerIdfromSession(request),pageNo);
			if(customerBeanList != null)
			{
				model.put("customers", customerBeanList);
			}
		} catch (Exception e) {
			log.error("Failed by sellerId : "+sellerId, e);
			e.printStackTrace();
		}
		log.info("$$$ listCustomer Ends : CustomerController $$$");
		return new ModelAndView("miscellaneous/customerList",model);
	}
	
	@RequestMapping(value = "/seller/viewCustomerOrders", method = RequestMethod.POST)
	public ModelAndView listCustomerOrders(HttpServletRequest request,@ModelAttribute("command") CustomerBean customerBean, BindingResult result){
		
		log.info("$$$ listCustomerOrders Strats : CustomerController $$$");
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		List<Order> orderList=null;
		
		String email=request.getParameter("email");
		model.put("email",request.getParameter("email"));
		model.put("name",request.getParameter("name"));
		model.put("purchased",Long.parseLong(request.getParameter("purchased")));
		model.put("returned",Long.parseLong(request.getParameter("returned")));
		model.put("revenue",Double.parseDouble(request.getParameter("revenue")));
		model.put("netPurchased",Long.parseLong(request.getParameter("netPurchased")));
		
		try {
			sellerId=helperClass.getSellerIdfromSession(request);
			orderList=orderService.findOrdersbyCustomerDetails("customerEmail", email, helperClass.getSellerIdfromSession(request));
			if(orderList != null){
				model.put("orderList", orderList);
			}
		}catch (CustomException ce) {
			log.error("listCustomerOrders exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		}catch (Exception e) {
			log.error("Failed By Seller ID : "+sellerId, e);
			e.printStackTrace();
		}
		log.info("$$$ listCustomerOrders Ends : CustomerController $$$");
		return new ModelAndView("miscellaneous/viewCustomerOrder",model);
	}
	
	@RequestMapping(value = "/seller/customerBlacklist", method = RequestMethod.GET)
	public ModelAndView blackListCustomer(HttpServletRequest request,@ModelAttribute("command") CustomerBean customerBean, BindingResult result){
		
		log.info("$$$ listCustomer Ends : CustomerController $$$");
		int sellerId = 0;
		int id=Integer.parseInt(request.getParameter("id"));
		Map<String, Object> model = new HashMap<String, Object>();
		int pageNo = request.getParameter("page") != null ? Integer
				.parseInt(request.getParameter("page")) : 0;
		List<CustomerDBaseBean> customerBeanList=new ArrayList<CustomerDBaseBean>();
		try {
			sellerId=helperClass.getSellerIdfromSession(request);
			customerService.changeStatus(id, sellerId);
			customerBeanList=customerService.listCustomerDB(helperClass.getSellerIdfromSession(request),pageNo);
			if(customerBeanList != null)
			{
				model.put("customers", customerBeanList);
			}
		} catch (Exception e) {
			log.error("Failed by sellerId : "+sellerId, e);
			e.printStackTrace();
		}
		log.info("$$$ listCustomer Ends : CustomerController $$$");
		return new ModelAndView("miscellaneous/customerList",model);
	}
}
