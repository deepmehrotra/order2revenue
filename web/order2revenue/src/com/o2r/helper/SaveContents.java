package com.o2r.helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
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
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import com.o2r.bean.CustomerBean;
import com.o2r.bean.DebitNoteBean;
import com.o2r.bean.ExpenseBean;
import com.o2r.bean.OrderBean;
import com.o2r.bean.OrderTaxBean;
import com.o2r.bean.PoPaymentBean;
import com.o2r.bean.ProductBean;
import com.o2r.bean.ProductConfigBean;
import com.o2r.dao.AreaConfigDao;
import com.o2r.model.Category;
import com.o2r.model.Events;
import com.o2r.model.ExpenseCategory;
import com.o2r.model.Expenses;
import com.o2r.model.GatePass;
import com.o2r.model.ManualCharges;
import com.o2r.model.NRnReturnCharges;
import com.o2r.model.Order;
import com.o2r.model.OrderPayment;
import com.o2r.model.OrderRTOorReturn;
import com.o2r.model.Partner;
import com.o2r.model.PartnerCategoryMap;
import com.o2r.model.PaymentUpload;
import com.o2r.model.PaymentUpload_Order;
import com.o2r.model.Product;
import com.o2r.model.ProductConfig;
import com.o2r.model.Seller;
import com.o2r.model.SellerAlerts;
import com.o2r.model.TaxCategory;
import com.o2r.model.TaxablePurchases;
import com.o2r.model.UploadReport;
import com.o2r.service.AlertsService;
import com.o2r.service.CategoryService;
import com.o2r.service.CustomerService;
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
import com.o2r.service.TaxablePurchaseService;
import com.o2r.service.UploadMappingService;

@Service("saveContents")
@Transactional
public class SaveContents {

	@Autowired
	private OrderService orderService;
	@Autowired
	private ProductService productService;
	@Autowired
	private PaymentUploadService paymentUploadService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ExpenseService expenseService;
	@Autowired
	private PartnerService partnerService;
	@Autowired
	private TaxDetailService taxDetailService;
	@Autowired
	private SellerService sellerService;
	@Autowired
	private EventsService eventsService;
	@Autowired
	private ReportGeneratorService reportGeneratorService;
	@Autowired
	private AreaConfigDao areaConfigDao;
	@Autowired
	private ManualChargesService manualChargesService;
	@Autowired
	private UploadMappingService uploadMappingService;
	@Autowired
	private SaveMappedFiles saveMappedFiles;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private AlertsService alertService;
	@Autowired
	private TaxablePurchaseService taxablePurchaseService;

	private static final String UPLOAD_DIR = "UploadReport";

	static Logger log = Logger.getLogger(SaveContents.class.getName());
	private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	Properties properties = null;
	org.springframework.core.io.Resource resource = new ClassPathResource(
			"database.properties");

	public Map<String, OrderBean> saveOrderContents(MultipartFile file,
			int sellerId, String path, UploadReport uploadReport)
			throws IOException {
		log.info("$$$ saveOrderContents starts : " + sellerId
				+ " : SaveContents $$$");
		HSSFRow entry;
		Integer noOfEntries = 1;
		boolean validaterow = true;
		Map<String, OrderBean> returnOrderMap = new LinkedHashMap<>();
		OrderBean order = null;
		StringBuffer errorMessage = null;
		CustomerBean customerBean = null;
		OrderTaxBean otb = null;
		Events event = null;
		String uploadFileName = "";
		List<Order> saveList = null;
		Map<String, String> idsList = new HashMap<String, String>();
		Map<String, String> duplicateKey = new HashMap<String, String>();
		try {
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = offices.getSheetAt(0);
			saveList = new ArrayList<Order>();
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			System.out.println(file.getOriginalFilename());
			uploadFileName = file.getOriginalFilename().substring(0,
					file.getOriginalFilename().lastIndexOf("."))
					+ new Date().getTime();
			System.out.println(uploadFileName);
			log.info(noOfEntries.toString());
			log.debug("After getting no of rows" + noOfEntries);
			idsList = orderService.listOrderIds("channelOrderID", sellerId);
			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				try {
					validaterow = true;
					Partner partner = null;
					ProductConfig productConfig = null;
					List<ProductConfig> productConfigs = null;
					entry = worksheet.getRow(rowIndex);
					errorMessage = new StringBuffer("Row :" + (rowIndex - 2)
							+ ":");
					log.debug(" Row index : " + (rowIndex - 2));
					log.debug(entry.getCell(0));
					log.debug(entry.getCell(1));
					log.debug(entry.getCell(2));
					TaxCategory taxcat = null;
					String channelID = null;
					String itemId = null;
					Product product = null;
					order = new OrderBean();
					customerBean = new CustomerBean();
					otb = new OrderTaxBean();
					if (entry.getCell(3) != null
							&& entry.getCell(3).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						partner = partnerService.getPartner(entry.getCell(3)
								.toString(), sellerId);
						if (partner != null)
							order.setPcName(entry.getCell(3).toString());
						else {
							order.setPcName(entry.getCell(3).toString());
							errorMessage.append(" Partner do not exist;");
							validaterow = false;
						}
					} else {
						errorMessage.append(" Partner Name is null;");
						validaterow = false;
					}

					if (entry.getCell(1) != null
							&& entry.getCell(1).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						try {
							if (HSSFDateUtil.isCellDateFormatted(entry
									.getCell(1))) {
								order.setOrderDate(entry.getCell(1)
										.getDateCellValue());
							} else {
								errorMessage
										.append(" Order Received Date format is wrong ,enter mm/dd/yyyy,");
								validaterow = false;
							}
						} catch (Exception e) {
							errorMessage
									.append(" Order Received Date format is wrong ,enter mm/dd/yyyy,");
							validaterow = false;
						}

					} else {
						errorMessage.append(" Order Recieved Date is null;");
						validaterow = false;
					}

					String skuCode = null;
					if (entry.getCell(0) != null
							&& entry.getCell(0).getCellType() != HSSFCell.CELL_TYPE_BLANK
							&& entry.getCell(2) != null
							&& entry.getCell(2).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						entry.getCell(0).setCellType(HSSFCell.CELL_TYPE_STRING);
						entry.getCell(2).setCellType(HSSFCell.CELL_TYPE_STRING);

						log.info(" Getting string value form : "
								+ entry.getCell(0)+" sku "+entry.getCell(2).toString());
						if (partner != null) {
							productConfigs = productService.getProductConfig(
									entry.getCell(2).toString().toUpperCase(),
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
										skuCode = productConfig
												.getVendorSkuRef();
										channelID = entry.getCell(0).toString()
												+ GlobalConstant.orderUniqueSymbol
												+ skuCode;
										order.setPartnerCommission(productConfig
												.getCommision());
									} else {
										errorMessage
												.append("VendorSKU code is not mapped.");
										validaterow = false;
									}
								} catch (Exception e) {

								}
							} else {
								errorMessage
										.append(" No Mapping present for this Channel And SKU");
								validaterow = false;
							}

							System.out.println(" Channel id wiht parent sku : "
									+ channelID);

							if (entry.getCell(8) != null
									&& entry.getCell(8).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
								entry.getCell(8).setCellType(
										HSSFCell.CELL_TYPE_STRING);
								itemId = entry.getCell(8).toString();
								if (itemId.contains("'")) {
									itemId = removeExtraQuote(itemId);
								}
								itemId = removeExtraQuote(itemId);
								order.setSubOrderID(itemId);
								if(partner != null && partner.getPcName().toLowerCase().contains(GlobalConstant.PCFLIPKART)
										&& itemId.contains("OI:")){
									itemId = itemId.replaceAll("OI:", "");												
								}
							}

							if (channelID != null) {
								if (!(partner.getPcName().toLowerCase()
										.contains(GlobalConstant.PCFLIPKART)
										|| partner
												.getPcName()
												.toLowerCase()
												.contains(
														GlobalConstant.PCPAYTM) || partner
										.getPcName().toLowerCase()
										.contains(GlobalConstant.PCJABONG))) {
									String duplicateItemId = null;
									if (!partner.getPcName().toLowerCase()
											.contains(GlobalConstant.PCAMAZON)) {
										duplicateItemId = "";
										itemId = format.format(order
												.getOrderDate());
									}

									if (!idsList.containsKey(channelID)
											&& !duplicateKey
													.containsKey(channelID)) {
										order.setChannelOrderID(channelID);
										if (productConfig != null) {
											order.setProductSkuCode(productConfig
													.getProductSkuCode());
											order.setPartnerCommission(productConfig
													.getCommision());
										}
										duplicateKey.put(channelID, itemId);
									} else {
										if (itemId != null
												&& ((idsList.get(channelID) != null && !idsList
														.get(channelID).equals(
																itemId)) || (duplicateKey
														.containsKey(channelID) && (duplicateKey
														.get(channelID) == null || !duplicateKey
														.get(channelID).equals(
																itemId))))) {
											channelID = channelID
													+ GlobalConstant.orderUniqueSymbol
													+ itemId;
											if (!idsList.containsKey(channelID)
													&& !duplicateKey
															.containsKey(channelID)) {
												order.setChannelOrderID(channelID);
												if (productConfig != null) {
													order.setProductSkuCode(productConfig
															.getProductSkuCode());
													order.setPartnerCommission(productConfig
															.getCommision());
												}
												duplicateKey.put(channelID,
														itemId);
											} else {
												errorMessage
														.append("Channel Order Id is Already Present.");
												validaterow = false;
											}
										} else {
											errorMessage
													.append("Channel Order Id is Already Present.");
											validaterow = false;
										}
									}
									if (duplicateItemId != null)
										itemId = null;
								}
							}

							/*
							 * if (channelID != null &&
							 * !idsList.contains(channelID) &&
							 * !duplicateKey.containsKey(channelID)) {
							 * 
							 * order.setChannelOrderID(channelID); if
							 * (productConfig != null)
							 * order.setProductSkuCode(productConfig
							 * .getProductSkuCode()); else
							 * order.setProductSkuCode(skuCode);
							 * 
							 * if (!(partner.getPcName().toLowerCase()
							 * .contains(GlobalConstant.PCFLIPKART) || partner
							 * .getPcName() .toLowerCase() .contains(
							 * GlobalConstant.PCPAYTM) || partner
							 * .getPcName().toLowerCase()
							 * .contains(GlobalConstant.PCJABONG))) {
							 * 
							 * if (!idsList.containsKey(channelID) &&
							 * !duplicateKey .containsKey(channelID)) {
							 * order.setChannelOrderID(channelID); if
							 * (productConfig != null) {
							 * order.setProductSkuCode(productConfig
							 * .getProductSkuCode());
							 * order.setPartnerCommission(productConfig
							 * .getCommision()); } duplicateKey.put(channelID,
							 * itemId); } else { if (!partner .getPcName()
							 * .toLowerCase() .contains(
							 * GlobalConstant.PCAMAZON)) { itemId =
							 * order.getOrderDate() .toString(); } if (itemId !=
							 * null && ((idsList.get(channelID) != null &&
							 * !idsList .get(channelID).equals( itemId)) ||
							 * (duplicateKey .containsKey(channelID) &&
							 * (duplicateKey .get(channelID) == null ||
							 * !duplicateKey .get(channelID).equals( itemId)))))
							 * { if (!partner .getPcName() .toLowerCase()
							 * .contains( GlobalConstant.PCAMAZON)) { channelID
							 * = channelID + GlobalConstant.orderUniqueSymbol +
							 * order.getOrderDate() .toString(); } else {
							 * channelID = channelID +
							 * GlobalConstant.orderUniqueSymbol + itemId; } if
							 * (!idsList.containsKey(channelID) && !duplicateKey
							 * .containsKey(channelID)) {
							 * order.setChannelOrderID(channelID); if
							 * (productConfig != null) {
							 * order.setProductSkuCode(productConfig
							 * .getProductSkuCode());
							 * order.setPartnerCommission(productConfig
							 * .getCommision()); } if (!partner .getPcName()
							 * .toLowerCase() .contains(
							 * GlobalConstant.PCAMAZON)) { duplicateKey
							 * .put(channelID, order.getOrderDate()
							 * .toString()); } else {
							 * duplicateKey.put(channelID, itemId); } } else {
							 * errorMessage
							 * .append("Channel Order Id is Already Present.");
							 * validaterow = false; } } else { errorMessage
							 * .append("Channel Order Id is Already Present.");
							 * validaterow = false; } } } }
							 * 
							 * /* if (channelID != null &&
							 * !idsList.contains(channelID) &&
							 * !duplicateKey.containsKey(channelID)) {
							 * 
							 * order.setChannelOrderID(channelID); if
							 * (productConfig != null)
							 * order.setProductSkuCode(productConfig
							 * .getProductSkuCode()); else
							 * order.setProductSkuCode(skuCode);
							 * 
							 * if (!(partner.getPcName().toLowerCase()
							 * .contains(GlobalConstant.PCFLIPKART) || partner
							 * .getPcName() .toLowerCase() .contains(
							 * GlobalConstant.PCPAYTM) || partner .getPcName()
							 * .toLowerCase() .contains(
							 * GlobalConstant.PCAMAZON) || partner
							 * .getPcName().toLowerCase()
							 * .contains(GlobalConstant.PCJABONG))) {
							 * duplicateKey.put(channelID, ""); } } else { if
							 * (channelID != null) { errorMessage
							 * .append(" Channel OrderId is already present;");
							 * 
							 * validaterow = false; } }
							 */
						}
					} else {
						errorMessage.append(" Channel OrderId or SKU is NULL");
						validaterow = false;
					}

					if (entry.getCell(4) != null
							&& entry.getCell(4).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						customerBean.setCustomerName(entry.getCell(4)
								.toString());
					}
					
					if(order.getPcName().contains("flipkart")){
						if (entry.getCell(5).toString().equalsIgnoreCase("Prepaid")) {
							order.setPaymentType(entry.getCell(5).toString());
						} else {
							order.setPaymentType("COD");
						}
					} else {
						if (entry.getCell(5) != null
								&& entry.getCell(5).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							if (entry.getCell(5).toString().equalsIgnoreCase("COD")
									|| entry.getCell(5).toString()
											.equalsIgnoreCase("Prepaid")
									|| entry.getCell(5).toString()
											.equalsIgnoreCase("Others"))
								order.setPaymentType(entry.getCell(5).toString());
							else {
								order.setPaymentType(entry.getCell(5).toString());
								errorMessage
										.append(" Payment type should be COD or Prepaid;");
								validaterow = false;
							}		
						} else {
							errorMessage.append(" Payment type is null;");
							validaterow = false;
						}
					}					
					if (entry.getCell(6) != null
							&& entry.getCell(6).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						entry.getCell(6).setCellType(HSSFCell.CELL_TYPE_STRING);
						order.setAwbNum(entry.getCell(6).toString());
					}
					if (entry.getCell(7) != null
							&& entry.getCell(7).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						entry.getCell(7).setCellType(HSSFCell.CELL_TYPE_STRING);
						order.setInvoiceID(entry.getCell(7).toString());
					} else {
						order.setInvoiceID(GlobalConstant.randomNo());
					}
					if (entry.getCell(8) != null
							&& entry.getCell(8).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						entry.getCell(8).setCellType(HSSFCell.CELL_TYPE_STRING);

						String itemID = entry.getCell(8).toString();
						if (itemID.contains("'")) {
							itemID = removeExtraQuote(itemID);
						}
						order.setSubOrderID(itemID);
						if (channelID != null) {
							if (partner != null
									&& (partner
											.getPcName()
											.toLowerCase()
											.contains(GlobalConstant.PCFLIPKART)
											|| partner
													.getPcName()
													.toLowerCase()
													.contains(
															GlobalConstant.PCPAYTM) || partner
											.getPcName().toLowerCase()
											.contains(GlobalConstant.PCJABONG))) {

								if (channelID != null) {
									channelID = channelID
											+ GlobalConstant.orderUniqueSymbol
											+ order.getSubOrderID();

									if ((idsList == null || (!idsList
											.containsKey(channelID))
											&& !duplicateKey
													.containsKey(channelID)
											&& productConfig != null)) {
										order.setChannelOrderID(channelID);
										order.setProductSkuCode(productConfig
												.getProductSkuCode());
										order.setPartnerCommission(productConfig
												.getCommision());
										duplicateKey.put(channelID, "");
									} else {

										errorMessage
												.append(" Channel OrderId is already present ");
										validaterow = false;
									}
								}
							}
						}
					} else {
						if (partner != null
								&& (partner.getPcName().toLowerCase()
										.contains(GlobalConstant.PCFLIPKART)
										|| partner
												.getPcName()
												.toLowerCase()
												.contains(
														GlobalConstant.PCPAYTM) || partner
										.getPcName().toLowerCase()
										.contains(GlobalConstant.PCJABONG))) {

							errorMessage
									.append(" Secondary OrderID is mandatory for Flipkart, PayTm & Jabong.");
							validaterow = false;
						}
					}
					if (entry.getCell(9) != null
							&& entry.getCell(9).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						order.setPIreferenceNo(entry.getCell(9).toString());
					}
					if (entry.getCell(10) != null
							&& entry.getCell(10).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						order.setLogisticPartner(entry.getCell(10).toString());
					}
					if (entry.getCell(11) != null
							&& entry.getCell(11).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						try {
							order.setOrderMRP(Double.parseDouble(entry.getCell(
									11).toString()));
						} catch (NumberFormatException e) {
							log.error("Failed! by SellerId : " + sellerId, e);
							errorMessage
									.append(" Order MRP should be a number ");
						}
					}
					if (entry.getCell(12) != null
							&& entry.getCell(12).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						try {
							order.setOrderSP(Double.parseDouble(entry.getCell(
									12).toString()));
						} catch (NumberFormatException e) {
							errorMessage
									.append(" Order SP should be a number ");
							validaterow = false;
						}
					} else {
						errorMessage.append(" Order SP is null ");
						validaterow = false;
					}
					/*
					 * if (entry.getCell(13) != null &&
					 * entry.getCell(13).getCellType() !=
					 * HSSFCell.CELL_TYPE_BLANK) { TaxCategory taxcat =
					 * taxDetailService.getTaxCategory(entry
					 * .getCell(13).toString(), sellerId); if (taxcat != null)
					 * otb.setTaxCategtory(entry.getCell(13).toString()); else {
					 * otb.setTaxCategtory(entry.getCell(13).toString());
					 * errorMessage.append("Tax Category does not exist ");
					 * validaterow = false; } } else {
					 * errorMessage.append("Tax Category is null "); validaterow
					 * = false; }
					 */

					if (entry.getCell(13) != null
							&& entry.getCell(13).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						try {
							if (HSSFDateUtil.isCellDateFormatted(entry
									.getCell(13))) {
								order.setShippedDate(entry.getCell(13)
										.getDateCellValue());
							} else {
								errorMessage
										.append(" Shipped Date formate is wrong ,enter mm/dd/yyyy,");
								validaterow = false;
							}
						} catch (Exception e) {
							errorMessage
									.append(" Shipped Date formate is wrong ,enter mm/dd/yyyy,");
							validaterow = false;
						}
					} else {
						errorMessage.append(" Shipping Date is null ");
						validaterow = false;
					}

					if (entry.getCell(14) != null
							&& entry.getCell(14).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						try {
							if ((int) Float.parseFloat(entry.getCell(14)
									.toString()) != 0) {
								order.setQuantity((int) Float.parseFloat(entry
										.getCell(14).toString()));
							} else {
								errorMessage.append(" Quantity can not be 0 ");
								validaterow = false;
							}
						} catch (NumberFormatException e) {
							log.error("Failed! by SellerId : " + sellerId, e);
							errorMessage
									.append(" Quantity should be a number ");
							validaterow = false;
						}
					} else {
						errorMessage.append(" Quantity can not be null ");
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

								if (entry.getCell(15) != null
										&& entry.getCell(15).getCellType() != HSSFCell.CELL_TYPE_BLANK) {

									try {
										order.setGrossNetRate(Double
												.parseDouble(entry.getCell(15)
														.toString()));
									} catch (NumberFormatException e) {
										log.error("Failed! by SellerId : "
												+ sellerId, e);
										errorMessage
												.append(" Net Rate should be number ");
										validaterow = false;
									}
								} else {
									if(productConfig != null && productConfig.getGrossNR() != 0){
										order.setGrossNetRate(productConfig.getGrossNR());
									} else {
										errorMessage
										.append(" Net Rate is null and ongoing event expects NR.");
										validaterow = false;
									}									
								}
							}
						} else {
							if (partner != null
									&& partner.getNrnReturnConfig() != null
									&& !partner.getNrnReturnConfig()
											.isNrCalculator()) {
								if (entry.getCell(15) != null
										&& entry.getCell(15).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
									try {
										order.setGrossNetRate(Double
												.parseDouble(entry.getCell(15)
														.toString()));
									} catch (NumberFormatException e) {
										log.error("Failed! by SellerId : "
												+ sellerId, e);
										errorMessage
												.append(" Net Rate should be number ");
										validaterow = false;

									}
								} else {
									if(productConfig != null && productConfig.getGrossNR() != 0){
										order.setGrossNetRate(productConfig.getGrossNR());
									} else {
										errorMessage.append(" Net Rate is null ");
										validaterow = false;
									}									
								}
							}

						}
					}
					if (entry.getCell(16) != null
							&& entry.getCell(16).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						log.debug(" Email from sheet ************ " + rowIndex
								+ ":" + entry.getCell(16).toString());
						customerBean.setCustomerEmail(entry.getCell(16)
								.toString());
					}
					if (entry.getCell(17) != null
							&& entry.getCell(17).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						entry.getCell(17)
								.setCellType(HSSFCell.CELL_TYPE_STRING);
						if (entry.getCell(17).toString().length() == 10
								&& !entry.getCell(17).toString()
										.contains("9999999999")) {
							if (entry.getCell(17).toString().matches("[0-9]+")
									&& entry.getCell(17).toString().length() > 2) {
								customerBean.setCustomerPhnNo(entry.getCell(17)
										.toString());
							}
						}
						try {
							boolean isBlackList = customerService.isBlackList(
									entry.getCell(17).toString(), sellerId);
							if (isBlackList == true) {
								SellerAlerts sellerAlert = new SellerAlerts();
								sellerAlert.setAlertDate(new Date());
								sellerAlert.setAlertType("Customer");
								sellerAlert
										.setAlertMessage(GlobalConstant.CustomerMsg
												+ " : "
												+ entry.getCell(17).toString());
								sellerAlert.setStatus("unread");
								alertService.saveAlerts(sellerAlert, sellerId);
								customerBean.setStatus("Inactive");
							} else {
								customerBean.setStatus("Active");
							}
						} catch (Exception e) {
							e.printStackTrace();
							log.error(
									"Error In saveOrderContents on set Customer BlackList Alert : Seller ID : "
											+ sellerId, e);
							customerBean.setStatus("Active");
						}
					}
					if (entry.getCell(18) != null
							&& entry.getCell(18).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						customerBean.setCustomerCity(entry.getCell(18)
								.toString());
					}
					if (entry.getCell(19) != null
							&& entry.getCell(19).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						customerBean.setCustomerAddress(entry.getCell(19)
								.toString());
					}
					if (entry.getCell(20) != null
							&& entry.getCell(20).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						log.debug(" getting zipcode ");
						String zipCode = entry.getCell(20).toString();
						if (zipCode.contains(".")) {
							zipCode = zipCode
									.substring(0, zipCode.indexOf("."));
						}
						System.out.println(zipCode);
						if (areaConfigDao.isZipCodeValid(zipCode)) {
							try {
								customerBean.setZipcode(zipCode);
							} catch (Exception e) {
								log.error("Failed! by SellerId : " + sellerId,
										e);
								errorMessage
										.append("Customer zipcode is corrupted.");
								validaterow = false;
							}
						} else {
							errorMessage
									.append("Either invalid zipcode or try after some time while admin will add this to database.");
							validaterow = false;
							customerBean.setZipcode(zipCode);
						}
					} else {
						errorMessage.append("Customer zipcode is blank ");
						validaterow = false;
					}

					if (order.getProductSkuCode() != null) {
						product = productService.getProduct(
								order.getProductSkuCode(), sellerId);
						if (product != null) {
							taxcat = taxDetailService.getTaxCategory(product,
									order.getOrderSP(), sellerId,
									customerBean.getZipcode());
						}
					}
					if (taxcat != null)
						otb.setTaxCategtory(taxcat.getTaxCatName());
					else {
						if (product != null) {
							errorMessage.append("Tax Category does not exist ");
							validaterow = false;
						}
					}

					if (entry.getCell(21) != null
							&& StringUtils.isNotBlank(entry.getCell(21)
									.toString())) {
						order.setSellerNote(entry.getCell(21).toString());

					}
					log.debug("Sheet values :1 :" + entry.getCell(1) + " 2 :"
							+ entry.getCell(2) + " 3 :" + entry.getCell(3));
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
			Set<String> errorSet = returnOrderMap.keySet();
			downloadUploadReportXLS(offices, "MP_Order_Upload", uploadFileName,
					23, errorSet, path, sellerId, uploadReport);

		} catch (Exception e) {
			log.debug("Inside save contents exception :"
					+ e.getLocalizedMessage());
			e.printStackTrace();
			addErrorUploadReport("MP_Order_Upload", sellerId, uploadReport);
			log.error("Failed! by SellerId : " + sellerId, e);
		}
		log.info("$$$ saveOrderContents ends : SaveContents $$$");
		return returnOrderMap;
	}

	public Map<String, OrderBean> saveOrderPOContents(MultipartFile file,
			int sellerId, String path, UploadReport uploadReport)
			throws IOException {
		log.info("$$$ saveOrderPOContents starts : SaveContents $$$");
		HSSFRow entry;
		Integer noOfEntries = 1;
		boolean validaterow = true;
		Map<String, OrderBean> returnOrderMap = new LinkedHashMap<>();
		OrderBean order = null;
		Partner partner = null;
		StringBuffer errorMessage = null;

		String poId = null;
		String invoiceId = null;
		String uploadFileName = "";
		List<Order> orderlist = new ArrayList<Order>();

		try {
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = offices.getSheetAt(0);
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			uploadFileName = file.getOriginalFilename().substring(0,
					file.getOriginalFilename().lastIndexOf("."))
					+ new Date().getTime();
			log.info(noOfEntries.toString());
			log.debug("After getting no of rows" + noOfEntries);
			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				validaterow = true;
				entry = worksheet.getRow(rowIndex);
				errorMessage = new StringBuffer("Row :" + (rowIndex - 2) + ":");

				order = new OrderBean();
				try {
					if (entry.getCell(0) != null
							&& entry.getCell(0).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						partner = partnerService.getPartner(entry.getCell(0)
								.toString(), sellerId);
						if (partner != null)
							order.setPcName(entry.getCell(0).toString());
						else {
							order.setPcName(entry.getCell(0).toString());
							errorMessage.append(" Channel does not exist;");
							validaterow = false;
						}
					} else {
						errorMessage.append(" Channel is null;");
						validaterow = false;
					}

					if (entry.getCell(1) != null
							&& StringUtils.isNotBlank(entry.getCell(1)
									.toString())) {
						order.setSubOrderID(entry.getCell(1).toString());
						order.setChannelOrderID(entry.getCell(1).toString());

						if (poId != null
								&& !poId.equalsIgnoreCase(order.getSubOrderID())
								&& !orderlist.isEmpty()) {
							Order consolidatedOrder = orderService
									.generateConsolidatedOrder(orderlist,
											sellerId);
							orderService.updatePOOrders(orderlist,
									consolidatedOrder);
							orderlist.clear();
						}
						poId = order.getSubOrderID();
					} else {
						errorMessage.append(" PO ID is null ");
						validaterow = false;
					}

					if (entry.getCell(2) != null
							&& StringUtils.isNotBlank(entry.getCell(2)
									.toString())) {
						List<ProductConfig> productConfigs = productService
								.getProductConfig(entry.getCell(2).toString()
										.toUpperCase(), order.getPcName(),
										sellerId);
						if (productConfigs == null) {
							errorMessage
									.append(" Product configuration does not exist ");
							validaterow = false;
						} else {
							if (productConfigs.size() == 1) {
								order.setProductSkuCode(entry.getCell(2)
										.toString());
							} else {
								ProductConfig pc = null;
								Set<String> parent = new HashSet<String>();
								for (Object PCo : productConfigs) {
									pc = (ProductConfig) PCo;
									parent.add(pc.getProductSkuCode());
								}
								if (parent.size() == 1) {
									order.setProductSkuCode(entry.getCell(2)
											.toString());
								} else {
									errorMessage
											.append(" Multiple Mapping present for this Channel And SKU.");
									validaterow = false;
								}
							}
						}
					} else {
						errorMessage.append(" Product SKU is null ");
						validaterow = false;
					}

					if (entry.getCell(3) != null
							&& StringUtils.isNotBlank(entry.getCell(3)
									.toString())) {
						try {
							order.setQuantity((int) Float.parseFloat(entry
									.getCell(3).toString()));
						} catch (NumberFormatException e) {
							errorMessage
									.append(" Quantity should be a number ");
							validaterow = false;
						}
					} else {
						errorMessage.append(" Quantity is null ");
						validaterow = false;
					}

					if (entry.getCell(4) != null
							&& StringUtils.isNotBlank(entry.getCell(4)
									.toString())) {
						try {
							order.setPoPrice(Double.parseDouble(entry
									.getCell(4).toString()));
						} catch (NumberFormatException e) {
							log.error("Failed! by SellerId : " + sellerId, e);
							errorMessage
									.append(" Gross PO Price should be a number ");
							validaterow = false;
						}
					} else {
						errorMessage.append(" Gross PO Price is null ");
						validaterow = false;
					}

					/*
					 * if (entry.getCell(5) != null &&
					 * StringUtils.isNotBlank(entry.getCell(5).toString())) {
					 * try {
					 * order.setOrderMRP(Double.parseDouble(entry.getCell(5)
					 * .toString())); } catch (NumberFormatException e) {
					 * log.error("Failed!", e);
					 * errorMessage.append(" PO MRP should be a number ");
					 * validaterow = false; } } else {
					 * errorMessage.append(" PO MRP is null "); validaterow =
					 * false; }
					 */

					if (entry.getCell(5) != null
							&& StringUtils.isNotBlank(entry.getCell(5)
									.toString())) {
						try {
							order.setTotalAmountRecieved(entry.getCell(5)
									.getNumericCellValue());

						} catch (NumberFormatException e) {
							log.error("Failed! by SellerId : " + sellerId, e);
							errorMessage.append(" Amount should be a number ");
							validaterow = false;
						}
					} else {
						errorMessage.append(" Amount is null ");
						validaterow = false;
					}

					if (entry.getCell(6) != null
							&& StringUtils.isNotBlank(entry.getCell(6)
									.toString())) {
						order.setInvoiceID(entry.getCell(6).toString());

						String curInvoiceId = entry.getCell(6).toString();
						if (curInvoiceId != null) {

							if (orderService.isPOOrderUploaded(
									order.getSubOrderID(),
									order.getInvoiceID(), sellerId)) {
								errorMessage.append(" PO already uploaded ");
								validaterow = false;
							}
						}

						if (invoiceId != null
								&& !invoiceId.equalsIgnoreCase(order
										.getInvoiceID())
								&& !orderlist.isEmpty()) {
							Order consolidateOrder = orderService
									.getConsolidatedOrder(
											order.getSubOrderID(),
											order.getInvoiceID());
							if (consolidateOrder != null) {
								errorMessage.append(" PO already uploaded ");
								validaterow = false;
							} else {
								Order consolidatedOrder = orderService
										.generateConsolidatedOrder(orderlist,
												sellerId);
								orderService.updatePOOrders(orderlist,
										consolidatedOrder);
								orderlist.clear();
							}
						}
						invoiceId = order.getInvoiceID();

					} else {
						errorMessage.append(" Invoice ID is null ");
						validaterow = false;
					}

					if (entry.getCell(7) != null
							&& StringUtils.isNotBlank(entry.getCell(7)
									.toString())) {
						order.setSealNo(entry.getCell(7).toString());
						order.setAwbNum(entry.getCell(7).toString());
					} else {
						errorMessage.append(" Seal No is null ");
						validaterow = false;
					}

					if (entry.getCell(8) != null
							&& StringUtils.isNotBlank(entry.getCell(8)
									.toString())) {

						try {
							if (HSSFDateUtil.isCellDateFormatted(entry
									.getCell(8))) {
								order.setOrderDate(entry.getCell(8)
										.getDateCellValue());
								order.setShippedDate(entry.getCell(8)
										.getDateCellValue());
								order.setDeliveryDate(entry.getCell(8)
										.getDateCellValue());
							} else {
								errorMessage
										.append(" Shipped Date format is wrong ");
								validaterow = false;
							}
						} catch (Exception e) {
							errorMessage
									.append(" Shipped Date format is wrong ");
							validaterow = false;
						}

					} else {
						errorMessage.append(" Shipped Date is null ");
						validaterow = false;
					}

					if (entry.getCell(9) != null
							&& StringUtils.isNotBlank(entry.getCell(9)
									.toString())) {
						order.setSellerNote(entry.getCell(9).toString());
					}

					log.debug("Sheet values :1 :" + entry.getCell(1) + " 2 :"
							+ entry.getCell(2) + " 3 :" + entry.getCell(3));
					// Pre save to generate id for use in hierarchy
					if (validaterow) {
						Order savedOrder = orderService.addPO(
								ConverterClass.prepareModel(order), sellerId);
						orderlist.add(savedOrder);
					} else {
						returnOrderMap.put(errorMessage.toString(), order);
					}
				} catch (Exception e) {
					errorMessage.append("Invalid Input! ");
					returnOrderMap.put(errorMessage.toString(), order);
				}
			}

			if (!orderlist.isEmpty()) {
				Order consolidatedOrder = orderService
						.generateConsolidatedOrder(orderlist, sellerId);
				orderService.updatePOOrders(orderlist, consolidatedOrder);
				orderlist.clear();
			}

			Set<String> errorSet = returnOrderMap.keySet();
			downloadUploadReportXLS(offices, "PO_Order_Upload", uploadFileName,
					10, errorSet, path, sellerId, uploadReport);

		} catch (Exception e) {
			log.debug("Inside save contents exception :"
					+ e.getLocalizedMessage());
			e.printStackTrace();
			log.error("Failed! by SellerId : " + sellerId, e);
			addErrorUploadReport("PO_Order_Upload", sellerId, uploadReport);
			throw new MultipartException("Constraints Violated");
		}
		log.info("$$$ saveOrderPOContents ends : SaveContents $$$");
		return returnOrderMap;
	}

	public Map<String, ProductBean> saveProductContents(MultipartFile file,
			int sellerId, String path, UploadReport uploadReport)
			throws IOException {

		log.info("$$$ saveProductContents starts : SaveContents $$$");
		boolean validaterow = true;
		Map<String, ProductBean> returnProductMap = new LinkedHashMap<>();
		StringBuffer errorMessage = null;
		Map<String, String> uniqueProductMap = new HashMap<String, String>();
		List<Product> saveList = null;
		String uploadFileName = "";
		try {
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = offices.getSheetAt(0);
			HSSFRow entry;
			Integer noOfEntries = 1;
			saveList = new ArrayList<Product>();
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			uploadFileName = file.getOriginalFilename().substring(0,
					file.getOriginalFilename().lastIndexOf("."))
					+ new Date().getTime();
			log.info(noOfEntries.toString());
			log.debug("After getting no of rows" + noOfEntries);
			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				Product product = null;
				try {
					entry = worksheet.getRow(rowIndex);
					validaterow = true;
					errorMessage = new StringBuffer("Row :" + (rowIndex - 2)
							+ ":");
					log.debug("Product 1" + entry.getCell(1));
					log.debug("Product 2" + entry.getCell(2));
					log.debug(entry.getCell(3));
					log.debug(entry.getCell(4));
					product = new Product();
					if (entry.getCell(0) != null
							&& entry.getCell(0).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						product.setProductName(entry.getCell(0).toString());
					} else {
						errorMessage.append(" Product Name is null ");
						validaterow = false;
					}
					if (entry.getCell(1) != null
							&& entry.getCell(1).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						entry.getCell(1).setCellType(HSSFCell.CELL_TYPE_STRING);

						Product obj = productService.getProduct(entry
								.getCell(1).toString(), sellerId);
						if (obj == null
								&& !uniqueProductMap.containsKey(entry.getCell(
										1).toString())) {
							product.setProductSkuCode(entry.getCell(1)
									.toString().toUpperCase());
							uniqueProductMap.put(entry.getCell(1).toString()
									.toUpperCase(), entry.getCell(1).toString()
									.toUpperCase());
						} else {
							product.setProductSkuCode(entry.getCell(1)
									.toString());
							errorMessage
									.append(" Product SKU already present ");
							validaterow = false;
						}
					} else {
						errorMessage.append(" Product SKU is null ");
						validaterow = false;
					}
					if (entry.getCell(2) != null
							&& entry.getCell(2).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						Category cat = categoryService.getSubCategory(entry
								.getCell(2).toString(), sellerId);
						if (cat != null) {
							product.setCategoryName(cat.getCatName());
						} else {
							product.setCategoryName(entry.getCell(2).toString());
							errorMessage.append(" Category does not exist ");
							validaterow = false;
						}
					} else {
						errorMessage.append(" Category is null ");
						validaterow = false;
					}
					if (entry.getCell(3) != null
							&& entry.getCell(3).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						try {
							product.setProductPrice(Float.valueOf(entry
									.getCell(3).toString()));
						} catch (NumberFormatException e) {
							log.error("Failed! by SellerId : " + sellerId, e);
							errorMessage
									.append(" Product price should be a number ");
						}
					} else {
						errorMessage.append(" Product price is null ");
					}
					if (entry.getCell(4) != null
							&& entry.getCell(4).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						try {
							if ((int) Float.parseFloat(entry.getCell(4)
									.toString()) != 0) {
								product.setQuantity(Float.valueOf(
										entry.getCell(4).toString())
										.longValue());
							} else {
								errorMessage.append(" Quantity can not be 0 ");
								validaterow = false;
							}
						} catch (NumberFormatException e) {
							log.error("Failed! by SellerId : " + sellerId, e);
							errorMessage
									.append(" Quantity should be a number ");
							validaterow = false;
						}
					} else {
						errorMessage.append(" Quantity is null");
						validaterow = false;
					}
					if (entry.getCell(5) != null
							&& entry.getCell(5).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						try {
							product.setThreholdLimit(Float.valueOf(
									entry.getCell(5).toString()).longValue());
						} catch (NumberFormatException e) {
							log.error("Failed! by SellerId : " + sellerId, e);
							errorMessage
									.append(" Threshold limit should be a number ");
						}
					} else {
						errorMessage.append(" Threshold limit is null ");
					}
					if (entry.getCell(6) != null
							&& entry.getCell(6).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						try {
							product.setLength(Float.valueOf(entry.getCell(6)
									.toString()));
						} catch (NumberFormatException e) {
							log.error("Failed! by SellerId : " + sellerId, e);
							errorMessage.append(" Length should be a number ");
							validaterow = false;
						}
					} else {
						errorMessage.append(" Length is null ");
						validaterow = false;
					}
					if (entry.getCell(7) != null
							&& entry.getCell(7).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						try {
							product.setBreadth(Float.valueOf(entry.getCell(7)
									.toString()));
						} catch (NumberFormatException e) {
							log.error("Failed! by SellerId : " + sellerId, e);
							errorMessage.append(" Breadth should be a number ");
							validaterow = false;
						}
					} else {
						errorMessage.append(" Breadth is null ");
						validaterow = false;
					}

					if (entry.getCell(8) != null
							&& entry.getCell(8).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						try {
							product.setHeight(Float.valueOf(entry.getCell(8)
									.toString()));
						} catch (NumberFormatException e) {
							log.error("Failed! by SellerId : " + sellerId, e);
							errorMessage.append(" Height should be a number ");
							validaterow = false;
						}
					} else {
						errorMessage.append(" Height is null ");
						validaterow = false;
					}
					if (entry.getCell(9) != null
							&& entry.getCell(9).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						try {
							product.setDeadWeight((float) entry.getCell(9)
									.getNumericCellValue());
						} catch (NumberFormatException e) {
							log.error("Failed! by SellerId : " + sellerId, e);
							errorMessage
									.append(" Dead Weight should be a number ");
							validaterow = false;
						}
					} else {
						errorMessage.append(" Dead Weight is null ");
						validaterow = false;
					}
					product.setProductDate(new Date());
					product.setVolume(product.getHeight() * product.getLength()
							* product.getBreadth());
					product.setVolWeight(product.getVolume() / 5);
					if (validaterow) {
						saveList.add(product);
						// productService.addProduct(product, sellerId);
					} else {
						returnProductMap.put(errorMessage.toString(),
								ConverterClass.prepareProductBean(product));
					}
					System.out.println("Sheet values :1 :" + entry.getCell(1)
							+ " 2 :" + entry.getCell(2) + " 3 :"
							+ entry.getCell(3));
					// Pre save to generate id for use in hierarchy
				} catch (Exception e) {
					log.error("Failed! by SellerId : " + sellerId, e);

					errorMessage.append("Invalid Input! ");
					returnProductMap.put(errorMessage.toString(), null);

				}
			}
			try {
				System.out.println(" SaveList Size : " + saveList.size());
				if (saveList != null && saveList.size() != 0)
					productService.addProduct(saveList, sellerId);
			} catch (CustomException ce) {
				returnProductMap
						.put("Row:1:Note-Some products("
								+ ce.getLocalMessage()
								+ " ) with valid input "
								+ "failed due to internal server error. Please contact admin.!",
								null);
			}
			Set<String> errorSet = returnProductMap.keySet();
			downloadUploadReportXLS(offices, "Create_Parent_Product",
					uploadFileName, 10, errorSet, path, sellerId, uploadReport);
		} catch (Exception e) {
			log.debug("Inside save contents exception :"
					+ e.getLocalizedMessage());
			e.printStackTrace();
			log.error("Failed! by SellerId : " + sellerId, e);
			addErrorUploadReport("Create_Parent_Product", sellerId,
					uploadReport);
			throw new MultipartException("Constraints Violated");
		}
		log.info("$$$ saveProductContents ends : SaveContents $$$");
		return returnProductMap;
	}

	public Map<String, ProductBean> saveEditProductContents(MultipartFile file,
			int sellerId, String path, UploadReport uploadReport)
			throws IOException {

		log.info("$$$ saveEditProductContents starts : SaveContents $$$");
		boolean validaterow = true;
		Map<String, ProductBean> returnProductMap = new LinkedHashMap<>();
		StringBuffer errorMessage = null;
		List<Product> productList = new ArrayList<Product>();
		Product product = null;
		String uploadFileName = "";
		try {
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = offices.getSheetAt(0);
			HSSFRow entry;
			Integer noOfEntries = 1;

			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			uploadFileName = file.getOriginalFilename().substring(0,
					file.getOriginalFilename().lastIndexOf("."))
					+ new Date().getTime();
			log.info(noOfEntries.toString());
			log.debug("After getting no of rows" + noOfEntries);
			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				try {
					entry = worksheet.getRow(rowIndex);
					validaterow = true;
					errorMessage = new StringBuffer("Row :" + (rowIndex - 2)
							+ ":");
					log.debug("Product 1" + entry.getCell(1));
					log.debug("Product  2" + entry.getCell(2));
					log.debug(entry.getCell(3));
					log.debug(entry.getCell(4));

					if (entry.getCell(1) != null
							&& entry.getCell(1).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						product = productService.getProduct(entry.getCell(1)
								.toString().toUpperCase(), sellerId);
						if (product == null) {
							errorMessage
									.append(" Invalid SKU code ! Product Doesn't Exist ! ");
							validaterow = false;
						} else {

							if (entry.getCell(0) != null
									&& entry.getCell(0).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
								product.setProductName(entry.getCell(0)
										.toString());
							}
							if (entry.getCell(2) != null
									&& entry.getCell(2).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
								try {
									product.setProductPrice(Float.valueOf(entry
											.getCell(2).toString()));
								} catch (NumberFormatException e) {
									log.error("Failed! by SellerId : "
											+ sellerId, e);
									errorMessage
											.append(" Product price should be a number ");
								}
							}
							if (entry.getCell(3) != null
									&& entry.getCell(3).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
								try {
									product.setThreholdLimit(Float.valueOf(
											entry.getCell(3).toString())
											.longValue());
								} catch (NumberFormatException e) {
									log.error("Failed! by SellerId : "
											+ sellerId, e);
									errorMessage
											.append(" Threshold limit should be a number ");
								}
							}
							if (entry.getCell(4) != null
									&& entry.getCell(4).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
								try {
									product.setLength(Float.valueOf(entry
											.getCell(4).toString()));
								} catch (NumberFormatException e) {
									log.error("Failed! by SellerId : "
											+ sellerId, e);
									errorMessage
											.append(" Length should be a number ");
								}
							}
							if (entry.getCell(5) != null
									&& entry.getCell(5).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
								try {
									product.setBreadth(Float.valueOf(entry
											.getCell(5).toString()));
								} catch (NumberFormatException e) {
									log.error("Failed! by SellerId : "
											+ sellerId, e);
									errorMessage
											.append(" Breadth should be a number ");
								}
							}
							if (entry.getCell(6) != null
									&& entry.getCell(6).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
								try {
									product.setHeight(Float.valueOf(entry
											.getCell(6).toString()));
								} catch (NumberFormatException e) {
									log.error("Failed! by SellerId : "
											+ sellerId, e);
									errorMessage
											.append(" Height should be a number ");

								}
							}
							if (entry.getCell(7) != null
									&& entry.getCell(7).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
								try {
									product.setDeadWeight((float) entry
											.getCell(7).getNumericCellValue());
								} catch (NumberFormatException e) {
									log.error("Failed! by SellerId : "
											+ sellerId, e);
									errorMessage
											.append(" Dead Weight should be a number ");
								}
							}
							product.setVolume(product.getHeight()
									* product.getLength()
									* product.getBreadth());
							product.setVolWeight(product.getVolume() / 5);

						}
					} else {
						errorMessage
								.append(" Blank or Null Sku Value ! SKU Code Should Not Be Blank or Null ! ");
						validaterow = false;
					}
					if (validaterow) {
						productList.add(product);
					} else {
						returnProductMap.put(errorMessage.toString(),
								ConverterClass.prepareProductBean(product));
					}
				} catch (Exception e) {
					log.error("Failed! by SellerId : " + sellerId, e);
					if (product != null) {
						errorMessage.append("Invalid Input! ");
						returnProductMap.put(errorMessage.toString(),
								ConverterClass.prepareProductBean(product));
					}
				}
			}
			if (productList != null && productList.size() != 0) {
				try {
					productService.editProduct(sellerId, productList);
				} catch (CustomException ce) {
					errorMessage.append(ce.getMessage());
				}
			}
			returnProductMap.put(errorMessage.toString(),
					ConverterClass.prepareProductBean(product));
			Set<String> errorSet = returnProductMap.keySet();
			downloadUploadReportXLS(offices, "Product_Edit", uploadFileName, 8,
					errorSet, path, sellerId, uploadReport);
		} catch (Exception e) {
			log.debug("Inside save contents exception :"
					+ e.getLocalizedMessage());
			e.printStackTrace();
			log.error("Failed! by SellerId : " + sellerId, e);
			addErrorUploadReport("Product_Edit", sellerId, uploadReport);
			throw new MultipartException("Constraints Violated");
		}
		log.info("$$$ saveEditProductContents ends : SaveContents $$$");
		return returnProductMap;
	}

	// My coding Product Config *********
	/*
	 * public Map<String, ProductConfigBean> saveSKUMappingContents(
	 * MultipartFile file, int sellerId, String path, UploadReport uploadReport)
	 * throws IOException {
	 * log.info("$$$ saveSKUMappingContents starts : SaveContents $$$"); boolean
	 * validaterow = true; Map<String, ProductConfigBean> returnProductConfigMap
	 * = new LinkedHashMap<>(); StringBuffer errorMessage = null; Map<String,
	 * String> uniqueProductMap = new HashMap<String, String>();
	 * List<ProductConfig> saveList = new ArrayList<ProductConfig>(); try {
	 * HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream()); HSSFSheet
	 * worksheet = offices.getSheetAt(0); HSSFRow entry; Integer noOfEntries =
	 * 1; while (worksheet.getRow(noOfEntries) != null) { noOfEntries++; }
	 * log.info(noOfEntries.toString()); log.debug("After getting no of rows" +
	 * noOfEntries); for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++)
	 * { ProductConfig productConfig = null; entry = worksheet.getRow(rowIndex);
	 * validaterow = true; errorMessage = new StringBuffer("Row :" + (rowIndex -
	 * 2) + ":"); log.debug("Product 1" + entry.getCell(1));
	 * log.debug("Product  2" + entry.getCell(2)); productConfig = new
	 * ProductConfig(); try { if (entry.getCell(0) != null &&
	 * entry.getCell(0).getCellType() != HSSFCell.CELL_TYPE_BLANK) { Product
	 * product = productService.getProduct(entry .getCell(0).toString(),
	 * sellerId); if (product != null) {
	 * productConfig.setProductSkuCode(entry.getCell(0) .toString()); if
	 * (entry.getCell(1) != null && entry.getCell(1).getCellType() !=
	 * HSSFCell.CELL_TYPE_BLANK && entry.getCell(2) != null &&
	 * entry.getCell(2).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
	 * ProductConfig procon = productService .getProductConfig(entry.getCell(1)
	 * .toString(), entry.getCell(2) .toString(), sellerId); if (procon == null
	 * && !uniqueProductMap.containsKey(entry .getCell(1).toString())) {
	 * productConfig.setChannelSkuRef(entry .getCell(1).toString());
	 * uniqueProductMap.put(entry.getCell(1) .toString(), entry.getCell(1)
	 * .toString()); } else { errorMessage
	 * .append(" Channel Reference Code already present for that SKU ");
	 * validaterow = false; } } else { errorMessage
	 * .append(" Channel Reference Code is null "); validaterow = false; } if
	 * (entry.getCell(2) != null && entry.getCell(2).getCellType() !=
	 * HSSFCell.CELL_TYPE_BLANK) { if (!entry.getCell(2).toString()
	 * .equalsIgnoreCase("Myntra")) productConfig.setChannelName(entry.getCell(
	 * 2).toString()); else { errorMessage
	 * .append("Enter only Market Place Channels "); validaterow = false; } }
	 * else { errorMessage.append(" Channel Name is null "); validaterow =
	 * false; }
	 * 
	 * } else { errorMessage
	 * .append(" Product with given SKU does not present "); validaterow =
	 * false; } } else { errorMessage.append(" Product SKU is null ");
	 * validaterow = false; }
	 * 
	 * if (validaterow) { // productService.addSKUMapping(productConfig, //
	 * sellerId); saveList.add(productConfig); } else { returnProductConfigMap
	 * .put(errorMessage.toString(), ConverterClass
	 * .prepareProductConfigBean(productConfig)); } } catch (Exception e) {
	 * log.error("Failed! by SellerId : " + sellerId, e); if (productConfig !=
	 * null) { errorMessage.append("Invalid Input! "); returnProductConfigMap
	 * .put(errorMessage.toString(), ConverterClass
	 * .prepareProductConfigBean(productConfig)); }
	 * 
	 * } log.debug("Sheet values :1 :" + entry.getCell(1) + " 2 :" +
	 * entry.getCell(2) + " 3 :" + entry.getCell(3)); // Pre save to generate id
	 * for use in hierarchy } try { if (saveList != null && saveList.size() !=
	 * 0) productService.addSKUMapping(saveList, sellerId); } catch
	 * (CustomException ce) { returnProductConfigMap
	 * .put("Row:1:Note-Some channel SKU(" + ce.getLocalMessage() +
	 * " ) with valid input " +
	 * "failed due to internal server error. Please contact admin.!", null); }
	 * Set<String> errorSet = returnProductConfigMap.keySet();
	 * downloadUploadReportXLS(offices, "MP_SKU_Mapping", 3, errorSet, path,
	 * sellerId, uploadReport); } catch (Exception e) {
	 * 
	 * log.error("Failed! by SellerId : " + sellerId, e);
	 * addErrorUploadReport("MP_SKU_Mapping", sellerId, uploadReport); throw new
	 * MultipartException("Constraints Violated"); }
	 * log.info("$$$ saveSKUMappingContents ends : SaveContents $$$"); return
	 * returnProductConfigMap; }
	 */

	public Map<String, TaxCategory> saveTaxCategoryContents(MultipartFile file,
			int sellerId, String path, UploadReport uploadReport)
			throws IOException {
		log.info("$$$ saveTaxCategoryContents starts : SaveContents $$$");
		boolean validaterow = true;
		Map<String, TaxCategory> returnTaxCategoryMap = new HashMap<>();
		Map<String, TaxCategory> returnTaxCatMap = new HashMap<String, TaxCategory>();
		StringBuffer errorMessage = null;
		List<Category> productCategoryList = null;
		Map<String, Category> productCategoryMap = new HashMap<String, Category>();
		String uploadFileName = "";
		try {
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = offices.getSheetAt(0);
			HSSFRow entry;
			Integer noOfEntries = 1;
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			uploadFileName = file.getOriginalFilename().substring(0,
					file.getOriginalFilename().lastIndexOf("."))
					+ new Date().getTime();
			productCategoryList = categoryService.listCategories(sellerId);
			System.out.println(productCategoryList.size());
			if (productCategoryList != null) {
				for (Category cat : productCategoryList) {
					productCategoryMap.put(cat.getCatName(), cat);
				}
			}
			log.info(noOfEntries.toString());
			log.debug("After getting no of rows" + noOfEntries);
			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				Category productCategory = null;
				TaxCategory taxCategory = null;
				List<Category> categoryList = null;
				errorMessage = new StringBuffer("Row :" + (rowIndex - 2) + ":");
				log.debug("Product 1" + entry.getCell(1));
				log.debug("Product  2" + entry.getCell(2));
				try {
					if (entry.getCell(0) != null
							&& entry.getCell(0).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						if (productCategoryMap.containsKey(entry.getCell(0)
								.toString())
								&& productCategoryMap.get(entry.getCell(0)
										.toString()) != null) {
							productCategory = productCategoryMap.get(entry
									.getCell(0).toString());
							if (entry.getCell(1) != null
									&& entry.getCell(1).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
								if (entry.getCell(2) != null
										&& entry.getCell(2).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
									if (returnTaxCategoryMap.containsKey(entry
											.getCell(1).toString())) {
										taxCategory = returnTaxCategoryMap
												.get(entry.getCell(1)
														.toString());
									} else {
										taxCategory = taxDetailService
												.getTaxCategory(entry
														.getCell(1).toString(),
														sellerId);
									}
									if (taxCategory != null) {
										if (entry.getCell(2).toString()
												.equalsIgnoreCase("LST")) {
											if (taxCategory.getTaxCatType()
													.equalsIgnoreCase("LST")) {
												if (productCategory.getLST() == null) {
													categoryList = taxCategory
															.getProductCategoryLST();
													if (categoryList != null) {
														productCategory
																.setLST(taxCategory);
														categoryList
																.add(productCategory);
														taxCategory
																.setProductCategoryLST(categoryList);
													} else {
														categoryList = new ArrayList<Category>();
														productCategory
																.setLST(taxCategory);
														categoryList
																.add(productCategory);
														taxCategory
																.setProductCategoryLST(categoryList);
													}
												} else {
													errorMessage
															.append("Product Is Already Mapped.");
													validaterow = false;
												}
											} else {
												errorMessage
														.append("Tax Type is Invalid For This Tax Category.");
												validaterow = false;
											}
										} else if (entry.getCell(2).toString()
												.equalsIgnoreCase("CST")) {
											if (taxCategory.getTaxCatType()
													.equalsIgnoreCase("CST")) {
												if (productCategory.getCST() == null) {
													categoryList = taxCategory
															.getProductCategoryCST();
													if (categoryList != null) {
														productCategory
																.setCST(taxCategory);
														categoryList
																.add(productCategory);
														taxCategory
																.setProductCategoryCST(categoryList);
													} else {
														categoryList = new ArrayList<Category>();
														productCategory
																.setCST(taxCategory);
														categoryList
																.add(productCategory);
														taxCategory
																.setProductCategoryCST(categoryList);
													}
												} else {
													errorMessage
															.append("Product Is Already Mapped.");
													validaterow = false;
												}
											} else {
												errorMessage
														.append("Tax Type is Invalid For This Tax Category.");
												validaterow = false;
											}
										} else {
											errorMessage
													.append("Tax Type is Not Valid.");
											validaterow = false;
										}
									} else {
										errorMessage
												.append("Tax Category is Not Valid.");
										validaterow = false;
									}
								} else {
									errorMessage.append("Tax Type is null. ");
									validaterow = false;
								}
							} else {
								errorMessage.append(" Tax Category is null. ");
								validaterow = false;
							}
						} else {
							errorMessage.append("Product Category Not Exist.");
							validaterow = false;
						}
					} else {
						errorMessage.append(" Product Category is null. ");
						validaterow = false;
					}
					if (validaterow) {
						taxDetailService.removeProductMapping(
								taxCategory.getTaxCatId(), sellerId);
						taxDetailService.addTaxCategory(taxCategory, sellerId);
						returnTaxCategoryMap.put(entry.getCell(1).toString(),
								taxCategory);
					} else {
						returnTaxCatMap.put(errorMessage.toString(),
								taxCategory);
					}
				} catch (Exception e) {
					log.error("Failed! by SellerId : " + sellerId, e);
				}
			}
			/*
			 * try { if(returnTaxCategoryMap != null){ for(Entry<String,
			 * TaxCategory> entryz : returnTaxCategoryMap.entrySet()){
			 * taxDetailService
			 * .removeProductMapping(entryz.getValue().getTaxCatId(), sellerId);
			 * System.out.println(entryz.getKey());
			 * taxDetailService.addTaxCategory(entryz.getValue() , sellerId); }
			 * } } catch (Exception ce) {
			 * log.error("Failed ! in Saving And Removing TaxCategory By : "
			 * +sellerId, ce); }
			 */
			Set<String> errorSet = returnTaxCatMap.keySet();
			downloadUploadReportXLS(offices, "product_Tax_Mapping",
					uploadFileName, 3, errorSet, path, sellerId, uploadReport);
		} catch (Exception e) {

			log.error("Failed! by SellerId : " + sellerId, e);
			addErrorUploadReport("product_Tax_Mapping", sellerId, uploadReport);
			throw new MultipartException("Constraints Violated");
		}
		log.info("$$$ saveTaxCategoryContents ends : SaveContents $$$");
		return returnTaxCatMap;
	}

	public Map<String, ProductConfig> saveDlinkSkuMappingContents(
			MultipartFile file, int sellerId, String path,
			UploadReport uploadReport) throws IOException {
		log.info("$$$ saveDlinkSkuMappingContents starts : SaveContents $$$");
		boolean validaterow = true;
		StringBuffer errorMessage = null;
		Map<String, ProductConfig> productConfigMap = new HashMap<String, ProductConfig>();
		String uploadFileName = "";
		try {
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = offices.getSheetAt(0);
			HSSFRow entry;
			Integer noOfEntries = 1;
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			uploadFileName = file.getOriginalFilename().substring(0,
					file.getOriginalFilename().lastIndexOf("."))
					+ new Date().getTime();
			log.info(noOfEntries.toString());
			log.debug("After getting no of rows" + noOfEntries);
			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				errorMessage = new StringBuffer("Row :" + (rowIndex - 2) + ":");
				List<ProductConfig> productConfigs = null;
				ProductConfig productConfig = null;
				try {
					if (entry.getCell(0) != null
							&& entry.getCell(0).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						if (entry.getCell(1) != null
								&& entry.getCell(1).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							if (entry.getCell(3) != null
									&& entry.getCell(3).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
								productConfigs = productService
										.getProductConfig(entry.getCell(1)
												.toString().trim()
												.toUpperCase(), entry
												.getCell(3).toString().trim(),
												sellerId);
								if (productConfigs != null) {
									if (productConfigs.size() == 1) {
										if (productConfigs
												.get(0)
												.getProductSkuCode()
												.equals(entry.getCell(0)
														.toString().trim()
														.toUpperCase())) {
											productConfig = productConfigs
													.get(0);
										} else {
											errorMessage
													.append("Invalid Product SKU, Channel SKU Combination.");
											validaterow = false;
										}
									} else {
										errorMessage
												.append("Multiple Product Mappings With This Channel And Channel SKU.");
										validaterow = false;
									}
								} else {
									errorMessage
											.append("No Product Mappings With This Channel And Channel SKU.");
									validaterow = false;
								}
							} else {
								errorMessage.append("Channel is Null.");
								validaterow = false;
							}

						} else {
							errorMessage.append("Child SKU is Null.");
							validaterow = false;
						}
					} else {
						errorMessage.append("Parent SKU is Null.");
						validaterow = false;
					}
					if (validaterow) {
						productService
								.removeSKUMapping(productConfig, sellerId);
					} else {
						productConfigMap.put(errorMessage.toString(),
								productConfig);
					}
				} catch (Exception e) {
					log.error("Failed! by SellerId : " + sellerId, e);
				}
			}

			Set<String> errorSet = productConfigMap.keySet();
			downloadUploadReportXLS(offices, "Dlink_SKU_Mapping",
					uploadFileName, 4, errorSet, path, sellerId, uploadReport);
		} catch (Exception e) {

			log.error("Failed! by SellerId : " + sellerId, e);
			addErrorUploadReport("Dlink_SKU_Mapping", sellerId, uploadReport);
			throw new MultipartException("Constraints Violated");
		}
		log.info("$$$ saveDlinkSkuMappingContents ends : SaveContents $$$");
		return productConfigMap;
	}

	public Set<String> saveTaxablePurchases(MultipartFile file, int sellerId,
			String path, UploadReport uploadReport) throws IOException {
		log.info("$$$ saveInventoryGroups starts : SaveContents $$$");
		boolean validaterow = true;
		Set<String> errorSet = new HashSet<>();
		StringBuffer errorMessage = null;
		TaxablePurchases taxablePurchases = null;
		String uploadFileName = "";
		try {
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = offices.getSheetAt(0);
			HSSFRow entry;
			Integer noOfEntries = 1;
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			uploadFileName = file.getOriginalFilename().substring(0,
					file.getOriginalFilename().lastIndexOf("."))
					+ new Date().getTime();
			log.info(noOfEntries.toString());
			log.debug("After getting no of rows" + noOfEntries);
			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {

				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				errorMessage = new StringBuffer("Row :" + (rowIndex) + ":");
				taxablePurchases = new TaxablePurchases();
				try {
					if (entry.getCell(0) != null
							&& entry.getCell(0).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						taxablePurchases.setTaxCategory(entry.getCell(0)
								.toString());

					} else {
						errorMessage
								.append(" TaxablePurchases TaxCategory is null ");
						validaterow = false;
					}

					if (entry.getCell(1) != null
							&& entry.getCell(1).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						taxablePurchases.setTaxRate(Float.parseFloat(entry
								.getCell(1).toString()));
					} else {
						errorMessage
								.append(" TaxablePurchases taxRate is null ");
						validaterow = false;
					}

					if (entry.getCell(2) != null
							&& StringUtils.isNotBlank(entry.getCell(2)
									.toString())) {

						try {
							if (HSSFDateUtil.isCellDateFormatted(entry
									.getCell(2))) {
								taxablePurchases.setPurchaseDate(entry.getCell(
										2).getDateCellValue());
							} else {
								errorMessage
										.append(" TaxablePurchases Purchase Date format is wrong ,enter mm/dd/yyyy ");
								validaterow = false;
							}
						} catch (Exception e) {
							errorMessage
									.append(" TaxablePurchases Purchase Date format is wrong ,enter mm/dd/yyyy ");
							validaterow = false;
						}
					} else {
						errorMessage
								.append(" TaxablePurchases Purchase Date is null ");
						validaterow = false;
					}

					if (entry.getCell(3) != null
							&& entry.getCell(3).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						taxablePurchases.setParticular(entry.getCell(3)
								.toString());
					} else {
						errorMessage.append(" Particular is null ");
						validaterow = false;
					}

					if (entry.getCell(4) != null
							&& entry.getCell(4).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						taxablePurchases.setBasicPrice(Double.parseDouble(entry
								.getCell(4).toString()));

						taxablePurchases.setTaxAmount(Double.parseDouble(entry
								.getCell(4).toString())
								* ((Double.parseDouble(entry.getCell(1)
										.toString())) / 100));

						taxablePurchases.setTotalAmount(Double
								.parseDouble(entry.getCell(4).toString())
								+ taxablePurchases.getTaxAmount());

					} else {
						errorMessage
								.append(" TaxablePurchases taxRate is null ");
						validaterow = false;
					}

					if (validaterow) {
						Date dt = new Date();
						taxablePurchases.setCreatedDate(dt);
						taxablePurchaseService.addTaxablePurchase(
								taxablePurchases, sellerId);
					} else {
						errorSet.add(errorMessage.toString());
					}

				} catch (Exception e) {
					log.error("Failed! by SellerId : " + sellerId, e);
				}
			}
			// saveMappedFiles.uploadResultXLS(offices,
			// "TaxablePurchases_Mapping", uploadFileName,errorSet, path,
			// sellerId, uploadReport);

			// Set<String> errorSet = returnProductConfigMap.keySet();
			downloadUploadReportXLS(offices, "TaxablePurchases_Mapping",
					uploadFileName, 5, errorSet, path, sellerId, uploadReport);

		} catch (Exception e) {

			e.printStackTrace();
			log.error("Failed! by SellerId : " + sellerId, e);
			addErrorUploadReport("TaxablePurchases_Mapping", sellerId,
					uploadReport);
			throw new MultipartException("Constraints Violated");
		}
		log.info("$$$ saveInventoryGroups ends : SaveContents $$$");
		return errorSet;
	}

	// My coding Product Config *********

	public Set<String> saveInventoryGroups(MultipartFile file, int sellerId,
			String path, UploadReport uploadReport) throws IOException {
		log.info("$$$ saveInventoryGroups starts : SaveContents $$$");
		boolean validaterow = true;
		Set<String> errorSet = new HashSet<>();
		StringBuffer errorMessage = null;
		Category category = null;
		String uploadFileName = "";
		try {
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = offices.getSheetAt(0);
			HSSFRow entry;
			Integer noOfEntries = 1;
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			uploadFileName = file.getOriginalFilename().substring(0,
					file.getOriginalFilename().lastIndexOf("."))
					+ new Date().getTime();
			log.info(noOfEntries.toString());
			log.debug("After getting no of rows" + noOfEntries);
			for (int rowIndex = 1; rowIndex < noOfEntries; rowIndex++) {

				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				errorMessage = new StringBuffer("Row :" + (rowIndex) + ":");
				category = new Category();
				try {
					if (entry.getCell(0) != null
							&& entry.getCell(0).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						Category catexist = categoryService.getCategory(entry
								.getCell(0).toString(), sellerId);
						if (catexist == null) {
							category.setCatName(entry.getCell(0).toString());
						} else {
							errorMessage
									.append(" Inventory group already Exists. ");
							validaterow = false;
						}
					} else {
						errorMessage.append(" Inventory Group is null ");
						validaterow = false;
					}
					if (entry.getCell(1) != null
							&& entry.getCell(1).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						category.setCatDescription(entry.getCell(0).toString());
					}

					if (validaterow) {
						// productService.addSKUMapping(productConfig,
						// sellerId);
						category.setSubCategory(false);
						categoryService.addCategory(category, sellerId);
					} else {
						errorSet.add(errorMessage.toString());
					}
				} catch (Exception e) {
					log.error("Failed! by SellerId : " + sellerId, e);

				}
			}

			saveMappedFiles.uploadResultXLS(offices, "Create_Inventory_Group",
					uploadFileName, errorSet, path, sellerId, uploadReport);

		} catch (Exception e) {

			log.error("Failed! by SellerId : " + sellerId, e);
			addErrorUploadReport("Create_Inventory_Group", sellerId,
					uploadReport);
			throw new MultipartException("Constraints Violated");
		}
		log.info("$$$ saveInventoryGroups ends : SaveContents $$$");
		return errorSet;
	}

	public Set<String> saveProductCategory(MultipartFile file, int sellerId,
			String path, UploadReport uploadReport) throws IOException {
		log.info("$$$ saveInventoryGroups starts : SaveContents $$$");
		boolean validaterow = true;
		Set<String> errorSet = new HashSet<>();
		StringBuffer errorMessage = null;
		Category category = null;
		String uploadFileName = "";
		try {
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = offices.getSheetAt(0);
			HSSFRow entry;
			Integer noOfEntries = 1;
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			uploadFileName = file.getOriginalFilename().substring(0,
					file.getOriginalFilename().lastIndexOf("."))
					+ new Date().getTime();
			log.info(noOfEntries.toString());
			log.debug("After getting no of rows" + noOfEntries);
			for (int rowIndex = 1; rowIndex < noOfEntries; rowIndex++) {

				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				errorMessage = new StringBuffer("Row :" + (rowIndex) + ":");
				category = new Category();
				try {
					if (entry.getCell(0) != null
							&& entry.getCell(0).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						Category catexist = categoryService.getCategory(entry
								.getCell(0).toString(), sellerId);
						if (catexist == null) {
							category.setCatName(entry.getCell(0).toString());
							if (entry.getCell(1) != null
									&& entry.getCell(1).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
								Category inventexist = categoryService
										.getCategory(entry.getCell(1)
												.toString(), sellerId);
								if (inventexist != null) {
									category.setParentCatName(inventexist
											.getCatName());
								} else {
									errorMessage
											.append(" Inventory group doesnt exist. ");
									validaterow = false;
								}

							} else {
								errorMessage
										.append(" Inventory group is null. ");
								validaterow = false;
							}

						} else {
							errorMessage
									.append(" Product Category already Exists. ");
							validaterow = false;
						}
					} else {
						errorMessage.append(" Product Category is null ");
						validaterow = false;
					}
					if (entry.getCell(2) != null
							&& entry.getCell(2).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						category.setCatDescription(entry.getCell(2).toString());
					}
					if (entry.getCell(3) != null
							&& entry.getCell(3).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						try {
							category.setTaxFreePriceLimit(Double
									.parseDouble(entry.getCell(3).toString()));
						} catch (Exception e) {
							e.printStackTrace();
							category.setTaxFreePriceLimit(0d);
						}
					} else {
						category.setTaxFreePriceLimit(0d);
					}

					if (validaterow) {
						// productService.addSKUMapping(productConfig,
						// sellerId);
						category.setSubCategory(true);
						categoryService.addCategory(category, sellerId);
					} else {
						errorSet.add(errorMessage.toString());
					}
				} catch (Exception e) {
					log.error("Failed! by SellerId : " + sellerId, e);
					errorSet.add("Invalid input!");
				}
			}

			saveMappedFiles.uploadResultXLS(offices, "Create_Product_Category",
					uploadFileName, errorSet, path, sellerId, uploadReport);
		} catch (Exception e) {

			log.error("Failed! by SellerId : " + sellerId, e);
			addErrorUploadReport("Create_Product_Category", sellerId,
					uploadReport);
			throw new MultipartException("Constraints Violated");
		}
		log.info("$$$ saveInventoryGroups ends : SaveContents $$$");
		return errorSet;
	}

	public Map<String, ProductConfigBean> saveVendorSKUMappingContents(
			MultipartFile file, int sellerId, String path,
			UploadReport uploadReport) throws IOException {
		log.info("$$$ saveVendorSKUMappingContents starts : SaveContents $$$");
		boolean validaterow = true;
		Map<String, ProductConfigBean> returnProductConfigMap = new LinkedHashMap<>();
		StringBuffer errorMessage = null;
		Map<String, String> uniqueProductMap = new HashMap<String, String>();
		Map<String, String> uniqueVendorSKUMap = new HashMap<String, String>();
		List<ProductConfig> saveList = new ArrayList<ProductConfig>();
		String uploadFileName = "";
		try {
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = offices.getSheetAt(0);
			HSSFRow entry;
			Integer noOfEntries = 1;
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			uploadFileName = file.getOriginalFilename().substring(0,
					file.getOriginalFilename().lastIndexOf("."))
					+ new Date().getTime();
			log.info(noOfEntries.toString());
			log.debug("After getting no of rows" + noOfEntries);
			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				ProductConfig productConfig = null;
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				errorMessage = new StringBuffer("Row :" + (rowIndex - 2) + ":");
				productConfig = new ProductConfig();
				try {
					if (entry.getCell(0) != null
							&& entry.getCell(0).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						entry.getCell(0).setCellType(HSSFCell.CELL_TYPE_STRING);

						Product product = productService.getProduct(entry
								.getCell(0).toString().trim().toUpperCase(),
								sellerId);
						if (product != null) {
							productConfig.setProductSkuCode(product
									.getProductSkuCode());
							if (entry.getCell(1) != null
									&& entry.getCell(1).getCellType() != HSSFCell.CELL_TYPE_BLANK
									&& entry.getCell(3) != null
									&& entry.getCell(3).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
								entry.getCell(1).setCellType(
										HSSFCell.CELL_TYPE_STRING);
								entry.getCell(3).setCellType(
										HSSFCell.CELL_TYPE_STRING);

								String mapKey = entry.getCell(1).toString()
										.trim().toUpperCase()
										+ "$"
										+ entry.getCell(3).toString().trim();

								List<ProductConfig> procon = productService
										.getProductConfig(entry.getCell(1)
												.toString().trim()
												.toUpperCase(), entry
												.getCell(3).toString(),
												sellerId);
								if (procon != null) {
									if (procon.size() == 1) {
										productConfig = procon.get(0);
									} else {
										errorMessage
												.append(" Multiple Mapping present for this Channel And SKU.");
										validaterow = false;
									}
								} else if (procon == null
										&& !uniqueProductMap
												.containsKey(mapKey)) {
									productConfig.setChannelSkuRef(entry
											.getCell(1).toString().trim()
											.toUpperCase());
									uniqueProductMap.put(mapKey, mapKey);
								} else {
									errorMessage
											.append(" Child SKU already present for that SKU ");
									validaterow = false;
								}
							} else {
								errorMessage.append(" Child SKU is null ");
								validaterow = false;
							}
							if (entry.getCell(3) != null
									&& entry.getCell(3).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
								if (!entry.getCell(3).toString()
										.equalsIgnoreCase("Myntra")) {
									if (productConfig.getChannelName() == null) {
										productConfig.setChannelName(entry
												.getCell(3).toString());
									}
								} else {
									errorMessage
											.append("Enter only Market Place Channels ");
									validaterow = false;
								}
							} else {
								errorMessage.append(" Channel Name is null ");
								validaterow = false;
							}
							if (entry.getCell(2) != null
									&& entry.getCell(2).getCellType() != HSSFCell.CELL_TYPE_BLANK
									&& entry.getCell(3) != null
									&& entry.getCell(3).getCellType() != HSSFCell.CELL_TYPE_BLANK) {

								entry.getCell(2).setCellType(
										HSSFCell.CELL_TYPE_STRING);

								/*
								 * String mapKey = entry.getCell(2).toString()
								 * .trim() + "$" +
								 * entry.getCell(3).toString().trim();
								 */

								/*
								 * ProductConfig procon = productService
								 * .getProductConfigByAnySKU(entry
								 * .getCell(2).toString(), entry
								 * .getCell(3).toString(), sellerId); if (procon
								 * == null && !uniqueVendorSKUMap
								 * .containsKey(mapKey)) {
								 */
								productConfig.setVendorSkuRef(entry.getCell(2)
										.toString().trim().toUpperCase());
								/*
								 * uniqueVendorSKUMap.put(mapKey, mapKey); }
								 * else { errorMessage .append(
								 * " Vendor SKU Code already present for that SKU "
								 * ); validaterow = false; }
								 */

							}

							if (entry.getCell(4) != null
									&& entry.getCell(4).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
								try {
									productConfig.setCommision(Float
											.valueOf(entry.getCell(4)
													.toString()));
								} catch (NumberFormatException e) {
									log.error("Failed! by SellerId : "
											+ sellerId, e);
									errorMessage
											.append(" Commission should be a number ");
									validaterow = false;
								}
							}
							
							if (entry.getCell(5) != null
									&& entry.getCell(5).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
								try {
									productConfig.setGrossNR(Double
											.valueOf(entry.getCell(5)
													.toString()));
								} catch (NumberFormatException e) {
									log.error("Failed! by SellerId : "+ sellerId, e);
									errorMessage.append("TP should be a number ");
									validaterow = false;
								}
							}

						} else {
							errorMessage
									.append(" Product with given SKU does not present ");
							validaterow = false;
						}
					} else {
						errorMessage.append(" Product SKU is null ");
						validaterow = false;
					}

					if (validaterow) {
						// productService.addSKUMapping(productConfig,
						// sellerId);
						saveList.add(productConfig);
					} else {
						returnProductConfigMap
								.put(errorMessage.toString(),
										ConverterClass
												.prepareProductConfigBean(productConfig));
					}
				} catch (Exception e) {
					log.error("Failed! by SellerId : " + sellerId, e);
					if (productConfig != null) {
						errorMessage.append("Invalid Input! ");
						returnProductConfigMap
								.put(errorMessage.toString(),
										ConverterClass
												.prepareProductConfigBean(productConfig));
					}

				}
				log.debug("Sheet values :1 :" + entry.getCell(1) + " 2 :"
						+ entry.getCell(2) + " 3 :" + entry.getCell(3));
				// Pre save to generate id for use in hierarchy
			}
			try {
				if (saveList != null && saveList.size() != 0)
					productService.addSKUMapping(saveList, sellerId);
			} catch (CustomException ce) {
				ce.printStackTrace();
				returnProductConfigMap
						.put("Row:1:Note-Some channel SKU("
								+ ce.getLocalMessage()
								+ " ) with valid input "
								+ "failed due to internal server error. Please contact admin.!",
								null);
			}
			Set<String> errorSet = returnProductConfigMap.keySet();
			downloadUploadReportXLS(offices, "MP_Vendor_SKU_Mapping",
					uploadFileName, 6, errorSet, path, sellerId, uploadReport);
		} catch (Exception e) {

			log.error("Failed! by SellerId : " + sellerId, e);
			addErrorUploadReport("MP_Vendor_SKU_Mapping", sellerId,
					uploadReport);
			throw new MultipartException("Constraints Violated");
		}
		log.info("$$$ saveVendorSKUMappingContents ends : SaveContents $$$");
		return returnProductConfigMap;
	}

	public Map<String, ProductConfigBean> saveProductConfigContents(
			MultipartFile file, int sellerId, String path,
			UploadReport uploadReport) throws IOException {
		log.info("$$$ saveProductConfigContents starts : SaveContents $$$");
		boolean validaterow = true;
		Map<String, ProductConfigBean> returnProductConfigMap = new LinkedHashMap<>();
		StringBuffer errorMessage = null;
		String uploadFileName = "";
		try {
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = offices.getSheetAt(0);
			HSSFRow entry;
			Integer noOfEntries = 1;
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			uploadFileName = file.getOriginalFilename().substring(0,
					file.getOriginalFilename().lastIndexOf("."))
					+ new Date().getTime();
			log.info(noOfEntries.toString());
			log.debug("After getting no of rows" + noOfEntries);
			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				ProductConfig productConfig = null;
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				errorMessage = new StringBuffer("Row :" + (rowIndex - 2) + ":");
				log.debug("Product 1" + entry.getCell(1));
				log.debug("Product  2" + entry.getCell(2));
				log.debug(entry.getCell(3));
				log.debug(entry.getCell(4));
				productConfig = new ProductConfig();
				try {
					if (entry.getCell(0) != null
							&& entry.getCell(0).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						productConfig.setProductSkuCode(entry.getCell(0)
								.toString().trim().toUpperCase());
					} else {
						errorMessage.append(" Product SKU is null ");
						validaterow = false;
					}
					if (entry.getCell(1) != null
							&& entry.getCell(1).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						productConfig.setChannelName(entry.getCell(1)
								.toString().toLowerCase());
					} else {
						errorMessage.append(" Channel Name is null ");
						validaterow = false;
					}
					if (entry.getCell(2) != null
							&& entry.getCell(2).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						productConfig.setChannelSkuRef(entry.getCell(2)
								.toString().trim().toUpperCase());
					} else {
						errorMessage.append(" Channel SKU is null ");
						validaterow = false;
					}

					List<ProductConfig> productConfigChk = productService
							.getProductConfig(productConfig.getChannelSkuRef(),
									productConfig.getChannelName(), sellerId);
					if (productConfigChk != null) {
						errorMessage.append(" Product config already exist ");
						validaterow = false;
					}

					if (entry.getCell(3) != null
							&& entry.getCell(3).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						try {
							productConfig.setMrp(Double.valueOf(entry
									.getCell(3).toString()));
						} catch (NumberFormatException e) {
							log.error("Failed! by SellerId : " + sellerId, e);
							errorMessage.append(" MRP should be a number ");
							validaterow = false;
						}
					} else {
						errorMessage.append(" MRP is null ");
						validaterow = false;
					}

					if (entry.getCell(4) != null
							&& entry.getCell(4).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						try {
							productConfig.setProductPrice((double) entry
									.getCell(4).getNumericCellValue());
						} catch (NumberFormatException e) {
							log.error("Failed! by SellerId : " + sellerId, e);
							errorMessage
									.append(" Product Price should be a number ");
							validaterow = false;
						}
					} else {
						errorMessage.append(" Product Price is null ");
						validaterow = false;
					}
					if (entry.getCell(5) != null
							&& entry.getCell(5).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						try {
							productConfig.setDiscount(Float.valueOf(entry
									.getCell(5).toString()));
						} catch (NumberFormatException e) {
							log.error("Failed! by SellerId : " + sellerId, e);
							errorMessage
									.append(" Discount should be a number ");
							validaterow = false;
						}
					} else {
						errorMessage.append(" Discount is null ");
						validaterow = false;
					}

					if (validaterow) {
						productService
								.addProductConfig(productConfig, sellerId);
					} else {
						returnProductConfigMap
								.put(errorMessage.toString(),
										ConverterClass
												.prepareProductConfigBean(productConfig));
					}
					log.debug("Sheet values :1 :" + entry.getCell(1) + " 2 :"
							+ entry.getCell(2) + " 3 :" + entry.getCell(3));
					// Pre save to generate id for use in hierarchy
				} catch (Exception e) {
					if (productConfig != null) {
						errorMessage.append("Invalid Input! ");
						returnProductConfigMap
								.put(errorMessage.toString(),
										ConverterClass
												.prepareProductConfigBean(productConfig));
					}
				}
			}
			Set<String> errorSet = returnProductConfigMap.keySet();
			downloadUploadReportXLS(offices, "PO_Product_Config",
					uploadFileName, 6, errorSet, path, sellerId, uploadReport);
		} catch (Exception e) {
			log.debug("Inside save contents exception :"
					+ e.getLocalizedMessage());
			e.printStackTrace();
			log.error("Failed! by SellerId : " + sellerId, e);
			addErrorUploadReport("PO_Product_Config", sellerId, uploadReport);
			throw new MultipartException("Constraints Violated");
		}
		log.info("$$$ saveProductConfigContents ends : SaveContents $$$");
		return returnProductConfigMap;
	}

	// My Coding Product Config Ends ********

	/*
	 * public Map<String, Object> savePaymentContents(MultipartFile file, int
	 * sellerId, String path, UploadReport uploadReport) throws IOException {
	 * log.info("$$$ savePaymentContents starts : SaveContents $$$");
	 * PaymentUpload paymentUpload = new PaymentUpload(); double totalpositive =
	 * 0; double totalnegative = 0; String skucode = null; Order order = null;
	 * String uploadPaymentId = null; HSSFRow entry; Integer noOfEntries = 1;
	 * String uploadFileName = ""; Date todaydat = new Date();
	 * List<ManualCharges> manualChargesList = new ArrayList<ManualCharges>();
	 * Map<String, Object> returnPaymentMap = new LinkedHashMap<>(); Map<String,
	 * String> channelOrderIdCheck = new HashMap<String, String>(); StringBuffer
	 * errorMessage = null; boolean validaterow = true; boolean
	 * generatePaymentUpload = false; String channelName = null;
	 * 
	 * try { HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
	 * 
	 * HSSFSheet worksheet = offices.getSheetAt(0); while
	 * (worksheet.getRow(noOfEntries) != null) { noOfEntries++; } uploadFileName
	 * = file.getOriginalFilename().substring(0,
	 * file.getOriginalFilename().lastIndexOf(".")) + new Date().getTime();
	 * log.info(noOfEntries.toString()); log.debug("After getting no of rows" +
	 * noOfEntries); for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++)
	 * { entry = worksheet.getRow(rowIndex); validaterow = true; double
	 * positiveAmount = 0; String channelId = ""; Partner partner = null;
	 * ProductConfig productConfig = null; List<ProductConfig> productConfigs =
	 * null; double negativeAmount = 0; // Product product=new Product();
	 * OrderPayment payment = new OrderPayment(); boolean combo=false;
	 * List<Order> onj=null; Date recievedDate=null; boolean
	 * orderavailable=false; errorMessage = new StringBuffer("Row :" + (rowIndex
	 * - 2) + ":"); try { if (entry.getCell(0) != null &&
	 * entry.getCell(0).getCellType() != HSSFCell.CELL_TYPE_BLANK &&
	 * entry.getCell(0).toString() .equalsIgnoreCase("payment")) {
	 * 
	 * if (entry.getCell(6) != null && entry.getCell(6).getCellType() !=
	 * HSSFCell.CELL_TYPE_BLANK) { partner = partnerService.getPartner(entry
	 * .getCell(6).toString(), sellerId); if (partner != null) { if
	 * (entry.getCell(1) != null && entry.getCell(1).getCellType() !=
	 * HSSFCell.CELL_TYPE_BLANK) { entry.getCell(1).setCellType(
	 * HSSFCell.CELL_TYPE_STRING); if (entry.getCell(2) != null &&
	 * entry.getCell(2).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
	 * productConfigs = productService .getProductConfig(entry
	 * .getCell(2).toString() .trim().toUpperCase(), partner.getPcName(),
	 * sellerId); if (productConfigs != null) {
	 * 
	 * if (productConfigs.size() == 1) { productConfig = (ProductConfig)
	 * productConfigs .get(0); } else { ProductConfig pc = null; Set<String>
	 * parent = new HashSet<String>(); for (Object PCo : productConfigs) { pc =
	 * (ProductConfig) PCo; parent.add(pc .getProductSkuCode()); } if
	 * (parent.size() == 1) { productConfig = (ProductConfig) productConfigs
	 * .get(0); } else { errorMessage
	 * .append(" Multiple Mapping present for this Channel And SKU.");
	 * validaterow = false; } } try { if (productConfig .getVendorSkuRef() !=
	 * null) { channelId = entry .getCell(1) .toString() +
	 * GlobalConstant.orderUniqueSymbol + productConfig .getVendorSkuRef(); }
	 * else { errorMessage .append("Vendor Sku Is Not Present."); validaterow =
	 * false; } } catch (Exception e) {
	 * 
	 * } } else { errorMessage .append("No Product Mappings With This SKU.");
	 * validaterow = false; } } else { channelId = entry.getCell(1).toString();
	 * }
	 * 
	 * if (partner .getPcName() .toLowerCase()
	 * .contains(GlobalConstant.PCFLIPKART) || partner .getPcName()
	 * .toLowerCase() .contains( GlobalConstant.PCPAYTM)|| partner .getPcName()
	 * .toLowerCase() .contains( GlobalConstant.PCJABONG)) {
	 * 
	 * if (entry.getCell(3) != null && entry.getCell(3) .getCellType() !=
	 * HSSFCell.CELL_TYPE_BLANK) { channelId = channelId +
	 * GlobalConstant.orderUniqueSymbol + entry.getCell(3) .toString();
	 * 
	 * } else { errorMessage .append(
	 * " Secondary Order ID is Null, it is mandatory for Flipkart,Paytm and Jabong"
	 * ); validaterow = false; } }
	 * 
	 * onj = orderService .searchAsIsOrder("channelOrderID", channelId,
	 * sellerId);
	 * 
	 * if (entry.getCell(8) != null && StringUtils.isNotBlank(entry
	 * .getCell(8).toString())) {
	 * 
	 * try { if (HSSFDateUtil .isCellDateFormatted(entry .getCell(8))) {
	 * recievedDate=entry .getCell(8) .getDateCellValue(); } } catch (Exception
	 * e) { e.printStackTrace(); } }
	 * 
	 * if (onj != null) { if (onj.size() == 1) { channelId = onj.get(0)
	 * .getChannelOrderID(); } else if (onj.size() > 1) { for(Order
	 * ordcheck:onj){ if(ordcheck.getTypeIdentifier()!=null){
	 * if(ordcheck.getTypeIdentifier().contains(entry.getCell(1).toString()))
	 * combo=true; else combo=false; } else if (recievedDate!=null){
	 * if(format.format
	 * (ordcheck.getOrderDate()).equals(format.format(recievedDate))){
	 * orderavailable=true; channelId=ordcheck.getChannelOrderID(); } } else{
	 * errorMessage .append("Multiple Orders With Channel Order ID.");
	 * validaterow = false; } } if(!(orderavailable||combo)){ errorMessage
	 * .append("Multiple Orders With Channel Order ID."); validaterow = false; }
	 * } else { errorMessage .append("Multiple Orders With Channel Order ID.");
	 * validaterow = false; } } else if (entry.getCell(3) != null &&
	 * entry.getCell(3).getCellType() != HSSFCell.CELL_TYPE_BLANK) { onj =
	 * orderService.searchAsIsOrder( "subOrderID", entry.getCell(3) .toString(),
	 * sellerId); if (onj != null) { if (onj.size() == 1) { channelId =
	 * onj.get(0) .getChannelOrderID(); } else { errorMessage
	 * .append("Multiple Orders With Channel Order ID."); validaterow = false; }
	 * } else { errorMessage
	 * .append(" No Orders with Channel OrderId. No Order With Secondary Order ID."
	 * ); validaterow = false; } } else { errorMessage
	 * .append(" No Orders with Channel OrderId, Secondary Order ID is Null.");
	 * validaterow = false; } } else if (entry.getCell(3) != null &&
	 * entry.getCell(3).getCellType() != HSSFCell.CELL_TYPE_BLANK) { onj =
	 * orderService .searchAsIsOrder( "subOrderID", entry.getCell(3).toString(),
	 * sellerId); if (onj != null) { if (onj.size() == 1) { channelId =
	 * onj.get(0) .getChannelOrderID(); } else { errorMessage
	 * .append("Multiple Orders With Secondary Order ID."); validaterow = false;
	 * } } else { errorMessage
	 * .append("Channel Order ID is Null. No Orders with Secondary Order ID.");
	 * validaterow = false; } } else { errorMessage
	 * .append(" Channel OrderId And Secondary Order ID is null"); validaterow =
	 * false; } try { if (entry.getCell(4) != null &&
	 * StringUtils.isNotBlank(entry .getCell(4).toString())) {
	 * 
	 * if ((int) Float.parseFloat(entry .getCell(4).toString()) < 0) {
	 * payment.setNegativeAmount(Math.abs(Double .parseDouble(entry.getCell(
	 * 4).toString()))); negativeAmount = Math.abs(Double
	 * .parseDouble(entry.getCell( 4).toString())); } else {
	 * payment.setPositiveAmount(Double .parseDouble(entry.getCell(
	 * 4).toString())); positiveAmount = Double .parseDouble(entry.getCell(
	 * 4).toString()); } } else { errorMessage
	 * .append(" Amount should be given "); validaterow = false; } } catch
	 * (NumberFormatException e) { log.error("Failed! by SellerId : " +
	 * sellerId, e); errorMessage .append(" Recieved amount should be number ");
	 * validaterow = false; } if (entry.getCell(5) != null &&
	 * StringUtils.isNotBlank(entry .getCell(5).toString())) {
	 * 
	 * try { if (HSSFDateUtil .isCellDateFormatted(entry .getCell(5))) {
	 * payment.setDateofPayment(entry .getCell(5) .getDateCellValue()); } else {
	 * errorMessage .append(" Payment Date format is wrong ,enter mm/dd/yyyy ");
	 * validaterow = false; } } catch (Exception e) { errorMessage
	 * .append(" Payment Date format is wrong ,enter mm/dd/yyyy "); validaterow
	 * = false; } } else { errorMessage .append(" Payment Date is null ");
	 * validaterow = false; } log.debug("Sheet values :1 :" + entry.getCell(1) +
	 * " 2 :" + entry.getCell(2) + " 3 :" + entry.getCell(3));
	 * 
	 * } else { errorMessage.append("Channel Not Present."); validaterow =
	 * false; }
	 * 
	 * if (validaterow) { payment.setPaymentFileName(uploadFileName);
	 * totalpositive = totalpositive + positiveAmount; totalnegative =
	 * totalnegative + negativeAmount; if(combo) {
	 * if(payment.getPositiveAmount()>0) {
	 * payment.setPositiveAmount(payment.getPositiveAmount()/onj.size()); } else
	 * { payment.setNegativeAmount(payment.getNegativeAmount()/onj.size()); }
	 * for(Order ord:onj) { order = orderService.addOrderPayment(skucode,
	 * ord.getChannelOrderID(), payment, sellerId); } } else { order =
	 * orderService.addOrderPayment(skucode, channelId, payment, sellerId); }
	 * 
	 * // New PaymentUpload_Order paymentUpload_Order = new
	 * PaymentUpload_Order(); paymentUpload_Order.setOrderId(order
	 * .getOrderId()); paymentUpload_Order.setAmount(positiveAmount -
	 * negativeAmount); paymentUpload_Order .setPaymentUpload(paymentUpload);
	 * paymentUpload.getOrderList().add( paymentUpload_Order); } else {
	 * returnPaymentMap.put(errorMessage.toString(),
	 * ConverterClass.prepareOrderBean(order)); } if (order != null &&
	 * validaterow == true) { if (!channelOrderIdCheck.containsKey(channelId)) {
	 * System.out.println("##### OrderID : " + order.getOrderId());
	 * channelOrderIdCheck.put(channelId, channelId); //
	 * order.getPaymentUpload().add(paymentUpload); //
	 * paymentUpload.getOrders().add(order); generatePaymentUpload = true; } } }
	 * else { errorMessage.append("Channel is Blank."); validaterow = false; } }
	 * else if (entry.getCell(0) != null && entry.getCell(0).getCellType() !=
	 * HSSFCell.CELL_TYPE_BLANK && entry.getCell(0).toString()
	 * .equalsIgnoreCase("manual charges")) { ManualCharges manualCharges = new
	 * ManualCharges(); if (entry.getCell(6) != null &&
	 * entry.getCell(6).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
	 * manualCharges.setPartner(entry.getCell(6) .toString()); channelName =
	 * entry.getCell(6).toString(); } if (entry.getCell(7) != null &&
	 * entry.getCell(7).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
	 * manualCharges.setParticular(entry.getCell(7) .toString()); } try { if
	 * (entry.getCell(4) != null && StringUtils.isNotBlank(entry.getCell(4)
	 * .toString())) {
	 * 
	 * if ((int) Float.parseFloat(entry.getCell(4) .toString()) < 0) {
	 * payment.setNegativeAmount(Math.abs(Double .parseDouble(entry.getCell(4)
	 * .toString()))); } else { payment.setPositiveAmount(Double
	 * .parseDouble(entry.getCell(4) .toString())); } } else {
	 * errorMessage.append(" Amount should be given "); validaterow = false; } }
	 * catch (NumberFormatException e) { log.error("Failed! by SellerId : " +
	 * sellerId, e); errorMessage .append(" Recieved amount should be number ");
	 * validaterow = false; } if (entry.getCell(5) != null &&
	 * StringUtils.isNotBlank(entry.getCell(5) .toString())) {
	 * 
	 * try { if (HSSFDateUtil.isCellDateFormatted(entry .getCell(5))) {
	 * manualCharges.setDateOfPayment(entry .getCell(5).getDateCellValue()); }
	 * else { errorMessage
	 * .append(" Payment Date format is wrong ,enter mm/dd/yyyy "); validaterow
	 * = false; } } catch (Exception e) { errorMessage
	 * .append(" Payment Date format is wrong ,enter mm/dd/yyyy "); validaterow
	 * = false; } } else { errorMessage.append(" Payment Date is null ");
	 * validaterow = false; } if (validaterow) {
	 * 
	 * manualChargesList.add(manualCharges); } else { returnPaymentMap
	 * .put(errorMessage.toString(), ConverterClass
	 * .prepareManualChargesBean(manualCharges)); }
	 * 
	 * } else { errorMessage.append("Invalid Criteria !");
	 * returnPaymentMap.put(errorMessage.toString(), null); } } catch (Exception
	 * e) { errorMessage.append("Invalid Input !");
	 * returnPaymentMap.put(errorMessage.toString(), null); } }
	 * 
	 * if (generatePaymentUpload) { log.debug(" Total Positive Amount : " +
	 * totalpositive); log.debug(" Total Negative Amount : " + totalnegative);
	 * paymentUpload.setTotalpositivevalue(totalpositive);
	 * paymentUpload.setTotalnegativevalue(totalnegative);
	 * paymentUpload.setNetRecievedAmount(totalpositive - totalnegative);
	 * paymentUpload.setUploadDesc("PAYU" + sellerId + "" + todaydat.getTime());
	 * paymentUpload.setUploadStatus("Success"); uploadPaymentId =
	 * paymentUploadService.addPaymentUpload( paymentUpload, sellerId); } if
	 * (manualChargesList != null && manualChargesList.size() != 0) { for
	 * (ManualCharges manuals : manualChargesList) { try {
	 * manuals.setChargesDesc(uploadPaymentId); expenseService.addExpense( new
	 * Expenses("Manual Charges", uploadPaymentId, "Manual Charges", new Date(),
	 * manuals .getDateOfPayment(), manuals .getPaidAmount(), channelName,
	 * sellerId), sellerId); } catch (Exception e) {
	 * log.error("Failed! by SellerId : " + sellerId, e); } }
	 * manualChargesService.addListManualCharges(manualChargesList, sellerId); }
	 * Set<String> errorSet = returnPaymentMap.keySet();
	 * downloadUploadReportXLS(offices, "MP_Payment_Upload", uploadFileName, 9,
	 * errorSet, path, sellerId, uploadReport); } catch (Exception e) {
	 * log.debug("Inside save contents exception :" + e.getLocalizedMessage());
	 * e.printStackTrace(); log.error("Failed! by SellerId : " + sellerId, e);
	 * addErrorUploadReport("MP_Payment_Upload", sellerId, uploadReport); throw
	 * new MultipartException("Constraints Violated"); }
	 * log.info("$$$ savePaymentContents ends : SaveContents $$$"); return
	 * returnPaymentMap; }
	 */
	public Map<String, Object> savePaymentContents(MultipartFile file,
			int sellerId, String path, UploadReport uploadReport)
			throws IOException {
		log.info("$$$ savePaymentContents starts : SaveContents $$$");
		PaymentUpload paymentUpload = new PaymentUpload();
		double totalpositive = 0;
		double totalnegative = 0;
		String skucode = null;
		Order order = null;
		String uploadPaymentId = null;
		HSSFRow entry;
		Integer noOfEntries = 1;
		String uploadFileName = "";
		Date todaydat = new Date();
		List<ManualCharges> manualChargesList = new ArrayList<ManualCharges>();
		Map<String, Object> returnPaymentMap = new LinkedHashMap<>();
		Map<String, String> channelOrderIdCheck = new HashMap<String, String>();
		StringBuffer errorMessage = null;
		boolean validaterow = true;
		boolean generatePaymentUpload = false;
		String channelName = null;

		try {
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());

			HSSFSheet worksheet = offices.getSheetAt(0);
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			uploadFileName = file.getOriginalFilename().substring(0,
					file.getOriginalFilename().lastIndexOf("."))
					+ new Date().getTime();
			log.info(noOfEntries.toString());
			log.debug("After getting no of rows" + noOfEntries);
			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				double positiveAmount = 0;
				String channelId = "";
				Partner partner = null;
				ProductConfig productConfig = null;
				List<ProductConfig> productConfigs = null;
				double negativeAmount = 0;
				// Product product=new Product();
				OrderPayment payment = new OrderPayment();
				// boolean combo = false;
				List<Order> onj = null;
				Date recievedDate = null;
				boolean orderavailable = false;
				errorMessage = new StringBuffer("Row :" + (rowIndex - 2) + ":");
				try {
					if (entry.getCell(0) != null
							&& entry.getCell(0).getCellType() != HSSFCell.CELL_TYPE_BLANK
							&& entry.getCell(0).toString()
									.equalsIgnoreCase("payment")) {

						if (entry.getCell(6) != null
								&& entry.getCell(6).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							partner = partnerService.getPartner(entry
									.getCell(6).toString(), sellerId);
							if (partner != null) {
								System.out.println(" Channle id : "+entry.getCell(1).toString());
								if (entry.getCell(1) != null
										&& entry.getCell(1).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
									entry.getCell(1).setCellType(
											HSSFCell.CELL_TYPE_STRING);
									if (entry.getCell(2) != null
											&& entry.getCell(2).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
										productConfigs = productService
												.getProductConfig(entry
														.getCell(2).toString()
														.trim().toUpperCase(),
														partner.getPcName(),
														sellerId);
										if (productConfigs != null) {

											if (productConfigs.size() == 1) {
												productConfig = (ProductConfig) productConfigs
														.get(0);
											} else {
												ProductConfig pc = null;
												Set<String> parent = new HashSet<String>();
												for (Object PCo : productConfigs) {
													pc = (ProductConfig) PCo;
													parent.add(pc
															.getProductSkuCode());
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
												if (productConfig
														.getVendorSkuRef() != null) {
													channelId = entry
															.getCell(1)
															.toString()
															+ GlobalConstant.orderUniqueSymbol
															+ productConfig
																	.getVendorSkuRef();
												} else {
													errorMessage
															.append("Vendor Sku Is Not Present.");
													validaterow = false;
												}
											} catch (Exception e) {

											}
										} else {
											errorMessage
													.append("No Product Mappings With This SKU.");
											validaterow = false;
										}
									} else {
										channelId = entry.getCell(1).toString();
									}

									if (partner
											.getPcName()
											.toLowerCase()
											.contains(GlobalConstant.PCFLIPKART)
											|| partner
													.getPcName()
													.toLowerCase()
													.contains(
															GlobalConstant.PCPAYTM)
											|| partner
													.getPcName()
													.toLowerCase()
													.contains(
															GlobalConstant.PCJABONG)) {

										if (entry.getCell(3) != null
												&& entry.getCell(3)
														.getCellType() != HSSFCell.CELL_TYPE_BLANK) {
											entry.getCell(3).setCellType(
													HSSFCell.CELL_TYPE_STRING);
											String itemId = entry.getCell(3).toString();
											if(partner.getPcName().toLowerCase().contains(GlobalConstant.PCFLIPKART)
													&& itemId.contains("OI:")){
												itemId = itemId.replaceAll("OI:", "");												
											}
											channelId = channelId
													+ GlobalConstant.orderUniqueSymbol
													+ itemId;
										} else {
											errorMessage
													.append(" Secondary Order ID is Null, it is mandatory for Flipkart,Paytm and Jabong");
											validaterow = false;
										}
									}

									onj = orderService.searchAsIsOrder(
											"channelOrderID", channelId,
											sellerId);

									if (entry.getCell(8) != null
											&& StringUtils.isNotBlank(entry
													.getCell(8).toString())) {

										try {
											if (HSSFDateUtil
													.isCellDateFormatted(entry
															.getCell(8))) {
												recievedDate = entry.getCell(8)
														.getDateCellValue();
											}
										} catch (Exception e) {
											e.printStackTrace();
										}
									}

									if (onj != null) {
										if (onj.size() == 1) {
											channelId = onj.get(0)
													.getChannelOrderID();
										} else if (onj.size() > 1) {
											for (Order ordcheck : onj) {
												if (recievedDate != null) {
													if (format
															.format(ordcheck
																	.getOrderDate())
															.equals(format
																	.format(recievedDate))) {
														orderavailable = true;
														channelId = ordcheck
																.getChannelOrderID();
													}
												} else {
													errorMessage
															.append("Multiple Orders With Channel Order ID and no recieved date.");
													validaterow = false;
												}
											}
											if (!orderavailable) {
												errorMessage
														.append("Multiple Orders With Channel Order ID.");
												validaterow = false;
											}
										} else {
											errorMessage
													.append("Multiple Orders With Channel Order ID.");
											validaterow = false;
										}
									} else if (entry.getCell(3) != null
											&& entry.getCell(3).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
										onj = orderService.searchAsIsOrder(
												"subOrderID", entry.getCell(3)
														.toString(), sellerId);
										if (onj != null) {
											if (onj.size() == 1) {
												channelId = onj.get(0)
														.getChannelOrderID();
											} else {
												errorMessage
														.append("Multiple Orders With Channel Order ID.");
												validaterow = false;
											}
										} else {
											errorMessage
													.append(" No Orders with Channel OrderId. No Order With Secondary Order ID.");
											validaterow = false;
										}
									} else {
										errorMessage
												.append(" No Orders with Channel OrderId, Secondary Order ID is Null.");
										validaterow = false;
									}
								} else if (entry.getCell(3) != null
										&& entry.getCell(3).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
									onj = orderService.searchAsIsOrder(
											"subOrderID", entry.getCell(3)
													.toString(), sellerId);
									if (onj != null) {
										if (onj.size() == 1) {
											channelId = onj.get(0)
													.getChannelOrderID();
										} else {
											errorMessage
													.append("Multiple Orders With Secondary Order ID.");
											validaterow = false;
										}
									} else {
										errorMessage
												.append("Channel Order ID is Null. No Orders with Secondary Order ID.");
										validaterow = false;
									}
								} else {
									errorMessage
											.append(" Channel OrderId And Secondary Order ID is null");
									validaterow = false;
								}
								try {
									if (entry.getCell(4) != null
											&& StringUtils.isNotBlank(entry
													.getCell(4).toString())) {

										if ((int) Float.parseFloat(entry
												.getCell(4).toString()) < 0) {
											payment.setNegativeAmount(Math.abs(Double
													.parseDouble(entry.getCell(
															4).toString())));
											negativeAmount = Math.abs(Double
													.parseDouble(entry.getCell(
															4).toString()));
										} else {
											payment.setPositiveAmount(Double
													.parseDouble(entry.getCell(
															4).toString()));
											positiveAmount = Double
													.parseDouble(entry.getCell(
															4).toString());
										}
									} else {
										errorMessage
												.append(" Amount should be given ");
										validaterow = false;
									}
								} catch (NumberFormatException e) {
									log.error("Failed! by SellerId : "
											+ sellerId, e);
									errorMessage
											.append(" Recieved amount should be number ");
									validaterow = false;
								}
								if (entry.getCell(5) != null
										&& StringUtils.isNotBlank(entry
												.getCell(5).toString())) {

									try {
										if (HSSFDateUtil
												.isCellDateFormatted(entry
														.getCell(5))) {
											payment.setDateofPayment(entry
													.getCell(5)
													.getDateCellValue());
										} else {
											errorMessage
													.append(" Payment Date format is wrong ,enter mm/dd/yyyy ");
											validaterow = false;
										}
									} catch (Exception e) {
										errorMessage
												.append(" Payment Date format is wrong ,enter mm/dd/yyyy ");
										validaterow = false;
									}
								} else {
									errorMessage
											.append(" Payment Date is null ");
									validaterow = false;
								}
								log.debug("Sheet values :1 :"
										+ entry.getCell(1) + " 2 :"
										+ entry.getCell(2) + " 3 :"
										+ entry.getCell(3));

							} else {
								errorMessage.append("Channel Not Present.");
								validaterow = false;
							}

							if (validaterow) {
								payment.setPaymentFileName(uploadFileName);
								totalpositive = totalpositive + positiveAmount;
								totalnegative = totalnegative + negativeAmount;

								order = orderService.addOrderPayment(skucode,
										channelId, payment, sellerId);

								// New
								PaymentUpload_Order paymentUpload_Order = new PaymentUpload_Order();
								paymentUpload_Order.setOrderId(order
										.getOrderId());
								paymentUpload_Order.setAmount(positiveAmount
										- negativeAmount);
								paymentUpload_Order
										.setPaymentUpload(paymentUpload);
								paymentUpload.getOrderList().add(
										paymentUpload_Order);
							} else {
								returnPaymentMap.put(errorMessage.toString(),
										ConverterClass.prepareOrderBean(order));
							}
							if (order != null && validaterow == true) {
								if (!channelOrderIdCheck.containsKey(channelId)) {
									System.out.println("##### OrderID : "
											+ order.getOrderId());
									channelOrderIdCheck.put(channelId,
											channelId);
									// order.getPaymentUpload().add(paymentUpload);
									// paymentUpload.getOrders().add(order);
									generatePaymentUpload = true;
								}
							}
						} else {
							errorMessage.append("Channel is Blank.");
							validaterow = false;
						}
					} else if (entry.getCell(0) != null
							&& entry.getCell(0).getCellType() != HSSFCell.CELL_TYPE_BLANK
							&& entry.getCell(0).toString()
									.equalsIgnoreCase("manual charges")) {
						ManualCharges manualCharges = new ManualCharges();
						if (entry.getCell(6) != null
								&& entry.getCell(6).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							manualCharges.setPartner(entry.getCell(6)
									.toString());
							channelName = entry.getCell(6).toString();
						}
						if (entry.getCell(7) != null
								&& entry.getCell(7).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							manualCharges.setParticular(entry.getCell(7)
									.toString());
						}
						try {
							if (entry.getCell(4) != null
									&& StringUtils.isNotBlank(entry.getCell(4)
											.toString())) {

								if ((int) Float.parseFloat(entry.getCell(4)
										.toString()) < 0) {
									payment.setNegativeAmount(Math.abs(Double
											.parseDouble(entry.getCell(4)
													.toString())));
								} else {
									payment.setPositiveAmount(Double
											.parseDouble(entry.getCell(4)
													.toString()));
								}
							} else {
								errorMessage.append(" Amount should be given ");
								validaterow = false;
							}
						} catch (NumberFormatException e) {
							log.error("Failed! by SellerId : " + sellerId, e);
							errorMessage
									.append(" Recieved amount should be number ");
							validaterow = false;
						}
						if (entry.getCell(5) != null
								&& StringUtils.isNotBlank(entry.getCell(5)
										.toString())) {

							try {
								if (HSSFDateUtil.isCellDateFormatted(entry
										.getCell(5))) {
									manualCharges.setDateOfPayment(entry
											.getCell(5).getDateCellValue());
								} else {
									errorMessage
											.append(" Payment Date format is wrong ,enter mm/dd/yyyy ");
									validaterow = false;
								}
							} catch (Exception e) {
								errorMessage
										.append(" Payment Date format is wrong ,enter mm/dd/yyyy ");
								validaterow = false;
							}
						} else {
							errorMessage.append(" Payment Date is null ");
							validaterow = false;
						}
						if (validaterow) {

							manualChargesList.add(manualCharges);
						} else {
							returnPaymentMap
									.put(errorMessage.toString(),
											ConverterClass
													.prepareManualChargesBean(manualCharges));
						}

					} else {
						errorMessage.append("Invalid Criteria !");
						returnPaymentMap.put(errorMessage.toString(), null);
					}
				} catch (Exception e) {
					errorMessage.append("Invalid Input !");
					returnPaymentMap.put(errorMessage.toString(), null);
				}
			}

			if (generatePaymentUpload) {
				log.debug(" Total Positive Amount : " + totalpositive);
				log.debug(" Total Negative Amount : " + totalnegative);
				paymentUpload.setTotalpositivevalue(totalpositive);
				paymentUpload.setTotalnegativevalue(totalnegative);
				paymentUpload.setNetRecievedAmount(totalpositive
						- totalnegative);
				paymentUpload.setUploadDesc("PAYU" + sellerId + ""
						+ todaydat.getTime());
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
												.getPaidAmount(), channelName,
										sellerId), sellerId);
					} catch (Exception e) {
						log.error("Failed! by SellerId : " + sellerId, e);
					}
				}
				manualChargesService.addListManualCharges(manualChargesList,
						sellerId);
			}
			Set<String> errorSet = returnPaymentMap.keySet();
			downloadUploadReportXLS(offices, "MP_Payment_Upload",
					uploadFileName, 9, errorSet, path, sellerId, uploadReport);
		} catch (Exception e) {
			log.debug("Inside save contents exception :"
					+ e.getLocalizedMessage());
			e.printStackTrace();
			log.error("Failed! by SellerId : " + sellerId, e);
			addErrorUploadReport("MP_Payment_Upload", sellerId, uploadReport);
			throw new MultipartException("Constraints Violated");
		}
		log.info("$$$ savePaymentContents ends : SaveContents $$$");
		return returnPaymentMap;
	}

	public Map<String, String> saveInventoryDetails(MultipartFile file,
			int sellerId, String path, UploadReport uploadReport)
			throws IOException {
		log.info("$$$ saveInventoryDetails starts : SaveContents $$$");
		Map<String, String> returnInventoryMap = new LinkedHashMap<>();
		StringBuffer errorMessage = null;
		boolean validaterow = true;
		String SkUCode = null;
		int currentInventory = 0;
		int quantoAdd = 0;
		int quantoSub = 0;
		String uploadFileName = "";
		try {
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = offices.getSheetAt(0);
			HSSFRow entry;
			Integer noOfEntries = 1;

			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			uploadFileName = file.getOriginalFilename().substring(0,
					file.getOriginalFilename().lastIndexOf("."))
					+ new Date().getTime();
			log.info(noOfEntries.toString());
			log.debug("After getting no of rows" + noOfEntries);
			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				errorMessage = new StringBuffer("Row :" + (rowIndex - 2) + ":");
				validaterow = true;
				entry = worksheet.getRow(rowIndex);
				log.debug("Product 1" + entry.getCell(1));
				log.debug("Product  2" + entry.getCell(2));
				if (entry.getCell(0) != null
						&& StringUtils.isNotBlank(entry.getCell(0).toString())) {
					Product product = productService.getProduct(entry
							.getCell(0).toString(), sellerId);
					if (product == null) {
						errorMessage.append(" Product SKU does not exist ");
						validaterow = false;
					} else {
						SkUCode = entry.getCell(0).toString().trim()
								.toUpperCase();
					}
					if ((entry.getCell(1) == null
							|| StringUtils.isNotBlank(entry.getCell(1)
									.toString()) || (int) entry.getCell(1)
							.getNumericCellValue() == 0)
							&& (entry.getCell(2) == null
									|| StringUtils.isNotBlank(entry.getCell(2)
											.toString()) || (int) entry
									.getCell(2).getNumericCellValue() == 0)
							&& (entry.getCell(3) == null
									|| StringUtils.isNotBlank(entry.getCell(3)
											.toString()) || (int) entry
									.getCell(3).getNumericCellValue() == 0)) {
						errorMessage
								.append(" All the inventory values can not be blank ");
						validaterow = false;
					} else {
						currentInventory = entry.getCell(2) != null ? (int) entry
								.getCell(2).getNumericCellValue() : 0;
						quantoAdd = entry.getCell(3) != null ? (int) entry
								.getCell(3).getNumericCellValue() : 0;
						quantoSub = entry.getCell(4) != null ? (int) entry
								.getCell(4).getNumericCellValue() : 0;
					}
					log.debug("Sheet values :1 :" + entry.getCell(1) + " 2 :"
							+ entry.getCell(2) + " 3 :" + entry.getCell(3));
					// Pre save to generate id for use in hierarchy
				} else {
					errorMessage.append(" SKU can not be blank ");
					validaterow = false;
				}
				if (validaterow) {
					productService.updateInventory(SkUCode, currentInventory,
							quantoAdd, quantoSub, true, sellerId, new Date());
				} else {
					returnInventoryMap.put(errorMessage.toString(), SkUCode);
				}
			}
			Set<String> errorSet = returnInventoryMap.keySet();
			downloadUploadReportXLS(offices, "InventoryReport", uploadFileName,
					5, errorSet, path, sellerId, uploadReport);
		} catch (Exception e) {
			log.debug("Inside save c" + "ontents exception :"
					+ e.getLocalizedMessage());
			e.printStackTrace();
			log.error("Failed! by SellerId : " + sellerId, e);
			addErrorUploadReport("InventoryReport", sellerId, uploadReport);
			throw new MultipartException("Constraints Violated");
		}
		log.info("$$$ saveInventoryDetails ends : SaveContents $$$");
		return returnInventoryMap;
	}

	public Map<String, Order> saveOrderReturnDetails(MultipartFile file,
			int sellerId, String path, UploadReport uploadReport)
			throws IOException {
		log.info("$$$ saveOrderReturnDetails starts : SaveContents $$$");
		HSSFRow entry;
		Integer noOfEntries = 1;
		Order order = new Order();
		StringBuffer errorMessage = null;
		boolean validaterow = true;
		String uploadFileName = "";
		OrderRTOorReturn orderReturn = null;
		Map<String, Order> returnlist = new LinkedHashMap<>();
		List<Order> orderlist = null;

		Map<String, String> returnMap = new HashMap<String, String>();
		returnMap.put("goodInventory", "goodInventory");
		returnMap.put("badInventory", "badInventory");
		returnMap.put("returnCharges", "returnCharges");
		returnMap.put("RTOCharges", "RTOCharges");
		returnMap.put("replacementCharges", "replacementCharges");
		returnMap.put("partialDeliveryCharges", "partialDeliveryCharges");
		returnMap.put("cancellationCharges", "cancellationCharges");
		returnMap.put("buyerReturn", "buyerReturn");
		returnMap.put("sellerFault", "sellerFault");
		returnMap.put("beforeRTD", "beforeRTD");
		returnMap.put("afterRTD", "afterRTD");

		try {
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = offices.getSheetAt(0);
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			uploadFileName = file.getOriginalFilename().substring(0,
					file.getOriginalFilename().lastIndexOf("."))
					+ new Date().getTime();
			log.info(noOfEntries.toString());
			log.debug("After getting no of rows" + noOfEntries);
			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				errorMessage = new StringBuffer("Row :" + (rowIndex - 2) + ":");
				orderReturn = new OrderRTOorReturn();
				List<Order> ord = null;
				String criteria = "";
				String column = null;
				String id = null;
				String channelID = null;
				Partner partner = null;
				ProductConfig productConfig = null;
				List<ProductConfig> productConfigs = null;
				try {
					if (entry.getCell(12) != null
							&& StringUtils.isNotBlank(entry.getCell(12)
									.toString())) {
						partner = partnerService.getPartner(entry.getCell(12)
								.toString(), sellerId);
					} else {
						validaterow = false;
						errorMessage.append("Column O2R Channel is null");
					}

					if (entry.getCell(0) != null
							&& StringUtils.isNotBlank(entry.getCell(0)
									.toString())) {

						criteria = entry.getCell(0).toString();
						if (criteria.equalsIgnoreCase("channelOrderId")) {
							column = "channelOrderID";
						} else if (criteria.equalsIgnoreCase("AWB")) {
							column = "awbNum";
						} else if (criteria.equalsIgnoreCase("PIreference")) {
							column = "PIreferenceNo";
						} else if (criteria.equalsIgnoreCase("suborderId")) {
							column = "subOrderID";
						} else if (criteria.equalsIgnoreCase("invoiceId")) {
							column = "invoiceID";
						}
						if (entry.getCell(1) != null
								&& StringUtils.isNotBlank(entry.getCell(1)
										.toString()) && column != null) {
							entry.getCell(1).setCellType(
									HSSFCell.CELL_TYPE_STRING);
							log.info("Channel Order ID for Return : "
									+ entry.getCell(1).toString());
							if (column.equals("channelOrderID")) {
								if (partner != null) {
									if (entry.getCell(2) != null
											&& StringUtils.isNotBlank(entry
													.getCell(2).toString())) {
										entry.getCell(2).setCellType(
												HSSFCell.CELL_TYPE_STRING);
										productConfigs = productService
												.getProductConfig(entry
														.getCell(2).toString()
														.trim().toUpperCase(),
														partner.getPcName(),
														sellerId);
										if (productConfigs != null) {

											if (productConfigs.size() == 1) {
												productConfig = (ProductConfig) productConfigs
														.get(0);
											} else {
												ProductConfig pc = null;
												Set<String> parent = new HashSet<String>();
												for (Object PCo : productConfigs) {
													pc = (ProductConfig) PCo;
													parent.add(pc
															.getProductSkuCode());
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
												if (productConfig
														.getVendorSkuRef() != null) {
													channelID = entry
															.getCell(1)
															.toString()
															+ GlobalConstant.orderUniqueSymbol
															+ productConfig
																	.getVendorSkuRef();
													System.out
															.println(" channelID withc vendor sku "
																	+ channelID);
												} else {
													channelID = entry
															.getCell(1)
															.toString()
															+ GlobalConstant.orderUniqueSymbol
															+ entry.getCell(2)
																	.toString();
												}
											} catch (Exception e) {

											}
										} else {
											validaterow = false;
											errorMessage
													.append("No product with this Channel & Sku");
										}

									} else {
										channelID = entry.getCell(1).toString();
									}
									if (channelID != null) {
										System.out
												.println(" not null searching order channelID"
														+ channelID);
										ord = orderService.searchAsIsOrder(
												column, channelID, sellerId);
									}
								} else {
									validaterow = false;
									errorMessage.append("Invalid Partner Name");
								}
							} else {
								orderlist = orderService.findOrders(column,
										entry.getCell(1).toString(), sellerId,
										false, false);
							}
							if (ord != null) {
								if (ord.size() == 1) {
									if (ord.get(0).getOrderReturnOrRTO()
											.getReturnDate() == null) {
										order.setChannelOrderID(ord.get(0)
												.getChannelOrderID());
										id = order.getChannelOrderID();
									} else {
										validaterow = false;
										errorMessage
												.append("Return Already Recieved. ");
									}
								} else {
									errorMessage
											.append("Multiple Orders With Channel Order ID.");
									validaterow = false;
								}
							} else if (orderlist != null
									&& orderlist.size() != 0) {
								if (orderlist.size() == 1) {
									if (orderlist.get(0).getOrderReturnOrRTO()
											.getReturnDate() == null) {
										order.setChannelOrderID(orderlist
												.get(0).getChannelOrderID());
										id = order.getChannelOrderID();
									} else {
										validaterow = false;
										errorMessage
												.append("Return accepted With this "
														+ criteria);
									}
								} else {
									validaterow = false;
									errorMessage
											.append("Many Orders With this "
													+ criteria);
								}

							} else {
								validaterow = false;
								errorMessage.append("Order doesnt exist.");
							}
						} else {
							validaterow = false;
							errorMessage.append("Invalid Criteria or Value !");
						}
					}
					if (id != null) {
						if (entry.getCell(3) != null
								&& entry.getCell(3).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							orderReturn.setReturnOrRTOId(entry.getCell(3)
									.toString());
						} else {
							validaterow = false;
							errorMessage.append("Return Id is null");
						}
						if (entry.getCell(4) != null
								&& StringUtils.isNotBlank(entry.getCell(4)
										.toString())) {
							orderReturn.setReturnOrRTOreason(entry.getCell(4)
									.toString());
						}
						if (entry.getCell(5) != null
								&& entry.getCell(5).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							try {
								if (HSSFDateUtil.isCellDateFormatted(entry
										.getCell(5))) {
									orderReturn.setReturnDate(entry.getCell(5)
											.getDateCellValue());
								} else {
									errorMessage
											.append(" Return Date format is wrong ,enter mm/dd/yyyy,");
									validaterow = false;
								}
							} catch (Exception e) {
								log.error("Failed! by SellerId : " + sellerId,
										e);
								errorMessage
										.append(" Return Date format is wrong ,enter mm/dd/yyyy,");
								validaterow = false;
							}

						} else {
							errorMessage.append(" Return Date is null;");
							validaterow = false;
						}
						if (entry.getCell(6) != null
								&& entry.getCell(6).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							try {
								if ((int) Float.parseFloat(entry.getCell(6)
										.toString()) != 0) {
									orderReturn.setReturnorrtoQty((int) Float
											.parseFloat(entry.getCell(6)
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

						if (entry.getCell(7) != null
								&& StringUtils.isNotBlank(entry.getCell(7)
										.toString())
								&& returnMap.containsKey(entry.getCell(7)
										.toString())) {

							orderReturn.setType(entry.getCell(7).toString());
						} else {
							errorMessage.append("Invalid order Return Type");
							validaterow = false;
						}
						if (entry.getCell(8) != null
								&& StringUtils.isNotBlank(entry.getCell(8)
										.toString())
								&& returnMap.containsKey(entry.getCell(8)
										.toString())) {
							orderReturn.setReturnCategory(entry.getCell(8)
									.toString());
						} else {
							errorMessage
									.append("Invalid order Return Fault Type");
							validaterow = false;
						}
						if (entry.getCell(9) != null
								&& StringUtils.isNotBlank(entry.getCell(9)
										.toString())
								&& returnMap.containsKey(entry.getCell(9)
										.toString())) {
							orderReturn.setCancelType(entry.getCell(9)
									.toString());
						}
						if (entry.getCell(10) != null
								&& StringUtils.isNotBlank(entry.getCell(10)
										.toString())
								&& returnMap.containsKey(entry.getCell(10)
										.toString())) {
							orderReturn.setInventoryType(entry.getCell(10)
									.toString());
						}
						if (entry.getCell(11) != null
								&& StringUtils.isNotBlank(entry.getCell(11)
										.toString())) {
							orderReturn.setBadReturnQty((int) Float
									.parseFloat(entry.getCell(11).toString()));
						}

					} /*
					 * else { validaterow = false;
					 * errorMessage.append("Value of " + criteria +
					 * " is Invalid for Return.. "); }
					 */
					if (validaterow) {
						orderReturn.setReturnFileName(uploadFileName);
						orderService.addReturnOrder(order.getChannelOrderID(),
								orderReturn, sellerId);

					} else {
						order.setOrderReturnOrRTO(orderReturn);
						returnlist.put(errorMessage.toString(), order);
					}
				} catch (Exception e) {
					log.error("Failed! by SellerId : " + sellerId, e);
					errorMessage.append("Invalid input");
					order.setOrderReturnOrRTO(orderReturn);
					returnlist.put(errorMessage.toString(), order);
				}
			}
			Set<String> errorSet = returnlist.keySet();
			downloadUploadReportXLS(offices, "MP_Return_Upload",
					uploadFileName, 13, errorSet, path, sellerId, uploadReport);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by SellerId : " + sellerId, e);
			addErrorUploadReport("MP_Return_Upload", sellerId, uploadReport);
			throw new MultipartException("Constraints Violated");
		}
		log.info("$$$ saveOrderReturnDetails ends : SaveContents $$$");
		return returnlist;
	}

	public Map<String, DebitNoteBean> saveDebitNoteDetails(MultipartFile file,
			int sellerId, String path, UploadReport uploadReport)
			throws IOException {
		log.info("$$$ saveDebitNoteDetails starts : SaveContents $$$");
		HSSFRow entry;
		Integer noOfEntries = 1;
		StringBuffer errorMessage;
		boolean validaterow = true;
		Map<String, DebitNoteBean> returnlist = new LinkedHashMap<>();
		try {
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = offices.getSheetAt(0);
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			log.info(noOfEntries.toString());
			log.debug("After getting no of rows" + noOfEntries);
			DebitNoteBean dnBean = null;
			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				validaterow = true;
				entry = worksheet.getRow(rowIndex);
				errorMessage = new StringBuffer("Row :" + (rowIndex - 2) + ":");
				log.debug(entry.getCell(1));
				log.debug(entry.getCell(2));
				log.debug(entry.getCell(3));
				log.debug(entry.getCell(4));
				if (entry.getCell(0) != null
						&& StringUtils.isNotBlank(entry.getCell(0).toString())) {
					dnBean = new DebitNoteBean();
					dnBean.setPOOrderId(entry.getCell(0).toString());
					if (entry.getCell(1) != null
							&& StringUtils.isNotBlank(entry.getCell(1)
									.toString())) {
						dnBean.setGatePassId(entry.getCell(1).toString());
					} else {
						errorMessage.append(" Gate Pass Id is null");
						validaterow = false;
					}
					if (entry.getCell(2) != null
							&& StringUtils.isNotBlank(entry.getCell(2)
									.toString())) {
						dnBean.setSKU(entry.getCell(2).toString());
					} else {
						errorMessage.append(" SKU is null");
						System.out.println(" *** +" + errorMessage);
						validaterow = false;
					}
					if (entry.getCell(3) != null
							&& StringUtils.isNotBlank(entry.getCell(3)
									.toString())) {
						dnBean.setPcName(entry.getCell(3).toString());
					} else {
						errorMessage.append(" Partner is null");
						System.out.println(" *** +" + errorMessage);
						validaterow = false;
					}
					if (entry.getCell(4) != null
							&& StringUtils.isNotBlank(entry.getCell(4)
									.toString())) {
						dnBean.setInvoiceId(entry.getCell(4).toString());
					} else {
						errorMessage.append(" Invoice is null");
						System.out.println(" *** +" + errorMessage);
						validaterow = false;
					}
					if (entry.getCell(5) != null
							&& StringUtils.isNotBlank(entry.getCell(5)
									.toString())) {
						dnBean.setAmount(Double.parseDouble(entry.getCell(5)
								.toString()));
					} else {
						errorMessage.append(" Amount is null");
						validaterow = false;
					}
					if (entry.getCell(6) != null
							&& StringUtils.isNotBlank(entry.getCell(6)
									.toString())) {
						dnBean.setQuantity((int) Float.parseFloat(entry
								.getCell(6).toString()));
					} else {
						errorMessage.append(" Quantity is null");
						validaterow = false;
					}
					if (entry.getCell(7) != null
							&& StringUtils.isNotBlank(entry.getCell(7)
									.toString())) {
						dnBean.setReturnDate(new Date(entry.getCell(7)
								.toString()));
					} else {
						errorMessage.append(" Return date is null");
						validaterow = false;
					}
					if (entry.getCell(8) != null
							&& StringUtils.isNotBlank(entry.getCell(8)
									.toString())) {
						dnBean.setReturnReason(entry.getCell(8).toString());
					}
					log.debug(" validaterow : " + validaterow
							+ " errorMessage : " + errorMessage);
					if (validaterow) {
						log.debug(" Saving dnbBean : " + dnBean.getInvoiceId());
						orderService.addDebitNote(dnBean, sellerId);
					} else {
						log.debug("false validate row :" + errorMessage);
						returnlist.put(errorMessage.toString(), dnBean);
					}
				}
			}
			Set<String> errorSet = returnlist.keySet();
			downloadUploadReportXLS(offices, "DebitNoteSheet", null, 9,
					errorSet, path, sellerId, uploadReport);
		} catch (Exception e) {
			log.error("Failed! by SellerId : " + sellerId, e);
			e.printStackTrace();
			addErrorUploadReport("DebitNoteSheet", sellerId, uploadReport);
			throw new MultipartException("Constraints Violated");
		}
		log.info("$$$ saveDebitNoteDetails starts : SaveContents $$$");
		return returnlist;
	}

	public Map<String, PoPaymentBean> savePoPaymentDetails(MultipartFile file,
			int sellerId, String path, UploadReport uploadReport)
			throws IOException {
		log.info("$$$ savePoPaymentDetails starts : SaveContents $$$");
		PaymentUpload paymentUpload = new PaymentUpload();
		Order poOrder = null;
		HSSFRow entry;
		Integer noOfEntries = 1;

		StringBuffer errorMessage = null;
		boolean validaterow = true;
		PoPaymentBean popabean = null;
		Map<String, PoPaymentBean> returnMap = new LinkedHashMap<>();
		double totalpositive = 0;
		double totalnegative = 0;
		boolean generatePaymentUpload = false;
		boolean disputedGP = false;
		String uploadFileName = "";
		try {

			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = offices.getSheetAt(0);
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			uploadFileName = file.getOriginalFilename().substring(0,
					file.getOriginalFilename().lastIndexOf("."))
					+ new Date().getTime();
			log.info(noOfEntries.toString());
			log.debug("After getting no of rows" + noOfEntries);
			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				entry = worksheet.getRow(rowIndex);
				popabean = new PoPaymentBean();
				validaterow = true;
				errorMessage = new StringBuffer("Row :" + (rowIndex - 2) + ":");
				if (entry.getCell(0) != null
						&& StringUtils.isNotBlank(entry.getCell(0).toString())) {
					popabean.setPcName(entry.getCell(0).toString());
				} else {
					errorMessage.append(" Channel is null");
					validaterow = false;
				}

				if (entry.getCell(1) != null
						&& StringUtils.isNotBlank(entry.getCell(1).toString())) {
					popabean.setInvoiceID(entry.getCell(1).toString());
				} else {
					errorMessage.append(" Invoice ID is null");
					validaterow = false;
				}

				if (validaterow) {
					Order order = orderService.findConsolidatedPO("invoiceID",
							popabean.getInvoiceID(), sellerId);
					if (order == null) {
						order = orderService.findConsolidatedPO(
								"channelOrderID", popabean.getInvoiceID(),
								sellerId);
						if (order == null) {
							log.info(" Disputed Gatepass");
							disputedGP = true;
							popabean.setGatePassId(popabean.getInvoiceID());
							// errorMessage.append(" PO or GatePass not found");
							// validaterow = false;
						} else {
							popabean.setGatePassId(order.getChannelOrderID());
						}
					} else {
						popabean.setPoOrderId(order.getChannelOrderID());
					}
				}

				if (entry.getCell(2) != null
						&& StringUtils.isNotBlank(entry.getCell(2).toString())) {

					try {
						if (HSSFDateUtil.isCellDateFormatted(entry.getCell(2))) {
							popabean.setPaymentDate(entry.getCell(2)
									.getDateCellValue());
						} else {
							errorMessage
									.append(" Payment Date format is wrong ");
							validaterow = false;
						}
					} catch (Exception e) {
						errorMessage.append(" Payment Date format is wrong ");
						validaterow = false;
					}
				} else {
					errorMessage.append(" Payment Date is null ");
					validaterow = false;
				}

				if (entry.getCell(3) != null
						&& StringUtils.isNotBlank(entry.getCell(3).toString())) {
					try {
						popabean.setPositiveAmount(Double.parseDouble(entry
								.getCell(3).toString()));
						totalpositive = totalpositive
								+ Double.parseDouble(entry.getCell(3)
										.toString());
						log.debug(" ******toatal psitive :" + totalpositive);
					} catch (NumberFormatException e) {
						log.error("Failed! by SellerId : " + sellerId, e);
						errorMessage
								.append(" Positive Amount should be a number ");
						validaterow = false;
					}
				} /*
				 * else { if (popabean.getPoOrderId() != null) {
				 * errorMessage.append(" Positive Amount is null "); validaterow
				 * = false; } }
				 */

				if (entry.getCell(4) != null
						&& StringUtils.isNotBlank(entry.getCell(4).toString())) {
					try {
						popabean.setNegativeAmount(Double.parseDouble(entry
								.getCell(4).toString()));
						totalnegative = totalnegative
								+ Math.abs(Double.parseDouble(entry.getCell(4)
										.toString()));
					} catch (NumberFormatException e) {
						log.error("Failed! by SellerId : " + sellerId, e);
						errorMessage
								.append(" Negative Amount should be a number ");
						validaterow = false;
					}
				} /*
				 * else { if (popabean.getGatePassId() != null) {
				 * errorMessage.append(" Negative Amount is null "); validaterow
				 * = false; } }
				 */

				if (entry.getCell(5) != null
						&& StringUtils.isNotBlank(entry.getCell(5).toString())) {
					popabean.setSellerNote(entry.getCell(5).toString());
				}

				if (validaterow) {
					poOrder = orderService.addPOPayment(popabean, sellerId);
				} else {
					returnMap.put(errorMessage.toString(), popabean);
				}

				if (poOrder != null) {
					// poOrder.getPaymentUpload().add(paymentUpload);
					// paymentUpload.getOrders().add(poOrder);
					generatePaymentUpload = true;
				}
			}

			if (generatePaymentUpload) {
				log.debug(" Total Positive Amount : " + totalpositive);
				log.debug(" Total Negative Amount : " + totalnegative);
				paymentUpload.setTotalpositivevalue(totalpositive);
				paymentUpload.setTotalnegativevalue(totalnegative);
				paymentUpload.setNetRecievedAmount(totalpositive
						- totalnegative);
				paymentUpload.setUploadDesc("PAYU" + sellerId + ""
						+ new Date().getTime());
				paymentUpload.setUploadStatus("Success");
				paymentUploadService.addPaymentUpload(paymentUpload, sellerId);
			}

			Set<String> errorSet = returnMap.keySet();
			downloadUploadReportXLS(offices, "Po_Payment_Upload",
					uploadFileName, 6, errorSet, path, sellerId, uploadReport);
		} catch (Exception e) {
			log.error("Failed! by SellerId : " + sellerId, e);
			e.printStackTrace();
			addErrorUploadReport("Po_Payment_Upload", sellerId, uploadReport);
			throw new MultipartException("Constraints Violated");
		}
		log.info("$$$ savePoPaymentDetails ends : SaveContents $$$");
		return returnMap;
	}

	public Map<String, ExpenseBean> saveExpenseDetails(MultipartFile file,
			int sellerId, String path, UploadReport uploadReport)
			throws IOException {
		log.info("$$$ saveExpenseDetails starts : SaveContents $$$");
		HSSFRow entry;
		Integer noOfEntries = 1;
		StringBuffer errorMessage = null;
		boolean validaterow = true;
		ExpenseBean expensebean = null;
		Map<String, ExpenseBean> returnlist = new LinkedHashMap<>();
		String uploadFileName = "";
		try {
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = offices.getSheetAt(0);
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			uploadFileName = file.getOriginalFilename().substring(0,
					file.getOriginalFilename().lastIndexOf("."))
					+ new Date().getTime();
			log.info(noOfEntries.toString());
			log.debug("After getting no of rows" + noOfEntries);
			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				log.debug(entry.getCell(1));
				log.debug(entry.getCell(2));
				errorMessage = new StringBuffer("Row :" + (rowIndex - 2) + ":");
				try {
					if (entry.getCell(0) != null
							&& StringUtils.isNotBlank(entry.getCell(0)
									.toString())) {
						expensebean = new ExpenseBean();
						expensebean.setExpenseName(entry.getCell(0).toString());
						if (entry.getCell(1) != null
								&& StringUtils.isNotBlank(entry.getCell(1)
										.toString())) {
							expensebean.setExpenseDescription(entry.getCell(1)
									.toString());
						}
						if (entry.getCell(2) != null
								&& StringUtils.isNotBlank(entry.getCell(2)
										.toString())) {
							List<ExpenseCategory> expCateList = expenseService
									.listExpenseCategories(sellerId);
							boolean temp = false;
							for (ExpenseCategory cat : expCateList) {
								if (cat.getExpcatName().equalsIgnoreCase(
										entry.getCell(2).toString())) {
									temp = true;
									break;
								}
							}
							if (temp) {
								expensebean.setExpenseCatName(entry.getCell(2)
										.toString());
							} else {
								errorMessage
										.append(" Expense Category does not exist ");
								validaterow = false;
								expensebean.setExpenseCatName(entry.getCell(2)
										.toString());
							}
						} else {
							errorMessage.append(" Expense Category is null ");
							validaterow = false;
						}
						if (entry.getCell(3) != null
								&& StringUtils.isNotBlank(entry.getCell(3)
										.toString())) {
							try {
								expensebean.setAmount(Double.parseDouble(entry
										.getCell(3).toString()));
							} catch (NumberFormatException e) {
								log.error("Failed! by SellerId : " + sellerId,
										e);
								errorMessage
										.append(" Amount should be numeric ");
								validaterow = false;
							}
						} else {
							errorMessage.append(" Amount is null ");
							validaterow = false;
						}
						if (entry.getCell(4) != null
								&& StringUtils.isNotBlank(entry.getCell(4)
										.toString())) {
							expensebean.setExpenditureByperson(entry.getCell(4)
									.toString());
						}
						if (entry.getCell(5) != null
								&& StringUtils.isNotBlank(entry.getCell(5)
										.toString())) {
							expensebean.setPaidTo(entry.getCell(5).toString());
						} else {
							errorMessage
									.append("'PaidTo' field should be filled with correct data");
							validaterow = false;
						}
						if (entry.getCell(6) != null
								&& entry.getCell(6).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							try {
								if (HSSFDateUtil.isCellDateFormatted(entry
										.getCell(6))) {
									expensebean.setExpenseDate(entry.getCell(6)
											.getDateCellValue());
								} else {
									errorMessage
											.append(" Expense Date formate is wrong ,enter mm/dd/yyyy,");
									validaterow = false;
								}
							} catch (Exception e) {
								errorMessage
										.append(" Expense Date formate is wrong ,enter mm/dd/yyyy,");
								validaterow = false;
							}
						} else {
							errorMessage.append(" Expense Date is null;");
							validaterow = false;
						}

					}
					log.debug("Validaterow : " + validaterow
							+ "  error message: " + errorMessage);
					if (validaterow)
						expenseService
								.addExpense(ConverterClass
										.prepareExpenseModel(expensebean),
										sellerId);
					else {
						returnlist.put(errorMessage.toString(), expensebean);
					}
				} catch (Exception e) {
					log.error("Failed! by SellerId : " + sellerId, e);
					errorMessage.append(" Invalid Input!");
					returnlist.put(errorMessage.toString(), expensebean);
				}
			}
			Set<String> errorSet = returnlist.keySet();
			downloadUploadReportXLS(offices, "Expense_Upload", uploadFileName,
					7, errorSet, path, sellerId, uploadReport);
		} catch (Exception e) {
			log.error("Failed! by SellerId : " + sellerId, e);
			e.printStackTrace();
			addErrorUploadReport("Expense_Upload", sellerId, uploadReport);
			throw new MultipartException("Constraints Violated");
		}
		log.info("$$$ saveExpenseDetails ends : SaveContents $$$");
		return returnlist;
	}

	public Map<String, Events> saveEventSKUDetails(MultipartFile file,
			int sellerId, String path, UploadReport uploadReport)
			throws IOException {
		log.info("$$$ saveEventSKUDetails starts : SaveContents $$$");
		HSSFRow entry;
		Integer noOfEntries = 1;
		StringBuffer errorMessage = null;
		boolean validaterow = true;
		Map<String, Events> returnlist = new LinkedHashMap<>();
		Events event = null;
		Map<String, Events> eventsMap = new HashMap<String, Events>();
		String uploadFileName = "";
		try {
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = offices.getSheetAt(0);
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			uploadFileName = file.getOriginalFilename().substring(0,
					file.getOriginalFilename().lastIndexOf("."))
					+ new Date().getTime();
			log.info(noOfEntries.toString());
			log.debug("After getting no of rows" + noOfEntries);
			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				log.debug(entry.getCell(0));
				log.debug(entry.getCell(1));
				errorMessage = new StringBuffer("Row :" + (rowIndex - 2) + ":");
				try {
					if (entry.getCell(0) != null
							&& StringUtils.isNotBlank(entry.getCell(0)
									.toString())) {
						if (entry.getCell(1) != null
								&& StringUtils.isNotBlank(entry.getCell(1)
										.toString())) {
							if (eventsMap.containsKey(entry.getCell(0)
									.toString())) {
								event = eventsMap.get(entry.getCell(0)
										.toString());
							} else {
								event = eventsService.getEvent(entry.getCell(0)
										.toString(), sellerId);
							}
							if (event != null) {
								if (event.getSkuList() == null) {
									event.setSkuList(entry.getCell(1)
											.toString().trim().toUpperCase()
											+ ",");
								} else if (!event.getSkuList().contains(
										entry.getCell(1).toString().trim()
												.toUpperCase())) {
									StringBuffer skuBuffer = new StringBuffer(
											event.getSkuList());
									event.setSkuList(skuBuffer.append(
											entry.getCell(1).toString().trim()
													.toUpperCase()).toString()
											+ ",");
								} else {
									errorMessage.append(" Duplicate SKU ");
									validaterow = false;
								}
							} else {
								errorMessage.append(" Invalid Event Name ");
								validaterow = false;
							}
						} else {
							errorMessage.append(" Invalid SKU ");
							validaterow = false;
						}
					} else {
						errorMessage.append(" Invalid Event Name ");
						validaterow = false;
					}
					log.debug("Validaterow : " + validaterow
							+ "  error message: " + errorMessage);
					if (validaterow)
						eventsMap.put(entry.getCell(0).toString(), event);
					else {
						returnlist.put(errorMessage.toString(), event);
					}
				} catch (Exception e) {
					log.error("Failed! by SellerId : " + sellerId, e);
					errorMessage.append(" Invalid Input!");
					returnlist.put(errorMessage.toString(), event);
				}
			}
			if (eventsMap.size() != 0) {
				for (Entry<String, Events> eventEntry : eventsMap.entrySet()) {
					eventsService.addEvent(eventEntry.getValue(), sellerId);
				}
			}
			Set<String> errorSet = returnlist.keySet();
			saveMappedFiles.uploadResultXLS(offices, "Event_SKU_Upload",
					uploadFileName, errorSet, path, sellerId, uploadReport);
		} catch (Exception e) {
			log.error("Failed! by SellerId : " + sellerId, e);
			e.printStackTrace();
			addErrorUploadReport("Event_SKU_Upload", sellerId, uploadReport);
			throw new MultipartException("Constraints Violated");
		}
		log.info("$$$ saveEventSKUDetails ends : SaveContents $$$");
		return returnlist;
	}

	public boolean validateRowForNull(HSSFRow entry, int cellcount) {
		log.info("$$$ validateRowForNull starts : SaveContents $$$");
		for (int i = 0; i < cellcount; i++) {
			if (entry.getCell(i) == null
					|| StringUtils.isBlank(entry.getCell(i).toString())) {
				return false;
			}
		}
		log.info("$$$ validateRowForNull ends : SaveContents $$$");
		return true;
	}

	public void downloadUploadReportXLS(HSSFWorkbook workbook,
			String worksheetName, String fileName, int colNumber,
			Set<String> errorSet, String path, int sellerId,
			UploadReport uploadReport) throws ClassNotFoundException,
			CustomException {

		log.info("$$$ downloadUploadReportXLS starts : SaveContents $$$");
		int noOfRows = 1;
		HSSFSheet worksheet = workbook.getSheetAt(0);
		while (worksheet.getRow(noOfRows) != null) {
			noOfRows++;
		}
		String errorMessage;
		boolean isError = false;

		worksheet.setColumnWidth(colNumber, 15000);
		int startRowIndex = 0;
		int startColIndex = 0;
		Layouter.buildReport(worksheet, startRowIndex, startColIndex,
				worksheetName);

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
		HSSFRow rowHeader = worksheet.getRow((short) startRowIndex + 2);
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

				System.out
						.println(" The column number ========================="
								+ colNumber);
				if (errorMessage.length() > 5) {
					isError = true;
					row = worksheet.getRow(errorRow + 2);
					if (row != null) {
						cell = row.createCell(colNumber);
						cell.setCellValue(errorMessage);
						cell.setCellStyle(errorCellStyle);
					}
				}
			}

			// constructs path of the directory to save uploaded file
			String uploadFilePath = path + File.separator + UPLOAD_DIR;
			log.debug("***** uploadFilePath path  : " + uploadFilePath);

			properties = PropertiesLoaderUtils.loadProperties(resource);
			uploadFilePath = properties.getProperty("uploadreport.path");

			log.debug("***** uploadFilePath path  : " + uploadFilePath);

			// creates the save directory if it does not exists
			File fileSaveDir = new File(uploadFilePath);
			if (!fileSaveDir.exists()) {
				log.debug(" Directory doesnnt exist");
				fileSaveDir.mkdirs();
			}
			String filePath = "";
			if (fileName != null && !fileName.equals("")) {
				filePath = uploadFilePath + File.separator + fileName + ".xls";
				uploadReport.setFileName(fileName);
			} else {
				filePath = uploadFilePath + File.separator + worksheetName
						+ "UploadStatus" + new Date().getTime() + ".xls";
				uploadReport.setFileName(worksheetName);
			}
			FileOutputStream out = new FileOutputStream(new File(filePath));
			workbook.write(out);
			out.close();
			uploadReport.setFileType(worksheetName);
			uploadReport.setFilePath(filePath);
			uploadReport.setDescription("Imported");
			uploadReport.setSeller(sellerService.getSeller(sellerId));
			uploadReport.setNoOfErrors(errorSet.size());
			uploadReport.setNoOfSuccess((noOfRows - errorSet.size()) - 3);

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

	public void addErrorUploadReport(String worksheetName, int sellerId,
			UploadReport uploadReport) {

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
			log.error("Failed! by SellerId : " + sellerId, e);
		}
	}

	public Map<String, GatePass> saveGatePassDetails(MultipartFile file,
			int sellerId, String path, UploadReport uploadReport)
			throws IOException {

		log.info("$$$ saveGatePassDetails starts : SaveContents $$$");
		HSSFRow entry;
		Integer noOfEntries = 1;

		GatePass gatepass;
		StringBuffer errorMessage = null;
		boolean validaterow = true;
		Map<String, GatePass> returnlist = new LinkedHashMap<>();

		String gpId = null;
		List<GatePass> gatepasslist = new ArrayList<GatePass>();
		ProductConfig productConfig = null;
		List<ProductConfig> productConfigs = null;
		String uploadFileName = "";
		try {

			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = offices.getSheetAt(0);
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			uploadFileName = file.getOriginalFilename().substring(0,
					file.getOriginalFilename().lastIndexOf("."))
					+ new Date().getTime();
			log.info(noOfEntries.toString());
			log.debug("After getting no of rows" + noOfEntries);
			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				errorMessage = new StringBuffer("Row :" + (rowIndex - 2) + ":");
				// orderReturn = new OrderRTOorReturn();
				gatepass = new GatePass();
				try {
					if (entry.getCell(0) != null
							&& StringUtils.isNotBlank(entry.getCell(0)
									.toString())) {
						gatepass.setGatepassId(entry.getCell(0).toString());
						if (gpId != null
								&& !gpId.equalsIgnoreCase(gatepass
										.getGatepassId())
								&& !gatepasslist.isEmpty()) {
							OrderRTOorReturn consolidatedReturn = orderService
									.generateConsolidatedReturn(gatepasslist,
											sellerId);
							orderService.updateGatePasses(gatepasslist,
									consolidatedReturn);
							gatepasslist.clear();
						}
						gpId = gatepass.getGatepassId();
					} else {
						errorMessage.append(" GatePass ID is null ");
						validaterow = false;
					}

					if (entry.getCell(1) != null
							&& StringUtils.isNotBlank(entry.getCell(1)
									.toString())) {
						gatepass.setPoID(entry.getCell(1).toString());
					}

					if (entry.getCell(2) != null
							&& StringUtils.isNotBlank(entry.getCell(2)
									.toString())) {
						gatepass.setInvoiceID(entry.getCell(2).toString());
					}

					if (entry.getCell(3) != null
							&& StringUtils.isNotBlank(entry.getCell(3)
									.toString())) {
						gatepass.setChannelSkuRef(entry.getCell(3).toString());
					} else {
						errorMessage.append(" Vendor SKU is null ");
						validaterow = false;
					}

					if (entry.getCell(4) != null
							&& StringUtils.isNotBlank(entry.getCell(4)
									.toString())) {
						gatepass.setQuantity((int) Float.parseFloat(entry
								.getCell(4).toString()));
					} else {
						errorMessage.append(" Quantity is null ");
						validaterow = false;
					}

					if (entry.getCell(5) != null
							&& StringUtils.isNotBlank(entry.getCell(5)
									.toString())) {
						gatepass.setReturnRate(Double.parseDouble(entry
								.getCell(5).toString()));
					} else {
						errorMessage.append(" Return Rate is null ");
						validaterow = false;
					}
					/*
					 * if (entry.getCell(6) != null &&
					 * StringUtils.isNotBlank(entry.getCell(6).toString())) {
					 * gatepass.setTaxPOAmt(Double.parseDouble(entry.getCell(6)
					 * .toString())); } else {
					 * errorMessage.append(" Tax Amount is null "); validaterow
					 * = false; }
					 * 
					 * if (entry.getCell(7) != null &&
					 * StringUtils.isNotBlank(entry.getCell(7).toString())) {
					 * gatepass
					 * .setReturnCharges(Double.parseDouble(entry.getCell(
					 * 7).toString())); } else {
					 * errorMessage.append(" Return Charges is null ");
					 * validaterow = false; }
					 */
					if (entry.getCell(6) != null
							&& StringUtils.isNotBlank(entry.getCell(6)
									.toString())) {
						gatepass.setTotalReturnCharges(entry.getCell(6)
								.getNumericCellValue());
					} else {
						errorMessage.append(" Total Return Charges is null ");
						validaterow = false;
					}

					/*
					 * if (entry.getCell(9) != null &&
					 * StringUtils.isNotBlank(entry.getCell(9).toString())) {
					 * gatepass.setSellerNotes(entry.getCell(9).toString()); }
					 * else { errorMessage.append(" Seller Note is null ");
					 * validaterow = false; }
					 */

					if (entry.getCell(7) != null
							&& StringUtils.isNotBlank(entry.getCell(7)
									.toString())) {
						gatepass.setPcName(entry.getCell(7).toString());
						productConfigs = productService.getProductConfig(
								gatepass.getChannelSkuRef(),
								gatepass.getPcName(), sellerId);
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

						} else {
							errorMessage.append(" Product SKU does not exist.");
							validaterow = false;
						}
					} else {
						errorMessage.append(" Channel is null ");
						validaterow = false;
					}

					if (entry.getCell(8) != null
							&& StringUtils.isNotBlank(entry.getCell(8)
									.toString())) {
						gatepass.setReturnReason(entry.getCell(8).toString());
					} else {
						errorMessage.append(" Return Reason is null ");
					}

					if (entry.getCell(9) != null
							&& StringUtils.isNotBlank(entry.getCell(9)
									.toString())) {

						try {
							if (HSSFDateUtil.isCellDateFormatted(entry
									.getCell(9))) {
								gatepass.setReturnDate(entry.getCell(9)
										.getDateCellValue());

							} else {
								errorMessage
										.append(" Return Date format is wrong ");
								validaterow = false;
							}
						} catch (Exception e) {
							errorMessage
									.append(" Return Date format is wrong ");
							validaterow = false;
						}
					} else {
						errorMessage.append(" Return Date is null ");
						validaterow = false;
					}

					if (validaterow) {
						gatepasslist.add(orderService.addGatePass(
								productConfig, gatepass, sellerId));
					} else {
						returnlist.put(errorMessage.toString(), gatepass);
					}
				} catch (Exception e) {
					log.error("Failed! by SellerId : " + sellerId, e);
					errorMessage.append(" Invalid input ");
					returnlist.put(errorMessage.toString(), gatepass);
				}
			}

			if (!gatepasslist.isEmpty()) {
				OrderRTOorReturn consolidatedReturn = orderService
						.generateConsolidatedReturn(gatepasslist, sellerId);
				orderService.updateGatePasses(gatepasslist, consolidatedReturn);
				gatepasslist.clear();
			}

			Set<String> errorSet = returnlist.keySet();
			downloadUploadReportXLS(offices, "PO_Gatepass_Uplooad",
					uploadFileName, 10, errorSet, path, sellerId, uploadReport);
		} catch (Exception e) {
			log.error("Failed! by SellerId : " + sellerId, e);
			e.printStackTrace();
			addErrorUploadReport("PO_Gatepass_Uplooad", sellerId, uploadReport);
			throw new MultipartException("Constraints Violated");
		}
		log.info("$$$ saveGatePassDetails ends : SaveContents $$$");
		return returnlist;
	}

	public boolean saveProdCatCommissionContents(MultipartFile file,
			int sellerId, String path, UploadReport uploadReport)
			throws IOException {
		log.info("$$$ saveProdCatCommissionContents starts : SaveContents $$$");
		boolean validaterow = true;
		StringBuffer errorMessage = null;
		String uploadFileName = "";
		try {
			Map<String, String> returnlist = new LinkedHashMap<>();
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = offices.getSheetAt(0);
			HSSFRow entry;
			Integer noOfEntries = 1;
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			uploadFileName = file.getOriginalFilename().substring(0,
					file.getOriginalFilename().lastIndexOf("."))
					+ new Date().getTime();
			log.info(noOfEntries.toString());
			log.debug("After getting no of rows" + noOfEntries);
			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				Partner partner = null;
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				errorMessage = new StringBuffer("Row :" + (rowIndex - 2) + ":");
				try {
					if (entry.getCell(0) != null
							&& entry.getCell(0).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						partner = partnerService.getPartner(entry.getCell(0)
								.toString(), sellerId);
						if (partner != null) {
							List<NRnReturnCharges> chargesList = partner
									.getNrnReturnConfig().getCharges();
							if (entry.getCell(1) != null
									&& entry.getCell(1).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
								Category prodcat = categoryService
										.getSubCategory(entry.getCell(1)
												.toString(), sellerId);
								NRnReturnCharges nrnReturncharge = null;
								if (prodcat != null) {
									for (NRnReturnCharges charge : chargesList) {
										if (charge.getChargeName()
												.equalsIgnoreCase(
														prodcat.getCatName())) {
											nrnReturncharge = charge;
											/*
											 * errorMessage .append(
											 * " Commission percent is already set for the category "
											 * ); validaterow = false;
											 */
										}
									}

									if (entry.getCell(2) != null
											&& entry.getCell(2).getCellType() != HSSFCell.CELL_TYPE_BLANK) {

										try {
											float commPercent = Float
													.valueOf(entry.getCell(2)
															.toString());

											if (nrnReturncharge == null) {
												nrnReturncharge = new NRnReturnCharges();
												nrnReturncharge
														.setChargeAmount(commPercent);
												nrnReturncharge
														.setChargeName(prodcat
																.getCatName());
												nrnReturncharge
														.setConfig(partner
																.getNrnReturnConfig());
												partner.getNrnReturnConfig()
														.getCharges()
														.add(nrnReturncharge);
											} else {
												nrnReturncharge
														.setChargeAmount(commPercent);
											}

										} catch (NumberFormatException e) {
											errorMessage
													.append(" Commission percent should be a number ");
											validaterow = false;
										}
									} else {
										errorMessage
												.append(" Commission percent is null ");
										validaterow = false;
									}
								} else {
									errorMessage
											.append(" Product Category does not exist ");
									validaterow = false;
								}
							} else {
								errorMessage
										.append(" Procduct Category is null ");
								validaterow = false;
							}
						} else {
							errorMessage.append(" Partner does not exist ");
							validaterow = false;
						}
					} else {
						errorMessage.append(" Partner Name is null ");
						validaterow = false;
					}
					if (validaterow) {
						partnerService.editPartner(partner, sellerId);
					} else {
						returnlist.put(errorMessage.toString(), "");
					}
				} catch (Exception e) {
					log.error("Failed! by SellerId : " + sellerId, e);
					if (partner != null) {
						errorMessage.append("Invalid Input! ");
						returnlist.put(errorMessage.toString(), "");
					}
				}
				log.debug("Sheet values :1 :" + entry.getCell(1) + " 2 :"
						+ entry.getCell(2) + " 3 :" + entry.getCell(3));
				// Pre save to generate id for use in hierarchy
			}
			Set<String> errorSet = returnlist.keySet();
			downloadUploadReportXLS(offices, "ProdCat_Comm_Mapping",
					uploadFileName, 3, errorSet, path, sellerId, uploadReport);
		} catch (Exception e) {
			log.error("Failed! by SellerId : " + sellerId, e);
			addErrorUploadReport("ProdCat_Comm_Mapping", sellerId, uploadReport);
			throw new MultipartException("Constraints Violated");
		}
		log.info("$$$ saveVendorSKUMappingContents ends : SaveContents $$$");
		return validaterow;
	}

	public boolean saveProdCatCommissionEventContents(MultipartFile file,
			int sellerId, String path, UploadReport uploadReport)
			throws IOException {
		log.info("$$$ saveProdCatCommissionEventContents starts : SaveContents $$$");
		boolean validaterow = true;
		StringBuffer errorMessage = null;
		String uploadFileName = "";
		try {
			Map<String, String> returnlist = new LinkedHashMap<>();
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = offices.getSheetAt(0);
			HSSFRow entry;
			Integer noOfEntries = 1;
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			uploadFileName = file.getOriginalFilename().substring(0,
					file.getOriginalFilename().lastIndexOf("."))
					+ new Date().getTime();
			log.info(noOfEntries.toString());
			log.debug("After getting no of rows" + noOfEntries);
			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				Events event = null;
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				errorMessage = new StringBuffer("Row :" + (rowIndex - 2) + ":");
				try {
					if (entry.getCell(0) != null
							&& entry.getCell(0).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						if (entry.getCell(3) != null
								&& entry.getCell(3).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							event = eventsService.getEvent(entry.getCell(3)
									.toString(), sellerId);
							System.out.println(event.getChannelName());
							if (event != null
									&& event.getChannelName().equalsIgnoreCase(
											entry.getCell(0).toString())) {

								if (event.getNrnReturnConfig()
										.getNrCalculatorEvent()
										.equalsIgnoreCase("variable")) {

									if (event.getNrnReturnConfig()
											.getCommissionType()
											.equalsIgnoreCase("categoryWise")) {

										List<NRnReturnCharges> chargesList = event
												.getNrnReturnConfig()
												.getCharges();
										if (entry.getCell(1) != null
												&& entry.getCell(1)
														.getCellType() != HSSFCell.CELL_TYPE_BLANK) {
											Category prodcat = categoryService
													.getSubCategory(entry
															.getCell(1)
															.toString(),
															sellerId);
											NRnReturnCharges nrnReturncharge = null;
											if (prodcat != null) {
												for (NRnReturnCharges charge : chargesList) {
													if (charge
															.getChargeName()
															.equalsIgnoreCase(
																	prodcat.getCatName())) {
														nrnReturncharge = charge;
													}
												}

												if (entry.getCell(2) != null
														&& entry.getCell(2)
																.getCellType() != HSSFCell.CELL_TYPE_BLANK) {

													try {
														float commPercent = Float
																.valueOf(entry
																		.getCell(
																				2)
																		.toString());

														if (nrnReturncharge == null) {
															nrnReturncharge = new NRnReturnCharges();
															nrnReturncharge
																	.setChargeAmount(commPercent);
															nrnReturncharge
																	.setChargeName(prodcat
																			.getCatName());
															nrnReturncharge
																	.setConfig(event
																			.getNrnReturnConfig());
															event.getNrnReturnConfig()
																	.getCharges()
																	.add(nrnReturncharge);
														} else {
															nrnReturncharge
																	.setChargeAmount(commPercent);
														}

													} catch (NumberFormatException e) {
														errorMessage
																.append(" Commission percent should be a number ");
														validaterow = false;
													}
												} else {
													errorMessage
															.append(" Commission percent is null ");
													validaterow = false;
												}
											} else {
												errorMessage
														.append(" Product Category does not exist ");
												validaterow = false;
											}
										} else {
											errorMessage
													.append(" Procduct Category is null ");
											validaterow = false;
										}

									} else {
										errorMessage
												.append("Commision Type is Fixed for This Event.");
										validaterow = false;
									}
								} else {
									errorMessage
											.append("NR Calculator Is Not 'Variable' For this Event.");
									validaterow = false;
								}
							} else {
								errorMessage
										.append("No Event By this Name On This Partner.");
								validaterow = false;
							}
						} else {
							errorMessage.append("Event Name Is Blank or Null.");
							validaterow = false;
						}
					} else {
						errorMessage.append(" Partner Name is null ");
						validaterow = false;
					}
					if (validaterow) {
						System.out.println(event.getEventId());
						eventsService.addEvent(event, sellerId);
					} else {
						returnlist.put(errorMessage.toString(), "");
					}
				} catch (Exception e) {
					log.error("Failed! by SellerId : " + sellerId, e);
					if (event != null) {
						errorMessage.append("Invalid Input! ");
						returnlist.put(errorMessage.toString(), "");
					}
				}
				log.debug("Sheet values :1 :" + entry.getCell(1) + " 2 :"
						+ entry.getCell(2) + " 3 :" + entry.getCell(3));
				// Pre save to generate id for use in hierarchy
			}
			Set<String> errorSet = returnlist.keySet();
			downloadUploadReportXLS(offices, "ProdCat_Comm_Event_Mapping",
					uploadFileName, 4, errorSet, path, sellerId, uploadReport);
		} catch (Exception e) {
			log.error("Failed! by SellerId : " + sellerId, e);
			addErrorUploadReport("ProdCat_Comm_Event_Mapping", sellerId,
					uploadReport);
			throw new MultipartException("Constraints Violated");
		}
		log.info("$$$ saveProdCatCommissionEventContents ends : SaveContents $$$");
		return validaterow;
	}

	private static String removeExtraQuote(String input) {
		String out = input.replaceAll("'", "");
		return out;
	}

	/*public Object saveSKUCatMapping(MultipartFile file, int sellerId,
			String path, UploadReport uploadReport) {
		log.info("$$$ saveSKUCatMapping starts : SaveContents $$$");
		boolean validaterow = true;
		StringBuffer errorMessage = null;
		String uploadFileName = "";
		try {
			Map<String, String> returnlist = new LinkedHashMap<>();
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = offices.getSheetAt(0);
			HSSFRow entry;
			Integer noOfEntries = 1;
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			uploadFileName = file.getOriginalFilename().substring(0,
					file.getOriginalFilename().lastIndexOf("."))
					+ new Date().getTime();
			log.info(noOfEntries.toString());
			log.debug("After getting no of rows" + noOfEntries);

			Map<String, Boolean> partnerMap = partnerService
					.getPartnerMap(sellerId);
			Seller seller = sellerService.getSeller(sellerId);
			List<Product> productList = productService.listProducts(sellerId);
			Map<String, Product> productMap = new HashMap<String, Product>();
			if (productList != null && !productList.isEmpty()) {
				for (Product tmpProduct : productList) {
					productMap.put(tmpProduct.getProductSkuCode().trim()
							.toLowerCase(), tmpProduct);
				}
			}
			Map<String, Product> saveProductMap = new HashMap<String, Product>();

			List<PartnerCategoryMap> partnerCatList = categoryService
					.listPartnerCategoryMap(sellerId, 0);
			Map<String, PartnerCategoryMap> partnerCategoryMap = new HashMap<String, PartnerCategoryMap>();
			if (partnerCatList != null && !partnerCatList.isEmpty()) {
				for (PartnerCategoryMap tmpPartnerCat : partnerCatList) {
					partnerCategoryMap.put(
							tmpPartnerCat.getPartnerCategoryRef(),
							tmpPartnerCat);
				}
			}

			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				Product product = null;
				PartnerCategoryMap partnerCatMap = null;
				String partnerName = null;

				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				errorMessage = new StringBuffer("Row :" + (rowIndex - 2) + ":");
				try {
					if (entry.getCell(1) != null
							&& entry.getCell(1).getCellType() != HSSFCell.CELL_TYPE_BLANK) {

						if (partnerMap.containsKey(entry.getCell(1).toString()
								.trim())) {

							partnerName = entry.getCell(1).toString().trim();

							if (entry.getCell(0) != null
									&& entry.getCell(0).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
								entry.getCell(0).setCellType(HSSFCell.CELL_TYPE_STRING);

								product = productMap.get(entry.getCell(0)
										.toString().trim().toLowerCase());

								if (product != null) {

									if (entry.getCell(2) != null
											&& entry.getCell(2).getCellType() != HSSFCell.CELL_TYPE_BLANK) {

										String catName = entry.getCell(2)
												.toString().trim();
										partnerCatMap = partnerCategoryMap
												.get(catName);

										if (partnerCatMap == null) {
											partnerCatMap = new PartnerCategoryMap();
											partnerCatMap
													.setPartnerName(partnerName);
											partnerCatMap
													.setPartnerCategoryRef(entry
															.getCell(2)
															.toString());
										}

										for (PartnerCategoryMap tempPartnerCatMap : product
												.getPartnerCategoryMap()) {
											if (partnerName
													.equalsIgnoreCase(tempPartnerCatMap
															.getPartnerName())) {
												errorMessage
														.append(" Channel Category reference already associated with this SKU for the Channel ");
												validaterow = false;
											}
										}

									} else {
										errorMessage
												.append(" Channel Category reference is null ");
										validaterow = false;
									}
								} else {
									errorMessage
											.append(" Parent SKU does not exist ");
									validaterow = false;
								}
							} else {
								errorMessage.append(" Parent SKU is null ");
								validaterow = false;
							}
						} else {
							errorMessage.append(" Partner does not exist ");
							validaterow = false;
						}
					} else {
						errorMessage.append(" Partner Name is null ");
						validaterow = false;
					}
					if (validaterow) {
						partnerCatMap.getProduct().add(product);
						partnerCatMap.setSeller(seller);
						product.getPartnerCategoryMap().add(partnerCatMap);
						// productService.addPartnerCatMapping(product,
						// sellerId);
						saveProductMap
								.put(product.getProductSkuCode(), product);
						partnerCategoryMap.put(
								partnerCatMap.getPartnerCategoryRef(),
								partnerCatMap);
					} else {
						returnlist.put(errorMessage.toString(), "");
					}
				} catch (Exception e) {
					log.error("Failed! by SellerId : " + sellerId, e);
					if (partnerName != null) {
						errorMessage.append("Invalid Input! ");
						returnlist.put(errorMessage.toString(), "");
					}
				}
				log.debug("Sheet values :1 :" + entry.getCell(1) + " 2 :"
						+ entry.getCell(2) + " 3 :" + entry.getCell(3));
				// Pre save to generate id for use in hierarchy
			}

			if (saveProductMap != null && !saveProductMap.isEmpty()) {
				productService.addPartnerCatMapping(saveProductMap, sellerId);
			}
			Set<String> errorSet = returnlist.keySet();
			downloadUploadReportXLS(offices, "SKU_PartnerCat_Mapping",
					uploadFileName, 3, errorSet, path, sellerId, uploadReport);
		} catch (Exception e) {
			log.error("Failed! by SellerId : " + sellerId, e);
			addErrorUploadReport("SKU_PartnerCat_Mapping", sellerId,
					uploadReport);
			throw new MultipartException("Constraints Violated");
		}
		log.info("$$$ savepartnerCatMapping ends : SaveContents $$$");
		return validaterow;

	}*/
	
	public Object saveSKUCatMapping(MultipartFile file, int sellerId,
			String path, UploadReport uploadReport) {
		log.info("$$$ saveSKUCatMapping starts : SaveContents $$$");
		boolean validaterow = true;
		StringBuffer errorMessage = null;
		String uploadFileName = "";
		try {
			Map<String, String> returnlist = new LinkedHashMap<>();
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = offices.getSheetAt(0);
			HSSFRow entry;
			Integer noOfEntries = 1;
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			uploadFileName = file.getOriginalFilename().substring(0,
					file.getOriginalFilename().lastIndexOf("."))
					+ new Date().getTime();
			log.info(noOfEntries.toString());
			log.debug("After getting no of rows" + noOfEntries);

			Map<String, Boolean> partnerMap = partnerService
					.getPartnerMap(sellerId);
			//Seller seller = sellerService.getSeller(sellerId);
			//List<PartnerCategoryMap> saveList=new ArrayList<PartnerCategoryMap>();
			List<Product> productList = productService.listProducts(sellerId);
			Map<String, Product> productMap = new HashMap<String, Product>();
			Map<String, List<PartnerCategoryMap>> saveMap = new HashMap<String, List<PartnerCategoryMap>>();

			if (productList != null && !productList.isEmpty()) {
				for (Product tmpProduct : productList) {
					productMap.put(tmpProduct.getProductSkuCode().trim()
							.toLowerCase(), tmpProduct);
				}
			}
			//Map<String, Product> saveProductMap = new HashMap<String, Product>();

			List<PartnerCategoryMap> partnerCatList = categoryService
					.listPartnerCategoryMap(sellerId, 0);
			Map<String, PartnerCategoryMap> partnerCategoryMap = new HashMap<String, PartnerCategoryMap>();
			if (partnerCatList != null && !partnerCatList.isEmpty()) {
				
				for (PartnerCategoryMap tmpPartnerCat : partnerCatList) {
					String key=tmpPartnerCat.getPartnerCategoryRef()+"-"+tmpPartnerCat.getPartnerName();
					partnerCategoryMap.put(
							key,
							tmpPartnerCat);
				}
			}

			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				Product product = null;
				PartnerCategoryMap partnerCatMap = null;
				String partnerName = null;

				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				errorMessage = new StringBuffer("Row :" + (rowIndex - 2) + ":");
				try {
					if (entry.getCell(1) != null
							&& entry.getCell(1).getCellType() != HSSFCell.CELL_TYPE_BLANK) {

						if (partnerMap.containsKey(entry.getCell(1).toString()
								.trim())) {

							partnerName = entry.getCell(1).toString().trim();

							if (entry.getCell(0) != null
									&& entry.getCell(0).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
								entry.getCell(0).setCellType(HSSFCell.CELL_TYPE_STRING);

								product = productMap.get(entry.getCell(0)
										.toString().trim().toLowerCase());

								if (product != null) {

									if (entry.getCell(2) != null
											&& entry.getCell(2).getCellType() != HSSFCell.CELL_TYPE_BLANK) {

										String catName = entry.getCell(2)
												.toString().trim();
										String key=catName+"-"+partnerName;
										partnerCatMap = partnerCategoryMap
												.get(key);

										if (partnerCatMap == null) {
											partnerCatMap = new PartnerCategoryMap();
											partnerCatMap
													.setPartnerName(partnerName);
											partnerCatMap
													.setPartnerCategoryRef(entry
															.getCell(2)
															.toString());
										}
										

										for (PartnerCategoryMap tempPartnerCatMap : product
												.getPartnerCategoryMap()) {
											if (partnerName
													.equalsIgnoreCase(tempPartnerCatMap
															.getPartnerName())) {
												errorMessage
														.append(" Channel Category reference already associated with this SKU for the Channel ");
												validaterow = false;
											}
										}

									} else {
										errorMessage
												.append(" Channel Category reference is null ");
										validaterow = false;
									}
								} else {
									errorMessage
											.append(" Parent SKU does not exist ");
									validaterow = false;
								}
							} else {
								errorMessage.append(" Parent SKU is null ");
								validaterow = false;
							}
						} else {
							errorMessage.append(" Partner does not exist ");
							validaterow = false;
						}
					} else {
						errorMessage.append(" Partner Name is null ");
						validaterow = false;
					}
					if (validaterow) {
						//partnerCatMap.getProduct().add(product);
						//partnerCatMap.setSeller(seller);
						if(saveMap.containsKey(product.getProductSkuCode()))
							saveMap.get(product.getProductSkuCode()).add(partnerCatMap);
						else
						{
							List<PartnerCategoryMap> newlist=new ArrayList<PartnerCategoryMap>();
							newlist.add(partnerCatMap);
							saveMap.put(product.getProductSkuCode(), newlist);
						}
						/*saveProductMap
								.put(product.getProductSkuCode(), product);*/
						
					} else {
						returnlist.put(errorMessage.toString(), "");
					}
				} catch (Exception e) {
					log.error("Failed! by SellerId : " + sellerId, e);
					if (partnerName != null) {
						errorMessage.append("Invalid Input! ");
						returnlist.put(errorMessage.toString(), "");
					}
				}
				log.debug("Sheet values :1 :" + entry.getCell(1) + " 2 :"
						+ entry.getCell(2) + " 3 :" + entry.getCell(3));
				// Pre save to generate id for use in hierarchy
			}

			if (saveMap != null && !saveMap.isEmpty()) {
				productService.addPartnerCatMapping(saveMap, sellerId);
			}
			Set<String> errorSet = returnlist.keySet();
			downloadUploadReportXLS(offices, "SKU_PartnerCat_Mapping",
					uploadFileName, 3, errorSet, path, sellerId, uploadReport);
		} catch (Exception e) {
			log.error("Failed! by SellerId : " + sellerId, e);
			addErrorUploadReport("SKU_PartnerCat_Mapping", sellerId,
					uploadReport);
			throw new MultipartException("Constraints Violated");
		}
		log.info("$$$ savepartnerCatMapping ends : SaveContents $$$");
		return validaterow;

	}

	public Object savePartnerCatCommMapping(MultipartFile file, int sellerId,
			String path, UploadReport uploadReport) {
		log.info("$$$ savePartnerCatCommMapping starts : SaveContents $$$");
		boolean validaterow = true;
		StringBuffer errorMessage = null;
		String uploadFileName = "";
		try {
			Map<String, String> returnlist = new LinkedHashMap<>();
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = offices.getSheetAt(0);
			HSSFRow entry;
			Integer noOfEntries = 1;
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			uploadFileName = file.getOriginalFilename().substring(0,
					file.getOriginalFilename().lastIndexOf("."))
					+ new Date().getTime();
			log.info(noOfEntries.toString());
			log.debug("After getting no of rows" + noOfEntries);
			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				Partner partner = null;
				Product product = null;
				PartnerCategoryMap partnerCatMap = null;

				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				errorMessage = new StringBuffer("Row :" + (rowIndex - 2) + ":");
				try {
					if (entry.getCell(0) != null
							&& entry.getCell(0).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						partner = partnerService.getPartner(entry.getCell(0)
								.toString(), sellerId);
						if (partner != null) {

							if (entry.getCell(1) != null
									&& entry.getCell(1).getCellType() != HSSFCell.CELL_TYPE_BLANK) {

								String catName = entry.getCell(1).toString()
										.trim();
								partnerCatMap = categoryService
										.getPartnerCategoryMap(
												partner.getPcName(), catName,
												sellerId);

								if (partnerCatMap == null) {
									errorMessage
											.append(" Channel Category reference not found ");
									validaterow = false;
								} else {
									if (entry.getCell(2) != null
											&& entry.getCell(2).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
										try {
											partnerCatMap
													.setCommission((float) entry
															.getCell(2)
															.getNumericCellValue());
										} catch (NumberFormatException e) {
											log.error("Failed! by SellerId : "
													+ sellerId, e);
											errorMessage
													.append(" Commission should be a number ");
											validaterow = false;
										}
										catch (Exception e) {
											log.error("Failed! by SellerId : "
													+ sellerId, e);
											errorMessage
													.append("Commission should be a number");
											validaterow = false;
										}
									} else {
										errorMessage
												.append(" Commission is null ");
										validaterow = false;
									}
								}

							} else {
								errorMessage
										.append(" Channel Category reference is null ");
								validaterow = false;
							}

						} else {
							errorMessage.append(" Partner does not exist ");
							validaterow = false;
						}
					} else {
						errorMessage.append(" Partner Name is null ");
						validaterow = false;
					}
					if (validaterow) {
						categoryService.addPartnerCatCommission(partnerCatMap,
								sellerId);
					} else {
						returnlist.put(errorMessage.toString(), "");
					}
				} catch (Exception e) {
					log.error("Failed! by SellerId : " + sellerId, e);
					if (partner != null) {
						errorMessage.append("Invalid Input! ");
						returnlist.put(errorMessage.toString(), "");
					}
				}
				log.debug("Sheet values :1 :" + entry.getCell(1) + " 2 :"
						+ entry.getCell(2) + " 3 :" + entry.getCell(3));
				// Pre save to generate id for use in hierarchy
			}
			Set<String> errorSet = returnlist.keySet();
			downloadUploadReportXLS(offices, "PartnerCat_ProdCat_Mapping",
					uploadFileName, 3, errorSet, path, sellerId, uploadReport);
		} catch (Exception e) {
			log.error("Failed! by SellerId : " + sellerId, e);
			addErrorUploadReport("PartnerCat_ProdCat_Mapping", sellerId,
					uploadReport);
			throw new MultipartException("Constraints Violated");
		}
		log.info("$$$ savepartnerCatMapping ends : SaveContents $$$");
		return validaterow;

	}
}
