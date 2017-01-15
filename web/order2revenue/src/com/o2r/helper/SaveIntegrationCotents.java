package com.o2r.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.o2r.model.Order;
import com.o2r.model.Product;
import com.o2r.model.ProductConfig;
import com.o2r.service.ProductService;


@Service("saveIntegrationCotents")
@Transactional
public class SaveIntegrationCotents {
	
	@Autowired
	private ProductService productService;
	
	static Logger log = Logger.getLogger(SaveContents.class.getName());
	
	public String saveAPIFetchProducts(List<String> productList, int sellerId )
			throws IOException {

		log.info("$$$ saveAPIFetchProducts starts : SaveIntegrationCotents $$$");
		List<Product> saveList = null;		
		try {			
			saveList = new ArrayList<Product>();			
			if(productList != null && productList.size() != 0){
				for(String eachProductString : productList){
					
					boolean validate = true;
					JSONObject jsonObject = new JSONObject(eachProductString);
					JSONArray jsonArray = jsonObject.getJSONArray("data");
					
					if(jsonArray.length() != 0){
						for(int each = 0; each < jsonArray.length(); each++){
							
							validate = true;
							JSONObject eachProductObject = jsonArray.getJSONObject(each);
							JSONArray inventoryObject = eachProductObject.getJSONArray("inventoryDetails");
							
							Product product = new Product();
							//product.setProductName(eachProductObject.getString("SKUName"));
							
							if(eachProductObject.getString("MasterSKU") != ""){
								product.setProductSkuCode(eachProductObject.getString("MasterSKU"));
							} else {
								validate = false;
							}				
							
							if(eachProductObject.has("Length")){
								try {
									product.setLength((float) eachProductObject.getDouble("Length"));
								} catch (Exception e) {
									validate = false;
								}						
							} else {
								validate = false;
							}
							
							if(eachProductObject.has("Height")){
								try {
									product.setHeight((float)eachProductObject.getDouble("Height"));
								} catch (Exception e) {
									validate = false;
								}						
							} else {
								validate = false;
							}
							
							if(eachProductObject.has("Width")){
								try {
									product.setBreadth((float) eachProductObject.getDouble("Width"));
								} catch (Exception e) {
									validate = false;
								}						
							} else {
								validate = false;
							}
							
							if(eachProductObject.has("Weight")){
								try {
									product.setDeadWeight((float) eachProductObject.getDouble("Weight"));
								} catch (Exception e) {
									validate = false;
								}						
							} else {
								validate = false;
							}
							
							/*if(eachProductObject.getString("MRP").toString() != ""){
								try {
									product.setProductPrice(Float.parseFloat(eachProductObject.getString("MRP")));
								} catch (Exception e) {
									validate = false;
								}						
							} else {
								validate = false;
							}*/
							
							if(inventoryObject != null && inventoryObject.length() != 0){
								JSONObject eachInventoryObject = inventoryObject.getJSONObject(0);
								
								if(eachInventoryObject.has("Quantity")){
									try {
										product.setQuantity(eachInventoryObject.getLong("Quantity"));
									} catch (Exception e) {
										validate = false;
									}						
								} else {
									validate = false;
								}
								
								/*if(eachInventoryObject.has("SellingPrice")){
									try {
										product.setProductPrice((float) eachInventoryObject.getDouble("SellingPrice"));
									} catch (Exception e) {
										validate = false;
									}						
								} else {
									validate = false;
								}*/
							} else {
								validate = false;
							}
							
							if(validate){
								saveList.add(product);
							} else {
								System.out.println("Failed : "+eachProductObject.toString());
							}
						}
					}			
				}								
			}
			try {
				System.out.println(" SaveList Size : " + saveList.size());
				if (saveList != null && saveList.size() != 0){
					productService.addProduct(saveList, sellerId);
				}					
			} catch (CustomException ce) {
				
			}			
			/*downloadUploadReportXLS(offices, "Create_Parent_Product",
					uploadFileName, 10, errorSet, path, sellerId, uploadReport);*/
		} catch (Exception e) {
			log.debug("Inside save contents exception :"
					+ e.getLocalizedMessage());
			e.printStackTrace();
			log.error("Failed! by SellerId : " + sellerId, e);			
		}
		log.info("$$$ saveAPIFetchProducts ends : SaveIntegrationCotents $$$");
		return "";
	}
	
	public String saveAPIFetchProductMapping(List<String> productMappingList, int sellerId )
			throws IOException {

		log.info("$$$ saveAPIFetchProductMapping starts : SaveIntegrationCotents $$$");
		List<ProductConfig> saveList = null;
		List<String> MasterSKUList = null;
		Map<String, List<ProductConfig>> saveMap = new HashMap<String, List<ProductConfig>>();
		try {			
						
			if(productMappingList != null && productMappingList.size() != 0){
				
				MasterSKUList = productService.listProductSKU(sellerId);
				if(MasterSKUList != null && MasterSKUList.size() != 0){
					
					for(String eachProductconfigString : productMappingList){
						
						boolean validate = true;
						JSONObject jsonObject = new JSONObject(eachProductconfigString);
						JSONArray jsonArray = jsonObject.getJSONArray("data");
						
						if(jsonArray.length() != 0){
							for(int each = 0; each < jsonArray.length(); each++){
								JSONObject eachProductconfigObject = jsonArray.getJSONObject(each);
								
								validate = true;
								ProductConfig productConfig = null;			
								
								if(eachProductconfigObject.opt("MasterSKU") != null && eachProductconfigObject.getString("MasterSKU") != ""){
									
									if(saveMap.containsKey(eachProductconfigObject.getString("MasterSKU"))){
										saveList = saveMap.get(eachProductconfigObject.getString("MasterSKU"));
									} else {
										saveList = new ArrayList<ProductConfig>();
									}
									
									if(MasterSKUList.contains(eachProductconfigObject.getString("MasterSKU"))){
										productConfig = new ProductConfig();
										try {								
											productConfig.setProductSkuCode(eachProductconfigObject.getString("MasterSKU"));
											
											if(eachProductconfigObject.opt("ChannelListingId") != null && eachProductconfigObject.getString("ChannelListingId") != ""){
												productConfig.setChannelSkuRef(eachProductconfigObject.getString("ChannelListingId"));						
											} else {
												validate = false;
											}
											
											if(eachProductconfigObject.opt("ChannelSKU") != null && eachProductconfigObject.getString("ChannelSKU") != ""){
												productConfig.setVendorSkuRef(eachProductconfigObject.getString("ChannelSKU"));						
											} else {
												validate = false;
											}
											
											if(eachProductconfigObject.opt("Channel") != null && eachProductconfigObject.getString("Channel") != ""){
												productConfig.setChannelName(eachProductconfigObject.getString("Channel"));						
											} else {
												validate = false;
											}
											
										} catch (JSONException e) {
											validate = false;
										}							
									} else {
										System.out.println("No product For : "+eachProductconfigObject.getString("MasterSKU"));
										validate = false;
									}
								} else {
									validate = false;
								}				
								
								if(validate && productConfig != null){
									saveList.add(productConfig);
									saveMap.put(productConfig.getProductSkuCode(), saveList);
								} else {
									System.out.println("Failed : "+eachProductconfigObject.toString());
								}
							}
						}
						
					}
				}											
			}
			try {
				System.out.println(" SaveList Size : " + saveList.size());
				if (saveMap != null && saveMap.size() != 0){
					productService.addSKUMapping(saveMap, sellerId);
				}
			} catch (Exception e) {
				log.error("Failed during Saving ProductConfig From API by SellerID : "+sellerId, e);
				e.printStackTrace();
			}			
			/*downloadUploadReportXLS(offices, "Create_Parent_Product",
					uploadFileName, 10, errorSet, path, sellerId, uploadReport);*/
		} catch (Exception e) {
			log.debug("Inside save contents exception :"
					+ e.getLocalizedMessage());
			e.printStackTrace();
			log.error("Failed! by SellerId : " + sellerId, e);
			/*addErrorUploadReport("Create_Parent_Product", sellerId,
					uploadReport);
			throw new MultipartException("Constraints Violated");*/
		}
		log.info("$$$ saveAPIFetchProductMapping ends : SaveIntegrationCotents $$$");
		return "";
	}
	
	
	public String saveAPIFetchOrders(List<String> orderList, int sellerId )
			throws IOException {

		log.info("$$$ saveAPIFetchOrders starts : SaveIntegrationCotents $$$");
		List<Order> saveList = null;
		List<String> MasterSKUList = null;
		try {			
			saveList = new ArrayList<Order>();			
			if(orderList != null && orderList.size() != 0){
				
				MasterSKUList = productService.listProductSKU(sellerId);
				if(MasterSKUList != null && MasterSKUList.size() != 0){
					
					for(String eachOrderString : orderList){
						
						boolean validate = true;
						JSONObject jsonObject = new JSONObject(eachOrderString);
						JSONArray jsonArray = jsonObject.getJSONArray("data");
						
						if(jsonArray.length() != 0){
							for(int each = 0; each < jsonArray.length(); each++){
								JSONObject eachOrderObject = jsonArray.getJSONObject(each);
								
								validate = true;
								Order order = new Order();			
								
												
								
								if(validate && order != null){
									saveList.add(order);
								} else {
									System.out.println("Failed : "+eachOrderObject.toString());
								}
							}
						}						
					}
				}											
			}
			try {
				System.out.println(" SaveList Size : " + saveList.size());
				if (saveList != null && saveList.size() != 0){
					
				}
			} catch (Exception ce) {
				
			}			
			/*downloadUploadReportXLS(offices, "Create_Parent_Product",
					uploadFileName, 10, errorSet, path, sellerId, uploadReport);*/
		} catch (Exception e) {
			log.debug("Inside save contents exception :"
					+ e.getLocalizedMessage());
			e.printStackTrace();
			log.error("Failed! by SellerId : " + sellerId, e);
			/*addErrorUploadReport("Create_Parent_Product", sellerId,
					uploadReport);
			throw new MultipartException("Constraints Violated");*/
		}
		log.info("$$$ saveAPIFetchOrders ends : SaveIntegrationCotents $$$");
		return "";
	}

}
