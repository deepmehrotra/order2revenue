package com.o2r.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.o2r.bean.ChannelReportDetails;
import com.o2r.bean.ChannelSalesDetails;
import com.o2r.bean.PartnerReportDetails;
import com.o2r.bean.TotalShippedOrder;
import com.o2r.dao.ReportsGeneratorDao;
import com.o2r.dao.ReportsChannelnCategoryDao;
import com.o2r.helper.CustomException;
import com.o2r.model.UploadReport;

/**
 * @author Deep Mehortra
 *
 */
@Service("reportGeneratorService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ReportGeneratorServiceImpl implements ReportGeneratorService {

	@Autowired
	private ReportsGeneratorDao reportGeneratorDao;
	@Autowired
	private ReportsChannelnCategoryDao reportGeneratorDao2;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public TotalShippedOrder getPartnerTSOdetails(String pcName,
			Date startDate, Date endDate, int sellerId) throws CustomException {
		return reportGeneratorDao.getPartnerTSOdetails(pcName, startDate,
				endDate, sellerId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<TotalShippedOrder> getAllPartnerTSOdetails(Date startDate,
			Date endDate, int sellerId) throws CustomException {
		return reportGeneratorDao.getAllPartnerTSOdetails(startDate, endDate,
				sellerId);
	}

	/*
	 * @Override
	 * 
	 * @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 * public UploadReport addUploadReport(UploadReport uploadReport, int
	 * sellerId) throws CustomException { return
	 * reportGeneratorDao.addUploadReport(uploadReport, sellerId); }
	 * 
	 * @Override
	 * 
	 * @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 * public List<UploadReport> listUploadReport() throws CustomException {
	 * return reportGeneratorDao.listUploadReport(); }
	 */
	@Override
	public List<TotalShippedOrder> getProductSalesDetails(Date startDate,
			Date endDate, int sellerId) throws CustomException {
		// TODO Auto-generated method stub
		return reportGeneratorDao2.getProductSalesDetails(startDate, endDate,
				sellerId);
	}

	@Override
	public List<ChannelSalesDetails> getChannelSalesDetails(Date startDate,
			Date endDate, int sellerId) throws CustomException {
		// TODO Auto-generated method stub
		return reportGeneratorDao2.getChannelSalesDetails(startDate, endDate,
				sellerId);
	}

	@Override
	public List<ChannelSalesDetails> getCategorySalesDetails(Date startDate,
			Date endDate, int sellerIdfromSession) throws CustomException {
		// TODO Auto-generated method stub
		return reportGeneratorDao2.getCategorySalesDetails(startDate, endDate,
				sellerIdfromSession);
	}

	@Override
	public List<ChannelSalesDetails> getPaymentsReceievedDetails(
			Date startDate, Date endDate, int sellerIdfromSession) throws CustomException {
		// TODO Auto-generated method stub
		return reportGeneratorDao2.getPaymentsReceievedDetails(startDate, endDate,
				sellerIdfromSession);
	}

	@Override
	public List<ChannelSalesDetails> getOrderwiseGPDetails(Date startDate,
			Date endDate, int sellerIdfromSession) throws CustomException {
		// TODO Auto-generated method stub
		return reportGeneratorDao2.getOrderwiseGPDetails(startDate, endDate,
				sellerIdfromSession);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<PartnerReportDetails> getPartnerReportDetails(Date startDate,
			Date endDate, int sellerId) throws CustomException {
		return reportGeneratorDao.getPartnerReportDetails(startDate, endDate,
				sellerId);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<PartnerReportDetails> getDebtorsReportDetails(Date startDate,
			Date endDate, int sellerId) throws CustomException {
		return reportGeneratorDao.getDebtorsReportDetails(startDate, endDate,
				sellerId);
	}

	@Override
	public List<ChannelReportDetails> getChannelReportDetails(Date startDate,
			Date endDate, int sellerId) throws CustomException {
		return reportGeneratorDao.getChannelReportDetails(startDate, endDate,
				sellerId);
	}

	/*
	 * 
	 * @Override
	 * 
	 * @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	 * public List<TotalShippedOrder> getAllPartnerTSOdetails(Date startDate
	 * ,Date endDate, int sellerId)throws CustomException { return
	 * reportGeneratorDao.getAllPartnerTSOdetails(startDate, endDate, sellerId);
	 * }
	 * 
	 * 
	 * @Override public List<ChannelSalesDetails> getChannelSalesDetails(Date
	 * startDate,Date endDate, int sellerId) throws CustomException { return
	 * reportGeneratorDao2.getChannelSalesDetails(startDate, endDate, sellerId);
	 * }
	 */

}