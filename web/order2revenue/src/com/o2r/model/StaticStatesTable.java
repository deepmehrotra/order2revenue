package com.o2r.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tbl_states")
public class StaticStatesTable {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private long id;
	@Column
	private String state_name;
	@Column
	private long country_id;
	@Column
	private boolean status;
	@Column
	private boolean is_deleted;
	@Column
	private String temp_name;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getState_name() {
		return state_name;
	}
	public void setState_name(String state_name) {
		this.state_name = state_name;
	}
	public long getCountry_id() {
		return country_id;
	}
	public void setCountry_id(long country_id) {
		this.country_id = country_id;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public boolean isIs_deleted() {
		return is_deleted;
	}
	public void setIs_deleted(boolean is_deleted) {
		this.is_deleted = is_deleted;
	}
	public String getTemp_name() {
		return temp_name;
	}
	public void setTemp_name(String temp_name) {
		this.temp_name = temp_name;
	}
	

}
