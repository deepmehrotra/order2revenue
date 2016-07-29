package com.o2r.service;

import com.o2r.helper.CustomException;
import com.o2r.model.AmazonOrder;

public interface AmazonService {	
	public void addAmazonOrder(AmazonOrder amazonOrder) throws CustomException;
}
