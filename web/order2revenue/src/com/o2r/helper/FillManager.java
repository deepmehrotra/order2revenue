package com.o2r.helper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.beans.factory.annotation.Autowired;

import com.o2r.bean.ChannelNR;
import com.o2r.bean.ChannelReportDetails;
import com.o2r.bean.ChannelSalesDetails;
import com.o2r.bean.PartnerReportDetails;
import com.o2r.bean.YearlyStockList;
import com.o2r.model.Expenses;
import com.o2r.model.Order;
import com.o2r.model.Product;
import com.o2r.service.ProductService;

public class FillManager {

	/**
	 * Fills the report with content
	 * 
	 * @param worksheet
	 * @param startRowIndex starting row offset
	 * @param startColIndex starting column offset
	 * @param datasource the data source
	 */
	
		
	public static void fillReport(HSSFSheet worksheet, int startRowIndex, int startColIndex,String status, List<Order> datasource , String[] headers, ProductService productService, int sellerId) {
		// Row offset
		startRowIndex += 2;
		int startCol=0;
		// Create cell style for the body
		HSSFCellStyle bodyCellStyle = worksheet.getWorkbook().createCellStyle();
		bodyCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		bodyCellStyle.setWrapText(true);
				
		/*System.out.println(" Insidee fill manage : datasource size : "+datasource);
		System.out.println(" Start ro index : "+startRowIndex);
		System.out.println(" header length  : "+headers.length);
		System.out.println(" headers  : "+headers);*/
		int rowIndex=startRowIndex-1;
		// Create body
		try{
			if(datasource!=null&&datasource.size()!=0)
			{
		for (int i=startRowIndex; i+startRowIndex-2< datasource.size()+2; i++) {
			
			rowIndex++;
			if(datasource.get(i-2).getStatus().equalsIgnoreCase(status) || datasource.get(i-2).getFinalStatus().equalsIgnoreCase(status) || status == ""){
				
				// Create a new row
				startColIndex=0;
				HSSFRow row = worksheet.createRow((short) rowIndex+1);
				// Retrieve the id value
				double commissionCharge=(datasource.get(i-2).getPccAmount()+datasource.get(i-2).getShippingCharges()+datasource.get(i-2).getFixedfee()+datasource.get(i-2).getPartnerCommission());
				Product product=productService.getProduct(datasource.get(i-2).getProductSkuCode(),sellerId);
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
					else if(headers[j].equals("logicalPartner"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						cell.setCellValue(datasource.get(i-2).getLogisticPartner());
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
							cell.setCellValue(datasource.get(i-2).getOrderPayment().getDateofPayment().toString());						
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
					else if(headers[j].equals("PIreferenceNo"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						cell.setCellValue(datasource.get(i-2).getPIreferenceNo());
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("productCategory"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						if(product != null)
							cell.setCellValue(product.getCategoryName());
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("totalMRP"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						cell.setCellValue(datasource.get(i-2).getOrderMRP());
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("grossNR"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						cell.setCellValue((datasource.get(i-2).getGrossNetRate())*(datasource.get(i-2).getQuantity()));
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("netNR"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						if(datasource.get(i-2).getOrderReturnOrRTO() != null)
							cell.setCellValue((datasource.get(i-2).getGrossNetRate())*((datasource.get(i-2).getQuantity())-datasource.get(i-2).getOrderReturnOrRTO().getReturnorrtoQty()));
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("grossPR"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						cell.setCellValue(datasource.get(i-2).getPr());
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("returnPR"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						if(datasource.get(i-2).getOrderReturnOrRTO() != null)
							cell.setCellValue((datasource.get(i-2).getPr()/datasource.get(i-2).getQuantity())*datasource.get(i-2).getOrderReturnOrRTO().getReturnorrtoQty());
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("netPR"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						if(datasource.get(i-2).getOrderReturnOrRTO() != null)
							cell.setCellValue((datasource.get(i-2).getPr())-((datasource.get(i-2).getPr()/datasource.get(i-2).getQuantity())*datasource.get(i-2).getOrderReturnOrRTO().getReturnorrtoQty()));
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("netPCC"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						if(datasource.get(i-2).getOrderReturnOrRTO() != null)
							cell.setCellValue(datasource.get(i-2).getPccAmount()*(datasource.get(i-2).getQuantity()-datasource.get(i-2).getOrderReturnOrRTO().getReturnorrtoQty()));
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("netFixedFee"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						if(datasource.get(i-2).getOrderReturnOrRTO() != null)
							cell.setCellValue(datasource.get(i-2).getFixedfee()*(datasource.get(i-2).getQuantity()-datasource.get(i-2).getOrderReturnOrRTO().getReturnorrtoQty()));
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("grossTDS"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						cell.setCellValue(datasource.get(i-2).getOrderTax().getTdsToDeduct());
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("returnTDS"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						cell.setCellValue(datasource.get(i-2).getOrderTax().getTdsToReturn());
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("netTDS"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						cell.setCellValue(datasource.get(i-2).getOrderTax().getTdsToDeduct()-datasource.get(i-2).getOrderTax().getTdsToReturn());
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("customerCity"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						if(datasource.get(i-2).getCustomer() != null)
							cell.setCellValue(datasource.get(i-2).getCustomer().getCustomerCity());
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("returnId"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						if(datasource.get(i-2).getOrderReturnOrRTO() != null)
							cell.setCellValue(datasource.get(i-2).getOrderReturnOrRTO().getReturnId());
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("deliveryDate"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						if(datasource.get(i-2).getDeliveryDate() != null)
							cell.setCellValue(datasource.get(i-2).getDeliveryDate().toString());
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("netSaleQuantity"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						if(datasource.get(i-2).getOrderReturnOrRTO() != null)
							cell.setCellValue(datasource.get(i-2).getQuantity()-datasource.get(i-2).getOrderReturnOrRTO().getReturnorrtoQty());
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("quantity"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						cell.setCellValue(datasource.get(i-2).getQuantity());
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("returnQuantity"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						if(datasource.get(i-2).getOrderReturnOrRTO() != null)
							cell.setCellValue(datasource.get(i-2).getOrderReturnOrRTO().getReturnorrtoQty());
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("grossSp"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						cell.setCellValue(datasource.get(i-2).getOrderSP());
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("returnSp"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						if(datasource.get(i-2).getOrderReturnOrRTO() != null)
							cell.setCellValue((datasource.get(i-2).getOrderSP()/datasource.get(i-2).getQuantity())*datasource.get(i-2).getOrderReturnOrRTO().getReturnorrtoQty());
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("netSp"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						if(datasource.get(i-2).getOrderReturnOrRTO() != null)
							cell.setCellValue(datasource.get(i-2).getOrderSP()-((datasource.get(i-2).getOrderSP()/datasource.get(i-2).getQuantity())*datasource.get(i-2).getOrderReturnOrRTO().getReturnorrtoQty()));
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("subOrderId"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						cell.setCellValue(datasource.get(i-2).getSubOrderID());
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("sku"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						cell.setCellValue(datasource.get(i-2).getProductSkuCode());
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("returnDate"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						if(datasource.get(i-2).getOrderReturnOrRTO() != null && datasource.get(i-2).getOrderReturnOrRTO().getReturnDate() != null)
							cell.setCellValue(datasource.get(i-2).getOrderReturnOrRTO().getReturnDate().toString());
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("taxCategory"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						cell.setCellValue(datasource.get(i-2).getOrderTax().getTaxCategtory());
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("serviceTax"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						cell.setCellValue(datasource.get(i-2).getServiceTax());
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("grossCostProduct"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						if(product != null)
							cell.setCellValue(product.getProductPrice()*datasource.get(i-2).getQuantity());
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("returnCostProduct"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						if(product != null && datasource.get(i-2).getOrderReturnOrRTO() != null)
							cell.setCellValue(product.getProductPrice()*datasource.get(i-2).getOrderReturnOrRTO().getReturnorrtoQty());
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("netCostProduct"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						if(product != null && datasource.get(i-2).getOrderReturnOrRTO() != null)
							cell.setCellValue((product.getProductPrice()*datasource.get(i-2).getQuantity())-(product.getProductPrice()*datasource.get(i-2).getOrderReturnOrRTO().getReturnorrtoQty()));
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("grossProfit"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						cell.setCellValue(datasource.get(i-2).getGrossProfit());
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("awbNo"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						cell.setCellValue(datasource.get(i-2).getAwbNum());
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("paymentType"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						cell.setCellValue(datasource.get(i-2).getPaymentType());
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("dateofPayment"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						if(datasource.get(i-2).getOrderPayment().getDateofPayment() != null)
							cell.setCellValue(datasource.get(i-2).getOrderPayment().getDateofPayment().toString());
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("grossTax"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						cell.setCellValue(datasource.get(i-2).getOrderTax().getTax());
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("returnTax"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						cell.setCellValue(datasource.get(i-2).getOrderTax().getTaxToReturn());
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("grossCommission"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						cell.setCellValue((commissionCharge*datasource.get(i-2).getQuantity())+((commissionCharge*datasource.get(i-2).getQuantity())*.145));
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("returnCommission"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						if(datasource.get(i-2).getOrderReturnOrRTO() != null)
							cell.setCellValue((commissionCharge*datasource.get(i-2).getOrderReturnOrRTO().getReturnorrtoQty())+((commissionCharge*(datasource.get(i-2).getQuantity()-datasource.get(i-2).getOrderReturnOrRTO().getReturnorrtoQty()))*.145));
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("netCommission"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						if(datasource.get(i-2).getOrderReturnOrRTO() != null)
							cell.setCellValue(((commissionCharge*datasource.get(i-2).getQuantity())+((commissionCharge*datasource.get(i-2).getQuantity())*.145))-((commissionCharge*datasource.get(i-2).getOrderReturnOrRTO().getReturnorrtoQty())+((commissionCharge*(datasource.get(i-2).getQuantity()-datasource.get(i-2).getOrderReturnOrRTO().getReturnorrtoQty()))*.145)));
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("netTax"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						cell.setCellValue(datasource.get(i-2).getOrderTax().getNetTax());
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("returnType"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						if(datasource.get(i-2).getOrderReturnOrRTO() != null)
							cell.setCellValue(datasource.get(i-2).getOrderReturnOrRTO().getType());
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("faultType"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						if(datasource.get(i-2).getOrderReturnOrRTO() != null)
							cell.setCellValue(datasource.get(i-2).getOrderReturnOrRTO().getReturnCategory());
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("returnStage"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						if(datasource.get(i-2).getOrderReturnOrRTO() != null)
							cell.setCellValue(datasource.get(i-2).getOrderReturnOrRTO().getCancelType());
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("shippingZone"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						if(datasource.get(i-2).getCustomer() != null)
							cell.setCellValue(datasource.get(i-2).getCustomer().getZipcode());
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("returnReason"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						if(datasource.get(i-2).getOrderReturnOrRTO() != null)
							cell.setCellValue(datasource.get(i-2).getOrderReturnOrRTO().getReturnOrRTOreason());
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("customerName"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						if(datasource.get(i-2).getCustomer() != null)
							cell.setCellValue(datasource.get(i-2).getCustomer().getCustomerName());
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("eossDiscount"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						cell.setCellValue(datasource.get(i-2).getDiscount());
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("totalReturnEossDiscount"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						if(datasource.get(i-2).getOrderReturnOrRTO() != null)
							cell.setCellValue((datasource.get(i-2).getDiscount()/datasource.get(i-2).getQuantity())*datasource.get(i-2).getOrderReturnOrRTO().getReturnorrtoQty());
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("netReturnEossDiscount"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						if(datasource.get(i-2).getOrderReturnOrRTO() != null)
							cell.setCellValue((datasource.get(i-2).getDiscount())-((datasource.get(i-2).getDiscount()/datasource.get(i-2).getQuantity())*datasource.get(i-2).getOrderReturnOrRTO().getReturnorrtoQty()));
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("netSellingFee"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						if(datasource.get(i-2).getOrderReturnOrRTO() != null)
							cell.setCellValue(datasource.get(i-2).getPartnerCommission()*(datasource.get(i-2).getQuantity()-datasource.get(i-2).getOrderReturnOrRTO().getReturnorrtoQty()));
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("addReturnCharges"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						if(datasource.get(i-2).getOrderReturnOrRTO() != null)
							cell.setCellValue(datasource.get(i-2).getOrderReturnOrRTO().getReturnOrRTOChargestoBeDeducted());
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("returnChargesReversed"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						if(datasource.get(i-2).getOrderReturnOrRTO() != null)
							cell.setCellValue(datasource.get(i-2).getGrossNetRate()*datasource.get(i-2).getOrderReturnOrRTO().getReturnorrtoQty());
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("totalReturnCharges"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						if(datasource.get(i-2).getOrderReturnOrRTO() != null)
							cell.setCellValue((datasource.get(i-2).getOrderReturnOrRTO().getReturnOrRTOChargestoBeDeducted())-(datasource.get(i-2).getGrossNetRate()*datasource.get(i-2).getOrderReturnOrRTO().getReturnorrtoQty()));
						cell.setCellStyle(bodyCellStyle);
					}
					else if(headers[j].equals("netShippingCharges"))
					{
						HSSFCell cell = row.createCell(startColIndex+j);
						if(datasource.get(i-2).getOrderReturnOrRTO() != null)
							cell.setCellValue(datasource.get(i-2).getShippingCharges()*(datasource.get(i-2).getQuantity()-datasource.get(i-2).getOrderReturnOrRTO().getReturnorrtoQty()));
						cell.setCellStyle(bodyCellStyle);
					}
					
				}
				
			}else{
				rowIndex-=1;
			}
		}
			}
		}catch(Exception e){
			e.printStackTrace();
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

	public static void fillPartnerReport(HSSFSheet worksheet, int startRowIndex,
			int startColIndex, List<PartnerReportDetails> partnerList,
			String[] headers) {
		// Row offset
		startRowIndex += 2;
		int startCol=0;
		
		// Create cell style for the body
		HSSFCellStyle bodyCellStyle = worksheet.getWorkbook().createCellStyle();
		bodyCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		bodyCellStyle.setWrapText(true);
				
		System.out.println(" Insidee fill manage : PartnerList size : "+partnerList.size());
		System.out.println(" Start ro index : "+startRowIndex);
		System.out.println(" header length  : "+headers.length);
		System.out.println(" headers  : "+headers);
		// Create body
		for (PartnerReportDetails partner: partnerList) {
			// Create a new row
			startColIndex=0;
			HSSFRow row = worksheet.createRow((short) ++startRowIndex);
			// Retrieve the id value
		
			for(int j=0;j<headers.length;j++) {
				if(headers[j].equals("SelectAll"))
					startColIndex--;
				
				Class partnerClass = partner.getClass();
				Method method;
				try {
					method = partnerClass.getDeclaredMethod(headers[j]);					
					HSSFCell cell = row.createCell(startColIndex+j);
					Object retObj = method.invoke(partner, null);
					if(retObj!=null){
						cell.setCellValue(retObj.toString());
					}
					cell.setCellStyle(bodyCellStyle);
				} catch (NoSuchMethodException e) {
					e.getMessage();
				} catch (SecurityException e) {
					e.getMessage();
				} catch (IllegalAccessException e) {
					e.getMessage();
				} catch (IllegalArgumentException e) {
					e.getMessage();
				} catch (InvocationTargetException e) {
					e.getMessage();
				}
			}
		}		
	}	
	
	public static void fillChannelReport(HSSFSheet worksheet, int startRowIndex,
			int startColIndex, List<ChannelReportDetails> channelReportList,
			String[] headers) {
		// Row offset
		startRowIndex += 2;
		int startCol=0;
		
		// Create cell style for the body
		HSSFCellStyle bodyCellStyle = worksheet.getWorkbook().createCellStyle();
		bodyCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		bodyCellStyle.setWrapText(true);
				
		System.out.println(" Insidee fill manage : Channel Report size : "+channelReportList.size());
		System.out.println(" Start ro index : "+startRowIndex);
		System.out.println(" header length  : "+headers.length);
		System.out.println(" headers  : "+headers);
		// Create body
		for (ChannelReportDetails channelReport: channelReportList) {
			// Create a new row
			startColIndex=0;
			HSSFRow row = worksheet.createRow((short) ++startRowIndex);
			// Retrieve the id value
		
			for(int j=0;j<headers.length;j++) {
				if(headers[j].equals("SelectAll"))
					startColIndex--;
				
				Class channelReportClass = channelReport.getClass();
				Method method;
				try {
					method = channelReportClass.getDeclaredMethod(headers[j]);					
					HSSFCell cell = row.createCell(startColIndex+j);
					Object retObj = method.invoke(channelReport, null);
					if(retObj!=null){
						cell.setCellValue(retObj.toString());
					}
					cell.setCellStyle(bodyCellStyle);
				} catch (NoSuchMethodException e) {
					e.getMessage();
				} catch (SecurityException e) {
					e.getMessage();
				} catch (IllegalAccessException e) {
					e.getMessage();
				} catch (IllegalArgumentException e) {
					e.getMessage();
				} catch (InvocationTargetException e) {
					e.getMessage();
				}
			}
		}		
	}
	
	public static void fillStockReport(HSSFSheet worksheet, int startRowIndex,
			int startColIndex, List<YearlyStockList> channelReportList,
			String[] headers) {
		// Row offset
		startRowIndex += 2;
		int startCol=0;
		
		// Create cell style for the body
		HSSFCellStyle bodyCellStyle = worksheet.getWorkbook().createCellStyle();
		bodyCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		bodyCellStyle.setWrapText(true);
				
		System.out.println(" Insidee fill manage : Channel Report size : "+channelReportList.size());
		System.out.println(" Start ro index : "+startRowIndex);
		System.out.println(" header length  : "+headers.length);
		System.out.println(" headers  : "+headers);
		// Create body
		for (YearlyStockList channelReport: channelReportList) {
			// Create a new row
			startColIndex=0;
			HSSFRow row = worksheet.createRow((short) ++startRowIndex);
			// Retrieve the id value
		
			for(int j=0;j<headers.length;j++) {
				if(headers[j].equals("SelectAll"))
					startColIndex--;
				
				Class channelReportClass = channelReport.getClass();
				Method method;
				try {
					method = channelReportClass.getDeclaredMethod(headers[j]);					
					HSSFCell cell = row.createCell(startColIndex+j);
					Object retObj = method.invoke(channelReport, null);
					if(retObj!=null){
						cell.setCellValue(retObj.toString());
					}
					cell.setCellStyle(bodyCellStyle);
				} catch (NoSuchMethodException e) {
					e.getMessage();
				} catch (SecurityException e) {
					e.getMessage();
				} catch (IllegalAccessException e) {
					e.getMessage();
				} catch (IllegalArgumentException e) {
					e.getMessage();
				} catch (InvocationTargetException e) {
					e.getMessage();
				}
			}
		}		
	}

	public static void fillExpenseReport(HSSFSheet worksheet,
			int startRowIndex, int startColIndex, List<Expenses> expensesList,
			String[] headers) {
		// Row offset
		startRowIndex += 2;
		int startCol=0;
		
		// Create cell style for the body
		HSSFCellStyle bodyCellStyle = worksheet.getWorkbook().createCellStyle();
		bodyCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		bodyCellStyle.setWrapText(true);
		// Create body
		for (Expenses report: expensesList) {
			// Create a new row
			startColIndex=0;
			HSSFRow row = worksheet.createRow((short) ++startRowIndex);
			// Retrieve the id value
		
			for(int j=0;j<headers.length;j++) {
				if(headers[j].equals("SelectAll"))
					startColIndex--;
				
				Class channelReportClass = report.getClass();
				Method method;
				try {
					method = channelReportClass.getDeclaredMethod(headers[j]);					
					HSSFCell cell = row.createCell(startColIndex+j);
					Object retObj = method.invoke(report, null);
					if(retObj!=null){
						cell.setCellValue(retObj.toString());
					}
					cell.setCellStyle(bodyCellStyle);
				} catch (NoSuchMethodException e) {
					e.getMessage();
				} catch (SecurityException e) {
					e.getMessage();
				} catch (IllegalAccessException e) {
					e.getMessage();
				} catch (IllegalArgumentException e) {
					e.getMessage();
				} catch (InvocationTargetException e) {
					e.getMessage();
				}
			}
		}		
	}

	public static void fillNetRateReport(HSSFSheet worksheet,
			int startRowIndex, int startColIndex, List<ChannelNR> nrList,
			String[] headers) {
		// Row offset
		startRowIndex += 2;
		int startCol=0;
		
		// Create cell style for the body
		HSSFCellStyle bodyCellStyle = worksheet.getWorkbook().createCellStyle();
		bodyCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		bodyCellStyle.setWrapText(true);
		// Create body
		for (ChannelNR report: nrList) {
			// Create a new row
			startColIndex=0;
			HSSFRow row = worksheet.createRow((short) ++startRowIndex);
			// Retrieve the id value
		
			for(int j=0;j<headers.length;j++) {
				if(headers[j].equals("SelectAll"))
					startColIndex--;
				
				Class channelReportClass = report.getClass();
				Method method;
				try {
					method = channelReportClass.getDeclaredMethod(headers[j]);					
					HSSFCell cell = row.createCell(startColIndex+j);
					Object retObj = method.invoke(report, null);
					if(retObj!=null){
						cell.setCellValue(retObj.toString());
					}
					cell.setCellStyle(bodyCellStyle);
				} catch (NoSuchMethodException e) {
					e.getMessage();
				} catch (SecurityException e) {
					e.getMessage();
				} catch (IllegalAccessException e) {
					e.getMessage();
				} catch (IllegalArgumentException e) {
					e.getMessage();
				} catch (InvocationTargetException e) {
					e.getMessage();
				}
			}
		}		
	}	
}





