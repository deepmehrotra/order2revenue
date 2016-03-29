package com.o2r.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.o2r.bean.OrderBean;
import com.o2r.bean.ProductBean;
import com.o2r.helper.ConverterClass;
import com.o2r.helper.CustomException;
import com.o2r.helper.FileUploadForm;
import com.o2r.helper.HelperClass;
import com.o2r.helper.SaveContents;
import com.o2r.helper.ValidateUpload;
import com.o2r.model.Order;
import com.o2r.model.Partner;
import com.o2r.model.Product;
import com.o2r.model.TaxCategory;
import com.o2r.service.DownloadService;
import com.o2r.service.OrderService;
import com.o2r.service.PartnerService;
import com.o2r.service.ProductService;
import com.o2r.service.TaxDetailService;


/**
 * @author Deep Mehrotra
 *
 */
@Controller
public class OrderController {

 @Autowired
 private OrderService orderService;
 @Resource(name="downloadService")
	private DownloadService downloadService;
 @Resource(name="saveContents")
 private SaveContents saveContents;
 @Autowired
 private PartnerService partnerService;
 @Autowired
 private ProductService productService;
 @Autowired
 private TaxDetailService taxDetailService;

 private static final String UPLOAD_DIR="upload";


 static Logger log = Logger.getLogger(OrderController.class.getName());
/**
 * Downloads the report as an Excel format.
 * <p>
 * Make sure this method doesn't return any model. Otherwise, you'll get
 * an "IllegalStateException: getOutputStream() has already been called for this response"
 */
@RequestMapping(value = "/seller/download/xls", method = RequestMethod.GET)
public void getXLS(HttpServletResponse response,@RequestParam(value="sheetvalue")String sheetvalue) throws ClassNotFoundException {

	// Delegate to downloadService. Make sure to pass an instance of HttpServletResponse
	System.out.println(" Downloading the sheet: "+sheetvalue);
	if(sheetvalue!=null)
	{
		if(sheetvalue.equals("ordersummary"))
		{
			downloadService.downloadXLS(response);
		}
		else if(sheetvalue.equals("orderPoSummary"))
		{
			downloadService.downloadOrderPOXLS(response);
		}
		else if(sheetvalue.equals("paymentSummary"))
		{
			downloadService.downloadPaymentXLS(response);
		}
		else if(sheetvalue.equals("returnSummary"))
		{
			downloadService.downloadReturnXLS(response);
		}
		else if(sheetvalue.equals("productSummary"))
		{
			downloadService.downloadProductXLS(response);
		}
		else if(sheetvalue.equals("inventorySummary"))
		{
			downloadService.downloadInventoryXLS(response);
		}
		else if(sheetvalue.equals("debitNoteSummary"))
		{
			downloadService.downloadDebitNoteXLS(response);
		}
		else if(sheetvalue.equals("poPaymentSummary"))
		{
			downloadService.downloadPOPaymentXLS(response);
		}
		else if(sheetvalue.equals("expenseSummary"))
		{
			downloadService.downloadExpensesXLS(response);
		}
	}
}

/**
 * Redirect to upload download page.
 * <p>

 */
/*@RequestMapping(value = "/seller/ordersheet", method = RequestMethod.GET)
public String displayForm() {

		return "file_upload_form";
	}*/

@RequestMapping(value = "/seller/downloadOrderDA", method = RequestMethod.GET)
public ModelAndView displayDownloadForm(@RequestParam("value") String value,@ModelAttribute("uploadForm") FileUploadForm uploadForm,
		Model map) {
	System.out.println("Inside download orders  viewpayments uploadId"+value);
	Map<String, Object> model = new HashMap<String, Object>();
	model.put("downloadValue",value);

  return new ModelAndView("dailyactivities/order_upload_form",model);
 }

@RequestMapping(value = "/seller/uploadOrderDA", method = RequestMethod.GET)
public ModelAndView displayUploadForm(@RequestParam("value") String value,@ModelAttribute("uploadForm") FileUploadForm uploadForm,
		Model map) {
	System.out.println("Inside Payment orders  viewpayments uploadId"+value);
	Map<String, Object> model = new HashMap<String, Object>();
	model.put("uploadValue",value);

  return new ModelAndView("dailyactivities/order_upload_form",model);
 }


@RequestMapping(value="/user-login", method=RequestMethod.GET)
public ModelAndView loginForm() {

	System.out.println("catalina.base  "+ System.getProperty("catalina.base"));

	try {
		org.springframework.core.io.Resource resource = new ClassPathResource("database.properties");
		Properties props = PropertiesLoaderUtils.loadProperties(resource);
		System.out.println("dialect in order controller : "+props.getProperty("hibernate.dialect"));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return new ModelAndView("login-form");
}

@RequestMapping(value="/error-login", method=RequestMethod.GET)
public ModelAndView invalidLogin() {
	ModelAndView modelAndView = new ModelAndView("login-form");
	modelAndView.addObject("error", true);
	return modelAndView;
}

@RequestMapping(value = "/seller/saveSheet", method = RequestMethod.POST)
public ModelAndView save(HttpServletRequest request,@ModelAttribute("uploadForm") FileUploadForm uploadForm,
				Model map) {
	System.out.println("Inside save method");
	double starttime=System.currentTimeMillis();
	System.out.println(" **StartTime : "+starttime);
	List<MultipartFile> files = uploadForm.getFiles();
	 InputStream inputStream = null;
	  OutputStream outputStream = null;
	//  Map<String ,Object> errorMap =null;
	  Map<String, Object> model = new HashMap<String, Object>();
	 // model.put("orders",  ConverterClass.prepareListofBean(orderService.listOrders(4)));
	List<String> fileNames = new ArrayList<String>();
	MultipartFile fileinput=files.get(0);
	int sellerId=HelperClass.getSellerIdfromSession(request);
	System.out.println(" got file");
	if(null != files && files.size() > 0) {
		fileNames.add(files.get(0).getOriginalFilename());
		try{
			System.out.println(" Filename : "+files.get(0).getOriginalFilename());
			System.out.println(" uploadForm.getSheetValue() : "+uploadForm.getSheetValue());

		 ValidateUpload.validateOfficeData(files.get(0));
		 System.out.println(" fileinput "+fileinput.getName());
		 switch(uploadForm.getSheetValue())
		 {
		 	case "ordersummary" :
		 		model.put("orderMap", saveContents.saveOrderContents(files.get(0),sellerId));
		 		model.put("mapType", "orderMap");
		 		//saveContents.saveOrderContents(files.get(0),sellerId);
		 		break;
		 	case "orderPoSummary" :
		 		model.put("orderPoMap", saveContents.saveOrderContents(files.get(0),sellerId));
		 		model.put("mapType", "orderPoMap");
		 		//saveContents.saveOrderPOContents(files.get(0),sellerId);
		 		break;
		 	case "paymentSummary" :
		 		model.put("orderPaymentMap",saveContents.savePaymentContents(files.get(0),sellerId));
		 		model.put("mapType", "orderPaymentMap");
		 		//saveContents.savePaymentContents(files.get(0),sellerId);
		 		break;
		 	case "returnSummary" :
		 		model.put("orderReturnMap",saveContents.saveOrderReturnDetails(files.get(0),sellerId));
		 		model.put("mapType", "orderReturnMap");
		 		break;
		 	case "productSummary" :
		 		model.put("productMap",saveContents.saveProductContents(files.get(0),sellerId));
		 		model.put("mapType", "productMap");
		 		//saveContents.saveProductContents(files.get(0),sellerId);
		 		break;
		 	case "inventorySummary" :
		 		model.put("inventoryMap",saveContents.saveInventoryDetails(files.get(0),sellerId));
		 		model.put("mapType", "inventoryMap");
		 		//saveContents.saveInventoryDetails(files.get(0),sellerId);
		 		break;
			case "debitNoteSummary" :
				//model.put("debitNotetMap",saveContents.saveInventoryDetails(files.get(0),sellerId));
		 		saveContents.saveDebitNoteDetails(files.get(0),sellerId);
		 		model.put("mapType", "debitNoteSummary");
		 		break;
			case "poPaymentSummary" :
				//model.put("poPaymentMap",saveContents.saveInventoryDetails(files.get(0),sellerId));
		 		saveContents.savePoPaymetnDetails(files.get(0),sellerId);
		 		model.put("mapType", "poPaymentSummary");
		 		break;
			case "expenseSummary" :
				model.put("expensesMap",saveContents.saveExpenseDetails(files.get(0),sellerId));
				model.put("mapType", "expensesMap");
		 		//saveContents.saveExpenseDetails(files.get(0),sellerId);
		 		break;

		 }
		 inputStream = files.get(0).getInputStream();

		 // gets absolute path of the web application
	        String applicationPath = request.getServletContext().getRealPath("");
	        // constructs path of the directory to save uploaded file
	        String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;
	          System.out.println("***** Application path  : "+applicationPath+"  file xontent type : "+files.get(0).getContentType());
	          System.out.println("***** uploadFilePath path  : "+uploadFilePath);
	        // creates the save directory if it does not exists
	        File fileSaveDir = new File(uploadFilePath);
	        if (!fileSaveDir.exists()) {
	        	System.out.println(" Directory doesnnt exist");
	            fileSaveDir.mkdirs();
	        }
	        outputStream = new FileOutputStream(fileSaveDir + File.separator+files.get(0).getOriginalFilename());
	        int read = 0;
	        byte[] bytes = new byte[1024];

	        while ((read = inputStream.read(bytes)) != -1) {
	         outputStream.write(bytes, 0, read);
	        }

	        System.out.println("### Saved files succesfully");
	        outputStream.close();
	        inputStream.close();
	        FileUtils.cleanDirectory(fileSaveDir);
	        double endtime=System.currentTimeMillis();
	        System.out.println(" **endtime : "+endtime);
	        double lapsetime=(endtime-starttime)/1000;
	        model.put("fileName", files.get(0).getOriginalFilename());
	        model.put("timeTaken", lapsetime);
		// saveContents.saveOrderContents(files.get(0),sellerId);
		}
		catch (Exception e) {
			System.out.println("Inside exception , filetype not accepted "+e.getLocalizedMessage());
			e.printStackTrace();

		}

	}


	  return new ModelAndView("dailyactivities/uploadResults",model);

}

//Methods for Ajax implementtation
@RequestMapping(value="/seller/searchOrder", method = RequestMethod.POST)
public ModelAndView searchOrder(HttpServletRequest request,@ModelAttribute("command")OrderBean orderBean,
		    BindingResult result){
 Map<String, Object> model = new HashMap<String, Object>();
 System.out.println("Inside list products ");
 List<OrderBean> orderList=new ArrayList<>();
 int sellerId=HelperClass.getSellerIdfromSession(request);
 ProductBean product=null;
 String channelOrderID=request.getParameter("channelOrderID");
 String startDate=request.getParameter("startDate");
 String endDate=request.getParameter("endDate");
 String searchOrder=request.getParameter("searchOrder");
 if(searchOrder!=null&&searchOrder.equals("searchchannelOrderID")&&channelOrderID!=null)
 {
	 orderList=ConverterClass.prepareListofBean(orderService.findOrders("channelOrderID", channelOrderID, sellerId));
 }
 else if(searchOrder!=null&&startDate!=null&&endDate!=null)
 {
	 orderList=ConverterClass.prepareListofBean(orderService.findOrdersbyDate("orderDate", new Date(startDate), new Date(endDate), sellerId));

 }
request.getSession().setAttribute("orderSearchObject", orderList);
 //model.put("productList", productList);
 return new ModelAndView("redirect:/seller/orderList.html");

}

@RequestMapping(value = "/seller/orderList", method = RequestMethod.GET)
public ModelAndView orderListDailyAct(HttpServletRequest request,@ModelAttribute("command")OrderBean orderBean,
  BindingResult result) {
 Map<String, Object> model = new HashMap<String, Object>();
 String savedOrder=request.getParameter("savedOrder");
 List<OrderBean> returnlist=new ArrayList<OrderBean>();
 Object obj=request.getSession().getAttribute("orderSearchObject");
 int sellerId=HelperClass.getSellerIdfromSession(request);
 if(obj!=null)
 {
	  System.out.println(" Getting list from session"+obj);
	  model.put("orders",obj);
	  request.getSession().removeAttribute("orderSearchObject");
 }
 else if(request.getParameter("status")!=null)
 {
	 String status=request.getParameter("status");
	 if(status.equalsIgnoreCase("return"))
	 {
       returnlist=ConverterClass.prepareListofBean(orderService.findOrders("status", "Return Recieved", sellerId));
       returnlist.addAll(ConverterClass.prepareListofBean(orderService.findOrders("status", "Return Limit Crossed", sellerId)));
       model.put("orders", returnlist);
	 }
	 else if(status.equalsIgnoreCase("payment"))
	 {
		  returnlist=ConverterClass.prepareListofBean(orderService.findOrders("status", "Payment Recieved", sellerId));
	       returnlist.addAll(ConverterClass.prepareListofBean(orderService.findOrders("status", "Payment Deducted", sellerId)));
	       model.put("orders", returnlist);
	 }
	 else if(status.equalsIgnoreCase("actionable"))
	 {
		  returnlist=ConverterClass.prepareListofBean(orderService.findOrders("finalStatus", "Actionable", sellerId));
		  model.put("orders", returnlist);
	 }
 }
 else
 {
 int pageNo=request.getParameter("page")!=null?Integer.parseInt(request.getParameter("page")):0;
 model.put("orders",  ConverterClass.prepareListofBean(orderService.listOrders(HelperClass.getSellerIdfromSession(request),pageNo)));
 }
  if(savedOrder!=null)
 model.put("savedOrder", savedOrder);
 return new ModelAndView("dailyactivities/orderList", model);
}

@RequestMapping(value = "/seller/viewOrderDA", method = RequestMethod.GET)
public ModelAndView viewOrderDailyAct(HttpServletRequest request,@ModelAttribute("command")OrderBean orderBean,
  BindingResult result) {
 Map<String, Object> model = new HashMap<String, Object>();
 int sellerId=HelperClass.getSellerIdfromSession(request);
 Order order=orderService.getOrder(orderBean.getOrderId(),sellerId);
 Product product = productService.getProduct(order.getProductSkuCode(),sellerId );
 System.out.println(" Payment difference :"+order.getOrderPayment().getPaymentDifference());
 model.put("order",  ConverterClass.prepareOrderBean(order));
 if(product!=null)
 model.put("productCost", product.getProductPrice());
 return new ModelAndView("dailyactivities/viewOrder", model);
}

@RequestMapping(value = "/seller/editOrderDA", method = RequestMethod.GET)
public ModelAndView editOrderDA(HttpServletRequest request,@ModelAttribute("command")OrderBean orderBean,
   BindingResult result) {
  Map<String, Object> model = new HashMap<String, Object>();
  model.put("order", ConverterClass.prepareOrderBean(orderService.getOrder(orderBean.getOrderId())));
  model.put("orders",  ConverterClass.prepareListofBean(orderService.listOrders(HelperClass.getSellerIdfromSession(request))));
  return new ModelAndView("dailyactivities/editOrder", model);
 }

@RequestMapping(value = "/seller/deleteOrderDA", method = RequestMethod.GET)
public ModelAndView deleteOrderDA(HttpServletRequest request,@ModelAttribute("command")OrderBean orderBean,
   BindingResult result) {
	System.out.println(" Order bean id todelete :"+orderBean.getOrderId());
  orderService.deleteOrder(ConverterClass.prepareModel(orderBean),HelperClass.getSellerIdfromSession(request));
  Map<String, Object> model = new HashMap<String, Object>();
  model.put("order", null);
  model.put("orders",  ConverterClass.prepareListofBean(orderService.listOrders(HelperClass.getSellerIdfromSession(request))));
  return new ModelAndView("dailyactivities/addOrder", model);
 }

@RequestMapping(value = "/seller/saveOrderDA", method = RequestMethod.POST)
public ModelAndView saveOrderDA(HttpServletRequest request,@ModelAttribute("command")OrderBean orderBean,
   BindingResult result) {
	System.out.println("Inside order Ssave");
	System.out.println(" Order id :"+orderBean.getOrderId());
//	Map<String, Object> model = new HashMap<String, Object>();
  Order order = ConverterClass.prepareModel(orderBean);
  orderService.addOrder(order,HelperClass.getSellerIdfromSession(request));
  return new ModelAndView("redirect:/seller/orderList.html?savedOrder="+orderBean.getChannelOrderID());

 }

@RequestMapping(value = "/seller/addOrderDA", method = RequestMethod.GET)
public ModelAndView addOrderDA(HttpServletRequest request,@ModelAttribute("command")OrderBean orderBean,
		BindingResult result) {
	log.info("***addOrderDA Start***");
	Map<String, Object> model = new HashMap<String, Object>();
	try
	{
		List<Partner> partnerlist=partnerService.listPartners(HelperClass.getSellerIdfromSession(request));
		List<TaxCategory> taxCatList=taxDetailService.listTaxCategories(HelperClass.getSellerIdfromSession(request));
		List<Product> productList=productService.listProducts(HelperClass.getSellerIdfromSession(request));
		Map<String,String> partnermap=new HashMap<String, String>();
		Map<String,String> taxcatmap=new HashMap<String, String>();
		Map<String,String> productmap=new HashMap<String, String>();
		if(partnerlist!=null&&partnerlist.size()!=0)
		{
			for(Partner bean:partnerlist)
			{
				partnermap.put(bean.getPcName(), bean.getPcName());

			}
		}
		if(taxCatList!=null&&taxCatList.size()!=0)
		{
			for(TaxCategory bean:taxCatList)
			{
				taxcatmap.put(bean.getTaxCatName(),bean.getTaxCatName());

			}
		}
		if(productList!=null&&productList.size()!=0)
		{
			for(Product bean:productList)
			{
				productmap.put(bean.getProductSkuCode(),bean.getProductSkuCode());

			}
		}

		model.put("partnermap", partnermap);
		model.put("taxcatmap", taxcatmap);
		model.put("productmap", productmap);
	}catch(CustomException ce)
	{
		log.error("addOrderDA exception : "+ce.toString());
		model.put("errorMessage", ce.getLocalMessage());
		model.put("errorTime", ce.getErrorTime());
		model.put("errorCode", ce.getErrorCode());
		return new ModelAndView("globalErorPage", model);
	}
	catch (Throwable e) {
		log.error(e);
		return new ModelAndView("globalErorPage", model);
	}
	//model.put("orders",  ConverterClass.prepareListofBean(orderService.listOrders(4)));
	return new ModelAndView("dailyactivities/addOrder", model);
}


//Method to check Availability of Order Id
@RequestMapping(value = "/seller/checkOrder.html", method = RequestMethod.GET)
public @ResponseBody String getCheckPartner(HttpServletRequest request,@ModelAttribute("command")OrderBean orderBean,
		   BindingResult result, Model model) {
	System.out.println(request.getParameter("id"));
	List parner = orderService.findOrders("channelOrderID", request.getParameter("id"), HelperClass.getSellerIdfromSession(request));
	if(parner!=null&&parner.size()!=0&&parner.get(0)!=null){
		return  "<font color='red'>Order ID Not available</font>";
	}else{
		return  "<font color='green'>Order ID Available</font>";
	}
}
}