package com.o2r.model;

// Generated Oct 23, 2016 4:48:17 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * AmazonOrderItemInfo generated by hbm2java
 */
@Entity
@Table(name = "amazon_order_item_info", catalog = "o2rschema")
public class AmazonOrderItemInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String amazonorderid;
	private String asin;
	private String sellersku;
	private String orderitemid;
	private String title;
	private String quantityordered;
	private String quantityshipped;
	private String pointsgrantedpointsnumber;
	private String pointsgrantedcurrencycode;
	private String pointsgrantedamount;
	private String itempricecurrencycode;
	private String itempriceamount;
	private String shippingpricecurrencycode;
	private String shippingpriceamount;
	private String giftwrappricecurrencycode;
	private String giftwrappriceamount;
	private String itemtaxcurrencycode;
	private String itemtaxamount;
	private String shippingtaxcurrencycode;
	private String shippingtaxamount;
	private String giftwraptaxcurrencycode;
	private String giftwraptaxamount;
	private String shippingdiscountcurrencycode;
	private String shippingdiscountamount;
	private String promotiondiscountcurrencycode;
	private String promotiondiscountamount;
	private String promotionids;
	private String codfeecurrencycode;
	private String codfeeamount;
	private String codfeediscountcurrencycode;
	private String codfeediscountamount;
	private String giftmessagetext;
	private String giftwraplevel;
	private String invoicedatainvoicerequirement;
	private String invoicedatabuyerselectedinvoicecategory;
	private String invoicedatainvoicetitle;
	private String invoicedatainvoiceinformation;
	private String conditionnote;
	private String conditionid;
	private String conditionsubtypeid;
	private String scheduleddeliverystartdate;
	private String scheduleddeliveryenddate;
	private String pricedesignation;
	private String buyercustomizedurl;
	private String requestid;
	//private int orderId=2;
	private int orderId;
	private AmazonOrderInfo amazonOrderInfo;
	
	
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ITEM_ID", unique = true, nullable = false)
	public int getId() {
		return this.id;
		//return 2;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "AMAZONORDER_ID")
	public String getAmazonorderid() {
		return this.amazonorderid;
	}

	public void setAmazonorderid(String amazonorderid) {
		this.amazonorderid = amazonorderid;
	}

	@Column(name = "ASIN")
	public String getAsin() {
		return this.asin;
	}

	public void setAsin(String asin) {
		this.asin = asin;
	}

	@Column(name = "SELLERSKU")
	public String getSellersku() {
		return this.sellersku;
	}

	public void setSellersku(String sellersku) {
		this.sellersku = sellersku;
	}

	@Column(name = "ORDERITEMID")
	public String getOrderitemid() {
		return this.orderitemid;
	}

	public void setOrderitemid(String orderitemid) {
		this.orderitemid = orderitemid;
	}

	@Column(name = "TITLE")
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "QUANTITYORDERED")
	public String getQuantityordered() {
		return this.quantityordered;
	}

	public void setQuantityordered(String quantityordered) {
		this.quantityordered = quantityordered;
	}

	@Column(name = "QUANTITYSHIPPED")
	public String getQuantityshipped() {
		return this.quantityshipped;
	}

	public void setQuantityshipped(String quantityshipped) {
		this.quantityshipped = quantityshipped;
	}

	@Column(name = "POINTSGRANTEDPOINTSNUMBER")
	public String getPointsgrantedpointsnumber() {
		return this.pointsgrantedpointsnumber;
	}

	public void setPointsgrantedpointsnumber(String pointsgrantedpointsnumber) {
		this.pointsgrantedpointsnumber = pointsgrantedpointsnumber;
	}

	@Column(name = "POINTSGRANTEDCURRENCYCODE")
	public String getPointsgrantedcurrencycode() {
		return this.pointsgrantedcurrencycode;
	}

	public void setPointsgrantedcurrencycode(String pointsgrantedcurrencycode) {
		this.pointsgrantedcurrencycode = pointsgrantedcurrencycode;
	}

	@Column(name = "POINTSGRANTEDAMOUNT")
	public String getPointsgrantedamount() {
		return this.pointsgrantedamount;
	}

	public void setPointsgrantedamount(String pointsgrantedamount) {
		this.pointsgrantedamount = pointsgrantedamount;
	}

	@Column(name = "ITEMPRICECURRENCYCODE")
	public String getItempricecurrencycode() {
		return this.itempricecurrencycode;
	}

	public void setItempricecurrencycode(String itempricecurrencycode) {
		this.itempricecurrencycode = itempricecurrencycode;
	}

	@Column(name = "ITEMPRICEAMOUNT")
	public String getItempriceamount() {
		return this.itempriceamount;
	}

	public void setItempriceamount(String itempriceamount) {
		this.itempriceamount = itempriceamount;
	}

	@Column(name = "SHIPPINGPRICECURRENCYCODE")
	public String getShippingpricecurrencycode() {
		return this.shippingpricecurrencycode;
	}

	public void setShippingpricecurrencycode(String shippingpricecurrencycode) {
		this.shippingpricecurrencycode = shippingpricecurrencycode;
	}

	@Column(name = "SHIPPINGPRICEAMOUNT")
	public String getShippingpriceamount() {
		return this.shippingpriceamount;
	}

	public void setShippingpriceamount(String shippingpriceamount) {
		this.shippingpriceamount = shippingpriceamount;
	}

	@Column(name = "GIFTWRAPPRICECURRENCYCODE")
	public String getGiftwrappricecurrencycode() {
		return this.giftwrappricecurrencycode;
	}

	public void setGiftwrappricecurrencycode(String giftwrappricecurrencycode) {
		this.giftwrappricecurrencycode = giftwrappricecurrencycode;
	}

	@Column(name = "GIFTWRAPPRICEAMOUNT")
	public String getGiftwrappriceamount() {
		return this.giftwrappriceamount;
	}

	public void setGiftwrappriceamount(String giftwrappriceamount) {
		this.giftwrappriceamount = giftwrappriceamount;
	}

	@Column(name = "ITEMTAXCURRENCYCODE")
	public String getItemtaxcurrencycode() {
		return this.itemtaxcurrencycode;
	}

	public void setItemtaxcurrencycode(String itemtaxcurrencycode) {
		this.itemtaxcurrencycode = itemtaxcurrencycode;
	}

	@Column(name = "ITEMTAXAMOUNT")
	public String getItemtaxamount() {
		return this.itemtaxamount;
	}

	public void setItemtaxamount(String itemtaxamount) {
		this.itemtaxamount = itemtaxamount;
	}

	@Column(name = "SHIPPINGTAXCURRENCYCODE")
	public String getShippingtaxcurrencycode() {
		return this.shippingtaxcurrencycode;
	}

	public void setShippingtaxcurrencycode(String shippingtaxcurrencycode) {
		this.shippingtaxcurrencycode = shippingtaxcurrencycode;
	}

	@Column(name = "SHIPPINGTAXAMOUNT")
	public String getShippingtaxamount() {
		return this.shippingtaxamount;
	}

	public void setShippingtaxamount(String shippingtaxamount) {
		this.shippingtaxamount = shippingtaxamount;
	}

	@Column(name = "GIFTWRAPTAXCURRENCYCODE")
	public String getGiftwraptaxcurrencycode() {
		return this.giftwraptaxcurrencycode;
	}

	public void setGiftwraptaxcurrencycode(String giftwraptaxcurrencycode) {
		this.giftwraptaxcurrencycode = giftwraptaxcurrencycode;
	}

	@Column(name = "GIFTWRAPTAXAMOUNT")
	public String getGiftwraptaxamount() {
		return this.giftwraptaxamount;
	}

	public void setGiftwraptaxamount(String giftwraptaxamount) {
		this.giftwraptaxamount = giftwraptaxamount;
	}

	@Column(name = "SHIPPINGDISCOUNTCURRENCYCODE")
	public String getShippingdiscountcurrencycode() {
		return this.shippingdiscountcurrencycode;
	}

	public void setShippingdiscountcurrencycode(
			String shippingdiscountcurrencycode) {
		this.shippingdiscountcurrencycode = shippingdiscountcurrencycode;
	}

	@Column(name = "SHIPPINGDISCOUNTAMOUNT")
	public String getShippingdiscountamount() {
		return this.shippingdiscountamount;
	}

	public void setShippingdiscountamount(String shippingdiscountamount) {
		this.shippingdiscountamount = shippingdiscountamount;
	}

	@Column(name = "PROMOTIONDISCOUNTCURRENCYCODE")
	public String getPromotiondiscountcurrencycode() {
		return this.promotiondiscountcurrencycode;
	}

	public void setPromotiondiscountcurrencycode(
			String promotiondiscountcurrencycode) {
		this.promotiondiscountcurrencycode = promotiondiscountcurrencycode;
	}

	@Column(name = "PROMOTIONDISCOUNTAMOUNT")
	public String getPromotiondiscountamount() {
		return this.promotiondiscountamount;
	}

	public void setPromotiondiscountamount(String promotiondiscountamount) {
		this.promotiondiscountamount = promotiondiscountamount;
	}

	@Column(name = "PROMOTIONIDS")
	public String getPromotionids() {
		return this.promotionids;
	}

	public void setPromotionids(String promotionids) {
		this.promotionids = promotionids;
	}

	@Column(name = "CODFEECURRENCYCODE")
	public String getCodfeecurrencycode() {
		return this.codfeecurrencycode;
	}

	public void setCodfeecurrencycode(String codfeecurrencycode) {
		this.codfeecurrencycode = codfeecurrencycode;
	}

	@Column(name = "CODFEEAMOUNT")
	public String getCodfeeamount() {
		return this.codfeeamount;
	}

	public void setCodfeeamount(String codfeeamount) {
		this.codfeeamount = codfeeamount;
	}

	@Column(name = "CODFEEDISCOUNTCURRENCYCODE")
	public String getCodfeediscountcurrencycode() {
		return this.codfeediscountcurrencycode;
	}

	public void setCodfeediscountcurrencycode(String codfeediscountcurrencycode) {
		this.codfeediscountcurrencycode = codfeediscountcurrencycode;
	}

	@Column(name = "CODFEEDISCOUNTAMOUNT")
	public String getCodfeediscountamount() {
		return this.codfeediscountamount;
	}

	public void setCodfeediscountamount(String codfeediscountamount) {
		this.codfeediscountamount = codfeediscountamount;
	}

	@Column(name = "GIFTMESSAGETEXT")
	public String getGiftmessagetext() {
		return this.giftmessagetext;
	}

	public void setGiftmessagetext(String giftmessagetext) {
		this.giftmessagetext = giftmessagetext;
	}

	@Column(name = "GIFTWRAPLEVEL")
	public String getGiftwraplevel() {
		return this.giftwraplevel;
	}

	public void setGiftwraplevel(String giftwraplevel) {
		this.giftwraplevel = giftwraplevel;
	}

	@Column(name = "INVOICEDATAINVOICEREQUIREMENT")
	public String getInvoicedatainvoicerequirement() {
		return this.invoicedatainvoicerequirement;
	}

	public void setInvoicedatainvoicerequirement(
			String invoicedatainvoicerequirement) {
		this.invoicedatainvoicerequirement = invoicedatainvoicerequirement;
	}

	@Column(name = "INVOICEDATABUYERSELECTEDINVOICECATEGORY")
	public String getInvoicedatabuyerselectedinvoicecategory() {
		return this.invoicedatabuyerselectedinvoicecategory;
	}

	public void setInvoicedatabuyerselectedinvoicecategory(
			String invoicedatabuyerselectedinvoicecategory) {
		this.invoicedatabuyerselectedinvoicecategory = invoicedatabuyerselectedinvoicecategory;
	}

	@Column(name = "INVOICEDATAINVOICETITLE")
	public String getInvoicedatainvoicetitle() {
		return this.invoicedatainvoicetitle;
	}

	public void setInvoicedatainvoicetitle(String invoicedatainvoicetitle) {
		this.invoicedatainvoicetitle = invoicedatainvoicetitle;
	}

	@Column(name = "INVOICEDATAINVOICEINFORMATION")
	public String getInvoicedatainvoiceinformation() {
		return this.invoicedatainvoiceinformation;
	}

	public void setInvoicedatainvoiceinformation(
			String invoicedatainvoiceinformation) {
		this.invoicedatainvoiceinformation = invoicedatainvoiceinformation;
	}

	@Column(name = "CONDITIONNOTE")
	public String getConditionnote() {
		return this.conditionnote;
	}

	public void setConditionnote(String conditionnote) {
		this.conditionnote = conditionnote;
	}

	@Column(name = "CONDITIONID")
	public String getConditionid() {
		return this.conditionid;
	}

	public void setConditionid(String conditionid) {
		this.conditionid = conditionid;
	}

	@Column(name = "CONDITIONSUBTYPEID")
	public String getConditionsubtypeid() {
		return this.conditionsubtypeid;
	}

	public void setConditionsubtypeid(String conditionsubtypeid) {
		this.conditionsubtypeid = conditionsubtypeid;
	}

	@Column(name = "SCHEDULEDDELIVERYSTARTDATE")
	public String getScheduleddeliverystartdate() {
		return this.scheduleddeliverystartdate;
	}

	public void setScheduleddeliverystartdate(String scheduleddeliverystartdate) {
		this.scheduleddeliverystartdate = scheduleddeliverystartdate;
	}

	@Column(name = "SCHEDULEDDELIVERYENDDATE")
	public String getScheduleddeliveryenddate() {
		return this.scheduleddeliveryenddate;
	}

	public void setScheduleddeliveryenddate(String scheduleddeliveryenddate) {
		this.scheduleddeliveryenddate = scheduleddeliveryenddate;
	}

	@Column(name = "PRICEDESIGNATION")
	public String getPricedesignation() {
		return this.pricedesignation;
	}

	public void setPricedesignation(String pricedesignation) {
		this.pricedesignation = pricedesignation;
	}

	@Column(name = "BUYERCUSTOMIZEDURL")
	public String getBuyercustomizedurl() {
		return this.buyercustomizedurl;
	}

	public void setBuyercustomizedurl(String buyercustomizedurl) {
		this.buyercustomizedurl = buyercustomizedurl;
	}

	@Column(name = "REQUESTID")
	public String getRequestid() {
		return this.requestid;
	}

	public void setRequestid(String requestid) {
		this.requestid = requestid;
	}
	
	 @Column(name="ORDER_ID") 
	 public Integer getOrderId() {
		 return	 orderId; 
	 }
	 
	 public void setOrderId(Integer orderId) {
		  this.orderId = orderId; 
	 }
	 
	 @ManyToOne
		@JoinColumn(name="ORDER_ID", insertable=false, updatable=false)
		public AmazonOrderInfo getAmazonOrderInfo() {
			return amazonOrderInfo;
		}

		public void setAmazonOrderInfo(AmazonOrderInfo amazonOrderInfo) {
			this.amazonOrderInfo = amazonOrderInfo;
		}
		

}
