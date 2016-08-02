/**
 * @Auther Deep Mehrotra
 */
package com.o2r.dao;

import com.o2r.helper.CustomException;
import com.o2r.model.ChannelUploadMapping;



public interface UploadMappingDao {

	public void addChannelUploadMapping(ChannelUploadMapping uploadMapping,
			int sellerId) throws CustomException;

	public ChannelUploadMapping getChannelUploadMapping(long mapId)
			throws CustomException;

	public ChannelUploadMapping getChannelUploadMapping(String channelName,
			String fileName, int sellerId) throws CustomException;
	
	
}
