package com.o2r.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.o2r.bean.ExpenseBean;
import com.o2r.bean.ExpenseCategoryBean;
import com.o2r.helper.ConverterClass;
import com.o2r.helper.CustomException;
import com.o2r.helper.GlobalConstant;
import com.o2r.helper.HelperClass;
import com.o2r.model.ExpenseCategory;
import com.o2r.model.Expenses;
import com.o2r.service.ExpenseService;

/**
 * @author Deep Mehrotra
 *
 */
@Controller
public class ExpenseController {

	@Autowired
	private ExpenseService expenseService;
	@Autowired
	private HelperClass helperClass;
	
	static Logger log = Logger.getLogger(AdminController.class.getName());

	@RequestMapping(value = "/seller/saveExpenseCategory", method = RequestMethod.POST)
	public ModelAndView saveExpenseCategory(HttpServletRequest request,
			@ModelAttribute("command") ExpenseCategoryBean categoryBean,
			BindingResult result) {

		log.info("$$$ saveExpenseCategory Starts : ExpenseController $$$");
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		categoryBean.setCreatedOn(new Date());
		ExpenseCategory category;
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			category = ConverterClass.prepareExpenseCategoryModel(categoryBean);
			expenseService.addExpenseCategory(category,
					helperClass.getSellerIdfromSession(request));
		} catch (CustomException ce) {
			log.error("saveExpenseCategory exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by Seller Id : " + sellerId, e);
		}
		log.info("$$$ saveExpenseCategory Ends : ExpenseController $$$");
		return new ModelAndView("redirect:/seller/expenseCategories.html");
	}

	@RequestMapping(value = "/seller/saveExpense", method = RequestMethod.POST)
	public ModelAndView saveExpense(HttpServletRequest request,
			@ModelAttribute("command") ExpenseBean expenseBean,
			BindingResult result) {

		log.info("$$$ saveExpense Starts : ExpenseController $$$");
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		Expenses expense;
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			expense = ConverterClass.prepareExpenseModel(expenseBean);
			expenseService.addExpense(expense,
					helperClass.getSellerIdfromSession(request));
		} catch (CustomException ce) {
			log.error("saveExpense exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by Seller Id : " + sellerId, e);
		}
		log.info("$$$ saveExpense Ends : ExpenseController $$$");
		return new ModelAndView("redirect:/seller/expenselist.html");
	}

	@RequestMapping(value = "/seller/searchExpense", method = RequestMethod.POST)
	public ModelAndView searchExpense(HttpServletRequest request,
			@ModelAttribute("command") ExpenseBean expenseBean,
			BindingResult result) {

		log.info("$$$ searchExpense Starts : ExpenseController $$$");
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		List<ExpenseBean> expenseList = new ArrayList<>();
		String expenseName = request.getParameter("expName");
		String expenseCategory = request.getParameter("expenseCategory");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String searchExpense = request.getParameter("searchExpense");
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			if (searchExpense != null
					&& searchExpense.equals("expenseCategory")
					&& expenseCategory != null) {
				expenseList = ConverterClass
						.prepareListofExpenseBean(expenseService
								.getExpenseByCategory(expenseCategory, sellerId));
			} else if (searchExpense != null && searchExpense.equals("expName")
					&& expenseCategory != null) {
				expenseList = ConverterClass
						.prepareListofExpenseBean(expenseService
								.getExpenseByName(expenseName, sellerId));
			} else if (searchExpense != null && startDate != null
					&& endDate != null) {

				expenseList = ConverterClass
						.prepareListofExpenseBean(expenseService
								.getExpenseByDate(new Date(startDate),
										new Date(endDate), sellerId));
			} else {
				log.error("searchExpense exception : "
						+ GlobalConstant.nullException);
				model.put("errorMessage", GlobalConstant.nullValuesError);
				model.put("errorTime", new Date());
				model.put("errorCode", GlobalConstant.nullValuesErrorCode);
				return new ModelAndView("globalErorPage", model);
			}
		} catch (CustomException ce) {
			log.error("searchExpense exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by Seller Id : " + sellerId, e);
		}
		request.getSession().setAttribute("expenseSearchObject", expenseList);
		log.info("$$$ searchExpense Ends : ExpenseController $$$");
		return new ModelAndView("redirect:/seller/expenselist.html");

	}

	@RequestMapping(value = "/seller/expenseCategories", method = RequestMethod.GET)
	public ModelAndView listExpenseCategory(HttpServletRequest request) {

		log.info("$$$ listExpenseCategory Starts : ExpenseController $$$");
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		List<ExpenseCategoryBean> expenseCategories = null;
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			expenseCategories = ConverterClass
					.prepareListofExpenseCategoryBean(expenseService
							.listExpenseCategories(sellerId));

			if (expenseCategories != null && expenseCategories.size() > 0) {
				for (ExpenseCategoryBean bean : expenseCategories) {
					bean.setMonthlyAmount(expenseService.getMonthlyAmount(
							bean.getExpcategoryId(), sellerId));
				}
			}
		} catch (CustomException ce) {
			log.error("listExpenseCategory exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by Seller Id : " + sellerId, e);
		}

		model.put("expenseCategories", expenseCategories);
		log.info("$$$ listExpenseCategory Ends : ExpenseController $$$");
		return new ModelAndView("initialsetup/expenseCategoryList", model);
	}

	@RequestMapping(value = "/seller/expenselist", method = RequestMethod.GET)
	public ModelAndView listExpenses(HttpServletRequest request) {

		log.info("$$$ listExpenses Starts : ExpenseController $$$");
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		Object obj = request.getSession().getAttribute("expenseSearchObject");
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			if (obj != null) {
				System.out.println(" Getting list from session" + obj);
				model.put("expenses", obj);
				request.getSession().removeAttribute("expenseSearchObject");
			} else {
				model.put("expenses", ConverterClass
						.prepareListofExpenseBean(expenseService
								.listExpenses(helperClass
										.getSellerIdfromSession(request))));
			}
		} catch (CustomException ce) {
			log.error("listExpense exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by Seller Id : " + sellerId, e);
		}
		log.info("$$$ listExpenses Ends : ExpenseController $$$");
		return new ModelAndView("initialsetup/viewExpenseGroup", model);
	}

	@RequestMapping(value = "/seller/addExpenseCategory", method = RequestMethod.GET)
	public String addExpenseCategory(HttpServletRequest request,
			@ModelAttribute("command") ExpenseCategoryBean categoryBean,
			BindingResult result) {
		return "initialsetup/addExpenseCategory";
	}

	@RequestMapping(value = "/seller/viewExpenseGroup", method = RequestMethod.GET)
	public ModelAndView viewExpenseCategory(HttpServletRequest request,
			@ModelAttribute("command") ExpenseCategoryBean categoryBean,
			BindingResult result) {

		log.info("$$$ viewExpenseCategory Starts : ExpenseController $$$");
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		ExpenseCategoryBean expcatbean = null;
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			expcatbean = ConverterClass
					.prepareExpenseCategoryBean(expenseService
							.getExpenseCategory(categoryBean.getExpcategoryId()));
		} catch (CustomException ce) {
			log.error("viewExpenseCategory exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by Seller Id : " + sellerId, e);
		}
		model.put("expenseCategory", expcatbean);
		model.put("expenses", expcatbean.getExpenses());
		log.info("$$$ viewExpenseCategory Ends : ExpenseController $$$");
		return new ModelAndView("initialsetup/viewExpenseGroup", model);
	}

	@RequestMapping(value = "/seller/addExpense", method = RequestMethod.GET)
	public ModelAndView addExpense(HttpServletRequest request,
			@ModelAttribute("command") ExpenseBean expense, BindingResult result) {

		log.info("$$$ addExpense Starts : ExpenseController $$$");
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, String> catmap = new HashMap<String, String>();
		List<ExpenseCategory> categorylist;
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			categorylist = expenseService.listExpenseCategories(helperClass
					.getSellerIdfromSession(request));
			for (ExpenseCategory expcat : categorylist) {
				catmap.put(expcat.getExpcatName(), expcat.getExpcatName());
			}
		} catch (CustomException ce) {
			log.error("addExpense exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by Seller Id : " + sellerId, e);
		}
		model.put("catmap", catmap);
		log.info("$$$ addExpense Ends : ExpenseController $$$");
		return new ModelAndView("initialsetup/addExpense", model);

	}

	@RequestMapping(value = "/seller/deleteExpenseCategory", method = RequestMethod.GET)
	public ModelAndView deleteExpenseCategory(HttpServletRequest request,
			@ModelAttribute("command") ExpenseCategoryBean categoryBean,
			BindingResult result) {

		log.info("$$$ deleteExpenseCategory Starts : ExpenseController $$$");
		int sellerId = 0;
		int catdelete;
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			catdelete = expenseService.deleteExpenseCategory(
					ConverterClass.prepareExpenseCategoryModel(categoryBean),
					helperClass.getSellerIdfromSession(request));
			if (catdelete == 0) {
				model.put("error",
						"Unable to delete Expense Category , first delete subcategories");
			}
			model.put("expenseCategories", ConverterClass
					.prepareListofExpenseCategoryBean(expenseService
							.listExpenseCategories(helperClass
									.getSellerIdfromSession(request))));
		} catch (CustomException ce) {
			log.error("deleteExpenseCategory exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by Seller Id : " + sellerId, e);
		}
		log.info("$$$ deleteExpenseCategory Ends : ExpenseController $$$");
		return new ModelAndView("initialsetup/expenseCategoryList", model);
	}

	@RequestMapping(value = "/seller/deleteExpense", method = RequestMethod.GET)
	public ModelAndView deleteExpense(HttpServletRequest request,
			@ModelAttribute("command") ExpenseBean expenseBean,
			BindingResult result) {

		log.info("$$$ deleteExpense Starts : ExpenseController $$$");
		int sellerId = 0;
		int catdelete;
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			catdelete = expenseService.deleteExpense(
					ConverterClass.prepareExpenseModel(expenseBean),
					helperClass.getSellerIdfromSession(request));
			if (catdelete == 0) {
				model.put("error", "Unable to delete Expense  , contact admin");
			}
			model.put("expenses", ConverterClass
					.prepareListofExpenseBean(expenseService
							.listExpenses(helperClass
									.getSellerIdfromSession(request))));
		} catch (CustomException ce) {
			log.error("deleteExpense exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by Seller Id : " + sellerId, e);
		}
		log.info("$$$ deleteExpense Ends : ExpenseController $$$");
		return new ModelAndView("initialsetup/viewExpenseGroup", model);
	}

	@RequestMapping(value = "/seller/editExpenseCategory", method = RequestMethod.GET)
	public ModelAndView editExpenseCategory(HttpServletRequest request,
			@ModelAttribute("command") ExpenseCategoryBean categoryBean,
			BindingResult result) {

		log.info("$$$ editExpenseCategory Starts : ExpenseController $$$");
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			model.put(
					"expenseCategory",
					ConverterClass.prepareExpenseCategoryBean(expenseService
							.getExpenseCategory(categoryBean.getExpcategoryId())));
			model.put("expenseCategories", ConverterClass
					.prepareListofExpenseCategoryBean(expenseService
							.listExpenseCategories(helperClass
									.getSellerIdfromSession(request))));
		} catch (CustomException ce) {
			log.error("editExpenseCategory exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by Seller Id : " + sellerId, e);
		}
		log.info("$$$ editExpenseCategory Ends : ExpenseController $$$");
		return new ModelAndView("addExpenseCategory", model);
	}

	@RequestMapping(value = "/seller/editExpense", method = RequestMethod.GET)
	public ModelAndView editExpense(HttpServletRequest request,
			@ModelAttribute("command") ExpenseBean expenseBean,
			BindingResult result) {

		log.info("$$$ editExpense Starts : ExpenseController $$$");
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, String> catmap = new HashMap<String, String>();
		List<ExpenseCategory> categorylist = null;
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			categorylist = expenseService.listExpenseCategories(helperClass
					.getSellerIdfromSession(request));
			if (categorylist != null) {
				for (ExpenseCategory expcat : categorylist) {
					catmap.put(expcat.getExpcatName(), expcat.getExpcatName());
				}
			}
			model.put("catmap", catmap);
			model.put("expense", ConverterClass
					.prepareExpenseBean(expenseService.getExpense(expenseBean
							.getExpenseId())));
		} catch (CustomException ce) {
			log.error("editExpense exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by Seller Id : " + sellerId, e);
		}
		log.info("$$$ editExpense Ends : ExpenseController $$$");
		return new ModelAndView("initialsetup/addExpense", model);
	}

}