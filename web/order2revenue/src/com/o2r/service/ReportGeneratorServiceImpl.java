package com.o2r.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.o2r.bean.TotalShippedOrder;
import com.o2r.dao.ReportsGeneratorDao;
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
	
	/*@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public UploadReport addUploadReport(UploadReport uploadReport, int sellerId)
			throws CustomException {
		return reportGeneratorDao.addUploadReport(uploadReport, sellerId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<UploadReport> listUploadReport() throws CustomException {
		return reportGeneratorDao.listUploadReport();
	}*/
}