package com.o2r.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.o2r.bean.DashboardBean;
import com.o2r.bean.ManualChargesBean;
import com.o2r.bean.OrderBean;
import com.o2r.bean.SellerBean;
import com.o2r.bean.UploadReportBean;
import com.o2r.bean.ViewDetailsBean;
import com.o2r.helper.ConverterClass;
import com.o2r.helper.CustomException;
import com.o2r.helper.DateDeserializer;
import com.o2r.helper.DateSerializer;
import com.o2r.helper.HelperClass;
import com.o2r.model.GenericQuery;
import com.o2r.model.Order;
import com.o2r.model.Partner;
import com.o2r.model.PaymentUpload;
import com.o2r.model.Product;
import com.o2r.model.Seller;
import com.o2r.model.SellerAccount;
import com.o2r.model.SellerAlerts;
import com.o2r.service.AdminService;
import com.o2r.service.AlertsService;
import com.o2r.service.CategoryService;
import com.o2r.service.DashboardService;
import com.o2r.service.ExpenseService;
import com.o2r.service.OrderService;
import com.o2r.service.PartnerService;
import com.o2r.service.ProductService;
import com.o2r.service.ReportGeneratorService;
import com.o2r.service.SellerAccountService;
import com.o2r.service.SellerService;
import com.o2r.service.TaxDetailService;
import com.o2r.service.ViewDetailsService;

/**
 * @author Deep Mehrotra
 *
 */
@Controller
public class ViewDetailController {

	@Autowired
	private ViewDetailsService viewDetailsService;

	@Autowired
	private HelperClass helperClass;

	private Logger log = Logger.getLogger(ViewDetailController.class);

	/*
	 * static { System.setProperty("mail.smtp.auth", "true");
	 * System.setProperty("mail.smtp.port", "587");
	 * System.setProperty("mail.smtp.socketFactory.port", "587");
	 * System.setProperty("mail.smtp.socketFactory.class",
	 * "javax.net.ssl.SSLSocketFactory");
	 * System.setProperty("mail.smtp.socketFactory.fallback", "false");
	 * System.setProperty("mail.smtp.user","bishnu@order2revenue.com");
	 * System.setProperty("mail.transport.protocol", "smtp");
	 * System.setProperty("mail.smtp.starttls.enable", "true");
	 * System.setProperty("mail.smtp.host", "smtp.gmail.com");
	 * System.setProperty("mail.smtp.quitwait", "false"); }
	 */

	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	@RequestMapping(value = "/seller/viewDetails", method = RequestMethod.GET)
	public ModelAndView getTopSKUDetailsToday(HttpServletRequest request,
			@ModelAttribute("command") ViewDetailsBean viewDetailsBean,
			BindingResult result) {

		log.info("$$$ viewDetailsCharge Starts : ViewDetailsController $$$");

		List<ViewDetailsBean> viewDetailsDbeanList = new ArrayList<ViewDetailsBean>();

		int sellerId = 1;
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			sellerId = helperClass.getSellerIdfromSession(request);

			viewDetailsDbeanList = viewDetailsService.getTopSKUDetailsToday(
					viewDetailsBean.getStartDate(),
					viewDetailsBean.getEndDate(), sellerId);

			viewDetailsDbeanList.add(viewDetailsBean);
			model.put("productList", viewDetailsDbeanList);

			System.out.println("Jagadeesha" + viewDetailsDbeanList);
		} catch (CustomException ce) {
			log.error("viewDetails exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed! by seller Id : " + sellerId, e);
			model.put("errorMessage", e.getCause());
			return new ModelAndView("globalErorPage", model);
		}
		log.info("$$$ viewDetails Ends : ViewDetailsController $$$");
		return new ModelAndView("/viewDetails", model);
	}

	@RequestMapping(value = "/seller/quaterlyindex", method = RequestMethod.POST)
	public ModelAndView getTopSKUDetailsQuarterly(HttpServletRequest request,
			@ModelAttribute("command") ViewDetailsBean viewDetailsBean,
			BindingResult result) {

		System.out.println("I am in Quarterly ");

		log.info("$$$ viewDetailsCharge Starts : ViewDetailsController $$$");

		List<ViewDetailsBean> viewDetailsDbeanList = new ArrayList<ViewDetailsBean>();

		int sellerId = 1;
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			String selectedQuarter = request.getParameter("Quarter");

			System.out.println(" The selected qurter"+selectedQuarter);
			
			viewDetailsDbeanList = viewDetailsService
					.getTopSKUDetailsQuarterly(viewDetailsBean.getStartDate(),
							viewDetailsBean.getEndDate(), sellerId,
							selectedQuarter);

			viewDetailsDbeanList.add(viewDetailsBean);
			model.put("productList", viewDetailsDbeanList);
			
		} catch (CustomException ce) {
			log.error("viewDetails Quarterly exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed! by seller Id : " + sellerId, e);
			model.put("errorMessage", e.getCause());
			return new ModelAndView("globalErorPage", model);
		}
		log.info("$$$ Quarterly Ends : ViewDetailsController $$$");
		return new ModelAndView("/viewDetails", model);
	}

	@RequestMapping(value = "/seller/monthindex", method = RequestMethod.POST)
	public ModelAndView getTopSKUDetailsMonthly(HttpServletRequest request,
			@ModelAttribute("command") ViewDetailsBean viewDetailsBean,
			BindingResult result) {

		System.out.println("I am in monthly ");
		log.info("$$$ viewDetailsCharge Starts : ViewDetailsController $$$");

		List<ViewDetailsBean> viewDetailsDbeanList = new ArrayList<ViewDetailsBean>();
		String selectedmonth = request.getParameter("month");

		int sellerId = 1;
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			sellerId = helperClass.getSellerIdfromSession(request);

			viewDetailsDbeanList = viewDetailsService.getTopSKUDetailsMonthly(
					viewDetailsBean.getStartDate(),
					viewDetailsBean.getEndDate(), sellerId, selectedmonth);

			viewDetailsDbeanList.add(viewDetailsBean);
			model.put("productList", viewDetailsDbeanList);

			System.out.println("Jagadeesha" + viewDetailsDbeanList);
		} catch (CustomException ce) {
			log.error("viewDetails exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed! by seller Id : " + sellerId, e);
			model.put("errorMessage", e.getCause());
			return new ModelAndView("globalErorPage", model);
		}
		log.info("$$$ ViewDetails Ends : ViewDetailsController $$$");
		return new ModelAndView("/viewDetails", model);
	}

	
	
	@RequestMapping(value = "/seller/quaterlyindex1", method = RequestMethod.POST)
	public ModelAndView getTopSKUDetailsAnnualy(HttpServletRequest request,
			@ModelAttribute("command") ViewDetailsBean viewDetailsBean,
			BindingResult result) {

		System.out.println("I am in Annually ");

		log.info("$$$ Annually SKU Details Starts : Annually SKU Details $$$");

		List<ViewDetailsBean> viewDetailsDbeanList = new ArrayList<ViewDetailsBean>();

		int sellerId = 1;
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			String selectedYear = request.getParameter("Year");

			
			
			viewDetailsDbeanList = viewDetailsService
					.getTopSKUDetailsAnnualy(viewDetailsBean.getStartDate(),
							viewDetailsBean.getEndDate(), sellerId,
							selectedYear);

			viewDetailsDbeanList.add(viewDetailsBean);
			model.put("productList", viewDetailsDbeanList);
			
		} catch (CustomException ce) {
			log.error("viewDetails Quarterly exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed! by seller Id : " + sellerId, e);
			model.put("errorMessage", e.getCause());
			return new ModelAndView("globalErorPage", model);
		}
		log.info("$$$ Quarterly Ends : ViewDetailsController $$$");
		return new ModelAndView("/viewDetails", model);
	}
	
	@RequestMapping(value = "/seller/topSkuCityDetails", method = RequestMethod.GET)
	public ModelAndView getTopSKUCityDetails(HttpServletRequest request,
			@ModelAttribute("command") ViewDetailsBean viewDetailsBean,
			BindingResult result) {

		log.info("$$$ viewDetailsCharge Starts : ViewDetailsController $$$");

		List<ViewDetailsBean> viewDetailsDbeanList = new ArrayList<ViewDetailsBean>();

		int sellerId = 1;
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			sellerId = helperClass.getSellerIdfromSession(request);

			viewDetailsDbeanList = viewDetailsService.getTopSKUCityDetails(
					viewDetailsBean.getStartDate(),
					viewDetailsBean.getEndDate(), sellerId);

			viewDetailsDbeanList.add(viewDetailsBean);
			model.put("productList", viewDetailsDbeanList);

			System.out.println("Jagadeesha" + viewDetailsDbeanList);
		} catch (CustomException ce) {
			log.error("ViewDetails exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed! by seller Id : " + sellerId, e);
			model.put("errorMessage", e.getCause());
			return new ModelAndView("globalErorPage", model);
		}
		log.info("$$$ ViewDetails Ends : ViewDetailsController $$$");
		return new ModelAndView("/topSkuCityDetails", model);
	}

}
