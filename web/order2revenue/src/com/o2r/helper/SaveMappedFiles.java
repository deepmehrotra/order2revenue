package com.o2r.helper;

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
import com.o2r.model.PaymentVariables;
import com.o2r.model.Product;
import com.o2r.model.ProductConfig;
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
	
	private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	private final SimpleDateFormat formatmonthname = new SimpleDateFormat("dd-MMM-yyyy");
	private final SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final String regexForZipcode = "\\d{6}$";
	
	public void saveUnicommerceOrderContents(MultipartFile file, int sellerId,
			String path, UploadReport uploadReport) throws IOException {
		log.info("$$$ saveUnicommerceOrderContents starts : SaveMappedFiles $$$");

		ChannelUploadMapping chanupload = null;
		Map<String, String> columHeaderMap = new LinkedHashMap<String, String>();
		Map<String, Integer> cellIndexMap = new LinkedHashMap<String, Integer>();
		StringBuffer errorMessage = new StringBuffer();
		CustomerBean customerBean = null;		
		Events event = null;
		List<Order> saveList = new ArrayList<Order>();
		Map<String, String> idsList = new HashMap<String, String>();
		Map<String, Order> multyQtyMap = new HashMap<String, Order>();
		Map<String, OrderBean> returnOrderMap = new LinkedHashMap<>();
		OrderBean order = null;
		int noOfEntries = 1;
		OrderTaxBean otb = null;
		HSSFRow entry;
		boolean validaterow = true;
		Set<String> errorSet = new HashSet<String>();
		Map<String, String> duplicateKey = new HashMap<String, String>();
		String uploadFileName = "";
		try {
			chanupload = uploadMappingService.getChannelUploadMapping(
					"Unicommerce", "order");
			if (chanupload != null && chanupload.getColumMap() != null) {
				for (ColumMap colums : chanupload.getColumMap()) {
					columHeaderMap.put(colums.getO2rColumName(),
							colums.getChannelColumName());
				}
			}
			log.info("columHeaderMap for  : " + columHeaderMap);
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());

			HSSFSheet worksheet = offices.getSheetAt(0);
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			uploadFileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")) + new Date().getTime();
			entry = worksheet.getRow(0);
			for (int cellIndex = 0; cellIndex < entry
					.getPhysicalNumberOfCells(); cellIndex++) {

				if (entry.getCell(cellIndex) != null
						&& columHeaderMap.containsValue(entry
								.getCell(cellIndex).toString())) {
					cellIndexMap.put(entry.getCell(cellIndex).toString(),
							cellIndex);
				}

			}
			// log.info("cellIndexMap: " + cellIndexMap);
			// SKUList = productService.listProductSKU(sellerId);
			idsList = orderService.listOrderIds("channelOrderID", sellerId);
			log.info(noOfEntries);
			log.debug("After getting no of rows" + noOfEntries);
			for (int rowIndex = 1; rowIndex < noOfEntries; rowIndex++) {
				errorMessage = new StringBuffer("Row :" + (rowIndex) + ":");
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				order = new OrderBean();
				customerBean = new CustomerBean();
				otb = new OrderTaxBean();
				int index = 0;
				String typeIden = null;
				String itemID = null;
				Partner partner = null;
				String channelID = null;
				String sku = "";
				String uniqueID4Map = null;
				Product product = null;
				ProductConfig productConfig = null;
				List<ProductConfig> productConfigs = null;
				TaxCategory taxcat = null;
				int o2rIndex;
				try {

					try {
						if (cellIndexMap.get(columHeaderMap.get("Type Identifier")) != null) {
							index = cellIndexMap.get(columHeaderMap.get("Type Identifier"));
							if (entry.getCell(index) != null
									&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
								typeIden = entry.getCell(index).toString();
							}
						}
						
						if (cellIndexMap.get(columHeaderMap
								.get("Secondary OrderID")) != null) {
							index = cellIndexMap.get(columHeaderMap.get("Secondary OrderID"));
							if (entry.getCell(index) != null
									&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
								entry.getCell(index).setCellType(HSSFCell.CELL_TYPE_STRING);
								itemID = entry.getCell(index).toString();
								if (itemID.contains("'")) {
									itemID = removeExtraQuote(itemID);
								}
								itemID = removeExtraQuote(itemID);
								order.setSubOrderID(itemID);
							}
						}
						
						try {
							index = cellIndexMap.get(columHeaderMap
									.get("Order Recieved Date"));
							if (entry.getCell(index) != null
									&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
								try {
									String dateStr = entry.getCell(index)
											.toString();
									order.setOrderDate(new Date(dateStr));
								} catch (Exception e) {
									errorMessage
											.append(" Order Received Date format is wrong,");
									validaterow = false;
								}

							} else {
								errorMessage
										.append(" Order Recieved Date is null;");
								validaterow = false;
							}
						} catch (NullPointerException e) {
							errorMessage
									.append("The column 'purchase-date' doesn't exist");
							validaterow = false;
						}
						
						try {
							o2rIndex = cellIndexMap.get(columHeaderMap
									.get("O2R Channel"));
							if (entry.getCell(o2rIndex) != null
									&& entry.getCell(o2rIndex).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
								partner = partnerService
										.getPartner(entry.getCell(o2rIndex)
												.toString(), sellerId);
								if (partner != null)
									order.setPcName(partner.getPcName());
								else {
									order.setPcName(entry.getCell(o2rIndex)
											.toString());
									errorMessage
											.append(" Partner do not exist;");
									validaterow = false;
								}
							} else {
								errorMessage.append(" Partner Name is null;");
								validaterow = false;
							}
						} catch (NullPointerException e) {
							errorMessage
									.append("The column 'O2R Channel' doesn't exist");
							validaterow = false;
						}

						/*
						 * if (cellIndexMap.get(columHeaderMap
						 * .get("Sales Channel")) != null) { index =
						 * cellIndexMap.get(columHeaderMap
						 * .get("Sales Channel")); } else { errorMessage
						 * .append("The column 'Sales Channel' doesn't exist");
						 * validaterow = false; }
						 */
						int idIndex = cellIndexMap.get(columHeaderMap
								.get("Channel Order ID"));
						int skuIndex = cellIndexMap.get(columHeaderMap
								.get("SkUCode"));
						if (partner != null) {
							if (entry.getCell(idIndex) != null
									&& entry.getCell(idIndex).getCellType() != HSSFCell.CELL_TYPE_BLANK
									&& entry.getCell(skuIndex) != null
									&& entry.getCell(skuIndex).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
								entry.getCell(idIndex).setCellType(
										HSSFCell.CELL_TYPE_STRING);
								entry.getCell(skuIndex).setCellType(
										HSSFCell.CELL_TYPE_STRING);
								productConfigs = productService
										.getProductConfig(
												entry.getCell(skuIndex)
														.toString().trim()
														.toUpperCase(),
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
										if (productConfig.getVendorSkuRef() == null) {
											errorMessage
													.append(" Corresponding vendor SKU code is not present.");
											validaterow = false;
										} else {
											sku = productConfig
													.getVendorSkuRef();

											if (partner != null
													&& partner.getPcName()
															.contains(
																	"limeroad")
													&& entry.getCell(idIndex)
															.toString()
															.contains("S")) {
												channelID = entry
														.getCell(idIndex)
														.toString()
														.substring(
																0,
																entry.getCell(
																		idIndex)
																		.toString()
																		.indexOf(
																				"S"))
														+ GlobalConstant.orderUniqueSymbol
														+ sku;
											} else {
												channelID = entry.getCell(
														idIndex).toString()
														+ GlobalConstant.orderUniqueSymbol
														+ sku;
											}										
											
											uniqueID4Map = channelID;
											
											if(channelID != null){                                                
                                                if (!(partner.getPcName().toLowerCase().contains(GlobalConstant.PCFLIPKART)
                                                       || partner.getPcName().toLowerCase().contains(GlobalConstant.PCPAYTM)
                                                       || partner.getPcName().toLowerCase().contains(GlobalConstant.PCJABONG))) {    
                                                    String duplicateItemId=null;
                                                    if(!(itemID != null && itemID.contains("-") && typeIden != null) 
                                                            && !(itemID != null && itemID.contains("-") && typeIden == null)
                                                            && !partner.getPcName().toLowerCase().contains(GlobalConstant.PCAMAZON)){
                                                    	duplicateItemId="";
                                                        itemID = format.format(order.getOrderDate());
                                                    }
                                                    
                                                    if(!idsList.containsKey(channelID)
                                                            && !duplicateKey.containsKey(channelID)){
                                                        order.setChannelOrderID(channelID);
                                                        if (productConfig != null){
                                                            order.setProductSkuCode(productConfig.getProductSkuCode());    
                                                            order.setPartnerCommission(productConfig.getCommision());
                                                        }
                                                        if(itemID != null && itemID.contains("-") && typeIden != null){
                                                            String TI = channelID.substring(0, channelID.indexOf(GlobalConstant.orderUniqueSymbol));                                            
                                                            order.setTypeIdentifier(TI);
                                                        }
                                                        duplicateKey.put(channelID, itemID);
                                                    } else {                                                        
                                                        if(itemID != null && ((idsList.get(channelID) != null 
                                                                && !idsList.get(channelID).equals(itemID))
                                                                || (duplicateKey.containsKey(channelID) 
                                                                        && (duplicateKey.get(channelID) == null
                                                                        || !duplicateKey.get(channelID).equals(itemID))))){
                                                            channelID = channelID + GlobalConstant.orderUniqueSymbol + itemID;                                                        
                                                            if(!idsList.containsKey(channelID)
                                                                    && !duplicateKey.containsKey(channelID)){
                                                                order.setChannelOrderID(channelID);
                                                                if (productConfig != null){
                                                                    order.setProductSkuCode(productConfig.getProductSkuCode());    
                                                                    order.setPartnerCommission(productConfig.getCommision());
                                                                }
                                                                if(itemID != null && itemID.contains("-") && typeIden != null){
                                                                    String TI = channelID.substring(0, channelID.indexOf(GlobalConstant.orderUniqueSymbol));                                            
                                                                    order.setTypeIdentifier(TI);                                                                                                                                        
                                                                }
                                                                duplicateKey.put(channelID, itemID);
                                                            } else {
                                                                errorMessage.append("Channel Order Id is Already Present.");
                                                                validaterow = false;
                                                            }
                                                        } else {
                                                            errorMessage.append("Channel Order Id is Already Present.");
                                                            validaterow = false;
                                                        }                                                                                                
                                                    }  
                                                    if(duplicateItemId!=null)
                                                    	itemID=null;
                                                }
                                            }												
										}
									} catch (Exception e) {

									}
								} else {
									errorMessage
											.append(" No product with this Channel & SKU ");
									validaterow = false;
								}
							} else {
								errorMessage
										.append(" Channel OrderId is null ");
								validaterow = false;
							}
						}
					} catch (NullPointerException e) {

						log.error("Failed in getting channel order id : ", e);
						errorMessage
								.append("The column 'order-id' doesn't exist");

						validaterow = false;
					}

					if (cellIndexMap.get(columHeaderMap
							.get("Secondary OrderID")) != null) {						
						if (partner != null && channelID != null) {							
							if (itemID != null) {
								if (partner.getPcName().toLowerCase()
										.contains(GlobalConstant.PCFLIPKART)
										|| partner
												.getPcName()
												.toLowerCase()
												.contains(
														GlobalConstant.PCPAYTM)
										||partner
												.getPcName()
												.toLowerCase()
												.contains(
														GlobalConstant.PCJABONG)) {

									channelID = channelID
											+ GlobalConstant.orderUniqueSymbol
											+ order.getSubOrderID();
									if ((channelID != null)
											&& (idsList == null || (!idsList
													.containsKey(channelID))
													&& !duplicateKey
															.containsKey(channelID)
													&& productConfig != null)) {
										order.setChannelOrderID(channelID);
										order.setProductSkuCode(productConfig.getProductSkuCode());
                                        order.setPartnerCommission(productConfig.getCommision());
                                        if(itemID != null && itemID.contains("-") && typeIden != null){
                							String TI = channelID.substring(0, channelID.indexOf(GlobalConstant.orderUniqueSymbol))
                									+ itemID.substring(0, itemID.indexOf("-"));                                        	
                                        	order.setTypeIdentifier(TI);
                						}
										duplicateKey.put(channelID, "");
									} else {
										errorMessage.append(" Channel OrderId is already present ");
										validaterow = false;
									}
								}
							} else {
								if (partner.getPcName().toLowerCase()
										.contains(GlobalConstant.PCFLIPKART)
										|| partner.getPcName().toLowerCase()
												.contains(GlobalConstant.PCPAYTM)
														||partner.getPcName().toLowerCase()
																.contains(GlobalConstant.PCJABONG)) {
									errorMessage.append(" The column 'Sale Order Item Code' is null, it is mandatory.");
									validaterow = false;
								}
							}
						}
					} else {
						if (partner != null) {
							if (partner.getPcName().toLowerCase()
									.contains(GlobalConstant.PCFLIPKART)
									|| partner.getPcName().toLowerCase()
											.contains(GlobalConstant.PCPAYTM)
											||partner.getPcName().toLowerCase()
													.contains(GlobalConstant.PCJABONG)) {

								errorMessage.append(" The column 'Sale Order Item Code' doesn't exist, it is mandatory for Flipkart & Paytm & Jabong.");
								validaterow = false;
							}
						}
					}					

					if (cellIndexMap.get(columHeaderMap.get("Customer Email")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer Email"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							if (!entry.getCell(index).toString()
									.contains("@marketplace.amazon.in"))
								customerBean.setCustomerEmail(entry.getCell(
										index).toString());
						}
					}
					if (cellIndexMap.get(columHeaderMap.get("Customer Name")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer Name"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							customerBean.setCustomerName(entry.getCell(index)
									.toString());
						}
					}
					if (cellIndexMap.get(columHeaderMap
							.get("Customer Phone No")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer Phone No"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							entry.getCell(index).setCellType(
									HSSFCell.CELL_TYPE_STRING);
							if (entry.getCell(index).toString().length() == 10
									&& !entry.getCell(index).toString()
											.contains("9999999999")) {
								if (entry.getCell(index).toString()
										.matches("[0-9]+")
										&& entry.getCell(index).toString()
												.length() > 2) {
									customerBean.setCustomerPhnNo(entry
											.getCell(index).toString());
								}
							}
						}
					}

					try {
						index = cellIndexMap
								.get(columHeaderMap.get("Quantity"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							try {
								if ((int) Float.parseFloat(entry.getCell(index)
										.toString()) != 0) {
									order.setQuantity((int) Float
											.parseFloat(entry.getCell(index)
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

					if (partner != null) {
						if (partner.getPcName().equalsIgnoreCase(
								GlobalConstant.PCAMAZON)
								|| partner.getPcName().equalsIgnoreCase(
										GlobalConstant.PCFLIPKART)) {
							try {
								index = cellIndexMap.get(columHeaderMap
										.get("Order SP"));
								if (entry.getCell(index) != null
										&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
									try {
										order.setOrderSP(Double
												.parseDouble(entry.getCell(
														index).toString()));
									} catch (NumberFormatException e) {
										errorMessage
												.append(" Total Price should be a number ");
										validaterow = false;
									}
								} else {
									errorMessage
											.append(" Total Price is null ");
									validaterow = false;
								}
							} catch (NullPointerException e) {
								errorMessage
										.append("The column 'Total Price' doesn't exist");
								validaterow = false;
							}
						} else {
							if (cellIndexMap.get(columHeaderMap
									.get("Order SP All")) != null) {
								index = cellIndexMap.get(columHeaderMap
										.get("Order SP All"));
								if (entry.getCell(index) != null
										&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
									try {
										order.setOrderSP(Double
												.parseDouble(entry.getCell(
														index).toString()));
									} catch (NumberFormatException e) {
										errorMessage
												.append(" Selling Price should be a number ");
										validaterow = false;
									}
								} else {
									errorMessage
											.append(" Selling Price is null ");
									validaterow = false;
								}
							} else {
								errorMessage
										.append("The column 'Selling Price' doesn't exist");
								validaterow = false;
							}
						}
					}

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer Address A"));
						int index1 = cellIndexMap.get(columHeaderMap
								.get("Customer Address B"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK
								&& entry.getCell(index1) != null
								&& entry.getCell(index1).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							customerBean.setCustomerAddress(entry
									.getCell(index).toString()
									+ entry.getCell(index1).toString());
						}
					} catch (NullPointerException e) {

					}

					if (cellIndexMap.get(columHeaderMap.get("Customer City")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer City"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							customerBean.setCustomerCity(entry.getCell(index)
									.toString());
						}
					}

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Shipping PinCode"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {

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
					}

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Payment Type"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							System.out.println(entry.getCell(index).toString());
							String type = entry.getCell(index).toString();
							if (type.contains(".")) {
								type = type.substring(0, type.indexOf("."));
							}
							if (type.equals("1")) {
								order.setPaymentType("COD");
							} else if (type.equals("0")) {
								order.setPaymentType("Prepaid");
							} else {
								errorMessage.append("Invalid COD Type");
								validaterow = false;
							}
						} else {
							errorMessage.append("COD column is Null or Blank");
							validaterow = false;
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("The column 'payment-method' doesn't exist");
						validaterow = false;
					}

					if (cellIndexMap
							.get(columHeaderMap.get("Logistic Partner")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Logistic Partner"));
						if (entry.getCell(index) != null) {
							if (entry.getCell(index).getCellType() == HSSFCell.CELL_TYPE_BLANK) {
								order.setLogisticPartner("Self Shipped");
							} else {
								order.setLogisticPartner(entry.getCell(index)
										.toString());
							}
						}
					}

					try {
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
					}

					if (cellIndexMap.get(columHeaderMap.get("AWB No")) != null) {
						index = cellIndexMap.get(columHeaderMap.get("AWB No"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							entry.getCell(index).setCellType(
									HSSFCell.CELL_TYPE_STRING);
							order.setAwbNum(entry.getCell(index).toString());
						}
					}

					if (cellIndexMap.get(columHeaderMap.get("PIreferenceNo")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("PIreferenceNo"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							order.setPIreferenceNo(entry.getCell(index)
									.toString());
						}
					}

					if (cellIndexMap.get(columHeaderMap.get("Order MRP")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Order MRP"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							try {
								order.setOrderMRP(Double.parseDouble(entry
										.getCell(index).toString()));
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
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							try {
								String dateStr = entry.getCell(index)
										.toString();
								String date = "";
								if (dateStr.contains("T")) {
									date = entry
											.getCell(index)
											.toString()
											.substring(
													0,
													entry.getCell(index)
															.toString()
															.indexOf("T"));
								} else {
									date = entry.getCell(index).toString();
								}
								order.setShippedDate(new Date(date));
							} catch (Exception e) {
								errorMessage
										.append(" Shipped Date format is wrong ,enter mm/dd/yyyy,");
								validaterow = false;
							}
						} else {
							index = cellIndexMap.get(columHeaderMap.get("Updated Date"));
							if (entry.getCell(index) != null
									&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
								try {
									String dateStr = entry.getCell(index)
											.toString();
									String date = "";
									if (dateStr.contains("T")) {
										date = entry
												.getCell(index)
												.toString()
												.substring(
														0,
														entry.getCell(index)
																.toString()
																.indexOf("T"));
									} else {
										date = entry.getCell(index).toString();
									}
									order.setShippedDate(new Date(date));
								} catch (Exception e) {
									errorMessage
											.append("Updated Date format is wrong ,enter mm/dd/yyyy,");
									validaterow = false;
								}
							} else {
								errorMessage.append(" Shipping Date and Updated column is null.");
								validaterow = false;
							}
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("The column 'Dispatch Date/Updated' doesn't exist.");
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
									if (entry.getCell(index) != null
											&& entry.getCell(index)
													.getCellType() != HSSFCell.CELL_TYPE_BLANK) {

										try {
											order.setGrossNetRate(Double
													.parseDouble(entry.getCell(
															index).toString()));
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
									if (entry.getCell(index) != null
											&& entry.getCell(index)
													.getCellType() != HSSFCell.CELL_TYPE_BLANK) {
										try {
											order.setGrossNetRate(Double
													.parseDouble(entry.getCell(
															index).toString()));
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

						if (taxcat != null)
							otb.setTaxCategtory(taxcat.getTaxCatName());
						else {
							if (product != null) {
								errorMessage
										.append("No Tax Category mapped for this product ");
								validaterow = false;
							}
						}
					}
					if (cellIndexMap.get(columHeaderMap.get("Seller Note")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Seller Note"));
						if (entry.getCell(index) != null
								&& StringUtils.isNotBlank(entry.getCell(index)
										.toString())) {
							order.setSellerNote(entry.getCell(index).toString());

						}
					}

					// Pre save to generate id for use in hierarchy
					if (validaterow) {
						order.setOrderFileName(uploadFileName);
						order.setCustomer(customerBean);
						order.setOrderTax(otb);	
						if(itemID != null && itemID.contains("-") && (typeIden == null || typeIden.equals(""))){
							Order orderMul = null;
							if(multyQtyMap.size() != 0 && multyQtyMap.get(uniqueID4Map) != null){								
								orderMul = multyQtyMap.get(uniqueID4Map);
								if(orderMul.getTypeIdentifier().contains(itemID.substring(itemID.lastIndexOf("-")+1))){
									errorMessage.append("Duplicate Order.");
									returnOrderMap.put(errorMessage.toString(), order);
								} else {
									orderMul.setQuantity(orderMul.getQuantity() + order.getQuantity());
									orderMul.setTypeIdentifier(orderMul.getTypeIdentifier()+","+itemID.substring(itemID.lastIndexOf("-")+1));								
									multyQtyMap.put(uniqueID4Map, orderMul);
								}								
							} else {
								List<Order> resultList = orderService.searchAsIsOrder("channelOrderID",										
										channelID.substring(0, channelID.lastIndexOf(GlobalConstant.orderUniqueSymbol)), sellerId);
								if(resultList != null){
									orderMul = resultList.get(0);
									if(orderMul.getTypeIdentifier() != null && orderMul.getTypeIdentifier().contains(itemID.substring(itemID.lastIndexOf("-")+1))){
										errorMessage.append("Duplicate Order.");
										returnOrderMap.put(errorMessage.toString(), order);
									} else {
										orderMul.setQuantity(orderMul.getQuantity()+order.getQuantity());
										orderMul.setTypeIdentifier(orderMul.getTypeIdentifier()+","+itemID.substring(itemID.lastIndexOf("-")+1));										
										orderMul.setCustomer(ConverterClass.prepareModel(order).getCustomer());
										orderMul.setOrderTax(ConverterClass.prepareModel(order).getOrderTax());
										orderMul.setOrderPayment(ConverterClass.prepareModel(order).getOrderPayment());
										orderMul.setOrderTimeline(ConverterClass.prepareModel(order).getOrderTimeline());
										orderMul.setOrderReturnOrRTO(ConverterClass.prepareModel(order).getOrderReturnOrRTO());
										orderMul.setSeller(ConverterClass.prepareModel(order).getSeller());
										orderMul.setProductConfig(ConverterClass.prepareModel(order).getProductConfig());
										orderMul.setConsolidatedOrder(ConverterClass.prepareModel(order).getConsolidatedOrder());
										if(orderMul.getTypeIdentifier() == null){
											order.setTypeIdentifier("MultiQty-"+itemID.substring(itemID.lastIndexOf("-")+1));
										}
										multyQtyMap.put(uniqueID4Map, orderMul);
									}
								} else {
									order.setTypeIdentifier("MultiQty-"+itemID.substring(itemID.lastIndexOf("-")+1));
									multyQtyMap.put(uniqueID4Map, ConverterClass.prepareModel(order));
								}
							}							
						} else {
							saveList.add(ConverterClass.prepareModel(order));
						}						
					} else {
						order.setCustomer(customerBean);
						order.setOrderTax(otb);
						log.debug(" Error while saving order : "
								+ order.getChannelOrderID()
								+ " errorMessage : " + errorMessage);
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
				if ((saveList != null && saveList.size() != 0) || multyQtyMap.size() != 0){
					if(multyQtyMap != null && multyQtyMap.size() != 0){
						for(Entry<String, Order> eachOrder : multyQtyMap.entrySet()){
							saveList.add(eachOrder.getValue());
						}
					}
					orderService.addOrder(saveList, sellerId);
				}
			} catch (CustomException ce) {
				returnOrderMap
						.put("Row :1:Note-Some orders("
								+ ce.getLocalMessage()
								+ " ) with valid input "
								+ "failed due to internal server error. Please contact admin.!",
								null);
			}
			errorSet = returnOrderMap.keySet();
			uploadResultXLS(offices, "Unicommerce_Order_Upload", uploadFileName, errorSet,
					path, sellerId, uploadReport);

		} catch (Exception e) {
			log.debug("Inside save contents exception :"
					+ e.getLocalizedMessage());
			e.printStackTrace();
			addErrorUploadReport("Unicommerce_Order_Upload", sellerId,
					uploadReport);
			log.error("Failed! by SellerId : " + sellerId, e);
		}
		log.info("$$$ saveUnicommerceOrderContents starts : SaveMappedFiles $$$");
	}

	public void saveFlipkartOrderContents(MultipartFile file, int sellerId,
			String path, UploadReport uploadReport) throws IOException {
		log.info("$$$ saveFlipkartOrderContents starts : SaveMappedFiles $$$");

		ChannelUploadMapping chanupload = null;
		Map<String, String> columHeaderMap = new LinkedHashMap<String, String>();
		Map<String, Integer> cellIndexMap = new LinkedHashMap<String, Integer>();
		StringBuffer errorMessage = new StringBuffer();
		CustomerBean customerBean = null;
		Partner partner = null;
		Events event = null;
		List<Order> saveList = new ArrayList<Order>();
		Map<String, String> idsList = new HashMap<String, String>();
		Map<String, OrderBean> returnOrderMap = new LinkedHashMap<>();
		OrderBean order = null;
		int noOfEntries = 1;
		OrderTaxBean otb = null;
		HSSFRow entry;
		boolean validaterow = true;
		Set<String> errorSet = new HashSet<String>();
		Map<String, String> duplicateKey = new HashMap<String, String>();
		String uploadFileName = "";
		try {
			chanupload = uploadMappingService.getChannelUploadMapping(
					"Flipkart", "order");
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
			uploadFileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")) + new Date().getTime();
			entry = worksheet.getRow(0);
			for (int cellIndex = 0; cellIndex < entry
					.getPhysicalNumberOfCells(); cellIndex++) {
				/*if (columHeaderMap.containsValue(entry.getCell(cellIndex)
						.toString())) {*/
				if(entry.getCell(cellIndex)!=null)
					cellIndexMap.put(entry.getCell(cellIndex).toString(),
							cellIndex);
				//}

			}
			// SKUList = productService.listProductSKU(sellerId);
			idsList = orderService.listOrderIds("channelOrderID", sellerId);
			log.info(noOfEntries);
			log.debug("After getting no of rows" + noOfEntries);
			for (int rowIndex = 1; rowIndex < noOfEntries; rowIndex++) {
				errorMessage = new StringBuffer("Row :" + (rowIndex) + ":");
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				order = new OrderBean();
				customerBean = new CustomerBean();
				otb = new OrderTaxBean();
				int index = 0;
				Product product = null;
				ProductConfig productConfig = null;
				List<ProductConfig> productConfigs = null;
				TaxCategory taxcat = null;
				String channelorderid = null;
				errorMessage = new StringBuffer("Row :" + (rowIndex) + ":");
				try {

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Sales Channel"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							partner = partnerService.getPartner(
									entry.getCell(index).toString(), sellerId);
							if (partner != null)
								order.setPcName(partner.getPcName());
							else {
								order.setPcName(entry.getCell(index).toString());
								errorMessage.append(" Partner do not exist;");
								validaterow = false;
							}
						} else {
							errorMessage.append(" Sales Channel is null;");
							validaterow = false;
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("Column 'Sales Channel' doesn't exist");
						validaterow = false;
					}

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Channel Order ID"));
						int skuIndex = cellIndexMap.get(columHeaderMap
								.get("SkUCode"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK
								&& entry.getCell(skuIndex) != null
								&& entry.getCell(skuIndex).getCellType() != HSSFCell.CELL_TYPE_BLANK) {

							entry.getCell(index).setCellType(
									HSSFCell.CELL_TYPE_STRING);
							if (partner != null) {
								productConfigs = productService
										.getProductConfig(
												entry.getCell(skuIndex)
														.toString().trim()
														.toString(),
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
											channelorderid = entry.getCell(
													index).toString()
													+ GlobalConstant.orderUniqueSymbol
													+ productConfig
															.getVendorSkuRef();
										} else {
											errorMessage
													.append(" Vendor SKU mapping not present");
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
								.append("The column 'ORDER ID' or 'SKU Code' doesn't exist");
						validaterow = false;
					}

					try {
						if (cellIndexMap.get(columHeaderMap
								.get("Secondary OrderID")) != null) {
							index = cellIndexMap.get(columHeaderMap
									.get("Secondary OrderID"));
							if (entry.getCell(index) != null
									&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
								entry.getCell(index).setCellType(
										HSSFCell.CELL_TYPE_STRING);
								order.setSubOrderID(entry
										.getCell(index)
										.toString()
										.substring(
												entry.getCell(index).toString()
														.indexOf("'") + 1));
								if (channelorderid != null) {
									channelorderid = channelorderid
											+ GlobalConstant.orderUniqueSymbol
											+ order.getSubOrderID();
									
											if (idsList == null || !idsList
													.containsKey(channelorderid)
													&& !duplicateKey
															.containsKey(channelorderid)
													&& productConfig != null) {
										order.setChannelOrderID(channelorderid);
										order.setProductSkuCode(productConfig
												.getProductSkuCode());
										order.setPartnerCommission(productConfig.getCommision());
										duplicateKey.put(channelorderid, "");
									} else {
										errorMessage
												.append(" Channel OrderId is already present ");
										validaterow = false;
									}
								}
							}
						} else {
							errorMessage.append(" ORDER ITEM ID is null ");
							validaterow = false;
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("The column 'ORDER ITEM ID' doesn't exist");
						validaterow = false;
					}

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Order Recieved Date"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							try {
								String date = entry.getCell(index).toString();
								order.setOrderDate(new Date(date));
							} catch (Exception e) {
								errorMessage
										.append(" Order Received Date format is wrong ,enter mm/dd/yyyy,");
								validaterow = false;
							}

						} else {
							errorMessage
									.append(" Order Recieved Date is null;");
							validaterow = false;
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("The column 'Ordered On' doesn't exist");
						validaterow = false;
					}

					/*
					 * try { index =
					 * cellIndexMap.get(columHeaderMap.get("SkUCode")); if
					 * (entry.getCell(index) != null &&
					 * entry.getCell(index).getCellType() !=
					 * HSSFCell.CELL_TYPE_BLANK) { if
					 * (SKUList!=null&&SKUList.contains(entry.getCell(index)
					 * .toString())) {
					 * order.setProductSkuCode(entry.getCell(index)
					 * .toString()); } else { errorMessage
					 * .append(" Product SKU does not exist;"); validaterow =
					 * false; } } else {
					 * errorMessage.append(" Product SKU is null;"); validaterow
					 * = false; } } catch (NullPointerException e) {
					 * errorMessage
					 * .append("The column 'SKU Code' doesn't exist");
					 * validaterow = false; }
					 */

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer Name"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							customerBean.setCustomerName(entry.getCell(index)
									.toString());
						}
					} catch (NullPointerException e) {

					}

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Payment Type"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							if (entry.getCell(index).toString().toUpperCase()
									.contains("PREPAID")) {
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
								.append("The column 'Payment Type' doesn't exist");
						validaterow = false;
					}

					if (cellIndexMap.get(columHeaderMap.get("AWB No")) != null) {
						index = cellIndexMap.get(columHeaderMap.get("AWB No"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							entry.getCell(index).setCellType(
									HSSFCell.CELL_TYPE_STRING);
							order.setAwbNum(entry.getCell(index).toString());
						}
					}

					try {
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
					}

					if (cellIndexMap.get(columHeaderMap.get("PIreferenceNo")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("PIreferenceNo"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							order.setPIreferenceNo(entry.getCell(index)
									.toString());
						}
					}
					if (cellIndexMap
							.get(columHeaderMap.get("Logistic Partner")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Logistic Partner"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							order.setLogisticPartner(entry.getCell(index)
									.toString());
						}
					}
					if (cellIndexMap.get(columHeaderMap.get("Order MRP")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Order MRP"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							try {
								order.setOrderMRP(Double.parseDouble(entry
										.getCell(index).toString()));
							} catch (NumberFormatException e) {
								log.error("Failed! by SellerId : " + sellerId,
										e);
								errorMessage
										.append(" Order MRP should be a number ");
							}
						}
					}

					try {
						index = cellIndexMap
								.get(columHeaderMap.get("Order SP"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							try {
								order.setOrderSP(Double.parseDouble(entry
										.getCell(index).toString()));
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
								.append("The column 'Invoice Amount' doesn't exist");
						validaterow = false;
					}

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Order Shipped Date"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							try {
								String date = entry.getCell(index).toString();
								order.setShippedDate(new Date(date));
							} catch (Exception e) {
								errorMessage
										.append(" Shipped Date formate is wrong ,enter mm/dd/yyyy,");
								validaterow = false;
							}
						} else {
							errorMessage.append(" Shipping Date is null ");
							validaterow = false;
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("The column 'Ready to Ship by date' doesn't exist");
						validaterow = false;
					}

					try {
						index = cellIndexMap
								.get(columHeaderMap.get("Quantity"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							try {
								if ((int) Float.parseFloat(entry.getCell(index)
										.toString()) != 0) {
									order.setQuantity((int) Float
											.parseFloat(entry.getCell(index)
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
								.append("The column 'Quantity' doesn't exist");
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
									if (entry.getCell(index) != null
											&& entry.getCell(index)
													.getCellType() != HSSFCell.CELL_TYPE_BLANK) {

										try {
											order.setGrossNetRate(Double
													.parseDouble(entry.getCell(
															index).toString()));
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
									if (entry.getCell(index) != null
											&& entry.getCell(index)
													.getCellType() != HSSFCell.CELL_TYPE_BLANK) {
										try {
											order.setGrossNetRate(Double
													.parseDouble(entry.getCell(
															index).toString()));
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
					if (cellIndexMap.get(columHeaderMap.get("Customer Email")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer Email"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {

							customerBean.setCustomerEmail(entry.getCell(index)
									.toString());
						}
					}
					if (cellIndexMap.get(columHeaderMap
							.get("Customer Phone No")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer Phone No"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							entry.getCell(index).setCellType(
									HSSFCell.CELL_TYPE_STRING);
							if (entry.getCell(index).toString().length() == 10
									&& !entry.getCell(index).toString()
											.contains("9999999999")) {
								if (entry.getCell(index).toString()
										.matches("[0-9]+")
										&& entry.getCell(index).toString()
												.length() > 2) {
									customerBean.setCustomerPhnNo(entry
											.getCell(index).toString());
								}
							}
						}
					}
					if (cellIndexMap.get(columHeaderMap.get("Customer City")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer City"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							customerBean.setCustomerCity(entry.getCell(index)
									.toString());
						}
					}
					if (cellIndexMap.get(columHeaderMap.get("Customer Email")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer Email"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							customerBean.setCustomerAddress(entry
									.getCell(index).toString());
						}
					}
					try {
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
								.append("The column 'PIN Code' doesn't exist");
						validaterow = false;
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

					if (cellIndexMap.get(columHeaderMap.get("Seller Note")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Seller Note"));
						if (entry.getCell(index) != null
								&& StringUtils.isNotBlank(entry.getCell(index)
										.toString())) {
							order.setSellerNote(entry.getCell(index).toString());

						}
					}

					// Pre save to generate id for use in hierarchy
					if (validaterow) {
						order.setOrderFileName(uploadFileName);
						order.setCustomer(customerBean);
						order.setOrderTax(otb);
						/*
						 * orderService.addOrder(ConverterClass.prepareModel(order
						 * ), sellerId);
						 */
						saveList.add(ConverterClass.prepareModel(order));
					} else {
						order.setCustomer(customerBean);
						order.setOrderTax(otb);
						log.debug(" Error while saving order : "
								+ order.getChannelOrderID()
								+ " errorMessage : " + errorMessage);
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
			uploadResultXLS(offices, "Flipkart_Order_Upload", uploadFileName, errorSet, path,
					sellerId, uploadReport);

		} catch (Exception e) {
			log.debug("Inside save contents exception :"
					+ e.getLocalizedMessage());
			e.printStackTrace();
			addErrorUploadReport("Flipkart_Order_Upload", sellerId,
					uploadReport);
			log.error("Failed! by SellerId : " + sellerId, e);
		}
	}

	public void saveAmzonOrderContents(MultipartFile file, int sellerId,
			String path, UploadReport uploadReport) throws IOException {
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
		HSSFRow entry;
		boolean validaterow = true;
		Set<String> errorSet = new HashSet<String>();
		Map<String, String> duplicateKey = new HashMap<String, String>();
		String uploadFileName = "";
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
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());

			HSSFSheet worksheet = offices.getSheetAt(0);
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			uploadFileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")) + new Date().getTime();
			entry = worksheet.getRow(0);
			for (int cellIndex = 0; cellIndex < entry
					.getPhysicalNumberOfCells(); cellIndex++) {
				/*if (columHeaderMap.containsValue(entry.getCell(cellIndex)
						.toString())) {*/
				if(entry.getCell(cellIndex)!=null)
					cellIndexMap.put(entry.getCell(cellIndex).toString(),
							cellIndex);
				//}

			}
			log.info("cellIndexMap for Amazon Order : " + cellIndexMap);
			// SKUList = productService.listProductSKU(sellerId);
			idsList = orderService.listOrderIds("channelOrderID", sellerId);
			log.info(noOfEntries);
			log.debug("After getting no of rows" + noOfEntries);
			for (int rowIndex = 1; rowIndex < noOfEntries; rowIndex++) {
				errorMessage = new StringBuffer("Row :" + (rowIndex) + ":");
				entry = worksheet.getRow(rowIndex);
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
						index = cellIndexMap.get(columHeaderMap
								.get("Sales Channel"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							partner = partnerService.getPartner(
									entry.getCell(index).toString(), sellerId);
							if (partner != null)
								order.setPcName(partner.getPcName());
							else {
								order.setPcName(entry.getCell(index).toString());
								errorMessage.append(" Partner do not exist;");
								validaterow = false;
							}
						} else {
							errorMessage.append(" Partner Name is null;");
							validaterow = false;
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("The column 'Sales Channel' doesn't exist");
						validaterow = false;
					}

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Channel Order ID"));
						int skuIndex = cellIndexMap.get(columHeaderMap
								.get("SkUCode"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK
								&& entry.getCell(skuIndex) != null
								&& entry.getCell(skuIndex).getCellType() != HSSFCell.CELL_TYPE_BLANK) {

							entry.getCell(index).setCellType(
									HSSFCell.CELL_TYPE_STRING);
							entry.getCell(skuIndex).setCellType(
									HSSFCell.CELL_TYPE_STRING);
							if (partner != null) {
								productConfigs = productService
										.getProductConfig(
												entry.getCell(skuIndex)
														.toString().trim()
														.toUpperCase(),
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
											channelorderid = entry.getCell(
													index).toString()
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
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							entry.getCell(index).setCellType(HSSFCell.CELL_TYPE_STRING);
							itemID = entry.getCell(index).toString();
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
					
					/*if (cellIndexMap.get(columHeaderMap
							.get("Secondary OrderID")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Secondary OrderID"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							entry.getCell(index).setCellType(
									HSSFCell.CELL_TYPE_STRING);
							order.setSubOrderID(entry.getCell(index).toString()
									.substring(
											entry.getCell(index).toString()
													.indexOf("'") + 1));
							
							if (channelorderid != null){
								channelorderid = channelorderid
										+ GlobalConstant.orderUniqueSymbol
										+ order.getSubOrderID();
									if( (idsList == null || !idsList
											.containsKey(channelorderid)
											&& !duplicateKey
													.containsKey(channelorderid)
											&& productConfig != null)) {
								order.setChannelOrderID(channelorderid);
								order.setProductSkuCode(productConfig
										.getProductSkuCode());
								order.setPartnerCommission(productConfig.getCommision());
								duplicateKey
										.put(channelorderid, "");
							} else {
								errorMessage
										.append(" Channel OrderId is already present ");
								validaterow = false;
							}
							}
						}
					}*/

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Order Recieved Date"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							try {
								String dateStr = entry.getCell(index)
										.toString();
								String date = "";
								if (dateStr.contains("T")) {
									date = entry
											.getCell(index)
											.toString()
											.substring(
													0,
													entry.getCell(index)
															.toString()
															.indexOf("T"));
								} else {
									date = entry.getCell(index).toString();
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
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							log.debug(" Email from sheet ************ "
									+ rowIndex + ":"
									+ entry.getCell(index).toString());
							customerBean.setCustomerEmail(entry.getCell(index)
									.toString());
						}
					}
					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer Name"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							customerBean.setCustomerName(entry.getCell(index)
									.toString());
						}
					} catch (NullPointerException e) {

					}
					if (cellIndexMap.get(columHeaderMap
							.get("Customer Phone No")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer Phone No"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							entry.getCell(index).setCellType(
									HSSFCell.CELL_TYPE_STRING);
							if (entry.getCell(index).toString().length() == 10
									&& !entry.getCell(index).toString()
											.contains("9999999999")) {
								if (entry.getCell(index).toString()
										.matches("[0-9]+")
										&& entry.getCell(index).toString()
												.length() > 2) {
									customerBean.setCustomerPhnNo(entry
											.getCell(index).toString());
								}
							}
						}
					}

					/*
					 * try { index =
					 * cellIndexMap.get(columHeaderMap.get("SkUCode")); if
					 * (entry.getCell(index) != null &&
					 * entry.getCell(index).getCellType() !=
					 * HSSFCell.CELL_TYPE_BLANK) { if
					 * (SKUList!=null&&SKUList.contains(entry.getCell(index)
					 * .toString())) {
					 * order.setProductSkuCode(entry.getCell(index)
					 * .toString()); } else { errorMessage
					 * .append(" Product SKU does not exist;"); validaterow =
					 * false; } } else {
					 * errorMessage.append(" Product SKU is null;"); validaterow
					 * = false; } } catch (NullPointerException e) {
					 * errorMessage.append("The column 'sku' doesn't exist");
					 * validaterow = false; }
					 */

					try {
						index = cellIndexMap
								.get(columHeaderMap.get("Quantity"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							try {
								if ((int) Float.parseFloat(entry.getCell(index)
										.toString()) != 0) {
									order.setQuantity((int) Float
											.parseFloat(entry.getCell(index)
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
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK
								&& entry.getCell(index1) != null
								&& entry.getCell(index1).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							try {
								order.setOrderSP(Double.parseDouble(entry
										.getCell(index).toString())
										+ Double.parseDouble(entry.getCell(
												index1).toString()));
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

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer Address 1"));
						int index1 = cellIndexMap.get(columHeaderMap
								.get("Customer Address 2"));
						int index2 = cellIndexMap.get(columHeaderMap
								.get("Customer Address 3"));
						if (entry.getCell(index) != null
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

					if (cellIndexMap.get(columHeaderMap.get("Customer City")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer City"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							customerBean.setCustomerCity(entry.getCell(index)
									.toString());
						}
					}

					try {
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
					}

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Payment Type"));
						if (entry.getCell(index) != null) {
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
					}

					if (cellIndexMap
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
					}

					try {
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
					}

					if (cellIndexMap.get(columHeaderMap.get("AWB No")) != null) {
						index = cellIndexMap.get(columHeaderMap.get("AWB No"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							entry.getCell(index).setCellType(
									HSSFCell.CELL_TYPE_STRING);
							order.setAwbNum(entry.getCell(index).toString());
						}
					}

					if (cellIndexMap.get(columHeaderMap.get("PIreferenceNo")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("PIreferenceNo"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							order.setPIreferenceNo(entry.getCell(index)
									.toString());
						}
					}

					if (cellIndexMap.get(columHeaderMap.get("Order MRP")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Order MRP"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							try {
								order.setOrderMRP(Double.parseDouble(entry
										.getCell(index).toString()));
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
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							try {
								String dateStr = entry.getCell(index)
										.toString();
								String date = "";
								if (dateStr.contains("T")) {
									date = entry
											.getCell(index)
											.toString()
											.substring(
													0,
													entry.getCell(index)
															.toString()
															.indexOf("T"));
								} else {
									date = entry.getCell(index).toString();
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
									if (entry.getCell(index) != null
											&& entry.getCell(index)
													.getCellType() != HSSFCell.CELL_TYPE_BLANK) {

										try {
											order.setGrossNetRate(Double
													.parseDouble(entry.getCell(
															index).toString()));
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
									if (entry.getCell(index) != null
											&& entry.getCell(index)
													.getCellType() != HSSFCell.CELL_TYPE_BLANK) {
										try {
											order.setGrossNetRate(Double
													.parseDouble(entry.getCell(
															index).toString()));
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

					if (cellIndexMap.get(columHeaderMap.get("Seller Note")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Seller Note"));
						if (entry.getCell(index) != null
								&& StringUtils.isNotBlank(entry.getCell(index)
										.toString())) {
							order.setSellerNote(entry.getCell(index).toString());

						}
					}

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
						if(cellIndexMap.get("ship-promotion-id")!=null
								&&entry.getCell(cellIndexMap.get("ship-promotion-id")) != null
								&& StringUtils.isNotBlank(entry.getCell(cellIndexMap.get("ship-promotion-id"))
										.toString()))
						{
							log.info("Promotion ID available for this order.");
						}
						else
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
			uploadResultXLS(offices, "Amazon_Order_Upload", uploadFileName, errorSet, path,
					sellerId, uploadReport);

		} catch (Exception e) {
			log.debug("Inside save contents exception :"
					+ e.getLocalizedMessage());
			e.printStackTrace();
			addErrorUploadReport("Amazon_Order_Upload", sellerId, uploadReport);
			log.error("Failed! by SellerId : " + sellerId, e);
		}
	}

	public void saveSnapdealOrderContents(MultipartFile file, int sellerId,
			String path, UploadReport uploadReport) throws IOException {
		log.info("$$$ saveSnapdealOrderContents starts : SaveMappedFiles $$$");

		ChannelUploadMapping chanupload = null;
		Map<String, String> columHeaderMap = new LinkedHashMap<String, String>();
		Map<String, Integer> cellIndexMap = new LinkedHashMap<String, Integer>();

		StringBuffer errorMessage = new StringBuffer();
		CustomerBean customerBean = null;
		Partner partner = null;
		Events event = null;
		List<Order> saveList = new ArrayList<Order>();
		Map<String, String> idsList = new HashMap<String, String>();
		Map<String, OrderBean> returnOrderMap = new LinkedHashMap<>();
		OrderBean order = null;
		int noOfEntries = 1;
		OrderTaxBean otb = null;
		HSSFRow entry;
		boolean validaterow = true;
		Set<String> errorSet = new HashSet<String>();
		Map<String, String> duplicateKey = new HashMap<String, String>();
		String uploadFileName = "";
		try {
			chanupload = uploadMappingService.getChannelUploadMapping(
					"Snapdeal", "order");
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
			uploadFileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")) + new Date().getTime();
			entry = worksheet.getRow(0);
			for (int cellIndex = 0; cellIndex < entry
					.getPhysicalNumberOfCells(); cellIndex++) {
				if (entry.getCell(cellIndex)!=null&&columHeaderMap.containsValue(entry.getCell(cellIndex)
						.toString())) {
					cellIndexMap.put(entry.getCell(cellIndex).toString(),
							cellIndex);
				}

			}
			// SKUList = productService.listProductSKU(sellerId);
			idsList = orderService.listOrderIds("channelOrderID", sellerId);
			log.info(noOfEntries);
			log.debug("After getting no of rows" + noOfEntries);
			for (int rowIndex = 1; rowIndex < noOfEntries; rowIndex++) {
				errorMessage = new StringBuffer("Row :" + (rowIndex) + ":");
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				order = new OrderBean();
				customerBean = new CustomerBean();
				otb = new OrderTaxBean();
				int index = 0;
				ProductConfig productConfig = null;
				List<ProductConfig> productConfigs = null;
				String channelorderid = "";
				Product product = null;
				TaxCategory taxcat = null;
				errorMessage = new StringBuffer("Row :" + (rowIndex) + ":");
				try {

					if (cellIndexMap
							.get(columHeaderMap.get("Logistic Partner")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Logistic Partner"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							order.setLogisticPartner(entry.getCell(index)
									.toString());
						}
					}
					if (cellIndexMap.get(columHeaderMap.get("PIreferenceNo")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("PIreferenceNo"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							order.setPIreferenceNo(entry.getCell(index)
									.toString());
						}
					}

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Sales Channel"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							partner = partnerService.getPartner(
									entry.getCell(index).toString(), sellerId);
							if (partner != null)
								order.setPcName(partner.getPcName());
							else {
								order.setPcName(entry.getCell(index).toString());
								errorMessage.append(" Partner do not exist;");
								validaterow = false;
							}
						} else {
							errorMessage.append(" Partner Name is null;");
							validaterow = false;
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("The column 'Sales Channel' doesn't exist");
						validaterow = false;
					}

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Channel Order ID"));
						int skuIndex = cellIndexMap.get(columHeaderMap
								.get("SkUCode"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK
								&& entry.getCell(skuIndex) != null
								&& entry.getCell(skuIndex).getCellType() != HSSFCell.CELL_TYPE_BLANK) {

							entry.getCell(index).setCellType(
									HSSFCell.CELL_TYPE_STRING);
							entry.getCell(skuIndex).setCellType(
									HSSFCell.CELL_TYPE_STRING);
							if (partner != null) {
								productConfigs = productService
										.getProductConfig(
												entry.getCell(skuIndex)
														.toString().trim()
														.toUpperCase(),
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
											channelorderid = entry.getCell(
													index).toString()
													+ GlobalConstant.orderUniqueSymbol
													+ productConfig
															.getVendorSkuRef();
										} else {
											errorMessage
													.append(" Vendor SKU code not mapped.");
											validaterow = false;
										}
										/*if ((channelorderid != null)
												&& (idsList == null || !idsList
														.containsKey(channelorderid)
														&& !duplicateKey
																.containsKey(channelorderid)
														&& productConfig != null)) {
											order.setChannelOrderID(channelorderid);
											order.setProductSkuCode(productConfig
													.getProductSkuCode());
											order.setPartnerCommission(productConfig.getCommision());
											duplicateKey
													.put(channelorderid, "");
										} else {
											errorMessage
													.append(" Channel OrderId is already present ");
											validaterow = false;
										}*/
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

					/*
					 * try { index =
					 * cellIndexMap.get(columHeaderMap.get("SkUCode")); if
					 * (entry.getCell(index) != null &&
					 * entry.getCell(index).getCellType() !=
					 * HSSFCell.CELL_TYPE_BLANK) { if
					 * (SKUList!=null&&SKUList.contains(entry.getCell(index)
					 * .toString())) {
					 * order.setProductSkuCode(entry.getCell(index)
					 * .toString()); } else { errorMessage
					 * .append(" Product SKU does not exist;"); validaterow =
					 * false; } } else {
					 * errorMessage.append(" Product SKU is null;"); validaterow
					 * = false; } } catch (NullPointerException e) {
					 * errorMessage
					 * .append("The column 'SKU Code' doesn't exist");
					 * validaterow = false; }
					 */

					if (cellIndexMap.get(columHeaderMap.get("AWB No")) != null) {
						index = cellIndexMap.get(columHeaderMap.get("AWB No"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							entry.getCell(index).setCellType(
									HSSFCell.CELL_TYPE_STRING);
							order.setAwbNum(entry.getCell(index).toString());
						}
					}

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Order Recieved Date"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							try {	
								String date = entry.getCell(index).toString();
								order.setOrderDate(format1.parse(date));
							
						} catch (Exception e) {
							errorMessage
									.append(" Order Received Date format is wrong ,expected text format yyyy-MM-dd HH:mm:ss");
							validaterow = false;
						}

						} else {
							errorMessage
									.append(" Order Recieved Date is null;");
							validaterow = false;
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("The column 'Order Created Date' doesn't exist");
						validaterow = false;
					}
					
					if(channelorderid != null&&order.getOrderDate()!=null){
						String itemID=format.format(order.getOrderDate());
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
								.get("Customer Name"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							customerBean.setCustomerName(entry.getCell(index)
									.toString());
						}
					} catch (NullPointerException e) {

					}

					if (cellIndexMap.get(columHeaderMap.get("Customer City")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer City"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							customerBean.setCustomerCity(entry.getCell(index)
									.toString());
						}
					}

					try {
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
								.append("The column 'Pin Code' doesn't exist");
						validaterow = false;
					}

					try {
						index = cellIndexMap
								.get(columHeaderMap.get("Order SP"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							try {
								order.setOrderSP(Double.parseDouble(entry
										.getCell(index).toString()));
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
								.append("The column 'Selling Price Per Item' doesn't exist");
						validaterow = false;
					}

					if (cellIndexMap.get(columHeaderMap.get("Order MRP")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Order MRP"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							try {
								order.setOrderMRP(Double.parseDouble(entry
										.getCell(index).toString()));
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
								.get("InvoiceID"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							entry.getCell(index).setCellType(
									HSSFCell.CELL_TYPE_STRING);
							order.setInvoiceID(entry.getCell(index).toString());
						} else {
							order.setInvoiceID(entry.getCell(index).toString());
							errorMessage.append(" Invoice ID is null;");
							validaterow = false;
						}
					} catch (Exception e) {
						errorMessage
								.append("The column 'Invoice Code' doesn't exist");
						validaterow = false;
					}

					if (cellIndexMap.get(columHeaderMap
							.get("Customer Phone No")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer Phone No"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							entry.getCell(index).setCellType(
									HSSFCell.CELL_TYPE_STRING);
							if (entry.getCell(index).toString().length() == 10
									&& !entry.getCell(index).toString()
											.contains("9999999999")) {
								if (entry.getCell(index).toString()
										.matches("[0-9]+")
										&& entry.getCell(index).toString()
												.length() > 2) {
									customerBean.setCustomerPhnNo(entry
											.getCell(index).toString());
								}
							}
						}
					}
					if (cellIndexMap.get(columHeaderMap.get("Customer Email")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer Email"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							log.debug(" Email from sheet ************ "
									+ rowIndex + ":"
									+ entry.getCell(index).toString());
							customerBean.setCustomerEmail(entry.getCell(index)
									.toString());
						}
					}

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Payment Type"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							if (entry.getCell(index).toString()
									.equalsIgnoreCase("COD"))
								order.setPaymentType(entry.getCell(index)
										.toString());
							else {
								order.setPaymentType("Others");
							}
						} else {
							errorMessage.append(" Payment type is null");
							validaterow = false;
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("The column 'Payment Mode' doesn't exist");
						validaterow = false;
					}

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Order Shipped Date"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							System.out.println(entry.getCell(index).toString());
							entry.getCell(index).setCellType(HSSFCell.CELL_TYPE_STRING);
							try {								
									String date = entry.getCell(index)
											.toString();							
									order.setShippedDate(format.parse(date));
																	
							} catch (Exception e) {
								errorMessage
										.append(" Shipped Date formate is wrong ,");
								validaterow = false;
							}
						} else {
							errorMessage.append(" Shipping Date is null ");
							validaterow = false;
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("The column 'Handover Date' doesn't exist");
						validaterow = false;
					}

					if (cellIndexMap
							.get(columHeaderMap.get("Customer Address")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer Address"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							customerBean.setCustomerAddress(entry
									.getCell(index).toString());
						}
					}
					try {
						index = cellIndexMap
								.get(columHeaderMap.get("Quantity"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							try {
								if ((int) Float.parseFloat(entry.getCell(index)
										.toString()) != 0) {
									order.setQuantity((int) Float
											.parseFloat(entry.getCell(index)
													.toString()));
								} else {
									order.setQuantity(1);
								}
							} catch (NumberFormatException e) {
								order.setQuantity(1);
							}
						} else {
							order.setQuantity(1);
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("The column 'Quantity' doesn't exist");
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
									if (entry.getCell(index) != null
											&& entry.getCell(index)
													.getCellType() != HSSFCell.CELL_TYPE_BLANK) {

										try {
											order.setGrossNetRate(Double
													.parseDouble(entry.getCell(
															index).toString()));
										} catch (NumberFormatException e) {
											log.error("Failed! by SellerId : "
													+ sellerId, e);
											errorMessage
													.append(" Net Rate should be number ");
											validaterow = false;
										}
									} else {
										errorMessage
												.append(" Net Rate is null and ongoing event on the order with fixed price.");
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
									if (entry.getCell(index) != null
											&& entry.getCell(index)
													.getCellType() != HSSFCell.CELL_TYPE_BLANK) {
										try {
											order.setGrossNetRate(Double
													.parseDouble(entry.getCell(
															index).toString()));
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

					if (cellIndexMap.get(columHeaderMap.get("Seller Note")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Seller Note"));
						if (entry.getCell(index) != null
								&& StringUtils.isNotBlank(entry.getCell(index)
										.toString())) {
							order.setSellerNote(entry.getCell(index).toString());

						}
					}

					// Pre save to generate id for use in hierarchy
					if (validaterow) {
						order.setOrderFileName(uploadFileName);
						order.setCustomer(customerBean);
						order.setOrderTax(otb);
						/*
						 * orderService.addOrder(ConverterClass.prepareModel(order
						 * ), sellerId);
						 */
						System.out.println(" Adding order to save list : "
								+ order.getChannelOrderID());
						saveList.add(ConverterClass.prepareModel(order));
					} else {
						order.setCustomer(customerBean);
						order.setOrderTax(otb);
						log.debug(" Error while saving order : "
								+ order.getChannelOrderID()
								+ " errorMessage : " + errorMessage);
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
			uploadResultXLS(offices, "Snapdeal_Order_Upload", uploadFileName, errorSet, path,
					sellerId, uploadReport);

		} catch (Exception e) {
			log.debug("Inside save contents exception :"
					+ e.getLocalizedMessage());
			e.printStackTrace();
			addErrorUploadReport("Snapdeal_Order_Upload", sellerId,
					uploadReport);
			log.error("Failed! by SellerId : " + sellerId, e);
		}
	}

	public void saveFlipkartPaymentContents(MultipartFile file, int sellerId,
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
		List<ManualCharges> manualChargesList = new ArrayList<ManualCharges>();
		ManualCharges newmanualCharge = null;
		boolean generatePaymentUpload = false;
		String uploadPaymentId = null;
		Map<String, String> duplicateKey = new HashMap<String, String>();
		String uploadFileName = "";
		try {
			chanupload = uploadMappingService.getChannelUploadMapping(
					"Flipkart", "Payment");
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
			uploadFileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")) + new Date().getTime();
			entry = worksheet.getRow(0);
			for (int cellIndex = 0; cellIndex < entry
					.getPhysicalNumberOfCells(); cellIndex++) {
				/*
				 * if (columHeaderMap.containsValue(entry.getCell(cellIndex)
				 * .toString())) {
				 * cellIndexMap.put(entry.getCell(cellIndex).toString(),
				 * cellIndex); }
				 * 
				 */
				if(entry.getCell(cellIndex)!=null)
					cellIndexMap.put(entry.getCell(cellIndex).toString(),
						cellIndex);
			}

			try {
				indexfulfilmentType = cellIndexMap.get(columHeaderMap
						.get("Fulfilment Type"));
				for (int rowIndex = 1; rowIndex < noOfEntries; rowIndex++) {
					errorMessage = new StringBuffer("Row :" + (rowIndex) + ":");
					entry = worksheet.getRow(rowIndex);
					validaterow = true;
					int index = 0;
					String channelheader = null;
					channelOrderId = null;
					boolean combo=false;
					
					// errorMessage = new StringBuffer("Row :" + (rowIndex) +
					// ":");
					// Code for fetching right colum
					orderPayment = new OrderPayment();
					order = null;
					double amount = 0;
					List<Order> onj=null;

					try {

						if (entry.getCell(indexfulfilmentType) != null
								&& entry.getCell(indexfulfilmentType)
										.toString().equalsIgnoreCase("NON-FA")) {

							try {
								channelheader = columHeaderMap
										.get("ChannelOrderId");
								index = cellIndexMap.get(channelheader);
								int skuIndex = cellIndexMap.get(columHeaderMap
										.get("Seller SKU"));
								entry.getCell(index).setCellType(
										HSSFCell.CELL_TYPE_STRING);

								int secOrderIndex = cellIndexMap
										.get(columHeaderMap
												.get("Secondary OrderID"));
								entry.getCell(secOrderIndex).setCellType(
										HSSFCell.CELL_TYPE_STRING);
								String itemId = "";
								if (entry.getCell(secOrderIndex) != null
										&& StringUtils.isNotBlank(entry
												.getCell(secOrderIndex)
												.toString())) {
									itemId = entry
											.getCell(secOrderIndex)
											.toString()
											.substring(
													entry.getCell(secOrderIndex)
															.toString()
															.indexOf(":") + 1);
								} else {
									errorMessage
											.append(" Order item ID not present ");
									validaterow = false;
								}

								channelOrderId = entry.getCell(index)
										.toString()
										+ GlobalConstant.orderUniqueSymbol
										+ entry.getCell(skuIndex).toString()
												.trim().toUpperCase()
										+ GlobalConstant.orderUniqueSymbol
										+ itemId;

								onj = orderService.searchAsIsOrder(
										"channelOrderID", channelOrderId,
										sellerId);
								
								if (onj == null) {
									errorMessage
											.append("No Orders With Channel OrderId.");
									validaterow = false;
								} else {
									if (onj.size() == 1
											&& onj.get(0).getChannelOrderID()
													.equals(channelOrderId)) {
										channelOrderId = onj.get(0)
												.getChannelOrderID();
										
									} 
									
									else if (onj.size() > 1&& onj.get(0).getChannelOrderID()
											.contains(entry.getCell(index).toString()))
									{
											if(onj.get(0).getTypeIdentifier()!=null&&
												onj.get(0).getTypeIdentifier().contains(entry.getCell(index)
														.toString()))
										{
											combo=true;
										}
									}
									else {
										errorMessage
												.append("Multiple Orders With Channel Order ID.");
										validaterow = false;
									}
								}

							} catch (NullPointerException e) {
								errorMessage
										.append("This Column 'Order item ID' or 'SKU' doesn't exist");
								validaterow = false;
							}
							try {
								index = cellIndexMap.get(columHeaderMap
										.get("Payment Date"));
								if (entry.getCell(index) != null
										&& HSSFDateUtil
												.isCellDateFormatted(entry
														.getCell(index))) {
									String date = entry.getCell(index)
											.toString();
									orderPayment
											.setDateofPayment(new Date(date));
								} else {
									errorMessage
											.append(" Payment Date format is wrong or null");
									validaterow = false;
								}
							} catch (NullPointerException e) {
								errorMessage
										.append("This Column 'Settlement Date' doesn't exist");
								validaterow = false;
							}

							try {
								index = cellIndexMap.get(columHeaderMap
										.get("Recieved Amount"));
								if (entry.getCell(index) != null
										&& StringUtils.isNotBlank(entry
												.getCell(index).toString())) {
									try {
										amount = entry.getCell(index)
												.getNumericCellValue();
										if (amount > 0) {
											orderPayment
													.setPositiveAmount(amount);
											/*
											 * totalpositive = totalpositive +
											 * amount;
											 */
										} else {
											orderPayment.setNegativeAmount(Math
													.abs(amount));
											/*
											 * totalnegative = totalnegative +
											 * Math.abs(amount);
											 */
										}
									} catch (Exception e) {
										log.error("Failed by seller "
												+ sellerId, e);
										errorMessage
												.append(" Payment Amount cell is corrupted");
										validaterow = false;

									}
								} else {
									errorMessage
											.append("Amount format is wrong or null.");
									validaterow = false;
								}
							} catch (NullPointerException e) {
								errorMessage
										.append("This Column 'Settlement Value (Rs.)' doesn't exist");
								validaterow = false;
							}

							try {
								index = cellIndexMap.get(columHeaderMap
										.get("Seller SKU"));
								if (entry.getCell(index) != null
										&& StringUtils.isNotBlank(entry
												.getCell(index).toString())) {
									skucode = entry.getCell(index).toString()
											.trim().toUpperCase();
								}
							} catch (NullPointerException e) {
								errorMessage
										.append("This Column 'Seller SKU' doesn't exist");
							}

							if (validaterow) {
								// Code to set extra variables from flipkart
								// payments sheet
								List<PaymentVariables> payvarlist = new ArrayList<PaymentVariables>();
								for (String s : GlobalConstant.flipkartpaymentvariablesList) {
									PaymentVariables payvar = new PaymentVariables();
									try {
										if (cellIndexMap.containsKey(s)) {
											index = cellIndexMap.get(s);
											if (entry.getCell(index) != null
													&& StringUtils
															.isNotBlank(entry
																	.getCell(
																			index)
																	.toString())) {
												payvar.setPayKey(s);
												payvar.setPayValue(entry
														.getCell(index)
														.toString());
												payvarlist.add(payvar);
											}

										}
									} catch (Exception e) {
										log.warn(
												"Failed by seller ID : "
														+ sellerId
														+ " FlipkatyPayment : Error in reading field "
														+ s, e);
									}

								}

								if (amount > 0) {
									totalpositive = totalpositive + amount;
								} else {
									totalnegative = totalnegative
											+ Math.abs(amount);
								}
								orderPayment.setPaymentFileName(uploadFileName);
								if(combo)
								{
									
									 if(onj!=null&&onj.size()>0)
									 {
										 if(amount>0)
										 {
										 orderPayment.setPositiveAmount(orderPayment.getPositiveAmount()/onj.size());
										 }
										 else{
											 orderPayment.setNegativeAmount(orderPayment.getNegativeAmount()/onj.size());
										 }
								 for(Order ord:onj)
									 {
										
										 order = orderService.addOrderPayment(skucode,
													ord.getChannelOrderID(), orderPayment, sellerId);
									 }
									 }
								}
								else{
									orderPayment.setPaymentVar(payvarlist);
									
								order = orderService.addOrderPayment(skucode,
										channelOrderId, orderPayment, sellerId);
								}
							} else {
								errorSet.add(errorMessage.toString());

							}
							if (order != null & validaterow) {								
								if (!duplicateKey.containsKey(channelOrderId)) {
									// order.getPaymentUpload().add(paymentUpload);
									// paymentUpload.getOrders().add(order);
									generatePaymentUpload = true;
									duplicateKey.put(channelOrderId,
											channelOrderId);
								}

							}

						} else if (entry.getCell(indexfulfilmentType) != null
								&& entry.getCell(indexfulfilmentType)
										.toString().equalsIgnoreCase("null")) {
							/*
							 * try { index = cellIndexMap.get(columHeaderMap
							 * .get("Seller SKU")); if (entry.getCell(index) !=
							 * null && StringUtils.isNotBlank(entry
							 * .getCell(index).toString())) { skucode =
							 * entry.getCell(index).toString(); } } catch
							 * (NullPointerException e) { errorMessage
							 * .append("This Column 'Seller SKU' doesn't exist"
							 * ); } if (skucode != null &&
							 * skucode.equalsIgnoreCase("null")) {
							 */
							newmanualCharge = new ManualCharges();
							try {
								index = cellIndexMap.get(columHeaderMap
										.get("Payment Date"));
								if (entry.getCell(index) != null
										&& HSSFDateUtil
												.isCellDateFormatted(entry
														.getCell(index))) {
									String date = entry.getCell(index)
											.toString();
									newmanualCharge.setDateOfPayment(new Date(
											date));
								} else {
									errorMessage
											.append(" Payment Date format is wrong or null");
									validaterow = false;
								}
							} catch (NullPointerException e) {
								errorMessage
										.append("This Column 'Settlement Date' doesn't exist");
								validaterow = false;
							}

							try {
								index = cellIndexMap.get(columHeaderMap
										.get("Particulars"));
								if (entry.getCell(index) != null
										&& StringUtils.isNotBlank(entry
												.getCell(index).toString())) {
									newmanualCharge.setParticular(entry
											.getCell(index).toString());
								}
							} catch (NullPointerException e) {
								errorMessage
										.append("This Column 'Order Type' doesn't exist");
							}

							try {
								index = cellIndexMap.get(columHeaderMap
										.get("Recieved Amount"));
								if (entry.getCell(index) != null
										&& StringUtils.isNotBlank(entry
												.getCell(index).toString())) {
									try {
										amount = entry.getCell(index)
												.getNumericCellValue();
										newmanualCharge
												.setPaidAmount((-amount));

									} catch (Exception e) {
										log.error("Failed by seller "
												+ sellerId, e);
										errorMessage
												.append(" Payment Amount cell is corrupted");
										validaterow = false;

									}
								} else {
									errorMessage
											.append("Amount format is wrong or null.");
									validaterow = false;
								}
							} catch (Exception e) {
								errorMessage
										.append("This Column 'Settlement Value (Rs.)' doesn't exist");
								validaterow = false;
							}

							/*
							 * } else { errorMessage .append(
							 * "The Column 'Seller SKU' value is empty or null."
							 * ); validaterow = false; }
							 */
							if (cellIndexMap.get(columHeaderMap
									.get("Sales Channel")) != null) {
								int indexchannel = cellIndexMap
										.get(columHeaderMap
												.get("Sales Channel"));
								newmanualCharge.setPartner(entry.getCell(
										indexchannel).toString());
							} else {
								errorMessage
										.append("O2R channel is not present.");
								validaterow = false;
							}

							if (validaterow) {

								newmanualCharge.setUploadDate(new Date());
								manualChargesList.add(newmanualCharge);
							} else {
								errorSet.add(errorMessage.toString());
							}
						} else {
							errorMessage
									.append("Fulfilment Type is not accepted.Only NON-FA or null is valid.");
							validaterow = false;
							errorSet.add(errorMessage.toString());
						}

					} catch (Exception e) {
						log.error("Failed by seller: " + sellerId, e);
						errorMessage.append("Invalid Input !");
						errorSet.add(errorMessage.toString());
					}
				}
			} catch (Exception e) {
				errorMessage
						.append("The Column 'Fulfilment Type' doesn't exist");
				validaterow = false;
			}

			if (generatePaymentUpload) {
				log.info("Updating payment Upload with negative amount "
						+ totalnegative + " totalpositive : " + totalpositive);
				paymentUpload.setTotalpositivevalue(totalpositive);
				paymentUpload.setTotalnegativevalue(totalnegative);
				paymentUpload.setNetRecievedAmount(totalpositive
						- totalnegative);
				paymentUpload.setUploadDesc("PAYU" + sellerId + ""
						+ new Date().getTime());
				paymentUpload.setUploadStatus("Success");
				uploadPaymentId = paymentUploadService.addPaymentUpload(
						paymentUpload, sellerId);
				log.info(" Saving payment upload : " + uploadPaymentId);
			}
			if (manualChargesList != null && manualChargesList.size() != 0) {
				for (ManualCharges manuals : manualChargesList) {
					try {

						if (uploadPaymentId != null)
							manuals.setChargesDesc(uploadPaymentId);
						// manuals.setPartner("Flipkart");
						expenseService.addExpense(
								new Expenses("Manual Charges", uploadPaymentId,
										"Manual Charges", new Date(), manuals
												.getDateOfPayment(), manuals
												.getPaidAmount(), manuals
												.getPartner() != null ? manuals
												.getPartner() : "", sellerId),
								sellerId);
					} catch (Exception e) {
						log.error("Failed! by SellerId : " + sellerId, e);
					}
				}
				manualChargesService.addListManualCharges(manualChargesList,
						sellerId);
			}
			uploadResultXLS(offices, "Flipkart_Payment", uploadFileName, errorSet, path,
					sellerId, uploadReport);
		}

		catch (Exception e) {
			addErrorUploadReport("Flipkart_Payment", sellerId,
					uploadReport);
			log.error("Failed by seller: " + sellerId, e);
		}
	}

	public void saveSnapDealPaymentContents(MultipartFile file, int sellerId,
			String path, UploadReport uploadReport) throws IOException {
		log.info("$$$ saveSnapDealPaymentContents starts : SaveMappedFiles $$$");

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
		List<ManualCharges> manualChargesList = new ArrayList<ManualCharges>();
		ManualCharges newmanualCharge = null;
		boolean generatePaymentUpload = false;
		String uploadPaymentId = null;
		Map<String, String> duplicateKey = new HashMap<String, String>();
		double amount = 0;
		String uploadFileName = "";
		try {
			chanupload = uploadMappingService.getChannelUploadMapping(
					"Snapdeal", "Payment");
			if (chanupload != null && chanupload.getColumMap() != null) {
				for (ColumMap colums : chanupload.getColumMap()) {
					columHeaderMap.put(colums.getO2rColumName(),
							colums.getChannelColumName());
				}
			}
			System.out.println(columHeaderMap);
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());

			HSSFSheet worksheet = offices.getSheetAt(0);
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			uploadFileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")) + new Date().getTime();
			entry = worksheet.getRow(0);
			for (int cellIndex = 0; cellIndex < entry
					.getPhysicalNumberOfCells(); cellIndex++) {
				/*
				 * if (columHeaderMap.containsValue(entry.getCell(cellIndex)
				 * .toString())) {
				 * cellIndexMap.put(entry.getCell(cellIndex).toString(),
				 * cellIndex); }
				 */
				if(entry.getCell(cellIndex)!=null)
				cellIndexMap
						.put(entry.getCell(cellIndex).toString(), cellIndex);

			}
			System.out.println("cellIndexMap "+cellIndexMap);
			for (int rowIndex = 1; rowIndex < noOfEntries; rowIndex++) {
				errorMessage = new StringBuffer("Row :" + (rowIndex) + ":");
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				int index = 0;
				String channelheader = null;
				channelOrderId = null;
				errorMessage = new StringBuffer("Row :" + (rowIndex) + ":");
				// Code for fetching right colum
				orderPayment = new OrderPayment();
				order = null;
				boolean combo=false;
				List<Order> onj=null;
				Date recievedDate=null;
				boolean orderavailable=false;
				try {
					if (cellIndexMap.get(columHeaderMap.get("ChannelOrderId")) != null) {
						channelheader = columHeaderMap.get("ChannelOrderId");
						index = cellIndexMap.get(channelheader);
					} else {
						errorMessage
								.append("The column 'Transaction ID' doesn't exist");
						validaterow = false;
					}
					if (entry.getCell(index) != null) {
						entry.getCell(index).setCellType(
								HSSFCell.CELL_TYPE_STRING);
						log.info(" channelorder id: "
								+ entry.getCell(index).toString());
						
						if (StringUtils.isNumeric(entry.getCell(index)
								.toString())) {

							int skuIndex = cellIndexMap.get(columHeaderMap
									.get("Seller SKU"));
							entry.getCell(index).setCellType(
									HSSFCell.CELL_TYPE_STRING);

							onj = null;
							if (entry.getCell(skuIndex) != null
									&& entry.getCell(skuIndex).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
								channelOrderId = entry.getCell(index)
										.toString()
										+ GlobalConstant.orderUniqueSymbol
										+ entry.getCell(skuIndex).toString()
												.trim().toUpperCase();
								onj = orderService.searchAsIsOrder(
										"channelOrderID", channelOrderId,
										sellerId);
							}
							
							try {
								int dateindex = cellIndexMap.get(columHeaderMap
										.get("Order Date"));
								if (entry.getCell(dateindex) != null
										&& entry.getCell(dateindex).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
									try {
										entry.getCell(dateindex).setCellType(
												HSSFCell.CELL_TYPE_STRING);
											String date = entry.getCell(dateindex)
													.toString();
											recievedDate=formatmonthname.parse(date);
										
									} catch (Exception e) {
									}

								} 
							} catch (NullPointerException e) {
								e.printStackTrace();
									}

							if (onj == null) {
								errorMessage
										.append(" Channel OrderId not present ");
								validaterow = false;
							} else {
								if (onj.size() == 1
										&& onj.get(0).getChannelOrderID()
												.equals(channelOrderId)) {
									channelOrderId = onj.get(0)
											.getChannelOrderID();
								}
								else if (onj.size() > 1)
								{
									for(Order ordcheck:onj){
									if(ordcheck.getTypeIdentifier()!=null){
										if(ordcheck.getTypeIdentifier().contains(entry.getCell(index).toString()))
											combo=true;
										else
											combo=false;
									}
									else if (recievedDate!=null){
										if(format.format(ordcheck.getOrderDate()).equals(format.format(recievedDate))){
											orderavailable=true;
											channelOrderId=ordcheck.getChannelOrderID();
										}
									}
									else{
										errorMessage
										.append("Multiple Orders With Channel Order ID.");
								validaterow = false;
									}
									}
									if(!(orderavailable||combo)){
										errorMessage
										.append("Multiple Orders With Channel Order ID.");
								validaterow = false;
									}
								}
								else {

									errorMessage
											.append("No Orders With Channel Order ID.");
									validaterow = false;
								}
							}
							try {
								index = cellIndexMap.get(columHeaderMap
										.get("Payment Date"));
								try {
									if (entry.getCell(index) != null) {
										orderPayment
												.setDateofPayment(new Date(
														entry.getCell(index)
																.toString()));
									} else {
										errorMessage
												.append(" Payment Date format is wrong or null");
										validaterow = false;
									}
								} catch (Exception e) {
									log.error(
											"Failed by selled ID " + sellerId,
											e);
									errorMessage
											.append(" Payment Date format is wrong or null");
									validaterow = false;
								}
							} catch (NullPointerException e) {
								errorMessage
										.append("The column 'Payment Date' doesn't exist");
								validaterow = false;
							}

							try {
								index = cellIndexMap.get(columHeaderMap
										.get("Recieved Amount"));
								if (entry.getCell(index) != null
										&& StringUtils.isNotBlank(entry
												.getCell(index).toString())) {
									try {
										amount = entry.getCell(index)
												.getNumericCellValue();
										if (amount > 0) {
											orderPayment
													.setPositiveAmount(amount);
											/*
											 * totalpositive = totalpositive +
											 * amount;
											 */
										} else {
											orderPayment.setNegativeAmount(Math
													.abs(amount));
											/*
											 * totalnegative = totalnegative +
											 * Math.abs(amount);
											 */
										}
									} catch (Exception e) {
										log.error("Failed by seller "
												+ sellerId, e);
										errorMessage
												.append(" Payment Amount cell is corrupted");
										validaterow = false;

									}
								} else {
									errorMessage
											.append("Amount format is wrong or null.");
									validaterow = false;
								}
							} catch (NullPointerException e) {
								errorMessage
										.append("The column 'Net Payable' doesn't exist");
								validaterow = false;
							}

							try {
								index = cellIndexMap.get(columHeaderMap
										.get("Seller SKU"));
								if (entry.getCell(index) != null
										&& StringUtils.isNotBlank(entry
												.getCell(index).toString())) {
									skucode = entry.getCell(index).toString()
											.trim().toUpperCase();
								}
							} catch (NullPointerException e) {
								errorMessage
										.append("The column 'SKU' doesn't exist");
							}

							if (validaterow) {

								// Code for ading other payment variables
								List<PaymentVariables> payvarlist = new ArrayList<PaymentVariables>();
								for (String s : GlobalConstant.snapdealpaymentvariablesList) {
									PaymentVariables payvar = new PaymentVariables();
									try {
										if (cellIndexMap.containsKey(s)) {
											index = cellIndexMap.get(s);
											if (entry.getCell(index) != null
													&& StringUtils
															.isNotBlank(entry
																	.getCell(
																			index)
																	.toString())) {
												payvar.setPayKey(s);
												payvar.setPayValue(entry
														.getCell(index)
														.toString());
												payvarlist.add(payvar);
											}

										}
									} catch (Exception e) {
										log.warn(
												"Failed by seller ID : "
														+ sellerId
														+ " FlipkatyPayment : Error in reading field "
														+ s, e);
									}

								}
								orderPayment.setPaymentFileName(uploadFileName);
								
								if (amount > 0) {
									totalpositive = totalpositive + amount;
								} else {
									totalnegative = totalnegative
											+ Math.abs(amount);
								}
								if(combo)
								{
									
									 if(onj!=null&&onj.size()>0)
									 {
										 if(amount>0)
										 {
										 orderPayment.setPositiveAmount(orderPayment.getPositiveAmount()/onj.size());
										 }
										 else{
											 orderPayment.setNegativeAmount(orderPayment.getNegativeAmount()/onj.size());
										 }
									 for(Order ord:onj)
									 {
										 order = orderService.addOrderPayment(skucode,
													ord.getChannelOrderID(), orderPayment, sellerId);
									 }
									 }
								}
								else{
									orderPayment.setPaymentVar(payvarlist);

								order = orderService.addOrderPayment(skucode,
										channelOrderId, orderPayment, sellerId);
								}
							} else {
								errorSet.add(errorMessage.toString());

							}
							if (order != null) {
								if (!duplicateKey.containsKey(channelOrderId)) {
									// order.getPaymentUpload().add(paymentUpload);
									// paymentUpload.getOrders().add(order);
									generatePaymentUpload = true;
									duplicateKey.put(channelOrderId,
											channelOrderId);
								}
							}

						} else {
							System.out.println(" Reading manual charge : "
									+ entry.getCell(index));
							newmanualCharge = new ManualCharges();
							// newmanualCharge.setPartner("Snapdeal");

							try {
								index = cellIndexMap.get(columHeaderMap
										.get("Payment Date"));
								System.out.println("Payment Date : "
										+ entry.getCell(index));
								if (entry.getCell(index) != null) {
									newmanualCharge.setDateOfPayment(new Date(
											entry.getCell(index).toString()));
								} else {
									errorMessage
											.append(" Payment Date format is wrong or null");
									validaterow = false;
								}
							} catch (NullPointerException e) {
								errorMessage
										.append("The column 'Payment Date' doesn't exist");
								validaterow = false;
							}

							try {
								index = cellIndexMap.get(columHeaderMap
										.get("Recieved Amount"));
								if (entry.getCell(index) != null
										&& StringUtils.isNotBlank(entry
												.getCell(index).toString())) {
									try {
										amount = entry.getCell(index)
												.getNumericCellValue();
										newmanualCharge
												.setPaidAmount((-amount));

									} catch (Exception e) {
										log.error("Failed by seller "
												+ sellerId, e);
										errorMessage
												.append(" Payment Amount cell is corrupted");
										validaterow = false;

									}
								} else {
									errorMessage
											.append("Amount format is wrong or null.");
									validaterow = false;
								}
							} catch (NullPointerException e) {
								errorMessage
										.append("The column 'Net  Payable' doesn't exist");
								validaterow = false;
							}

							try {
								index = cellIndexMap.get(columHeaderMap
										.get("Particulars"));
								if (entry.getCell(index) != null
										&& !StringUtils.isBlank(entry.getCell(
												index).toString())) {
									newmanualCharge.setParticular(entry
											.getCell(index).toString());
								}
							} catch (NullPointerException e) {
								errorMessage
										.append("The column 'Type' doesn't exist");
							}

							if (cellIndexMap.get(columHeaderMap
									.get("Sales Channel")) != null) {
								int indexchannel = cellIndexMap
										.get(columHeaderMap
												.get("Sales Channel"));
								newmanualCharge.setPartner(entry.getCell(
										indexchannel).toString());
								newmanualCharge.setUploadDate(new Date());
							}

							if (validaterow) {
								manualChargesList.add(newmanualCharge);
							} else {
								errorSet.add(errorMessage.toString());
							}
						}

					} else {
						errorMessage.append("Order ID is empty or null.");
						validaterow = false;
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
				uploadPaymentId = paymentUploadService.addPaymentUpload(
						paymentUpload, sellerId);
			}
			if (manualChargesList != null && manualChargesList.size() != 0) {
				for (ManualCharges manuals : manualChargesList) {
					try {
						System.out.println(" Payment Upload list created : "
								+ uploadPaymentId);
						manuals.setChargesDesc(uploadPaymentId);

						expenseService.addExpense(
								new Expenses("Manual Charges", uploadPaymentId,
										"Manual Charges", new Date(), manuals
												.getDateOfPayment(), manuals
												.getPaidAmount(), manuals
												.getPartner() != null ? manuals
												.getPartner() : "Snapdeal",
										sellerId), sellerId);
					} catch (Exception e) {
						log.error("Failed! by SellerId : " + sellerId, e);
					}
				}
				manualChargesService.addListManualCharges(manualChargesList,
						sellerId);
			}
			uploadResultXLS(offices, "Snapdeal_Payment", uploadFileName, errorSet, path,
					sellerId, uploadReport);
		}

		catch (Exception e) {
			addErrorUploadReport("Snapdeal_Payment", sellerId,
					uploadReport);
			log.error("Failed by seller: " + sellerId, e);
		}
	}

	public void savePayTMOrderContents(MultipartFile file, int sellerId,
			String path, UploadReport uploadReport) throws IOException {
		log.info("$$$ savePayTMOrderContents starts : SaveMappedFiles $$$");

		ChannelUploadMapping chanupload = null;
		Map<String, String> columHeaderMap = new LinkedHashMap<String, String>();
		Map<String, Integer> cellIndexMap = new LinkedHashMap<String, Integer>();
		StringBuffer errorMessage = new StringBuffer();
		CustomerBean customerBean = null;
		Partner partner = null;
		Events event = null;
		List<Order> saveList = new ArrayList<Order>();
		Map<String, String> idsList = new HashMap<String, String>();
		Map<String, OrderBean> returnOrderMap = new LinkedHashMap<>();
		OrderBean order = null;
		int noOfEntries = 1;
		OrderTaxBean otb = null;
		HSSFRow entry;
		boolean validaterow = true;
		Set<String> errorSet = new HashSet<String>();
		Map<String, String> duplicateKey = new HashMap<String, String>();
		String uploadFileName = "";
		try {
			chanupload = uploadMappingService.getChannelUploadMapping("PayTM",
					"order");
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
			uploadFileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")) + new Date().getTime();
			entry = worksheet.getRow(0);
			for (int cellIndex = 0; cellIndex < entry
					.getPhysicalNumberOfCells(); cellIndex++) {
				if (entry.getCell(cellIndex)!=null&&columHeaderMap.containsValue(entry.getCell(cellIndex)
						.toString())) {
					cellIndexMap.put(entry.getCell(cellIndex).toString(),
							cellIndex);
				}

			}
			System.out.println("cellIndexMap "+cellIndexMap+" columHeaderMap "+columHeaderMap);
			// SKUList = productService.listProductSKU(sellerId);
			idsList = orderService.listOrderIds("channelOrderID", sellerId);
			log.info(noOfEntries);
			log.debug("After getting no of rows" + noOfEntries);
			for (int rowIndex = 1; rowIndex < noOfEntries; rowIndex++) {
				errorMessage = new StringBuffer("Row :" + (rowIndex) + ":");
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				order = new OrderBean();
				customerBean = new CustomerBean();
				otb = new OrderTaxBean();
				int index = 0;
				Product product = null;
				ProductConfig productConfig = null;
				List<ProductConfig> productConfigs = null;
				String channelorderid = "";
				TaxCategory taxcat = null;
				errorMessage = new StringBuffer("Row :" + (rowIndex) + ":");
				try {

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Sales Channel"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							partner = partnerService.getPartner(
									entry.getCell(index).toString(), sellerId);
							if (partner != null)
								order.setPcName(partner.getPcName());
							else {
								order.setPcName(entry.getCell(index).toString());
								errorMessage.append(" Partner do not exist;");
								validaterow = false;
							}
						} else {
							errorMessage.append(" Partner Name is null;");
							validaterow = false;
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("The column 'Sales Channel' doesn't exist");
						validaterow = false;
					}

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Channel Order ID"));
						int skuIndex = cellIndexMap.get(columHeaderMap
								.get("SkUCode"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK
								&& entry.getCell(skuIndex) != null
								&& entry.getCell(skuIndex).getCellType() != HSSFCell.CELL_TYPE_BLANK) {

							entry.getCell(index).setCellType(
									HSSFCell.CELL_TYPE_STRING);
							entry.getCell(skuIndex).setCellType(
									HSSFCell.CELL_TYPE_STRING);
							if (partner != null) {
								productConfigs = productService
										.getProductConfig(
												entry.getCell(skuIndex)
														.toString().trim()
														.toUpperCase(),
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
											channelorderid = entry.getCell(
													index).toString()
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
									/*if ((channelorderid != null)
											&& (idsList == null || !idsList
													.containsKey(channelorderid)
													&& !duplicateKey
															.containsKey(channelorderid)
													&& productConfig != null)) {
										order.setChannelOrderID(channelorderid);
										order.setProductSkuCode(productConfig
												.getProductSkuCode());
										order.setPartnerCommission(productConfig.getCommision());
										duplicateKey.put(channelorderid, "");
									} else {
										errorMessage
												.append(" Channel OrderId is already present ");
										validaterow = false;
									}*/

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
								.append("The column 'order_id' doesn't exist");
						validaterow = false;
					}

					try {
						if (cellIndexMap.get(columHeaderMap
								.get("Secondary OrderID")) != null) {
							index = cellIndexMap.get(columHeaderMap
									.get("Secondary OrderID"));
							if (entry.getCell(index) != null
									&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
								entry.getCell(index).setCellType(
										HSSFCell.CELL_TYPE_STRING);
								order.setSubOrderID(entry.getCell(index)
										.toString());
								channelorderid = channelorderid
										+ GlobalConstant.orderUniqueSymbol
										+ order.getSubOrderID();
								if ((channelorderid != null)
										&& (idsList == null || !idsList
												.containsKey(channelorderid)
												&& !duplicateKey
														.containsKey(channelorderid)
												&& productConfig != null)) {
									order.setChannelOrderID(channelorderid);
									order.setProductSkuCode(productConfig
											.getProductSkuCode());
									duplicateKey.put(channelorderid, "");
								} else {
									errorMessage
											.append(" Channel OrderId is already present ");
									validaterow = false;
								}
							}
						} else {
							errorMessage.append(" item_id is null ");
							validaterow = false;
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("The column 'item_id' doesn't exist");
						validaterow = false;
					}

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Order Recieved Date"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							try {
								if (HSSFDateUtil.isCellDateFormatted(entry
										.getCell(index))) {
									order.setOrderDate(entry.getCell(index)
											.getDateCellValue());
								} else {
									String date = entry.getCell(index)
											.toString();
									order.setOrderDate(new Date(date));
								}
							} catch (Exception e) {
								errorMessage
										.append(" Order Received Date format is wrong ,enter mm/dd/yyyy,");
								validaterow = false;
							}

						} else {
							errorMessage
									.append(" Order Recieved Date is null;");
							validaterow = false;
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("The column 'creation date' doesn't exist");
						validaterow = false;
					}

					/*
					 * try { index =
					 * cellIndexMap.get(columHeaderMap.get("SkUCode")); if
					 * (entry.getCell(index) != null &&
					 * entry.getCell(index).getCellType() !=
					 * HSSFCell.CELL_TYPE_BLANK) { if
					 * (SKUList!=null&&SKUList.contains(entry.getCell(index)
					 * .toString())) {
					 * order.setProductSkuCode(entry.getCell(index)
					 * .toString()); } else { errorMessage
					 * .append(" Product SKU does not exist;"); validaterow =
					 * false; } } else {
					 * errorMessage.append(" Product SKU is null;"); validaterow
					 * = false; } } catch (NullPointerException e) {
					 * errorMessage
					 * .append("The column 'item.sku' doesn't exist");
					 * validaterow = false; }
					 */

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer_Fname"));
						int index1 = cellIndexMap.get(columHeaderMap
								.get("Customer_Lname"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK
								|| entry.getCell(index1) != null
								&& entry.getCell(index1).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							customerBean.setCustomerName(entry.getCell(index)
									.toString()
									+ " "
									+ entry.getCell(index1).toString());
						}
					} catch (NullPointerException e) {

					}

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Payment Type"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							if (entry.getCell(index).toString().toUpperCase()
									.contains("PREPAID")) {
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
								.append("The column 'Payment Type' doesn't exist");
						validaterow = false;
					}

					if (cellIndexMap.get(columHeaderMap.get("AWB No")) != null) {
						index = cellIndexMap.get(columHeaderMap.get("AWB No"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							entry.getCell(index).setCellType(
									HSSFCell.CELL_TYPE_STRING);
							order.setAwbNum(entry.getCell(index).toString());
						}
					}

					try {
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
					}

					if (cellIndexMap.get(columHeaderMap.get("PIreferenceNo")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("PIreferenceNo"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							order.setPIreferenceNo(entry.getCell(index)
									.toString());
						}
					}
					if (cellIndexMap
							.get(columHeaderMap.get("Logistic Partner")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Logistic Partner"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							order.setLogisticPartner(entry.getCell(index)
									.toString());
						}
					}
					if (cellIndexMap.get(columHeaderMap.get("Order MRP")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Order MRP"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							try {
								order.setOrderMRP(Double.parseDouble(entry
										.getCell(index).toString()));
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
								.get("OrderSP Component A"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							try {
								order.setOrderSP(Double.parseDouble(entry
										.getCell(index).toString()));
							} catch (NumberFormatException e) {
								errorMessage
										.append(" Item_price should be a number ");
								validaterow = false;
							}
						} else {
							errorMessage.append(" Item_price is null ");
							validaterow = false;
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("The column 'item_price' doesn't exist");
						validaterow = false;
					}

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Order Shipped Date"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							try {
								if (HSSFDateUtil.isCellDateFormatted(entry
										.getCell(index))) {
									order.setShippedDate(entry.getCell(index)
											.getDateCellValue());
								} else {
									String date = entry.getCell(index)
											.toString();
									order.setShippedDate(new Date(date));
								}
							} catch (Exception e) {
								errorMessage
										.append(" Shipped Date formate is wrong ,enter mm/dd/yyyy,");
								validaterow = false;
							}
						} else {
							
							index = cellIndexMap.get(columHeaderMap
									.get("Alt Shipped Date"));
							if (entry.getCell(index) != null
									&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
								try {
									if (HSSFDateUtil.isCellDateFormatted(entry
											.getCell(index))) {
										order.setShippedDate(entry.getCell(index)
												.getDateCellValue());
									} else {
										order.setShippedDate(new Date(entry.getCell(index)
												.toString()));
									}
								} catch (Exception e) {
									errorMessage
											.append(" Estimated shipped date format is wrong ,enter mm/dd/yyyy,");
									validaterow = false;
								}
							}
							else
							{
							errorMessage.append(" Shipping Date and Estimated shipped date are empty");
							validaterow = false;
							}
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("The column 'Order Shipped Date' or 'Estimated shipped date' doesn't exist");
						validaterow = false;
					}

					try {
						index = cellIndexMap
								.get(columHeaderMap.get("Quantity"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							try {
								if ((int) Float.parseFloat(entry.getCell(index)
										.toString()) != 0) {
									order.setQuantity((int) Float
											.parseFloat(entry.getCell(index)
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
						errorMessage.append("The column 'qty' doesn't exist");
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
									if (entry.getCell(index) != null
											&& entry.getCell(index)
													.getCellType() != HSSFCell.CELL_TYPE_BLANK) {

										try {
											order.setGrossNetRate(Double
													.parseDouble(entry.getCell(
															index).toString()));
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
									if (entry.getCell(index) != null
											&& entry.getCell(index)
													.getCellType() != HSSFCell.CELL_TYPE_BLANK) {
										try {
											order.setGrossNetRate(Double
													.parseDouble(entry.getCell(
															index).toString()));
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
					if (cellIndexMap.get(columHeaderMap.get("Customer Email")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer Email"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							log.debug(" Email from sheet ************ "
									+ rowIndex + ":"
									+ entry.getCell(index).toString());
							customerBean.setCustomerEmail(entry.getCell(index)
									.toString());
						}
					}
					if (cellIndexMap.get(columHeaderMap
							.get("Customer Phone No")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer Phone No"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							entry.getCell(index).setCellType(
									HSSFCell.CELL_TYPE_STRING);
							if (entry.getCell(index).toString().length() == 10
									&& !entry.getCell(index).toString()
											.contains("9999999999")) {
								if (entry.getCell(index).toString()
										.matches("[0-9]+")
										&& entry.getCell(index).toString()
												.length() > 2) {
									customerBean.setCustomerPhnNo(entry
											.getCell(index).toString());
								}
							}
						}
					}
					if (cellIndexMap.get(columHeaderMap.get("Customer City")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer City"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							customerBean.setCustomerCity(entry.getCell(index)
									.toString());
						}
					}
					if (cellIndexMap.get(columHeaderMap.get("Customer Email")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer Email"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							customerBean.setCustomerAddress(entry
									.getCell(index).toString());
						}
					}
					try {
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
								.append("The column 'pincode' doesn't exist");
						validaterow = false;
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

					if (cellIndexMap.get(columHeaderMap.get("Seller Note")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Seller Note"));
						if (entry.getCell(index) != null
								&& StringUtils.isNotBlank(entry.getCell(index)
										.toString())) {
							order.setSellerNote(entry.getCell(index).toString());

						}
					}

					// Pre save to generate id for use in hierarchy
					if (validaterow) {
						order.setOrderFileName(uploadFileName);
						order.setCustomer(customerBean);
						order.setOrderTax(otb);
						/*
						 * orderService.addOrder(ConverterClass.prepareModel(order
						 * ), sellerId);
						 */
						
						saveList.add(ConverterClass.prepareModel(order));
					} else {
						order.setCustomer(customerBean);
						order.setOrderTax(otb);
						log.debug(" Error while saving order : "
								+ order.getChannelOrderID()
								+ " errorMessage : " + errorMessage);
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
			uploadResultXLS(offices, "PayTM_Order_Upload", uploadFileName, errorSet, path,
					sellerId, uploadReport);

		} catch (Exception e) {
			log.debug("Inside save contents exception :"
					+ e.getLocalizedMessage());
			e.printStackTrace();
			addErrorUploadReport("PayTM_Order_Upload", sellerId, uploadReport);
			log.error("Failed! by SellerId : " + sellerId, e);
		}
	}

	public void savePayTMPaymentContents(MultipartFile file, int sellerId,
			String path, UploadReport uploadReport) throws IOException {

		log.info("$$$ savePayTMPaymentContents starts : SaveMappedFiles $$$");

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
		Map<String, String> duplicateKey = new HashMap<String, String>();
		String uploadFileName = "";
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
			uploadFileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")) + new Date().getTime();
			entry = worksheet.getRow(0);
			for (int cellIndex = 0; cellIndex < entry
					.getPhysicalNumberOfCells(); cellIndex++) {
				/*
				 * if (columHeaderMap.containsValue(entry.getCell(cellIndex)
				 * .toString())) {
				 * cellIndexMap.put(entry.getCell(cellIndex).toString(),
				 * cellIndex); }
				 */
				if(entry.getCell(cellIndex)!=null)
				cellIndexMap
						.put(entry.getCell(cellIndex).toString(), cellIndex);
			}

			for (int rowIndex = 1; rowIndex < noOfEntries; rowIndex++) {
				errorMessage = new StringBuffer("Row :" + (rowIndex) + ":");
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				double amount = 0;
				int index = 0;
				String channelheader = null;
				channelOrderId = null;
				Partner partner = null;
				ProductConfig productConfig = null;
				List<ProductConfig> productConfigs = null;
				errorMessage = new StringBuffer("Row :" + (rowIndex) + ":");
				// Code for fetching right colum
				orderPayment = new OrderPayment();
				boolean combo=false;
				List<Order> onj=null;

				try {
					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Sales Channel"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							partner = partnerService.getPartner(
									entry.getCell(index).toString(), sellerId);
						} else {
							errorMessage.append(" Partner Name is null;");
							validaterow = false;
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("The column 'Sales Channel' doesn't exist");
						validaterow = false;
					}

					if (cellIndexMap.get(columHeaderMap.get("ChannelOrderId")) != null
							&& cellIndexMap.get(columHeaderMap
									.get("Seller SKU")) != null
							&& cellIndexMap.get(columHeaderMap
									.get("Secondary OrderID")) != null) {
						channelheader = columHeaderMap.get("ChannelOrderId");
						index = cellIndexMap.get(channelheader);
						int skuIndex = cellIndexMap.get(columHeaderMap
								.get("Seller SKU"));
						int secOrderIndex = cellIndexMap.get(columHeaderMap
								.get("Secondary OrderID"));

						if (entry.getCell(index) != null
								&& StringUtils.isNotBlank(entry.getCell(index)
										.toString())
								&& entry.getCell(skuIndex) != null

								&& StringUtils.isNotBlank(entry.getCell(
										skuIndex).toString())) {
							entry.getCell(index).setCellType(
									HSSFCell.CELL_TYPE_STRING);
							entry.getCell(skuIndex).setCellType(
									HSSFCell.CELL_TYPE_STRING);
							entry.getCell(secOrderIndex).setCellType(
									HSSFCell.CELL_TYPE_STRING);
							skucode = entry.getCell(skuIndex).toString().trim()
									.toUpperCase();
							if (partner != null) {
								productConfigs = productService
										.getProductConfig(
												entry.getCell(skuIndex)
														.toString().trim()
														.toUpperCase(),
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
											channelOrderId = entry.getCell(
													index).toString()
													+ GlobalConstant.orderUniqueSymbol
													+ productConfig
															.getVendorSkuRef()
													+ GlobalConstant.orderUniqueSymbol
													+ entry.getCell(
															secOrderIndex)
															.toString();

										} /*else {
											channelOrderId = entry.getCell(
													index).toString()
													+ GlobalConstant.orderUniqueSymbol
													+ entry.getCell(skuIndex)
															.toString().trim()
															.toUpperCase()
													+ GlobalConstant.orderUniqueSymbol
													+ entry.getCell(
															secOrderIndex)
															.toString();
										}*/

										 onj = orderService
												.searchAsIsOrder(
														"channelOrderID",
														channelOrderId,
														sellerId);
										if (onj != null) {
											if (onj.size() == 1) {
												channelOrderId = onj.get(0)
														.getChannelOrderID();
											} else if (onj.size() > 1&& onj.get(0).getChannelOrderID()
													.contains(entry.getCell(index).toString()))
											{
													if(onj.get(0).getTypeIdentifier()!=null&&
														onj.get(0).getTypeIdentifier().contains(entry.getCell(index)
																.toString()))
												{
													combo=true;
												}
											}
											else {
												errorMessage
														.append("Multiple Orders With Channel Order ID.");
												validaterow = false;
											}

										} else {
											errorMessage
													.append("Order Item ID not present.");
											validaterow = false;
										}

										try {
											index = cellIndexMap
													.get(columHeaderMap
															.get("Payment Date"));
											if (entry.getCell(index) != null
													&& StringUtils
															.isNotBlank(entry
																	.getCell(
																			index)
																	.toString())) {
												String date = entry.getCell(
														index).toString();
												orderPayment
														.setDateofPayment(new Date(
																date));
											} else {
												errorMessage
														.append(" Payment Date format is wrong or null");
												validaterow = false;
											}
										} catch (NullPointerException e) {
											errorMessage
													.append("The column 'Payment Date' doesn't exist");
											validaterow = false;
										}

										try {
											index = cellIndexMap
													.get(columHeaderMap
															.get("Recieved Amount"));
											if (entry.getCell(index) != null
													&& StringUtils
															.isNotBlank(entry
																	.getCell(
																			index)
																	.toString())) {
												try {
													amount = entry
															.getCell(index)
															.getNumericCellValue();
													if (amount > 0) {
														orderPayment
																.setPositiveAmount(amount);
													} else if (amount < 0) {
														orderPayment
																.setNegativeAmount(Math
																		.abs(amount));
													}
												} catch (Exception e) {
													log.error(
															"Failed by seller "
																	+ sellerId,
															e);
													errorMessage
															.append(" Payment Amount cell is corrupted");
													validaterow = false;

												}
											} else {
												errorMessage
														.append("Amount format is wrong or null.");
												validaterow = false;
											}
										} catch (NullPointerException e) {
											errorMessage
													.append("The column 'Payable Amount' doesn't exist");
											validaterow = false;
										}
									} catch (Exception e) {

									}

								} else {
									errorMessage
											.append(" Invalid Product ID with Order item ID.");
									validaterow = false;
								}

							} else {
								errorMessage.append(" Partner do not exist;");
								validaterow = false;
							}
						} else {
							errorMessage
									.append(" 'Order Item ID' or 'Product ID'  or ' 'May be Blank or Null");
							validaterow = false;
						}

					} else {
						errorMessage
								.append("The column 'Order Item ID' or 'Order ID' doesn't exist");
						validaterow = false;
					}

					if (validaterow) {
						// Code for saving extra payment varibale in order
						List<PaymentVariables> payvarlist = new ArrayList<PaymentVariables>();
						for (String s : GlobalConstant.paytmpaymentvariablesList) {
							PaymentVariables payvar = new PaymentVariables();
							try {
								if (cellIndexMap.containsKey(s)) {
									index = cellIndexMap.get(s);
									if (entry.getCell(index) != null
											&& StringUtils.isNotBlank(entry
													.getCell(index).toString())) {
										payvar.setPayKey(s);
										payvar.setPayValue(entry.getCell(index)
												.toString());
										payvarlist.add(payvar);
									}

								}
							} catch (Exception e) {
								log.warn(
										"Failed by seller ID : "
												+ sellerId
												+ " FlipkatyPayment : Error in reading field "
												+ s, e);
							}

						}
						orderPayment.setPaymentFileName(uploadFileName);
						
						if (amount > 0) {
							totalpositive = totalpositive + amount;
						} else if (amount < 0) {
							totalnegative = totalnegative + Math.abs(amount);
						}
						if(combo)
						{
							 if(onj!=null&&onj.size()>0)
							 {
								 if(amount>0)
								 {
								 orderPayment.setPositiveAmount(orderPayment.getPositiveAmount()/onj.size());
								 }
								 else{
									 orderPayment.setNegativeAmount(orderPayment.getNegativeAmount()/onj.size());
								 }
							 for(Order ord:onj)
							 {
								 order = orderService.addOrderPayment(skucode,
											ord.getChannelOrderID(), orderPayment, sellerId);
							 }
							 }
						}
						else{
							orderPayment.setPaymentVar(payvarlist);

						order = orderService.addOrderPayment(skucode,
								channelOrderId, orderPayment, sellerId);
						}
					} else {
						errorSet.add(errorMessage.toString());
					}
					if (order != null) {
						if (!duplicateKey.containsKey(channelOrderId)) {
							// order.getPaymentUpload().add(paymentUpload);
							// paymentUpload.getOrders().add(order);
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

			uploadResultXLS(offices, "PayTM_Payment", uploadFileName, errorSet, path, sellerId,
					uploadReport);
		}

		catch (Exception e) {
			addErrorUploadReport("PayTM_Payment", sellerId,
					uploadReport);
			log.error("Failed by seller: " + sellerId, e);
		}
	}

	public void saveAmazonPaymentContents(MultipartFile file, int sellerId,
			String path, UploadReport uploadReport) throws IOException {
		log.info("$$$ saveAmazonPaymentContents starts : SaveMappedFiles $$$");

		ChannelUploadMapping chanupload = null;
		Map<String, String> columHeaderMap = new LinkedHashMap<String, String>();
		Map<String, Integer> cellIndexMap = new LinkedHashMap<String, Integer>();
		StringBuffer errorMessage = null;
		// String channelOrderId = null;
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
		Map<String, OrderPaymentBean> paymentMap = new HashMap<String, OrderPaymentBean>();
		Map<String, OrderPaymentBean> comboPaymentMap = new HashMap<String, OrderPaymentBean>();
		Map<String, Double> easyShipMap = new HashMap<String, Double>();
		OrderPaymentBean paymentBean = null;
		List<ManualCharges> manualChargesList = new ArrayList<ManualCharges>();
		ManualCharges manualCharge = null;
		String uploadPaymentId = null;		
		String uploadFileName = "";		
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
			uploadFileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")) + new Date().getTime();
			entry = worksheet.getRow(0);
			
			for (int cellIndex = 0; cellIndex < entry
					.getPhysicalNumberOfCells(); cellIndex++) {
				if (entry.getCell(cellIndex)!=null&&columHeaderMap.containsValue(entry.getCell(cellIndex)
						.toString())) {
					cellIndexMap.put(entry.getCell(cellIndex).toString(),
							cellIndex);
				}
			}
			for (int rowIndex = 1; rowIndex < noOfEntries; rowIndex++) {
				errorMessage = new StringBuffer("Row :" + (rowIndex) + ":");
				entry = worksheet.getRow(rowIndex);
				String key = null;
				validaterow = true;
				int index = 0;
				String channelheader = null;
				// channelOrderId = null;
				errorMessage = new StringBuffer("Row :" + (rowIndex) + ":");
				double amount = 0;
				boolean combo=false;
				List<Order> onj=null;

				// Code for fetching right colum
				try {
					if (columHeaderMap.get("ChannelOrderId") != null) {
						channelheader = columHeaderMap.get("ChannelOrderId");
						index = cellIndexMap.get(channelheader);
					} else {
						errorMessage
								.append("The column 'Order ID' doesn't exist");
						validaterow = false;
					}
					if (entry.getCell(index) != null
							&& StringUtils.isNotBlank(entry.getCell(index)
									.toString())) {
						entry.getCell(index).setCellType(
								HSSFCell.CELL_TYPE_STRING);
						if (cellIndexMap.get(columHeaderMap.get("Seller SKU")) != null) {
							int skuIndex = cellIndexMap.get(columHeaderMap
									.get("Seller SKU"));
							if (entry.getCell(skuIndex) != null
									&& StringUtils.isNotBlank(entry.getCell(
											skuIndex).toString())) {		
								
								
								String channelOrderID = entry.getCell(index)
										.toString()
										+ GlobalConstant.orderUniqueSymbol
										+ entry.getCell(skuIndex).toString()
												.trim().toUpperCase();

								 onj = orderService.searchAsIsOrder(
										"channelOrderID", channelOrderID,
										sellerId);
								System.out.println(entry.getCell(index)
										.toString());
								if (onj != null) {
									if(onj.size() > 1 
											&& onj.get(0) != null 
											&& onj.get(0).getTypeIdentifier().contains(channelOrderID.substring(0, channelOrderID.indexOf(GlobalConstant.orderUniqueSymbol)))){
										combo  = true;
									}
									if (onj.size() == 1 || combo) {
										if(combo){
											if (comboPaymentMap.containsKey(channelOrderID)) {
												paymentBean = comboPaymentMap.get(channelOrderID);
											} else {
												paymentBean = new OrderPaymentBean();
											}
											key = channelOrderID;
											paymentBean.setChannelOrderId(channelOrderID);
										} else {
											if (paymentMap.containsKey(onj.get(0).getChannelOrderID())) {
												paymentBean = paymentMap.get(onj.get(0).getChannelOrderID());
											} else {
												paymentBean = new OrderPaymentBean();
											}
											key = onj.get(0).getChannelOrderID();
											paymentBean.setChannelOrderId(onj
													.get(0).getChannelOrderID());
										}
										try {
											index = cellIndexMap
													.get(columHeaderMap
															.get("Payment Date"));
											if (entry.getCell(index) != null
													&& StringUtils
															.isNotBlank(entry
																	.getCell(
																			index)
																	.toString())) {
												String date = entry.getCell(
														index).toString();
												paymentBean
														.setDateofPayment(new Date(
																date));
											} else {
												errorMessage
														.append(" Payment Date format is wrong or null");
												validaterow = false;
											}
										} catch (NullPointerException e) {
											errorMessage
													.append("The column 'Date' doesn't exist");
											validaterow = false;
										}

										try {
											index = cellIndexMap
													.get(columHeaderMap
															.get("Recieved Amount"));
											if (entry.getCell(index) != null
													&& StringUtils
															.isNotBlank(entry
																	.getCell(
																			index)
																	.toString())) {
												try {
													String amt = entry
															.getCell(index)
															.toString()
															.substring(
																	entry.getCell(
																			index)
																			.toString()
																			.indexOf(
																					".") + 1)
															.trim();
													amount = Double
															.parseDouble(amt);
													if (amount > 0) {
														paymentBean
																.setPositiveAmount(paymentBean
																		.getPositiveAmount()
																		+ amount);
														// totalpositive =
														// totalpositive
														// + amount;
													} else if (amount < 0) {
														paymentBean
																.setNegativeAmount(Math.abs(paymentBean
																		.getNegativeAmount()
																		+ Math.abs(amount)));
														/*
														 * totalnegative =
														 * totalnegative +
														 * Math.abs(amount);
														 */
													}
												} catch (Exception e) {
													log.error(
															"Failed by seller "
																	+ sellerId,
															e);
													errorMessage
															.append(" Payment Amount cell is corrupted");
													validaterow = false;
												}
											} else {
												errorMessage
														.append("Amount format is wrong or null.");
												validaterow = false;
											}
										} catch (NullPointerException e) {
											errorMessage
													.append("The column 'Amount' doesn't exist");
											validaterow = false;
										}

									} else {
										errorMessage
												.append("Multiple Orders With This Channel Order ID.");
										validaterow = false;
									}
								} else {
									errorMessage
											.append("Channel OrderId not present.");
									validaterow = false;
								}								
							} else {
								
								if (cellIndexMap.get(columHeaderMap
										.get("Payment Detail")) != null) {
									int PDindex = cellIndexMap.get(columHeaderMap.get("Payment Detail"));
									if (entry.getCell(PDindex) != null
											&& StringUtils.isNotBlank(entry.getCell(PDindex)
													.toString())
											&& (entry
													.getCell(PDindex)
													.toString()
													.trim()
													.equalsIgnoreCase(
															"Amazon Easy Ship Fees"))) {
										String chanId = entry.getCell(index).toString();
										try {
											index = cellIndexMap
													.get(columHeaderMap
															.get("Recieved Amount"));
											if (entry.getCell(index) != null
													&& StringUtils
															.isNotBlank(entry
																	.getCell(
																			index)
																	.toString())) {
												try {
													String amt = entry
															.getCell(index)
															.toString()
															.substring(
																	entry.getCell(
																			index)
																			.toString()
																			.indexOf(
																					".") + 1)
															.trim();
													System.out.println(amt);
													amount = Double.parseDouble(amt);
													System.out.println(amount);													
												} catch (Exception e) {
													log.error("Failed by seller "+ sellerId, e);
													errorMessage.append(" Payment Amount cell is corrupted");
													validaterow = false;
												}
											} else {
												errorMessage.append("Amount format is wrong or null.");
												validaterow = false;
											}
										} catch (NullPointerException e) {
											errorMessage.append("The column 'Amount' doesn't exist");
											validaterow = false;
										}
										if(easyShipMap.containsKey(chanId)){
											easyShipMap.put(chanId, easyShipMap.get(chanId)+(amount));
										} else {
											easyShipMap.put(chanId, amount);
										}										
										
									} else {
										errorMessage.append("SKU is Blank or Null.");
										validaterow = false;
									}
								} else {
									errorMessage
											.append("The column 'Payment Detail' doesn't exist");
									validaterow = false;
								}																
							}							
						} else {
							errorMessage
									.append("The column 'SKU' doesn't exist.");
							validaterow = false;
						}
						if (validaterow) {
							if (amount > 0) {
								totalpositive = totalpositive + amount;
							} else {
								totalnegative = totalnegative
										+ Math.abs(amount);
							}
							if(key != null && combo){
								comboPaymentMap.put(key, paymentBean);
							} else if (key != null) {
								paymentMap.put(key, paymentBean);
							}

						} else {
							errorSet.add(errorMessage.toString());
						}
					} else {
						if (cellIndexMap.get(columHeaderMap
								.get("Payment Detail")) != null) {
							index = cellIndexMap.get(columHeaderMap
									.get("Payment Detail"));
						} else {
							errorMessage
									.append("The column 'Payment Detail' doesn't exist");
							validaterow = false;
						}
						if (entry.getCell(index) != null
								&& StringUtils.isNotBlank(entry.getCell(index)
										.toString())
								&& (!entry
										.getCell(index)
										.toString()
										.trim()
										.equalsIgnoreCase(
												"Current Reserve Amount") && !entry
										.getCell(index)
										.toString()
										.trim()
										.equalsIgnoreCase(
												"Previous Reserve Amount Balance"))) {

							manualCharge = new ManualCharges();
							manualCharge.setParticular(entry.getCell(index)
									.toString());
							manualCharge.setPartner("Amazon");

							try {
								index = cellIndexMap.get(columHeaderMap
										.get("Recieved Amount"));
								if (entry.getCell(index) != null
										&& StringUtils.isNotBlank(entry
												.getCell(index).toString())) {
									try {
										String amt = entry
												.getCell(index)
												.toString()
												.substring(
														entry.getCell(index)
																.toString()
																.indexOf(".") + 1)
												.trim();
										System.out.println(amt);
										System.out.println(NumberFormat
												.getNumberInstance(
														java.util.Locale.US)
												.parse(amt));
										amount = Double.parseDouble(amt
												.replaceAll(",", ""));
										System.out.println(amount);
										manualCharge.setPaidAmount(-amount);
									} catch (Exception e) {
										log.error("Failed", e);
										errorMessage
												.append("Invalid Amount Format");
										validaterow = false;
									}
								} else {
									errorMessage
											.append("Field Is Empty Or Null");
									validaterow = false;
								}
							} catch (NullPointerException e) {
								errorMessage
										.append("The column 'Amount' doesn't exist");
								validaterow = false;
							}

							try {
								index = cellIndexMap.get(columHeaderMap
										.get("Payment Date"));
								if (entry.getCell(index) != null
										&& StringUtils.isNotBlank(entry
												.getCell(index).toString())) {
									try {
										String date = entry.getCell(index)
												.toString();
										manualCharge.setDateOfPayment(new Date(date));
									} catch (Exception e) {
										errorMessage
												.append("Date May Be Wrong Format");
										validaterow = false;
									}
								} else {
									errorMessage
											.append("Date Is Empty Or Null");
									validaterow = false;
								}
							} catch (NullPointerException e) {
								errorMessage
										.append("The column 'Date' doesn't exist");
								validaterow = false;
							}

						} else {
							errorMessage
									.append("Payment Details May Be Null Or Blank Or Payment Details with '"
											+ entry.getCell(index).toString()
											+ "' will Not Accepted .");
							validaterow = false;
						}
						if (cellIndexMap.get(columHeaderMap
								.get("Sales Channel")) != null
								&& manualCharge != null) {
							int indexchannel = cellIndexMap.get(columHeaderMap
									.get("Sales Channel"));
							manualCharge.setPartner(entry.getCell(indexchannel)
									.toString());
							manualCharge.setUploadDate(new Date());
						}

						if (validaterow) {
							manualChargesList.add(manualCharge);
						} else {
							errorSet.add(errorMessage.toString());
						}
					}
				} catch (Exception e) {
					log.error("Failed by seller: " + sellerId, e);
					errorMessage.append("Invalid Input !");
					errorSet.add(errorMessage.toString());
				}
			}
			if (comboPaymentMap != null && comboPaymentMap.size() != 0) {
				double lastAmt = 0;
				for (Entry<String, OrderPaymentBean> entryz : comboPaymentMap.entrySet()) {
					entryz.getValue().setPaymentFileName(uploadFileName);
					double finalCharge = entryz.getValue().getPositiveAmount() - entryz.getValue().getNegativeAmount();
					if(easyShipMap.size() != 0){
						String CID = entryz.getValue().getChannelOrderId()
								.substring(0, entryz.getValue().getChannelOrderId().indexOf(GlobalConstant.orderUniqueSymbol));
						
						if(easyShipMap.containsKey(CID)){
							finalCharge = finalCharge + easyShipMap.get(CID);
						}
					}					
					List<Order> comboOrderList = orderService.searchAsIsOrder("typeIdentifier", entryz.getKey(), sellerId);
					if(comboOrderList != null && comboOrderList.size() != 0){
						lastAmt = finalCharge / comboOrderList.size();
						for (Order ord : comboOrderList){
							entryz.getValue().setChannelOrderId(ord.getChannelOrderID());
							if(lastAmt < 0){
								entryz.getValue().setNegativeAmount(Math.abs(lastAmt));
								entryz.getValue().setPositiveAmount(0);
							} else {
								entryz.getValue().setPositiveAmount(lastAmt);
								entryz.getValue().setNegativeAmount(0);
							}
							order = orderService.addOrderPayment(skucode, entryz
									.getValue().getChannelOrderId(), ConverterClass
									.prepareOrderPaymentModel(entryz.getValue()),
									sellerId);
						}
					}						
					if (order != null) {
						// order.getPaymentUpload().add(paymentUpload);
						// paymentUpload.getOrders().add(order);
						generatePaymentUpload = true;
					}
				}
			}
			if (paymentMap != null) {
				for (Entry<String, OrderPaymentBean> entryz : paymentMap.entrySet()) {
					entryz.getValue().setPaymentFileName(uploadFileName);
					double finalCharge = entryz.getValue().getPositiveAmount() - entryz.getValue().getNegativeAmount();
					if(easyShipMap.size() != 0){
						String CID = entryz.getValue().getChannelOrderId()
								.substring(0, entryz.getValue().getChannelOrderId().indexOf(GlobalConstant.orderUniqueSymbol));
						
						if(easyShipMap.containsKey(CID)){
							finalCharge = finalCharge + easyShipMap.get(CID);
						}
					}
					if(finalCharge < 0){
						entryz.getValue().setNegativeAmount(Math.abs(finalCharge));
						entryz.getValue().setPositiveAmount(0);
					} else {
						entryz.getValue().setPositiveAmount(finalCharge);
						entryz.getValue().setNegativeAmount(0);
					}
					order = orderService.addOrderPayment(skucode, entryz
							.getValue().getChannelOrderId(), ConverterClass
							.prepareOrderPaymentModel(entryz.getValue()),
							sellerId);
					if (order != null) {
						// order.getPaymentUpload().add(paymentUpload);
						// paymentUpload.getOrders().add(order);
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
				uploadPaymentId = paymentUploadService.addPaymentUpload(
						paymentUpload, sellerId);
			}
			if (manualChargesList != null && manualChargesList.size() != 0) {
				for (ManualCharges manuals : manualChargesList) {
					try {
						manuals.setChargesDesc(uploadPaymentId);
						expenseService.addExpense(
								new Expenses("Manual Charges", uploadPaymentId,
										"Manual Charges", new Date(), manuals
												.getDateOfPayment(), manuals
												.getPaidAmount(), manuals
												.getPartner() != null ? manuals
												.getPartner() : "Amazon",
										sellerId), sellerId);
					} catch (Exception e) {
						log.error("Failed! by SellerId : " + sellerId, e);
					}
				}
				System.out.println(manualChargesList.size());
				manualChargesService.addListManualCharges(manualChargesList,
						sellerId);
			}
			uploadResultXLS(offices, "Amazon_Payment", uploadFileName, errorSet, path,
					sellerId, uploadReport);
		} catch (Exception e) {
			addErrorUploadReport("Amazon_Payment", sellerId,
					uploadReport);
			log.error("Failed by seller: " + sellerId, e);
		}
	}

	public void saveLimeroadOrderContents(MultipartFile file, int sellerId,
			String path, UploadReport uploadReport) throws IOException {
		log.info("$$$ saveLimeroadOrderContents starts : SaveMappedFiles $$$");

		ChannelUploadMapping chanupload = null;
		Map<String, String> columHeaderMap = new LinkedHashMap<String, String>();
		Map<String, Integer> cellIndexMap = new LinkedHashMap<String, Integer>();
		StringBuffer errorMessage = new StringBuffer();
		CustomerBean customerBean = null;
		Partner partner = null;
		Events event = null;
		List<Order> saveList = new ArrayList<Order>();
		Map<String, String> idsList = new HashMap<String, String>();
		Map<String, OrderBean> returnOrderMap = new LinkedHashMap<>();
		OrderBean order = null;
		int noOfEntries = 1;
		OrderTaxBean otb = null;
		HSSFRow entry;
		boolean validaterow = true;
		Set<String> errorSet = new HashSet<String>();
		Map<String, String> duplicateKey = new HashMap<String, String>();
		String uploadFileName = "";
		try {
			chanupload = uploadMappingService.getChannelUploadMapping(
					"Limeroad", "order");
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
			uploadFileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")) + new Date().getTime();
			entry = worksheet.getRow(0);
			for (int cellIndex = 0; cellIndex < entry
					.getPhysicalNumberOfCells(); cellIndex++) {
				if (entry.getCell(cellIndex)!=null&&columHeaderMap.containsValue(entry.getCell(cellIndex)
						.toString())) {
					cellIndexMap.put(entry.getCell(cellIndex).toString(),
							cellIndex);
				}

			}
			/* SKUList = productService.listProductSKU(sellerId); */
			idsList = orderService.listOrderIds("channelOrderID", sellerId);
			log.info(noOfEntries);
			log.debug("After getting no of rows" + noOfEntries);
			for (int rowIndex = 1; rowIndex < noOfEntries; rowIndex++) {
				errorMessage = new StringBuffer("Row :" + (rowIndex) + ":");
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				order = new OrderBean();
				customerBean = new CustomerBean();
				otb = new OrderTaxBean();
				int index = 0;
				Product product = null;
				ProductConfig productConfig = null;
				List<ProductConfig> productConfigs = null;
				TaxCategory taxcat = null;
				errorMessage = new StringBuffer("Row :" + (rowIndex) + ":");
				String channelorderid=null;
				try {

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Sales Channel"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							partner = partnerService.getPartner(
									entry.getCell(index).toString(), sellerId);
							if (partner != null)
								order.setPcName(partner.getPcName());
							else {
								order.setPcName(entry.getCell(index).toString());
								errorMessage.append(" Partner do not exist;");
								validaterow = false;
							}
						} else {
							errorMessage.append(" Partner Name is null;");
							validaterow = false;
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("The column 'Sales Channel' doesn't exist");
						validaterow = false;
					}

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Channel Order ID"));
						int skuIndex = cellIndexMap.get(columHeaderMap
								.get("SkUCode"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK
								&& entry.getCell(skuIndex) != null
								&& entry.getCell(skuIndex).getCellType() != HSSFCell.CELL_TYPE_BLANK) {

							entry.getCell(index).setCellType(
									HSSFCell.CELL_TYPE_STRING);
							entry.getCell(skuIndex).setCellType(
									HSSFCell.CELL_TYPE_STRING);
							channelorderid= "";
							if (partner != null) {
								productConfigs = productService
										.getProductConfig(
												entry.getCell(skuIndex)
														.toString().trim()
														.toUpperCase(),
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
										if (entry.getCell(index).toString()
												.contains("S")
												&& productConfig
														.getVendorSkuRef() != null)
											channelorderid = entry
													.getCell(index)
													.toString()
													.substring(
															0,
															entry.getCell(index)
																	.toString()
																	.indexOf(
																			"S"))
													+ GlobalConstant.orderUniqueSymbol
													+ productConfig
															.getVendorSkuRef();

										else if (!entry.getCell(index)
												.toString().contains("S")
												&& productConfig
														.getVendorSkuRef() != null)
											channelorderid = entry.getCell(index)
													.toString()
													+ GlobalConstant.orderUniqueSymbol
													+ productConfig
															.getVendorSkuRef();

									} catch (Exception e) {

									}

								} else {
									errorMessage
											.append("No Product with this Channel & SKU.");
									validaterow = false;
								}
							}

						/*	if (idsList == null || !idsList.containsKey(id)
									&& !duplicateKey.containsKey(id)
									&& productConfig != null) {
								order.setChannelOrderID(id);
								order.setProductSkuCode(productConfig
										.getProductSkuCode());
								order.setPartnerCommission(productConfig.getCommision());
								duplicateKey.put(id, "");
							} else {
								order.setChannelOrderID(entry.getCell(index)
										.toString());
								errorMessage
										.append(" Channel OrderId is already present. ");
								validaterow = false;
							}*/
						} else {
							errorMessage.append(" Channel OrderId orSKU code is null ");
							validaterow = false;
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("The column 'Order id' or 'Vendor Code' doesn't exist");
						validaterow = false;
					} catch (Exception e) {
						errorMessage.append("Invalid Order ID..");
						validaterow = false;
					}

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Order Recieved Date"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							try {
								String date = entry.getCell(index).toString();
								if (date.contains(" "))
									date = date.substring(0, date.indexOf(" "));
								order.setOrderDate(new Date(date));
							} catch (Exception e) {
								errorMessage
										.append(" Order Received Date format is wrong ,enter mm/dd/yyyy,");
								validaterow = false;
							}

						} else {
							errorMessage
									.append(" Order Recieved Date is null;");
							validaterow = false;
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("The column 'Date Time' doesn't exist");
						validaterow = false;
					}

					if(channelorderid != null&&order.getOrderDate()!=null){
						String itemID=format.format(order.getOrderDate());
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
									errorMessage.append("Channel Order Id is already present.");
									validaterow = false;
								}
							} else {
								errorMessage.append("Channel Order Id is already present.");
								validaterow = false;
							}																								
						}
					}
					/*
					 * try { index =
					 * cellIndexMap.get(columHeaderMap.get("SkUCode")); if
					 * (entry.getCell(index) != null &&
					 * entry.getCell(index).getCellType() !=
					 * HSSFCell.CELL_TYPE_BLANK) { if
					 * (SKUList!=null&&SKUList.contains(entry.getCell(index)
					 * .toString())) {
					 * order.setProductSkuCode(entry.getCell(index)
					 * .toString()); } else {
					 * errorMessage.append(" Product SKU does not exist;");
					 * validaterow = false; } } else {
					 * errorMessage.append(" Product SKU is null;"); validaterow
					 * = false; } } catch (NullPointerException e) {
					 * errorMessage
					 * .append("The column 'Vendor Code' doesn't exist");
					 * validaterow = false; }
					 */

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer Name"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							customerBean.setCustomerName(entry.getCell(index)
									.toString());
						}
					} catch (NullPointerException e) {

					}

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Payment Type"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							if (entry.getCell(index).toString().toUpperCase()
									.contains("PREPAID")) {
								order.setPaymentType("Prepaid");
							} else if (entry.getCell(index).toString()
									.toUpperCase().contains("COD")) {
								System.out.println(entry.getCell(index)
										.toString().toUpperCase());
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
								.append("The column 'Order Type' doesn't exist");
						validaterow = false;
					}

					if (cellIndexMap.get(columHeaderMap.get("AWB No")) != null) {
						index = cellIndexMap.get(columHeaderMap.get("AWB No"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							entry.getCell(index).setCellType(
									HSSFCell.CELL_TYPE_STRING);
							order.setAwbNum(entry.getCell(index).toString());
						}
					}

					try {
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
						errorMessage
								.append("The column 'Invoice No.' doesn't exist");
						validaterow = false;
					}

					if (cellIndexMap.get(columHeaderMap
							.get("Secondary OrderID")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Secondary OrderID"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							entry.getCell(index).setCellType(
									HSSFCell.CELL_TYPE_STRING);
							order.setSubOrderID(entry.getCell(index).toString());
						}
					}
					if (cellIndexMap.get(columHeaderMap.get("PIreferenceNo")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("PIreferenceNo"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							order.setPIreferenceNo(entry.getCell(index)
									.toString());
						}
					}
					if (cellIndexMap
							.get(columHeaderMap.get("Logistic Partner")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Logistic Partner"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							order.setLogisticPartner(entry.getCell(index)
									.toString());
						}
					}
					if (cellIndexMap.get(columHeaderMap.get("Order MRP")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Order MRP"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							try {
								order.setOrderMRP(Double.parseDouble(entry
										.getCell(index).toString()));
							} catch (NumberFormatException e) {
								log.error("Failed! by SellerId : " + sellerId,
										e);
								errorMessage
										.append(" Order MRP should be a number ");
							}
						}
					}

					try {
						index = cellIndexMap
								.get(columHeaderMap.get("Order SP"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							try {
								order.setOrderSP(Double.parseDouble(entry
										.getCell(index).toString()));
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
								.append("The column 'Order SP' doesn't exist");
						validaterow = false;
					}

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Order Shipped Date"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							try {
								String date = entry.getCell(index).toString();
								order.setShippedDate(new Date(date));
							} catch (Exception e) {
								errorMessage
										.append(" Shipped Date formate is wrong ,enter mm/dd/yyyy,");
								validaterow = false;
							}
						} else {
							index = cellIndexMap.get(columHeaderMap
									.get("Alt Shipped Date"));
							if (entry.getCell(index) != null
									&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
								try {
									if (HSSFDateUtil.isCellDateFormatted(entry
											.getCell(index))) {
										order.setShippedDate(entry.getCell(index)
												.getDateCellValue());
									} else {
										order.setShippedDate(new Date(entry.getCell(index)
												.toString()));
									}
								} catch (Exception e) {
									errorMessage
											.append(" Date Time format is wrong ,expecting formated date");
									validaterow = false;
								}
						}
					} 
					}catch (NullPointerException e) {
						errorMessage
								.append("The column 'Order Shipped Date' doesn't exist");
						validaterow = false;
					}

					try {
						index = cellIndexMap
								.get(columHeaderMap.get("Quantity"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							try {
								if ((int) Float.parseFloat(entry.getCell(index)
										.toString()) != 0) {
									order.setQuantity((int) Float
											.parseFloat(entry.getCell(index)
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
						errorMessage.append("The column 'Qty' doesn't exist");
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
									if (entry.getCell(index) != null
											&& entry.getCell(index)
													.getCellType() != HSSFCell.CELL_TYPE_BLANK) {

										try {
											order.setGrossNetRate(Double
													.parseDouble(entry.getCell(
															index).toString()));
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
									if (entry.getCell(index) != null
											&& entry.getCell(index)
													.getCellType() != HSSFCell.CELL_TYPE_BLANK) {
										try {
											order.setGrossNetRate(Double
													.parseDouble(entry.getCell(
															index).toString()));
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
					if (cellIndexMap.get(columHeaderMap.get("Customer Email")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer Email"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							
							customerBean.setCustomerEmail(entry.getCell(index)
									.toString());
						}
					}
					if (cellIndexMap.get(columHeaderMap
							.get("Customer Phone No")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer Phone No"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							entry.getCell(index).setCellType(
									HSSFCell.CELL_TYPE_STRING);
							if (entry.getCell(index).toString().length() == 10
									&& !entry.getCell(index).toString()
											.contains("9999999999")) {
								if (entry.getCell(index).toString()
										.matches("[0-9]+")
										&& entry.getCell(index).toString()
												.length() > 2) {
									customerBean.setCustomerPhnNo(entry
											.getCell(index).toString());
								}
							}
						}
					}
					if (cellIndexMap.get(columHeaderMap.get("Customer City")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer City"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							customerBean.setCustomerCity(entry.getCell(index)
									.toString());
						}
					}
					if (cellIndexMap.get(columHeaderMap.get("Customer Address")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer Address"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							customerBean.setCustomerAddress(entry
									.getCell(index).toString());
						}
					}
					try {
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
						} else if(customerBean.getCustomerAddress()!=null)
						{
							 Pattern p = Pattern.compile(regexForZipcode);   // the pattern to search for
							    Matcher m = p.matcher(customerBean.getCustomerAddress());
							    if(m.find())
							    	customerBean.setZipcode(m.group());
							    else
							    {
							    	errorMessage.append("Customer zipcode is blank and no zipcode found in address ");
									validaterow = false;
							    }
							    	
						}
						else{
							errorMessage.append("Customer zipcode is blank ");
							validaterow = false;
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("The column 'Pincode' doesn't exist");
						validaterow = false;
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

					if (cellIndexMap.get(columHeaderMap.get("Customer Email")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Seller Note"));
						if (entry.getCell(index) != null
								&& StringUtils.isNotBlank(entry.getCell(index)
										.toString())) {
							order.setSellerNote(entry.getCell(index).toString());

						}
					}

					// Pre save to generate id for use in hierarchy
					if (validaterow) {
						order.setOrderFileName(uploadFileName);
						order.setCustomer(customerBean);
						order.setOrderTax(otb);
						/*
						 * orderService.addOrder(ConverterClass.prepareModel(order
						 * ), sellerId);
						 */
						System.out.println(" Adding order to save list : "
								+ order.getChannelOrderID());
						saveList.add(ConverterClass.prepareModel(order));
					} else {
						order.setCustomer(customerBean);
						order.setOrderTax(otb);
						log.debug(" Error while saving order : "
								+ order.getChannelOrderID()
								+ " errorMessage : " + errorMessage);
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
			uploadResultXLS(offices, "Limeroad_Order_Upload", uploadFileName, errorSet, path,
					sellerId, uploadReport);

		} catch (Exception e) {
			log.debug("Inside save contents exception :"
					+ e.getLocalizedMessage());
			e.printStackTrace();
			addErrorUploadReport("Limeroad_Order_Upload", sellerId,
					uploadReport);
			log.error("Failed! by SellerId : " + sellerId, e);
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
		Map<String, String> duplicateKey = new HashMap<String, String>();
		OrderPaymentBean paymentBean = null;
		String uploadFileName = "";
		try {
			chanupload = uploadMappingService.getChannelUploadMapping(
					"Limeroad", "Payment");
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
			uploadFileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")) + new Date().getTime();
			entry = worksheet.getRow(0);
			for (int cellIndex = 0; cellIndex < entry
					.getPhysicalNumberOfCells(); cellIndex++) {
				/*
				 * if (columHeaderMap.containsValue(entry.getCell(cellIndex)
				 * .toString())) {
				 * cellIndexMap.put(entry.getCell(cellIndex).toString(),
				 * cellIndex); }
				 */
				cellIndexMap
						.put(entry.getCell(cellIndex).toString(), cellIndex);
			}
			for (int rowIndex = 1; rowIndex < noOfEntries; rowIndex++) {
				errorMessage = new StringBuffer("Row :" + (rowIndex) + ":");
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				double amount = 0;
				int index = 0;
				int skuIndex = 0;
				String channelheader = null;
				channelOrderId = null;
				errorMessage = new StringBuffer("Row :" + (rowIndex) + ":");
				boolean combo=false;
				List<Order> onj=null;
				Date recievedDate=null;
				boolean orderavailable=false;

				// Code for fetching right colum
				try {
					if (cellIndexMap.get(columHeaderMap.get("ChannelOrderId")) != null
							&& cellIndexMap.get(columHeaderMap.get("SKU")) != null) {
						channelheader = columHeaderMap.get("ChannelOrderId");
						index = cellIndexMap.get(channelheader);
						skuIndex = cellIndexMap.get(columHeaderMap.get("SKU"));
					} else {
						errorMessage
								.append("The column 'Order ID' or 'Style Code' doesn't exist");
						validaterow = false;
					}
					try {
						int dateindex = cellIndexMap.get(columHeaderMap
								.get("Order Date"));
						if (entry.getCell(dateindex) != null
								&& entry.getCell(dateindex).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							try {
								if (HSSFDateUtil.isCellDateFormatted(entry
										.getCell(dateindex))) {
									recievedDate=entry.getCell(dateindex)
											.getDateCellValue();
								} else {
									String date = entry.getCell(dateindex)
											.toString();
									recievedDate=new Date(date);
								}
							} catch (Exception e) {
							}

						} 
					} catch (NullPointerException e) {
							}
					if (entry.getCell(index) != null
							&& StringUtils.isNotBlank(entry.getCell(index)
									.toString())
							&& entry.getCell(skuIndex) != null
							&& StringUtils.isNotBlank(entry.getCell(skuIndex)
									.toString())) {
						entry.getCell(index).setCellType(
								HSSFCell.CELL_TYPE_STRING);
						channelOrderId = entry.getCell(index).toString()
								+ GlobalConstant.orderUniqueSymbol
								+ entry.getCell(skuIndex).toString().trim()
										.toUpperCase();
						onj = orderService.searchAsIsOrder(
								"channelOrderID", channelOrderId, sellerId);
						if (onj != null) {
							if (onj.size() == 1
									&& onj.get(0).getChannelOrderID()
											.equals(channelOrderId)) {
								channelOrderId = onj.get(0).getChannelOrderID();
							} 
							else if (onj.size() > 1)
							{
								for(Order ordcheck:onj)
								{
								
								if(ordcheck.getTypeIdentifier()!=null)
								{
									if(onj.get(0).getTypeIdentifier().contains(entry.getCell(index).toString()))
									combo=true;
									else
										combo=false;
								}
								else if (recievedDate!=null)
								{
									if(format.format(ordcheck.getOrderDate()).equals(format.format(recievedDate)))
									{
										orderavailable=true;
										channelOrderId=ordcheck.getChannelOrderID();
									}
								}
								else
								{
									errorMessage
									.append("Multiple Orders With Channel Order ID.");
							validaterow = false;
								}
								
								}
								if(!(orderavailable||combo))
								{
									errorMessage
									.append("Multiple Orders With Channel Order ID.");
							validaterow = false;
								}
							}
							else {
								errorMessage
										.append("No Orders With Channel Order ID.");
								validaterow = false;
							}
							paymentBean = new OrderPaymentBean();
							try {
								index = cellIndexMap.get(columHeaderMap
										.get("Payment Date"));
								if (entry.getCell(index) != null
										&& StringUtils.isNotBlank(entry
												.getCell(index).toString())) {
									String date = entry.getCell(index)
											.toString();
									paymentBean
											.setDateofPayment(new Date(date));
								} else {
									errorMessage
											.append(" Payment Date format is wrong or null");
									validaterow = false;
								}
							} catch (NullPointerException e) {
								errorMessage
										.append("The column 'Payment Date' doesn't exist");
								validaterow = false;
							}

							try {
								index = cellIndexMap.get(columHeaderMap
										.get("Recieved Amount"));
								if (entry.getCell(index) != null
										&& StringUtils.isNotBlank(entry
												.getCell(index).toString())) {
									try {
										amount = entry.getCell(index)
												.getNumericCellValue();
										if (amount > 0) {
											paymentBean
													.setPositiveAmount(paymentBean
															.getPositiveAmount()
															+ amount);
										} else if (amount < 0) {
											paymentBean
													.setNegativeAmount(Math.abs(paymentBean
															.getNegativeAmount()
															+ amount));
										}
									} catch (Exception e) {
										log.error("Failed by seller "
												+ sellerId, e);
										errorMessage
												.append(" Payment Amount cell is corrupted");
										validaterow = false;
									}
								} else {
									errorMessage
											.append("Amount format is wrong or null.");
									validaterow = false;
								}
							} catch (NullPointerException e) {
								errorMessage
										.append("The column 'Outstanding Payable to Vendors (Final)' doesn't exist");
								validaterow = false;
							}

						} else {
							channelOrderId = entry.getCell(index).toString();
							errorMessage
									.append(" Channel OrderId not present ");
							validaterow = false;
						}

					} else {
						channelOrderId = entry.getCell(index).toString();
						errorMessage
								.append(" ChannelOrderId & SKU May be Blank or Null");
						validaterow = false;
					}
					if (validaterow) {

						// Code for saving extra payment varibale in order
						List<PaymentVariables> payvarlist = new ArrayList<PaymentVariables>();
						for (String s : GlobalConstant.limeroadpaymentvariablesList) {
							PaymentVariables payvar = new PaymentVariables();
							try {
								if (cellIndexMap.containsKey(s)) {
									index = cellIndexMap.get(s);
									if (entry.getCell(index) != null
											&& StringUtils.isNotBlank(entry
													.getCell(index).toString())) {
										payvar.setPayKey(s);
										payvar.setPayValue(entry.getCell(index)
												.toString());
										payvarlist.add(payvar);
									}

								}
							} catch (Exception e) {
								log.warn(
										"Failed by seller ID : "
												+ sellerId
												+ " FlipkatyPayment : Error in reading field "
												+ s, e);
							}

						}
						paymentBean.setPaymentFileName(uploadFileName);
						
						if (amount > 0) {
							totalpositive = totalpositive + amount;
						} else if (amount < 0) {
							totalnegative = totalnegative + Math.abs(amount);
						}
						if(combo)
						{
							
							 if(onj!=null&&onj.size()>0)
							 {
								 if(amount>0)
								 {
									 paymentBean.setPositiveAmount(paymentBean.getPositiveAmount()/onj.size());
								 }
								 else{
									 paymentBean.setNegativeAmount(paymentBean.getNegativeAmount()/onj.size());
								 }
							 for(Order ord:onj)
							 {
								 order = orderService.addOrderPayment(skucode,
										 ord.getChannelOrderID(), ConverterClass
													.prepareOrderPaymentModel(paymentBean),
											sellerId);
								
							 }
							 }
						}
						else{
							paymentBean.setPaymentVar(payvarlist);

						order = orderService.addOrderPayment(skucode,
								channelOrderId, ConverterClass
										.prepareOrderPaymentModel(paymentBean),
								sellerId);
						}
					} else {
						errorSet.add(errorMessage.toString());
					}
					if (order != null) {
						if (!duplicateKey.containsKey(channelOrderId)) {
							// order.getPaymentUpload().add(paymentUpload);
							// paymentUpload.getOrders().add(order);
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
			uploadResultXLS(offices, "Limeroad_Payment", uploadFileName, errorSet, path,
					sellerId, uploadReport);
		} catch (Exception e) {
			addErrorUploadReport("Limeroad_Payment", sellerId,
					uploadReport);
			log.error("Failed by seller: " + sellerId, e);
		}
	}

	public void saveJabongOrderContents(MultipartFile file, int sellerId,
			String path, UploadReport uploadReport) throws IOException {
		log.info("$$$ saveJabongOrderContents starts : SaveMappedFiles $$$");

		ChannelUploadMapping chanupload = null;
		Map<String, String> columHeaderMap = new LinkedHashMap<String, String>();
		Map<String, Integer> cellIndexMap = new LinkedHashMap<String, Integer>();
		StringBuffer errorMessage = new StringBuffer();
		CustomerBean customerBean = null;
		Partner partner = null;
		Events event = null;
		List<Order> saveList = new ArrayList<Order>();
		Map<String, String> idsList = new HashMap<String, String>();
		Map<String, OrderBean> returnOrderMap = new LinkedHashMap<>();
		OrderBean order = null;
		int noOfEntries = 1;
		OrderTaxBean otb = null;
		HSSFRow entry;
		boolean validaterow = true;
		Set<String> errorSet = new HashSet<String>();
		Map<String, String> duplicateKey = new HashMap<String, String>();
		String uploadFileName = "";
		try {
			chanupload = uploadMappingService.getChannelUploadMapping("Jabong",
					"order");
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
			uploadFileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")) + new Date().getTime();
			entry = worksheet.getRow(0);
			for (int cellIndex = 0; cellIndex < entry
					.getPhysicalNumberOfCells(); cellIndex++) {
				if (entry.getCell(cellIndex)!=null&&columHeaderMap.containsValue(entry.getCell(cellIndex)
						.toString())) {
					cellIndexMap.put(entry.getCell(cellIndex).toString(),
							cellIndex);
				}
			}
			idsList = orderService.listOrderIds("channelOrderID", sellerId);
			log.info(noOfEntries);
			log.debug("After getting no of rows" + noOfEntries);
			for (int rowIndex = 1; rowIndex < noOfEntries; rowIndex++) {
				errorMessage = new StringBuffer("Row :" + (rowIndex) + ":");
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				order = new OrderBean();
				customerBean = new CustomerBean();
				otb = new OrderTaxBean();
				int index = 0;
				Product product = null;
				ProductConfig productConfig = null;
				List<ProductConfig> productConfigs = null;
				TaxCategory taxcat = null;
				String channelorderid = null;
				errorMessage = new StringBuffer("Row :" + (rowIndex) + ":");
				try {

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Sales Channel"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							partner = partnerService.getPartner(
									entry.getCell(index).toString(), sellerId);
							if (partner != null)
								order.setPcName(partner.getPcName());
							else {
								order.setPcName(entry.getCell(index).toString());
								errorMessage.append(" Partner do not exist;");
								validaterow = false;
							}
						} else {
							errorMessage.append(" Sales Channel is null;");
							validaterow = false;
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("Column 'Sales Channel' doesn't exist");
						validaterow = false;
					}

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("ChannelOrderID"));
						int skuIndex = cellIndexMap.get(columHeaderMap
								.get("SkUCode"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK
								&& entry.getCell(skuIndex) != null
								&& entry.getCell(skuIndex).getCellType() != HSSFCell.CELL_TYPE_BLANK) {

							entry.getCell(index).setCellType(
									HSSFCell.CELL_TYPE_STRING);
							if (partner != null) {
								productConfigs = productService
										.getProductConfig(
												entry.getCell(skuIndex)
														.toString().trim()
														.toUpperCase(),
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
											channelorderid = entry.getCell(
													index).toString()
													+ GlobalConstant.orderUniqueSymbol
													+ productConfig
															.getVendorSkuRef();
										} else {
											errorMessage
													.append(" Vendor SKU mapping not present");
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
								.append("The column 'Order Number' doesn't exist");
						validaterow = false;
					}

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("OrderRecievedDate"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							try {
								String date = entry.getCell(index).toString();
								order.setOrderDate(new Date(date));
							} catch (Exception e) {
								errorMessage
										.append(" Order Received Date format is wrong ,enter mm/dd/yyyy,");
								validaterow = false;
							}

						} else {
							errorMessage
									.append(" Order Recieved Date is null;");
							validaterow = false;
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("The column 'Created at' doesn't exist");
						validaterow = false;
					}

					/*
					 * try { index =
					 * cellIndexMap.get(columHeaderMap.get("SkUCode")); if
					 * (entry.getCell(index) != null &&
					 * entry.getCell(index).getCellType() !=
					 * HSSFCell.CELL_TYPE_BLANK) { if
					 * (SKUList!=null&&SKUList.contains(entry.getCell(index)
					 * .toString())) {
					 * order.setProductSkuCode(entry.getCell(index)
					 * .toString()); } else { errorMessage
					 * .append(" Product SKU does not exist;"); validaterow =
					 * false; } } else {
					 * errorMessage.append(" Product SKU is null;"); validaterow
					 * = false; } } catch (NullPointerException e) {
					 * errorMessage
					 * .append("The column 'SKU Code' doesn't exist");
					 * validaterow = false; }
					 */

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer Name"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							customerBean.setCustomerName(entry.getCell(index)
									.toString());
						}
					} catch (NullPointerException e) {

					}

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Payment Type"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							if (entry.getCell(index).toString().toUpperCase()
									.contains("PAYU")) {
								order.setPaymentType("Prepaid");
							} else if (entry.getCell(index).toString()
									.toUpperCase().contains("CashOnDelivery")) {
								order.setPaymentType("COD");
							} else if (entry.getCell(index).toString()
									.toUpperCase().contains("OTHERS")) {
								order.setPaymentType("Others");
							} else {
								order.setPaymentType("Prepaid");
							}
						} else {
							errorMessage.append(" Payment type is null");
							validaterow = false;
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("The column 'Payment Method' doesn't exist");
						validaterow = false;
					}

					if (cellIndexMap.get(columHeaderMap.get("AWB No")) != null) {
						index = cellIndexMap.get(columHeaderMap.get("AWB No"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							entry.getCell(index).setCellType(
									HSSFCell.CELL_TYPE_STRING);
							order.setAwbNum(entry.getCell(index).toString());
						}
					}

					try {
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
					}

					try {
						if (cellIndexMap.get(columHeaderMap
								.get("Secondary OrderID")) != null) {
							index = cellIndexMap.get(columHeaderMap
									.get("Secondary OrderID"));
							if (entry.getCell(index) != null
									&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
								entry.getCell(index).setCellType(
										HSSFCell.CELL_TYPE_STRING);
								order.setSubOrderID(entry.getCell(index)
										.toString());
								if (channelorderid != null){
									channelorderid = channelorderid
											+ GlobalConstant.orderUniqueSymbol
											+ order.getSubOrderID();
										if( (idsList == null || !idsList
												.containsKey(channelorderid)
												&& !duplicateKey
														.containsKey(channelorderid)
												&& productConfig != null)) {

										order.setChannelOrderID(channelorderid);
										order.setProductSkuCode(productConfig
												.getProductSkuCode());
										order.setPartnerCommission(productConfig.getCommision());
										duplicateKey
												.put(channelorderid, "");
									} else {
										errorMessage
												.append(" Channel OrderId is already present ");
										validaterow = false;
									}
								}
							}
						} else {
							errorMessage
									.append(" Order Item Id is null;");
							validaterow = false;
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("The column 'Order Item Id' doesn't exist");
						validaterow = false;
					}
					
					if (cellIndexMap.get(columHeaderMap.get("PIreferenceNo")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("PIreferenceNo"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							order.setPIreferenceNo(entry.getCell(index)
									.toString());
						}
					}
					if (cellIndexMap
							.get(columHeaderMap.get("Logistic Partner")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Logistic Partner"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							order.setLogisticPartner(entry.getCell(index)
									.toString());
						}
					}
					if (cellIndexMap.get(columHeaderMap.get("Order MRP")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Order MRP"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							try {
								order.setOrderMRP(Double.parseDouble(entry
										.getCell(index).toString()));
							} catch (NumberFormatException e) {
								log.error("Failed! by SellerId : " + sellerId,
										e);
								errorMessage
										.append(" Order MRP should be a number ");
							}
						}
					}

					try {
						index = cellIndexMap
								.get(columHeaderMap.get("Order SP"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							try {
								order.setOrderSP(Double.parseDouble(entry
										.getCell(index).toString()));
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
								.append("The column 'Paid Price' doesn't exist");
						validaterow = false;
					}

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Order Shipped Date"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							try {
								String date = entry.getCell(index).toString();
								order.setShippedDate(new Date(date));
							} catch (Exception e) {
								errorMessage
										.append(" Shipped Date formate is wrong ,enter mm/dd/yyyy,");
								validaterow = false;
							}
						} else {

							
							index = cellIndexMap.get(columHeaderMap
									.get("Alt Shipped Date"));
							if (entry.getCell(index) != null
									&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
								try {
									if (HSSFDateUtil.isCellDateFormatted(entry
											.getCell(index))) {
										order.setShippedDate(entry.getCell(index)
												.getDateCellValue());
									} else {
										order.setShippedDate(new Date(entry.getCell(index)
												.toString()));
									}
								} catch (Exception e) {
									errorMessage
											.append(" Updated at date format is wrong ,enter mm/dd/yyyy,");
									validaterow = false;
								}
							}
							else
							{
							errorMessage.append(" Shipping Date and Updated at dates are empty");
							validaterow = false;
							}
						
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("The column 'Order Shipped Date' doesn't exist");
						validaterow = false;
					}

					try {
						index = cellIndexMap
								.get(columHeaderMap.get("Quantity"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							try {
								if ((int) Float.parseFloat(entry.getCell(index)
										.toString()) != 0) {
									order.setQuantity((int) Float
											.parseFloat(entry.getCell(index)
													.toString()));
								} else {
									order.setQuantity(1);
								}
							} catch (NumberFormatException e) {
								order.setQuantity(1);
							}
						} else {
							order.setQuantity(1);
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("The column 'Quantity' doesn't exist");
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
									if (entry.getCell(index) != null
											&& entry.getCell(index)
													.getCellType() != HSSFCell.CELL_TYPE_BLANK) {

										try {
											order.setGrossNetRate(Double
													.parseDouble(entry.getCell(
															index).toString()));
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
									if (entry.getCell(index) != null
											&& entry.getCell(index)
													.getCellType() != HSSFCell.CELL_TYPE_BLANK) {
										try {
											order.setGrossNetRate(Double
													.parseDouble(entry.getCell(
															index).toString()));
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
					if (cellIndexMap.get(columHeaderMap.get("Customer Email")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer Email"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {

							customerBean.setCustomerEmail(entry.getCell(index)
									.toString());
						}
					}
					if (cellIndexMap.get(columHeaderMap
							.get("Customer Phone No")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer Phone No"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							entry.getCell(index).setCellType(
									HSSFCell.CELL_TYPE_STRING);
							if (entry.getCell(index).toString().length() == 10
									&& !entry.getCell(index).toString()
											.contains("9999999999")) {
								if (entry.getCell(index).toString()
										.matches("[0-9]+")
										&& entry.getCell(index).toString()
												.length() > 2) {
									customerBean.setCustomerPhnNo(entry
											.getCell(index).toString());
								}
							}
						}
					}
					if (cellIndexMap.get(columHeaderMap.get("Customer City")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer City"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							customerBean.setCustomerCity(entry.getCell(index)
									.toString());
						}
					}
					if (cellIndexMap.get(columHeaderMap.get("Customer Email")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer Email"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							customerBean.setCustomerAddress(entry
									.getCell(index).toString());
						}
					}
					try {
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
								.append("The column 'PIN Code' doesn't exist");
						validaterow = false;
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

					if (cellIndexMap.get(columHeaderMap.get("Seller Note")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Seller Note"));
						if (entry.getCell(index) != null
								&& StringUtils.isNotBlank(entry.getCell(index)
										.toString())) {
							order.setSellerNote(entry.getCell(index).toString());

						}
					}

					// Pre save to generate id for use in hierarchy
					if (validaterow) {
						order.setOrderFileName(uploadFileName);
						order.setCustomer(customerBean);
						order.setOrderTax(otb);
						/*
						 * orderService.addOrder(ConverterClass.prepareModel(order
						 * ), sellerId);
						 */
						
						saveList.add(ConverterClass.prepareModel(order));
					} else {
						order.setCustomer(customerBean);
						order.setOrderTax(otb);
						log.debug(" Error while saving order : "
								+ order.getChannelOrderID()
								+ " errorMessage : " + errorMessage);
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
			uploadResultXLS(offices, "Jabong_Order_Upload", uploadFileName, errorSet, path,
					sellerId, uploadReport);

		} catch (Exception e) {
			log.debug("Inside save contents exception :"
					+ e.getLocalizedMessage());
			e.printStackTrace();
			addErrorUploadReport("Jabong_Order_Upload", sellerId, uploadReport);
			log.error("Failed! by SellerId : " + sellerId, e);
		}
	}

	public void saveJabongPaymentContents(MultipartFile file, int sellerId,
			String path, UploadReport uploadReport) throws IOException {

		log.info("$$$ saveJabongPaymentContents starts : SaveMappedFiles $$$");

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
		List<ManualCharges> manualChargesList = new ArrayList<ManualCharges>();
		/* ManualCharges newmanualCharge = null; */
		boolean generatePaymentUpload = false;
		String uploadPaymentId = null;
		Map<String, String> duplicateKey = new HashMap<String, String>();
		double amount = 0;
		String uploadFileName = "";
		try {
			chanupload = uploadMappingService.getChannelUploadMapping("Jabong",
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
			uploadFileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")) + new Date().getTime();
			entry = worksheet.getRow(0);
			for (int cellIndex = 0; cellIndex < entry
					.getPhysicalNumberOfCells(); cellIndex++) {
				/*
				 * if (columHeaderMap.containsValue(entry.getCell(cellIndex)
				 * .toString())) {
				 * cellIndexMap.put(entry.getCell(cellIndex).toString(),
				 * cellIndex); }
				 */
				cellIndexMap
						.put(entry.getCell(cellIndex).toString(), cellIndex);

			}
			System.out.println(" columHeaderMap : " + columHeaderMap);
			System.out.println(" cellIndexMap : " + cellIndexMap);
			for (int rowIndex = 1; rowIndex < noOfEntries; rowIndex++) {
				errorMessage = new StringBuffer("Row :" + (rowIndex) + ":");
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				int index = 0;
				String channelheader = null;
				channelOrderId = null;
				Partner partner = null;
				errorMessage = new StringBuffer("Row :" + (rowIndex) + ":");
				// Code for fetching right colum
				orderPayment = new OrderPayment();
				boolean combo=false;
				List<Order> onj=null;
				order = null;
				int combocount=0;
				try {
					if (cellIndexMap.get(columHeaderMap.get("ChannelOrderId")) != null) {
						channelheader = columHeaderMap.get("ChannelOrderId");
						index = cellIndexMap.get(channelheader);
					} else {
						errorMessage
								.append("The column 'Order Number' doesn't exist");
						validaterow = false;
					}

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Sales Channel"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							partner = partnerService.getPartner(
									entry.getCell(index).toString(), sellerId);
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

					if (cellIndexMap.get(columHeaderMap.get("ChannelOrderId")) != null
							&& cellIndexMap.get(columHeaderMap
									.get("Secondary OrderID")) != null) {
						channelheader = columHeaderMap.get("ChannelOrderId");
						index = cellIndexMap.get(channelheader);
						int secOrderIndex = cellIndexMap.get(columHeaderMap
								.get("Secondary OrderID"));

						if (entry.getCell(index) != null
								&& StringUtils.isNotBlank(entry.getCell(index)
										.toString())
								&& entry.getCell(secOrderIndex) != null
								&& StringUtils.isNotBlank(entry.getCell(
										secOrderIndex).toString())) {
							entry.getCell(index).setCellType(
									HSSFCell.CELL_TYPE_STRING);
							entry.getCell(secOrderIndex).setCellType(
									HSSFCell.CELL_TYPE_STRING);

							if (partner != null) {

								String itemID = entry.getCell(secOrderIndex)
										.toString();
								channelOrderId = entry.getCell(index)
										.toString()
										+ GlobalConstant.orderUniqueSymbol
										+ itemID;

								onj = orderService.searchAsIsOrder(
										"channelOrderID", channelOrderId,
										sellerId);

								if (onj != null) {
									if (onj.size() > 1) {
										for (Order tmpOrder : onj) {
											if (tmpOrder.getSubOrderID().equalsIgnoreCase(itemID)) {
												channelOrderId = tmpOrder.getChannelOrderID();
											}
											if(tmpOrder.getTypeIdentifier().contains(entry.getCell(index)
													.toString()))
											{
												combo=true;
												combocount++;
											}
										}
									} else if (onj.size() == 1
											&& onj.get(0).getSubOrderID()
													.equalsIgnoreCase(itemID)) {
										channelOrderId = onj.get(0)
												.getChannelOrderID();
									} else {
										errorMessage
												.append("Multiple Orders With Channel Order ID.");
										validaterow = false;
									}
								} else {
									errorMessage
											.append("No order present with that Order ID and Order Item ID.");
									validaterow = false;
								}

								try {
									index = cellIndexMap.get(columHeaderMap
											.get("Payment Date"));
									try {
										if (entry.getCell(index) != null) {
											orderPayment
													.setDateofPayment(new Date(
															entry.getCell(index)
																	.toString()));
										} else {
											errorMessage
													.append(" Payment Date format is wrong or null");
											validaterow = false;
										}
									} catch (Exception e) {
										log.error("Failed by selled ID "
												+ sellerId, e);
										errorMessage
												.append(" Payment Date format is wrong or null");
										validaterow = false;
									}
								} catch (NullPointerException e) {
									errorMessage
											.append("The column 'Payment Date' doesn't exist");
									validaterow = false;
								}

								try {
									index = cellIndexMap.get(columHeaderMap
											.get("Recieved Amount"));
									if (entry.getCell(index) != null
											&& StringUtils.isNotBlank(entry
													.getCell(index).toString())) {
										try {
											amount = entry.getCell(index)
													.getNumericCellValue();
											if (amount > 0) {
												orderPayment
														.setPositiveAmount(amount);
												/*
												 * totalpositive = totalpositive
												 * + amount;
												 */
											} else {
												orderPayment
														.setNegativeAmount(Math
																.abs(amount));
												/*
												 * totalnegative = totalnegative
												 * + Math.abs(amount);
												 */
											}
										} catch (Exception e) {
											log.error("Failed by seller "
													+ sellerId, e);
											errorMessage
													.append(" Payment Amount cell is corrupted");
											validaterow = false;

										}
									} else {
										errorMessage
												.append("Amount format is wrong or null.");
										validaterow = false;
									}
								} catch (NullPointerException e) {
									errorMessage
											.append("The column 'Payable' doesn't exist");
									validaterow = false;
								}
							} else {
								errorMessage.append(" Partner do not exist;");
								validaterow = false;
							}
						} else {
							validaterow = false;
							if (StringUtils.isNotBlank(entry.getCell(index)
									.toString())) {
								errorMessage
										.append(" 'Order Item ID' May be Blank or Null");
							}
						}

					} else {
						errorMessage
								.append("The column 'Order Item ID' or 'Order Number' doesn't exist");
						validaterow = false;
					}

					if (validaterow) {

						// Code for saving extra payment varibale in order
						List<PaymentVariables> payvarlist = new ArrayList<PaymentVariables>();
						for (String s : GlobalConstant.jabongpaymentvariablesList) {
							PaymentVariables payvar = new PaymentVariables();
							try {
								if (cellIndexMap.containsKey(s)) {
									index = cellIndexMap.get(s);
									if (entry.getCell(index) != null
											&& StringUtils.isNotBlank(entry
													.getCell(index).toString())) {
										payvar.setPayKey(s);
										payvar.setPayValue(entry.getCell(index)
												.toString());
										payvarlist.add(payvar);
									}

								}
							} catch (Exception e) {
								log.warn(
										"Failed by seller ID : "
												+ sellerId
												+ " FlipkatyPayment : Error in reading field "
												+ s, e);
							}

						}
						orderPayment.setPaymentFileName(uploadFileName);

						if (amount > 0) {
							totalpositive = totalpositive + amount;
						} else {
							totalnegative = totalnegative + Math.abs(amount);
						}
						if(combo)
						{
							
							 if(onj!=null&&onj.size()>0)
							 {
								 if(amount>0)
								 {
								 orderPayment.setPositiveAmount(orderPayment.getPositiveAmount()/combocount);
								 }
								 else{
									 orderPayment.setNegativeAmount(orderPayment.getNegativeAmount()/combocount);
								 }
							 for(Order ord:onj)
							 {
								 order = orderService.addOrderPayment(skucode,
											ord.getChannelOrderID(), orderPayment, sellerId);
							 }
							 }
						}
						else{
							orderPayment.setPaymentVar(payvarlist);

						order = orderService.addOrderPayment(skucode,
								channelOrderId, orderPayment, sellerId);
						}
					} else {
						if (StringUtils.isNotBlank(errorMessage.toString().trim())) {
							errorSet.add(errorMessage.toString());
						}
					}
					if (order != null) {
						if (!duplicateKey.containsKey(channelOrderId)) {
							// order.getPaymentUpload().add(paymentUpload);
							// paymentUpload.getOrders().add(order);
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
				uploadPaymentId = paymentUploadService.addPaymentUpload(
						paymentUpload, sellerId);
			}
			if (manualChargesList != null && manualChargesList.size() != 0) {
				for (ManualCharges manuals : manualChargesList) {
					try {
						System.out.println(" Payment Upload list created : "
								+ uploadPaymentId);
						manuals.setChargesDesc(uploadPaymentId);

						expenseService.addExpense(
								new Expenses("Manual Charges", uploadPaymentId,
										"Manual Charges", new Date(), manuals
												.getDateOfPayment(), manuals
												.getPaidAmount(), manuals
												.getPartner() != null ? manuals
												.getPartner() : "Jabong",
										sellerId), sellerId);
					} catch (Exception e) {
						log.error("Failed! by SellerId : " + sellerId, e);
					}
				}
				manualChargesService.addListManualCharges(manualChargesList,
						sellerId);
			}
			uploadResultXLS(offices, "Jabong_Payment", uploadFileName, errorSet, path,
					sellerId, uploadReport);
		}

		catch (Exception e) {
			addErrorUploadReport("Jabong_Payment", sellerId,
					uploadReport);
			log.error("Failed by seller: " + sellerId, e);
		}

	}

	public void uploadResultXLS(HSSFWorkbook workbook, String worksheetName, String fileName,
			Set<String> errorSet, String path, int sellerId,
			UploadReport uploadReport) throws ClassNotFoundException,
			CustomException {

		log.info("$$$ uploadResultXLS starts : SaveContents $$$");
		String errorMessage;
		boolean isError = false;
		int noOfRows = 1;
		HSSFSheet worksheet = workbook.getSheetAt(0);
		while (worksheet.getRow(noOfRows) != null) {
			noOfRows++;
		}
		int colNumber = worksheet.getRow(0).getPhysicalNumberOfCells();
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

			/*
			 * properties = PropertiesLoaderUtils.loadProperties(resource);
			 * uploadFilePath = properties.getProperty("uploadreport.path");
			 */

			log.debug("***** uploadFilePath path  : " + uploadFilePath);

			// creates the save directory if it does not exists
			File fileSaveDir = new File(uploadFilePath);
			if (!fileSaveDir.exists()) {
				log.debug(" Directory doesnnt exist");
				fileSaveDir.mkdirs();
			}
			String filePath = "";
			if(fileName != null && !fileName.equals("")) {
				filePath = dataConfig.getUploadReportPath() + File.separator
						+ fileName + ".xls";
				uploadReport.setFileName(fileName);
			} else {
				filePath = dataConfig.getUploadReportPath() + File.separator
						+ worksheetName + "UploadStatus"
						+ new Date().getTime() + ".xls";
				uploadReport.setFileName(worksheetName);
			}
			
			FileOutputStream out = new FileOutputStream(new File(filePath));
			workbook.write(out);
			out.close();
			uploadReport.setFileType(worksheetName);
			uploadReport.setFilePath(filePath);
			uploadReport.setNoOfErrors(errorSet.size());
			uploadReport.setNoOfSuccess((noOfRows - errorSet.size()) - 1);
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
			log.error("Failed! by SellerId : " + sellerId, e);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("Failed! by SellerId : " + sellerId, e);
		}
	}

	public String getSubstringValue(char delimeter, String text) {
		if (text != null && !StringUtils.isBlank(text)) {
			return text.substring(text.indexOf(delimeter) + 1, text.length());
		} else
			return "";
	}

	public void addErrorUploadReport(String worksheetName, int sellerId,
			UploadReport uploadReport) {

		log.info("$$$ addErrorUploadReport starts : SaveContents $$$");

		try {

			uploadReport.setFileType(worksheetName);
			uploadReport.setDescription("Imported");
			uploadReport.setSeller(sellerService.getSeller(sellerId));
			uploadReport.setStatus("Exception");

			reportGeneratorService.addUploadReport(uploadReport, sellerId);
			log.info("$$$ downloadUploadReportXLS ends : SaveContents $$$");

		} catch (Exception e) {
			e.printStackTrace();
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
}
