package com.o2r.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.o2r.bean.DataConfig;
import com.o2r.bean.OrderBean;
import com.o2r.bean.PoPaymentDetailsBean;
import com.o2r.bean.UploadReportBean;
import com.o2r.helper.ConverterClass;
import com.o2r.helper.CustomException;
import com.o2r.helper.FileUploadForm;
import com.o2r.helper.HelperClass;
import com.o2r.helper.SaveContents;
import com.o2r.model.Events;
import com.o2r.model.GatePass;
import com.o2r.model.Order;
import com.o2r.model.Partner;
import com.o2r.model.Product;
import com.o2r.model.TaxCategory;
import com.o2r.model.UploadReport;
import com.o2r.service.DownloadService;
import com.o2r.service.EventsService;
import com.o2r.service.OrderService;
import com.o2r.service.PartnerService;
import com.o2r.service.ProductService;
import com.o2r.service.ReportGeneratorService;
import com.o2r.service.SellerService;
import com.o2r.service.TaxDetailService;

/**
 * @author Deep Mehrotra
 *
 */
@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;
	@Resource(name = "downloadService")
	private DownloadService downloadService;
	@Resource(name = "saveContents")
	private SaveContents saveContents;
	@Autowired
	private PartnerService partnerService;
	@Autowired
	private SellerService serviceService;
	@Autowired
	private ProductService productService;
	@Autowired
	private TaxDetailService taxDetailService;
	@Autowired
	private ReportGeneratorService reportGeneratorService;
	@Autowired
	private HelperClass helperClass;
	@Autowired
	EventsService eventsService;
	@Autowired
	DataConfig dataConfig;

	private static final String UPLOAD_DIR = "upload";

	static Logger log = Logger.getLogger(OrderController.class.getName());
	Gson gson = new Gson();

	/**
	 * Downloads the report as an Excel format.
	 * <p>
	 * Make sure this method doesn't return any model. Otherwise, you'll get an
	 * "IllegalStateException: getOutputStream() has already been called for this response"
	 * @throws IOException 
	 * 
	 */
	@RequestMapping(value = "/seller/download/xls", method = RequestMethod.GET)
	public void getXLS(HttpServletResponse response,
			@RequestParam(value = "sheetvalue") String sheetvalue,HttpServletRequest request)
			throws ClassNotFoundException, IOException {

		log.info("$$$ getXLS Starts : OrderController $$$");
		log.debug(" Downloading the sheet: " + sheetvalue);
			
			response.reset();
			int BUFF_SIZE = 1024;
			int byteRead = 0;
			byte[] buffer = new byte[BUFF_SIZE];
			File targetFile = new File(System.getProperty("catalina.base")+dataConfig.getXlsPath()+sheetvalue+".xls");
			FileInputStream inputStream = new FileInputStream(targetFile);
			response.setContentType("application/vnd.ms-excel");			
			response.setHeader("Content-Disposition", "attachment; filename="+targetFile.getName());
			response.setContentLength((int) targetFile.length());
			OutputStream outputStream = response.getOutputStream();			
			try {			    
			    while ((byteRead = inputStream.read()) != -1) {
			    	outputStream.write(buffer, 0, byteRead);			
			    }			    
			    outputStream.flush();
			} catch (Exception e) {			    
			    e.printStackTrace();
			    log.error("failed !", e);
			} finally {
				outputStream.close();
				inputStream.close();
			}		
		
		
		/*if (sheetvalue != null) {
			if (sheetvalue.equals("ordersummary")) {
				downloadService.downloadXLS(response);
			} else if (sheetvalue.equals("orderposummary")) {
				downloadService.downloadOrderPOXLS(response);
			} else if (sheetvalue.equals("paymentsummary")) {
				downloadService.downloadPaymentXLS(response);
			} else if (sheetvalue.equals("returnsummary")) {
				downloadService.downloadReturnXLS(response);
			} else if (sheetvalue.equals("gatepasssummary")) {
				downloadService.downloadGatePassXLS(response);
			} else if (sheetvalue.equals("productsummary")) {
				downloadService.downloadProductXLS(response);
			}else if (sheetvalue.equals("editproductsummary")) {
				downloadService.downloadEditProductXLS(response);
			}else if (sheetvalue.equals("productconfigsummary")) {
				downloadService.downloadProductConfigXLS(response);
			} else if (sheetvalue.equals("inventorysummary")) {
				downloadService.downloadInventoryXLS(response);
			} else if (sheetvalue.equals("debitNotesummary")) {
				downloadService.downloadDebitNoteXLS(response);
			} else if (sheetvalue.equals("popaymentsummary")) {
				downloadService.downloadPOPaymentXLS(response);
			} else if (sheetvalue.equals("expensesummary")) {
				downloadService.downloadExpensesXLS(response);
			}else if (sheetvalue.equals("skumappingsummary")) {
				downloadService.downloadSKUMappingXLS(response);
			}
		}*/
		log.info("$$$ getXLS Ends : OrderController $$$");
	}

	@RequestMapping(value = "/seller/downloadOrderDA", method = RequestMethod.GET)
	public ModelAndView displayDownloadForm(
			@RequestParam("value") String value,
			@ModelAttribute("uploadForm") FileUploadForm uploadForm, Model map) {

		log.info("$$$ displayDownloadForm Starts : OrderController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		log.debug("Inside download orders  viewpayments uploadId" + value);
		model.put("downloadValue", value);
		log.info("$$$ displayDownloadForm Ends : OrderController $$$");
		return new ModelAndView("dailyactivities/order_upload_form", model);
	}

	@RequestMapping(value = "/seller/uploadOrderDA", method = RequestMethod.GET)
	public ModelAndView displayUploadForm(@RequestParam("value") String value,
			@ModelAttribute("uploadForm") FileUploadForm uploadForm, Model map) {

		log.info("$$$ displayUploadForm Starts : OrderController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		System.out.println("Inside Payment orders  viewpayments uploadId"
				+ value);
		model.put("uploadValue", value);
		log.info("$$$ displayUploadForm Ends : OrderController $$$");
		return new ModelAndView("dailyactivities/order_upload_form", model);
	}

	@RequestMapping(value = "/user-login", method = RequestMethod.GET)
	public ModelAndView loginForm() {

		log.info("$$$ loginForm Starts : OrderController $$$");
		log.debug("catalina.base  " + System.getProperty("catalina.base"));

		try {
			org.springframework.core.io.Resource resource = new ClassPathResource(
					"database.properties");
			Properties props = PropertiesLoaderUtils.loadProperties(resource);
			log.debug("dialect in order controller : "
					+ props.getProperty("hibernate.dialect"));
		} catch (IOException e) {
			e.printStackTrace();
			log.error("Failed!", e);
		}
		log.info("$$$ loginForm Ends : OrderController $$$");
		return new ModelAndView("login_register");
	}

	@RequestMapping(value = "/error-login", method = RequestMethod.GET)
	public ModelAndView invalidLogin() {
		ModelAndView modelAndView = new ModelAndView("login_register");
		modelAndView.addObject("error", true);
		return modelAndView;
	}

	@RequestMapping(value = "/seller/saveSheet", method = RequestMethod.POST)
	@SuppressWarnings("rawtypes")
	public ModelAndView save(MultipartHttpServletRequest request,
			@ModelAttribute("uploadForm") FileUploadForm uploadForm, Model map) {

		log.info("$$$ save() Starts : OrderController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		double starttime = System.currentTimeMillis();
		log.debug(" **StartTime : " + starttime);
		//List<MultipartFile> files = request.getFiles("0");
		List<MultipartFile> files = uploadForm.getFiles();

		InputStream inputStream = null;
		OutputStream outputStream = null;
		List<String> fileNames = new ArrayList<String>();
		MultipartFile fileinput = files.get(0);
		UploadReport uploadReport = new UploadReport();
		uploadReport.setUploadDate(new Date());
		int sellerId;
		System.out.println(" got file");

		// gets absolute path of the web application
		String applicationPath = request.getServletContext().getRealPath("");

		if (null != files && files.size() > 0) {
			fileNames.add(files.get(0).getOriginalFilename());
			try {
				sellerId = helperClass.getSellerIdfromSession(request);
				log.debug(" Filename : " + files.get(0).getOriginalFilename());
				log.debug(" uploadForm.getSheetValue() : "
						+ uploadForm.getSheetValue());

				// ValidateUpload.validateOfficeData(files.get(0));

				Map orderProcessedMap = null;
				log.debug("fileinput " + fileinput.getName());
				switch (uploadForm.getSheetValue()) {
				case "ordersummary":
					orderProcessedMap = saveContents.saveOrderContents(
							files.get(0), sellerId, applicationPath,
							uploadReport);

					if (orderProcessedMap != null
							&& !orderProcessedMap.isEmpty())
						serviceService.updateProcessedOrdersCount(sellerId,
								orderProcessedMap.size());
					else
						System.out
								.println("No Orders processed, so not updating the totalProcessedOrder");
					model.put("orderMap", orderProcessedMap);
					model.put("mapType", "orderMap");
					break;
				case "orderPoSummary":
					orderProcessedMap = saveContents.saveOrderPOContents(
							files.get(0), sellerId, applicationPath,
							uploadReport);
					if (orderProcessedMap != null
							&& !orderProcessedMap.isEmpty())
						serviceService.updateProcessedOrdersCount(sellerId,
								orderProcessedMap.size());
					else
						log.debug("No Orders processed, so not updating the totalProcessedOrder");
					model.put("orderPoMap", orderProcessedMap);
					model.put("mapType", "orderPoMap");
					break;
				case "gatepassSummary":
					model.put("gatepassMap", saveContents.saveGatePassDetails(
							files.get(0), sellerId, applicationPath,
							uploadReport));
					model.put("mapType", "gatepassMap");
					break;
				case "paymentSummary":
					model.put("orderPaymentMap", saveContents
							.savePaymentContents(files.get(0), sellerId,
									applicationPath, uploadReport));
					model.put("mapType", "orderPaymentMap");
					break;
				case "returnSummary":
					model.put("orderReturnMap", saveContents
							.saveOrderReturnDetails(files.get(0), sellerId,
									applicationPath, uploadReport));
					model.put("mapType", "orderReturnMap");
					break;
				case "productSummary":
					model.put("productMap", saveContents.saveProductContents(
							files.get(0), sellerId, applicationPath,
							uploadReport));
					model.put("mapType", "productMap");
					break;					
				case "editProductSummary":
					model.put("editProductMap", saveContents.saveEditProductContents(
							files.get(0), sellerId, applicationPath,
							uploadReport));
					model.put("mapType", "editProductMap");
					break;
				case "productConfigSummary":
					model.put("productMap", saveContents
							.saveProductConfigContents(files.get(0), sellerId,
									applicationPath, uploadReport));
					model.put("mapType", "productMap");
					break;
				case "inventorySummary":
					model.put("inventoryMap", saveContents
							.saveInventoryDetails(files.get(0), sellerId,
									applicationPath, uploadReport));
					model.put("mapType", "inventoryMap");
					break;
				case "debitNoteSummary":
					saveContents.saveDebitNoteDetails(files.get(0), sellerId,
							applicationPath, uploadReport);
					model.put("mapType", "debitNoteSummary");
					break;
				case "poPaymentSummary":
					saveContents.savePoPaymentDetails(files.get(0), sellerId,
							applicationPath, uploadReport);
					model.put("mapType", "poPaymentSummary");
					break;
				case "expenseSummary":
					model.put("expensesMap", saveContents.saveExpenseDetails(
							files.get(0), sellerId, applicationPath,
							uploadReport));
					model.put("mapType", "expensesMap");
					break;
				case "skuMapping":
					model.put("skuMapping", saveContents.saveSKUMappingContents(
							files.get(0), sellerId, applicationPath,
							uploadReport));
					model.put("mapType", "skuMappingMap");
					break;

				}
				inputStream = files.get(0).getInputStream();
				String uploadFilePath = applicationPath + File.separator
						+ UPLOAD_DIR;
				log.debug("***** Application path  : " + applicationPath
						+ "  file xontent type : "
						+ files.get(0).getContentType());
				log.debug("***** uploadFilePath path  : " + uploadFilePath);
				File fileSaveDir = new File(uploadFilePath);
				if (!fileSaveDir.exists()) {
					log.debug(" Directory doesnnt exist");
					fileSaveDir.mkdirs();
				}
				outputStream = new FileOutputStream(fileSaveDir
						+ File.separator + files.get(0).getOriginalFilename());
				int read = 0;
				byte[] bytes = new byte[1024];

				while ((read = inputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}

				System.out.println("### Saved files succesfully");
				outputStream.close();
				inputStream.close();
				FileUtils.cleanDirectory(fileSaveDir);
				double endtime = System.currentTimeMillis();
				System.out.println(" **endtime : " + endtime);
				double lapsetime = (endtime - starttime) / 1000;

				// update uploadReport with time
				model.put("fileName", files.get(0).getOriginalFilename());
				model.put("timeTaken", lapsetime);

				uploadReport.setTimeTaken((float) lapsetime);
				uploadReport = reportGeneratorService.addUploadReport(
						uploadReport, sellerId);
				
				List<UploadReportBean> uploadReports = ConverterClass.prepareUploadReportListBean(
						reportGeneratorService.listUploadReport(helperClass.getSellerIdfromSession(request)));
				if (uploadReports != null && uploadReports.size() > 3) {
					uploadReports = uploadReports.subList(uploadReports.size() - 3, uploadReports.size());
				}
				model.put("uploadReportList", uploadReports);
			} catch (Exception e) {
				log.debug("Inside exception , filetype not accepted "
						+ e.getLocalizedMessage());
				e.printStackTrace();
				log.error("Failed!", e);
			}

		}
		log.info("$$$ save() Ends : OrderController $$$");
		// return new ModelAndView("dailyactivities/orderList", model);
		return new ModelAndView("redirect:/seller/dashboard.html");

	}

	// Methods for Ajax implementtation
	@RequestMapping(value = "/seller/searchOrder", method = RequestMethod.POST)
	public ModelAndView searchOrder(HttpServletRequest request,
			@ModelAttribute("command") OrderBean orderBean, BindingResult result) {

		log.info("$$$ searchOrder() Starts : OrderController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		List<OrderBean> orderList = new ArrayList<>();
		int sellerId;
		String channelOrderID = request.getParameter("channelOrderID");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String searchOrder = request.getParameter("searchOrder");
		System.out.println(channelOrderID + "**********" + searchOrder);
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			if (searchOrder != null && searchOrder.equals("channelOrderID")
					|| searchOrder.equals("invoiceID")
					|| searchOrder.equals("subOrderID")
					|| searchOrder.equals("pcName")
					|| searchOrder.equals("status") && channelOrderID != null) {
				orderList = ConverterClass.prepareListofBean(orderService
						.findOrders(searchOrder, channelOrderID, sellerId,
								true, true));
			} else if (searchOrder != null
					&& searchOrder.equals("customerName")
					&& channelOrderID != null) {
				orderList = ConverterClass.prepareListofBean(orderService
						.findOrdersbyCustomerDetails(searchOrder,
								channelOrderID, sellerId));
			} else if (searchOrder != null && startDate != null
					&& endDate != null) {
				orderList = ConverterClass.prepareListofBean(orderService
						.findOrdersbyDate("orderDate", new Date(startDate),
								new Date(endDate), sellerId, true));

			}
		} catch (CustomException ce) {
			log.error("searchOrder exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed!", e);
		}
		request.getSession().setAttribute("orderSearchObject", orderList);
		log.info("$$$ searchOrder() Ends : OrderController $$$");
		return new ModelAndView("redirect:/seller/orderList.html");

	}

	@RequestMapping(value = "/seller/poOrderDetails", method = RequestMethod.GET)
	public ModelAndView poOrderDetails(
			HttpServletRequest request,
			@ModelAttribute("command") PoPaymentDetailsBean poPaymentDetailsBean,
			@RequestParam("value") String value,
			BindingResult result) {

		log.info("$$$ poOrderDetails Starts : OrderController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		int sellerId;
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			
			String year = "";
			if ("".equals(value.trim()) || value.equals("0")) {
				Calendar c = Calendar.getInstance();
				c.setTime(new Date());
				year = String.valueOf(c.get(Calendar.YEAR));
			} else {
				year = value;
			}
		
			List<PoPaymentDetailsBean> poPaymentList = orderService
					.getPOPaymentDetails(sellerId, year);
			model.put("poPaymentListMonthly", poPaymentList);

		} catch (CustomException ce) {
			log.error("viewOrderDailyAct exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed!",e);
		}

		log.info("$$$ poOrderDetails Ends : OrderController $$$");
		return new ModelAndView("dailyactivities/poOrderDetails", model);
	}

	@RequestMapping(value = "/seller/orderList", method = RequestMethod.GET)
	public ModelAndView orderListDailyAct(HttpServletRequest request,
			@ModelAttribute("command") OrderBean orderBean, BindingResult result) {

		log.info("$$$ orderListDailyAct Starts : OrderController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		String savedOrder = request.getParameter("savedOrder");
		List<OrderBean> returnlist = new ArrayList<OrderBean>();
		List<OrderBean> poOrderlist = new ArrayList<OrderBean>();
		Object obj = request.getSession().getAttribute("orderSearchObject");
		int sellerId;
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			if (obj != null) {
				log.debug(" Getting list from session" + obj);
				model.put("orders", obj);
				request.getSession().removeAttribute("orderSearchObject");
			} else if (request.getParameter("status") != null) {
				String status = request.getParameter("status");
				if (status.equalsIgnoreCase("return")) {
					returnlist = ConverterClass.prepareListofBean(orderService
							.findOrders("status", "Return Recieved", sellerId,
									false, false));
					returnlist.addAll(ConverterClass
							.prepareListofBean(orderService.findOrders(
									"status", "Return Limit Crossed", sellerId,
									false, false)));
					model.put("orders", returnlist);

					poOrderlist = ConverterClass.prepareListofBean(orderService
							.findOrders("status", "Return Recieved", sellerId,
									true, false));
					model.put("poOrders", poOrderlist);

				} else if (status.equalsIgnoreCase("payment")) {
					returnlist = ConverterClass.prepareListofBean(orderService
							.findOrders("status", "Payment Recieved", sellerId,
									false, false));
					returnlist.addAll(ConverterClass
							.prepareListofBean(orderService.findOrders(
									"status", "Payment Deducted", sellerId,
									false, false)));
					model.put("orders", returnlist);

					poOrderlist = ConverterClass.prepareListofBean(orderService
							.findOrders("status", "Payment Recieved", sellerId,
									true, false));
					model.put("poOrders", poOrderlist);
				} else if (status.equalsIgnoreCase("actionable")) {
					returnlist = ConverterClass.prepareListofBean(orderService
							.findOrders("finalStatus", "Actionable", sellerId,
									false, false));
					model.put("orders", returnlist);

					poOrderlist = ConverterClass.prepareListofBean(orderService
							.findOrders("finalStatus", "Actionable", sellerId,
									true, false));
					model.put("poOrders", poOrderlist);
				}
			} else {
				int pageNo = request.getParameter("page") != null ? Integer
						.parseInt(request.getParameter("page")) : 0;
				model.put("orders", ConverterClass
						.prepareListofBean(orderService.listOrders(
								helperClass.getSellerIdfromSession(request),
								pageNo)));

				model.put("poOrders", ConverterClass
						.prepareListofBean(orderService.listPOOrders(
								helperClass.getSellerIdfromSession(request),
								pageNo)));
			}
		} catch (CustomException ce) {
			ce.printStackTrace();
			log.error("orderListDailyAct exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed!", e);
		}
		if (savedOrder != null)
			model.put("savedOrder", savedOrder);

		log.info("$$$ orderListDailyAct Ends : OrderController $$$");
		return new ModelAndView("dailyactivities/orderList", model);
	}

	@RequestMapping(value = "/seller/viewOrderDA", method = RequestMethod.GET)
	public ModelAndView viewOrderDailyAct(HttpServletRequest request,
			@ModelAttribute("command") OrderBean orderBean, BindingResult result) {

		log.info("$$$ viewOrderDailyAct Starts : OrderController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		int sellerId;
		Product product = null;
		Events event = null;
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			Order order = orderService.getOrder(orderBean.getOrderId(),
					sellerId);
			product = productService.getProduct(order.getProductSkuCode(),
					sellerId);
			log.debug(" Payment difference :"
					+ order.getOrderPayment().getPaymentDifference());
			event = eventsService.getEvent(order.getEventName(), sellerId);
			model.put("order", ConverterClass.prepareOrderBean(order));
			model.put("event", ConverterClass.prepareEventsBean(event));
			model.put("serviceTax", (order.getPccAmount()+order.getFixedfee()+order.getPartnerCommission()+order.getShippingCharges())*dataConfig.getServiceTax()/100);
			System.out.println((order.getPccAmount()+order.getFixedfee()+order.getPartnerCommission()+order.getShippingCharges())*dataConfig.getServiceTax()/100);
		} catch (CustomException ce) {
			log.error("viewOrderDailyAct exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed!", e);
		}
		if (product != null)
			model.put("productCost", product.getProductPrice());

		log.info("$$$ viewOrderDailyAct Ends : OrderController $$$");
		return new ModelAndView("dailyactivities/viewOrder", model);
	}

	@RequestMapping(value = "/seller/viewPOOrderDA", method = RequestMethod.GET)
	public ModelAndView viewPOOrderDailyAct(HttpServletRequest request,
			@ModelAttribute("command") OrderBean orderBean, BindingResult result) {

		log.info("$$$ viewPOOrderDailyAct Starts : OrderController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		int sellerId;
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			Order order = orderService.getOrder(orderBean.getOrderId(),
					sellerId);
			List<Order> orderlist = orderService.getPOOrdersFromConsolidated(
					order.getOrderId(), sellerId);
			
			model.put("order", ConverterClass.prepareOrderBean(order));

			if (orderlist == null) {
				List<GatePass> gatepassList = orderService.getGatepassesFromConsolidated(
						order.getOrderReturnOrRTO().getReturnId(), sellerId);
				model.put("gatepasslist", gatepassList);
				
				log.info("$$$ viewPOOrderDailyAct Ends : OrderController $$$");
				return new ModelAndView("dailyactivities/viewGatePass", model);
			} else {
				model.put("orderlist", ConverterClass.prepareListofBean(orderlist));
				
				log.info("$$$ viewPOOrderDailyAct Ends : OrderController $$$");
				return new ModelAndView("dailyactivities/viewPOOrder", model);
			}
		} catch (CustomException ce) {
			log.error("viewOrderDailyAct exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed!", e);
			return new ModelAndView("globalErorPage", model);
		}
	}

	@RequestMapping(value = "/seller/editOrderDA", method = RequestMethod.GET)
	public ModelAndView editOrderDA(HttpServletRequest request,
			@ModelAttribute("command") OrderBean orderBean, BindingResult result) {

		log.info("$$$ editOrderDA Starts : OrderController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("order", ConverterClass.prepareOrderBean(orderService
					.getOrder(orderBean.getOrderId())));
			model.put("orders", ConverterClass.prepareListofBean(orderService
					.listOrders(helperClass.getSellerIdfromSession(request))));
		} catch (CustomException ce) {
			log.error("editOrderDA exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed!", e);
		}
		log.info("$$$ editOrderDA Ends : OrderController $$$");
		return new ModelAndView("dailyactivities/editOrder", model);
	}

	@RequestMapping(value = "/seller/deleteOrderDA", method = RequestMethod.GET)
	public ModelAndView deleteOrderDA(HttpServletRequest request,
			@ModelAttribute("command") OrderBean orderBean, BindingResult result) {

		log.info("$$$ deleteOrderDA Starts : OrderController $$$");
		log.debug(" Order bean id todelete :" + orderBean.getOrderId());
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			orderService.deleteOrder(ConverterClass.prepareModel(orderBean),
					helperClass.getSellerIdfromSession(request));
			model.put("order", null);
			model.put("orders", ConverterClass.prepareListofBean(orderService
					.listOrders(helperClass.getSellerIdfromSession(request))));
		} catch (CustomException ce) {
			log.error("deleteOrderDA exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed!", e);
		}

		log.info("$$$ deleteOrderDA Ends : OrderController $$$");
		return new ModelAndView("dailyactivities/addOrder", model);
	}

	@RequestMapping(value = "/seller/saveOrderDA", method = RequestMethod.POST)
	public ModelAndView saveOrderDA(HttpServletRequest request,
			@ModelAttribute("command") OrderBean orderBean, BindingResult result) {

		log.info("$$$ saveOrderDA Starts : OrderController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		List<Order> orderlist=new ArrayList<Order>();

		try {
			Order order = ConverterClass.prepareModel(orderBean);
			orderlist.add(order);
			orderService.addOrder(orderlist,
					helperClass.getSellerIdfromSession(request));
		} catch (CustomException ce) {
			log.error("saveOrderDA exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed!", e);
		}

		log.info("$$$ saveOrderDA Ends : OrderController $$$");
		return new ModelAndView("redirect:/seller/orderList.html?savedOrder="
				+ orderBean.getChannelOrderID());

	}

	@RequestMapping(value = "/seller/addOrderDA", method = RequestMethod.GET)
	public ModelAndView addOrderDA(HttpServletRequest request,
			@ModelAttribute("command") OrderBean orderBean, BindingResult result) {

		log.info("$$$ addOrderDA Starts : OrderController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<Partner> partnerlist = partnerService.listPartners(helperClass
					.getSellerIdfromSession(request));
			List<TaxCategory> taxCatList = taxDetailService
					.listTaxCategories(helperClass
							.getSellerIdfromSession(request));
			List<Product> productList = productService.listProducts(helperClass
					.getSellerIdfromSession(request));
			Map<String, String> partnermap = new HashMap<String, String>();
			Map<String, String> taxcatmap = new HashMap<String, String>();
			Map<String, String> productmap = new HashMap<String, String>();
			if (partnerlist != null && partnerlist.size() != 0) {
				for (Partner bean : partnerlist) {
					partnermap.put(bean.getPcName(), bean.getPcName());

				}
			}
			if (taxCatList != null && taxCatList.size() != 0) {
				for (TaxCategory bean : taxCatList) {
					taxcatmap.put(bean.getTaxCatName(), bean.getTaxCatName());

				}
			}
			if (productList != null && productList.size() != 0) {
				for (Product bean : productList) {
					productmap.put(bean.getProductSkuCode(),
							bean.getProductSkuCode());

				}
			}

			model.put("partnermap", partnermap);
			model.put("taxcatmap", taxcatmap);
			model.put("productmap", productmap);
		} catch (CustomException ce) {
			log.error("addOrderDA exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed!", e);
			model.put("errorMessage", e.getCause());
			return new ModelAndView("globalErorPage", model);
		}
		log.info("$$$ addOrderDA Ends : OrderController $$$");
		return new ModelAndView("dailyactivities/addOrder", model);
	}

	// Method to check Availability of Order Id
	@RequestMapping(value = "/seller/checkOrder.html", method = RequestMethod.GET)
	public @ResponseBody String getCheckPartner(HttpServletRequest request,
			@ModelAttribute("command") OrderBean orderBean,
			BindingResult result, Model model) {

		log.info("$$$ getCheckPartner Starts : OrderController $$$");
		Map<String, Object> mode = new HashMap<String, Object>();
		List parner = new ArrayList();
		try {
			parner = orderService.findOrders("channelOrderID",
					request.getParameter("id"),
					helperClass.getSellerIdfromSession(request), false, false);
		} catch (CustomException ce) {
			log.error("addOrderDA exception : " + ce.toString());
			mode.put("error", ce.getLocalMessage());
			String errors = gson.toJson(mode);
			return errors;
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed!", e);
		}
		if (parner != null && parner.size() != 0 && parner.get(0) != null) {
			log.info("$$$ getCheckPartner Ends : OrderController $$$");
			return "<font color='red'>Order ID Not available</font>";
		} else {
			log.info("$$$ getCheckPartner Ends : OrderController $$$");
			return "<font color='green'>Order ID Available</font>";
		}
	}

	@RequestMapping(value = "/seller/gatepasslist", method = RequestMethod.GET)
	public ModelAndView gatepasslistDA(HttpServletRequest request,
			@ModelAttribute("command") OrderBean orderBean, BindingResult result) {

		log.info("$$$ gatepasslistDA Starts : OrderController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			int pageNo = request.getParameter("page") != null ? Integer
					.parseInt(request.getParameter("page")) : 0;

			model.put(
					"gatepasses",
					ConverterClass.prepareListofBean(orderService.listGatePasses(
							helperClass.getSellerIdfromSession(request), pageNo)));
		} catch (CustomException ce) {
			log.error("orderListDailyAct exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed!", e);
		}
		log.info("$$$ gatepasslistDA Ends : OrderController $$$");
		return new ModelAndView("dailyactivities/gatepasslist", model);
	}
	
	@RequestMapping(value = "/seller/disputedGPList", method = RequestMethod.GET)
	public ModelAndView disputedGPList(HttpServletRequest request,
			@ModelAttribute("command") OrderBean orderBean, BindingResult result) {

		log.info("$$$ disputedGPList Starts : OrderController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			int pageNo = request.getParameter("page") != null ? Integer
					.parseInt(request.getParameter("page")) : 0;

			model.put(
					"gatepasses",
					ConverterClass.prepareListofBean(orderService.listDisputedGatePasses(
							helperClass.getSellerIdfromSession(request), pageNo)));
		} catch (CustomException ce) {
			log.error("orderListDailyAct exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed!", e);
		}
		log.info("$$$ disputedGPList Ends : OrderController $$$");
		return new ModelAndView("dailyactivities/disputedGPList", model);
	}

	@RequestMapping(value = "/seller/poOrderList", method = RequestMethod.GET)
	public ModelAndView poOrderListDailyAct(HttpServletRequest request,
			@RequestParam("value") String value,
			@ModelAttribute("command") OrderBean orderBean, BindingResult result) {

		log.info("$$$ poOrderListDailyAct Starts : OrderController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		List<OrderBean> poOrderlist = new ArrayList<OrderBean>();
		int sellerId;
		try {
			sellerId = helperClass.getSellerIdfromSession(request);

			String dateString;
			DateFormat format = new SimpleDateFormat("d MMMM yyyy",
					Locale.ENGLISH);
			DateFormat monthFormat = new SimpleDateFormat("MMM yyyy",
					Locale.ENGLISH);
			Date startDate = null;
			Date endDate = null;
			String period = "";
			boolean dateRange = true;
			
			if ("".equals(value.trim()) || value.equals("0")) {
				Calendar c = Calendar.getInstance();
				c.setTime(new Date());
				c.set(Calendar.DAY_OF_MONTH, 1);
				startDate = c.getTime();
				c.set(Calendar.DAY_OF_MONTH,
						c.getActualMaximum(Calendar.DAY_OF_MONTH));
				endDate = c.getTime();
				period = monthFormat.format(startDate);
				//period = period.substring(period.indexOf(' '));
				
			} else if ("All".equalsIgnoreCase(value.trim())) {
				dateRange = false;
				
			} else if (value.contains(" ")) {
				dateString = "1 " + value;
				startDate = format.parse(dateString);

				Calendar c = Calendar.getInstance();
				c.setTime(startDate);
				c.set(Calendar.DAY_OF_MONTH,
						c.getActualMaximum(Calendar.DAY_OF_MONTH));
				endDate = c.getTime();
				period = monthFormat.format(startDate);
			} else {
				dateString = "1 January " + value;
				startDate = format.parse(dateString);

				dateString = "31 December " + value;
				endDate = format.parse(dateString);
			}

			if (dateRange) {
				poOrderlist = ConverterClass.prepareListofBean(orderService
						.findPOOrdersbyDate("orderDate", startDate, endDate,
								sellerId));
			} else {
				int pageNo = request.getParameter("page") != null ? Integer
						.parseInt(request.getParameter("page")) : 0;
				poOrderlist = ConverterClass.prepareListofBean(orderService
						.listPOOrders(sellerId, pageNo));
			}
			
			model.put("poOrders", poOrderlist);
			model.put("period", period);

		} catch (CustomException ce) {
			ce.printStackTrace();
			log.error("orderListDailyAct exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed!", e);
		}

		log.info("$$$ poOrderListDailyAct Ends : OrderController $$$");
		return new ModelAndView("dailyactivities/poOrderList", model);
	}

	@RequestMapping(value = "/seller/download/uploadLog", method = RequestMethod.GET)
	public void getUploadLog(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "id") String id)
			throws ClassNotFoundException {

		log.info("$$$ getUploadLog Starts : OrderController $$$");
		try {
			log.debug(" Downloading the Log: " + id);
			int uploadId = Integer.parseInt(id);
			int sellerId = helperClass.getSellerIdfromSession(request);
			String filePath = reportGeneratorService.getUploadLog(uploadId, sellerId).getFilePath();
			if (filePath != null) {
				downloadService.getUploadLog(response, filePath);
			}

		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed!", e);
		}
		log.info("$$$ getXLS Ends : OrderController $$$");
	}

}
