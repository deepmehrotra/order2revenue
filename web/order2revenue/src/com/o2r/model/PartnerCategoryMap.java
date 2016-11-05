package com.o2r.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PartnerCategoryMap")
public class PartnerCategoryMap {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long id;
	@Column
	private String partnerName;
	@Column
	private String partnerCategoryRef;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPartnerCategoryRef() {
		return partnerCategoryRef;
	}
	public void setPartnerCategoryRef(String partnerCategoryRef) {
		this.partnerCategoryRef = partnerCategoryRef;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
}
