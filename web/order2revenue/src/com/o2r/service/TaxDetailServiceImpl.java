package com.o2r.service;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.o2r.dao.TaxDetailsDao;
import com.o2r.helper.CustomException;
import com.o2r.model.Product;
import com.o2r.model.TaxCategory;
import com.o2r.model.TaxDetail;

/**
 * @author Deep Mehortra
 *
 */
@Service("taxDetailService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TaxDetailServiceImpl implements TaxDetailService {

	@Autowired
	private TaxDetailsDao taxDetailDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addTaxDetail(TaxDetail taxDetail, int sellerId) {
		taxDetailDao.addTaxDetail(taxDetail, sellerId);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<TaxDetail> listTaxDetails(int sellerId) throws CustomException {
		return taxDetailDao.listTaxDetails(sellerId);

	}

	@Override
	public TaxDetail getTaxDetail(int taxDetailId) {
		return taxDetailDao.getTaxDetail(taxDetailId);
	}

	@Override
	public void deleteTaxDetail(TaxDetail taxDetail, int sellerId) {
		taxDetailDao.deleteTaxDetail(taxDetail, sellerId);
	}

	@Override
	public TaxCategory addTaxCategory(TaxCategory taxCategory, int sellerId)
			throws CustomException {
		return taxDetailDao.addTaxCategory(taxCategory, sellerId);
	}

	@Override
	public TaxCategory getTaxCategory(int tcId) {
		return taxDetailDao.getTaxCategory(tcId);
	}
	
	@Override
	public TaxCategory getTaxCategory(Product product, int sellerId,
			String zipcode) throws CustomException {
		return taxDetailDao.getTaxCategory(product, sellerId, zipcode);
	}

	@Override
	public List<TaxCategory> listTaxCategories(int sellerId)
			throws CustomException {
		return taxDetailDao.listTaxCategories(sellerId);
	}

	@Override
	public void deleteTaxCategory(TaxCategory taxCategory, int sellerId)
			throws CustomException {
		taxDetailDao.deleteTaxCategory(taxCategory, sellerId);
	}

	@Override
	public TaxCategory getTaxCategory(String catName, int sellerId)
			throws CustomException {
		return taxDetailDao.getTaxCategory(catName, sellerId);
	}

	@Override
	public void addPaymentTaxDetail(TaxDetail taxDetail, int sellerId)
			throws CustomException {
		taxDetailDao.addPaymentTaxDetail(taxDetail, sellerId);
	}

	@Override
	public TaxDetail addMonthlyTDSDetail(Session session, TaxDetail taxDetail,
			int sellerId) {
		return taxDetailDao.addMonthlyTDSDetail(session, taxDetail, sellerId);
	}

	@Override
	public void addStatusTDSDetail(TaxDetail taxDetail, int sellerId)
			throws CustomException {
		taxDetailDao.addStatusTDSDetail(taxDetail, sellerId);
	}

	@Override
	public TaxDetail addMonthlyTaxDetail(Session session, TaxDetail taxDetail,
			int sellerId) {
		// TODO Auto-generated method stub
		return taxDetailDao.addMonthlyTaxDetail(session, taxDetail, sellerId);
	}

	@Override
	public List<TaxDetail> listTaxDetails(int sellerId, String taxOrTds)
			throws CustomException {
		return taxDetailDao.listTaxDetails(sellerId, taxOrTds);
	}
	
	@Override
	public void removeProductMapping(int tcId, int sellerId)
			throws CustomException {
		taxDetailDao.removeProductMapping(tcId, sellerId);
	}
	@Override
	public Map<String, Float> getTaxCategoryMap(int sellerId) throws CustomException
	{
		return taxDetailDao.getTaxCategoryMap(sellerId);
	}
}