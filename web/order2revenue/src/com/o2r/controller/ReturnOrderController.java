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
import com.o2r.helper.HelperClass;
import com.o2r.helper.SaveContents;
import com.o2r.helper.ValidateUpload;
import com.o2r.model.Order;
import com.o2r.model.OrderRTOorReturn;
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
	Map<String, Object> model = new HashMap<String, Object>();

	static Logger log = Logger.getLogger(ReturnOrderController.class.getName());

	@RequestMapping(value = "/seller/returnOrderList", method = RequestMethod.GET)
	public ModelAndView returnOrRTOlist() {

		Map<String, Object> model = new HashMap<String, Object>();
		System.out.println(" Inside return or Rto list");
		model.put("order", new OrderBean());
		return new ModelAndView("dailyactivities/returnOrRTOList", model);
	}

	@RequestMapping(value = "/seller/saveReturnOrder", method = RequestMethod.POST)
	public ModelAndView saveReturnOrder(HttpServletRequest request,
			@ModelAttribute("command") OrderBean orderBean, BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		int sellerId;
		System.out.println("Inside expense category Ssave");
		try {
			sellerId = HelperClass.getSellerIdfromSession(request);
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
			log.error(e);
		}
		return new ModelAndView("redirect:/seller/returnOrRTOlist.html");
	}

	@RequestMapping(value = "/seller/searchReturnOrder", method = RequestMethod.POST)
	public ModelAndView searchReturnOrder(HttpServletRequest request,
			@ModelAttribute("command") OrderBean orderBean, BindingResult result) {
		OrderRTOorReturn orderReturn = null;
		List<OrderBean> orderlist = null;
		List<Order> temporaryorderlist = null;
		Order order = null;
		Map<String, Object> model = new HashMap<String, Object>();
		int sellerId;
		String searchColumn = request.getParameter("searchCriteria");
		String searchString = request.getParameter("searchString");
		String searchType = request.getParameter("toggler");
		String searchDateCriteria = request.getParameter("searchDateCriteria");
		String action = request.getParameter("action");

		try {
			sellerId = HelperClass.getSellerIdfromSession(request);
			if (searchType.equals("searchByProperty") && searchColumn != null) {
				if (searchColumn.equals("customerName")
						|| searchColumn.equals("customerCity")
						|| searchColumn.equals("customerEmail")
						|| searchColumn.equals("customerPhnNo")) {
					orderlist = ConverterClass
							.prepareListofBean(orderService.findOrdersbyCustomerDetails(
									searchColumn, searchString,
									HelperClass.getSellerIdfromSession(request)));
				}

				else {
					System.out.println(" Inside search find orders");
					orderlist = ConverterClass
							.prepareListofBean(orderService.findOrders(
									searchColumn, searchString,
									HelperClass.getSellerIdfromSession(request), false));
				}
			} else {
				orderlist = new ArrayList<OrderBean>();
				Date startDate = new Date(request.getParameter("startDate"));
				Date endDate = new Date(request.getParameter("endDate"));
				if (searchDateCriteria.equalsIgnoreCase("returnDate")) {
					temporaryorderlist = orderService.findOrdersbyReturnDate(
							searchDateCriteria, startDate, endDate,
							HelperClass.getSellerIdfromSession(request));
				} else if (searchColumn.equals("dateofPayment")) {
					temporaryorderlist = orderService.findOrdersbyPaymentDate(
							searchDateCriteria, startDate, endDate,
							HelperClass.getSellerIdfromSession(request));

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
			log.error(e);
		}

		/*
		 * System.out.println(" Getting value from request :  action  :" +
		 * action + "  searchColumn :" + searchColumn + "searchString : " +
		 * searchString + " searchType :" + searchType +
		 * " searchDateCriteria : " + searchDateCriteria + " startdate :" +
		 * request.getParameter("startDate") + " enddate :" +
		 * request.getParameter("endDate"));
		 */
		model.put("searchOrderList", orderlist);
		return new ModelAndView("dailyactivities/returnOrderTable", model);
	}

	/*
	 * 
	 * Return order save method
	 */

	@RequestMapping(value = "/seller/saveReturnorRTO", method = RequestMethod.POST)
	public ModelAndView saveReturnorRTO(HttpServletRequest request,
			@ModelAttribute("command") OrderBean orderBean, BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		List<OrderBean> orderlist = null;
		System.out.println("Inside savereturn order : channelordrid : "
				+ orderBean.getChannelOrderID());
		System.out.println(" orderid : "
				+ orderBean.getOrderId()
				+ " return id : "
				+ orderBean.getOrderReturnOrRTO().getReturnOrRTOId()
				+ " return charge : "
				+ orderBean.getOrderReturnOrRTO()
						.getReturnOrRTOChargestoBeDeducted() + "return date : "
				+ orderBean.getOrderReturnOrRTO().getReturnDate());
		try {
			if (orderBean.getChannelOrderID() != null
					&& orderBean.getOrderReturnOrRTO().getReturnOrRTOId() != null) {
				orderService.addReturnOrder(orderBean.getChannelOrderID(),
						ConverterClass.prepareOrderRTOorReturnModel(orderBean
								.getOrderReturnOrRTO()), HelperClass
								.getSellerIdfromSession(request));

			}
		} catch (CustomException ce) {
			log.error("saveReturnorRTO exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			log.error(e);
		}
		System.out.println(" Returnorders list : " + orderlist);
		// model.put("orders",
		// ConverterClass.prepareListofBean(orderService.listOrders(HelperClass.getSellerIdfromSession(request),0)));
		return new ModelAndView("redirect:/seller/orderList.html?savedOrder="
				+ orderBean.getChannelOrderID());
	}

	/*
	 * 
	 * Method to implemnt return through Ajax in Jtable
	 */
	@RequestMapping(value = "/seller/findOrderDA", method = RequestMethod.POST)
	public @ResponseBody String findOrderDA(HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		// Gson gson = new GsonBuilder().setPrettyPrinting().create();
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
		gsonBuilder.registerTypeAdapter(Date.class, new DateSerializer());
		Gson gson = gsonBuilder.setPrettyPrinting().create();
		System.out.println(" Inside find order method controller");
		List<OrderBean> orderlist = null;
		List<Order> temporaryorderlist = null;
		Order order = null;
		int sellerId;
		OrderRTOorReturn orderReturn = null;
		String searchColumn = request.getParameter("searchCriteria");
		String searchString = request.getParameter("searchString");
		String searchType = request.getParameter("toggler");
		String searchDateCriteria = request.getParameter("searchDateCriteria");
		String action = request.getParameter("action");

		System.out.println(" Getting value from request :  action  :" + action
				+ "  searchColumn :" + searchColumn + "searchString : "
				+ searchString + " searchType :" + searchType
				+ " searchDateCriteria : " + searchDateCriteria
				+ " startdate :" + request.getParameter("startDate")
				+ " enddate :" + request.getParameter("endDate"));

		System.out.println("*** Returnid  : "
				+ request.getParameter("returnId"));

		try {
			sellerId = HelperClass.getSellerIdfromSession(request);
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
												HelperClass
														.getSellerIdfromSession(request)));
					} else if (searchColumn.equals("returnOrRTOId")) {

					}

					else {
						System.out.println(" Inside search find orders");
						orderlist = new ArrayList<OrderBean>();
						temporaryorderlist = new ArrayList<Order>();
						temporaryorderlist = orderService.findOrders(
								searchColumn, searchString,
								HelperClass.getSellerIdfromSession(request), false);
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
							System.out.println("  Got order in controller : "
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
										endDate,
										HelperClass
												.getSellerIdfromSession(request));
					} else if (searchColumn.equals("dateofPayment")) {
						temporaryorderlist = orderService
								.findOrdersbyPaymentDate(
										searchDateCriteria,
										startDate,
										endDate,
										HelperClass
												.getSellerIdfromSession(request));

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
					System.out.println("  temporaryorderlist  size :"
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
				// String returnDate=request.getParameter("returnDate");
				order = new Order();
				orderlist = new ArrayList<OrderBean>();
				orderReturn = new OrderRTOorReturn();
				if (returnId != null)
					// orderReturn.setReturnId(Integer.parseInt(returnId));
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
				System.out.println("set order");
				orderService.addReturnOrder(channelOrderID,
						order.getOrderReturnOrRTO(), sellerId);
				Order temp = orderService.getOrder(order.getOrderId());
				System.out.println(" return id :"
						+ temp.getOrderReturnOrRTO().getReturnId());
				System.out.println(" return reason  :"
						+ temp.getOrderReturnOrRTO().getReturnOrRTOreason());
				System.out.println(" return user id :"
						+ temp.getOrderReturnOrRTO().getReturnOrRTOId());
				// orderlist.add(ConverterClass.prepareOrderBean(order));
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
			// return new ModelAndView("globalErorPage", model);
			String errors = gson.toJson(model);
			return errors;
		} catch (Throwable e) {
			log.error(e);
		}
		model.put("Result", "OK");
		model.put("Records", orderlist);

		// Convert Java Object to Json
		String jsonArray = gson.toJson(model);
		return jsonArray;
	}

	/**
	 * Downloads the report as an Excel format.
	 * <p>
	 * Make sure this method doesn't return any model. Otherwise, you'll get an
	 * "IllegalStateException: getOutputStream() has already been called for this response"
	 */
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
		System.out.println("Inside save method");
		// gets absolute path of the web application
		String applicationPath = request.getServletContext().getRealPath("");

		List<MultipartFile> files = uploadForm.getFiles();
		List<String> fileNames = new ArrayList<String>();
		MultipartFile fileinput = files.get(0);
		int sellerId;
		System.out.println(" got file");
		if (null != files && files.size() > 0) {
			fileNames.add(files.get(0).getOriginalFilename());
			try {
				sellerId = HelperClass.getSellerIdfromSession(request);
				System.out.println(" Filename : "
						+ files.get(0).getOriginalFilename());
				System.out.println(" Filename : " + files.get(0).getName());
				ValidateUpload.validateOfficeData(files.get(0));
				System.out.println(" fileinput " + fileinput.getName());
				saveContents.saveOrderReturnDetails(files.get(0), sellerId,
						applicationPath);
			} catch (Exception e) {
				System.out.println("Inside exception , filetype not accepted "
						+ e.getLocalizedMessage());

			}

		}

		return "returnUploadSuccess";

	}

}