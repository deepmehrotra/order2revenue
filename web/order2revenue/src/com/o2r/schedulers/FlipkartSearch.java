package com.o2r.schedulers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.o2r.bean.FlipkartCustomerBean;
import com.o2r.bean.OrderItemsBean;
import com.o2r.model.SellerAuthInfo;


public class FlipkartSearch {

	@Autowired	
	 private SessionFactory sessionFactory;
	
	static Logger log = Logger.getLogger(FlipkartSearch.class.getName());
	
	public void fixedDelayTask() throws IOException,JsonParseException, JsonMappingException, IOException {
		
		log.info("************************ FlipkartSearch fixedDelayTask started");
		
	try{
		ObjectMapper mapper = new ObjectMapper().
		          disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES	);
		
		org.springframework.core.io.Resource resource = new ClassPathResource(
				"flipkart_url.properties");
		
		
		Properties props = PropertiesLoaderUtils.loadProperties(resource);
		
		
		String searchUrl=props.getProperty("domain")+""+props.getProperty("searchURL");
		String shipmentUrl=props.getProperty("domain")+""+props.getProperty("shipmentURL");
		
	       
	       DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
	       Date date = new Date();
	       
	       String tempDate=dateFormat.format(date).toString();
	       Calendar cal = Calendar.getInstance();
	       cal.add(Calendar.HOUR, -1);
	       DateFormat dateFormat1 = new SimpleDateFormat("HH");
	       Date oneHourBack = cal.getTime();
	       
	       String tempFromDate=dateFormat1.format(oneHourBack).toString();
	       
	       String[] tempDateArray=tempDate.split(" ");
	       
	       String toDate=tempDateArray[0]+"T"+tempFromDate+":59:59+05:30";
	       
	       String fromDate=tempDateArray[0]+"T"+tempFromDate+":00:00+05:30";
	       
		
		
		StringEntity postingString = new StringEntity("{\"filter\":"
				+ " {\"orderDate\":{\"fromDate\":\""+fromDate+"\",\"toDate\":\""+toDate+"\"},"
				+ " \"states\": [\"APPROVED\"]},\"pagination\":"
				+ " {\"pageSize\": 20},\"sort\": {\"field\": "
				+ "\"dispatchByDate\",\"order\": \"asc\"}}");
		
		
		
		List<SellerAuthInfo> result = fetchSellerAuthInfo();
		
		if(result.size()!=0){
			
			for(int i=0;i<result.size();i++){
				String sellerId=result.get(i).getSellerId();
				String accessToken=result.get(i).getMwsAuthToken();
			
				String token="Bearer "+accessToken;
				
				String finalOutput = executePost(searchUrl,token,postingString);
		         
				
				 JSONObject jsonObject = new JSONObject(finalOutput);
		         JSONArray jsonArray = jsonObject.getJSONArray("orderItems");
		         
		         if(jsonArray.length()!=0){
		         
		        	 for (int j = 0; j < jsonArray.length(); j++) {
		        		 JSONObject explrObject = jsonArray.getJSONObject(j);
		        		 OrderItemsBean orItemsBean = mapper.readValue(explrObject.toString(), OrderItemsBean.class);
		        		 
		        		 if(checkOrderItem(orItemsBean.getOrderItemId())){
		        			
		        			String customerDetails=executeGet(orItemsBean.getOrderItemId(),shipmentUrl,token);
		        			FlipkartCustomerBean customerBean=fetchCustomer(customerDetails);
		        			
		        			orItemsBean.setCustomerBean(customerBean);
		        			orItemsBean.setSellerId(sellerId);
		        			insertSearchDetails(orItemsBean);
		        		 }
		        	 
		        	}
		         }
			
			}
		}
		
		log.info("************************ FlipkartSearch fixedDelayTask end");
		
	} catch (Exception e) {
		log.error("************************ FlipkartSearch fixedDelayTask : "+ e);
       }
	
		
		
	 }
	
public String executePost(String searchUrl,String token,StringEntity postingString){
	
	log.info("************************ FlipkartSearch executePost start");
		
	try {
		HttpPost post = new HttpPost(searchUrl);
		
		HttpClient httpClient = HttpClientBuilder.create().build();
		
		post.setEntity(postingString);
		
		post.setHeader("authorization", token);
		post.setHeader("Content-type", "application/json");
		HttpResponse response;
		
			response = httpClient.execute(post);
		
		 String finalOutput="";
		
	         
	         InputStream content = response.getEntity().getContent();

	         BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
	         String s = "";
	         while ((s = buffer.readLine()) != null) {
	        	 finalOutput += s;
	         }
		
	         log.info("************************ FlipkartSearch executePost end "+finalOutput);     
		return finalOutput;
	} catch (ClientProtocolException clientProtocolException) {
		log.error("************************ FlipkartSearch executePost : "+clientProtocolException);
		return clientProtocolException.toString();
		
	} catch (IOException ioException) {
		log.error("************************ FlipkartSearch executePost : "+ioException);
		return ioException.toString();
	}
	
	}


	public boolean checkOrderItem(long orderItemId){
		log.info("************************ checkOrderItem checkOrderItem start");  
		
		Session session = sessionFactory.openSession();
		
		Criteria criteria = session.createCriteria(OrderItemsBean.class);
		criteria.add(Restrictions.eq("orderItemId", orderItemId));
		
		log.info("************************ FlipkartSearch checkOrderItem end"); 
		if(criteria.uniqueResult()==null){
			return true;
		}else{
			return false;
		}
		
		 
		
	}

	public void insertSearchDetails(OrderItemsBean orderItem){
		log.info("************************ FlipkartSearch insertSearchDetails start"); 
		Session session = sessionFactory.openSession();
		
		if(session != null)
			session.beginTransaction(); 
		
		session.save(orderItem);
		session.getTransaction().commit();
		
		session.close();
		log.info("************************ FlipkartSearch insertSearchDetails end"); 
		
	}

	public List<SellerAuthInfo> fetchSellerAuthInfo(){
		
		log.info("************************ FlipkartSearch fetchSellerAuthInfo start"); 
		Session session = sessionFactory.openSession();
		
		Criteria criteria = session.createCriteria(SellerAuthInfo.class);
		criteria.add(Restrictions.like("pcName", "flipkart", MatchMode.START));
		
		List<SellerAuthInfo> results = criteria.list();

		log.info("************************ FlipkartSearch fetchSellerAuthInfo end : "+results); 
		return results;
		
		
	}
	
	
	public FlipkartCustomerBean fetchCustomer(String details){
		log.info("************************ FlipkartSearch fetchCustomer start");
		
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(details);
			JSONArray jsonArray = jsonObject.getJSONArray("shipments");
			
			if(jsonArray.length()!=0){
				  ObjectMapper mapper = new ObjectMapper().
				          disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES	);
		        FlipkartCustomerBean customerBean = new FlipkartCustomerBean();
				
		        customerBean.setFirstName(jsonArray.getJSONObject(0).getJSONObject("deliveryAddress").get("firstName").toString());
		        customerBean.setLastName(jsonArray.getJSONObject(0).getJSONObject("deliveryAddress").get("lastName").toString());
		        customerBean.setPincode(jsonArray.getJSONObject(0).getJSONObject("deliveryAddress").get("pincode").toString());
		        customerBean.setCity(jsonArray.getJSONObject(0).getJSONObject("deliveryAddress").get("city").toString());
		        customerBean.setStateName(jsonArray.getJSONObject(0).getJSONObject("deliveryAddress").get("stateName").toString());
		        customerBean.setAddressLine1(jsonArray.getJSONObject(0).getJSONObject("deliveryAddress").get("addressLine1").toString());
		        customerBean.setAddressLine2(jsonArray.getJSONObject(0).getJSONObject("deliveryAddress").get("addressLine2").toString());
		        customerBean.setStateCode(jsonArray.getJSONObject(0).getJSONObject("deliveryAddress").get("stateCode").toString());
		        customerBean.setState(jsonArray.getJSONObject(0).getJSONObject("deliveryAddress").get("state").toString());
		        customerBean.setLandmark(jsonArray.getJSONObject(0).getJSONObject("deliveryAddress").get("landmark").toString());
		        customerBean.setContactNumber(jsonArray.getJSONObject(0).getJSONObject("buyerDetails").get("contactNumber").toString());
		        customerBean.setPrimaryEmail(jsonArray.getJSONObject(0).getJSONObject("buyerDetails").get("primaryEmail").toString());
		        
		        log.info("************************ FlipkartSearch fetchCustomer end");
		        return customerBean;
			}
			
			
		} catch (JSONException e) {
			log.error("************************ FlipkartSearch fetchCustomer error : "+e);
		}
		return null;
        
	}
	
	public String executeGet(long orderItemId,String shipmentUrl,String token){
		
		log.info("************************ FlipkartSearch executeGet start "+orderItemId +" "+shipmentUrl+" "+token);
		
		try {
			
			shipmentUrl=shipmentUrl+orderItemId;
			
			HttpGet get = new HttpGet(shipmentUrl);
			
			HttpClient httpClient = HttpClientBuilder.create().build();
			
			
			get.setHeader("authorization", token);
			get.setHeader("Content-type", "application/json");
			HttpResponse response;
			
				response = httpClient.execute(get);
			
			 String finalOutput="";
			
		         
		         InputStream content = response.getEntity().getContent();

		         BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
		         String s = "";
		         while ((s = buffer.readLine()) != null) {
		        	 finalOutput += s;
		         }
			
		         log.info("************************ FlipkartSearch executeGet end "+finalOutput);
		         
			return finalOutput;
		} catch (ClientProtocolException clientProtocolException) {
			 log.error("************************ FlipkartSearch executeGet error "+clientProtocolException);
			return clientProtocolException.toString();
			
		} catch (IOException ioException) {
			log.error("************************ FlipkartSearch executeGet error "+ioException);
			return ioException.toString();
		}
	}
}
