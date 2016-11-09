package com.o2r.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.o2r.bean.OrderBean;
import com.o2r.helper.ConverterClass;
import com.o2r.helper.CustomException;
import com.o2r.helper.DateDeserializer;
import com.o2r.helper.DateSerializer;
import com.o2r.helper.FileUploadForm;
import com.o2r.helper.GlobalConstant;
import com.o2r.helper.HelperClass;
import com.o2r.helper.SaveContents;
import com.o2r.helper.ValidateUpload;
import com.o2r.model.Order;
import com.o2r.model.OrderRTOorReturn;
import com.o2r.model.UploadReport;
import com.o2r.service.DownloadService;
import com.o2r.service.OrderService;

/**
 * @author Deep Mehrotra
 *
 */
@Controller
public class ReturnOrderController {

	@Autowired
	private OrderService orderService;
	@Resource(name = "downloadService")
	private DownloadService downloadService;
	@Resource(name = "saveContents")
	private SaveContents saveContents;
	@Autowired
	private HelperClass helperClass;

	static Logger log = Logger.getLogger(ReturnOrderController.class.getName());

	@RequestMapping(value = "/seller/returnOrderList", method = RequestMethod.GET)
	public ModelAndView returnOrRTOlist() {

		log.info("$$$ returnOrRTOlist Starts : ReturnOrderController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		System.out.println(" Inside return or Rto list");
		model.put("order", new OrderBean());
		log.info("$$$ returnOrRTOlist Ends : ReturnOrderController $$$");
		return new ModelAndView("dailyactivities/returnOrRTOList", model);
	}

	@RequestMapping(value = "/seller/saveReturnOrder", method = RequestMethod.POST)
	public ModelAndView saveReturnOrder(HttpServletRequest request,
			@ModelAttribute("command") OrderBean orderBean, BindingResult result) {

		log.info("$$$ saveReturnOrder Starts : ReturnOrderController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		int sellerId = 0;
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			orderService.addReturnOrder(orderBean.getChannelOrderID(),
					ConverterClass.prepareOrderRTOorReturnModel(orderBean
							.getOrderReturnOrRTO()), sellerId);
		} catch (CustomException ce) {
			log.error("saveReturnOrder exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed! by seller : "+sellerId, e);
		}
		log.info("$$$ saveReturnOrder Ends : ReturnOrderController $$$");
		return new ModelAndView("redirect:/seller/returnOrRTOlist.html");
	}

	@RequestMapping(value = "/seller/searchReturnOrder", method = RequestMethod.POST)
	public ModelAndView searchReturnOrder(HttpServletRequest request,
			@ModelAttribute("command") OrderBean orderBean, BindingResult result) {

		log.info("$$$ searchReturnOrder Starts : ReturnOrderController $$$");
		List<OrderBean> orderlist = null;
		List<Order> temporaryorderlist = null;
		Map<String, Object> model = new HashMap<String, Object>();
		String searchColumn = request.getParameter("searchCriteria");
		String searchString = request.getParameter("searchString");
		String searchType = request.getParameter("toggler");
		String searchDateCriteria = request.getParameter("searchDateCriteria");
		int sellerId=0;
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			if (searchType.equals("searchByProperty") && searchColumn != null && searchString != null) {
				if (searchColumn.equals("customerName")
						|| searchColumn.equals("customerCity")
						|| searchColumn.equals("customerEmail")
						|| searchColumn.equals("customerPhnNo")) {
					orderlist = ConverterClass
							.prepareListofBean(orderService.findOrdersbyCustomerDetails(
									searchColumn, searchString, sellerId));
				}

				else {
					orderlist = ConverterClass
							.prepareListofBean(orderService.findOrders(
									searchColumn,
									searchString,
									helperClass.getSellerIdfromSession(request),
									false, true));
				}
			} else if(searchType.equals("searchByDate")){
				orderlist = new ArrayList<OrderBean>();
				System.out.println("request.getParameter(startDate)  "
						+ request.getParameter("startDate"));
				System.out.println("request.getParameter(endDate)  "
						+ request.getParameter("endDate"));
				if (request.getParameter("startDate") != null
						&& request.getParameter("endDate") != null
						&& request.getParameter("startDate") != ""
						&& request.getParameter("endDate") != "") {
					Date startDate = new Date(request.getParameter("startDate"));
					Date endDate = new Date(request.getParameter("endDate"));
					if (searchDateCriteria.equalsIgnoreCase("returnDate")) {
						temporaryorderlist = orderService
								.findOrdersbyReturnDate(
										searchDateCriteria,
										startDate,
										endDate,
										helperClass
												.getSellerIdfromSession(request));
					} else if (searchColumn.equals("dateofPayment")) {
						temporaryorderlist = orderService
								.findOrdersbyPaymentDate(
										searchDateCriteria,
										startDate,
										endDate, sellerId);

					} else {
						temporaryorderlist = orderService.findOrdersbyDate(
								searchDateCriteria, startDate, endDate, sellerId, false);
					}
				}
				if (temporaryorderlist != null
						&& temporaryorderlist.size() != 0)
					orderlist = ConverterClass
							.prepareListofBean(temporaryorderlist);

			}
		} catch (CustomException ce) {
			log.error("searchReturnOrder exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed! by seller : "+sellerId, e);
		}
		model.put("searchOrderList", orderlist);
		log.info("$$$ searchReturnOrder Ends : ReturnOrderController $$$");
		return new ModelAndView("dailyactivities/returnOrRTOList", model);
	}

	/*
	 * 
	 * Return order save method
	 */

	@RequestMapping(value = "/seller/saveReturnorRTO", method = RequestMethod.POST)
	public ModelAndView saveReturnorRTO(HttpServletRequest request,
			@ModelAttribute("command") OrderBean orderBean, BindingResult result) {

		log.info("$$$ saveReturnorRTO Starts : ReturnOrderController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		List<OrderBean> orderlist = null;
		int sellerId = 0;
		log.debug("Inside savereturn order : channelordrid : "
				+ orderBean.getChannelOrderID());
		log.debug(" orderid : "
				+ orderBean.getOrderId()
				+ " return id : "
				+ orderBean.getOrderReturnOrRTO().getReturnOrRTOId()
				+ " return charge : "
				+ orderBean.getOrderReturnOrRTO()
						.getReturnOrRTOChargestoBeDeducted() + "return date : "
				+ orderBean.getOrderReturnOrRTO().getReturnDate());
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			if (orderBean.getChannelOrderID() != null
					&& orderBean.getOrderReturnOrRTO().getReturnOrRTOId() != null) {
				/*List<Order> orderListToReturn = orderService.searchAsIsOrder("typeIdentifier", 
						(String) orderBean.getChannelOrderID().subSequence(0, orderBean.getChannelOrderID().indexOf(GlobalConstant.orderUniqueSymbol)), sellerId);
				if(orderListToReturn != null && orderListToReturn.size() != 0){
					int orderCount = 0;
					for (Order eachOrder : orderListToReturn) {
						if(orderCount != 0){
							orderBean.getOrderReturnOrRTO().setReturnorrtoQty(0);
						}
						orderService.addReturnOrder(eachOrder.getChannelOrderID(),
								ConverterClass.prepareOrderRTOorReturnModel(orderBean
										.getOrderReturnOrRTO()), sellerId);
						orderCount ++;
					}
				} else {
					orderService.addReturnOrder(orderBean.getChannelOrderID(),
							ConverterClass.prepareOrderRTOorReturnModel(orderBean
									.getOrderReturnOrRTO()), sellerId);
				}*/
				orderService.addReturnOrder(orderBean.getChannelOrderID(),
						ConverterClass.prepareOrderRTOorReturnModel(orderBean
								.getOrderReturnOrRTO()), sellerId);
			}
		} catch (CustomException ce) {
			log.error("saveReturnorRTO exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			log.error("Failed! by seller : "+sellerId, e);
			log.error(e);
		}
		log.debug(" Returnorders list : " + orderlist);
		log.info("$$$ saveReturnorRTO Ends : ReturnOrderController $$$");
		return new ModelAndView("redirect:/seller/orderList.html?savedOrder="
				+ orderBean.getChannelOrderID());
	}

	/*
	 * 
	 * Method to implemnt return through Ajax in Jtable
	 */
	@RequestMapping(value = "/seller/findOrderDA", method = RequestMethod.POST)
	public @ResponseBody String findOrderDA(HttpServletRequest request) {

		log.info("$$$ findOrderDA Starts : ReturnOrderController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
		gsonBuilder.registerTypeAdapter(Date.class, new DateSerializer());
		Gson gson = gsonBuilder.setPrettyPrinting().create();
		List<OrderBean> orderlist = null;
		List<Order> temporaryorderlist = null;
		Order order = null;
		int sellerId = 0;
		OrderRTOorReturn orderReturn = null;
		String searchColumn = request.getParameter("searchCriteria");
		String searchString = request.getParameter("searchString");
		String searchType = request.getParameter("toggler");
		String searchDateCriteria = request.getParameter("searchDateCriteria");
		String action = request.getParameter("action");

		log.debug(" Getting value from request :  action  :" + action
				+ "  searchColumn :" + searchColumn + "searchString : "
				+ searchString + " searchType :" + searchType
				+ " searchDateCriteria : " + searchDateCriteria
				+ " startdate :" + request.getParameter("startDate")
				+ " enddate :" + request.getParameter("endDate"));

		log.debug("*** Returnid  : " + request.getParameter("returnId"));

		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			if (action != null && action.equals("list")) {
				if (searchType != null && searchType.equals("searchByProperty")
						&& searchColumn != null) {
					if (searchColumn.equals("customerName")
							|| searchColumn.equals("customerCity")
							|| searchColumn.equals("customerEmail")
							|| searchColumn.equals("customerPhnNo")) {
						orderlist = ConverterClass
								.prepareListofBean(orderService
										.findOrdersbyCustomerDetails(
												searchColumn,
												searchString,
												helperClass
														.getSellerIdfromSession(request)));
					} else if (searchColumn.equals("returnOrRTOId")) {

					}

					else {
						orderlist = new ArrayList<OrderBean>();
						temporaryorderlist = new ArrayList<Order>();
						temporaryorderlist = orderService.findOrders(
								searchColumn, searchString,
								helperClass.getSellerIdfromSession(request),
								false, true);
						if (temporaryorderlist != null) {
							for (Order ordertemp : temporaryorderlist) {
								if (ordertemp.getOrderReturnOrRTO()
										.getReturnOrRTOId() == null) {
									ordertemp.getOrderReturnOrRTO()
											.setReturnOrRTOId("");
									ordertemp.getOrderReturnOrRTO()
											.setReturnDate(
													new Date("01/01/1900"));
									ordertemp.getOrderReturnOrRTO()
											.setReturnOrRTOreason("");

								}
								orderlist.add(ConverterClass
										.prepareOrderBean(ordertemp));
							}
						}
						if (orderlist.size() != 0)
							log.debug("  Got order in controller : "
									+ orderlist.size());

					}
				} else {
					temporaryorderlist = new ArrayList<Order>();
					orderlist = new ArrayList<OrderBean>();
					Date startDate = new Date(request.getParameter("startDate"));
					Date endDate = new Date(request.getParameter("endDate"));
					if (searchDateCriteria.equalsIgnoreCase("returnDate")) {
						temporaryorderlist = orderService
								.findOrdersbyReturnDate(
										searchDateCriteria,
										startDate,
										endDate, sellerId);
					} else if (searchColumn.equals("dateofPayment")) {
						temporaryorderlist = orderService
								.findOrdersbyPaymentDate(
										searchDateCriteria,
										startDate,
										endDate, sellerId);

					} else {

					}
					if (temporaryorderlist != null) {
						for (Order ordertemp : temporaryorderlist) {
							if (ordertemp.getOrderReturnOrRTO()
									.getReturnOrRTOId() == null) {
								ordertemp.getOrderReturnOrRTO()
										.setReturnOrRTOId("");
								ordertemp.getOrderReturnOrRTO().setReturnDate(
										new Date("01/01/2000"));
								ordertemp.getOrderReturnOrRTO()
										.setReturnOrRTOreason("");
							}
							orderlist.add(ConverterClass
									.prepareOrderBean(ordertemp));
						}
					}
					log.debug("  temporaryorderlist  size :"
							+ temporaryorderlist.size());
					if (temporaryorderlist != null
							&& temporaryorderlist.size() != 0)
						orderlist = ConverterClass
								.prepareListofBean(temporaryorderlist);

				}
			} else if (action.equals("create") || action.equals("update")) {
				String orderId = request.getParameter("orderId");
				String channelOrderID = request.getParameter("channelOrderID");
				String status = request.getParameter("status");
				String returnOrRTOId = request.getParameter("returnOrRTOId");
				String returnDate = request.getParameter("returnDate");
				String returnOrRTOCharges = request
						.getParameter("returnOrRTOCharges");
				String returnorrtoQty = request.getParameter("returnorrtoQty");
				String returnOrRTOreason = request
						.getParameter("returnOrRTOreason");
				String returnId = request.getParameter("returnId");
				String returnOrRTOstatus = request
						.getParameter("returnOrRTOstatus");
				System.out.println("  *************** returnId :" + returnId);
				System.out.println(" Check:orderId: " + orderId
						+ " >channelOrderID >>" + channelOrderID + "status :"
						+ status + " >>returnOrRTOId :   " + returnOrRTOId
						+ "returnDate >" + returnDate
						+ ">>returnOrRTOCharges :" + returnOrRTOCharges
						+ "returnorrtoQty :" + returnorrtoQty
						+ " returnOrRTOreason " + returnOrRTOreason);
				order = new Order();
				orderlist = new ArrayList<OrderBean>();
				orderReturn = new OrderRTOorReturn();
				if (returnId != null)
					if (orderId != null && StringUtils.isNotEmpty(orderId)) {
						order.setOrderId(Integer.parseInt(orderId));
					}
				if (channelOrderID != null
						&& StringUtils.isNotEmpty(channelOrderID)) {
					order.setChannelOrderID(channelOrderID);
				}
				if (status != null && StringUtils.isNotEmpty(status)) {
					order.setStatus(status);
				}
				if (returnOrRTOId != null
						&& StringUtils.isNotEmpty(returnOrRTOId)) {
					orderReturn.setReturnOrRTOId(returnOrRTOId);
				}
				if (returnDate != null && StringUtils.isNotEmpty(returnDate)) {
					orderReturn.setReturnDate(new Date(returnDate));
				}
				if (returnOrRTOCharges != null
						&& StringUtils.isNotEmpty(returnOrRTOCharges)) {
					orderReturn.setReturnOrRTOChargestoBeDeducted(Double
							.parseDouble(returnOrRTOCharges));
				}
				if (returnorrtoQty != null
						&& StringUtils.isNotEmpty(returnorrtoQty)) {
					orderReturn.setReturnorrtoQty(Integer
							.parseInt(returnorrtoQty));
				}
				if (returnOrRTOreason != null
						&& StringUtils.isNotEmpty(returnOrRTOreason)) {
					orderReturn.setReturnOrRTOreason(returnOrRTOreason);
				}
				if (returnOrRTOstatus != null
						&& StringUtils.isNotEmpty(returnOrRTOstatus)) {
					orderReturn.setReturnOrRTOstatus(returnOrRTOstatus);
				}
				order.setOrderReturnOrRTO(orderReturn);
				log.debug("set order");
				orderService.addReturnOrder(channelOrderID,
						order.getOrderReturnOrRTO(), sellerId);
				Order temp = orderService.getOrder(order.getOrderId());
				log.debug(" return id :"
						+ temp.getOrderReturnOrRTO().getReturnId());
				log.debug(" return reason  :"
						+ temp.getOrderReturnOrRTO().getReturnOrRTOreason());
				log.debug(" return user id :"
						+ temp.getOrderReturnOrRTO().getReturnOrRTOId());
				orderlist.add(ConverterClass.prepareOrderBean(orderService
						.getOrder(order.getOrderId())));

			} else if (action.equals("delete")) {
				String orderId = request.getParameter("orderId");
				if (orderId != null && StringUtils.isNotEmpty(orderId)) {
					orderService.deleteReturnInfo(orderId);
				}
			}
		} catch (CustomException ce) {
			log.error("findOrderDA exception : " + ce.toString());
			model.put("Error", ce.getLocalizedMessage());
			String errors = gson.toJson(model);
			return errors;
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed! by Seller : "+sellerId, e);
		}
		model.put("Result", "OK");
		model.put("Records", orderlist);

		// Convert Java Object to Json
		String jsonArray = gson.toJson(model);

		log.info("$$$ findOrderDA Ends : ReturnOrderController $$$");
		return jsonArray;
	}

	@RequestMapping(value = "/seller/download/Returnxls", method = RequestMethod.GET)
	public void getXLS(HttpServletResponse response)
			throws ClassNotFoundException {

		// Delegate to downloadService. Make sure to pass an instance of
		// HttpServletResponse
		downloadService.downloadReturnXLS(response);
	}

	@RequestMapping(value = "/seller/returnUploadsheet", method = RequestMethod.GET)
	public String ReturnUploadForm() {

		return "dailyactivities/return_upload_form";
	}

	@RequestMapping(value = "/seller/saveReturnSheet", method = RequestMethod.POST)
	public String save(HttpServletRequest request,
			@ModelAttribute("uploadForm") FileUploadForm uploadForm, Model map) {

		log.info("$$$ save() Starts : ReturnOrderController $$$");
		String applicationPath = request.getServletContext().getRealPath("");
		UploadReport uploadReport = new UploadReport();

		List<MultipartFile> files = uploadForm.getFiles();
		List<String> fileNames = new ArrayList<String>();
		MultipartFile fileinput = files.get(0);
		int sellerId = 0;
		if (null != files && files.size() > 0) {
			fileNames.add(files.get(0).getOriginalFilename());
			try {
				sellerId = helperClass.getSellerIdfromSession(request);
				log.debug(" Filename : " + files.get(0).getOriginalFilename());
				log.debug(" Filename : " + files.get(0).getName());
				ValidateUpload.validateOfficeData(files.get(0));
				log.debug(" fileinput " + fileinput.getName());
				saveContents.saveOrderReturnDetails(files.get(0), sellerId,
						applicationPath, uploadReport);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Failed! by Seller : "+sellerId, e);
				log.debug("Inside exception , filetype not accepted "
						+ e.getLocalizedMessage());
			}
		}
		log.info("$$$ save() Ends : ReturnOrderController $$$");
		return "returnUploadSuccess";

	}

}