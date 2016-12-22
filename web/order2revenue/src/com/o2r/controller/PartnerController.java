package com.o2r.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
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

import com.o2r.bean.ChargesBean;
import com.o2r.bean.ChargesBean.SortByCriteriaRange;
import com.o2r.bean.ChargesBean.SortByCriteria;
import com.o2r.bean.MetaPartnerBean;
import com.o2r.bean.PartnerBean;
import com.o2r.bean.PartnerCategoryBean;
import com.o2r.bean.PartnerDetailsBean;
import com.o2r.helper.ConverterClass;
import com.o2r.helper.CustomException;
import com.o2r.helper.GlobalConstant;
import com.o2r.helper.HelperClass;
import com.o2r.model.Category;
import com.o2r.model.MetaNRnReturnCharges;
import com.o2r.model.MetaPartner;
import com.o2r.model.NRnReturnCharges;
import com.o2r.model.Partner;
import com.o2r.model.TaxCategory;
import com.o2r.service.CategoryService;
import com.o2r.service.OrderService;
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
	@Autowired
	private OrderService orderService;

	Properties props = null;
	org.springframework.core.io.Resource resource = new ClassPathResource(
			"database.properties");
	ArrayList<String> partnerList = new ArrayList<String>() {
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		{
			add("amazon");
			add("flipkart");
			add("snapdeal");
			add("paytm");
			add("ebay");
			add("shopclues");
			add("jabong");
			add("myntra");
			add("askmebazaar");
			add("babyoye");
			add("craftsvilla");
			add("fabfurnish");
			add("firstcry");
			add("fashionnyou");
			add("homeshop18");
			add("indiatimes");
			add("infibeam");
			add("limeroad");
			add("mebelkart");
			add("naaptol");
			add("pepperfry");
			add("rediff");
			add("tradus");
			add("voonik");
			add("zansaar");
		}
	};
	
	ArrayList<String> sellerTierList = new ArrayList<String>() {
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		{
			add("Bronze");
			add("Silver");
			add("Gold");
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
		log.info(partnerBean.getFixedfeeList().size());
		log.info(partnerBean.getshippingfeeVolumeFixedList().size());
		log.info(partnerBean.getshippingfeeWeightFixedList().size());
		log.info(partnerBean.getshippingfeeVolumeVariableList().size());
		log.info(partnerBean.getshippingfeeWeightVariableList().size());

		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		Partner existPartner = null;
		String suffix = request.getParameter("pcNameSuffix");
		String partnerName = "";
		if (suffix != null && !suffix.equals("")) {
			partnerName = partnerBean.getPcName() + suffix;
		} else {
			partnerName = partnerBean.getPcName();
		}
		partnerBean.setPcName(partnerName);
		
		String sellerTier = request.getParameter("sellerTier");
		partnerBean.setSellerTier(sellerTier);
		
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			existPartner = partnerService.getPartner(partnerBean.getPcName(),
					helperClass.getSellerIdfromSession(request));
		} catch (Exception e) {
			log.error("Exception in getPartner(name,sellerId) : ", e);
		}
		if (existPartner != null && partnerBean.getPcId() == 0) {
			log.info("Partner is Exist With the Same Name ! ");
		} else {
			if (partnerBean.getPcId() != 0) {
				log.debug("******** ConfigId : "
						+ partnerBean.getNrnReturnConfig().getConfigId());
				log.debug("******** Partner ID : " + partnerBean.getPcId());

			}

			log.debug(" Nr calculayor value from bean : "
					+ partnerBean.getNrnReturnConfig().isNrCalculator());
			Map<String, String[]> parameters = request.getParameterMap();

			List<String> fixedfeeParams = new ArrayList<String>();
			List<String> shippingfeeVolumeParams = new ArrayList<String>();
			List<String> shippingfeeWeightParams = new ArrayList<String>();

			try {
				for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
					if (entry.getKey().contains("nr-")) {
						log.info(" Key with nr: " + entry.getKey()
								+ " Values is : " + entry.getValue()[0]);

						if (entry.getKey().contains("fixedfee")) {

							String param = entry.getKey().substring(0,
									entry.getKey().lastIndexOf('-') + 1);
							if (!fixedfeeParams.contains(param)) {
								fixedfeeParams.add(param);
								NRnReturnCharges nrnReturncharge = new NRnReturnCharges();
								String charge = parameters.get(param + "value")[0];
								if (charge.isEmpty()) {
									nrnReturncharge.setChargeAmount(0);
								} else {
									nrnReturncharge.setChargeAmount(Float
											.parseFloat(charge));
								}

								if (param.contains("Others")) {
									nrnReturncharge.setChargeName("fixedfeeOthers");
								} else {
									nrnReturncharge.setChargeName("fixedfee");
								}
								
								nrnReturncharge.setCriteria(parameters
										.get(param + "criteria")[0]);
								if (!parameters.get(param + "range")[0]
										.isEmpty()) {
									nrnReturncharge.setCriteriaRange(Long
											.parseLong(parameters.get(param
													+ "range")[0]));
								}

								nrnReturncharge.setConfig(partnerBean
										.getNrnReturnConfig());
								partnerBean.getNrnReturnConfig().getCharges()
										.add(nrnReturncharge);

							}
						} else if (entry.getKey().contains("shippingfeeVolume")
								&& partnerBean.getNrnReturnConfig()
										.getShippingFeeType() != null) {

							if (partnerBean.getNrnReturnConfig()
									.getShippingFeeType()
									.equalsIgnoreCase("variable")
									&& entry.getKey().contains(
											"shippingfeeVolumeVariable")) {
								String param = entry.getKey().substring(0,
										entry.getKey().lastIndexOf('-') + 1);
								if (!shippingfeeVolumeParams.contains(param)) {
									shippingfeeVolumeParams.add(param);
									NRnReturnCharges nrnReturncharge = new NRnReturnCharges();

									if (!parameters.get(param + "localValue")[0]
											.isEmpty())
										nrnReturncharge
												.setChargeAmount(Float.parseFloat(parameters
														.get(param
																+ "localValue")[0]));
									nrnReturncharge
											.setChargeName("shippingfeeVolumeVariableLocal");
									nrnReturncharge.setCriteria(parameters
											.get(param + "criteria")[0]);

									if (!parameters.get(param + "range")[0]
											.isEmpty())
										nrnReturncharge.setCriteriaRange(Long
												.parseLong(parameters.get(param
														+ "range")[0]));

									nrnReturncharge.setConfig(partnerBean
											.getNrnReturnConfig());
									partnerBean.getNrnReturnConfig()
											.getCharges().add(nrnReturncharge);

									nrnReturncharge = new NRnReturnCharges();
									if (!parameters.get(param + "zonalValue")[0]
											.isEmpty())
										nrnReturncharge
												.setChargeAmount(Float.parseFloat(parameters
														.get(param
																+ "zonalValue")[0]));
									nrnReturncharge
											.setChargeName("shippingfeeVolumeVariableZonal");
									nrnReturncharge.setCriteria(parameters
											.get(param + "criteria")[0]);
									if (!parameters.get(param + "range")[0]
											.isEmpty())
										nrnReturncharge.setCriteriaRange(Long
												.parseLong(parameters.get(param
														+ "range")[0]));

									nrnReturncharge.setConfig(partnerBean
											.getNrnReturnConfig());
									partnerBean.getNrnReturnConfig()
											.getCharges().add(nrnReturncharge);

									nrnReturncharge = new NRnReturnCharges();
									if (!parameters
											.get(param + "nationalValue")[0]
											.isEmpty())
										nrnReturncharge
												.setChargeAmount(Float.parseFloat(parameters
														.get(param
																+ "nationalValue")[0]));
									nrnReturncharge
											.setChargeName("shippingfeeVolumeVariableNational");
									nrnReturncharge.setCriteria(parameters
											.get(param + "criteria")[0]);
									if (!parameters.get(param + "range")[0]
											.isEmpty())
										nrnReturncharge.setCriteriaRange(Long
												.parseLong(parameters.get(param
														+ "range")[0]));

									nrnReturncharge.setConfig(partnerBean
											.getNrnReturnConfig());
									partnerBean.getNrnReturnConfig()
											.getCharges().add(nrnReturncharge);

									nrnReturncharge = new NRnReturnCharges();
									if (!parameters.get(param + "metroValue")[0]
											.isEmpty())
										nrnReturncharge
												.setChargeAmount(Float.parseFloat(parameters
														.get(param
																+ "metroValue")[0]));
									nrnReturncharge
											.setChargeName("shippingfeeVolumeVariableMetro");
									nrnReturncharge.setCriteria(parameters
											.get(param + "criteria")[0]);
									if (!parameters.get(param + "range")[0]
											.isEmpty())
										nrnReturncharge.setCriteriaRange(Long
												.parseLong(parameters.get(param
														+ "range")[0]));

									nrnReturncharge.setConfig(partnerBean
											.getNrnReturnConfig());
									partnerBean.getNrnReturnConfig()
											.getCharges().add(nrnReturncharge);

								}
							} else if (entry.getKey().contains(
									"shippingfeeVolumeFixed")) {
								String param = entry.getKey().substring(0,
										entry.getKey().lastIndexOf('-') + 1);
								if (!shippingfeeVolumeParams.contains(param)) {
									shippingfeeVolumeParams.add(param);
									NRnReturnCharges nrnReturncharge = new NRnReturnCharges();
									if (!parameters.get(param + "value")[0]
											.isEmpty())
										nrnReturncharge
												.setChargeAmount(Float.parseFloat(parameters
														.get(param + "value")[0]));
									nrnReturncharge
											.setChargeName("shippingfeeVolumeFixed");
									nrnReturncharge.setCriteria(parameters
											.get(param + "criteria")[0]);
									if (!parameters.get(param + "range")[0]
											.isEmpty())
										nrnReturncharge.setCriteriaRange(Long
												.parseLong(parameters.get(param
														+ "range")[0]));

									nrnReturncharge.setConfig(partnerBean
											.getNrnReturnConfig());
									partnerBean.getNrnReturnConfig()
											.getCharges().add(nrnReturncharge);
								}
							}
						} else if (entry.getKey().contains("shippingfeeWeight")
								&& partnerBean.getNrnReturnConfig()
										.getShippingFeeType() != null) {

							if (partnerBean.getNrnReturnConfig()
									.getShippingFeeType()
									.equalsIgnoreCase("variable")
									&& entry.getKey().contains(
											"shippingfeeWeightVariable")) {
								String param = entry.getKey().substring(0,
										entry.getKey().lastIndexOf('-') + 1);
								if (!shippingfeeVolumeParams.contains(param)) {
									shippingfeeVolumeParams.add(param);
									NRnReturnCharges nrnReturncharge = new NRnReturnCharges();

									if (!parameters.get(param + "localValue")[0]
											.isEmpty()) {
										nrnReturncharge
												.setChargeAmount(Float.parseFloat(parameters
														.get(param
																+ "localValue")[0]));
									}

									nrnReturncharge
											.setChargeName("shippingfeeWeightVariableLocal");
									nrnReturncharge.setCriteria(parameters
											.get(param + "criteria")[0]);
									if (!parameters.get(param + "range")[0]
											.isEmpty())
										nrnReturncharge.setCriteriaRange(Long
												.parseLong(parameters.get(param
														+ "range")[0]));

									nrnReturncharge.setConfig(partnerBean
											.getNrnReturnConfig());
									partnerBean.getNrnReturnConfig()
											.getCharges().add(nrnReturncharge);

									nrnReturncharge = new NRnReturnCharges();
									if (!parameters.get(param + "zonalValue")[0]
											.isEmpty()) {
										nrnReturncharge
												.setChargeAmount(Float.parseFloat(parameters
														.get(param
																+ "zonalValue")[0]));
									}

									nrnReturncharge
											.setChargeName("shippingfeeWeightVariableZonal");
									nrnReturncharge.setCriteria(parameters
											.get(param + "criteria")[0]);
									if (!parameters.get(param + "range")[0]
											.isEmpty())
										nrnReturncharge.setCriteriaRange(Long
												.parseLong(parameters.get(param
														+ "range")[0]));

									nrnReturncharge.setConfig(partnerBean
											.getNrnReturnConfig());
									partnerBean.getNrnReturnConfig()
											.getCharges().add(nrnReturncharge);

									nrnReturncharge = new NRnReturnCharges();
									if (!parameters
											.get(param + "nationalValue")[0]
											.isEmpty()) {
										nrnReturncharge
												.setChargeAmount(Float.parseFloat(parameters
														.get(param
																+ "nationalValue")[0]));
									}

									nrnReturncharge
											.setChargeName("shippingfeeWeightVariableNational");
									nrnReturncharge.setCriteria(parameters
											.get(param + "criteria")[0]);
									if (!parameters.get(param + "range")[0]
											.isEmpty())
										nrnReturncharge.setCriteriaRange(Long
												.parseLong(parameters.get(param
														+ "range")[0]));

									nrnReturncharge.setConfig(partnerBean
											.getNrnReturnConfig());
									partnerBean.getNrnReturnConfig()
											.getCharges().add(nrnReturncharge);

									nrnReturncharge = new NRnReturnCharges();
									if (!parameters.get(param + "metroValue")[0]
											.isEmpty()) {
										nrnReturncharge
												.setChargeAmount(Float.parseFloat(parameters
														.get(param
																+ "metroValue")[0]));
									}
									nrnReturncharge
											.setChargeName("shippingfeeWeightVariableMetro");
									nrnReturncharge.setCriteria(parameters
											.get(param + "criteria")[0]);

									if (!parameters.get(param + "range")[0]
											.isEmpty())
										nrnReturncharge.setCriteriaRange(Long
												.parseLong(parameters.get(param
														+ "range")[0]));

									nrnReturncharge.setConfig(partnerBean
											.getNrnReturnConfig());
									partnerBean.getNrnReturnConfig()
											.getCharges().add(nrnReturncharge);

								}
							} else if (entry.getKey().contains(
									"shippingfeeWeightFixed")) {
								String param = entry.getKey().substring(0,
										entry.getKey().lastIndexOf('-') + 1);
								if (!shippingfeeWeightParams.contains(param)) {
									shippingfeeWeightParams.add(param);
									NRnReturnCharges nrnReturncharge = new NRnReturnCharges();
									if (!parameters.get(param + "value")[0]
											.isEmpty())
										nrnReturncharge
												.setChargeAmount(Float.parseFloat(parameters
														.get(param + "value")[0]));
									nrnReturncharge
											.setChargeName("shippingfeeWeightFixed");
									nrnReturncharge.setCriteria(parameters
											.get(param + "criteria")[0]);
									if (!parameters.get(param + "range")[0]
											.isEmpty())
										nrnReturncharge.setCriteriaRange(Long
												.parseLong(parameters.get(param
														+ "range")[0]));

									nrnReturncharge.setConfig(partnerBean
											.getNrnReturnConfig());
									partnerBean.getNrnReturnConfig()
											.getCharges().add(nrnReturncharge);
								}
							}
						}
					}
				}
				for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
					if (entry.getKey() != null && !entry.getKey().isEmpty())
						log.debug(" Print :entry.getKey()  " + entry.getKey()
								+ " value: " + entry.getValue()[0]);

					if (entry.getValue()[0] != null
							&& !entry.getValue()[0].isEmpty()) {
						if (entry.getKey().contains("nr-")) {

							if (entry.getKey().contains("fixedfee")
									|| entry.getKey().contains("shippingfee")) {

							} else {
								log.debug(" Key : " + entry.getKey());
								String temp = entry.getKey().substring(3);
								NRnReturnCharges nrnReturncharge = new NRnReturnCharges();
								nrnReturncharge.setChargeAmount(Float
										.parseFloat(entry.getValue()[0]));
								nrnReturncharge.setChargeName(temp);
								nrnReturncharge.setConfig(partnerBean
										.getNrnReturnConfig());
								partnerBean.getNrnReturnConfig().getCharges()
										.add(nrnReturncharge);
							}
						} else if (entry.getKey().contains("local")) {

							String localstring = Arrays.toString(entry
									.getValue());
							log.debug("localstring " + localstring);
							partnerBean.getNrnReturnConfig().setLocalList(
									localstring
											.substring(localstring.toString()
													.indexOf('[') + 1,
													localstring.toString()
															.indexOf(']')));
						} else if (entry.getKey().contains("zonal")) {
							String zonalstring = Arrays.toString(entry
									.getValue());
							log.debug("zonalstring " + zonalstring);

							partnerBean.getNrnReturnConfig().setZonalList(
									zonalstring
											.substring(zonalstring.toString()
													.indexOf('[') + 1,
													zonalstring.toString()
															.indexOf(']')));
						} else if (entry.getKey().contains("national")) {
							String nationalstring = Arrays.toString(entry
									.getValue());
							partnerBean.getNrnReturnConfig().setNationalList(
									nationalstring.substring(
											nationalstring.toString().indexOf(
													'[') + 1,
											nationalstring.toString().indexOf(
													']')));
						} else if (entry.getKey().contains("metro")) {
							String metrostring = Arrays.toString(entry
									.getValue());
							log.debug("metrostring " + metrostring);

							partnerBean.getNrnReturnConfig().setMetroList(
									metrostring
											.substring(metrostring.toString()
													.indexOf('[') + 1,
													metrostring.toString()
															.indexOf(']')));
						}
					}
				}
				
				String[] catList = request.getParameterValues("multiSku");
				if (catList != null && catList.length != 0) {
					
					NRnReturnCharges nrnReturncharge = new NRnReturnCharges();
					nrnReturncharge.setChargeAmount(0);
					nrnReturncharge.setChargeName("fixedfeeCategory");
					nrnReturncharge.setCriteria(StringUtils.join(catList, ",")); 

					nrnReturncharge.setConfig(partnerBean
							.getNrnReturnConfig());
					partnerBean.getNrnReturnConfig().getCharges()
							.add(nrnReturncharge);
				}

				if (!partnerBean.isIsshippeddatecalc()) {
					partnerBean.setNoofdaysfromshippeddate(partnerBean
							.getNoofdaysfromdeliverydate());
				}
				if (partnerBean.getPcName().toLowerCase()
						.contains(GlobalConstant.PCFLIPKART)
						&& !partnerBean.isIsshippeddatecalcPost()) {
					partnerBean.setNoofdaysfromshippeddatePost(partnerBean
							.getNoofdaysfromdeliverydatePost());
				}
				if (partnerBean.getPcName().toLowerCase()
						.contains(GlobalConstant.PCFLIPKART)
						&& !partnerBean.isIsshippeddatecalcOthers()) {
					partnerBean.setNoofdaysfromshippeddateOthers(partnerBean
							.getNoofdaysfromdeliverydateOthers());
				}

				if (image.getSize() != 0) {
					if (!image.isEmpty()) {
						try {
							validateImage(image);

						} catch (RuntimeException re) {
							log.error("Failed! by Seller ID : " + sellerId, re);
							result.reject(re.getMessage());
						}
					}
					try {
						props = PropertiesLoaderUtils.loadProperties(resource);
						partnerBean.setPcLogoUrl(props
								.getProperty("partnerimage.view")
								+ helperClass.getSellerIdfromSession(request)
								+ partnerBean.getPcName() + ".jpg");
						saveImage(helperClass.getSellerIdfromSession(request)
								+ partnerBean.getPcName() + ".jpg", image);

					} catch (Exception e) {
						e.printStackTrace();
						log.error("Failed! by Seller ID : " + sellerId, e);
						result.reject(e.getMessage());
						return new ModelAndView(
								"redirect:/seller/partners.html");
					}
				} else {
					try {
						props = PropertiesLoaderUtils.loadProperties(resource);
						if(partnerList != null){
							for(String eachName : partnerList){
								if(partnerBean.getPcName().contains(eachName)){
									partnerBean.setPcLogoUrl(props
											.getProperty("partnerimage.view")
											+eachName+ ".jpg");
								} else if (partnerList.contains(partnerBean.getPcName())) {
									partnerBean.setPcLogoUrl(props
											.getProperty("partnerimage.view")
											+ partnerBean.getPcName() + ".jpg");
								}
							}
						}						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				Partner partner = ConverterClass
						.preparePartnerModel(partnerBean);
				partnerService.addPartner(partner,
						helperClass.getSellerIdfromSession(request));
			} catch (CustomException ce) {
				log.error("SavePartner exception : " + ce.toString());
				model.put("errorMessage", ce.getLocalMessage());
				model.put("errorTime", ce.getErrorTime());
				model.put("errorCode", ce.getErrorCode());
				return new ModelAndView("globalErorPage", model);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Failed! by Seller ID : " + sellerId, e);
			}
		}
		log.info("$$$ savePartner Ends : OrderController $$$");
		return new ModelAndView("redirect:/seller/partners.html");
	}

	@RequestMapping(value = "/seller/listPartners", method = RequestMethod.GET)
	public ModelAndView listAllPartners(HttpServletRequest request) {

		log.info("$$$ listAllPartners Starts : OrderController $$$");
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		List<PartnerBean> partnerList = null;
		List<PartnerDetailsBean> PDBeans = new ArrayList<PartnerDetailsBean>();
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			partnerList = ConverterClass
					.prepareListofPartnerBean(partnerService
							.listPartners(helperClass
									.getSellerIdfromSession(request)));
			for (PartnerBean PB : partnerList) {
				PartnerDetailsBean PDBean = orderService.detailsOfPartner(
						PB.getPcName(), sellerId);
				if (PDBean != null)
					PDBeans.add(PDBean);
			}

		} catch (CustomException ce) {
			log.error("ListAllPartner exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			log.error("Failed! by Seller ID : " + sellerId, e);
			e.printStackTrace();
			log.error(e.getCause());
		}
		model.put("partnerDetailsList", PDBeans);
		log.info("$$$ listAllPartners Ends : OrderController $$$");
		return new ModelAndView("initialsetup/partnerDetail", model);
	}

	@RequestMapping(value = "/seller/partners", method = RequestMethod.GET)
	public ModelAndView listPartners(HttpServletRequest request) {

		log.info("$$$ listPartners Starts : OrderController $$$");
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		List<PartnerBean> toAddPartner = new ArrayList<PartnerBean>();
		List<PartnerBean> addedlist = null;
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			addedlist = ConverterClass.prepareListofPartnerBean(partnerService
					.listPartners(helperClass.getSellerIdfromSession(request)));

			props = PropertiesLoaderUtils.loadProperties(resource);

			for (String partner : partnerList) {
				if (partnerService.getPartner(partner,
						helperClass.getSellerIdfromSession(request)) == null) {
					String pcUrl = props.getProperty("partnerimage.view")
							+ partner + ".jpg";
					log.debug(" Pc logourl set to partner baen " + pcUrl);
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
			log.error("Failed! by Seller ID : " + sellerId, e);
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
		int sellerId = 0;
		String sellerTier = null;
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> datemap = new LinkedHashMap<String, Object>();
		List<String> categoryList = new ArrayList<String>();
		List<Category> categoryObjects = null;
		datemap.put("true", "Select payment from");
		datemap.put("true", "Shipping Date");
		datemap.put("false", "Delivery Date");
		PartnerBean partner = new PartnerBean();
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
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
			log.error("Failed! by Seller ID : " + sellerId, e);
		}
		String partnerName = request.getParameter("partnerName");
		try {
			if (partnerName != null) {
				partner.setPcName(partnerName);

				// Code to fetch default values
				MetaPartner metaPartner = partnerService
						.getMetaPartner(partnerName);
				if (partnerName.contains("flipkart") && partnerName.contains("-$$")) {
					partner.setPcName(partnerName.substring(0,partnerName.indexOf("-$$")));
					sellerTier = partnerName.substring(partnerName.indexOf("-$$") + 3);
				}

				if (metaPartner != null) {					
					metaPartner.getNrnReturnConfig().setLocalList(request.getSession().getAttribute("localList") != null ? request.getSession().getAttribute("localList").toString() : "");
					metaPartner.getNrnReturnConfig().setZonalList(request.getSession().getAttribute("zonalList") != null ? request.getSession().getAttribute("zonalList").toString() : "");
					metaPartner.getNrnReturnConfig().setNationalList(request.getSession().getAttribute("nationalList") != null ? request.getSession().getAttribute("nationalList").toString() : "");
					metaPartner.getNrnReturnConfig().setMetroList(request.getSession().getAttribute("metroList") != null ? request.getSession().getAttribute("metroList").toString() : "");
					partner = ConverterClass.preparePartnerBean(ConverterClass
							.convertPartner(metaPartner));
					if (partnerName.contains("flipkart") && partnerName.contains("-$$")) {
						if(request.getParameter("name") != null && request.getParameter("name") != ""){
							partner.setPcId(Long.parseLong(request.getParameter("pid")));
							partner.setPcName(request.getParameter("name"));
						} else {
							partner.setPcName(partnerName.substring(0,partnerName.indexOf("-$$")));
						}						
					}

					Map<String, Float> chargeMap = new HashMap<String, Float>();
					for (NRnReturnCharges charge : partner.getNrnReturnConfig()
							.getCharges()) {
						if (charge.getChargeName().contains("fixedfee")
								&& charge.getCriteria() != null
								&& !"".equals(charge.getCriteria())) {

							ChargesBean chargeBean = new ChargesBean();
							chargeBean.setChargeType("fixedfee");
							chargeBean.setCriteria(charge.getCriteria());
							chargeBean.setRange(charge.getCriteriaRange());
							chargeBean.setValue(charge.getChargeAmount());
							partner.getFixedfeeList().add(chargeBean);

						} else if (charge.getChargeName().contains(
								"shippingfeeVolume")
								&& charge.getCriteria() != null
								&& !"".equals(charge.getCriteria())) {

							if (partner.getNrnReturnConfig()
									.getShippingFeeType()
									.equalsIgnoreCase("variable")
									&& charge.getChargeName().contains(
											"shippingfeeVolumeVariable")) {

								ChargesBean chargeBean = partner
										.getChargesBean(
												"shippingfeeVolumeVariable",
												charge.getCriteria(),
												charge.getCriteriaRange());
								if (chargeBean == null) {
									chargeBean = new ChargesBean();
									chargeBean
											.setChargeType("shippingfeeVolumeVariable");
									chargeBean
											.setCriteria(charge.getCriteria());
									chargeBean.setRange(charge
											.getCriteriaRange());
									partner.getshippingfeeVolumeVariableList()
											.add(chargeBean);
								}

								if (charge.getChargeName().contains("Local")) {
									chargeBean.setLocalValue(charge
											.getChargeAmount());
								} else if (charge.getChargeName().contains(
										"Zonal")) {
									chargeBean.setZonalValue(charge
											.getChargeAmount());
								} else if (charge.getChargeName().contains(
										"National")) {
									chargeBean.setNationalValue(charge
											.getChargeAmount());
								} else if (charge.getChargeName().contains(
										"Metro")) {
									chargeBean.setMetroValue(charge
											.getChargeAmount());
								}

							} else if (charge.getChargeName().contains(
									"shippingfeeVolumeFixed")) {
								ChargesBean chargeBean = new ChargesBean();
								chargeBean
										.setChargeType("shippingfeeVolumeFixed");
								chargeBean.setCriteria(charge.getCriteria());
								chargeBean.setRange(charge.getCriteriaRange());
								chargeBean.setValue(charge.getChargeAmount());
								partner.getshippingfeeVolumeFixedList().add(
										chargeBean);
							}

						} else if (charge.getChargeName().contains(
								"shippingfeeWeight")
								&& charge.getCriteria() != null
								&& !"".equals(charge.getCriteria())) {

							if (partner.getNrnReturnConfig()
									.getShippingFeeType()
									.equalsIgnoreCase("variable")
									&& charge.getChargeName().contains(
											"shippingfeeWeightVariable")) {

								ChargesBean chargeBean = partner
										.getChargesBean(
												"shippingfeeWeightVariable",
												charge.getCriteria(),
												charge.getCriteriaRange());
								if (chargeBean == null) {
									chargeBean = new ChargesBean();
									chargeBean
											.setChargeType("shippingfeeWeightVariable");
									chargeBean
											.setCriteria(charge.getCriteria());
									chargeBean.setRange(charge
											.getCriteriaRange());
									partner.getshippingfeeWeightVariableList()
											.add(chargeBean);
								}

								if (charge.getChargeName().contains("Local")) {
									chargeBean.setLocalValue(charge
											.getChargeAmount());
								} else if (charge.getChargeName().contains(
										"Zonal")) {
									chargeBean.setZonalValue(charge
											.getChargeAmount());
								} else if (charge.getChargeName().contains(
										"National")) {
									chargeBean.setNationalValue(charge
											.getChargeAmount());
								} else if (charge.getChargeName().contains(
										"Metro")) {
									chargeBean.setMetroValue(charge
											.getChargeAmount());
								}

							} else if (charge.getChargeName().contains(
									"shippingfeeWeightFixed")) {
								ChargesBean chargeBean = new ChargesBean();
								chargeBean
										.setChargeType("shippingfeeWeightFixed");
								chargeBean.setCriteria(charge.getCriteria());
								chargeBean.setRange(charge.getCriteriaRange());
								chargeBean.setValue(charge.getChargeAmount());
								partner.getshippingfeeWeightFixedList().add(
										chargeBean);
							}

						} else {
							chargeMap.put(charge.getChargeName(),
									charge.getChargeAmount());
						}

					}
					if (partner.getFixedfeeList() != null
							&& partner.getFixedfeeList().size() != 0)
						Collections.sort(partner.getFixedfeeList(),
								new SortByCriteriaRange());
					if (partner.getshippingfeeVolumeFixedList() != null
							&& partner.getshippingfeeVolumeFixedList().size() != 0)
						Collections.sort(
								partner.getshippingfeeVolumeFixedList(),
								new SortByCriteria());
					if (partner.getshippingfeeWeightFixedList() != null
							&& partner.getshippingfeeWeightFixedList().size() != 0)
						Collections.sort(
								partner.getshippingfeeWeightFixedList(),
								new SortByCriteria());
					if (partner.getshippingfeeVolumeVariableList() != null
							&& partner.getshippingfeeVolumeVariableList()
									.size() != 0)
						Collections.sort(
								partner.getshippingfeeVolumeVariableList(),
								new SortByCriteria());
					if (partner.getshippingfeeWeightVariableList() != null
							&& partner.getshippingfeeWeightVariableList()
									.size() != 0)
						Collections.sort(
								partner.getshippingfeeWeightVariableList(),
								new SortByCriteria());

					model.put("chargeMap", chargeMap);
				}

			}
			
			List<String> partnerCatList = categoryService
									.listPartnerCategories("amazon", sellerId);

			Collections.sort(categoryList);
			model.put("partner", partner);
			model.put("categoryList", categoryList);
			model.put("partnerCatList", partnerCatList);
			model.put("datemap", datemap);
			model.put("partners", ConverterClass
					.prepareListofPartnerBean(partnerService
							.listPartners(helperClass
									.getSellerIdfromSession(request))));
			model.put("sellerTiers", sellerTierList);
			model.put("sellerTier", sellerTier);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by Seller ID : " + sellerId, e);
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

		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		log.debug(" Nr calculayor value from bean : "
				+ partnerBean.getNrnReturnConfig().isNrCalculator());

		log.debug(" Nr calculayor value from bean : "
				+ partnerBean.getNrnReturnConfig().isNrCalculator());
		partnerBean.setPcName(GlobalConstant.PCJABONG);

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
					try {
						nrnReturncharge.setChargeAmount(Float.parseFloat(entry
								.getValue()[0]));
						nrnReturncharge.setChargeName(temp);
					} catch (NumberFormatException e) {
						nrnReturncharge.setChargeAmount(1);
						nrnReturncharge.setChargeName(temp
								+ GlobalConstant.TaxCategoryPrefix
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
		if (partnerBean.getPcName().toLowerCase()
				.contains(GlobalConstant.PCFLIPKART)
				&& !partnerBean.isIsshippeddatecalcPost()) {
			partnerBean.setNoofdaysfromshippeddatePost(partnerBean
					.getNoofdaysfromdeliverydatePost());
		}
		if (partnerBean.getPcName().toLowerCase()
				.contains(GlobalConstant.PCFLIPKART)
				&& !partnerBean.isIsshippeddatecalcOthers()) {
			partnerBean.setNoofdaysfromshippeddateOthers(partnerBean
					.getNoofdaysfromdeliverydateOthers());
		}

		if (image != null) {
			if (!image.isEmpty()) {
				try {
					validateImage(image);

				} catch (RuntimeException re) {
					log.error("Failed! by Seller ID : " + sellerId, re);
					result.reject(re.getMessage());
				}
			}
		}

		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			props = PropertiesLoaderUtils.loadProperties(resource);

			if (!partnerList.contains(partnerBean.getPcName())) {
				partnerBean.setPcLogoUrl(props.getProperty("partnerimage.view")
						+ helperClass.getSellerIdfromSession(request)
						+ partnerBean.getPcName() + ".jpg");
				saveImage(partnerBean.getPcName() + ".jpg", image);
			} else {
				partnerBean.setPcLogoUrl(props.getProperty("partnerimage.view")
						+ partnerBean.getPcName() + ".jpg");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by Seller ID : " + sellerId, e);
			result.reject(e.getMessage());
			return new ModelAndView("redirect:/seller/partners.html");
		}

		try {

			Partner partner = ConverterClass.preparePartnerModel(partnerBean);
			partnerService.addPartner(partner,
					helperClass.getSellerIdfromSession(request));
		} catch (CustomException ce) {
			log.error("SaveJabong exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			log.error("Failed! by Seller ID : " + sellerId, e);
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
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		log.debug(" Nr calculayor value from bean : "
				+ partnerBean.getNrnReturnConfig().isNrCalculator());

		log.debug(" Nr calculayor value from bean : "
				+ partnerBean.getNrnReturnConfig().isNrCalculator());

		partnerBean.setPcName(GlobalConstant.PCMYNTRA);

		Map<String, String[]> parameters = request.getParameterMap();
		for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
			log.debug(" Request Param: " + entry.getKey() + "  Values  : "
					+ entry.getValue().length);

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
						nrnReturncharge.setChargeName(temp
								+ GlobalConstant.TaxCategoryPrefix
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
		if (partnerBean.getPcName().toLowerCase()
				.contains(GlobalConstant.PCFLIPKART)
				&& !partnerBean.isIsshippeddatecalcPost()) {
			partnerBean.setNoofdaysfromshippeddatePost(partnerBean
					.getNoofdaysfromdeliverydatePost());
		}
		if (partnerBean.getPcName().toLowerCase()
				.contains(GlobalConstant.PCFLIPKART)
				&& !partnerBean.isIsshippeddatecalcOthers()) {
			partnerBean.setNoofdaysfromshippeddateOthers(partnerBean
					.getNoofdaysfromdeliverydateOthers());
		}

		if (image != null) {
			if (!image.isEmpty()) {
				try {
					validateImage(image);
				} catch (RuntimeException re) {
					re.printStackTrace();
					log.error("Failed! by Seller ID : " + sellerId, re);
					result.reject(re.getMessage());
				}
			}
		}
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			props = PropertiesLoaderUtils.loadProperties(resource);

			if (!partnerList.contains(partnerBean.getPcName())) {
				partnerBean.setPcLogoUrl(props.getProperty("partnerimage.view")
						+ helperClass.getSellerIdfromSession(request)
						+ partnerBean.getPcName() + ".jpg");
				saveImage(partnerBean.getPcName() + ".jpg", image);
			} else {
				partnerBean.setPcLogoUrl(props.getProperty("partnerimage.view")
						+ partnerBean.getPcName() + ".jpg");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by Seller ID : " + sellerId, e);
			result.reject(e.getMessage());
			return new ModelAndView("redirect:/seller/partners.html");
		}

		try {

			Partner partner = ConverterClass.preparePartnerModel(partnerBean);
			partnerService.addPartner(partner,
					helperClass.getSellerIdfromSession(request));
		} catch (CustomException ce) {
			log.error("SaveMyntra exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			log.error("Failed! by Seller ID : " + sellerId, e);
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
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> datemap = new LinkedHashMap<String, Object>();
		List<String> categoryList = new ArrayList<String>();
		List<String> taxCategoryList = new ArrayList<String>();
		List<Category> categoryObjects = null;
		List<TaxCategory> taxCategoryObjects = null;

		datemap.put("true", "Select payment from");
		datemap.put("true", "Shipping Date");
		datemap.put("false", "Delivery Date");
		PartnerBean partner = new PartnerBean();
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			categoryObjects = categoryService.listCategories(helperClass
					.getSellerIdfromSession(request));
			if (categoryObjects != null) {
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
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by Seller ID : " + sellerId, e);
		}
		String partnerName = request.getParameter("partnerName");
		try {
			if (partnerName != null) {
				partner.setPcName(partnerName);
			}
			model.put("partner", partner);
			model.put("categoryList", categoryList);
			model.put("taxCategoryList", taxCategoryList);
			model.put("datemap", datemap);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by Seller ID : " + sellerId, e);
		}
		log.info("$$$ addJabong Ends : OrderController $$$");
		return new ModelAndView("initialsetup/addJabong", model);
	}

	@RequestMapping(value = "/seller/addMyntra", method = RequestMethod.GET)
	public ModelAndView addMyntra(HttpServletRequest request,
			@ModelAttribute("command") PartnerBean partnerBean,
			BindingResult result) {

		log.info("$$$ addMyntra Starts : OrderController $$$");
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> datemap = new LinkedHashMap<String, Object>();
		List<String> categoryList = new ArrayList<String>();
		List<String> taxCategoryList = new ArrayList<String>();
		List<Category> categoryObjects = null;
		List<TaxCategory> taxCategoryObjects = null;
		datemap.put("true", "Select payment from");
		datemap.put("true", "Shipping Date");
		datemap.put("false", "Delivery Date");
		PartnerBean partner = new PartnerBean();
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
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
		} /*
		 * catch (CustomException ce) { log.error("addPartner exception : " +
		 * ce.toString()); model.put("errorMessage", ce.getLocalMessage());
		 * model.put("errorTime", ce.getErrorTime()); model.put("errorCode",
		 * ce.getErrorCode()); return new ModelAndView("globalErorPage", model);
		 * }
		 */catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by Seller ID : " + sellerId, e);
		}

		try {
			model.put("partner", partner);
			model.put("categoryList", categoryList);
			model.put("taxCategoryList", taxCategoryList);
			model.put("datemap", datemap);
			model.put("partners", ConverterClass
					.prepareListofPartnerBean(partnerService
							.listPartners(helperClass
									.getSellerIdfromSession(request))));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by Seller ID : " + sellerId, e);
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
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		PartnerBean partner = new PartnerBean();
		String id = request.getParameter("pid");
		log.debug(" Inside partner controller :" + id);

		try {
			sellerId = helperClass.getSellerIdfromSession(request);
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
			log.error("Failed! by Seller ID : " + sellerId, e);
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
		int sellerId = 0;
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
			log.error("Failed! by Seller ID : " + sellerId, ce);
			log.error("deletePartner exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by Seller ID : " + sellerId, e);
		}
		log.info("$$$ deletePartner Ends : OrderController $$$");
		return new ModelAndView("addPartner", model);
	}

	@RequestMapping(value = "/seller/viewPartner", method = RequestMethod.GET)
	public ModelAndView viewPartner(HttpServletRequest request,
			@ModelAttribute("command") PartnerBean partnerBean,
			BindingResult result) {

		log.info("$$$ viewPartner Starts : OrderController $$$");
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> datemap = new HashMap<String, Object>();
		PartnerBean pbean = null;
		Map<String, Float> chargeMap = new HashMap<String, Float>();
		Map<String, Float> categoryMap = new HashMap<String, Float>();

		Map<String, String> chargesMap = new HashMap<String, String>();
		Map<String, Float> commissionMap = new HashMap<String, Float>();
		Map<String, String> taxSpMap = new HashMap<String, String>();
		Map<String, String> taxPoMap = new HashMap<String, String>();

		List<Category> categoryObjects = null;
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
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

				if (pbean.getPcName().equalsIgnoreCase(GlobalConstant.PCMYNTRA)
						|| pbean.getPcName().equalsIgnoreCase(
								GlobalConstant.PCJABONG)) {
					if (charge.getChargeName().contains(
							GlobalConstant.TaxCategoryPrefix)) {
						String[] split = charge.getChargeName().split(
								GlobalConstant.TaxCategoryPrefix);
						String firstSubString = split[0];
						String secondSubString = split[1];
						if (firstSubString.contains(GlobalConstant.TaxSPPrefix)) {
							String key = firstSubString
									.substring(GlobalConstant.TaxSPPrefix
											.length());
							taxSpMap.put(key, secondSubString);
						} else if (firstSubString
								.contains(GlobalConstant.TaxPOPrefix)) {
							String key = firstSubString
									.substring(GlobalConstant.TaxPOPrefix
											.length());
							taxPoMap.put(key, secondSubString);
						} else {
							chargesMap.put(firstSubString, secondSubString);
						}
					} else {
						if (charge.getChargeName().contains(
								GlobalConstant.CommPOPrefix)) {
							String key = charge.getChargeName().substring(
									GlobalConstant.CommPOPrefix.length());
							commissionMap.put(key, charge.getChargeAmount());
						} else {
							chargesMap.put(charge.getChargeName(),
									String.valueOf(charge.getChargeAmount()));
						}
					}

				} else {
					chargeMap.put(charge.getChargeName(),
							charge.getChargeAmount());
				}
			}
			for (NRnReturnCharges charge : pbean.getNrnReturnConfig()
					.getCharges()) {
				if (charge.getChargeName().contains("fixedfee")
						&& charge.getCriteria() != null
						&& !"".equals(charge.getCriteria())) {

					ChargesBean chargeBean = new ChargesBean();
					chargeBean.setChargeType("fixedfee");
					chargeBean.setCriteria(charge.getCriteria());
					chargeBean.setRange(charge.getCriteriaRange());
					chargeBean.setValue(charge.getChargeAmount());
					pbean.getFixedfeeList().add(chargeBean);

				} else if (charge.getChargeName().contains("shippingfeeVolume")
						&& charge.getCriteria() != null
						&& !"".equals(charge.getCriteria())) {

					if (pbean.getNrnReturnConfig().getShippingFeeType()
							.equalsIgnoreCase("variable")
							&& charge.getChargeName().contains(
									"shippingfeeVolumeVariable")) {

						ChargesBean chargeBean = pbean
								.getChargesBean("shippingfeeVolumeVariable",
										charge.getCriteria(),
										charge.getCriteriaRange());
						if (chargeBean == null) {
							chargeBean = new ChargesBean();
							chargeBean
									.setChargeType("shippingfeeVolumeVariable");
							chargeBean.setCriteria(charge.getCriteria());
							chargeBean.setRange(charge.getCriteriaRange());
							pbean.getshippingfeeVolumeVariableList().add(
									chargeBean);
						}

						if (charge.getChargeName().contains("Local")) {
							chargeBean.setLocalValue(charge.getChargeAmount());
						} else if (charge.getChargeName().contains("Zonal")) {
							chargeBean.setZonalValue(charge.getChargeAmount());
						} else if (charge.getChargeName().contains("National")) {
							chargeBean.setNationalValue(charge
									.getChargeAmount());
						} else if (charge.getChargeName().contains("Metro")) {
							chargeBean.setMetroValue(charge.getChargeAmount());
						}

					} else if (charge.getChargeName().contains(
							"shippingfeeVolumeFixed")) {
						ChargesBean chargeBean = new ChargesBean();
						chargeBean.setChargeType("shippingfeeVolumeFixed");
						chargeBean.setCriteria(charge.getCriteria());
						chargeBean.setRange(charge.getCriteriaRange());
						chargeBean.setValue(charge.getChargeAmount());
						pbean.getshippingfeeVolumeFixedList().add(chargeBean);
					}

				} else if (charge.getChargeName().contains("shippingfeeWeight")
						&& charge.getCriteria() != null
						&& !"".equals(charge.getCriteria())) {

					if (pbean.getNrnReturnConfig().getShippingFeeType()
							.equalsIgnoreCase("variable")
							&& charge.getChargeName().contains(
									"shippingfeeWeightVariable")) {

						ChargesBean chargeBean = pbean
								.getChargesBean("shippingfeeWeightVariable",
										charge.getCriteria(),
										charge.getCriteriaRange());
						if (chargeBean == null) {
							chargeBean = new ChargesBean();
							chargeBean
									.setChargeType("shippingfeeWeightVariable");
							chargeBean.setCriteria(charge.getCriteria());
							chargeBean.setRange(charge.getCriteriaRange());
							pbean.getshippingfeeWeightVariableList().add(
									chargeBean);
						}

						if (charge.getChargeName().contains("Local")) {
							chargeBean.setLocalValue(charge.getChargeAmount());
						} else if (charge.getChargeName().contains("Zonal")) {
							chargeBean.setZonalValue(charge.getChargeAmount());
						} else if (charge.getChargeName().contains("National")) {
							chargeBean.setNationalValue(charge
									.getChargeAmount());
						} else if (charge.getChargeName().contains("Metro")) {
							chargeBean.setMetroValue(charge.getChargeAmount());
						}

					} else if (charge.getChargeName().contains(
							"shippingfeeWeightFixed")) {
						ChargesBean chargeBean = new ChargesBean();
						chargeBean.setChargeType("shippingfeeWeightFixed");
						chargeBean.setCriteria(charge.getCriteria());
						chargeBean.setRange(charge.getCriteriaRange());
						chargeBean.setValue(charge.getChargeAmount());
						pbean.getshippingfeeWeightFixedList().add(chargeBean);
					}

				} else {
					chargeMap.put(charge.getChargeName(),
							charge.getChargeAmount());
				}

			}
			if (pbean.getFixedfeeList() != null
					&& pbean.getFixedfeeList().size() != 0)
				Collections.sort(pbean.getFixedfeeList(),
						new SortByCriteriaRange());
			if (pbean.getshippingfeeVolumeFixedList() != null
					&& pbean.getshippingfeeVolumeFixedList().size() != 0)
				Collections.sort(pbean.getshippingfeeVolumeFixedList(),
						new SortByCriteria());
			if (pbean.getshippingfeeWeightFixedList() != null
					&& pbean.getshippingfeeWeightFixedList().size() != 0)
				Collections.sort(pbean.getshippingfeeWeightFixedList(),
						new SortByCriteria());
			if (pbean.getshippingfeeVolumeVariableList() != null
					&& pbean.getshippingfeeVolumeVariableList().size() != 0)
				Collections.sort(pbean.getshippingfeeVolumeVariableList(),
						new SortByCriteria());
			if (pbean.getshippingfeeWeightVariableList() != null
					&& pbean.getshippingfeeWeightVariableList().size() != 0)
				Collections.sort(pbean.getshippingfeeWeightVariableList(),
						new SortByCriteria());

			categoryObjects = categoryService.listCategories(helperClass
					.getSellerIdfromSession(request));
			if (categoryObjects != null && categoryObjects.size() > 0) {
				for (Category cat : categoryObjects) {
					categoryMap.put(cat.getCatName(),
							chargeMap.get(cat.getCatName()));
				}
			}

			model.put("categoryMap", categoryMap);
			System.out.println(pbean.getFixedfeeList().size());
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
			log.error("Failed! by Seller ID : " + sellerId, e);
			log.error("editPartner exception : " + e.getLocalizedMessage());
			model.put("errorMessage", e.getMessage());

		}

		if (pbean.getPcName().equalsIgnoreCase(GlobalConstant.PCMYNTRA)
				|| pbean.getPcName().equalsIgnoreCase(GlobalConstant.PCJABONG)) {

			model.put("chargesMap", chargesMap);
			model.put("commissionMap", commissionMap);
			model.put("taxSpMap", taxSpMap);
			model.put("taxPoMap", taxPoMap);

			return new ModelAndView("initialsetup/viewPOPartner", model);
		} else {
			return new ModelAndView("initialsetup/viewPartner", model);
		}
	}

	@RequestMapping(value = "/seller/editPartner", method = RequestMethod.GET)
	public ModelAndView editPartner(
			HttpServletRequest request,
			@ModelAttribute("command") PartnerBean partnerBean,
			@RequestParam(value = "isDuplicate", required = false) Boolean isDuplicate,
			BindingResult result) {

		log.info("$$$ editPartner Starts : OrderController $$$");
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> datemap = new HashMap<String, Object>();
		PartnerBean pbean = null;
		Map<String, Float> chargeMap = new HashMap<String, Float>();
		Map<String, Float> categoryMap = new HashMap<String, Float>();
		List<Category> categoryObjects = null;
		String mappedPartnerCat = "";

		try {
			sellerId = helperClass.getSellerIdfromSession(request);
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
				if (charge.getChargeName().contains("fixedfee")
						&& charge.getCriteria() != null
						&& !"".equals(charge.getCriteria())) {
					
					ChargesBean chargeBean = new ChargesBean();
					chargeBean.setCriteria(charge.getCriteria());
					chargeBean.setRange(charge.getCriteriaRange());
					chargeBean.setValue(charge.getChargeAmount());
					
					if (charge.getChargeName().contains("Others")) {
						chargeBean.setChargeType("fixedfeeOthers");
						pbean.getFixedfeeListOthers().add(chargeBean);
					} else if (charge.getChargeName().contains("Category")) {
						mappedPartnerCat = charge.getCriteria();
					} else {
						chargeBean.setChargeType("fixedfee");
						pbean.getFixedfeeList().add(chargeBean);
					}

				} else if (charge.getChargeName().contains("shippingfeeVolume")
						&& charge.getCriteria() != null
						&& !"".equals(charge.getCriteria())) {

					if (pbean.getNrnReturnConfig().getShippingFeeType()
							.equalsIgnoreCase("variable")
							&& charge.getChargeName().contains(
									"shippingfeeVolumeVariable")) {

						ChargesBean chargeBean = pbean
								.getChargesBean("shippingfeeVolumeVariable",
										charge.getCriteria(),
										charge.getCriteriaRange());
						if (chargeBean == null) {
							chargeBean = new ChargesBean();
							chargeBean
									.setChargeType("shippingfeeVolumeVariable");
							chargeBean.setCriteria(charge.getCriteria());
							chargeBean.setRange(charge.getCriteriaRange());
							pbean.getshippingfeeVolumeVariableList().add(
									chargeBean);
						}

						if (charge.getChargeName().contains("Local")) {
							chargeBean.setLocalValue(charge.getChargeAmount());
						} else if (charge.getChargeName().contains("Zonal")) {
							chargeBean.setZonalValue(charge.getChargeAmount());
						} else if (charge.getChargeName().contains("National")) {
							chargeBean.setNationalValue(charge
									.getChargeAmount());
						} else if (charge.getChargeName().contains("Metro")) {
							chargeBean.setMetroValue(charge.getChargeAmount());
						}

					} else if (charge.getChargeName().contains(
							"shippingfeeVolumeFixed")) {
						ChargesBean chargeBean = new ChargesBean();
						chargeBean.setChargeType("shippingfeeVolumeFixed");
						chargeBean.setCriteria(charge.getCriteria());
						chargeBean.setRange(charge.getCriteriaRange());
						chargeBean.setValue(charge.getChargeAmount());
						pbean.getshippingfeeVolumeFixedList().add(chargeBean);
					}

				} else if (charge.getChargeName().contains("shippingfeeWeight")
						&& charge.getCriteria() != null
						&& !"".equals(charge.getCriteria())) {

					if (pbean.getNrnReturnConfig().getShippingFeeType()
							.equalsIgnoreCase("variable")
							&& charge.getChargeName().contains(
									"shippingfeeWeightVariable")) {

						ChargesBean chargeBean = pbean
								.getChargesBean("shippingfeeWeightVariable",
										charge.getCriteria(),
										charge.getCriteriaRange());
						if (chargeBean == null) {
							chargeBean = new ChargesBean();
							chargeBean
									.setChargeType("shippingfeeWeightVariable");
							chargeBean.setCriteria(charge.getCriteria());
							chargeBean.setRange(charge.getCriteriaRange());
							pbean.getshippingfeeWeightVariableList().add(
									chargeBean);
						}

						if (charge.getChargeName().contains("Local")) {
							chargeBean.setLocalValue(charge.getChargeAmount());
						} else if (charge.getChargeName().contains("Zonal")) {
							chargeBean.setZonalValue(charge.getChargeAmount());
						} else if (charge.getChargeName().contains("National")) {
							chargeBean.setNationalValue(charge
									.getChargeAmount());
						} else if (charge.getChargeName().contains("Metro")) {
							chargeBean.setMetroValue(charge.getChargeAmount());
						}

					} else if (charge.getChargeName().contains(
							"shippingfeeWeightFixed")) {
						ChargesBean chargeBean = new ChargesBean();
						chargeBean.setChargeType("shippingfeeWeightFixed");
						chargeBean.setCriteria(charge.getCriteria());
						chargeBean.setRange(charge.getCriteriaRange());
						chargeBean.setValue(charge.getChargeAmount());
						pbean.getshippingfeeWeightFixedList().add(chargeBean);
					}

				} else {
					chargeMap.put(charge.getChargeName(),
							charge.getChargeAmount());
				}

			}
			if (pbean.getFixedfeeList() != null
					&& pbean.getFixedfeeList().size() != 0)
				Collections.sort(pbean.getFixedfeeList(),
						new SortByCriteriaRange());
			if (pbean.getFixedfeeListOthers() != null
					&& pbean.getFixedfeeListOthers().size() != 0)
				Collections.sort(pbean.getFixedfeeListOthers(),
						new SortByCriteriaRange());
			if (pbean.getshippingfeeVolumeFixedList() != null
					&& pbean.getshippingfeeVolumeFixedList().size() != 0)
				Collections.sort(pbean.getshippingfeeVolumeFixedList(),
						new SortByCriteria());
			if (pbean.getshippingfeeWeightFixedList() != null
					&& pbean.getshippingfeeWeightFixedList().size() != 0)
				Collections.sort(pbean.getshippingfeeWeightFixedList(),
						new SortByCriteria());
			if (pbean.getshippingfeeVolumeVariableList() != null
					&& pbean.getshippingfeeVolumeVariableList().size() != 0)
				Collections.sort(pbean.getshippingfeeVolumeVariableList(),
						new SortByCriteria());
			if (pbean.getshippingfeeWeightVariableList() != null
					&& pbean.getshippingfeeWeightVariableList().size() != 0)
				Collections.sort(pbean.getshippingfeeWeightVariableList(),
						new SortByCriteria());

			categoryObjects = categoryService.listCategories(helperClass
					.getSellerIdfromSession(request));
			if (categoryObjects != null) {
				for (Category cat : categoryObjects) {
					categoryMap.put(cat.getCatName(),
							chargeMap.get(cat.getCatName()));
				}
			}

			if (isDuplicate != null && isDuplicate) {
				pbean.setPcId(0);
				pbean.getNrnReturnConfig().setConfigId(0);
				pbean.setPcName(pbean.getPcName() + "_");
			}

			Map<String, Float> sortedCategoryMap = new TreeMap<String, Float>(
					categoryMap);
			
			List<String> partnerCatList = categoryService
					.listPartnerCategories("amazon", sellerId);
			
			List<String> mappedPartnerCatList = null;
			if (!"".equals(mappedPartnerCat)) {
				mappedPartnerCatList = new ArrayList<String>(Arrays.asList(mappedPartnerCat.split(",")));
			} else {
				pbean.setFixedfeeListOthers(null);
			}
			request.getSession().setAttribute("localList", pbean.getNrnReturnConfig().getLocalList());
			request.getSession().setAttribute("zonalList", pbean.getNrnReturnConfig().getZonalList());
			request.getSession().setAttribute("nationalList", pbean.getNrnReturnConfig().getNationalList());
			request.getSession().setAttribute("metroList", pbean.getNrnReturnConfig().getMetroList());
			model.put("mappedPartnerCatList", mappedPartnerCatList);
			model.put("sellerTiers", sellerTierList);
			model.put("sellerTier", pbean.getSellerTier());
			model.put("partnerCatList", partnerCatList);
			model.put("categoryMap", sortedCategoryMap);
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
			log.error("Failed! by Seller ID : " + sellerId, e);
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

		log.info("$$$ editMyntra Starts : OrderController $$$");
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> datemap = new HashMap<String, Object>();
		PartnerBean pbean = null;
		Map<String, String> chargesMap = new HashMap<String, String>();
		Map<String, Float> commissionMap = new HashMap<String, Float>();
		Map<String, String> taxSpMap = new HashMap<String, String>();
		Map<String, String> taxPoMap = new HashMap<String, String>();
		List<String> categoryList = new ArrayList<String>();
		List<String> taxCategoryList = new ArrayList<String>();
		List<Category> categoryObjects = null;
		List<TaxCategory> taxCategoryObjects = null;

		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			datemap.put("true", "Select payment from");
			datemap.put("true", "Shipping Date");
			datemap.put("false", "Delivery Date");
			model.put("datemap", datemap);
			pbean = ConverterClass.preparePartnerBean(partnerService
					.getPartner(partnerBean.getPcId()));
			log.debug("************** Inside edit Myntra : config ID :"
					+ pbean.getNrnReturnConfig().getConfigId());
			for (NRnReturnCharges charge : pbean.getNrnReturnConfig()
					.getCharges()) {
				if (charge.getChargeName().contains(
						GlobalConstant.TaxCategoryPrefix)) {
					String[] split = charge.getChargeName().split(
							GlobalConstant.TaxCategoryPrefix);
					String firstSubString = split[0];
					String secondSubString = split[1];
					if (firstSubString.contains(GlobalConstant.TaxSPPrefix)) {
						String key = firstSubString
								.substring(GlobalConstant.TaxSPPrefix.length());
						taxSpMap.put(key, secondSubString);
					} else if (firstSubString
							.contains(GlobalConstant.TaxPOPrefix)) {
						String key = firstSubString
								.substring(GlobalConstant.TaxPOPrefix.length());
						taxPoMap.put(key, secondSubString);
					} else {
						chargesMap.put(firstSubString, secondSubString);
					}
				} else {
					if (charge.getChargeName().contains(
							GlobalConstant.CommPOPrefix)) {
						String key = charge.getChargeName().substring(
								GlobalConstant.CommPOPrefix.length());
						commissionMap.put(key, charge.getChargeAmount());
					} else {
						chargesMap.put(charge.getChargeName(),
								String.valueOf(charge.getChargeAmount()));
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
			log.error("editMyntra exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by Seller ID : " + sellerId, e);
			log.error("editPartner exception : " + e.getLocalizedMessage());
			model.put("errorMessage", e.getMessage());

		}
		log.info("$$$ editMyntra Ends : OrderController $$$");
		return new ModelAndView("initialsetup/addMyntra", model);
	}

	@RequestMapping(value = "/seller/editJabong", method = RequestMethod.GET)
	public ModelAndView editJabong(HttpServletRequest request,
			@ModelAttribute("command") PartnerBean partnerBean,
			BindingResult result) {

		log.info("$$$ editJabong Starts : OrderController $$$");
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> datemap = new HashMap<String, Object>();
		PartnerBean pbean = null;
		Map<String, Float> chargeMap = new HashMap<String, Float>();
		Map<String, Float> categoryMap = new HashMap<String, Float>();
		List<Category> categoryObjects = null;

		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			datemap.put("true", "Select payment from");
			datemap.put("true", "Shipping Date");
			datemap.put("false", "Delivery Date");
			model.put("datemap", datemap);
			pbean = ConverterClass.preparePartnerBean(partnerService
					.getPartner(partnerBean.getPcId()));
			log.debug("************** Inside edit Jabong : config ID :"
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
			log.error("editJabong exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by Seller ID : " + sellerId, e);
			log.error("editJabong exception : " + e.getLocalizedMessage());
			model.put("errorMessage", e.getMessage());

		}
		log.info("$$$ editJabong Ends : OrderController $$$");
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
			log.debug(" context.getRealPath(/) " + context.getRealPath("/"));
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
		int sellerId = 0;
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
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
			log.error("Failed! by Seller ID : " + sellerId, e);
			return "Error";
		}
	}

	@RequestMapping(value = "/seller/saveMetaPartner", method = RequestMethod.POST)
	public ModelAndView saveMetaPartner(HttpServletRequest request,
			@ModelAttribute("command") MetaPartnerBean partnerBean,
			BindingResult result,
			@RequestParam(value = "image", required = false) MultipartFile image) {

		log.info("$$$ saveMetaPartner Starts : OrderController $$$");
		log.info(partnerBean.getFixedfeeList().size());

		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		MetaPartner existPartner = null;
		String tier = "";
		try {			
			sellerId = helperClass.getSellerIdfromSession(request);
			tier = request.getParameter("sellerTier");
			if(!tier.equals("")){
				partnerBean.setPcName(partnerBean.getPcName()+"-$$"+tier);
			}
			existPartner = partnerService.getMetaPartner(partnerBean
					.getPcName());
		} catch (Exception e) {
			log.error("Exception in getPartner(name,sellerId) : ", e);
		}

		if (partnerBean.getPcId() != 0) {
			log.debug("******** ConfigId : "
					+ partnerBean.getNrnReturnConfig().getConfigId());
			log.debug("******** Partner ID : " + partnerBean.getPcId());

		}

		log.debug(" Nr calculayor value from bean : "
				+ partnerBean.getNrnReturnConfig().isNrCalculator());
		Map<String, String[]> parameters = request.getParameterMap();

		List<String> fixedfeeParams = new ArrayList<String>();
		List<String> shippingfeeVolumeParams = new ArrayList<String>();
		List<String> shippingfeeWeightParams = new ArrayList<String>();

		try {
			for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
				if (entry.getKey().contains("nr-")) {
					log.info(" Key with nr: " + entry.getKey()
							+ " Values is : " + entry.getValue()[0]);

					if (entry.getKey().contains("fixedfee")) {

						String param = entry.getKey().substring(0,
								entry.getKey().lastIndexOf('-') + 1);
						if (!fixedfeeParams.contains(param)) {
							fixedfeeParams.add(param);
							MetaNRnReturnCharges nrnReturncharge = new MetaNRnReturnCharges();
							String charge = parameters.get(param + "value")[0];
							if (charge.isEmpty()) {
								nrnReturncharge.setChargeAmount(0);
							} else {
								nrnReturncharge.setChargeAmount(Float
										.parseFloat(charge));
							}

							nrnReturncharge.setChargeName("fixedfee");
							nrnReturncharge.setCriteria(parameters.get(param
									+ "criteria")[0]);
							if (!parameters.get(param + "range")[0].isEmpty()) {
								nrnReturncharge.setCriteriaRange(Long
										.parseLong(parameters.get(param
												+ "range")[0]));
							}

							nrnReturncharge.setConfig(partnerBean
									.getNrnReturnConfig());
							partnerBean.getNrnReturnConfig().getCharges()
									.add(nrnReturncharge);

						}
					} else if (entry.getKey().contains("shippingfeeVolume")
							&& partnerBean.getNrnReturnConfig()
									.getShippingFeeType() != null) {

						if (partnerBean.getNrnReturnConfig()
								.getShippingFeeType()
								.equalsIgnoreCase("variable")
								&& entry.getKey().contains(
										"shippingfeeVolumeVariable")) {
							String param = entry.getKey().substring(0,
									entry.getKey().lastIndexOf('-') + 1);
							if (!shippingfeeVolumeParams.contains(param)) {
								shippingfeeVolumeParams.add(param);
								MetaNRnReturnCharges nrnReturncharge = new MetaNRnReturnCharges();

								if (!parameters.get(param + "localValue")[0]
										.isEmpty())
									nrnReturncharge.setChargeAmount(Float
											.parseFloat(parameters.get(param
													+ "localValue")[0]));
								nrnReturncharge
										.setChargeName("shippingfeeVolumeVariableLocal");
								nrnReturncharge.setCriteria(parameters
										.get(param + "criteria")[0]);

								if (!parameters.get(param + "range")[0]
										.isEmpty())
									nrnReturncharge.setCriteriaRange(Long
											.parseLong(parameters.get(param
													+ "range")[0]));

								nrnReturncharge.setConfig(partnerBean
										.getNrnReturnConfig());
								partnerBean.getNrnReturnConfig().getCharges()
										.add(nrnReturncharge);

								nrnReturncharge = new MetaNRnReturnCharges();
								if (!parameters.get(param + "zonalValue")[0]
										.isEmpty())
									nrnReturncharge.setChargeAmount(Float
											.parseFloat(parameters.get(param
													+ "zonalValue")[0]));
								nrnReturncharge
										.setChargeName("shippingfeeVolumeVariableZonal");
								nrnReturncharge.setCriteria(parameters
										.get(param + "criteria")[0]);
								if (!parameters.get(param + "range")[0]
										.isEmpty())
									nrnReturncharge.setCriteriaRange(Long
											.parseLong(parameters.get(param
													+ "range")[0]));

								nrnReturncharge.setConfig(partnerBean
										.getNrnReturnConfig());
								partnerBean.getNrnReturnConfig().getCharges()
										.add(nrnReturncharge);

								nrnReturncharge = new MetaNRnReturnCharges();
								if (!parameters.get(param + "nationalValue")[0]
										.isEmpty())
									nrnReturncharge.setChargeAmount(Float
											.parseFloat(parameters.get(param
													+ "nationalValue")[0]));
								nrnReturncharge
										.setChargeName("shippingfeeVolumeVariableNational");
								nrnReturncharge.setCriteria(parameters
										.get(param + "criteria")[0]);
								if (!parameters.get(param + "range")[0]
										.isEmpty())
									nrnReturncharge.setCriteriaRange(Long
											.parseLong(parameters.get(param
													+ "range")[0]));

								nrnReturncharge.setConfig(partnerBean
										.getNrnReturnConfig());
								partnerBean.getNrnReturnConfig().getCharges()
										.add(nrnReturncharge);

								nrnReturncharge = new MetaNRnReturnCharges();
								if (!parameters.get(param + "metroValue")[0]
										.isEmpty())
									nrnReturncharge.setChargeAmount(Float
											.parseFloat(parameters.get(param
													+ "metroValue")[0]));
								nrnReturncharge
										.setChargeName("shippingfeeVolumeVariableMetro");
								nrnReturncharge.setCriteria(parameters
										.get(param + "criteria")[0]);
								if (!parameters.get(param + "range")[0]
										.isEmpty())
									nrnReturncharge.setCriteriaRange(Long
											.parseLong(parameters.get(param
													+ "range")[0]));

								nrnReturncharge.setConfig(partnerBean
										.getNrnReturnConfig());
								partnerBean.getNrnReturnConfig().getCharges()
										.add(nrnReturncharge);

							}
						} else if (entry.getKey().contains(
								"shippingfeeVolumeFixed")) {
							String param = entry.getKey().substring(0,
									entry.getKey().lastIndexOf('-') + 1);
							if (!shippingfeeVolumeParams.contains(param)) {
								shippingfeeVolumeParams.add(param);
								MetaNRnReturnCharges nrnReturncharge = new MetaNRnReturnCharges();
								if (!parameters.get(param + "value")[0]
										.isEmpty())
									nrnReturncharge.setChargeAmount(Float
											.parseFloat(parameters.get(param
													+ "value")[0]));
								nrnReturncharge
										.setChargeName("shippingfeeVolumeFixed");
								nrnReturncharge.setCriteria(parameters
										.get(param + "criteria")[0]);
								if (!parameters.get(param + "range")[0]
										.isEmpty())
									nrnReturncharge.setCriteriaRange(Long
											.parseLong(parameters.get(param
													+ "range")[0]));

								nrnReturncharge.setConfig(partnerBean
										.getNrnReturnConfig());
								partnerBean.getNrnReturnConfig().getCharges()
										.add(nrnReturncharge);
							}
						}
					} else if (entry.getKey().contains("shippingfeeWeight")
							&& partnerBean.getNrnReturnConfig()
									.getShippingFeeType() != null) {

						if (partnerBean.getNrnReturnConfig()
								.getShippingFeeType()
								.equalsIgnoreCase("variable")
								&& entry.getKey().contains(
										"shippingfeeWeightVariable")) {
							String param = entry.getKey().substring(0,
									entry.getKey().lastIndexOf('-') + 1);
							if (!shippingfeeVolumeParams.contains(param)) {
								shippingfeeVolumeParams.add(param);
								MetaNRnReturnCharges nrnReturncharge = new MetaNRnReturnCharges();

								if (!parameters.get(param + "localValue")[0]
										.isEmpty()) {
									nrnReturncharge.setChargeAmount(Float
											.parseFloat(parameters.get(param
													+ "localValue")[0]));
								}

								nrnReturncharge
										.setChargeName("shippingfeeWeightVariableLocal");
								nrnReturncharge.setCriteria(parameters
										.get(param + "criteria")[0]);
								if (!parameters.get(param + "range")[0]
										.isEmpty())
									nrnReturncharge.setCriteriaRange(Long
											.parseLong(parameters.get(param
													+ "range")[0]));

								nrnReturncharge.setConfig(partnerBean
										.getNrnReturnConfig());
								partnerBean.getNrnReturnConfig().getCharges()
										.add(nrnReturncharge);

								nrnReturncharge = new MetaNRnReturnCharges();
								if (!parameters.get(param + "zonalValue")[0]
										.isEmpty()) {
									nrnReturncharge.setChargeAmount(Float
											.parseFloat(parameters.get(param
													+ "zonalValue")[0]));
								}

								nrnReturncharge
										.setChargeName("shippingfeeWeightVariableZonal");
								nrnReturncharge.setCriteria(parameters
										.get(param + "criteria")[0]);
								if (!parameters.get(param + "range")[0]
										.isEmpty())
									nrnReturncharge.setCriteriaRange(Long
											.parseLong(parameters.get(param
													+ "range")[0]));

								nrnReturncharge.setConfig(partnerBean
										.getNrnReturnConfig());
								partnerBean.getNrnReturnConfig().getCharges()
										.add(nrnReturncharge);

								nrnReturncharge = new MetaNRnReturnCharges();
								if (!parameters.get(param + "nationalValue")[0]
										.isEmpty()) {
									nrnReturncharge.setChargeAmount(Float
											.parseFloat(parameters.get(param
													+ "nationalValue")[0]));
								}

								nrnReturncharge
										.setChargeName("shippingfeeWeightVariableNational");
								nrnReturncharge.setCriteria(parameters
										.get(param + "criteria")[0]);
								if (!parameters.get(param + "range")[0]
										.isEmpty())
									nrnReturncharge.setCriteriaRange(Long
											.parseLong(parameters.get(param
													+ "range")[0]));

								nrnReturncharge.setConfig(partnerBean
										.getNrnReturnConfig());
								partnerBean.getNrnReturnConfig().getCharges()
										.add(nrnReturncharge);

								nrnReturncharge = new MetaNRnReturnCharges();
								if (!parameters.get(param + "metroValue")[0]
										.isEmpty()) {
									nrnReturncharge.setChargeAmount(Float
											.parseFloat(parameters.get(param
													+ "metroValue")[0]));
								}
								nrnReturncharge
										.setChargeName("shippingfeeWeightVariableMetro");
								nrnReturncharge.setCriteria(parameters
										.get(param + "criteria")[0]);

								if (!parameters.get(param + "range")[0]
										.isEmpty())
									nrnReturncharge.setCriteriaRange(Long
											.parseLong(parameters.get(param
													+ "range")[0]));

								nrnReturncharge.setConfig(partnerBean
										.getNrnReturnConfig());
								partnerBean.getNrnReturnConfig().getCharges()
										.add(nrnReturncharge);

							}
						} else if (entry.getKey().contains(
								"shippingfeeWeightFixed")) {
							String param = entry.getKey().substring(0,
									entry.getKey().lastIndexOf('-') + 1);
							if (!shippingfeeWeightParams.contains(param)) {
								shippingfeeWeightParams.add(param);
								MetaNRnReturnCharges nrnReturncharge = new MetaNRnReturnCharges();
								if (!parameters.get(param + "value")[0]
										.isEmpty())
									nrnReturncharge.setChargeAmount(Float
											.parseFloat(parameters.get(param
													+ "value")[0]));
								nrnReturncharge
										.setChargeName("shippingfeeWeightFixed");
								nrnReturncharge.setCriteria(parameters
										.get(param + "criteria")[0]);
								if (!parameters.get(param + "range")[0]
										.isEmpty())
									nrnReturncharge.setCriteriaRange(Long
											.parseLong(parameters.get(param
													+ "range")[0]));

								nrnReturncharge.setConfig(partnerBean
										.getNrnReturnConfig());
								partnerBean.getNrnReturnConfig().getCharges()
										.add(nrnReturncharge);
							}
						}
					}
				}
			}
			for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
				if (entry.getKey() != null && !entry.getKey().isEmpty())
					log.debug(" Print :entry.getKey()  " + entry.getKey()
							+ " value: " + entry.getValue()[0]);

				if (entry.getValue()[0] != null
						&& !entry.getValue()[0].isEmpty()) {
					if (entry.getKey().contains("nr-")) {

						if (entry.getKey().contains("fixedfee")
								|| entry.getKey().contains("shippingfee")) {

						} else {
							log.debug(" Key : " + entry.getKey());
							String temp = entry.getKey().substring(3);
							MetaNRnReturnCharges nrnReturncharge = new MetaNRnReturnCharges();
							nrnReturncharge.setChargeAmount(Float
									.parseFloat(entry.getValue()[0]));
							nrnReturncharge.setChargeName(temp);
							nrnReturncharge.setConfig(partnerBean
									.getNrnReturnConfig());
							partnerBean.getNrnReturnConfig().getCharges()
									.add(nrnReturncharge);
						}
					} else if (entry.getKey().contains("local")) {

						String localstring = Arrays.toString(entry.getValue());
						log.debug("localstring " + localstring);
						partnerBean.getNrnReturnConfig().setLocalList(
								localstring.substring(localstring.toString()
										.indexOf('[') + 1, localstring
										.toString().indexOf(']')));
					} else if (entry.getKey().contains("zonal")) {
						String zonalstring = Arrays.toString(entry.getValue());
						log.debug("zonalstring " + zonalstring);

						partnerBean.getNrnReturnConfig().setZonalList(
								zonalstring.substring(zonalstring.toString()
										.indexOf('[') + 1, zonalstring
										.toString().indexOf(']')));
					} else if (entry.getKey().contains("national")) {
						String nationalstring = Arrays.toString(entry
								.getValue());
						partnerBean.getNrnReturnConfig()
								.setNationalList(
										nationalstring.substring(nationalstring
												.toString().indexOf('[') + 1,
												nationalstring.toString()
														.indexOf(']')));
					} else if (entry.getKey().contains("metro")) {
						String metrostring = Arrays.toString(entry.getValue());
						log.debug("metrostring " + metrostring);

						partnerBean.getNrnReturnConfig().setMetroList(
								metrostring.substring(metrostring.toString()
										.indexOf('[') + 1, metrostring
										.toString().indexOf(']')));
					}
				}
			}

			if (!partnerBean.isIsshippeddatecalc()) {
				partnerBean.setNoofdaysfromshippeddate(partnerBean
						.getNoofdaysfromdeliverydate());
			}
			if (partnerBean.getPcName().toLowerCase()
					.contains(GlobalConstant.PCFLIPKART)
					&& !partnerBean.isIsshippeddatecalcPost()) {
				partnerBean.setNoofdaysfromshippeddatePost(partnerBean
						.getNoofdaysfromdeliverydatePost());
			}
			if (partnerBean.getPcName().toLowerCase()
					.contains(GlobalConstant.PCFLIPKART)
					&& !partnerBean.isIsshippeddatecalcOthers()) {
				partnerBean.setNoofdaysfromshippeddateOthers(partnerBean
						.getNoofdaysfromdeliverydateOthers());
			}

			if (image.getSize() != 0) {
				if (!image.isEmpty()) {
					try {
						validateImage(image);

					} catch (RuntimeException re) {
						log.error("Failed! by Seller ID : " + sellerId, re);
						result.reject(re.getMessage());
					}
				}
				try {
					props = PropertiesLoaderUtils.loadProperties(resource);
					partnerBean.setPcLogoUrl(props
							.getProperty("partnerimage.view")
							+ helperClass.getSellerIdfromSession(request)
							+ partnerBean.getPcName() + ".jpg");
					saveImage(helperClass.getSellerIdfromSession(request)
							+ partnerBean.getPcName() + ".jpg", image);

				} catch (Exception e) {
					e.printStackTrace();
					log.error("Failed! by Seller ID : " + sellerId, e);
					result.reject(e.getMessage());
					return new ModelAndView("redirect:/seller/partners.html");
				}
			} else {
				try {
					props = PropertiesLoaderUtils.loadProperties(resource);
					if (partnerList.contains(partnerBean.getPcName())) {
						partnerBean.setPcLogoUrl(props
								.getProperty("partnerimage.view")
								+ partnerBean.getPcName() + ".jpg");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			MetaPartner partner = ConverterClass
					.prepareMetaPartnerModel(partnerBean);
			if (existPartner != null && partnerBean.getPcId() == 0) {
				partner.setPcId(existPartner.getPcId());
			}
			partnerService.addMetaPartner(partner);
		} catch (CustomException ce) {
			log.error("SavePartner exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by Seller ID : " + sellerId, e);
		}

		log.info("$$$ savePartner Ends : OrderController $$$");
		return new ModelAndView("redirect:/seller/partners.html");
	}
}
