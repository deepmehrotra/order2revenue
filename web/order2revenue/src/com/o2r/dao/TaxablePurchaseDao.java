package com.o2r.dao;

import java.util.List;

import com.o2r.bean.TaxablePurchaseBean;
import com.o2r.helper.CustomException;
import com.o2r.model.TaxablePurchases;

/**
 * @author Deep Mehrotra
 *
 */
public interface TaxablePurchaseDao {

	public void addTaxablePurchase(TaxablePurchases taxablePurchas, int sellerId)
			throws CustomException;

	public List<TaxablePurchases> listTaxablePurchase(int sellerId)
			throws CustomException;

	public int deleteTaxablePurchase(int taxCategory, int sellerId)
			throws CustomException;

	public TaxablePurchaseBean getTaxProduct(int taxCategoryID)
			throws CustomException;


}