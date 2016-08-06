/**
 * @Auther Deep Mehrotra
 */
package com.o2r.service;

import com.o2r.helper.CustomException;
import com.o2r.model.ChannelUploadMapping;

public interface UploadMappingService {
	
	public void addChannelUploadMapping(ChannelUploadMapping uploadMapping
			) throws CustomException;

	public ChannelUploadMapping getChannelUploadMapping(long mapId)
			throws CustomException;

	public ChannelUploadMapping getChannelUploadMapping(String channelName,
			String fileName) throws CustomException;
	 
}
