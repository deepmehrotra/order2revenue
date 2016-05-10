package com.o2r.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class StaticCountryTable {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private int id;
	@Column
	private String name;
	@Column
	private String nicename;
	@Column(length=2)
	private String iso;
	@Column(length=3)
	private String iso3;
	@Column
	private int numcode;
	@Column
	private int phonecode;
	@Column
	private boolean is_deleted;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNicename() {
		return nicename;
	}
	public void setNicename(String nicename) {
		this.nicename = nicename;
	}
	public String getIso() {
		return iso;
	}
	public void setIso(String iso) {
		this.iso = iso;
	}
	public String getIso3() {
		return iso3;
	}
	public void setIso3(String iso3) {
		this.iso3 = iso3;
	}
	public int getNumcode() {
		return numcode;
	}
	public void setNumcode(int numcode) {
		this.numcode = numcode;
	}
	public int getPhonecode() {
		return phonecode;
	}
	public void setPhonecode(int phonecode) {
		this.phonecode = phonecode;
	}
	public boolean isIs_deleted() {
		return is_deleted;
	}
	public void setIs_deleted(boolean is_deleted) {
		this.is_deleted = is_deleted;
	}
	
	
	
}
