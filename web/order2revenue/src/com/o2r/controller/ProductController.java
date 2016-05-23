package com.o2r.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.o2r.bean.ProductBean;
import com.o2r.bean.ProductConfigBean;
import com.o2r.helper.ConverterClass;
import com.o2r.helper.CustomException;
import com.o2r.helper.DateDeserializer;
import com.o2r.helper.DateSerializer;
import com.o2r.helper.FileUploadForm;
import com.o2r.helper.HelperClass;
import com.o2r.helper.SaveContents;
import com.o2r.helper.ValidateUpload;
import com.o2r.model.Category;
import com.o2r.model.Partner;
import com.o2r.model.Product;
import com.o2r.model.ProductConfig;
import com.o2r.service.CategoryService;
import com.o2r.service.DownloadService;
import com.o2r.service.OrderService;
import com.o2r.service.PartnerService;
import com.o2r.service.ProductService;

/**
 * @author Deep Mehrotra
 *
 */
@Controller
public class ProductController {

	@Autowired
	private ProductService productService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private PartnerService partnerService;
	@Resource(name = "downloadService")
	private DownloadService downloadService;
	@Resource(name = "saveContents")
	private SaveContents saveContents;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private HelperClass helperClass;

	static Logger log = Logger.getLogger(ProductController.class.getName());

	private static final String UPLOAD_DIR = "upload";

	@RequestMapping(value = "/seller/searchProduct", method = RequestMethod.POST)
	public ModelAndView searchProduct(HttpServletRequest request,
			@ModelAttribute("command") ProductBean productBean,
			BindingResult result) {

		log.info("$$$ searchProduct Starts : ProductController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		List<ProductBean> productList = new ArrayList<>();
		int sellerId;
		String skuCode = request.getParameter("skuCode");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String searchProduct = request.getParameter("searchProduct");
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			if (searchProduct != null && searchProduct.equals("SKU")
					&& skuCode != null) {
				productBean = ConverterClass.prepareProductBean(productService
						.getProduct(skuCode, sellerId));
				productList.add(productBean);
			} else if (searchProduct != null && startDate != null
					&& endDate != null) {

				productList = ConverterClass
						.prepareListofProductBean(productService
								.getProductwithCreatedDate(new Date(startDate),
										new Date(endDate), sellerId));
			}
		} catch (CustomException ce) {
			log.error("searchProduct exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed!",e);
		}
		request.getSession().setAttribute("productSearchObject", productList);
		log.info("$$$ searchProduct Ends : ProductController $$$");
		return new ModelAndView("redirect:/seller/Product.html");

	}

	@RequestMapping(value = "/seller/Product", method = RequestMethod.GET)
	public ModelAndView productList(HttpServletRequest request,
			@ModelAttribute("command") ProductBean productBean,
			BindingResult result) {

		log.info("$$$ productList Starts : ProductController $$$");
		List<Product> products = null;
		Map<String, Object> model = new HashMap<String, Object>();
		List<List<ProductConfig>> productConfigs = new ArrayList<List<ProductConfig>>();

		try {
			Object obj = request.getSession().getAttribute(
					"productSearchObject");
			if (obj != null) {
				log.debug(" Getting list from session" + obj);
				model.put("productList", obj);
				request.getSession().removeAttribute("productSearchObject");
			} else {
				int pageNo = request.getParameter("page") != null ? Integer
						.parseInt(request.getParameter("page")) : 0;
				model.put("productList", ConverterClass
						.prepareListofProductBean(productService.listProducts(
								helperClass.getSellerIdfromSession(request),
								pageNo)));
			}
			products = productService.listProducts(helperClass
					.getSellerIdfromSession(request));
			if (products != null) {
				for (Product product : products) {
					productConfigs.add(product.getProductConfig());
				}
				model.put("productConfigList", productConfigs);
			}
		} catch (CustomException ce) {
			log.error("productList exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed!",e);
		}

		log.info("$$$ productList Ends : ProductController $$$");
		return new ModelAndView("initialsetup/Product", model);

	}

	@RequestMapping(value = "/seller/ProductMapping", method = RequestMethod.GET)
	public ModelAndView productConfigList(HttpServletRequest request,
			@ModelAttribute("command") ProductBean productBean,
			BindingResult result) {

		log.info("$$$ productConfigList Starts : ProductController $$$");
		List<Product> products = null;
		Map<String, Object> model = new HashMap<String, Object>();
		List<ProductConfig> productConfigs = new ArrayList<ProductConfig>();

		try {
			Object obj = request.getSession().getAttribute(
					"productSearchObject");
			if (obj != null) {
				System.out.println(" Getting list from session" + obj);
				model.put("productList", obj);
				request.getSession().removeAttribute("productSearchObject");
			} else {
				int pageNo = request.getParameter("page") != null ? Integer
						.parseInt(request.getParameter("page")) : 0;
				model.put("productList", ConverterClass
						.prepareListofProductBean(productService.listProducts(
								helperClass.getSellerIdfromSession(request),
								pageNo)));
			}
			products = productService.listProducts(helperClass
					.getSellerIdfromSession(request));
			if (products != null) {
				for (Product product : products) {
					productConfigs.addAll(product.getProductConfig());
				}
				model.put("productConfigList", productConfigs);
			}
		} catch (CustomException ce) {
			log.error("productList exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed!",e);
		}

		log.info("$$$ productConfigList Ends : ProductController $$$");
		return new ModelAndView("initialsetup/ProductMapping", model);

	}

	@RequestMapping(value = "/seller/saveUpdateInventory", method = RequestMethod.POST)
	public ModelAndView saveDeleteProduct(HttpServletRequest request,
			@ModelAttribute("command") ProductBean productBean,
			BindingResult result) {
		
		log.info("$$$ productConfigList Starts : ProductController $$$");
		if (productBean.getProductSkuCode() != null) {
			productBean.setProductDate(new Date());
			int productId = request.getParameter("productId") != null ? Integer
					.parseInt(request.getParameter("productId")) : 0;
			String productSkuCode = productBean.getProductSkuCode();
			int quantityToAdd = (request.getParameter("quantityToAdd") != null && request
					.getParameter("quantityToAdd").toString().length() != 0) ? Integer
					.parseInt(request.getParameter("quantityToAdd")) : 0;
			int quantityToSubstract = (request
					.getParameter("quantityToSubtract") != null && request
					.getParameter("quantityToSubtract").toString().length() != 0) ? Integer
					.parseInt(request.getParameter("quantityToSubtract")) : 0;
			int sellerId = 0;
			try {
				sellerId = helperClass.getSellerIdfromSession(request);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Failed!", e);
			}

			try {
				productService.updateInventory(productSkuCode, 0,
						quantityToAdd, quantityToSubstract, true, sellerId);
			} catch (CustomException e) {
				e.printStackTrace();
				log.error("Failed!",e);
			}

		}
		log.info("$$$ productConfigList Ends : ProductController $$$");
		return new ModelAndView("redirect:/seller/Product.html");
	}

	@RequestMapping(value = "/seller/saveProduct", method = RequestMethod.POST)
	public ModelAndView saveProduct(HttpServletRequest request,
			@ModelAttribute("command") ProductBean productBean,
			BindingResult result) {

		log.info("$$$ saveProduct Starts : ProductController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		
		log.debug(" Product Id :" + productBean.getProductId());
		log.debug(" Product SKU :" + productBean.getProductSkuCode());
		log.debug(" Product category  :"+ productBean.getCategoryName());

		try {
			if (productBean.getProductSkuCode() != null) {
				productBean.setProductDate(new Date());
				productBean.setVolume(productBean.getHeight()
						* productBean.getLength() * productBean.getBreadth());
				productBean.setVolWeight(productBean.getVolume() / 5);
				Product product = ConverterClass
						.prepareProductModel(productBean);
				productService.addProduct(product,
						helperClass.getSellerIdfromSession(request));
			}
		} catch (CustomException ce) {
			log.error("saveProduct exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed!",e);
			log.equals(e);
		}
		log.info("$$$ saveProduct Ends : ProductController $$$");
		return new ModelAndView("redirect:/seller/Product.html");
	}

	/* Product Config */

	@RequestMapping(value = "/seller/saveProductConfig", method = RequestMethod.POST)
	public ModelAndView saveProductConfig(HttpServletRequest request,
			@ModelAttribute("command") ProductConfigBean productConfigBean,
			BindingResult result) {

		log.info("$$$ saveProductConfig Starts : ProductController $$$");
		try {
			ProductConfig productConfig = ConverterClass
					.prepareProductConfigModel(productConfigBean);
			productService.addProductConfig(productConfig,
					helperClass.getSellerIdfromSession(request));

			/*
			 * } catch (CustomException ce) {
			 * log.error("saveProduct exception : " + ce.toString());
			 * model.put("errorMessage", ce.getLocalMessage());
			 * model.put("errorTime", ce.getErrorTime()); model.put("errorCode",
			 * ce.getErrorCode()); return new ModelAndView("globalErorPage",
			 * model);
			 */
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed!", e);
		}
		log.info("$$$ saveProductConfig Ends : ProductController $$$");
		return new ModelAndView("redirect:/seller/ProductMapping.html");
	}

	/* Product Config */

	@RequestMapping(value = "/seller/addProductConfig", method = RequestMethod.GET)
	public ModelAndView addProductConfig(HttpServletRequest request,
			@ModelAttribute("command") ProductConfigBean productConfigBean,
			BindingResult result) {

		log.info("$$$ addProductConfig Starts : ProductController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, String> productSkuCodeMap = new LinkedHashMap<>();
		try {
			List<Product> products = productService.listProducts(helperClass
					.getSellerIdfromSession(request));

			if (products != null && products.size() != 0) {
				for (Product product : products) {
					productSkuCodeMap.put(product.getProductSkuCode(),
							product.getProductSkuCode());
				}
			}

			Map<String, String> partnermap = new HashMap<String, String>();
			List<Partner> partnerlist = partnerService.listPartners(helperClass
					.getSellerIdfromSession(request));
			if (partnerlist != null && partnerlist.size() != 0) {
				for (Partner bean : partnerlist) {
					partnermap.put(bean.getPcName(), bean.getPcName());

				}
			}
			/*
			 * } catch (CustomException ce) {
			 * log.error("addProduct exception : " + ce.toString());
			 * model.put("errorMessage", ce.getLocalMessage());
			 * model.put("errorTime", ce.getErrorTime()); model.put("errorCode",
			 * ce.getErrorCode()); return new ModelAndView("globalErorPage",
			 * model);
			 */
			model.put("productSkuMap", productSkuCodeMap);
			model.put("partnermap", partnermap);
			log.info("$$$ addProductConfig Ends : ProductController $$$");
			return new ModelAndView("initialsetup/addProductConfig", model);
		} catch (Throwable e) {
			log.error("Failed!",e);
			e.printStackTrace();
			return new ModelAndView("globalErorPage", model);
		}
	}

	@RequestMapping(value = "/seller/addProduct", method = RequestMethod.GET)
	public ModelAndView addProduct(HttpServletRequest request,
			@ModelAttribute("command") ProductBean productBean,
			BindingResult result) {

		log.info("$$$ addProduct Starts : ProductController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, String> categoryMap = new LinkedHashMap<>();
		try {
			List<Category> categoryList = categoryService
					.listCategories(helperClass.getSellerIdfromSession(request));
			if (categoryList != null && categoryList.size() != 0) {
				for (Category category : categoryList) {
					categoryMap.put(category.getCatName(),
							category.getCatName());

				}
			}
		} catch (CustomException ce) {
			log.error("addProduct exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed!",e);
			return new ModelAndView("globalErorPage", model);
		}
		model.put("categoryMap", categoryMap);
		log.info("$$$ addProduct Ends : ProductController $$$");
		return new ModelAndView("initialsetup/addProduct", model);
	}

	@RequestMapping(value = "/seller/editProduct", method = RequestMethod.GET)
	public ModelAndView editProduct(HttpServletRequest request,
			@ModelAttribute("command") ProductBean productBean,
			BindingResult result) {
		
		log.info("$$$ editProduct Starts : ProductController $$$");
		Product product = null;
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			if (request.getParameter("id") != null) {
				int productId = Integer.parseInt(request.getParameter("id"));
				product = productService.getProduct(productId);
			}
		} catch (CustomException ce) {
			log.error("editProduct exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		}
		if (product != null)
			model.put("product", product);
		log.info("$$$ editProduct Ends : ProductController $$$");
		return new ModelAndView("initialsetup/addProduct", model);
	}

	@RequestMapping(value = "/seller/saveProductJson", method = RequestMethod.POST)
	public @ResponseBody String saveProductJson(HttpServletRequest request) {
		
		log.info("$$$ saveProductJson Starts : ProductController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Product product = new Product();
		log.debug("Product id " + request.getParameter("productId"));
		int productId = 0;
		try {
			if (request.getParameter("productId") != null
					&& request.getParameter("productId").toString().length() != 0) {
				productId = Integer.parseInt(request.getParameter("productId"));
			}
			String productSkuCode = request.getParameter("productSkuCode") != null ? request
					.getParameter("productSkuCode") : "";
			String productName = request.getParameter("productName") != null ? request
					.getParameter("productName") : "";
			String categoryName = request.getParameter("categoryName") != null ? request
					.getParameter("categoryName") : "";
			float productPrice = request.getParameter("productPrice") != null ? Float
					.valueOf(request.getParameter("productPrice")) : 0;
			long quantity = request.getParameter("quantity") != null ? Long
					.parseLong(request.getParameter("quantity")) : 0;
			long threholdLimit = request.getParameter("threholdLimit") != null ? Long
					.parseLong(request.getParameter("threholdLimit")) : 0;
			String channelSKU = request.getParameter("channelSKU") != null ? request
					.getParameter("channelSKU") : "";
			float productLength = request.getParameter("length") != null ? Float
					.valueOf(request.getParameter("length")) : 0;
			float productBreadth = request.getParameter("breadth") != null ? Float
					.valueOf(request.getParameter("breadth")) : 0;
			float productHeight = request.getParameter("height") != null ? Float
					.valueOf(request.getParameter("height")) : 0;
			float productDeadweight = request.getParameter("deadWeight") != null ? Float
					.valueOf(request.getParameter("deadWeight")) : 0;

			if (productId != 0) {
				product.setProductId(productId);

			} else {
				product.setProductDate(new Date());
				product.setCategoryName(categoryName);
			}

			product.setProductSkuCode(productSkuCode);
			product.setProductName(productName);
			product.setProductPrice(productPrice);
			product.setQuantity(quantity);
			product.setThreholdLimit(threholdLimit);
			product.setChannelSKU(channelSKU);
			product.setLength(productLength);
			product.setBreadth(productBreadth);
			product.setHeight(productHeight);
			product.setDeadWeight(productDeadweight);
			productService.addProduct(product,
					helperClass.getSellerIdfromSession(request));

		} catch (CustomException ce) {
			log.error("saveProduct exception : " + ce.toString());
			model.put("error", ce.getLocalMessage());
			String errors = gson.toJson(model);
			return errors;
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed!",e);
		}

		model.put("Result", "OK");
		model.put("Record", ConverterClass.prepareProductBean(product));
		String jsonArray = gson.toJson(model);
		log.info("$$$ saveProductJson Ends : ProductController $$$");
		return jsonArray;
	}

	@RequestMapping(value = "/seller/download/Product/xls", method = RequestMethod.GET)
	public void getXLS(HttpServletResponse response)
			throws ClassNotFoundException {

		// Delegate to downloadService. Make sure to pass an instance of
		// HttpServletResponse
		downloadService.downloadProductXLS(response);
	}

	@RequestMapping(value = "/seller/download/Inventoryxls", method = RequestMethod.GET)
	public void getInventoryXLS(HttpServletResponse response)
			throws ClassNotFoundException {

		// Delegate to downloadService. Make sure to pass an instance of
		// HttpServletResponse
		downloadService.downloadInventoryXLS(response);
	}

	
	@RequestMapping(value = "/seller/Productsheet", method = RequestMethod.GET)
	public String displayForm() {

		return "product_upload_form";
	}

	@RequestMapping(value = "/seller/Inventorysheet", method = RequestMethod.GET)
	public String InventoryForm() {

		return "dailyactivities/inventory_upload_form";
	}

	@RequestMapping(value = "/seller/inventoryList", method = RequestMethod.GET)
	public String redirectIventoryPage() {

		return "dailyactivities/productInventories";
	}

	@RequestMapping(value = "/seller/listProductJson", method = RequestMethod.POST)
	public @ResponseBody String listProductsJSON(HttpServletRequest request) {

		log.info("$$$ listProductsJSON Starts : ProductController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
		gsonBuilder.registerTypeAdapter(Date.class, new DateSerializer());

		Gson gson = gsonBuilder.setPrettyPrinting().create();
		try {
			model.put("Records", ConverterClass
					.prepareListofProductBean(productService.listProducts(
							helperClass.getSellerIdfromSession(request), 0)));
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed!",e);
		}
		model.put("Result", "OK");
		String jsonArray = gson.toJson(model);
		log.info("$$$ listProductsJSON Ends : ProductController $$$");
		return jsonArray;
	}

	@RequestMapping(value = "/seller/showInventory", method = RequestMethod.POST)
	public @ResponseBody String inventoryList(HttpServletRequest request) {

		log.info("$$$ inventoryList Starts : ProductController $$$");
		Map<String, Object> model = new HashMap<String, Object>();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String action = request.getParameter("action");
		int sellerId;

		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			if (action != null && action.equals("list")) {
				model.put("Records",
						ConverterClass.prepareListofProductBean(productService
								.listProducts(helperClass
										.getSellerIdfromSession(request), 0)));

			} else {
				log.debug(" Getting inventory update subtract value : : "
								+ request.getParameter("quantityToSubstract"));
				int productId = request.getParameter("productId") != null ? Integer
						.parseInt(request.getParameter("productId")) : 0;
				String productSkuCode = request.getParameter("productSkuCode") != null ? request
						.getParameter("productSkuCode") : "";
				int currentInventory = request.getParameter("currentInventory") != null ? Integer
						.parseInt(request.getParameter("currentInventory")) : 0;
				int quantityToAdd = (request.getParameter("quantityToAdd") != null && request
						.getParameter("quantityToAdd").toString().length() != 0) ? Integer
						.parseInt(request.getParameter("quantityToAdd")) : 0;
				int quantityToSubstract = (request
						.getParameter("quantityToSubstract") != null && request
						.getParameter("quantityToSubstract").toString()
						.length() != 0) ? Integer.parseInt(request
						.getParameter("quantityToSubstract")) : 0;

				productService.updateInventory(productSkuCode,
						currentInventory, quantityToAdd, quantityToSubstract,
						true, sellerId);
				model.put("Record", ConverterClass
						.prepareProductBean(productService
								.getProduct(productId)));
			}
		} catch (CustomException ce) {
			log.error("inventoryList exception : " + ce.toString());
			model.put("error", ce.getLocalMessage());
			String error = gson.toJson(model);
			return error;
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed!",e);
		}
		model.put("Result", "OK");

		String jsonArray = gson.toJson(model);
		log.info("$$$ inventoryList Ends : ProductController $$$");
		return jsonArray;
	}

	@RequestMapping(value = "/seller/saveProductSheet", method = RequestMethod.POST)
	public ModelAndView save(HttpServletRequest request,
			@ModelAttribute("uploadForm") FileUploadForm uploadForm, Model map) {

		log.info("$$$ save() Starts : ProductController $$$");
		List<MultipartFile> files = uploadForm.getFiles();

		List<String> fileNames = new ArrayList<String>();
		MultipartFile fileinput = files.get(0);
		int sellerId;

		// gets absolute path of the web application
		String applicationPath = request.getServletContext().getRealPath("");
		// constructs path of the directory to save uploaded file
		String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;
		log.debug("***** uploadFilePath path  : " + uploadFilePath);

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			if (null != files && files.size() > 0) {
				fileNames.add(files.get(0).getOriginalFilename());

				sellerId = helperClass.getSellerIdfromSession(request);

				log.debug(" Filename : "
						+ files.get(0).getOriginalFilename());
				log.debug(" Filename : " + files.get(0).getName());
				ValidateUpload.validateOfficeData(files.get(0));
				log.debug(" fileinput " + fileinput.getName());
				saveContents.saveProductContents(files.get(0), sellerId,
						uploadFilePath);

			}
			model.put("products", ConverterClass
					.prepareListofProductBean(productService.listProducts(
							helperClass.getSellerIdfromSession(request), 0)));
		} catch (CustomException ce) {
			log.error("save exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("Failed!",e);
		}

		log.info("$$$ save() Ends : ProductController $$$");
		return new ModelAndView("productList", model);

	}

	@RequestMapping(value = "/seller/saveInventorySheet", method = RequestMethod.POST)
	public ModelAndView saveInventories(HttpServletRequest request,
			@ModelAttribute("uploadForm") FileUploadForm uploadForm, Model map) {

		log.info("$$$ saveInventories Starts : ProductController $$$");
		// gets absolute path of the web application
		String applicationPath = request.getServletContext().getRealPath("");

		List<MultipartFile> files = uploadForm.getFiles();

		List<String> fileNames = new ArrayList<String>();
		MultipartFile fileinput = files.get(0);
		int sellerId;
		Map<String, Object> model = new HashMap<String, Object>();

		try {

			sellerId = helperClass.getSellerIdfromSession(request);
			if (null != files && files.size() > 0) {
				fileNames.add(files.get(0).getOriginalFilename());

				log.debug(" Filename : "
						+ files.get(0).getOriginalFilename());
				log.debug(" Filename : " + files.get(0).getName());
				ValidateUpload.validateOfficeData(files.get(0));
				log.debug(" fileinput " + fileinput.getName());
				saveContents.saveInventoryDetails(files.get(0), sellerId,
						applicationPath);

			}
			model.put("products", ConverterClass
					.prepareListofProductBean(productService.listProducts(
							helperClass.getSellerIdfromSession(request), 0)));

		} catch (CustomException ce) {
			log.error("saveInventories exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed!",e);
		}
		log.info("$$$ saveInventories Ends : ProductController $$$");
		return new ModelAndView("productList", model);

	}

	@RequestMapping(value = "/seller/checkSKUAvailability", method = RequestMethod.GET)
	public @ResponseBody String checkSKUAvailability(HttpServletRequest request) {

		log.info("$$$ checkSKUAvailability Starts : ProductController $$$");
		Product product = null;
		try {
			product = productService.getProduct(request.getParameter("sku"),
					helperClass.getSellerIdfromSession(request));
		} catch (Exception e) {
			log.error(e);
		}
		
		if (product != null) {
			log.info("$$$ checkSKUAvailability Ends : ProductController $$$");
			return "false";
		} else {
			log.info("$$$ checkSKUAvailability Ends : ProductController $$$");
			return "true";
		}
	}

}
