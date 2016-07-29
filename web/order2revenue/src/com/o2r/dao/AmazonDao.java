package com.o2r.dao;

import com.o2r.helper.CustomException;
import com.o2r.model.AmazonOrder;

public interface AmazonDao {
	public void addAmazonOrder(AmazonOrder amazonOrder) throws CustomException;
}
