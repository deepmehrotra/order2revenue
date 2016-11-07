package com.o2r.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.o2r.bean.CategoryBean;
import com.o2r.bean.FormBean;
import com.o2r.bean.PartnerBean;
import com.o2r.bean.TaxCategoryBean;
import com.o2r.helper.ConverterClass;
import com.o2r.helper.CustomException;
import com.o2r.helper.GlobalConstant;
import com.o2r.helper.HelperClass;
import com.o2r.helper.SaveContents;
import com.o2r.model.Category;
import com.o2r.model.Partner;
import com.o2r.model.TaxCategory;
import com.o2r.service.CategoryService;
import com.o2r.service.DownloadService;
import com.o2r.service.PartnerService;
import com.o2r.service.SellerService;
import com.o2r.service.TaxDetailService;

/**
 * @author Deep Mehrotra
 *
 */
@Controller
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private TaxDetailService taxDetailService;
	@Autowired
	private SellerService sellerService;
	@Autowired
	private PartnerService partnerService;
	@Autowired
	private HelperClass helperClass;
	@Resource(name = "downloadService")
	private DownloadService downloadService;
	@Resource(name = "saveContents")
	private SaveContents saveContents;

	static Logger log = Logger.getLogger(CategoryController.class.getName());

	@RequestMapping(value = "/seller/changeInventorygroup", method = RequestMethod.GET)
	public ModelAndView changeInventorygroup(HttpServletRequest request,
			@RequestParam("catId") String catId,
			@ModelAttribute("command") CategoryBean categoryBean,
			BindingResult result) {

		log.info("$$$ changeInventorygroup Starts : CategoryController $$$");
		log.debug(" Category ID in changeInventorygroup: " + catId);
		log.debug(" Category ID in changeInventorygroup: " + catId);
		int sellerId = 0;
		List<CategoryBean> categorylist = null;
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, String> catageorymap = new HashMap<String, String>();
		Category category = null;
		if (catId != null) {
			try {
				sellerId = helperClass.getSellerIdfromSession(request);
				categorylist = ConverterClass
						.prepareListofCategoryBean(categoryService
								.listParentCategories(helperClass
										.getSellerIdfromSession(request)));
				category = categoryService.getCategory(Integer.parseInt(catId));
				for (CategoryBean bean : categorylist) {
					catageorymap.put(String.valueOf(bean.getId()),
							bean.getCatName());

				}
			} catch (CustomException ce) {
				log.error("changeInventorygroup exception : " + ce.toString());
				model.put("errorMessage", ce.getLocalMessage());
				model.put("errorTime", ce.getErrorTime());
				model.put("errorCode", ce.getErrorCode());
				return new ModelAndView("globalErorPage", model);
			} catch (Throwable e) {
				log.error("Failed! by Seller ID : " + sellerId, e);
				e.printStackTrace();
				return new ModelAndView("globalErorPage", model);
			}
			model.put("categorymap", catageorymap);
			model.put("category", ConverterClass.prepareCategoryBean(category));
			model.put("subcategory", ConverterClass
					.prepareListofCategoryBean(category.getSubCategory()));
		}
		log.info("$$$ changeInventorygroup Ends : CategoryController $$$");
		return new ModelAndView("initialsetup/viewInventorygroup", model);
	}

	@RequestMapping(value = "/seller/deleteInventoryGroup", method = RequestMethod.GET)
	public ModelAndView deleteInventoryGroup(HttpServletRequest request,
			@ModelAttribute("command") CategoryBean categoryBean,
			BindingResult result) {

		log.info("$$$ deleteInventoryGroup Starts : CategoryController $$$");
		log.debug(" Category  to delete :" + categoryBean.getId());
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			if (categoryBean.getId() != 0) {
				sellerId = helperClass.getSellerIdfromSession(request);
				int deleted = categoryService.deleteCategory(
						ConverterClass.prepareCategoryModel(categoryBean),
						sellerId);
				if (deleted == 0) {
					model.put("error",
							"Cannot delete Inventory Group,first delete its product categories");
				}
				List<CategoryBean> categorylist = ConverterClass
						.prepareListofCategoryBean(categoryService
								.listParentCategories(sellerId));
				if (categorylist != null && categorylist.size() != 0) {
					for (CategoryBean bean : categorylist) {
						List<Long> sum = categoryService.getSKuCount(
								bean.getCatName(), bean.getId(), sellerId);
						if (sum != null && sum.size() != 0) {
							bean.setSkuCount(sum.get(0));
							bean.setProductCount(sum.get(1));
						}
					}
				}
				model.put("categories", categorylist);
			}
		} catch (CustomException ce) {
			log.error("deleteInventoryGroup exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed! by Seller ID : " + sellerId, e);
			return new ModelAndView("globalErorPage", model);
		}
		log.info("$$$ deleteInventoryGroup Ends : CategoryController $$$");
		return new ModelAndView("initialsetup/inventorygroup", model);
	}

	@RequestMapping(value = "/seller/deleteProductCategory", method = RequestMethod.GET)
	public ModelAndView deleteProductCategory(HttpServletRequest request,
			@RequestParam("parentId") String parentCatId,
			@ModelAttribute("command") CategoryBean categoryBean,
			BindingResult result) {

		log.info("$$$ deleteProductCategory Starts : CategoryController $$$");
		log.debug(" Category  to delete :" + categoryBean.getId());
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			if (categoryBean.getId() != 0) {
				sellerId = helperClass.getSellerIdfromSession(request);
				int deleted = categoryService.deleteCategory(
						ConverterClass.prepareCategoryModel(categoryBean),
						sellerId);

				if (deleted == 0) {
					model.put("error",
							"Cannot delete Product Category ,some error occured");
				}
				List<CategoryBean> categorylist = ConverterClass
						.prepareListofCategoryBean(categoryService
								.listParentCategories(helperClass
										.getSellerIdfromSession(request)));
				Map<String, String> catageorymap = new HashMap<String, String>();
				Category category = categoryService.getCategory(Integer
						.parseInt(parentCatId));
				if (category.getSubCategory() != null)
					log.debug("No of subcat :"
							+ category.getSubCategory().size());
				for (CategoryBean bean : categorylist) {
					catageorymap.put(String.valueOf(bean.getId()),
							bean.getCatName());

				}
				model.put("categorymap", catageorymap);
				model.put("category",
						ConverterClass.prepareCategoryBean(category));
				model.put("subcategory", ConverterClass
						.prepareListofCategoryBean(category.getSubCategory()));
			}
		} catch (CustomException ce) {
			log.error("deleteProductCategory exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed! by Seller ID : " + sellerId, e);
			return new ModelAndView("globalErorPage", model);
		}

		log.info("$$$ deleteProductCategory Ends : CategoryController $$$");
		return new ModelAndView("initialsetup/viewInventorygroup", model);
	}

	@RequestMapping(value = "/seller/viewInventorygroup", method = RequestMethod.GET)
	public ModelAndView viewsingleInventorygroup(HttpServletRequest request,
			@ModelAttribute("command") CategoryBean categoryBean,
			BindingResult result) {

		log.info("$$$ viewsingleInventorygroup Starts : CategoryController $$$");
		log.debug(" Category  to view  :" + categoryBean.getId());
		log.debug(" Category  to view  :" + categoryBean.getId());
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			if (categoryBean.getId() != 0) {
				List<CategoryBean> categorylist = ConverterClass
						.prepareListofCategoryBean(categoryService
								.listParentCategories(helperClass
										.getSellerIdfromSession(request)));
				log.debug(" Parent catgeories : " + categorylist.get(0));
				Map<String, String> catageorymap = new HashMap<String, String>();
				Category category = categoryService.getCategory(categoryBean
						.getId());
				if (category.getSubCategory() != null)
					log.debug("No of subcat :"
							+ category.getSubCategory().size());
				for (CategoryBean bean : categorylist) {
					catageorymap.put(String.valueOf(bean.getId()),
							bean.getCatName());

				}
				model.put("categorymap", catageorymap);
				model.put("category",
						ConverterClass.prepareCategoryBean(category));
				model.put("subcategory", ConverterClass
						.prepareListofCategoryBean(category.getSubCategory()));
			}
		} catch (CustomException ce) {
			log.error("viewsingleInventorygroup exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed! by Seller ID : " + sellerId, e);
			return new ModelAndView("globalErorPage", model);
		}
		log.info("$$$ viewsingleInventorygroup Ends : CategoryController $$$");
		return new ModelAndView("initialsetup/viewInventorygroup", model);
	}

	@RequestMapping(value = "/seller/inventoryGroups", method = RequestMethod.GET)
	public ModelAndView inventorygroups(HttpServletRequest request,
			@ModelAttribute("command") CategoryBean categoryBean,
			BindingResult result) {

		log.info("$$$ inventorygroups Starts : CategoryController $$$");
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			List<CategoryBean> categorylist = ConverterClass
					.prepareListofCategoryBean(categoryService
							.listParentCategories(sellerId));
			if (categorylist != null && categorylist.size() != 0) {
				for (CategoryBean bean : categorylist) {
					List<Long> sum = categoryService.getSKuCount(
							bean.getCatName(), bean.getId(), sellerId);
					log.debug(" Setting sum into bean in controller");
					if (sum != null && sum.size() != 0) {
						bean.setSkuCount(sum.get(0));
						bean.setProductCount(sum.get(1));
						bean.setOpeningStock(sum.get(2));
					}
				}
			}

			model.put("categories", categorylist);
		} catch (CustomException ce) {
			log.error("inventorygroups exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed! by Seller ID : " + sellerId, e);
			return new ModelAndView("globalErorPage", model);
		}
		log.info("$$$ inventorygroups Ends : CategoryController $$$");
		return new ModelAndView("initialsetup/inventorygroup", model);
	}

	@RequestMapping(value = "/seller/saveInventoryGroup", method = RequestMethod.POST)
	public ModelAndView saveInventoryGroup(HttpServletRequest request,
			@ModelAttribute("command") CategoryBean categoryBean,
			BindingResult result) {

		log.info("$$$ saveInventoryGroup Starts : CategoryController $$$");
		log.debug(" category id :" + categoryBean.getCatName());
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			if (result.hasErrors()) {
				return new ModelAndView("initialsetup/addInventoryGroup");
			}
			categoryBean.setSubCategory(false);
			Category category = ConverterClass
					.prepareCategoryModel(categoryBean);
			categoryService.addCategory(category,
					helperClass.getSellerIdfromSession(request));
		} catch (CustomException ce) {
			log.error("saveInventoryGroup exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed! by Seller ID : " + sellerId, e);
			return new ModelAndView("globalErorPage", model);
		}
		log.info("$$$ saveInventoryGroup Ends : CategoryController $$$");
		return new ModelAndView("redirect:/seller/inventoryGroups.html");
	}

	@RequestMapping(value = "/seller/addCatInventory", method = RequestMethod.GET)
	public ModelAndView addCatInventory(
			@ModelAttribute("command") CategoryBean categoryBean,
			BindingResult result, HttpServletRequest request) {

		log.info("$$$ addCatInventory Starts : CategoryController $$$");

		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			String parentcatid = request.getParameter("parentid");

			List<CategoryBean> categorylist = ConverterClass
					.prepareListofCategoryBean(categoryService
							.listParentCategories(sellerId));

			List<Partner> partnerList = partnerService.listPartners(helperClass
					.getSellerIdfromSession(request));
			List<String> partnerNameList = new ArrayList<String>();
			if (partnerList != null && !partnerList.isEmpty()) {
				for (Partner bean : partnerList) {
					partnerNameList.add(bean.getPcName());
				}
			}

			List<String> partnerCategoryList = new ArrayList<String>();
			partnerCategoryList = categoryService.listPartnerCategories();

			model.put("parentcatid", parentcatid);
			model.put("categories", categorylist);
			model.put("partnerCategories", partnerCategoryList);
			model.put("partnerList", partnerNameList);
		} catch (CustomException ce) {
			log.error("saveInventoryGroup exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed! by Seller ID : " + sellerId, e);
			return new ModelAndView("globalErorPage", model);
		}
		log.info("$$$ saveInventoryGroup Ends : CategoryController $$$");
		return new ModelAndView("initialsetup/addCatInventorygroup", model);
	}

	@RequestMapping(value = "/seller/editCatInventory", method = RequestMethod.GET)
	public ModelAndView editCatInventory(
			@ModelAttribute("command") CategoryBean categoryBean,
			BindingResult result, HttpServletRequest request) {

		log.info("$$$ editCatInventory Starts : CategoryController $$$");

		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			int catid = Integer.parseInt(request.getParameter("catId"));
			int parentcatid = 0;

			List<String> partnerCatRefList = new ArrayList<String>();
			CategoryBean category = ConverterClass
					.prepareCategoryBean(categoryService.getCategory(catid));
			if (category.getPartnerCatRef() != null
					&& !category.getPartnerCatRef().isEmpty()) {
				partnerCatRefList = Arrays.asList(category.getPartnerCatRef()
						.split(","));
			}

			List<CategoryBean> categorylist = ConverterClass
					.prepareListofCategoryBean(categoryService
							.listParentCategories(sellerId));
			for (CategoryBean bean : categorylist) {
				if (bean.getCatName().equalsIgnoreCase(
						category.getParentCatName())) {
					parentcatid = bean.getId();
				}
			}

			List<Partner> partnerList = partnerService.listPartners(helperClass
					.getSellerIdfromSession(request));
			List<String> partnerNameList = new ArrayList<String>();
			if (partnerList != null && !partnerList.isEmpty()) {
				for (Partner bean : partnerList) {
					partnerNameList.add(bean.getPcName());
				}
			}

			List<String> partnerCategoryList = new ArrayList<String>();
			partnerCategoryList = categoryService.listPartnerCategories();

			model.put("category", category);
			model.put("parentcatid", parentcatid);
			model.put("categories", categorylist);
			model.put("partnerList", partnerNameList);
			model.put("partnerCatRefList", partnerCatRefList);
			model.put("partnerCategories", partnerCategoryList);
		} catch (CustomException ce) {
			log.error("saveInventoryGroup exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed! by Seller ID : " + sellerId, e);
			return new ModelAndView("globalErorPage", model);
		}
		log.info("$$$ saveInventoryGroup Ends : CategoryController $$$");
		return new ModelAndView("initialsetup/addCatInventorygroup", model);
	}

	@RequestMapping(value = "/seller/saveCatInventory", method = RequestMethod.POST)
	public ModelAndView saveCatInventory(
			@ModelAttribute("command") CategoryBean categoryBean,
			BindingResult result, HttpServletRequest request) {

		log.info("$$$ saveCatInventory Starts : CategoryController $$$");
		log.debug(" ********* parent category name :"
				+ categoryBean.getParentCatName());
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		String parentcatid = request.getParameter("parentcatid");
		if (result.hasErrors()) {
			log.error("Errors in the product category");
			return new ModelAndView(
					"redirect:/seller/viewInventorygroup.html?id="
							+ parentcatid);
		}
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			Category category;
			if (categoryBean.getId() > 0) {
				category = categoryService.getCategory(categoryBean.getId());
			} else {
				categoryBean.setSubCategory(true);
				category = ConverterClass.prepareCategoryModel(categoryBean);
			}

			List<String> partnerCatRefList = new ArrayList<String>();
			String tempPartnerCat = "";

			Map<String, String[]> parameters = request.getParameterMap();
			for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
				if (entry.getKey().contains("partnerCatRef-")) {
					String paramIndex = entry.getKey().substring(
							entry.getKey().indexOf('-') + 1);
					String pcName = parameters.get("partnerName-" + paramIndex)[0];
					String partnerCatRef = parameters.get("partnerCatRef-"
							+ paramIndex)[0].trim();

					List<String> tempPartnerCatRefList = new ArrayList<String>();

					if (partnerCatRef != null && !partnerCatRef.isEmpty()) {
						if (partnerCatRef.contains(",")) {
							tempPartnerCatRefList = Arrays.asList(partnerCatRef
									.split(","));
							for (String tempPartnerCatRef : tempPartnerCatRefList) {
								if (!tempPartnerCatRef.trim().isEmpty()) {
									tempPartnerCat = pcName + "_"
											+ tempPartnerCatRef;
									Category tempCat = categoryService
											.getSubCategory(tempPartnerCat,
													sellerId);
									if (tempCat == null) {
										partnerCatRefList
												.add(GlobalConstant.orderUniqueSymbol
														+ tempPartnerCat
														+ GlobalConstant.orderUniqueSymbol);
									}
								}
							}
						} else {
							tempPartnerCat = pcName + "_" + partnerCatRef;
							Category tempCat = categoryService.getSubCategory(
									tempPartnerCat, sellerId);
							if (tempCat == null) {
								partnerCatRefList
										.add(GlobalConstant.orderUniqueSymbol
												+ tempPartnerCat
												+ GlobalConstant.orderUniqueSymbol);
							}
						}
					}
				}
			}

			String[] catList = request.getParameterValues("multiSku");
			if (catList != null && catList.length != 0) {
				for (int i = 0; i < catList.length; i++) {
					tempPartnerCat = catList[i];
					partnerCatRefList
							.add(GlobalConstant.orderUniqueSymbol
									+ tempPartnerCat
									+ GlobalConstant.orderUniqueSymbol);
				}
			}

			catList = request.getParameterValues("multiSku1");
			if (catList != null && catList.length != 0) {
				for (int i = 0; i < catList.length; i++) {
					tempPartnerCat = catList[i];
					Category tempCat = categoryService.getSubCategory(
							tempPartnerCat, sellerId);
					if (tempCat == null) {
						partnerCatRefList.add(GlobalConstant.orderUniqueSymbol
								+ tempPartnerCat
								+ GlobalConstant.orderUniqueSymbol);
					}
				}
			}

			if (partnerCatRefList != null && !partnerCatRefList.isEmpty()) {
				category.setPartnerCatRef(StringUtils.join(partnerCatRefList,
						','));
			}

			if (category.getCategoryId() > 0) {
				categoryService.addPartnerCatRef(category, sellerId);
			} else {
				categoryService.addCategory(category,
						helperClass.getSellerIdfromSession(request));
			}
		} catch (CustomException ce) {
			log.error("saveCatInventory exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed! by Seller ID : " + sellerId, e);
			return new ModelAndView("globalErorPage", model);
		}
		log.info("$$$ saveCatInventory Ends : CategoryController $$$");
		return new ModelAndView("redirect:/seller/viewInventorygroup.html?id="
				+ parentcatid);
	}

	@RequestMapping(value = "/seller/addInventoryGroup", method = RequestMethod.GET)
	public ModelAndView addInventoryGroup(HttpServletRequest request,
			@ModelAttribute("command") CategoryBean categoryBean,
			BindingResult result) {
		return new ModelAndView("initialsetup/addInventoryGroup");
	}

	@RequestMapping(value = "/seller/checkInventoryGroup", method = RequestMethod.GET)
	public @ResponseBody String checkInventoryGroup(HttpServletRequest request,
			@ModelAttribute("command") CategoryBean categoryBean,
			BindingResult result) {

		log.info("$$$ checkInventoryGroup Starts : CategoryController $$$");
		int sellerId = 0;
		log.debug(" *********checkInventoryGroup name :"
				+ categoryBean.getParentCatName());
		String name = request.getParameter("name");
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			if (name != null && name.length() != 0) {
				Category cat = categoryService.getCategory(name,
						helperClass.getSellerIdfromSession(request));
				if (cat != null)
					return "false";
				else
					return "true";
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by Seller ID : " + sellerId, e);
			return "false";
		}
		log.info("$$$ checkInventoryGroup Ends : CategoryController $$$");
		return "false";
	}

	// Code for Implementation of Tax Categories

	@RequestMapping(value = "/seller/saveTaxCategory", method = RequestMethod.POST)
	public ModelAndView saveTaxCategory(HttpServletRequest request,
			@ModelAttribute("command") TaxCategoryBean taxCategoryBean,
			BindingResult result) {

		log.info("$$$ saveTaxCategory Starts : CategoryController $$$");
		log.debug("category id :" + taxCategoryBean.getTaxCatName());
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			TaxCategory category = ConverterClass
					.prepareTaxCategoryModel(taxCategoryBean);
			List<Category> categoryList = new ArrayList<Category>();

			if (category.getTaxCatId() > 0) {
				TaxCategory tmpCat = taxDetailService.getTaxCategory(category
						.getTaxCatId());
				if (tmpCat != null) {
					/*
					 * categoryList = tmpCat.getProductCategoryCST(); if
					 * (categoryList != null && !categoryList.isEmpty()) {
					 * Iterator<Category> categoryListIterator =
					 * categoryList.iterator(); while
					 * (categoryListIterator.hasNext()) { Category productCat =
					 * categoryListIterator.next(); if
					 * (category.getTaxCatType().equalsIgnoreCase("LST")) {
					 * productCat.setLST(null); } else {
					 * productCat.setCST(null); } } }
					 */
					taxDetailService.removeProductMapping(tmpCat.getTaxCatId(),
							sellerId);
				}
			}

			String[] catList = request.getParameterValues("multiSku");
			if (catList != null && catList.length != 0) {
				for (int i = 0; i < catList.length; i++) {
					Category productCat = categoryService.getSubCategory(
							catList[i], sellerId);
					if (category.getTaxCatType().equalsIgnoreCase("LST")) {
						productCat.setLST(category);
					} else {
						productCat.setCST(category);
					}
					categoryList.add(productCat);
				}
			}
			if (category.getTaxCatType().equalsIgnoreCase("LST")) {
				category.setProductCategoryLST(categoryList);
			} else {
				category.setProductCategoryCST(categoryList);
			}
			taxDetailService.addTaxCategory(category,
					helperClass.getSellerIdfromSession(request));
		} catch (CustomException ce) {
			log.error("saveTaxCategory exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed! by Seller ID : " + sellerId, e);
			return new ModelAndView("globalErorPage", model);
		}
		log.info("$$$ saveTaxCategory Ends : CategoryController $$$");
		return new ModelAndView("redirect:/seller/listTaxCategories.html");
	}

	@RequestMapping(value = "/seller/listTaxCategories", method = RequestMethod.GET)
	public ModelAndView listTaxCategory(HttpServletRequest request,
			@ModelAttribute("command") TaxCategoryBean taxCategoryBean,
			BindingResult result) {

		log.info("$$$ listTaxCategory Starts : CategoryController $$$");
		log.debug("category id :" + taxCategoryBean.getTaxCatName());
		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			model.put("taxCategories", ConverterClass
					.prepareListofTaxCategoryBean(taxDetailService
							.listTaxCategories(helperClass
									.getSellerIdfromSession(request))));
		} catch (CustomException ce) {
			log.error("saveTaxCategory exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed! by Seller ID : " + sellerId, e);
			return new ModelAndView("globalErorPage", model);
		}
		log.info("$$$ listTaxCategory Ends : CategoryController $$$");
		return new ModelAndView("initialsetup/taxCategoryList", model);
	}

	@RequestMapping(value = "/seller/addTaxCategory", method = RequestMethod.GET)
	public ModelAndView addTaxCategory(HttpServletRequest request,
			@ModelAttribute("command") TaxCategoryBean categoryBean,
			BindingResult result) {

		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		List<String> categoryList = new ArrayList<String>();
		List<String> categoryLSTList = new ArrayList<String>();
		List<String> categoryCSTList = new ArrayList<String>();
		List<Category> categoryObjects = null;
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			categoryObjects = categoryService.listCategories(sellerId);
			if (categoryObjects != null && categoryObjects.size() > 0) {
				for (Category cat : categoryObjects) {
					categoryList.add(cat.getCatName());
					if (cat.getCST() == null) {
						categoryCSTList.add(cat.getCatName());
					}
					if (cat.getLST() == null) {
						categoryLSTList.add(cat.getCatName());
					}
				}
			}
			model.put("categoryList", categoryList);
			model.put("categoryLSTList", categoryLSTList);
			model.put("categoryCSTList", categoryCSTList);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by Seller ID : " + sellerId, e);
		}
		return new ModelAndView("initialsetup/addTaxCategory", model);
	}

	@RequestMapping(value = "/seller/editTaxCategory", method = RequestMethod.GET)
	public ModelAndView editTaxCategory(HttpServletRequest request,
			@ModelAttribute("command") TaxCategoryBean categoryBean,
			@RequestParam(value = "taxCategoryId") int taxCategoryId,
			BindingResult result) {

		int sellerId = 0;
		Map<String, Object> model = new HashMap<String, Object>();
		List<String> categoryList = new ArrayList<String>();
		List<String> categoryLSTList = new ArrayList<String>();
		List<String> categoryCSTList = new ArrayList<String>();
		List<String> prodCategoryList = new ArrayList<String>();
		List<Category> prodCategory = null;
		List<Category> categoryObjects = null;
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			categoryObjects = categoryService.listCategories(sellerId);
			if (categoryObjects != null && categoryObjects.size() > 0) {
				for (Category cat : categoryObjects) {
					categoryList.add(cat.getCatName());
					if (cat.getCST() == null) {
						categoryCSTList.add(cat.getCatName());
					}
					if (cat.getLST() == null) {
						categoryLSTList.add(cat.getCatName());
					}
				}
			}

			TaxCategoryBean taxCategory = ConverterClass
					.prepareTaxCategoryBean(taxDetailService
							.getTaxCategory(taxCategoryId));
			if (taxCategory.getTaxCatType() != null) {
				if (taxCategory.getTaxCatType().equalsIgnoreCase("LST")) {
					prodCategory = taxCategory.getProductCategoryLST();
				} else {
					prodCategory = taxCategory.getProductCategoryCST();
				}
			}
			if (prodCategory != null && prodCategory.size() > 0) {
				for (Category cat : prodCategory) {
					prodCategoryList.add(cat.getCatName());
				}
			}
			categoryList.removeAll(prodCategoryList);
			model.put("categoryList", categoryList);
			model.put("prodCategoryList", prodCategoryList);
			model.put("categoryLSTList", categoryLSTList);
			model.put("categoryCSTList", categoryCSTList);
			model.put("taxCategory", taxCategory);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by Seller ID : " + sellerId, e);
		}
		return new ModelAndView("initialsetup/addTaxCategory", model);
	}

	@RequestMapping(value = "/seller/checkTaxCategory", method = RequestMethod.GET)
	public @ResponseBody String checkTaxCategory(HttpServletRequest request,
			@ModelAttribute("command") FormBean formBean, BindingResult result) {

		log.info("$$$ checkTaxCategory Starts : CategoryController $$$");
		log.debug("tax category name : " + request.getParameter("name"));
		int sellerId = 0;
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			TaxCategory taxCategory = taxDetailService.getTaxCategory(
					request.getParameter("name"),
					helperClass.getSellerIdfromSession(request));
			if (taxCategory != null) {
				log.info("$$$ checkTaxCategory Ends : CategoryController $$$");
				return "false";
			} else {
				log.info("$$$ checkTaxCategory Ends : CategoryController $$$");
				return "true";
			}
		} catch (CustomException ce) {
			log.error("saveTaxCategory exception : ", ce);
			return "false";
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed! by Seller ID : " + sellerId, e);
			return "false";
		}

	}

}