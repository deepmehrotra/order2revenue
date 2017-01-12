package com.o2r.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import com.o2r.bean.ProductBean;
import com.o2r.model.Category;
import com.o2r.model.Product;
import com.o2r.model.ProductConfig;
import com.o2r.model.UploadReport;
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
		String jsonString = "";
		boolean validate = true;
		try {			
			saveList = new ArrayList<Product>();			
			if(productList != null && productList.size() != 0){
				jsonString = productList.get(0);
				if(jsonString != ""){
					JSONObject jsonObject = new JSONObject(jsonString);
					Product product = new Product();
					product.setProductName(jsonObject.getString("SKUName"));
					
					if(jsonObject.getString("ParentSkuCode") != ""){
						Product returnObj = productService.getProduct(jsonObject.getString("ParentSkuCode"), sellerId);
						if(returnObj != null){
							validate = false;
						} else {
							product.setProductSkuCode(jsonObject.getString("ParentSkuCode"));
						}
					} else {
						validate = false;
					}
					
					product.setProductPrice((float) jsonObject.getDouble("DirectproductCost"));
					
					if(jsonObject.getString("Length").toString() != ""){
						try {
							product.setLength(Float.parseFloat(jsonObject.getString("Length")));
						} catch (Exception e) {
							validate = false;
						}						
					} else {
						validate = false;
					}
					
					if(jsonObject.getString("Height").toString() != ""){
						try {
							product.setHeight(Float.parseFloat(jsonObject.getString("Height")));
						} catch (Exception e) {
							validate = false;
						}						
					} else {
						validate = false;
					}
					
					if(jsonObject.getString("Weight").toString() != ""){
						try {
							product.setDeadWeight(Float.parseFloat(jsonObject.getString("Weight")));
						} catch (Exception e) {
							validate = false;
						}						
					} else {
						validate = false;
					}
					
					if(validate){
						saveList.add(product);
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
		String jsonString = "";
		boolean validate = true;
		try {			
			saveList = new ArrayList<ProductConfig>();			
			if(productMappingList != null && productMappingList.size() != 0){
				jsonString = productMappingList.get(0);
				if(jsonString != ""){
					JSONObject jsonObject = new JSONObject(jsonString);
					ProductConfig productConfig = new ProductConfig();
					if(jsonObject.getString("ParentSkuCode") != ""){
						Product returnObj = productService.getProduct(jsonObject.getString("ParentSkuCode"), sellerId);
						if(returnObj != null){
							productConfig.setProductSkuCode(jsonObject.getString("ParentSkuCode"));
						} else {
							validate = false;
						}
					} else {
						validate = false;
					}
					
					if(validate){
						saveList.add(productConfig);
					}					
				}				
			}
			try {
				System.out.println(" SaveList Size : " + saveList.size());
				if (saveList != null && saveList.size() != 0)
					productService.addSKUMapping(saveList, sellerId);
			} catch (CustomException ce) {
				
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

}
