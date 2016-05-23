package com.o2r.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.o2r.bean.ChannelSalesDetails;
import com.o2r.bean.TotalShippedOrder;
import com.o2r.helper.CustomException;
import com.o2r.helper.HelperClass;
import com.o2r.model.Order;
import com.o2r.model.Partner;
import com.o2r.service.OrderService;
import com.o2r.service.PartnerService;
import com.o2r.service.ReportDownloadService;
import com.o2r.service.ReportGeneratorService;

/**
 * @author Deep Mehrotra
 *
 */
@Controller
public class ReportController {

@Resource(name="reportGeneratorService")
private ReportGeneratorService reportGeneratorService;

@Resource(name="reportDownloadService")
private ReportDownloadService reportDownloadService;

@Resource(name="partnerService")
private PartnerService partnerService;

@Autowired
private OrderService orderService;
@Autowired
private HelperClass helperClass;

private static final SimpleDateFormat dateFormat = new SimpleDateFormat("mm/dd/yy");

static Logger log = Logger.getLogger(ReportController.class.getName());

@RequestMapping(value = "/seller/getAllReports", method = RequestMethod.GET)
public String displayForm() {
	
		return "reports/allreports";
	}

@RequestMapping(value = "/seller/getReportsFilter", method = RequestMethod.GET)
public ModelAndView addManualPayment(HttpServletRequest request) {
		
		log.info("$$$ addManualPayment Starts : ReportController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		List<String> partnerlist = new ArrayList<String>();
		List<Partner> partners=new ArrayList<Partner>();
		String reportName = request.getParameter("reportName");
		try{
			partners = partnerService.listPartners(helperClass.getSellerIdfromSession(request));
			for (Partner partner : partners)
				partnerlist.add(partner.getPcName());
			
			model.put("reportName", reportName);
			model.put("partnerlist", partnerlist);
		}catch(CustomException ce){
			log.error("addManualPayment exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		}catch(Exception e){
			e.printStackTrace();
			log.error("Failed!",e);
		} 
		log.info("$$$ addManualPayment Ends : ReportController $$$");
		if(reportName.equals("channelSaleReport") || reportName.equals("categoryWiseSaleReport"))
			return new ModelAndView("reports/channelSaleReport", model);
		else if(reportName.equals("paymentsReceievedReport" )|| reportName.equals("orderwiseGPReport"))
			return new ModelAndView("reports/channelSaleReport", model);
		else
			return new ModelAndView("reports/filterReports", model);
}


@RequestMapping(value = "/seller/getReport", method = RequestMethod.POST)
public ModelAndView getReport(HttpServletRequest request)throws Exception
{
		log.info("$$$ getReport Starts : ReportController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		List<TotalShippedOrder> ttso = new ArrayList<>();
		String reportName=null;
		Date startDate;
		Date endDate;		
		try{
			reportName = request.getParameter("reportName");
			startDate = new Date(request.getParameter("startdate"));
			endDate = new Date(request.getParameter("enddate"));
			ttso = reportGeneratorService.getAllPartnerTSOdetails(startDate,
					endDate, helperClass.getSellerIdfromSession(request));
			if (ttso != null)
				System.out.println(" ****Inside controller after gettitng ttso objkect : "+ ttso.size());
			else
				System.out.println(" TTSO object is geting null");
	
			model.put("ttsolist", ttso);
			if (ttso.size() > 0) {
				System.out.println(" Citi quantity size : "	+ ttso.get(0).getCityQuantity());
				System.out.println(" Citi percent size : " + ttso.get(0).getCityPercentage());
				model.put("citicount", ttso.get(0).getCityQuantity());
				model.put("citipercent", ttso.get(0).getCityPercentage());
			}
			Collections.sort(ttso, new TotalShippedOrder.OrderByNR());
			model.put("NRsortedttso", getSortedList(ttso));
			Collections.sort(ttso, new TotalShippedOrder.OrderByReturnamount());
			model.put("returnAmountsortedttso", getSortedList(ttso));
			Collections.sort(ttso, new TotalShippedOrder.OrderByReturnQuantity());
			model.put("returnQsortedttso", getSortedList(ttso));
			Collections.sort(ttso, new TotalShippedOrder.OrderBySaleQuantity());
			model.put("saleQsortedttso", getSortedList(ttso));
			Collections.sort(ttso, new TotalShippedOrder.OrderBySaleamount());
			model.put("saleAmounrsortedttso", getSortedList(ttso));
	
			model.put("period",dateFormat.format(startDate) + " to "+ dateFormat.format(endDate));
		}catch(CustomException ce){
			log.error("getReport exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		}catch(Exception e){
			e.printStackTrace();
			log.error("Failed!",e);
		}
		log.info("$$$ getReport Ends : ReportController $$$");
		if(reportName.equals("channelSaleReport"))
			return new ModelAndView("reports/viewChannelSaleGraphReport", model);
		else if(reportName.equals("categoryWiseSaleReport"))
			return new ModelAndView("reports/viewCategorySaleGraphReport", model);
		else
			return new ModelAndView("reports/viewGraphReport", model);
}


@RequestMapping(value = "/seller/getChannelReport", method = RequestMethod.POST)
public ModelAndView getChannelReport(HttpServletRequest request)throws Exception
{
		log.info("$$$ getChannelReport Starts : ReportController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		List<ChannelSalesDetails> ttso = new ArrayList<>();
		String reportName=null;
		Date startDate;
		Date endDate;
		
		try {

			reportName = request.getParameter("reportName");
			startDate = new Date(request.getParameter("startdate"));
			endDate = new Date(request.getParameter("enddate"));
			if (reportName.equals("categoryWiseSaleReport"))
				ttso = reportGeneratorService.getCategorySalesDetails(
						startDate, endDate,
						helperClass.getSellerIdfromSession(request));
			else if (reportName.equals("paymentsReceievedReport"))
				ttso = reportGeneratorService.getPaymentsReceievedDetails(
						startDate, endDate,
						helperClass.getSellerIdfromSession(request));
			else if (reportName.equals("orderwiseGPReport"))
				ttso = reportGeneratorService.getOrderwiseGPDetails(startDate,
						endDate, helperClass.getSellerIdfromSession(request));
			else
				ttso = reportGeneratorService.getChannelSalesDetails(startDate,
						endDate, helperClass.getSellerIdfromSession(request));

			model.put("ttsolist", ttso);
			model.put("period", dateFormat.format(startDate) + " to "
					+ dateFormat.format(endDate));
			
		} catch (CustomException ce) {
			log.error("getReport exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed!",e);
		}
		log.info("$$$ getChannelReport Ends : ReportController $$$");
		if(reportName.equals("channelSaleReport"))
			return new ModelAndView("reports/viewChannelSaleGraphReport", model);
		else if(reportName.equals("categoryWiseSaleReport"))
			return new ModelAndView("reports/viewCategorySaleGraphReport", model);
		else if(reportName.equals("orderwiseGPReport"))
			return new ModelAndView("reports/orderwiseGPReport", model);
		else
			return new ModelAndView("reports/paymentsReceievedReport", model);		
}

@RequestMapping(value = "/seller/downloadreport", method = RequestMethod.POST)
public void downloadreport(HttpServletRequest request ,HttpServletResponse response){
		
		log.info("$$$ downloadreport Starts : ReportController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		List<TotalShippedOrder> ttso = new ArrayList<>();
		List<Order> orderlist = new ArrayList<>();
		String reportName;
		Date startDate;
		Date endDate;
		String[] reportheaders;
		log.debug(request.getParameter("enddate"));
		log.debug(request.getParameter("startdate"));

		reportName = request.getParameter("reportName");
		startDate = new Date(request.getParameter("startdate"));
		endDate = new Date(request.getParameter("enddate"));
		reportheaders = request.getParameterValues("headers");
		try {
			orderlist = orderService.findOrdersbyDate("orderDate", startDate,endDate, helperClass.getSellerIdfromSession(request));
			reportDownloadService.downloadReport(response, orderlist,reportheaders, reportName,	helperClass.getSellerIdfromSession(request));
		} catch (ClassNotFoundException e) {
			System.out.println(" Class castexception in download report");
			e.printStackTrace();
			log.error(e);
		}catch(CustomException ce){
			ce.printStackTrace();
			log.error("downloadReport exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
		}catch(Exception e){
			e.printStackTrace();
			log.error("Failed!",e);
		}
		log.debug(" Got response ttso size in controller: "+ ttso.size());
		log.info("$$$ downloadreport Ends : ReportController $$$");
	}

@RequestMapping(value = "/seller/downloadOrderReport", method = RequestMethod.POST)
public void downloadOrderReport(HttpServletRequest request ,HttpServletResponse response){
		
		log.info("$$$ downloadOrderReport Starts : ReportController $$$");
		Map<String, Object> model = new HashMap<String, Object>();		
		List<ChannelSalesDetails> orderlist = new ArrayList<>();
		
		Date startDate;
		Date endDate;
		String[] reportheaders;		

		startDate = new Date(request.getParameter("startdate"));
		endDate = new Date(request.getParameter("enddate"));
		reportheaders = request.getParameterValues("headers");
		try {
			orderlist = orderService.findChannelOrdersbyDate("orderDate", startDate,endDate, helperClass.getSellerIdfromSession(request));
			reportDownloadService.downloadCOReport(response, orderlist,reportheaders, "ChannelSalesReport",	helperClass.getSellerIdfromSession(request));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			log.error(e);
		}catch(CustomException ce){
			log.error("downloadReport exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			//return new ModelAndView("globalErorPage", model);
		}catch(Exception e){
			log.error(e);
		}
		log.info("$$$ downloadOrderReport Ends : ReportController $$$");		
	}

public List<TotalShippedOrder> getSortedList(List<TotalShippedOrder> ttso)
{
	log.info("$$$ getSortedList Starts : ReportController $$$");
	List<TotalShippedOrder> returnlist=new ArrayList<TotalShippedOrder>();
	TotalShippedOrder fifthreco=null;
	if(ttso!=null&&ttso.size()>4)
	{		
	returnlist.add(ttso.get(0));
	returnlist.add(ttso.get(1));
	returnlist.add(ttso.get(2));
	returnlist.add(ttso.get(3));
	int i=0;
	fifthreco=new TotalShippedOrder();
	for(TotalShippedOrder newttso:ttso)
	{
		
		if(i>3)
		{
		fifthreco.setSaleQuantity(fifthreco.getSaleQuantity()+newttso.getSaleQuantity());
		fifthreco.setSaleQuantityPercent(fifthreco.getSaleQuantityPercent()+newttso.getSaleQuantityPercent());
		fifthreco.setReturnQuantity(fifthreco.getReturnQuantity()+newttso.getReturnQuantity());
		fifthreco.setReturnQuantityPercent(fifthreco.getReturnQuantityPercent()+newttso.getReturnQuantityPercent());
		fifthreco.setNr(fifthreco.getNr()+newttso.getNr());
		fifthreco.setNrPercent(fifthreco.getNrPercent()+newttso.getNrPercent());
		fifthreco.setReturnAmount(fifthreco.getReturnAmount()+newttso.getReturnAmount());
		fifthreco.setReturnAmountPercent(fifthreco.getReturnAmountPercent()+newttso.getReturnAmountPercent());
		fifthreco.setNetSaleAmount(fifthreco.getNetSaleAmount()+newttso.getNetSaleAmount());
		fifthreco.setNetSaleAmountPercent(fifthreco.getNetSaleAmountPercent()+newttso.getNetSaleAmountPercent());
		fifthreco.setNoOfDeliveredOrder(fifthreco.getNoOfDeliveredOrder()+newttso.getNoOfDeliveredOrder());
		fifthreco.setDeliveredOrderPercent(fifthreco.getDeliveredOrderPercent()+newttso.getDeliveredOrderPercent());
		fifthreco.setNoOfReturnOrder(fifthreco.getNoOfReturnOrder()+newttso.getNoOfReturnOrder());
		fifthreco.setReturnOrderPercent(fifthreco.getNetSaleAmount()+newttso.getNetSaleAmount());
		fifthreco.setNoOfRTOOrder(fifthreco.getNoOfRTOOrder()+newttso.getNoOfRTOOrder());
		fifthreco.setRTOOrderPercent(fifthreco.getRTOOrderPercent()+newttso.getRTOOrderPercent());
		fifthreco.setNoOfRTOLimitCrossed(fifthreco.getNoOfRTOLimitCrossed()+newttso.getNoOfRTOLimitCrossed());
		fifthreco.setRTOLimitCrossedPercent(fifthreco.getRTOLimitCrossedPercent()+newttso.getRTOLimitCrossedPercent());
		fifthreco.setNoOfReturnLimitCrossed(fifthreco.getNoOfReturnLimitCrossed()+newttso.getNoOfReturnLimitCrossed());
		fifthreco.setReturnLimitCrossedPercent(fifthreco.getReturnLimitCrossedPercent()+newttso.getReturnLimitCrossedPercent());
		fifthreco.setNoOfActionableOrders(fifthreco.getNoOfActionableOrders()+newttso.getNoOfActionableOrders());
		fifthreco.setActionableOrdersPercent(fifthreco.getActionableOrdersPercent()+newttso.getActionableOrdersPercent());
		fifthreco.setNoOfSettledOrders(fifthreco.getNoOfSettledOrders()+newttso.getNoOfSettledOrders());
		fifthreco.setSettledOrdersPercent(fifthreco.getSettledOrdersPercent()+newttso.getSettledOrdersPercent());
		}
		i++;
	}
	
	fifthreco.setPcName("Others");
	returnlist.add(fifthreco);
	
	
	}
	else if(ttso!=null&&ttso.size()<5&&ttso.size()>0)
	{
		for(int i=0;i<ttso.size();i++)
		{
			returnlist.add(ttso.get(i));
		}
	}
	
	log.info("$$$ getSortedList Ends : ReportController $$$");
	return returnlist;
	
}

}
