package com.o2r.bean;

import java.util.ArrayList;
import java.util.List;

public class PartnerCategoryBean {
	
	private long id;
	private String partnerName;
	private String partnerCategoryRef;
	private float commission;
	
	private List<ProductBean> productList = new ArrayList<ProductBean>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getPartnerCategoryRef() {
		return partnerCategoryRef;
	}

	public void setPartnerCategoryRef(String partnerCategoryRef) {
		this.partnerCategoryRef = partnerCategoryRef;
	}

	public float getCommission() {
		return commission;
	}

	public void setCommission(float commission) {
		this.commission = commission;
	}

	public List<ProductBean> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductBean> productList) {
		this.productList = productList;
	}
	


}
