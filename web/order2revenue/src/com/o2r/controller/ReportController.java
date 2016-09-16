package com.o2r.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.o2r.bean.BusinessDetails;
import com.o2r.bean.ChannelCatNPR;
import com.o2r.bean.ChannelGP;
import com.o2r.bean.ChannelMC;
import com.o2r.bean.ChannelMCNPR;
import com.o2r.bean.ChannelNPR;
import com.o2r.bean.ChannelNR;
import com.o2r.bean.ChannelReportDetails;
import com.o2r.bean.ChannelSalesDetails;
import com.o2r.bean.CommissionAnalysis;
import com.o2r.bean.CommissionDetails;
import com.o2r.bean.ConsolidatedOrderBean;
//import com.o2r.bean.ConsolidatedOrderBean;
import com.o2r.bean.DebtorsGraph1;
import com.o2r.bean.ExpensesDetails;
import com.o2r.bean.MonthlyCommission;
import com.o2r.bean.NetPaymentResult;
import com.o2r.bean.NetProfitabilityST;
import com.o2r.bean.PartnerReportDetails;
import com.o2r.bean.TotalShippedOrder;
import com.o2r.bean.YearlyStockList;
import com.o2r.helper.ConverterClass;
import com.o2r.helper.CustomException;
import com.o2r.helper.GlobalConstant;
import com.o2r.helper.HelperClass;
import com.o2r.model.Expenses;
import com.o2r.model.ManualCharges;
import com.o2r.model.Order;
import com.o2r.model.Partner;
import com.o2r.service.ExpenseService;
import com.o2r.service.ManualChargesService;
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

	@Resource(name = "reportGeneratorService")
	private ReportGeneratorService reportGeneratorService;

	@Resource(name = "reportDownloadService")
	private ReportDownloadService reportDownloadService;

	@Resource(name = "partnerService")
	private PartnerService partnerService;

	@Resource(name = "manualChargesService")
	private ManualChargesService manualChargesService;

	@Resource(name = "expenseService")
	private ExpenseService expenseService;

	@Autowired
	private OrderService orderService;
	@Autowired
	private HelperClass helperClass;

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"mm/dd/yy");

	static Logger log = Logger.getLogger(ReportController.class.getName());

	@RequestMapping(value = "/seller/getAllReports", method = RequestMethod.GET)
	public String displayForm() {

		return "reports/allreports";
	}

	@RequestMapping(value = "/seller/getReportsFilter", method = RequestMethod.GET)
	public ModelAndView addManualPayment(HttpServletRequest request) {

		log.info("$$$ addManualPayment Starts : ReportController $$$");
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		List<String> partnerlist = new ArrayList<String>();
		List<Partner> partners = new ArrayList<Partner>();
		String reportName = request.getParameter("reportName");
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			/*partners = partnerService.listPartners(helperClass
					.getSellerIdfromSession(request));
			for (Partner partner : partners)
				partnerlist.add(partner.getPcName());*/

			model.put("reportName", reportName);
			model.put("reportNameStr",
					GlobalConstant.reportNameMap.get(reportName));
			//model.put("partnerlist", partnerlist);
		} catch (CustomException ce) {
			log.error("addManualPayment exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! By Seller ID : " + sellerId, e);
		}
		log.info("$$$ addManualPayment Ends : ReportController $$$");
		if (reportName.equals("channelSaleReport")
				|| reportName.equals("categoryWiseSaleReport"))
			return new ModelAndView("reports/channelSaleReport", model);
		else if (reportName.equals("totalPaymentsReceived")
				|| reportName.equals("orderwiseGPReport"))
			return new ModelAndView("reports/channelSaleReport", model);
		else if (reportName.equals("partnerBusinessReport")
				|| reportName.equals("partnerCommissionReport")
				|| reportName.equals("debtorsReport"))
			return new ModelAndView("reports/partnerReport", model);
		else if (reportName.equals("consolidatedOrders"))
			return new ModelAndView("reports/filterReports", model);
		else if (reportName.equals("netProfitabilityReport"))
			return new ModelAndView("reports/revenueReport", model);		
		else
			return new ModelAndView("reports/comingSoon", model);
	}

	@RequestMapping(value = "/seller/getRevenueReport", method = RequestMethod.POST)
	public ModelAndView getRevenueReport(HttpServletRequest request)
			throws Exception {
		log.info("$$$ getRevenueReport Starts : ReportController $$$");
		long startreportCalc=System.currentTimeMillis();
		long endreportCalc=0;
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String reportName = request.getParameter("reportName");
			String selectedYear = request.getParameter("selectedYear");
			int selectedYearInt = 2016;
			if (StringUtils.isNotBlank(selectedYear))
				selectedYearInt = Integer.parseInt(selectedYear);
			model.put("reportNameStr",
					GlobalConstant.reportNameMap.get(reportName));

			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = ft.parse(selectedYearInt + "-04-01");
			Date endDate = ft.parse((selectedYearInt + 1) + "-03-31");
			sellerId = helperClass.getSellerIdfromSession(request);

			List<YearlyStockList> stockList = reportGeneratorService
					.fetchStockList(selectedYearInt);
			List<YearlyStockList> combinedStockList = ConverterClass
					.combineStockList(stockList);
			model.put("stockList", combinedStockList);
			List<ExpensesDetails> expensesList = expenseService
					.getExpenseByYear(sellerId, startDate, endDate);
			model.put("expensesList", expensesList);
			List<NetPaymentResult> nprList = reportGeneratorService.fetchNPR(
					sellerId, startDate, endDate);
			model.put("nprList", nprList);
			List<ChannelNR> nrList = reportGeneratorService.fetchNetRate(
					sellerId, startDate, endDate);
			model.put("nrList", nrList);
			List<ExpensesDetails> expensesCatList = expenseService
					.getExpenseByCatYear(sellerId, startDate, endDate);
			Set<String> categorySet = new HashSet<String>();
			for (ExpensesDetails expenseCat : expensesCatList) {
				categorySet.add(expenseCat.getExpenseCatName());
			}
			List<String> catList = new ArrayList<>();
			catList.addAll(categorySet);
			List<NetProfitabilityST> npList = ConverterClass
					.combineForShortTable(combinedStockList, nprList, nrList,
							expensesCatList, catList);
			model.put("shortTable", npList);
			model.put("catList", catList);
			endreportCalc=System.currentTimeMillis();
			log.info("Time take to generate report : "+(endreportCalc-startreportCalc));
			log.info("$$$ getRevenueReport end : ReportController $$$");
			return new ModelAndView("reports/viewBusinessProfitReport", model);

		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("Failed! By Seller ID : " + sellerId, ex);
			model.put("errorMessage", ex.getMessage());
		}
		log.info("$$$ getRevenueReport end : ReportController $$$");

		return new ModelAndView("globalErorPage", model);
	}

	@RequestMapping(value = "/seller/getPartnerReport", method = RequestMethod.POST)
	public ModelAndView getPartnerReport(HttpServletRequest request)
			throws Exception {
		log.info("$$$ getPartnerReport Starts : ReportController $$$");
		long startreportCalc=System.currentTimeMillis();
		long endreportCalc=0;
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String reportName = request.getParameter("reportName");
			String startDateStr = request.getParameter("startdate");
			String endDateStr = request.getParameter("enddate");
			Date startDate = null;
			if (StringUtils.isNotBlank(startDateStr))
				startDate = new Date(startDateStr);
			Date endDate = null;
			if (StringUtils.isNotBlank(endDateStr))
				endDate = new Date(endDateStr);
			sellerId = helperClass.getSellerIdfromSession(request);
			model.put("reportNameStr",
					GlobalConstant.reportNameMap.get(reportName));

			if ("debtorsReport".equalsIgnoreCase(reportName)) {
				Map<String,Object> objMap=reportGeneratorService
						.getDebtorsReportDetails(startDate, endDate, sellerId);
				List<PartnerReportDetails> debtorsList =(List<PartnerReportDetails>) objMap.get("list");
				
				System.out.println(" Size of debtors list : "+debtorsList.size());
				List<DebtorsGraph1> debtorsGraph1PartnerList = ConverterClass
						.transformDebtorsGraph1Graph((Map<String,DebtorsGraph1> )objMap.get("partnermap"));
				List<DebtorsGraph1> debtorsGraph1CategoryList = ConverterClass
						.transformDebtorsGraph1Graph( (Map<String,DebtorsGraph1> )objMap.get("categorymap"));
				model.put("shortTablePartner", debtorsGraph1PartnerList);
				model.put("shortTableCategory", debtorsGraph1CategoryList);

				Collections.sort(debtorsGraph1PartnerList,
						new DebtorsGraph1.OrderByNPR());
				model.put("partnerByNPR", ConverterClass
						.getDebtorsGraph1SortedList(debtorsGraph1PartnerList));
				Collections.sort(debtorsGraph1PartnerList,
						new DebtorsGraph1.OrderByACPD());
				model.put("partnerByACPD", ConverterClass
						.getDebtorsGraph1SortedList(debtorsGraph1PartnerList));
				Collections.sort(debtorsGraph1PartnerList,
						new DebtorsGraph1.OrderByACNS());
				model.put("partnerByACNS", ConverterClass
						.getDebtorsGraph1SortedList(debtorsGraph1PartnerList));

				Collections.sort(debtorsGraph1CategoryList,
						new DebtorsGraph1.OrderByNPR());
				model.put("categoryByNPR", ConverterClass
						.getDebtorsGraph1SortedList(debtorsGraph1CategoryList));
				Collections.sort(debtorsGraph1CategoryList,
						new DebtorsGraph1.OrderByACPD());
				model.put("categoryByACPD", ConverterClass
						.getDebtorsGraph1SortedList(debtorsGraph1CategoryList));
				Collections.sort(debtorsGraph1CategoryList,
						new DebtorsGraph1.OrderByACNS());
				model.put("categoryByACNS", ConverterClass
						.getDebtorsGraph1SortedList(debtorsGraph1CategoryList));
				endreportCalc=System.currentTimeMillis();
				log.info("Time take to generate debtorsReport : "+(endreportCalc-startreportCalc));
				log.info("$$$ getPartnerReport end : ReportController $$$");
				return new ModelAndView("reports/viewDebtorsGraphReport", model);
			}
			List<PartnerReportDetails> partnerBusinessList = reportGeneratorService
					.getPartnerReportDetails(startDate, endDate, sellerId);
			if ("partnerBusinessReport".equalsIgnoreCase(reportName)) {
				List<BusinessDetails> partnerBusinessGraphList = ConverterClass
						.transformBusinessGraph(partnerBusinessList, "partner");
				log.info("PartnerList: " + partnerBusinessGraphList.size());
				model.put("shortTableMainPartner", partnerBusinessGraphList);
				Collections.sort(partnerBusinessGraphList,
						new BusinessDetails.OrderByNetSP());
				model.put("shortTablePartner", ConverterClass
						.getBusinessSortedList(partnerBusinessGraphList,
								"NetSP"));
				List<BusinessDetails> categoryBusinessGraphList = ConverterClass
						.transformBusinessGraph(partnerBusinessList, "category");
				log.info("CategoryList: " + categoryBusinessGraphList.size());
				model.put("shortTableMainCategory", categoryBusinessGraphList);
				Collections.sort(categoryBusinessGraphList,
						new BusinessDetails.OrderByNetSP());
				model.put("shortTableCategory", ConverterClass
						.getBusinessSortedList(categoryBusinessGraphList,
								"NetSP"));

				Collections.sort(partnerBusinessGraphList,
						new BusinessDetails.OrderByNetPartnerCommission());
				model.put("partnerByNetCommission", ConverterClass
						.getBusinessSortedList(partnerBusinessGraphList,
								"NetCommission"));
				Collections.sort(partnerBusinessGraphList,
						new BusinessDetails.OrderByNetTax());
				model.put("partnerByNetTax", ConverterClass
						.getBusinessSortedList(partnerBusinessGraphList,
								"NetTax"));
				Collections.sort(partnerBusinessGraphList,
						new BusinessDetails.OrderByNetTDS());
				model.put("partnerByNetTDS", ConverterClass
						.getBusinessSortedList(partnerBusinessGraphList,
								"NetTDS"));
				Collections.sort(partnerBusinessGraphList,
						new BusinessDetails.OrderByEOSS());
				model.put("partnerByEOSS",
						ConverterClass.getBusinessSortedList(
								partnerBusinessGraphList, "EOSS"));
				Collections.sort(partnerBusinessGraphList,
						new BusinessDetails.OrderByNetTaxable());
				model.put("partnerByNetTaxable", ConverterClass
						.getBusinessSortedList(partnerBusinessGraphList,
								"NetTaxable"));

				Collections.sort(categoryBusinessGraphList,
						new BusinessDetails.OrderByNetPartnerCommission());
				model.put("categoryByNetCommission", ConverterClass
						.getBusinessSortedList(categoryBusinessGraphList,
								"NetCommission"));
				Collections.sort(categoryBusinessGraphList,
						new BusinessDetails.OrderByNetTax());
				model.put("categoryByNetTax", ConverterClass
						.getBusinessSortedList(categoryBusinessGraphList,
								"NetTax"));
				Collections.sort(categoryBusinessGraphList,
						new BusinessDetails.OrderByNetTDS());
				model.put("categoryByNetTDS", ConverterClass
						.getBusinessSortedList(categoryBusinessGraphList,
								"NetTDS"));
				Collections.sort(categoryBusinessGraphList,
						new BusinessDetails.OrderByEOSS());
				model.put("categoryByEOSS", ConverterClass
						.getBusinessSortedList(categoryBusinessGraphList,
								"EOSS"));
				Collections.sort(categoryBusinessGraphList,
						new BusinessDetails.OrderByNetTaxable());
				model.put("categoryByNetTaxable", ConverterClass
						.getBusinessSortedList(categoryBusinessGraphList,
								"NetTaxable"));
				List<ChannelMC> channelMCList = reportGeneratorService
						.fetchChannelMC(sellerId, startDate, endDate, "");
				model.put("channelMC", channelMCList);

				model.put("partnerBusiness", partnerBusinessList);
				endreportCalc=System.currentTimeMillis();
				log.info("Time take to generate partnerBusinessReport : "+(endreportCalc-startreportCalc));
				log.info("$$$ getPartnerReport end : ReportController $$$");

				return new ModelAndView("reports/viewBRGraphReport", model);
			}

			if ("partnerCommissionReport".equalsIgnoreCase(reportName)) {
				List<CommissionDetails> partnerCommissionGraphList = reportGeneratorService
						.fetchPC(sellerId, startDate, endDate, "partner");
				log.info("PartnerList: " + partnerCommissionGraphList.size());
				model.put("shortTablePartner", partnerCommissionGraphList);
				List<CommissionDetails> categoryCommissionGraphList = reportGeneratorService
						.fetchPC(sellerId, startDate, endDate, "category");
				log.info("CategoryList: " + categoryCommissionGraphList.size());
				model.put("shortTableCategory", categoryCommissionGraphList);

				Collections.sort(partnerCommissionGraphList,
						new CommissionDetails.OrderByGrossCommission());
				model.put("partnerByGrossComm", ConverterClass
						.getCommissionSortedList(partnerCommissionGraphList,
								"GrossComm"));
				Collections.sort(partnerCommissionGraphList,
						new CommissionDetails.OrderByNetChannelCommission());
				model.put("partnerByNetChann", ConverterClass
						.getCommissionSortedList(partnerCommissionGraphList,
								"NetChann"));

				Collections.sort(categoryCommissionGraphList,
						new CommissionDetails.OrderByGrossCommission());
				model.put("categoryByGrossComm", ConverterClass
						.getCommissionSortedList(categoryCommissionGraphList,
								"GrossComm"));
				Collections.sort(categoryCommissionGraphList,
						new CommissionDetails.OrderByNetChannelCommission());
				model.put("categoryByNetChann", ConverterClass
						.getCommissionSortedList(categoryCommissionGraphList,
								"NetChann"));

				List<CommissionAnalysis> partnerBusinessGraphList = ConverterClass
						.transformCommAGraph(partnerBusinessList, "partner");
				model.put("commTablePartner", ConverterClass
						.getCommASortedList(partnerBusinessGraphList));
				List<CommissionAnalysis> categoryBusinessGraphList = ConverterClass
						.transformCommAGraph(partnerBusinessList, "category");
				model.put("commTableCategory", ConverterClass
						.getCommASortedList(categoryBusinessGraphList));

				List<MonthlyCommission> monthlyGraph = reportGeneratorService
						.fetchMonthlyComm(sellerId, startDate, endDate);
				model.put("monthlyGraph", monthlyGraph);
				endreportCalc=System.currentTimeMillis();
				log.info("Time take to generate partnerCommissionReport : "+(endreportCalc-startreportCalc));
				log.info("$$$ getPartnerReport end : ReportController $$$");
				return new ModelAndView("reports/viewCommGraphReport", model);
			}
		} catch (CustomException ce) {
			log.error("getReport exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("Failed! By Seller ID : " + sellerId, ex);
			model.put("errorMessage", ex.getMessage());
		}
		endreportCalc=System.currentTimeMillis();
		log.info("Time take to generate report : "+(endreportCalc-startreportCalc));
		log.info("$$$ getPartnerReport end : ReportController $$$");

		return new ModelAndView("globalErorPage", model);
	}

	@RequestMapping(value = "/seller/getReport", method = RequestMethod.POST)
	public ModelAndView getReport(HttpServletRequest request) throws Exception {
		log.info("$$$ getReport Starts : ReportController $$$");
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		List<TotalShippedOrder> ttso = new ArrayList<>();
		String reportName = null;
		Date startDate;
		Date endDate;
		String status;
		List<ConsolidatedOrderBean> consolidatedBeans = new ArrayList<ConsolidatedOrderBean>();
		try {
			reportName = request.getParameter("reportName");
			startDate = new Date(request.getParameter("startdate"));
			endDate = new Date(request.getParameter("enddate"));
			status = request.getParameter("statusList");

			consolidatedBeans = reportGeneratorService
					.getConsolidatedOrdersReport(startDate, endDate, status,
							helperClass.getSellerIdfromSession(request));
			model.put("consolidatedOrders", consolidatedBeans);

			ttso = reportGeneratorService.getAllPartnerTSOdetails(startDate,
					endDate, helperClass.getSellerIdfromSession(request));
			if (ttso != null)
				System.out
						.println(" ****Inside controller after gettitng ttso objkect : "
								+ ttso.size());
			else
				System.out.println(" TTSO object is geting null");
			model.put("reportNameStr",
					GlobalConstant.reportNameMap.get(reportName));
			model.put("ttsolist", ttso);
			if (ttso.size() > 0) {
				System.out.println(" Citi quantity size : "
						+ ttso.get(0).getCityQuantity());
				System.out.println(" Citi percent size : "
						+ ttso.get(0).getCityPercentage());
				model.put("citicount", ttso.get(0).getCityQuantity());
				model.put("citipercent", ttso.get(0).getCityPercentage());
			}
			Collections.sort(ttso, new TotalShippedOrder.OrderByNR());
			model.put("NRsortedttso", getSortedList(ttso));
			Collections.sort(ttso, new TotalShippedOrder.OrderByReturnamount());
			model.put("returnAmountsortedttso", getSortedList(ttso));
			Collections.sort(ttso,
					new TotalShippedOrder.OrderByReturnQuantity());
			model.put("returnQsortedttso", getSortedList(ttso));
			Collections.sort(ttso, new TotalShippedOrder.OrderBySaleQuantity());
			model.put("saleQsortedttso", getSortedList(ttso));
			Collections.sort(ttso, new TotalShippedOrder.OrderBySaleamount());
			model.put("saleAmounrsortedttso", getSortedList(ttso));

			model.put(
					"period",
					new SimpleDateFormat("dd-MM-yyyy").format(startDate)
							+ " to "
							+ new SimpleDateFormat("dd-MM-yyyy")
									.format(endDate));
		} catch (CustomException ce) {
			log.error("getReport exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! By Seller ID : " + sellerId, e);
		}
		log.info("$$$ getReport Ends : ReportController $$$");
		if (reportName.equals("channelSaleReport"))
			return new ModelAndView("reports/viewChannelSaleGraphReport", model);
		else if (reportName.equals("categoryWiseSaleReport"))
			return new ModelAndView("reports/viewCategorySaleGraphReport",
					model);
		else
			return new ModelAndView("reports/viewGraphReport", model);
	}

	@RequestMapping(value = "/seller/getChannelReport", method = RequestMethod.POST)
	public ModelAndView getChannelReport(HttpServletRequest request)
			throws Exception {
		log.info("$$$ getChannelReport Starts : ReportController $$$");
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		String reportName = null;
		try {
			reportName = request.getParameter("reportName");
			String startDateStr = request.getParameter("startdate");
			String endDateStr = request.getParameter("enddate");
			Date startDate = null;
			if (StringUtils.isNotBlank(startDateStr))
				startDate = new Date(startDateStr);
			Date endDate = null;
			if (StringUtils.isNotBlank(endDateStr))
				endDate = new Date(endDateStr);
			sellerId = helperClass.getSellerIdfromSession(request);

			model.put("reportNameStr",
					GlobalConstant.reportNameMap.get(reportName));
			List<ChannelReportDetails> channelReportDetailsList = reportGeneratorService
					.getChannelReportDetails(startDate, endDate, sellerId,
							reportName);
			List<ChannelReportDetails> partnerListST = new ArrayList<ChannelReportDetails>();
			List<ChannelReportDetails> categoryListST = new ArrayList<ChannelReportDetails>();
			List<ChannelReportDetails> partnerList = ConverterClass
					.transformChannelReport(channelReportDetailsList, "partner");
			List<ChannelReportDetails> categoryList = ConverterClass
					.transformChannelReport(channelReportDetailsList,
							"category");
			List<ChannelReportDetails> monthlyList = new ArrayList<ChannelReportDetails>();

			switch (reportName) {
			case "channelSaleReport":
				partnerListST = ConverterClass.transformChannelReportST(
						channelReportDetailsList, "partner");
				Collections.sort(partnerListST,
						new ChannelReportDetails.OrderByTC());
				model.put("shortTablePartner", partnerListST);

				Collections.sort(partnerList,
						new ChannelReportDetails.OrderByNR());
				model.put("partnerByNR", ConverterClass.getChannelSortedList(
						partnerList, "NetSaleSP"));
				Collections.sort(partnerList,
						new ChannelReportDetails.OrderByGSvSR());
				model.put("partnerByGSvSR", ConverterClass
						.getChannelSortedList(partnerList, "GSvSR"));

				Collections.sort(partnerList,
						new ChannelReportDetails.OrderByEOSS());
				model.put("partnerByEOSS", ConverterClass.getChannelSortedList(
						partnerList, "EOSS"));
				Collections.sort(partnerList,
						new ChannelReportDetails.OrderByTaxableSale());
				model.put("partnerByTaxableSale", ConverterClass
						.getChannelSortedList(partnerList, "TaxableSale"));
				Collections.sort(partnerList,
						new ChannelReportDetails.OrderByActualSale());
				model.put("partnerByActualSale", ConverterClass
						.getChannelSortedList(partnerList, "ActualSale"));
				Collections.sort(partnerList,
						new ChannelReportDetails.OrderByTaxfreeSale());
				model.put("partnerByTaxfreeSale", ConverterClass
						.getChannelSortedList(partnerList, "TaxfreeSale"));

				monthlyList = ConverterClass.getChannelMonthlyList(
						channelReportDetailsList, startDate, endDate);
				Collections.sort(monthlyList,
						new ChannelReportDetails.OrderByMonthIndex());
				model.put("monthlySale", monthlyList);
				break;
			case "categoryWiseSaleReport":
				categoryListST = ConverterClass.transformChannelReportST(
						channelReportDetailsList, "category");
				Collections.sort(categoryListST,
						new ChannelReportDetails.OrderByTC());
				model.put("shortTableCategory", categoryListST);

				categoryListST = ConverterClass.transformChannelReportST(
						channelReportDetailsList, "category1");
				Collections.sort(categoryListST,
						new ChannelReportDetails.OrderByTC());
				model.put("shortTableCategory1", categoryListST);

				Collections.sort(categoryList,
						new ChannelReportDetails.OrderByNR());
				model.put("categoryByNR", ConverterClass.getChannelSortedList(
						categoryList, "NetSaleSP"));
				Collections.sort(categoryList,
						new ChannelReportDetails.OrderByGSvSR());
				model.put("categoryByGSvSR", ConverterClass
						.getChannelSortedList(categoryList, "GSvSR"));

				Collections.sort(categoryList,
						new ChannelReportDetails.OrderByEOSS());
				model.put("categoryByEOSS", ConverterClass
						.getChannelSortedList(categoryList, "EOSS"));
				Collections.sort(categoryList,
						new ChannelReportDetails.OrderByTaxableSale());
				model.put("categoryByTaxableSale", ConverterClass
						.getChannelSortedList(categoryList, "TaxableSale"));
				Collections.sort(categoryList,
						new ChannelReportDetails.OrderByActualSale());
				model.put("categoryByActualSale", ConverterClass
						.getChannelSortedList(categoryList, "ActualSale"));
				Collections.sort(categoryList,
						new ChannelReportDetails.OrderByTaxfreeSale());
				model.put("categoryByTaxfreeSale", ConverterClass
						.getChannelSortedList(categoryList, "TaxfreeSale"));

				monthlyList = ConverterClass.getChannelMonthlyList(
						channelReportDetailsList, startDate, endDate);
				Collections.sort(monthlyList,
						new ChannelReportDetails.OrderByMonthIndex());
				model.put("monthlySale", monthlyList);
				break;
			case "orderwiseGPReport":
				model.put("shortTablePartner", partnerList);
				model.put("shortTableCategory", categoryList);
				Collections.sort(partnerList,
						new ChannelReportDetails.OrderByGrossProfit());
				model.put("partnerByGrossProfit", ConverterClass
						.getChannelSortedList(partnerList, "GrossProfit"));
				Collections.sort(partnerList,
						new ChannelReportDetails.OrderByGPCP());
				model.put("partnerByGPCP", ConverterClass.getChannelSortedList(
						partnerList, "GPCP"));
				Collections.sort(partnerList,
						new ChannelReportDetails.OrderByGNR());
				model.put("partnerByPR",
						ConverterClass.getChannelSortedList(partnerList, "PR"));
				Collections.sort(partnerList,
						new ChannelReportDetails.OrderByNR());
				model.put("partnerByGNR",
						ConverterClass.getChannelSortedList(partnerList, "GNR"));
				List<ChannelGP> partnerGPList = reportGeneratorService
						.getChannelNetGPList(startDate, endDate, sellerId,
								"partner");
				model.put("partnerByNetGP",
						ConverterClass.getChannelNetGPSortedList(partnerGPList));
				Collections.sort(categoryList,
						new ChannelReportDetails.OrderByGrossProfit());
				model.put("categoryByGrossProfit", ConverterClass
						.getChannelSortedList(categoryList, "GrossProfit"));
				Collections.sort(categoryList,
						new ChannelReportDetails.OrderByGPCP());
				model.put("categoryByGPCP", ConverterClass
						.getChannelSortedList(categoryList, "GPCP"));
				Collections.sort(categoryList,
						new ChannelReportDetails.OrderByNR());
				model.put("categoryByPR",
						ConverterClass.getChannelSortedList(categoryList, "PR"));
				Collections.sort(categoryList,
						new ChannelReportDetails.OrderByGNR());
				model.put("categoryByGNR", ConverterClass.getChannelSortedList(
						categoryList, "GNR"));
				List<ChannelGP> categoryGPList = reportGeneratorService
						.getChannelNetGPList(startDate, endDate, sellerId,
								"category");
				Collections
						.sort(categoryGPList, new ChannelGP.OrderByTotalGP());
				model.put("categoryByNetGP", ConverterClass
						.getChannelNetGPSortedList(categoryGPList));
				break;
			case "totalPaymentsReceived":
				List<ChannelNPR> partnerChannelNprList = reportGeneratorService
						.fetchChannelNPR(sellerId, startDate, endDate,
								"partner");
				Collections.sort(partnerChannelNprList,
						new ChannelNPR.OrderByNPR());
				model.put("partnerByNPR", ConverterClass
						.getChannelNPRSortedList(partnerChannelNprList));
				List<ChannelNPR> categoryChannelNprList = reportGeneratorService
						.fetchChannelNPR(sellerId, startDate, endDate,
								"category");
				Collections.sort(categoryChannelNprList,
						new ChannelNPR.OrderByNPR());
				model.put("categoryByNPR", ConverterClass
						.getChannelNPRSortedList(categoryChannelNprList));
				List<ChannelCatNPR> channelCatNprList = reportGeneratorService
						.fetchChannelCatNPR(sellerId, startDate, endDate, "");
				Set<String> categories = getCategories(categoryChannelNprList);
				model.put("categories", categories);
				Collections.sort(channelCatNprList,
						new ChannelCatNPR.OrderByNPR());
				model.put("channelCatNPR", ConverterClass
						.getChannelCatNPRSortedList(channelCatNprList));
				List<ChannelMC> channelMCList = reportGeneratorService
						.fetchChannelMC(sellerId, startDate, endDate, "");
				model.put("channelMC", channelMCList);
				List<NetPaymentResult> nprList = reportGeneratorService
						.fetchNPR(sellerId, startDate, endDate);
				model.put("nprList", nprList);
				List<ChannelMCNPR> shortTableList = reportGeneratorService
						.fetchChannelMCNPR(sellerId, startDate, endDate,
								"partner");
				Collections.sort(shortTableList,
						new ChannelMCNPR.OrderByPartner());
				model.put("shortTable", shortTableList);
				List<ManualCharges> manualChargesList = manualChargesService
						.listManualCharges(sellerId, startDate, endDate);
				model.put("shortTableMC", manualChargesList);
			default:
				break;
			}

			model.put("channelReportDetails", channelReportDetailsList);
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
			log.error("Failed! By Seller ID : " + sellerId, e);
		}
		log.info("$$$ getChannelReport Ends : ReportController $$$");
		if (reportName.equals("channelSaleReport"))
			return new ModelAndView("reports/viewChannelSaleGraphReport", model);
		else if (reportName.equals("categoryWiseSaleReport"))
			return new ModelAndView("reports/viewCategorySaleGraphReport",
					model);
		else if (reportName.equals("orderwiseGPReport"))
			return new ModelAndView("reports/orderwiseGPReport", model);
		else
			return new ModelAndView("reports/paymentsReceievedReport", model);
	}

	private Set<String> getCategories(List<ChannelNPR> channelCatNprList) {
		Set<String> categorySet = new HashSet<String>();
		for (ChannelNPR channelNPR : channelCatNprList) {
			categorySet.add(channelNPR.getCategory());
		}
		return categorySet;
	}

	@RequestMapping(value = "/seller/downloadPartnerReport", method = RequestMethod.POST)
	public void downloadPartnerReport(HttpServletRequest request,
			HttpServletResponse response) {

		log.info("$$$ downloadPartnerReport Starts : ReportController $$$");
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String reportName = request.getParameter("reportName");
			String startDateStr = request.getParameter("startdate");
			String endDateStr = request.getParameter("enddate");
			Map<String,Object> obj=null;
			Date startDate = null;
			if (StringUtils.isNotBlank(startDateStr))
				startDate = new Date(startDateStr);
			Date endDate = null;
			if (StringUtils.isNotBlank(endDateStr))
				endDate = new Date(endDateStr);
			String[] reportheaders = request.getParameterValues("headers");
			sellerId = helperClass.getSellerIdfromSession(request);

			List<PartnerReportDetails> partnerBusinessList = new ArrayList<PartnerReportDetails>();
			if ("debtorsReport".equalsIgnoreCase(reportName)) {
				
						obj=reportGeneratorService
						.getDebtorsReportDetails(startDate, endDate, sellerId);
						partnerBusinessList =(List<PartnerReportDetails>)obj.get("list");
			} else {
				partnerBusinessList = reportGeneratorService
						.getPartnerReportDetails(startDate, endDate, sellerId);
			}
			Collections.sort(partnerBusinessList,
					new PartnerReportDetails.OrderByShippedDate());
			reportDownloadService.downloadPartnerReport(response,
					partnerBusinessList, reportheaders, reportName, sellerId);
		} catch (ClassNotFoundException e) {
			System.out.println(" Class castexception in download report");
			e.printStackTrace();
			log.error(e);
		} catch (CustomException ce) {
			ce.printStackTrace();
			log.error("downloadReport exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! By Seller ID : " + sellerId, e);
		}
		log.info("$$$ downloadreport Ends : ReportController $$$");
	}

	@RequestMapping(value = "/seller/downloadChannelReport", method = RequestMethod.POST)
	public void downloadChannelReport(HttpServletRequest request,
			HttpServletResponse response) {

		log.info("$$$ downloadPartnerReport Starts : ReportController $$$");
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String reportName = request.getParameter("reportName");
			Date startDate = new Date(request.getParameter("startdate"));
			Date endDate = new Date(request.getParameter("enddate"));
			String[] reportheaders = request.getParameterValues("headers");
			sellerId = helperClass.getSellerIdfromSession(request);

			List<ChannelReportDetails> channelReportList = reportGeneratorService
					.getChannelReportDetails(startDate, endDate, sellerId,
							reportName);
			Collections.sort(channelReportList,
					new ChannelReportDetails.OrderByShippedDate());
			reportDownloadService.downloadChannelReport(response,
					channelReportList, reportheaders, reportName, sellerId);
		} catch (ClassNotFoundException e) {
			System.out.println(" Class castexception in download report");
			e.printStackTrace();
			log.error(e);
		} catch (CustomException ce) {
			ce.printStackTrace();
			log.error("downloadReport exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! By Seller ID : " + sellerId, e);
		}
		log.info("$$$ downloadreport Ends : ReportController $$$");
	}

	@RequestMapping(value = "/seller/downloadRevenueReport", method = RequestMethod.POST)
	public void downloadRevenueReport(HttpServletRequest request,
			HttpServletResponse response) {

		log.info("$$$ downloadPartnerReport Starts : ReportController $$$");
		int sellerId = 0;
		try {
			String reportName = request.getParameter("reportName");
			String selectedYear = request.getParameter("selectedYear");
			int selectedYearInt = 2016;
			if (StringUtils.isNotBlank(selectedYear))
				selectedYearInt = Integer.parseInt(selectedYear);
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = ft.parse(selectedYearInt + "-04-01");
			Date endDate = ft.parse((selectedYearInt + 1) + "-03-31");
			sellerId = helperClass.getSellerIdfromSession(request);

			List<YearlyStockList> revenueReportList = reportGeneratorService
					.fetchStockList(selectedYearInt);
			Collections.sort(revenueReportList,
					new YearlyStockList.OrderByMonthCat());
			List<Expenses> expensesList = expenseService.getExpenseByDate(
					startDate, endDate, sellerId);
			List<ChannelNR> nrList = reportGeneratorService.fetchNetRate(
					sellerId, startDate, endDate);
			reportDownloadService.downloadRevenueReport(response,
					revenueReportList, expensesList, nrList, reportName);
		} catch (ClassNotFoundException e) {
			System.out.println(" Class castexception in download report");
			e.printStackTrace();
			log.error(e);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("! By Seller ID : " + sellerId, e);
		}
		log.info("$$$ downloadreport Ends : ReportController $$$");
	}

	@RequestMapping(value = "/seller/downloadreport", method = RequestMethod.POST)
	public void downloadreport(HttpServletRequest request,
			HttpServletResponse response) {

		log.info("$$$ downloadreport Starts : ReportController $$$");
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		List<TotalShippedOrder> ttso = new ArrayList<>();
		List<Order> orderlist = new ArrayList<>();
		String reportName;
		Date startDate;
		Date endDate;
		String status;
		String[] reportheaders;
		log.debug(request.getParameter("enddate"));
		log.debug(request.getParameter("startdate"));

		reportName = request.getParameter("reportName");
		startDate = new Date(request.getParameter("startdate"));
		endDate = new Date(request.getParameter("enddate"));
		reportheaders = request.getParameterValues("headers");
		status = request.getParameter("statusList");
		try {
			orderlist = orderService
					.findOrdersbyDate("shippedDate", startDate, endDate,
							helperClass.getSellerIdfromSession(request), false);
			
			reportDownloadService.downloadReport(response, status, orderlist,
					reportheaders, reportName,
					helperClass.getSellerIdfromSession(request));
		} catch (ClassNotFoundException e) {
			System.out.println(" Class castexception in download report");
			e.printStackTrace();
			log.error("Failed ! in ReportContrller ",e);
		} catch (CustomException ce) {
			ce.printStackTrace();
			log.error("downloadReport exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! By Seller ID : " + sellerId, e);
		}
		log.debug(" Got response ttso size in controller: " + ttso.size());
		log.info("$$$ downloadreport Ends : ReportController $$$");
	}

	@RequestMapping(value = "/seller/downloadOrderReport", method = RequestMethod.POST)
	public void downloadOrderReport(HttpServletRequest request,
			HttpServletResponse response) {

		log.info("$$$ downloadOrderReport Starts : ReportController $$$");
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		List<ChannelSalesDetails> orderlist = new ArrayList<>();
		String reportName;
		Date startDate;
		Date endDate;
		String[] reportheaders;
		// System.out.println(" Cat :" + partner);

		reportName = request.getParameter("reportName");
		startDate = new Date(request.getParameter("startdate"));
		endDate = new Date(request.getParameter("enddate"));
		reportheaders = request.getParameterValues("headers");
		try {
			if (reportName.equalsIgnoreCase("channelSaleReport")) {
				orderlist = orderService.findChannelOrdersbyDate("orderDate",
						startDate, endDate,
						helperClass.getSellerIdfromSession(request));
				reportDownloadService.downloadCOReport(response, orderlist,
						reportheaders, "ChannelSalesReport",
						helperClass.getSellerIdfromSession(request));
			} else if (reportName.equalsIgnoreCase("categoryWiseSaleReport")) {
				orderlist = orderService.findChannelOrdersbyDate("orderDate",
						startDate, endDate,
						helperClass.getSellerIdfromSession(request));
				reportDownloadService.downloadCOReport(response, orderlist,
						reportheaders, "ChannelSalesReport",
						helperClass.getSellerIdfromSession(request));

			} else if (reportName.equalsIgnoreCase("paymentsReceievedReport")) {
				orderlist = orderService.findChannelOrdersbyDate("orderDate",
						startDate, endDate,
						helperClass.getSellerIdfromSession(request));
				reportDownloadService.downloadCOReport(response, orderlist,
						reportheaders, "ChannelSalesReport",
						helperClass.getSellerIdfromSession(request));

			} else if (reportName.equalsIgnoreCase("orderwiseGPReport")) {
				orderlist = orderService.findChannelOrdersbyDate("orderDate",
						startDate, endDate,
						helperClass.getSellerIdfromSession(request));
				reportDownloadService.downloadCOReport(response, orderlist,
						reportheaders, "ChannelSalesReport",
						helperClass.getSellerIdfromSession(request));

			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			log.error(e);
		} catch (CustomException ce) {
			log.error("downloadReport exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			// return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			log.error("Failed! By Seller ID : " + sellerId, e);
			log.error(e);
		}
		log.info("$$$ downloadOrderReport Ends : ReportController $$$");

	}

	public List<TotalShippedOrder> getSortedList(List<TotalShippedOrder> ttso) {
		log.info("$$$ getSortedList Starts : ReportController $$$");
		List<TotalShippedOrder> returnlist = new ArrayList<TotalShippedOrder>();
		TotalShippedOrder fifthreco = null;
		if (ttso != null && ttso.size() > 4) {
			returnlist.add(ttso.get(0));
			returnlist.add(ttso.get(1));
			returnlist.add(ttso.get(2));
			returnlist.add(ttso.get(3));
			int i = 0;
			fifthreco = new TotalShippedOrder();
			for (TotalShippedOrder newttso : ttso) {

				if (i > 3) {
					fifthreco.setSaleQuantity(fifthreco.getSaleQuantity()
							+ newttso.getSaleQuantity());
					fifthreco.setSaleQuantityPercent(fifthreco
							.getSaleQuantityPercent()
							+ newttso.getSaleQuantityPercent());
					fifthreco.setReturnQuantity(fifthreco.getReturnQuantity()
							+ newttso.getReturnQuantity());
					fifthreco.setReturnQuantityPercent(fifthreco
							.getReturnQuantityPercent()
							+ newttso.getReturnQuantityPercent());
					fifthreco.setNr(fifthreco.getNr() + newttso.getNr());
					fifthreco.setNrPercent(fifthreco.getNrPercent()
							+ newttso.getNrPercent());
					fifthreco.setReturnAmount(fifthreco.getReturnAmount()
							+ newttso.getReturnAmount());
					fifthreco.setReturnAmountPercent(fifthreco
							.getReturnAmountPercent()
							+ newttso.getReturnAmountPercent());
					fifthreco.setNetSaleAmount(fifthreco.getNetSaleAmount()
							+ newttso.getNetSaleAmount());
					fifthreco.setNetSaleAmountPercent(fifthreco
							.getNetSaleAmountPercent()
							+ newttso.getNetSaleAmountPercent());
					fifthreco.setNoOfDeliveredOrder(fifthreco
							.getNoOfDeliveredOrder()
							+ newttso.getNoOfDeliveredOrder());
					fifthreco.setDeliveredOrderPercent(fifthreco
							.getDeliveredOrderPercent()
							+ newttso.getDeliveredOrderPercent());
					fifthreco.setNoOfReturnOrder(fifthreco.getNoOfReturnOrder()
							+ newttso.getNoOfReturnOrder());
					fifthreco.setReturnOrderPercent(fifthreco
							.getNetSaleAmount() + newttso.getNetSaleAmount());
					fifthreco.setNoOfRTOOrder(fifthreco.getNoOfRTOOrder()
							+ newttso.getNoOfRTOOrder());
					fifthreco.setRTOOrderPercent(fifthreco.getRTOOrderPercent()
							+ newttso.getRTOOrderPercent());
					fifthreco.setNoOfRTOLimitCrossed(fifthreco
							.getNoOfRTOLimitCrossed()
							+ newttso.getNoOfRTOLimitCrossed());
					fifthreco.setRTOLimitCrossedPercent(fifthreco
							.getRTOLimitCrossedPercent()
							+ newttso.getRTOLimitCrossedPercent());
					fifthreco.setNoOfReturnLimitCrossed(fifthreco
							.getNoOfReturnLimitCrossed()
							+ newttso.getNoOfReturnLimitCrossed());
					fifthreco.setReturnLimitCrossedPercent(fifthreco
							.getReturnLimitCrossedPercent()
							+ newttso.getReturnLimitCrossedPercent());
					fifthreco.setNoOfActionableOrders(fifthreco
							.getNoOfActionableOrders()
							+ newttso.getNoOfActionableOrders());
					fifthreco.setActionableOrdersPercent(fifthreco
							.getActionableOrdersPercent()
							+ newttso.getActionableOrdersPercent());
					fifthreco.setNoOfSettledOrders(fifthreco
							.getNoOfSettledOrders()
							+ newttso.getNoOfSettledOrders());
					fifthreco.setSettledOrdersPercent(fifthreco
							.getSettledOrdersPercent()
							+ newttso.getSettledOrdersPercent());
				}
				i++;
			}

			fifthreco.setPcName("Others");
			returnlist.add(fifthreco);

		} else if (ttso != null && ttso.size() < 5 && ttso.size() > 0) {
			for (int i = 0; i < ttso.size(); i++) {
				returnlist.add(ttso.get(i));
			}
		}

		log.info("$$$ getSortedList Ends : ReportController $$$");
		return returnlist;

	}

}
