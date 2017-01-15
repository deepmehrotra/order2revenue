package com.o2r.helper;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.o2r.model.Seller;
import com.o2r.model.SellerAPIInfo;
import com.o2r.service.SellerService;

@Service("integrationFetchClient")
public class IntegrationFetchClient {
	
	@Autowired
	private SellerService sellerService;
	static int count;
	
	public List<String> fetchAPIProduct(int sellerId){
		List<String> jsonStrList = new ArrayList<String>();
		SellerAPIInfo sellerInfo = null;
		try {	
			sellerInfo = sellerService.getSellerApiInfo(sellerId);
			String targetUrl = "http://apisseller78.sellerware.com/FilterMasterProducts";			
			/*boolean isNext = true;	
			while (isNext){
				sellerInfo = sellerService.getSellerApiInfo(sellerId);
				if(sellerInfo != null){
					String jsonStr = "";
					String targetUrl = "http://apisseller78.sellerware.com/FilterMasterProducts";
					Client client = ClientBuilder.newClient();
					String token = "bearer "+sellerInfo.getTokenUserKey();			
					String body = "{\"Email\":\""
									+ sellerInfo.getPageSize()
									+ "\",\"PageNumber\":\"1\"}";
					jsonStr = client
				            .target(targetUrl)
				            .request()
				            .header("Authorization", token)
				            .header("Content-Type", "application/json")
				            .post(Entity.entity(body, MediaType.APPLICATION_JSON))
				            .readEntity(String.class);
					System.out.println(jsonStr);
					if(jsonStr != ""){
						JSONObject jsonObject = new JSONObject(jsonStr);
						
					} else {
						throw new Exception("Unable to get Master Product !!!");
					}
				} else {
					generateToken(sellerId);
				}
				
			}*/	
			
			if(sellerInfo != null){	
				String body = "{\"PageSize\":\""
						+ sellerInfo.getPageSize()
						+ "\",\"PageNumber\":\"1\"}";
				jsonStrList = fetchAPICode(0, targetUrl, body, sellerInfo);
			} else {				
				generateToken(sellerId);
				sellerInfo = sellerService.getSellerApiInfo(sellerId);
				if(sellerInfo != null){		
					String body = "{\"PageSize\":\""
							+ sellerInfo.getPageSize()
							+ "\",\"PageNumber\":\"1\"}";
					jsonStrList = fetchAPICode(0, targetUrl, body, sellerInfo);					
				} else {
					throw new Exception("Unable to get SellerApiInfo !!!");
				}			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonStrList;
	}
	
	public List<String> fetchAPIProductMapping(int sellerId, int channelId){
		List<String> jsonStrList = new ArrayList<String>();
		SellerAPIInfo sellerInfo = null;
		try {
			sellerInfo = sellerService.getSellerApiInfo(sellerId);
			String targetUrl = "http://apisseller78.sellerware.com/FilterChannelProducts";			
			/*boolean isNext = true;	
			while (isNext){
				sellerInfo = sellerService.getSellerApiInfo(sellerId);
				if(sellerInfo != null){
					String jsonStr = "";
					String targetUrl = "http://apisseller78.sellerware.com/FilterMasterProducts";
					Client client = ClientBuilder.newClient();
					String token = "bearer "+sellerInfo.getTokenUserKey();			
					String body = "{\"Email\":\""
									+ sellerInfo.getPageSize()
									+ "\",\"PageNumber\":\"1\"}";
					jsonStr = client
				            .target(targetUrl)
				            .request()
				            .header("Authorization", token)
				            .header("Content-Type", "application/json")
				            .post(Entity.entity(body, MediaType.APPLICATION_JSON))
				            .readEntity(String.class);
					System.out.println(jsonStr);
					if(jsonStr != ""){
						JSONObject jsonObject = new JSONObject(jsonStr);
						
					} else {
						throw new Exception("Unable to get Master Product !!!");
					}
				} else {
					generateToken(sellerId);
				}
				
			}*/	
			
			if(sellerInfo != null){	
				String body = "{\"PageSize\":\""
						+ sellerInfo.getPageSize()
						+ "\",\"PageNumber\":\"1"
						+ "\",\"ChannelId\":\""
						+ channelId
						+ "\"}";
				jsonStrList = fetchAPICode(channelId, targetUrl, body, sellerInfo);
			} else {				
				generateToken(sellerId);
				sellerInfo = sellerService.getSellerApiInfo(sellerId);
				if(sellerInfo != null){		
					String body = "{\"PageSize\":\""
							+ sellerInfo.getPageSize()
							+ "\",\"PageNumber\":\"1"
							+ "\",\"ChannelId\":\""
							+ channelId
							+ "\"}";
					jsonStrList = fetchAPICode(channelId ,targetUrl, body, sellerInfo);					
				} else {
					throw new Exception("Unable to get SellerApiInfo !!!");
				}			
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonStrList;
	}
	
	public List<String> fetchAPICode(int channelId, String url, String body, SellerAPIInfo sellerInfo){
		List<String> jsonStrList = new ArrayList<String>();
		try {
			
			boolean isNext = true;						
			Client client = ClientBuilder.newClient();
			String token = "bearer "+sellerInfo.getTokenUserKey();
			
			while(isNext){
				String jsonStr = "";
				jsonStr = client
			            .target(url)
			            .request()
			            .header("Authorization", token)
			            .header("Content-Type", "application/json")
			            .post(Entity.entity(body, MediaType.APPLICATION_JSON))
			            .readEntity(String.class);
				System.out.println(jsonStr);
				if(jsonStr != ""){
					JSONObject jsonObject = new JSONObject(jsonStr);
					try {
						if(jsonObject.has("status") && jsonObject.isNull("status") && jsonObject.has("messages")){
							generateToken(sellerInfo.getSellerId());
							sellerInfo = sellerService.getSellerApiInfo(sellerInfo.getSellerId());
							token = "bearer "+sellerInfo.getTokenUserKey();		
							jsonStr = client
						            .target(url)
						            .request()
						            .header("Authorization", token)
						            .header("Content-Type", "application/json")
						            .post(Entity.entity(body, MediaType.APPLICATION_JSON))
						            .readEntity(String.class);
							jsonObject = new JSONObject(jsonStr);
							if(jsonObject.has("status") && jsonObject.isNull("status") && jsonObject.has("messages")){
								jsonStr = "";
								isNext = false;
								throw new Exception("Unable to get Data After Regenerate the Token !!!");
							}
						}
					} catch (JSONException e) {
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(jsonStr != ""){
						jsonStrList.add(jsonStr);
						if(jsonObject.opt("PageSize") != null && jsonObject.getInt("PageSize") != 0 
								&& jsonObject.opt("PageNumber") != null && jsonObject.getInt("PageNumber") != 0
								&& jsonObject.opt("TotalCount") != null && jsonObject.getInt("TotalCount") != 0){
							int pageSize = jsonObject.getInt("PageSize");
							int pageNumber	= jsonObject.getInt("PageNumber");
							int totalCount	= jsonObject.getInt("TotalCount");
							if((pageNumber*pageSize) < totalCount){
								if(body.contains("ChannelId") && channelId != 0){
									body = "{\"PageSize\":\""
											+ sellerInfo.getPageSize()
											+ "\",\"PageNumber\":\""
											+ (pageNumber+1)
											+ "\",\"ChannelId\":\""
											+ channelId
											+ "\"}";
								} else {
									body = "{\"PageSize\":\""
											+ sellerInfo.getPageSize()
											+ "\",\"PageNumber\":\""
											+ (pageNumber+1)
											+ "\"}";
								}								
							} else {
								isNext = false;
							}
						} else {
							isNext = false;
						}
					}				
				} else {
					isNext = false;
					throw new Exception("Unable to get Required Data !!!");
				}
			}						
			
			/*while(isNext){
				jsonStr = client
				            .target(targetUrl)
				            .request()				            
				            .get(String.class);
				jsonStrList.add(jsonStr);
				JSONObject jsonObject = new JSONObject(jsonStr);
				JSONObject newObj = jsonObject.getJSONObject("extra");
				if(!newObj.get("next").toString().equals("")){
					targetUrl = newObj.get("next").toString();
				} else {
					isNext = false;
				}
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonStrList;
	}
	
	public void generateToken(int sellerId){
		
		SellerAPIInfo sInfo = null;
		Seller seller = null;
		try{
			seller = sellerService.getSeller(sellerId);
			if(seller != null){
				Client client = ClientBuilder.newClient();				
				String jsonStr = "";
				sInfo = sellerService.getSellerApiInfo(sellerId);
				if(sInfo == null){
					sInfo = new SellerAPIInfo();
					sInfo.setSellerId(sellerId);
					sInfo.setSellerName(seller.getName());
					sInfo.setPageSize(25);					
				}				
				/*String body = "{\"Email\":\""
								+ seller.getEmail()
								+ "\",\"Password\":\""
								+ decodePass(seller.getPassword())
								+ "\"}";*/
				String body = "{\"Email\":\"dhaval@incynk.com\",\"Password\":\"Incynk@123\"}";
				
				jsonStr = client
			            .target("http://apisseller78.sellerware.com/UserLogin")
			            .request()
			            .header("Content-Type", "application/json")
			            .post(Entity.entity(body, MediaType.APPLICATION_JSON))
			            .readEntity(String.class);
				if(jsonStr != null & !jsonStr.equals("")){
					JSONObject jsonObject = new JSONObject(jsonStr);
					if(jsonObject.has("TokenUserKey")){
						sInfo.setTokenUserKey(jsonObject.getString("TokenUserKey"));
						sellerService.saveSellerApiInfo(sInfo);
					} else {
						throw new Exception("TokenUserKey not Present !!!");
					}
				} else {
					throw new Exception("Not able to get Seller Login Token !!!");
				}
			} else {
				throw new Exception("Seller Not Present !!!");
			}			
		}catch(Exception e){
			//log.error("Failed! by sellerId : "+sellerId,e);
			e.printStackTrace();
		}	
	}
	
	public String decodePass(String hexa){
		String password = "";
		try {
			password = new String(hexStringToByteArray(hexa),StandardCharsets.UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return password;
	}
	public static byte[] hexStringToByteArray(String hex) {
	    int l = hex.length();
	    byte[] data = new byte[l/2];
	    for (int i = 0; i < l; i += 2) {
	        data[i/2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
	                             + Character.digit(hex.charAt(i+1), 16));
	    }
	    return data;
	}
}
