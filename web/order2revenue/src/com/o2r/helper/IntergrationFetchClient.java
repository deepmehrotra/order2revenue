package com.o2r.helper;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.springframework.stereotype.Service;

@Service("intergrationFetchClient")
public class IntergrationFetchClient {
	
	public List<String> fetchAPIProduct(){
		List<String> jsonStrList = new ArrayList<String>();
		try {
			String jsonStr = "";
			//boolean isNext = true;			
			String targetUrl = "http://sellerwaretestmobileapi.azurewebsites.net/GetMasterDetailsById/1";
			Client client = ClientBuilder.newClient();
			jsonStr = client
		            .target(targetUrl)
		            .request()				            
		            .get(String.class);
			jsonStrList.add(jsonStr);
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
	
	public List<String> fetchAPIProductMapping(){
		List<String> jsonStrList = new ArrayList<String>();
		try {
			String jsonStr = "";
			//boolean isNext = true;			
			String targetUrl = "http://sellerwaretestmobileapi.azurewebsites.net/GetMasterDetailsById/1";
			Client client = ClientBuilder.newClient();
			jsonStr = client
		            .target(targetUrl)
		            .request()				            
		            .get(String.class);
			jsonStrList.add(jsonStr);
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
}
