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
import com.o2r.helper.ConverterClass;
import com.o2r.helper.CustomException;
import com.o2r.helper.DateDeserializer;
import com.o2r.helper.DateSerializer;
import com.o2r.helper.FileUploadForm;
import com.o2r.helper.HelperClass;
import com.o2r.helper.SaveContents;
import com.o2r.helper.ValidateUpload;
import com.o2r.model.Category;
import com.o2r.model.Product;
import com.o2r.service.CategoryService;
import com.o2r.service.DownloadService;
import com.o2r.service.OrderService;
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
	@Resource(name = "downloadService")
	private DownloadService downloadService;
	@Resource(name = "saveContents")
	private SaveContents saveContents;
	@Autowired
	private CategoryService categoryService;

	static Logger log = Logger.getLogger(ProductController.class.getName());
	
	private static final String UPLOAD_DIR = "upload";

	@RequestMapping(value = "/seller/searchProduct", method = RequestMethod.POST)
	public ModelAndView searchProduct(HttpServletRequest request,
			@ModelAttribute("command") ProductBean productBean,
			BindingResult result) {

		log.info("*** serchProduct start ***");
		Map<String, Object> model = new HashMap<String, Object>();
		System.out.println("Inside list products ");
		List<ProductBean> productList = new ArrayList<>();
		int sellerId;
		String skuCode = request.getParameter("skuCode");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String searchProduct = request.getParameter("searchProduct");
		try {
			sellerId = HelperClass.getSellerIdfromSession(request);
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
			log.error(e);
		}
		request.getSession().setAttribute("productSearchObject", productList);
		// model.put("productList", productList);
		log.info("*** searchProduct exit ***");
		return new ModelAndView("redirect:/seller/Product.html");

	}

	@RequestMapping(value = "/seller/Product", method = RequestMethod.GET)
	public ModelAndView productList(HttpServletRequest request,
			@ModelAttribute("command") ProductBean productBean,
			BindingResult result) {

		log.info("*** productList start ***");
		Map<String, Object> model = new HashMap<String, Object>();

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
								HelperClass.getSellerIdfromSession(request),
								pageNo)));
			}
		} catch (CustomException ce) {
			log.error("productList exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			log.error(e);
		}

		log.info("*** productList exit ***");
		return new ModelAndView("initialsetup/Product", model);

	}
	
	 @RequestMapping(value = "/seller/saveUpdateInventory", method = RequestMethod.POST)
	 public ModelAndView saveDeleteProduct(HttpServletRequest request,@ModelAttribute("command")ProductBean productBean,
	    BindingResult result) {
		 	System.out.println("Inside Delete  Product");
		 	String var1=request.getParameter("quantityToAdd");
		 	String var2=request.getParameter("quantityToSubtract");

	 	if(productBean.getProductSkuCode()!=null)
	 	{
	 	productBean.setProductDate(new Date());
	 	Product product = ConverterClass.prepareProductModel(productBean);
	 	
	 	int productId=request.getParameter("productId")!=null?Integer.parseInt(request.getParameter("productId")):0;
	 	String productSkuCode=productBean.getProductSkuCode();
	//   int currentInventory=request.getParameter("currentInventory")!=null?Integer.parseInt(request.getParameter("currentInventory")):0;
	   int quantityToAdd=(request.getParameter("quantityToAdd")!=null&&request.getParameter("quantityToAdd").toString().length()!=0)?Integer.parseInt(request.getParameter("quantityToAdd")):0;
	   int quantityToSubstract=(request.getParameter("quantityToSubtract")!=null&&request.getParameter("quantityToSubtract").toString().length()!=0)?Integer.parseInt(request.getParameter("quantityToSubtract")):0;
	   int sellerId=0;
	try {
		sellerId = HelperClass.getSellerIdfromSession(request);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

		   try {
			productService.updateInventory(productSkuCode, 0, quantityToAdd, quantityToSubstract,true,sellerId);
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 	
	 	}
	 	return new ModelAndView("redirect:/seller/Product.html");
	  }


	@RequestMapping(value = "/seller/saveProduct", method = RequestMethod.POST)
	public ModelAndView saveProduct(HttpServletRequest request,
			@ModelAttribute("command") ProductBean productBean,
			BindingResult result) {

		log.info("*** saveProduct start ***");
		Map<String, Object> model = new HashMap<String, Object>();

		System.out.println("Inside Product Ssave");
		System.out.println(" Product Id :" + productBean.getProductId());
		System.out.println(" Product SKU :" + productBean.getProductSkuCode());
		System.out.println(" Product category  :"
				+ productBean.getCategoryName());

		try {
			if (productBean.getProductSkuCode() != null) {
				productBean.setProductDate(new Date());
				productBean.setVolume(productBean.getHeight() * productBean.getLength()
						* productBean.getBreadth());
				productBean.setVolWeight(productBean.getVolume() / 5);
				Product product = ConverterClass
						.prepareProductModel(productBean);
				productService.addProduct(product,
						HelperClass.getSellerIdfromSession(request));
			}
		} catch (CustomException ce) {
			log.error("saveProduct exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			log.equals(e);
		}
		log.info("*** saveProduct exit ***");
		return new ModelAndView("redirect:/seller/Product.html");
	}

	@RequestMapping(value = "/seller/addProduct", method = RequestMethod.GET)
	public ModelAndView addProduct(HttpServletRequest request,
			@ModelAttribute("command") ProductBean productBean,
			BindingResult result) {

		log.info("***addProduct  Start****");
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, String> categoryMap = new LinkedHashMap<>();
		try {
			List<Category> categoryList = categoryService
					.listCategories(HelperClass.getSellerIdfromSession(request));
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
			log.error(e);
			return new ModelAndView("globalErorPage", model);
		}
		model.put("categoryMap", categoryMap);
		log.info("*** addProduct exit ***");
		return new ModelAndView("initialsetup/addProduct", model);
	}

	@RequestMapping(value = "/seller/editProduct", method = RequestMethod.GET)
	public ModelAndView editProduct(HttpServletRequest request,
			@ModelAttribute("command") ProductBean productBean,
			BindingResult result) {
		log.info("*** editProduct start ***");
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
		log.info("*** editProduct exit ***");
		return new ModelAndView("initialsetup/addProduct", model);
	}

	@RequestMapping(value = "/seller/saveProductJson", method = RequestMethod.POST)
	public @ResponseBody String saveProductJson(HttpServletRequest request) {
		log.info("*** saveeProductJson  start ***");
		Map<String, Object> model = new HashMap<String, Object>();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Product product = new Product();
		System.out.println("Product id " + request.getParameter("productId"));
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
			String channelSKU = request.getParameter("channelSKU") != null ? request.getParameter("channelSKU") : "";
			float productLength = request.getParameter("length") != null ? Float.valueOf(request.getParameter("length")) : 0;
			float productBreadth = request.getParameter("breadth") != null ? Float.valueOf(request.getParameter("breadth")) : 0;
			float productHeight = request.getParameter("height") != null ? Float.valueOf(request.getParameter("height")) : 0;
			float productDeadweight = request.getParameter("deadWeight") != null ? Float.valueOf(request.getParameter("deadWeight")) : 0;
					

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
					HelperClass.getSellerIdfromSession(request));

		} catch (CustomException ce) {
			log.error("saveProduct exception : " + ce.toString());
			model.put("error", ce.getLocalMessage());
			String errors=gson.toJson(model);
			return errors;
		} catch (Throwable e) {
			log.error(e);
		}

		model.put("Result", "OK");
		model.put("Record", ConverterClass.prepareProductBean(product));
		String jsonArray = gson.toJson(model);
		log.info("*** saveeProductJson  start ***");
		return jsonArray;
	}

	/**
	 * Downloads the report as an Excel format.
	 * <p>
	 * Make sure this method doesn't return any model. Otherwise, you'll get an
	 * "IllegalStateException: getOutputStream() has already been called for this response"
	 */
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

	/**
	 * Redirect to upload download page.
	 * <p>
	 */
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

		log.info("*** listProductsJSON start ***");
		Map<String, Object> model = new HashMap<String, Object>();
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
		gsonBuilder.registerTypeAdapter(Date.class, new DateSerializer());

		Gson gson = gsonBuilder.setPrettyPrinting().create();
		try {
			model.put("Records", ConverterClass
					.prepareListofProductBean(productService.listProducts(
							HelperClass.getSellerIdfromSession(request), 0)));
		} catch (Throwable e) {
			log.error(e);
		}
		model.put("Result", "OK");
		String jsonArray = gson.toJson(model);
		log.info("*** listProductsJSON exit ***");
		return jsonArray;
	}

	@RequestMapping(value = "/seller/showInventory", method = RequestMethod.POST)
	public @ResponseBody String inventoryList(HttpServletRequest request) {

		log.info("***inventoryList start***");
		Map<String, Object> model = new HashMap<String, Object>();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String action = request.getParameter("action");
		int sellerId;

		try {
			sellerId = HelperClass.getSellerIdfromSession(request);
			if (action != null && action.equals("list")) {
				model.put("Records",
						ConverterClass.prepareListofProductBean(productService
								.listProducts(HelperClass
										.getSellerIdfromSession(request), 0)));

			} else {
				System.out
						.println(" Getting inventory update subtract value : : "
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
			log.error(e);
		}
		model.put("Result", "OK");

		String jsonArray = gson.toJson(model);
		log.info("*** inventoryList exit ***");
		return jsonArray;
	}

	@RequestMapping(value = "/seller/saveProductSheet", method = RequestMethod.POST)
	public ModelAndView save(HttpServletRequest request,
			@ModelAttribute("uploadForm") FileUploadForm uploadForm, Model map) {

		log.info("*** save start ***");
		System.out.println("Inside save method");
		List<MultipartFile> files = uploadForm.getFiles();

		List<String> fileNames = new ArrayList<String>();
		MultipartFile fileinput = files.get(0);
		System.out.println(" got file");
		int sellerId;

		// gets absolute path of the web application
		String applicationPath = request.getServletContext().getRealPath("");
		// constructs path of the directory to save uploaded file
		String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;
		System.out.println("***** uploadFilePath path  : " + uploadFilePath);

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			if (null != files && files.size() > 0) {
				fileNames.add(files.get(0).getOriginalFilename());

				sellerId = HelperClass.getSellerIdfromSession(request);

				System.out.println(" Filename : "
						+ files.get(0).getOriginalFilename());
				System.out.println(" Filename : " + files.get(0).getName());
				ValidateUpload.validateOfficeData(files.get(0));
				System.out.println(" fileinput " + fileinput.getName());
				saveContents.saveProductContents(files.get(0), sellerId, uploadFilePath);

			}
			model.put("products", ConverterClass
					.prepareListofProductBean(productService.listProducts(
							HelperClass.getSellerIdfromSession(request), 0)));
		} catch (CustomException ce) {
			log.error("save exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Throwable e) {
			log.error(e);

		}

		log.info("*** save exit ***");
		return new ModelAndView("productList", model);

	}

	@RequestMapping(value = "/seller/saveInventorySheet", method = RequestMethod.POST)
	public ModelAndView saveInventories(HttpServletRequest request,
			@ModelAttribute("uploadForm") FileUploadForm uploadForm, Model map) {

		log.info("*** saveInventories start ***");
		System.out.println("Inside save method");
		// gets absolute path of the web application
		String applicationPath = request.getServletContext().getRealPath("");
				
		List<MultipartFile> files = uploadForm.getFiles();

		List<String> fileNames = new ArrayList<String>();
		MultipartFile fileinput = files.get(0);
		int sellerId;
		Map<String, Object> model = new HashMap<String, Object>();

		try {

			sellerId = HelperClass.getSellerIdfromSession(request);
			if (null != files && files.size() > 0) {
				fileNames.add(files.get(0).getOriginalFilename());

				System.out.println(" Filename : "
						+ files.get(0).getOriginalFilename());
				System.out.println(" Filename : " + files.get(0).getName());
				ValidateUpload.validateOfficeData(files.get(0));
				System.out.println(" fileinput " + fileinput.getName());
				saveContents.saveInventoryDetails(files.get(0), sellerId, applicationPath);

			}
			model.put("products", ConverterClass
					.prepareListofProductBean(productService.listProducts(
							HelperClass.getSellerIdfromSession(request), 0)));

		} catch (CustomException ce) {
			log.error("saveInventories exception : " + ce.toString());
			model.put("errorMessage", ce.getLocalMessage());
			model.put("errorTime", ce.getErrorTime());
			model.put("errorCode", ce.getErrorCode());
			return new ModelAndView("globalErorPage", model);
		} catch (Exception e) {
			log.error(e);
		}
		log.info("*** saveInventories exit ***");
		return new ModelAndView("productList", model);

	}

	// Method to List Categoires for New Product
	/*
	 * @RequestMapping(value="/seller/getProductCategories", method =
	 * RequestMethod.POST) public @ResponseBody String
	 * getProductCategories(HttpServletRequest request) { int
	 * sellerId=HelperClass.getSellerIdfromSession(request);
	 * System.out.println("Inside getProductCategories list json"); Map<String,
	 * Object> model = new HashMap<String, Object>();
	 * 
	 * GsonBuilder gsonBuilder = new GsonBuilder();
	 * gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
	 * gsonBuilder.registerTypeAdapter(Date.class, new DateSerializer()); Gson
	 * gson = gsonBuilder.setPrettyPrinting().create();
	 * 
	 * 
	 * JSONArray ja = new JSONArray();
	 * 
	 * JSONObject otherjo = new JSONObject();
	 * 
	 * Map<String , String> categoryMap=new HashMap<>(); List<Category>
	 * categoryList=categoryService.listCategories(sellerId);
	 * if(categoryList!=null&&categoryList.size()!=0) { for(Category
	 * category:categoryList) {
	 * System.out.println(" categoryList name : "+category.getCatName());
	 * JSONObject jo = new JSONObject(); jo.put("DisplayText",
	 * category.getCatName()); jo.put("Value", category.getCatName());
	 * ja.add(jo);
	 * 
	 * } } model.put("Options",ja); model.put("Result", "OK"); String jsonArray
	 * = gson.toJson(model); System.out.println(jsonArray); return jsonArray; }
	 */

	@RequestMapping(value = "/seller/checkSKUAvailability", method = RequestMethod.GET)
	public @ResponseBody String checkSKUAvailability(HttpServletRequest request) {

		log.info("*** checkSKUAvailability start ***");
		Product product = null;
		try {
			product = productService.getProduct(request.getParameter("sku"),
					HelperClass.getSellerIdfromSession(request));
			// System.out.println(product);
		} catch (Exception e) {
			log.error(e);
		}
		log.info("*** checkSKUAvailability exit ***");

		if (product != null) {
			return "false";
		} else {
			return "true";
		}
	}

}
