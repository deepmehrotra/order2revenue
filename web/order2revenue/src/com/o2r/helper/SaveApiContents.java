package com.o2r.helper;

import java.io.IOException;
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
import com.o2r.service.OrderService;
import com.o2r.service.PartnerService;

@Service("saveApiContents")
@Transactional
public class SaveApiContents {
	
	@Autowired
	private APIService apiService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private PartnerService partnerService;
	
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
		Set<String> errorSet = new HashSet<String>();
		Map<String, String> duplicateKey = new HashMap<String, String>();
		
		
		try {
			o2rIdsList = orderService.listOrderIds("channelOrderID", sellerId);
			partner = partnerService.getPartner("shopclues", sellerId);
			if(idList.length != 0 && orderMap.size() != 0){
				for(String orderId : idList){
					shopcluesOrderApi = orderMap.get(orderId);
					if(shopcluesOrderApi != null){
						validaterow = true;
						errorMessage = new StringBuffer();
						order = new OrderBean();
						customerBean = new CustomerBean();
						orderTax = new OrderTaxBean();
						
						
						
						
						
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	
	
	
	
	/*public void saveShopcluesOrderContents(MultipartFile file, int sellerId,
			String path, UploadReport uploadReport) throws IOException {
		log.info("$$$ saveShopcluesOrderContents starts : SaveMappedFiles $$$");

		ChannelUploadMapping chanupload = null;
		Map<String, String> columHeaderMap = new LinkedHashMap<String, String>();
		Map<String, Integer> cellIndexMap = new LinkedHashMap<String, Integer>();

		StringBuffer errorMessage = new StringBuffer();
		CustomerBean customerBean = null;
		Partner partner = null;
		Events event = null;
		List<Order> saveList = new ArrayList<Order>();
		Map<String, String> idsList = new HashMap<String, String>();
		Map<String, OrderBean> returnOrderMap = new LinkedHashMap<>();
		OrderBean order = null;
		int noOfEntries = 1;
		OrderTaxBean otb = null;
		HSSFRow entry;
		boolean validaterow = true;
		Set<String> errorSet = new HashSet<String>();
		Map<String, String> duplicateKey = new HashMap<String, String>();
		String uploadFileName = "";
		try {
			chanupload = uploadMappingService.getChannelUploadMapping(
					"Shopclues", "order");
			if (chanupload != null && chanupload.getColumMap() != null) {
				for (ColumMap colums : chanupload.getColumMap()) {
					columHeaderMap.put(colums.getO2rColumName(),
							colums.getChannelColumName());
				}
			}
			HSSFWorkbook offices = new HSSFWorkbook(file.getInputStream());

			HSSFSheet worksheet = offices.getSheetAt(0);
			while (worksheet.getRow(noOfEntries) != null) {
				noOfEntries++;
			}
			uploadFileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")) + new Date().getTime();
			entry = worksheet.getRow(0);
			for (int cellIndex = 0; cellIndex < entry
					.getPhysicalNumberOfCells(); cellIndex++) {
				if (entry.getCell(cellIndex)!=null&&columHeaderMap.containsValue(entry.getCell(cellIndex)
						.toString())) {
					cellIndexMap.put(entry.getCell(cellIndex).toString(),
							cellIndex);
				}

			}			
			idsList = orderService.listOrderIds("channelOrderID", sellerId);
			log.info(noOfEntries);
			log.debug("After getting no of rows" + noOfEntries);
			for (int rowIndex = 1; rowIndex < noOfEntries; rowIndex++) {
				errorMessage = new StringBuffer("Row :" + (rowIndex) + ":");
				entry = worksheet.getRow(rowIndex);
				validaterow = true;
				order = new OrderBean();
				customerBean = new CustomerBean();
				otb = new OrderTaxBean();
				int index = 0;
				ProductConfig productConfig = null;
				List<ProductConfig> productConfigs = null;
				String channelorderid = "";
				Product product = null;
				TaxCategory taxcat = null;
				errorMessage = new StringBuffer("Row :" + (rowIndex) + ":");
				try {

					if (cellIndexMap
							.get(columHeaderMap.get("Logistic Partner")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Logistic Partner"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							order.setLogisticPartner(entry.getCell(index)
									.toString());
						}
					}
					if (cellIndexMap.get(columHeaderMap.get("PIreferenceNo")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("PIreferenceNo"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							order.setPIreferenceNo(entry.getCell(index)
									.toString());
						}
					}

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Sales Channel"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							partner = partnerService.getPartner(
									entry.getCell(index).toString(), sellerId);
							if (partner != null)
								order.setPcName(partner.getPcName());
							else {
								order.setPcName(entry.getCell(index).toString());
								errorMessage.append(" Partner do not exist;");
								validaterow = false;
							}
						} else {
							errorMessage.append(" Partner Name is null;");
							validaterow = false;
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("The column 'Sales Channel' doesn't exist");
						validaterow = false;
					}

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Channel Order ID"));
						int skuIndex = cellIndexMap.get(columHeaderMap
								.get("SkUCode"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK
								&& entry.getCell(skuIndex) != null
								&& entry.getCell(skuIndex).getCellType() != HSSFCell.CELL_TYPE_BLANK) {

							entry.getCell(index).setCellType(
									HSSFCell.CELL_TYPE_STRING);
							entry.getCell(skuIndex).setCellType(
									HSSFCell.CELL_TYPE_STRING);
							if (partner != null) {
								productConfigs = productService
										.getProductConfig(
												entry.getCell(skuIndex)
														.toString().trim()
														.toUpperCase(),
												partner.getPcName(), sellerId);
								if (productConfigs != null) {

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
											errorMessage
													.append(" Multiple Mapping present for this Channel And SKU.");
											validaterow = false;
										}
									}
									try {
										if (productConfig.getVendorSkuRef() != null) {
											channelorderid = entry.getCell(
													index).toString()
													+ GlobalConstant.orderUniqueSymbol
													+ productConfig
															.getVendorSkuRef();
										} else {
											errorMessage
													.append(" Vendor SKU code not mapped.");
											validaterow = false;
										}
										
									} catch (Exception e) {

									}

								} else {
									errorMessage
											.append("No Product with this Channel & SKU");
									validaterow = false;
								}
							}
						} else {
							errorMessage
									.append(" Channel OrderId or SKU code is null ");
							validaterow = false;
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("The column 'Order No.' doesn't exist");
						validaterow = false;
					}

					if (cellIndexMap.get(columHeaderMap.get("AWB No")) != null) {
						index = cellIndexMap.get(columHeaderMap.get("AWB No"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							entry.getCell(index).setCellType(
									HSSFCell.CELL_TYPE_STRING);
							order.setAwbNum(entry.getCell(index).toString());
						}
					}

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Order Recieved Date"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							try {	
								String date = entry.getCell(index).toString();
								order.setOrderDate(new Date(date));
							
						} catch (Exception e) {
							errorMessage
									.append(" Order Received Date format is wrong ,expected text format dd-mmm-yyyy.");
							validaterow = false;
						}

						} else {
							errorMessage
									.append(" Order Recieved Date is null;");
							validaterow = false;
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("The column 'Order Created Date' doesn't exist");
						validaterow = false;
					}
					
					if(channelorderid != null&&order.getOrderDate()!=null){
						String itemID=format.format(order.getOrderDate());
						if(!idsList.containsKey(channelorderid)
								&& !duplicateKey.containsKey(channelorderid)){
							order.setChannelOrderID(channelorderid);
							if (productConfig != null){
								order.setProductSkuCode(productConfig.getProductSkuCode());	
								order.setPartnerCommission(productConfig.getCommision());
							}
							duplicateKey.put(channelorderid, itemID);
						} else {
							if(itemID != null && ((idsList.get(channelorderid) != null 
									&& !idsList.get(channelorderid).equals(itemID))
									|| (duplicateKey.containsKey(channelorderid) 
											&& (duplicateKey.get(channelorderid) == null
											|| !duplicateKey.get(channelorderid).equals(itemID))))){
								channelorderid = channelorderid + GlobalConstant.orderUniqueSymbol + itemID;
								if(!idsList.containsKey(channelorderid)
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

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer Name"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							customerBean.setCustomerName(entry.getCell(index)
									.toString());
						}
					} catch (NullPointerException e) {

					}

					if (cellIndexMap.get(columHeaderMap.get("Customer City")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer City"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							customerBean.setCustomerCity(entry.getCell(index)
									.toString());
						}
					}

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Shipping PinCode"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							log.debug(" getting zipcode ");
							String zipCode = entry.getCell(index).toString();
							if (zipCode.contains(".")) {
								zipCode = zipCode.substring(0,
										zipCode.indexOf("."));
							}
							if (areaConfigDao.isZipCodeValid(zipCode)) {
								try {
									customerBean.setZipcode(zipCode);
								} catch (Exception e) {
									log.error("Failed! by SellerId : "
											+ sellerId, e);
									errorMessage
											.append("Shipping PinCode is corrupted.");
									validaterow = false;
								}
							} else {
								errorMessage
										.append("Either invalid Shipping PinCode or try after some time while admin will add this to database.");
								validaterow = false;
								customerBean.setZipcode(entry.getCell(index)
										.toString());
							}
						} else {
							errorMessage.append("Shipping PinCode is blank.");
							validaterow = false;
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("The column 'Shipping Pincode' doesn't exist.");
						validaterow = false;
					}

					try {
						index = cellIndexMap
								.get(columHeaderMap.get("OrderSP"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							try {
								order.setOrderSP(Double.parseDouble(entry
										.getCell(index).toString()));
							} catch (NumberFormatException e) {
								errorMessage
										.append(" Order SP should be a number ");
								validaterow = false;
							}
						} else {
							errorMessage.append(" Order SP is null ");
							validaterow = false;
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("The column 'Order SubTotal doesn't exist");
						validaterow = false;
					}

					if (cellIndexMap.get(columHeaderMap.get("Order MRP")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Order MRP"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							try {
								order.setOrderMRP(Double.parseDouble(entry
										.getCell(index).toString()));
							} catch (NumberFormatException e) {
								log.error("Failed! by SellerId : " + sellerId,
										e);
								errorMessage
										.append(" Order MRP should be a number ");
							}
						}
					}

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("InvoiceID"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							entry.getCell(index).setCellType(
									HSSFCell.CELL_TYPE_STRING);
							order.setInvoiceID(entry.getCell(index).toString());
						} else {
							order.setInvoiceID(entry.getCell(index).toString());
							errorMessage.append(" Invoice ID is null;");
							validaterow = false;
						}
					} catch (Exception e) {
						errorMessage
								.append("The column 'Invoice ID' doesn't exist");
						validaterow = false;
					}

					if (cellIndexMap.get(columHeaderMap
							.get("Customer Phone No")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer Phone No"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							entry.getCell(index).setCellType(
									HSSFCell.CELL_TYPE_STRING);
							if (entry.getCell(index).toString().length() == 10
									&& !entry.getCell(index).toString()
											.contains("9999999999")) {
								if (entry.getCell(index).toString()
										.matches("[0-9]+")
										&& entry.getCell(index).toString()
												.length() > 2) {
									customerBean.setCustomerPhnNo(entry
											.getCell(index).toString());
								}
							}
						}
					}
					if (cellIndexMap.get(columHeaderMap.get("Customer Email")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer Email"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							log.debug(" Email from sheet ************ "
									+ rowIndex + ":"
									+ entry.getCell(index).toString());
							customerBean.setCustomerEmail(entry.getCell(index)
									.toString());
						}
					}

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Payment Type"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							if (entry.getCell(index).toString()
									.equalsIgnoreCase("COD"))
								order.setPaymentType(entry.getCell(index)
										.toString());
							else {
								order.setPaymentType("Others");
							}
						} else {
							errorMessage.append("Payment Details is null.");
							validaterow = false;
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("The column 'Payment Details' doesn't exist.");
						validaterow = false;
					}

					try {
						index = cellIndexMap.get(columHeaderMap
								.get("Order Shipped Date"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {							
							try {								
									String date = entry.getCell(index).toString();							
									order.setShippedDate(new Date(date));																	
							} catch (Exception e) {
								errorMessage.append("Shipped Date formate is wrong.");
								validaterow = false;
							}
						} else {
							errorMessage.append(" Shipping Date is null ");
							validaterow = false;
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("The column 'Order Shipped Date' doesn't exist");
						validaterow = false;
					}

					if (cellIndexMap
							.get(columHeaderMap.get("Customer Address")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Customer Address"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							customerBean.setCustomerAddress(entry
									.getCell(index).toString());
						}
					}
					try {
						index = cellIndexMap
								.get(columHeaderMap.get("Quantity"));
						if (entry.getCell(index) != null
								&& entry.getCell(index).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
							try {
								if ((int) Float.parseFloat(entry.getCell(index)
										.toString()) != 0) {
									order.setQuantity((int) Float
											.parseFloat(entry.getCell(index)
													.toString()));
								} else {
									order.setQuantity(1);
								}
							} catch (NumberFormatException e) {
								order.setQuantity(1);
							}
						} else {
							order.setQuantity(1);
						}
					} catch (NullPointerException e) {
						errorMessage
								.append("The column 'Item Count' doesn't exist");
						validaterow = false;
					}

					if (partner != null) {
						event = eventsService.isEventActiive(
								order.getOrderDate(), partner.getPcName(),
								order.getProductSkuCode(), sellerId);

						if (event != null) {
							if (event.getNrnReturnConfig()
									.getNrCalculatorEvent()
									.equalsIgnoreCase("fixed")) {
								if (cellIndexMap.get(columHeaderMap
										.get("Net Rate")) != null) {
									index = cellIndexMap.get(columHeaderMap
											.get("Net Rate"));
									if (entry.getCell(index) != null
											&& entry.getCell(index)
													.getCellType() != HSSFCell.CELL_TYPE_BLANK) {

										try {
											order.setGrossNetRate(Double
													.parseDouble(entry.getCell(
															index).toString()));
										} catch (NumberFormatException e) {
											log.error("Failed! by SellerId : "
													+ sellerId, e);
											errorMessage
													.append(" Net Rate should be number ");
											validaterow = false;
										}
									} else {
										errorMessage
												.append(" Net Rate is null and ongoing event on the order with fixed price.");
										validaterow = false;
									}
								} else {
									errorMessage
											.append("The column 'Gross NR' doesn't exist");
									validaterow = false;
								}
							}
						} else {
							if (partner != null
									&& partner.getNrnReturnConfig() != null
									&& !partner.getNrnReturnConfig()
											.isNrCalculator()) {
								if (cellIndexMap.get(columHeaderMap
										.get("Net Rate")) != null) {
									index = cellIndexMap.get(columHeaderMap
											.get("Net Rate"));
									if (entry.getCell(index) != null
											&& entry.getCell(index)
													.getCellType() != HSSFCell.CELL_TYPE_BLANK) {
										try {
											order.setGrossNetRate(Double
													.parseDouble(entry.getCell(
															index).toString()));
										} catch (NumberFormatException e) {
											log.error("Failed! by SellerId : "
													+ sellerId, e);
											errorMessage
													.append(" Net Rate should be number ");
											validaterow = false;
										}
									} else {
										errorMessage
												.append(" Net Rate is null.");
										validaterow = false;
									}
								} else {
									errorMessage
											.append(" The Column 'Net Rate' Not Present.");
									validaterow = false;
								}
							}
						}
					}

					if (order.getProductSkuCode() != null) {
						product = productService.getProduct(
								order.getProductSkuCode(), sellerId);
						if (product != null) {
							taxcat = taxDetailService.getTaxCategory(product,
									order.getOrderSP(), sellerId, customerBean.getZipcode());
						}
					}
					if (taxcat != null)
						otb.setTaxCategtory(taxcat.getTaxCatName());
					else {
						if (product != null) {
							errorMessage
									.append("No Tax Category mapped for this product ");
							validaterow = false;
						}
					}

					if (cellIndexMap.get(columHeaderMap.get("Seller Note")) != null) {
						index = cellIndexMap.get(columHeaderMap
								.get("Seller Note"));
						if (entry.getCell(index) != null
								&& StringUtils.isNotBlank(entry.getCell(index)
										.toString())) {
							order.setSellerNote(entry.getCell(index).toString());

						}
					}
					
					if (validaterow) {
						order.setOrderFileName(uploadFileName);
						order.setCustomer(customerBean);
						order.setOrderTax(otb);						
						saveList.add(ConverterClass.prepareModel(order));
					} else {
						order.setCustomer(customerBean);
						order.setOrderTax(otb);
						log.debug(" Error while saving order : "
								+ order.getChannelOrderID()
								+ " errorMessage : " + errorMessage);
						returnOrderMap.put(errorMessage.toString(), order);
					}
				} catch (Exception e) {

					log.error("Failed! by SellerId : " + sellerId, e);
					errorMessage.append("Invalid Input! ");
					returnOrderMap.put(errorMessage.toString(), order);
				}
			}

			try {
				System.out.println(" SaveList Size : " + saveList.size());
				if (saveList != null && saveList.size() != 0)
					orderService.addOrder(saveList, sellerId);
			} catch (CustomException ce) {
				returnOrderMap
						.put("Row :1:Note-Some orders("
								+ ce.getLocalMessage()
								+ " ) with valid input "
								+ "failed due to internal server error. Please contact admin.!",
								null);
			}
			errorSet = returnOrderMap.keySet();
			uploadResultXLS(offices, "Shopclues_Order_Upload", uploadFileName, errorSet, path,
					sellerId, uploadReport);

		} catch (Exception e) {
			log.debug("Inside save contents exception :"
					+ e.getLocalizedMessage());
			e.printStackTrace();
			addErrorUploadReport("Shopclues_Order_Upload", sellerId,
					uploadReport);
			log.error("Failed! by SellerId : " + sellerId, e);
		}
	}*/
	
	
	
	
	
	
	
	
	
}
