package com.o2r.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.web.servlet.ModelAndView;

import com.o2r.bean.ExpenseBean;
import com.o2r.bean.ProductBean;
import com.o2r.bean.TaxDetailBean;
import com.o2r.bean.TaxablePurchaseBean;
import com.o2r.helper.ConverterClass;
import com.o2r.helper.CustomException;
import com.o2r.helper.GlobalConstant;
import com.o2r.helper.HelperClass;
import com.o2r.model.ExpenseCategory;
import com.o2r.model.Expenses;
import com.o2r.model.Product;
import com.o2r.model.TaxablePurchases;
import com.o2r.service.ExpenseService;
import com.o2r.service.TaxDetailService;
import com.o2r.service.TaxablePurchaseService;

/**
 * @author Deep Mehrotra
 *
 */
@Controller
public class TaxablePurchaseController {

	@Autowired
	private TaxablePurchaseService taxablePurchaseService;

	@Autowired
	private TaxDetailService taxDetailService;

	@Autowired
	private HelperClass helperClass;

	static Logger log = Logger.getLogger(AdminController.class.getName());

	@RequestMapping(value = "/seller/listTaxablePurchases", method = RequestMethod.GET)
	public ModelAndView listTaxablePurchases(HttpServletRequest request,
			@ModelAttribute("command") TaxablePurchases taxablePurchases,
			BindingResult result) {

		log.info("$$$ listPurchases Starts : PurchaseControl $$$");
		int sellerId = 65;
		Map<String, Object> model = new HashMap<String, Object>();

		List<TaxablePurchases> taxablePurchasesDbeanList = new ArrayList<TaxablePurchases>();

		try {

			sellerId = helperClass.getSellerIdfromSession(request);

			taxablePurchasesDbeanList = taxablePurchaseService
					.listTaxablePurchase(sellerId);

			model.put("productList", taxablePurchasesDbeanList);

		} catch (CustomException ce) {
			log.error("listExpense exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by Seller Id : " + sellerId, e);
		}
		log.info("$$$ listPurchases Ends : PurchaseController $$$");
		return new ModelAndView("initialsetup/listTaxablePurchases", model);
	}

	@RequestMapping(value = "/seller/addTaxablePurchases", method = RequestMethod.GET)
	public String addTaxablePurchase(HttpServletRequest request,
			@ModelAttribute("command") TaxablePurchaseBean taxablePurchaseBean,
			BindingResult result) {

		return "selleraccount/addTaxablePurchases";
	}

	@RequestMapping(value = "/seller/saveTaxablePurchases", method = RequestMethod.GET)
	public ModelAndView saveTaxablePurchase(HttpServletRequest request,
			@ModelAttribute("command") TaxablePurchaseBean taxablePurchaseBean,
			BindingResult result) {

		log.info("$$$ saveExpense Starts : ExpenseController $$$");
		int sellerId =0;
		Map<String, Object> model = new HashMap<String, Object>();
		TaxablePurchases taxablePurchases;

		try {
			sellerId = helperClass.getSellerIdfromSession(request);

			String selCategory = request.getParameter("taxCategory");						
			taxablePurchaseBean.setTaxAmount(taxablePurchaseBean.getBasicPrice()*(taxablePurchaseBean.getTaxRate()/100));	
			taxablePurchaseBean.setTotalAmount(taxablePurchaseBean.getTaxAmount()+taxablePurchaseBean.getBasicPrice());			 
			taxablePurchaseBean.setTaxCategory(selCategory);
			taxablePurchaseBean.setSellerId(sellerId);
			taxablePurchases = ConverterClass
					.prepareTaxablePurchasesModel(taxablePurchaseBean);
			taxablePurchaseService.addTaxablePurchase(taxablePurchases,
					sellerId);
		} catch (CustomException ce) {
			log.error("saveExpense exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by Seller Id : " + sellerId, e);
		}
		log.info("$$$ saveExpense Ends : ExpenseController $$$");
		return new ModelAndView("redirect:/seller/listTaxablePurchases.html");
	}

	@RequestMapping(value = "/seller/edittaxablepurchase", method = RequestMethod.GET)
	public ModelAndView editTaxablepurchase(HttpServletRequest request,
			@ModelAttribute("command") TaxablePurchaseBean taxablePurchaseBean,
			BindingResult result) {

		log.info("$$$ editProduct Starts : ProductController $$$");
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			if (request.getParameter("id") != null) {
				int productId = Integer.parseInt(request.getParameter("id"));
				taxablePurchaseBean = taxablePurchaseService
						.getTaxProduct(productId);
				/*
				 * taxablePurchases = ConverterClass
				 * .prepareTaxablePurchasesModel(taxablePurchaseBean);
				 * taxablePurchaseService
				 * .addPurchase(taxablePurchases,sellerId);
				 */

			}
		} catch (CustomException ce) {
			log.error("editProduct exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			log.error("Failed By seller ID :" + sellerId, e);
		}
		if (taxablePurchaseBean != null)
			model.put("taxablePurchases", taxablePurchaseBean);
		log.info("$$$ editProduct Ends : ProductController $$$");
		return new ModelAndView("selleraccount/addTaxablePurchases", model);
	}

	@RequestMapping(value = "/seller/editPurchaseTaxables", method = RequestMethod.GET)
	public ModelAndView editPurchaseTaxables(HttpServletRequest request,
			@ModelAttribute("command") TaxablePurchaseBean taxablePurchaseBean,
			BindingResult result) {

		log.info("$$$ editExpenseCategory Starts : ExpenseController $$$");
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		TaxablePurchases taxablePurchases;

		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			taxablePurchases = ConverterClass
					.prepareTaxablePurchasesModel(taxablePurchaseBean);
			taxablePurchaseService.addTaxablePurchase(taxablePurchases,
					sellerId);

		} catch (CustomException ce) {
			log.error("editExpenseCategory exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by Seller Id : " + sellerId, e);
		}
		log.info("$$$ editExpenseCategory Ends : ExpenseController $$$");
		return new ModelAndView("redirect:/seller/listTaxablePurchases.html",
				model);

	}

	@RequestMapping(value = "/seller/deletePurchaseTaxables", method = RequestMethod.GET)
	public ModelAndView deletePurchaseTaxables(HttpServletRequest request,
			@ModelAttribute("command") TaxablePurchaseBean taxablePurchaseBean,
			BindingResult result) {

		log.info("$$$ deleteProduct Starts : ProductController $$$");
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		String status = "";
		String taxCategory = "";

		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			if (request.getParameter("id") != null) {
				int productId = Integer.parseInt(request.getParameter("id"));
				// status=productService.deleteProduct(productId,helperClass.getSellerIdfromSession(request));
				taxablePurchaseService.deleteTaxablePurchase(productId,
						sellerId);

				/*
				 * if(status.equals("Deleted")){
				 * log.info("********* Delete Product !!!!"); }else{
				 * log.info("********* Can't Delete Product !!!!"); }
				 */
			}
			request.getSession().setAttribute("deleteStatus", status);
		} catch (CustomException ce) {
			log.error("DeleteProduct exception : " + ce.toString());
			model.put("errorMessage", "Error in Delete Product");
			model.put("errorTime", new Date());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			log.error("Failed By seller ID :" + sellerId, e);
		}
		log.info("$$$ deleteProduct Ends : ProductController $$$");
		return new ModelAndView("redirect:/seller/listTaxablePurchases.html",
				model);
	}

	@RequestMapping(value = "/seller/taxDetailsOnMonth", method = RequestMethod.GET)
	public ModelAndView listtaxDetailsOnMonth(HttpServletRequest request,
			@ModelAttribute("command") TaxDetailBean taxDetailBean,
			BindingResult result) {

		log.info("$$$ listPurchases Starts : PurchaseControl $$$");
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();

		List<TaxDetailBean> taxablePurchasesDbeanList = new ArrayList<TaxDetailBean>();

		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			String sDate = request.getParameter("single");
			String eDate = request.getParameter("second");			
			
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");				
			Calendar cal = Calendar.getInstance();
			
			Date startDate;
			Date endDate;
			
			int month = cal.get(Calendar.MONTH);
			int year = cal.get(Calendar.YEAR);

			if (sDate == null || eDate == null) {
				if (month >= 3 && month <= 11) {
					System.out.println(" I am in if ");
					sDate = "04/01/" + year;
					eDate = "03/31/" + (year + 1);
				} else {
					System.out.println(" I am in else ");
					sDate = "04/01/" + (year - 1);
					eDate = "03/31/" + year;
				}
				startDate = df.parse(sDate);
				endDate = df.parse(eDate);
			}else{
				startDate = new SimpleDateFormat("MM/dd/yyyy").parse(request.getParameter("single"));
				endDate = new SimpleDateFormat("MM/dd/yyyy").parse(request.getParameter("second"));
			}			
			
			taxablePurchasesDbeanList = taxDetailService.listtaxDetailsOnMonth(
					sellerId, startDate, endDate);

			model.put("productList", taxablePurchasesDbeanList);
			model.put("starDate", sDate);
			model.put("endDate", eDate);

		} catch (CustomException ce) {
			log.error("listExpense exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by Seller Id : " + sellerId, e);
		}
		log.info("$$$ listPurchases Ends : PurchaseController $$$");
		return new ModelAndView("initialsetup/taxDetailsOnMonth", model);
	}
	
	
	
	@RequestMapping(value = "/seller/viewVatTaxDetails", method = RequestMethod.GET)
	public String viewVatTaxDetails(HttpServletRequest request,
			@ModelAttribute("command") TaxablePurchases taxablePurchases,
			BindingResult result) {

		log.info("$$$ listPurchases Starts : PurchaseControl $$$");
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();

		List<TaxDetailBean> taxablePurchasesDbeanList = new ArrayList<TaxDetailBean>();

		
		System.out.println("view tax details");
		try {

			sellerId = helperClass.getSellerIdfromSession(request);

			
			String idvale= request.getParameter("id");
			System.out.println(" PASSED VALUE"+idvale);
			
			//taxablePurchasesDbeanList = taxDetailService.getVatTaxDetails(sellerId);

			model.put("productList", taxablePurchasesDbeanList);

		} catch (CustomException ce) {
			log.error("listExpense exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
		//	return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by Seller Id : " + sellerId, e);
		}
		log.info("$$$ listPurchases Ends : PurchaseController $$$");
		// return new ModelAndView("miscellaneous/purchasesDetails", model);
		return "selleraccount/addTaxablePurchases";
		//return new ModelAndView("initialsetup/listTaxablePurchases", model);
	}
	

}