package com.o2r.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ColumMap")
public class ColumMap {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long columId;
	@Column
	private String channelColumName;
	@Column
	private String o2rColumName;
		
	@ManyToOne(cascade = CascadeType.ALL)
	private ChannelUploadMapping uploadMapping;

	public long getColumId() {
		return columId;
	}

	public void setColumId(long columId) {
		this.columId = columId;
	}

	public String getChannelColumName() {
		return channelColumName;
	}

	public void setChannelColumName(String channelColumName) {
		this.channelColumName = channelColumName;
	}

	public String getO2rColumName() {
		return o2rColumName;
	}

	public void setO2rColumName(String o2rColumName) {
		this.o2rColumName = o2rColumName;
	}

	public ChannelUploadMapping getUploadMapping() {
		return uploadMapping;
	}

	public void setUploadMapping(ChannelUploadMapping uploadMapping) {
		this.uploadMapping = uploadMapping;
	}

	

}
