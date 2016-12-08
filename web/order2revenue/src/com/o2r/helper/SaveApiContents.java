package com.o2r.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.o2r.bean.ShopcluesOrderAPIBean;
import com.o2r.model.ShopcluesOrderAPI;
import com.o2r.service.APIService;

@Service("saveApiContents")
@Transactional
public class SaveApiContents {
	
	@Autowired
	private APIService apiService;
	
	public void saveShopcluesOrderAPI(int sellerID, List<String> jsonList){
		List<ShopcluesOrderAPIBean> shopcluesOrderBeans = new ArrayList<ShopcluesOrderAPIBean>();
		try {
			if(jsonList != null && jsonList.size() != 0){
				for(String jsonString : jsonList){
					JSONObject jsonObject = new JSONObject(jsonString);
					JSONArray jsonArray = jsonObject.getJSONArray("data");
					
					if(jsonArray.length() != 0){
						for(int each = 0; each < jsonArray.length(); each++){
							
							JSONObject eachObject = jsonArray.getJSONObject(each);
							JSONArray itemsList = eachObject.getJSONArray("items_list");
							
							ShopcluesOrderAPIBean orderBean = new ShopcluesOrderAPIBean();
							orderBean.setSellerId(sellerID);
							orderBean.setOrderStatus("new");
							orderBean.setOrder_id(eachObject.getInt("order_id"));
							orderBean.setIs_parent_order(eachObject.getString("is_parent_order"));
							orderBean.setExempt_from_billing(eachObject.getInt("exempt_from_billing"));
							orderBean.setParent_order_id(eachObject.getInt("parent_order_id"));
							orderBean.setCompany_id(eachObject.getInt("company_id"));
							orderBean.setTimestamp(new Date(eachObject.getLong("timestamp") * 1000L));
							orderBean.setStatus(eachObject.getString("status"));
							orderBean.setTotal(eachObject.getDouble("total"));
							orderBean.setSubtotal(eachObject.getDouble("subtotal"));
							orderBean.setDetails(eachObject.getString("details"));
							orderBean.setPayment_id(eachObject.getInt("payment_id"));
							orderBean.setS_city(eachObject.getString("s_city"));
							orderBean.setS_state(eachObject.getString("s_state"));
							orderBean.setS_zipcode(eachObject.getInt("s_zipcode"));
							orderBean.setLabel_printed(eachObject.getString("label_printed"));
							orderBean.setGift_it(eachObject.getString("gift_it"));
							orderBean.setFirstname(eachObject.getString("firstname"));
							orderBean.setLastname(eachObject.getString("lastname"));
							orderBean.setPayment_type(eachObject.getString("payment_type"));
							
							if(itemsList.length() > 1){
								for(int eachItem = 0; eachItem < itemsList.length(); eachItem++){
									ShopcluesOrderAPIBean newOrderBean = orderBean;
									JSONObject eachItemObject = itemsList.getJSONObject(eachItem);
									//newOrderBean.setCredit_used(eachItemObject.getDouble("credit_used"));
									newOrderBean.setProduct_name(eachItemObject.getString("product_name"));
									newOrderBean.setProduct_id(eachItemObject.getInt("product_id"));
									newOrderBean.setQuantity(eachItemObject.getInt("quantity"));
									newOrderBean.setSelling_price(eachItemObject.getDouble("selling_price"));
									newOrderBean.setImage_path(eachItemObject.getString("image_path"));
									newOrderBean.setShopcluesUniqueId(newOrderBean.getOrder_id()+GlobalConstant.orderUniqueSymbol+newOrderBean.getProduct_id());
									shopcluesOrderBeans.add(newOrderBean);
								}							
							} else {
								JSONObject eachItemObject = itemsList.getJSONObject(0);
								orderBean.setProduct_id(eachItemObject.getInt("product_id"));
								//orderBean.setCredit_used(eachItemObject.getDouble("credit_used"));
								orderBean.setProduct_name(eachItemObject.getString("product_name"));							
								orderBean.setQuantity(eachItemObject.getInt("quantity"));
								orderBean.setSelling_price(eachItemObject.getDouble("selling_price"));
								orderBean.setImage_path(eachItemObject.getString("image_path"));
								orderBean.setShopcluesUniqueId(orderBean.getOrder_id()+GlobalConstant.orderUniqueSymbol+orderBean.getProduct_id());
								shopcluesOrderBeans.add(orderBean);
							}
						}
					}
				}
			}
			
			if(shopcluesOrderBeans.size() != 0){
				apiService.saveShopcluesOrderFromAPI(sellerID, ConverterClass.listShopcluesOrderModel(shopcluesOrderBeans));
			}
			
		} catch (Exception e) {
			e.printStackTrace();			
		}
	}
	
	public void saveShopcluesOrderAPIBeanToO2R(int sellerID, String ids, Map<Integer, ShopcluesOrderAPI> orderMap){
		
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
