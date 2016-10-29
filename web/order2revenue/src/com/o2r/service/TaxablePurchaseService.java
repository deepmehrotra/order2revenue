package com.o2r.service;

import java.util.Date;
import java.util.List;

import com.o2r.bean.ExpensesDetails;
import com.o2r.bean.TaxablePurchaseBean;
import com.o2r.helper.CustomException;
import com.o2r.model.ExpenseCategory;
import com.o2r.model.Expenses;
import com.o2r.model.TaxablePurchases;

/**
 * @author Deep Mehrotra
 *
 */
public interface TaxablePurchaseService { 
	
	public List<TaxablePurchases> listTaxablePurchase(int sellerId)
			throws CustomException;

	public int deleteTaxablePurchase(int taxCategory, int sellerId)
			throws CustomException;

	public void addTaxablePurchase(TaxablePurchases taxablePurchas, int sellerId)
			throws CustomException;
	
	public TaxablePurchaseBean getTaxProduct(int taxCategoryID) throws CustomException;
	
}