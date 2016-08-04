package com.o2r.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.o2r.bean.OrderPaymentBean;
import com.o2r.model.ChannelUploadMapping;
import com.o2r.model.ColumMap;
import com.o2r.model.ManualCharges;
import com.o2r.model.Order;
import com.o2r.model.PaymentUpload;
import com.o2r.model.UploadReport;
import com.o2r.service.OrderService;
import com.o2r.service.UploadMappingService;

public class SaveMappedFiles {
	
	@Autowired
	private UploadMappingService uploadMappingService;
	@Autowired
	private OrderService orderService;
	
	static Logger log = Logger.getLogger(SaveMappedFiles.class.getName());
	
	public void saveFlipkartPaymentContents(MultipartFile file,
			int sellerId, String path, UploadReport uploadReport)
			throws IOException {
		log.info("$$$ savePaymentContents starts : SaveContents $$$");
		
		ChannelUploadMapping chanupload=null;
		Map<String, String> columHeaderMap = new LinkedHashMap<String, String>();
		Map<String,Integer> cellIndexMap = new LinkedHashMap<String,Integer>();
		Map<String, Double> channelOrderIdCheck=new HashMap<String, Double>();
		OrderPaymentBean orderPaymentbean=null;
		StringBuffer errorMessage = null;
		String channelOrderId=null;
		int noOfEntries = 1;
		HSSFRow entry;
		boolean validaterow = true;
		
		try {
			chanupload=uploadMappingService.getChannelUploadMapping("Flipkart", "Payment");
			if(chanupload!=null&&chanupload.getColumMap()!=null)
			{
				for(ColumMap colums:chanupload.getColumMap())
				{
					columHeaderMap.put(colums.getO2rColumName(),colums.getChannelColumName());
				}
			}
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());

			HSSFSheet worksheet = offices.getSheetAt(0);
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			entry = worksheet.getRow(0);
			for(int cellIndex=0;cellIndex<entry.getPhysicalNumberOfCells();cellIndex++)
			{
				if(columHeaderMap.containsValue(entry.getCell(cellIndex).toString()))
				{
					cellIndexMap.put(entry.getCell(cellIndex).toString(),cellIndex );
				}
				
			}
			for (int rowIndex = 0; rowIndex < noOfEntries; rowIndex++) {
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				boolean repeatOrderid=false;
				int index=0; 
				errorMessage = new StringBuffer("Row :" + (rowIndex) + ":");
				//Code for fetching right colum
				orderPaymentbean=new OrderPaymentBean();
				for (Map.Entry<String, String> entryset : columHeaderMap.entrySet()) {
					switch(entryset.getKey())
					{
					case "Fulfilment Type":
						if(!entry.getCell(cellIndexMap.get(entryset.getValue())).toString().equalsIgnoreCase("NON-FA"))
							continue;
						
						break;
					
					case "ChannelOrderId":
						index=cellIndexMap.get(entryset.getValue());
						if(channelOrderIdCheck.containsKey(entry.getCell(index).toString())){
							/*channelOrderIdCheck.put(entry.getCell(index).toString(),
									channelOrderIdCheck.get(entry.getCell(index).toString())+
									
							orderPaymentbean.setChannelOrderId(entry.getCell(index).toString());*/
							
						}else{
						//	channelOrderIdCheck.put(entry.getCell(index).toString(), orderPaymentbean);
												
						List<Order> onj = orderService
								.findOrders("channelOrderID", entry.getCell(1)
										.toString(), sellerId, false, false);
						if (onj != null && onj.size() != 0&&onj.size()<2) {
							channelOrderId = entry.getCell(1).toString();
						} else {
							channelOrderId = entry.getCell(1).toString();
							errorMessage
									.append(" Channel OrderId not present ");
							validaterow = false;
						}
						}
					
						orderPaymentbean.setChannelOrderId(channelOrderId);
						break;
					case "Recieved Amount":
						
						break;
				
					case "Payment Date":
	
						break;
						
					default:
						continue;
						
					}
				}
			}
		}
				
		catch(Exception e)
		{
			
		}
	}
}

