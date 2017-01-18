package com.o2r.helper;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.o2r.bean.CustomerBean;
import com.o2r.bean.DataConfig;
import com.o2r.bean.OrderBean;
import com.o2r.bean.OrderTaxBean;
import com.o2r.dao.AreaConfigDao;
import com.o2r.dao.OrderDaoImpl;
import com.o2r.dao.SellerDao;
import com.o2r.model.Events;
import com.o2r.model.Order;
import com.o2r.model.OrderTimeline;
import com.o2r.model.Partner;
import com.o2r.model.PartnerCategoryMap;
import com.o2r.model.Product;
import com.o2r.model.ProductConfig;
import com.o2r.model.TaxCategory;
import com.o2r.model.TaxDetail;
import com.o2r.service.EventsService;
import com.o2r.service.OrderService;
import com.o2r.service.PartnerService;
import com.o2r.service.ProductService;
import com.o2r.service.TaxDetailService;


@Service("saveIntegrationCotents")
@Transactional
public class SaveIntegrationCotents {
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private ProductService productService;
	@Autowired
	private AreaConfigDao areaConfigDao;
	@Autowired
	private TaxDetailService taxDetailService;
	@Autowired
	private PartnerService partnerService;
	@Autowired
	private SellerDao sellerDao;
	@Autowired
	private DataConfig dataConfig;
	@Autowired
	private EventsService eventsService;
	
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
		List<Events> eventList = null;
		Map<String, Partner> partnerMap = new HashMap<String, Partner>();
		Map<Integer, TaxDetail> taxDetailMap = new HashMap<Integer, TaxDetail>();
		Map<Integer, TaxDetail> tdsDetailMap = new HashMap<Integer, TaxDetail>();	
		
		DateFormat orderDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
		DateFormat shipDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH);
		
		StringBuffer errorMsg = new StringBuffer();
		
		try {			
			saveList = new ArrayList<Order>();			
			if(orderList != null && orderList.size() != 0){
				int eventCall = 0;				
				MasterSKUList = productService.listProductSKU(sellerId);
				if(MasterSKUList != null && MasterSKUList.size() != 0){
					
					for(String eachOrderString : orderList){
						
						boolean validate = true;
						JSONObject jsonObject = new JSONObject(eachOrderString);
						JSONArray jsonArray = jsonObject.getJSONArray("data");
						
						if(jsonArray.length() != 0){
							for(int each = 0; each < jsonArray.length(); each++){
								JSONObject eachOrderObject = jsonArray.getJSONObject(each);
								JSONArray itemsList = eachOrderObject.getJSONArray("Items");					
								
								if(itemsList.length() !=0){
									for(int eachItem = 0; each < itemsList.length(); eachItem++){										
										
										validate = true;
										List<ProductConfig> productConfigs = null;
										ProductConfig productConfig = null; 
										OrderBean order = null;
										CustomerBean customerBean = null;
										OrderTaxBean taxBean = null;
										String partnerCatRef = null;
										Date reconciledate = null;
										TaxDetail taxDetails= null;
										Events event = null;										
										Product product = null;
										TaxCategory taxcat = null;
										Partner partner = null;
										
										JSONObject eachItemObject = itemsList.getJSONObject(eachItem);
										
										try {
											productConfigs = productService.getProductConfig(eachItemObject.getString("SKU"),
													eachOrderObject.getString("Channel"), sellerId);
											
											if(productConfigs != null){
												if (productConfigs.size() == 1) {
													productConfig = (ProductConfig) productConfigs
															.get(0);
												} else {
													ProductConfig pc = null;
													Set<String> parent = new HashSet<String>();
													for (Object PCo : productConfigs) {
														pc = (ProductConfig) PCo;
														parent.add(pc.getProductSkuCode());
													}
													if (parent.size() == 1) {
														productConfig = (ProductConfig) productConfigs
																.get(0);
													} else {												
														validate = false;
													}
												}
												
												if(productConfig != null){													
													order = new OrderBean();
													customerBean = new CustomerBean();
													taxBean = new OrderTaxBean();
													
													if(eachOrderObject.has("ChannelOrderId") && eachOrderObject.getString("ChannelOrderId") != ""){
														
														order.setProductSkuCode(productConfig.getProductSkuCode());
														order.setPcName(eachOrderObject.getString("Channel"));
														order.setQuantity(eachItemObject.getInt("Quantity") != 0 ? eachItemObject.getInt("Quantity") : 1);
														order.setPartnerCommission(productConfig.getCommision());
														
														
														String channelOrderId = eachOrderObject.getString("ChannelOrderId")
																+GlobalConstant.orderUniqueSymbol+ productConfig.getVendorSkuRef();
														
														order.setChannelOrderID(channelOrderId);
														
														if(eachOrderObject.has("OrderDate") && eachOrderObject.getString("OrderDate") != ""){
															order.setOrderDate(orderDateFormat.parse(eachOrderObject.getString("OrderDate")));
														} else {
															validate = false;
														}
														
														if(eachOrderObject.has("ShippedOn") && eachOrderObject.getString("ShippedOn") != ""){
															order.setShippedDate(shipDateFormat.parse(eachOrderObject.getString("ShippedOn")));
														} else {
															validate = false;
														}
														
														if(eachOrderObject.has("Pincode") && (!eachOrderObject.isNull("Pincode") && eachOrderObject.getString("Pincode") != "" ) && validate){
															String zipCode = eachOrderObject.getString("Pincode");
															if (zipCode.contains(".")) {
																zipCode = zipCode.substring(0, zipCode.indexOf("."));
															}															
															if (areaConfigDao.isZipCodeValid(zipCode)) {
																try {
																	customerBean.setZipcode(zipCode);
																} catch (Exception e) {
																	errorMsg.append("Failed : "+eachItemObject.getString("SKU")+" : Cause : "+e.getLocalizedMessage()+"#");
																	validate = false;
																}
															} else {
																validate = false;
															}
															
															if (order.getProductSkuCode() != null) {
																product = productService.getProduct(
																		order.getProductSkuCode(), sellerId);
																if (product != null) {
																	taxcat = taxDetailService.getTaxCategory(product,
																			order.getOrderSP(), sellerId,
																			customerBean.getZipcode());
																}
																if(taxcat != null){
																	taxBean.setTaxCategtory(taxcat.getTaxCatName());
																} else {
																	validate = false;
																}
															} 														
														} else {
															validate = false;
														}
														
														if(eachOrderObject.has("Courier") && eachOrderObject.getString("Courier") != ""){
															order.setLogisticPartner(eachOrderObject.getString("Courier"));
														}
														
														if(eachOrderObject.has("TrackingNumber") && eachOrderObject.getString("TrackingNumber") != ""){
															order.setAwbNum(eachOrderObject.getString("TrackingNumber"));
														}
														
														if(eachOrderObject.has("CustomerName") && (!eachOrderObject.isNull("CustomerName") && eachOrderObject.getString("CustomerName") != "")){
															customerBean.setCustomerName(eachOrderObject.getString("CustomerName"));
														}
														
														if(eachOrderObject.has("PhoneNumber") && (!eachOrderObject.isNull("PhoneNumber") && eachOrderObject.getString("PhoneNumber") != "")){
															customerBean.setCustomerPhnNo(eachOrderObject.getString("PhoneNumber"));
														}
																
													} else {
														validate = false;
													}
												} else {
													validate = false;
												}												
											} else {
												validate = false;
											}
										} catch (Exception e) {
											errorMsg.append("Failed : "+eachItemObject.getString("SKU")+" : Cause : "+e.getLocalizedMessage()+"#");
											validate = false;
										}
										
										if(validate && order != null){
											order.setCustomer(customerBean);
											order.setOrderTax(taxBean);
											Order orderModel = ConverterClass.prepareModel(order);
											
											if(eventCall == 0){
												eventList = eventsService.getEvents(orderModel.getPcName(), sellerId);
												eventCall++;
											}										
											
											try {
												if(partnerMap.containsKey(orderModel.getPcName())){
													partner = partnerMap.get(orderModel.getPcName());
												} else {
													partner = partnerService.getPartner(orderModel.getPcName(), sellerId);
												}
												if(partner != null){
													//Putting partner into map
													partnerMap.put(partner.getPcName(), partner);
													
													//getting Partner Category Reference
													if (product.getPartnerCategoryMap() != null) {
														for (PartnerCategoryMap partnerCat : product
																.getPartnerCategoryMap()) {
															if (partnerCat.getPartnerName()
																	.equalsIgnoreCase(
																			partner.getPcName())) {
																partnerCatRef = partnerCat
																		.getPartnerCategoryRef();
															}
														}
													}
													
													// Checking For Event
													if(eventList != null && eventList.size() != 0){
														event = chechEvent(eventList, orderModel);
													}
													
													
													// Calculating NR
													if (partner != null
															&& partner.getNrnReturnConfig() != null
															&& partner.getNrnReturnConfig()
																	.isNrCalculator()) {

														if (event != null) {

															orderModel.setEventName(event.getEventName());
															event.setNetSalesQuantity(event
																	.getNetSalesQuantity()
																	+ orderModel.getQuantity());

															if (event.getNrnReturnConfig()
																	.getNrCalculatorEvent()
																	.equalsIgnoreCase("variable")) {
																if (!new OrderDaoImpl().calculateNR(
																		event.getNrnReturnConfig(), orderModel,
																		partnerCatRef,
																		product.getDeadWeight(),
																		product.getVolWeight(), sellerId))
																	throw new Exception();
															} else if (event.getNrnReturnConfig()
																	.getNrCalculatorEvent()
																	.equalsIgnoreCase("original")) {
																if (!new OrderDaoImpl().calculateNR(partner, orderModel,
																		partnerCatRef,
																		product.getDeadWeight(),
																		product.getVolWeight(), sellerId))
																	throw new Exception();
															} else if (event.getNrnReturnConfig()
																	.getNrCalculatorEvent()
																	.equalsIgnoreCase("fixed")) {
																double commissiontemp = (orderModel.getOrderSP() - orderModel
																		.getGrossNetRate())
																		* orderModel.getQuantity();

																orderModel.setPartnerCommission(commissiontemp / 1.15);
																orderModel.setServiceTax((float) (commissiontemp - orderModel
																		.getPartnerCommission()));

																if (partner.isTdsApplicable())
																	orderModel.getOrderTax()
																			.setTdsToDeduct(
																					(orderModel.getPartnerCommission() - (orderModel
																							.getPartnerCommission() * 100 / (100 + dataConfig
																							.getServiceTax())))
																							* (((dataConfig
																									.getTds()) != 0 ? dataConfig
																									.getTds()
																									: 0) / 100)
																							* orderModel.getQuantity());
															}

														} else if (!new OrderDaoImpl().calculateNR(partner, orderModel,
																partnerCatRef, product.getDeadWeight(),
																product.getVolWeight(), sellerId))
															throw new Exception();

														log.debug(" Shipping charges :"
																+ orderModel.getShippingCharges()
																+ " >> Gross net rate "
																+ orderModel.getGrossNetRate()
																+ " delivery date :"
																+ orderModel.getDeliveryDate());
													} else {

														if (event != null) {

															orderModel.setEventName(event.getEventName());
															event.setNetSalesQuantity(event
																	.getNetSalesQuantity()
																	+ orderModel.getQuantity());

															if (event.getNrnReturnConfig()
																	.getNrCalculatorEvent()
																	.equalsIgnoreCase("variable")) {
																if (!new OrderDaoImpl().calculateNR(
																		event.getNrnReturnConfig(), orderModel,
																		partnerCatRef,
																		product.getDeadWeight(),
																		product.getVolWeight(), sellerId))
																	throw new Exception();
															} else if (event.getNrnReturnConfig()
																	.getNrCalculatorEvent()
																	.equalsIgnoreCase("original")
																	|| event.getNrnReturnConfig()
																			.getNrCalculatorEvent()
																			.equalsIgnoreCase("fixed")) {																
																double commissiontemp = (orderModel.getOrderSP() - orderModel
																		.getGrossNetRate())
																		* orderModel.getQuantity();

																orderModel.setPartnerCommission(commissiontemp / 1.15);
																orderModel.setServiceTax((float) (commissiontemp - orderModel
																		.getPartnerCommission()));

																if (partner.isTdsApplicable())
																	orderModel.getOrderTax()
																			.setTdsToDeduct(
																					(orderModel.getPartnerCommission() - (orderModel
																							.getPartnerCommission() * 100 / (100 + dataConfig
																							.getServiceTax())))
																							* (((dataConfig
																									.getTds()) != 0 ? dataConfig
																									.getTds()
																									: 0) / 100)
																							* orderModel.getQuantity());
															}

														} else {
															double commissiontemp = (orderModel.getOrderSP() - orderModel
																	.getGrossNetRate())
																	* orderModel.getQuantity();

															orderModel.setPartnerCommission(commissiontemp / 1.15);
															orderModel.setServiceTax((float) (commissiontemp - orderModel
																	.getPartnerCommission()));															

															if (partner.isTdsApplicable())
																orderModel.getOrderTax()
																		.setTdsToDeduct(
																				(orderModel.getPartnerCommission() - (orderModel
																						.getPartnerCommission() * 100 / (100 + dataConfig
																						.getServiceTax())))
																						* (((dataConfig
																								.getTds()) != 0 ? dataConfig
																								.getTds()
																								: 0) / 100)
																						* orderModel.getQuantity());
														}
													}				
													
													
													//Calculate Delivery Date
													new OrderDaoImpl().calculateDeliveryDate(orderModel, sellerId);
													
													//Tax percentage
													float taxPercent = taxcat.getTaxPercent();
													
													//Reconcile Date
													reconciledate = new OrderDaoImpl().getreconciledate(orderModel, partner,
															orderModel.getOrderDate());
													if (reconciledate != null){
														orderModel.setPaymentDueDate(reconciledate);
													}
													
													orderModel.setStatus("Shipped");
													orderModel.setOrderMRP(orderModel.getOrderMRP() * orderModel.getQuantity());
													orderModel.setOrderSP(orderModel.getOrderSP()	* orderModel.getQuantity());
													orderModel.setNetRate(orderModel.getGrossNetRate() * orderModel.getQuantity());
													orderModel.setTotalAmountRecieved(orderModel.getNetRate());
													orderModel.setFinalStatus("In Process");
													
													if (orderModel.getOrderMRP() > orderModel.getOrderSP()){
														orderModel.setDiscount((Math.abs(orderModel.getOrderMRP() - orderModel.getOrderSP())));
													}
													
													//Calculate & Set Tax to Order												
													orderModel.getOrderTax().setTax(orderModel.getOrderSP()
															- (orderModel.getOrderSP() * (100 / (100 + taxPercent))));											
													
													
													//Set Tax Details
													if(taxDetailMap.containsKey(orderModel.getOrderDate().getMonth()+1)){
														taxDetails = taxDetailMap.get(orderModel.getOrderDate().getMonth()+1);
													} else {
														taxDetails = new TaxDetail();
														taxDetails.setParticular(orderModel.getOrderTax().getTaxCategtory());
														taxDetails.setUploadDate(orderModel.getOrderDate());
													}													
													taxDetails.setBalanceRemaining(taxDetails.getBalanceRemaining() + orderModel.getOrderTax().getTax());
													taxDetailMap.put(orderModel.getOrderDate().getMonth()+1, taxDetails);
													
													
													//Set TDS
													if (partner.isTdsApplicable()) {
														if(tdsDetailMap.containsKey(orderModel.getShippedDate().getMonth()+1)){
															taxDetails = tdsDetailMap.get(orderModel.getShippedDate().getMonth()+1);
														} else {
															taxDetails = new TaxDetail();
															taxDetails.setParticular("TDS");
															taxDetails.setUploadDate(orderModel.getShippedDate());
														}														
														taxDetails.setBalanceRemaining(taxDetails.getBalanceRemaining() + orderModel.getOrderTax().getTdsToDeduct());													
														tdsDetailMap.put(orderModel.getShippedDate().getMonth()+1, taxDetails);
													}
													
													
													//Setting Order Details To Customer
													orderModel.getCustomer().setSellerId(sellerId);
													orderModel.getCustomer().getOrders().add(orderModel);
													
													
													// Setting payment difference for old orders
													if (orderModel.getPaymentDueDate().compareTo(
															java.util.Calendar.getInstance().getTime()) < 0) {
														orderModel.getOrderPayment().setPaymentDifference(
																0 - orderModel.getNetRate());
														orderModel.setStatus("Payment Disputed");
														orderModel.setFinalStatus("Actionable");
													}
													
													
													// Setting Return and RTO limits
													Date tempDate = (Date) orderModel.getDeliveryDate().clone();
													tempDate.setDate(tempDate.getDate()	+ partner.getMaxReturnAcceptance());
													orderModel.setReturnLimitCrossed(tempDate);
													
													tempDate = (Date) orderModel.getDeliveryDate().clone();
													tempDate.setDate(tempDate.getDate() + partner.getMaxRTOAcceptance());
													orderModel.setrTOLimitCrossed(tempDate);
													
													
													// Setting PR and Gross Profit for Order
													orderModel.setPr(orderModel.getNetRate() - orderModel.getOrderTax().getTax());
													orderModel.setGrossProfit(orderModel.getPr()
															- (product.getProductPrice() * orderModel.getQuantity()));
													orderModel.setGrossMargin(orderModel.getGrossProfit());
													orderModel.setProductCost(product.getProductPrice());
													
													
													// Set Order Time line
													OrderTimeline timeline = new OrderTimeline();
													timeline.setEvent("Order Created");
													orderModel.setLastActivityOnOrder(new Date());
													timeline.setEventDate(new Date());
													orderModel.getOrderTimeline().add(timeline);							
													
												} else {
													validate = false;
												}					
												
											} catch (Exception e) {
												e.printStackTrace();
												validate = false;
											}
											
											if(validate){
												saveList.add(orderModel);
											}									
										} else {
											System.out.println("Failed : "+eachOrderObject.toString());
										}									
									}
								}							
							}
						}						
					}
				}											
			}
			try {
				System.out.println(" SaveList Size : " + saveList.size());
				if (saveList != null && saveList.size() != 0){
					orderService.addAPIOrders(saveList, sellerId);
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
	
	private Events chechEvent (List<Events> eventList, Order order){
		Events event = null;
		try {
			for(Events ev : eventList){
				if(!order.getOrderDate().before(ev.getStartDate()) && !order.getOrderDate().after(ev.getEndDate())){
					if(ev != null && ((ev.getSelectAll() != null && ev.getSelectAll().equals("true")) 
							|| (order.getProductSkuCode() != null && ev.getSkuList() != null && ev.getSkuList().contains(order.getProductSkuCode())))
							&& ev.getStatus().equals("Active")){				
						return ev;
					}
				}				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return event;		
	}

}
