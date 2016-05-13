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
import com.o2r.helper.HelperClass;
import com.o2r.model.Category;
import com.o2r.model.NRnReturnCharges;
import com.o2r.model.Partner;
import com.o2r.service.CategoryService;
import com.o2r.service.PartnerService;

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
	
	List<NRnReturnCharges> chargeList=new ArrayList<NRnReturnCharges>();

	@RequestMapping(value = "/seller/savePartner", method = RequestMethod.POST)
	public ModelAndView savePartner(HttpServletRequest request,
			@ModelAttribute("command") PartnerBean partnerBean,
			BindingResult result,
			@RequestParam(value = "image", required = false) MultipartFile image) {

		log.info("*** savePartner start ***");
		if(partnerBean.getPcId() != 0){		
			System.out.println("****************************** ConfigId : "+partnerBean.getNrnReturnConfig().getConfigId());
			System.out.println("****************************** Partner ID : "+partnerBean.getPcId());
					
		}
		
		
		System.out.println(" Nr calculayor value from bean : "+partnerBean.getNrnReturnConfig().isNrCalculator());
		Map<String, String[]> parameters = request.getParameterMap();
		for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
			if (entry.getKey().contains("nr-")) 
			{
				System.out.println(" Key with nr: "+entry.getKey()+" Values is : "+entry.getValue()[0]);
			}
		}
				for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
		if (entry.getKey() != null && !entry.getKey().isEmpty())
				System.out.println(" Print :entry.getKey()  " +entry.getKey()+" value: "+ entry.getValue()[0]);

			if (entry.getValue()[0] != null && !entry.getValue()[0].isEmpty()) {
				if (entry.getKey().contains("nr-")) {
					System.out.println(" Key : "+entry.getKey());
					String temp = entry.getKey().substring(3);
					NRnReturnCharges nrnReturncharge = new NRnReturnCharges();
					nrnReturncharge.setChargeAmount(Float.parseFloat(entry
							.getValue()[0]));
					nrnReturncharge.setChargeName(temp);
					nrnReturncharge.setConfig(partnerBean.getNrnReturnConfig());
					partnerBean.getNrnReturnConfig().getCharges().add(nrnReturncharge);
					//chargeList.add(nrnReturncharge);
				} else if (entry.getKey().contains("local")) {
					partnerBean.getNrnReturnConfig().setLocalList(Arrays.toString(entry.getValue()));
				} else if (entry.getKey().contains("zonal")) {
					partnerBean.getNrnReturnConfig().setZonalList(Arrays.toString(entry.getValue()));
				} else if (entry.getKey().contains("national")) {
					partnerBean.getNrnReturnConfig().setNationalList(Arrays.toString(entry.getValue()));
				} else if (entry.getKey().contains("metro")) {
					partnerBean.getNrnReturnConfig().setMetroList(Arrays.toString(entry.getValue()));
				}
			}
		}

		/*
		 * System.out.println("Inside partner Ssave ");
		 * System.out.println(" partnerBean : **** "+partnerBean);
		 * System.out.println
		 * ("inside controller noofdaysfromshipped date : "+partnerBean
		 * .getNoofdaysfromshippeddate());
		 * System.out.println(" Seller id :"+partnerBean.getPcId());
		 */
		//partnerBean.getNrnReturnConfig().setCharges(chargeList);
		//configobj.setCharges(chargeList);
		//partnerBean.setNrnReturnConfig(configobj);
		if (!partnerBean.isIsshippeddatecalc()) {
			partnerBean.setNoofdaysfromshippeddate(partnerBean
					.getNoofdaysfromdeliverydate());
		}
		// int sellerId=HelperClass.getSellerIdfromSession(request);
		if (image.getSize() != 0) {
			System.out.println(" Not getting any image");
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
				partnerBean.setPcLogoUrl(props.getProperty("partnerimage.view")
						+ HelperClass.getSellerIdfromSession(request)
						+ partnerBean.getPcName() + ".jpg");
				saveImage(HelperClass.getSellerIdfromSession(request)
						+ partnerBean.getPcName() + ".jpg", image);
			} else {
				partnerBean.setPcLogoUrl(props.getProperty("partnerimage.view")
						+ partnerBean.getPcName() + ".jpg");
			}
		} catch (Exception e) {
			result.reject(e.getMessage());
			return new ModelAndView("redirect:/seller/partners.html");
		}
		try {
			Partner partner = ConverterClass.preparePartnerModel(partnerBean);
			partnerService.addPartner(partner,HelperClass.getSellerIdfromSession(request));
		} catch (Exception e) {
			log.error(e);
		}
		
		log.info("*** savePartner exit ***");
		return new ModelAndView("redirect:/seller/partners.html");
	}
	

	@RequestMapping(value = "/seller/listPartners", method = RequestMethod.GET)
	public ModelAndView listAllPartners(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<PartnerBean> addedlist = null;
		try {
			addedlist = ConverterClass.prepareListofPartnerBean(partnerService.listPartners(HelperClass.getSellerIdfromSession(request)));
		} catch (Exception e) {
			log.error(e.getCause());
		}
		model.put("partners", addedlist);
		return new ModelAndView("initialsetup/partnerDetail", model);
	}

	@RequestMapping(value = "/seller/partners", method = RequestMethod.GET)
	public ModelAndView listPartners(HttpServletRequest request) {

		log.info("*** listPartners start ***");
		Map<String, Object> model = new HashMap<String, Object>();
		List<PartnerBean> toAddPartner = new ArrayList<PartnerBean>();
		List<PartnerBean> addedlist = null;
		try {
			addedlist = ConverterClass.prepareListofPartnerBean(partnerService
					.listPartners(HelperClass.getSellerIdfromSession(request)));

			props = PropertiesLoaderUtils.loadProperties(resource);

			/*
			 * System.out.println("dialect in order controller : " +
			 * props.getProperty("partnerimage.view"));
			 */

			for (String partner : partnerList) {
				if (partnerService.getPartner(partner,
						HelperClass.getSellerIdfromSession(request)) == null) {
					String pcUrl = props.getProperty("partnerimage.view")
							+ partner + ".jpg";
					System.out.println(" Pc logourl set to partner baen "
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
			log.error(e);
		}
		model.put("partners", addedlist);
		model.put("partnertoadd", toAddPartner);
		System.out.println(" toaddpartner size  :" + toAddPartner.size());
		log.info("*** listPartners exit ***");
		return new ModelAndView("initialsetup/partnerInfo", model);
	}

	@RequestMapping(value = "/seller/addPartner", method = RequestMethod.GET)
	public ModelAndView addPartner(HttpServletRequest request,
			@ModelAttribute("command") PartnerBean partnerBean,
			BindingResult result) {

		log.info("*** addPartner start ***");
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> datemap = new LinkedHashMap<String, Object>();
		List<String> categoryList = new ArrayList<String>();
		List<Category> categoryObjects = null;
		datemap.put("true", "Select payment from");
		datemap.put("true", "Shipping Date");
		datemap.put("false", "Delivery Date");
		PartnerBean partner = new PartnerBean();
		try {
			categoryObjects = categoryService.listCategories(HelperClass
					.getSellerIdfromSession(request));
			if(categoryObjects!=null)
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
			log.error(e);
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
							.listPartners(HelperClass
									.getSellerIdfromSession(request))));
		} catch (Exception e) {
			log.error(e);
		}
		log.info("*** addPartner exit ***");
		return new ModelAndView("initialsetup/addPartner", model);
	}
	
	/*My Code Ranjan*/
	
	@RequestMapping(value = "/seller/saveJabong", method = RequestMethod.POST)
	public ModelAndView saveJabong(HttpServletRequest request,
			@ModelAttribute("command") PartnerBean partnerBean,
			BindingResult result,
			@RequestParam(value = "image", required = false) MultipartFile image) {

		log.info("*** saveJabong start ***");
		System.out.println(" Nr calculayor value from bean : "+partnerBean.getNrnReturnConfig().isNrCalculator());
		Map<String, String[]> parameters = request.getParameterMap();
				for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
			System.out.println(" Request Param: " + entry.getKey()+ "  Values  : " + entry.getValue().length);

			if (entry.getKey() != null && !entry.getKey().isEmpty())
				System.out.println(" Print : " + entry.getValue()[0]);

			if (entry.getValue()[0] != null && !entry.getValue()[0].isEmpty()) {
				if (entry.getKey().contains("nr-")) {
					String temp = entry.getKey().substring(3);
					NRnReturnCharges nrnReturncharge = new NRnReturnCharges();
					nrnReturncharge.setChargeAmount(Float.parseFloat(entry.getValue()[0]));
					nrnReturncharge.setChargeName(temp);
					nrnReturncharge.setConfig(partnerBean.getNrnReturnConfig());
					partnerBean.getNrnReturnConfig().getCharges().add(nrnReturncharge);
				}
			}
		}

		//partnerBean.getNrnReturnConfig().setCharges(chargeList);
		
		if (!partnerBean.isIsshippeddatecalc()) {
			partnerBean.setNoofdaysfromshippeddate(partnerBean
					.getNoofdaysfromdeliverydate());
		}
		
		/*if (image != null) {
			System.out.println(" Not getting any image");
			if (!image.isEmpty()) {
				try {
					validateImage(image);

				} catch (RuntimeException re) {
					result.reject(re.getMessage());
				}
			}
		}*/
		/*try {
			props = PropertiesLoaderUtils.loadProperties(resource);

			if (!partnerList.contains(partnerBean.getPcName())) {
				partnerBean.setPcLogoUrl(props.getProperty("partnerimage.view")
						+ HelperClass.getSellerIdfromSession(request)
						+ partnerBean.getPcName() + ".jpg");
				saveImage(HelperClass.getSellerIdfromSession(request)
						+ partnerBean.getPcName() + ".jpg", image);
			} else {
				partnerBean.setPcLogoUrl(props.getProperty("partnerimage.view")
						+ partnerBean.getPcName() + ".jpg");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.reject(e.getMessage());
			return new ModelAndView("redirect:/seller/partners.html");
		}*/
		try {
			partnerBean.setPcName("Jabong");
			Partner partner = ConverterClass.preparePartnerModel(partnerBean);
			partnerService.addPartner(partner,HelperClass.getSellerIdfromSession(request));
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}

		log.info("*** saveJabong exit ***");
		return new ModelAndView("redirect:/seller/partners.html");
	}
	
	@RequestMapping(value = "/seller/saveMyntra", method = RequestMethod.POST)
	public ModelAndView saveMyntra(HttpServletRequest request,
			@ModelAttribute("command") PartnerBean partnerBean,
			BindingResult result,
			@RequestParam(value = "image", required = false) MultipartFile image) {

		log.info("*** saveMyntra start ***");
		System.out.println(" Nr calculayor value from bean : "+partnerBean.getNrnReturnConfig().isNrCalculator());
		Map<String, String[]> parameters = request.getParameterMap();
				for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
			System.out.println(" Request Param: " + entry.getKey()+ "  Values  : " + entry.getValue().length);

			if (entry.getKey() != null && !entry.getKey().isEmpty())
				System.out.println(" Print : " + entry.getValue()[0]);

			if (entry.getValue()[0] != null && !entry.getValue()[0].isEmpty()) {
				if (entry.getKey().contains("nr-")) {
					String temp = entry.getKey().substring(3);
					NRnReturnCharges nrnReturncharge = new NRnReturnCharges();
					nrnReturncharge.setChargeAmount(Float.parseFloat(entry.getValue()[0]));
					nrnReturncharge.setChargeName(temp);
					nrnReturncharge.setConfig(partnerBean.getNrnReturnConfig());
					partnerBean.getNrnReturnConfig().getCharges().add(nrnReturncharge);
				}
			}
		}

		//partnerBean.getNrnReturnConfig().setCharges(chargeList);
		
		if (!partnerBean.isIsshippeddatecalc()) {
			partnerBean.setNoofdaysfromshippeddate(partnerBean
					.getNoofdaysfromdeliverydate());
		}
		
		/*if (image != null) {
			System.out.println(" Not getting any image");
			if (!image.isEmpty()) {
				try {
					validateImage(image);

				} catch (RuntimeException re) {
					result.reject(re.getMessage());
				}
			}
		}*/
		/*try {
			props = PropertiesLoaderUtils.loadProperties(resource);

			if (!partnerList.contains(partnerBean.getPcName())) {
				partnerBean.setPcLogoUrl(props.getProperty("partnerimage.view")
						+ HelperClass.getSellerIdfromSession(request)
						+ partnerBean.getPcName() + ".jpg");
				saveImage(HelperClass.getSellerIdfromSession(request)
						+ partnerBean.getPcName() + ".jpg", image);
			} else {
				partnerBean.setPcLogoUrl(props.getProperty("partnerimage.view")
						+ partnerBean.getPcName() + ".jpg");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.reject(e.getMessage());
			return new ModelAndView("redirect:/seller/partners.html");
		}*/
		try {
			partnerBean.setPcName("Myntra");
			Partner partner = ConverterClass.preparePartnerModel(partnerBean);
			partnerService.addPartner(partner,HelperClass.getSellerIdfromSession(request));
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}

		log.info("*** saveMyntra exit ***");
		return new ModelAndView("redirect:/seller/partners.html");
	}
	
	@RequestMapping(value = "/seller/addJabong", method = RequestMethod.GET)
	public ModelAndView addJabong(HttpServletRequest request,
			@ModelAttribute("command") PartnerBean partnerBean,
			BindingResult result) {

		log.info("*** addJabong start ***");
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> datemap = new LinkedHashMap<String, Object>();
		List<String> categoryList = new ArrayList<String>();
		List<Category> categoryObjects = null;
		datemap.put("true", "Select payment from");
		datemap.put("true", "Shipping Date");
		datemap.put("false", "Delivery Date");
		PartnerBean partner = new PartnerBean();
		try {
			categoryObjects = categoryService.listCategories(HelperClass.getSellerIdfromSession(request));
			for (Category cat : categoryObjects) {
				categoryList.add(cat.getCatName());
			}
		} /*catch (CustomException ce) {
			log.error("addJabong exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		}*/ catch (Exception e) {
			log.error(e);
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
			log.error(e);
		}
		log.info("*** addJabong exit ***");
		return new ModelAndView("initialsetup/addJabong", model);
	}
	
	
	@RequestMapping(value = "/seller/addMyntra", method = RequestMethod.GET)
	public ModelAndView addMyntra(HttpServletRequest request,
			@ModelAttribute("command") PartnerBean partnerBean,
			BindingResult result) {

		log.info("*** addMyntra start ***");
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> datemap = new LinkedHashMap<String, Object>();
		List<String> categoryList = new ArrayList<String>();
		List<Category> categoryObjects = null;
		datemap.put("true", "Select payment from");
		datemap.put("true", "Shipping Date");
		datemap.put("false", "Delivery Date");
		PartnerBean partner = new PartnerBean();
		try {
			categoryObjects = categoryService.listCategories(HelperClass
					.getSellerIdfromSession(request));
			for (Category cat : categoryObjects) {
				categoryList.add(cat.getCatName());
			}
		} /*catch (CustomException ce) {
			log.error("addPartner exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		}*/ catch (Exception e) {
			log.error(e);
		}
		//String partnerName = request.getParameter("partnerName");
		try {
			/*if (partnerName != null) {
				partner.setPcName(partnerName);
			}*/
			model.put("partner", partner);
			model.put("categoryList", categoryList);
			model.put("datemap", datemap);
			model.put("partners", ConverterClass
					.prepareListofPartnerBean(partnerService
							.listPartners(HelperClass
									.getSellerIdfromSession(request))));
		} catch (Exception e) {
			log.error(e);
		}
		log.info("*** addMyntra exit ***");
		return new ModelAndView("initialsetup/addMyntra", model);
	}
	
	
	/*My Code Ranjan Ends*/
	
	
	@RequestMapping(value = "/seller/addPartnertest", method = RequestMethod.GET)
	public ModelAndView addPartnertest(HttpServletRequest request,
			@ModelAttribute("command") PartnerBean partnerBean,
			BindingResult result) {
		log.info("*** addPartnertest start ***");
		Map<String, Object> model = new HashMap<String, Object>();
		PartnerBean partner = new PartnerBean();
		String id = request.getParameter("pid");
		System.out.println(" Inside partner controller :" + id);

		try {
			if (id != null) {

				if (id.equals("1"))
					partner.setPcName("Amazon");
			}
			model.put("partner", partner);
			model.put("partners", ConverterClass
					.prepareListofPartnerBean(partnerService
							.listPartners(HelperClass
									.getSellerIdfromSession(request))));
		} catch (Exception e) {
			log.error(e);
		}
		log.info("*** addPartertest exit ***");
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
		Map<String, Object> model = new HashMap<String, Object>();

		/*
		 * System.out.println(" pcid in controller " + partnerBean.getPcId());
		 * System.out.println(" pcname in controller " +
		 * partnerBean.getPcName());
		 */
		try {
			partnerService.deletePartner(
					ConverterClass.preparePartnerModel(partnerBean),
					HelperClass.getSellerIdfromSession(request));
			model.put("partner", null);
			model.put("partners", ConverterClass
					.prepareListofPartnerBean(partnerService
							.listPartners(HelperClass
									.getSellerIdfromSession(request))));
		} catch (CustomException ce) {

		} catch (Exception e) {
			log.error(e);
		}
		return new ModelAndView("addPartner", model);
	}

	@RequestMapping(value = "/seller/editPartner", method = RequestMethod.GET)
	public ModelAndView editPartner(HttpServletRequest request,
			@ModelAttribute("command") PartnerBean partnerBean,
			BindingResult result) {
		log.info("*** editPartner start ***");
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> datemap = new HashMap<String, Object>();
		PartnerBean pbean=null;
		Map<String,Float> chargeMap=new HashMap<String, Float>();
		Map<String,Float> categoryMap=new HashMap<String, Float>();
		List<Category> categoryObjects = null;
		try {
			datemap.put("true", "Select payment from");
			datemap.put("true", "Shipping Date");
			datemap.put("false", "Delivery Date");
			model.put("datemap", datemap);
			pbean=ConverterClass
			.preparePartnerBean(partnerService.getPartner(partnerBean
					.getPcId()));
			System.out.println("************** Inside edit partner : config ID :"+pbean.getNrnReturnConfig().getConfigId());
			for(NRnReturnCharges charge:pbean.getNrnReturnConfig().getCharges())
			{
				chargeMap.put(charge.getChargeName(), charge.getChargeAmount());
			}
			
			categoryObjects = categoryService.listCategories(HelperClass
					.getSellerIdfromSession(request));
			for (Category cat : categoryObjects) {
				categoryMap.put(cat.getCatName(), chargeMap.get(cat.getCatName()));
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("editPartner exception : " + e.getLocalizedMessage());
			model.put("errorMessage", e.getMessage());
			
		}
		log.info("*** editPartner exit ***");
		return new ModelAndView("initialsetup/addPartner", model);
	}

	private void validateImage(MultipartFile image) {
		if (!image.getContentType().equals("image/jpeg")) {
			throw new RuntimeException("Only JPG images are accepted");
		}
	}

	private void saveImage(String filename, MultipartFile image)
			throws RuntimeException, IOException {
		log.info("*** saveImage start ***");
		try {
			String catalinabase = System.getProperty("catalina.base");

			try {
				props = PropertiesLoaderUtils.loadProperties(resource);
			} catch (IOException e) {
				log.error(e.getCause());

			}
			System.out.println("dialect in order controller : "
					+ props.getProperty("partnerimage.path"));
			File file = new File(catalinabase
					+ props.getProperty("partnerimage.path") + filename);
			System.out.println(" context.getRealPath(/) "
					+ context.getRealPath("/"));
			System.out.println(" Path to save file : " + file.toString());
			FileUtils.writeByteArrayToFile(file, image.getBytes());
			System.out
					.println("Go to the location:  "
							+ file.toString()
							+ " on your computer and verify that the image has been stored.");
		} catch (IOException e) {

			log.error(e.getCause());
			/*
			 * System.out.println(" Error in saving image : "+
			 * e.getLocalizedMessage()); e.printStackTrace(); throw e;
			 */
		}
		log.info("*** saveImage exit ***");
	}

	@RequestMapping(value = "/seller/ajaxPartnerCheck.html", method = RequestMethod.GET)
	public @ResponseBody String getCheckPartner(HttpServletRequest request,
			@ModelAttribute("command") PartnerBean partnerBean,
			BindingResult result, Model model) {
		System.out.println(request.getParameter("partner"));
		try {
			Partner parner = partnerService.getPartner(
					request.getParameter("partner"),
					HelperClass.getSellerIdfromSession(request));
			if (parner != null) {
				return "false";
			} else {
				return "true";
			}
		} catch (Exception e) {
			log.error(e);
			return "Error";
		}
	}
}
