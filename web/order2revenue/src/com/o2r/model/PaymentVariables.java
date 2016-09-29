package com.o2r.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PaymentVariables")
public class PaymentVariables {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private long payvarId;
	@Column
	private String payKey;
	@Column
	private String payValue;
	@Column
	private String description;
	
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getPayvarId() {
		return payvarId;
	}
	public void setPayvarId(long payvarId) {
		this.payvarId = payvarId;
	}
	public String getPayKey() {
		return payKey;
	}
	public void setPayKey(String payKey) {
		this.payKey = payKey;
	}
	public String getPayValue() {
		return payValue;
	}
	public void setPayValue(String payValue) {
		this.payValue = payValue;
	}
	
		
	
	

}
