/**
 * @Auther Deep Mehrotra
 */
package com.o2r.dao;

import com.o2r.helper.CustomException;
import com.o2r.model.ChannelUploadMapping;



public interface UploadMappingDao {


	public ChannelUploadMapping getChannelUploadMapping(long mapId)
			throws CustomException;

	public void addChannelUploadMapping(ChannelUploadMapping uploadMapping)
			throws CustomException;

	public ChannelUploadMapping getChannelUploadMapping(String channelName,
			String fileName) throws CustomException;
	
	
}
