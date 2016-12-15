package com.o2r.helper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.o2r.bean.CustomerBean;
import com.o2r.bean.OrderBean;
import com.o2r.bean.OrderTaxBean;
import com.o2r.bean.ShopcluesOrderAPIBean;
import com.o2r.dao.AreaConfigDao;
import com.o2r.model.ChannelUploadMapping;
import com.o2r.model.ColumMap;
import com.o2r.model.Events;
import com.o2r.model.Order;
import com.o2r.model.Partner;
import com.o2r.model.Product;
import com.o2r.model.ProductConfig;
import com.o2r.model.ShopcluesOrderAPI;
import com.o2r.model.TaxCategory;
import com.o2r.model.UploadReport;
import com.o2r.service.APIService;
import com.o2r.service.EventsService;
import com.o2r.service.OrderService;
import com.o2r.service.PartnerService;
import com.o2r.service.ProductService;
import com.o2r.service.TaxDetailService;

@Service("saveApiContents")
@Transactional
public class SaveApiContents {
	
	@Autowired
	private APIService apiService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private PartnerService partnerService;
	@Autowired
	private ProductService productService;
	@Autowired
	private AreaConfigDao areaConfigDao;
	@Autowired
	private EventsService eventsService;
	@Autowired
	private TaxDetailService taxDetailService;
	
	private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
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
	
	public void saveShopcluesOrderAPIBeanToO2R(int sellerId, String ids, Map<Integer, ShopcluesOrderAPI> orderMap){
		
		String idList[] = ids.split(",");
		ShopcluesOrderAPI shopcluesOrderApi = null;
		List<ShopcluesOrderAPI> shopcluesOrderApiList = new ArrayList<ShopcluesOrderAPI>();
		StringBuffer errorMessage = new StringBuffer();
		CustomerBean customerBean = null;
		Partner partner = null;
		Events event = null;
		List<Order> saveList = new ArrayList<Order>();
		Map<String, String> o2rIdsList = new HashMap<String, String>();
		OrderBean order = null;		
		OrderTaxBean orderTax = null;
		boolean validaterow = true;
		Map<String, String> duplicateKey = new HashMap<String, String>();		
		
		try {
			o2rIdsList = orderService.listOrderIds("channelOrderID", sellerId);
			partner = partnerService.getPartner("shopclues", sellerId);
			if(idList.length != 0 && orderMap.size() != 0){
				for(String orderId : idList){
					shopcluesOrderApi = orderMap.get(Integer.parseInt(orderId));
					if(shopcluesOrderApi != null){
						validaterow = true;
						errorMessage = new StringBuffer("Msg : ");
						order = new OrderBean();
						customerBean = new CustomerBean();
						orderTax = new OrderTaxBean();
						ProductConfig productConfig = null;
						List<ProductConfig> productConfigs = null;
						String channelorderid = "";
						Product product = null;
						TaxCategory taxcat = null;
						
						
						if(shopcluesOrderApi.getTimestamp() != null){
							order.setOrderDate(shopcluesOrderApi.getTimestamp());
						} else {
							errorMessage = errorMessage.append("Order Received Date is Null.");
							validaterow = false;
						}
						
						if(shopcluesOrderApi.getOrder_id() != 0 && shopcluesOrderApi.getProduct_id() != 0){
							
							if(partner != null){
								order.setPcName(partner.getPcName());
								productConfigs = productService
										.getProductConfig(String.valueOf(shopcluesOrderApi.getProduct_id()).trim(),
										partner.getPcName(), sellerId);
								if (productConfigs != null) {
									if (productConfigs.size() == 1) {
										productConfig = (ProductConfig) productConfigs.get(0);
									} else {
										ProductConfig pc = null;
										Set<String> parent = new HashSet<String>();
										for (Object PCo : productConfigs) {
											pc = (ProductConfig) PCo;
											parent.add(pc.getProductSkuCode());
										}
										if (parent.size() == 1) {
											productConfig = (ProductConfig) productConfigs.get(0);
										} else {
											errorMessage.append(" Multiple Mapping present for this Channel And SKU.");
											validaterow = false;
										}
									}
									if (productConfig.getVendorSkuRef() != null) {
										channelorderid = String.valueOf(shopcluesOrderApi.getOrder_id())
												+ GlobalConstant.orderUniqueSymbol
												+ productConfig.getVendorSkuRef();
									} else {
										errorMessage.append(" Vendor SKU code not mapped.");
										validaterow = false;
									}
								} else {
									errorMessage.append("No Product with this Channel & SKU");
									validaterow = false;
								}
								
							} else {
								errorMessage = errorMessage.append("Sales Channel Not Present.");
								validaterow = false;
							}
						} else {
							errorMessage = errorMessage.append("Order_Id or Product_Id not present.");
							validaterow = false;
						}
						
						if(channelorderid != null && order.getOrderDate() != null){
							String itemID=format.format(order.getOrderDate());
							if(!o2rIdsList.containsKey(channelorderid)
									&& !duplicateKey.containsKey(channelorderid)){
								order.setChannelOrderID(channelorderid);
								if (productConfig != null){
									order.setProductSkuCode(productConfig.getProductSkuCode());	
									order.setPartnerCommission(productConfig.getCommision());
								}
								duplicateKey.put(channelorderid, itemID);
							} else {
								if(itemID != null && ((o2rIdsList.get(channelorderid) != null 
										&& !o2rIdsList.get(channelorderid).equals(itemID))
										|| (duplicateKey.containsKey(channelorderid) 
												&& (duplicateKey.get(channelorderid) == null
												|| !duplicateKey.get(channelorderid).equals(itemID))))){
									channelorderid = channelorderid + GlobalConstant.orderUniqueSymbol + itemID;
									if(!o2rIdsList.containsKey(channelorderid)
											&& !duplicateKey.containsKey(channelorderid)){
										order.setChannelOrderID(channelorderid);
										if (productConfig != null){
											order.setProductSkuCode(productConfig.getProductSkuCode());	
											order.setPartnerCommission(productConfig.getCommision());
										}													
										duplicateKey.put(channelorderid, itemID);
									} else {
										errorMessage.append("Channel Order Id is Already Present.");
										validaterow = false;
									}
								} else {
									errorMessage.append("Channel Order Id is Already Present.");
									validaterow = false;
								}																								
							}
						}
						
						if(shopcluesOrderApi.getS_zipcode() != 0){
							String zipCode = String.valueOf(shopcluesOrderApi.getS_zipcode());
							if (zipCode.contains(".")) {
								zipCode = zipCode.substring(0,
										zipCode.indexOf("."));
							}
							if (areaConfigDao.isZipCodeValid(zipCode)) {
								try {
									customerBean.setZipcode(zipCode);
								} catch (Exception e) {									
									errorMessage.append("Shipping PinCode is corrupted.");
									validaterow = false;
								}
							} else {
								errorMessage.append("Invalid Zipcode Or Not Added To DB.");
								validaterow = false;								
							}
						} else {
							errorMessage.append("Zipcode is Blank.");
							validaterow = false;
						}
						
						
						if(shopcluesOrderApi.getSubtotal() != 0){
							order.setOrderSP(shopcluesOrderApi.getSubtotal());
						} else {
							errorMessage.append("Sub total should not be 0 or blank.");
							validaterow = false;
						}
						
						if(shopcluesOrderApi.getPayment_type() != null && !shopcluesOrderApi.getPayment_type().equals("")){
							if (shopcluesOrderApi.getPayment_type().equalsIgnoreCase("COD"))
								order.setPaymentType(shopcluesOrderApi.getPayment_type());
							else {
								order.setPaymentType("Others");
							}
						} else {
							
						}
						
						if(shopcluesOrderApi.getTimestamp() != null){
							order.setShippedDate(shopcluesOrderApi.getTimestamp());
						} else {
							errorMessage = errorMessage.append("Order Shipped Date is Null.");
							validaterow = false;
						}
						
						if(shopcluesOrderApi.getQuantity() != 0){
							order.setQuantity(shopcluesOrderApi.getQuantity());
						} else {
							order.setQuantity(1);
						}
						
						/*if (partner != null) {
							event = eventsService.isEventActiive(
									order.getOrderDate(), partner.getPcName(),
									order.getProductSkuCode(), sellerId);

							if (event != null) {
								if (event.getNrnReturnConfig()
										.getNrCalculatorEvent()
										.equalsIgnoreCase("fixed")) {
									if (shopcluesOrderApi.getTotal() != 0) {
										order.setGrossNetRate(shopcluesOrderApi.getTotal());
									} else {
										errorMessage.append("Net Rate should not be Blank.");
										validaterow = false;
									}
								}
							} else {
								if (partner != null
										&& partner.getNrnReturnConfig() != null
										&& !partner.getNrnReturnConfig()
												.isNrCalculator()) {
									if (shopcluesOrderApi.getTotal() != 0) {
										order.setGrossNetRate(shopcluesOrderApi.getTotal());
									} else {
										errorMessage.append("Net Rate should not be Blank.");
										validaterow = false;
									}
								}
							}
						}*/
						
						if (order.getProductSkuCode() != null) {
							product = productService.getProduct(
									order.getProductSkuCode(), sellerId);
							if (product != null) {
								taxcat = taxDetailService.getTaxCategory(product,
										order.getOrderSP(), sellerId, customerBean.getZipcode());
							}
						}
						if (taxcat != null)
							orderTax.setTaxCategtory(taxcat.getTaxCatName());
						else {
							if (product != null) {
								errorMessage.append("No Tax Category mapped for this product.");
								validaterow = false;
							}
						}
						
						if(shopcluesOrderApi.getS_city() != null){
							customerBean.setCustomerCity(shopcluesOrderApi.getS_city());
						}
						
						if(shopcluesOrderApi.getFirstname() != null){
							customerBean.setCustomerName(shopcluesOrderApi.getFirstname()
									+" "+shopcluesOrderApi.getFirstname() != null ? shopcluesOrderApi.getFirstname() : "" );
						}
						
						if (validaterow) {
							order.setCustomer(customerBean);
							order.setOrderTax(orderTax);
							shopcluesOrderApi.setOrderStatus("ShippedToO2R");
							saveList.add(ConverterClass.prepareModel(order));
							shopcluesOrderApiList.add(shopcluesOrderApi);							
						} else {
							shopcluesOrderApi.setOrderStatus("error");
							shopcluesOrderApi.setErrorMsg(errorMessage.toString());
							shopcluesOrderApiList.add(shopcluesOrderApi);
						}						
					}
				}
				
				try {
					if (saveList != null && saveList.size() != 0){
						orderService.addOrder(saveList, sellerId);
					}
					if (shopcluesOrderApiList != null && shopcluesOrderApiList.size() != 0){
						apiService.saveShopcluesOrderFromAPI(sellerId, shopcluesOrderApiList);
					}						
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
}
