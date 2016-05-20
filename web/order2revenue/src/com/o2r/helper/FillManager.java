package com.o2r.helper;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import com.o2r.bean.ChannelSalesDetails;
import com.o2r.model.Order;

public class FillManager {

	/**
	 * Fills the report with content
	 * 
	 * @param worksheet
	 * @param startRowIndex starting row offset
	 * @param startColIndex starting column offset
	 * @param datasource the data source
	 */
	public static void fillReport(HSSFSheet worksheet, int startRowIndex, int startColIndex, List<Order> datasource , String[] headers) {
		// Row offset
		startRowIndex += 2;
		int startCol=0;
		
		// Create cell style for the body
		HSSFCellStyle bodyCellStyle = worksheet.getWorkbook().createCellStyle();
		bodyCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		bodyCellStyle.setWrapText(true);
				
		System.out.println(" Insidee fill manage : datasource size : "+datasource.size());
		System.out.println(" Start ro index : "+startRowIndex);
		System.out.println(" header length  : "+headers.length);
		System.out.println(" headers  : "+headers);
		// Create body
		for (int i=startRowIndex; i+startRowIndex-2< datasource.size()+2; i++) {
			// Create a new row
			startColIndex=0;
			HSSFRow row = worksheet.createRow((short) i+1);
			// Retrieve the id value
		
			for(int j=0;j<headers.length;j++)
			{
				if(headers[j].equals("SelectAll"))
					startColIndex--;
			
				if(headers[j].equals("orderId"))
				{
					HSSFCell cell = row.createCell(startColIndex+j);
					cell.setCellValue(datasource.get(i-2).getChannelOrderID());
					cell.setCellStyle(bodyCellStyle);
				}
				else if(headers[j].equals("invoiceId"))
				{
					HSSFCell cell = row.createCell(startColIndex+j);
					cell.setCellValue(datasource.get(i-2).getInvoiceID());
					cell.setCellStyle(bodyCellStyle);
				}
				else if(headers[j].equals("Partner"))
				{
					HSSFCell cell = row.createCell(startColIndex+j);
					cell.setCellValue(datasource.get(i-2).getPcName());
					cell.setCellStyle(bodyCellStyle);
				}
				else if(headers[j].equals("recievedDate"))
				{
					HSSFCell cell = row.createCell(startColIndex+j);
					if(datasource.get(i-2).getOrderDate()!=null)
					cell.setCellValue(datasource.get(i-2).getOrderDate().toString());
					cell.setCellStyle(bodyCellStyle);
				}
				else if(headers[j].equals("status"))
				{
					HSSFCell cell = row.createCell(startColIndex+j);
					cell.setCellValue(datasource.get(i-2).getStatus());
					cell.setCellStyle(bodyCellStyle);
				}
				else if(headers[j].equals("shippedDate"))
				{
					HSSFCell cell = row.createCell(startColIndex+j);
					if(datasource.get(i-2).getShippedDate()!=null)
					cell.setCellValue(datasource.get(i-2).getShippedDate().toString());
					cell.setCellStyle(bodyCellStyle);
				}
				else if(headers[j].equals("payCycle"))
				{
					HSSFCell cell = row.createCell(startColIndex+j);
					cell.setCellValue(datasource.get(i-2).getOrderPayment().getPaymentCycle());
					cell.setCellStyle(bodyCellStyle);
				}
				else if(headers[j].equals("finalStatus"))
				{
					HSSFCell cell = row.createCell(startColIndex+j);
					cell.setCellValue(datasource.get(i-2).getFinalStatus());
					cell.setCellStyle(bodyCellStyle);
				}
				else if(headers[j].equals("payDate"))
				{
					HSSFCell cell = row.createCell(startColIndex+j);
					if(datasource.get(i-2).getOrderPayment().getDateofPayment()!=null)
					cell.setCellValue(datasource.get(i-2).getOrderPayment().getDateofPayment());
					else
					cell.setCellValue("");	
					cell.setCellStyle(bodyCellStyle);
				}
				else if(headers[j].equals("netPayment"))
				{
					HSSFCell cell = row.createCell(startColIndex+j);
					cell.setCellValue(datasource.get(i-2).getOrderPayment().getNetPaymentResult());
					cell.setCellStyle(bodyCellStyle);
				}
				else if(headers[j].equals("payDiff"))
				{
					HSSFCell cell = row.createCell(startColIndex+j);
					cell.setCellValue(datasource.get(i-2).getOrderPayment().getPaymentDifference());
					cell.setCellStyle(bodyCellStyle);
				}
				else if(headers[j].equals("netRate"))
				{
					HSSFCell cell = row.createCell(startColIndex+j);
					cell.setCellValue(datasource.get(i-2).getNetRate());
					cell.setCellStyle(bodyCellStyle);
				}
				else if(headers[j].equals("returnCharges"))
				{
					HSSFCell cell = row.createCell(startColIndex+j);
					cell.setCellValue(datasource.get(i-2).getOrderReturnOrRTO().getReturnOrRTOChargestoBeDeducted());
					cell.setCellStyle(bodyCellStyle);
				}	
			}	
		}
	}

	public static void fillCOReport(HSSFSheet worksheet, int startRowIndex, int startColIndex, List<ChannelSalesDetails> datasource , String[] headers) {
		// Row offset
		startRowIndex =2;
		int startCol=0;
		
		// Create cell style for the body
		HSSFCellStyle bodyCellStyle = worksheet.getWorkbook().createCellStyle();
		bodyCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		bodyCellStyle.setWrapText(true);
		// Create body

		for (int j=0; j< datasource.size(); j++) {
			Font font = worksheet.getWorkbook().createFont();
	        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
	        startColIndex=0;
	        // Create cell style for the headers
			HSSFCellStyle headerCellStyle = worksheet.getWorkbook().createCellStyle();
			headerCellStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
			headerCellStyle.setFillPattern(CellStyle.FINE_DOTS);
			headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			headerCellStyle.setWrapText(true);
			headerCellStyle.setFont(font);
			headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
			
			
			// Create the column headers
			HSSFRow rowHeader = worksheet.createRow(j+4);
			//rowHeader.setHeight((short) 500);
			int i=0;
			
			HSSFCell cell = rowHeader.createCell(i++);
			cell.setCellValue(datasource.get(j).getStartDate());

					 cell = rowHeader.createCell(i++);
			cell.setCellValue(datasource.get(j).getEndDate());

			
			 cell = rowHeader.createCell(startColIndex+ i++);
			cell.setCellValue(datasource.get(j).getOrderId());
			
			 cell = rowHeader.createCell(startColIndex+ i++);
			cell.setCellValue(datasource.get(j).getInvoiceID());
/*			
			 cell = rowHeader.createCell(startColIndex+ i++);
			cell.setCellValue("TBD");
			
			 cell = rowHeader.createCell(startColIndex+ i++);
			cell.setCellValue("TBD");
*/			
			 cell = rowHeader.createCell(startColIndex+ i++);
			cell.setCellValue(datasource.get(j).getProductSkuCode());
			
			
			 cell = rowHeader.createCell(startColIndex+ i++);
			cell.setCellValue(datasource.get(j).getNetRate());
			
			 cell = rowHeader.createCell(startColIndex+ i++);
			cell.setCellValue(datasource.get(j).getOrderSP());
			
			 cell = rowHeader.createCell(startColIndex+ i++);
			cell.setCellValue(datasource.get(j).getQuantity());
			
			
			 cell = rowHeader.createCell(startColIndex+ i++);
			cell.setCellValue(datasource.get(j).getTax());
			
			
			 cell = rowHeader.createCell(startColIndex+ i++);
			cell.setCellValue(datasource.get(j).getNetRate());
			
			 cell = rowHeader.createCell(startColIndex+ i++);
			cell.setCellValue(datasource.get(j).getReturnSP());
			
			 cell = rowHeader.createCell(startColIndex+ i++);
			cell.setCellValue(datasource.get(j).getReturnorrtoQty());
			
			 cell = rowHeader.createCell(startColIndex+ i++);
			cell.setCellValue(datasource.get(j).getNrTax());

			 cell = rowHeader.createCell(startColIndex+ i++);
			cell.setCellValue(datasource.get(j).getReturnorrtoQty()*100/datasource.get(j).getQuantity());
			
			
			 cell = rowHeader.createCell(startColIndex+ i++);
			cell.setCellValue(datasource.get(j).getNetRate());
			
			 cell = rowHeader.createCell(startColIndex+ i++);
			cell.setCellValue(datasource.get(j).getOrderSP()-datasource.get(j).getReturnOrRTOCharges());
			
			 cell = rowHeader.createCell(startColIndex+ i++);
			cell.setCellValue(datasource.get(j).getQuantity()-datasource.get(j).getReturnorrtoQty());
			
			 cell = rowHeader.createCell(startColIndex+ i++);
			cell.setCellValue(datasource.get(j).getTax()-datasource.get(j).getNrTax());			
			

			 cell = rowHeader.createCell(startColIndex+ i++);
			cell.setCellValue(datasource.get(j).getTaxCategtory());

			
			double d= datasource.get(j).getOrderSP()-datasource.get(j).getReturnSP();
			d = d -((d*100)/(100+datasource.get(j).getTaxPercent()));
			 cell = rowHeader.createCell(startColIndex+ i++);
			cell.setCellValue(d);			

			 cell = rowHeader.createCell(startColIndex+ i++);
			cell.setCellValue(datasource.get(j).getNetRate()-datasource.get(j).getNrReturn());
			

			 cell = rowHeader.createCell(startColIndex+ i++);
			 cell.setCellValue((datasource.get(j).getOrderSP()-datasource.get(j).getReturnOrRTOCharges())-d);
					
	cell = rowHeader.createCell(startColIndex+ i++);
	cell.setCellValue(datasource.get(j).getQuantity()-datasource.get(j).getReturnorrtoQty());
			
	cell = rowHeader.createCell(startColIndex+ i++);
	cell.setCellValue(datasource.get(j).getPositiveAmount()+datasource.get(j).getNegativeAmount());
			
	cell = rowHeader.createCell(startColIndex+ i++);
	cell.setCellValue(datasource.get(j).getPaymentDifference());
	
			
		}
	}		
	}





