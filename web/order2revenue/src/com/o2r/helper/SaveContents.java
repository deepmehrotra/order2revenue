package com.o2r.helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	private static final Logger logger = LoggerFactory
			.getLogger(SaveContents.class);
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

	private final SimpleDateFormat formatter = new SimpleDateFormat(
			"mm/dd/yyyy");

	private static final String UPLOAD_DIR = "UploadReport";

	public Map<String, OrderBean> saveOrderContents(MultipartFile file,
			int sellerId, String path) throws IOException {
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
			System.out.println("Inside save content -->");
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = offices.getSheetAt(0);
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			logger.info(noOfEntries.toString());
			System.out.println("After getting no of rows" + noOfEntries);
			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				validaterow = true;
				entry = worksheet.getRow(rowIndex);
				errorMessage = new StringBuffer("Row : " + (rowIndex - 2));
				System.out.println(" Row index : " + (rowIndex - 2));
				System.out.println(entry.getCell(0).toString());
				System.out.println(entry.getCell(1).getDateCellValue());
				System.out.println(entry.getCell(2).toString());
				// System.out.println(entry.getCell(3).toString());
				// System.out.println(entry.getCell(4).toString());
				order = new OrderBean();
				customerBean = new CustomerBean();
				otb = new OrderTaxBean();
				if (entry.getCell(0) != null
						&& entry.getCell(0).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					entry.getCell(0).setCellType(HSSFCell.CELL_TYPE_STRING);
					System.out.println(" Getting string value form : "
							+ entry.getCell(0));
					List<Order> onj = orderService.findOrders("channelOrderID",
							entry.getCell(0).toString(), sellerId, false);
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
						order.setProductSkuCode(entry.getCell(2).toString());
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
					try {
						order.setShippingCharges(Double.parseDouble(entry
								.getCell(13).toString()));
					} catch (NumberFormatException e) {
						errorMessage
								.append(" Shipping Charges should be number ");
						validaterow = false;
					}
				} else {
					errorMessage.append(" Shipping Charges is null ");
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
				/*
				 * if (entry.getCell(15) != null &&
				 * entry.getCell(15).getCellType() != HSSFCell.CELL_TYPE_BLANK)
				 * { if (HSSFDateUtil.isCellDateFormatted(entry.getCell(15))) {
				 * order.setDeliveryDate(entry.getCell(15) .getDateCellValue());
				 * } else { errorMessage
				 * .append(" Delivery Date formate is wrong ,enter mm/dd/yyyy,"
				 * ); validaterow = false; } } else {
				 * errorMessage.append(" Delivery Date is null "); validaterow =
				 * false; }
				 */
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
				/*
				 * if (partner != null && partner.getNrnReturnConfig() != null
				 * && !partner.getNrnReturnConfig().isNrCalculator()) {
				 */
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
					System.out.println(" Email from sheet ************ "
							+ rowIndex + ":" + entry.getCell(17).toString());
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
					System.out.println(" getting zipcode ");
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
						&& entry.getCell(22).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					TaxCategory taxcat = taxDetailService.getTaxCategory(entry
							.getCell(22).toString(), sellerId);
					if (taxcat != null)
						otb.setTaxCategtory(entry.getCell(22).toString());
					else {
						otb.setTaxCategtory(entry.getCell(22).toString());
						errorMessage.append("Tax Category does not exist ");
						validaterow = false;
					}
				} else {
					errorMessage.append("Tax Category is null ");
					validaterow = false;
				}
				if (entry.getCell(23) != null
						&& StringUtils.isNotBlank(entry.getCell(23).toString())) {
					order.setSellerNote(entry.getCell(23).toString());

				}
				System.out
						.println("Sheet values :1 :" + entry.getCell(1)
								+ " 2 :" + entry.getCell(2) + " 3 :"
								+ entry.getCell(3));
				// Pre save to generate id for use in hierarchy
				if (validaterow) {
					order.setCustomer(customerBean);
					order.setOrderTax(otb);
					orderService.addOrder(ConverterClass.prepareModel(order),
							sellerId);
				} else {
					order.setCustomer(customerBean);
					order.setOrderTax(otb);
					System.out.println(" Error while saving order : "
							+ order.getChannelOrderID() + " errorMessage : "
							+ errorMessage);
					returnOrderMap.put(errorMessage.toString(), order);
				}
			}
			Set<String> errorSet = returnOrderMap.keySet();
			downloadUploadReportXLS(offices, "Order Report", 24, errorSet,
					path, sellerId);

		} catch (Exception e) {
			System.out.println("Inside save contents exception :"
					+ e.getLocalizedMessage());
			e.printStackTrace();
			// throw new MultipartException("Constraints Violated");
		}
		return returnOrderMap;
	}

	public Map<String, OrderBean> saveOrderPOContents(MultipartFile file,
			int sellerId, String path) throws IOException {
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
			System.out.println("Inside save content -->");
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = offices.getSheetAt(0);
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			logger.info(noOfEntries.toString());
			System.out.println("After getting no of rows" + noOfEntries);
			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				entry = worksheet.getRow(rowIndex);
				errorMessage = new StringBuffer("");

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
							&& !poId.equalsIgnoreCase(order.getSubOrderID())) {
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
						errorMessage.append(" Product SKU does not exist ");
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
						order.setOrderSP(entry.getCell(6).getNumericCellValue());

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
							Order consolidatedOrder = orderService.generateConsolidatedOrder(orderlist,
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
					} else {
						errorMessage
								.append(" Order Recieved Date format is wrong ");
						validaterow = false;
					}
				} else {
					errorMessage.append(" Shipped Date format is null ");
					validaterow = false;
				}

				if (entry.getCell(10) != null
						&& StringUtils.isNotBlank(entry.getCell(10).toString())) {
					order.setSellerNote(entry.getCell(10).toString());
				}

				System.out
						.println("Sheet values :1 :" + entry.getCell(1)
								+ " 2 :" + entry.getCell(2) + " 3 :"
								+ entry.getCell(3));
				// Pre save to generate id for use in hierarchy
				if (validaterow) {
					Order savedOrder = orderService.addPO(
							ConverterClass.prepareModel(order), sellerId);
					orderlist.add(savedOrder);
				} else {
					returnOrderMap.put(errorMessage.toString(), order);
				}
			}

			Order consolidatedOrder = orderService
					.generateConsolidatedOrder(orderlist, sellerId);
			orderService.updatePOOrders(orderlist,
					consolidatedOrder);
			orderlist.clear();
			
			Set<String> errorSet = returnOrderMap.keySet();
			downloadUploadReportXLS(offices, "OrderPOSheet", 11, errorSet,
					path, sellerId);

		} catch (Exception e) {
			System.out.println("Inside save contents exception :"
					+ e.getLocalizedMessage());
			e.printStackTrace();
			throw new MultipartException("Constraints Violated");
		}
		return returnOrderMap;
	}

	public Map<String, ProductBean> saveProductContents(MultipartFile file,
			int sellerId, String path) throws IOException {
		boolean validaterow = true;
		Map<String, ProductBean> returnProductMap = new LinkedHashMap<>();
		StringBuffer errorMessage = null;
		try {
			System.out.println("Inside save content -->");
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			// HSSFSheet worksheet = offices.getSheet("OrderReport");
			HSSFSheet worksheet = offices.getSheetAt(0);
			HSSFRow entry;
			Integer noOfEntries = 1;
			// sellerId=4;
			// getLastRowNum and getPhysicalNumberOfRows showing false values
			// sometimes.
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			logger.info(noOfEntries.toString());
			System.out.println("After getting no of rows" + noOfEntries);
			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				errorMessage = new StringBuffer("Row :" + (rowIndex - 2));
				System.out.println("Product 1" + entry.getCell(1).toString());
				System.out.println("Product  2" + entry.getCell(2).toString());
				System.out.println(entry.getCell(3).toString());
				System.out.println(entry.getCell(4).toString());
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
					path, sellerId);
		} catch (Exception e) {
			System.out.println("Inside save contents exception :"
					+ e.getLocalizedMessage());
			e.printStackTrace();
			throw new MultipartException("Constraints Violated");
		}

		return returnProductMap;
	}

	// My coding Product Config *********

	public Map<String, ProductConfigBean> saveProductConfigContents(
			MultipartFile file, int sellerId, String path) throws IOException {
		boolean validaterow = true;
		Map<String, ProductConfigBean> returnProductConfigMap = new LinkedHashMap<>();
		StringBuffer errorMessage = null;
		try {
			System.out.println("Inside saveProductConfigContent -->");
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			// HSSFSheet worksheet = offices.getSheet("OrderReport");
			HSSFSheet worksheet = offices.getSheetAt(0);
			HSSFRow entry;
			Integer noOfEntries = 1;
			// sellerId=4;
			// getLastRowNum and getPhysicalNumberOfRows showing false values
			// sometimes.
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			logger.info(noOfEntries.toString());
			System.out.println("After getting no of rows" + noOfEntries);
			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				errorMessage = new StringBuffer("Row :" + (rowIndex - 2));
				System.out.println("Product 1" + entry.getCell(1).toString());
				System.out.println("Product  2" + entry.getCell(2).toString());
				System.out.println(entry.getCell(3).toString());
				System.out.println(entry.getCell(4).toString());
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
					productConfig.setChannelName(entry.getCell(1).toString());
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

				// product.setVolume(product.getHeight() * product.getLength() *
				// product.getBreadth());
				// product.setVolWeight(product.getVolume() / 5);
				if (validaterow) {
					productService.addProductConfig(productConfig, sellerId);
				} else {
					returnProductConfigMap.put(errorMessage.toString(),
							ConverterClass
									.prepareProductConfigBean(productConfig));
				}
				System.out
						.println("Sheet values :1 :" + entry.getCell(1)
								+ " 2 :" + entry.getCell(2) + " 3 :"
								+ entry.getCell(3));
				// Pre save to generate id for use in hierarchy
			}
			Set<String> errorSet = returnProductConfigMap.keySet();
			downloadUploadReportXLS(offices, "ProductConfigReport", 11,
					errorSet, path, sellerId);
		} catch (Exception e) {
			System.out.println("Inside save contents exception :"
					+ e.getLocalizedMessage());
			e.printStackTrace();
			throw new MultipartException("Constraints Violated");
		}

		return returnProductConfigMap;
	}

	// My Coding Product Config Ends ********

	public Map<String, OrderBean> savePaymentContents(MultipartFile file,
			int sellerId, String path) throws IOException {
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
		// sellerId=4;
		try {
			System.out.println("Inside save content -->");
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			// HSSFSheet worksheet = offices.getSheet("OrderReport");
			HSSFSheet worksheet = offices.getSheetAt(0);
			// getLastRowNum and getPhysicalNumberOfRows showing false values
			// sometimes.
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			logger.info(noOfEntries.toString());
			System.out.println("After getting no of rows" + noOfEntries);
			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				System.out.println("Payment 1" + entry.getCell(1).toString());
				System.out.println("Payment  2" + entry.getCell(2).toString());
				/*
				 * System.out.println(entry.getCell(3).toString());
				 * System.out.println(entry.getCell(4).toString());
				 */
				// Product product=new Product();
				OrderPayment payment = new OrderPayment();
				errorMessage = new StringBuffer("Row :" + (rowIndex - 2));
				System.out.println(" channelOrderId " + channelOrderId);
				if (entry.getCell(0) != null
						&& entry.getCell(0).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
					List<Order> onj = orderService.findOrders("channelOrderID",
							entry.getCell(0).toString(), sellerId, false);
					if (onj != null && onj.size() != 0) {
						channelOrderId = entry.getCell(0).toString();
					} else {
						channelOrderId = entry.getCell(0).toString();
						errorMessage
								.append(" Channel OrderId is already present ");
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
						skucode = entry.getCell(2).toString();
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
						System.out.println(" ******toatal psitive :"
								+ totalpositive);

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
					payment.setDateofPayment(new Date(entry.getCell(5)
							.toString()));
					try {
						Date dateobj = formatter.parse(entry.getCell(5)
								.toString());
						payment.setDateofPayment(dateobj);
					} catch (ParseException e) {
						errorMessage
								.append(" Payment Date format is wrong ,enter mm/dd/yyyy ");
						validaterow = false;
					}
				} else {
					errorMessage.append(" Payment Date is null ");
					validaterow = false;
				}
				System.out
						.println("Sheet values :1 :" + entry.getCell(1)
								+ " 2 :" + entry.getCell(2) + " 3 :"
								+ entry.getCell(3));
				order = orderService.addOrderPayment(skucode, channelOrderId,
						payment, sellerId);
				if (validaterow) {
					orderService.addOrderPayment(skucode, channelOrderId,
							payment, sellerId);
				} else {
					returnPaymentMap.put(errorMessage.toString(),
							ConverterClass.prepareOrderBean(order));
				}
				if (order != null) {
					System.out.println(order);
					order.setPaymentUpload(paymentUpload);
					paymentUpload.getOrders().add(order);
				}
			}
			System.out.println(" Total Positive Amount : " + totalpositive);
			System.out.println(" Total Negative Amount : " + totalnegative);
			paymentUpload.setTotalpositivevalue(totalpositive);
			paymentUpload.setTotalnegativevalue(totalnegative);
			paymentUpload.setNetRecievedAmount(totalpositive - totalnegative);
			paymentUpload.setUploadDesc("PAYU" + sellerId + ""
					+ todaydat.getTime());
			paymentUpload.setUploadStatus("Success");
			paymentUploadService.addPaymentUpload(paymentUpload, sellerId);

			Set<String> errorSet = returnPaymentMap.keySet();
			downloadUploadReportXLS(offices, "PaymentReport", 6, errorSet,
					path, sellerId);
		} catch (Exception e) {
			System.out.println("Inside save contents exception :"
					+ e.getLocalizedMessage());
			e.printStackTrace();
			throw new MultipartException("Constraints Violated");
		}
		return returnPaymentMap;
	}

	public Map<String, String> saveInventoryDetails(MultipartFile file,
			int sellerId, String path) throws IOException {
		Map<String, String> returnInventoryMap = new LinkedHashMap<>();
		StringBuffer errorMessage = null;
		boolean validaterow = true;
		String SkUCode = null;
		int currentInventory = 0;
		int quantoAdd = 0;
		int quantoSub = 0;
		try {
			System.out.println("Inside save inventory data -->");
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = offices.getSheetAt(0);
			HSSFRow entry;
			Integer noOfEntries = 1;
			// sellerId=4;
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			logger.info(noOfEntries.toString());
			System.out.println("After getting no of rows" + noOfEntries);
			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				errorMessage = new StringBuffer("Row :" + (rowIndex - 2));
				validaterow = true;
				entry = worksheet.getRow(rowIndex);
				System.out.println("Product 1" + entry.getCell(1).toString());
				System.out.println("Product  2" + entry.getCell(2).toString());
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
					System.out.println("Sheet values :1 :" + entry.getCell(1)
							+ " 2 :" + entry.getCell(2) + " 3 :"
							+ entry.getCell(3));
					// Pre save to generate id for use in hierarchy
				} else {
					errorMessage.append(" SKU can not be blank ");
					validaterow = false;
				}
				if (validaterow) {
					productService.updateInventory(SkUCode, currentInventory,
							quantoAdd, quantoSub, true, sellerId);
				} else {
					returnInventoryMap.put(errorMessage.toString(), SkUCode);
				}
			}
			Set<String> errorSet = returnInventoryMap.keySet();
			downloadUploadReportXLS(offices, "InventoryReport", 5, errorSet,
					path, sellerId);
		} catch (Exception e) {
			System.out.println("Inside save contents exception :"
					+ e.getLocalizedMessage());
			e.printStackTrace();
			throw new MultipartException("Constraints Violated");
		}
		return returnInventoryMap;
	}

	public Map<String, Order> saveOrderReturnDetails(MultipartFile file,
			int sellerId, String path) throws IOException {
		HSSFRow entry;
		Integer noOfEntries = 1;
		// sellerId=4;
		Order order = new Order();
		StringBuffer errorMessage = null;
		boolean validaterow = true;
		int returnId = 0;
		OrderRTOorReturn orderReturn = null;
		Map<String, Order> returnlist = new LinkedHashMap<>();
		List<Order> orderlist = null;
		try {
			System.out.println("Inside save order retrun data data -->");
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = offices.getSheetAt(0);
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			logger.info(noOfEntries.toString());
			System.out.println("After getting no of rows" + noOfEntries);
			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				errorMessage = new StringBuffer("Row :" + (rowIndex - 2));
				orderReturn = new OrderRTOorReturn();
				System.out.println(entry.getCell(0));
				System.out.println(entry.getCell(1));
				System.out.println(entry.getCell(2));
				System.out.println(entry.getCell(3));
				System.out.println(entry.getCell(5));
				String id = null;
				if (entry.getCell(0) != null
						&& StringUtils.isNotBlank(entry.getCell(0).toString())) {
					id = entry.getCell(0).toString();
					orderlist = orderService.findOrders("channelOrderID", entry
							.getCell(0).toString(), sellerId, false);
					if (orderlist != null && orderlist.size() != 0) {

						System.out.println(" Found match for channel order id");
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
							.getCell(1).toString(), sellerId, false);
					if (orderlist != null && orderlist.size() != 0) {
						System.out.println(" Found match for sub order id");
						order.setChannelOrderID(orderlist.get(0)
								.getChannelOrderID());
					} else {
						validaterow = false;
						errorMessage.append("Sub Order Id doesnt exist");
					}
				}
				if (id != null) {
					System.out.println(" Order list in return : " + orderlist);
					if (orderlist != null && orderlist.size() != 0) {
						System.out.println(" Order list is not null ");
						returnId = orderlist.get(0).getOrderReturnOrRTO()
								.getReturnId();
						System.out.println();
					}
					// orderReturn.setReturnId(returnId);
					// order.setOrderId(orderlist.get(0).getOrderId());
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
					path, sellerId);
		} catch (Exception e) {
			System.out.println("Inside save contents exception :"
					+ e.getLocalizedMessage());
			e.printStackTrace();
			throw new MultipartException("Constraints Violated");
		}
		return returnlist;
	}

	public Map<String, DebitNoteBean> saveDebitNoteDetails(MultipartFile file,
			int sellerId, String path) throws IOException {
		HSSFRow entry;
		Integer noOfEntries = 1;
		// sellerId=4;
		String errorMessage = "Default error";
		boolean validaterow = true;
		// int returnId =0;
		Map<String, DebitNoteBean> returnlist = new LinkedHashMap<>();
		try {
			System.out.println("Inside save debit note data -->");
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = offices.getSheetAt(0);
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			logger.info(noOfEntries.toString());
			System.out.println("After getting no of rows" + noOfEntries);
			DebitNoteBean dnBean = null;
			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				entry = worksheet.getRow(rowIndex);
				System.out.println(entry.getCell(1).toString());
				System.out.println(entry.getCell(2).toString());
				System.out.println(entry.getCell(3).toString());
				System.out.println(entry.getCell(4).toString());
				if (entry.getCell(0) != null
						&& StringUtils.isNotBlank(entry.getCell(0).toString())) {
					dnBean = new DebitNoteBean();
					dnBean.setPOOrderId(entry.getCell(0).toString());
					if (entry.getCell(1) != null
							&& StringUtils.isNotBlank(entry.getCell(1)
									.toString())) {
						dnBean.setGatePassId(entry.getCell(1).toString());
					} else {
						errorMessage = "Error at row " + rowIndex
								+ " , Gate Pass Id is null";
						System.out.println(" *** +" + errorMessage);
						validaterow = false;
					}
					if (entry.getCell(2) != null
							&& StringUtils.isNotBlank(entry.getCell(2)
									.toString())) {
						dnBean.setSKU(entry.getCell(2).toString());
					} else {
						errorMessage = "Error at row " + rowIndex
								+ " , SKU is null";
						System.out.println(" *** +" + errorMessage);
						validaterow = false;
					}
					if (entry.getCell(3) != null
							&& StringUtils.isNotBlank(entry.getCell(3)
									.toString())) {
						dnBean.setPcName(entry.getCell(3).toString());
					} else {
						errorMessage = "Error at row " + rowIndex
								+ " , Partner is null";
						System.out.println(" *** +" + errorMessage);
						validaterow = false;
					}
					if (entry.getCell(4) != null
							&& StringUtils.isNotBlank(entry.getCell(4)
									.toString())) {
						dnBean.setInvoiceId(entry.getCell(4).toString());
					} else {
						errorMessage = "Error at row " + rowIndex
								+ " , Invoice is null";
						System.out.println(" *** +" + errorMessage);
						validaterow = false;
					}
					if (entry.getCell(5) != null
							&& StringUtils.isNotBlank(entry.getCell(5)
									.toString())) {
						dnBean.setAmount(Double.parseDouble(entry.getCell(5)
								.toString()));
					} else {
						errorMessage = "Error at row " + rowIndex
								+ " , Amount is null";
						validaterow = false;
					}
					if (entry.getCell(6) != null
							&& StringUtils.isNotBlank(entry.getCell(6)
									.toString())) {
						dnBean.setQuantity((int) Float.parseFloat(entry
								.getCell(6).toString()));
					} else {
						errorMessage = "Error at row " + rowIndex
								+ " , Quantity is null";
						System.out.println(" *** +" + errorMessage);
						validaterow = false;
					}
					if (entry.getCell(7) != null
							&& StringUtils.isNotBlank(entry.getCell(7)
									.toString())) {
						dnBean.setReturnDate(new Date(entry.getCell(7)
								.toString()));
					} else {
						errorMessage = "Error at row " + rowIndex
								+ " , Return date is null";
						System.out.println(" *** +" + errorMessage);
						validaterow = false;
					}
					if (entry.getCell(8) != null
							&& StringUtils.isNotBlank(entry.getCell(8)
									.toString())) {
						dnBean.setReturnReason(entry.getCell(8).toString());
					}
					System.out.println(" validaterow : " + validaterow
							+ " errorMessage : " + errorMessage);
					if (validaterow) {
						System.out.println(" Saving dnbBean : "
								+ dnBean.getInvoiceId());
						orderService.addDebitNote(dnBean, sellerId);
					} else {
						System.out.println("false validate row :"
								+ errorMessage);
						returnlist.put(errorMessage, dnBean);
					}
				}
			}
			Set<String> errorSet = returnlist.keySet();
			downloadUploadReportXLS(offices, "DebitNoteSheet", 9, errorSet,
					path, sellerId);
		} catch (Exception e) {
			System.out.println("Inside save debit note exception :"
					+ e.getLocalizedMessage());
			e.printStackTrace();
			throw new MultipartException("Constraints Violated");
		}
		return returnlist;
	}

	public Map<String, PoPaymentBean> savePoPaymetnDetails(MultipartFile file,
			int sellerId, String path) throws IOException {
		HSSFRow entry;
		Integer noOfEntries = 1;
		
		String errorMessage = null;
		boolean validaterow = true;
		PoPaymentBean popabean = null;
		Map<String, PoPaymentBean> returnlist = new LinkedHashMap<>();
		Map<String, PoPaymentBean> acceptPOlist = new HashMap<>();
		try {
			System.out.println("Inside save popayment note data -->");
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = offices.getSheetAt(0);
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			logger.info(noOfEntries.toString());
			System.out.println("After getting no of rows" + noOfEntries);
			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				entry = worksheet.getRow(rowIndex);
				if (entry.getCell(0) != null
						&& StringUtils.isNotBlank(entry.getCell(0).toString())) {
					popabean = new PoPaymentBean();
					popabean.setPoOrderId(entry.getCell(0).toString());
					if (entry.getCell(1) != null
							&& StringUtils.isNotBlank(entry.getCell(1)
									.toString())) {
						popabean.setInvoiceID(entry.getCell(1).toString());
					} else {
						errorMessage = "Error at row " + rowIndex
								+ " , invoice Id is null";
						validaterow = false;
					}
					if (entry.getCell(2) != null
							&& StringUtils.isNotBlank(entry.getCell(2)
									.toString())) {
						popabean.setPcName(entry.getCell(2).toString());
					} else {
						errorMessage = "Error at row " + rowIndex
								+ " , Partner is null";
						validaterow = false;
					}
					if (entry.getCell(3) != null
							&& StringUtils.isNotBlank(entry.getCell(3)
									.toString())) {
						popabean.setGatePassId(entry.getCell(3).toString());
					}
					if (entry.getCell(4) != null
							&& StringUtils.isNotBlank(entry.getCell(4)
									.toString())) {
						popabean.setPositiveAmount(Double.parseDouble(entry
								.getCell(4).toString()));
					}
					if (entry.getCell(5) != null
							&& StringUtils.isNotBlank(entry.getCell(5)
									.toString())) {
						popabean.setNegativeAmount(Double.parseDouble(entry
								.getCell(5).toString()));
					}
					if (entry.getCell(6) != null
							&& StringUtils.isNotBlank(entry.getCell(6)
									.toString())) {
						popabean.setPaymentDate(new Date(entry.getCell(6)
								.toString()));
					} else {
						errorMessage = "Error at row " + rowIndex
								+ " , date is null";
						validaterow = false;
					}
					if (entry.getCell(7) != null
							&& StringUtils.isNotBlank(entry.getCell(7)
									.toString())) {
						popabean.setQuantity((int) Float.parseFloat(entry
								.getCell(7).toString()));
					} else {
						errorMessage = "Error at row " + rowIndex
								+ " , Return date is null";
						validaterow = false;
					}
				}
				if (acceptPOlist != null
						&& acceptPOlist.containsKey(popabean.getInvoiceID())) {
					if ((int) popabean.getPositiveAmount() != 0) {
						acceptPOlist.get(popabean.getInvoiceID())
								.setPositiveAmount(
										(acceptPOlist.get(
												popabean.getInvoiceID())
												.getPositiveAmount() + popabean
												.getPositiveAmount()));
					} else {
						acceptPOlist.get(popabean.getInvoiceID())
								.setNegativeAmount(
										(acceptPOlist.get(
												popabean.getInvoiceID())
												.getNegativeAmount() + popabean
												.getNegativeAmount()));
					}
				} else {
					acceptPOlist.put(popabean.getInvoiceID(), popabean);
				}
			}
			for (Map.Entry<String, PoPaymentBean> mapentry : acceptPOlist
					.entrySet()) {
				orderService.addPOPayment(mapentry.getValue(), sellerId);
				System.out.println("Key = " + mapentry.getKey() + ", Value = "
						+ mapentry.getValue());
			}
			Set<String> errorSet = returnlist.keySet();
			downloadUploadReportXLS(offices, "POPaymentSheet", 8, errorSet,
					path, sellerId);
		} catch (Exception e) {
			System.out.println("Inside save debit note exception :"
					+ e.getLocalizedMessage());
			e.printStackTrace();
			throw new MultipartException("Constraints Violated");
		}
		return returnlist;
	}

	public Map<String, ExpenseBean> saveExpenseDetails(MultipartFile file,
			int sellerId, String path) throws IOException {
		HSSFRow entry;
		Integer noOfEntries = 1;
		// sellerId=4;
		StringBuffer errorMessage = null;
		boolean validaterow = true;
		ExpenseBean expensebean = null;
		Map<String, ExpenseBean> returnlist = new LinkedHashMap<>();
		try {
			System.out.println("Inside save expense datails note data -->");
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = offices.getSheetAt(0);
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			logger.info(noOfEntries.toString());
			System.out.println("After getting no of rows" + noOfEntries);
			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				System.out.println(entry.getCell(1).toString());
				System.out.println(entry.getCell(2).toString());
				errorMessage = new StringBuffer("Row :" + (rowIndex - 2));
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
				System.out.println("Validaterow : " + validaterow
						+ "  error message: " + errorMessage);
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
					sellerId);
		} catch (Exception e) {
			System.out.println("Inside save expense exception :"
					+ e.getLocalizedMessage());
			e.printStackTrace();
			throw new MultipartException("Constraints Violated");
		}
		return returnlist;
	}

	public boolean validateRowForNull(HSSFRow entry, int cellcount) {
		for (int i = 0; i < cellcount; i++) {
			if (entry.getCell(i) == null
					|| StringUtils.isBlank(entry.getCell(i).toString())) {
				return false;
			}
		}
		return true;
	}

	public void downloadUploadReportXLS(HSSFWorkbook workbook,
			String worksheetName, int colNumber, Set<String> errorSet,
			String path, int sellerId) throws ClassNotFoundException {

		HSSFSheet worksheet = workbook.getSheetAt(0);

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

		int rowIndex = 3;
		Row row = null;
		Cell cell = null;

		for (String key : errorSet) {
			row = worksheet.getRow(rowIndex);
			cell = row.createCell(colNumber);
			cell.setCellValue(key.substring(7));
			cell.setCellStyle(errorCellStyle);
			rowIndex++;
		}
		try {

			// constructs path of the directory to save uploaded file
			String uploadFilePath = path + File.separator + UPLOAD_DIR;
			System.out
					.println("***** uploadFilePath path  : " + uploadFilePath);
			// creates the save directory if it does not exists
			File fileSaveDir = new File(uploadFilePath);
			if (!fileSaveDir.exists()) {
				System.out.println(" Directory doesnnt exist");
				fileSaveDir.mkdirs();
			}
			FileOutputStream out = new FileOutputStream(new File(uploadFilePath
					+ File.separator + worksheetName + "UploadStatus"
					+ new Date().getTime() + ".xls"));
			workbook.write(out);
			out.close();
			System.out.println("Excel written successfully..");

			UploadReport uploadReport = new UploadReport();
			uploadReport.setFileType(worksheetName);
			uploadReport.setPath(uploadFilePath);
			// uploadReport.setStatus(status);

			// reportGeneratorService.addUploadReport(uploadReport, sellerId);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Map<String, GatePass> saveGatePassDetails(MultipartFile file,
			int sellerId, String path) throws IOException {
		HSSFRow entry;
		Integer noOfEntries = 1;

		GatePass gatepass;
		StringBuffer errorMessage = null;
		boolean validaterow = true;
		Map<String, GatePass> returnlist = new LinkedHashMap<>();
		
		String gpId = null;
		List<GatePass> gatepasslist = new ArrayList<GatePass>();
		
		try {
			System.out.println("Inside save gate pass data -->");
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = offices.getSheetAt(0);
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			logger.info(noOfEntries.toString());
			System.out.println("After getting no of rows" + noOfEntries);
			for (int rowIndex = 3; rowIndex < noOfEntries; rowIndex++) {
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				errorMessage = new StringBuffer("Row :" + (rowIndex - 2));
				// orderReturn = new OrderRTOorReturn();
				gatepass = new GatePass();

				if (entry.getCell(0) != null
						&& StringUtils.isNotBlank(entry.getCell(0).toString())) {
					gatepass.setGatepassId(entry.getCell(0).toString());
					if (gpId != null
							&& !gpId.equalsIgnoreCase(gatepass.getGatepassId())) {
						OrderRTOorReturn consolidatedReturn = orderService
								.generateConsolidatedReturn(gatepasslist, sellerId);
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
				} else {
					errorMessage.append(" PO ID is null ");
					validaterow = false;
				}

				if (entry.getCell(2) != null
						&& StringUtils.isNotBlank(entry.getCell(2).toString())) {
					gatepass.setInvoiceID(entry.getCell(2).toString());
				} else {
					errorMessage.append(" Invoice ID is null ");
					validaterow = false;
				}

				if (entry.getCell(3) != null
						&& StringUtils.isNotBlank(entry.getCell(3).toString())) {
					gatepass.setChannelSkuRef(entry.getCell(3).toString());
				} else {
					errorMessage.append(" Vendor SKU is null ");
					validaterow = false;
				}

				Order poOrder = orderService.findPOOrder(gatepass.getPoID(),
						gatepass.getInvoiceID(), gatepass.getChannelSkuRef(),
						sellerId);
				if (poOrder == null) {
					errorMessage.append(" PO Not received ");
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
					gatepass.setTotalReturnCharges(entry.getCell(8).getNumericCellValue());
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
					gatepasslist.add(orderService.addGatePass(poOrder, gatepass, sellerId));
				} else {
					returnlist.put(errorMessage.toString(), gatepass);
				}
			}
			
			OrderRTOorReturn consolidatedReturn = orderService
					.generateConsolidatedReturn(gatepasslist, sellerId);
			orderService.updateGatePasses(gatepasslist,
					consolidatedReturn);
			gatepasslist.clear();
			
			Set<String> errorSet = returnlist.keySet();
			downloadUploadReportXLS(offices, "GatePassReport", 13, errorSet,
					path, sellerId);
		} catch (Exception e) {
			System.out.println("Inside save contents exception :"
					+ e.getLocalizedMessage());
			e.printStackTrace();
			throw new MultipartException("Constraints Violated");
		}
		return returnlist;
	}
}
