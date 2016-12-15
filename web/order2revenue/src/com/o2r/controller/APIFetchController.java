package com.o2r.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.o2r.helper.HelperClass;
import com.o2r.helper.SaveApiContents;
import com.o2r.model.ShopcluesOrderAPI;
import com.o2r.service.APIService;

@Controller
public class APIFetchController {
	
	@Autowired
	private HelperClass helperClass;
	@Resource(name = "saveApiContents")
	private SaveApiContents saveApiContents;
	@Autowired
	private APIService apiService;
	
	
	static Logger log = Logger.getLogger(APIFetchController.class.getName());
	
	@RequestMapping(value = "/seller/orderAPI_Admin", method = RequestMethod.GET)
	public ModelAndView orderAPIPage() {				
		return new ModelAndView("admin/apiOrdersList");		
	}
	@RequestMapping(value = "/seller/orderAPI_Seller", method = RequestMethod.GET)
	public ModelAndView orderAPIPageSeller() {				
		return new ModelAndView("dailyactivities/apiOrdersList");		
	}
	
	@RequestMapping(value = "/seller/listAPIOrders_Admin", method = RequestMethod.GET)
	public ModelAndView OrderAPIListForAdmin(HttpServletRequest request) {
		int sellerId = 0;	
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Map<Integer, ShopcluesOrderAPI> shopcluesOrderMap = new HashMap<Integer, ShopcluesOrderAPI>();
		Map<String, Object> model = new HashMap<String, Object>();
		Date sDate = null;
		Date eDate = null;
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			String partner = request.getParameter("selectPartner");
			try {
				sDate = formatter.parse(request.getParameter("startDateList"));
				eDate = formatter.parse(request.getParameter("endDateList"));
			} catch (Exception e) {
				sDate = new Date();
				eDate = new Date();
			}	
			switch (partner) {
			case "Shopclues":
				saveApiContents.saveShopcluesOrderAPI(sellerId, 
						fetchOrders("Bearer 13b758f298525a5bc49e84a43c617219c7d1dd00", sDate, eDate));			
				shopcluesOrderMap = apiService.listShopcluesOrderAPI(sellerId);
				//request.getSession().setAttribute("OrderMap", shopcluesOrderMap);
				model.put("Orders", shopcluesOrderMap);
				break;
			case "Amazon":
				
				break;
			default:
				break;
			}			
			model.put("partner", partner);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed By Seller : "+sellerId, e);
		}		
		return new ModelAndView("admin/apiOrdersList", model);		
	}
	
	/*@RequestMapping(value = "", method = RequestMethod.POST)
	public void saveSnapdealOrdersFromAPI(HttpServletRequest request) {
				
	}*/
	
	/*@RequestMapping(value = "/seller/listSnapdealOrders", method = RequestMethod.GET)
	public ModelAndView listSnapdealOrderList(HttpServletRequest request) {
		int sellerId = 0;
		Map<Integer, SnapdealOrderAPI> snapdealOrderMap = new HashMap<Integer, SnapdealOrderAPI>();
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			model.put("Orders", snapdealOrderMap);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed By Seller : "+sellerId, e);
		}		
		return new ModelAndView("miscellaneous/snapdealOrderAPIList", model);		
	}*/
	
	/*@RequestMapping(value = "", method = RequestMethod.POST)
	public ModelAndView saveSnapdealOrdersToO2R(HttpServletRequest request) {
		return null;		
	}*/
	
	/*@RequestMapping(value = "", method = RequestMethod.POST)
	public void saveShopcluesOrdersFromAPI(HttpServletRequest request) {
				
	}*/	
	
	@RequestMapping(value = "/seller/listAPIOrders_Seller", method = RequestMethod.GET)
	public ModelAndView APIOrderListForSeller(HttpServletRequest request) {
		int sellerId = 0;			
		Map<Integer, ShopcluesOrderAPI> shopcluesOrderMap = new HashMap<Integer, ShopcluesOrderAPI>();
		Map<String, Object> model = new HashMap<String, Object>();
		
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			String partner = request.getParameter("selectPartner");				
			switch (partner) {
			case "Shopclues":							
				shopcluesOrderMap = apiService.listShopcluesOrderAPI(sellerId);
				request.getSession().setAttribute("OrderMap", shopcluesOrderMap);
				model.put("Orders", shopcluesOrderMap);
				break;
			case "Amazon":
				
				break;
			default:
				break;
			}			
			model.put("partner", partner);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed By Seller : "+sellerId, e);
		}		
		return new ModelAndView("dailyactivities/apiOrdersList", model);			
	}
	
	/*@RequestMapping(value = "", method = RequestMethod.POST)
	public ModelAndView saveShopcluesOrdersToO2R(HttpServletRequest request) {
		return null;		
	}*/
	
	@RequestMapping(value = "/seller/saveShopcluesOrderAPIToO2r", method = RequestMethod.GET)
	public ModelAndView saveShopcluesOrderAPIToO2r(HttpServletRequest request) {
		int sellerId = 0;
		Map<Integer, ShopcluesOrderAPI> snapdealOrderMap = new HashMap<Integer, ShopcluesOrderAPI>();
		String orderIds= request.getParameter("orders");
		try {
			sellerId = helperClass.getSellerIdfromSession(request);
			if(!orderIds.equals("")){
				snapdealOrderMap = (Map<Integer, ShopcluesOrderAPI>) request.getSession().getAttribute("OrderMap");
				saveApiContents.saveShopcluesOrderAPIBeanToO2R(sellerId, orderIds, snapdealOrderMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed By Seller : "+sellerId, e);
		}		
		return new ModelAndView("redirect:/seller/dashboard.html");		
	}
	
	public List<String> fetchOrders(String token, Date from, Date to){
		List<String> jsonStrList = new ArrayList<String>();
		try {
			String jsonStr = "";
			boolean isNext = true;
			long fromSec = from.getTime()/1000;
			long fromTo = to.getTime()/1000;
			String targetUrl = "http://developer.shopclues.com/api/v1/order?time_from="+fromSec+"&time_to="+fromTo;
			Client client = ClientBuilder.newClient();
			while(isNext){
				jsonStr = client
				            .target(targetUrl)
				            .request()
				            .header("Authorization", token)
				            .header("Content-Type", "application/json")
				            .get(String.class);
				jsonStrList.add(jsonStr);
				JSONObject jsonObject = new JSONObject(jsonStr);
				JSONObject newObj = jsonObject.getJSONObject("extra");
				if(!newObj.get("next").toString().equals("")){
					targetUrl = newObj.get("next").toString();
				} else {
					isNext = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonStrList;
	}
}
