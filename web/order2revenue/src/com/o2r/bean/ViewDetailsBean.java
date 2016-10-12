package com.o2r.bean;
import java.util.Date;
import java.util.List;
import java.util.Map;
public class ViewDetailsBean {

	private String skucode;
	private int grossSaleQty;
	private int returnSaleQty;	
	private int netSaleQty;	 
	private Date startDate  = new Date();
	private Date endDate  = new Date();
	private int sellerID;
	
	/**
	 * @return the sellerID
	 */
	public int getSellerID() {
		return sellerID;
	}
	/**
	 * @param sellerID the sellerID to set
	 */
	public void setSellerID(int sellerID) {
		this.sellerID = sellerID;
	}
	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * @return the skucode
	 */
	public String getSkucode() {
		return skucode;
	}
	/**
	 * @param skucode the skucode to set
	 */
	public void setSkucode(String skucode) {
		this.skucode = skucode;
	}
	/**
	 * @return the grossSaleQty
	 */
	public int getGrossSaleQty() {
		return grossSaleQty;
	}
	/**
	 * @param grossSaleQty the grossSaleQty to set
	 */
	public void setGrossSaleQty(int grossSaleQty) {
		this.grossSaleQty = grossSaleQty;
	}
	/**
	 * @return the returnSaleQty
	 */
	public int getReturnSaleQty() {
		return returnSaleQty;
	}
	/**
	 * @param returnSaleQty the returnSaleQty to set
	 */
	public void setReturnSaleQty(int returnSaleQty) {
		this.returnSaleQty = returnSaleQty;
	}
	/**
	 * @return the netSaleQty
	 */
	public int getNetSaleQty() {
		return netSaleQty;
	}
	/**
	 * @param netSaleQty the netSaleQty to set
	 */
	public void setNetSaleQty(int netSaleQty) {
		this.netSaleQty = netSaleQty;
	}
	           
}
