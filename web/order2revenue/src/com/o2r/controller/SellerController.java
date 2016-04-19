package com.o2r.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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

	static Logger log = Logger.getLogger(SellerController.class.getName());
	Map<String, Object> model = new HashMap<String, Object>();

	@RequestMapping(value = "/saveSeller", method = RequestMethod.POST)
	public ModelAndView saveOrder(
			@ModelAttribute("command") SellerBean sellerBean,
			BindingResult result) {
		log.info("*** saveOrder starts ***");
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Seller seller = ConverterClass.prepareSellerModel(sellerBean);
			// seller.getRole().setRole("moderator");
			sellerService.addSeller(seller);
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

			if (seller.getStateDeliveryTime() == null
					|| seller.getStateDeliveryTime().size() == 0) {
				List<State> stateList = sellerService.listStates();
				List<StateDeliveryTime> stateDeliveryTimeList = new ArrayList<StateDeliveryTime>();
				if (stateList != null && stateList.size() != 0) {
					for (State bean : stateList) {
						StateDeliveryTime stateDeliveryTime = new StateDeliveryTime();
						stateDeliveryTime.setState(bean);
						stateDeliveryTimeList.add(stateDeliveryTime);
					}
				}
				seller.setStateDeliveryTime(ConverterClass
						.prepareStateDeliveryTimeBean(stateDeliveryTimeList));
			}
			model.put("seller", seller);
		} catch (Throwable e) {
			log.error(e);
			return new ModelAndView("globalErorPage", model);
		}
		return new ModelAndView("selleraccount/addSeller", model);

	}

	@RequestMapping(value = "/seller/saveSeller", method = RequestMethod.POST)
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
			sellerService.addSeller(seller);
			model.put("seller", seller);
		} catch (Throwable e) {
			log.error(e);
			return new ModelAndView("globalErorPage", model);
		}
		return new ModelAndView("selleraccount/addSeller", model);
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
		return new ModelAndView("planUpgrade", model);
	}

	@RequestMapping("/seller/upgrade2.html")
	public ModelAndView planUpgrade2(HttpServletRequest request,
			@ModelAttribute("command") PlanBean planBean, BindingResult result) {
		// System.out.println("inside upgrade controller");
		// System.out.println("PPlan id in controller " + planBean.getPid());
		// System.out.println("Plan id from request in controller "+
		// request.getParameter("pid"));
		// Plan plan=ConverterClass.preparePlanModel(planBean);
		// System.out.println(" Controller : "+plan.getPid());

		log.info("*** planUpgrade2 starts ***");
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			sellerService.planUpgrade(planBean.getPid(),
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
		return new ModelAndView("planUpgrade2");
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

}
