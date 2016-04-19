package com.o2r.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.o2r.bean.ChannelSalesDetails;
import com.o2r.bean.TotalShippedOrder;
import com.o2r.dao.ReportsGeneratorDao;
import com.o2r.dao.ReportsGeneratorDao2;
import com.o2r.helper.CustomException;

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
 private ReportsGeneratorDao2 reportGeneratorDao2;
 

@Override
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public TotalShippedOrder getPartnerTSOdetails(String pcName,Date startDate ,Date endDate, int sellerId)throws CustomException
{
	return reportGeneratorDao.getPartnerTSOdetails(pcName, startDate, endDate, sellerId);
}





@Override
public List<TotalShippedOrder> getProductSalesDetails(Date startDate,Date endDate, int sellerId) throws CustomException {
	// TODO Auto-generated method stub
	return reportGeneratorDao2.getProductSalesDetails(startDate, endDate, sellerId);
}





@Override
public List<TotalShippedOrder> getAllPartnerTSOdetails(Date startDate,
		Date endDate, int sellerId) throws CustomException {
	// TODO Auto-generated method stub
	return reportGeneratorDao.getAllPartnerTSOdetails(startDate, endDate, sellerId);
}





@Override
public List<ChannelSalesDetails> getChannelSalesDetails(Date startDate,
		Date endDate, int sellerId) throws CustomException {
	// TODO Auto-generated method stub
	return reportGeneratorDao2.getChannelSalesDetails(startDate, endDate, sellerId);
}

/*
 * 
 * @Override
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public List<TotalShippedOrder> getAllPartnerTSOdetails(Date startDate ,Date endDate, int sellerId)throws CustomException
{
	return reportGeneratorDao.getAllPartnerTSOdetails(startDate, endDate, sellerId);
}


@Override
public List<ChannelSalesDetails> getChannelSalesDetails(Date startDate,Date endDate, int sellerId) throws CustomException {
	return reportGeneratorDao2.getChannelSalesDetails(startDate, endDate, sellerId);
}
 * 
 */



}