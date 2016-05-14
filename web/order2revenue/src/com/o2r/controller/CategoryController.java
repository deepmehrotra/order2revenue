package com.o2r.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.o2r.bean.TaxCategoryBean;
import com.o2r.helper.ConverterClass;
import com.o2r.helper.CustomException;
import com.o2r.helper.HelperClass;
import com.o2r.helper.SaveContents;
import com.o2r.model.Category;
import com.o2r.model.TaxCategory;
import com.o2r.service.CategoryService;
import com.o2r.service.DownloadService;
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
		log.info("***changeInventorygroup Start****");
		log.debug(" Category ID in changeInventorygroup: " + catId);
		System.out.println(" Category ID in changeInventorygroup: " + catId);
		List<CategoryBean> categorylist = null;
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, String> catageorymap = new HashMap<String, String>();
		Category category = null;
		if (catId != null) {
			try {
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
				log.error(e);
				return new ModelAndView("globalErorPage", model);
			}
			model.put("categorymap", catageorymap);
			model.put("category", ConverterClass.prepareCategoryBean(category));
			model.put("subcategory", ConverterClass
					.prepareListofCategoryBean(category.getSubCategory()));
		}
		log.info("***changeInventorygroup Exit****");
		return new ModelAndView("initialsetup/viewInventorygroup", model);
	}

	@RequestMapping(value = "/seller/deleteInventoryGroup", method = RequestMethod.GET)
	public ModelAndView deleteInventoryGroup(HttpServletRequest request,
			@ModelAttribute("command") CategoryBean categoryBean,
			BindingResult result) {
		log.info("***deleteInventoryGroup Start****");
		log.debug(" Category  to delete :" + categoryBean.getId());
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			if (categoryBean.getId() != 0) {
				int sellerId = helperClass.getSellerIdfromSession(request);
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
			log.error(e);
			return new ModelAndView("globalErorPage", model);
		}
		log.info("***deleteInventoryGroup Exit****");
		return new ModelAndView("initialsetup/inventorygroup", model);
	}

	@RequestMapping(value = "/seller/deleteProductCategory", method = RequestMethod.GET)
	public ModelAndView deleteProductCategory(HttpServletRequest request,
			@RequestParam("parentId") String parentCatId,
			@ModelAttribute("command") CategoryBean categoryBean,
			BindingResult result) {
		log.info("***deleteProductCategory Start****");
		log.debug(" Category  to delete :" + categoryBean.getId());
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			if (categoryBean.getId() != 0) {
				int sellerId = helperClass.getSellerIdfromSession(request);
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
					System.out.println("No of subcat :"
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
			log.error(e);
			return new ModelAndView("globalErorPage", model);
		}

		log.info("***deleteProductCategory Exit****");
		return new ModelAndView("initialsetup/viewInventorygroup", model);
	}

	@RequestMapping(value = "/seller/viewInventorygroup", method = RequestMethod.GET)
	public ModelAndView viewsingleInventorygroup(HttpServletRequest request,
			@ModelAttribute("command") CategoryBean categoryBean,
			BindingResult result) {
		log.info("***viewsingleInventorygroup Start****");
		log.debug(" Category  to view  :" + categoryBean.getId());
System.out.println(" Category  to view  :" + categoryBean.getId());
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			if (categoryBean.getId() != 0) {
				List<CategoryBean> categorylist = ConverterClass
						.prepareListofCategoryBean(categoryService
								.listParentCategories(helperClass
										.getSellerIdfromSession(request)));
				System.out.println(" Parent catgeories : "+categorylist.get(0));
				Map<String, String> catageorymap = new HashMap<String, String>();
				Category category = categoryService.getCategory(categoryBean
						.getId());
				if (category.getSubCategory() != null)
					System.out.println("No of subcat :"
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
			log.error(e);
			return new ModelAndView("globalErorPage", model);
		}
		log.info("***viewsingleInventorygroup Exit****");
		return new ModelAndView("initialsetup/viewInventorygroup", model);
	}

	@RequestMapping(value = "/seller/inventoryGroups", method = RequestMethod.GET)
	public ModelAndView inventorygroups(HttpServletRequest request,
			@ModelAttribute("command") CategoryBean categoryBean,
			BindingResult result) {
		log.info("***inventorygroups Start****");
		int sellerId;
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
					System.out.println(" Setting sum into bean in controller");
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
			log.error(e);
			return new ModelAndView("globalErorPage", model);
		}
		log.info("***inventorygroups Exit****");
		return new ModelAndView("initialsetup/inventorygroup", model);
	}

	@RequestMapping(value = "/seller/saveInventoryGroup", method = RequestMethod.POST)
	public ModelAndView saveInventoryGroup(HttpServletRequest request,
			@ModelAttribute("command") CategoryBean categoryBean,
			BindingResult result) {
		log.info("***saveInventoryGroup Start****");
		log.debug(" category id :" + categoryBean.getCatName());
		Map<String, Object> model = new HashMap<String, Object>();
		try {
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
			log.error(e);
			return new ModelAndView("globalErorPage", model);
		}
		log.info("***saveInventoryGroup Exit****");
		return new ModelAndView("redirect:/seller/inventoryGroups.html");
	}

	@RequestMapping(value = "/seller/saveCatInventory", method = RequestMethod.POST)
	public ModelAndView saveCatInventory(
			@ModelAttribute("command") CategoryBean categoryBean,
			BindingResult result, HttpServletRequest request) {
		log.info("***saveCatInventory Start****");
		log.debug(" ********* parent category name :"
				+ categoryBean.getParentCatName());
		Map<String, Object> model = new HashMap<String, Object>();
		String parentcatid = request.getParameter("parentid");
		if (result.hasErrors()) {
			log.error("Errors in the product category");
			return new ModelAndView(
					"redirect:/seller/viewInventorygroup.html?id="
							+ parentcatid);
		}
		try {
			categoryBean.setSubCategory(true);
			Category category = ConverterClass
					.prepareCategoryModel(categoryBean);
			categoryService.addCategory(category,
					helperClass.getSellerIdfromSession(request));
		} catch (CustomException ce) {
			log.error("saveCatInventory exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			log.error(e);
			return new ModelAndView("globalErorPage", model);
		}
		log.info("***saveCatInventory Exit****");
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
		log.info("***checkInventoryGroup Start****");
		log.debug(" *********checkInventoryGroup name :"
				+ categoryBean.getParentCatName());
		String name = request.getParameter("name");
		try {
			if (name != null && name.length() != 0) {
				Category cat = categoryService.getCategory(name,
						helperClass.getSellerIdfromSession(request));
				if (cat != null)
					return "false";
				else
					return "true";
			}
		} catch (CustomException ce) {
			log.error("saveCatInventory exception : " + ce.toString());
			return "false";
		} catch (Throwable e) {
			log.error(e);
			return "false";
		}
		log.info("***checkInventoryGroup Exit****");
		return "false";
	}

	// Code for Implementation of Tax Categories

	@RequestMapping(value = "/seller/saveTaxCategory", method = RequestMethod.POST)
	public ModelAndView saveTaxCategory(HttpServletRequest request,
			@ModelAttribute("command") TaxCategoryBean taxCategoryBean,
			BindingResult result) {
		log.info("***saveTaxCategory Start****");
		log.debug("category id :" + taxCategoryBean.getTaxCatName());
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			TaxCategory category = ConverterClass
					.prepareTaxCategoryModel(taxCategoryBean);
			taxDetailService.addTaxCategory(category,
					helperClass.getSellerIdfromSession(request));
		} catch (CustomException ce) {
			log.error("saveTaxCategory exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			log.error(e);
			return new ModelAndView("globalErorPage", model);
		}
		log.info("***saveTaxCategory Exit****");
		return new ModelAndView("redirect:/seller/listTaxCategories.html");
	}

	@RequestMapping(value = "/seller/listTaxCategories", method = RequestMethod.GET)
	public ModelAndView listTaxCategory(HttpServletRequest request,
			@ModelAttribute("command") TaxCategoryBean taxCategoryBean,
			BindingResult result) {
		log.info("***listTaxCategory Start****");
		log.debug("category id :" + taxCategoryBean.getTaxCatName());
		Map<String, Object> model = new HashMap<String, Object>();
		try {
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
			log.error(e);
			return new ModelAndView("globalErorPage", model);
		}
		log.info("***listTaxCategory Exit****");
		return new ModelAndView("initialsetup/taxCategoryList", model);
	}

	@RequestMapping(value = "/seller/addTaxCategory", method = RequestMethod.GET)
	public String addTaxCategory(HttpServletRequest request,
			@ModelAttribute("command") TaxCategoryBean categoryBean,
			BindingResult result) {
		return "initialsetup/addTaxCategory";
	}

	@RequestMapping(value = "/seller/checkTaxCategory", method = RequestMethod.GET)
	public @ResponseBody String checkTaxCategory(HttpServletRequest request,
			@ModelAttribute("command") FormBean formBean, BindingResult result) {
		log.info("***checkTaxCategory Start****");
		log.debug("tax category name : " + request.getParameter("name"));
		try {
			TaxCategory taxCategory = taxDetailService.getTaxCategory(
					request.getParameter("name"),
					helperClass.getSellerIdfromSession(request));
			if (taxCategory != null) {
				return "false";
			} else {
				return "true";
			}
		} catch (CustomException ce) {
			log.error("saveTaxCategory exception : " + ce.toString());
			return "false";
		} catch (Throwable e) {
			log.error(e);
			return "false";
		}

	}

}