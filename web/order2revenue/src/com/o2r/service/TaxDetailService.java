package com.o2r.service;

import java.util.List;

import org.hibernate.Session;

import com.o2r.helper.CustomException;
import com.o2r.model.TaxCategory;
import com.o2r.model.TaxDetail;

/**
 * @author Deep Mehrotra
 *
 */
public interface TaxDetailService {

	 public void addTaxDetail(TaxDetail taxDetail , int sellerId);

	 public List<TaxDetail> listTaxDetails(int sellerId) throws CustomException;

	 public List<TaxDetail> listTaxDetails(int sellerId,String taxOrTds) throws CustomException;

	 public TaxDetail getTaxDetail(int taxDetailId);

	 public void deleteTaxDetail(TaxDetail taxDetail,int sellerId);

	 public TaxCategory addTaxCategory(TaxCategory taxCategory, int sellerId) throws CustomException;

	 public TaxCategory getTaxCategory(int tcId);

	 public List<TaxCategory> listTaxCategories(int sellerId) throws CustomException;

	 public void deleteTaxCategory(TaxCategory taxCategory, int sellerId) throws CustomException;

	 public TaxCategory getTaxCategory(String catName, int sellerId) throws CustomException;

	 public void addPaymentTaxDetail(TaxDetail taxDetail, int sellerId) throws CustomException;

	 public TaxDetail addMonthlyTaxDetail(Session session, TaxDetail taxDetail, int sellerId);

	 public TaxDetail addMonthlyTDSDetail(Session session, TaxDetail taxDetail, int sellerId);

	 public void addStatusTDSDetail(TaxDetail taxDetail, int sellerId) throws CustomException;
}