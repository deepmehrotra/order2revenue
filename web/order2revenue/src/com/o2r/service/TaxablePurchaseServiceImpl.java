package com.o2r.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.o2r.bean.TaxablePurchaseBean;
import com.o2r.dao.TaxablePurchaseDao;
import com.o2r.helper.CustomException;
import com.o2r.model.TaxablePurchases;

/**
 * @author Deep Mehortra
 *
 */
@Service("TaxablePurchaseService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TaxablePurchaseServiceImpl implements TaxablePurchaseService {

 @Autowired
 private TaxablePurchaseDao taxablePurchaseDao;

@Override
public void addTaxablePurchase(TaxablePurchases taxablePurchas, int sellerId)
		throws CustomException {
	// TODO Auto-generated method stub
	taxablePurchaseDao.addTaxablePurchase(taxablePurchas,sellerId);
	
}

@Override
public List<TaxablePurchases> listTaxablePurchase(int sellerId) throws CustomException {	
	
	// TODO Auto-generated method stub
	return taxablePurchaseDao.listTaxablePurchase(sellerId);
}

@Override
public int deleteTaxablePurchase(int taxCategory, int sellerId)
		throws CustomException {
	// TODO Auto-generated method stub
	//return taxablePurchaseDao.deletePurchase(sellerId,sellerId);
	return taxablePurchaseDao.deleteTaxablePurchase(taxCategory, sellerId);
}



public TaxablePurchaseBean getTaxProduct(int taxCategoryID) throws CustomException{
	
	return taxablePurchaseDao.getTaxProduct(taxCategoryID); 
}



 
 

}
