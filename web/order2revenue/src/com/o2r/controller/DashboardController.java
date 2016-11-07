package com.o2r.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.o2r.bean.DetailedDashboardBean;
import com.o2r.helper.GlobalConstant;
import com.o2r.helper.HelperClass;
import com.o2r.service.CategoryService;
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
			List<Map<String, Object>> topSKUs = null;
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
			topSKUs = detailedDashboardService.getTopSellingSKU(startDate, endDate, null, sellerId);
			if (topSKUs != null && topSKUs.size() != 0) {
				Map<String, Object> topSKU = topSKUs.get(0);
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
		Map<String, Object> TopRegion = new HashMap<String, Object>();
		List<Map<String, Object>> topRegions = new ArrayList<Map<String,Object>>();
		Gson gson = null;
		String period = "ALL";
		String status = request.getParameter("status");		
		try {
			sellerId = helperClass.getSellerIdfromSession(request);			
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
			topRegions = detailedDashboardService.getTopSellingRegion(startDate, endDate, null, sellerId);
			if (topRegions != null && topRegions.size() != 0) {
				Map<String, Object> resultMap = topRegions.get(0);
				TopRegion.put("Region", resultMap.get("Region"));
				TopRegion.put("grossSale", resultMap.get("grossSale"));			
				TopRegion.put("returnSale", resultMap.get("returnSale"));	
				TopRegion.put("netSale", resultMap.get("netSale"));
			}			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by seller ID : " + sellerId, e);
		}
		log.info("$$$ getTopSellingRegion Ends : DashboardController $$$");
		return gson.toJson(TopRegion);
	}
	
	@RequestMapping(value = "/seller/getUpcomingPayment", method = RequestMethod.GET)
	public @ResponseBody String getUpcomingPayment(HttpServletRequest request) {

		log.info("$$$ getUpcomingPayment Starts : DashboardController $$$");		
		int sellerId = 0;	
		Map<String, Object> upcomPayMap = new HashMap<String, Object>();		
		Gson gson = null;
		List<Map<String, Object>> upPayList = null;		
		try {
			sellerId = helperClass.getSellerIdfromSession(request);						
			GsonBuilder gsonBuilder = new GsonBuilder();			
			gson = gsonBuilder.setPrettyPrinting().create();
			upPayList = detailedDashboardService.getUpcomingPayment(sellerId, "");
			if (upPayList != null && upPayList.size() != 0) {
				Map<String, Object> upPay = upPayList.get(0);
				if(upPay != null)
					upcomPayMap.put("Total", Math.round((double)(upPay.get("Total"))*100)/100);				
			}			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by seller ID : " + sellerId, e);
		}
		log.info("$$$ getUpcomingPayment Ends : DashboardController $$$");
		return gson.toJson(upcomPayMap);
	}
	
	@RequestMapping(value = "/seller/getOutstandingPayment", method = RequestMethod.GET)
	public @ResponseBody String getOutstandingPayment(HttpServletRequest request) {

		log.info("$$$ getOutstandingPayment Starts : DashboardController $$$");		
		int sellerId = 0;	
		Map<String, Object> OutPayMap = new HashMap<String, Object>();		
		Gson gson = null;
		List<Map<String, Object>> OutPay = null;			
		try {
			sellerId = helperClass.getSellerIdfromSession(request);			
			GsonBuilder gsonBuilder = new GsonBuilder();			
			gson = gsonBuilder.setPrettyPrinting().create();
			OutPay = detailedDashboardService.getOutstandingPayment(sellerId, "");
			if (OutPay != null && OutPay.size() != 0) {
				Map<String, Object> oPay = OutPay.get(0);
				if(oPay != null)
					OutPayMap.put("Total", Math.round((double)(oPay.get("Total"))*100)/100);				
			}			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by seller ID : " + sellerId, e);
		}
		log.info("$$$ getOutstandingPayment Ends : DashboardController $$$");
		return gson.toJson(OutPayMap);
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
	@RequestMapping(value = "/seller/getDashListOnCriteria", method = RequestMethod.GET)
	public ModelAndView getDashListOnCriteria(HttpServletRequest request) {

		log.info("$$$ getGrossMargin Starts : DashboardController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Date> dateMap = new HashMap<String, Date>();
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date startDate = new Date();
		Date endDate = new Date();
		int sellerId = 0;
		List<String> headerList  = null;
		List<DetailedDashboardBean> DDbeanList = new ArrayList<DetailedDashboardBean>();
		DetailedDashboardBean DDbean = null;
		List<Map<String, Object>> paymentMapList = new ArrayList<Map<String,Object>>();
		try {
			String period = "ALL";
			String status = request.getParameter("status") != null ? request.getParameter("status") : "";
			String criteria = request.getParameter("selectCriteriaList") != null ? request.getParameter("selectCriteriaList") : "";
			sellerId = helperClass.getSellerIdfromSession(request);			
			if(status != null && !status.equals("")){
				period = status;
				dateMap = setDate(period);
				if(dateMap.size() != 0){
					startDate = dateMap.get("startDate");
					endDate = dateMap.get("endDate");
				}
			} else {
				System.out.println(request.getParameter("startDateList"));
				System.out.println(request.getParameter("endDateList"));
				if(request.getParameter("startDateList") != null){
					startDate = formatter.parse(request.getParameter("startDateList"));
				} else {
					startDate = new Date();
				}
				if(request.getParameter("endDateList") != null){
					endDate = formatter.parse(request.getParameter("endDateList"));
				} else {
					endDate = new Date();
				}
			}
			
			switch (criteria) {
			case "Gross Margin":
				DDbeanList = detailedDashboardService.getGrossMarginList(startDate, endDate, sellerId);	
				Collections.sort(DDbeanList, new DetailedDashboardBean.sortByMonth());
				headerList = GlobalConstant.grossHeader;
				break;
			case "Actual Sale":
				DDbeanList = detailedDashboardService.getActualSaleList(startDate, endDate, sellerId);
				Collections.sort(DDbeanList, new DetailedDashboardBean.sortByMonth());
				headerList = GlobalConstant.NR_PR_Header;
				break;
			case "Tax Free Sale":
				DDbeanList = detailedDashboardService.getTaxFreeSaleList(startDate, endDate, sellerId);
				Collections.sort(DDbeanList, new DetailedDashboardBean.sortByMonth());
				headerList = GlobalConstant.NR_PR_Header;
				break;
			case "Taxable Sale":
				DDbeanList = detailedDashboardService.getTaxableSaleList(startDate, endDate, sellerId);
				Collections.sort(DDbeanList, new DetailedDashboardBean.sortByMonth());
				headerList = GlobalConstant.SP_SaleQty_Header;
				break;
			case "Sale Quantity":
				DDbeanList = detailedDashboardService.getSaleQuantityList(startDate, endDate, sellerId);
				Collections.sort(DDbeanList, new DetailedDashboardBean.sortByMonth());
				headerList = GlobalConstant.SP_SaleQty_Header;
				break;
			case "Top Selleing SKUs":
				paymentMapList = detailedDashboardService.getTopSellingSKU(startDate, endDate, "list", sellerId);
				if(paymentMapList != null && paymentMapList.size() != 0){
					for(Map<String, Object> each : paymentMapList){
						DDbean = new DetailedDashboardBean();
						DDbean.setSku(each.get("sku").toString());
						DDbean.setGrossValue((long)each.get("grossQ"));
						DDbean.setReturnValue((long)each.get("grossQ") - (long)each.get("netQ"));
						DDbean.setNetValue((long)each.get("netQ"));
						DDbeanList.add(DDbean);
					}
				}
				Collections.sort(DDbeanList, new DetailedDashboardBean.sortByGrossValue());
				headerList = GlobalConstant.SKUHeader;
				break;
			case "Top Selling Regions":
				paymentMapList = detailedDashboardService.getTopSellingRegion(startDate, endDate, "list", sellerId);
				if(paymentMapList != null && paymentMapList.size() != 0){
					for(Map<String, Object> each : paymentMapList){
						DDbean = new DetailedDashboardBean();
						DDbean.setCity(each.get("Region").toString());
						DDbean.setGrossValue((long)each.get("grossSale"));
						DDbean.setReturnValue((long)each.get("returnSale"));
						DDbean.setNetValue((long)each.get("netSale"));
						DDbeanList.add(DDbean);
					}
				}
				Collections.sort(DDbeanList, new DetailedDashboardBean.sortByGrossValue());
				headerList = GlobalConstant.RegionsHeader;
				break;
				
			case "Outstanding Payment":
				paymentMapList = detailedDashboardService.getOutstandingPayment(sellerId, "list");
				if(paymentMapList != null && paymentMapList.size() != 0){
					for(Map<String, Object> each : paymentMapList){
						DDbean = new DetailedDashboardBean();
						Map.Entry<String, Object> entry=each.entrySet().iterator().next();
						DDbean.setChannel(entry.getKey());
						DDbean.setGrossValue((double) entry.getValue());
						DDbeanList.add(DDbean);
					}
				}
				Collections.sort(DDbeanList, new DetailedDashboardBean.sortByGrossValue());
				headerList = GlobalConstant.Up_Out_Header;
				break;
				
			case "Upcoming Payment":
				paymentMapList = detailedDashboardService.getUpcomingPayment(sellerId, "list");
				if(paymentMapList != null && paymentMapList.size() != 0){
					for(Map<String, Object> each : paymentMapList){
						DDbean = new DetailedDashboardBean();
						Map.Entry<String, Object> entry=each.entrySet().iterator().next();
						DDbean.setChannel(entry.getKey());
						DDbean.setGrossValue((double) entry.getValue());
						DDbeanList.add(DDbean);
					}
				}
				Collections.sort(DDbeanList, new DetailedDashboardBean.sortByGrossValue());
				headerList = GlobalConstant.Up_Out_Header;
				break;
				
			default:
				DDbean = new DetailedDashboardBean();
				break;
			}
			
			model.put("DDBeans", DDbeanList);
			model.put("HeaderList", headerList);
			model.put("criteria", criteria);
			model.put("startDate", startDate);
			model.put("endDate", endDate);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by seller ID : " + sellerId, e);
		}
		log.info("$$$ getGrossMargin Ends : DashboardController $$$");
		return new ModelAndView("detailedDashboard", model);
	}
	
}
