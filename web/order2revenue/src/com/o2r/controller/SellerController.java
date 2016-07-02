package com.o2r.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
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
	@Autowired
	private HelperClass helperClass;

	Properties props = null;
	org.springframework.core.io.Resource resource = new ClassPathResource(
			"database.properties");

	static Logger log = Logger.getLogger(SellerController.class.getName());
	Map<String, Object> model = new HashMap<String, Object>();

	@RequestMapping(value = "/verify", method = RequestMethod.GET)
	public String verifySeller(HttpServletRequest request){
		try{
			String verifyCode = request.getParameter("code");
			Seller seller = sellerService.getSellerVerCode(verifyCode);
			if (seller.getVerCode() != null && seller.getVerCode().equals(verifyCode)) {
				seller.setVerCode("Verified");
				sellerService.addSeller(seller);
				return "login_register";				
			}else{
				return "login_register";
			}
		}catch(Exception e){
			log.error("Failed!",e);
			e.printStackTrace();
		}
		return "confirmation";
	}
	
	
	@RequestMapping(value = "/saveSeller", method = RequestMethod.POST)
	public ModelAndView registerSeller(
			@ModelAttribute("command") SellerBean sellerBean,
			BindingResult result,HttpServletRequest request) {
		
		log.info("$$$ registerSeller Starts : SellerController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		
		String name=request.getParameter("name");
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		sellerBean.setName(name);
		sellerBean.setEmail(email);
		sellerBean.setPassword(password);				
		try {
			Seller seller = ConverterClass.prepareSellerModel(sellerBean);
			sellerService.addSeller(seller);
		} catch (CustomException ce) {
			log.error("saveOrder exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("redirect:/landing/500error.html");
			//return new ModelAndView("globalErorPage", model);
		}

		log.info("$$$ registerSeller Ends : SellerController $$$");
		return new ModelAndView("confirmation");
	}

	@RequestMapping(value = "/sellers", method = RequestMethod.GET)
	public ModelAndView listSellers() {

		log.info("$$$ listSellers Starts : SellerController $$$");
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

		log.info("$$$ listSellers Ends : SellerController $$$");
		return new ModelAndView("sellerList", model);
	}

	@RequestMapping(value = "/addSeller", method = RequestMethod.GET)
	public ModelAndView addNewSeller(
			@ModelAttribute("command") SellerBean sellerBean,
			BindingResult result) {

		log.info("$$$ addNewSeller Starts : SellerController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("sellers", ConverterClass
					.prepareListofSellerBean(sellerService.listSellers()));
		} catch (CustomException ce) {
			log.error("addOrder exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("redirect:/landing/500error.html");
			//return new ModelAndView("globalErorPage", model);
		}
		log.info("$$$ addOrder Starts : SellerController $$$");
		return new ModelAndView("addSeller", model);
	}
	
	@RequestMapping(value = "/seller/sellerList", method = RequestMethod.GET)
	public ModelAndView listSeller(HttpServletRequest request, @ModelAttribute("command") SellerBean sellerBean, BindingResult result) {
		
		log.info("$$$ listSeller Starts : SellerController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		List sellers=null;
		try{
			sellers=sellerService.listSellers();
			if(sellers != null && sellers.size() != 0){
				model.put("sellers", sellers);
			}
			
		}catch(Exception e){
			log.error("Failed to getting Sellers ! SellerController ", e);
			e.printStackTrace();
		}		
		log.info("$$$ listSeller Ends : SellerController $$$");
		return new ModelAndView("miscellaneous/sellerList", model);
	}
	
	@RequestMapping(value = "/seller/changePassword", method = RequestMethod.POST)
	public ModelAndView changePassword(HttpServletRequest request, HttpSession session) {
		
		log.info("$$$ changePassword Starts : SellerController $$$");		
		String oldPassword=request.getParameter("oldPass");	
		System.out.println(request.getParameter("oldPass")+" --> "+oldPassword);
		Seller seller=null;
		
		try{			
			seller=sellerService.getSeller(helperClass.getSellerIdfromSession(request));
			if(seller != null){
				if(seller.getPassword().equals(DatatypeConverter.printHexBinary(oldPassword.getBytes("UTF-8")))){
					seller.setPassword(DatatypeConverter.printHexBinary(request.getParameter("newPass").getBytes("UTF-8")));
					sellerService.addSeller(seller);
					session.setAttribute("passwordStatus", "Password Changed Successfully");					
				}else{
					session.setAttribute("passwordStatus", "Invalid Old Password");					
				}
			}	
		}catch(Exception e){
			log.error("Failed to Change Password ! SellerController ", e);
			e.printStackTrace();
		}		
		log.info("$$$ changePassword Ends : SellerController $$$");
		return new ModelAndView("redirect:/seller/addSeller.html");
	}
	
	
	@RequestMapping(value = "/mail4get", method = RequestMethod.POST)
	public String mail4getPassword(HttpServletRequest request) {
		
		log.info("$$$ mail4getPassword Starts : SellerController $$$");
		
		String target_mail=request.getParameter("passwordMail");
		System.out.println("Mail Address : "+target_mail);
		Random random=new Random();
		Seller seller=null;
		String subject=null;
		String body=null;
		try{
			seller=sellerService.getSeller(target_mail);
			if(seller != null){
				subject="Login Password";
				int newPass=random.nextInt((999999 - 111111) + 1) + 111111;
				System.out.println(newPass);
				body="Your New Login Password : "+newPass;
				boolean status=sellerService.sendMail(target_mail, subject, body);
				if(status == true){
					seller.setPassword(DatatypeConverter.printHexBinary(String.valueOf(newPass).getBytes("UTF-8")));
					sellerService.addSeller(seller);
				}
			}
		}catch(Exception e){
			log.error("Failed to Sending Mail 4 get Password ! SellerController ", e);
			e.printStackTrace();
		}		
		log.info("$$$ mail4getPassword Ends : SellerController $$$");
		return "login_register";
	}

	@RequestMapping(value = "/seller/addSeller", method = RequestMethod.GET)
	public ModelAndView addSeller(HttpServletRequest request,
			@ModelAttribute("command") SellerBean sellerBean,
			BindingResult result, HttpSession session) {

		log.info("$$$ addSeller Starts : SellerController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			int sellerId = helperClass.getSellerIdfromSession(request);
			SellerBean seller = ConverterClass.prepareSellerBean(sellerService
					.getSeller(sellerId));
			Map<String,Integer> stateTimes=new HashMap<String, Integer>();

			if (seller.getStateDeliveryTime() == null
					|| seller.getStateDeliveryTime().size() == 0) {
				List<State> stateList = sellerService.listStates();
				if (stateList != null && stateList.size() != 0) {
					
					for (State bean : stateList) {
						stateTimes.put(bean.getStateName(), 0);						
					}
				}
			}
			else
			{
				List<StateDeliveryTime> sdtlist = seller.getStateDeliveryTime();
				for (StateDeliveryTime sdt : sdtlist) {
					stateTimes.put(sdt.getState().getStateName(), sdt.getDeliveryTime());
				}
				
			}
			model.put("passwordStatus", session.getAttribute("passwordStatus"));
			session.removeAttribute("passwordStatus");
			model.put("stateTimes", stateTimes);
			model.put("seller", seller);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed!",e);
			return new ModelAndView("globalErorPage", model);
		}
		
		log.info("$$$ addSeller Ends : SellerController $$$");
		return new ModelAndView("selleraccount/addSeller", model);

	}

	@RequestMapping(value = "/seller/saveSeller",headers=("content-type=multipart/*"), method = RequestMethod.POST)
	public ModelAndView saveSeller(
			@ModelAttribute("command") SellerBean sellerBean,HttpServletRequest request,
			BindingResult result, @RequestParam(value = "image", required = false) MultipartFile image, HttpSession session) {
		
		log.info("$$$ saveSeller Starts : SellerController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		List<StateDeliveryTime> sdtList=new ArrayList<StateDeliveryTime>();
		try {
			if (image.getSize() != 0) {
				log.debug(" Not getting any image");
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
						saveImage(sellerBean.getName() +sellerBean.getId() +".jpg", image);
					}
				} catch (Exception e) {
					e.printStackTrace();
					log.error("Failed!",e);
					result.reject(e.getMessage());
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
						
						sdtList.add(sdtobj);
		
						sdtobj.setSeller(ConverterClass.prepareSellerModel(sellerBean));
						sellerBean.getStateDeliveryTime().add(sdtobj);						
					}
				}
			}
			session.setAttribute("sellerName", sellerBean.getName());			
			Seller seller = ConverterClass.prepareSellerModel(sellerBean);
			log.debug("****** : "+seller.getLogoUrl());
			Set<Seller> sellerRoles = new HashSet<Seller>();
			sellerRoles.add(seller);
			seller.getRole().setSellerRoles(sellerRoles);			
			
			sellerService.addSeller(seller);
			sellerService.addStateDeliveryTime(sdtList, seller.getId());
			model.put("seller", seller);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed!",e);
			return new ModelAndView("globalErorPage", model);
		}
		log.info("$$$ saveSeller Ends : SellerController $$$");
		return new ModelAndView("redirect:/seller/dashboard.html");
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(
			@ModelAttribute("command") SellerBean sellerBean,
			BindingResult result) {
		log.debug(" Calling register");
		return new ModelAndView("login_register", model);
	}

	@RequestMapping(value = "/sellerindex", method = RequestMethod.GET)
	public ModelAndView welcome() {
		return new ModelAndView("index");
	}

	@RequestMapping(value = "/deleteSeller", method = RequestMethod.GET)
	public ModelAndView deleteOrder(
			@ModelAttribute("command") SellerBean sellerBean,
			BindingResult result) {
		
		log.info("$$$ deleteOrder Starts : SellerController $$$");
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
		log.info("$$$ deleteOrder Ends : SellerController $$$");
		return new ModelAndView("addSeller", model);
	}

	@RequestMapping(value = "/editSeller", method = RequestMethod.GET)
	public ModelAndView editOrder(
			@ModelAttribute("command") SellerBean sellerBean,
			BindingResult result) {

		log.info("$$$ editOrder Starts : SellerController $$$");
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

		log.info("$$$ editOrder Ends : SellerController $$$");
		return new ModelAndView("addSeller", model);
	}

	@RequestMapping("/seller/summary.html")
	public ModelAndView planSummary(HttpServletRequest request) {
		
		log.info("$$$ planSummary Starts : SellerController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			int sellerId = helperClass.getSellerIdfromSession(request);
			model.put("myAccount", sellerService.getSeller(sellerId));
		} catch (CustomException ce) {
			log.error("planUpgrade exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Exception ex) {
			log.error("Failed!",ex);
			ex.printStackTrace();
			model.put("errorMessage", ex.getMessage());
			model.put("errorTime", new Date());
			return new ModelAndView("globalErorPage", model);
		}
		log.info("$$$ planSummary Ends : SellerController $$$");
		return new ModelAndView("selleraccount/planSummary", model);
	}

	@RequestMapping("/seller/upgrade.html")
	public ModelAndView planUpgrade(HttpServletRequest request, 
			@ModelAttribute("command") PlanBean planBean, BindingResult result) {
		
		log.info("$$$ planUpgrade Starts : SellerController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("upgrade", ConverterClass
					.prepareListofPlanBean(planService.listPlans()));
			int sellerId = helperClass.getSellerIdfromSession(request);
			model.put("myAccount", sellerService.getSeller(sellerId));
		} catch (CustomException ce) {
			log.error("planUpgrade exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed!",e);
		}
		log.info("$$$ planUpgrade Ends : SellerController $$$");
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
		
		log.info("$$$ planUpgrade2 Starts : SellerController $$$");
		Double currTotalAmount = new Double(request.getParameter("totalAmount"));
		Long currOrderCount = new Long(request.getParameter("orderCount"));
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("currTotalAmount", currTotalAmount);
			model.put("currOrderCount", currOrderCount);
			sellerService.planUpgrade(planBean.getPid(), currTotalAmount, currOrderCount,
					helperClass.getSellerIdfromSession(request));
		} catch (CustomException ce) {
			log.error("planUpgrade2 exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed!",e);
		}

		log.info("$$$ planUpgrade2 Ends : SellerController $$$");
		return new ModelAndView("selleraccount/planUpgrade2", model);
	}

	@RequestMapping(value = "/checkExistingUser", method = RequestMethod.GET)
	public @ResponseBody String checkExistingUser(HttpServletRequest request) {
		
		log.info("$$$ checkExistingUser Starts : SellerController $$$");
		//Map<String, Object> model = new HashMap<String, Object>();
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
		}catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed!",e);
			return "false";
		}
	}
	
	
	@RequestMapping(value = "/seller/getSellerName", method = RequestMethod.GET)
	public @ResponseBody String getSellerName(HttpServletRequest request) {
		
		log.info("$$$ getSellerName Starts : SellerController $$$");
		//Map<String, Object> model = new HashMap<String, Object>();
		Seller seller = null;
		if(request.getSession().getAttribute("logoUrl")==null||request.getSession().getAttribute("sellerName")==null)
		{
			try {
				seller=sellerService.getSeller(helperClass.getSellerIdfromSession(request));
		
			request.getSession().setAttribute("logoUrl", seller.getLogoUrl());
			request.getSession().setAttribute("sellerName", seller.getName());
			if(seller.getLogoUrl()!=null&&StringUtils.isNotBlank(seller.getLogoUrl()))
			return seller.getLogoUrl()+","+seller.getName();
			else
				return "null,"+seller.getName();
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Error getting seller ", e);
			}
			
		}
		return "true";
		
	}
	
	
	private void validateImage(MultipartFile image) {
		if (!image.getContentType().equals("image/jpeg")) {
			throw new RuntimeException("Only JPG images are accepted");
		}
	}
	
	private void saveImage(String filename, MultipartFile image)
			throws RuntimeException, IOException {
		

		log.info("$$$ saveImage Starts : SellerController $$$");
		try {
			String catalinabase = System.getProperty("catalina.base");

			try {
				props = PropertiesLoaderUtils.loadProperties(resource);
			} catch (IOException e) {
				log.error(e.getCause());

			}
			log.debug("dialect in order controller : "
					+ props.getProperty("sellerimages.path"));
			File file = new File(catalinabase
					+ props.getProperty("sellerimages.path") + filename);
			log.debug(" context.getRealPath(/) "
					+ context.getRealPath("/"));
			log.debug(" Path to save file : " + file.toString());
			FileUtils.writeByteArrayToFile(file, image.getBytes());
			log.debug("Go to the location:  "+ file.toString()+ " on your computer and verify that the image has been stored.");
		} catch (IOException e) {
			e.printStackTrace();
			log.error("Failed!",e);
		}
		log.info("$$$ saveImage Ends : SellerController $$$");
	}
	

}
