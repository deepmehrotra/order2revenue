
package com.o2r.service;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.o2r.bean.ChannelReportDetails;
import com.o2r.bean.ChannelSalesDetails;
import com.o2r.bean.PartnerReportDetails;
import com.o2r.helper.FillManager;
import com.o2r.helper.Layouter;
import com.o2r.helper.Writer;
import com.o2r.model.Order;


/**
 * Service for processing Apache POI-based reports
 * 
 * @author 'Deep Mehrotra
 */
@Service("reportDownloadService")
@Transactional
public class ReportDownloadService {

	
	
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
	
	public void downloadReport(HttpServletResponse response , List<Order> orderlist, String[] headers,String reportname ,int sellerId) throws ClassNotFoundException {
		
		
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
		FillManager.fillReport(worksheet, startRowIndex, startColIndex, orderlist , headers);
		
		// 6. Set the response properties
		String fileName = "Total_Shipped_Order_Report.xls";
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
		String fileName = "Total_Shipped_Order_Report.xls";
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
			String[] headers,String reportName ,int sellerId) throws ClassNotFoundException {	
		
		// 1. Create new workbook
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		// 2. Create new worksheet
		HSSFSheet worksheet = workbook.createSheet(reportName);
		
		// 3. Define starting indices for rows and columns
		int startRowIndex = 0;
		int startColIndex = 0;
		
		// 4. Build layout 
		// Build title, date, and column headers
		Layouter.buildOrderReport(worksheet, startRowIndex, startColIndex, reportName, headers);

		// 5. Fill report
		FillManager.fillChannelReport(worksheet, startRowIndex, startColIndex, partnerlist , headers);
		
		// 6. Set the response properties
		String fileName = "Partner_Report.xls";
		switch(reportName){
			case "channelSaleReport": fileName = "Channel_Sale_Report.xls";break;
			case "categoryWiseSaleReport": fileName = "Category_Wise_Sale_Report.xls";break;
			case "orderwiseGPReport": fileName = "Orderwise_GP_Report.xls";break;
			default: break;
		}
		response.setHeader("Content-Disposition", "inline; filename=" + fileName);
		// Make sure to set the correct content type
		response.setContentType("application/vnd.ms-excel");
		
		//7. Write to the output stream
		Writer.write(response, worksheet,fileName);
	}
}