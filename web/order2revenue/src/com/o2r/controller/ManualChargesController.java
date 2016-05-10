package com.o2r.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.o2r.helper.ConverterClass;
import com.o2r.helper.CustomException;
import com.o2r.helper.DateDeserializer;
import com.o2r.helper.DateSerializer;
import com.o2r.helper.HelperClass;
import com.o2r.model.ManualCharges;
import com.o2r.model.Partner;
import com.o2r.model.PaymentUpload;
import com.o2r.model.TaxDetail;
import com.o2r.service.ManualChargesService;
import com.o2r.service.PartnerService;
import com.o2r.service.PaymentUploadService;
import com.o2r.service.TaxDetailService;

/**
 * @author Deep Mehrotra
 *
 */
@Controller
public class ManualChargesController {

	@Autowired
	private ManualChargesService manualChargesService;

	@Autowired
	private TaxDetailService taxDetailService;

	@Autowired
	private PartnerService partnerService;

	@Autowired
	private PaymentUploadService paymentUploadService;

	static Logger log = Logger.getLogger(ManualChargesController.class.getName());

	@RequestMapping(value = "/seller/saveManualChargesJson", method = RequestMethod.POST)
	public @ResponseBody String saveManualChargesJson(HttpServletRequest request) {
		log.info("*** saveManualChargesJson start ***");
		Map<String, Object> model = new HashMap<String, Object>();
		ManualCharges manualCharges = new ManualCharges();

		// implementing gson date deserializer
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
		gsonBuilder.registerTypeAdapter(Date.class, new DateSerializer());
		Gson gson = gsonBuilder.setPrettyPrinting().create();

		System.out.println("mcId id " + request.getParameter("mcId"));
		int mcId = 0;
		int sellerId;
		try{
		sellerId = HelperClass.getSellerIdfromSession(request);
		if (request.getParameter("mcId") != null
				&& request.getParameter("mcId").toString().length() != 0) {
			mcId = Integer.parseInt(request.getParameter("mcId"));
		}
		System.out.println(" paidAmouny :  "+request.getParameter("paidAmount"));
		String chargesCategory = request.getParameter("chargesCategory") != null ? request
				.getParameter("chargesCategory") : "";
		String chargesDesc = request.getParameter("chargesDesc") != null ? request
				.getParameter("chargesDesc") : "";
		String partner = request.getParameter("partner") != null ? request
				.getParameter("partner") : "";
		String particular = request.getParameter("particular") != null ? request
				.getParameter("particular") : "";
		String paymentCycle = request.getParameter("paymentCycle") != null ? request
				.getParameter("paymentCycle") : "";
		Double paidAmount = request.getParameter("paidAmount") != null ? Double
				.parseDouble(request.getParameter("paidAmount")) : 0;
		Date dateOfPayment = request.getParameter("dateOfPayment") != null ? new Date(
				request.getParameter("dateOfPayment").toString()) : new Date();

		if (mcId != 0) {
			manualCharges.setMcId(mcId);
			;

		} else {
			manualCharges.setUploadDate(new Date());
		}
		manualCharges.setChargesCategory(chargesCategory);
		manualCharges.setChargesDesc(chargesDesc);
		manualCharges.setDateOfPayment(dateOfPayment);
		manualCharges.setPaidAmount(paidAmount);
		manualCharges.setParticular(particular);
		manualCharges.setPartner(partner);
		manualCharges.setPaymentCycle(paymentCycle);
		manualChargesService.addManualCharges(manualCharges, sellerId);

		model.put("Result", "OK");
		model.put("Record", ConverterClass.prepareManualChargesBean(manualCharges));
		}catch(CustomException ce){
			log.error("saveManualChargesJson exception : " + ce.toString());
			model.put("error", ce.getLocalMessage());
			String errors = gson.toJson(model);
			return errors;
		}catch(Exception e){
			log.error(e);
			log.info("Error :",e);
		}
		String jsonArray = gson.toJson(model);
		log.info("*** saveManualChargesJson Exit ***");
		return jsonArray;

	}

	@RequestMapping(value = "/seller/saveTaxDetailsJson", method = RequestMethod.POST)
	public @ResponseBody String saveTaxDetailJson(HttpServletRequest request) {
		
		log.info("*** saveTaxDetailsJson start ***");
		Map<String, Object> model = new HashMap<String, Object>();
		// implementing gson date deserializer

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
		gsonBuilder.registerTypeAdapter(Date.class, new DateSerializer());
		Gson gson = gsonBuilder.setPrettyPrinting().create();
		TaxDetail taxDetail = new TaxDetail();
		System.out.println("taxId id " + request.getParameter("taxId"));
		int taxId = 0;
		int sellerId;
		try{
		sellerId = HelperClass.getSellerIdfromSession(request);
		if (request.getParameter("taxId") != null
				&& request.getParameter("taxId").toString().length() != 0) {
			taxId = Integer.parseInt(request.getParameter("taxId"));
		}
		String taxortds = request.getParameter("taxortds") != null ? request
				.getParameter("taxortds") : "";
		String description = request.getParameter("description") != null ? request
				.getParameter("description") : "";
		String status = request.getParameter("status") != null ? request
				.getParameter("status") : "";
		String taxortdsCycle = request.getParameter("taxortdsCycle") != null ? request
				.getParameter("taxortdsCycle") : "";
		String particular = request.getParameter("particular") != null ? request
				.getParameter("particular") : "";
		Double paidAmount = request.getParameter("paidAmount") != null ? Double
				.parseDouble(request.getParameter("paidAmount")) : 0;
		Double balanceRemaining = request.getParameter("balanceRemaining") != null ? Double
				.parseDouble(request.getParameter("balanceRemaining")) : 0;
		Date dateOfPayment = request.getParameter("dateOfPayment") != null ? new Date(
				request.getParameter("dateOfPayment").toString()) : new Date();
		System.out.println("while saving taxortds : " + taxortds);
		if (taxId != 0) {
			taxDetail.setTaxId(taxId);

		} else {
			taxDetail.setUploadDate(new Date());
		}
		taxDetail.setTaxId(taxId);
		taxDetail.setBalanceRemaining(balanceRemaining);
		taxDetail.setDateOfPayment(dateOfPayment);
		taxDetail.setDescription(description);
		taxDetail.setPaidAmount(paidAmount);
		taxDetail.setParticular(particular);
		taxDetail.setStatus(status);
		taxDetail.setTaxortds(taxortds);
		taxDetail.setTaxortdsCycle(taxortdsCycle);
		taxDetailService.addTaxDetail(taxDetail, sellerId);

		model.put("Result", "OK");
		model.put("Record", ConverterClass.prepareTaxDetailBean(taxDetail));
		// model.put("Records",ConverterClass.prepareListofTaxDetailBean(taxDetailService.listTaxDetails(sellerId)));
		
		}catch(CustomException ce){
			log.error("savetaxDetailsJson exception : " + ce.toString());
			model.put("error", ce.getLocalMessage());
			String errors = gson.toJson(model);
			return errors;
		}catch(Exception e){
			log.error(e);
			log.info("Error :",e);
		}
		String jsonArray = gson.toJson(model);
		log.info("*** saveTaxDetailsJson Exit ***");
		return jsonArray;
	}

	@RequestMapping(value = "/seller/savePaidTaxDetailJson", method = RequestMethod.POST)
	public @ResponseBody String savePaidTaxDetailJson(HttpServletRequest request) {
		log.info("*****savePaidTaxDetailJson  Start**");
		Map<String, Object> model = new HashMap<String, Object>();
		Gson gson = null;
		// implementing gson date deserializer

		GsonBuilder gsonBuilder = new GsonBuilder();
		try {
			gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
			gsonBuilder.registerTypeAdapter(Date.class, new DateSerializer());
			gson = gsonBuilder.setPrettyPrinting().create();
			TaxDetail taxDetail = new TaxDetail();
			System.out.println("taxId id " + request.getParameter("taxId"));
			int taxId = 0;
			int sellerId = HelperClass.getSellerIdfromSession(request);
			if (request.getParameter("taxId") != null
					&& request.getParameter("taxId").toString().length() != 0) {
				taxId = Integer.parseInt(request.getParameter("taxId"));
			}
			if (request.getParameter("status") != null
					&& request.getParameter("paidAmount") != null) {
				String status = request.getParameter("status") != null ? request
						.getParameter("status") : "";
				Double paidAmount = request.getParameter("paidAmount") != null ? Double
						.parseDouble(request.getParameter("paidAmount")) : 0;
				System.out.println("while saving taxortds : " + status);
				if (taxId != 0) {
					taxDetail.setTaxId(taxId);

				}

				taxDetail.setTaxId(taxId);
				taxDetail.setDateOfPayment(new Date());
				taxDetail.setPaidAmount(paidAmount);
				taxDetail.setStatus(status);
				taxDetailService.addPaymentTaxDetail(taxDetail, sellerId);

				model.put("Result", "OK");
				model.put("Record", ConverterClass
						.prepareTaxDetailBean(taxDetailService
								.getTaxDetail(taxId)));
			}
		} catch (Throwable e) {
			log.error(e);
			model.put("Result", "ERROR");
			model.put("Message", "Error in saving Tax Detail Status");
		}

		// model.put("Records",ConverterClass.prepareListofTaxDetailBean(taxDetailService.listTaxDetails(sellerId)));
		String jsonArray = gson.toJson(model);
		log.info("*****savePaidTaxDetailJson  Exit**");
		return jsonArray;
	}

	@RequestMapping(value = "/seller/taxDetailList", method = RequestMethod.GET)
	public String taxDetailList() {

		return "miscellaneous/taxdetails";
	}

	@RequestMapping(value = "/seller/tdsDetailList", method = RequestMethod.GET)
	public String tdsDetailList() {

		return "miscellaneous/tdsDetails";
	}

	@RequestMapping(value = "/seller/manualCharges", method = RequestMethod.GET)
	public String manualChargesList() {

		return "miscellaneous/manualcharges";
	}

	@RequestMapping(value = "/seller/listManualChargesJson", method = RequestMethod.POST)
	public @ResponseBody String listManualChargesJson(HttpServletRequest request) {
		
		log.info("*** listManualChargesJson start ***");
		int sellerId;
		Map<String, Object> model = new HashMap<String, Object>();
		// Gson gson = new GsonBuilder().setPrettyPrinting().create();

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
		gsonBuilder.registerTypeAdapter(Date.class, new DateSerializer());

		Gson gson = gsonBuilder.setPrettyPrinting().create();
		try{
		sellerId = HelperClass.getSellerIdfromSession(request);
		model.put("Records", ConverterClass
				.prepareListofManualChargesBean(manualChargesService
						.listManualCharges(sellerId)));
		model.put("Result", "OK");
		}catch(CustomException ce){
			log.error("listManualChargesJson exception : " + ce.toString());
			model.put("error", ce.getLocalMessage());
			String errors = gson.toJson(model);
			return errors;
		}catch (Exception e) {
			log.error(e);
			log.info("Error :",e);
		}
		String jsonArray = gson.toJson(model);
		log.info("*** listManualChargesJson exit ***");
		return jsonArray;
	}

	@RequestMapping(value = "/seller/listTaxDetailsJson", method = RequestMethod.POST)
	public @ResponseBody String listtaxDetailsJson(HttpServletRequest request) {
		
		log.info("*****listtaxDetailsJson  Start**");
		Map<String, Object> model = new HashMap<String, Object>();
		int sellerId;
		Gson gson = null;
		GsonBuilder gsonBuilder = new GsonBuilder();
		try {
			sellerId = HelperClass.getSellerIdfromSession(request);
			gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
			gsonBuilder.registerTypeAdapter(Date.class, new DateSerializer());

			gson = gsonBuilder.setPrettyPrinting().create();
			model.put("Records", ConverterClass
					.prepareListofTaxDetailBean(taxDetailService
							.listTaxDetails(sellerId, "Tax")));
			model.put("Result", "OK");
		}catch(CustomException ce){
			log.error("listtxDetails exception : " + ce.toString());
			model.put("error", ce.getLocalMessage());
			String errors = gson.toJson(model);
			return errors;
		}catch (Throwable e) {
			log.error(e);
			model.put("Result", "ERROR");
			model.put("Message", "Error in getting Tax Details");			
		}

		String jsonArray = gson.toJson(model);
		log.info("*****listtaxDetailsJson  Exit**");
		return jsonArray;
	}

	@RequestMapping(value = "/seller/listTdsDetailsJson", method = RequestMethod.POST)
	public @ResponseBody String listTdsDetailsJson(HttpServletRequest request) {
		log.info("*****listTdsDetailsJson  Start**");
		Map<String, Object> model = new HashMap<String, Object>();
		Gson gson = null;
		try {

			GsonBuilder gsonBuilder = new GsonBuilder();
			int sellerId = HelperClass.getSellerIdfromSession(request);
			gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
			gsonBuilder.registerTypeAdapter(Date.class, new DateSerializer());

			gson = gsonBuilder.setPrettyPrinting().create();
			model.put("Records", ConverterClass
					.prepareListofTaxDetailBean(taxDetailService
							.listTaxDetails(sellerId, "TDS")));
			model.put("Result", "OK");
		} catch (Throwable e) {
			log.error(e);
			model.put("Result", "ERROR");
			model.put("Message", "Error in getting TDS Details");
		}

		String jsonArray = gson.toJson(model);
		log.info("*****listTdsDetailsJson  Exit**");
		return jsonArray;
	}

	@RequestMapping(value = "/seller/deleteTaxDetailsJson", method = RequestMethod.POST)
	public @ResponseBody String deleteTaxDetailsJson(HttpServletRequest request) {
		
		log.info("*** deleteTaxDetailsJson start ***");
		int sellerId;
		Map<String, Object> model = new HashMap<String, Object>();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		TaxDetail taxDetail = new TaxDetail();
		System.out.println("taxId id " + request.getParameter("taxId"));
		int taxId = 0;
		try{
		sellerId = HelperClass.getSellerIdfromSession(request);
		if (request.getParameter("taxId") != null
				&& request.getParameter("taxId").toString().length() != 0) {
			taxId = Integer.parseInt(request.getParameter("taxId"));
		}
		if (taxId != 0) {
			taxDetail.setTaxId(taxId);

		}

		taxDetailService.deleteTaxDetail(taxDetail, sellerId);

		model.put("Result", "OK");
		// model.put("Records",ConverterClass.prepareTaxDetailBean(taxDetail));
		// model.put("Records",ConverterClass.prepareListofTaxDetailBean(taxDetailService.listTaxDetails(sellerId)));
		}catch(Exception e){
			log.error(e);
			model.put("Error", e.getLocalizedMessage());
		}
		String jsonArray = gson.toJson(model);
		log.info("*** deleteTaxDetailsJson exit ***");
		return jsonArray;
	}

	@RequestMapping(value = "/seller/deleteManualChargesJson", method = RequestMethod.POST)
	public @ResponseBody String deleteManualChargesJson(
			HttpServletRequest request) {
		
		log.info("*** deleteManualChargesJson start ***");
		Map<String, Object> model = new HashMap<String, Object>();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		ManualCharges manualCharges = new ManualCharges();
		System.out.println("mcId id " + request.getParameter("mcId"));
		int mcId = 0;
		int sellerId;
		try{
		sellerId = HelperClass.getSellerIdfromSession(request);
		if (request.getParameter("mcId") != null
				&& request.getParameter("mcId").toString().length() != 0) {
			mcId = Integer.parseInt(request.getParameter("mcId"));
		}
		if (mcId != 0) {
			manualCharges.setMcId(mcId);
			;

		}
		manualChargesService.deleteManualCharges(manualCharges, sellerId);
		model.put("Result", "OK");
		}catch(CustomException ce){
			log.error("deleteManualChargesJson exception : " + ce.toString());
			model.put("error", ce.getLocalMessage());
			String errors = gson.toJson(model);
			return errors;
		}catch (Exception e) {
			log.error(e);
			model.put("Error", e.getLocalizedMessage());
		}
		// model.put("Records",ConverterClass.prepareListofManualChargesBean(manualChargesService.listManualCharges(sellerId)));
		String jsonArray = gson.toJson(model);
		log.info("*** deleteManualChargesJson exit ***");
		return jsonArray;
	}

	@RequestMapping(value = "/seller/getPartnerForMC", method = RequestMethod.POST)
	public @ResponseBody String gtePartnerForMC(HttpServletRequest request) {
		int sellerId;
		log.info("*** getPartnerForMC start ***");
		Map<String, Object> model = new HashMap<String, Object>();
		// Gson gson = new GsonBuilder().setPrettyPrinting().create();

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
		gsonBuilder.registerTypeAdapter(Date.class, new DateSerializer());
		Gson gson = gsonBuilder.setPrettyPrinting().create();

		JSONArray ja = new JSONArray();

		JSONObject otherjo = new JSONObject();
		otherjo.put("DisplayText", "Others");
		otherjo.put("Value", "Others");
		ja.add(otherjo);
		List<Partner> partnerList;
		Map<String, String> partnerMap = new HashMap<>();
		try{
		sellerId = HelperClass.getSellerIdfromSession(request);
		partnerList = partnerService.listPartners(sellerId);
		for (Partner partner : partnerList) {
			System.out.println(" Partner name : " + partner.getPcName());
			JSONObject jo = new JSONObject();
			jo.put("DisplayText", partner.getPcName());
			jo.put("Value", partner.getPcName());
			ja.add(jo);

		}
		model.put("Options", ja);
		model.put("Result", "OK");
		}catch(CustomException ce){
			log.error("getPartnerFromMC exception : " + ce.toString());
			model.put("error", ce.getLocalMessage());
			String errors = gson.toJson(model);
			return errors;
		}catch (Exception e) {
			log.error(e);
			model.put("Error ", e.getLocalizedMessage());
		}
		String jsonArray = gson.toJson(model);
		log.info("*** getPartnerForMC exit ***");
		return jsonArray;
	}

	@RequestMapping(value = "/seller/getPaymentIdForMC", method = RequestMethod.POST)
	public @ResponseBody String getPaymentIdForMC(HttpServletRequest request) {
		
		log.info("*** getPaymentIdForMC start ***");
		int sellerId;		
		Map<String, Object> model = new HashMap<String, Object>();
		// Gson gson = new GsonBuilder().setPrettyPrinting().create();

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
		gsonBuilder.registerTypeAdapter(Date.class, new DateSerializer());
		Gson gson = gsonBuilder.setPrettyPrinting().create();

		JSONArray ja = new JSONArray();

		JSONObject otherjo = new JSONObject();
		otherjo.put("DisplayText", "Others");
		otherjo.put("Value", "Others");
		ja.add(otherjo);

		Map<String, String> partnerMap = new HashMap<>();
		List<PaymentUpload> paymentUploadList;
		
		try{
		sellerId = HelperClass.getSellerIdfromSession(request);
		paymentUploadList = paymentUploadService.listPaymentUploads(sellerId);
		if (paymentUploadList != null) {
			for (PaymentUpload upload : paymentUploadList) {
				System.out.println(" Pauyment upload id  name : "
						+ upload.getUploadDesc());
				JSONObject jo = new JSONObject();
				jo.put("DisplayText", upload.getUploadDesc());
				jo.put("Value", upload.getUploadDesc());
				ja.add(jo);

			}
		}
		model.put("Options", ja);
		model.put("Result", "OK");
		}catch(CustomException ce){
			log.error("getPaymantIdForMC exception : " + ce.toString());
			model.put("error", ce.getLocalMessage());
			String errors = gson.toJson(model);
			return errors;
		}catch (Exception e) {
			log.error(e);
			model.put("Error", e.getLocalizedMessage());
		}
		String jsonArray = gson.toJson(model);
		log.info("*** getPaymentIdForMC exit ***");
		return jsonArray;
	}

}