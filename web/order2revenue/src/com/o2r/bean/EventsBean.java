package com.o2r.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.o2r.model.NRnReturnConfig;
import com.o2r.model.Partner;

public class EventsBean {

	private int sellerId;
	private int eventId;
	private String eventName;
	private String channelName;
	private Date startDate;
	private Date endDate;
	private String productCategories;
	private String nrType;
	private String returnCharges;
	private NRnReturnConfig nrnReturnConfig;
	private Partner partner;
	private Date createdDate;
	private long netSalesQuantity;
	private double netSalesAmount;
	private String skuList;
	private String status;
	private String selectAll;

	private List<ChargesBean> fixedfeeList = new ArrayList<ChargesBean>();
	private List<ChargesBean> shippingfeeVolumeFixedList = new ArrayList<ChargesBean>();
	private List<ChargesBean> shippingFeeWeightFixedList = new ArrayList<ChargesBean>();
	private List<ChargesBean> shippingfeeVolumeVariableList = new ArrayList<ChargesBean>();
	private List<ChargesBean> shippingfeeWeightVariableList = new ArrayList<ChargesBean>();

	public int getSellerId() {
		return sellerId;
	}

	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getProductCategories() {
		return productCategories;
	}

	public void setProductCategories(String productCategories) {
		this.productCategories = productCategories;
	}

	public String getNrType() {
		return nrType;
	}

	public void setNrType(String nrType) {
		this.nrType = nrType;
	}

	public String getReturnCharges() {
		return returnCharges;
	}

	public void setReturnCharges(String returnCharges) {
		this.returnCharges = returnCharges;
	}

	public NRnReturnConfig getNrnReturnConfig() {
		return nrnReturnConfig;
	}

	public void setNrnReturnConfig(NRnReturnConfig nrnReturnConfig) {
		this.nrnReturnConfig = nrnReturnConfig;
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public long getNetSalesQuantity() {
		return netSalesQuantity;
	}

	public void setNetSalesQuantity(long netSalesQuantity) {
		this.netSalesQuantity = netSalesQuantity;
	}

	public double getNetSalesAmount() {
		return netSalesAmount;
	}

	public void setNetSalesAmount(double netSalesAmount) {
		this.netSalesAmount = netSalesAmount;
	}

	public List<ChargesBean> getFixedfeeList() {
		return fixedfeeList;
	}

	public void setFixedfeeList(List<ChargesBean> fixedfeeList) {
		this.fixedfeeList = fixedfeeList;
	}

	public ChargesBean getChargesBean(String type, String criteria,
			long criteriaRange) {
		ChargesBean returnBean = null;
		if (type.equalsIgnoreCase("fixedfee")) {
			for (ChargesBean bean : this.fixedfeeList) {
				if (bean.getCriteria().equalsIgnoreCase(criteria)
						&& bean.getRange() == criteriaRange) {
					returnBean = bean;
				}
			}
		} else if (type.equalsIgnoreCase("shippingfeeVolumeFixed")) {
			for (ChargesBean bean : this.shippingfeeVolumeFixedList) {
				if (bean.getCriteria().equalsIgnoreCase(criteria)
						&& bean.getRange() == criteriaRange) {
					returnBean = bean;
				}
			}
		} else if (type.equalsIgnoreCase("shippingFeeWeightFixed")) {
			for (ChargesBean bean : this.shippingFeeWeightFixedList) {
				if (bean.getCriteria().equalsIgnoreCase(criteria)
						&& bean.getRange() == criteriaRange) {
					returnBean = bean;
				}
			}
		} else if (type.equalsIgnoreCase("shippingfeeVolumeVariable")) {
			for (ChargesBean bean : this.shippingfeeVolumeVariableList) {
				if (bean.getCriteria().equalsIgnoreCase(criteria)
						&& bean.getRange() == criteriaRange) {
					returnBean = bean;
				}
			}
		} else if (type.equalsIgnoreCase("shippingfeeWeightVariable")) {
			for (ChargesBean bean : this.shippingfeeWeightVariableList) {
				if (bean.getCriteria().equalsIgnoreCase(criteria)
						&& bean.getRange() == criteriaRange) {
					returnBean = bean;
				}
			}
		}
		return returnBean;
	}

	public String getSkuList() {
		return skuList;
	}

	public void setSkuList(String skuList) {
		this.skuList = skuList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ChargesBean> getShippingfeeVolumeFixedList() {
		return shippingfeeVolumeFixedList;
	}

	public void setShippingfeeVolumeFixedList(
			List<ChargesBean> shippingfeeVolumeFixedList) {
		this.shippingfeeVolumeFixedList = shippingfeeVolumeFixedList;
	}

	public List<ChargesBean> getShippingfeeWeightFixedList() {
		return shippingFeeWeightFixedList;
	}

	public void setShippingfeeWeightFixedList(
			List<ChargesBean> shippingFeeWeightFixedList) {
		this.shippingFeeWeightFixedList = shippingFeeWeightFixedList;
	}

	public List<ChargesBean> getShippingfeeVolumeVariableList() {
		return shippingfeeVolumeVariableList;
	}

	public void setShippingfeeVolumeVariableList(
			List<ChargesBean> shippingfeeVolumeVariableList) {
		this.shippingfeeVolumeVariableList = shippingfeeVolumeVariableList;
	}

	public List<ChargesBean> getShippingfeeWeightVariableList() {
		return shippingfeeWeightVariableList;
	}

	public void setShippingfeeWeightVariableList(
			List<ChargesBean> shippingfeeWeightVariableList) {
		this.shippingfeeWeightVariableList = shippingfeeWeightVariableList;
	}

	public String getSelectAll() {
		return selectAll;
	}

	public void setSelectAll(String selectAll) {
		this.selectAll = selectAll;
	}

}
