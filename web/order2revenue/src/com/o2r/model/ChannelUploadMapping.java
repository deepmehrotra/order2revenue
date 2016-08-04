package com.o2r.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ChannelUploadMapping")
public class ChannelUploadMapping {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long mapId;
	@Column
	private String channelName;
	@Column
	private String fileName;
		
	@OneToMany(mappedBy = "uploadMapping", cascade = CascadeType.ALL)
	private List<ColumMap> columMap = new ArrayList<>();
	
	public long getMapId() {
		return mapId;
	}

	public void setMapId(long mapId) {
		this.mapId = mapId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public List<ColumMap> getColumMap() {
		return columMap;
	}

	public void setColumMap(List<ColumMap> columMap) {
		this.columMap = columMap;
	}


	

}
