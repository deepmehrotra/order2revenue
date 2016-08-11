package com.o2r.helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

import com.o2r.bean.CustomerBean;
import com.o2r.bean.DataConfig;
import com.o2r.bean.OrderBean;
import com.o2r.bean.OrderPaymentBean;
import com.o2r.bean.OrderTaxBean;
import com.o2r.dao.AreaConfigDao;
import com.o2r.model.ChannelUploadMapping;
import com.o2r.model.ColumMap;
import com.o2r.model.Events;
import com.o2r.model.Expenses;
import com.o2r.model.ManualCharges;
import com.o2r.model.Order;
import com.o2r.model.OrderPayment;
import com.o2r.model.Partner;
import com.o2r.model.PaymentUpload;
import com.o2r.model.Product;
import com.o2r.model.TaxCategory;
import com.o2r.model.UploadReport;
import com.o2r.service.EventsService;
import com.o2r.service.ExpenseService;
import com.o2r.service.ManualChargesService;
import com.o2r.service.OrderService;
import com.o2r.service.PartnerService;
import com.o2r.service.PaymentUploadService;
import com.o2r.service.ProductService;
import com.o2r.service.ReportGeneratorService;
import com.o2r.service.SellerService;
import com.o2r.service.TaxDetailService;
import com.o2r.service.UploadMappingService;


@Service("saveMappedFiles")
@Transactional
public class SaveMappedFiles {
	
	@Autowired
	private UploadMappingService uploadMappingService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private ProductService productService;
	@Autowired
	private PaymentUploadService paymentUploadService;
	@Autowired
	private DataConfig dataConfig;
	@Autowired
	private SellerService sellerService;
	@Autowired
	private ReportGeneratorService reportGeneratorService;
	@Autowired
	private EventsService eventsService;
	@Autowired
	private ManualChargesService manualChargesService;
	@Autowired
	private PartnerService partnerService;
	@Autowired
	private TaxDetailService taxDetailService;
	@Autowired
	private AreaConfigDao areaConfigDao;
	@Autowired
	private ExpenseService expenseService;
	
	private static final String UPLOAD_DIR = "UploadReport";
	
	static Logger log = Logger.getLogger(SaveMappedFiles.class.getName());
	
	
	public void saveFlipkartOrderContents(MultipartFile file, int sellerId,
			String path, UploadReport uploadReport) throws IOException {
		log.info("$$$ saveFlipkartPaymentContents starts : SaveMappedFiles $$$");

		ChannelUploadMapping chanupload = null;
		Map<String, String> columHeaderMap = new LinkedHashMap<String, String>();
		Map<String, Integer> cellIndexMap = new LinkedHashMap<String, Integer>();		
		StringBuffer errorMessage = null;
		String channelOrderId = null;
		String skucode = null;
		CustomerBean customerBean = null;
		Partner partner = null;		
		Events event = null;
		List<Order> saveList=null;
		List<String> SKUList=new ArrayList<String>();
		List<String> idsList=new ArrayList<String>();		
		Map<String, OrderBean> returnOrderMap = new LinkedHashMap<>();
		OrderBean order = null;		
		int noOfEntries = 1;
		OrderTaxBean otb = null;
		HSSFRow entry;
		boolean validaterow = true;				
		Set<String> errorSet = new HashSet<String>();		
		Map<String,String> duplicateKey=new HashMap<String, String>();
		try {
			chanupload=uploadMappingService.getChannelUploadMapping("Flipkart", "order");
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
			SKUList=productService.listProductSKU(sellerId);
			idsList=orderService.listOrderIds("channelOrderID", sellerId);			
			log.info(noOfEntries);
			log.debug("After getting no of rows" + noOfEntries);
			for (int rowIndex = 1; rowIndex < noOfEntries; rowIndex++) {
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				order = new OrderBean();
				customerBean = new CustomerBean();
				otb = new OrderTaxBean();
				int index=0; 
				String channelheader=null;
				channelOrderId=null;
				try
				{		

				index=cellIndexMap.get(columHeaderMap.get("Channel Order ID"));
				if (entry.getCell(index) != null
						&& entry.getCell(0).getCellType() != HSSFCell.CELL_TYPE_BLANK) {				
					
					if ((!idsList.contains(entry.getCell(index).toString())) 
							&& !duplicateKey.containsKey(entry.getCell(0).toString())) {						
						order.setChannelOrderID(entry.getCell(0).toString());
						duplicateKey.put(entry.getCell(index).toString(), "");
					} else {
						order.setChannelOrderID(entry.getCell(index).toString());
						errorMessage.append(" Channel OrderId is already present ");
						validaterow = false;
					}
				} else {
					errorMessage.append(" Channel OrderId is null ");
					validaterow = false;
				}
				index=cellIndexMap.get(columHeaderMap.get("Order Recieved Date"));
				if (entry.getCell(index) != null
						&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					try {
						if (HSSFDateUtil.isCellDateFormatted(entry.getCell(index))) {
							order.setOrderDate(entry.getCell(index).getDateCellValue());
						} else {
							errorMessage.append(" Order Received Date format is wrong ,enter mm/dd/yyyy,");
							validaterow = false;
						}
					} catch (Exception e) {
						errorMessage.append(" Order Received Date format is wrong ,enter mm/dd/yyyy,");
						validaterow = false;
					}				
					
				} else {
					errorMessage.append(" Order Recieved Date is null;");
					validaterow = false;
				}
				index=cellIndexMap.get(columHeaderMap.get("SkUCode"));
				if (entry.getCell(index) != null
						&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {					
					if (SKUList.contains(entry.getCell(index).toString())) {
						order.setProductSkuCode(entry.getCell(index).toString());						
					} else {
						order.setProductSkuCode(entry.getCell(index).toString());	
						errorMessage.append(" Product SKU does not exist;");
						validaterow = false;
					}
				} else {
					errorMessage.append(" Product SKU is null;");
					validaterow = false;
				}
				index=cellIndexMap.get(columHeaderMap.get("Sales Channel"));
				if (entry.getCell(index) != null
						&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {					
					partner=partnerService.getPartner(entry.getCell(index).toString(), sellerId);
					if (partner != null)
						order.setPcName(partner.getPcName());
					else {
						order.setPcName(partner.getPcName());
						errorMessage.append(" Partner do not exist;");
						validaterow = false;
					}
				} else {
					errorMessage.append(" Partner Name is null;");
					validaterow = false;
				}
				index=cellIndexMap.get(columHeaderMap.get("Customer Name"));
				if (entry.getCell(index) != null
						&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					customerBean.setCustomerName(entry.getCell(index).toString());
				}
				index=cellIndexMap.get(columHeaderMap.get("Payment Type"));
				if (entry.getCell(index) != null
						&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					if (entry.getCell(index).toString().equalsIgnoreCase("COD")
							|| entry.getCell(index).toString().equalsIgnoreCase("Prepaid") 
							|| entry.getCell(index).toString().equalsIgnoreCase("Others"))
						order.setPaymentType(entry.getCell(index).toString());
					else {
						order.setPaymentType(entry.getCell(index).toString());
						errorMessage.append(" Payment type should be COD or Prepaid or Others");
						validaterow = false;
					}
				} else {
					errorMessage.append(" Payment type is null");
					validaterow = false;
				}
				index=cellIndexMap.get(columHeaderMap.get("AWB No"));
				if (entry.getCell(index) != null
						&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					order.setAwbNum(entry.getCell(index).toString());
				} 
				index=cellIndexMap.get(columHeaderMap.get("InvoiceID"));
				if (entry.getCell(index) != null
						&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					order.setInvoiceID(entry.getCell(index).toString());
				} else {
					order.setInvoiceID(entry.getCell(index).toString());
					errorMessage.append(" Invoice ID is null;");
					validaterow = false;
				}
				index=cellIndexMap.get(columHeaderMap.get("Secondary OrderID"));
				if (entry.getCell(index) != null
						&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					order.setSubOrderID(entry.getCell(index).toString());
				}
				index=cellIndexMap.get(columHeaderMap.get("PIreferenceNo"));
				if (entry.getCell(index) != null
						&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					order.setPIreferenceNo(entry.getCell(index).toString());
				}
				index=cellIndexMap.get(columHeaderMap.get("Logistic Partner"));
				if (entry.getCell(index) != null
						&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					order.setLogisticPartner(entry.getCell(index).toString());
				}
				index=cellIndexMap.get(columHeaderMap.get("Order MRP"));
				if (entry.getCell(index) != null
						&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					try {
						order.setOrderMRP(Double.parseDouble(entry.getCell(index).toString()));
					} catch (NumberFormatException e) {
						log.error("Failed! by SellerId : "+sellerId, e);
						errorMessage.append(" Order MRP should be a number ");						
					}
				} 
				index=cellIndexMap.get(columHeaderMap.get("Order SP"));
				if (entry.getCell(index) != null
						&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					try {
						order.setOrderSP(Double.parseDouble(entry.getCell(index).toString()));
					} catch (NumberFormatException e) {
						errorMessage.append(" Order SP should be a number ");
						validaterow = false;
					}
				} else {
					errorMessage.append(" Order SP is null ");
					validaterow = false;
				}
				index=cellIndexMap.get(columHeaderMap.get("Tax Category"));
				if (entry.getCell(index) != null
						&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					TaxCategory taxcat = taxDetailService.getTaxCategory(entry.getCell(index).toString(), sellerId);
					if (taxcat != null)
						otb.setTaxCategtory(entry.getCell(index).toString());
					else {
						otb.setTaxCategtory(entry.getCell(index).toString());
						errorMessage.append("Tax Category does not exist ");
						validaterow = false;
					}
				} else {
					errorMessage.append("Tax Category is null ");
					validaterow = false;
				}
				index=cellIndexMap.get(columHeaderMap.get("Order Shipped Date"));
				if (entry.getCell(index) != null
						&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					try
					{
						if (HSSFDateUtil.isCellDateFormatted(entry.getCell(index))) {
							order.setShippedDate(entry.getCell(index).getDateCellValue());
						} else {
							errorMessage.append(" Shipped Date formate is wrong ,enter mm/dd/yyyy,");
							validaterow = false;
						}
					}
					catch(Exception e)
					{
						errorMessage
						.append(" Shipped Date formate is wrong ,enter mm/dd/yyyy,");
						validaterow = false;
					}
				} else {
					errorMessage.append(" Shipping Date is null ");
					validaterow = false;
				}
				index=cellIndexMap.get(columHeaderMap.get("Quantity"));
				if (entry.getCell(index) != null
						&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					try {
						if ((int) Float.parseFloat(entry.getCell(index).toString()) != 0) {
							order.setQuantity((int) Float.parseFloat(entry.getCell(index).toString()));
						} else {
							errorMessage.append(" Quantity can not be 0 ");
							validaterow = false;
						}
					} catch (NumberFormatException e) {
						log.error("Failed! by SellerId : "+sellerId, e);
						errorMessage.append(" Quantity should be a number ");
						validaterow = false;
					}
				} else {
					errorMessage.append(" Quantity can not be null ");
					validaterow = false;
				}
				
				/*if (partner != null) {
					event = eventsService.isEventActiive(order.getOrderDate(),
							partner.getPcName(),order.getProductSkuCode(), sellerId);
					if (event != null) {
						if (event.getNrnReturnConfig().getNrCalculatorEvent()
								.equalsIgnoreCase("fixed")) {

							if (entry.getCell(16) != null
									&& entry.getCell(16).getCellType() != HSSFCell.CELL_TYPE_BLANK) {

								try {
									order.setGrossNetRate(Double.parseDouble(entry.getCell(16).toString()));
								} catch (NumberFormatException e) {
									log.error("Failed! by SellerId : "+sellerId, e);
									errorMessage.append(" Net Rate should be number ");
									validaterow = false;
								}
							} else {
								errorMessage.append(" Net Rate is null and ongoing event on the order with fixed price.");
								
							}
						}
					} else {
						if (partner != null
								&& partner.getNrnReturnConfig() != null
								&& !partner.getNrnReturnConfig().isNrCalculator()) {
							if (entry.getCell(16) != null
									&& entry.getCell(16).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
								try {
									order.setGrossNetRate(Double.parseDouble(entry.getCell(16).toString()));
								} catch (NumberFormatException e) {
									log.error("Failed! by SellerId : "+sellerId, e);
									errorMessage.append(" Net Rate should be number ");
									
								}
							} else {
								errorMessage.append(" Net Rate is null ");								
							}
						}

					}
				}*/
				index=cellIndexMap.get(columHeaderMap.get("Customer Email"));
				if (entry.getCell(index) != null
						&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					log.debug(" Email from sheet ************ " + rowIndex
							+ ":" + entry.getCell(index).toString());
					customerBean.setCustomerEmail(entry.getCell(index).toString());
				}
				index=cellIndexMap.get(columHeaderMap.get("Customer Phone No"));
				if (entry.getCell(index) != null
						&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					customerBean.setCustomerPhnNo(entry.getCell(index).toString());
				}
				index=cellIndexMap.get(columHeaderMap.get("Customer City"));
				if (entry.getCell(index) != null
						&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					customerBean.setCustomerCity(entry.getCell(index).toString());
				}
				index=cellIndexMap.get(columHeaderMap.get("Customer Email"));
				if (entry.getCell(index) != null
						&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					customerBean.setCustomerAddress(entry.getCell(index).toString());
				}
				index=cellIndexMap.get(columHeaderMap.get("Shipping PinCode"));
				if (entry.getCell(index) != null
						&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					log.debug(" getting zipcode ");
					if (areaConfigDao.isZipCodeValid(entry.getCell(index).toString())) {
						try {
							customerBean.setZipcode(entry.getCell(index).toString());
						} catch (Exception e) {
							log.error("Failed! by SellerId : "+sellerId, e);
							errorMessage.append("Customer zipcode is corrupted ");
							validaterow = false;
						}
					} else {
						errorMessage.append("Customer zipcode is not valid ");
						validaterow = false;
						customerBean.setZipcode(entry.getCell(index).toString());
					}
				} else {
					errorMessage.append("Customer zipcode is blank ");
					validaterow = false;
				}
				index=cellIndexMap.get(columHeaderMap.get("Seller Note"));
				if (entry.getCell(index) != null
						&& StringUtils.isNotBlank(entry.getCell(index).toString())) {
					order.setSellerNote(entry.getCell(index).toString());

				}
				
				// Pre save to generate id for use in hierarchy
				if (validaterow) {
					order.setCustomer(customerBean);
					order.setOrderTax(otb);
					/*orderService.addOrder(ConverterClass.prepareModel(order),
							sellerId);*/
					System.out.println(" Adding order to save list : "+order.getChannelOrderID());
					saveList.add(ConverterClass.prepareModel(order));
				} else {
					order.setCustomer(customerBean);
					order.setOrderTax(otb);
					log.debug(" Error while saving order : "
							+ order.getChannelOrderID() + " errorMessage : "
							+ errorMessage);
					returnOrderMap.put(errorMessage.toString(), order);
				}
				}
				catch(Exception e)
				{
					
					log.error("Failed! by SellerId : "+sellerId, e);
					errorMessage.append("Invalid Input! ");
					returnOrderMap.put(errorMessage.toString(), order);
				}
			}
			try
			{
				System.out.println(" SaveList Size : "+saveList.size());
				if(saveList!=null&&saveList.size()!=0)
					orderService.addOrder(saveList, sellerId);
			}
			catch(CustomException ce)
			{
				returnOrderMap.put("Row :1:Note-Some orders("+ce.getLocalMessage() +" ) with valid input "
						+ "failed due to internal server error. Please contact admin.!", null);
			}
			errorSet = returnOrderMap.keySet();
			uploadResultXLS(offices, "MP_Order_Upload", errorSet,
					path, sellerId, uploadReport);

		} catch (Exception e) {
			log.debug("Inside save contents exception :"
					+ e.getLocalizedMessage());
			e.printStackTrace();
			addErrorUploadReport("MP_Order_Upload", sellerId, uploadReport);
			log.error("Failed! by SellerId : "+sellerId, e);
		}
	}
	
	
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
		List<ManualCharges> manualChargesList=new ArrayList<ManualCharges>();
		ManualCharges newmanualCharge=null;
		boolean generatePaymentUpload = false;
		String uploadPaymentId=null;
		Map<String,String> duplicateKey=new HashMap<String, String>();
		
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
			for (int rowIndex = 1; rowIndex < noOfEntries; rowIndex++) {
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				int index=0; 
				String channelheader=null;
				channelOrderId=null;
				errorMessage = new StringBuffer("Row :" + (rowIndex) + ":");
				//Code for fetching right colum
				orderPayment=new OrderPayment();
				order=null;
				
					try
					{
					
					if(entry.getCell(indexfulfilmentType)!=null&&
							entry.getCell(indexfulfilmentType).toString().equalsIgnoreCase("NON-FA"))
					{
			
					channelheader=columHeaderMap.get("ChannelOrderId");
					index=cellIndexMap.get(channelheader);
					List<Order> onj = orderService
							.findOrders("channelOrderID", getSubstringValue(':',entry.getCell(index)
									.toString()), sellerId, false, false);
					if (onj != null && onj.size() != 0) {
						channelOrderId = getSubstringValue(':',entry.getCell(index)
								.toString());
							} else {
						channelOrderId = getSubstringValue(':',entry.getCell(index)
								.toString());
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
						else
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
						
					if (validaterow) {
						order =orderService.addOrderPayment(skucode, channelOrderId,
								orderPayment, sellerId);
					} else {
						errorSet.add(errorMessage.toString());
						
					}
					if (order != null&validaterow) {
						if(!duplicateKey.containsKey(channelOrderId))
						{
						System.out.println(" Setting payment upload");
						order.setPaymentUpload(paymentUpload);
						paymentUpload.getOrders().add(order);
						generatePaymentUpload = true;
						duplicateKey.put(channelOrderId, channelOrderId);
						}
						
					}	
					
					}
					else if(entry.getCell(indexfulfilmentType)!=null&&
							entry.getCell(indexfulfilmentType).toString().equalsIgnoreCase("null"))
					{
						index=cellIndexMap.get(columHeaderMap.get("Seller SKU"));
						if(entry.getCell(index) != null
								&& StringUtils.isNotBlank(entry.getCell(index)
										.toString()))
								{
							skucode=entry.getCell(index).toString();
								}
						if(skucode!=null&&skucode.equalsIgnoreCase("null"))
						{
							System.out.println(" Setting manula charges ");
							newmanualCharge=new ManualCharges();
							index=cellIndexMap.get(columHeaderMap.get("Payment Date"));
							if(entry.getCell(index) != null
									&& HSSFDateUtil.isCellDateFormatted(entry.getCell(index)))
									{
								newmanualCharge.setDateOfPayment(entry.getCell(index)
										.getDateCellValue());
									}
							else
							{
								errorMessage
								.append(" Payment Date format is wrong or null");
								validaterow = false;
							}
							index=cellIndexMap.get(columHeaderMap.get("Particulars"));
							if (entry.getCell(index) != null
									&& StringUtils.isNotBlank(entry.getCell(index)
											.toString())) {
								newmanualCharge.setParticular(entry.getCell(index).toString());
							}
							
							index=cellIndexMap.get(columHeaderMap.get("Recieved Amount"));
							if(entry.getCell(index) != null
									&& StringUtils.isNotBlank(entry.getCell(index)
											.toString()))
									{
								try
								{
								double amount=entry.getCell(index).getNumericCellValue();
									newmanualCharge.setPaidAmount((-amount));
								
								
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
						}
						else
						{
							errorMessage
							.append("SKU value should be present SKU or null.");
							validaterow = false;
						}
						
						if (validaterow) {
							System.out.println(" Adding manul charges to list");
							manualChargesList.add(newmanualCharge);
						} else {
							errorSet.add(errorMessage.toString());
						}
					}
					else
					{
						errorMessage
						.append("Fulfilment Type is not accepted.Only NON-FA is valid.");
						validaterow = false;
						errorSet.add(errorMessage.toString());
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
				uploadPaymentId=paymentUploadService.addPaymentUpload(paymentUpload, sellerId);		
				System.out.println(" Saving payment upload : "+uploadPaymentId);
			}
			if(manualChargesList != null && manualChargesList.size() != 0){
				for(ManualCharges manuals:manualChargesList){
					try
					{
						System.out.println(" Seting manual charges uploadPaymentId "+uploadPaymentId);
						if(uploadPaymentId!=null)
						manuals.setChargesDesc(uploadPaymentId);
					manuals.setPartner("Flipkart");
					expenseService.addExpense(new Expenses("Manual Charges", uploadPaymentId, "Manual Charges",
							new Date(), manuals.getDateOfPayment(), manuals.getPaidAmount(),"Flipkart", sellerId), sellerId);
					}
					catch(Exception e)
					{
						log.error("Failed! by SellerId : "+sellerId, e);
					}
				}
				manualChargesService.addListManualCharges(manualChargesList, sellerId);
			}
			uploadResultXLS(offices, "Flipkart_Payment", errorSet,
					path, sellerId, uploadReport);
		}
				
		catch(Exception e)
		{
			log.error("Failed by seller: "+sellerId, e);
		}
	}
	
	
	public void saveSnapDealPaymentContents(MultipartFile file,
			int sellerId, String path, UploadReport uploadReport)
			throws IOException {
		log.info("$$$ saveSnapDealPaymentContents starts : SaveMappedFiles $$$");
		
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
		double totalpositive=0;
		double totalnegative=0;
		Set<String>  errorSet=new HashSet<String>();
		PaymentUpload paymentUpload = new PaymentUpload();
		List<ManualCharges> manualChargesList=new ArrayList<ManualCharges>();
		ManualCharges newmanualCharge=null;
		boolean generatePaymentUpload = false;
		String uploadPaymentId=null;
		Map<String,String> duplicateKey=new HashMap<String, String>();
		
		try {
			chanupload=uploadMappingService.getChannelUploadMapping("Snapdeal", "Payment");
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
			for (int rowIndex = 1; rowIndex < noOfEntries; rowIndex++) {
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				int index=0; 
				String channelheader=null;
				channelOrderId=null;
				errorMessage = new StringBuffer("Row :" + (rowIndex) + ":");
				//Code for fetching right colum
				orderPayment=new OrderPayment();
				order=null;
					try
					{
						channelheader=columHeaderMap.get("ChannelOrderId");
						index=cellIndexMap.get(channelheader);
					if(entry.getCell(index)!=null)
					{
						log.info(" channelorder id: "+entry.getCell(index).toString());
						;
					if(StringUtils.isNumeric(entry.getCell(index).toString()))
					{
					List<Order> onj = orderService
							.findOrders("channelOrderID", entry.getCell(index)
									.toString(), sellerId, false, false);
					if (onj != null && onj.size() != 0&&onj.size()<2) {
						channelOrderId = entry.getCell(index).toString();
							} else {
						channelOrderId = entry.getCell(index).toString();
						errorMessage
								.append(" Order with channel OrderId not present ");
						validaterow = false;
					}
					
					index=cellIndexMap.get(columHeaderMap.get("Payment Date"));
					try
					{
					if(entry.getCell(index) != null
							)
							{
						orderPayment.setDateofPayment(new Date(entry.getCell(index).toString()));
							}
					else
					{
						errorMessage
						.append(" Payment Date format is wrong or null");
						validaterow = false;
					}
					}
					catch(Exception e)
					{
						log.error("Failed by selled ID "+sellerId,e);
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
						else
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
					if (validaterow) {
						order =orderService.addOrderPayment(skucode, channelOrderId,
								orderPayment, sellerId);
					} else {
						errorSet.add(errorMessage.toString());
						
					}
					if (order != null) {
						if(!duplicateKey.containsKey(channelOrderId)){
							order.setPaymentUpload(paymentUpload);
							paymentUpload.getOrders().add(order);
							generatePaymentUpload = true;
							duplicateKey.put(channelOrderId, channelOrderId);
						}						
					}	
					
					}
					else
					{
						System.out.println(" Reading manual charge : "+entry.getCell(index));
							newmanualCharge=new ManualCharges();
							newmanualCharge.setPartner("Snapdeal");
							index=cellIndexMap.get(columHeaderMap.get("Payment Date"));
							System.out.println("Payment Date : "+entry.getCell(index));
							if(entry.getCell(index) != null)
									{
								newmanualCharge.setDateOfPayment(new Date(entry.getCell(index).toString()));
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
									newmanualCharge.setPaidAmount((-amount));
								
								
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
							index=cellIndexMap.get(columHeaderMap.get("Particulars"));
							if(entry.getCell(index) != null
									&& !StringUtils.isBlank(entry.getCell(index).toString()))
									{
								newmanualCharge.setParticular(entry.getCell(index).toString());
									}
							
							
						if (validaterow) {
							manualChargesList.add(newmanualCharge);
						} else {
							errorSet.add(errorMessage.toString());
						}
					}
					
					}
					else
					{
						errorMessage
						.append("Order ID is empty or null.");
						validaterow = false;
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
				uploadPaymentId=paymentUploadService.addPaymentUpload(paymentUpload, sellerId);				
			}
			if(manualChargesList != null && manualChargesList.size() != 0){
				for(ManualCharges manuals:manualChargesList){
					try
					{
						System.out.println(" Payment Upload list created : "+uploadPaymentId);
					manuals.setChargesDesc(uploadPaymentId);					
					expenseService.addExpense(new Expenses("Manual Charges", uploadPaymentId, "Manual Charges", new Date(), 
							manuals.getDateOfPayment(), manuals.getPaidAmount(),"Snapdeal", sellerId), sellerId);
					}
					catch(Exception e)
					{
						log.error("Failed! by SellerId : "+sellerId, e);
					}
				}
				manualChargesService.addListManualCharges(manualChargesList, sellerId);
			}
			uploadResultXLS(offices, "Snapdeal_Payment", errorSet,
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
		double totalpositive = 0;
		double totalnegative = 0;
		Set<String> errorSet = new HashSet<String>();
		PaymentUpload paymentUpload = new PaymentUpload();
		boolean generatePaymentUpload = false;
		Map<String,String> duplicateKey=new HashMap<String, String>();
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
			
			for (int rowIndex = 1; rowIndex < noOfEntries; rowIndex++) {
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				int index = 0;
				String channelheader = null;
				channelOrderId = null;
				errorMessage = new StringBuffer("Row :" + (rowIndex) + ":");
				// Code for fetching right colum
				orderPayment = new OrderPayment();

				try {					
					channelheader = columHeaderMap.get("ChannelOrderId");					
					index = cellIndexMap.get(channelheader);
					if (entry.getCell(index) != null && StringUtils.isNotBlank(entry.getCell(index).toString())) {						
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
					}else{
						errorMessage.append(" ChannelOrderId & SKU May be Blank or Null");
						validaterow = false;
					}					
					if (validaterow) {
						order = orderService.addOrderPayment(skucode,channelOrderId, orderPayment, sellerId);						
					} else {
						errorSet.add(errorMessage.toString());

					}
					if (order != null) {
						if(!duplicateKey.containsKey(channelOrderId)){
							order.setPaymentUpload(paymentUpload);
							paymentUpload.getOrders().add(order);
							generatePaymentUpload = true;
							duplicateKey.put(channelOrderId, channelOrderId);							
						}						
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
		double totalpositive = 0;
		double totalnegative = 0;
		Set<String> errorSet = new HashSet<String>();
		PaymentUpload paymentUpload = new PaymentUpload();
		boolean generatePaymentUpload = false;
		Map<String , OrderPaymentBean> paymentMap= new HashMap<String, OrderPaymentBean>();
		OrderPaymentBean paymentBean=null;
		List<ManualCharges> manualChargesList=new ArrayList<ManualCharges>();
		ManualCharges manualCharge = null;
		String uploadPaymentId=null;
		String key=null;
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
			for (int rowIndex = 1; rowIndex < noOfEntries; rowIndex++) {
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
					
					if(entry.getCell(index) != null && StringUtils.isNotBlank(entry.getCell(index).toString())){						
						
						List<Order> onj = orderService.findOrders("subOrderID", entry.getCell(index)
										.toString(), sellerId, false, false);
						System.out.println(entry.getCell(index).toString());
						if (onj != null && onj.size() != 0) {
							if(onj.size() == 1){
								if(paymentMap.containsKey(entry.getCell(index).toString())){
									paymentBean=paymentMap.get(entry.getCell(index).toString());									
								}else{
									paymentBean=new OrderPaymentBean();
								}	
								key=entry.getCell(index).toString();
								paymentBean.setChannelOrderId(onj.get(0).getChannelOrderID());
							}else{
								errorMessage.append(" Multiple Order on this ID ");
								validaterow = false;
							}										
							
						} else {
							channelOrderId = onj.get(0).getChannelOrderID();
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
								String amt=entry.getCell(index).toString().substring(entry.getCell(index).toString().indexOf(".")+1).trim();
								System.out.println(amt);
								double amount =Double.parseDouble(amt);		
								System.out.println(amount);
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
							paymentMap.put(key, paymentBean);
							
						} else {
							errorSet.add(errorMessage.toString());
						}								
						
					}else{
						index=cellIndexMap.get(columHeaderMap.get("Payment Detail"));
						if(entry.getCell(index) != null	&& StringUtils.isNotBlank(entry.getCell(index).toString()) && 
								(!entry.getCell(index).toString().equalsIgnoreCase("Current Reserve Amount") && 
										!entry.getCell(index).toString().equalsIgnoreCase("Previous Reserve Amount Balance"))){
							
							manualCharge= new ManualCharges();
							manualCharge.setParticular(entry.getCell(index).toString());
							manualCharge.setPartner("Amazon");
							index=cellIndexMap.get(columHeaderMap.get("Recieved Amount"));
							if(entry.getCell(index) != null	&& StringUtils.isNotBlank(entry.getCell(index).toString())){
								try{
									String amt=entry.getCell(index).toString().substring(entry.getCell(index).toString().indexOf(".")+1).trim();
									System.out.println(amt);
									System.out.println(NumberFormat.getNumberInstance(java.util.Locale.US).parse(amt));
									double amount =Double.parseDouble(amt.replaceAll(",", ""));
									System.out.println(amount);
									manualCharge.setPaidAmount(-amount);
								}catch(Exception e){
									log.error("Failed",e);
									errorMessage.append("Invalid Amount Format");
									validaterow = false;
								}								
							}else{
								errorMessage.append("Field Is Empty Or Null");
								validaterow = false;
							}
							index=cellIndexMap.get(columHeaderMap.get("Payment Date"));
							if(entry.getCell(index) != null	&& StringUtils.isNotBlank(entry.getCell(index).toString())){
								try{
									if (HSSFDateUtil.isCellDateFormatted(entry.getCell(index))) {
										manualCharge.setDateOfPayment(entry.getCell(index).getDateCellValue());
									} else {
										errorMessage.append(" Payment Date format is wrong ,enter mm/dd/yyyy ");
										validaterow = false;
									}
								}catch(Exception e){
									errorMessage.append("Date May Be Wrong Format");
									validaterow = false;
								}								
							}else{
								errorMessage.append("Date Is Empty Or Null");
								validaterow = false;
							}
						}else{
							errorMessage.append("Payment Details May Be Null Or Blank Or Not In Condition");
							validaterow = false;
						}
						if (validaterow) {
							manualChargesList.add(manualCharge);
						}
					}					
				} catch (Exception e) {
					log.error("Failed by seller: " + sellerId, e);
					errorMessage.append("Invalid Input !");
					errorSet.add(errorMessage.toString());
				}
			}
			if(paymentMap != null){
				for (Entry<String, OrderPaymentBean> entryz : paymentMap.entrySet())
				{
					order = orderService.addOrderPayment(skucode,entryz.getValue().getChannelOrderId(), ConverterClass.prepareOrderPaymentModel(entryz.getValue()), sellerId);
					if (order != null) {
						order.setPaymentUpload(paymentUpload);
						paymentUpload.getOrders().add(order);
						generatePaymentUpload = true;
					}
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
				uploadPaymentId=paymentUploadService.addPaymentUpload(paymentUpload, sellerId);
			}
			if(manualChargesList != null && manualChargesList.size() != 0){
				for(ManualCharges manuals:manualChargesList){
					try
					{
					manuals.setChargesDesc(uploadPaymentId);
					expenseService.addExpense(new Expenses("Manual Charges", uploadPaymentId, "Manual Charges", new Date(),
							manuals.getDateOfPayment(), manuals.getPaidAmount(),"Amazon", sellerId), sellerId);
					}
					catch(Exception e)
					{
						log.error("Failed! by SellerId : "+sellerId, e);
					}
				}
				manualChargesService.addListManualCharges(manualChargesList, sellerId);
			}			
			uploadResultXLS(offices, "Amazon_Payment", errorSet, path,
					sellerId, uploadReport);
		} catch (Exception e) {
			log.error("Failed by seller: " + sellerId, e);
		}
	}
	
	
	public void saveLimeroadPaymentContents(MultipartFile file, int sellerId,
			String path, UploadReport uploadReport) throws IOException {
		log.info("$$$ saveLimeroadPaymentContents starts : SaveMappedFiles $$$");

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
		double totalpositive = 0;
		double totalnegative = 0;
		Set<String> errorSet = new HashSet<String>();
		PaymentUpload paymentUpload = new PaymentUpload();
		boolean generatePaymentUpload = false;
		Map<String,String> duplicateKey=new HashMap<String, String>();
		OrderPaymentBean paymentBean=null;		
		try {
			chanupload = uploadMappingService.getChannelUploadMapping("Limeroad",
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
			for (int rowIndex = 1; rowIndex < noOfEntries; rowIndex++) {
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				int index = 0;
				int skuIndex= 0;
				String channelheader = null;
				channelOrderId = null;
				errorMessage = new StringBuffer("Row :" + (rowIndex) + ":");
				
				// Code for fetching right colum				
				try {
					
					channelheader = columHeaderMap.get("ChannelOrderId");
					index = cellIndexMap.get(channelheader);
					skuIndex = cellIndexMap.get(columHeaderMap.get("SKU"));				
					if(entry.getCell(index) != null && StringUtils.isNotBlank(entry.getCell(index).toString()) 
							&& entry.getCell(skuIndex) != null && StringUtils.isNotBlank(entry.getCell(skuIndex).toString())){							
						
						List<Order> onj = orderService.findOrders("channelOrderID", entry.getCell(index)
								.toString()+"-"+entry.getCell(skuIndex).toString(), sellerId, false, false);
						System.out.println(entry.getCell(index).toString()+"-"+entry.getCell(skuIndex).toString());
						if (onj != null && onj.size() != 0) {
							if(onj.size() == 1){
								paymentBean=new OrderPaymentBean();								
								channelOrderId = onj.get(0).getChannelOrderID();
								
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
										double amount = entry.getCell(index).getNumericCellValue();	
										System.out.println(amount);
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
								
							}else{
								errorMessage.append(" Multiple Order on this ID ");
								validaterow = false;
							}										
							
						} else {
							channelOrderId = entry.getCell(index).toString()+"-"+entry.getCell(skuIndex).toString();
							errorMessage.append(" Channel OrderId not present ");
							validaterow = false;
						}
						
					} else{
						channelOrderId = entry.getCell(index).toString()+"-"+entry.getCell(skuIndex).toString();
						errorMessage.append(" ChannelOrderId & SKU May be Blank or Null");
						validaterow = false;
					}
					if (validaterow) {
						order = orderService.addOrderPayment(skucode,channelOrderId, ConverterClass.prepareOrderPaymentModel(paymentBean), sellerId);						
					} else {
						errorSet.add(errorMessage.toString());
					}	
					if (order != null) {
						if(!duplicateKey.containsKey(channelOrderId)){					
							order.setPaymentUpload(paymentUpload);
							paymentUpload.getOrders().add(order);
							generatePaymentUpload = true;
							duplicateKey.put(channelOrderId,channelOrderId);
						}						
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
			uploadResultXLS(offices, "Limeroad_Payment", errorSet, path,
					sellerId, uploadReport);
		} catch (Exception e) {
			log.error("Failed by seller: " + sellerId, e);
		}
	}
	
	
	
	public void uploadResultXLS(HSSFWorkbook workbook,String worksheetName, Set<String> errorSet,
			String path, int sellerId, UploadReport uploadReport)
			throws ClassNotFoundException, CustomException {

		log.info("$$$ uploadResultXLS starts : SaveContents $$$");
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

		HSSFCell cell1 = rowHeader.createCell(colNumber);
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
					row = worksheet.getRow(errorRow);
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
	public String getSubstringValue(char delimeter,String text)
	{
		if(text!=null&&!StringUtils.isBlank(text))
		{
			return text.substring(text.indexOf(delimeter)+1, text.length());
		}
		else 
			return "";
	}
	public void addErrorUploadReport(String worksheetName,
			int sellerId, UploadReport uploadReport) {

	log.info("$$$ addErrorUploadReport starts : SaveContents $$$");
	
	try {
		
		uploadReport.setFileType(worksheetName);
		uploadReport.setDescription("Imported");
		uploadReport.setSeller(sellerService.getSeller(sellerId));
		uploadReport.setStatus("Error");
		
		reportGeneratorService.addUploadReport(uploadReport, sellerId);
		log.info("$$$ downloadUploadReportXLS ends : SaveContents $$$");

	} catch (Exception e) {
		e.printStackTrace();
		log.error("Failed! by SellerId : "+sellerId, e);
	}
}
}

