package com.o2r.bean;

public class StateDeliveryTimeBean {

	private Integer id;
	private int deliveryTime;
	private StateBean state;
	private SellerBean seller;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(int deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	
	public StateBean getState() {
		return state;
	}

	public void setState(StateBean state) {
		this.state = state;
	}

	public SellerBean getSeller() {
		return seller;
	}

	public void setSeller(SellerBean seller) {
		this.seller = seller;
	}

}
