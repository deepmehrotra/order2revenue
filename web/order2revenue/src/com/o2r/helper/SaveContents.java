package com.o2r.helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
import org.hibernate.SessionFactory;
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
import com.o2r.model.GatePass;
import com.o2r.model.Order;
import com.o2r.model.OrderPayment;
import com.o2r.model.OrderRTOorReturn;
import com.o2r.model.Partner;
import com.o2r.model.PaymentUpload;
import com.o2r.model.Product;
import com.o2r.model.ProductConfig;
import com.o2r.model.TaxCategory;
import com.o2r.model.UploadReport;
import com.o2r.service.CategoryService;
import com.o2r.service.EventsService;
import com.o2r.service.ExpenseService;
import com.o2r.service.OrderService;
import com.o2r.service.PartnerService;
import com.o2r.service.PaymentUploadService;
import com.o2r.service.ProductService;
import com.o2r.service.ReportGeneratorService;
import com.o2r.service.SellerService;
import com.o2r.service.TaxDetailService;

@Service("saveContents")
@Transactional
public class SaveContents {

	/*
	 * @Resource(name="sessionFactory") private SessionFactory sessionFactory;
	 */
	@Autowired
	private SessionFactory sessionFactory;
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

	private static final String UPLOAD_DIR = "UploadReport";

	static Logger log = Logger.getLogger(SaveContents.class.getName());

	Properties properties = null;
	org.springframework.core.io.Resource resource = new ClassPathResource(
			"database.properties");

	public Map<String, OrderBean> saveOrderContents(MultipartFile file,
			int sellerId, String path, UploadReport uploadReport)
			throws IOException {
		log.info("$$$ saveOrderContents starts : SaveContents $$$");
		HSSFRow entry;
		Integer noOfEntries = 1;
		boolean validaterow = true;
		Map<String, OrderBean> returnOrderMap = new LinkedHashMap<>();
		OrderBean order = null;
		StringBuffer errorMessage = null;
		CustomerBean customerBean = null;
		OrderTaxBean otb = null;
		Partner partner = null;
		Events event = null;
		try {
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = offices.getSheetAt(0);
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			log.info(noOfEntries.toString());
			log.debug("After getting no of rows" + noOfEntries);
			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				validaterow = true;
				entry = worksheet.getRow(rowIndex);
				errorMessage = new StringBuffer("Row :" + (rowIndex - 2) + ":");
				log.debug(" Row index : " + (rowIndex - 2));
				log.debug(entry.getCell(0).toString());
				log.debug(entry.getCell(1).getDateCellValue());
				log.debug(entry.getCell(2).toString());

				order = new OrderBean();
				customerBean = new CustomerBean();
				otb = new OrderTaxBean();
				if (entry.getCell(0) != null
						&& entry.getCell(0).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					entry.getCell(0).setCellType(HSSFCell.CELL_TYPE_STRING);
					log.debug(" Getting string value form : "
							+ entry.getCell(0));
					List<Order> onj = orderService
							.findOrders("channelOrderID", entry.getCell(0)
									.toString(), sellerId, false, false);
					if (onj == null || onj.size() == 0) {
						order.setChannelOrderID(entry.getCell(0).toString());
					} else {
						order.setChannelOrderID(entry.getCell(0).toString());
						errorMessage
								.append(" Channel OrderId is already present;");
						validaterow = false;
					}
				} else {
					errorMessage.append(" Channel OrderId is null ");
					validaterow = false;
				}
				if (entry.getCell(1) != null
						&& entry.getCell(1).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					if (HSSFDateUtil.isCellDateFormatted(entry.getCell(1))) {
						order.setOrderDate(entry.getCell(1).getDateCellValue());
					} else {
						errorMessage
								.append(" Order Recieved Date formate is wrong ,enter mm/dd/yyyy,");
						validaterow = false;
					}
				} else {
					errorMessage.append(" Order Recieved Date is null;");
					validaterow = false;
				}
				if (entry.getCell(2) != null
						&& entry.getCell(2).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					Product product = productService.getProduct(entry
							.getCell(2).toString(), sellerId);
					if (product == null) {
						order.setProductSkuCode(entry.getCell(2).toString());
						errorMessage.append(" Product SKU does not exist;");
						validaterow = false;
					} else {
						order.setProductSkuCode(product.getProductSkuCode());
					}
				} else {
					errorMessage.append(" Product SKU is null;");
					validaterow = false;
				}
				if (entry.getCell(3) != null
						&& entry.getCell(3).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					partner = partnerService.getPartner(entry.getCell(3)
							.toString(), sellerId);
					if (partner != null)
						order.setPcName(entry.getCell(3).toString());
					else {
						order.setPcName(entry.getCell(3).toString());
						errorMessage.append(" Partner does not exist;");
						validaterow = false;
					}
				} else {
					errorMessage.append(" Partner Name is null;");
					validaterow = false;
				}
				if (entry.getCell(4) != null
						&& entry.getCell(4).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					customerBean.setCustomerName(entry.getCell(4).toString());
				}
				if (entry.getCell(5) != null
						&& entry.getCell(5).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					if (entry.getCell(5).toString().equalsIgnoreCase("COD")
							|| entry.getCell(5).toString()
									.equalsIgnoreCase("Prepaid"))
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
				if (entry.getCell(6) != null
						&& entry.getCell(6).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					order.setAwbNum(entry.getCell(6).toString());
				} else {
					errorMessage.append(" AWBNUM is null;");
					validaterow = false;
				}
				if (entry.getCell(7) != null
						&& entry.getCell(7).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					order.setInvoiceID(entry.getCell(7).toString());
				} else {
					errorMessage.append(" Invoice ID is null;");
					validaterow = false;
				}
				if (entry.getCell(8) != null
						&& entry.getCell(8).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					order.setSubOrderID(entry.getCell(8).toString());
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
						order.setOrderMRP(Double.parseDouble(entry.getCell(11)
								.toString()));
					} catch (NumberFormatException e) {
						errorMessage.append(" Order MRP should be a number ");
						validaterow = false;
					}
				} else {
					errorMessage.append(" Order MRP is null ");
					validaterow = false;
				}
				if (entry.getCell(12) != null
						&& entry.getCell(12).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					try {
						order.setOrderSP(Double.parseDouble(entry.getCell(12)
								.toString()));
					} catch (NumberFormatException e) {
						errorMessage.append(" Order SP should be a number ");
						validaterow = false;
					}
				} else {
					errorMessage.append(" Order SP is null ");
					validaterow = false;
				}

				if (entry.getCell(13) != null
						&& entry.getCell(13).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					TaxCategory taxcat = taxDetailService.getTaxCategory(entry
							.getCell(13).toString(), sellerId);
					if (taxcat != null)
						otb.setTaxCategtory(entry.getCell(13).toString());
					else {
						otb.setTaxCategtory(entry.getCell(13).toString());
						errorMessage.append("Tax Category does not exist ");
						validaterow = false;
					}
				} else {
					errorMessage.append("Tax Category is null ");
					validaterow = false;
				}
				if (entry.getCell(14) != null
						&& entry.getCell(14).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					if (HSSFDateUtil.isCellDateFormatted(entry.getCell(14))) {
						order.setShippedDate(entry.getCell(14)
								.getDateCellValue());
					} else {
						errorMessage
								.append(" Shipped Date formate is wrong ,enter mm/dd/yyyy,");
						validaterow = false;
					}
				} else {
					errorMessage.append(" Shipping Date is null ");
					validaterow = false;
				}

				if (entry.getCell(15) != null
						&& entry.getCell(15).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					try {
						if ((int) Float
								.parseFloat(entry.getCell(15).toString()) != 0) {
							order.setQuantity((int) Float.parseFloat(entry
									.getCell(15).toString()));
						} else {
							errorMessage.append(" Quantity can not be 0 ");
							validaterow = false;
						}
					} catch (NumberFormatException e) {
						errorMessage.append(" Quantity should be a number ");
						validaterow = false;
					}
				} else {
					errorMessage.append(" Quantity can not be null ");
					validaterow = false;
				}

				if (partner != null) {
					event = eventsService.isEventActiive(order.getOrderDate(),
							partner.getPcName(), sellerId);
					if (event != null) {
						if (event.getNrnReturnConfig().getNrCalculatorEvent()
								.equalsIgnoreCase("fixed")) {

							if (entry.getCell(16) != null
									&& entry.getCell(16).getCellType() != HSSFCell.CELL_TYPE_BLANK) {

								try {
									order.setGrossNetRate(Double
											.parseDouble(entry.getCell(16)
													.toString()));
								} catch (NumberFormatException e) {
									errorMessage
											.append(" Net Rate should be number ");
									validaterow = false;
								}
							} else {
								errorMessage
										.append(" Net Rate is null and ongoing event on the order with fixed price.");
								validaterow = false;
							}
						}
					} else {
						if (partner != null
								&& partner.getNrnReturnConfig() != null
								&& !partner.getNrnReturnConfig()
										.isNrCalculator()) {
							if (entry.getCell(16) != null
									&& entry.getCell(16).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
								try {
									order.setGrossNetRate(Double
											.parseDouble(entry.getCell(16)
													.toString()));
								} catch (NumberFormatException e) {
									errorMessage
											.append(" Net Rate should be number ");
									validaterow = false;
								}
							} else {
								errorMessage.append(" Net Rate is null ");
								validaterow = false;
							}
						}

					}
				}
				if (entry.getCell(17) != null
						&& entry.getCell(17).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					log.debug(" Email from sheet ************ " + rowIndex
							+ ":" + entry.getCell(17).toString());
					customerBean.setCustomerEmail(entry.getCell(17).toString());
				}
				if (entry.getCell(18) != null
						&& entry.getCell(18).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					customerBean.setCustomerPhnNo(entry.getCell(18).toString());
				}
				if (entry.getCell(19) != null
						&& entry.getCell(19).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					customerBean.setCustomerCity(entry.getCell(19).toString());
				}
				if (entry.getCell(20) != null
						&& entry.getCell(20).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					customerBean.setCustomerAddress(entry.getCell(20)
							.toString());
				}
				if (entry.getCell(21) != null
						&& entry.getCell(21).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					log.debug(" getting zipcode ");
					if (areaConfigDao.isZipCodeValid(String
							.valueOf((long) entry.getCell(21)
									.getNumericCellValue()))) {
						try {
							customerBean.setZipcode(String.valueOf((long) entry
									.getCell(21).getNumericCellValue()));
						} catch (Exception e) {
							e.printStackTrace();
							errorMessage
									.append("Customer zipcode is corrupted ");
							validaterow = false;
						}
					} else {
						errorMessage.append("Customer zipcode is not valid ");
						validaterow = false;
						customerBean.setZipcode(String.valueOf((long) entry
								.getCell(21).getNumericCellValue()));
					}
				} else {
					errorMessage.append("Customer zipcode is blank ");
					validaterow = false;
				}

				if (entry.getCell(22) != null
						&& StringUtils.isNotBlank(entry.getCell(22).toString())) {
					order.setSellerNote(entry.getCell(22).toString());

				}
				log.debug("Sheet values :1 :" + entry.getCell(1) + " 2 :"
						+ entry.getCell(2) + " 3 :" + entry.getCell(3));
				// Pre save to generate id for use in hierarchy
				if (validaterow) {
					order.setCustomer(customerBean);
					order.setOrderTax(otb);
					orderService.addOrder(ConverterClass.prepareModel(order),
							sellerId);
				} else {
					order.setCustomer(customerBean);
					order.setOrderTax(otb);
					log.debug(" Error while saving order : "
							+ order.getChannelOrderID() + " errorMessage : "
							+ errorMessage);
					returnOrderMap.put(errorMessage.toString(), order);
				}
			}
			Set<String> errorSet = returnOrderMap.keySet();
			downloadUploadReportXLS(offices, "Order Report", 24, errorSet,
					path, sellerId, uploadReport);

		} catch (Exception e) {
			log.debug("Inside save contents exception :"
					+ e.getLocalizedMessage());
			e.printStackTrace();
			addErrorUploadReport("Order Report", sellerId, uploadReport);
			log.error("Failed!", e);
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

		List<Order> orderlist = new ArrayList<Order>();

		try {
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = offices.getSheetAt(0);
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			log.info(noOfEntries.toString());
			log.debug("After getting no of rows" + noOfEntries);
			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				validaterow = true;
				entry = worksheet.getRow(rowIndex);
				errorMessage = new StringBuffer("Row :" + (rowIndex - 2) + ":");

				order = new OrderBean();

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
						&& StringUtils.isNotBlank(entry.getCell(1).toString())) {
					order.setSubOrderID(entry.getCell(1).toString());
					order.setChannelOrderID(entry.getCell(1).toString());

					if (poId != null
							&& !poId.equalsIgnoreCase(order.getSubOrderID())
							&& !orderlist.isEmpty()) {
						Order consolidatedOrder = orderService
								.generateConsolidatedOrder(orderlist, sellerId);
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
						&& StringUtils.isNotBlank(entry.getCell(2).toString())) {
					ProductConfig productConfig = productService
							.getProductConfig(entry.getCell(2).toString(),
									order.getPcName(), sellerId);
					if (productConfig == null) {
						errorMessage
								.append(" Product configuration does not exist ");
						validaterow = false;
					} else {
						order.setProductSkuCode(entry.getCell(2).toString());
					}
				} else {
					errorMessage.append(" Product SKU is null ");
					validaterow = false;
				}

				if (entry.getCell(3) != null
						&& StringUtils.isNotBlank(entry.getCell(3).toString())) {
					try {
						order.setQuantity((int) Float.parseFloat(entry.getCell(
								3).toString()));
					} catch (NumberFormatException e) {
						errorMessage.append(" Quantity should be a number ");
						validaterow = false;
					}
				} else {
					errorMessage.append(" Quantity is null ");
					validaterow = false;
				}

				if (entry.getCell(4) != null
						&& StringUtils.isNotBlank(entry.getCell(4).toString())) {
					try {
						order.setPoPrice(Double.parseDouble(entry.getCell(4)
								.toString()));
					} catch (NumberFormatException e) {
						errorMessage
								.append(" Gross PO Price should be a number ");
						validaterow = false;
					}
				} else {
					errorMessage.append(" Gross PO Price is null ");
					validaterow = false;
				}

				if (entry.getCell(5) != null
						&& StringUtils.isNotBlank(entry.getCell(5).toString())) {
					try {
						order.setOrderMRP(Double.parseDouble(entry.getCell(5)
								.toString()));
					} catch (NumberFormatException e) {
						errorMessage.append(" PO MRP should be a number ");
						validaterow = false;
					}
				} else {
					errorMessage.append(" PO MRP is null ");
					validaterow = false;
				}

				if (entry.getCell(6) != null
						&& StringUtils.isNotBlank(entry.getCell(6).toString())) {
					try {
						order.setTotalAmountRecieved(entry.getCell(6)
								.getNumericCellValue());

					} catch (NumberFormatException e) {
						errorMessage.append(" Amount should be a number ");
						validaterow = false;
					}
				} else {
					errorMessage.append(" Amount is null ");
					validaterow = false;
				}

				if (entry.getCell(7) != null
						&& StringUtils.isNotBlank(entry.getCell(7).toString())) {
					order.setInvoiceID(entry.getCell(7).toString());

					String curInvoiceId = entry.getCell(7).toString();
					if (curInvoiceId != null) {

						if (orderService.isPOOrderUploaded(
								order.getSubOrderID(), order.getInvoiceID(), sellerId)) {
							errorMessage.append(" PO already uploaded ");
							validaterow = false;
						}
					}

					if (invoiceId != null
							&& !invoiceId
									.equalsIgnoreCase(order.getInvoiceID())
							&& !orderlist.isEmpty()) {
						Order consolidateOrder = orderService
								.getConsolidatedOrder(order.getSubOrderID(),
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

				if (entry.getCell(8) != null
						&& StringUtils.isNotBlank(entry.getCell(8).toString())) {
					order.setSealNo(entry.getCell(8).toString());
					order.setAwbNum(entry.getCell(8).toString());
				} else {
					errorMessage.append(" Seal No is null ");
					validaterow = false;
				}

				if (entry.getCell(9) != null
						&& StringUtils.isNotBlank(entry.getCell(9).toString())) {

					if (HSSFDateUtil.isCellDateFormatted(entry.getCell(9))) {
						order.setOrderDate(entry.getCell(9).getDateCellValue());
						order.setShippedDate(entry.getCell(9)
								.getDateCellValue());
						order.setDeliveryDate(entry.getCell(9)
								.getDateCellValue());
					} else {
						errorMessage.append(" Shipped Date format is wrong ");
						validaterow = false;
					}
				} else {
					errorMessage.append(" Shipped Date is null ");
					validaterow = false;
				}

				if (entry.getCell(10) != null
						&& StringUtils.isNotBlank(entry.getCell(10).toString())) {
					order.setSellerNote(entry.getCell(10).toString());
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
			}

			if (!orderlist.isEmpty()) {
				Order consolidatedOrder = orderService
						.generateConsolidatedOrder(orderlist, sellerId);
				orderService.updatePOOrders(orderlist, consolidatedOrder);
				orderlist.clear();
			}

			Set<String> errorSet = returnOrderMap.keySet();
			downloadUploadReportXLS(offices, "OrderPOSheet", 11, errorSet,
					path, sellerId, uploadReport);

		} catch (Exception e) {
			log.debug("Inside save contents exception :"
					+ e.getLocalizedMessage());
			e.printStackTrace();
			log.error("Failed!", e);
			addErrorUploadReport("OrderPOSheet", sellerId, uploadReport);
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
		try {
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = offices.getSheetAt(0);
			HSSFRow entry;
			Integer noOfEntries = 1;

			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			log.info(noOfEntries.toString());
			log.debug("After getting no of rows" + noOfEntries);
			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				errorMessage = new StringBuffer("Row :" + (rowIndex - 2) + ":");
				log.debug("Product 1" + entry.getCell(1).toString());
				log.debug("Product  2" + entry.getCell(2).toString());
				log.debug(entry.getCell(3).toString());
				log.debug(entry.getCell(4).toString());
				Product product = new Product();
				if (entry.getCell(0) != null
						&& entry.getCell(0).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					product.setProductName(entry.getCell(0).toString());
				} else {
					errorMessage.append(" Product Name is null ");
					validaterow = false;
				}
				if (entry.getCell(1) != null
						&& entry.getCell(1).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					Product obj = productService.getProduct(entry.getCell(1)
							.toString(), sellerId);
					if (obj == null) {
						product.setProductSkuCode(entry.getCell(1).toString());
					} else {
						product.setProductSkuCode(entry.getCell(1).toString());
						errorMessage.append(" Product SKU already present ");
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
						product.setCategoryName(entry.getCell(2).toString());
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
						product.setProductPrice(Float.valueOf(entry.getCell(3)
								.toString()));
					} catch (NumberFormatException e) {
						errorMessage
								.append(" Product price should be a number ");
						validaterow = false;
					}
				} else {
					errorMessage.append(" Product price is null ");
					validaterow = false;
				}
				if (entry.getCell(4) != null
						&& entry.getCell(4).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					try {
						if ((int) Float.parseFloat(entry.getCell(4).toString()) != 0) {
							product.setQuantity(Float.valueOf(
									entry.getCell(4).toString()).longValue());
						} else {
							errorMessage.append(" Quantity can not be 0 ");
							validaterow = false;
						}
					} catch (NumberFormatException e) {
						errorMessage.append(" Quantity should be a number ");
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
						errorMessage
								.append(" Product price should be a number ");
						validaterow = false;
					}
				} else {
					errorMessage.append(" Product price is null ");
					validaterow = false;
				}
				if (entry.getCell(7) != null
						&& entry.getCell(7).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					try {
						product.setLength(Float.valueOf(
								entry.getCell(7).toString()).longValue());
					} catch (NumberFormatException e) {
						errorMessage.append(" Length should be a number ");
						validaterow = false;
					}
				} else {
					errorMessage.append(" Length is null ");
					validaterow = false;
				}
				if (entry.getCell(8) != null
						&& entry.getCell(8).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					try {
						product.setBreadth(Float.valueOf(
								entry.getCell(8).toString()).longValue());
					} catch (NumberFormatException e) {
						errorMessage.append(" Breadth should be a number ");
						validaterow = false;
					}
				} else {
					errorMessage.append(" Breadth is null ");
					validaterow = false;
				}

				if (entry.getCell(9) != null
						&& entry.getCell(9).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					try {
						product.setHeight(Float.valueOf(
								entry.getCell(9).toString()).longValue());
					} catch (NumberFormatException e) {
						errorMessage.append(" Height should be a number ");
						validaterow = false;
					}
				} else {
					errorMessage.append(" Height is null ");
					validaterow = false;
				}
				if (entry.getCell(10) != null
						&& entry.getCell(10).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					try {
						product.setDeadWeight((float) entry.getCell(10)
								.getNumericCellValue());
					} catch (NumberFormatException e) {
						errorMessage.append(" Dead Weight should be a number ");
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
				if (entry.getCell(6) != null
						&& entry.getCell(6).getCellType() != HSSFCell.CELL_TYPE_BLANK)
					product.setChannelSKU(entry.getCell(6).toString());
				if (validaterow) {
					productService.addProduct(product, sellerId);
				} else {
					returnProductMap.put(errorMessage.toString(),
							ConverterClass.prepareProductBean(product));
				}
				System.out
						.println("Sheet values :1 :" + entry.getCell(1)
								+ " 2 :" + entry.getCell(2) + " 3 :"
								+ entry.getCell(3));
				// Pre save to generate id for use in hierarchy
			}
			Set<String> errorSet = returnProductMap.keySet();
			downloadUploadReportXLS(offices, "ProductReport", 11, errorSet,
					path, sellerId, uploadReport);
		} catch (Exception e) {
			log.debug("Inside save contents exception :"
					+ e.getLocalizedMessage());
			e.printStackTrace();
			log.error("Failed!", e);
			addErrorUploadReport("ProductReport", sellerId, uploadReport);
			throw new MultipartException("Constraints Violated");
		}
		log.info("$$$ saveProductContents ends : SaveContents $$$");
		return returnProductMap;
	}

	// My coding Product Config *********

	public Map<String, ProductConfigBean> saveSKUMappingContents(
			MultipartFile file, int sellerId, String path,
			UploadReport uploadReport) throws IOException {
		log.info("$$$ saveSKUMappingContents starts : SaveContents $$$");
		boolean validaterow = true;
		Map<String, ProductConfigBean> returnProductConfigMap = new LinkedHashMap<>();
		StringBuffer errorMessage = null;
		try {
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = offices.getSheetAt(0);
			HSSFRow entry;
			Integer noOfEntries = 1;
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			log.info(noOfEntries.toString());
			log.debug("After getting no of rows" + noOfEntries);
			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				errorMessage = new StringBuffer("Row :" + (rowIndex - 2) + ":");
				log.debug("Product 1" + entry.getCell(1));
				log.debug("Product  2" + entry.getCell(2));
				ProductConfig productConfig = new ProductConfig();
				if (entry.getCell(0) != null
						&& entry.getCell(0).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					Product product = productService.getProduct(entry
							.getCell(0).toString(), sellerId);
					if (product != null) {
						productConfig.setProductSkuCode(entry.getCell(0)
								.toString());
						if (entry.getCell(1) != null
								&& entry.getCell(1).getCellType() != HSSFCell.CELL_TYPE_BLANK
								&&entry.getCell(2) != null
								&& entry.getCell(2).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							ProductConfig procon=productService.getProductConfig(entry.getCell(1)
									.toString(), entry.getCell(2).toString(), sellerId);
							if(procon==null)
							productConfig.setChannelSkuRef(entry.getCell(1)
									.toString());
							else
							{
								errorMessage.append(" Channel Reference Code already present for that channel ");
								validaterow = false;
							}
						} else {
							errorMessage.append(" Channel Reference Code is null ");
							validaterow = false;
						}
						if (entry.getCell(2) != null
								&& entry.getCell(2).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							if(!entry.getCell(2)
									.toString().equalsIgnoreCase("Myntra"))
							productConfig.setChannelName(entry.getCell(2)
									.toString());
							else
							{
								errorMessage.append("Enter only Market Place Channels ");
								validaterow = false;
							}
						} else {
							errorMessage.append(" Channel Name is null ");
							validaterow = false;
						}

					}
					 else {
							errorMessage.append(" Product with given SKU does not present ");
							validaterow = false;
						}
				} else {
					errorMessage.append(" Product SKU is null ");
					validaterow = false;
				}

				if (validaterow) {
					productService.addSKUMapping(productConfig, sellerId);
				} else {
					returnProductConfigMap.put(errorMessage.toString(),
							ConverterClass
									.prepareProductConfigBean(productConfig));
				}
				log.debug("Sheet values :1 :" + entry.getCell(1) + " 2 :"
						+ entry.getCell(2) + " 3 :" + entry.getCell(3));
				// Pre save to generate id for use in hierarchy
			}
			Set<String> errorSet = returnProductConfigMap.keySet();
			downloadUploadReportXLS(offices, "SKUMappingReport", 4, errorSet,
					path, sellerId, uploadReport);
		} catch (Exception e) {
			log.debug("Inside save contents exception :"
					+ e.getLocalizedMessage());
			e.printStackTrace();
			log.error("Failed!", e);
			addErrorUploadReport("SKUMappingReport", sellerId, uploadReport);
			throw new MultipartException("Constraints Violated");
		}
		log.info("$$$ saveSKUMappingContents ends : SaveContents $$$");
		return returnProductConfigMap;
	}

	public Map<String, ProductConfigBean> saveProductConfigContents(
			MultipartFile file, int sellerId, String path,
			UploadReport uploadReport) throws IOException {
		log.info("$$$ saveProductConfigContents starts : SaveContents $$$");
		boolean validaterow = true;
		Map<String, ProductConfigBean> returnProductConfigMap = new LinkedHashMap<>();
		StringBuffer errorMessage = null;
		try {
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = offices.getSheetAt(0);
			HSSFRow entry;
			Integer noOfEntries = 1;
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			log.info(noOfEntries.toString());
			log.debug("After getting no of rows" + noOfEntries);
			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				errorMessage = new StringBuffer("Row :" + (rowIndex - 2) + ":");
				log.debug("Product 1" + entry.getCell(1).toString());
				log.debug("Product  2" + entry.getCell(2).toString());
				log.debug(entry.getCell(3).toString());
				log.debug(entry.getCell(4).toString());
				ProductConfig productConfig = new ProductConfig();
				if (entry.getCell(0) != null
						&& entry.getCell(0).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					productConfig
							.setProductSkuCode(entry.getCell(0).toString());
				} else {
					errorMessage.append(" Product SKU is null ");
					validaterow = false;
				}
				if (entry.getCell(1) != null
						&& entry.getCell(1).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					productConfig.setChannelName(entry.getCell(1).toString().toLowerCase());
				} else {
					errorMessage.append(" Channel Name is null ");
					validaterow = false;
				}
				if (entry.getCell(2) != null
						&& entry.getCell(2).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					productConfig.setChannelSkuRef(entry.getCell(2).toString());
				} else {
					errorMessage.append(" Channel SKU is null ");
					validaterow = false;
				}
				
				ProductConfig productConfigChk = productService
						.getProductConfig(productConfig.getChannelSkuRef(),
								productConfig.getChannelName(), sellerId);
				if (productConfigChk != null) {
					errorMessage.append(" Product config already exist ");
					validaterow = false;
				}

				
				if (entry.getCell(3) != null
						&& entry.getCell(3).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					try {
						productConfig.setMrp(Double.valueOf(entry.getCell(3)
								.toString()));
					} catch (NumberFormatException e) {
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
						productConfig.setProductPrice((double) entry.getCell(4)
								.getNumericCellValue());
					} catch (NumberFormatException e) {
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
						errorMessage.append(" Discount should be a number ");
						validaterow = false;
					}
				} else {
					errorMessage.append(" Discount is null ");
					validaterow = false;
				}

				if (validaterow) {
					productService.addProductConfig(productConfig, sellerId);
				} else {
					returnProductConfigMap.put(errorMessage.toString(),
							ConverterClass
									.prepareProductConfigBean(productConfig));
				}
				log.debug("Sheet values :1 :" + entry.getCell(1) + " 2 :"
						+ entry.getCell(2) + " 3 :" + entry.getCell(3));
				// Pre save to generate id for use in hierarchy
			}
			Set<String> errorSet = returnProductConfigMap.keySet();
			downloadUploadReportXLS(offices, "ProductConfigReport", 6,
					errorSet, path, sellerId, uploadReport);
		} catch (Exception e) {
			log.debug("Inside save contents exception :"
					+ e.getLocalizedMessage());
			e.printStackTrace();
			log.error("Failed!", e);
			addErrorUploadReport("ProductConfigReport", sellerId, uploadReport);
			throw new MultipartException("Constraints Violated");
		}
		log.info("$$$ saveProductConfigContents ends : SaveContents $$$");
		return returnProductConfigMap;
	}

	// My Coding Product Config Ends ********

	public Map<String, OrderBean> savePaymentContents(MultipartFile file,
			int sellerId, String path, UploadReport uploadReport)
			throws IOException {
		log.info("$$$ savePaymentContents starts : SaveContents $$$");
		PaymentUpload paymentUpload = new PaymentUpload();
		double totalpositive = 0;
		double totalnegative = 0;
		String channelOrderId = null;
		String skucode = null;
		Order order = null;
		HSSFRow entry;
		Integer noOfEntries = 1;
		Date todaydat = new Date();
		Map<String, OrderBean> returnPaymentMap = new LinkedHashMap<>();
		StringBuffer errorMessage = null;
		boolean validaterow = true;
		boolean generatePaymentUpload = false;
		
		try {
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());

			HSSFSheet worksheet = offices.getSheetAt(0);
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			log.info(noOfEntries.toString());
			log.debug("After getting no of rows" + noOfEntries);
			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				entry = worksheet.getRow(rowIndex);
				validaterow = true;

				// Product product=new Product();
				OrderPayment payment = new OrderPayment();
				errorMessage = new StringBuffer("Row :" + (rowIndex - 2) + ":");
				log.debug(" channelOrderId " + channelOrderId);
				if (entry.getCell(0) != null
						&& entry.getCell(0).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					List<Order> onj = orderService
							.findOrders("channelOrderID", entry.getCell(0)
									.toString(), sellerId, false, false);
					if (onj != null && onj.size() != 0) {
						channelOrderId = entry.getCell(0).toString();
					} else {
						channelOrderId = entry.getCell(0).toString();
						errorMessage
								.append(" Channel OrderId not present ");
						validaterow = false;
					}
				} else {
					errorMessage.append(" Channel OrderId is null ");
					validaterow = false;
				}
				if (entry.getCell(2) != null
						&& entry.getCell(2).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					Product product = productService.getProduct(entry
							.getCell(2).toString(), sellerId);
					if (product == null) {
						errorMessage.append(" Product SKU does not exist ");
						validaterow = false;
					} else {
						skucode = product.getProductSkuCode();
					}
				} else {
					errorMessage.append(" Product SKU is null ");
					validaterow = false;
				}
				try {
					if (entry.getCell(4) != null
							&& StringUtils.isNotBlank(entry.getCell(4)
									.toString())
							&& (int) entry.getCell(4).getNumericCellValue() != 0) {

						payment.setNegativeAmount(Math.abs(Double
								.parseDouble(entry.getCell(4).toString())));
						totalnegative = totalnegative
								+ Math.abs(Double.parseDouble(entry.getCell(4)
										.toString()));

					} else if (entry.getCell(3) != null
							&& StringUtils.isNotBlank(entry.getCell(3)
									.toString())
							&& (int) Float.parseFloat(entry.getCell(3)
									.toString()) != 0) {

						payment.setPositiveAmount(Double.parseDouble(entry
								.getCell(3).toString()));
						totalpositive = totalpositive
								+ Double.parseDouble(entry.getCell(3)
										.toString());
						log.debug(" ******toatal psitive :" + totalpositive);

					} else {
						errorMessage.append(" Amount should be given ");
						validaterow = false;
					}
				} catch (NumberFormatException e) {
					errorMessage.append(" Recieved amount should be number ");
					validaterow = false;
				}
				if (entry.getCell(5) != null
						&& StringUtils.isNotBlank(entry.getCell(5).toString())) {

					if (HSSFDateUtil.isCellDateFormatted(entry.getCell(5))) {
						payment.setDateofPayment(entry.getCell(5)
								.getDateCellValue());
					} else {
						errorMessage
								.append(" Payment Date format is wrong ,enter mm/dd/yyyy ");
						validaterow = false;
					}
				} else {
					errorMessage.append(" Payment Date is null ");
					validaterow = false;
				}
				log.debug("Sheet values :1 :" + entry.getCell(1) + " 2 :"
						+ entry.getCell(2) + " 3 :" + entry.getCell(3));
				/*order = orderService.addOrderPayment(skucode, channelOrderId,
						payment, sellerId);*/
				if (validaterow) {
					order =orderService.addOrderPayment(skucode, channelOrderId,
							payment, sellerId);
				} else {
					returnPaymentMap.put(errorMessage.toString(),
							ConverterClass.prepareOrderBean(order));
				}
				if (order != null) {
					order.setPaymentUpload(paymentUpload);
					paymentUpload.getOrders().add(order);
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
						+ todaydat.getTime());
				paymentUpload.setUploadStatus("Success");
				paymentUploadService.addPaymentUpload(paymentUpload, sellerId);
			}

			Set<String> errorSet = returnPaymentMap.keySet();
			downloadUploadReportXLS(offices, "PaymentReport", 6, errorSet,
					path, sellerId, uploadReport);
		} catch (Exception e) {
			log.debug("Inside save contents exception :"
					+ e.getLocalizedMessage());
			e.printStackTrace();
			log.error("Failed!", e);
			addErrorUploadReport("PaymentReport", sellerId, uploadReport);
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
		try {
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = offices.getSheetAt(0);
			HSSFRow entry;
			Integer noOfEntries = 1;

			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			log.info(noOfEntries.toString());
			log.debug("After getting no of rows" + noOfEntries);
			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				errorMessage = new StringBuffer("Row :" + (rowIndex - 2) + ":");
				validaterow = true;
				entry = worksheet.getRow(rowIndex);
				log.debug("Product 1" + entry.getCell(1).toString());
				log.debug("Product  2" + entry.getCell(2).toString());
				if (entry.getCell(0) != null
						&& StringUtils.isNotBlank(entry.getCell(0).toString())) {
					Product product = productService.getProduct(entry
							.getCell(0).toString(), sellerId);
					if (product == null) {
						errorMessage.append(" Product SKU does not exist ");
						validaterow = false;
					} else {
						SkUCode = entry.getCell(0).toString();
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
							quantoAdd, quantoSub, true, sellerId,new Date());
				} else {
					returnInventoryMap.put(errorMessage.toString(), SkUCode);
				}
			}
			Set<String> errorSet = returnInventoryMap.keySet();
			downloadUploadReportXLS(offices, "InventoryReport", 5, errorSet,
					path, sellerId, uploadReport);
		} catch (Exception e) {
			log.debug("Inside save c" + "ontents exception :"
					+ e.getLocalizedMessage());
			e.printStackTrace();
			log.error("Failed!", e);
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
		int returnId = 0;
		OrderRTOorReturn orderReturn = null;
		Map<String, Order> returnlist = new LinkedHashMap<>();
		List<Order> orderlist = null;
		try {
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = offices.getSheetAt(0);
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			log.info(noOfEntries.toString());
			log.debug("After getting no of rows" + noOfEntries);
			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				errorMessage = new StringBuffer("Row :" + (rowIndex - 2) + ":");
				orderReturn = new OrderRTOorReturn();
				log.debug(entry.getCell(0));
				log.debug(entry.getCell(1));
				log.debug(entry.getCell(2));
				log.debug(entry.getCell(3));
				log.debug(entry.getCell(5));
				String id = null;
				if (entry.getCell(0) != null
						&& StringUtils.isNotBlank(entry.getCell(0).toString())) {
					id = entry.getCell(0).toString();
					orderlist = orderService.findOrders("channelOrderID", entry
							.getCell(0).toString(), sellerId, false, false);
					if (orderlist != null && orderlist.size() != 0) {

						log.debug(" Found match for channel order id");
						if (orderlist.get(0).getOrderReturnOrRTO() != null
								&& orderlist.get(0).getOrderReturnOrRTO()
										.getReturnDate() == null)
							order.setChannelOrderID(entry.getCell(0).toString());
						else {
							validaterow = false;
							errorMessage
									.append("Return already recieved for order");
						}
					} else {
						validaterow = false;
						errorMessage.append("Order Id doesnt exist");
					}
				} else if (entry.getCell(1) != null
						&& StringUtils.isNotBlank(entry.getCell(1).toString())) {
					id = entry.getCell(1).toString();
					orderlist = orderService.findOrders("subOrderID", entry
							.getCell(1).toString(), sellerId, false, false);
					if (orderlist != null && orderlist.size() != 0) {
						log.debug(" Found match for sub order id");
						order.setChannelOrderID(orderlist.get(0)
								.getChannelOrderID());
					} else {
						validaterow = false;
						errorMessage.append("Sub Order Id doesnt exist");
					}
				}
				if (id != null) {
					log.debug(" Order list in return : " + orderlist);
					if (orderlist != null && orderlist.size() != 0) {
						log.debug(" Order list is not null ");
						returnId = orderlist.get(0).getOrderReturnOrRTO()
								.getReturnId();
					}
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
						if (HSSFDateUtil.isCellDateFormatted(entry.getCell(5))) {
							orderReturn.setReturnDate(entry.getCell(5)
									.getDateCellValue());
						} else {
							errorMessage
									.append(" Return Date formate is wrong ,enter mm/dd/yyyy,");
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
								orderReturn
										.setReturnorrtoQty((int) Float
												.parseFloat(entry.getCell(6)
														.toString()));
							} else {
								errorMessage.append(" Quantity can not be 0 ");
								validaterow = false;
							}
						} catch (NumberFormatException e) {
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
									.toString())) {
						orderReturn.setType(entry.getCell(7).toString());
					} else {
						errorMessage.append(" Mark order Return Type");
						validaterow = false;
					}
					if (entry.getCell(8) != null
							&& StringUtils.isNotBlank(entry.getCell(8)
									.toString())) {
						orderReturn.setReturnCategory(entry.getCell(8)
								.toString());
					} else {
						errorMessage.append(" Mark order Return Fault Type");
						validaterow = false;
					}
					if (entry.getCell(9) != null
							&& StringUtils.isNotBlank(entry.getCell(9)
									.toString())) {
						orderReturn.setCancelType(entry.getCell(9).toString());
					}
				} else {
					validaterow = false;
					errorMessage
							.append("Order Id and Suborder Id values are null");
				}
				if (validaterow)
					orderService.addReturnOrder(order.getChannelOrderID(),
							orderReturn, sellerId);
				else {
					order.setOrderReturnOrRTO(orderReturn);
					returnlist.put(errorMessage.toString(), order);
				}
			}
			Set<String> errorSet = returnlist.keySet();
			downloadUploadReportXLS(offices, "OrderReturnReport", 9, errorSet,
					path, sellerId, uploadReport);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed!", e);
			addErrorUploadReport("OrderReturnReport", sellerId, uploadReport);
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
				log.debug(entry.getCell(1).toString());
				log.debug(entry.getCell(2).toString());
				log.debug(entry.getCell(3).toString());
				log.debug(entry.getCell(4).toString());
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
			downloadUploadReportXLS(offices, "DebitNoteSheet", 9, errorSet,
					path, sellerId, uploadReport);
		} catch (Exception e) {
			log.error("Failed!", e);
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

		try {

			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = offices.getSheetAt(0);
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
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
							errorMessage.append(" PO or GatePass not found");
							validaterow = false;
						} else {
							popabean.setGatePassId(order.getChannelOrderID());
						}
					} else {
						popabean.setPoOrderId(order.getChannelOrderID());
					}
				}

				if (entry.getCell(2) != null
						&& StringUtils.isNotBlank(entry.getCell(2).toString())) {

					if (HSSFDateUtil.isCellDateFormatted(entry.getCell(2))) {
						popabean.setPaymentDate(entry.getCell(2)
								.getDateCellValue());
					} else {
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
						errorMessage
								.append(" Positive Amount should be a number ");
						validaterow = false;
					}
				} /*else {
					if (popabean.getPoOrderId() != null) {
						errorMessage.append(" Positive Amount is null ");
						validaterow = false;
					}
				}*/

				if (entry.getCell(4) != null
						&& StringUtils.isNotBlank(entry.getCell(4).toString())) {
					try {
						popabean.setNegativeAmount(Double.parseDouble(entry
								.getCell(4).toString()));
						totalnegative = totalnegative
								+ Math.abs(Double.parseDouble(entry.getCell(4)
										.toString()));
					} catch (NumberFormatException e) {
						errorMessage
								.append(" Negative Amount should be a number ");
						validaterow = false;
					}
				} /*else {
					if (popabean.getGatePassId() != null) {
						errorMessage.append(" Negative Amount is null ");
						validaterow = false;
					}
				}*/

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
					poOrder.setPaymentUpload(paymentUpload);
					paymentUpload.getOrders().add(poOrder);
					generatePaymentUpload = true;
				}
			}

			if (generatePaymentUpload) {
				log.debug(" Total Positive Amount : " + totalpositive);
				log.debug(" Total Negative Amount : " + totalnegative);
				paymentUpload.setTotalpositivevalue(totalpositive);
				paymentUpload.setTotalnegativevalue(totalnegative);
				paymentUpload.setNetRecievedAmount(totalpositive - totalnegative);
				paymentUpload.setUploadDesc("PAYU" + sellerId + ""
						+ new Date().getTime());
				paymentUpload.setUploadStatus("Success");
				paymentUploadService.addPaymentUpload(paymentUpload, sellerId);
			}

			Set<String> errorSet = returnMap.keySet();
			downloadUploadReportXLS(offices, "POPaymentSheet", 6, errorSet,
					path, sellerId, uploadReport);
		} catch (Exception e) {
			log.error("Failed!", e);
			e.printStackTrace();
			addErrorUploadReport("POPaymentSheet", sellerId, uploadReport);
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
		try {
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = offices.getSheetAt(0);
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			log.info(noOfEntries.toString());
			log.debug("After getting no of rows" + noOfEntries);
			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				log.debug(entry.getCell(1).toString());
				log.debug(entry.getCell(2).toString());
				errorMessage = new StringBuffer("Row :" + (rowIndex - 2) + ":");
				if (entry.getCell(0) != null
						&& StringUtils.isNotBlank(entry.getCell(0).toString())) {
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
							errorMessage.append(" Amount should be numeric ");
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
					}
					if (entry.getCell(6) != null
							&& entry.getCell(6).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
						if (HSSFDateUtil.isCellDateFormatted(entry.getCell(6))) {
							expensebean.setExpenseDate(entry.getCell(6)
									.getDateCellValue());
						} else {
							errorMessage
									.append(" Expense Date formate is wrong ,enter mm/dd/yyyy,");
							validaterow = false;
						}
					} else {
						errorMessage.append(" Expense Date is null;");
						validaterow = false;
					}
				}
				log.debug("Validaterow : " + validaterow + "  error message: "
						+ errorMessage);
				if (validaterow)
					expenseService.addExpense(
							ConverterClass.prepareExpenseModel(expensebean),
							sellerId);
				else {
					returnlist.put(errorMessage.toString(), expensebean);
				}
			}
			Set<String> errorSet = returnlist.keySet();
			downloadUploadReportXLS(offices, "ExpenseSheet", 7, errorSet, path,
					sellerId, uploadReport);
		} catch (Exception e) {
			log.error("Failed!", e);
			e.printStackTrace();
			addErrorUploadReport("ExpenseSheet", sellerId, uploadReport);
			throw new MultipartException("Constraints Violated");
		}
		log.info("$$$ saveExpenseDetails ends : SaveContents $$$");
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
			String worksheetName, int colNumber, Set<String> errorSet,
			String path, int sellerId, UploadReport uploadReport)
			throws ClassNotFoundException, CustomException {

		log.info("$$$ downloadUploadReportXLS starts : SaveContents $$$");
		HSSFSheet worksheet = workbook.getSheetAt(0);
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

				if (errorMessage.length() > 5) {
					isError = true;
					row = worksheet.getRow(errorRow + 2);
					cell = row.createCell(colNumber);
					cell.setCellValue(errorMessage);
					cell.setCellStyle(errorCellStyle);
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

			String filePath = uploadFilePath + File.separator + worksheetName
					+ "UploadStatus" + new Date().getTime() + ".xls";
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
			log.error("Failed!", e);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("Failed!", e);
		}
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
			log.error("Failed!", e);
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

		try {

			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = offices.getSheetAt(0);
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			log.info(noOfEntries.toString());
			log.debug("After getting no of rows" + noOfEntries);
			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				errorMessage = new StringBuffer("Row :" + (rowIndex - 2) + ":");
				// orderReturn = new OrderRTOorReturn();
				gatepass = new GatePass();

				if (entry.getCell(0) != null
						&& StringUtils.isNotBlank(entry.getCell(0).toString())) {
					gatepass.setGatepassId(entry.getCell(0).toString());
					if (gpId != null
							&& !gpId.equalsIgnoreCase(gatepass.getGatepassId())
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
						&& StringUtils.isNotBlank(entry.getCell(1).toString())) {
					gatepass.setPoID(entry.getCell(1).toString());
				}

				if (entry.getCell(2) != null
						&& StringUtils.isNotBlank(entry.getCell(2).toString())) {
					gatepass.setInvoiceID(entry.getCell(2).toString());
				}

				if (entry.getCell(3) != null
						&& StringUtils.isNotBlank(entry.getCell(3).toString())) {
					gatepass.setChannelSkuRef(entry.getCell(3).toString());
				} else {
					errorMessage.append(" Vendor SKU is null ");
					validaterow = false;
				}

				if (entry.getCell(4) != null
						&& StringUtils.isNotBlank(entry.getCell(4).toString())) {
					gatepass.setQuantity((int) Float.parseFloat(entry
							.getCell(4).toString()));
				} else {
					errorMessage.append(" Quantity is null ");
					validaterow = false;
				}

				if (entry.getCell(5) != null
						&& StringUtils.isNotBlank(entry.getCell(5).toString())) {
					gatepass.setReturnRate(Double.parseDouble(entry.getCell(5)
							.toString()));
				} else {
					errorMessage.append(" Return Rate is null ");
					validaterow = false;
				}

				if (entry.getCell(6) != null
						&& StringUtils.isNotBlank(entry.getCell(6).toString())) {
					gatepass.setTaxAmt(Double.parseDouble(entry.getCell(6)
							.toString()));
				} else {
					errorMessage.append(" Tax Amount is null ");
					validaterow = false;
				}

				if (entry.getCell(7) != null
						&& StringUtils.isNotBlank(entry.getCell(7).toString())) {
					gatepass.setReturnCharges(Double.parseDouble(entry.getCell(
							7).toString()));
				} else {
					errorMessage.append(" Return Charges is null ");
					validaterow = false;
				}

				if (entry.getCell(8) != null
						&& StringUtils.isNotBlank(entry.getCell(8).toString())) {
					gatepass.setTotalReturnCharges(entry.getCell(8)
							.getNumericCellValue());
				} else {
					errorMessage.append(" Total Return Charges is null ");
					validaterow = false;
				}

				if (entry.getCell(9) != null
						&& StringUtils.isNotBlank(entry.getCell(9).toString())) {
					gatepass.setSellerNotes(entry.getCell(9).toString());
				} else {
					errorMessage.append(" Seller Note is null ");
					validaterow = false;
				}

				if (entry.getCell(10) != null
						&& StringUtils.isNotBlank(entry.getCell(10).toString())) {
					gatepass.setPcName(entry.getCell(10).toString());
					productConfig = productService.getProductConfig(
							gatepass.getChannelSkuRef(), gatepass.getPcName(),
							sellerId);
					if (productConfig == null) {
						errorMessage.append(" Product SKU does not exist ");
						validaterow = false;
					}
				} else {
					errorMessage.append(" Channel is null ");
					validaterow = false;
				}

				if (entry.getCell(11) != null
						&& StringUtils.isNotBlank(entry.getCell(11).toString())) {
					gatepass.setReturnReason(entry.getCell(11).toString());
				} else {
					errorMessage.append(" Return Reason is null ");
					validaterow = false;
				}

				if (entry.getCell(12) != null
						&& StringUtils.isNotBlank(entry.getCell(12).toString())) {
					if (HSSFDateUtil.isCellDateFormatted(entry.getCell(12))) {
						gatepass.setReturnDate(entry.getCell(12)
								.getDateCellValue());

					} else {
						errorMessage.append(" Return Date format is wrong ");
						validaterow = false;
					}
				} else {
					errorMessage.append(" Return Date is null ");
					validaterow = false;
				}

				if (validaterow) {
					gatepasslist.add(orderService.addGatePass(productConfig,
							gatepass, sellerId));
				} else {
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
			downloadUploadReportXLS(offices, "GatePassReport", 13, errorSet,
					path, sellerId, uploadReport);
		} catch (Exception e) {
			log.error("Failed!", e);
			e.printStackTrace();
			addErrorUploadReport("GatePassReport", sellerId, uploadReport);
			throw new MultipartException("Constraints Violated");
		}
		log.info("$$$ saveGatePassDetails ends : SaveContents $$$");
		return returnlist;
	}
}
