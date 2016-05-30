package com.o2r.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.o2r.bean.PaymentUploadBean;
import com.o2r.helper.ConverterClass;
import com.o2r.helper.CustomException;
import com.o2r.helper.FileUploadForm;
import com.o2r.helper.HelperClass;
import com.o2r.helper.SaveContents;
import com.o2r.helper.ValidateUpload;
import com.o2r.model.Order;
import com.o2r.model.OrderPayment;
import com.o2r.model.Partner;
import com.o2r.model.PaymentUpload;
import com.o2r.model.UploadReport;
import com.o2r.service.DownloadService;
import com.o2r.service.ManualChargesService;
import com.o2r.service.OrderService;
import com.o2r.service.PartnerService;
import com.o2r.service.PaymentUploadService;

/**
 * @author Deep Mehrotra
 *
 */
@Controller
public class UploadController {

	@Autowired
	private PaymentUploadService paymentUploadService;
	@Autowired
	private OrderService orderService;
	@Resource(name = "downloadService")
	private DownloadService downloadService;
	@Resource(name = "saveContents")
	private SaveContents saveContents;
	@Autowired
	private PartnerService partnerService;
	@Autowired
	private ManualChargesService manualChargesService;
	@Autowired
	private HelperClass helperClass;

	static Logger log = Logger.getLogger(UploadController.class.getName());

	@RequestMapping(value = "/seller/paymentUploadList", method = RequestMethod.GET)
	public ModelAndView paymentUploadList(HttpServletRequest request)
			throws CustomException {

		log.info("$$$ paymentUploadList Starts : UploadController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		List<PaymentUploadBean> payuploadbeanlist = null;
		try {
			payuploadbeanlist = ConverterClass
					.prepareListofPaymentUploadBean(paymentUploadService
							.listPaymentUploads(helperClass
									.getSellerIdfromSession(request)));

			if (payuploadbeanlist != null) {

				for (PaymentUploadBean bean : payuploadbeanlist) {
					log.debug(" Positive paymein in controller : "+bean.getTotalpositivevalue());
					bean.setManualCharges(manualChargesService.getMCforPaymentID(
							bean.getUploadDesc(),
							helperClass.getSellerIdfromSession(request)));
				}
			}
		} catch (CustomException ce) {
			log.error("changeInventorygroup exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error(e);
			model.put("errorMessage", e.getCause());
			return new ModelAndView("globalErorPage", model);
		}

		model.put("payments", payuploadbeanlist);

		log.info("$$$ paymentUploadList Ends : UploadController $$$");
		return new ModelAndView("dailyactivities/paymentUploadList", model);
	}

	@RequestMapping(value = "/seller/viewPayments", method = RequestMethod.GET)
	public ModelAndView viewPayments(HttpServletRequest request) {

		log.info("$$$ viewPayments Starts : UploadController $$$");
		String uploadId = request.getParameter("uploadId");
		String manualPay = request.getParameter("manualPay");
		Map<String, Object> model = new HashMap<String, Object>();
		String manualpayid = null;
		int sellerId;
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			if (manualPay != null && manualPay.equals("true")) {
				if (paymentUploadService.getManualPayment(sellerId) != null)
					manualpayid = String.valueOf(paymentUploadService
							.getManualPayment(sellerId).getUploadId());
				model.put("uploadId", manualpayid);
			} else {
				model.put("uploadId", uploadId);
			}
		} catch (CustomException ce) {
			log.error("viewPayment exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed!",e);
			model.put("errorMessage", e.getCause());
			return new ModelAndView("globalErorPage", model);
		}
		log.info("$$$ viewPayments Ends : UploadController $$$");
		return new ModelAndView("dailyactivities/orderPaymentDetails", model);
	}

	@RequestMapping(value = "/seller/paymentDetails", method = RequestMethod.POST)
	public @ResponseBody String viewPaymentDetails(HttpServletRequest request) {

		log.info("$$$ viewPaymentDetails Starts : UploadController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		PaymentUpload payment = null;
		List<OrderBean> orderlist = null;
		String action = request.getParameter("action");
		String uploadId = request.getParameter("uploadId");
		try {
			if (action != null && action.equals("list") && uploadId != null
					&& uploadId.length() != 0) {
				payment = paymentUploadService.getPaymentUpload(Integer
						.parseInt(uploadId));
				orderlist = ConverterClass.prepareListofBean(payment
						.getOrders());

			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("FAiled!",e);
		}
		model.put("Result", "OK");
		model.put("Records", orderlist);

		// Convert Java Object to Json
		String jsonArray = gson.toJson(model);
		log.info("$$$ viewPaymentDetails Ends : UploadController $$$");
		return jsonArray;
	}

	/**
	 * Downloads the report as an Excel format.
	 * <p>
	 * Make sure this method doesn't return any model. Otherwise, you'll get an
	 * "IllegalStateException: getOutputStream() has already been called for this response"
	 */
	@RequestMapping(value = "/seller/download/paymentXls", method = RequestMethod.GET)
	public void getXLS(HttpServletResponse response)
			throws ClassNotFoundException {

		// Delegate to downloadService. Make sure to pass an instance of
		// HttpServletResponse
		downloadService.downloadPaymentXLS(response);
	}

	/**
	 * Redirect to upload download page.
	 * <p>
	 */
	@RequestMapping(value = "/seller/orderPaymentSheet", method = RequestMethod.GET)
	public String displayForm() {

		return "dailyactivities/payment_upload_form";
	}

	@RequestMapping(value = "/seller/addManualPayment", method = RequestMethod.GET)
	public ModelAndView addManualPayment(HttpServletRequest request,
			@ModelAttribute("command") OrderBean orderBean, BindingResult result) {

		log.info("$$$ addManualPayment Starts : UploadController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		Map<Integer, String> orderIdmap = new HashMap<>();
		Map<String, String> partnermap = new HashMap<>();
		try {
			int sellerId = helperClass.getSellerIdfromSession(request);
			List<Order> orderlist = orderService.listOrders(sellerId);
			for (Order order : orderlist) {
				orderIdmap.put(order.getOrderId(), order.getChannelOrderID());
			}
			List<Partner> partnerList = partnerService.listPartners(sellerId);
			for (Partner partner : partnerList) {
				partnermap.put(partner.getPcName(), partner.getPcName());
			}
		} catch (CustomException ce) {
			log.error("addManualPayment exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed!",e);
		}
		model.put("orderIdmap", orderIdmap);
		model.put("partnermap", partnermap);
		log.info("$$$ addManualPayment Ends : UploadController $$$");
		return new ModelAndView("dailyactivities/addManualPayment", model);
	}

	@RequestMapping(value = "/seller/saveManualPayment", method = RequestMethod.POST)
	public ModelAndView saveManualPayment(HttpServletRequest request,
			@ModelAttribute("command") OrderBean orderBean, BindingResult result) {

		log.info("$$$ saveManualPayment Starts : UploadController $$$");
		int sellerId;
		Order order = null;
		PaymentUpload paymentUpload = null;
		OrderPayment payment = new OrderPayment();
		Map<String, Object> model = new HashMap<String, Object>();
		log.debug(" channelOrderId " + orderBean.getChannelOrderID());
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			if ((int) orderBean.getOrderPayment().getNegativeAmount() != 0) {

				payment.setNegativeAmount(Math.abs(orderBean.getOrderPayment()
						.getNegativeAmount()));
			}

			else {
				payment.setPositiveAmount(orderBean.getOrderPayment()
						.getPositiveAmount());

			}
			payment.setDateofPayment(orderBean.getOrderPayment()
					.getDateofPayment());
			log.debug("order id in payment controller : "
					+ orderBean.getOrderId());
			order = orderService.addOrderPayment(orderBean.getOrderId(),
					payment, sellerId);
			
			paymentUpload = paymentUploadService.getManualPayment(sellerId);
			if (paymentUpload == null) {
				paymentUpload = new PaymentUpload();
				paymentUpload
						.setTotalpositivevalue(payment.getPositiveAmount());
				paymentUpload
						.setTotalnegativevalue(payment.getNegativeAmount());
				paymentUpload.setNetRecievedAmount(payment.getPositiveAmount()
						- payment.getNegativeAmount());
				paymentUpload.setUploadDesc("Manual Upload");
				paymentUpload.setUploadStatus("Success");

			} else {
				paymentUpload.setTotalpositivevalue(paymentUpload
						.getTotalpositivevalue() + payment.getPositiveAmount());
				paymentUpload.setTotalnegativevalue(paymentUpload
						.getTotalnegativevalue() + payment.getNegativeAmount());
				paymentUpload.setNetRecievedAmount(paymentUpload
						.getTotalpositivevalue()
						- paymentUpload.getTotalnegativevalue());
				paymentUpload.setUploadStatus("Success");
			}
			if (order != null) {
				order.setPaymentUpload(paymentUpload);
				paymentUpload.getOrders().add(order);
			}

			paymentUploadService.addPaymentUpload(paymentUpload, sellerId);
		} catch (CustomException ce) {
			log.error("changeInventorygroup exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed!",e);
		}

		log.info("$$$ saveManualPayment Ends : UploadController $$$");
		return new ModelAndView("redirect:/seller/paymentUploadList.html");
	}

	@RequestMapping(value = "/seller/savePaymentUpload", method = RequestMethod.POST)
	public ModelAndView save(HttpServletRequest request,
			@ModelAttribute("uploadForm") FileUploadForm uploadForm, Model map) {

		log.info("$$$ save() Starts : UploadController $$$");
		// gets absolute path of the web application
		String applicationPath = request.getServletContext().getRealPath("");
		// System.out.println("Inside save method");
		List<MultipartFile> files = uploadForm.getFiles();
		UploadReport uploadReport = new UploadReport();

		List<String> fileNames = new ArrayList<String>();
		MultipartFile fileinput = files.get(0);
		int sellerId;
		System.out.println(" got file");
		if (null != files && files.size() > 0) {
			fileNames.add(files.get(0).getOriginalFilename());
			try {
				sellerId = helperClass.getSellerIdfromSession(request);
				System.out.println(" Filename : "
						+ files.get(0).getOriginalFilename());
				System.out.println(" Filename : " + files.get(0).getName());
				ValidateUpload.validateOfficeData(files.get(0));
				System.out.println(" fileinput " + fileinput.getName());
				saveContents.savePaymentContents(files.get(0), sellerId,
						applicationPath, uploadReport);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Failed!",e);
				log.debug("Inside exception , filetype not accepted "
						+ e.getLocalizedMessage());

			}
		}
		log.info("$$$ save() Ends : UploadController $$$");
		return new ModelAndView("dailyactivities/dailyactivities");

	}

}