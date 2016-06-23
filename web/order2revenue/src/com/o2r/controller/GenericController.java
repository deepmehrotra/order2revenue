package com.o2r.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.attribute.standard.JobSheets;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.o2r.bean.DashboardBean;
import com.o2r.bean.OrderBean;
import com.o2r.bean.SellerBean;
import com.o2r.bean.UploadReportBean;
import com.o2r.helper.ConverterClass;
import com.o2r.helper.CustomException;
import com.o2r.helper.DateDeserializer;
import com.o2r.helper.DateSerializer;
import com.o2r.helper.HelperClass;
import com.o2r.model.GenericQuery;
import com.o2r.model.Order;
import com.o2r.model.Seller;
import com.o2r.schedulers.SampleJob;
import com.o2r.service.AdminService;
import com.o2r.service.CategoryService;
import com.o2r.service.DashboardService;
import com.o2r.service.ExpenseService;
import com.o2r.service.OrderService;
import com.o2r.service.PartnerService;
import com.o2r.service.ProductService;
import com.o2r.service.ReportGeneratorService;
import com.o2r.service.SellerService;
import com.o2r.service.TaxDetailService;

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
	private ReportGeneratorService reportGeneratorService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private AdminService adminService;
	@Autowired
	 private HelperClass helperClass;
	@Autowired
	 private CategoryService categoryService;
	@Autowired
	 private ProductService productService;
	@Autowired
	 private TaxDetailService taxDetailService;
	@Autowired
	 private PartnerService partnerService;
	@Autowired
	 private ExpenseService expenseService;
	

	private Logger logger = Logger.getLogger(GenericController.class);
		
	/*static
	{
	     System.setProperty("mail.smtp.auth", "true");
	     System.setProperty("mail.smtp.port", "587");
	     System.setProperty("mail.smtp.socketFactory.port", "587");
	     System.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	     System.setProperty("mail.smtp.socketFactory.fallback", "false");
	     System.setProperty("mail.smtp.user","bishnu@order2revenue.com");
	     System.setProperty("mail.transport.protocol", "smtp");
	     System.setProperty("mail.smtp.starttls.enable", "true");
	     System.setProperty("mail.smtp.host", "smtp.gmail.com");	     
	     System.setProperty("mail.smtp.quitwait", "false");
	}*/
	
	@RequestMapping(value = "/seller/support", method = RequestMethod.GET)
	public ModelAndView support(HttpServletRequest request) {
		
		System.out.println("Inside support ..... ");		
		Map<String, Object> model = new HashMap<String, Object>();
		String section=request.getParameter("set");
		System.out.println(section);
		model.put("setSection", section);
		return new ModelAndView("support", model);

	}
	
	
	@RequestMapping(value = "/seller/orderindex", method = RequestMethod.GET)
	public ModelAndView welcome(HttpServletRequest request) {
		
		logger.info("$$$ welcome Starts : GenericController $$$");
		logger.info("catalina.base : " + System.getProperty("catalina.base"));
		Map<String, Object> model = new HashMap<String, Object>();
		Seller seller = null;
		try {
			seller = sellerService.getSeller(request.getUserPrincipal()
					.getName());
		} catch (CustomException ce) {
			logger.error("OrderIndex:welcome exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("failed!",e);
		}
		if (seller != null) {
			request.getSession().setAttribute("sellerId", seller.getId());
			logger.debug(" Afterlogin"+ request.getUserPrincipal().getName());
			logger.info("$$$ welcome Ends : GenericController $$$");
			return new ModelAndView("redirect:/seller/dashboard.html");
		} else {
			logger.info("$$$ welcome Ends : GenericController $$$");
			return new ModelAndView("redirect:/login_register.html");
		}

	}	
	
	@RequestMapping(value = "/seller/dashboard", method = RequestMethod.GET)
	public ModelAndView displayDashboard(HttpServletRequest request,
			@ModelAttribute("command") DashboardBean dashboardBean,
			BindingResult result) {
		
		logger.info("$$$ displayDashboard Starts : GenericController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		DashboardBean dbean=null;
		Seller seller=null;
		try{
		seller=sellerService.getSeller(helperClass.getSellerIdfromSession(request));
		SellerBean sellerBean=ConverterClass.prepareSellerBean(seller);
		dbean = dashboardService.getDashboardDetails(helperClass.getSellerIdfromSession(request));
		model.put("sellerBean", sellerBean);
		model.put("dashboardValue", dbean);
		List<UploadReportBean> uploadReports = ConverterClass.prepareUploadReportListBean(
				reportGeneratorService.listUploadReport(helperClass.getSellerIdfromSession(request)));
		if (uploadReports != null && uploadReports.size() > 3) {
			uploadReports = uploadReports.subList(uploadReports.size() - 3, uploadReports.size());
		}
		model.put("uploadReportList", uploadReports);
		
		}catch(CustomException ce){
			logger.error("displayDashboard exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Failed!",e);
		}
		logger.info("$$$ displayDashboard Ends : GenericController $$$");
		return new ModelAndView("landing", model);
	}
	
	@ResponseBody
	@RequestMapping(value = "/seller/getUploadReports", method = RequestMethod.GET)
	public String getUploadReports(HttpServletRequest request) {
		
		logger.info("$$$ getUploadReports Starts : GenericController $$$");
		List<UploadReportBean> uploadReports = null;
		Gson gson = null;
		try{
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
			gsonBuilder.registerTypeAdapter(Date.class, new DateSerializer());
			gson = gsonBuilder.setPrettyPrinting().create();
			
			uploadReports = ConverterClass.prepareUploadReportListBean(
					reportGeneratorService.listUploadReport(helperClass.getSellerIdfromSession(request)));
			if (uploadReports != null && uploadReports.size() > 3) {
				uploadReports = uploadReports.subList(uploadReports.size() - 3, uploadReports.size());
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Failed!",e);
		}
		logger.info("$$$ getUploadReports Ends : GenericController $$$");
		return gson.toJson(uploadReports);
	}

	@RequestMapping(value = "/seller/initialsetup", method = RequestMethod.GET)
	public String initialsetup(HttpServletRequest request) {
		logger.debug(" Inside initialsetup ");
		return "initialsetup/initialsetup";
	}

	@RequestMapping(value = "/seller/productInfo", method = RequestMethod.GET)
	public String productInfo(HttpServletRequest request) {
		logger.debug(" Inside productInfo ");
		return "initialsetup/productInfo";
	}

	@RequestMapping(value = "/seller/partnerInfo", method = RequestMethod.GET)
	public String partnerInfo(HttpServletRequest request) {
		logger.debug(" Inside opartner info ");
		return "initialsetup/partnerInfo";
	}

	@RequestMapping(value = "/seller/dailyactivities", method = RequestMethod.GET)
	public String dailyactivities(HttpServletRequest request) {
		logger.debug(" Inside initialsetup ");
		return "dailyactivities/dailyactivities";
	}

	@RequestMapping(value = "/login-form", method = RequestMethod.GET)
	public String redirectlogin() {
		logger.debug(" Inside login-form ");		
		return "login_register";
	}

	@RequestMapping(value = "/seller/findGlobalOrders", method = RequestMethod.POST)
	public ModelAndView findGlobalOrders(HttpServletRequest request,
			@ModelAttribute("command") OrderBean orderBean, BindingResult result) {
		
		logger.info("$$$ findGlobalOrders Starts : GenericController $$$");
		List<OrderBean> orderlist = null;
		List<Order> temporaryorderlist = null;
		Map<String, Object> model = new HashMap<String, Object>();
		Date startDate = null;
		Date endDate = null;
		int sellerId;
		String searchCriteria = null;
		String searchString =null;
		
		try{		
		sellerId = helperClass.getSellerIdfromSession(request);
		searchCriteria = request.getParameter("searchCriteria");
		searchString = request.getParameter("searchString");
		
		if (request.getParameter("startDate") != null
				&& request.getParameter("endDate") != null
				&& (request.getParameter("startDate").length() != 0)
				&& (request.getParameter("endDate").length() != 0)) {
			startDate = new Date(request.getParameter("startDate"));
			endDate = new Date(request.getParameter("endDate"));
			orderlist = new ArrayList<OrderBean>();
		}

		logger.debug(" Values in global search header :searchCriteria : "
				+ searchCriteria + "  -->searchString : " + searchString + " "
				+ "startDate : " + startDate + " endDate  " + endDate);

		if(searchString != null && searchString.length() != 0){
			if (searchCriteria.equals("customerName")
					|| searchCriteria.equals("customerCity")
					|| searchCriteria.equals("customerEmail")
					|| searchCriteria.equals("customerPhnNo")) {
				orderlist = ConverterClass
						.prepareListofBean(orderService.findOrdersbyCustomerDetails(
								searchCriteria, searchString,sellerId));
			}else{				
				orderlist = ConverterClass.prepareListofBean(orderService.findOrders(searchCriteria, searchString, sellerId, false,true));
			}
		}else{	
			if(searchCriteria.equalsIgnoreCase("dateofPayment") && startDate != null && endDate != null){
				temporaryorderlist = orderService.findOrdersbyPaymentDate(
						searchCriteria, startDate, endDate,sellerId);			
					
			}else {
				if(searchString != null && startDate != null && endDate != null){
					temporaryorderlist = orderService.findOrdersbyDate(searchCriteria,startDate, endDate, sellerId, false);
				}
			}
			
			if (temporaryorderlist != null && temporaryorderlist.size() != 0)
				orderlist = ConverterClass.prepareListofBean(temporaryorderlist);
		}		
		model.put("searchOrderList", orderlist);
		}catch(CustomException ce){
			logger.error("findGlobalOrders exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("Failed!",e);
		}
		logger.info("$$$ findGlobalOrders Ends : GenericController $$$");
		return new ModelAndView("globalOrderSearch", model);
	}

	@RequestMapping(value = "/userquery", method = RequestMethod.POST)
	public @ResponseBody String saveQuery(HttpServletRequest request) {

		logger.info("$$$ saveQuery Starts : GenericController $$$");
		String email = request.getParameter("email");
		String name = request.getParameter("name");
		String query = request.getParameter("query");
		GenericQuery gq = new GenericQuery();
		try {
			if (email != null) {
				gq.setEmail(email);
				if (name != null)
					gq.setName(name);
				if (query != null)
					gq.setQueryText(query);
				gq.setQueryTime(new Date());
				adminService.addQuery(gq);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Failed!",e);
			return "false";
		}
		logger.info("$$$ saveQuery Ends : GenericController $$$");
		return "true";
	}
	
	@RequestMapping(value = "/seller/getSetupStatus", method = RequestMethod.POST)
	public @ResponseBody String getSetupStatus(HttpServletRequest request) {

		logger.info("$$$ getSetupStatus Starts : GenericController $$$");
		Seller seller=null;
		try {
			seller=sellerService.getSeller(helperClass.getSellerIdfromSession(request));
			if(seller.getStateDeliveryTime()==null)
			{
				return "1:0:100";
			}
			else if(orderService.listOrders(helperClass.getSellerIdfromSession(request))!=null)
			{
				return "7:100:0";
			}
			else if(categoryService.listCategories(helperClass.getSellerIdfromSession(request))!=null)
			{
				return "2:15:85";
			}
			else if(productService.listProducts(helperClass.getSellerIdfromSession(request))!=null)
			{
				return "3:30:70";
			}
			else if(taxDetailService.listTaxCategories(helperClass.getSellerIdfromSession(request))!=null)
			{
				return "4:45:55";
			}
			else if(partnerService.listPartners(helperClass.getSellerIdfromSession(request))!=null)
			{
				return "5:75:25";
			}
			else if(productService.getProductwithProductConfig(helperClass.getSellerIdfromSession(request)))
			{
				return "6:90:10";
			}
			else if(expenseService.listExpenseCategories(helperClass.getSellerIdfromSession(request))!=null)
			{
				return "7:100:0";
			}
				
		}catch (CustomException e) {
			e.printStackTrace();
			logger.error("Failed!",e);
			
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Failed!",e);
			
		}
		logger.info("$$$ saveQuery Ends : GenericController $$$");
		return "true";
	}
}
