package com.o2r.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.o2r.dao.PartnerDao;
import com.o2r.helper.CustomException;
import com.o2r.model.MetaPartner;
import com.o2r.model.Partner;

/**
 * @author Deep Mehrotra
 *
 */
@Service("partnerService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PartnerServiceImpl implements PartnerService {

	@Autowired
	private PartnerDao partnerDao;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addPartner(Partner partner, int sellerId)
			throws CustomException {
		partnerDao.addPartner(partner, sellerId);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addMetaPartner(MetaPartner partner)
			throws CustomException {
		partnerDao.addMetaPartner(partner);
	}

	public List<Partner> listPartners(int sellerId) throws CustomException {
		return partnerDao.listPartner(sellerId);
	}

	public Partner getPartner(long partnerid) throws CustomException {
		return partnerDao.getPartner(partnerid);
	}

	public void deletePartner(Partner partner, int sellerId)
			throws CustomException {
		partnerDao.deletePartner(partner, sellerId);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Partner getPartner(String name, int sellerId) throws CustomException {
		return partnerDao.getPartner(name, sellerId);
	}

	@Override
	public MetaPartner getMetaPartner(String partnerName)
			throws CustomException { 
		return partnerDao.getMetaPartner(partnerName);
	}
}