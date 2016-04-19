package com.o2r.dao;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.o2r.bean.ChannelSalesDetails;
import com.o2r.bean.TotalShippedOrder;
import com.o2r.helper.CustomException;

/**
 * @author Deep Mehrotra
 *
 */

public interface ReportsGeneratorDao2 {
 
	public List<ChannelSalesDetails> getChannelSalesDetails(Date startDate,Date endDate, int sellerId) throws CustomException;

	public List<TotalShippedOrder> getProductSalesDetails(Date startDate,Date endDate, int sellerId) throws CustomException;
	 
 
}