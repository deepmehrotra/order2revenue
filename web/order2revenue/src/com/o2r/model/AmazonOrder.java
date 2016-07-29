package com.o2r.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.amazonservices.mws.orders._2013_09_01.model.BuyerCustomizedInfoDetail;
import com.amazonservices.mws.orders._2013_09_01.model.InvoiceData;
import com.amazonservices.mws.orders._2013_09_01.model.Money;
import com.amazonservices.mws.orders._2013_09_01.model.OrderItem;
import com.amazonservices.mws.orders._2013_09_01.model.PointsGrantedDetail;
import com.o2r.helper.ConverterClass;

@Entity
@Table(name="Amazon_Orders")
public class AmazonOrder {
	@Id
	@Column
	private String mainOrderId;

	@Column
	private String amazonOrderId;

	@Column
	private String sellerOrderId;

	@Column
	private Date purchaseDate;

	@Column
	private Date lastUpdateDate;

	@Column
	private String orderStatus;

	@Column
	private String fulfillmentChannel;

	@Column
	private String salesChannel;

	@Column
	private String orderChannel;

	@Column
	private String shipServiceLevel;

//	@Column
//	private Address shippingAddress;

	@Column
	private Double orderTotal;

	@Column
	private Integer numberOfItemsShipped;

	@Column
	private Integer numberOfItemsUnshipped;

//	@Column
//	private List<PaymentExecutionDetailItem> paymentExecutionDetail;

	@Column
	private String paymentMethod;

	@Column
	private String marketplaceId;

	@Column
	private String buyerEmail;

	@Column
	private String buyerName;

	@Column
	private String shipmentServiceLevelCategory;

	@Column
	private Boolean shippedByAmazonTFM;

	@Column
	private String tfmShipmentStatus;

	@Column
	private String cbaDisplayableShippingLabel;

	@Column
	private String orderType;

	@Column
	private Date earliestShipDate;

	@Column
	private Date latestShipDate;

	@Column
	private Date earliestDeliveryDate;

	@Column
	private Date latestDeliveryDate;

	@Column
	private Boolean isBusinessOrder;

	@Column
	private String purchaseOrderNumber;

	@Column
	private Boolean isPrime;

	@Column
	private Boolean isPremiumOrder;
	
	@Column
	private String asin;

	@Column
	private String sellerSKU;

	@Column
	private String orderItemId;

	@Column
	private String title;

	@Column
	private int quantityOrdered;

	@Column
	private Integer quantityShipped;

//	@Column
//	private PointsGrantedDetail pointsGranted;

	@Column
	private Double itemPrice;

	@Column
	private Double shippingPrice;

	@Column
	private Double giftWrapPrice;

	@Column
	private Double itemTax;

	@Column
	private Double shippingTax;

	@Column
	private Double giftWrapTax;

	@Column
	private Double shippingDiscount;

	@Column
	private Double promotionDiscount;

	@Column
	private String promotionIds;

	@Column
	private Double codFee;

	@Column
	private Double codFeeDiscount;

	@Column
	private String giftMessageText;

	@Column
	private String giftWrapLevel;

//	@Column
//	private InvoiceData invoiceData;

	@Column
	private String conditionNote;

	@Column
	private String conditionId;

	@Column
	private String conditionSubtypeId;

	@Column
	private String scheduledDeliveryStartDate;

	@Column
	private String scheduledDeliveryEndDate;

	@Column
	private String priceDesignation;

//	@Column
//	private BuyerCustomizedInfoDetail buyerCustomizedInfo;

	public String getMainOrderId() {
		return mainOrderId;
	}

	public void setMainOrderId(String mainOrderId) {
		this.mainOrderId = mainOrderId;
	}

	public String getAmazonOrderId() {
		return amazonOrderId;
	}

	public void setAmazonOrderId(String amazonOrderId) {
		this.amazonOrderId = amazonOrderId;
	}

	public String getSellerOrderId() {
		return sellerOrderId;
	}

	public void setSellerOrderId(String sellerOrderId) {
		this.sellerOrderId = sellerOrderId;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getFulfillmentChannel() {
		return fulfillmentChannel;
	}

	public void setFulfillmentChannel(String fulfillmentChannel) {
		this.fulfillmentChannel = fulfillmentChannel;
	}

	public String getSalesChannel() {
		return salesChannel;
	}

	public void setSalesChannel(String salesChannel) {
		this.salesChannel = salesChannel;
	}

	public String getOrderChannel() {
		return orderChannel;
	}

	public void setOrderChannel(String orderChannel) {
		this.orderChannel = orderChannel;
	}

	public String getShipServiceLevel() {
		return shipServiceLevel;
	}

	public void setShipServiceLevel(String shipServiceLevel) {
		this.shipServiceLevel = shipServiceLevel;
	}

	public Double getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(Double orderTotal) {
		this.orderTotal = orderTotal;
	}

	public Integer getNumberOfItemsShipped() {
		return numberOfItemsShipped;
	}

	public void setNumberOfItemsShipped(Integer numberOfItemsShipped) {
		this.numberOfItemsShipped = numberOfItemsShipped;
	}

	public Integer getNumberOfItemsUnshipped() {
		return numberOfItemsUnshipped;
	}

	public void setNumberOfItemsUnshipped(Integer numberOfItemsUnshipped) {
		this.numberOfItemsUnshipped = numberOfItemsUnshipped;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getMarketplaceId() {
		return marketplaceId;
	}

	public void setMarketplaceId(String marketplaceId) {
		this.marketplaceId = marketplaceId;
	}

	public String getBuyerEmail() {
		return buyerEmail;
	}

	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getShipmentServiceLevelCategory() {
		return shipmentServiceLevelCategory;
	}

	public void setShipmentServiceLevelCategory(String shipmentServiceLevelCategory) {
		this.shipmentServiceLevelCategory = shipmentServiceLevelCategory;
	}

	public Boolean getShippedByAmazonTFM() {
		return shippedByAmazonTFM;
	}

	public void setShippedByAmazonTFM(Boolean shippedByAmazonTFM) {
		this.shippedByAmazonTFM = shippedByAmazonTFM;
	}

	public String getTfmShipmentStatus() {
		return tfmShipmentStatus;
	}

	public void setTfmShipmentStatus(String tfmShipmentStatus) {
		this.tfmShipmentStatus = tfmShipmentStatus;
	}

	public String getCbaDisplayableShippingLabel() {
		return cbaDisplayableShippingLabel;
	}

	public void setCbaDisplayableShippingLabel(String cbaDisplayableShippingLabel) {
		this.cbaDisplayableShippingLabel = cbaDisplayableShippingLabel;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public Date getEarliestShipDate() {
		return earliestShipDate;
	}

	public void setEarliestShipDate(Date earliestShipDate) {
		this.earliestShipDate = earliestShipDate;
	}

	public Date getLatestShipDate() {
		return latestShipDate;
	}

	public void setLatestShipDate(Date latestShipDate) {
		this.latestShipDate = latestShipDate;
	}

	public Date getEarliestDeliveryDate() {
		return earliestDeliveryDate;
	}

	public void setEarliestDeliveryDate(Date earliestDeliveryDate) {
		this.earliestDeliveryDate = earliestDeliveryDate;
	}

	public Date getLatestDeliveryDate() {
		return latestDeliveryDate;
	}

	public void setLatestDeliveryDate(Date latestDeliveryDate) {
		this.latestDeliveryDate = latestDeliveryDate;
	}

	public Boolean getIsBusinessOrder() {
		return isBusinessOrder;
	}

	public void setIsBusinessOrder(Boolean isBusinessOrder) {
		this.isBusinessOrder = isBusinessOrder;
	}

	public String getPurchaseOrderNumber() {
		return purchaseOrderNumber;
	}

	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}

	public Boolean getIsPrime() {
		return isPrime;
	}

	public void setIsPrime(Boolean isPrime) {
		this.isPrime = isPrime;
	}

	public Boolean getIsPremiumOrder() {
		return isPremiumOrder;
	}

	public void setIsPremiumOrder(Boolean isPremiumOrder) {
		this.isPremiumOrder = isPremiumOrder;
	}	

	public String getAsin() {
		return asin;
	}

	public void setAsin(String asin) {
		this.asin = asin;
	}

	public String getSellerSKU() {
		return sellerSKU;
	}

	public void setSellerSKU(String sellerSKU) {
		this.sellerSKU = sellerSKU;
	}

	public String getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(String orderItemId) {
		this.orderItemId = orderItemId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getQuantityOrdered() {
		return quantityOrdered;
	}

	public void setQuantityOrdered(int quantityOrdered) {
		this.quantityOrdered = quantityOrdered;
	}

	public Integer getQuantityShipped() {
		return quantityShipped;
	}

	public void setQuantityShipped(Integer quantityShipped) {
		this.quantityShipped = quantityShipped;
	}

	public Double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(Double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public Double getShippingPrice() {
		return shippingPrice;
	}

	public void setShippingPrice(Double shippingPrice) {
		this.shippingPrice = shippingPrice;
	}

	public Double getGiftWrapPrice() {
		return giftWrapPrice;
	}

	public void setGiftWrapPrice(Double giftWrapPrice) {
		this.giftWrapPrice = giftWrapPrice;
	}

	public Double getItemTax() {
		return itemTax;
	}

	public void setItemTax(Double itemTax) {
		this.itemTax = itemTax;
	}

	public Double getShippingTax() {
		return shippingTax;
	}

	public void setShippingTax(Double shippingTax) {
		this.shippingTax = shippingTax;
	}

	public Double getGiftWrapTax() {
		return giftWrapTax;
	}

	public void setGiftWrapTax(Double giftWrapTax) {
		this.giftWrapTax = giftWrapTax;
	}

	public Double getShippingDiscount() {
		return shippingDiscount;
	}

	public void setShippingDiscount(Double shippingDiscount) {
		this.shippingDiscount = shippingDiscount;
	}

	public Double getPromotionDiscount() {
		return promotionDiscount;
	}

	public void setPromotionDiscount(Double promotionDiscount) {
		this.promotionDiscount = promotionDiscount;
	}

	public String getPromotionIds() {
		return promotionIds;
	}

	public void setPromotionIds(String promotionIds) {
		this.promotionIds = promotionIds;
	}

	public Double getCodFee() {
		return codFee;
	}

	public void setCodFee(Double codFee) {
		this.codFee = codFee;
	}

	public Double getCodFeeDiscount() {
		return codFeeDiscount;
	}

	public void setCodFeeDiscount(Double codFeeDiscount) {
		this.codFeeDiscount = codFeeDiscount;
	}

	public String getGiftMessageText() {
		return giftMessageText;
	}

	public void setGiftMessageText(String giftMessageText) {
		this.giftMessageText = giftMessageText;
	}

	public String getGiftWrapLevel() {
		return giftWrapLevel;
	}

	public void setGiftWrapLevel(String giftWrapLevel) {
		this.giftWrapLevel = giftWrapLevel;
	}

	public String getConditionNote() {
		return conditionNote;
	}

	public void setConditionNote(String conditionNote) {
		this.conditionNote = conditionNote;
	}

	public String getConditionId() {
		return conditionId;
	}

	public void setConditionId(String conditionId) {
		this.conditionId = conditionId;
	}

	public String getConditionSubtypeId() {
		return conditionSubtypeId;
	}

	public void setConditionSubtypeId(String conditionSubtypeId) {
		this.conditionSubtypeId = conditionSubtypeId;
	}

	public String getScheduledDeliveryStartDate() {
		return scheduledDeliveryStartDate;
	}

	public void setScheduledDeliveryStartDate(String scheduledDeliveryStartDate) {
		this.scheduledDeliveryStartDate = scheduledDeliveryStartDate;
	}

	public String getScheduledDeliveryEndDate() {
		return scheduledDeliveryEndDate;
	}

	public void setScheduledDeliveryEndDate(String scheduledDeliveryEndDate) {
		this.scheduledDeliveryEndDate = scheduledDeliveryEndDate;
	}

	public String getPriceDesignation() {
		return priceDesignation;
	}

	public void setPriceDesignation(String priceDesignation) {
		this.priceDesignation = priceDesignation;
	}

	public AmazonOrder(com.amazonservices.mws.orders._2013_09_01.model.Order order, com.amazonservices.mws.orders._2013_09_01.model.OrderItem orderItem) {
		super();
		this.amazonOrderId = order.getAmazonOrderId();
		this.sellerOrderId = order.getSellerOrderId();
		this.purchaseDate = ConverterClass.getDate(order.getPurchaseDate());
		this.lastUpdateDate = ConverterClass.getDate(order.getLastUpdateDate());
		this.orderStatus = order.getOrderStatus();
		this.fulfillmentChannel = order.getFulfillmentChannel();
		this.salesChannel = order.getSalesChannel();
		this.orderChannel = order.getOrderChannel();
		this.shipServiceLevel = order.getShipServiceLevel();
		this.orderTotal = new Double(order.getOrderTotal().getAmount());
		this.numberOfItemsShipped = order.getNumberOfItemsShipped();
		this.numberOfItemsUnshipped = order.getNumberOfItemsUnshipped();
		this.paymentMethod = order.getPaymentMethod();
		this.marketplaceId = order.getMarketplaceId();
		this.buyerEmail = order.getBuyerEmail();
		this.buyerName = order.getBuyerName();
		this.shipmentServiceLevelCategory = order.getShipmentServiceLevelCategory();
		this.shippedByAmazonTFM = order.getShippedByAmazonTFM();
		this.tfmShipmentStatus = order.getTFMShipmentStatus();
		this.cbaDisplayableShippingLabel = order.getCbaDisplayableShippingLabel();
		this.orderType = order.getOrderType();
		this.earliestShipDate = ConverterClass.getDate(order.getEarliestShipDate());
		this.latestShipDate = ConverterClass.getDate(order.getLatestShipDate());
		this.earliestDeliveryDate = ConverterClass.getDate(order.getEarliestDeliveryDate());
		this.latestDeliveryDate = ConverterClass.getDate(order.getLatestDeliveryDate());
		this.isBusinessOrder = order.getIsBusinessOrder();
		this.purchaseOrderNumber = order.getPurchaseOrderNumber();
		this.isPrime = order.getIsPrime();
		this.isPremiumOrder = order.getIsPremiumOrder();
		this.asin = orderItem.getASIN();
		this.sellerSKU = orderItem.getSellerSKU();
		this.orderItemId = orderItem.getOrderItemId();
		this.mainOrderId = this.amazonOrderId + "-" + this.orderItemId; 
		this.title = orderItem.getTitle();
		this.quantityOrdered = orderItem.getQuantityOrdered();
		this.quantityShipped = orderItem.getQuantityShipped();
		if(orderItem.getItemPrice() != null)
		this.itemPrice = new Double(orderItem.getItemPrice().getAmount());
		if(orderItem.getShippingPrice() != null)
		this.shippingPrice = new Double(orderItem.getShippingPrice().getAmount());
		if(orderItem.getGiftWrapPrice() != null)
		this.giftWrapPrice = new Double(orderItem.getGiftWrapPrice().getAmount());
		if(orderItem.getItemTax() != null)
		this.itemTax = new Double(orderItem.getItemTax().getAmount());
		if(orderItem.getShippingTax() != null)
		this.shippingTax = new Double(orderItem.getShippingTax().getAmount());
		if(orderItem.getGiftWrapTax() != null)
		this.giftWrapTax = new Double(orderItem.getGiftWrapTax().getAmount());
		if(orderItem.getShippingDiscount() != null)
		this.shippingDiscount = new Double(orderItem.getShippingDiscount().getAmount());
		if(orderItem.getPromotionDiscount() != null)
		this.promotionDiscount = new Double(orderItem.getPromotionDiscount().getAmount());
		if(orderItem.getPromotionIds() != null)
		this.promotionIds = orderItem.getPromotionIds().toString();
		if(orderItem.getCODFee() != null)
		this.codFee = new Double(orderItem.getCODFee().getAmount());
		if(orderItem.getCODFeeDiscount() != null)
		this.codFeeDiscount = new Double(orderItem.getCODFeeDiscount().getAmount());
		this.giftMessageText = orderItem.getGiftMessageText();
		this.giftWrapLevel = orderItem.getGiftWrapLevel();
		this.conditionNote = orderItem.getConditionNote();
		this.conditionId = orderItem.getConditionId();
		this.conditionSubtypeId = orderItem.getConditionSubtypeId();
		this.scheduledDeliveryStartDate = orderItem.getScheduledDeliveryStartDate();
		this.scheduledDeliveryEndDate = orderItem.getScheduledDeliveryEndDate();
		this.priceDesignation = orderItem.getPriceDesignation();
	}
}
