package com.o2r.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.extractor.EventBasedExcelExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.o2r.bean.ChargesBean;
import com.o2r.bean.EventsBean;
import com.o2r.bean.ChargesBean.SortByCriteria;
import com.o2r.bean.ChargesBean.SortByCriteriaRange;
import com.o2r.helper.ConverterClass;
import com.o2r.helper.CustomException;
import com.o2r.helper.HelperClass;
import com.o2r.model.Category;
import com.o2r.model.Events;
import com.o2r.model.NRnReturnCharges;
import com.o2r.model.Partner;
import com.o2r.model.Product;
import com.o2r.service.CategoryService;
import com.o2r.service.EventsService;
import com.o2r.service.PartnerService;
import com.o2r.service.ProductService;

@Controller
public class EventsController {

	@Autowired
	private EventsService eventsService;
	@Autowired
	private PartnerService partnerService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private HelperClass helperClass;
	@Autowired
	private ProductService productService;

	static Logger log = Logger.getLogger(EventsController.class.getName());

	@RequestMapping(value = "/seller/saveEvent", method = RequestMethod.POST)
	public ModelAndView saveEvents(HttpServletRequest request,
			@ModelAttribute("command") EventsBean eventsBean,
			BindingResult result) {

		log.info("$$$ saveEvents Starts : EventsController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		List<NRnReturnCharges> chargeList = new ArrayList<NRnReturnCharges>();
		Map<String, String[]> parameters = request.getParameterMap();
		StringBuffer skus4Event = new StringBuffer();
		List<String> fixedfeeParams = new ArrayList<String>();
		List<String> shippingfeeVolumeParams = new ArrayList<String>();
		List<String> shippingfeeWeightParams = new ArrayList<String>();

		String[] skuList = request.getParameterValues("multiSku");
		if (skuList != null && skuList.length != 0) {
			for (int i = 0; i < skuList.length; i++) {
				skus4Event.append(skuList[i] + ",");
			}
		}
		System.out.println(skus4Event.toString().contains("Ranjan"));
		eventsBean.setSkuList(skus4Event.toString());
		eventsBean.setStatus("Active");
		try {
			/*
			 * if(eventsBean.getEventId() != 0){ eventsBean.setEventId(0); }
			 */

			for (Map.Entry<String, String[]> entry : parameters.entrySet()) {

				if (entry.getValue()[0] != null
						&& !entry.getValue()[0].isEmpty()) {
					if (entry.getKey().contains("nrType"))
						eventsBean.setNrType(entry.getValue()[0]);
					else if (entry.getKey().contains("pCategories"))
						eventsBean.setProductCategories(entry.getValue()[0]);
					else if (entry.getKey().contains("nr-")) {

						if (entry.getKey().contains("fixedfee")
								|| entry.getKey().contains("shippingfee")) {

							if (entry.getKey().contains("fixedfee")) {

								String param = entry.getKey().substring(0,
										entry.getKey().lastIndexOf('-') + 1);
								if (!fixedfeeParams.contains(param)) {
									fixedfeeParams.add(param);
									NRnReturnCharges nrnReturncharge = new NRnReturnCharges();
									if(!parameters.get(param+ "value")[0].isEmpty())
										nrnReturncharge.setChargeAmount(Float
											.parseFloat(parameters.get(param
													+ "value")[0]));
									nrnReturncharge.setChargeName("fixedfee");
									if(!parameters.get(param + "criteria")[0].isEmpty())
										nrnReturncharge.setCriteria(parameters
											.get(param + "criteria")[0]);
									if(!parameters.get(param+ "range")[0].isEmpty())
										nrnReturncharge.setCriteriaRange(Long
											.parseLong(parameters.get(param
													+ "range")[0]));

									nrnReturncharge.setConfig(eventsBean
											.getNrnReturnConfig());
									chargeList.add(nrnReturncharge);

								}
							} else if (entry.getKey().contains(
									"shippingfeeVolume")
									&& eventsBean.getNrnReturnConfig()
											.getShippingFeeType() != null) {

								if (eventsBean.getNrnReturnConfig()
										.getShippingFeeType()
										.equalsIgnoreCase("variable")
										&& entry.getKey().contains(
												"shippingfeeVolumeVariable")) {
									String param = entry.getKey()
											.substring(
													0,
													entry.getKey().lastIndexOf(
															'-') + 1);
									if (!shippingfeeVolumeParams
											.contains(param)) {
										shippingfeeVolumeParams.add(param);
										NRnReturnCharges nrnReturncharge = new NRnReturnCharges();

										if (!parameters.get(param
												+ "localValue")[0].isEmpty())
											nrnReturncharge
													.setChargeAmount(Float
															.parseFloat(parameters
																	.get(param
																			+ "localValue")[0]));
										nrnReturncharge
												.setChargeName("shippingfeeVolumeVariableLocal");
										nrnReturncharge.setCriteria(parameters
												.get(param + "criteria")[0]);

										if (!parameters.get(param + "range")[0]
												.isEmpty())
											nrnReturncharge
													.setCriteriaRange(Long
															.parseLong(parameters
																	.get(param
																			+ "range")[0]));

										nrnReturncharge.setConfig(eventsBean
												.getNrnReturnConfig());
										eventsBean.getNrnReturnConfig()
												.getCharges()
												.add(nrnReturncharge);

										nrnReturncharge = new NRnReturnCharges();
										if (!parameters.get(param
												+ "zonalValue")[0].isEmpty())
											nrnReturncharge
													.setChargeAmount(Float
															.parseFloat(parameters
																	.get(param
																			+ "zonalValue")[0]));
										nrnReturncharge
												.setChargeName("shippingfeeVolumeVariableZonal");
										nrnReturncharge.setCriteria(parameters
												.get(param + "criteria")[0]);
										if (!parameters.get(param + "range")[0]
												.isEmpty())
											nrnReturncharge
													.setCriteriaRange(Long
															.parseLong(parameters
																	.get(param
																			+ "range")[0]));

										nrnReturncharge.setConfig(eventsBean
												.getNrnReturnConfig());
										eventsBean.getNrnReturnConfig()
												.getCharges()
												.add(nrnReturncharge);

										nrnReturncharge = new NRnReturnCharges();
										if (!parameters.get(param
												+ "nationalValue")[0].isEmpty())
											nrnReturncharge
													.setChargeAmount(Float
															.parseFloat(parameters
																	.get(param
																			+ "nationalValue")[0]));
										nrnReturncharge
												.setChargeName("shippingfeeVolumeVariableNational");
										nrnReturncharge.setCriteria(parameters
												.get(param + "criteria")[0]);
										if (!parameters.get(param + "range")[0]
												.isEmpty())
											nrnReturncharge
													.setCriteriaRange(Long
															.parseLong(parameters
																	.get(param
																			+ "range")[0]));

										nrnReturncharge.setConfig(eventsBean
												.getNrnReturnConfig());
										eventsBean.getNrnReturnConfig()
												.getCharges()
												.add(nrnReturncharge);

										nrnReturncharge = new NRnReturnCharges();
										if (!parameters.get(param
												+ "metroValue")[0].isEmpty())
											nrnReturncharge
													.setChargeAmount(Float
															.parseFloat(parameters
																	.get(param
																			+ "metroValue")[0]));
										nrnReturncharge
												.setChargeName("shippingfeeVolumeVariableMetro");
										nrnReturncharge.setCriteria(parameters
												.get(param + "criteria")[0]);
										if (!parameters.get(param + "range")[0]
												.isEmpty())
											nrnReturncharge
													.setCriteriaRange(Long
															.parseLong(parameters
																	.get(param
																			+ "range")[0]));

										nrnReturncharge.setConfig(eventsBean
												.getNrnReturnConfig());
										eventsBean.getNrnReturnConfig()
												.getCharges()
												.add(nrnReturncharge);
										System.out.println(eventsBean.getNrnReturnConfig().getCharges());
									}
								} else if (entry.getKey().contains(
										"shippingfeeVolumeFixed")) {
									String param = entry.getKey()
											.substring(
													0,
													entry.getKey().lastIndexOf(
															'-') + 1);
									if (!shippingfeeVolumeParams
											.contains(param)) {
										shippingfeeVolumeParams.add(param);
										NRnReturnCharges nrnReturncharge = new NRnReturnCharges();
										if (!parameters.get(param + "value")[0]
												.isEmpty())
											nrnReturncharge
													.setChargeAmount(Float
															.parseFloat(parameters
																	.get(param
																			+ "value")[0]));
										nrnReturncharge
												.setChargeName("shippingfeeVolumeFixed");
										nrnReturncharge.setCriteria(parameters
												.get(param + "criteria")[0]);
										if (!parameters.get(param + "range")[0]
												.isEmpty())
											nrnReturncharge
													.setCriteriaRange(Long
															.parseLong(parameters
																	.get(param
																			+ "range")[0]));

										nrnReturncharge.setConfig(eventsBean
												.getNrnReturnConfig());
										eventsBean.getNrnReturnConfig()
												.getCharges()
												.add(nrnReturncharge);
									}
									System.out.println(eventsBean.getNrnReturnConfig().getCharges());
								}
							} else if (entry.getKey().contains(
									"shippingfeeWeight")
									&& eventsBean.getNrnReturnConfig()
											.getShippingFeeType() != null) {

								if (eventsBean.getNrnReturnConfig()
										.getShippingFeeType()
										.equalsIgnoreCase("variable")
										&& entry.getKey().contains(
												"shippingfeeWeightVariable")) {
									String param = entry.getKey()
											.substring(
													0,
													entry.getKey().lastIndexOf(
															'-') + 1);
									if (!shippingfeeVolumeParams
											.contains(param)) {
										shippingfeeVolumeParams.add(param);
										NRnReturnCharges nrnReturncharge = new NRnReturnCharges();

										if (!parameters.get(param
												+ "localValue")[0].isEmpty()) {
											nrnReturncharge
													.setChargeAmount(Float
															.parseFloat(parameters
																	.get(param
																			+ "localValue")[0]));
										}

										nrnReturncharge
												.setChargeName("shippingfeeWeightVariableLocal");
										nrnReturncharge.setCriteria(parameters
												.get(param + "criteria")[0]);
										if (!parameters.get(param + "range")[0]
												.isEmpty())
											nrnReturncharge
													.setCriteriaRange(Long
															.parseLong(parameters
																	.get(param
																			+ "range")[0]));

										nrnReturncharge.setConfig(eventsBean
												.getNrnReturnConfig());
										eventsBean.getNrnReturnConfig()
												.getCharges()
												.add(nrnReturncharge);

										nrnReturncharge = new NRnReturnCharges();
										if (!parameters.get(param
												+ "zonalValue")[0].isEmpty()) {
											nrnReturncharge
													.setChargeAmount(Float
															.parseFloat(parameters
																	.get(param
																			+ "zonalValue")[0]));
										}

										nrnReturncharge
												.setChargeName("shippingfeeWeightVariableZonal");
										nrnReturncharge.setCriteria(parameters
												.get(param + "criteria")[0]);
										if (!parameters.get(param + "range")[0]
												.isEmpty())
											nrnReturncharge
													.setCriteriaRange(Long
															.parseLong(parameters
																	.get(param
																			+ "range")[0]));

										nrnReturncharge.setConfig(eventsBean
												.getNrnReturnConfig());
										eventsBean.getNrnReturnConfig()
												.getCharges()
												.add(nrnReturncharge);

										nrnReturncharge = new NRnReturnCharges();
										if (!parameters.get(param
												+ "nationalValue")[0].isEmpty()) {
											nrnReturncharge
													.setChargeAmount(Float
															.parseFloat(parameters
																	.get(param
																			+ "nationalValue")[0]));
										}

										nrnReturncharge
												.setChargeName("shippingfeeWeightVariableNational");
										nrnReturncharge.setCriteria(parameters
												.get(param + "criteria")[0]);
										if (!parameters.get(param + "range")[0]
												.isEmpty())
											nrnReturncharge
													.setCriteriaRange(Long
															.parseLong(parameters
																	.get(param
																			+ "range")[0]));

										nrnReturncharge.setConfig(eventsBean
												.getNrnReturnConfig());
										eventsBean.getNrnReturnConfig()
												.getCharges()
												.add(nrnReturncharge);

										nrnReturncharge = new NRnReturnCharges();
										if (!parameters.get(param
												+ "metroValue")[0].isEmpty()) {
											nrnReturncharge
													.setChargeAmount(Float
															.parseFloat(parameters
																	.get(param
																			+ "metroValue")[0]));
										}
										nrnReturncharge
												.setChargeName("shippingfeeWeightVariableMetro");
										nrnReturncharge.setCriteria(parameters
												.get(param + "criteria")[0]);

										if (!parameters.get(param + "range")[0]
												.isEmpty())
											nrnReturncharge
													.setCriteriaRange(Long
															.parseLong(parameters
																	.get(param
																			+ "range")[0]));

										nrnReturncharge.setConfig(eventsBean
												.getNrnReturnConfig());
										eventsBean.getNrnReturnConfig()
												.getCharges()
												.add(nrnReturncharge);
										System.out.println(eventsBean.getNrnReturnConfig().getCharges());
									}
								} else if (entry.getKey().contains(
										"shippingfeeWeightFixed")) {
									String param = entry.getKey()
											.substring(
													0,
													entry.getKey().lastIndexOf(
															'-') + 1);
									if (!shippingfeeWeightParams
											.contains(param)) {
										shippingfeeWeightParams.add(param);
										NRnReturnCharges nrnReturncharge = new NRnReturnCharges();
										if (!parameters.get(param + "value")[0]
												.isEmpty())
											nrnReturncharge
													.setChargeAmount(Float
															.parseFloat(parameters
																	.get(param
																			+ "value")[0]));
										nrnReturncharge
												.setChargeName("shippingfeeWeightFixed");
										nrnReturncharge.setCriteria(parameters
												.get(param + "criteria")[0]);
										if (!parameters.get(param + "range")[0]
												.isEmpty())
											nrnReturncharge
													.setCriteriaRange(Long
															.parseLong(parameters
																	.get(param
																			+ "range")[0]));

										nrnReturncharge.setConfig(eventsBean
												.getNrnReturnConfig());
										eventsBean.getNrnReturnConfig()
												.getCharges()
												.add(nrnReturncharge);
										System.out.println(eventsBean.getNrnReturnConfig().getCharges());
									}
								}
							}
						

					} else {
						log.debug(" Key : " + entry.getKey());
						String temp = entry.getKey().substring(3);
						NRnReturnCharges nrnReturncharge = new NRnReturnCharges();
						nrnReturncharge.setChargeAmount(Float.parseFloat(entry
								.getValue()[0]));
						nrnReturncharge.setChargeName(temp);
						nrnReturncharge.setConfig(eventsBean
								.getNrnReturnConfig());						
						chargeList.add(nrnReturncharge);
					}

				} else if (entry.getKey().contains("local")) {
					eventsBean.getNrnReturnConfig().setLocalList(
							Arrays.toString(entry.getValue()));
				} else if (entry.getKey().contains("zonal")) {
					eventsBean.getNrnReturnConfig().setZonalList(
							Arrays.toString(entry.getValue()));
				} else if (entry.getKey().contains("national")) {
					eventsBean.getNrnReturnConfig().setNationalList(
							Arrays.toString(entry.getValue()));
				} else if (entry.getKey().contains("metro")) {
					eventsBean.getNrnReturnConfig().setMetroList(
							Arrays.toString(entry.getValue()));
				}
			}
			}
			System.out.println(eventsBean.getNrnReturnConfig().getCharges());
			Partner partner = partnerService.getPartner(
					eventsBean.getChannelName(),
					helperClass.getSellerIdfromSession(request));
			if (partner != null) {
				eventsBean.getNrnReturnConfig().setLocalList(
						partner.getNrnReturnConfig().getLocalList());
				eventsBean.getNrnReturnConfig().setMetroList(
						partner.getNrnReturnConfig().getMetroList());
				eventsBean.getNrnReturnConfig().setZonalList(
						partner.getNrnReturnConfig().getZonalList());
				eventsBean.getNrnReturnConfig().setNationalList(
						partner.getNrnReturnConfig().getNationalList());
			}			
			
			
			for(NRnReturnCharges charge : chargeList){
				eventsBean.getNrnReturnConfig().getCharges().add(charge);
			}
			
			try {
				eventsBean.setSellerId(helperClass
						.getSellerIdfromSession(request));
				eventsBean.setCreatedDate(new Date());
				System.out.println(eventsBean.getNrnReturnConfig().getCharges());
				Events events = ConverterClass.prepareEventsModel(eventsBean);
				eventsService.addEvent(events,
						helperClass.getSellerIdfromSession(request));
			} catch (CustomException ce) {
				log.error("saveEvent exception : " + ce.toString());
				model.put("errorMessage", ce.getLocalMessage());
				model.put("errorTime", ce.getErrorTime());
				model.put("errorCode", ce.getErrorCode());
				return new ModelAndView("globalErorPage", model);
			} catch (Exception e) {
				log.debug("*** Exception In EventsDaoImpl ***");
				e.printStackTrace();
				log.error("Failed !", e);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed!", e);
		}

		log.info("$$$ saveEvents Ends : EventsController $$$");
		return new ModelAndView("redirect:/seller/eventsList.html");

	}

	@RequestMapping(value = "/seller/addEvent", method = RequestMethod.GET)
	public ModelAndView addEvent(HttpServletRequest request,
			@ModelAttribute("command") EventsBean eventsBean,
			BindingResult result) {

		log.info("$$$ addEvents Starts : EventsController $$$");
		Map<String, Float> chargeMap = new HashMap<String, Float>();
		Map<String, Float> categoryMap = new HashMap<String, Float>();
		Map<String, Object> model = new HashMap<String, Object>();
		List<String> productList = new ArrayList<String>();
		try {
			List<Product> products = productService.listProducts(helperClass
					.getSellerIdfromSession(request));
			if (products != null && products.size() != 0) {
				for (Product product : products) {
					productList.add(product.getProductSkuCode());
				}
			}
			List<Partner> partnerList = partnerService.listPartners(helperClass
					.getSellerIdfromSession(request));
			Map<String, String> partnerMap = new HashMap<String, String>();
			if (partnerList != null && !partnerList.isEmpty()) {
				for (Partner bean : partnerList) {
					partnerMap.put(bean.getPcName(), bean.getPcName());
				}
			}
			List<Category> categoryList = categoryService
					.listCategories(helperClass.getSellerIdfromSession(request));
			for (Category cat : categoryList) {
				categoryMap.put(cat.getCatName(),
						chargeMap.get(cat.getCatName()));
			}
			model.put("skus", productList);
			model.put("partnerMap", partnerMap);
			model.put("categoryMap", categoryMap);
		} catch (CustomException ce) {
			log.error("addEvent exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			log.error("Failed!", e);
			e.printStackTrace();
		}
		log.info("$$$ addEvents Ends : EventsController $$$");
		return new ModelAndView("miscellaneous/addEvent", model);
	}

	@RequestMapping(value = "/seller/addDuplicateEvent", method = RequestMethod.GET)
	public ModelAndView addDuplicateEvent(HttpServletRequest request,
			@ModelAttribute("command") EventsBean eventsBean,
			BindingResult result) {

		log.info("$$$ addDuplicateEvent Starts : EventsController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Float> chargeMap = new HashMap<String, Float>();
		Events event = null;
		Map<String, Float> categoryMap = new HashMap<String, Float>();
		List<String> productList = new ArrayList<String>();
		eventsBean
				.setEventId(Integer.parseInt(request.getParameter("eventId")));
		log.debug("***** Check : " + eventsBean.getEventId());
		try {
			if (eventsBean.getEventId() != 0) {
				event = eventsService.getEvent(eventsBean.getEventId());
				eventsBean = ConverterClass.prepareEventsBean(event);
				eventsBean.setEventId(0);
				model.put("eventsBean", eventsBean);
				log.debug("************ "
						+ (eventsBean.getNrnReturnConfig()
								.getNrCalculatorEvent()).toString());
				for (NRnReturnCharges charge : eventsBean.getNrnReturnConfig()
						.getCharges()) {
					chargeMap.put(charge.getChargeName(),
							charge.getChargeAmount());
				}
				model.put("chargeMap", chargeMap);
			}
			List<Product> products = productService.listProducts(helperClass
					.getSellerIdfromSession(request));
			if (products != null && products.size() != 0) {
				for (Product product : products) {
					productList.add(product.getProductSkuCode());
				}
			}
			List<Category> categoryList = categoryService
					.listCategories(helperClass.getSellerIdfromSession(request));
			for (Category cat : categoryList) {
				categoryMap.put(cat.getCatName(),
						chargeMap.get(cat.getCatName()));
			}
			List<Partner> partnerList = partnerService.listPartners(helperClass
					.getSellerIdfromSession(request));
			Map<String, String> partnerMap = new HashMap<String, String>();
			if (partnerList != null && !partnerList.isEmpty()) {
				for (Partner bean : partnerList) {
					partnerMap.put(bean.getPcName(), bean.getPcName());
				}
			}
			model.put("skus", productList);
			model.put("categoryMap", categoryMap);
			model.put("partnerMap", partnerMap);
		} catch (CustomException ce) {
			log.error("addDuplicateEvent exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			log.error("Failed!", e);
			e.printStackTrace();
		}
		log.info("$$$ addDuplicateEvent Ends : EventsController $$$");
		return new ModelAndView("miscellaneous/addEvent", model);
	}

	@RequestMapping(value = "/seller/editEvent", method = RequestMethod.GET)
	public ModelAndView editEvent(HttpServletRequest request,
			@ModelAttribute("command") EventsBean eventsBean,
			BindingResult result) {

		log.info("$$$ editEvent Starts : EventsController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Float> chargeMap = new HashMap<String, Float>();
		Events event = null;
		List<String> skuList=null;
		Map<String, Float> categoryMap = new HashMap<String, Float>();
		List<String> productList = new ArrayList<String>();
		eventsBean
				.setEventId(Integer.parseInt(request.getParameter("eventId")));
		log.debug("***** Check : " + eventsBean.getEventId());
		try {
			if (eventsBean.getEventId() != 0) {
				event = eventsService.getEvent(eventsBean.getEventId());
				eventsBean = ConverterClass.prepareEventsBean(event);
				if(eventsBean.getSkuList() != null)
					skuList = Arrays.asList(eventsBean.getSkuList().split(",")); 
				model.put("skuList", skuList);
				model.put("eventsBean", eventsBean);
				log.debug("************ "
						+ (eventsBean.getNrnReturnConfig()
								.getNrCalculatorEvent()).toString());
				for (NRnReturnCharges charge : eventsBean.getNrnReturnConfig()
						.getCharges()) {

					if (charge.getChargeName().contains("fixedfee")
							&& charge.getCriteria() != null
							&& !"".equals(charge.getCriteria())) {

						ChargesBean chargeBean = new ChargesBean();
						chargeBean.setChargeType("fixedfee");
						chargeBean.setCriteria(charge.getCriteria());
						chargeBean.setRange(charge.getCriteriaRange());
						chargeBean.setValue(charge.getChargeAmount());
						eventsBean.getFixedfeeList().add(chargeBean);

					} else if (charge.getChargeName().contains(
							"shippingfeeVolume")
							&& charge.getCriteria() != null
							&& !"".equals(charge.getCriteria())) {

						if (eventsBean.getNrnReturnConfig()
								.getShippingFeeType()
								.equalsIgnoreCase("variable")
								&& charge.getChargeName().contains(
										"shippingfeeVolumeVariable")) {

							ChargesBean chargeBean = eventsBean.getChargesBean(
									"shippingfeeVolumeVariable",
									charge.getCriteria(),
									charge.getCriteriaRange());
							if (chargeBean == null) {
								chargeBean = new ChargesBean();
								chargeBean
										.setChargeType("shippingfeeVolumeVariable");
								chargeBean.setCriteria(charge.getCriteria());
								chargeBean.setRange(charge.getCriteriaRange());
								eventsBean.getShippingfeeVolumeVariableList()
										.add(chargeBean);
							}

							if (charge.getChargeName().contains("Local")) {
								chargeBean.setLocalValue(charge
										.getChargeAmount());
							} else if (charge.getChargeName().contains("Zonal")) {
								chargeBean.setZonalValue(charge
										.getChargeAmount());
							} else if (charge.getChargeName().contains(
									"National")) {
								chargeBean.setNationalValue(charge
										.getChargeAmount());
							} else if (charge.getChargeName().contains("Metro")) {
								chargeBean.setMetroValue(charge
										.getChargeAmount());
							}

						} else if (charge.getChargeName().contains(
								"shippingfeeVolumeFixed")) {
							ChargesBean chargeBean = new ChargesBean();
							chargeBean.setChargeType("shippingfeeVolume");
							chargeBean.setCriteria(charge.getCriteria());
							chargeBean.setRange(charge.getCriteriaRange());
							chargeBean.setValue(charge.getChargeAmount());
							eventsBean.getShippingfeeVolumeFixedList().add(
									chargeBean);
						}

					} else if (charge.getChargeName().contains(
							"shippingfeeWeight")
							&& charge.getCriteria() != null
							&& !"".equals(charge.getCriteria())) {

						if (eventsBean.getNrnReturnConfig()
								.getShippingFeeType()
								.equalsIgnoreCase("variable")
								&& charge.getChargeName().contains(
										"shippingfeeWeightVariable")) {

							ChargesBean chargeBean = eventsBean.getChargesBean(
									"shippingfeeWeightVariable",
									charge.getCriteria(),
									charge.getCriteriaRange());
							if (chargeBean == null) {
								chargeBean = new ChargesBean();
								chargeBean
										.setChargeType("shippingfeeWeightVariable");
								chargeBean.setCriteria(charge.getCriteria());
								chargeBean.setRange(charge.getCriteriaRange());
								eventsBean.getShippingfeeWeightVariableList()
										.add(chargeBean);
							}

							if (charge.getChargeName().contains("Local")) {
								chargeBean.setLocalValue(charge
										.getChargeAmount());
							} else if (charge.getChargeName().contains("Zonal")) {
								chargeBean.setZonalValue(charge
										.getChargeAmount());
							} else if (charge.getChargeName().contains(
									"National")) {
								chargeBean.setNationalValue(charge
										.getChargeAmount());
							} else if (charge.getChargeName().contains("Metro")) {
								chargeBean.setMetroValue(charge
										.getChargeAmount());
							}

						} else if (charge.getChargeName().contains(
								"shippingfeeWeightFixed")) {
							ChargesBean chargeBean = new ChargesBean();
							chargeBean.setChargeType("shippingfeeWeight");
							chargeBean.setCriteria(charge.getCriteria());
							chargeBean.setRange(charge.getCriteriaRange());
							chargeBean.setValue(charge.getChargeAmount());
							eventsBean.getShippingfeeWeightFixedList().add(
									chargeBean);
						}

					} else {
						chargeMap.put(charge.getChargeName(),
								charge.getChargeAmount());
					}
				}
				if (eventsBean.getFixedfeeList() != null)
					Collections.sort(eventsBean.getFixedfeeList(),
							new SortByCriteriaRange());
				if (eventsBean.getShippingfeeVolumeVariableList() != null)
					Collections.sort(
							eventsBean.getShippingfeeVolumeVariableList(),
							new SortByCriteria());
				if (eventsBean.getShippingfeeWeightVariableList() != null)
					Collections.sort(
							eventsBean.getShippingfeeWeightVariableList(),
							new SortByCriteria());
				if (eventsBean.getShippingfeeVolumeFixedList() != null)
					Collections.sort(
							eventsBean.getShippingfeeVolumeFixedList(),
							new SortByCriteria());
				if (eventsBean.getShippingfeeWeightFixedList() != null)
					Collections.sort(
							eventsBean.getShippingfeeWeightFixedList(),
							new SortByCriteria());

				model.put("chargeMap", chargeMap);
			}
			
			List<Product> products = productService.listProducts(helperClass
					.getSellerIdfromSession(request));
			if (products != null && products.size() != 0) {
				for (Product product : products) {
					productList.add(product.getProductSkuCode());
				}
			}
			
			List<Category> categoryList = categoryService
					.listCategories(helperClass.getSellerIdfromSession(request));
			for (Category cat : categoryList) {
				categoryMap.put(cat.getCatName(),
						chargeMap.get(cat.getCatName()));
			}
			List<Partner> partnerList = partnerService.listPartners(helperClass
					.getSellerIdfromSession(request));
			Map<String, String> partnerMap = new HashMap<String, String>();
			if (partnerList != null && !partnerList.isEmpty()) {
				for (Partner bean : partnerList) {
					partnerMap.put(bean.getPcName(), bean.getPcName());
				}
			}	
			model.put("skus", productList);
			model.put("event", eventsBean);
			model.put("categoryMap", categoryMap);
			model.put("partnerMap", partnerMap);
		} catch (CustomException ce) {
			log.error("addDuplicateEvent exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			log.error("Failed!", e);
			e.printStackTrace();
		}
		log.info("$$$ editEvent Ends : EventsController $$$");
		return new ModelAndView("miscellaneous/addEvent", model);
	}

	@RequestMapping(value = "/seller/eventsList", method = RequestMethod.GET)
	public ModelAndView eventsList(HttpServletRequest request,
			@ModelAttribute("command") EventsBean eventsBean,
			BindingResult result) {

		log.info("$$$ eventsList Starts : EventsController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("eventsList", ConverterClass
					.prepareListOfEventsBean(eventsService
							.listEvents(helperClass
									.getSellerIdfromSession(request))));

		} catch (CustomException ce) {
			log.error("eventList exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			log.error("Failed!", e);
			e.printStackTrace();
		}
		log.info("$$$ eventsList Ends : EventsController $$$");
		return new ModelAndView("miscellaneous/eventsList", model);
	}
	
	@RequestMapping(value = "/seller/viewEvent", method = RequestMethod.GET)
	public ModelAndView viewEvent(HttpServletRequest request) {

		log.info("$$$ viewEvent Starts : EventsController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		int sellerID = 0;
		EventsBean eventBean = null;
		List<String> skuList=null;
		Map<String, Float> chargeMap = new HashMap<String, Float>();
		Map<String, Float> categoryMap = new HashMap<String, Float>();		
		List<Category> categoryObjects = null;
		try {
			int id = Integer.parseInt(request.getParameter("eventId"));
			sellerID = helperClass.getSellerIdfromSession(request);
			eventBean = ConverterClass.prepareEventsBean(eventsService.getEvent(id));
			model.put("event", eventBean);
			if(eventBean.getSkuList() != null)
				skuList = Arrays.asList(eventBean.getSkuList().split(",")); 
			model.put("skuList", skuList);					
			for (NRnReturnCharges charge : eventBean.getNrnReturnConfig()
					.getCharges()) {

				if (charge.getChargeName().contains("fixedfee")
						&& charge.getCriteria() != null
						&& !"".equals(charge.getCriteria())) {

					ChargesBean chargeBean = new ChargesBean();
					chargeBean.setChargeType("fixedfee");
					chargeBean.setCriteria(charge.getCriteria());
					chargeBean.setRange(charge.getCriteriaRange());
					chargeBean.setValue(charge.getChargeAmount());
					eventBean.getFixedfeeList().add(chargeBean);

				} else if (charge.getChargeName().contains(
						"shippingfeeVolume")
						&& charge.getCriteria() != null
						&& !"".equals(charge.getCriteria())) {

					if (eventBean.getNrnReturnConfig()
							.getShippingFeeType()
							.equalsIgnoreCase("variable")
							&& charge.getChargeName().contains(
									"shippingfeeVolumeVariable")) {

						ChargesBean chargeBean = eventBean.getChargesBean(
								"shippingfeeVolumeVariable",
								charge.getCriteria(),
								charge.getCriteriaRange());
						if (chargeBean == null) {
							chargeBean = new ChargesBean();
							chargeBean
									.setChargeType("shippingfeeVolumeVariable");
							chargeBean.setCriteria(charge.getCriteria());
							chargeBean.setRange(charge.getCriteriaRange());
							eventBean.getShippingfeeVolumeVariableList()
									.add(chargeBean);
						}

						if (charge.getChargeName().contains("Local")) {
							chargeBean.setLocalValue(charge
									.getChargeAmount());
						} else if (charge.getChargeName().contains("Zonal")) {
							chargeBean.setZonalValue(charge
									.getChargeAmount());
						} else if (charge.getChargeName().contains(
								"National")) {
							chargeBean.setNationalValue(charge
									.getChargeAmount());
						} else if (charge.getChargeName().contains("Metro")) {
							chargeBean.setMetroValue(charge
									.getChargeAmount());
						}

					} else if (charge.getChargeName().contains(
							"shippingfeeVolumeFixed")) {
						ChargesBean chargeBean = new ChargesBean();
						chargeBean.setChargeType("shippingfeeVolume");
						chargeBean.setCriteria(charge.getCriteria());
						chargeBean.setRange(charge.getCriteriaRange());
						chargeBean.setValue(charge.getChargeAmount());
						eventBean.getShippingfeeVolumeFixedList().add(
								chargeBean);
					}

				} else if (charge.getChargeName().contains(
						"shippingfeeWeight")
						&& charge.getCriteria() != null
						&& !"".equals(charge.getCriteria())) {

					if (eventBean.getNrnReturnConfig()
							.getShippingFeeType()
							.equalsIgnoreCase("variable")
							&& charge.getChargeName().contains(
									"shippingfeeWeightVariable")) {

						ChargesBean chargeBean = eventBean.getChargesBean(
								"shippingfeeWeightVariable",
								charge.getCriteria(),
								charge.getCriteriaRange());
						if (chargeBean == null) {
							chargeBean = new ChargesBean();
							chargeBean
									.setChargeType("shippingfeeWeightVariable");
							chargeBean.setCriteria(charge.getCriteria());
							chargeBean.setRange(charge.getCriteriaRange());
							eventBean.getShippingfeeWeightVariableList()
									.add(chargeBean);
						}

						if (charge.getChargeName().contains("Local")) {
							chargeBean.setLocalValue(charge
									.getChargeAmount());
						} else if (charge.getChargeName().contains("Zonal")) {
							chargeBean.setZonalValue(charge
									.getChargeAmount());
						} else if (charge.getChargeName().contains(
								"National")) {
							chargeBean.setNationalValue(charge
									.getChargeAmount());
						} else if (charge.getChargeName().contains("Metro")) {
							chargeBean.setMetroValue(charge
									.getChargeAmount());
						}

					} else if (charge.getChargeName().contains(
							"shippingfeeWeightFixed")) {
						ChargesBean chargeBean = new ChargesBean();
						chargeBean.setChargeType("shippingfeeWeight");
						chargeBean.setCriteria(charge.getCriteria());
						chargeBean.setRange(charge.getCriteriaRange());
						chargeBean.setValue(charge.getChargeAmount());
						eventBean.getShippingfeeWeightFixedList().add(
								chargeBean);
					}

				} else {
					chargeMap.put(charge.getChargeName(),
							charge.getChargeAmount());
				}
			}
			if (eventBean.getFixedfeeList() != null)
				Collections.sort(eventBean.getFixedfeeList(),
						new SortByCriteriaRange());
			if (eventBean.getShippingfeeVolumeVariableList() != null)
				Collections.sort(
						eventBean.getShippingfeeVolumeVariableList(),
						new SortByCriteria());
			if (eventBean.getShippingfeeWeightVariableList() != null)
				Collections.sort(
						eventBean.getShippingfeeWeightVariableList(),
						new SortByCriteria());
			if (eventBean.getShippingfeeVolumeFixedList() != null)
				Collections.sort(
						eventBean.getShippingfeeVolumeFixedList(),
						new SortByCriteria());
			if (eventBean.getShippingfeeWeightFixedList() != null)
				Collections.sort(
						eventBean.getShippingfeeWeightFixedList(),
						new SortByCriteria());

			model.put("chargeMap", chargeMap);	
			categoryObjects = categoryService.listCategories(helperClass
					.getSellerIdfromSession(request));
			if (categoryObjects != null && categoryObjects.size() > 0) {
				for (Category cat : categoryObjects) {
					categoryMap.put(cat.getCatName(),
							chargeMap.get(cat.getCatName()));
				}
			}
			model.put("categoryMap", categoryMap);
			
		} catch (CustomException ce) {
			log.error("eventList exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			log.error("Failed! by Seller ID : "+sellerID, e);
			e.printStackTrace();
		}
		log.info("$$$ viewEvent Ends : EventsController $$$");
		return new ModelAndView("miscellaneous/viewEvent", model);
	}

	@RequestMapping(value = "/seller/eventAction", method = RequestMethod.GET)
	public ModelAndView PlayPause(HttpServletRequest request,
			@ModelAttribute("command") EventsBean eventsBean,
			BindingResult result) {

		log.info("$$$ eventsList Starts : EventsController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		int id = Integer.parseInt(request.getParameter("id"));
		try {
			eventsService.changeStatus(id,
					helperClass.getSellerIdfromSession(request));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception in PlayPause : EventsController ", e);
			model.put("errorMessage", "Invalid Operation");
			model.put("errorTime", new Date());
			model.put("errorCode", 500);
			return new ModelAndView("globalErorPage", model);
		}
		log.info("$$$ eventsList Ends : EventsController $$$");
		return new ModelAndView("redirect:/seller/eventsList.html");
	}

	@RequestMapping(value = "/seller/checkEvent", method = RequestMethod.GET)
	public @ResponseBody String checkEvent(HttpServletRequest request,
			@ModelAttribute("command") EventsBean eventsBean,
			BindingResult result) {

		log.info("$$$ checkEvent Starts : EventsController $$$");
		String eventName = request.getParameter("name");
		try {
			if (eventName != null && eventName.length() != 0) {
				Events event = eventsService.getEvent(eventName,
						helperClass.getSellerIdfromSession(request));
				if (event != null)
					return "false";
				else
					return "true";
			}
		} catch (Exception e) {
			log.error("Failed! ", e);
			e.printStackTrace();
			return "false";
		}
		log.info("$$$ checkEvent Ends : EventsController $$$");
		return "false";
	}

	@RequestMapping(value = "/seller/checkDates", method = RequestMethod.GET)
	public @ResponseBody String checkDates(HttpServletRequest request,
			@ModelAttribute("command") EventsBean eventsBean,
			BindingResult result) {

		log.info("$$$ checkDates Starts : EventsController $$$");
		boolean event;
		try {
			if (request.getParameter("start") != ""
					&& request.getParameter("end") != "") {
				Date startDate = new SimpleDateFormat("MM/dd/yyyy")
						.parse(request.getParameter("start"));
				Date endDate = new SimpleDateFormat("MM/dd/yyyy").parse(request
						.getParameter("end"));
				String channelName = request.getParameter("channel");
				if (startDate != null && endDate != null) {
					event = eventsService.isDatesAllowForEvent(startDate,
							endDate, channelName,
							helperClass.getSellerIdfromSession(request));
					if (event == false) {
						log.info("$$$ checkDates Ends : EventsController $$$");
						return "false";
					} else {
						log.info("$$$ checkDates Ends : EventsController $$$");
						return "true";
					}
				}
			}
		} catch (Exception e) {
			log.error("Failed! ", e);
			e.printStackTrace();
			return "false";
		}
		log.info("$$$ checkDates Ends : EventsController $$$");
		return "false";
	}

}
