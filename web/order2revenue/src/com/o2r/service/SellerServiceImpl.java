package com.o2r.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.o2r.dao.SellerDao;
import com.o2r.helper.CustomException;
import com.o2r.model.Seller;

/**
 * @author Deep Mehrotra
 *
 */
@Service("sellerService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SellerServiceImpl implements SellerService {

	@Autowired
	private SellerDao sellerDao;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addSeller(Seller seller) throws CustomException {
		sellerDao.addSeller(seller);
	}

	public List<Seller> listSellers() throws CustomException {
		return sellerDao.listSeller();
	}

	public Seller getSeller(int sellerid) throws CustomException {
		return sellerDao.getSeller(sellerid);
	}

	public void deleteSeller(Seller seller) throws CustomException {
		sellerDao.deleteSeller(seller);
	}

	public Seller getSeller(String email) throws CustomException {
		return sellerDao.getSeller(email);
	}

	public void planUpgrade(int pid, int sellerid) throws CustomException {
		sellerDao.planUpgrade(pid, sellerid);
	}
}