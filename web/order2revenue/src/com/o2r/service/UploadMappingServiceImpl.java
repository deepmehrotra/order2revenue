/**
 * @Auther Deep Mehrotra
 */
package com.o2r.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.o2r.dao.UploadMappingDao;
import com.o2r.helper.CustomException;
import com.o2r.model.ChannelUploadMapping;

@Service("uploadMappingService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UploadMappingServiceImpl implements UploadMappingService {

	@Autowired
	 private UploadMappingDao uploadMappingDao;
	
	@Override
	public void addChannelUploadMapping(ChannelUploadMapping uploadMapping,
			int sellerId) throws CustomException
			{
		uploadMappingDao.addChannelUploadMapping(uploadMapping, sellerId);
			}
	
	@Override
	public ChannelUploadMapping getChannelUploadMapping(long mapId)
			throws CustomException
			{
		return uploadMappingDao.getChannelUploadMapping(mapId);
			}
	@Override
	public ChannelUploadMapping getChannelUploadMapping(String channelName,
			String fileName, int sellerId) throws CustomException
			{
		return uploadMappingDao.getChannelUploadMapping(channelName,fileName,sellerId);
		
			}

}
