package com.o2r.helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.o2r.bean.DataConfig;
import com.o2r.bean.OrderPaymentBean;
import com.o2r.model.ChannelUploadMapping;
import com.o2r.model.ColumMap;
import com.o2r.model.Order;
import com.o2r.model.OrderPayment;
import com.o2r.model.PaymentUpload;
import com.o2r.model.UploadReport;
import com.o2r.service.OrderService;
import com.o2r.service.PaymentUploadService;
import com.o2r.service.ReportGeneratorService;
import com.o2r.service.SellerService;
import com.o2r.service.UploadMappingService;


@Service("saveMappedFiles")
@Transactional
public class SaveMappedFiles {
	
	@Autowired
	private UploadMappingService uploadMappingService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private PaymentUploadService paymentUploadService;
	@Autowired
	private DataConfig dataConfig;
	@Autowired
	private SellerService sellerService;
	@Autowired
	private ReportGeneratorService reportGeneratorService;
	private static final String UPLOAD_DIR = "UploadReport";
	
	static Logger log = Logger.getLogger(SaveMappedFiles.class.getName());
	
	public void saveFlipkartPaymentContents(MultipartFile file,
			int sellerId, String path, UploadReport uploadReport)
			throws IOException {
		log.info("$$$ saveFlipkartPaymentContents starts : SaveMappedFiles $$$");
		
		ChannelUploadMapping chanupload=null;
		Map<String, String> columHeaderMap = new LinkedHashMap<String, String>();
		Map<String,Integer> cellIndexMap = new LinkedHashMap<String,Integer>();
		OrderPayment orderPayment=null;
		StringBuffer errorMessage = null;
		String channelOrderId=null;
		String skucode=null;
		Order order=null;
		int noOfEntries = 1;
		HSSFRow entry;
		boolean validaterow = true;
		int indexfulfilmentType=0;
		double totalpositive=0;
		double totalnegative=0;
		Set<String>  errorSet=new HashSet<String>();
		PaymentUpload paymentUpload = new PaymentUpload();
		boolean generatePaymentUpload = false;
		
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
			indexfulfilmentType=cellIndexMap.get(columHeaderMap.get("Fulfilment Type"));
			for (int rowIndex = 0; rowIndex < noOfEntries; rowIndex++) {
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				int index=0; 
				String channelheader=null;
				channelOrderId=null;
				errorMessage = new StringBuffer("Row :" + (rowIndex) + ":");
				//Code for fetching right colum
				orderPayment=new OrderPayment();
				
					try
					{
					
					if(entry.getCell(indexfulfilmentType)!=null&&
							entry.getCell(indexfulfilmentType).toString().equalsIgnoreCase("NON-FA"))
					{
			
					channelheader=columHeaderMap.get("ChannelOrderId");
					index=cellIndexMap.get(channelheader);
					List<Order> onj = orderService
							.findOrders("channelOrderID", entry.getCell(index)
									.toString(), sellerId, false, false);
					if (onj != null && onj.size() != 0) {
						channelOrderId = entry.getCell(index).toString();
							} else {
						channelOrderId = entry.getCell(index).toString();
						errorMessage
								.append(" Channel OrderId not present ");
						validaterow = false;
					}
					
					index=cellIndexMap.get(columHeaderMap.get("Payment Date"));
					if(entry.getCell(index) != null
							&& HSSFDateUtil.isCellDateFormatted(entry.getCell(index)))
							{
						orderPayment.setDateofPayment(entry.getCell(index)
								.getDateCellValue());
							}
					else
					{
						errorMessage
						.append(" Payment Date format is wrong or null");
						validaterow = false;
					}
					
					index=cellIndexMap.get(columHeaderMap.get("Recieved Amount"));
					if(entry.getCell(index) != null
							&& StringUtils.isNotBlank(entry.getCell(index)
									.toString()))
							{
						try
						{
						double amount=entry.getCell(index).getNumericCellValue();
						if(amount>0)
						{
							orderPayment.setPositiveAmount(amount);
							totalpositive = totalpositive+amount;
						}
						else if (amount<0)
						{
							orderPayment.setNegativeAmount(Math.abs(amount));
							totalnegative=totalnegative+Math.abs(amount);
						}
						}catch(Exception e)
						{
							log.error("Failed by seller "+sellerId,e);
							errorMessage
							.append(" Payment Amount cell is corrupted");
							validaterow = false;
							
						}
							}
					else
					{
						errorMessage
						.append("Amount format is wrong or null.");
						validaterow = false;
					}
					index=cellIndexMap.get(columHeaderMap.get("Seller SKU"));
					if(entry.getCell(index) != null
							&& StringUtils.isNotBlank(entry.getCell(index)
									.toString()))
							{
						skucode=entry.getCell(index).toString();
							}
					
					
					}
					else
					{
						errorMessage
						.append("Fulfilment Type is not accepted.Only NON-FA is valid.");
						validaterow = false;
					}
					if (validaterow) {
						order =orderService.addOrderPayment(skucode, channelOrderId,
								orderPayment, sellerId);
					} else {
						errorSet.add(errorMessage.toString());
						
					}
					if (order != null) {
						
						order.setPaymentUpload(paymentUpload);
						paymentUpload.getOrders().add(order);
						generatePaymentUpload = true;
					}	
					
				
			}catch(Exception e)
					{
				log.error("Failed by seller: "+sellerId, e);
				errorMessage.append("Invalid Input !");
				errorSet.add(errorMessage.toString());
					}
		}
			if (generatePaymentUpload) {
				log.info("Updating payment Upload with positive amount " + totalpositive);
				log.info("Updating payment Upload with negative amount " + totalnegative);
				paymentUpload.setTotalpositivevalue(totalpositive);
				paymentUpload.setTotalnegativevalue(totalnegative);
				paymentUpload.setNetRecievedAmount(totalpositive
						- totalnegative);
				paymentUpload.setUploadDesc("PAYU" + sellerId + ""
						+ new Date().getTime());
				paymentUpload.setUploadStatus("Success");
				paymentUploadService.addPaymentUpload(paymentUpload, sellerId);				
			}
			
			uploadResultXLS(offices, "Flipkart_Payment", errorSet,
					path, sellerId, uploadReport);
		}
				
		catch(Exception e)
		{
			log.error("Failed by seller: "+sellerId, e);
		}
	}
	
	public void savePayTMPaymentContents(MultipartFile file, int sellerId,
			String path, UploadReport uploadReport) throws IOException {
		log.info("$$$ saveFlipkartPaymentContents starts : SaveMappedFiles $$$");

		ChannelUploadMapping chanupload = null;
		Map<String, String> columHeaderMap = new LinkedHashMap<String, String>();
		Map<String, Integer> cellIndexMap = new LinkedHashMap<String, Integer>();
		OrderPayment orderPayment = null;
		StringBuffer errorMessage = null;
		String channelOrderId = null;
		String skucode = null;
		Order order = null;
		int noOfEntries = 1;
		HSSFRow entry;
		boolean validaterow = true;
		int indexfulfilmentType = 0;
		double totalpositive = 0;
		double totalnegative = 0;
		Set<String> errorSet = new HashSet<String>();
		PaymentUpload paymentUpload = new PaymentUpload();
		boolean generatePaymentUpload = false;

		try {
			chanupload = uploadMappingService.getChannelUploadMapping("PayTM",
					"Payment");
			if (chanupload != null && chanupload.getColumMap() != null) {
				for (ColumMap colums : chanupload.getColumMap()) {
					columHeaderMap.put(colums.getO2rColumName(),
							colums.getChannelColumName());
				}
			}
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());

			HSSFSheet worksheet = offices.getSheetAt(0);
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			entry = worksheet.getRow(0);
			for (int cellIndex = 0; cellIndex < entry
					.getPhysicalNumberOfCells(); cellIndex++) {
				if (columHeaderMap.containsValue(entry.getCell(cellIndex)
						.toString())) {
					cellIndexMap.put(entry.getCell(cellIndex).toString(),
							cellIndex);
				}

			}
			// indexfulfilmentType=cellIndexMap.get(columHeaderMap.get("Fulfilment Type"));
			for (int rowIndex = 0; rowIndex < noOfEntries; rowIndex++) {
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				int index = 0;
				String channelheader = null;
				channelOrderId = null;
				errorMessage = new StringBuffer("Row :" + (rowIndex) + ":");
				// Code for fetching right colum
				orderPayment = new OrderPayment();

				try {

					if (entry.getCell(indexfulfilmentType) != null
							&& entry.getCell(indexfulfilmentType).toString()
									.equalsIgnoreCase("NON-FA")) {

						channelheader = columHeaderMap.get("ChannelOrderId");
						index = cellIndexMap.get(channelheader);
						List<Order> onj = orderService.findOrders(
								"channelOrderID", entry.getCell(index)
										.toString(), sellerId, false, false);
						if (onj != null && onj.size() != 0) {
							channelOrderId = entry.getCell(index).toString();
						} else {
							channelOrderId = entry.getCell(index).toString();
							errorMessage
									.append(" Channel OrderId not present ");
							validaterow = false;
						}

						index = cellIndexMap.get(columHeaderMap.get("Payment Date"));
						if (entry.getCell(index) != null
								&& HSSFDateUtil.isCellDateFormatted(entry
										.getCell(index))) {
							orderPayment.setDateofPayment(entry.getCell(index)
									.getDateCellValue());
						} else {
							errorMessage
									.append(" Payment Date format is wrong or null");
							validaterow = false;
						}

						index = cellIndexMap.get(columHeaderMap.get("Recieved Amount"));
						if (entry.getCell(index) != null
								&& StringUtils.isNotBlank(entry.getCell(index)
										.toString())) {
							try {
								double amount = entry.getCell(index)
										.getNumericCellValue();
								if (amount > 0) {
									orderPayment.setPositiveAmount(amount);
									totalpositive = totalpositive + amount;
								} else if (amount < 0) {
									orderPayment.setNegativeAmount(Math
											.abs(amount));
									totalnegative = totalnegative
											+ Math.abs(amount);
								}
							} catch (Exception e) {
								log.error("Failed by seller " + sellerId, e);
								errorMessage
										.append(" Payment Amount cell is corrupted");
								validaterow = false;

							}
						} else {
							errorMessage
									.append("Amount format is wrong or null.");
							validaterow = false;
						}
						
					} else {
						errorMessage
								.append("Fulfilment Type is not accepted.Only NON-FA is valid.");
						validaterow = false;
					}
					if (validaterow) {
						order = orderService.addOrderPayment(skucode,
								channelOrderId, orderPayment, sellerId);
					} else {
						errorSet.add(errorMessage.toString());

					}
					if (order != null) {
						order.setPaymentUpload(paymentUpload);
						paymentUpload.getOrders().add(order);
						generatePaymentUpload = true;
					}

				} catch (Exception e) {
					log.error("Failed by seller: " + sellerId, e);
					errorMessage.append("Invalid Input !");
					errorSet.add(errorMessage.toString());
				}
			}
			if (generatePaymentUpload) {
				log.info("Updating payment Upload with positive amount "
						+ totalpositive);
				log.info("Updating payment Upload with negative amount "
						+ totalnegative);
				paymentUpload.setTotalpositivevalue(totalpositive);
				paymentUpload.setTotalnegativevalue(totalnegative);
				paymentUpload.setNetRecievedAmount(totalpositive
						- totalnegative);
				paymentUpload.setUploadDesc("PAYU" + sellerId + ""
						+ new Date().getTime());
				paymentUpload.setUploadStatus("Success");
				paymentUploadService.addPaymentUpload(paymentUpload, sellerId);
			}

			uploadResultXLS(offices, "PayTM_Payment", errorSet, path,
					sellerId, uploadReport);
		}

		catch (Exception e) {
			log.error("Failed by seller: " + sellerId, e);
		}
	}
	
	
	public void saveAmazonPaymentContents(MultipartFile file, int sellerId,
			String path, UploadReport uploadReport) throws IOException {
		log.info("$$$ saveFlipkartPaymentContents starts : SaveMappedFiles $$$");

		ChannelUploadMapping chanupload = null;
		Map<String, String> columHeaderMap = new LinkedHashMap<String, String>();
		Map<String, Integer> cellIndexMap = new LinkedHashMap<String, Integer>();		
		StringBuffer errorMessage = null;
		String channelOrderId = null;
		String skucode = null;
		Order order = null;
		int noOfEntries = 1;
		HSSFRow entry;
		boolean validaterow = true;
		int indexfulfilmentType = 0;
		double totalpositive = 0;
		double totalnegative = 0;
		Set<String> errorSet = new HashSet<String>();
		PaymentUpload paymentUpload = new PaymentUpload();
		boolean generatePaymentUpload = false;
		Map<String , OrderPaymentBean> paymentMap= new HashMap<String, OrderPaymentBean>();
		OrderPaymentBean paymentBean=null;

		try {
			chanupload = uploadMappingService.getChannelUploadMapping("Amazon",
					"Payment");
			if (chanupload != null && chanupload.getColumMap() != null) {
				for (ColumMap colums : chanupload.getColumMap()) {
					columHeaderMap.put(colums.getO2rColumName(),
							colums.getChannelColumName());
				}
			}
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());

			HSSFSheet worksheet = offices.getSheetAt(0);
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			entry = worksheet.getRow(0);
			for (int cellIndex = 0; cellIndex < entry
					.getPhysicalNumberOfCells(); cellIndex++) {
				if (columHeaderMap.containsValue(entry.getCell(cellIndex)
						.toString())) {
					cellIndexMap.put(entry.getCell(cellIndex).toString(),
							cellIndex);
				}

			}
			// indexfulfilmentType=cellIndexMap.get(columHeaderMap.get("Fulfilment Type"));
			for (int rowIndex = 0; rowIndex < noOfEntries; rowIndex++) {
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				int index = 0;
				String channelheader = null;
				channelOrderId = null;
				errorMessage = new StringBuffer("Row :" + (rowIndex) + ":");
				// Code for fetching right colum		
				
				try {

					channelheader = columHeaderMap.get("ChannelOrderId");
					index = cellIndexMap.get(channelheader);
					List<Order> onj = orderService.findOrders(
							"channelOrderID subOrderID", entry.getCell(index)
									.toString(), sellerId, false, false);
					if (onj != null && onj.size() != 0) {
						if(onj.size() == 1){
							if(paymentMap.containsKey(entry.getCell(index).toString())){
								paymentBean=paymentMap.get(entry.getCell(index).toString());									
							}else{
								paymentBean=new OrderPaymentBean();
							}
							channelOrderId = entry.getCell(index).toString();
						}else{
							errorMessage.append(" Multiple Order on this ID ");
							validaterow = false;
						}										
						
					} else {
						channelOrderId = entry.getCell(index).toString();
						errorMessage.append(" Channel OrderId not present ");
						validaterow = false;
					}
					index = cellIndexMap.get(columHeaderMap.get("Payment Date"));
					if (entry.getCell(index) != null
							&& HSSFDateUtil.isCellDateFormatted(entry
									.getCell(index))) {
						paymentBean.setDateofPayment(entry.getCell(index).getDateCellValue());
					} else {
						errorMessage
								.append(" Payment Date format is wrong or null");
						validaterow = false;
					}

					index = cellIndexMap.get(columHeaderMap.get("Recieved Amount"));
					if (entry.getCell(index) != null
							&& StringUtils.isNotBlank(entry.getCell(index).toString())) {
						try {
							double amount =entry.getCell(index).getNumericCellValue();								
							if (amount > 0) {
								paymentBean.setPositiveAmount(paymentBean.getPositiveAmount()+amount);
								totalpositive = totalpositive + amount;
							} else if (amount < 0) {
								paymentBean.setNegativeAmount(Math.abs(paymentBean.getNegativeAmount()+amount));
								totalnegative = totalnegative+ Math.abs(amount);
							}
						} catch (Exception e) {
							log.error("Failed by seller " + sellerId, e);
							errorMessage.append(" Payment Amount cell is corrupted");
							validaterow = false;
						}
					} else {
						errorMessage.append("Amount format is wrong or null.");
						validaterow = false;
					}
					if (validaterow) {
						order = orderService.addOrderPayment(skucode,
								channelOrderId, ConverterClass.prepareOrderPaymentModel(paymentBean), sellerId);
					} else {
						errorSet.add(errorMessage.toString());

					}
					if (order != null) {
						order.setPaymentUpload(paymentUpload);
						paymentUpload.getOrders().add(order);
						generatePaymentUpload = true;
					}
					paymentMap.put(channelOrderId, paymentBean);
				} catch (Exception e) {
					log.error("Failed by seller: " + sellerId, e);
					errorMessage.append("Invalid Input !");
					errorSet.add(errorMessage.toString());
				}
			}			
			if (generatePaymentUpload) {
				log.info("Updating payment Upload with positive amount "
						+ totalpositive);
				log.info("Updating payment Upload with negative amount "
						+ totalnegative);
				paymentUpload.setTotalpositivevalue(totalpositive);
				paymentUpload.setTotalnegativevalue(totalnegative);
				paymentUpload.setNetRecievedAmount(totalpositive
						- totalnegative);
				paymentUpload.setUploadDesc("PAYU" + sellerId + ""
						+ new Date().getTime());
				paymentUpload.setUploadStatus("Success");
				paymentUploadService.addPaymentUpload(paymentUpload, sellerId);
			}

			uploadResultXLS(offices, "PayTM_Payment", errorSet, path,
					sellerId, uploadReport);
		}

		catch (Exception e) {
			log.error("Failed by seller: " + sellerId, e);
		}
	}
	
	
	
	public void uploadResultXLS(HSSFWorkbook workbook,String worksheetName, Set<String> errorSet,
			String path, int sellerId, UploadReport uploadReport)
			throws ClassNotFoundException, CustomException {

		log.info("$$$ downloadUploadReportXLS starts : SaveContents $$$");
		String errorMessage;
		boolean isError = false;
		HSSFSheet worksheet = workbook.getSheetAt(0);
	    int	colNumber=worksheet.getRow(0).getPhysicalNumberOfCells();
		worksheet.setColumnWidth(colNumber, 15000);
		// Create font style for the headers
		Font font = worksheet.getWorkbook().createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);

		// Create cell style for the headers
		HSSFCellStyle headerCellStyle = worksheet.getWorkbook()
				.createCellStyle();
		headerCellStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
		headerCellStyle.setFillPattern(CellStyle.FINE_DOTS);
		headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		headerCellStyle.setWrapText(true);
		headerCellStyle.setFont(font);
		headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);

		// Create font style for the error column
		font = worksheet.getWorkbook().createFont();
		font.setColor(HSSFColor.RED.index);

		// Create cell style for the error column
		HSSFCellStyle errorCellStyle = worksheet.getWorkbook()
				.createCellStyle();
		errorCellStyle.setWrapText(true);
		errorCellStyle.setFont(font);

		// Create the column headers
		HSSFRow rowHeader = worksheet.getRow(0);

		HSSFCell cell1 = rowHeader.createCell(colNumber+1);
		cell1.setCellValue("Error Message");
		cell1.setCellStyle(headerCellStyle);

		Row row = null;
		Cell cell = null;

		try {
			for (String key : errorSet) {

				errorMessage = key.substring(key.indexOf(':') + 1);
				int errorRow = Integer.parseInt(errorMessage.substring(0,
						errorMessage.indexOf(':')));
				errorMessage = errorMessage
						.substring(errorMessage.indexOf(':') + 1);

				if (errorMessage.length() > 5) {
					isError = true;
					row = worksheet.getRow(errorRow+1);
					cell = row.createCell(colNumber);
					cell.setCellValue(errorMessage);
					cell.setCellStyle(errorCellStyle);
				}
			}

			// constructs path of the directory to save uploaded file
			String uploadFilePath = path + File.separator + UPLOAD_DIR;
			log.debug("***** uploadFilePath path  : " + uploadFilePath);

			/*properties = PropertiesLoaderUtils.loadProperties(resource);
			uploadFilePath = properties.getProperty("uploadreport.path");*/

			log.debug("***** uploadFilePath path  : " + uploadFilePath);

			// creates the save directory if it does not exists
			File fileSaveDir = new File(uploadFilePath);
			if (!fileSaveDir.exists()) {
				log.debug(" Directory doesnnt exist");
				fileSaveDir.mkdirs();
			}

			String filePath = dataConfig.getUploadReportPath() + File.separator + worksheet.getSheetName()
					+ "Status" + new Date().getTime() + ".xls";
			FileOutputStream out = new FileOutputStream(new File(filePath));
			workbook.write(out);
			out.close();
			uploadReport.setFileType(worksheetName);
			uploadReport.setFilePath(filePath);
			uploadReport.setDescription("Imported");
			uploadReport.setSeller(sellerService.getSeller(sellerId));
			if (isError) {
				uploadReport.setStatus("Failed");
			} else {
				uploadReport.setStatus("Success");
			}
			reportGeneratorService.addUploadReport(uploadReport, sellerId);
			log.info("$$$ downloadUploadReportXLS ends : SaveContents $$$");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			log.error("Failed! by SellerId : "+sellerId, e);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("Failed! by SellerId : "+sellerId, e);
		}
	}
}

