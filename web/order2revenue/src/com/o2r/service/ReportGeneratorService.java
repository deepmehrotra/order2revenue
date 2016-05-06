package com.o2r.service;

import java.util.Date;
import java.util.List;

import com.o2r.bean.ChannelSalesDetails;
import com.o2r.bean.TotalShippedOrder;
import com.o2r.helper.CustomException;
import com.o2r.model.UploadReport;

/**
 * @author Deep Mehrotra
 *
 */
public interface ReportGeneratorService {

	public TotalShippedOrder getPartnerTSOdetails(String pcName,
			Date startDate, Date endDate, int sellerId) throws CustomException;

	public List<TotalShippedOrder> getAllPartnerTSOdetails(Date startDate,
			Date endDate, int sellerId) throws CustomException;

	/*public UploadReport addUploadReport(UploadReport uploadReport, int sellerId)
			throws CustomException;

	public List<UploadReport> listUploadReport() throws CustomException;*/
	
	 public List<ChannelSalesDetails> getChannelSalesDetails(Date startDate ,Date endDate, int sellerId)throws CustomException;

	 public List<TotalShippedOrder> getProductSalesDetails(Date startDate ,Date endDate, int sellerId)throws CustomException;

	public List<ChannelSalesDetails> getCategorySalesDetails(Date startDate,
			Date endDate, int sellerIdfromSession) throws CustomException;

	public List<ChannelSalesDetails> getPaymentsReceievedDetails(Date startDate, Date endDate, int sellerIdfromSession) throws CustomException;

	public List<ChannelSalesDetails> getOrderwiseGPDetails(Date startDate,Date endDate, int sellerIdfromSession)  throws CustomException;

}