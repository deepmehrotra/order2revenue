package com.o2r.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
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
import org.springframework.web.servlet.ModelAndView;

import com.o2r.bean.PartnerBean;
import com.o2r.helper.ConverterClass;
import com.o2r.helper.CustomException;
import com.o2r.helper.GlobalConstant;
import com.o2r.helper.HelperClass;
import com.o2r.model.Category;
import com.o2r.model.NRnReturnCharges;
import com.o2r.model.Partner;
import com.o2r.model.TaxCategory;
import com.o2r.service.CategoryService;
import com.o2r.service.PartnerService;
import com.o2r.service.TaxDetailService;

/**
 * @author Deep Mehrotra
 *
 */
@Controller
public class PartnerController {

	@Autowired
	private PartnerService partnerService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private HelperClass helperClass;
	@Autowired
	private TaxDetailService taxDetailService;

	@Autowired
	ServletContext context;
	Properties props = null;
	org.springframework.core.io.Resource resource = new ClassPathResource(
			"database.properties");
	ArrayList<String> partnerList = new ArrayList<String>() {
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		{
			add("Amazon");
			add("Flipkart");
			add("Snapdeal");
			add("ebay");
			add("ShopClues");
			add("Jabong");
			add("Myntra");
		}
	};

	static Logger log = Logger.getLogger(PartnerController.class.getName());

	List<NRnReturnCharges> chargeList = new ArrayList<NRnReturnCharges>();

	@RequestMapping(value = "/seller/savePartner", method = RequestMethod.POST)
	public ModelAndView savePartner(HttpServletRequest request,
			@ModelAttribute("command") PartnerBean partnerBean,
			BindingResult result,
			@RequestParam(value = "image", required = false) MultipartFile image) {

		log.info("$$$ savePartner Starts : OrderController $$$");
		if (partnerBean.getPcId() != 0) {
			log.debug("******** ConfigId : "
					+ partnerBean.getNrnReturnConfig().getConfigId());
			log.debug("******** Partner ID : "
					+ partnerBean.getPcId());

		}

		log.debug(" Nr calculayor value from bean : "
				+ partnerBean.getNrnReturnConfig().isNrCalculator());
		Map<String, String[]> parameters = request.getParameterMap();
		for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
			if (entry.getKey().contains("nr-")) {
				log.debug(" Key with nr: " + entry.getKey()
						+ " Values is : " + entry.getValue()[0]);
			}
		}
		for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
			if (entry.getKey() != null && !entry.getKey().isEmpty())
				log.debug(" Print :entry.getKey()  " + entry.getKey()
						+ " value: " + entry.getValue()[0]);

			if (entry.getValue()[0] != null && !entry.getValue()[0].isEmpty()) {
				if (entry.getKey().contains("nr-")) {
					log.debug(" Key : " + entry.getKey());
					String temp = entry.getKey().substring(3);
					NRnReturnCharges nrnReturncharge = new NRnReturnCharges();
					nrnReturncharge.setChargeAmount(Float.parseFloat(entry
							.getValue()[0]));
					nrnReturncharge.setChargeName(temp);
					nrnReturncharge.setConfig(partnerBean.getNrnReturnConfig());
					partnerBean.getNrnReturnConfig().getCharges()
							.add(nrnReturncharge);
				} else if (entry.getKey().contains("local")) {

					String localstring = Arrays.toString(entry.getValue());
					log.debug("localstring " + localstring);
					partnerBean.getNrnReturnConfig().setLocalList(
							localstring.substring(localstring.toString()
									.indexOf('[') + 1, localstring.toString()
									.indexOf(']')));
				} else if (entry.getKey().contains("zonal")) {
					String zonalstring = Arrays.toString(entry.getValue());
					log.debug("zonalstring " + zonalstring);

					partnerBean.getNrnReturnConfig().setZonalList(
							zonalstring.substring(zonalstring.toString()
									.indexOf('[') + 1, zonalstring.toString()
									.indexOf(']')));
				} else if (entry.getKey().contains("national")) {
					String nationalstring = Arrays.toString(entry.getValue());
					partnerBean.getNrnReturnConfig().setNationalList(
							nationalstring.substring(nationalstring.toString()
									.indexOf('[') + 1, nationalstring
									.toString().indexOf(']')));
				} else if (entry.getKey().contains("metro")) {
					String metrostring = Arrays.toString(entry.getValue());
					log.debug("metrostring " + metrostring);

					partnerBean.getNrnReturnConfig().setMetroList(
							metrostring.substring(metrostring.toString()
									.indexOf('[') + 1, metrostring.toString()
									.indexOf(']')));
				}
			}
		}

		if (!partnerBean.isIsshippeddatecalc()) {
			partnerBean.setNoofdaysfromshippeddate(partnerBean
					.getNoofdaysfromdeliverydate());
		}
		
		if (image.getSize() != 0) {
			if (image != null) {
				if (!image.isEmpty()) {
					try {
						validateImage(image);

					} catch (RuntimeException re) {
						result.reject(re.getMessage());
					}
				}
			}
			try {
				props = PropertiesLoaderUtils.loadProperties(resource);

				if (!partnerList.contains(partnerBean.getPcName())) {
					partnerBean.setPcLogoUrl(props
							.getProperty("partnerimage.view")
							+ helperClass.getSellerIdfromSession(request)
							+ partnerBean.getPcName() + ".jpg");
					saveImage(helperClass.getSellerIdfromSession(request)
							+ partnerBean.getPcName() + ".jpg", image);
				} else {
					partnerBean.setPcLogoUrl(props
							.getProperty("partnerimage.view")
							+ partnerBean.getPcName() + ".jpg");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Failed!",e);
				result.reject(e.getMessage());
				return new ModelAndView("redirect:/seller/partners.html");
			}
		}
		try {
			Partner partner = ConverterClass.preparePartnerModel(partnerBean);
			partnerService.addPartner(partner,
					helperClass.getSellerIdfromSession(request));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed!",e);
		}

		log.info("$$$ savePartner Ends : OrderController $$$");
		return new ModelAndView("redirect:/seller/partners.html");
	}

	@RequestMapping(value = "/seller/listPartners", method = RequestMethod.GET)
	public ModelAndView listAllPartners(HttpServletRequest request) {
		
		log.info("$$$ listAllPartners Starts : OrderController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		List<PartnerBean> addedlist = null;
		try {
			addedlist = ConverterClass.prepareListofPartnerBean(partnerService
					.listPartners(helperClass.getSellerIdfromSession(request)));
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getCause());
		}
		model.put("partners", addedlist);
		log.info("$$$ listAllPartners Ends : OrderController $$$");
		return new ModelAndView("initialsetup/partnerDetail", model);
	}

	@RequestMapping(value = "/seller/partners", method = RequestMethod.GET)
	public ModelAndView listPartners(HttpServletRequest request) {

		log.info("$$$ listPartners Starts : OrderController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		List<PartnerBean> toAddPartner = new ArrayList<PartnerBean>();
		List<PartnerBean> addedlist = null;
		try {
			addedlist = ConverterClass.prepareListofPartnerBean(partnerService
					.listPartners(helperClass.getSellerIdfromSession(request)));

			props = PropertiesLoaderUtils.loadProperties(resource);

			for (String partner : partnerList) {
				if (partnerService.getPartner(partner,
						helperClass.getSellerIdfromSession(request)) == null) {
					String pcUrl = props.getProperty("partnerimage.view")
							+ partner + ".jpg";
					log.debug(" Pc logourl set to partner baen "
							+ pcUrl);
					toAddPartner.add(new PartnerBean(partner, partner, pcUrl));
				}
			}
		} catch (CustomException ce) {
			log.error("listPartners exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed!",e);
		}
		model.put("partners", addedlist);
		model.put("partnertoadd", toAddPartner);
		System.out.println(" toaddpartner size  :" + toAddPartner.size());
		log.info("$$$ listPartners Ends : OrderController $$$");
		return new ModelAndView("initialsetup/partnerInfo", model);
	}

	@RequestMapping(value = "/seller/addPartner", method = RequestMethod.GET)
	public ModelAndView addPartner(HttpServletRequest request,
			@ModelAttribute("command") PartnerBean partnerBean,
			BindingResult result) {

		log.info("$$$ addPartner Starts : OrderController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> datemap = new LinkedHashMap<String, Object>();
		List<String> categoryList = new ArrayList<String>();
		List<Category> categoryObjects = null;
		datemap.put("true", "Select payment from");
		datemap.put("true", "Shipping Date");
		datemap.put("false", "Delivery Date");
		PartnerBean partner = new PartnerBean();
		try {
			categoryObjects = categoryService.listCategories(helperClass
					.getSellerIdfromSession(request));
			if (categoryObjects != null)
				for (Category cat : categoryObjects) {
					categoryList.add(cat.getCatName());
				}
		} catch (CustomException ce) {
			log.error("addPartner exception : " + ce.toString());
			ce.printStackTrace();
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed!",e);
		}
		String partnerName = request.getParameter("partnerName");
		try {
			if (partnerName != null) {
				partner.setPcName(partnerName);
			}
			model.put("partner", partner);
			model.put("categoryList", categoryList);
			model.put("datemap", datemap);
			model.put("partners", ConverterClass
					.prepareListofPartnerBean(partnerService
							.listPartners(helperClass
									.getSellerIdfromSession(request))));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed!",e);
		}
		log.info("$$$ addPartner Ends : OrderController $$$");
		return new ModelAndView("initialsetup/addPartner", model);
	}

	/* My Code Bishnu */

	@RequestMapping(value = "/seller/saveJabong", method = RequestMethod.POST)
	public ModelAndView saveJabong(HttpServletRequest request,
			@ModelAttribute("command") PartnerBean partnerBean,
			BindingResult result,
			@RequestParam(value = "image", required = false) MultipartFile image) {

		log.info("$$$ saveJabong Starts : OrderController $$$");
		log.debug(" Nr calculayor value from bean : "
				+ partnerBean.getNrnReturnConfig().isNrCalculator());

		log.debug(" Nr calculayor value from bean : "+partnerBean.getNrnReturnConfig().isNrCalculator());
		partnerBean.setPcName("Jabong");

		Map<String, String[]> parameters = request.getParameterMap();
		for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
			System.out.println(" Request Param: " + entry.getKey()
					+ "  Values  : " + entry.getValue().length);

			if (entry.getKey() != null && !entry.getKey().isEmpty())
				System.out.println(" Print : " + entry.getValue()[0]);

			if (entry.getValue()[0] != null && !entry.getValue()[0].isEmpty()) {
				if (entry.getKey().contains("nr-")) {
					String temp = entry.getKey().substring(3);
					NRnReturnCharges nrnReturncharge = new NRnReturnCharges();
					nrnReturncharge.setChargeAmount(Float.parseFloat(entry
							.getValue()[0]));
					nrnReturncharge.setChargeName(temp);
					nrnReturncharge.setConfig(partnerBean.getNrnReturnConfig());
					partnerBean.getNrnReturnConfig().getCharges()
							.add(nrnReturncharge);
				}
			}
		}
		
		if (!partnerBean.isIsshippeddatecalc()) {
			partnerBean.setNoofdaysfromshippeddate(partnerBean
					.getNoofdaysfromdeliverydate());
		}

		if (image != null) {
			if (!image.isEmpty()) {
				try {
					validateImage(image);


					} catch (RuntimeException re) {
						result.reject(re.getMessage());
					}
				}
			}		

		try {
			props = PropertiesLoaderUtils.loadProperties(resource);



				if (!partnerList.contains(partnerBean.getPcName())) {
					partnerBean.setPcLogoUrl(props
							.getProperty("partnerimage.view")
							+ helperClass.getSellerIdfromSession(request)
							+ partnerBean.getPcName() + ".jpg");
					saveImage(partnerBean.getPcName() + ".jpg", image);
				} else {
					partnerBean.setPcLogoUrl(props
							.getProperty("partnerimage.view")
							+ partnerBean.getPcName() + ".jpg");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Failed!",e);
				result.reject(e.getMessage());
				return new ModelAndView("redirect:/seller/partners.html");
			}
					
		try {			

			Partner partner = ConverterClass.preparePartnerModel(partnerBean);
			partnerService.addPartner(partner,
					helperClass.getSellerIdfromSession(request));
		} catch (Exception e) {
			log.error("Failed!",e);
			e.printStackTrace();
		}

		log.info("$$$ saveJabong Ends : OrderController $$$");
		return new ModelAndView("redirect:/seller/partners.html");
	}

	@RequestMapping(value = "/seller/saveMyntra", method = RequestMethod.POST)
	public ModelAndView saveMyntra(HttpServletRequest request,
			@ModelAttribute("command") PartnerBean partnerBean,
			BindingResult result,
			@RequestParam(value = "image", required = false) MultipartFile image) {

		log.info("$$$ saveMyntra Starts : OrderController $$$");
		log.debug(" Nr calculayor value from bean : "
				+ partnerBean.getNrnReturnConfig().isNrCalculator());

		log.debug(" Nr calculayor value from bean : "+partnerBean.getNrnReturnConfig().isNrCalculator());
		partnerBean.setPcName("Myntra");

		Map<String, String[]> parameters = request.getParameterMap();
		for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
			log.debug(" Request Param: " + entry.getKey()
					+ "  Values  : " + entry.getValue().length);

			if (entry.getKey() != null && !entry.getKey().isEmpty())
				log.debug(" Print : " + entry.getValue()[0]);

			if (entry.getValue()[0] != null && !entry.getValue()[0].isEmpty()) {
				if (entry.getKey().contains("nr-")) {
					String temp = entry.getKey().substring(3);
					NRnReturnCharges nrnReturncharge = new NRnReturnCharges();
					try {
						nrnReturncharge.setChargeAmount(Float.parseFloat(entry
								.getValue()[0]));
						nrnReturncharge.setChargeName(temp);
					} catch (NumberFormatException e) {
						nrnReturncharge.setChargeAmount(1);
						nrnReturncharge.setChargeName(temp + GlobalConstant.TaxCategoryPrefix
								+ entry.getValue()[0]);
					}
					nrnReturncharge.setConfig(partnerBean.getNrnReturnConfig());
					partnerBean.getNrnReturnConfig().getCharges()
							.add(nrnReturncharge);
				}
			}
		}
		if (!partnerBean.isIsshippeddatecalc()) {
			partnerBean.setNoofdaysfromshippeddate(partnerBean
					.getNoofdaysfromdeliverydate());
		}
		if (image != null) {
			if (!image.isEmpty()) {
				try {
					validateImage(image);
					} catch (RuntimeException re) {
						re.printStackTrace();
						log.error("Failed!",re);
						result.reject(re.getMessage());
					}
				}
			}
		try {
			props = PropertiesLoaderUtils.loadProperties(resource);


				if (!partnerList.contains(partnerBean.getPcName())) {
					partnerBean.setPcLogoUrl(props
							.getProperty("partnerimage.view")
							+ helperClass.getSellerIdfromSession(request)
							+ partnerBean.getPcName() + ".jpg");
					saveImage(partnerBean.getPcName() + ".jpg", image);
				} else {
					partnerBean.setPcLogoUrl(props
							.getProperty("partnerimage.view")
							+ partnerBean.getPcName() + ".jpg");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Failed!",e);
				result.reject(e.getMessage());
				return new ModelAndView("redirect:/seller/partners.html");
			}

		try {			

			Partner partner = ConverterClass.preparePartnerModel(partnerBean);
			partnerService.addPartner(partner,
					helperClass.getSellerIdfromSession(request));
		} catch (Exception e) {
			log.error("Failed!",e);
			e.printStackTrace();
		}

		log.info("$$$ saveMyntra Ends : OrderController $$$");
		return new ModelAndView("redirect:/seller/partners.html");
	}

	@RequestMapping(value = "/seller/addJabong", method = RequestMethod.GET)
	public ModelAndView addJabong(HttpServletRequest request,
			@ModelAttribute("command") PartnerBean partnerBean,
			BindingResult result) {

		log.info("$$$ addJabong Starts : OrderController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> datemap = new LinkedHashMap<String, Object>();
		List<String> categoryList = new ArrayList<String>();
		List<Category> categoryObjects = null;
		datemap.put("true", "Select payment from");
		datemap.put("true", "Shipping Date");
		datemap.put("false", "Delivery Date");
		PartnerBean partner = new PartnerBean();
		try {
			categoryObjects = categoryService.listCategories(helperClass
					.getSellerIdfromSession(request));
			for (Category cat : categoryObjects) {
				categoryList.add(cat.getCatName());
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Failed!",e);
		}
		String partnerName = request.getParameter("partnerName");
		try {
			if (partnerName != null) {
				partner.setPcName(partnerName);
			}
			model.put("partner", partner);
			model.put("categoryList", categoryList);
			model.put("datemap", datemap);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed!",e);
		}
		log.info("$$$ addJabong Ends : OrderController $$$");
		return new ModelAndView("initialsetup/addJabong", model);
	}

	@RequestMapping(value = "/seller/addMyntra", method = RequestMethod.GET)
	public ModelAndView addMyntra(HttpServletRequest request,
			@ModelAttribute("command") PartnerBean partnerBean,
			BindingResult result) {

		log.info("$$$ addMyntra Starts : OrderController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> datemap = new LinkedHashMap<String, Object>();
		List<String> categoryList = new ArrayList<String>();
		Map<String, Float> commissionMap = new HashMap<String, Float>();
		List<String> taxCategoryList = new ArrayList<String>();
		List<Category> categoryObjects = null;
		List<TaxCategory> taxCategoryObjects = null;
		datemap.put("true", "Select payment from");
		datemap.put("true", "Shipping Date");
		datemap.put("false", "Delivery Date");
		PartnerBean partner = new PartnerBean();
		try {
			categoryObjects = categoryService.listCategories(helperClass
					.getSellerIdfromSession(request));
			if (categoryObjects != null && categoryObjects.size() > 0) {
				for (Category cat : categoryObjects) {
					categoryList.add(cat.getCatName());
					commissionMap.put(cat.getCatName(), 0.0F);
				}
			}
			taxCategoryObjects = taxDetailService.listTaxCategories(helperClass
					.getSellerIdfromSession(request));
			if (taxCategoryObjects != null && taxCategoryObjects.size() > 0) {
				for (TaxCategory taxCat : taxCategoryObjects) {
					taxCategoryList.add(taxCat.getTaxCatName());
				}
			}
		} /*
		 * catch (CustomException ce) { log.error("addPartner exception : " +
		 * ce.toString()); model.put("errorMessage", ce.getLocalMessage());
		 * model.put("errorTime", ce.getErrorTime()); model.put("errorCode",
		 * ce.getErrorCode()); return new ModelAndView("globalErorPage", model);
		 * }
		 */catch (Exception e) {
			 e.printStackTrace();
			log.error("Failed!",e);
		}
		
		try {
			model.put("partner", partner);
			model.put("categoryList", categoryList);
			model.put("commissionMap", commissionMap);
			model.put("taxCategoryList", taxCategoryList);
			model.put("datemap", datemap);
			model.put("partners", ConverterClass
					.prepareListofPartnerBean(partnerService
							.listPartners(helperClass
									.getSellerIdfromSession(request))));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed!",e);
		}
		log.info("$$$ addMyntra Ends : OrderController $$$");
		return new ModelAndView("initialsetup/addMyntra", model);
	}

	/* My Code Ranjan Ends */

	@RequestMapping(value = "/seller/addPartnertest", method = RequestMethod.GET)
	public ModelAndView addPartnertest(HttpServletRequest request,
			@ModelAttribute("command") PartnerBean partnerBean,
			BindingResult result) {
		
		log.info("$$$ addPartnertest Starts : OrderController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		PartnerBean partner = new PartnerBean();
		String id = request.getParameter("pid");
		log.debug(" Inside partner controller :" + id);

		try {
			if (id != null) {

				if (id.equals("1"))
					partner.setPcName("Amazon");
			}
			model.put("partner", partner);
			model.put("partners", ConverterClass
					.prepareListofPartnerBean(partnerService
							.listPartners(helperClass
									.getSellerIdfromSession(request))));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed!",e);
		}
		log.info("$$$ addPartnertest Ends : OrderController $$$");
		return new ModelAndView("addPartner", model);
	}

	@RequestMapping(value = "/seller/partnerindex", method = RequestMethod.GET)
	public ModelAndView welcome() {
		return new ModelAndView("index");
	}

	@RequestMapping(value = "/seller/deletePartner", method = RequestMethod.GET)
	public ModelAndView deletePartner(HttpServletRequest request,
			@ModelAttribute("command") PartnerBean partnerBean,
			BindingResult result) {
		
		log.info("$$$ deletePartner Starts : OrderController $$$");
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			partnerService.deletePartner(
					ConverterClass.preparePartnerModel(partnerBean),
					helperClass.getSellerIdfromSession(request));
			model.put("partner", null);
			model.put("partners", ConverterClass
					.prepareListofPartnerBean(partnerService
							.listPartners(helperClass
									.getSellerIdfromSession(request))));
		} catch (CustomException ce) {

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed!",e);
		}
		log.info("$$$ deletePartner Ends : OrderController $$$");
		return new ModelAndView("addPartner", model);
	}

	@RequestMapping(value = "/seller/viewPartner", method = RequestMethod.GET)
	public ModelAndView viewPartner(HttpServletRequest request,
			@ModelAttribute("command") PartnerBean partnerBean,
			BindingResult result) {

		log.info("$$$ viewPartner Starts : OrderController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> datemap = new HashMap<String, Object>();
		PartnerBean pbean = null;
		Map<String, Float> chargeMap = new HashMap<String, Float>();
		Map<String, Float> categoryMap = new HashMap<String, Float>();
		List<Category> categoryObjects = null;
		try {
			datemap.put("true", "Select payment from");
			datemap.put("true", "Shipping Date");
			datemap.put("false", "Delivery Date");
			model.put("datemap", datemap);
			pbean = ConverterClass.preparePartnerBean(partnerService
					.getPartner(partnerBean.getPcId()));
			log.debug("********** Inside edit partner : config ID :"
							+ pbean.getNrnReturnConfig().getConfigId());
			for (NRnReturnCharges charge : pbean.getNrnReturnConfig()
					.getCharges()) {
				chargeMap.put(charge.getChargeName(), charge.getChargeAmount());
			}

			categoryObjects = categoryService.listCategories(helperClass
					.getSellerIdfromSession(request));
			if (categoryObjects != null && categoryObjects.size() > 0) {
				for (Category cat : categoryObjects) {
					categoryMap.put(cat.getCatName(),
							chargeMap.get(cat.getCatName()));
				}
			}
			model.put("categoryMap", categoryMap);
			model.put("partner", pbean);
			model.put("chargeMap", chargeMap);
			model.put("partnerList", ConverterClass
					.prepareListofPartnerBean(partnerService
							.listPartners(helperClass
									.getSellerIdfromSession(request))));
		} catch (CustomException ce) {
			log.error("editPartner exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed!",e);
			log.error("editPartner exception : " + e.getLocalizedMessage());
			model.put("errorMessage", e.getMessage());

		}
		log.info("$$$ viewPartner Ends : OrderController $$$");
		if (pbean.getPcName().equalsIgnoreCase(GlobalConstant.PCMYNTRA) ||
				pbean.getPcName().equalsIgnoreCase(GlobalConstant.PCJABONG)) {
			return new ModelAndView("initialsetup/viewPOPartner", model);
		} else {
			return new ModelAndView("initialsetup/viewPartner", model);
		}
	}

	@RequestMapping(value = "/seller/editPartner", method = RequestMethod.GET)
	public ModelAndView editPartner(HttpServletRequest request,
			@ModelAttribute("command") PartnerBean partnerBean,
			BindingResult result) {
		
		log.info("$$$ editPartner Starts : OrderController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> datemap = new HashMap<String, Object>();
		PartnerBean pbean = null;
		Map<String, Float> chargeMap = new HashMap<String, Float>();
		Map<String, Float> categoryMap = new HashMap<String, Float>();
		List<Category> categoryObjects = null;

		try {
			datemap.put("true", "Select payment from");
			datemap.put("true", "Shipping Date");
			datemap.put("false", "Delivery Date");
			model.put("datemap", datemap);
			pbean = ConverterClass.preparePartnerBean(partnerService
					.getPartner(partnerBean.getPcId()));
			log.debug("************** Inside edit partner : config ID :"
							+ pbean.getNrnReturnConfig().getConfigId());
			for (NRnReturnCharges charge : pbean.getNrnReturnConfig()
					.getCharges()) {
				chargeMap.put(charge.getChargeName(), charge.getChargeAmount());
			}

			categoryObjects = categoryService.listCategories(helperClass
					.getSellerIdfromSession(request));
			for (Category cat : categoryObjects) {
				categoryMap.put(cat.getCatName(),
						chargeMap.get(cat.getCatName()));
			}
			model.put("categoryMap", categoryMap);
			model.put("partner", pbean);
			model.put("chargeMap", chargeMap);
		} catch (CustomException ce) {
			log.error("editPartner exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("FAiled!",e);
			log.error("editPartner exception : " + e.getLocalizedMessage());
			model.put("errorMessage", e.getMessage());

		}
		log.info("$$$ editPartner Ends : OrderController $$$");
		return new ModelAndView("initialsetup/addPartner", model);
	}

	@RequestMapping(value = "/seller/editMyntra", method = RequestMethod.GET)
	public ModelAndView editMyntra(HttpServletRequest request,
			@ModelAttribute("command") PartnerBean partnerBean,
			BindingResult result) {
		
		log.info("$$$ editPartner Starts : OrderController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> datemap = new HashMap<String, Object>();
		PartnerBean pbean = null;
		Map<String, Float> chargesMap = new HashMap<String, Float>();
		Map<String, Float> commissionMap = new HashMap<String, Float>();
		Map<String, String> taxSpMap = new HashMap<String, String>();
		Map<String, String> taxPoMap = new HashMap<String, String>();
		List<String> categoryList = new ArrayList<String>();
		List<String> taxCategoryList = new ArrayList<String>();
		List<Category> categoryObjects = null;
		List<TaxCategory> taxCategoryObjects = null;

		try {
			datemap.put("true", "Select payment from");
			datemap.put("true", "Shipping Date");
			datemap.put("false", "Delivery Date");
			model.put("datemap", datemap);
			pbean = ConverterClass.preparePartnerBean(partnerService
					.getPartner(partnerBean.getPcId()));
			log.debug("************** Inside edit partner : config ID :"
							+ pbean.getNrnReturnConfig().getConfigId());
			for (NRnReturnCharges charge : pbean.getNrnReturnConfig()
					.getCharges()) {
				if (charge.getChargeName().contains(GlobalConstant.TaxCategoryPrefix)) {
					String[] split = charge.getChargeName().split(GlobalConstant.TaxCategoryPrefix);
					String firstSubString = split[0];
					String secondSubString = split[1];
					if (firstSubString.contains(GlobalConstant.TaxSPPrefix)) {
						String key = firstSubString.substring(GlobalConstant.TaxSPPrefix.length());
						taxSpMap.put(key,secondSubString);
					} else {
						String key = firstSubString.substring(GlobalConstant.TaxPOPrefix.length());
						taxPoMap.put(key,secondSubString);
					}
				} else {
					if (charge.getChargeName().contains(GlobalConstant.CommPOPrefix)) {
						String key = charge.getChargeName().substring(GlobalConstant.CommPOPrefix.length());
						commissionMap.put(key,charge.getChargeAmount());
					} else {
						chargesMap.put(charge.getChargeName(), charge.getChargeAmount());
					}
				}
			}

			categoryObjects = categoryService.listCategories(helperClass
					.getSellerIdfromSession(request));
			if (categoryObjects != null && categoryObjects.size() > 0) {
				for (Category cat : categoryObjects) {
					categoryList.add(cat.getCatName());
				}
			}
			taxCategoryObjects = taxDetailService.listTaxCategories(helperClass
					.getSellerIdfromSession(request));
			if (taxCategoryObjects != null && taxCategoryObjects.size() > 0) {
				for (TaxCategory taxCat : taxCategoryObjects) {
					taxCategoryList.add(taxCat.getTaxCatName());
				}
			}
			model.put("categoryList", categoryList);
			model.put("taxCategoryList", taxCategoryList);
			model.put("partner", pbean);
			model.put("chargeMap", chargesMap);
			model.put("commissionMap", commissionMap);
			model.put("taxSpMap", taxSpMap);
			model.put("taxPoMap", taxPoMap);
		} catch (CustomException ce) {
			log.error("editPartner exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("FAiled!",e);
			log.error("editPartner exception : " + e.getLocalizedMessage());
			model.put("errorMessage", e.getMessage());

		}
		log.info("$$$ editPartner Ends : OrderController $$$");
		return new ModelAndView("initialsetup/addMyntra", model);
	}
	
	@RequestMapping(value = "/seller/editJabong", method = RequestMethod.GET)
	public ModelAndView editJabong(HttpServletRequest request,
			@ModelAttribute("command") PartnerBean partnerBean,
			BindingResult result) {
		
		log.info("$$$ editPartner Starts : OrderController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> datemap = new HashMap<String, Object>();
		PartnerBean pbean = null;
		Map<String, Float> chargeMap = new HashMap<String, Float>();
		Map<String, Float> categoryMap = new HashMap<String, Float>();
		List<Category> categoryObjects = null;

		try {
			datemap.put("true", "Select payment from");
			datemap.put("true", "Shipping Date");
			datemap.put("false", "Delivery Date");
			model.put("datemap", datemap);
			pbean = ConverterClass.preparePartnerBean(partnerService
					.getPartner(partnerBean.getPcId()));
			log.debug("************** Inside edit partner : config ID :"
							+ pbean.getNrnReturnConfig().getConfigId());
			for (NRnReturnCharges charge : pbean.getNrnReturnConfig()
					.getCharges()) {
				chargeMap.put(charge.getChargeName(), charge.getChargeAmount());
			}

			categoryObjects = categoryService.listCategories(helperClass
					.getSellerIdfromSession(request));
			for (Category cat : categoryObjects) {
				categoryMap.put(cat.getCatName(),
						chargeMap.get(cat.getCatName()));
			}
			model.put("categoryMap", categoryMap);
			model.put("partner", pbean);
			model.put("chargeMap", chargeMap);
		} catch (CustomException ce) {
			log.error("editPartner exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("FAiled!",e);
			log.error("editPartner exception : " + e.getLocalizedMessage());
			model.put("errorMessage", e.getMessage());

		}
		log.info("$$$ editPartner Ends : OrderController $$$");
		return new ModelAndView("initialsetup/addJabong", model);
	}
	
	private void validateImage(MultipartFile image) {
		
		log.info("$$$ validateImage Starts : OrderController $$$");
		if (!image.getContentType().equals("image/jpeg")) {
			log.info("$$$ validateImage Error : OrderController $$$");
			throw new RuntimeException("Only JPG images are accepted");
		}
	}

	private void saveImage(String filename, MultipartFile image)
			throws RuntimeException, IOException {
		
		log.info("$$$ saveImage Starts : OrderController $$$");
		try {
			String catalinabase = System.getProperty("catalina.base");

			try {
				props = PropertiesLoaderUtils.loadProperties(resource);
			} catch (IOException e) {
				log.error(e.getCause());

			}
			log.debug("dialect in order controller : "
					+ props.getProperty("partnerimage.path"));
			File file = new File(catalinabase
					+ props.getProperty("partnerimage.path") + filename);
			log.debug(" context.getRealPath(/) "
					+ context.getRealPath("/"));
			log.debug(" Path to save file : " + file.toString());
			FileUtils.writeByteArrayToFile(file, image.getBytes());
			log.debug("Go to the location:  "
							+ file.toString()
							+ " on your computer and verify that the image has been stored.");
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getCause());
		}
		log.info("$$$ saveImage Ends : OrderController $$$");
	}

	@RequestMapping(value = "/seller/ajaxPartnerCheck.html", method = RequestMethod.GET)
	public @ResponseBody String getCheckPartner(HttpServletRequest request,
			@ModelAttribute("command") PartnerBean partnerBean,
			BindingResult result, Model model) {
		
		log.info("$$$ getCheckPartner Starts : OrderController $$$");
		log.debug(request.getParameter("partner"));
		try {
			Partner parner = partnerService.getPartner(
					request.getParameter("partner"),
					helperClass.getSellerIdfromSession(request));
			if (parner != null) {
				log.info("$$$ getCheckPartner Ends : OrderController $$$");
				return "false";
			} else {
				log.info("$$$ getCheckPartner Ends : OrderController $$$");
				return "true";
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed!",e);
			return "Error";
		}
	}
}
