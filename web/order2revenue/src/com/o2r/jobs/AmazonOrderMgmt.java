package com.o2r.jobs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Set;

import org.apache.jasper.tagplugins.jstl.core.Out;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.o2r.bean.AuthInfoBean;
import com.o2r.bean.CustomerBean;
import com.o2r.bean.OrderBean;
import com.o2r.bean.OrderTaxBean;
import com.o2r.helper.ConverterClass;
import com.o2r.helper.CustomException;
import com.o2r.helper.GlobalConstant;
import com.o2r.helper.SaveMappedFiles;
import com.o2r.model.AmazonOrderInfo;
import com.o2r.bean.DataConfig;
import com.o2r.model.AmazonOrderItemInfo;
import com.o2r.bean.OrderPaymentBean;
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
import com.o2r.model.PaymentVariables;
import com.o2r.model.Product;
import com.o2r.model.ProductConfig;
import com.o2r.model.Seller;
import com.o2r.model.TaxCategory;
import com.o2r.model.UploadReport;
import com.o2r.model.PartnerSellerAuthInfo;
import com.o2r.service.EventsService;
import com.o2r.service.ExpenseService;
import com.o2r.service.ManualChargesService;
import com.o2r.service.MwsAmazonOrdMgmtService;
import com.o2r.service.OrderService;
import com.o2r.service.PartnerService;
import com.o2r.service.PaymentUploadService;
import com.o2r.service.ProductService;
import com.o2r.service.ReportGeneratorService;
import com.o2r.service.SellerService;
import com.o2r.service.TaxDetailService;
import com.o2r.service.UploadMappingService;


@Service("amazonOrderMgmt")
@Transactional
public class AmazonOrderMgmt {

	//public MwsAmazonOrdMgmtService ordMgmtService = new MwsAmazonOrdMgmtServiceImpl();
	
	@Autowired
	private MwsAmazonOrdMgmtService ordMgmtService;
	@Autowired
	private UploadMappingService uploadMappingService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private PartnerService partnerService;
	@Autowired
	private ProductService productService;
	@Autowired
	private EventsService eventsService;
	@Autowired
	private TaxDetailService taxDetailService;
	
	
	static Logger log = Logger.getLogger(AmazonOrderMgmt.class.getName());
	
	
	public AmazonOrderMgmt() {
		// TODO Auto-generated constructor stub
	}
	//PartnerSellerAuthInfo partnerSellerAuthInfo
	public void invokeListOrdersAndOrderItems() throws Exception {
		
		/*try {
			List<Seller> sellers = ordMgmtService.getSellers();
			for (Seller seller : sellers) {
				for (Partner partner : seller.getPartners()) {
					PartnerSellerAuthInfo authInfo = ordMgmtService.getAuthInfoBeanObj(partner.getAuthInfo());
					com.amazonservices.mws.orders._2013_09_01.model.ListOrdersResult listOrderResult = ordMgmtService.getListOrders(ordMgmtService.getCreatedAfter(new Date()), ordMgmtService.getCreatedBefore(new Date()), ordMgmtService.getConfiguredoOrderStatus(), authInfo);
					List<com.amazonservices.mws.orders._2013_09_01.model.Order> orders = listOrderResult.getOrders();
					for (com.amazonservices.mws.orders._2013_09_01.model.Order order : orders) {
						com.amazonservices.mws.orders._2013_09_01.model.ListOrderItemsResult itemResults = ordMgmtService.getListOrderItems(authInfo, order.getAmazonOrderId());
						List<com.amazonservices.mws.orders._2013_09_01.model.OrderItem> orderItems = itemResults.getOrderItems();
						
						ordMgmtService.saveOrderInfo(order, orderItems);
					}
				}
			}
			
		} catch (Exception expObj) {
			expObj.printStackTrace();
		}*/
		
		
		try {
		//List<Seller> sellers = ordMgmtService.getSellers();
		//for (Seller seller : sellers) {
		//	for (Partner partner : seller.getPartners()) {
		
			List<PartnerSellerAuthInfo> partnerSellerAuthInfoList = ordMgmtService.getSellersFromPartnerSellerAuthoInfo();	
			
		//	System.out.println(" The PartnerSellerAuthInfo1 size is======================="+partnerSellerAuthInfoList.size());
			
			for (PartnerSellerAuthInfo PartnerSellerAuthInfo : partnerSellerAuthInfoList) {		
				
		 		PartnerSellerAuthInfo authInfo = ordMgmtService.getAuthInfoBeanObj(PartnerSellerAuthInfo);
				com.amazonservices.mws.orders._2013_09_01.model.ListOrdersResult listOrderResult = ordMgmtService.getListOrders(ordMgmtService.getCreatedAfter(new Date()), ordMgmtService.getCreatedBefore(new Date()), ordMgmtService.getConfiguredoOrderStatus(), authInfo);
				List<com.amazonservices.mws.orders._2013_09_01.model.Order> orders = listOrderResult.getOrders();
				for (com.amazonservices.mws.orders._2013_09_01.model.Order order : orders) {
					com.amazonservices.mws.orders._2013_09_01.model.ListOrderItemsResult itemResults = ordMgmtService.getListOrderItems(authInfo, order.getAmazonOrderId());
					List<com.amazonservices.mws.orders._2013_09_01.model.OrderItem> orderItems = itemResults.getOrderItems();
					
					ordMgmtService.saveOrderInfo(order, orderItems);
				}
			}
		//}
		
	} catch (Exception expObj) {
		expObj.printStackTrace();
	}
		
		
		
		
	}
	
	
	
	
	//public void saveAmzonOrderContents(MultipartFile file, int sellerId,
	//		String path, UploadReport uploadReport) throws IOException {
		
	public void  saveAmzonOrderContents(List<AmazonOrderInfo> amazonOrderInfoList) throws IOException {
		log.info("$$$ saveAmzonOrderContents starts : SaveMappedFiles $$$");

		ChannelUploadMapping chanupload = null;
		Map<String, String> columHeaderMap = new LinkedHashMap<String, String>();
		Map<String, Integer> cellIndexMap = new LinkedHashMap<String, Integer>();
		StringBuffer errorMessage = new StringBuffer();
		CustomerBean customerBean = null;
		Partner partner = null;
		Events event = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		List<Order> saveList = new ArrayList<Order>();
		Map<String, String> idsList = new HashMap<String, String>();
		Map<String, OrderBean> returnOrderMap = new LinkedHashMap<>();
		OrderBean order = null;
		int noOfEntries = 1;
		OrderTaxBean otb = null;
		int sellerId=0;
	//	HSSFRow entry;
		boolean validaterow = true;
		Set<String> errorSet = new HashSet<String>();
		Map<String, String> duplicateKey = new HashMap<String, String>();
		String uploadFileName = "";
		AmazonOrderItemInfo amazonOrderItemInfo=null;
		try {
			chanupload = uploadMappingService.getChannelUploadMapping("Amazon",
					"order");
			if (chanupload != null && chanupload.getColumMap() != null) {
				for (ColumMap colums : chanupload.getColumMap()) {
					columHeaderMap.put(colums.getO2rColumName(),
							colums.getChannelColumName());
				}
			}
			log.info("columHeaderMap for Amazon Order : " + columHeaderMap);
			//HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());

			//HSSFSheet worksheet = offices.getSheetAt(0);
			//while (worksheet.getRow(noOfEntries) != null) {
			//	noOfEntries++;
			//}
			
			//uploadFileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")) + new Date().getTime();
			//entry = worksheet.getRow(0);
			
			//amazonOrderInfoList.get(index) 
			for (int cellIndex = 0; cellIndex < amazonOrderInfoList.size(); cellIndex++) {
				
				
				AmazonOrderInfo amazonOrderInfo= amazonOrderInfoList.get(cellIndex); 
				//java.util.set com.o2r.model.AmazonOrderItemInfo>= amazonOrderInfo.getAmazonOrderItemInfos().
						
				//Set<AmazonOrderItemInfo> set = amazonOrderInfo.getAmazonOrderItemInfos();
				//List<AmazonOrderItemInfo> list= amazonOrderInfo.getAmazonOrderItemInfos();
				//amazonOrderItemInfo=(AmazonOrderItemInfo) set.toArray()[0];			
				
				
				//Set<Foo> set = ...;
				//List<AmazonOrderItemInfo> list = new ArrayList<AmazonOrderItemInfo>(set);
				//amazonOrderItemInfo = list.get(0);
				
				
				//sellerId = amazonOrderInfo.getSeller().getId();
				sellerId=65;
				if(amazonOrderInfo!=null){
					
					System.out.println("amazonOrderInfo.getAmazonorderid()"+amazonOrderInfo.getAmazonorderid());
					
					System.out.println("cellIndex"+cellIndex);
					
					cellIndexMap.put(amazonOrderInfo.getAmazonorderid(),
							cellIndex);
				}
			}
			log.info("cellIndexMap for Amazon Order : " + cellIndexMap);
			// SKUList = productService.listProductSKU(sellerId);
			idsList = orderService.listOrderIds("channelOrderID", sellerId);
			log.info(noOfEntries);
			log.debug("After getting no of rows" + noOfEntries);
			for (int rowIndex = 1; rowIndex < amazonOrderInfoList.size(); rowIndex++) {
				errorMessage = new StringBuffer("Row :" + (rowIndex) + ":");
				
				AmazonOrderInfo amazonOrderInfo= amazonOrderInfoList.get(rowIndex); 
				
				//amazonOrderInfo.getAmazonOrderItemInfos()amazonOrderInfo;
				
				//entry = worksheet.getRow(rowIndex);
				validaterow = true;
				order = new OrderBean();
				customerBean = new CustomerBean();
				otb = new OrderTaxBean();
				int index = 0;
				String itemID = null;
				ProductConfig productConfig = null;
				List<ProductConfig> productConfigs = null;
				Product product = null;
				TaxCategory taxcat = null;
				String channelorderid = "";
				errorMessage = new StringBuffer("Row :" + (rowIndex) + ":");
				try {

					try {
						//index = cellIndexMap.get(columHeaderMap.get("Sales Channel"));
						if (amazonOrderInfo.getSaleschannel() != null) {
							partner = partnerService.getPartner(amazonOrderInfo.getSaleschannel(), sellerId);
							
							System.out.println(" The partner"+partner);
				
							if (partner != null)
								order.setPcName(partner.getPcName());
							else {
								order.setPcName(amazonOrderInfo.getSaleschannel());
								errorMessage.append(" Partner do not exist;");
								validaterow = false;
							}
						} else {
							errorMessage.append(" Partner Name is null;");
							validaterow = false;
						}
					} catch (NullPointerException e) {
						e.printStackTrace();
						errorMessage
								.append("The column 'Sales Channel' doesn't exist");
						validaterow = false;
					}

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Channel Order ID"));
						int skuIndex = cellIndexMap.get(columHeaderMap
								.get("SkUCode"));
						if (amazonOrderItemInfo.getSellersku()!=null) {

							//entry.getCell(index).setCellType(HSSFCell.CELL_TYPE_STRING);
							//entry.getCell(skuIndex).setCellType(HSSFCell.CELL_TYPE_STRING);
							if (partner != null) {
								productConfigs = productService
										.getProductConfig(amazonOrderItemInfo.getSellersku(),
												partner.getPcName(), sellerId);
								if (productConfigs != null) {

									if (productConfigs.size() == 1) {
										productConfig = (ProductConfig) productConfigs
												.get(0);
									} else {
										ProductConfig pc = null;
										Set<String> parent = new HashSet<String>();
										for (Object PCo : productConfigs) {
											pc = (ProductConfig) PCo;
											parent.add(pc.getProductSkuCode());
										}
										if (parent.size() == 1) {
											productConfig = (ProductConfig) productConfigs
													.get(0);
										} else {
											errorMessage
													.append(" Multiple Mapping present for this Channel And SKU.");
											validaterow = false;
										}
									}

									try {
										if (productConfig.getVendorSkuRef() != null) {
											channelorderid = amazonOrderInfo.getAmazonorderid()
													+ GlobalConstant.orderUniqueSymbol
													+ productConfig
															.getVendorSkuRef();
										} else {
											errorMessage
													.append(" Vendor SKU code not mapped.");
											validaterow = false;
										}
										
									} catch (Exception e) {

									}
								} else {
									errorMessage
											.append("No Product with this Channel & SKU");
									validaterow = false;
								}
							}
						} else {
							errorMessage
									.append(" Channel OrderId or SKU code is null ");
							validaterow = false;
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("The column 'ORDER ID' doesn't exist");
						validaterow = false;
					}
					
					
					
					if (cellIndexMap.get(columHeaderMap
							.get("Secondary OrderID")) != null) {
						index = cellIndexMap.get(columHeaderMap.get("Secondary OrderID"));
						if (amazonOrderInfo.getAmazonorderid() != null) {
							//entry.getCell(index).setCellType(HSSFCell.CELL_TYPE_STRING);
							//itemID = amazonOrderInfo.getAmazonOrderItemInfos().entry.getCell(index).toString();
							itemID="";
							if (itemID.contains("'")) {
								itemID = removeExtraQuote(itemID);
							}
							itemID = removeExtraQuote(itemID);
							order.setSubOrderID(itemID);
						}
					}
					
					if(channelorderid != null){
						if(!idsList.containsKey(channelorderid)
								&& !duplicateKey.containsKey(channelorderid)){
							order.setChannelOrderID(channelorderid);
							if (productConfig != null){
								order.setProductSkuCode(productConfig.getProductSkuCode());	
								order.setPartnerCommission(productConfig.getCommision());
							}
							duplicateKey.put(channelorderid, itemID);
						} else {
							if(itemID != null && ((idsList.get(channelorderid) != null 
									&& !idsList.get(channelorderid).equals(itemID))
									|| (duplicateKey.containsKey(channelorderid) 
											&& (duplicateKey.get(channelorderid) == null
											|| !duplicateKey.get(channelorderid).equals(itemID))))){
								channelorderid = channelorderid + GlobalConstant.orderUniqueSymbol + itemID;
								if(!idsList.containsKey(channelorderid)
										&& !duplicateKey.containsKey(channelorderid)){
									order.setChannelOrderID(channelorderid);
									if (productConfig != null){
										order.setProductSkuCode(productConfig.getProductSkuCode());	
										order.setPartnerCommission(productConfig.getCommision());
									}													
									duplicateKey.put(channelorderid, itemID);
								} else {
									errorMessage.append("Channel Order Id is Already Present.");
									validaterow = false;
								}
							} else {
								errorMessage.append("Channel Order Id is Already Present.");
								validaterow = false;
							}																								
						}
					}
					
					

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Order Recieved Date"));
						if (amazonOrderInfo.getPurchasedate() != null ) {
							try {
								String dateStr = amazonOrderInfo.getPurchasedate().toString();
								String date = "";
								if (dateStr.contains("T")) {
									date = dateStr.substring(0,dateStr.indexOf("T"));
								} else {
									//date = entry.getCell(index).toString();
									date = dateStr;
								}
								order.setOrderDate(format.parse(date));
							} catch (Exception e) {
								errorMessage
										.append(" Order Received Date format is wrong,");
								validaterow = false;
							}

						} else {
							errorMessage
							.append(" Order Received Date is null,");
					validaterow = false;
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("The column 'Order recieved date' doesn't exist");
						validaterow = false;
					}

					if (cellIndexMap.get(columHeaderMap.get("Customer Email")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer Email"));
						if (amazonOrderInfo.getBuyeremail() != null) {
							log.debug(" Email from sheet ************ "
									+ rowIndex + ":"
									+ amazonOrderInfo.getBuyeremail());
							customerBean.setCustomerEmail(amazonOrderInfo.getBuyeremail());
						}
					}
					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer Name"));
						if (amazonOrderInfo.getBuyername() != null ) {
							customerBean.setCustomerName(amazonOrderInfo.getBuyername());
						}
					} catch (NullPointerException e) {

					}
					if (cellIndexMap.get(columHeaderMap
							.get("Customer Phone No")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer Phone No"));
						if (amazonOrderInfo.getBuyername() != null) {
							customerBean.setCustomerPhnNo(amazonOrderInfo.getBuyername());
							//entry.getCell(index).setCellType(HSSFCell.CELL_TYPE_STRING);
						/*	if (entry.getCell(index).toString().length() == 10
									&& !entry.getCell(index).toString()
											.contains("9999999999")) {
								if (entry.getCell(index).toString()
										.matches("[0-9]+")
										&& entry.getCell(index).toString()
												.length() > 2) {
									customerBean.setCustomerPhnNo(entry
											.getCell(index).toString());
								}
							}*/
						}
					}

					

					try {
						index = cellIndexMap
								.get(columHeaderMap.get("Quantity"));
						if (amazonOrderInfo.getNumberofitemsshipped()>=0) {
							try {
								if ((int) Float.parseFloat(amazonOrderInfo.getNumberofitemsshipped()
										.toString()) != 0) {
									order.setQuantity((int) Float
											.parseFloat(amazonOrderInfo.getNumberofitemsshipped()
													.toString()));
								} else {
									errorMessage
											.append(" Quantity can not be 0 ");
									validaterow = false;
								}
							} catch (NumberFormatException e) {
								log.error("Failed! by SellerId : " + sellerId,
										e);
								errorMessage
										.append(" Quantity should be a number ");
								validaterow = false;
							}
						} else {
							errorMessage.append(" Quantity can not be null ");
							validaterow = false;
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("The column 'quantity-purchased' doesn't exist");
						validaterow = false;
					}

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("OrderSP Component A"));
						int index1 = cellIndexMap.get(columHeaderMap
								.get("OrderSP Component B"));
						if (amazonOrderInfo.getOrdertotalamount() != null ) {
							try {
								order.setOrderSP(Double.parseDouble(amazonOrderInfo.getOrdertotalamount().toString())
										+ Double.parseDouble(amazonOrderInfo.getOrdertotalamount()));
							} catch (NumberFormatException e) {
								errorMessage
										.append(" Order SP should be a number ");
								validaterow = false;
							}
						} else {
							errorMessage.append(" Order SP is null ");
							validaterow = false;
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("The column 'item-price' or 'shipping-price' doesn't exist");
						validaterow = false;
					}

					/*try {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer Address 1"));
						int index1 = cellIndexMap.get(columHeaderMap
								.get("Customer Address 2"));
						int index2 = cellIndexMap.get(columHeaderMap
								.get("Customer Address 3"));
						if (amazonOrderInfo.getShippingAddress() != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK
								&& entry.getCell(index1) != null
								&& entry.getCell(index1).getCellType() != HSSFCell.CELL_TYPE_BLANK
								&& entry.getCell(index2) != null
								&& entry.getCell(index2).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							customerBean.setCustomerAddress(entry
									.getCell(index).toString()
									+ entry.getCell(index1).toString()
									+ entry.getCell(index2).toString());
						}
					} catch (NullPointerException e) {

					}

					if (amazonOrderInfo.getcellIndexMap.get(columHeaderMap.get("Customer City")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer City"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							customerBean.setCustomerCity(entry.getCell(index)
									.toString());
						}
					}*/

					/*try {
						index = cellIndexMap.get(columHeaderMap
								.get("Shipping PinCode"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							log.debug(" getting zipcode ");
							String zipCode = entry.getCell(index).toString();
							if (zipCode.contains(".")) {
								zipCode = zipCode.substring(0,
										zipCode.indexOf("."));
							}
							if (areaConfigDao.isZipCodeValid(zipCode)) {
								try {
									customerBean.setZipcode(zipCode);
								} catch (Exception e) {
									log.error("Failed! by SellerId : "
											+ sellerId, e);
									errorMessage
											.append("Customer zipcode is corrupted ");
									validaterow = false;
								}
							} else {
								errorMessage
										.append("Either invalid zipcode or try after some time while admin will add this to database.");
								validaterow = false;
								customerBean.setZipcode(entry.getCell(index)
										.toString());
							}
						} else {
							errorMessage.append("Customer zipcode is blank ");
							validaterow = false;
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("The column 'ship-postal-code' doesn't exist");
						validaterow = false;
					}*/

					/*try {
						index = cellIndexMap.get(columHeaderMap
								.get("Payment Type"));
						if (amazonOrderInfo.geentry.getCell(index) != null) {
							if (entry.getCell(index).toString().toUpperCase()
									.contains("PREPAID")
									|| entry.getCell(index).getCellType() == HSSFCell.CELL_TYPE_BLANK) {
								order.setPaymentType("Prepaid");
							} else if (entry.getCell(index).toString()
									.toUpperCase().contains("COD")) {
								order.setPaymentType("COD");
							} else if (entry.getCell(index).toString()
									.toUpperCase().contains("OTHERS")) {
								order.setPaymentType("Others");
							} else {
								order.setPaymentType("Others");
							}
						} else {
							order.setPaymentType("Others");
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("The column 'payment-method' doesn't exist");
						validaterow = false;
					}*/

					/*if (cellIndexMap
							.get(columHeaderMap.get("Logistic Partner")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Logistic Partner"));
						if (entry.getCell(index) != null) {
							if (entry.getCell(index).getCellType() == HSSFCell.CELL_TYPE_BLANK) {
								order.setLogisticPartner("Self Shift");
							} else {
								order.setLogisticPartner(entry.getCell(index)
										.toString());
							}
						}
					}*/

					/*try {
						index = cellIndexMap.get(columHeaderMap
								.get("InvoiceID"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							entry.getCell(index).setCellType(
									HSSFCell.CELL_TYPE_STRING);
							order.setInvoiceID(entry.getCell(index).toString());
						} else {
							order.setInvoiceID(GlobalConstant.randomNo());
						}
					} catch (Exception e) {
						order.setInvoiceID(GlobalConstant.randomNo());
					}*/

					/*if (cellIndexMap.get(columHeaderMap.get("AWB No")) != null) {
						index = cellIndexMap.get(columHeaderMap.get("AWB No"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							entry.getCell(index).setCellType(
									HSSFCell.CELL_TYPE_STRING);
							order.setAwbNum(entry.getCell(index).toString());
						}
					}*/

					
					/*if (cellIndexMap.get(columHeaderMap.get("PIreferenceNo")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("PIreferenceNo"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							order.setPIreferenceNo(entry.getCell(index)
									.toString());
						}
					}*/

					if (cellIndexMap.get(columHeaderMap.get("Order MRP")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Order MRP"));
						amazonOrderInfo.getOrdertotalamount();
						if (amazonOrderInfo.getOrdertotalamount() != null) {
							try {
								order.setOrderMRP(Double.parseDouble(amazonOrderInfo.getOrdertotalamount()));
							} catch (NumberFormatException e) {
								log.error("Failed! by SellerId : " + sellerId,
										e);
								errorMessage
										.append(" Order MRP should be a number ");
							}
						}
					}

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Order Shipped Date"));
						if (amazonOrderInfo.getPurchasedate() != null) {
							try {
								String dateStr = amazonOrderInfo.getPurchasedate().toString();
								String date = "";
								if (dateStr.contains("T")) {
								//	date = entry.getCell(index).toString().substring(0,entry.getCell(index).toString().indexOf("T"));
								} else {
									date = dateStr;
								}
								order.setShippedDate(new Date(date));
							} catch (Exception e) {
								errorMessage
										.append(" Shipped Date formate is wrong ,enter mm/dd/yyyy,");
								validaterow = false;
							}
						} else {
							if(order.getOrderDate()!=null)
								order.setShippedDate(order.getOrderDate());
							else
							{
								errorMessage
								.append("'Order Shipped Date' or 'Purchase Date' are empty. ");
						validaterow = false;
							}
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("The column 'Order Shipped Date' or 'Purchase Date' doesn't exist");
						validaterow = false;
					}

					if (partner != null) {
						event = eventsService.isEventActiive(
								order.getOrderDate(), partner.getPcName(),
								order.getProductSkuCode(), sellerId);

						if (event != null) {
							if (event.getNrnReturnConfig()
									.getNrCalculatorEvent()
									.equalsIgnoreCase("fixed")) {
								if (cellIndexMap.get(columHeaderMap
										.get("Net Rate")) != null) {
									index = cellIndexMap.get(columHeaderMap
											.get("Net Rate"));
									if (amazonOrderInfo.getOrdertotalamount() != null) {
										try {
											order.setGrossNetRate(Double
													.parseDouble(amazonOrderInfo.getOrdertotalamount()));
										} catch (NumberFormatException e) {
											log.error("Failed! by SellerId : "
													+ sellerId, e);
											errorMessage
													.append(" Net Rate should be number ");
											validaterow = false;
										}
									} else {
										errorMessage
												.append(" Net Rate is null and ongoing event expects NR.");
										validaterow = false;
									}
								} else {
									errorMessage
											.append("The column 'Gross NR' doesn't exist");
									validaterow = false;
								}
							}
						} else {
							if (partner != null
									&& partner.getNrnReturnConfig() != null
									&& !partner.getNrnReturnConfig()
											.isNrCalculator()) {
								if (cellIndexMap.get(columHeaderMap
										.get("Net Rate")) != null) {
									index = cellIndexMap.get(columHeaderMap
											.get("Net Rate"));
									if (amazonOrderInfo.getOrdertotalamount() != null) {
										try {
											order.setGrossNetRate(Double
													.parseDouble(amazonOrderInfo.getOrdertotalamount().toString()));
										} catch (NumberFormatException e) {
											log.error("Failed! by SellerId : "
													+ sellerId, e);
											errorMessage
													.append(" Net Rate should be number ");
											validaterow = false;
										}
									} else {
										errorMessage
												.append(" Net Rate is null.");
										validaterow = false;
									}
								} else {
									errorMessage
											.append(" The Column 'Net Rate' Not Present.");
									validaterow = false;
								}
							}
						}
					}

					if (order.getProductSkuCode() != null) {
						product = productService.getProduct(
								order.getProductSkuCode(), sellerId);
						if (product != null) {
							taxcat = taxDetailService.getTaxCategory(product,
									order.getOrderSP(), sellerId, customerBean.getZipcode());
						}
					}
					if (taxcat != null)
						otb.setTaxCategtory(taxcat.getTaxCatName());
					else {
						if (product != null) {
							errorMessage
									.append("No Tax Category mapped for this product ");
							validaterow = false;
						}
					}

					/*if (cellIndexMap.get(columHeaderMap.get("Seller Note")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Seller Note"));
						if (entry.getCell(index) != null
								&& StringUtils.isNotBlank(entry.getCell(index)
										.toString())) {
							order.setSellerNote(entry.getCell(index).toString());

						}
					}*/

					// Pre save to generate id for use in hierarchy
					if (validaterow) {
						order.setOrderFileName(uploadFileName);
						order.setCustomer(customerBean);
						order.setOrderTax(otb);
						/*
						 * orderService.addOrder(ConverterClass.prepareModel(order
						 * ), sellerId);
						 * 
						 */
						log.info(" Adding order to save list : "
								+ order.getChannelOrderID());
						saveList.add(ConverterClass.prepareModel(order));
					} else {
						order.setCustomer(customerBean);
						order.setOrderTax(otb);
						log.debug(" Error while saving order : "
								+ order.getChannelOrderID()
								+ " errorMessage : " + errorMessage);
						/*if(cellIndexMap.get("ship-promotion-id")!=null
								&&entry.getCell(cellIndexMap.get("ship-promotion-id")) != null
								&& StringUtils.isNotBlank(entry.getCell(cellIndexMap.get("ship-promotion-id"))
										.toString()))
						{
							log.info("Promotion ID available for this order.");
						}
						else*/
						returnOrderMap.put(errorMessage.toString(), order);
					}
				} catch (Exception e) {

					log.error("Failed! by SellerId : " + sellerId, e);
					errorMessage.append("Invalid Input! ");
					returnOrderMap.put(errorMessage.toString(), order);
				}
			}

			try {
				System.out.println(" SaveList Size : " + saveList.size());
				if (saveList != null && saveList.size() != 0)
					orderService.addOrder(saveList, sellerId);
			} catch (CustomException ce) {
				returnOrderMap
						.put("Row :1:Note-Some orders("
								+ ce.getLocalMessage()
								+ " ) with valid input "
								+ "failed due to internal server error. Please contact admin.!",
								null);
			}
			errorSet = returnOrderMap.keySet();
			//uploadResultXLS(offices, "Amazon_Order_Upload", uploadFileName, errorSet, path,sellerId, uploadReport);

		} catch (Exception e) {
			log.debug("Inside save contents exception :"
					+ e.getLocalizedMessage());
			e.printStackTrace();
			//addErrorUploadReport("Amazon_Order_Upload", sellerId, uploadReport);
			log.error("Failed! by SellerId : " + sellerId, e);
		}
	}
	
	
	private static String removeExtraQuote(String input) {
		if (input.contains("'"))
			input = input.replaceAll("'", "");
		if (input.contains("`"))
			input = input.replaceAll("`", "");

		return input;
	}
	
	
	
	
	public static void main(String args[]) throws Exception{
		
		System.out.println(" I am here");
		AmazonOrderMgmt amazonOrderMgmt = new AmazonOrderMgmt();
		//amazonOrderMgmt.invokeListOrdersAndOrderItems();
	}
	
}
