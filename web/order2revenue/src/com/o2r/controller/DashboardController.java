package com.o2r.controller;

import java.awt.Robot;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.o2r.dao.DashboardDaoImpl;
import com.o2r.helper.HelperClass;
import com.o2r.model.AccountTransaction;
import com.o2r.service.CategoryService;
import com.o2r.service.DashboardService;
import com.o2r.service.DetailedDashboardService;
import com.o2r.service.PartnerService;
import com.o2r.service.ProductService;

@Controller
public class DashboardController {
	
	@Autowired
	private PartnerService partnerService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private HelperClass helperClass;
	@Autowired
	private ProductService productService;
	@Autowired
	private DetailedDashboardService detailedDashboardService;
		
	static Logger log = Logger.getLogger(DashboardController.class.getName());	
	
	@RequestMapping(value = "/seller/showDetailed", method = RequestMethod.GET)
	public ModelAndView showDetailedDashboard() {		
		return new ModelAndView("detailedDashboard");
	}
	
	@RequestMapping(value = "/seller/getGrossMargin", method = RequestMethod.GET)
	public @ResponseBody String getGrossMargin(HttpServletRequest request) {

		log.info("$$$ getGrossMargin Starts : DashboardController $$$");
		Map<String, Date> dateMap = new HashMap<String, Date>();		
		Date startDate = new Date();
		Date endDate = new Date();
		int sellerId = 0;	
		Map<String, Object> grossMargin = new HashMap<String, Object>();		
		Gson gson = null;
		String period = "ALL";
		String status = request.getParameter("status");		
		try {
			sellerId = helperClass.getSellerIdfromSession(request);	
			double gM = 0;
			double nM = 0;
			double badQV= 0;
			if(status != null && !status.equals("")){
				period = status;
			}
			dateMap = setDate(period);
			if(dateMap.size() != 0){
				startDate = dateMap.get("startDate");
				endDate = dateMap.get("endDate");
			}
			GsonBuilder gsonBuilder = new GsonBuilder();			
			gson = gsonBuilder.setPrettyPrinting().create();
			gM = detailedDashboardService.getGrossGrossProfit(startDate, endDate, sellerId);
			nM = detailedDashboardService.getNetGrossProfit(startDate, endDate, sellerId);
			badQV = detailedDashboardService.getGrossBadquantityValue(startDate, endDate, sellerId);
			grossMargin.put("grossMargin", Math.round(gM*100)/100);
			grossMargin.put("netGrossProfit", Math.round(nM*100)/100);
			grossMargin.put("returnGrossProfit", Math.round((gM-nM)*100)/100);
			grossMargin.put("badQuantityValue", Math.round((badQV)*100)/100);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by seller ID : " + sellerId, e);
		}
		log.info("$$$ getGrossMargin Ends : DashboardController $$$");
		return gson.toJson(grossMargin);
	}
	
	@RequestMapping(value = "/seller/getSaleQuantity", method = RequestMethod.GET)
	public @ResponseBody String getSaleQuantity(HttpServletRequest request) {

		log.info("$$$ getSaleQuantity Starts : DashboardController $$$");
		Map<String, Date> dateMap = new HashMap<String, Date>();		
		Date startDate = new Date();
		Date endDate = new Date();
		int sellerId = 0;	
		Map<String, Object> saleQuantity = new HashMap<String, Object>();		
		Gson gson = null;
		String period = "ALL";
		String status = request.getParameter("status");		
		try {
			sellerId = helperClass.getSellerIdfromSession(request);	
			long gM = 0;
			long nM = 0;			
			if(status != null && !status.equals("")){
				period = status;
			}
			dateMap = setDate(period);
			if(dateMap.size() != 0){
				startDate = dateMap.get("startDate");
				endDate = dateMap.get("endDate");
			}
			GsonBuilder gsonBuilder = new GsonBuilder();			
			gson = gsonBuilder.setPrettyPrinting().create();
			gM = detailedDashboardService.getGrossSaleQuantity(startDate, endDate, sellerId);
			nM = detailedDashboardService.getNetSaleQuantity(startDate, endDate, sellerId);			
			saleQuantity.put("grossSaleQty", gM);
			saleQuantity.put("netSaleQty", nM);
			saleQuantity.put("returnSaleQty", (gM-nM));			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by seller ID : " + sellerId, e);
		}
		log.info("$$$ getSaleQuantity Ends : DashboardController $$$");
		return gson.toJson(saleQuantity);
	}
	
	@RequestMapping(value = "/seller/getTaxFreeSale", method = RequestMethod.GET)
	public @ResponseBody String getTaxFreeSale(HttpServletRequest request) {

		log.info("$$$ getTaxFreeSale Starts : DashboardController $$$");
		Map<String, Date> dateMap = new HashMap<String, Date>();		
		Date startDate = new Date();
		Date endDate = new Date();
		int sellerId = 0;	
		Map<String, Object>  TaxFreeSale= new HashMap<String, Object>();		
		Gson gson = null;
		String period = "ALL";
		String status = request.getParameter("status");		
		try {
			sellerId = helperClass.getSellerIdfromSession(request);	
			double gV = 0;
			double rV = 0;
			double aV = 0;
			if(status != null && !status.equals("")){
				period = status;
			}
			dateMap = setDate(period);
			if(dateMap.size() != 0){
				startDate = dateMap.get("startDate");
				endDate = dateMap.get("endDate");
			}
			GsonBuilder gsonBuilder = new GsonBuilder();			
			gson = gsonBuilder.setPrettyPrinting().create();
			gV = detailedDashboardService.getGrossPR(startDate, endDate, sellerId);
			rV = detailedDashboardService.getReturnPR(startDate, endDate, sellerId);
			aV = detailedDashboardService.getAdditionalCharges(startDate, endDate, sellerId);
			TaxFreeSale.put("grossPR", Math.round(gV*100)/100);
			TaxFreeSale.put("netPR", Math.round((gV-rV-aV)*100)/100);
			TaxFreeSale.put("returnPR", Math.round(rV*100)/100);
			TaxFreeSale.put("addCharges", Math.round(aV*100)/100);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by seller ID : " + sellerId, e);
		}
		log.info("$$$ getTaxFreeSale Ends : DashboardController $$$");
		return gson.toJson(TaxFreeSale);
	}
	
	@RequestMapping(value = "/seller/getTaxableSale", method = RequestMethod.GET)
	public @ResponseBody String getTaxableSale(HttpServletRequest request) {

		log.info("$$$ getTaxableSale Starts : DashboardController $$$");
		Map<String, Date> dateMap = new HashMap<String, Date>();		
		Date startDate = new Date();
		Date endDate = new Date();
		int sellerId = 0;	
		Map<String, Object> TaxableSale = new HashMap<String, Object>();		
		Gson gson = null;
		String period = "ALL";
		String status = request.getParameter("status");		
		try {
			sellerId = helperClass.getSellerIdfromSession(request);	
			double gV = 0;
			double rV = 0;
			if(status != null && !status.equals("")){
				period = status;
			}
			dateMap = setDate(period);
			if(dateMap.size() != 0){
				startDate = dateMap.get("startDate");
				endDate = dateMap.get("endDate");
			}
			GsonBuilder gsonBuilder = new GsonBuilder();			
			gson = gsonBuilder.setPrettyPrinting().create();
			gV = detailedDashboardService.getGrossSP(startDate, endDate, sellerId);
			rV = detailedDashboardService.getReturnSP(startDate, endDate, sellerId);			
			TaxableSale.put("grossSP", Math.round(gV*100)/100);
			TaxableSale.put("netSP", Math.round((gV-rV)*100)/100);			
			TaxableSale.put("returnSP", Math.round(rV*100)/100);			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by seller ID : " + sellerId, e);
		}
		log.info("$$$ getTaxableSale Ends : DashboardController $$$");
		return gson.toJson(TaxableSale);
	}
	
	@RequestMapping(value = "/seller/getActualSale", method = RequestMethod.GET)
	public @ResponseBody String getActualSale(HttpServletRequest request) {

		log.info("$$$ getActualSale Starts : DashboardController $$$");
		Map<String, Date> dateMap = new HashMap<String, Date>();		
		Date startDate = new Date();
		Date endDate = new Date();
		int sellerId = 0;	
		Map<String, Object> ActualSale = new HashMap<String, Object>();		
		Gson gson = null;
		String period = "ALL";
		String status = request.getParameter("status");		
		try {
			sellerId = helperClass.getSellerIdfromSession(request);	
			double gV = 0;
			double rV = 0;
			double aV = 0;
			if(status != null && !status.equals("")){
				period = status;
			}
			dateMap = setDate(period);
			if(dateMap.size() != 0){
				startDate = dateMap.get("startDate");
				endDate = dateMap.get("endDate");
			}
			GsonBuilder gsonBuilder = new GsonBuilder();			
			gson = gsonBuilder.setPrettyPrinting().create();
			gV = detailedDashboardService.getGrossNR(startDate, endDate, sellerId);
			rV = detailedDashboardService.getReturnNR(startDate, endDate, sellerId);	
			aV = detailedDashboardService.getAdditionalCharges(startDate, endDate, sellerId);
			ActualSale.put("grossNR", Math.round(gV*100)/100);
			ActualSale.put("netNR", Math.round((gV-rV-aV)*100)/100);			
			ActualSale.put("returnNR", Math.round(rV*100)/100);	
			ActualSale.put("addCharges", Math.round(aV*100)/100);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by seller ID : " + sellerId, e);
		}
		log.info("$$$ getActualSale Ends : DashboardController $$$");
		return gson.toJson(ActualSale);
	}
	
	@RequestMapping(value = "/seller/getTopSellingSKU", method = RequestMethod.GET)
	public @ResponseBody String getTopSellingSKU(HttpServletRequest request) {

		log.info("$$$ getTopSellingSKU Starts : DashboardController $$$");
		Map<String, Date> dateMap = new HashMap<String, Date>();		
		Date startDate = new Date();
		Date endDate = new Date();
		int sellerId = 0;	
		Map<String, Object> TopSKUMap = new HashMap<String, Object>();		
		Gson gson = null;
		String period = "ALL";
		String status = request.getParameter("status");		
		try {
			sellerId = helperClass.getSellerIdfromSession(request);	
			Map<String, Object> topSKU = null;
			if(status != null && !status.equals("")){
				period = status;
			}
			dateMap = setDate(period);
			if(dateMap.size() != 0){
				startDate = dateMap.get("startDate");
				endDate = dateMap.get("endDate");
			}
			GsonBuilder gsonBuilder = new GsonBuilder();			
			gson = gsonBuilder.setPrettyPrinting().create();
			topSKU = detailedDashboardService.getTopSellingSKU(startDate, endDate, sellerId);
			if (topSKU != null) {
				TopSKUMap.put("sku", topSKU.get("sku"));
				TopSKUMap.put("saleQ", topSKU.get("grossQ"));			
				TopSKUMap.put("returnQ", ((long)topSKU.get("grossQ") - (long)topSKU.get("netQ")));	
				TopSKUMap.put("netQ", topSKU.get("netQ"));
			}			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by seller ID : " + sellerId, e);
		}
		log.info("$$$ getTopSellingSKU Ends : DashboardController $$$");
		return gson.toJson(TopSKUMap);
	}
	
	@RequestMapping(value = "/seller/getTopSellingRegion", method = RequestMethod.GET)
	public @ResponseBody String getTopSellingRegion(HttpServletRequest request) {

		log.info("$$$ getTopSellingRegion Starts : DashboardController $$$");
		Map<String, Date> dateMap = new HashMap<String, Date>();		
		Date startDate = new Date();
		Date endDate = new Date();
		int sellerId = 0;	
		Map<String, Object> TopSKUMap = new HashMap<String, Object>();		
		Gson gson = null;
		String period = "ALL";
		String status = request.getParameter("status");		
		try {
			sellerId = helperClass.getSellerIdfromSession(request);	
			Map<String, Object> topSKU = null;
			if(status != null && !status.equals("")){
				period = status;
			}
			dateMap = setDate(period);
			if(dateMap.size() != 0){
				startDate = dateMap.get("startDate");
				endDate = dateMap.get("endDate");
			}
			GsonBuilder gsonBuilder = new GsonBuilder();			
			gson = gsonBuilder.setPrettyPrinting().create();
			topSKU = detailedDashboardService.getTopSellingSKU(startDate, endDate, sellerId);
			if (topSKU != null) {
				TopSKUMap.put("sku", topSKU.get("sku"));
				TopSKUMap.put("saleQ", topSKU.get("grossQ"));			
				TopSKUMap.put("returnQ", ((long)topSKU.get("grossQ") - (long)topSKU.get("netQ")));	
				TopSKUMap.put("netQ", topSKU.get("netQ"));
			}			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by seller ID : " + sellerId, e);
		}
		log.info("$$$ getTopSellingRegion Ends : DashboardController $$$");
		return gson.toJson(TopSKUMap);
	}	
	
	private Map<String, Date> setDate(String status){
		Map<String, Date> dateMap = new HashMap<String, Date>();
		Date currentDate = new Date();
		Date monthStartDate = new Date();	
		Date quaterStartDate = new Date();	
		Date financialStartDate = new Date();
		try {
			if(status.equalsIgnoreCase("Monthly")) {
				monthStartDate.setDate(1);								
				dateMap.put("startDate", monthStartDate);				
				dateMap.put("endDate", currentDate);
			} else if(status.equalsIgnoreCase("Today")) {
				Date yesterday = new Date();
				yesterday.setDate(yesterday.getDate()-1);
				dateMap.put("startDate", yesterday);
				dateMap.put("endDate", currentDate);
			} else if(status.equalsIgnoreCase("Quaterly")) {
				quaterStartDate.setDate(1);
				if(currentDate.getDate() >= 0 && currentDate.getDate() < 2){					
					quaterStartDate.setMonth(0);
				} else if(currentDate.getDate() >= 3 && currentDate.getDate() < 5) {
					quaterStartDate.setMonth(3);
				} else if(currentDate.getDate() >= 6 && currentDate.getDate() < 8) {
					quaterStartDate.setMonth(6);
				} else {
					quaterStartDate.setMonth(9);
				}
				quaterStartDate.setDate(quaterStartDate.getDate() - 1);
				dateMap.put("startDate", quaterStartDate);
				dateMap.put("endDate", currentDate);
			} else if(status.equalsIgnoreCase("Yearly") || status.equalsIgnoreCase("ALL")) {
				financialStartDate.setDate(1);
				financialStartDate.setMonth(3);
				if(currentDate.getMonth() < 3){
					financialStartDate.setYear(currentDate.getYear() - 1);
				}
				financialStartDate.setDate(financialStartDate.getDate() - 1);
				dateMap.put("startDate", financialStartDate);
				dateMap.put("endDate", currentDate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return dateMap;
	}
	
}
