
package com.o2r.service;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.o2r.bean.ChannelNR;
import com.o2r.bean.ChannelReportDetails;
import com.o2r.bean.ChannelSalesDetails;
import com.o2r.bean.PartnerReportDetails;
import com.o2r.bean.YearlyStockList;
import com.o2r.helper.CustomException;
import com.o2r.helper.FillManager;
import com.o2r.helper.HelperClass;
import com.o2r.helper.Layouter;
import com.o2r.helper.Writer;
import com.o2r.model.Expenses;
import com.o2r.model.Order;


/**
 * Service for processing Apache POI-based reports
 * 
 * @author 'Deep Mehrotra
 */
@Service("reportDownloadService")
@Transactional
public class ReportDownloadService {

	@Autowired
	private ProductService productService;
	@Autowired
	private HelperClass helperClass;
	@Autowired
	private CategoryService categoryService;
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	/**
	 * Processes the download for Excel format.
	 * It does the following steps:
	 * <pre>1. Create new workbook
	 * 2. Create new worksheet
	 * 3. Define starting indices for rows and columns
	 * 4. Build layout 
	 * 5. Fill report
	 * 6. Set the HttpServletResponse properties
	 * 7. Write to the output stream
	 * </pre>
	 */
	
	public void downloadReport(HttpServletResponse response , String status, List<Order> orderlist, String[] headers,String reportname ,int sellerId) throws ClassNotFoundException {
		
		
		// 1. Create new workbook
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		// 2. Create new worksheet
		HSSFSheet worksheet = workbook.createSheet(reportname);
		
		// 3. Define starting indices for rows and columns
		int startRowIndex = 0;
		int startColIndex = 0;
		
		// 4. Build layout 
		// Build title, date, and column headers
		Layouter.buildOrderReport(worksheet, startRowIndex, startColIndex,reportname,headers);
		
		// 5. Fill report
		
		try {
			FillManager.fillReport(worksheet, startRowIndex, startColIndex,status, orderlist , headers, productService,categoryService, sellerId );
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		
		// 6. Set the response properties
		String fileName = "Consolidated_Order_Report.xls";
		response.setHeader("Content-Disposition", "inline; filename=" + fileName);
		// Make sure to set the correct content type
		response.setContentType("application/vnd.ms-excel");
		
		//7. Write to the output stream
		Writer.write(response, worksheet,fileName);
	}

	public void downloadCOReport(HttpServletResponse response,List<ChannelSalesDetails> orderlist, String[] reportheaders,
			String reportName, int sellerIdfromSession) {
		
		// 1. Create new workbook
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		// 2. Create new worksheet
		HSSFSheet worksheet = workbook.createSheet(reportName);
		
		// 3. Define starting indices for rows and columns
		int startRowIndex = 0;
		int startColIndex = 0;
		
		// 4. Build layout 
		// Build title, date, and column headers
		Layouter.buildChannelOrderReport(worksheet, startRowIndex, startColIndex,reportName,reportheaders);

		// 5. Fill report
		FillManager.fillCOReport(worksheet, startRowIndex, startColIndex, orderlist , reportheaders);
		
		// 6. Set the response properties
		String fileName = "Consolidated_Order_Report.xls";
		response.setHeader("Content-Disposition", "inline; filename=" + reportName);
		// Make sure to set the correct content type
	//	response.setContentType("application/vnd.ms-excel");
 		
		//7. Write to the output stream
		Writer.write(response, worksheet,reportName);
	
	}
	
	public void downloadPartnerReport(HttpServletResponse response , List<PartnerReportDetails> partnerlist, 
			String[] headers,String reportname ,int sellerId) throws ClassNotFoundException {	
		
		// 1. Create new workbook
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		// 2. Create new worksheet
		HSSFSheet worksheet = workbook.createSheet(reportname);
		
		// 3. Define starting indices for rows and columns
		int startRowIndex = 0;
		int startColIndex = 0;
		
		// 4. Build layout 
		// Build title, date, and column headers
		Layouter.buildOrderReport(worksheet, startRowIndex, startColIndex,reportname,headers);

		// 5. Fill report
		FillManager.fillPartnerReport(worksheet, startRowIndex, startColIndex, partnerlist , headers);
		
		// 6. Set the response properties
		String fileName = "Partner_Report.xls";
		switch(reportname){
			case "partnerBusinessReport": fileName = "Partner_Business_Report.xls";break;
			case "partnerCommissionReport": fileName = "Partner_Commission_Report.xls";break;
			case "debtorsReport": fileName = "Debtors_Report.xls";break;
			default: break;
		}
		response.setHeader("Content-Disposition", "inline; filename=" + fileName);
		// Make sure to set the correct content type
		response.setContentType("application/vnd.ms-excel");
		
		//7. Write to the output stream
		Writer.write(response, worksheet,fileName);
	}
	
	public void downloadChannelReport(HttpServletResponse response , List<ChannelReportDetails> partnerlist, 
			String[] headers, String reportName ,int sellerId) throws ClassNotFoundException {	
		
		// 1. Create new workbook
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		// 2. Create new worksheet
		HSSFSheet worksheet = workbook.createSheet(reportName);
		
		// 3. Define starting indices for rows and columns
		int startRowIndex = 0;
		int startColIndex = 0;
		
		// 4. Build layout 
		// Build title, date, and column headers
		if("paymentsReceievedReport".equalsIgnoreCase(reportName)){
			String[] headers2 = new String[headers.length];
			for(int index=0; index<headers2.length; index++){
				switch(headers[index]){
					case "getGrossNrAmount": headers2[index] =  "Net N/R"; break;
					case "getNetReturnCharges": headers2[index] =  "Additional Return Charges"; break;
					case "getSaleRetNrAmount": headers2[index] =  "Total Return Charges"; break;
					case "getNetNrAmount": headers2[index] =  "Net Actual Sale"; break;
					default: headers2[index] = headers[index]; break;
				}					
			}
			Layouter.buildOrderReport(worksheet, startRowIndex, startColIndex, reportName, headers2);
		} else{
			Layouter.buildOrderReport(worksheet, startRowIndex, startColIndex, reportName, headers);
		}

		// 5. Fill report
		FillManager.fillChannelReport(worksheet, startRowIndex, startColIndex, partnerlist , headers);
		
		// 6. Set the response properties
		String fileName = "Partner_Report.xls";
		switch(reportName){
			case "channelSaleReport": fileName = "Channel_Sale_Report.xls";break;
			case "categoryWiseSaleReport": fileName = "Category_Wise_Sale_Report.xls";break;
			case "orderwiseGPReport": fileName = "Orderwise_GP_Report.xls";break;
			case "paymentsReceievedReport": fileName = "Total_Payments_Received_Report.xls";break;
			default: break;
		}
		response.setHeader("Content-Disposition", "inline; filename=" + fileName);
		// Make sure to set the correct content type
		response.setContentType("application/vnd.ms-excel");
		
		//7. Write to the output stream
		Writer.write(response, worksheet,fileName);
	}
	
	public void downloadRevenueReport(HttpServletResponse response,
			List<YearlyStockList> revenueReportList,
			List<Expenses> expensesList, List<ChannelNR> nrList,
			String reportName) throws ClassNotFoundException {	
		
		// 1. Create new workbook
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		// Begin: Expense Sheet
		String[] headers = new String[4];
		headers[0] = "getExpenseDate";
		headers[1] = "getExpenseName";
		headers[2] = "getExpenseCatName";
		headers[3] = "getAmount";		
		
		// 2. Create new worksheet
		HSSFSheet worksheet = workbook.createSheet("Gross Expenses");
		
		// 3. Define starting indices for rows and columns
		int startRowIndex = 0;
		int startColIndex = 0;

		// 4. Build layout 
		// Build title, date, and column headers
		Layouter.buildOrderReport(worksheet, startRowIndex, startColIndex, reportName, headers);

		// 5. Fill report
		FillManager.fillExpenseReport(worksheet, startRowIndex, startColIndex, expensesList, headers);
		// End: Expense Sheet
		
		// Begin: Net Sales Sheet
		headers = new String[2];
		headers[0] = "getKey";
		headers[1] = "getTotalNR";
		
		// 2. Create new worksheet
		HSSFSheet worksheet2 = workbook.createSheet("Net Sales Receipts");
		
		// 3. Define starting indices for rows and columns
		startRowIndex = 0;
		startColIndex = 0;

		// 4. Build layout 
		// Build title, date, and column headers
		Layouter.buildOrderReport(worksheet2, startRowIndex, startColIndex, reportName, headers);

		// 5. Fill report
		FillManager.fillNetRateReport(worksheet2, startRowIndex, startColIndex, nrList, headers);
		// End: Expense Sheet
		// Begin: Expense Sheet
		headers = new String[6];
		headers[0] = "getMonthStr";
		headers[1] = "getCategory";
		headers[2] = "getOpenStock";
		headers[3] = "getOpenStockValuation";		
		headers[4] = "getCloseStock";
		headers[5] = "getCloseStockValuation";		
		
		// 2. Create new worksheet
		HSSFSheet worksheet3 = workbook.createSheet("Stock Valuation");
		
		// 3. Define starting indices for rows and columns
		startRowIndex = 0;
		startColIndex = 0;

		// 4. Build layout 
		// Build title, date, and column headers
		Layouter.buildOrderReport(worksheet3, startRowIndex, startColIndex, reportName, headers);

		// 5. Fill report
		FillManager.fillStockReport(worksheet3, startRowIndex, startColIndex, revenueReportList, headers);
		// End: Expense Sheet

		// 6. Set the response properties
		String fileName = "Revenue_Report.xls";
		switch(reportName){
			case "viewBusinessProfitReport.jsp": fileName = "Net_Profitability_Report.xls";break;
			default: break;
		}
		response.setHeader("Content-Disposition", "inline; filename=" + fileName);
		// Make sure to set the correct content type
		response.setContentType("application/vnd.ms-excel");
		
		//7. Write to the output stream
		Writer.write(response, worksheet,fileName);
	}
}