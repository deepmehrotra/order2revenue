package com.o2r.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.o2r.bean.PlanBean;
import com.o2r.bean.SellerBean;
import com.o2r.helper.ConverterClass;
import com.o2r.helper.CustomException;
import com.o2r.helper.HelperClass;
import com.o2r.model.Seller;
import com.o2r.model.State;
import com.o2r.model.StateDeliveryTime;
import com.o2r.service.PartnerService;
import com.o2r.service.PlanService;
import com.o2r.service.SellerService;
import com.o2r.service.TaxDetailService;

/**
 * @author Deep Mehrotra
 *
 */
// GIT Test
@Controller
public class SellerController {

	@Autowired
	private SellerService sellerService;
	@Autowired
	private PlanService planService;
	@Autowired
	private TaxDetailService taxDetailService;
	@Autowired
	private PartnerService partnerService;
	@Autowired
	ServletContext context;
	Properties props = null;
	org.springframework.core.io.Resource resource = new ClassPathResource(
			"database.properties");

	static Logger log = Logger.getLogger(SellerController.class.getName());
	Map<String, Object> model = new HashMap<String, Object>();

	@RequestMapping(value = "/saveSeller", method = RequestMethod.POST)
	public ModelAndView registerSeller(
			@ModelAttribute("command") SellerBean sellerBean,
			BindingResult result,HttpServletRequest request) {
		log.info("*** saveOrder starts ***");
		Properties p=new Properties(); 		
		
			try {
			InputStream input = request.getServletContext().getResourceAsStream("/WEB-INF/mail.properties");
 		    p.load(input);				   
		} catch (IOException e) {
			e.printStackTrace();
		}		
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Seller seller = ConverterClass.prepareSellerModel(sellerBean);

			sellerService.addSeller(seller,p);
		} catch (CustomException ce) {
			log.error("saveOrder exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		}

		log.info("*** saveOrder ends ***");
		return new ModelAndView("redirect:/login-form.html?registered=true");
	}

	@RequestMapping(value = "/sellers", method = RequestMethod.GET)
	public ModelAndView listSellers() {

		log.info("*** listSeller starts ***");
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("sellers", ConverterClass
					.prepareListofSellerBean(sellerService.listSellers()));
		} catch (CustomException ce) {
			log.error("listSeller exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		}

		log.info("*** listOrder ends ***");
		return new ModelAndView("sellerList", model);
	}

	@RequestMapping(value = "/addSeller", method = RequestMethod.GET)
	public ModelAndView addOrder(
			@ModelAttribute("command") SellerBean sellerBean,
			BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		log.info("*** addOrder starts ***");
		try {
			model.put("sellers", ConverterClass
					.prepareListofSellerBean(sellerService.listSellers()));
		} catch (CustomException ce) {
			log.error("addOrder exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		}
		log.info("*** addOrder ends ***");
		return new ModelAndView("addSeller", model);
	}

	@RequestMapping(value = "/seller/addSeller", method = RequestMethod.GET)
	public ModelAndView addSeller(HttpServletRequest request,
			@ModelAttribute("command") SellerBean sellerBean,
			BindingResult result) {

		log.info("***addSeller Start***");
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			int sellerId = HelperClass.getSellerIdfromSession(request);
			SellerBean seller = ConverterClass.prepareSellerBean(sellerService
					.getSeller(sellerId));
			Map<String,Integer> stateTimes=new HashMap<String, Integer>();

			if (seller.getStateDeliveryTime() == null
					|| seller.getStateDeliveryTime().size() == 0) {
				List<State> stateList = sellerService.listStates();
				//List<StateDeliveryTime> stateDeliveryTimeList = new ArrayList<StateDeliveryTime>();
				if (stateList != null && stateList.size() != 0) {
					
					for (State bean : stateList) {
					/*	StateDeliveryTime stateDeliveryTime = new StateDeliveryTime();
						stateDeliveryTime.setState(bean);
						seller.getStateDeliveryTime().add(stateDeliveryTime);*/
						stateTimes.put(bean.getStateName(), 0);
						//stateDeliveryTimeList.add(stateDeliveryTime);
					}
				}
				/*seller.setStateDeliveryTime(ConverterClass
						.prepareStateDeliveryTimeBean(stateDeliveryTimeList));*/
			}
			else
			{
				List<StateDeliveryTime> sdtlist = ConverterClass.prepareStateDeliveryTimeModel(seller.getStateDeliveryTime());
				for (StateDeliveryTime sdt : sdtlist) {
					/*	StateDeliveryTime stateDeliveryTime = new StateDeliveryTime();
						stateDeliveryTime.setState(bean);
						seller.getStateDeliveryTime().add(stateDeliveryTime);*/
						stateTimes.put(sdt.getState().getStateName(), sdt.getDeliveryTime());
						//stateDeliveryTimeList.add(stateDeliveryTime);
					}
				
			}
			model.put("stateTimes", stateTimes);
			model.put("seller", seller);
		} catch (Throwable e) {
			log.error(e);
			return new ModelAndView("globalErorPage", model);
		}
		return new ModelAndView("selleraccount/addSeller", model);

	}
/*	@RequestMapping(value = "/seller/saveSeller", method = RequestMethod.POST)
	public ModelAndView saveSeller(
			@ModelAttribute("command") SellerBean sellerBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			log.info("***saveSeller Start***");
			Seller seller = ConverterClass.prepareSellerModel(sellerBean);
			Set<Seller> sellerRoles = new HashSet<Seller>();
			sellerRoles.add(seller);
			seller.getRole().setSellerRoles(sellerRoles);
			sellerService.addSeller(seller,null);
			model.put("seller", seller);
		} catch (Throwable e) {
			log.error(e);
			return new ModelAndView("globalErorPage", model);
		}
		return new ModelAndView("selleraccount/addSeller", model);
	}*/
	
	@RequestMapping(value = "/seller/saveSeller",headers=("content-type=multipart/*"), method = RequestMethod.POST)
	public ModelAndView saveSeller(
			@ModelAttribute("command") SellerBean sellerBean,HttpServletRequest request,
			BindingResult result, @RequestParam(value = "image", required = false) MultipartFile image) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			log.info("***saveSeller Start***");
			
			if (image != null) {
				System.out.println(" Not getting any image");
				if (!image.isEmpty()) {
					try {
						validateImage(image);

					} catch (RuntimeException re) {
						result.reject(re.getMessage());
					}
				}
				
				try {
					props = PropertiesLoaderUtils.loadProperties(resource);

					if (sellerBean.getName() != null) {
						sellerBean.setLogoUrl(props.getProperty("sellerimages.view")
								+ sellerBean.getName() + ".jpg");
						saveImage(sellerBean.getName() + ".jpg", image);
					}
				} catch (Exception e) {
					result.reject(e.getMessage());
					//return new ModelAndView("redirect:/seller/partners.html");
				}
			}	
			Map<String, String[]> parameters = request.getParameterMap();
			
			for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
				if (entry.getKey() != null && !entry.getKey().isEmpty())
				{
					if (entry.getKey().contains("sdt-")) {
						String temp = entry.getKey().substring(4);
						System.out.println(" Sdt : "+temp);
						StateDeliveryTime sdtobj=new StateDeliveryTime();
						sdtobj.setDeliveryTime(entry.getValue()[0]!=null?Integer.parseInt(entry.getValue()[0]):0);
						sdtobj.setState(sellerService.getStateByName(temp));
						sdtobj.setSeller(ConverterClass.prepareSellerModel(sellerBean));
						sellerBean.getStateDeliveryTime().add(ConverterClass.prepareStateDeliveryTimeBean(sdtobj));
						
					}
				}
			}
			//System.out.println(" Seellerbean populating : "+sellerBean.getStateDeliveryTime().size());
			Seller seller = ConverterClass.prepareSellerModel(sellerBean);
			System.out.println("***************** : "+seller.getLogoUrl());
			Set<Seller> sellerRoles = new HashSet<Seller>();
			sellerRoles.add(seller);
			seller.getRole().setSellerRoles(sellerRoles);
			
			sellerService.addSeller(seller, null);
			model.put("seller", seller);
		} catch (Throwable e) {
			log.error(e);
			return new ModelAndView("globalErorPage", model);
		}
		log.info("***saveSeller Exit***");
		return new ModelAndView("redirect:/seller/dashboard.html");
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(
			@ModelAttribute("command") SellerBean sellerBean,
			BindingResult result) {
		System.out.println(" Calling register");
		return new ModelAndView("register", model);
	}

	@RequestMapping(value = "/sellerindex", method = RequestMethod.GET)
	public ModelAndView welcome() {
		return new ModelAndView("index");
	}

	@RequestMapping(value = "/deleteSeller", method = RequestMethod.GET)
	public ModelAndView deleteOrder(
			@ModelAttribute("command") SellerBean sellerBean,
			BindingResult result) {
		log.info("*** deleteOrder starts ***");
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			sellerService.deleteSeller(ConverterClass
					.prepareSellerModel(sellerBean));
			model.put("seller", null);
			model.put("sellers", ConverterClass
					.prepareListofSellerBean(sellerService.listSellers()));
		} catch (CustomException ce) {
			log.error("deleteOrder exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		}
		log.info("*** deleteOrder ends ***");
		return new ModelAndView("addSeller", model);
	}

	@RequestMapping(value = "/editSeller", method = RequestMethod.GET)
	public ModelAndView editOrder(
			@ModelAttribute("command") SellerBean sellerBean,
			BindingResult result) {

		log.info("*** editOrder starts ***");
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("seller", ConverterClass.prepareSellerBean(sellerService
					.getSeller(sellerBean.getId())));
			model.put("sellers", ConverterClass
					.prepareListofSellerBean(sellerService.listSellers()));
		} catch (CustomException ce) {
			log.error("editOrder exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		}

		log.info("*** editOrder starts ***");
		return new ModelAndView("addSeller", model);
	}

	@RequestMapping("/seller/summary.html")
	public ModelAndView planSummary(
			@ModelAttribute("command") PlanBean planBean, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("myAccount", sellerService.getSeller(5));
		} catch (CustomException ce) {
			log.error("planUpgrade exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		}
		return new ModelAndView("selleraccount/planSummary", model);
	}

	@RequestMapping("/seller/upgrade.html")
	public ModelAndView planUpgrade(
			@ModelAttribute("command") PlanBean planBean, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("upgrade", ConverterClass
					.prepareListofPlanBean(planService.listPlans()));
		} catch (CustomException ce) {
			log.error("planUpgrade exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		}
		return new ModelAndView("selleraccount/planUpgrade", model);
	}
	
	@RequestMapping("/seller/payumoney.html")
	public ModelAndView payuMoney(HttpServletRequest request,
			@ModelAttribute("command") PlanBean planBean, BindingResult result) {
		return new ModelAndView("selleraccount/payuform");
	}

	@RequestMapping("/seller/thankyou.html")
	public ModelAndView planUpgrade2(HttpServletRequest request,
			@ModelAttribute("command") PlanBean planBean, BindingResult result) {
		// System.out.println("inside upgrade controller");
		// System.out.println("PPlan id in controller " + planBean.getPid());
		// System.out.println("Plan id from request in controller "+
		Double currTotalAmount = new Double(request.getParameter("totalAmount"));
		Long currOrderCount = new Long(request.getParameter("orderCount"));
		// Plan plan=ConverterClass.preparePlanModel(planBean);
		// System.out.println(" Controller : "+plan.getPid());

		log.info("*** planUpgrade2 starts ***");
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("currTotalAmount", currTotalAmount);
			model.put("currOrderCount", currOrderCount);
			sellerService.planUpgrade(planBean.getPid(), currTotalAmount, currOrderCount,
					HelperClass.getSellerIdfromSession(request));
		} catch (CustomException ce) {
			log.error("planUpgrade2 exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			log.error(e);
		}

		log.info("*** planUpgrade2 ends ***");
		return new ModelAndView("selleraccount/planUpgrade2", model);
	}

	@RequestMapping(value = "/checkExistingUser", method = RequestMethod.GET)
	public @ResponseBody String checkExistingUser(HttpServletRequest request) {
		log.info("***checkExistingUser Start****");
		Map<String, Object> model = new HashMap<String, Object>();
		log.debug("Registered seller email : " + request.getParameter("email"));
		try {
			String email = request.getParameter("email");
			if (email != null) {
				Seller seller = sellerService.getSeller(email);

				if (seller != null) {
					return "false";
				} else {
					return "true";
				}
			} else
				return "false";
		}
		/*
		 * catch(CustomException ce) {
		 * log.error("saveTaxCategory exception : "+ce.toString()); return
		 * "false"; }
		 */
		catch (Throwable e) {
			log.error(e);
			return "false";
		}
	}
	
	private void validateImage(MultipartFile image) {
		if (!image.getContentType().equals("image/jpeg")) {
			throw new RuntimeException("Only JPG images are accepted");
		}
	}
	
	private void saveImage(String filename, MultipartFile image)
			throws RuntimeException, IOException {
		log.info("*** saveImage for seller start ***");
		try {
			String catalinabase = System.getProperty("catalina.base");

			try {
				props = PropertiesLoaderUtils.loadProperties(resource);
			} catch (IOException e) {
				log.error(e.getCause());

			}
			System.out.println("dialect in order controller : "
					+ props.getProperty("sellerimages.path"));
			File file = new File(catalinabase
					+ props.getProperty("sellerimages.path") + filename);
			System.out.println(" context.getRealPath(/) "
					+ context.getRealPath("/"));
			System.out.println(" Path to save file : " + file.toString());
			FileUtils.writeByteArrayToFile(file, image.getBytes());
			System.out.println("Go to the location:  "+ file.toString()+ " on your computer and verify that the image has been stored.");
		} catch (IOException e) {

			log.error(e.getCause());
			/*
			 * System.out.println(" Error in saving image : "+
			 * e.getLocalizedMessage()); e.printStackTrace(); throw e;
			 */
		}
		log.info("*** saveImage for seller exit ***");
	}

}
