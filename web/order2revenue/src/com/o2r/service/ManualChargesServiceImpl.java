package com.o2r.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.o2r.dao.ManualChargesDao;
import com.o2r.helper.CustomException;
import com.o2r.model.ManualCharges;

/**
 * @author Deep Mehortra
 *
 */
@Service("manualChargesService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ManualChargesServiceImpl implements ManualChargesService {

 @Autowired
 private ManualChargesDao manualChargesDao;
 

@Override
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public void addManualCharges(ManualCharges manualCharges , int sellerId)throws CustomException {
	manualChargesDao.addManualCharges(manualCharges, sellerId);
	
}

@Override
	public void addListManualCharges(List<ManualCharges> manualCharges,
			int sellerId) throws CustomException {
		manualChargesDao.addListManualCharges(manualCharges, sellerId);
		
	}

@Override
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public List<ManualCharges> listManualCharges(int sellerId)throws CustomException {
	return manualChargesDao.listManualCharges(sellerId);
	
}

@Override
public ManualCharges getManualCharges(int mcId)throws CustomException
{
	return manualChargesDao.getManualCharges(mcId);
}
@Override
public void deleteManualCharges(ManualCharges manualCharges,int sellerId)throws CustomException {
	manualChargesDao.deleteManualCharges(manualCharges, sellerId);
}

@Override
public Double getMCforPaymentID(String paymentId, int sellerId)throws CustomException
{
	return manualChargesDao.getMCforPaymentID(paymentId, sellerId);
}

@Override
public List<ManualCharges> listManualCharges(int sellerId, Date startDate,
		Date endDate) throws CustomException {
	return manualChargesDao.listManualCharges(sellerId, startDate, endDate);
}

}
