package com.o2r.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.o2r.dao.AmazonDao;
import com.o2r.helper.CustomException;
import com.o2r.model.AmazonOrder;

@Service("amazonService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AmazonServiceImpl implements AmazonService{
	
	@Autowired
	private AmazonDao amazonDao;
	
	@Override
	public void addAmazonOrder(AmazonOrder amazonOrder) throws CustomException {
		amazonDao.addAmazonOrder(amazonOrder);		
	}
}
