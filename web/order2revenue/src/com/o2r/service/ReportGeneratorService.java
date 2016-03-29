package com.o2r.service;

import java.util.Date;
import java.util.List;

import com.o2r.bean.TotalShippedOrder;

/**
 * @author Deep Mehrotra
 *
 */
public interface ReportGeneratorService {
 
	public TotalShippedOrder getPartnerTSOdetails(String pcName,Date startDate ,Date endDate, int sellerId);

	 public List<TotalShippedOrder> getAllPartnerTSOdetails(Date startDate ,Date endDate, int sellerId);
	 
}