package com.o2r.service;

import java.util.Date;
import java.util.List;

import com.o2r.bean.TotalShippedOrder;
import com.o2r.helper.CustomException;

/**
 * @author Deep Mehrotra
 *
 */
public interface ReportGeneratorService {
 
	public TotalShippedOrder getPartnerTSOdetails(String pcName,Date startDate ,Date endDate, int sellerId)throws CustomException;

	 public List<TotalShippedOrder> getAllPartnerTSOdetails(Date startDate ,Date endDate, int sellerId)throws CustomException;
	 
}