package com.o2r.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="seller_state_deliverytime")
public class StateDeliveryTime {

	@Id
	@GeneratedValue
	@Column
	private Integer id;
	
	@Column
	private int deliveryTime;
	
	@OneToOne(cascade=CascadeType.ALL)
	//@JoinTable(name="states",
	//	joinColumns = {@JoinColumn(name="id", referencedColumnName="id")}
	//)
	private State state;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Seller seller;
	
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
	
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}
}
