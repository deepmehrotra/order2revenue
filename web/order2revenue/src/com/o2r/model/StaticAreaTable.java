package com.o2r.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class StaticAreaTable {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private long id;
	@Column
	private String area_name;
	@Column
	private String zipcode;
	@Column
	private long city_id;
	@Column
	private boolean status;
	@Column
	private boolean is_deleted;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getArea_name() {
		return area_name;
	}
	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public long getCity_id() {
		return city_id;
	}
	public void setCity_id(long city_id) {
		this.city_id = city_id;
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
}
